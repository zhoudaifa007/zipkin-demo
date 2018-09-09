package com.frank.cloud.message.common.model.msg;

public class TypeBean {
	
	//1.评论；2。回复；3.通知
	private Integer type;
	
	private Integer index;
	
	private Integer pageSize;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
