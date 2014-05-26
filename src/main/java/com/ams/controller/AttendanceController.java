package com.ams.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
	// @RoleValidate(roleID=RoleValidConstants.SALES_CONTRACT_ADD, desc =
	// RoleValidConstants.SALES_CONTRACT_ADD_DESC)
	public void exportAttendanceToExcle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Attendance attendance = (Attendance) parserJsonParameters(request, false, Attendance.class);
		// 以流的形式下载文件。

		String path = attendanceService.exportAttendanceToExcle(attendance, request);

		exportFile(response, path);

	}


}
