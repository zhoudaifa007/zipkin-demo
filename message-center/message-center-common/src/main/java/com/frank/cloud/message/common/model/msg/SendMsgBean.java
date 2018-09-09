package com.frank.cloud.message.common.model.msg;

import java.util.List;

import com.frank.cloud.message.common.enums.MsgType;

public class SendMsgBean {
	
	private String title;
	
	private List<String> userIds;
	
	private String content;
	
	//1.评论；2.回复；3.通知（管理员）；4私信;5.版主消息；6.个人消息
	//若是通知类型，则userIds可以为空
	private MsgType type;
	
	//文章，评论，回复id；与消息类型有关
	private Long articleId;
	
	private Long commentId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public MsgType getType() {
		return type;
	}

	public void setType(MsgType type) {
		this.type = type;
	}
}
