package com.ams.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import com.ams.bean.Attendance;
import com.ams.bean.Pic;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;

public interface IAttendanceService {

	EntityResults<Attendance> listAttendances(Attendance attendance);
	


	String exportAttendanceToExcle(Attendance attendance, HttpServletRequest request);



	String exportPicToExcle(Pic pic, HttpServletRequest request);  
    

}
