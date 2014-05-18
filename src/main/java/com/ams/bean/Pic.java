package com.ams.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Pic.TABLE_NAME)
public class Pic extends BaseEntity {
	public static final String UPLOAD_ADDRESS = "uploadAddress";

	public static final String LAT = "lat";

	public static final String LNG = "lng";

	public static final String DAILY_REPORT_ID = "dailyReportId";

	public static final String PIC_URL = "picUrl";

	public static final String DESCRIPTION = "description";

	public static final String PROJECT_NAME = "projectName";

	public static final String USER_ID = "userId";

	public static final String TABLE_NAME = "Pic";

	@Column(name = USER_ID)
	public String userId;

	@Column(name = PROJECT_NAME)
	public String projectName;

	@Column(name = DESCRIPTION)
	public String description;

	@Column(name = PIC_URL)
	public String picUrl;

	@Column(name = DAILY_REPORT_ID)
	public String dailyReportId;

	@Column(name = LNG)
	public Double lng;

	@Column(name = LAT)
	public Double lat;

	@Column(name = UPLOAD_ADDRESS)
	public String uploadAddress;
	
	
	public String userName;

	public Integer imagesCount;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Integer getImagesCount() {
		return imagesCount;
	}

	public void setImagesCount(Integer imagesCount) {
		this.imagesCount = imagesCount;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getUploadAddress() {
		return uploadAddress;
	}

	public void setUploadAddress(String uploadAddress) {
		this.uploadAddress = uploadAddress;
	}

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static void main(String args[]) {
		Pic pic = new Pic();
		pic.setDescription("");
		pic.setUserId("");
		pic.setProjectName("");

		System.out.println(pic.toString());

	}
}
