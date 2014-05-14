package com.ams.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = DailyReportComment.TABLE_NAME)
public class DailyReportComment extends BaseEntity {

	public static final String USER_ID = "userId";

	public static final String DAILY_REPORT_ID = "dailyReportId";

	public static final String COMMENT = "comment";

	public static final String COMMENT_DATE = "commentDate";

	public static final String TABLE_NAME = "DailyReportComment";

	@Column(name = USER_ID)
	public String userId;

	@Column(name = DAILY_REPORT_ID)
	public String dailyReportId;

	@Column(name = COMMENT)
	public String comment;

	@Column(name = COMMENT_DATE)
	public Date commentDate;

	public String userName;

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

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
