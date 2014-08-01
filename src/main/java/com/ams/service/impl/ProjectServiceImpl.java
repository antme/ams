package com.ams.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ams.bean.Attendance;
import com.ams.bean.Customer;
import com.ams.bean.CustomerContact;
import com.ams.bean.DailyReport;
import com.ams.bean.DailyReportComment;
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
import com.eweblib.util.DateUtil;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;

@Service(value = "projectService")
public class ProjectServiceImpl extends AbstractAmsService implements IProjectService {

	@Autowired
	private IUserService userService;

	public static Map<String, String> projectMap = new HashMap<String, String>();

	@Override
	@Transactional
	public void addProject(Project project) {
		String[] projectMagerIds = project.getProjectManagerIds();

		String pmids = "";
		if (projectMagerIds != null) {

			for (String id : projectMagerIds) {

				if (EweblibUtil.isValid(id)) {
					pmids = pmids + id + ",";
				}
			}
		}

		if (EweblibUtil.isValid(pmids)) {
			pmids = pmids + ";";
			pmids = pmids.replace(",;", "");
		}

		project.setProjectManagerId(pmids);

		String[] projectAttendanceMangerids = project.getProjectAttendanceManagerIds();

		pmids = "";
		if (projectAttendanceMangerids != null) {

			for (String id : projectAttendanceMangerids) {
				if (EweblibUtil.isValid(id)) {
					pmids = pmids + id + ",";
				}
			}
		}

		if (EweblibUtil.isValid(pmids)) {
			pmids = pmids + ";";
			pmids = pmids.replace(",;", "");
		}

		project.setProjectAttendanceManagerId(pmids);

		if (EweblibUtil.isValid(project.getId())) {

			this.dao.updateById(project);

			DataBaseQueryBuilder teamQuery = new DataBaseQueryBuilder(Team.TABLE_NAME);
			teamQuery.and(DataBaseQueryOpertion.LIKE, Team.PROJECT_ID, project.getId());
			List<Team> teams = this.dao.listByQuery(teamQuery, Team.class);

			for (Team t : teams) {
//				if (!t.getDepartmentId().equalsIgnoreCase(project.getDepartmentId())) {
//					t.setDepartmentId(project.getDepartmentId());
//					this.dao.updateById(t);
//				}
			}

			DataBaseQueryBuilder attQuery = new DataBaseQueryBuilder(Attendance.TABLE_NAME);
			attQuery.and(Attendance.PROJECT_ID, project.getId());
			List<Attendance> attlist = this.dao.listByQuery(attQuery, Attendance.class);

			for (Attendance a : attlist) {
				if(a.getDepartmentId()!=null){
					if (!a.getDepartmentId().equalsIgnoreCase(project.getDepartmentId())) {
						a.setDepartmentId(project.getDepartmentId());
						this.dao.updateById(a);
					}
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

			project.setProjectManagerIds(project.getProjectManagerId().split(","));
			project.setProjectAttendanceManagerIds(project.getProjectAttendanceManagerId().split(","));

			project.setProjectMemberIds(userIds);
		}

		return project;
	}

	@Transactional
	public void addTeam(Team team) {

		String[] projectIds = team.getProjectIds();

		String pids = "";
		if (projectIds != null) {

			for (String id : projectIds) {

				if (EweblibUtil.isValid(id)) {
					pids = pids + id + ",";
				}
			}
		}

		if (EweblibUtil.isValid(pids)) {
			pids = pids + ";";
			pids = pids.replace(",;", "");
		}

		team.setProjectId(pids);

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

		if (team.getDepartmentId() != null) {
			Department dep = (Department) this.dao.findById(team.getDepartmentId(), Department.TABLE_NAME, Department.class);

			if (dep != null) {
				team.setDepartmentName(dep.getDepartmentName());
			}

		}
		if (team.getProjectId() != null) {
			team.setProjectIds(team.getProjectId().split(","));
		}

		return team;
	}

	public EntityResults<Team> listTeams(Team team) {
		DataBaseQueryBuilder builder = getTeamQuery(team);

		DataBaseQueryBuilder etQuery = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		etQuery.leftJoin(EmployeeTeam.TABLE_NAME, User.TABLE_NAME, EmployeeTeam.USER_ID, User.ID);
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

		mergeCommonQuery(builder);
		EntityResults<Team> teamList = this.dao.listByQueryWithPagnation(builder, Team.class);

		for (Team t : teamList.getEntityList()) {

			t.setTeamMembers(etMap.get(t.getId()));

			DataBaseQueryBuilder pquery = new DataBaseQueryBuilder(Project.TABLE_NAME);
			if (t.getProjectId() != null) {
				pquery.and(DataBaseQueryOpertion.IN, Project.ID, t.getProjectId().split(","));

				List<Project> projectlist = this.dao.listByQuery(pquery, Project.class);

				String pname = null;
				for (Project p : projectlist) {
					if (pname == null) {
						pname = p.getProjectName();
					} else {
						pname = pname + "," + p.getProjectName();
					}
				}
				t.setProjectName(pname);
			}
		}

		return teamList;
	}

	public DataBaseQueryBuilder getTeamQuery(Team team) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Team.TABLE_NAME);
		builder.leftJoin(Team.TABLE_NAME, Department.TABLE_NAME, Team.DEPARTMENT_ID, Department.ID);
		builder.joinColumns(Department.TABLE_NAME, new String[] { Department.DEPARTMENT_NAME });

		builder.leftJoin(Team.TABLE_NAME, User.TABLE_NAME, Team.TEAM_LEADER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		// builder.join(Team.TABLE_NAME, Project.TABLE_NAME, Team.PROJECT_ID,
		// Project.ID);
		// builder.joinColumns(Project.TABLE_NAME, new String[] {
		// Project.PROJECT_NAME });

		if (team != null) {

			if (EweblibUtil.isValid(team.getTeamName())) {
				builder.and(DataBaseQueryOpertion.LIKE, Team.TEAM_NAME, team.getTeamName());
			}

			if (EweblibUtil.isValid(team.getTeamLeaderId())) {
				builder.and(Team.TEAM_LEADER_ID, team.getTeamLeaderId());
			}

			if (EweblibUtil.isValid(team.getProjectId())) {
				builder.and(DataBaseQueryOpertion.LIKE, Team.PROJECT_ID, team.getProjectId());
			}
		}

		builder.limitColumns(new Team().getColumnList());
		return builder;
	}

	public List<Team> listTeamsForApp(Team team) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Team.TABLE_NAME);
		builder.limitColumns(new String[] { Team.ID, Team.TEAM_DESCRIPTION, Team.TEAM_NAME });

