package com.ams.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ams.bean.Attendance;
import com.ams.bean.Customer;
import com.ams.bean.DailyReport;
import com.ams.bean.DailyReportComment;
import com.ams.bean.DailyReportView;
import com.ams.bean.Department;
import com.ams.bean.EmployeeProject;
import com.ams.bean.EmployeeTeam;
import com.ams.bean.Pic;
import com.ams.bean.Project;
import com.ams.bean.Task;
import com.ams.bean.Team;
import com.ams.bean.User;
import com.ams.bean.vo.DailyReportVo;
import com.ams.bean.vo.SearchVo;
import com.ams.service.IProjectService;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.service.AbstractService;
import com.eweblib.util.EweblibUtil;

@Service(value = "projectService")
public class ProjectServiceImpl extends AbstractService implements IProjectService {

	@Override
	public void addProject(Project project) {

		if (EweblibUtil.isValid(project.getId())) {

			this.dao.updateById(project);
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

		return team;
	}

	public EntityResults<Team> listTeams(SearchVo vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Team.TABLE_NAME);
		builder.join(Team.TABLE_NAME, Department.TABLE_NAME, Team.DEPARTMENT_ID, Department.ID);
		builder.joinColumns(Department.TABLE_NAME, new String[] { Department.DEPARTMENT_NAME });

		builder.join(Team.TABLE_NAME, User.TABLE_NAME, Team.TEAM_LEADER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		builder.join(Team.TABLE_NAME, Project.TABLE_NAME, Team.PROJECT_ID, Project.ID);
		builder.joinColumns(Project.TABLE_NAME, new String[] { Project.PROJECT_NAME });

		builder.limitColumns(new Team().getColumnList());

		return this.dao.listByQueryWithPagnation(builder, Team.class);
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
	public EntityResults<Project> listProjects() {

		return this.dao.listByQueryWithPagnation(new DataBaseQueryBuilder(Project.TABLE_NAME), Project.class);
	}

	@Override
	public List<Task> listProjectTasksForAppDailyReport() {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Project.TABLE_NAME);

		builder.limitColumns(new String[] { Project.PROJECT_NAME, Project.ID, Project.PROJECT_START_DATE, Project.PROJECT_END_DATE });
		List<Task> projects = this.dao.listByQuery(builder, Task.class);

		for (Task task : projects) {

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

			task.setUserWorkedDays(2d);

			task.setTaskName("任务一号");

		}

		return projects;

	}

	public EntityResults<Task> listAllTasksFor() {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Task.TABLE_NAME);

		return this.dao.listByQueryWithPagnation(builder, Task.class);
	}

	public EntityResults<Customer> listCustomers(SearchVo vo) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Customer.TABLE_NAME);

		// FIXME: 上下级查询
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
		DataBaseQueryBuilder query = getDaliReportQueryBuilderForApp();

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

		DataBaseQueryBuilder builder = getDaliReportQueryBuilderForApp();

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

			DataBaseQueryBuilder pbuilder = new DataBaseQueryBuilder(Project.TABLE_NAME);

			pbuilder.limitColumns(new String[] { Project.PROJECT_NAME, Project.ID, Project.PROJECT_START_DATE, Project.PROJECT_END_DATE });
			List<Task> projects = this.dao.listByQuery(pbuilder, Task.class);

			for (Task task : projects) {

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

				task.setUserWorkedDays(2d);

				task.setTaskName("任务一号");

			}

			if (projects != null && projects.size() > 0) {
				vo.setTaskInfo(projects.get(0));
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

	public DataBaseQueryBuilder getDaliReportQueryBuilderForApp() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(DailyReport.TABLE_NAME);
		builder.join(DailyReport.TABLE_NAME, User.TABLE_NAME, DailyReport.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		builder.limitColumns(new String[] { DailyReport.ID, DailyReport.WEATHER, DailyReport.MATERIAL_RECORD, DailyReport.WORKING_RECORD, DailyReport.PLAN, DailyReport.SUMMARY,
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

		// FIXME: 上下级查询
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

	public BaseEntity getCustomerInfo(Customer customer) {

		return this.dao.findById(customer.getId(), Customer.TABLE_NAME, Customer.class);
	}

}
