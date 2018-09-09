package com.frank.cloud.message.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.frank.cloud.message.common.model.DO.CommentDO;
import com.frank.cloud.message.common.model.DO.MessageDO;
import com.frank.cloud.message.service.dao.MsgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.frank.cloud.message.common.model.DO.AdminMsgDO;
import com.frank.cloud.message.common.model.DO.ReplyDO;

@Component
public class MsgService  {

	@Autowired
	private MsgDao msgDao;
	
	/**
	 * 保存消息
	 * @param map
	 */
	public void save(Map<String,Object> map) {
		msgDao.insertMsg(map);
	}
	
	/**
	 * 添加用户信息
	 * @param map
	 */
	public void saveUserMsg(Map<String,Object> map) {
		msgDao.insertUserMsg(map);
	}
	
	/**
	 * 更新消息状态
	 * @param map
	 */
	public void update(Map<String,Object> map) {
		msgDao.updateMsg(map);
	}
	
	/**
	 * 获取消息列表
	 * @param map
	 * @return
	 */
	public List<MessageDO> queryByCondition(Map<String,Object> map) {
		List<MessageDO> list = new ArrayList<MessageDO>();
		list = msgDao.queryByCondition(map);
		Integer type  = (Integer) map.get("type");
		if(type == 1 || type == 2) {
			for(MessageDO l:list) {
				Long artId = l.getArticleId();
				if(artId != null) {
					Long forumId = msgDao.getForumId(artId);
					l.setForumId(forumId);
				}
				//获取原文，并且判断用户是否匿名回复
				if(type == 2) {
					Long replyId = l.getCommentId();
					if(replyId != null) {
						String s = null;
						ReplyDO r = msgDao.getReplyById(replyId);
						if(r != null) {
							Integer anonymous = r.getAnonymous();
							if(r.getParentId() != -1) {
								//回复回复
								r = msgDao.getReplyById(r.getParentId());
								if(r != null) {
									s = r.getContent();
								}
							}else {
								//获取评论的内容
								s = msgDao.getCommentById(r.getCommentId()).getContent();
							}
							if(anonymous != null && anonymous == 1) {
								l.setAuthor(null);
								l.setAvator(null);
							}
							l.setAnonymous(anonymous);
							l.setCommentId(r.getCommentId());
						}
						l.setOrigal(s);
						l.setReplyId(replyId);
					}
				}else {
					//判断用户是否匿名评论
					CommentDO c = msgDao.getCommentById(l.getCommentId());
					if(c != null) {
						if(c.getAnonymous() != null && c.getAnonymous() == 1) {
							l.setAuthor(null);
							l.setAvator(null);
						}
						l.setAnonymous(c.getAnonymous());
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取消息条数
	 * @param map
	 * @return
	 */
	public Integer countByCondition(Map<String,Object> map) {
		return msgDao.countByCondition(map);
	}
	
	/**
	 * 获取所有登录过的用户
	 * @return
	 */
	public List<String> getAllUser() {
		return msgDao.getAllUser();
	}
	
	public List<String> getMsgToUser(Long id) {
		return msgDao.getMsgToUser(id);
	}
	
	public List<AdminMsgDO> getAdminMsg(Map<String,Object> map) {
		return msgDao.getAdminMsg(map);
	}
	
	public Integer countAdminMsg(Map<String,Object> map) {
		return msgDao.countAdminMsg(map);
	}
	
	public void deleteMsg(List<Long> ids) {
		for(Long i:ids) {
			msgDao.deleteUserMsg(i);
			msgDao.deleteMsg(i);
		}
	}
	
	public void updateAllMsg(Map<String,Object> map) {
		msgDao.updateAllMsg(map);
	}
	
	public String getOrginaltById(Long id) {
		String s = null;
		ReplyDO r = msgDao.getReplyById(id);
		if(r != null) {
			if(r.getParentId() != -1) {
				//回复回复
				r = msgDao.getReplyById(r.getParentId());
				if(r != null) {
					s = r.getContent();
				}
			}else {
				//获取评论的内容
				s = msgDao.getCommentById(r.getCommentId()).getContent();
			}
		}
		return s;
	}
	
	public Long getForumId(Long id) {
		return msgDao.getForumId(id);
	}
}
