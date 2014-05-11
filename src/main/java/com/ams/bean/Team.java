package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Team.TABLE_NAME)
public class Team extends BaseEntity {
	public static final String TABLE_NAME = "Team";

	@Column(name = "teamName")
	public String teamName;

	@Column(name = "teamLeaderId")
	public String teamLeaderId;

	@Column(name = "projectId")
	public String projectId;
	
	@Column(name = "departmentId")
	public String departmentId;

	@Column(name = "teamDescription")
	public String teamDescription;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamLeaderId() {
		return teamLeaderId;
	}

	public void setTeamLeaderId(String teamLeaderId) {
		this.teamLeaderId = teamLeaderId;
	}


	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTeamDescription() {
		return teamDescription;
	}

	public void setTeamDescription(String teamDescription) {
		this.teamDescription = teamDescription;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	

}
