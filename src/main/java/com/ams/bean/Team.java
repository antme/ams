package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Team.TABLE_NAME)
public class Team extends BaseEntity {
	public static final String DEPARTMENT_ID = "departmentId";

	public static final String TEAM_DESCRIPTION = "teamDescription";

	public static final String TEAM_NAME = "teamName";

	public static final String TABLE_NAME = "Team";

	@Column(name = TEAM_NAME)
	public String teamName;

	@Column(name = "teamLeaderId")
	public String teamLeaderId;

	@Column(name = "projectId")
	public String projectId;
	
	@Column(name = DEPARTMENT_ID)
	public String departmentId;

	@Column(name = TEAM_DESCRIPTION)
	public String teamDescription;
	
	
	
	public String departmentName;
	
	public Integer membersNumber;

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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Integer getMembersNumber() {
		return membersNumber;
	}

	public void setMembersNumber(Integer membersNumber) {
		this.membersNumber = membersNumber;
	}
	
	
	

}
