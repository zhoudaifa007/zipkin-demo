package com.frank.cloud.message.api.socketio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;

import com.frank.cloud.message.common.constant.CacheKey;
import com.frank.cloud.message.common.model.socket.ChatBean;
import com.frank.cloud.message.common.model.socket.LoginBean;
import com.frank.cloud.message.service.service.MsgService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;

@Component
public class SocketIOHandler {

    private static Logger logger = LoggerFactory.getLogger(SocketIOHandler.class);
	
	@Value("${sysConfig.cache_time}")
	protected Long cacheTime;
	
	@Value("${sysConfig.sso.url}")
	protected String ssoUrl;
	
	@Value("${sysConfig.sso.checkCookie}")
	protected String checkCookie;
	
	private static Set<SocketIOClient> clientSet = new CopyOnWriteArraySet<SocketIOClient>();
	
	private final SocketIOServer server;
	
	@Autowired
    private RedisTemplate<String, String> redisTemplate;
	
	@Autowired  
	protected RestTemplate restTemplate;  
	
	@Resource
	private MsgService msgService;
	
	@Autowired  
    public SocketIOHandler(SocketIOServer server)   
    {  
        this.server = server;  
    }
	
	/**
	 * 添加connect事件
	 * 
	 * @param client
	 */
	@OnConnect  
    public void onConnect(SocketIOClient client)  
    {
		UUID uuid = client.getSessionId();
		logger.info("【SocketIO】有新连接加入,uuid={}",JSONObject.toJSONString(uuid));
		clientSet.add(client);
    }
	
	/**
	 * 添加Disconnect事件
	 * 
	 * @param client
	 */
     @OnDisconnect
     public void onDisconnect(SocketIOClient client)
     {
    	 UUID uuid = client.getSessionId();
    	 clientSet.remove(client);
    	 logger.info("【SocketIO】有连接断开,uuid={}",JSONObject.toJSONString(uuid));
     }
     
     /**
      * 收到客户端消息
      * 
      * @param message
      */
     @OnEvent(value = "message")
     public void onEvent(SocketIOClient client,String msg)
     {
    	 logger.info("【SocketIO】收到客户端的消息:{}",msg);
     }
     
     /**
      * 建立聊天室
      * @param client
      * @param bean
      */
     @OnEvent(value = "chatroom")
     public void chatroom(SocketIOClient client,ChatBean bean){
    	 logger.info("【SocketIO】客户端建立聊天室:{}",JSONObject.toJSONString(bean));
    	 String sender = bean.getSender();
    	 String receiver = bean.getReceiver();
    	 String key = CacheKey.BBS_SOCKET_CLIENT+sender+"_"+receiver;
    	 String value = JSONObject.toJSONString(client.getSessionId());
		 redisTemplate.opsForValue().set(key, value, cacheTime, TimeUnit.SECONDS);
     }
     
     /**
      * 登录
      * 
      * @param message
      */
     @OnEvent(value = "login")
     public void login(SocketIOClient client,LoginBean msg)
     {
    	 String userId = msg.getUserId();
    	 logger.info("【SocketIO】来自客户端，登录消息,userId:{}",userId);
		 String key = CacheKey.BBS_SOCKET_CLIENT+userId;
		 String value = JSONObject.toJSONString(client.getSessionId());
		 redisTemplate.opsForSet().add(key, value);
		 redisTemplate.expire(key, cacheTime, TimeUnit.SECONDS);
		 JSONObject m = new JSONObject();
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("userId", userId);
		 map.put("status", 3);
		 //获取用户未读消息
		 Integer u = msgService.countByCondition(map);
		 m.put("unread", u);
		 client.sendEvent("unread", m);
     }
     
     /**
      * 发送消息
      * @param msg
      */
     public void sendMessage(String name,JSONObject msg,String uid,String sid)
     {
    	Set<String> os =  redisTemplate.opsForSet().members(CacheKey.BBS_SOCKET_CLIENT+uid);
    	Set<String> ol = new HashSet<String>();
    	logger.info("【sid={},uid={},name={}】【SocketIO】sendMessage获取uuid={}",sid,uid,name,os);
    	if(CollectionUtils.isNotEmpty(os))
    	{
    		for(String o:os) {
    			UUID uuid = JSONObject.parseObject(o, UUID.class);
        		SocketIOClient client = server.getClient(uuid);
        		if(client == null){
        			ol.add(o);
             	}else {
        			logger.info("【sid={},uid={},name={}】【SocketIO】给客户端发送消息:{}",sid,uid,name,msg.toJSONString());
        			client.sendEvent(name, msg);
        		}
    		}
    		if(CollectionUtils.isNotEmpty(ol)) {
    			if(redisTemplate.hasKey(CacheKey.BBS_SOCKET_CLIENT+uid)) {
    				redisTemplate.opsForSet().remove(CacheKey.BBS_SOCKET_CLIENT+uid,ol.toArray());
    			}
    		}
    	}
     }
     
     /**
      * 群发消息
      * @param msg
      */
     public void broastMessage(String name,JSONObject msg,String uid,String sid)
     {
    	 logger.info("【sid={},uid={},name={}】【SocketIO】给客户端群发消息:{}",sid,uid,name,msg.toJSONString());
    	 
    	 for (SocketIOClient client : clientSet) 
         {
    		 client.sendEvent(name, msg);
         }
     }
}
