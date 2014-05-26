package com.ams.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ams.bean.Attendance;
import com.ams.service.IAttendanceService;
import com.eweblib.annotation.column.LoginRequired;
import com.eweblib.annotation.column.Permission;

@Controller
@RequestMapping("/ams/attendance")
@Permission()
@LoginRequired()
public class AttendanceController extends AmsController {

	
	
	@Autowired
	private IAttendanceService attendanceService;
	
	
	@RequestMapping("/list.do")
	public void listAttendances(HttpServletRequest request, HttpServletResponse response) {
		
		Attendance attendance = (Attendance) parserJsonParameters(request, false, Attendance.class);
		
		responseWithDataPagnation(attendanceService.listAttendances(attendance), request, response);
	}
	
    
    @RequestMapping("/export")
    //@RoleValidate(roleID=RoleValidConstants.SALES_CONTRACT_ADD, desc = RoleValidConstants.SALES_CONTRACT_ADD_DESC)
    public void exportAttendanceToExcle(HttpServletRequest request, HttpServletResponse response){    
    	Attendance attendance = (Attendance) parserJsonParameters(request, false, Attendance.class);
    	responseWithKeyValue("file", attendanceService.exportAttendanceToExcle(attendance), request, response);
    }  
    
}
