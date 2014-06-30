package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = Team.TABLE_NAME)
public class Team extends AmsBaseEntity {
	public static final String PROJECT_ID = "projectId";

	public static final String TEAM_LEADER_ID = "teamLeaderId";

	public static final String DEPARTMENT_ID = "departmentId";

	public static final String TEAM_DESCRIPTION = "teamDescription";

	public static final String TEAM_NAME = "teamName";

	public static final String TABLE_NAME = "Team";

	@Column(name = TEAM_NAME)
	public String teamName;

	@Column(name = TEAM_LEADER_ID)
	public String teamLeaderId;

	@Column(name = PROJECT_ID)
	public String projectId;
	
	@Column(name = DEPARTMENT_ID)
	public String departmentId;

	@Column(name = TEAM_DESCRIPTION)
	public String teamDescription;
	
	
	public String workTimePeriod;
	
	public String departmentName;
	
	public String userName;
	
	public String projectName;
	
	
	public Integer membersNumber;
	
	public String teamMembers;
	
	public String[] teamMemberIds;

	public String[] projectIds;
	
	
	
	public String[] getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(String[] projectIds) {
		this.projectIds = projectIds;
	}

	public String getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(String teamMembers) {
		this.teamMembers = teamMembers;
	}

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

	public String[] getTeamMemberIds() {
		return teamMemberIds;
	}

	public void setTeamMemberIds(String[] teamMemberIds) {
		this.teamMemberIds = teamMemberIds;
	}

	public String getWorkTimePeriod() {
		return workTimePeriod;
	}

	public void setWorkTimePeriod(String workTimePeriod) {
		this.workTimePeriod = workTimePeriod;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
	
	

}
