package com.ams.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ams.bean.DailyReport;
import com.ams.bean.DailyReportComment;
import com.ams.bean.Project;
import com.ams.bean.vo.DailyReportVo;
import com.ams.bean.vo.SearchVo;
import com.ams.service.IProjectService;
import com.ams.util.PermissionConstants;
import com.eweblib.annotation.column.LoginRequired;
import com.eweblib.annotation.column.Permission;
import com.eweblib.constants.EWebLibConstants;
import com.eweblib.exception.ResponseException;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;

@Controller
@RequestMapping("/ams/project")
@Permission()
@LoginRequired()
public class ProjectController extends AmsController {

	@Autowired
	private IProjectService projectService;
	
	
	@RequestMapping("/add.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void addProject(HttpServletRequest request, HttpServletResponse response) {
		Project project = (Project)  parserJsonParameters(request, false, Project.class);
		projectService.addProject(project);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/get.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void getProject(HttpServletRequest request, HttpServletResponse response) {
		Project project = (Project) parserJsonParameters(request, false, Project.class);
		responseWithEntity(projectService.getProjectInfo(project), request, response);
	}
	
	
	@RequestMapping("/list.do")
	public void listProjects(HttpServletRequest request, HttpServletResponse response) {
		parserJsonParameters(request, true);
		responseWithDataPagnation(projectService.listProjects(), request, response);
	}
	
	@RequestMapping("/app/task/select.do")
	public void listProjectsForApp(HttpServletRequest request, HttpServletResponse response) {
		parserJsonParameters(request, true);
		responseWithListData(projectService.listProjectTasksForAppDailyReport(), request, response);
	}
	
	@RequestMapping("/app/task/list.do")
	public void listTasksForApp(HttpServletRequest request, HttpServletResponse response) {
		parserJsonParameters(request, true);
		responseWithDataPagnation(projectService.listProjectTasks(), request, response);
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
		
		if (EweblibUtil.isEmpty(report.getTaskId())) {
			throw new ResponseException("请先选择任务");
		}

		Integer images = report.getImagesCount();

		if (images == null) {
			images = 0;
		}
		String relativeFilePath = genRandomRelativePath(EWeblibThreadLocal.getCurrentUserId());

		List<String> pics = new ArrayList<String>();

		for (int i = 0; i < images; i++) {
			pics.add(uploadFile(request, relativeFilePath, "picData" + i, 512, new String[]{"gif","jpg","jpeg","png"}));
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
		DailyReportVo report = (DailyReportVo) parserJsonParameters(request, false, DailyReportVo.class);
		responseWithDataPagnation(projectService.listDailyReport(report), request, response);
	}
	
	@RequestMapping("/dailyreport/app/view.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void viewDailyReport(HttpServletRequest request, HttpServletResponse response) {
		DailyReportVo report = (DailyReportVo) parserJsonParameters(request, false, DailyReportVo.class);
		projectService.viewDailyReport(report);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/dailyreport/app/count.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void countDailyReport(HttpServletRequest request, HttpServletResponse response) {
		DailyReportVo report = (DailyReportVo) parserJsonParameters(request, false, DailyReportVo.class);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("count", projectService.countDailyReport(report));

		responseWithData(result, request, response);
	}
	
	@RequestMapping("/dailyreport/comment/add.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void addDailyReportComment(HttpServletRequest request, HttpServletResponse response) {
		DailyReportComment comment = (DailyReportComment)  parserJsonParameters(request, false, DailyReportComment.class);
		projectService.addDailyReportComment(comment);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/customer/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listCustomers(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, true, SearchVo.class);
	
		responseWithDataPagnation(projectService.listCustomers(vo), request, response);
	}
	
	

}
