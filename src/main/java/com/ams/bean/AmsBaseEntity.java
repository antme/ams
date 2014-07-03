package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.eweblib.annotation.column.BooleanColumn;
import com.eweblib.annotation.column.IntegerColumn;
import com.eweblib.bean.BaseEntity;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AmsBaseEntity extends BaseEntity {

	public static final String DISPLAY_FOR_APP = "displayForApp";

	public static final String DISPLAY_ORDER = "displayOrder";

	@IntegerColumn
	@Column(name = DISPLAY_ORDER)
	public Integer displayOrder;

	@BooleanColumn
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
