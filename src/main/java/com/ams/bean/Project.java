package com.ams.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Project.TABLE_NAME)
public class Project extends BaseEntity {

	public static final String CUSTOMER_ID = "customerId";

	public static final String PROJECT_STATUS = "projectStatus";

	public static final String DEPARTMENT_ID = "departmentId";

	public static final String PROJECT_DESCRIPTION = "projectDescription";

	public static final String PROJECT_USED_DAYS = "projectUsedDays";

	public static final String PROJECT_REMAINING_DAYS = "projectRemainingDays";

	public static final String PROJECT_TOTAL_DAYS = "projectTotalDays";

	public static final String PROJECT_END_DATE = "projectEndDate";

	public static final String PROJECT_START_DATE = "projectStartDate";

	public static final String PROJECT_MANAGER_ID = "projectManagerId";

	public static final String PROJECT_NAME = "projectName";

	public static final String TABLE_NAME = "Project";
	
	@Column(name = PROJECT_NAME)
	public String projectName;

	@Column(name = PROJECT_MANAGER_ID)
	public String projectManagerId;

	@Column(name = PROJECT_START_DATE)
	public Date projectStartDate;

	@Column(name = PROJECT_END_DATE)
	public Date projectEndDate;
	
	@Column(name = DEPARTMENT_ID)
	public String departmentId;
	

	@Column(name = PROJECT_DESCRIPTION)
	public String projectDescription;
	
	@Column(name = PROJECT_STATUS)
	public Integer projectStatus;
	
	@Column(name = CUSTOMER_ID)
	public String customerId;
	
	
	
	public Integer projectTotalDays;

	public Integer projectRemainingDays;

	public Integer projectUsedDays;
	
	public Double userWorkedDays;
	

	public Integer getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Integer projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

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

	public Integer getProjectRemainingDays() {
		return projectRemainingDays;
	}

	public void setProjectRemainingDays(Integer remaremainingDays) {
		this.projectRemainingDays = remaremainingDays;
	}


	
	
	public Integer getProjectUsedDays() {
		return projectUsedDays;
	}

	public void setProjectUsedDays(Integer projectUsedDays) {
		this.projectUsedDays = projectUsedDays;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public Integer getProjectTotalDays() {
		return projectTotalDays;
	}

	public void setProjectTotalDays(Integer totalDays) {
		this.projectTotalDays = totalDays;
	}
	
	

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Double getUserWorkedDays() {
		return userWorkedDays;
	}

	public void setUserWorkedDays(Double userWokedDays) {
		this.userWorkedDays = userWokedDays;
	}

	public static void main(String args[]){
		Project project = new Project();
		project.setId("");
		project.setProjectName("");
		project.setProjectStartDate(new Date());
		project.setProjectEndDate(new Date());
		project.setProjectRemainingDays(11);
		project.setProjectUsedDays(44);
		project.setProjectTotalDays(55);
		project.setUserWorkedDays(11.5);
		
		System.out.println(project.toString());
		
	}

}
