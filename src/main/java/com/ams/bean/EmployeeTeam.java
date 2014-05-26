package com.ams.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = EmployeeTeam.TABLE_NAME)
public class EmployeeTeam extends BaseEntity {
	public static final String TEAM_ID = "teamId";

	public static final String USER_ID = "userId";

	public static final String TABLE_NAME = "EmployeeTeam";

	@Column(name = USER_ID)
	public String userId;

	@Column(name = TEAM_ID)
	public String teamId;

	public String teamName;

	public Date attendanceDate;

	public Integer attendanceDayType;

	public Integer getAttendanceDayType() {
		return attendanceDayType;
	}

	public void setAttendanceDayType(Integer attendanceDayType) {
		this.attendanceDayType = attendanceDayType;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

}
