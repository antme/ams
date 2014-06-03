package com.ams.service;

import javax.servlet.http.HttpServletRequest;

import com.ams.bean.Attendance;
import com.ams.bean.Pic;
import com.eweblib.bean.EntityResults;

public interface IAttendanceService {

	EntityResults<Attendance> listAttendances(Attendance attendance);
	


	String exportAttendanceToExcle(Attendance attendance, HttpServletRequest request);



	String exportPicToExcle(Pic pic, HttpServletRequest request);  
    

}
