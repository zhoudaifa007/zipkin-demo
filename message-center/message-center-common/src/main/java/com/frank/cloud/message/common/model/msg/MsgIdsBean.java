package com.frank.cloud.message.common.model.msg;

import java.util.List;

public class MsgIdsBean {

	public List<Long> ids;
	
	public Integer type;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
