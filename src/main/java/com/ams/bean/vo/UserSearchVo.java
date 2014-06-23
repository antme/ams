package com.ams.bean.vo;

import com.eweblib.bean.BaseEntity;

public class UserSearchVo extends BaseEntity {

	public String userName;

	public String teamId;

	public String projectId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
