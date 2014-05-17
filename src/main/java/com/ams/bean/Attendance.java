package com.ams.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Attendance.TABLE_NAME)
public class Attendance extends BaseEntity {

	public static final String TEAM_ID = "teamId";

	public static final String TIME = "time";

	public static final String ATTENDANCE_TIME_SELECT_TYPE = "attendanceTimeSelectType";

	public static final String ATTENDANCE_TYPE = "attendanceType";

	public static final String ATTENDANCE_DAY_TYPE = "attendanceDayType";

	public static final String ATTENDANCE_DATE = "attendanceDate";

	public static final String OPERATOR_ID = "operatorId";

	public static final String USER_ID = "userId";

	public static final String TABLE_NAME = "Attendance";

	@Column(name = USER_ID)
	public String userId;

	@Column(name = TEAM_ID)
	public String teamId;

	@Column(name = OPERATOR_ID)
	public String operatorId;

	@Column(name = ATTENDANCE_DATE)
	public Date attendanceDate;

	@Column(name = ATTENDANCE_DAY_TYPE)
	public Integer attendanceDayType;

	@Column(name = ATTENDANCE_TYPE)
	public Integer attendanceType;

	@Column(name = ATTENDANCE_TIME_SELECT_TYPE)
	public Integer attendanceTimeSelectType;

	@Column(name = TIME)
	public Double time;

	public String userName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public Integer getAttendanceDayType() {
		return attendanceDayType;
	}

	public void setAttendanceDayType(Integer attendanceDayType) {
		this.attendanceDayType = attendanceDayType;
	}

	public Integer getAttendanceType() {
		return attendanceType;
	}

	public void setAttendanceType(Integer attendanceType) {
		this.attendanceType = attendanceType;
	}

	public Integer getAttendanceTimeSelectType() {
		return attendanceTimeSelectType;
	}

	public void setAttendanceTimeSelectType(Integer attendanceTimeSelectType) {
		this.attendanceTimeSelectType = attendanceTimeSelectType;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public static void main(String args[]) {

		List<Attendance> list = new ArrayList<Attendance>();

		Attendance ad = new Attendance();
		ad.setAttendanceDate(new Date());
		ad.setAttendanceDayType(0);
		ad.setAttendanceTimeSelectType(1);
		ad.setAttendanceType(0);
		ad.setOperatorId("05c07bcc-833e-4b22-a8be-3c3a63609ac8");
		ad.setUserId("12c07bcc-833e-4b22-a8be-3c3a63609ac8");
		ad.setTeamId("1098fcd9-8f05-4b44-938f-071e000b923e");
		ad.setTime(5d);

		list.add(ad);

		ad = new Attendance();
		ad.setAttendanceDate(new Date());
		ad.setAttendanceDayType(0);
		ad.setAttendanceTimeSelectType(1);
		ad.setAttendanceType(0);
		ad.setOperatorId("05c07bcc-833e-4b22-a8be-3c3a63609ac8");
		ad.setUserId("05c07bcc-833e-4b22-a8be-3c3a63609ac8");
		ad.setTeamId("1098fcd9-8f05-4b44-938f-071e000b923e");
		ad.setTime(5d);

		list.add(ad);
		System.out.println(list);

	}

}
