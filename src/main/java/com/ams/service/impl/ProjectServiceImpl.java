package com.ams.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ams.bean.Attendance;
import com.ams.bean.Customer;
import com.ams.bean.CustomerContact;
import com.ams.bean.DailyReport;
import com.ams.bean.DailyReportComment;
import com.ams.bean.DailyReportView;
import com.ams.bean.Department;
import com.ams.bean.EmployeeProject;
import com.ams.bean.EmployeeTeam;
import com.ams.bean.Pic;
import com.ams.bean.Project;
import com.ams.bean.ProjectTask;
import com.ams.bean.Task;
import com.ams.bean.Team;
import com.ams.bean.User;
import com.ams.bean.vo.CustomerContactVo;
import com.ams.bean.vo.DailyReportVo;
import com.ams.bean.vo.SearchVo;
import com.ams.service.IProjectService;
import com.ams.service.IUserService;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;
import com.eweblib.bean.IDS;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.service.AbstractService;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;
import com.eweblib.util.ExcelUtil;

@Service(value = "projectService")
public class ProjectServiceImpl extends AbstractService implements IProjectService {

	@Autowired
	private IUserService userService;

	@Override
	public void addProject(Project project) {

		if (EweblibUtil.isValid(project.getId())) {

			this.dao.updateById(project);

			DataBaseQueryBuilder teamQuery = new DataBaseQueryBuilder(Team.TABLE_NAME);
			teamQuery.and(Team.PROJECT_ID, project.getId());
			List<Team> teams = this.dao.listByQuery(teamQuery, Team.class);

			for (Team t : teams) {
				if (!t.getDepartmentId().equalsIgnoreCase(project.getDepartmentId())) {
					t.setDepartmentId(project.getDepartmentId());
					this.dao.updateById(t);
				}
			}

			DataBaseQueryBuilder attQuery = new DataBaseQueryBuilder(Attendance.TABLE_NAME);
			attQuery.and(Attendance.PROJECT_ID, project.getId());
			List<Attendance> attlist = this.dao.listByQuery(attQuery, Attendance.class);

			for (Attendance a : attlist) {
				if (!a.getDepartmentId().equalsIgnoreCase(project.getDepartmentId())) {
					a.setDepartmentId(project.getDepartmentId());
					this.dao.updateById(a);
				}
			}

		} else {
			this.dao.insert(project);
		}

		// 删除原来的队员
		String[] members = project.getProjectMemberIds();

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(EmployeeProject.TABLE_NAME);
		query.and(EmployeeProject.PROJECT_ID, project.getId());
		this.dao.deleteByQuery(query);

		if (members != null) {
			for (String id : members) {
				EmployeeProject ep = new EmployeeProject();
				ep.setUserId(id);
				ep.setProjectId(project.getId());
				this.dao.insert(ep);
			}
		}

	}

	public Project getProjectInfo(Project project) {
		project = (Project) this.dao.findById(project.getId(), Project.TABLE_NAME, Project.class);

		if (project != null) {
			DataBaseQueryBuilder query = new DataBaseQueryBuilder(EmployeeProject.TABLE_NAME);
			query.and(EmployeeProject.PROJECT_ID, project.getId());
			query.limitColumns(new String[] { EmployeeProject.USER_ID });

			List<EmployeeProject> epList = this.dao.listByQuery(query, EmployeeProject.class);
			String[] userIds = new String[epList.size()];
			int i = 0;
			for (EmployeeProject ep : epList) {
				userIds[i] = ep.getUserId();
				i++;
			}

			project.setProjectMemberIds(userIds);
		}

		return project;
	}

	public void addTeam(Team team) {
		if (EweblibUtil.isValid(team.getId())) {
			this.dao.updateById(team);
		} else {
			this.dao.insert(team);
		}

		// 删除原来的队员
		String[] members = team.getTeamMemberIds();

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		query.and(EmployeeTeam.TEAM_ID, team.getId());
		this.dao.deleteByQuery(query);

		if (members != null) {
			for (String id : members) {
				EmployeeTeam et = new EmployeeTeam();
				et.setUserId(id);
				et.setTeamId(team.getId());
				this.dao.insert(et);
			}
		}
	}

	public Team getTeam(Team t) {

		Team team = (Team) this.dao.findById(t.getId(), Team.TABLE_NAME, Team.class);

		if (team != null) {
			DataBaseQueryBuilder query = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
			query.and(EmployeeTeam.TEAM_ID, team.getId());
			query.limitColumns(new String[] { EmployeeTeam.USER_ID });

			List<EmployeeTeam> etList = this.dao.listByQuery(query, EmployeeTeam.class);
			String[] userIds = new String[etList.size()];
			int i = 0;
			for (EmployeeTeam ep : etList) {
				userIds[i] = ep.getUserId();
				i++;
			}

			team.setTeamMemberIds(userIds);
		}

		Department dep = (Department) this.dao.findById(team.getDepartmentId(), Department.TABLE_NAME, Department.class);

		team.setDepartmentName(dep.getDepartmentName());

		return team;
	}

