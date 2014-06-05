package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = EmployeeProject.TABLE_NAME)
public class EmployeeProject extends BaseEntity {
	public static final String PROJECT_ID = "projectId";

	public static final String USER_ID = "userId";

	public static final String TABLE_NAME = "EmployeeProject";

	@Column(name = USER_ID)
	public String userId;

	@Column(name = PROJECT_ID)
	public String projectId;
	
	
	public String userName;
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
