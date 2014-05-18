package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;
import com.google.gson.annotations.Expose;

@Table(name = User.TABLE_NAME)
public class User extends BaseEntity {

	public static final String TABLE_NAME = "User";

	public static final String PASSWORD = "password";
	public static final String USER_NAME = "userName";

	@Column(name = USER_NAME, unique = true)
	@Expose
	public String userName;

	@Column(name = PASSWORD)
	@Expose
	public String password;

	public static final String USER_LEVEL_ID = "userLevelId";

	public static final String USER_CODE = "userCode";

	public static final String MOBILE_NUMBER = "mobileNumber";

	public static final String STATUS = "status";

	public static final String NAME = "name";

	@Column(name = MOBILE_NUMBER)
	public String mobileNumber;

	@Column(name = "userTypeId")
	public String userTypeId;

	@Column(name = USER_CODE)
	public String userCode;

	@Column(name = USER_LEVEL_ID)
	public String userLevelId;

	@Column(name = NAME)
	@Expose
	public String name;

	@Column(name = STATUS)
	@Expose
	public Integer status;

	public String teams;

	public String userType;

	public String userLevel;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}