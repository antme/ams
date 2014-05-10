package com.ams.bean;

import java.util.Date;

import com.eweblib.bean.BaseEntity;

public class Project extends BaseEntity {

	public String projectName;

	public String projectManagerId;



	public Date projectStartDate;

	public Date projectEndDate;
	
	public Double projectTotalDays;

	public Double projectRemainingDays;

	public Double projectUserdDays;

	public Double userWokedDays;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectManagerId() {
		return projectManagerId;
	}

	public void setProjectManagerId(String projectManagerId) {
		this.projectManagerId = projectManagerId;
	}


	public Date getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public Date getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public Double getProjectRemainingDays() {
		return projectRemainingDays;
	}

	public void setProjectRemainingDays(Double remaremainingDays) {
		this.projectRemainingDays = remaremainingDays;
	}

	public Double getProjectUserdDays() {
		return projectUserdDays;
	}

	public void setProjectUserdDays(Double userdDays) {
		this.projectUserdDays = userdDays;
	}

	
	
	public Double getProjectTotalDays() {
		return projectTotalDays;
	}

	public void setProjectTotalDays(Double totalDays) {
		this.projectTotalDays = totalDays;
	}
	
	

	public Double getUserWokedDays() {
		return userWokedDays;
	}

	public void setUserWokedDays(Double userWokedDays) {
		this.userWokedDays = userWokedDays;
	}

	public static void main(String args[]){
		Project project = new Project();
		project.setId("");
		project.setProjectName("");
		project.setProjectStartDate(new Date());
		project.setProjectEndDate(new Date());
		project.setProjectRemainingDays(11d);
		project.setProjectUserdDays(44d);
		project.setProjectTotalDays(55d);
		project.setUserWokedDays(11.5);
		
		System.out.println(project.toString());
		
	}

}
