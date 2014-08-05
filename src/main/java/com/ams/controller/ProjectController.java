package com.ams.controller;

import java.io.IOException;
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

import com.ams.bean.Customer;
import com.ams.bean.CustomerContact;
import com.ams.bean.DailyReport;
import com.ams.bean.DailyReportComment;
import com.ams.bean.Project;
import com.ams.bean.ProjectTask;
import com.ams.bean.Task;
import com.ams.bean.vo.CustomerContactVo;
import com.ams.bean.vo.DailyReportVo;
import com.ams.bean.vo.SearchVo;
import com.ams.service.IProjectService;
import com.ams.util.PermissionConstants;
import com.eweblib.annotation.column.LoginRequired;
import com.eweblib.annotation.column.Permission;
import com.eweblib.bean.EntityResults;
import com.eweblib.bean.IDS;
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
	
	
	
	@RequestMapping("/customer/delete.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void deleteCustomer(HttpServletRequest request, HttpServletResponse response) {
		Customer customer = (Customer)  parserJsonParameters(request, false, Customer.class);
//		projectService.deleteCustomer(customer);
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
		Project project = (Project) parserJsonParameters(request, false, Project.class);
		responseWithDataPagnation(projectService.listProjects(project), request, response);
	}
	
	
	
	@RequestMapping("/app/list.do")
	public void listProjectsForAppAttendance(HttpServletRequest request, HttpServletResponse response) {

		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		
		if(vo.getUserId() == null){
			throw new ResponseException("请先登录");
		}
		responseWithListData(projectService.listProjectsForAppAttendance(vo), request, response);
	}
	

	
	@RequestMapping("/app/task/select.do")
	public void listProjectsForAppDailyReportSelect(HttpServletRequest request, HttpServletResponse response) {
		Task t = (Task) parserJsonParameters(request, false, Task.class);
		if (EweblibUtil.isEmpty(t.getUserId())) {
			throw new ResponseException("请先登录");
		}
		List<Project> plist = projectService.listProjectsForAppDailyReportSelect(t);

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		for (Project project : plist) {
			Map<String, Object> pmap = project.toMap();

			if (pmap.get(Project.PROJECT_START_DATE) == null) {
				pmap.put(Project.PROJECT_START_DATE, "");
			}
			if (pmap.get(Project.PROJECT_END_DATE) == null) {
				pmap.put(Project.PROJECT_END_DATE, "");
			}
			mapList.add(pmap);

		}

		Map<String, Object> list = new HashMap<String, Object>();
		list.put("rows", mapList);
		responseMsg(list, ResponseStatus.SUCCESS, request, response, null);

	}
	
	@RequestMapping("/app/task/list.do")
	public void listTasksForApp(HttpServletRequest request, HttpServletResponse response) {
		Task task = (Task) parserJsonParameters(request, false, Task.class);
		responseWithDataPagnation(projectService.listProjectTasks(task), request, response);
	}
	
	
	@RequestMapping("/projecttask/app/list.do")
	public void listProjectTasksForApp(HttpServletRequest request, HttpServletResponse response) {
		Task task = (Task) parserJsonParameters(request, false, Task.class);
		
		if (EweblibUtil.isEmpty(task.getUserId())) {
			throw new ResponseException("请先登录");
		}
		responseWithDataPagnation(projectService.listProjectTasksForApp(task), request, response);
	}
	
	@RequestMapping("/projecttask/app/detail.do")
	public void getProjectTaskDetails(HttpServletRequest request, HttpServletResponse response) {
		ProjectTask task = (ProjectTask) parserJsonParameters(request, true, ProjectTask.class);		
		if (EweblibUtil.isEmpty(task.getUserId())) {
			throw new ResponseException("请先登录");
		}
		
		if (EweblibUtil.isEmpty(task.getId())) {
			throw new ResponseException("请选择要查看的项目");
		}
		
		responseWithEntity(projectService.getProjectTaskDetails(task), request, response);
	}
	
	
	@RequestMapping("/projecttask/list.do")
	public void listAllProjectTasks(HttpServletRequest request, HttpServletResponse response) {
		ProjectTask task = (ProjectTask) parserJsonParameters(request, true, ProjectTask.class);
		responseWithDataPagnation(projectService.listAllProjectTasks(task), request, response);
	}
	
	@RequestMapping("/projecttask/task/list.do")
	public void listAllTasksFromProjectTasks(HttpServletRequest request, HttpServletResponse response) {
		Task task = (Task) parserJsonParameters(request, true, Task.class);
		
		responseWithListData(projectService.listAllTasksFromProjectTasks(task), request, response);
	}
	
	
	
	@RequestMapping("/projecttask/delete.do")
	public void deleteProjectTasks(HttpServletRequest request, HttpServletResponse response) {
		IDS ids = (IDS) parserJsonParameters(request, true, IDS.class);
		projectService.deleteProjectTasks(ids);
		responseWithData(null, request, response);
	}
	
	
	
	
	@RequestMapping("/task/list.do")
	public void listAllTasksFor(HttpServletRequest request, HttpServletResponse response) {
		Task task = (Task) parserJsonParameters(request, true, Task.class);
		responseWithDataPagnation(projectService.listAllTasksFor(task), request, response);
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
		String relativeFilePath = genRandomRelativePath(report.getUserId());

		List<String> pics = new ArrayList<String>();

		for (int i = 0; i < images; i++) {
			pics.add(uploadFile(request, relativeFilePath, "picData" + i, 512, null));
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

		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		if (EweblibUtil.isEmpty(vo.getUserId())) {
			throw new ResponseException("请先登录");
		}

		EntityResults<DailyReportVo> results = projectService.listDailyReport(report, true);

		Map<String, Object> list = new HashMap<String, Object>();
		list.put("total", results.getPagnation().getTotal());

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		for (DailyReportVo dr : results.getEntityList()) {

			Map<String, Object> map = dr.toMap();

			Map<String, Object> projectMap = dr.getTaskInfo().toMap();

			if (projectMap.get(Project.PROJECT_START_DATE) == null) {
				projectMap.put(Project.PROJECT_START_DATE, "");
			}
			if (projectMap.get(Project.PROJECT_END_DATE) == null) {
				projectMap.put(Project.PROJECT_END_DATE, "");
			}

			map.put("taskInfo", projectMap);
			mapList.add(map);

		}
		list.put("rows", mapList);

		responseMsg(list, ResponseStatus.SUCCESS, request, response, null);

	}
	
	
	@RequestMapping("/dailyreport/plan/app/list.do")
	public void listDailyReportPlan(HttpServletRequest request, HttpServletResponse response) {
		DailyReportVo report = (DailyReportVo) parserJsonParameters(request, false, DailyReportVo.class);
		
		SearchVo vo = (SearchVo)parserJsonParameters(request, false, SearchVo.class);
		
		if(vo.getUserId() == null){
			vo.setUserId(EWeblibThreadLocal.getCurrentUserId());
		}

		
		if (EweblibUtil.isEmpty(vo.getUserId())) {			
			throw new ResponseException("请先登录");
		}
		
		responseWithListData(projectService.listDailyReportPlan(report), request, response);
	}
	
	@RequestMapping("/dailyreport/list.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void listAllDailyReport(HttpServletRequest request, HttpServletResponse response) {
		DailyReportVo report = (DailyReportVo) parserJsonParameters(request, false, DailyReportVo.class);
		
		responseWithDataPagnation(projectService.listDailyReport(report, false), request, response);
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
		
		if (EweblibUtil.isEmpty(comment.getUserId())) {			
			throw new ResponseException("请先登录");
		}
		projectService.addDailyReportComment(comment);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/customer/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listCustomers(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, true, SearchVo.class);
	
		responseWithDataPagnation(projectService.listCustomers(vo), request, response);
	}
	
	
	@RequestMapping("/customer/get.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void getCustomerInfo(HttpServletRequest request, HttpServletResponse response) {
		Customer customer = (Customer) parserJsonParameters(request, false, Customer.class);
		responseWithEntity(projectService.getCustomerInfo(customer), request, response);
	}
	
	

	@RequestMapping("/dailyreport/export.do")
	public void exportDailyReportToExcle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 以流的形式下载文件。

		DailyReportVo report = (DailyReportVo) parserJsonParameters(request, false, DailyReportVo.class);

		String path = projectService.exportDailyReportToExcle(report, request);

		exportFile(response, path);

	}
	
	

	@RequestMapping("/customer/contact/add.do")
	public void addCustomerContact(HttpServletRequest request, HttpServletResponse response)  {
		// 以流的形式下载文件。

		CustomerContactVo vo = (CustomerContactVo) parserJsonParameters(request, false, CustomerContactVo.class);
		List<CustomerContact> concats = parserListJsonParameters(request, false, CustomerContact.class);

		projectService.addCustomerContact(vo, concats);

		responseWithData(null, request, response);
	}
	
	@RequestMapping("/customer/contact/list.do")
	public void listCustomerContact(HttpServletRequest request, HttpServletResponse response)  {
		// 以流的形式下载文件。

		CustomerContactVo vo = (CustomerContactVo) parserJsonParameters(request, false, CustomerContactVo.class);
		

		responseWithListData(projectService.listCustomerContact(vo), request, response);
	}
	
	

}
