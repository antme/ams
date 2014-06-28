package com.ams.bean;

import javax.persistence.Column;

import com.eweblib.annotation.column.IntegerColumn;
import com.eweblib.bean.BaseEntity;

public class AmsBaseEntity extends BaseEntity {

	public static final String DISPLAY_FOR_APP = "displayForApp";

	public static final String DISPLAY_ORDER = "displayOrder";

	@IntegerColumn
	@Column(name = DISPLAY_ORDER)
	public Integer displayOrder;

	@Column(name = DISPLAY_FOR_APP)
	public Boolean displayForApp;

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

}
