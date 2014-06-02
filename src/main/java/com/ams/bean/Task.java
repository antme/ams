package com.ams.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Task.TABLE_NAME)
public class Task extends BaseEntity {

	public static final String REMARK = "remark";

	public static final String PROJECT_TASK_ID = "projectTaskId";

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

	public static final String TABLE_NAME = "Task";

	@Column(name = TASK_NAME)
	public String taskName;

	@Column(name = PROJECT_ID)
	public String projectId;

	@Column(name = TEAM_ID)
	public String teamId;

	@Column(name = DISPLAY_ORDER)
	public Integer displayOrder;

	@Column(name = UNIT)
	public String unit;

	@Column(name = PRICE)
	public Double price;

	@Column(name = AMOUNT)
	public Double amount;

	@Column(name = DESCRIPTION)
	public String description;
	
	
	@Column(name = REMARK)
	public String remark;

	@Column(name = AMOUNT_DESCRIPTION)
	public String amountDescription;

	@Column(name = PRICE_DESCRIPTION)
	public String priceDescription;

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
	
	
	@Column(name = PROJECT_TASK_ID)
	public String projectTaskId;

	public Integer projectTotalDays;

	public Integer projectRemainingDays;

	public Integer projectUsedDays;

	public String memebers;

	public Double userWorkedDays;

	public String userName;
	
	public String overrideexists;
	
	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProjectTaskId() {
		return projectTaskId;
	}

	public void setProjectTaskId(String projectTaskId) {
		this.projectTaskId = projectTaskId;
	}

	public String getOverrideexists() {
		return overrideexists;
	}

	public void setOverrideexists(String overrideexists) {
		this.overrideexists = overrideexists;
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public Double getUserWorkedDays() {
		return userWorkedDays;
	}

	public void setUserWorkedDays(Double userWorkedDays) {
		this.userWorkedDays = userWorkedDays;
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

	public String getMemebers() {
		return memebers;
	}

	public void setMemebers(String memebers) {
		this.memebers = memebers;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getAmountDescription() {
		return amountDescription;
	}

	public void setAmountDescription(String amountDescription) {
		this.amountDescription = amountDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriceDescription() {
		return priceDescription;
	}

	public void setPriceDescription(String priceDescription) {
		this.priceDescription = priceDescription;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public static void main(String args[]) {
		Task task = new Task();
		task.setAmountDescription("4000㎡");
		task.setPriceDescription("1.2元");
		task.setProjectName("");
		task.setProjectStartDate(new Date());
		task.setProjectEndDate(new Date());
		task.setDescription("");
		task.setTeamName("");
		task.setMemebers("");

		System.out.println(task);

	}

}
