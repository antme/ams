package com.ams.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ams.bean.DailyReport;
import com.ams.bean.DailyReportComment;
import com.ams.bean.Project;
import com.ams.bean.vo.DailyReportVo;
import com.ams.service.IProjectService;
import com.ams.util.PermissionConstants;
import com.eweblib.annotation.column.LoginRequired;
import com.eweblib.annotation.column.Permission;
import com.eweblib.constants.EWebLibConstants;
import com.eweblib.controller.AbstractController;
import com.eweblib.exception.ResponseException;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;

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
	
	@RequestMapping("/app/report/list.do")
	public void listProjectsForApp(HttpServletRequest request, HttpServletResponse response) {
		parserJsonParameters(request, true);
		responseWithListData(projectService.listProjectsForAppDailyReport(), request, response);
	}
	
	
	@RequestMapping("/dailyreport/app/add.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void addDailyReport(HttpServletRequest request, HttpServletResponse response) {
		DailyReportVo report = (DailyReportVo) parserJsonParameters(request, false, DailyReportVo.class);

		if (EweblibUtil.isEmpty(report.getUserId())) {
			report.setUserId(EWeblibThreadLocal.getCurrentUserId());
		}

		if (EweblibUtil.isEmpty(report.getUserId())) {
			throw new ResponseException("请先登录");
		}
		if (EweblibUtil.isEmpty(report.getProjectId())) {
			throw new ResponseException("请先选择项目");
		}

		Integer images = report.getImagesCount();

		if (images == null) {
			images = 0;
		}
		String relativeFilePath = genRandomRelativePath(EWeblibThreadLocal.getCurrentUserId());

		List<String> pics = new ArrayList<String>();

		for (int i = 0; i < images; i++) {
			pics.add(uploadFile(request, relativeFilePath, "picData" + i));
		}

	
		if(report.getReportDay() == null){
			report.setReportDay(new Date());
		}
		DailyReport re = projectService.addDailyReport(report, pics);
		responseWithKeyValue(EWebLibConstants.ID, re.getId(), request, response);
	}
	
	@RequestMapping("/dailyreport/app/list.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void listDailyReport(HttpServletRequest request, HttpServletResponse response) {
		parserJsonParameters(request, true);
		responseWithDataPagnation(projectService.listDailyReport(), request, response);
	}
	
	@RequestMapping("/dailyreport/comment/add.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void addDailyReportComment(HttpServletRequest request, HttpServletResponse response) {
		DailyReportComment comment = (DailyReportComment)  parserJsonParameters(request, false, DailyReportComment.class);
		projectService.addDailyReportComment(comment);
		responseWithData(null, request, response);
	}
	

}