		if (team.getDepartmentId() != null) {
			builder.and(Team.DEPARTMENT_ID, team.getDepartmentId());
		}

		if (team.getProjectId() != null) {
			builder.and(DataBaseQueryOpertion.LIKE, Team.PROJECT_ID, team.getProjectId());
			projectMap.put(team.getUserId(), team.getProjectId());
		}
		
		
		DataBaseQueryBuilder pquery = new DataBaseQueryBuilder(Project.TABLE_NAME);
		pquery.leftJoin(Project.TABLE_NAME, Department.TABLE_NAME, Project.DEPARTMENT_ID, Department.ID);
		pquery.joinColumns(Department.TABLE_NAME, new String[] { Department.DEPARTMENT_NAME });
		pquery.and(Project.ID, team.getProjectId());
		pquery.limitColumns(new String[]{Project.WORK_TIME_PERIOD});
		Project p = (Project) this.dao.findOneByQuery(pquery, Project.class);
		
	

		mergeCommonQueryForApp(builder);
		List<Team> teams = this.dao.listByQuery(builder, Team.class);

		for (Team t : teams) {

			DataBaseQueryBuilder teamquery = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
			teamquery.and(EmployeeTeam.TEAM_ID, t.getId());
			t.setMembersNumber(this.dao.count(teamquery));

			if (p != null) {
				String wp = p.getWorkTimePeriod();
				wp = wp.replaceAll(";", " ");
				wp = wp.replaceAll(",", " ");
				wp = wp.replaceAll("；", " ");
				wp = wp.replaceAll("，", " ");
				
				t.setWorkTimePeriod(wp);
				t.setDepartmentName(p.getDepartmentName());
			}else{
				t.setWorkTimePeriod(" 未设置");
				t.setDepartmentName("");
			}

		}

