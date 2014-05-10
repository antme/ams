package com.ams.bean;

import java.util.Date;

import com.eweblib.bean.BaseEntity;

public class Pic extends BaseEntity {

	public String userId;

	public Date uploadDate;

	public String projectName;

	public String description;

	public String picUrl;
	
	
	public String dailyReportId;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	
	

	public String getDailyReportId() {
		return dailyReportId;
	}

	public void setDailyReportId(String dailyReportId) {
		this.dailyReportId = dailyReportId;
	}

	public static void main(String args[]){
		Pic pic = new Pic();
		pic.setDescription("");
		pic.setUserId("");
		pic.setProjectName("");
		
		System.out.println(pic.toString());
		
	}
}
