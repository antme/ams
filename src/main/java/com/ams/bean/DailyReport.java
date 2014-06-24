package com.ams.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = DailyReport.TABLE_NAME)
public class DailyReport extends BaseEntity {

	public static final String TASK_ID = "taskId";

	public static final String IS_COMMENTED = "isCommented";

	public static final String PLAN = "plan";

	public static final String REPORT_DAY = "reportDay";

	public static final String SUMMARY = "summary";

	public static final String WORKING_RECORD = "workingRecord";

	public static final String MATERIAL_RECORD = "materialRecord";

	public static final String WEATHER = "weather";

	public static final String USER_ID = "userId";

	public static final String PROJECT_ID = "projectId";

	public static final String TABLE_NAME = "DailyReport";

	@Column(name = PROJECT_ID)
	public String projectId;

	@Column(name = TASK_ID)
	public String taskId;

	@Column(name = USER_ID)
	public String userId;

	@Column(name = WEATHER)
	public String weather;

	@Column(name = MATERIAL_RECORD)
	public String materialRecord;

	@Column(name = WORKING_RECORD)
	public String workingRecord;

	@Column(name = SUMMARY)
	public String summary;

	@Column(name = REPORT_DAY)
	public Date reportDay;

	@Column(name = PLAN)
	public String plan;

	@Column(name = IS_COMMENTED)
	public Boolean isCommented;

	

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getMaterialRecord() {
		return materialRecord;
	}

	public void setMaterialRecord(String materialRecord) {
		this.materialRecord = materialRecord;
	}

	public String getWorkingRecord() {
		return workingRecord;
	}

	public void setWorkingRecord(String workingRecord) {
		this.workingRecord = workingRecord;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getReportDay() {
		return reportDay;
	}

	public void setReportDay(Date reportDay) {
		this.reportDay = reportDay;
	}

	public Boolean getIsCommented() {
		return isCommented;
	}

	public void setIsCommented(Boolean isCommented) {
		this.isCommented = isCommented;
	}

	public static void main(String args[]) {
		DailyReport report = new DailyReport();
		report.setId("");
		report.setMaterialRecord("");
		report.setWorkingRecord("");
		report.setSummary("");
		report.setPlan("");
		report.setWeather("");
		report.setProjectId("");
		report.setUserId("");

		System.out.println(report.toString());

		DailyReportComment comment = new DailyReportComment();
		comment.setUserName("经理一");
		comment.setUserId("");
		comment.setComment("计划安排不详细");
		comment.setDailyReportId("");

		System.out.println(comment.toString());

	}

}
