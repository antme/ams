package com.ams.bean;

import com.eweblib.bean.BaseEntity;

public class DailyReportComment extends BaseEntity {

	public String userId;

	public String dailyReportId;

	public String comment;

	public String commentDate;

	public String toUserId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDailyReportId() {
		return dailyReportId;
	}

	public void setDailyReportId(String dailyReportId) {
		this.dailyReportId = dailyReportId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	
	
}
