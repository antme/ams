package com.ams.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ams.service.ISystemService;
import com.eweblib.annotation.column.LoginRequired;
import com.eweblib.annotation.column.Permission;

@Controller
@RequestMapping("/ams/sys")
@Permission()
@LoginRequired()
public class SystemController extends AmsController {

	@Autowired
	private ISystemService sys;

	@RequestMapping("/salary/import.do")
	public void importSalary(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile uploadFile = multipartRequest.getFile("salaryFile");
		try {
			InputStream inputStream = uploadFile.getInputStream();
			sys.importSalary(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		responseWithData(null, request, response);
	}
	
	
	@RequestMapping("/task/import.do")
	public void importTask(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile uploadFile = multipartRequest.getFile("taskFile");
		try {
			InputStream inputStream = uploadFile.getInputStream();
			sys.importTask(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		responseWithData(null, request, response);
	}

}
