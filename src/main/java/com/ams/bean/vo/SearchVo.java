package com.ams.bean.vo;

import com.eweblib.bean.BaseEntity;

public class SearchVo extends BaseEntity {



	public String keyword;

	
	public String userId;


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}
	

	

}
