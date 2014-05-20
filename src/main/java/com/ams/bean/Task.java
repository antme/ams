package com.ams.bean;

import java.util.Date;

import javax.persistence.Column;

import com.eweblib.bean.BaseEntity;

public class Task extends BaseEntity {
	
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
	
	@Column(name = "projectId")
	public String projectId;

	@Column(name = "teamId")
	public String teamId;
	
	public Integer displayOrder;

	public String unit;

	public Double price;
	
	public Double amount;
	
	public String description;

	public String amountDescription;
	public String priceDescription;

	public String teamName;

	public String memebers;
	
	public String projectName;

	public Date projectStartDate;

	public Date projectEndDate;

	public Integer projectTotalDays;

	public Integer projectRemainingDays;

	public Integer projectUsedDays;
	
	public Double userWorkedDays;

	
	
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