	public EntityResults<Team> listTeams(Team team) {
		DataBaseQueryBuilder builder = getTeamQuery(team);

		DataBaseQueryBuilder etQuery = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		etQuery.join(EmployeeTeam.TABLE_NAME, User.TABLE_NAME, EmployeeTeam.USER_ID, User.ID);
		etQuery.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });
		etQuery.limitColumns(new String[] { EmployeeTeam.TEAM_ID });

		List<EmployeeTeam> etList = this.dao.listByQuery(etQuery, EmployeeTeam.class);

		Map<String, String> etMap = new HashMap<String, String>();

		for (EmployeeTeam et : etList) {

			if (etMap.get(et.getTeamId()) == null) {
				etMap.put(et.getTeamId(), et.getUserName());
			} else {
				etMap.put(et.getTeamId(), etMap.get(et.getTeamId()) + "," + et.getUserName());
			}
		}

		EntityResults<Team> teamList = this.dao.listByQueryWithPagnation(builder, Team.class);

		for (Team t : teamList.getEntityList()) {
			t.setTeamMembers(etMap.get(t.getId()));
		}

		return teamList;
	}

	public DataBaseQueryBuilder getTeamQuery(Team team) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Team.TABLE_NAME);
		builder.join(Team.TABLE_NAME, Department.TABLE_NAME, Team.DEPARTMENT_ID, Department.ID);
		builder.joinColumns(Department.TABLE_NAME, new String[] { Department.DEPARTMENT_NAME });

		builder.join(Team.TABLE_NAME, User.TABLE_NAME, Team.TEAM_LEADER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		builder.join(Team.TABLE_NAME, Project.TABLE_NAME, Team.PROJECT_ID, Project.ID);
		builder.joinColumns(Project.TABLE_NAME, new String[] { Project.PROJECT_NAME });
		if (team != null) {

			if (EweblibUtil.isValid(team.getTeamName())) {
				builder.and(DataBaseQueryOpertion.LIKE, Team.TEAM_NAME, team.getTeamName());
			}

			if (EweblibUtil.isValid(team.getTeamLeaderId())) {
				builder.and(Team.TEAM_LEADER_ID, team.getTeamLeaderId());
			}

			if (EweblibUtil.isValid(team.getProjectId())) {
				builder.and(Team.PROJECT_ID, team.getProjectId());
			}
		}

		builder.limitColumns(new Team().getColumnList());
		return builder;
	}

	public List<Team> listTeamsForApp(Team team) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Team.TABLE_NAME);
		builder.join(Team.TABLE_NAME, Department.TABLE_NAME, Team.DEPARTMENT_ID, Department.ID);
		builder.joinColumns(Department.TABLE_NAME, new String[] { Department.DEPARTMENT_NAME });

		builder.limitColumns(new String[] { Team.ID, Team.TEAM_DESCRIPTION, Team.TEAM_NAME });

		builder.and(Team.DEPARTMENT_ID, team.getDepartmentId());

		List<Team> teams = this.dao.listByQuery(builder, Team.class);

		for (Team t : teams) {
			t.setMembersNumber((int) (Math.random() * 100));
			t.setWorkTimePeriod("早上9:00-12:00,下午15:00-20:00");
		}

		return teams;

	}

	public List<Attendance> listTeamMemebersForApp(EmployeeTeam team) {

		Team t = (Team) this.dao.findById(team.getTeamId(), Team.TABLE_NAME, Team.class);
		DataBaseQueryBuilder pquery = new DataBaseQueryBuilder(Project.TABLE_NAME);
		pquery.and(Project.PROJECT_ATTENDANCE_MANAGER_ID, team.getUserId());
		pquery.and(Project.ID, t.getProjectId());
		if (!this.dao.exists(pquery)) {
			return new ArrayList<Attendance>();
		}

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		query.and(EmployeeTeam.TEAM_ID, team.getTeamId());
		List<EmployeeTeam> ets = this.dao.listByQuery(query, EmployeeTeam.class);

		Set<String> userIds = new HashSet<String>();

		for (EmployeeTeam et : ets) {
			userIds.add(et.getUserId());
		}

		DataBaseQueryBuilder atquery = new DataBaseQueryBuilder(User.TABLE_NAME);
		atquery.join(User.TABLE_NAME, Attendance.TABLE_NAME, User.ID, Attendance.USER_ID);
		atquery.joinColumns(Attendance.TABLE_NAME, new String[] { Attendance.ID, Attendance.ATTENDANCE_DATE, Attendance.ATTENDANCE_DAY_TYPE, Attendance.MINUTES, Attendance.ATTENDANCE_TYPE,
		        Attendance.HOURS });

		if (team.getAttendanceDate() != null) {
			atquery.and(Attendance.TABLE_NAME + "." + Attendance.ATTENDANCE_DATE, team.getAttendanceDate());
		}

		if (team.getAttendanceDayType() != null) {
			atquery.and(Attendance.TABLE_NAME + "." + Attendance.ATTENDANCE_DAY_TYPE, team.getAttendanceDayType());
		}

		atquery.and(Attendance.TABLE_NAME + "." + Attendance.TEAM_ID, team.getTeamId());

		atquery.limitColumns(new String[] { User.USER_NAME, User.ID + "," + Attendance.USER_ID });
		atquery.and(DataBaseQueryOpertion.IN, User.ID, userIds);

		List<Attendance> result = this.dao.listByQuery(atquery, Attendance.class);

		if (result.isEmpty()) {
			DataBaseQueryBuilder userquery = new DataBaseQueryBuilder(User.TABLE_NAME);
			userquery.limitColumns(new String[] { User.USER_NAME, User.ID + "," + Attendance.USER_ID });
			userquery.and(DataBaseQueryOpertion.IN, User.ID, userIds);

			result = this.dao.listByQuery(userquery, Attendance.class);
		}

		return result;

	}

	@Override
	public EntityResults<Project> listProjects(Project project) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Project.TABLE_NAME);
		builder.join(Project.TABLE_NAME, Department.TABLE_NAME, Project.DEPARTMENT_ID, Department.ID);
		builder.joinColumns(Department.TABLE_NAME, new String[] { Department.DEPARTMENT_NAME });

		builder.join(Project.TABLE_NAME, Customer.TABLE_NAME, Project.CUSTOMER_ID, Customer.ID);
		builder.joinColumns(Customer.TABLE_NAME, new String[] { Customer.NAME + "," + "customerName" });

		if (EweblibUtil.isValid(project.getProjectName())) {

			builder.and(DataBaseQueryOpertion.LIKE, Project.PROJECT_NAME, project.getProjectName());

		}

		if (EweblibUtil.isValid(project.getProjectAttendanceManagerId())) {
			builder.and(Project.PROJECT_ATTENDANCE_MANAGER_ID, project.getProjectAttendanceManagerId());
		}

		if (EweblibUtil.isValid(project.getProjectManagerId())) {
			builder.and(Project.PROJECT_MANAGER_ID, project.getProjectManagerId());
		}
		builder.limitColumns(new Project().getColumnList());

		EntityResults<Project> projects = this.dao.listByQueryWithPagnation(builder, Project.class);

		List<User> users = this.dao.listByQuery(new DataBaseQueryBuilder(User.TABLE_NAME), User.class);
		for (Project p : projects.getEntityList()) {

			for (User user : users) {

				if (p.getProjectAttendanceManagerId() != null) {
					if (p.getProjectAttendanceManagerId().equalsIgnoreCase(user.getId())) {
						p.setProjectManagerName(user.getUserName());
					}
				}

				if (p.getProjectManagerId() != null) {
					if (p.getProjectManagerId().equalsIgnoreCase(user.getId())) {
						p.setProjectManagerName(user.getUserName());
					}
				}
			}
		}

		return projects;
	}

	@Override
	public List<Task> listProjectTasksForAppDailyReport(Task t) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Task.TABLE_NAME);

		builder.limitColumns(new String[] { Task.TASK_NAME, Task.PROJECT_NAME, Task.ID, Task.PROJECT_START_DATE, Task.PROJECT_END_DATE });

		builder.and(Task.USER_ID, t.getUserId());

		List<Task> tasks = this.dao.listByQuery(builder, Task.class);

		for (Task task : tasks) {

			Calendar c = Calendar.getInstance();
			c.setTime(task.getProjectStartDate());
			int startDay = c.get(Calendar.DAY_OF_YEAR);

			c.setTime(task.getProjectEndDate());
			int endDay = c.get(Calendar.DAY_OF_YEAR);

			c.setTime(new Date());
			int currentDay = c.get(Calendar.DAY_OF_YEAR);

			task.setProjectTotalDays((endDay - startDay));

			task.setProjectRemainingDays(endDay - currentDay);
			task.setProjectUsedDays(currentDay - startDay);

			// FIXME : from attendance
			task.setUserWorkedDays(getUserWorkedDaysFromTask(task));

		}

		return tasks;

	}
	
	
	private double getUserWorkedDaysFromTask(Task task) {
		Task temp = (Task) this.dao.findById(task.getId(), Task.TABLE_NAME, Task.class);

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Attendance.TABLE_NAME);
		query.and(Attendance.USER_ID, temp.getUserId());
		query.and(Attendance.PROJECT_ID, temp.getProjectId());
		query.limitColumns(new String[] { Attendance.HOURS, Attendance.MINUTES, Attendance.ATTENDANCE_DATE });

		Set<String> set = new HashSet<String>();

		List<Attendance> list = this.dao.listByQuery(query, Attendance.class);
		for (Attendance attendance : list) {

			set.add(String.valueOf(attendance.getAttendanceDate().getTime()));
		}

		return set.size();
	}

	public EntityResults<Task> listAllTasksFor(Task task) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Task.TABLE_NAME);

		builder.join(Task.TABLE_NAME, User.TABLE_NAME, Task.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		String userName = task.getUserName();
		if (EweblibUtil.isValid(userName)) {
			List<String> userIds = userService.getUserIds(userName);
			builder.and(DataBaseQueryOpertion.IN, Task.USER_ID, userIds);
		}

		if (EweblibUtil.isValid(task.getUserId())) {
			builder.and(Task.USER_ID, task.getUserId());
		}

		if (EweblibUtil.isValid(task.getProjectId())) {
			builder.and(Task.PROJECT_ID, task.getProjectId());
		}

		if (EweblibUtil.isValid(task.getTeamId())) {

			builder.and(Task.TEAM_ID, task.getTeamId());
		}

		builder.limitColumns(new String[] { Task.UNIT, Task.TASK_PERIOD, Task.TASK_CONTACT_PHONE, Task.AMOUNT, Task.PRICE, Task.TASK_NAME, Task.DESCRIPTION, Task.AMOUNT_DESCRIPTION,
		        Task.PRICE_DESCRIPTION, Task.TEAM_NAME, Task.PROJECT_NAME, Task.ID, Task.PROJECT_START_DATE, Task.PROJECT_END_DATE });
		EntityResults<Task> tasks = this.dao.listByQueryWithPagnation(builder, Task.class);

		for (Task t : tasks.getEntityList()) {

			Calendar c = Calendar.getInstance();
			c.setTime(t.getProjectStartDate());
			int startDay = c.get(Calendar.DAY_OF_YEAR);

			c.setTime(t.getProjectEndDate());
			int endDay = c.get(Calendar.DAY_OF_YEAR);

			c.setTime(new Date());
			int currentDay = c.get(Calendar.DAY_OF_YEAR);

			t.setProjectTotalDays((endDay - startDay));

			t.setProjectRemainingDays(endDay - currentDay);
			t.setProjectUsedDays(currentDay - startDay);

		}

		return tasks;
	}

	public EntityResults<Customer> listCustomers(SearchVo vo) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Customer.TABLE_NAME);

		builder.limitColumns(new String[] { Customer.ID, Customer.NAME, Customer.CONTACT_MOBILE_NUMBER, Customer.CONTACT_PERSON, Customer.ADDRESS, Customer.REMARK, Customer.POSITION });

		mergeKeywordQuery(builder, vo.getKeyword(), Customer.TABLE_NAME, new String[] { Customer.ID, Customer.NAME, Customer.ADDRESS, Customer.CONTACT_PERSON, Customer.CONTACT_MOBILE_NUMBER });
		EntityResults<Customer> customerList = this.dao.listByQueryWithPagnation(builder, Customer.class);

		for (Customer customer : customerList.getEntityList()) {

			DataBaseQueryBuilder projectQuery = new DataBaseQueryBuilder(Project.TABLE_NAME);
			projectQuery.and(Project.CUSTOMER_ID, customer.getId());
			projectQuery.limitColumns(new String[] { Project.PROJECT_NAME });

			List<Project> projects = this.dao.listByQuery(projectQuery, Project.class);

			if (projects.size() > 0) {
				String p = "";
				for (Project project : projects) {
					p = p + project.getProjectName() + ",";
				}

				p = p + "]]";
				p = p.replace(",]]", "");
				customer.setProjects(p);
			} else {
				customer.setProjects("");
			}
		}

		return customerList;

	}

	public EntityResults<Task> listProjectTasks(Task t) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Task.TABLE_NAME);

		builder.and(Task.USER_ID, t.getUserId());
		builder.limitColumns(new String[] { Task.TASK_NAME, Task.DESCRIPTION, Task.AMOUNT_DESCRIPTION, Task.PRICE_DESCRIPTION, Task.TEAM_NAME, Task.PROJECT_NAME, Task.ID, Task.PROJECT_START_DATE,
		        Task.PROJECT_END_DATE });
		EntityResults<Task> tasks = this.dao.listByQueryWithPagnation(builder, Task.class);

		User user = (User) this.dao.findById(t.getUserId(), User.TABLE_NAME, User.class);
		for (Task project : tasks.getEntityList()) {

			Calendar c = Calendar.getInstance();
			c.setTime(project.getProjectStartDate());
			int startDay = c.get(Calendar.DAY_OF_YEAR);

			c.setTime(project.getProjectEndDate());
			int endDay = c.get(Calendar.DAY_OF_YEAR);

			c.setTime(new Date());
			int currentDay = c.get(Calendar.DAY_OF_YEAR);

			project.setProjectTotalDays((endDay - startDay));

			project.setProjectRemainingDays(endDay - currentDay);
			project.setProjectUsedDays(currentDay - startDay);

			project.setMemebers(user.getUserName());

		}

		return tasks;

	}

	@Transactional
	public DailyReport addDailyReport(DailyReportVo vo, List<String> pics) {

		if (vo.getRemovedPics() != null) {
			for (String pic : vo.getRemovedPics()) {
				DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Pic.TABLE_NAME);
				builder.and(Pic.PIC_URL, pic);
				this.dao.deleteByQuery(builder);
			}
		}
		DailyReport report = (DailyReport) EweblibUtil.toEntity(vo.toString(), DailyReport.class);
		if (EweblibUtil.isValid(report.getId())) {

			this.dao.updateById(report);
		} else {
			Task task = (Task) this.dao.findById(report.getTaskId(), Task.TABLE_NAME, Task.class);

			report.setProjectId(task.getProjectId());

			this.dao.insert(report);
		}

		if (pics != null) {
			for (String pic : pics) {
				Pic picture = new Pic();
				picture.setPicUrl(pic);
				picture.setDailyReportId(report.getId());
				picture.setUserId(report.getUserId());
				this.dao.insert(picture);
			}
		}

		return report;

	}

	public void viewDailyReport(DailyReportVo report) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(DailyReportView.TABLE_NAME);
		builder.and(DailyReportView.DAILY_REPORT_ID, report.getDailyReportId());
		builder.and(DailyReportView.USER_ID, report.getUserId());

		if (!this.dao.exists(builder)) {

			DailyReportView view = new DailyReportView();
			view.setDailyReportId(report.getDailyReportId());
			view.setUserId(report.getUserId());

			this.dao.insert(view);
		}

	}

	public int countDailyReport(DailyReportVo report) {

		// FXIME: 性能问题
		DataBaseQueryBuilder query = getDaliReportQueryBuilderForApp(report.getUserId());

		List<DailyReport> reports = this.dao.listByQuery(query, DailyReport.class);

		int count = reports.size();

		for (DailyReport rep : reports) {
			DataBaseQueryBuilder viewQuery = new DataBaseQueryBuilder(DailyReportView.TABLE_NAME);
			viewQuery.and(DailyReportView.USER_ID, report.getUserId());
			viewQuery.and(DailyReportView.DAILY_REPORT_ID, rep.getId());
			if (this.dao.exists(viewQuery)) {
				count = count - 1;
			}

		}

		return count;

	}

	public EntityResults<DailyReportVo> listDailyReport(DailyReportVo report) {

		DataBaseQueryBuilder builder = getDailyReportQuery(report);

		EntityResults<DailyReportVo> reports = this.dao.listByQueryWithPagnation(builder, DailyReportVo.class);

		for (DailyReportVo vo : reports.getEntityList()) {

			List<String> picUrls = new ArrayList<String>();

			DataBaseQueryBuilder picQuery = new DataBaseQueryBuilder(Pic.TABLE_NAME);
			picQuery.and(Pic.DAILY_REPORT_ID, vo.getId());
			List<Pic> pics = this.dao.listByQuery(picQuery, Pic.class);
			for (Pic p : pics) {
				picUrls.add(p.getPicUrl());
			}
			vo.setPics(picUrls);

			DataBaseQueryBuilder viewQuery = new DataBaseQueryBuilder(DailyReportView.TABLE_NAME);
			viewQuery.and(DailyReportView.DAILY_REPORT_ID, vo.getId());
			viewQuery.and(DailyReportView.USER_ID, report.getUserId());
			if (this.dao.exists(viewQuery)) {
				vo.setIsViewed(true);
			} else {
				vo.setIsViewed(false);
			}

			DataBaseQueryBuilder taskQuery = new DataBaseQueryBuilder(Task.TABLE_NAME);

			taskQuery.and(Task.ID, vo.getTaskId());

			taskQuery.limitColumns(new String[] { Task.PROJECT_END_DATE, Task.PROJECT_NAME, Task.PROJECT_START_DATE, Task.ID, Task.TASK_NAME });
			Task task = (Task) this.dao.findOneByQuery(taskQuery, Task.class);

			if (task != null) {
				Calendar c = Calendar.getInstance();
				c.setTime(task.getProjectStartDate());
				int startDay = c.get(Calendar.DAY_OF_YEAR);

				c.setTime(task.getProjectEndDate());
				int endDay = c.get(Calendar.DAY_OF_YEAR);

				c.setTime(new Date());
				int currentDay = c.get(Calendar.DAY_OF_YEAR);

				task.setProjectTotalDays((endDay - startDay));

				task.setProjectRemainingDays(endDay - currentDay);
				task.setProjectUsedDays(currentDay - startDay);

				// FIXME
				task.setUserWorkedDays(getUserWorkedDaysFromTask(task));

				vo.setTaskInfo(task);
			}

			DataBaseQueryBuilder commentQuery = new DataBaseQueryBuilder(DailyReportComment.TABLE_NAME);
			commentQuery.join(DailyReportComment.TABLE_NAME, User.TABLE_NAME, DailyReportComment.USER_ID, User.ID);
			commentQuery.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

			commentQuery.and(DailyReportComment.DAILY_REPORT_ID, vo.getId());

			commentQuery.limitColumns(new String[] { DailyReportComment.COMMENT, DailyReportComment.COMMENT_DATE, DailyReportComment.ID });

			List<DailyReportComment> comments = this.dao.listByQuery(commentQuery, DailyReportComment.class);

			vo.setComments(comments);
		}

		return reports;

	}

	public DataBaseQueryBuilder getDailyReportQuery(DailyReportVo report) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(DailyReport.TABLE_NAME);
		builder.join(DailyReport.TABLE_NAME, User.TABLE_NAME, DailyReport.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		builder.join(DailyReport.TABLE_NAME, Project.TABLE_NAME, DailyReport.PROJECT_ID, Project.ID);
		builder.joinColumns(Project.TABLE_NAME, new String[] { Project.PROJECT_NAME });

		if (report.getQueryUserId() == null) {

			String userId = report.getUserId();

			if (EweblibUtil.isEmpty(userId)) {
				userId = EWeblibThreadLocal.getCurrentUserId();
			}
			Set<String> userIds = userService.getOwnedUserIds(userId);
			userIds.add(userId);
			builder.and(DataBaseQueryOpertion.IN, DailyReport.USER_ID, userIds);
		} else {
			builder.and(DailyReport.USER_ID, report.getQueryUserId());

		}

		if (report.getStartDate() != null) {
			builder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, DailyReport.REPORT_DAY, report.getStartDate());
		}

		if (report.getEndDate() != null) {
			builder.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, DailyReport.REPORT_DAY, report.getEndDate());
		}

		if (EweblibUtil.isValid(report.getProjectId())) {
			builder.and(DailyReport.PROJECT_ID, report.getProjectId());
		}

		builder.limitColumns(new String[] { DailyReport.TASK_ID, DailyReport.ID, DailyReport.WEATHER, DailyReport.MATERIAL_RECORD, DailyReport.WORKING_RECORD, DailyReport.PLAN, DailyReport.SUMMARY,
		        DailyReport.REPORT_DAY, DailyReport.CREATED_ON });
		return builder;
	}

	public List<DailyReportVo> listDailyReportPlan(DailyReportVo report) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(DailyReport.TABLE_NAME);

		query.join(DailyReport.TABLE_NAME, Project.TABLE_NAME, DailyReport.PROJECT_ID, Project.ID);
		query.joinColumns(Project.TABLE_NAME, new String[] { Project.PROJECT_NAME });

		query.and(DailyReport.USER_ID, report.getUserId());

		Calendar c = Calendar.getInstance();

		c.setTime(new Date());
		c.add(Calendar.DAY_OF_YEAR, -1);

		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		Date startDate = c.getTime();
		c.add(Calendar.DAY_OF_YEAR, 1);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		Date endDate = c.getTime();

		query.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, DailyReport.REPORT_DAY, startDate);

		query.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, DailyReport.REPORT_DAY, endDate);

		query.limitColumns(new String[] { DailyReport.ID, DailyReport.PLAN });
		return this.dao.listByQuery(query, DailyReportVo.class);

	}

	public DataBaseQueryBuilder getDaliReportQueryBuilderForApp(String currentUserId) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(DailyReport.TABLE_NAME);
		builder.join(DailyReport.TABLE_NAME, User.TABLE_NAME, DailyReport.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		Set<String> userIds = userService.getOwnedUserIds(currentUserId);
		userIds.add(currentUserId);
		builder.and(DataBaseQueryOpertion.IN, DailyReport.USER_ID, userIds);

		builder.limitColumns(new String[] { DailyReport.TASK_ID, DailyReport.ID, DailyReport.WEATHER, DailyReport.MATERIAL_RECORD, DailyReport.WORKING_RECORD, DailyReport.PLAN, DailyReport.SUMMARY,
		        DailyReport.REPORT_DAY, DailyReport.CREATED_ON });
		return builder;
	}

	public void addDailyReportComment(DailyReportComment comment) {
		comment.setCommentDate(new Date());
		this.dao.insert(comment);

	}

	public void addCustomer(Customer customer) {

		if (EweblibUtil.isValid(customer.getId())) {
			this.dao.updateById(customer);
		} else {
			this.dao.insert(customer);
		}

	}

	public EntityResults<Customer> listCustomersForApp(SearchVo vo) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Customer.TABLE_NAME);

		String currentUserId = vo.getUserId();
		if (!userService.isAdmin(currentUserId)) {

			Set<String> ids = userService.getOwnedDepartmentIds(currentUserId);

			DataBaseQueryBuilder query = new DataBaseQueryBuilder(Project.TABLE_NAME);
			query.or(Project.PROJECT_MANAGER_ID, currentUserId);
			query.or(Project.PROJECT_ATTENDANCE_MANAGER_ID, currentUserId);
			query.or(DataBaseQueryOpertion.IN, Project.DEPARTMENT_ID, ids);

			List<Project> pList = this.dao.listByQuery(query, Project.class);

			Set<String> customerIds = new HashSet<String>();

			for (Project project : pList) {
				customerIds.add(project.getCustomerId());
			}

			builder.and(DataBaseQueryOpertion.IN, Customer.ID, customerIds);

		}
		builder.limitColumns(new String[] { Customer.ID, Customer.NAME, Customer.CONTACT_MOBILE_NUMBER, Customer.CONTACT_PERSON, Customer.ADDRESS, Customer.REMARK, Customer.POSITION });

		mergeKeywordQuery(builder, vo.getKeyword(), Customer.TABLE_NAME, new String[] { Customer.ID, Customer.NAME, Customer.ADDRESS, Customer.CONTACT_PERSON, Customer.CONTACT_MOBILE_NUMBER });
		EntityResults<Customer> customerList = this.dao.listByQueryWithPagnation(builder, Customer.class);

		for (Customer customer : customerList.getEntityList()) {

			DataBaseQueryBuilder projectQuery = new DataBaseQueryBuilder(Project.TABLE_NAME);
			projectQuery.and(Project.CUSTOMER_ID, customer.getId());
			projectQuery.limitColumns(new String[] { Project.PROJECT_NAME });

			List<Project> projects = this.dao.listByQuery(projectQuery, Project.class);

			if (projects.size() > 0) {
				String p = "";
				for (Project project : projects) {
					p = p + project.getProjectName() + ",";
				}

				p = p + "]]";
				p = p.replace(",]]", "");
				customer.setProjects(p);
			} else {
				customer.setProjects("");
			}

			DataBaseQueryBuilder query = new DataBaseQueryBuilder(CustomerContact.TABLE_NAME);
			query.and(CustomerContact.CUSTOMER_ID, customer.getId());
			query.limitColumns(new String[] { CustomerContact.CONTACT_PERSON, CustomerContact.CONTACT_MOBILE_NUMBER, CustomerContact.POSITION, CustomerContact.REMARK });
			List<CustomerContact> contacts = this.dao.listByQuery(query, CustomerContact.class);

			customer.setContacts(contacts);

		}

		return customerList;

	}

	public BaseEntity getCustomerInfo(Customer customer) {

		return this.dao.findById(customer.getId(), Customer.TABLE_NAME, Customer.class);
	}

	public String exportDailyReportToExcle(DailyReportVo report, HttpServletRequest request) {

		if (EweblibUtil.isEmpty(report.getUserId())) {
			report.setUserId(EWeblibThreadLocal.getCurrentUserId());
		}

		DataBaseQueryBuilder query = getDailyReportQuery(report);
		List<DailyReport> reportList = this.dao.listByQuery(query, DailyReport.class);
		String[] colunmTitleHeaders = new String[] { "用户", "日期", "项目", "材料纪录", "作业面记录", "今日总结", "明日计划", "天气" };
		String[] colunmHeaders = new String[] { "userName", "reportDay", "projectName", "materialRecord", "workingRecord", "summary", "plan", "weather" };

		String webPath = request.getSession().getServletContext().getRealPath("/");

		String filePath = genDownloadRandomRelativePath(EWeblibThreadLocal.getCurrentUserId()) + "日报" + new Date().getTime() + ".xls";
		String desXlsPath = webPath + filePath;

		ExcelUtil.createExcelListFileByEntity(reportList, colunmTitleHeaders, colunmHeaders, new File(desXlsPath));

		return desXlsPath;
	}

	public void addCustomerContact(CustomerContactVo vo, List<CustomerContact> concats) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(CustomerContact.TABLE_NAME);
		query.and(CustomerContact.CUSTOMER_ID, vo.getCustomerId());

		this.dao.deleteByQuery(query);

		for (CustomerContact contact : concats) {
			contact.setCustomerId(vo.getCustomerId());
			this.dao.insert(contact);
		}

	}

	public List<CustomerContact> listCustomerContact(CustomerContactVo vo) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(CustomerContact.TABLE_NAME);
		query.and(CustomerContact.CUSTOMER_ID, vo.getCustomerId());

		return this.dao.listByQuery(query, CustomerContact.class);
	}

	public EntityResults<ProjectTask> listAllProjectTasks(ProjectTask task) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ProjectTask.TABLE_NAME);

		builder.join(ProjectTask.TABLE_NAME, User.TABLE_NAME, ProjectTask.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		builder.join(ProjectTask.TABLE_NAME, Project.TABLE_NAME, ProjectTask.PROJECT_ID, Project.ID);
		builder.joinColumns(Project.TABLE_NAME, new String[] { Project.PROJECT_NAME });

		builder.join(ProjectTask.TABLE_NAME, Team.TABLE_NAME, ProjectTask.TEAM_ID, Team.ID);
		builder.joinColumns(Team.TABLE_NAME, new String[] { Team.TEAM_NAME });

		if (EweblibUtil.isValid(task.getUserId())) {
			builder.and(ProjectTask.USER_ID, task.getUserId());
		}

		if (EweblibUtil.isValid(task.getProjectId())) {
			builder.and(ProjectTask.PROJECT_ID, task.getProjectId());
		}

		if (EweblibUtil.isValid(task.getTeamId())) {

			builder.and(ProjectTask.TEAM_ID, task.getTeamId());
		}

		builder.limitColumns(new String[] { ProjectTask.TASK_PERIOD, ProjectTask.TASK_CONTACT_PHONE, ProjectTask.DESCRIPTION, ProjectTask.ID, ProjectTask.PROJECT_START_DATE,
		        ProjectTask.PROJECT_END_DATE });
		return this.dao.listByQueryWithPagnation(builder, ProjectTask.class);

	}

	public List<Task> listAllTasksFromProjectTasks(Task task) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Task.TABLE_NAME);

		builder.join(Task.TABLE_NAME, User.TABLE_NAME, Task.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		builder.join(Task.TABLE_NAME, Project.TABLE_NAME, Task.PROJECT_ID, Project.ID);
		builder.joinColumns(Project.TABLE_NAME, new String[] { Project.PROJECT_NAME });

		builder.join(Task.TABLE_NAME, Team.TABLE_NAME, Task.TEAM_ID, Team.ID);
		builder.joinColumns(Team.TABLE_NAME, new String[] { Team.TEAM_NAME });

		builder.and(Task.PROJECT_TASK_ID, task.getProjectTaskId());

		if (EweblibUtil.isValid(task.getUserId())) {
			builder.and(Task.USER_ID, task.getUserId());
		}

		if (EweblibUtil.isValid(task.getProjectId())) {
			builder.and(Task.PROJECT_ID, task.getProjectId());
		}

		if (EweblibUtil.isValid(task.getTeamId())) {

			builder.and(Task.TEAM_ID, task.getTeamId());
		}

		builder.limitColumns(new String[] { Task.UNIT, Task.REMARK, Task.AMOUNT, Task.PRICE, Task.TASK_NAME, Task.ID });

		return this.dao.listByQuery(builder, Task.class);

	}

	public void deleteProjectTasks(IDS ids) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ProjectTask.TABLE_NAME);
		query.and(DataBaseQueryOpertion.IN, ProjectTask.ID, ids.getIds());
		this.dao.deleteByQuery(query);

		query = new DataBaseQueryBuilder(Task.TABLE_NAME);
		query.and(DataBaseQueryOpertion.IN, Task.PROJECT_TASK_ID, ids.getIds());
		this.dao.deleteByQuery(query);

	}

}
