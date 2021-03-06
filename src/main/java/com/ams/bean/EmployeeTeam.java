package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = EmployeeTeam.TABLE_NAME)
public class EmployeeTeam extends BaseEntity {
	public static final String TEAM_ID = "teamId";

	public static final String USER_ID = "userId";

	public static final String TABLE_NAME = "EmployeeTeam";

	@Column(name = USER_ID)
	public String userId;

	@Column(name = TEAM_ID)
	public String teamId;

	public String teamName;

	public String userName;

	public String projectId;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

}
