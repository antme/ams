package com.ams.bean.vo;

import java.util.List;

import com.ams.bean.DailyReport;
import com.ams.bean.DailyReportComment;

public class DailyReportVo extends DailyReport {

	
	public String userName;

	public List<String> pics;
	
	public List<String> removedPics;

	public List<DailyReportComment> comments;
	
	public Integer imagesCount;
	
	

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
}
