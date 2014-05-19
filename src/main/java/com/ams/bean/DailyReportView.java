package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = DailyReportView.TABLE_NAME)
public class DailyReportView extends BaseEntity {
	public static final String USER_ID = "userId";

	public static final String DAILY_REPORT_ID = "dailyReportId";

	public static final String TABLE_NAME = "DailyReportView";

	@Column(name = DAILY_REPORT_ID)
	public String dailyReportId;

	@Column(name = USER_ID)
	public String userId;

	public String getDailyReportId() {
		return dailyReportId;
	}

	public void setDailyReportId(String dailyReportId) {
		this.dailyReportId = dailyReportId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
