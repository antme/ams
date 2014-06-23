package com.ams.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = Project.TABLE_NAME)
public class Project extends AmsBaseEntity {

	public static final String PROJECT_ATTENDANCE_MANAGER_ID = "projectAttendanceManagerId";

	public static final String WORK_TIME_PERIOD = "workTimePeriod";

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
	
	@Column(name = WORK_TIME_PERIOD)
	public String workTimePeriod;

	@Column(name = PROJECT_DESCRIPTION)
	public String projectDescription;
	
	@Column(name = PROJECT_STATUS)
	public String projectStatus;
	
	@Column(name = CUSTOMER_ID)
	public String customerId;
	
	@Column(name = PROJECT_ATTENDANCE_MANAGER_ID)
	public String projectAttendanceManagerId;
	
	
	
	
	public Integer projectTotalDays;

	public Integer projectRemainingDays;

	public Integer projectUsedDays;
	
	public Double userWorkedDays;
	
	public String[] projectMemberIds;
	
	
	public String userName;
	
	public String departmentName;
	

	public String projectAttendanceManagerName;
	
	public String projectManagerName;
	
	public String customerName;
	
	
	public String prjectMembers;
	
	

	
	public String getPrjectMembers() {
		return prjectMembers;
	}

	public void setPrjectMembers(String prjectMembers) {
		this.prjectMembers = prjectMembers;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProjectAttendanceManagerName() {
		return projectAttendanceManagerName;
	}

	public void setProjectAttendanceManagerName(String projectAttendanceManagerName) {
		this.projectAttendanceManagerName = projectAttendanceManagerName;
	}

	public String getProjectManagerName() {
		return projectManagerName;
	}

	public void setProjectManagerName(String projectManagerName) {
		this.projectManagerName = projectManagerName;
	}

	public String getProjectAttendanceManagerId() {
		return projectAttendanceManagerId;
	}

	public void setProjectAttendanceManagerId(String projectAttendanceManagerId) {
		this.projectAttendanceManagerId = projectAttendanceManagerId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getWorkTimePeriod() {
		return workTimePeriod;
	}

	public void setWorkTimePeriod(String workTimePeriod) {
		this.workTimePeriod = workTimePeriod;
	}

	public String[] getProjectMemberIds() {
		return projectMemberIds;
	}

	public void setProjectMemberIds(String[] projectMemberIds) {
		this.projectMemberIds = projectMemberIds;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
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
