package com.ams.bean;

import java.util.Date;
import java.util.List;

import com.eweblib.bean.BaseEntity;

public class DailyReport extends BaseEntity {

	public String projectId;
	
	public String userId;

	public String weather;

	public String materialRecord;

	public String workingRecord;

	public String summary;
	
	public Date reportDay;
	

	public String plan;

	public List<String> pics;

	public List<DailyReportComment> comments;

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

	public List<String> getPics() {
		return pics;
	}

	public void setPics(List<String> pics) {
		this.pics = pics;
	}

	public List<DailyReportComment> getComments() {
		return comments;
	}

	public void setComments(List<DailyReportComment> comments) {
		this.comments = comments;
	}
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public static void main(String args[]){
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
		comment.setCommentDate(new Date());
		comment.setDailyReportId("");
		
		System.out.println(comment.toString());
		
	}

}
