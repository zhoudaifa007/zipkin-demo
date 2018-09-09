package com.frank.cloud.message.service.dao;

import java.util.List;
import java.util.Map;
import com.frank.cloud.message.common.model.DO.AdminMsgDO;
import com.frank.cloud.message.common.model.DO.CommentDO;
import com.frank.cloud.message.common.model.DO.MessageDO;
import com.frank.cloud.message.common.model.DO.ReplyDO;

public interface MsgDao {
	
	void insertMsg(Map<String,Object> map);
	
	void updateMsg(Map<String,Object> map);
	
	void insertUserMsg(Map<String,Object> map);
	
	List<MessageDO> queryByCondition(Map<String,Object> map);
	
	Integer countByCondition(Map<String,Object> map);
	
	List<String> getAllUser();
	
	List<String> getMsgToUser(Long id);
	
	List<AdminMsgDO> getAdminMsg(Map<String,Object> map);
	
	Integer countAdminMsg(Map<String,Object> map);
	
	void deleteMsg(Long id);
	
	void deleteUserMsg(Long id);
	
	void updateAllMsg(Map<String,Object> map);
	
	ReplyDO getReplyById(Long id);
	
	CommentDO getCommentById(Long id);
	
	Long getForumId(Long id);
}
