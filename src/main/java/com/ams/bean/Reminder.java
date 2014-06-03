package com.ams.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Reminder.TABLE_NAME)
public class Reminder extends BaseEntity {
	public static final String USER_ID = "userId";

	public static final String CONTENT = "content";

	public static final String REMIND_DATE = "remindDate";

	public static final String TITLE = "title";

	public static final String TABLE_NAME = "Reminder";

	@Column(name = TITLE)
	public String title;

	@Column(name = REMIND_DATE)
	public Date remindDate;

	@Column(name = CONTENT)
	public String content;

	@Column(name = USER_ID)
	public String userId;

	public String keyword;

	// 查询今天的
	public Boolean isQueryToday;

	public Date startDate;

	public Date endDate;

	public String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getRemindDate() {
		return remindDate;
	}

	public void setRemindDate(Date remindDate) {
		this.remindDate = remindDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getIsQueryToday() {
		return isQueryToday;
	}

	public void setIsQueryToday(Boolean isQueryTotay) {
		this.isQueryToday = isQueryTotay;
	}

}
