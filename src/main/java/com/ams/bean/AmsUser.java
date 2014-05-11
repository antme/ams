package com.ams.bean;

import java.util.UUID;

import javax.persistence.Column;

import com.eweblib.bean.User;

public class AmsUser extends User {

	@Column(name = "mobileNumber")
	public String mobileNumber;

	@Column(name = "userTypeId")
	public String userTypeId;

	@Column(name = "userCode")
	public String userCode;

	@Column(name = "userLevelId")
	public String userLevelId;

	public String teams;
	
	public String userType;
	
	public String userLevel;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}



	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	
	public String getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getUserLevelId() {
		return userLevelId;
	}

	public void setUserLevelId(String userLevelId) {
		this.userLevelId = userLevelId;
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

	public String getTeams() {
		return teams;
	}

	public void setTeams(String teams) {
		this.teams = teams;
	}

	public static void main(String args[]) {
		AmsUser user = new AmsUser();
		user.setUserName("test");
		user.setId(UUID.randomUUID().toString());
		user.setPassword("test");
		user.setMobileNumber("13818638563");

		user.setUserType("油漆工");
		user.setUserLevel("油漆工一级");
		user.setUserCode("T00001");

		user.setTeams("施工一队， 施工二队");

		System.out.println(user);
	}

}
