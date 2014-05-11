package com.ams.bean;

import java.util.Date;

import com.eweblib.bean.BaseEntity;

public class Attendance extends BaseEntity {

	public String userId;

	public String operatorId;

	public Date attendanceDate;

	public Integer attendanceDayType;

	public Integer attendanceType;

	public Integer attendanceTimeSelectType;

	public Double time;

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
	
	
	
	public static void main(String args[]){
		
		Attendance ad = new Attendance();
		ad.setAttendanceDate(new Date());
		ad.setAttendanceDayType(0);
		ad.setAttendanceTimeSelectType(1);
		ad.setAttendanceType(0);
		ad.setOperatorId("");
		ad.setUserId("");
		ad.setTime(5d);
		
		System.out.println(ad);
		
	}

}
