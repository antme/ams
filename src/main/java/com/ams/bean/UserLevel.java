package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = UserLevel.TABLE_NAME)
public class UserLevel extends AmsBaseEntity {

	public static final String USER_TYPE_ID = "userTypeId";

	public static final String LEVEL_DESCRIPTION = "levelDescription";

	public static final String LEVEL_NAME = "levelName";

	public static final String TABLE_NAME = "UserLevel";

	@Column(name = LEVEL_NAME)
	public String levelName;

	@Column(name = LEVEL_DESCRIPTION)
	public String levelDescription;

	@Column(name = USER_TYPE_ID)
	public String userTypeId;
	
	
	public String userName;
	
	public String typeName;
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelDescription() {
		return levelDescription;
	}

	public void setLevelDescription(String levelDescription) {
		this.levelDescription = levelDescription;
	}

	public String getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}

}