		return teams;

	}

	public List<Attendance> listTeamMemebersForApp(EmployeeTeam team) {



		DataBaseQueryBuilder query = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		query.and(EmployeeTeam.TEAM_ID, team.getTeamId());
		List<EmployeeTeam> ets = this.dao.listByQuery(query, EmployeeTeam.class);

		// FIXME: FOR TEST, REMOVE IT LATER
		String projectId = projectMap.get(team.getUserId());
		
		System.out.println(projectId);

		if (EweblibUtil.isValid(team.getProjectId())) {
			projectId = team.getProjectId();
		}

		DataBaseQueryBuilder epquery = new DataBaseQueryBuilder(EmployeeProject.TABLE_NAME);
		epquery.and(EmployeeProject.PROJECT_ID, projectId);
		List<EmployeeProject> eps = this.dao.listByQuery(epquery, EmployeeProject.class);
		Set<String> epuids = new HashSet<String>();
		for (EmployeeProject ep : eps) {
			epuids.add(ep.getUserId());
		}

		Set<String> userIds = new HashSet<String>();

		for (EmployeeTeam et : ets) {

			if (epuids.contains(et.getUserId())) {
				userIds.add(et.getUserId());
			}
		}

		DataBaseQueryBuilder atquery = new DataBaseQueryBuilder(User.TABLE_NAME);
		atquery.leftJoin(User.TABLE_NAME, Attendance.TABLE_NAME, User.ID, Attendance.USER_ID);
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
			userquery.and(DataBaseQueryOpertion.IN, User.ID, userIds);
			userquery.limitColumns(new String[] { User.USER_NAME, User.ID + "," + Attendance.USER_ID });

			result = this.dao.listByQuery(userquery, Attendance.class);
		}

		return result;

	}

	@Override
	public EntityResults<Project> listProjects(Project project) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Project.TABLE_NAME);
		builder.leftJoin(Project.TABLE_NAME, Department.TABLE_NAME, Project.DEPARTMENT_ID, Department.ID);
		builder.joinColumns(Department.TABLE_NAME, new String[] { Department.DEPARTMENT_NAME });

		builder.leftJoin(Project.TABLE_NAME, Customer.TABLE_NAME, Project.CUSTOMER_ID, Customer.ID);
		builder.joinColumns(Customer.TABLE_NAME, new String[] { Customer.NAME + "," + "customerName" });

		if (EweblibUtil.isValid(project.getProjectName())) {

			builder.and(DataBaseQueryOpertion.LIKE, Project.PROJECT_NAME, project.getProjectName());

		}

		if (EweblibUtil.isValid(project.getProjectAttendanceManagerId())) {
			builder.and(DataBaseQueryOpertion.LIKE, Project.PROJECT_ATTENDANCE_MANAGER_ID, project.getProjectAttendanceManagerId());
		}

		if (EweblibUtil.isValid(project.getProjectManagerId())) {
			builder.and(DataBaseQueryOpertion.LIKE, Project.PROJECT_MANAGER_ID, project.getProjectManagerId());
		}
		builder.limitColumns(new Project().getColumnList());

		DataBaseQueryBuilder etQuery = new DataBaseQueryBuilder(EmployeeProject.TABLE_NAME);
		etQuery.leftJoin(EmployeeProject.TABLE_NAME, User.TABLE_NAME, EmployeeProject.USER_ID, User.ID);
		etQuery.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });
		etQuery.limitColumns(new String[] { EmployeeProject.PROJECT_ID });

		List<EmployeeProject> etList = this.dao.listByQuery(etQuery, EmployeeProject.class);

		Map<String, String> etMap = new HashMap<String, String>();

		for (EmployeeProject et : etList) {

			if (EweblibUtil.isValid(et.getUserName())) {
				if (etMap.get(et.getProjectId()) == null) {
					etMap.put(et.getProjectId(), et.getUserName());
				} else {
					etMap.put(et.getProjectId(), etMap.get(et.getProjectId()) + "," + et.getUserName());
				}
			}
		}

		mergeCommonQuery(builder);
		EntityResults<Project> projects = this.dao.listByQueryWithPagnation(builder, Project.class);

		List<User> users = this.dao.listByQuery(new DataBaseQueryBuilder(User.TABLE_NAME), User.class);
		for (Project p : projects.getEntityList()) {
			String userName = "";

			for (User user : users) {
				if (p.getProjectAttendanceManagerId() != null) {
					if (p.getProjectAttendanceManagerId().contains(user.getId())) {
						userName = userName + user.getUserName() + ",";
					}
				}

			}

			if (EweblibUtil.isValid(userName)) {
				userName = userName + ";";
				userName = userName.replaceAll(",;", "");
			}
			p.setProjectAttendanceManagerName(userName);

			userName = "";
			for (User user : users) {
				if (p.getProjectManagerId() != null) {
					if (p.getProjectManagerId().contains(user.getId())) {
						userName = userName + user.getUserName() + ",";
					}
				}
			}

			if (EweblibUtil.isValid(userName)) {
				userName = userName + ";";
				userName = userName.replaceAll(",;", "");
			}
			p.setProjectManagerName(userName);

			p.setPrjectMembers(etMap.get(p.getId()));
		}

		return projects;
	}

	public List<Project> listProjectsForAppAttendance(SearchVo vo) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Project.TABLE_NAME);
		builder.leftJoin(Project.TABLE_NAME, Department.TABLE_NAME, Project.DEPARTMENT_ID, Department.ID);
		builder.joinColumns(Department.TABLE_NAME, new String[] { Department.DEPARTMENT_NAME });

		if (EweblibUtil.isValid(vo.getKeyword())) {

			builder.and(DataBaseQueryOpertion.LIKE, Project.PROJECT_NAME, vo.getKeyword());

		}

		Set<String> userIds = userService.getOwnedUserIdsByReportManager(vo.getUserId());
		DataBaseQueryBuilder uquery = new DataBaseQueryBuilder(Project.TABLE_NAME);

		// FIXME : USER LOGIC
		for (String userId : userIds) {
			
			uquery.or(DataBaseQueryOpertion.LIKE, Project.PROJECT_MANAGER_ID, userId);
			uquery.or(DataBaseQueryOpertion.LIKE, Project.PROJECT_ATTENDANCE_MANAGER_ID, userId);

		}
		
	
		builder.and(uquery);

		builder.limitColumns(new String[] { Project.PROJECT_NAME, Project.ID });

		mergeCommonQueryForApp(builder);
		return this.dao.listByQuery(builder, Project.class);

	}

	@Override
	public List<Project> listProjectsForAppDailyReportSelect(Task t) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Project.TABLE_NAME);

		builder.limitColumns(new String[] { Project.PROJECT_NAME, Project.ID, Project.PROJECT_START_DATE, Project.PROJECT_END_DATE });

		Set<String> userIds = userService.getOwnedUserIdsByReportManager(t.getUserId());
	
		
		DataBaseQueryBuilder uquery = new DataBaseQueryBuilder(Project.TABLE_NAME);

		// FIXME : USER LOGIC
		for (String userId : userIds) {

			uquery.or(DataBaseQueryOpertion.LIKE, Project.PROJECT_MANAGER_ID, userId);
			uquery.or(DataBaseQueryOpertion.LIKE, Project.PROJECT_ATTENDANCE_MANAGER_ID, userId);

		}

		builder.and(uquery);
		

