package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = UserType.TABLE_NAME)
public class UserType extends AmsBaseEntity {

	public static final String TABLE_NAME = "UserType";

	public static final String TYPE_NAME = "typeName";

	public static final String TYPE_DESCRIPTION = "typeDescription";

	@Column(name = TYPE_NAME)
	public String typeName;

	@Column(name = TYPE_DESCRIPTION)
	public String typeDescription;

	public String userName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
