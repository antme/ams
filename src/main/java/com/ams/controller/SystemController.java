package com.ams.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ams.bean.Department;
import com.ams.bean.Log;
import com.ams.bean.Menu;
import com.ams.bean.RoleGroup;
import com.ams.bean.Salary;
import com.ams.bean.Task;
import com.ams.bean.UserLevel;
import com.ams.bean.UserType;
import com.ams.bean.vo.SearchVo;
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

		Salary temp = (Salary) parserJsonParameters(request, true, Salary.class);

		if (uploadFile == null) {
			throw new ResourceAccessException("请选择上传的文件");
		}
		try {
			InputStream inputStream = uploadFile.getInputStream();
			sys.importSalary(inputStream, temp);
		} catch (IOException e) {
			e.printStackTrace();
		}

		responseWithData(null, request, response);
	}

	@RequestMapping("/task/import.do")
	public void importTask(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile uploadFile = multipartRequest.getFile("taskFile");
		Task temp = (Task) parserJsonParameters(request, true, Task.class);

		try {
			InputStream inputStream = uploadFile.getInputStream();
			sys.importTask(inputStream, temp);
		} catch (IOException e) {
			e.printStackTrace();
		}

		responseWithData(null, request, response);
	}

	@RequestMapping("/usertype/add.do")
	public void addUserType(HttpServletRequest request, HttpServletResponse response) {

		UserType type = (UserType) parserJsonParameters(request, false, UserType.class);

		sys.addUserType(type);
		responseWithData(null, request, response);
	}

	@RequestMapping("/usertype/get.do")
	public void getUserType(HttpServletRequest request, HttpServletResponse response) {

		UserType type = (UserType) parserJsonParameters(request, false, UserType.class);

		responseWithEntity(sys.getUserType(type), request, response);
	}

	@RequestMapping("/usertype/list.do")
	public void listUserTypes(HttpServletRequest request, HttpServletResponse response) {

		UserType type = (UserType) parserJsonParameters(request, false, UserType.class);

		responseWithDataPagnation(sys.listUserTypes(type), request, response);
	}

	@RequestMapping("/userlevel/list.do")
	public void listUserLevels(HttpServletRequest request, HttpServletResponse response) {
		UserLevel level = (UserLevel) parserJsonParameters(request, false, UserLevel.class);
		responseWithDataPagnation(sys.listUserLevels(level), request, response);
	}

	@RequestMapping("/userlevel/add.do")
	public void addUserLevel(HttpServletRequest request, HttpServletResponse response) {

		UserLevel level = (UserLevel) parserJsonParameters(request, false, UserLevel.class);

		sys.addUserLevel(level);
		responseWithData(null, request, response);
	}

	@RequestMapping("/userlevel/get.do")
	public void getUserLevel(HttpServletRequest request, HttpServletResponse response) {

		UserLevel level = (UserLevel) parserJsonParameters(request, false, UserLevel.class);

		responseWithEntity(sys.getUserLevel(level), request, response);
	}

	@RequestMapping("/group/list.do")
	public void listUserGroups(HttpServletRequest request, HttpServletResponse response) {
		RoleGroup group = (RoleGroup) parserJsonParameters(request, false, RoleGroup.class);
		responseWithDataPagnation(sys.listUserGroups(group), request, response);
	}

	@RequestMapping("/group/add.do")
	public void addUserGroup(HttpServletRequest request, HttpServletResponse response) {

		RoleGroup group = (RoleGroup) parserJsonParameters(request, false, RoleGroup.class);

		sys.addUserGroup(group);
		responseWithData(null, request, response);
	}

	@RequestMapping("/group/get.do")
	public void getUserGroup(HttpServletRequest request, HttpServletResponse response) {

		RoleGroup group = (RoleGroup) parserJsonParameters(request, false, RoleGroup.class);

		responseWithEntity(sys.getUserGroup(group), request, response);
	}

	@RequestMapping("/group/delete.do")
	public void deleteUserGroup(HttpServletRequest request, HttpServletResponse response) {

		RoleGroup group = (RoleGroup) parserJsonParameters(request, false, RoleGroup.class);
		sys.deleteUserGroup(group);
		responseWithEntity(null, request, response);
	}

	@RequestMapping("/usertype/delete.do")
	public void deleteUserType(HttpServletRequest request, HttpServletResponse response) {

		UserType type = (UserType) parserJsonParameters(request, false, UserType.class);
		sys.deleteUserType(type);
		responseWithEntity(null, request, response);
	}

	@RequestMapping("/userlevel/delete.do")
	public void deleteUserLevel(HttpServletRequest request, HttpServletResponse response) {

		UserLevel level = (UserLevel) parserJsonParameters(request, false, UserLevel.class);
		sys.deleteUserLevel(level);
		responseWithEntity(null, request, response);
	}

	@RequestMapping("/department/delete.do")
	public void deleteDepartment(HttpServletRequest request, HttpServletResponse response) {

		Department dep = (Department) parserJsonParameters(request, false, Department.class);

		sys.deleteDepartment(dep);
		responseWithData(null, request, response);
	}

	@RequestMapping("/menu/create.do")
	public void createMenu(HttpServletRequest request, HttpServletResponse response) {

		Menu menu = (Menu) parserJsonParameters(request, false, Menu.class);

		sys.createMenu(menu.getItems());
		responseWithData(null, request, response);
	}

	@RequestMapping("/log/list.do")
	public void listLogs(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(sys.listLogs(vo), request, response);
	}

	@RequestMapping("/log/items/list.do")
	public void listLogItems(HttpServletRequest request, HttpServletResponse response) {
		Log vo = (Log) parserJsonParameters(request, false, Log.class);
		responseWithListData(sys.listLogItemss(vo), request, response);
	}

}
