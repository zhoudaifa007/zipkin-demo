package com.frank.cloud.message.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.frank.cloud.message.common.enums.ErrorCode;
import com.frank.cloud.message.common.exception.AppException;
import com.frank.cloud.message.common.model.DO.AdminMsgDO;
import com.frank.cloud.message.common.model.DO.MessageDO;
import com.frank.cloud.message.common.model.RoleInfoDTO;
import com.frank.cloud.message.common.model.ServerResponse;
import com.frank.cloud.message.common.model.msg.MsgIdsBean;
import com.frank.cloud.message.common.model.msg.SendMsgBean;
import com.frank.cloud.message.common.model.msg.TypeBean;
import com.frank.cloud.message.common.util.Assert;
import com.frank.cloud.message.service.service.MsgService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.frank.cloud.message.api.socketio.SocketIOHandler;

@RestController
@RequestMapping("/v1/bbs/message")
public class MsgController {

	@Resource
	protected MsgService msgService;

	@Resource
	protected SocketIOHandler socketIOHandler;
	
	@Value("${sysConfig.appId}")
	protected String appId;
	
	@Value("${sysConfig.appKey}")
	protected String appKey;
	
	public final Logger logger = LoggerFactory.getLogger(MsgController.class);
	
	/**
	 * 发送消息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/send/message",  method = RequestMethod.POST,consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	public ServerResponse<Object> sendMsg(HttpServletRequest request, HttpServletResponse response, @RequestBody SendMsgBean bean) {
		String sid = UUID.randomUUID().toString().replace("-", "");
		JSONObject s = new JSONObject();
		long startTime = System.currentTimeMillis();
		//记录请求参数
		logger.info("[sid={}],/v1/bbs/message/send/msg request===>{}",sid,JSONObject.toJSON(bean));
		
		//检验参数
		checkSendMsg(bean);
		Map<String,Object> map = new HashMap<String,Object>();
		Integer type = bean.getType().getCode();
		map.put("content", bean.getContent());
		map.put("title", bean.getTitle());
		map.put("type", type);
		map.put("articleId", bean.getArticleId());
		map.put("commentId", bean.getCommentId());
		String userId = request.getHeader("userId");
		if(userId == null) {
			throw new AppException(ErrorCode.AUTH_FAILED);
		}
		map.put("author", userId);
		map.put("createTime", new Date());
		msgService.save(map);
		Long id = (Long) map.get("id");
		map.clear();
		map.put("msgId", id);
		//1.是已读；2.是删除；3.是未读
		map.put("status", 3);
		if(type == 3) {
			List<String> all = bean.getUserIds();
			if(CollectionUtils.isEmpty(all)) {
				//此时发送给所有登录过的人
				all = msgService.getAllUser();
			}
			logger.info("[sid={}],/v1/bbs/message/all/users ===>{}",sid,JSONObject.toJSON(all));
			if(CollectionUtils.isNotEmpty(all)) {
				for(String a:all) {
					map.put("userId", a);
					msgService.saveUserMsg(map);
					Integer u = msgService.countByCondition(map);
					JSONObject msg = new JSONObject();
					msg.put("unread", u);
					msg.put("userId", a);
					socketIOHandler.sendMessage("unread", msg,a,sid);
				}
			}
		}else {
			List<String> list = bean.getUserIds();
			logger.info("[sid={}],/v1/bbs/message/list/users ===>{}",sid,JSONObject.toJSON(list));
			if(CollectionUtils.isNotEmpty(list)) {
				for(String l:list) {
					map.put("userId", l);
					msgService.saveUserMsg(map);
					Integer u = msgService.countByCondition(map);
					JSONObject msg = new JSONObject();
					msg.put("unread", u);
					msg.put("userId", l);
					socketIOHandler.sendMessage("unread", msg,l,sid);
				}
			}
		}
		logger.info("[sid={}],/v1/bbs/message/send/msg response={} || cost:{}ms",sid,s.toJSONString(),(System.currentTimeMillis()-startTime));
		return ServerResponse.successWithData(s);
	}


	@RequestMapping(value = "/test/message",  method = RequestMethod.GET)
	public ServerResponse<Object> sendMsg(HttpServletRequest request,HttpServletResponse response) {
		logger.info("+++++++");
		return ServerResponse.successWithData(null);
	}

	/**
	 * 获取系统消息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/system/message",  method = RequestMethod.POST,consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	public ServerResponse<Object> systemMsg(HttpServletRequest request,HttpServletResponse response,@RequestBody TypeBean bean) {
		String sid = UUID.randomUUID().toString().replace("-", "");
		JSONObject s = new JSONObject();
		long startTime = System.currentTimeMillis();
		//记录请求参数
		logger.info("[sid={}],/v1/bbs/message/system/msg request===>{}",sid,JSONObject.toJSON(bean));
		//检验参数
		checkMsg(bean);
		String userId = request.getHeader("userId");
		if(userId == null) {
			throw new AppException(ErrorCode.AUTH_FAILED);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", bean.getType());
		map.put("index", (bean.getIndex()-1)*bean.getPageSize());
		map.put("pageSize", bean.getPageSize());
		map.put("userId", userId);
		List<MessageDO> list = msgService.queryByCondition(map);
		Integer total = msgService.countByCondition(map);
		s.put("list", list);
		s.put("total", total);
		logger.info("[sid={}],/v1/bbs/message/system/msg response={} || cost:{}ms",sid,s.toJSONString(),(System.currentTimeMillis()-startTime));
		return ServerResponse.successWithData(s);
	}

	/**
	 * 获取用户未读消息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/unread",  method = RequestMethod.GET)
	public ServerResponse<Object> unreadMsg(HttpServletRequest request,HttpServletResponse response) {
		String sid = UUID.randomUUID().toString().replace("-", "");
		JSONObject s = new JSONObject();
		long startTime = System.currentTimeMillis();
		//记录请求参数
		Map<String,Object> map = new HashMap<String,Object>();
		String userId = request.getHeader("userId");
		if(userId == null) {
			throw new AppException(ErrorCode.AUTH_FAILED);
		}
		map.put("userId", userId);
		map.put("status", 3);
		logger.info("[sid={}],/v1/bbs/message/unread request===>{}",sid,JSONObject.toJSON(map));
		//获取评论信息条数
		map.put("type", 1);
		Integer commentNum = msgService.countByCondition(map);
		//获取回复信息条数
		map.put("type", 2);
		Integer replyNum = msgService.countByCondition(map);
		//获取通知信息条数
		map.put("type", 3);
		Integer informNum = msgService.countByCondition(map);
		s.put("commentNum", commentNum);
		s.put("replyNum", replyNum);
		s.put("informNum", informNum);
		logger.info("[sid={}],/v1/bbs/message/unread response={} || cost:{}ms",sid,s.toJSONString(),(System.currentTimeMillis()-startTime));
		return ServerResponse.successWithData(s);
	}
	
	/**
	 * 用户阅读或是删除消息
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping(value = "/edit/message",  method = RequestMethod.POST,consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	public ServerResponse<Object> editMsg(HttpServletRequest request,HttpServletResponse response,@RequestBody MsgIdsBean bean) {
		String sid = UUID.randomUUID().toString().replace("-", "");
		JSONObject s = new JSONObject();
		long startTime = System.currentTimeMillis();
		//记录请求参数
		logger.info("[sid={}],/v1/bbs/message/edit/msg request===>{}",sid,JSONObject.toJSON(bean));
		
		//检验参数
		checkEditMsg(bean);
		String userId = request.getHeader("userId");
		if(userId == null) {
			throw new AppException(ErrorCode.AUTH_FAILED);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ids", bean.getIds());
		map.put("status", bean.getType());
		msgService.update(map);
		logger.info("[sid={}],/v1/bbs/message/edit/msg response={} || cost:{}ms",sid,s.toJSONString(),(System.currentTimeMillis()-startTime));
		//发送socket消息通知前端
		map.clear();
		map.put("userId", userId);
		map.put("status", 3);
		Integer u = msgService.countByCondition(map);
		JSONObject msg = new JSONObject();
		msg.put("unread", u);
		msg.put("userId", userId);
		socketIOHandler.sendMessage("unread", msg,userId,sid);
		return ServerResponse.successWithData(s);
	}
	
	/**
	 * 管理员删除发送的广播消息
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping(value = "/admin/delete/msg",  method = RequestMethod.POST,consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	public ServerResponse<Object> deleteMsg(HttpServletRequest request,HttpServletResponse response,@RequestBody MsgIdsBean bean) {
		String sid = UUID.randomUUID().toString().replace("-", "");
		JSONObject s = new JSONObject();
		long startTime = System.currentTimeMillis();
		//记录请求参数
		logger.info("[sid={}],/v1/bbs/admin/delete/msg request===>{}",sid,JSONObject.toJSON(bean));
		
		//检验参数
		checkDeleteMsg(bean);
		String userId = request.getHeader("userId");
		if(userId == null) {
			throw new AppException(ErrorCode.AUTH_FAILED);
		}
		msgService.deleteMsg(bean.getIds());
		logger.info("[sid={}],/v1/bbs/admin/delete/msg response={} || cost:{}ms",sid,s.toJSONString(),(System.currentTimeMillis()-startTime));
		return ServerResponse.successWithData(s);
	}
	
	/**
	 * 删除所有信息
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping(value = "/delete/all/msg",  method = RequestMethod.POST,consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	public ServerResponse<Object> deleteAllMsg(HttpServletRequest request,HttpServletResponse response,@RequestBody MsgIdsBean bean) {
		String sid = UUID.randomUUID().toString().replace("-", "");
		JSONObject s = new JSONObject();
		long startTime = System.currentTimeMillis();
		//记录请求参数
		logger.info("[sid={}],/v1/bbs/delete/all/msg request===>{}",sid,JSONObject.toJSON(bean));
		
		//检验参数
		checkDeleteAllMsg(bean);
		String userId = request.getHeader("userId");
		if(userId == null) {
			throw new AppException(ErrorCode.AUTH_FAILED);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", bean.getType());
		map.put("userId", userId);
		map.put("status", 2);
		msgService.updateAllMsg(map);
		//发送socket消息通知前端
		map.clear();
		map.put("userId", userId);
		map.put("status", 3);
		Integer u = msgService.countByCondition(map);
		JSONObject msg = new JSONObject();
		msg.put("unread", u);
		msg.put("userId", userId);
		socketIOHandler.sendMessage("unread", msg,userId,sid);
		logger.info("[sid={}],/v1/bbs/delete/all/msg response={} || cost:{}ms",sid,s.toJSONString(),(System.currentTimeMillis()-startTime));
		return ServerResponse.successWithData(s);
	}
	
	/**
	 * 异常统一处理
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(AppException.class)
	@ResponseBody
	protected JSONObject handleBASException(AppException e) 
	{
		JSONObject result = new JSONObject();
		result.put("code", e.getErrorCode().getCode());
		result.put("msg", e.getMsg());
		return result;
	}
	
	//检查发送消息请求参数
	protected void checkSendMsg(SendMsgBean bean) {
		Assert.assertNotEmpty(bean.getTitle(), "title");
		Assert.assertNotEmpty(bean.getContent(), "content");
		Assert.assertIntegerNotEmpty(bean.getType().getCode(), "type");
		if(bean.getType().getCode()==0) {
			throw new AppException(ErrorCode.INVALID_PARAMETER,"type");
		}
	}
	
	//检验获取系统消息参数
	protected void checkMsg(TypeBean bean) {
		Assert.assertIntegerNotEmpty(bean.getIndex(), "index");
		Assert.assertIntegerNotEmpty(bean.getPageSize(), "pageSize");
		Assert.assertIntegerNotEmpty(bean.getType(), "type");
	}
	
	//检验管理员获取广播请求参数
	protected void checkAdminMsg(TypeBean bean) {
		Assert.assertIntegerNotEmpty(bean.getIndex(), "index");
		Assert.assertIntegerNotEmpty(bean.getPageSize(), "pageSize");
	}
	
	//检验获取编辑消息请求参数
	protected void checkEditMsg(MsgIdsBean bean) {
		Assert.assertNotNumArrayEmpty(bean.getIds(), "ids");
		Assert.assertIntegerNotEmpty(bean.getType(), "type");
	}
	
	//检验获取编辑消息请求参数
	protected void checkDeleteMsg(MsgIdsBean bean) {
		Assert.assertNotNumArrayEmpty(bean.getIds(), "ids");
	}
	
	//检验删除所有信息请求参数
	protected void checkDeleteAllMsg(MsgIdsBean bean) {
		Assert.assertIntegerNotEmpty(bean.getType(), "type");
	}
}
