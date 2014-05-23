package com.ams.bean;

import com.eweblib.bean.BaseEntity;

public class AmsBaseEntity extends BaseEntity {

	public String keyword;

	public String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
