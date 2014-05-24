package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;
import com.google.gson.annotations.Expose;

@Table(name = User.TABLE_NAME)
public class User extends BaseEntity {

	public static final String USER_CATEGORY = "userCategory";

	public static final String BSTATUS = "bstatus";

	public static final String USER_TYPE_ID = "userTypeId";

	public static final String TABLE_NAME = "User";

	public static final String PASSWORD = "password";
	public static final String USER_NAME = "userName";

	public static final String USER_LEVEL_ID = "userLevelId";

	public static final String USER_CODE = "userCode";

	public static final String MOBILE_NUMBER = "mobileNumber";

	public static final String STATUS = "status";

	public static final String NAME = "name";

	@Column(name = USER_NAME, unique = true)
	@Expose
	public String userName;

	@Column(name = PASSWORD)
	@Expose
	public String password;

	@Column(name = MOBILE_NUMBER)
	public String mobileNumber;

	@Column(name = USER_TYPE_ID)
	public String userTypeId;

	@Column(name = USER_CODE)
	public String userCode;

	@Column(name = USER_LEVEL_ID)
	public String userLevelId;

	@Column(name = STATUS)
	@Expose
	public Integer status;

	@Column(name = BSTATUS)
	@Expose
	public Integer bstatus;
	
	@Column(name = USER_CATEGORY)
	public String userCategory;
	

	public String teams;

	public String userType;

	public String userLevel;

	public String levelName;
	public String typeName;

	public String userPassword;
	
	public String imgCode;
	
	

	public String getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(String userCategory) {
		this.userCategory = userCategory;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public Integer getBstatus() {
		return bstatus;
	}

	public void setBstatus(Integer bstatus) {
		this.bstatus = bstatus;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserLevelId() {
		return userLevelId;
	}

	public void setUserLevelId(String userLevelId) {
		this.userLevelId = userLevelId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTeams() {
		return teams;
	}

	public void setTeams(String teams) {
		this.teams = teams;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}
