package com.ams.bean;

import java.util.UUID;

import com.eweblib.bean.User;

public class AmsUser extends User {

	public String mobileNumber;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	

	public String userType;

	public String userCode;

	public String userLevel;
	
	public String teams;
	
	
	public static void main(String args[]){
		AmsUser user = new AmsUser();
		user.setUserName("test");
		user.setId(UUID.randomUUID().toString());
		user.setPassword("test");
		user.setMobileNumber("13818638563");
		
		
		System.out.println(user);
	}

}
