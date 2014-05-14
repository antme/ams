package com.ams.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ams.bean.Project;
import com.ams.service.IProjectService;
import com.ams.util.PermissionConstants;
import com.eweblib.annotation.column.LoginRequired;
import com.eweblib.annotation.column.Permission;
import com.eweblib.controller.AbstractController;

@Controller
@RequestMapping("/ams/project")
@Permission()
@LoginRequired()
public class ProjectController extends AbstractController {

	@Autowired
	private IProjectService projectService;
	
	
	@RequestMapping("/add.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void addProject(HttpServletRequest request, HttpServletResponse response) {
		Project project = (Project)  parserJsonParameters(request, false, Project.class);
		projectService.addProject(project);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/list.do")
	public void listProjects(HttpServletRequest request, HttpServletResponse response) {
		parserJsonParameters(request, true);
		responseWithDataPagnation(projectService.listProjects(), request, response);
	}
	
	@RequestMapping("/app/list.do")
	public void listProjectsForApp(HttpServletRequest request, HttpServletResponse response) {
		parserJsonParameters(request, true);
		responseWithDataPagnation(projectService.listProjectsForApp(), request, response);
	}
	
}