//		builder.and(DataBaseQueryOpertion.IS_FALSE, Task.IS_DELETED);
		 
		mergeCommonQueryForApp(builder);

		List<Project> projectList = this.dao.listByQuery(builder, Project.class);

		for (Project project : projectList) {
			project.setTaskName(project.getProjectName());
			Calendar c = Calendar.getInstance();
			project.setProjectTotalDays(0);
			project.setProjectRemainingDays(0);
			project.setProjectUsedDays(0);
			project.setUserWorkedDays(0d);
			
			if (project.getProjectStartDate() != null && project.getProjectEndDate() != null) {

				c.setTime(project.getProjectStartDate());
				int startDay = c.get(Calendar.DAY_OF_YEAR);

				c.setTime(project.getProjectEndDate());
				int endDay = c.get(Calendar.DAY_OF_YEAR);

				c.setTime(new Date());
				int currentDay = c.get(Calendar.DAY_OF_YEAR);

				project.setProjectTotalDays((endDay - startDay));

				project.setProjectRemainingDays(endDay - currentDay);
				project.setProjectUsedDays(currentDay - startDay);
			}
			
			
			project.setUserWorkedDays(getUserWorkedDaysFromProject(project, t.getUserId()));

		}

		return projectList;

	}


	private double getUserWorkedDaysFromProject(Project project, String userId) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Attendance.TABLE_NAME);
		// query.and(Attendance.USER_ID, userId);
		query.and(Attendance.PROJECT_ID, project.getId());
		query.limitColumns(new String[] { Attendance.ATTENDANCE_TYPE, Attendance.HOURS, Attendance.MINUTES, Attendance.ATTENDANCE_DATE });

		BigDecimal days = new BigDecimal(0);

		List<Attendance> list = this.dao.listByQuery(query, Attendance.class);
		for (Attendance attendance : list) {

			if (attendance.getAttendanceType() == 0 || attendance.getAttendanceType() == 5 || attendance.getAttendanceType() == 6 || attendance.getAttendanceType() == 7
			        || attendance.getAttendanceType() == 10 || attendance.getAttendanceType() == 4) {
				days = days.add(new BigDecimal(0.5));

			}

			if (attendance.getAttendanceType() == 4) {

				if (attendance.getHours() != null && attendance.getHours() > 5) {
					days = days.add(new BigDecimal(1));
				} else if (attendance.getHours() != null && attendance.getHours() > 3) {
					days = days.add(new BigDecimal(0.5));
				}
			}

		}

		return days.doubleValue();
	}

	public EntityResults<Task> listAllTasksFor(Task task) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Task.TABLE_NAME);

		builder.leftJoin(Task.TABLE_NAME, User.TABLE_NAME, Task.USER_ID, User.ID);
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

		builder.and(DataBaseQueryOpertion.IS_FALSE, Task.IS_DELETED);

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

		mergeCommonQuery(builder);
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

		Set<String> userIds = userService.getOwnedUserIds(t.getUserId());
		builder.and(DataBaseQueryOpertion.IN, Task.USER_ID, userIds);
		
		builder.limitColumns(new String[] { Task.TASK_NAME, Task.DESCRIPTION, Task.AMOUNT_DESCRIPTION, Task.PRICE_DESCRIPTION, Task.TEAM_NAME, Task.PROJECT_NAME, Task.ID, Task.PROJECT_START_DATE,
		        Task.PROJECT_END_DATE });

		builder.and(DataBaseQueryOpertion.IS_FALSE, Task.IS_DELETED);

		if (EweblibUtil.isValid(t.getKeyword())) {

			builder.and(DataBaseQueryOpertion.LIKE, Task.TASK_NAME, t.getKeyword());
		}

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
	
	
	public EntityResults<ProjectTask> listProjectTasksForApp(Task task) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ProjectTask.TABLE_NAME);
		builder.leftJoin(ProjectTask.TABLE_NAME, Project.TABLE_NAME, ProjectTask.PROJECT_ID, Project.ID);
		builder.joinColumns(Project.TABLE_NAME, new String[] { Project.PROJECT_NAME });

		builder.leftJoin(ProjectTask.TABLE_NAME, Team.TABLE_NAME, ProjectTask.TEAM_ID, Team.ID);
		builder.joinColumns(Team.TABLE_NAME, new String[] { Team.TEAM_NAME });

		builder.leftJoin(ProjectTask.TABLE_NAME, User.TABLE_NAME, ProjectTask.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		Set<String> userIds = userService.getOwnedUserIdsByReportManager(task.getUserId());
		Set<String> depIds = userService.getOwnedDepartmentIds(userIds);
		Set<String> pids = userService.getOwnerdProjectIds(userIds, depIds);
		DataBaseQueryBuilder ownQuery = new DataBaseQueryBuilder(ProjectTask.TABLE_NAME);
		ownQuery.or(DataBaseQueryOpertion.IN, ProjectTask.TABLE_NAME + "." + ProjectTask.USER_ID, userIds);
		ownQuery.or(DataBaseQueryOpertion.IN, ProjectTask.TABLE_NAME + "." + ProjectTask.PROJECT_ID, pids);
		ownQuery.or(DataBaseQueryOpertion.IN, ProjectTask.TABLE_NAME + "." + ProjectTask.TEAM_ID, userService.getOwnedTeamIds(userIds, pids));

		builder.and(DataBaseQueryOpertion.IS_FALSE, ProjectTask.IS_DELETED);
		builder.and(ownQuery);

		DataBaseQueryBuilder keywordQuery = new DataBaseQueryBuilder(ProjectTask.TABLE_NAME);

		if (EweblibUtil.isValid(task.getKeyword())) {

			keywordQuery.or(DataBaseQueryOpertion.LIKE, User.TABLE_NAME + "." + User.USER_NAME, task.getKeyword());
			keywordQuery.or(DataBaseQueryOpertion.LIKE, Project.TABLE_NAME + "." + Project.PROJECT_NAME, task.getKeyword());
			keywordQuery.or(DataBaseQueryOpertion.LIKE, Team.TABLE_NAME + "." + Team.TEAM_NAME, task.getKeyword());

			builder.and(keywordQuery);
		}

		builder.limitColumns(new String[] { ProjectTask.ID });

		return this.dao.listByQueryWithPagnation(builder, ProjectTask.class);

	}
	
	
	public ProjectTask getProjectTaskDetails(ProjectTask task) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ProjectTask.TABLE_NAME);
		builder.leftJoin(ProjectTask.TABLE_NAME, Project.TABLE_NAME, ProjectTask.PROJECT_ID, Project.ID);
		builder.joinColumns(Project.TABLE_NAME, new String[] { Project.PROJECT_NAME });

		builder.leftJoin(ProjectTask.TABLE_NAME, Team.TABLE_NAME, ProjectTask.TEAM_ID, Team.ID);
		builder.joinColumns(Team.TABLE_NAME, new String[] { Team.TEAM_NAME });

		builder.leftJoin(ProjectTask.TABLE_NAME, User.TABLE_NAME, ProjectTask.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		builder.and(ProjectTask.ID, task.getId());
		builder.limitColumns(new String[] { ProjectTask.ADDRESS, ProjectTask.ID, ProjectTask.TASK_PERIOD, ProjectTask.DESCRIPTION, ProjectTask.PROJECT_START_DATE, ProjectTask.PROJECT_END_DATE,
		        ProjectTask.TASK_CONTACT_PHONE });

		ProjectTask pt = (ProjectTask) this.dao.findOneByQuery(builder, ProjectTask.class);

		Calendar c = Calendar.getInstance();
		c.setTime(pt.getProjectStartDate());
		int startDay = c.get(Calendar.DAY_OF_YEAR);

		c.setTime(pt.getProjectEndDate());
		int endDay = c.get(Calendar.DAY_OF_YEAR);

		c.setTime(new Date());
		int currentDay = c.get(Calendar.DAY_OF_YEAR);

		if (currentDay < startDay) {
			currentDay = startDay;
		}
		pt.setProjectTotalDays((endDay - startDay - 1));
		pt.setProjectRemainingDays(endDay - currentDay - 1);
		pt.setProjectUsedDays(currentDay - startDay);

		pt.setMemebers(pt.getUserName());

		DataBaseQueryBuilder taskQuery = new DataBaseQueryBuilder(Task.TABLE_NAME);
		taskQuery.and(Task.PROJECT_TASK_ID, pt.getId());
		taskQuery.limitColumns(new String[] { Task.PRICE, Task.DISPLAY_ORDER, Task.PRICE_DESCRIPTION, Task.AMOUNT_DESCRIPTION, Task.TASK_NAME, Task.REMARK });
		taskQuery.orderBy(Task.DISPLAY_ORDER, true);
		List<Task> tasks = this.dao.listByQuery(taskQuery, Task.class);

		pt.setTasks(tasks);

		double totalPrice = 0;
		BigDecimal bd = new BigDecimal(0);
		for (Task t : tasks) {
			
			
			
			bd = bd.add(new BigDecimal(t.getPrice()));
			
		}
		
		pt.setPrice(String.valueOf(bd.doubleValue()));

		return pt;

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
			
			Set<String> managerIds = userService.getUserReportManagerIds(report.getUserId());

			String mids = null;
			for (String managerId : managerIds) {
				if (mids == null) {
					mids = managerId;
				} else {
					mids = mids + " " + managerId;
				}

			}

			report.setProjectId(vo.getTaskId());
			report.setManagerIds(mids);
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
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(DailyReport.TABLE_NAME);
		builder.and(DailyReport.ID, report.getDailyReportId());
		builder.limitColumns(new String[] { DailyReport.ID, DailyReport.MANAGER_IDS });

		DailyReport rep = (DailyReport) this.dao.findOneByQuery(builder, DailyReport.class);

		if (rep != null) {

			if (EweblibUtil.isValid(rep.getManagerIds())) {

				rep.setManagerIds(rep.getManagerIds().replaceAll(report.getUserId(), " "));

				this.dao.updateById(rep);
			}
		}

	}

	public int countDailyReport(DailyReportVo report) {
		return this.dao.count(getNotViewdReportQuery(report));
	}

	public EntityResults<DailyReportVo> listDailyReport(DailyReportVo report, boolean fromApp) {
		
		
		List<DailyReport> list = this.dao.listByQuery(getNotViewdReportQuery(report), DailyReport.class);
		
		Set<String> ids =new HashSet<String>();
		
		if(list.size() > 0){
			for(DailyReport dr: list){
				ids.add(dr.getId());
			}
		}
		
		DataBaseQueryBuilder notViewedQuery = getDailyReportQuery(report, fromApp);
		notViewedQuery.and(DataBaseQueryOpertion.IN, DailyReport.ID, ids);

		List<DailyReportVo> notreviewedlist = this.dao.listByQuery(notViewedQuery, DailyReportVo.class);
		for (DailyReportVo vo : notreviewedlist) {
			mergeReportInfo(report, vo);
			vo.setIsViewed(false);
		}

		DataBaseQueryBuilder builder = getDailyReportQuery(report, fromApp);
		builder.and(DataBaseQueryOpertion.NOT_IN, DailyReport.ID, ids);
		EntityResults<DailyReportVo> reports = this.dao.listByQueryWithPagnation(builder, DailyReportVo.class);

		for (DailyReportVo vo : reports.getEntityList()) {
			mergeReportInfo(report, vo);
			vo.setIsViewed(true);
		}

		notreviewedlist.addAll(reports.getEntityList());
		reports.setEntityList(notreviewedlist);
		return reports;

	}

	public void mergeReportInfo(DailyReportVo report, DailyReportVo vo) {
	    List<String> picUrls = new ArrayList<String>();

	    DataBaseQueryBuilder picQuery = new DataBaseQueryBuilder(Pic.TABLE_NAME);
	    picQuery.and(Pic.DAILY_REPORT_ID, vo.getId());
	    List<Pic> pics = this.dao.listByQuery(picQuery, Pic.class);
	    for (Pic p : pics) {
	    	picUrls.add(p.getPicUrl());
	    }
	    vo.setPics(picUrls);

	    DataBaseQueryBuilder projectQuery = new DataBaseQueryBuilder(Project.TABLE_NAME);

	    projectQuery.and(Project.ID, vo.getProjectId());

	    projectQuery.limitColumns(new String[] { Project.PROJECT_END_DATE, Project.PROJECT_NAME, Project.PROJECT_START_DATE, Project.ID });
	    Project p = (Project) this.dao.findOneByQuery(projectQuery, Project.class);

	    p.setProjectTotalDays(0);
	    p.setProjectRemainingDays(0);
	    p.setProjectUsedDays(0);
	    p.setUserWorkedDays(0d);

	    if (p != null) {

	    	if (p.getProjectStartDate() != null && p.getProjectEndDate() != null) {
	    		Calendar c = Calendar.getInstance();
	    		c.setTime(p.getProjectStartDate());
	    		int startDay = c.get(Calendar.DAY_OF_YEAR);

	    		c.setTime(p.getProjectEndDate());
	    		int endDay = c.get(Calendar.DAY_OF_YEAR);

	    		c.setTime(new Date());
	    		int currentDay = c.get(Calendar.DAY_OF_YEAR);

	    		p.setProjectTotalDays((endDay - startDay));
	    		p.setProjectRemainingDays(endDay - currentDay);
	    		p.setProjectUsedDays(currentDay - startDay);
	    	}
	    	p.setUserWorkedDays(getUserWorkedDaysFromProject(p, report.getUserId()));
	    	p.setTaskName(p.getProjectName());
	    	vo.setTaskInfo(p);

	    }

	    DataBaseQueryBuilder commentQuery = new DataBaseQueryBuilder(DailyReportComment.TABLE_NAME);
	    commentQuery.leftJoin(DailyReportComment.TABLE_NAME, User.TABLE_NAME, DailyReportComment.USER_ID, User.ID);
	    commentQuery.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

	    commentQuery.and(DailyReportComment.DAILY_REPORT_ID, vo.getId());

	    commentQuery.limitColumns(new String[] { DailyReportComment.COMMENT, DailyReportComment.COMMENT_DATE, DailyReportComment.ID });

	    List<DailyReportComment> comments = this.dao.listByQuery(commentQuery, DailyReportComment.class);

	    vo.setComments(comments);
    }

	public DataBaseQueryBuilder getNotViewdReportQuery(DailyReportVo report) {
	    DataBaseQueryBuilder viewQuery = new DataBaseQueryBuilder(DailyReport.TABLE_NAME);
		viewQuery.and(DataBaseQueryOpertion.LIKE, DailyReport.MANAGER_IDS, report.getUserId());
		viewQuery.limitColumns(new String[]{DailyReport.ID});
	    return viewQuery;
    }

