package com.ams.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = ProjectTask.TABLE_NAME)
public class ProjectTask extends BaseEntity{

	public static final String ADDRESS = "address";

	public static final String TASK_FILE_NAME = "taskFileName";

	public static final String USER_ID = "userId";

	public static final String TEAM_NAME = "teamName";

	public static final String TASK_PERIOD = "taskPeriod";

	public static final String TASK_CONTACT_PHONE = "taskContactPhone";

	public static final String PRICE_DESCRIPTION = "priceDescription";

	public static final String AMOUNT_DESCRIPTION = "amountDescription";

	public static final String DESCRIPTION = "description";

	public static final String AMOUNT = "amount";

	public static final String PRICE = "price";

	public static final String UNIT = "unit";

	public static final String DISPLAY_ORDER = "displayOrder";

	public static final String TEAM_ID = "teamId";

	public static final String PROJECT_ID = "projectId";

	public static final String TASK_NAME = "taskName";

	public static final String PROJECT_USED_DAYS = "projectUsedDays";

	public static final String PROJECT_REMAINING_DAYS = "projectRemainingDays";

	public static final String PROJECT_TOTAL_DAYS = "projectTotalDays";

	public static final String PROJECT_END_DATE = "projectEndDate";

	public static final String PROJECT_START_DATE = "projectStartDate";

	public static final String PROJECT_NAME = "projectName";

	public static final String TABLE_NAME = "ProjectTask";

	@Column(name = PROJECT_ID)
	public String projectId;

	@Column(name = TEAM_ID)
	public String teamId;

	@Column(name = DESCRIPTION)
	public String description;

	@Column(name = PROJECT_NAME)
	public String projectName;

	@Column(name = PROJECT_START_DATE)
	public Date projectStartDate;

	@Column(name = PROJECT_END_DATE)
	public Date projectEndDate;

	@Column(name = TEAM_NAME)
	public String teamName;

	@Column(name = TASK_CONTACT_PHONE)
	public String taskContactPhone;

	@Column(name = TASK_PERIOD)
	public String taskPeriod;

	@Column(name = USER_ID)
	public String userId;
	
	public static final String IS_DELETED = "isDeleted";

	@Column(name = IS_DELETED)
	public Boolean isDeleted;
	
	@Column(name = TASK_FILE_NAME)
	public String taskFileName;
	
	@Column(name = ADDRESS)
	public String address;
	
	
	public double price;
	

	public Integer projectTotalDays;

	public Integer projectRemainingDays;

	public Integer projectUsedDays;
	
	public String memebers;
	
	public String taskName;
	
	
	public List<Task> tasks;
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getMemebers() {
		return memebers;
	}

	public void setMemebers(String memebers) {
		this.memebers = memebers;
	}

	public Integer getProjectTotalDays() {
		return projectTotalDays;
	}

	public void setProjectTotalDays(Integer projectTotalDays) {
		this.projectTotalDays = projectTotalDays;
	}

	public Integer getProjectRemainingDays() {
		return projectRemainingDays;
	}

	public void setProjectRemainingDays(Integer projectRemainingDays) {
		this.projectRemainingDays = projectRemainingDays;
	}

	public Integer getProjectUsedDays() {
		return projectUsedDays;
	}

	public void setProjectUsedDays(Integer projectUsedDays) {
		this.projectUsedDays = projectUsedDays;
	}

	public String userName;


	public String getTaskFileName() {
		return taskFileName;
	}

	public void setTaskFileName(String taskFileName) {
		this.taskFileName = taskFileName;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

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

	public String getTaskContactPhone() {
		return taskContactPhone;
	}

	public void setTaskContactPhone(String taskContactPhone) {
		this.taskContactPhone = taskContactPhone;
	}

	public String getTaskPeriod() {
		return taskPeriod;
	}

	public void setTaskPeriod(String taskPeriod) {
		this.taskPeriod = taskPeriod;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
