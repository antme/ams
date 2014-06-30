package com.ams.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.annotation.column.IntegerColumn;
import com.eweblib.bean.BaseEntity;

@Table(name = Attendance.TABLE_NAME)
public class Attendance extends BaseEntity {

	public static final String YEAR = "year";

	public static final String MONTH = "month";

	public static final String DEPARTMENT_ID = "departmentId";

	public static final String PROJECT_ID = "projectId";

	public static final String MINUTES = "minutes";

	public static final String HOURS = "hours";

	public static final String TEAM_ID = "teamId";

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

	@Column(name = DEPARTMENT_ID)
	public String departmentId;

	@Column(name = PROJECT_ID)
	public String projectId;

	@Column(name = OPERATOR_ID)
	public String operatorId;

	@Column(name = ATTENDANCE_DATE)
	public Date attendanceDate;

	@Column(name = ATTENDANCE_DAY_TYPE)
	@IntegerColumn
	public Integer attendanceDayType;

	@Column(name = ATTENDANCE_TYPE)
	@IntegerColumn
	public Integer attendanceType;

	@Column(name = HOURS)
	@IntegerColumn
	public Integer hours;

	@Column(name = MINUTES)
	@IntegerColumn
	public Integer minutes;
	
	
	@IntegerColumn
	@Column(name = YEAR)
	public Integer year;
	
	@IntegerColumn
	@Column(name = MONTH)
	public Integer month;

	public String userName;

	public String operator;

	public String teamName;

	public String departmentName;

	public String projectName;

	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

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

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public static void main(String args[]) {

		List<Attendance> list = new ArrayList<Attendance>();

		Attendance ad = new Attendance();
		ad.setAttendanceDate(new Date());
		ad.setAttendanceDayType(0);
		ad.setHours(1);
		ad.setAttendanceType(0);
		ad.setOperatorId("05c07bcc-833e-4b22-a8be-3c3a63609ac8");
		ad.setUserId("12c07bcc-833e-4b22-a8be-3c3a63609ac8");
		ad.setTeamId("4758847b-f81e-4047-b31a-9a758085a469");
		ad.setMinutes(5);

		list.add(ad);

		ad = new Attendance();
		ad.setAttendanceDate(new Date());
		ad.setAttendanceDayType(0);
		ad.setHours(1);
		ad.setAttendanceType(0);
		ad.setOperatorId("05c07bcc-833e-4b22-a8be-3c3a63609ac8");
		ad.setUserId("05c07bcc-833e-4b22-a8be-3c3a63609ac8");
		ad.setTeamId("4758847b-f81e-4047-b31a-9a758085a469");
		ad.setMinutes(5);

		list.add(ad);
		System.out.println(list);

	}

}
