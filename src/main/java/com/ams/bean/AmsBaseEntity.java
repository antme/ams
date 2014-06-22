package com.ams.bean;

import javax.persistence.Column;

import com.eweblib.bean.BaseEntity;

public class AmsBaseEntity extends BaseEntity {

	public static final String DISPLAY_FOR_APP = "displayForApp";

	public static final String DISPLAY_ORDER = "displayOrder";

	@Column(name = DISPLAY_ORDER)
	public Integer displayOrder;

	@Column(name = DISPLAY_FOR_APP)
	public Boolean displayForApp;

	public String keyword;

	public String userId;

	public Boolean getDisplayForApp() {
		return displayForApp;
	}

	public void setDisplayForApp(Boolean displayForApp) {
		this.displayForApp = displayForApp;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

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