//	public DataBaseQueryBuilder getDaliReportQueryBuilderForApp(String currentUserId) {
//		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(DailyReport.TABLE_NAME);
//		builder.join(DailyReport.TABLE_NAME, User.TABLE_NAME, DailyReport.USER_ID, User.ID);
//		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });
//
//		Set<String> userIds = userService.getOwnedUserIds(currentUserId);
//		userIds.add(currentUserId);
//		builder.and(DataBaseQueryOpertion.IN, DailyReport.USER_ID, userIds);
//
//		builder.limitColumns(new String[] { DailyReport.TASK_ID, DailyReport.ID, DailyReport.WEATHER, DailyReport.MATERIAL_RECORD, DailyReport.WORKING_RECORD, DailyReport.PLAN, DailyReport.SUMMARY,
//		        DailyReport.REPORT_DAY, DailyReport.CREATED_ON });
//		return builder;
//	}
	
	public DataBaseQueryBuilder getDailyReportQuery(DailyReportVo report, boolean fromApp) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(DailyReport.TABLE_NAME);
		builder.leftJoin(DailyReport.TABLE_NAME, User.TABLE_NAME, DailyReport.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		builder.leftJoin(DailyReport.TABLE_NAME, Project.TABLE_NAME, DailyReport.PROJECT_ID, Project.ID);
		builder.joinColumns(Project.TABLE_NAME, new String[] { Project.PROJECT_NAME });

		if (report.getQueryUserId() == null) {

			String userId = report.getUserId();

			if (EweblibUtil.isEmpty(userId)) {
				userId = EWeblibThreadLocal.getCurrentUserId();
			}

			if (fromApp) {
				Set<String> userIds = userService.getOwnedUserIdsByReportManager(userId);
				userIds.add(userId);
				builder.and(DataBaseQueryOpertion.IN, DailyReport.USER_ID, userIds);
			}
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

		builder.limitColumns(new String[] { DailyReport.TASK_ID, DailyReport.PROJECT_ID, DailyReport.ID, DailyReport.WEATHER, DailyReport.MATERIAL_RECORD, DailyReport.WORKING_RECORD, DailyReport.PLAN, DailyReport.SUMMARY,
		        DailyReport.REPORT_DAY, DailyReport.CREATED_ON, DailyReport.MANAGER_IDS });
		return builder;
	}

	public List<DailyReportVo> listDailyReportPlan(DailyReportVo report) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(DailyReport.TABLE_NAME);

		query.leftJoin(DailyReport.TABLE_NAME, User.TABLE_NAME, DailyReport.USER_ID, User.ID);
		query.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });
		
		
		query.leftJoin(DailyReport.TABLE_NAME, Project.TABLE_NAME, DailyReport.PROJECT_ID, Project.ID);
		query.joinColumns(Project.TABLE_NAME, new String[] { Project.PROJECT_NAME });

		query.and(DataBaseQueryOpertion.IN, DailyReport.USER_ID, userService.getOwnedUserIdsByReportManager(report.getUserId()));

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

			Set<String> mockedUserIds = userService.getOwnedUserIdsByReportManager(currentUserId);
			Set<String> ids = userService.getOwnedDepartmentIds(mockedUserIds);
			Set<String> projectIds = userService.getOwnerdProjectIds(mockedUserIds, ids);

			DataBaseQueryBuilder query = new DataBaseQueryBuilder(Project.TABLE_NAME);
			query.and(DataBaseQueryOpertion.IN, Project.ID, projectIds);

			List<Project> pList = this.dao.listByQuery(query, Project.class);

			Set<String> customerIds = new HashSet<String>();

			for (Project project : pList) {
				customerIds.add(project.getCustomerId());
			}

			builder.and(DataBaseQueryOpertion.IN, Customer.ID, customerIds);

		}
		builder.limitColumns(new String[] { Customer.ID, Customer.NAME, Customer.CONTACT_MOBILE_NUMBER, Customer.CONTACT_PERSON, Customer.ADDRESS, Customer.REMARK, Customer.POSITION });

		mergeKeywordQuery(builder, vo.getKeyword(), Customer.TABLE_NAME, new String[] { Customer.ID, Customer.NAME, Customer.ADDRESS, Customer.CONTACT_PERSON, Customer.CONTACT_MOBILE_NUMBER });

		mergeCommonQueryForApp(builder);
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

		DataBaseQueryBuilder query = getDailyReportQuery(report, false);
		List<DailyReportVo> reportList = this.dao.listByQuery(query, DailyReportVo.class);
		// String[] colunmTitleHeaders = new String[] { "用户", "日期", "项目",
		// "材料纪录", "作业面记录", "今日总结", "明日计划", "天气" };
		// String[] colunmHeaders = new String[] { "userName", "reportDay",
		// "projectName", "materialRecord", "workingRecord", "summary", "plan",
		// "weather" };

		String filePath = genDownloadRandomRelativePath(EWeblibThreadLocal.getCurrentUserId()) + "日报" + new Date().getTime() + ".xls";

		// ExcelUtil.createExcelListFileByEntity(reportList, colunmTitleHeaders,
		// colunmHeaders, new File(desXlsPath));

		FileOutputStream fileOut = null;

		BufferedImage bufferImg = null;
		String desXlsPath = null;
		String[] columnHeaders = new String[] { "用户", "日期", "项目", "材料纪录", "作业面记录", "今日总结", "明日计划", "天气" };

		try {
			// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
			// 创建一个工作薄
			HSSFWorkbook wb = new HSSFWorkbook();

			HSSFSheet sheet1 = wb.createSheet("日报");
			sheet1.setColumnWidth(2, 4000);
			sheet1.setColumnWidth(3, 10000);
			sheet1.setColumnWidth(4, 10000);
			sheet1.setColumnWidth(5, 10000);
			sheet1.setColumnWidth(6, 10000);
			HSSFRow row = sheet1.createRow(0);
			int index = 0;
			for (String header : columnHeaders) {
				HSSFCell cell = row.createCell(index);
				cell.setCellValue(header);
				index++;
			}
			String webPath = request.getSession().getServletContext().getRealPath("/");

			int rowIndex = 1;
			for (DailyReportVo rep : reportList) {
				row = sheet1.createRow(rowIndex);

				row.createCell(0).setCellValue(rep.getUserName());
				row.createCell(1).setCellValue(DateUtil.getDateString(rep.getReportDay()));
				row.createCell(2).setCellValue(rep.getProjectName());
				row.createCell(3).setCellValue(rep.getMaterialRecord());
				row.createCell(4).setCellValue(rep.getWorkingRecord());
				row.createCell(5).setCellValue(rep.getSummary());
				row.createCell(6).setCellValue(rep.getPlan());
				row.createCell(7).setCellValue(rep.getWeather());

				DataBaseQueryBuilder picquery = new DataBaseQueryBuilder(Pic.TABLE_NAME);
				picquery.and(Pic.DAILY_REPORT_ID, rep.getId());
				List<Pic> picList = this.dao.listByQuery(picquery, Pic.class);
				int col = 0;
				for (Pic pic : picList) {
					File picFile = new File(webPath + pic.getPicUrl());

					if (picFile.exists()) {
						HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 120, (short) (8 + col), rowIndex, (short) (9 + col), (rowIndex + 1));
						ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
						bufferImg = ImageIO.read(picFile);
						ImageIO.write(bufferImg, "png", byteArrayOut);
						patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
						col++;
					}

				}
				rowIndex++;

			}

			desXlsPath = webPath + filePath;

			new File(desXlsPath).getParentFile().mkdirs();

			fileOut = new FileOutputStream(desXlsPath);

			// 写入excel文件

			wb.write(fileOut);

			fileOut.close();

		} catch (IOException io) {

			io.printStackTrace();

			System.out.println("io erorr : " + io.getMessage());

		} finally

		{

			if (fileOut != null)

			{

				try {

					fileOut.close();

				}

				catch (IOException e)

				{

					e.printStackTrace();

				}

			}

		}
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

		builder.leftJoin(ProjectTask.TABLE_NAME, User.TABLE_NAME, ProjectTask.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		builder.leftJoin(ProjectTask.TABLE_NAME, Project.TABLE_NAME, ProjectTask.PROJECT_ID, Project.ID);
		builder.joinColumns(Project.TABLE_NAME, new String[] { Project.PROJECT_NAME });

		builder.leftJoin(ProjectTask.TABLE_NAME, Team.TABLE_NAME, ProjectTask.TEAM_ID, Team.ID);
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

		builder.and(DataBaseQueryOpertion.IS_FALSE, ProjectTask.IS_DELETED);

		builder.limitColumns(new String[] { ProjectTask.TASK_FILE_NAME, ProjectTask.TASK_PERIOD, ProjectTask.TASK_CONTACT_PHONE, ProjectTask.DESCRIPTION, ProjectTask.ID,
		        ProjectTask.PROJECT_START_DATE, ProjectTask.PROJECT_END_DATE });
		return this.dao.listByQueryWithPagnation(builder, ProjectTask.class);

	}

	public List<Task> listAllTasksFromProjectTasks(Task task) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Task.TABLE_NAME);

		builder.leftJoin(Task.TABLE_NAME, User.TABLE_NAME, Task.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		builder.leftJoin(Task.TABLE_NAME, Project.TABLE_NAME, Task.PROJECT_ID, Project.ID);
		builder.joinColumns(Project.TABLE_NAME, new String[] { Project.PROJECT_NAME });

		builder.leftJoin(Task.TABLE_NAME, Team.TABLE_NAME, Task.TEAM_ID, Team.ID);
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

		builder.and(DataBaseQueryOpertion.IS_FALSE, Task.IS_DELETED);
		builder.orderBy(Task.DISPLAY_ORDER, true);

		builder.limitColumns(new String[] { Task.UNIT, Task.REMARK, Task.AMOUNT, Task.PRICE, Task.TASK_NAME, Task.ID });

		return this.dao.listByQuery(builder, Task.class);

	}

	public void deleteProjectTasks(IDS ids) {

		for (String id : ids.getIds()) {
			ProjectTask pt = new ProjectTask();
			pt.setId(id);
			pt.setIsDeleted(true);
			this.dao.updateById(pt);
		}

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Task.TABLE_NAME);
		query.and(DataBaseQueryOpertion.IN, Task.PROJECT_TASK_ID, ids.getIds());
		query.limitColumns(new String[] { Task.ID });
		List<Task> list = this.dao.listByQuery(query, Task.class);
		for (Task task : list) {
			task.setIsDeleted(true);
			this.dao.updateById(task);
		}

	}
	
	

	public void addAttendance(List<Attendance> attendanceList, Attendance att) {

		String userId = att.getUserId();
		
		for (Attendance attendance : attendanceList) {

			Calendar c = Calendar.getInstance();
			c.setTime(attendance.getAttendanceDate());

			
			if(EweblibUtil.isEmpty(userId)){
				userId = attendance.getOperatorId();
			}


			// FIXME: FOR TEST, REMOVE IT LATER
			String projectId = projectMap.get(userId);
			
			System.out.println("===========" + attendance.getProjectId());

			if (EweblibUtil.isValid(attendance.getProjectId())) {
				projectId = attendance.getProjectId();
			}
			
			attendance.setProjectId(projectId);

			Project p = (Project) this.dao.findById(projectId, Project.TABLE_NAME, Project.class);

			if (p != null) {
				attendance.setDepartmentId(p.getDepartmentId());
			}

			attendance.setYear(c.get(Calendar.YEAR));
			attendance.setMonth(c.get(Calendar.MONDAY));

			this.dao.insert(attendance);
		}
	}


}
