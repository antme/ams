package com.ams.bean.vo;

import java.util.List;

import com.ams.bean.DailyReport;
import com.ams.bean.DailyReportComment;
import com.ams.bean.Task;

public class DailyReportVo extends DailyReport {

	
	public String userName;
	
	public Boolean isViewed;
	
	public String dailyReportId;
	

	public List<String> pics;
	
	public List<String> removedPics;

	public List<DailyReportComment> comments;
	
	public Integer imagesCount;
	
	
	public Task taskInfo;


	

	public Task getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(Task taskInfo) {
		this.taskInfo = taskInfo;
	}

	public Integer getImagesCount() {
		return imagesCount;
	}

	public void setImagesCount(Integer imagesCount) {
		this.imagesCount = imagesCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public List<String> getPics() {
		return pics;
	}

	public void setPics(List<String> pics) {
		this.pics = pics;
	}

	
	public List<String> getRemovedPics() {
		return removedPics;
	}

	public void setRemovedPics(List<String> removedPics) {
		this.removedPics = removedPics;
	}

	public List<DailyReportComment> getComments() {
		return comments;
	}

	public void setComments(List<DailyReportComment> comments) {
		this.comments = comments;
	}

	public Boolean getIsViewed() {
		return isViewed;
	}

	public void setIsViewed(Boolean isViewed) {
		this.isViewed = isViewed;
	}

	public String getDailyReportId() {
		return dailyReportId;
	}

	public void setDailyReportId(String dailyReportId) {
		this.dailyReportId = dailyReportId;
	}
	
	
	
}
