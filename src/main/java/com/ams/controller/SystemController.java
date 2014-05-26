package com.ams.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ams.bean.RoleGroup;
import com.ams.bean.UserLevel;
import com.ams.bean.UserType;
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

	
	@RequestMapping("/usertype/add.do")
	public void addUserType(HttpServletRequest request, HttpServletResponse response) {

		UserType type = (UserType)parserJsonParameters(request, false, UserType.class);
		
		sys.addUserType(type);
		responseWithData(null, request, response);
	}
	
	
	@RequestMapping("/usertype/get.do")
	public void getUserType(HttpServletRequest request, HttpServletResponse response) {

		UserType type = (UserType)parserJsonParameters(request, false, UserType.class);
	
		responseWithEntity(sys.getUserType(type), request, response);
	}
	
	@RequestMapping("/usertype/list.do")
	public void listUserTypes(HttpServletRequest request, HttpServletResponse response) {

		UserType type = (UserType)parserJsonParameters(request, false, UserType.class);
		
	
		responseWithDataPagnation(sys.listUserTypes(type), request, response);
	}

	@RequestMapping("/userlevel/list.do")
	public void listUserLevels(HttpServletRequest request, HttpServletResponse response) {
		UserLevel level = (UserLevel) parserJsonParameters(request, false, UserLevel.class);
		responseWithDataPagnation(sys.listUserLevels( level), request, response);
	}
	
	@RequestMapping("/userlevel/add.do")
	public void addUserLevel(HttpServletRequest request, HttpServletResponse response) {

		UserLevel level = (UserLevel)parserJsonParameters(request, false, UserLevel.class);
		
		sys.addUserLevel(level);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/userlevel/get.do")
	public void getUserLevel(HttpServletRequest request, HttpServletResponse response) {

		UserLevel level = (UserLevel)parserJsonParameters(request, false, UserLevel.class);
	
		responseWithEntity(sys.getUserLevel(level), request, response);
	}
	
	@RequestMapping("/group/list.do")
	public void listUserGroups(HttpServletRequest request, HttpServletResponse response) {
		RoleGroup group = (RoleGroup) parserJsonParameters(request, false, RoleGroup.class);
		responseWithDataPagnation(sys.listUserGroups( group), request, response);
	}
	
	@RequestMapping("/group/add.do")
	public void addUserGroup(HttpServletRequest request, HttpServletResponse response) {

		RoleGroup group = (RoleGroup)parserJsonParameters(request, false, RoleGroup.class);
		
		sys.addUserGroup(group);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/group/get.do")
	public void getUserGroup(HttpServletRequest request, HttpServletResponse response) {

		RoleGroup group = (RoleGroup)parserJsonParameters(request, false, RoleGroup.class);
	
		responseWithEntity(sys.getUserGroup(group), request, response);
	}
	
	
	@RequestMapping("/group/delete.do")
	public void deleteUserGroup(HttpServletRequest request, HttpServletResponse response) {

		RoleGroup group = (RoleGroup)parserJsonParameters(request, false, RoleGroup.class);
		sys.deleteUserGroup(group);
		responseWithEntity(null, request, response);
	}
	
	
}
