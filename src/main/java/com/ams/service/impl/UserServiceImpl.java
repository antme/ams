package com.ams.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ams.bean.DeductedSalaryItem;
import com.ams.bean.Department;
import com.ams.bean.EmployeeProject;
import com.ams.bean.EmployeeTeam;
import com.ams.bean.Menu;
import com.ams.bean.MenuItem;
import com.ams.bean.Pic;
import com.ams.bean.Project;
import com.ams.bean.RoleGroup;
import com.ams.bean.Salary;
import com.ams.bean.SalaryItem;
import com.ams.bean.Team;
import com.ams.bean.User;
import com.ams.bean.UserLevel;
import com.ams.bean.UserType;
import com.ams.bean.vo.SearchVo;
import com.ams.bean.vo.UserSearchVo;
import com.ams.service.ISystemService;
import com.ams.service.IUserService;
import com.eweblib.bean.EntityResults;
import com.eweblib.bean.IDS;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.exception.LoginException;
import com.eweblib.exception.ResponseException;
import com.eweblib.util.DataEncrypt;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;

@Service(value = "userService")
public class UserServiceImpl extends AbstractAmsService implements IUserService {
	public static final String ADM_ORDER_MANAGE = "adm_order_manage";

	@Autowired
	private ISystemService sys;

	private static Logger logger = LogManager.getLogger(UserServiceImpl.class);

	public List<String> getUserIds(String userName) {
		DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
		userQuery.and(DataBaseQueryOpertion.LIKE, User.USER_NAME, userName);

		List<User> users = this.dao.listByQuery(userQuery, User.class);
		List<String> userIds = new ArrayList<String>();

		for (User user : users) {
			userIds.add(user.getId());
		}
		return userIds;
	}

	@Override
	public User regUser(User user) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.USER_NAME, user.getUserName());

		if (user.getId() != null) {
			builder.and(DataBaseQueryOpertion.NOT_EQUALS, User.ID, user.getId());
		}

		if (dao.exists(builder)) {
			throw new ResponseException("此用户名已经被录入");
		}

		builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.USER_CODE, user.getUserCode());

		if (user.getId() != null) {
			builder.and(DataBaseQueryOpertion.NOT_EQUALS, User.ID, user.getId());
		}

		if (dao.exists(builder)) {
			throw new ResponseException("员工编号不能重复");
		}

		if (EweblibUtil.isValid(user.getUserPassword())) {
			user.setPassword(DataEncrypt.generatePassword(user.getUserPassword()));
		}

		if (EweblibUtil.isValid(user.getId())) {
			this.dao.updateById(user);
		} else {
			dao.insert(user);

		}

		List<String> teamIds = user.getTeamIds();
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		query.and(EmployeeTeam.USER_ID, user.getId());
		this.dao.deleteByQuery(query);

		if (teamIds != null) {
			for (String id : teamIds) {
				EmployeeTeam et = new EmployeeTeam();
				et.setUserId(user.getId());
				et.setTeamId(id);
				this.dao.insert(et);
			}
		}

		return user;
	}

	public User loadUser(User user) {

		user = (User) this.dao.findById(user.getId(), User.TABLE_NAME, User.class);

		DataBaseQueryBuilder teamQuery = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		teamQuery.and(EmployeeTeam.USER_ID, user.getId());
		teamQuery.limitColumns(new String[] { EmployeeTeam.TEAM_ID });

		List<EmployeeTeam> teamList = this.dao.listByQuery(teamQuery, EmployeeTeam.class);
		List<String> teamIds = new ArrayList<String>();

		for (EmployeeTeam et : teamList) {
			teamIds.add(et.getTeamId());
		}

		user.setTeamIds(teamIds);

		return user;

	}

	public User login(User user, boolean fromApp) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.PASSWORD, DataEncrypt.generatePassword(user.getPassword()));
		builder.and(User.USER_NAME, user.getUserName());

		if (!dao.exists(builder)) {
			throw new ResponseException("用户名或密码错误");
		}

		builder.limitColumns(new String[] { User.ID, User.USER_NAME, User.STATUS, User.BSTATUS });
		User u = (User) dao.findOneByQuery(builder, User.class);

		if (fromApp && u.getStatus() == 0) {
			throw new ResponseException("你没有登录手机端的权限，请联系管理员");
		} else if (!fromApp && u.getBstatus() == 0) {
			throw new ResponseException("你没有登录后端的权限，请联系管理员");
		}

		if (fromApp) {
			sys.createMsgLog(u.getId(), "从手机端登录");
		} else {
			sys.createMsgLog(u.getId(), "从后台登录");
		}

		return u;
	}

	public void resetPwd(User user) {
		user.setPassword(DataEncrypt.generatePassword(user.getUserPassword()));
		sys.createMsgLog(null, "修改密码");

		this.dao.updateById(user);
	}

	public List<String> listUserAccessMenuIds() {
		User user = (User) this.dao.findById(EWeblibThreadLocal.getCurrentUserId(), User.TABLE_NAME, User.class);

		if (user == null) {
			throw new LoginException();
		}
		String groupId = "";// user.getGroupId();
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
		List<String> menus = new ArrayList<String>();

		if (!EweblibUtil.isEmpty(groupId)) {
			String[] groups = groupId.split(",");
			for (String group : groups) {
				builder.or(RoleGroup.ID, group);
			}

			List<RoleGroup> list = this.dao.listByQuery(builder, RoleGroup.class);
			for (RoleGroup rg : list) {
				String premissions = rg.getPermissions();
				if (!EweblibUtil.isEmpty(premissions)) {

					String[] splitPremissions = premissions.split(",");
					for (String p : splitPremissions) {
						if (!EweblibUtil.isEmpty(p)) {
							menus.add(p.trim());
						}
					}
				}
			}
		}

		return menus;
	}

	public boolean inRole(String groupIds, String roleId) {

		DataBaseQueryBuilder roleQuery = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
		roleQuery.and(DataBaseQueryOpertion.LIKE, RoleGroup.PERMISSIONS, roleId);
		List<RoleGroup> groupList = this.dao.listByQuery(roleQuery, RoleGroup.class);

		for (RoleGroup group : groupList) {

			if (groupIds.contains(group.getId())) {
				return true;
			}
		}

		return false;

	}

	public boolean inRole(String roleId) {
		DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
		userQuery.limitColumns(new String[] { "GROUP_ID" });
		User user = (User) this.dao.findById(EWeblibThreadLocal.getCurrentUserId(), User.TABLE_NAME, User.class);
		// String groupIds = user.getGroupId();
		// DataBaseQueryBuilder roleQuery = new
		// DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
		// roleQuery.and(DataBaseQueryOpertion.LIKE, RoleGroup.PERMISSIONS,
		// roleId);
		// List<RoleGroup> groupList = this.dao.listByQuery(roleQuery,
		// RoleGroup.class);
		//
		// for(RoleGroup group: groupList){
		//
		// if (groupIds != null && group != null &&
		// groupIds.contains(group.getId())) {
		// return true;
		// }
		// }
		//
		return false;

	}

	public EntityResults<User> listUserForApp(SearchVo vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);

		builder.leftJoin(User.TABLE_NAME, UserType.TABLE_NAME, User.USER_TYPE_ID, UserType.ID);
		builder.joinColumns(UserType.TABLE_NAME, new String[] { UserType.TYPE_NAME + "," + "userType" });

		builder.leftJoin(User.TABLE_NAME, UserLevel.TABLE_NAME, User.USER_LEVEL_ID, UserLevel.ID);
		builder.joinColumns(UserLevel.TABLE_NAME, new String[] { UserLevel.LEVEL_NAME + "," + "userLevel" });

		builder.limitColumns(new String[] { User.USER_NAME, User.USER_CODE, User.MOBILE_NUMBER, User.ID });

		String currentUserId = vo.getUserId();

		if (!isAdmin(currentUserId)) {
			Set<String> userIds = getOwnedUserIds(currentUserId);

			builder.and(DataBaseQueryOpertion.IN, User.ID, userIds);

		}

		mergeKeywordQuery(builder, vo.getKeyword(), User.TABLE_NAME, new String[] { User.USER_NAME, User.MOBILE_NUMBER });

		mergeCommonQueryForApp(builder);
		EntityResults<User> userList = this.dao.listByQueryWithPagnation(builder, User.class);

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		query.leftJoin(EmployeeTeam.TABLE_NAME, Team.TABLE_NAME, EmployeeTeam.TEAM_ID, Team.ID);
		query.joinColumns(Team.TABLE_NAME, new String[] { Team.TEAM_NAME });
		query.limitColumns(new String[] { EmployeeTeam.USER_ID });

		List<EmployeeTeam> ets = this.dao.listByQuery(query, EmployeeTeam.class);

		if (ets != null) {
			for (User user : userList.getEntityList()) {
				user.setTeams("");
				for (EmployeeTeam et : ets) {
					if (et.getUserId().equalsIgnoreCase(user.getId())) {
						user.setTeams(user.getTeams() + " " + et.getTeamName());
					}
				}

			}
		}

		return userList;

	}

	public Set<String> getOwnedUserIds(String currentUserId) {

		Set<String> mockedUserIds = getOwnedUserIdsByReportManager(currentUserId);

		Set<String> ids = getOwnedDepartmentIds(mockedUserIds);
		Set<String> projectIds = getOwnerdProjectIds(mockedUserIds, ids);
		Set<String> teamIds = getOwnedTeamIds(mockedUserIds, projectIds);

		Set<String> userIds = new HashSet<String>();

		DataBaseQueryBuilder epQuery = new DataBaseQueryBuilder(EmployeeProject.TABLE_NAME);
		epQuery.and(DataBaseQueryOpertion.IN, EmployeeProject.PROJECT_ID, projectIds);
		List<EmployeeProject> epList = this.dao.listByQuery(epQuery, EmployeeProject.class);
		for (EmployeeProject ep : epList) {
			userIds.add(ep.getUserId());
		}

		DataBaseQueryBuilder etQuery = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		etQuery.and(DataBaseQueryOpertion.IN, EmployeeTeam.TEAM_ID, teamIds);
		List<EmployeeTeam> etList = this.dao.listByQuery(etQuery, EmployeeTeam.class);
		for (EmployeeTeam ep : etList) {
			userIds.add(ep.getUserId());
		}

		userIds.addAll(mockedUserIds);
		return userIds;
	}

	public Set<String> getOwnedTeamIds(Set<String> mockedUserIds, Set<String> projectIds) {
		DataBaseQueryBuilder teamQuery = new DataBaseQueryBuilder(Team.TABLE_NAME);
//		teamQuery.or(DataBaseQueryOpertion.IN, Team.TEAM_LEADER_ID, mockedUserIds);
//		teamQuery.or(DataBaseQueryOpertion.IN, Team.PROJECT_ID, projectIds);
		List<Team> teamList = this.dao.listByQuery(teamQuery, Team.class);
		Set<String> teamIds = new HashSet<String>();
		for (Team team : teamList) {
			
			boolean find = false;
			
			if(team.getTeamLeaderId()!=null && mockedUserIds.contains(team.getTeamLeaderId())){
				
				find = true;
			}
			
//			if(team.getProjectId()!=null && !find){
//				
//				for(String id: team.getProjectId().split(",")){
//					
//					if(projectIds.contains(id)){
//						find = true;
//						break;
//					}
//				}
//			}
			
			if (find) {
				teamIds.add(team.getId());
			}
		}

		return teamIds;
	}

	public Set<String> getOwnerdProjectIds(Set<String> mockedUserIds, Set<String> depIds) {
		Set<String> projectIds = new HashSet<String>();
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Project.TABLE_NAME);
//		query.or(DataBaseQueryOpertion.IN, Project.PROJECT_MANAGER_ID, mockedUserIds);
//		query.or(DataBaseQueryOpertion.IN, Project.PROJECT_ATTENDANCE_MANAGER_ID, mockedUserIds);
//		query.or(DataBaseQueryOpertion.IN, Project.DEPARTMENT_ID, depIds);
		
		if (mockedUserIds == null) {

			mockedUserIds = new HashSet<String>();

		}

		if (depIds == null) {
			depIds = new HashSet<String>();
		}

		List<Project> pList = this.dao.listByQuery(query, Project.class);

		for (Project project : pList) {

			boolean find = false;

			if (project.getProjectManagerId() != null) {

				for (String id : project.getProjectManagerId().split(",")) {

					if (mockedUserIds.contains(id)) {
						find = true;
						break;
					}
				}
			}

			//FIXME ： 考勤负责人是否也能查看所有的人员信息？
			if (project.getProjectAttendanceManagerId() != null && !find) {

				for (String id : project.getProjectAttendanceManagerId().split(",")) {

					if (mockedUserIds.contains(id)) {
						find = true;
						break;
					}
				}
			}

			if (project.getDepartmentId() != null && !find) {

				if (depIds.contains(project.getDepartmentId())) {
					find = true;
				}
			}
			
			if (find) {
				projectIds.add(project.getId());

			}
		}

		return projectIds;
	}

	public Set<String> getOwnedDepartmentIds(Set<String> mockedUserIds) {
		DataBaseQueryBuilder depQuery = new DataBaseQueryBuilder(Department.TABLE_NAME);
		depQuery.and(DataBaseQueryOpertion.IN, Department.DEPARTMENT_MANAGER_ID, mockedUserIds);
		List<Department> deplist = this.dao.listByQuery(depQuery, Department.class);
		Set<String> ids = new HashSet<String>();

		for (Department dep : deplist) {
			ids.add(dep.getId());
		}
		return ids;
	}

	public void addDepartment(Department dep) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Department.TABLE_NAME);
		builder.and(Department.DEPARTMENT_NAME, dep.getDepartmentName());
		if (dep.getId() != null) {
			builder.and(DataBaseQueryOpertion.NOT_EQUALS, Department.ID, dep.getId());
		}

		if (dao.exists(builder)) {
			throw new ResponseException("此部门已经存在");
		}

		if (EweblibUtil.isValid(dep.getId())) {
			this.dao.updateById(dep);
		} else {
			this.dao.insert(dep);
		}
	}

	public Department loadDepartment(Department dep) {
		return (Department) this.dao.findById(dep.getId(), Department.TABLE_NAME, Department.class);
	}

	public EntityResults<Department> listDepartments(SearchVo vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Department.TABLE_NAME);
		builder.leftJoin(Department.TABLE_NAME, User.TABLE_NAME, Department.DEPARTMENT_MANAGER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });
		builder.limitColumns(new Department().getColumnList());
		mergeCommonQuery(builder);
		return this.dao.listByQueryWithPagnation(builder, Department.class);
	}

	public void addPic(Pic pic) {

		this.dao.insert(pic);
	}

	public EntityResults<Pic> listPics(Pic pic) {
		DataBaseQueryBuilder builder = getPicQuery(pic);
		return this.dao.listByQueryWithPagnation(builder, Pic.class);
	}

	public DataBaseQueryBuilder getPicQuery(Pic pic) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Pic.TABLE_NAME);

		builder.leftJoin(Pic.TABLE_NAME, User.TABLE_NAME, Pic.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });
		builder.limitColumns(new Pic().getColumnList());

		if (pic != null) {
			if (EweblibUtil.isValid(pic.getProjectName())) {
				builder.and(DataBaseQueryOpertion.LIKE, Pic.PROJECT_NAME, pic.getProjectName());

			}

			if (EweblibUtil.isValid(pic.getStartDate())) {
				builder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, Pic.CREATED_ON, pic.getStartDate());
			}

			if (EweblibUtil.isValid(pic.getEndDate())) {
				builder.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, Pic.CREATED_ON, pic.getEndDate());
			}

			if (EweblibUtil.isValid(pic.getUserId())) {
				builder.and(Pic.USER_ID, pic.getUserId());
			}

		}
		builder.and(DataBaseQueryOpertion.NULL, Pic.DAILY_REPORT_ID);

		return builder;
	}

	public EntityResults<Salary> listUserSalaries(Salary salary) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Salary.TABLE_NAME);
		builder.leftJoin(Salary.TABLE_NAME, User.TABLE_NAME, Salary.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		Set<String> userIds = getOwnedUserIds(salary.getUserId());
		userIds.add(salary.getUserId());

		if (EweblibUtil.isValid(salary.getUserName())) {
			List<String> queryUserIds = this.getUserIds(salary.getUserName());
			List<String> finalQueryUserIds = new ArrayList<String>();
			for (String userId : queryUserIds) {
				if (userIds.contains(userId)) {
					finalQueryUserIds.add(userId);
				}
			}

			builder.and(DataBaseQueryOpertion.IN, Salary.USER_ID, finalQueryUserIds);
		} else {
			builder.and(DataBaseQueryOpertion.IN, Salary.USER_ID, userIds);
		}

		if (EweblibUtil.isValid(salary.getYear())) {
			builder.and(Salary.YEAR, salary.getYear());
		}

		if (EweblibUtil.isValid(salary.getMonth())) {
			builder.and(Salary.MONTH, salary.getMonth());
		}

		builder.limitColumns(new String[] { Salary.USER_ID, Salary.ID, Salary.DEDUCTED_SALARY, Salary.REMAINING_SALARAY, Salary.TOTAL_SALARY, Salary.MONTH, Salary.YEAR });

		return this.dao.listByQueryWithPagnation(builder, Salary.class);
	}

	public EntityResults<Salary> listAllUserSalaries(Salary salary) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Salary.TABLE_NAME);
		builder.leftJoin(Salary.TABLE_NAME, User.TABLE_NAME, Salary.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		if (EweblibUtil.isValid(salary.getUserId())) {

			builder.and(Salary.USER_ID, salary.getUserId());
		}

		if (EweblibUtil.isValid(salary.getYear())) {
			builder.and(Salary.YEAR, salary.getYear());
		}

		if (EweblibUtil.isValid(salary.getMonth())) {
			builder.and(Salary.MONTH, salary.getMonth());
		}

		builder.limitColumns(new String[] { Salary.USER_ID, Salary.ID, Salary.SALARY_FILE_NAME, Salary.DEDUCTED_SALARY, Salary.REMAINING_SALARAY, Salary.TOTAL_SALARY, Salary.MONTH, Salary.YEAR });

		return this.dao.listByQueryWithPagnation(builder, Salary.class);

	}

	public void addSalart(Salary salary) {
		salary.setUserId(EWeblibThreadLocal.getCurrentUserId());
		this.dao.insert(salary);
	}

	public Salary getSalaryDetail(Salary salary) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Salary.TABLE_NAME);
		builder.limitColumns(new String[] { Salary.ID, Salary.MONTH, Salary.YEAR, Salary.SALARY_PER_DAY});
		builder.and(Salary.ID, salary.getId());

		salary = (Salary) this.dao.findOneByQuery(builder, Salary.class);


		Map<String, String> pMap = new HashMap<String, String>();
		List<Project> projects = this.dao.listByQuery(new DataBaseQueryBuilder(Project.TABLE_NAME), Project.class);
		for (Project p : projects) {
			pMap.put(p.getId(), p.getProjectName());
		}

		DataBaseQueryBuilder detailQuery = new DataBaseQueryBuilder(SalaryItem.TABLE_NAME);
		detailQuery.and(SalaryItem.SALARY_ID, salary.getId());
		detailQuery.limitColumns(new String[] { SalaryItem.PERFORMANCE_SALARY_UNIT, SalaryItem.PROJECT_NAME, SalaryItem.ATTENDANCE_DAYS, SalaryItem.PROJECT_ID, SalaryItem.TOTOL_SALARY,
		        SalaryItem.PERFORMANCE_SALARY, SalaryItem.COMMENT });

		detailQuery.orderBy(SalaryItem.DISPAY_ORDER, true);

		List<SalaryItem> items = this.dao.listByQuery(detailQuery, SalaryItem.class);
		double salaryTotal = 0;

		for (SalaryItem item : items) {

			if (item.getTotolSalary() != null) {
				salaryTotal += item.getTotolSalary();
			}
		}

		salary.setTotalSalary(salaryTotal);
		salary.setSalaryItems(items);
		DataBaseQueryBuilder deductedQuery = new DataBaseQueryBuilder(DeductedSalaryItem.TABLE_NAME);
		deductedQuery.and(SalaryItem.SALARY_ID, salary.getId());
		deductedQuery.limitColumns(new String[] { DeductedSalaryItem.TOTOL_SALARY, DeductedSalaryItem.NAME, DeductedSalaryItem.COMMENT });
		deductedQuery.orderBy(DeductedSalaryItem.DISPAY_ORDER, true);
		List<DeductedSalaryItem> ditems = this.dao.listByQuery(deductedQuery, DeductedSalaryItem.class);
		salaryTotal = 0;
		for (DeductedSalaryItem item : ditems) {
			salaryTotal += item.getTotolSalary();
		}
		salary.setDeductedSalaryItems(ditems);
		salary.setDeductedSalary(salaryTotal);
		return salary;
	}

	public EntityResults<Department> listDepartmentsForApp(SearchVo vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Department.TABLE_NAME);

		// if (!isAdmin(vo.getUserId())) {
		//
		// Set<String> depIds = getOwnedDepartmentIds(vo.getUserId());
		//
		// DataBaseQueryBuilder query = new DataBaseQueryBuilder(Team.)
		// builder.and(DataBaseQueryOpertion.IN, Department.ID, depIds);
		//
		// }
		builder.limitColumns(new String[] { Department.ID, Department.DEPARTMENT_NAME });

		mergeCommonQueryForApp(builder);
		return this.dao.listByQueryWithPagnation(builder, Department.class);
	}

	public EntityResults<User> listAllUsers(User vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.leftJoin(User.TABLE_NAME, UserType.TABLE_NAME, User.USER_TYPE_ID, UserType.ID);
		builder.joinColumns(UserType.TABLE_NAME, new String[] { UserType.TYPE_NAME });

		builder.leftJoin(User.TABLE_NAME, UserLevel.TABLE_NAME, User.USER_LEVEL_ID, UserLevel.ID);
		builder.joinColumns(UserLevel.TABLE_NAME, new String[] { UserLevel.LEVEL_NAME });

		builder.leftJoin(User.TABLE_NAME, RoleGroup.TABLE_NAME, User.GROUP_ID, RoleGroup.ID);
		builder.joinColumns(RoleGroup.TABLE_NAME, new String[] { RoleGroup.GROUP_NAME });

		if (EweblibUtil.isValid(vo.getUserName())) {
			builder.and(DataBaseQueryOpertion.LIKE, User.USER_NAME, vo.getUserName());
		}

		if (EweblibUtil.isValid(vo.getGroupId())) {
			builder.and(User.GROUP_ID, vo.getGroupId());
		}

		if (EweblibUtil.isValid(vo.getIdCard())) {
			builder.and(DataBaseQueryOpertion.LIKE, User.ID_CARD, vo.getIdCard());
		}

		if (EweblibUtil.isValid(vo.getTeamGroup())) {
			builder.and(DataBaseQueryOpertion.LIKE, User.TEAM_GROUP, vo.getTeamGroup());
		}

		if (EweblibUtil.isValid(vo.getUserCode())) {
			builder.and(DataBaseQueryOpertion.LIKE, User.USER_CODE, vo.getUserCode());
		}

		if (EweblibUtil.isValid(vo.getUserTypeId())) {
			builder.and(User.USER_TYPE_ID, vo.getUserTypeId());
		}

		if (EweblibUtil.isValid(vo.getUserLevelId())) {
			builder.and(User.USER_LEVEL_ID, vo.getUserLevelId());
		}
		
		if (EweblibUtil.isValid(vo.getReportManagerId())) {
			builder.and(User.REPORT_MANAGER_ID, vo.getReportManagerId());
		}

		builder.limitColumns(new User().getColumnList());

		mergeCommonQuery(builder);

		EntityResults<User> results = this.dao.listByQueryWithPagnation(builder, User.class);

		DataBaseQueryBuilder etQuery = new DataBaseQueryBuilder(EmployeeProject.TABLE_NAME);
		etQuery.leftJoin(EmployeeProject.TABLE_NAME, Project.TABLE_NAME, EmployeeProject.PROJECT_ID, Project.ID);
		etQuery.joinColumns(Project.TABLE_NAME, new String[] { Project.PROJECT_NAME });
		etQuery.limitColumns(new String[] { EmployeeProject.USER_ID });

		List<EmployeeProject> etList = this.dao.listByQuery(etQuery, EmployeeProject.class);

		Map<String, String> projectMap = new HashMap<String, String>();

		for (EmployeeProject et : etList) {

			if (projectMap.get(et.getUserId()) == null) {
				projectMap.put(et.getUserId(), et.getProjectName());
			} else {
				projectMap.put(et.getUserId(), projectMap.get(et.getUserId()) + "," + et.getProjectName());
			}

		}

		DataBaseQueryBuilder teamQuery = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		teamQuery.leftJoin(EmployeeTeam.TABLE_NAME, Team.TABLE_NAME, EmployeeTeam.TEAM_ID, Team.ID);
		teamQuery.joinColumns(Team.TABLE_NAME, new String[] { Team.TEAM_NAME });
		teamQuery.limitColumns(new String[] { EmployeeTeam.USER_ID });

		List<EmployeeTeam> teamList = this.dao.listByQuery(teamQuery, EmployeeTeam.class);

		Map<String, String> teamMap = new HashMap<String, String>();

		for (EmployeeTeam et : teamList) {

			if (teamMap.get(et.getUserId()) == null) {
				teamMap.put(et.getUserId(), et.getTeamName());
			} else {
				teamMap.put(et.getUserId(), teamMap.get(et.getUserId()) + "," + et.getTeamName());
			}

		}

		List<User> userList = results.getEntityList();

		Set<String> managerIds = new HashSet<String>();

		for (User user : userList) {

			user.setProjects(projectMap.get(user.getId()));
			user.setTeams(teamMap.get(user.getId()));

			managerIds.add(user.getReportManagerId());
		}

		DataBaseQueryBuilder managerQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
		managerQuery.and(DataBaseQueryOpertion.IN, User.ID, managerIds);
		managerQuery.limitColumns(new String[] { User.ID, User.USER_NAME });

		List<User> managers = this.dao.listByQuery(managerQuery, User.class);
		Map<String, String> managerMap = new HashMap<String, String>();
		for (User manager : managers) {
			managerMap.put(manager.getId(), manager.getUserName());
		}

		for (User user : userList) {

			user.setReportManagerName(managerMap.get(user.getReportManagerId()));
		}

		return results;
	}

	public List<User> selectAllUsersForProject(UserSearchVo vo) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.leftJoin(User.TABLE_NAME, UserType.TABLE_NAME, User.USER_TYPE_ID, UserType.ID);
		builder.joinColumns(UserType.TABLE_NAME, new String[] { UserType.TYPE_NAME });

		builder.leftJoin(User.TABLE_NAME, UserLevel.TABLE_NAME, User.USER_LEVEL_ID, UserLevel.ID);
		builder.joinColumns(UserLevel.TABLE_NAME, new String[] { UserLevel.LEVEL_NAME });

		// DataBaseQueryBuilder pquery = new
		// DataBaseQueryBuilder(EmployeeProject.TABLE_NAME);
		//
		// if (vo.getId() != null) {
		// pquery.and(DataBaseQueryOpertion.NOT_EQUALS,
		// EmployeeProject.PROJECT_ID, vo.getId());
		// }
		// List<EmployeeProject> epList = this.dao.listByQuery(pquery,
		// EmployeeProject.class);
		// Set<String> userIds = new HashSet<String>();
		// for (EmployeeProject ep : epList) {
		//
		// userIds.add(ep.getUserId());
		// }
		//
		// pquery = new DataBaseQueryBuilder(EmployeeProject.TABLE_NAME);
		//
		// if (vo.getId() != null) {
		// pquery.and(EmployeeProject.PROJECT_ID, vo.getId());
		// epList = this.dao.listByQuery(pquery, EmployeeProject.class);
		// userIds = new HashSet<String>();
		// for (EmployeeProject ep : epList) {
		//
		// userIds.add(ep.getUserId());
		// }
		// builder.or(DataBaseQueryOpertion.IN, User.ID, userIds);
		// }
		//
		// builder.or(DataBaseQueryOpertion.IS_TRUE, User.IS_MULTIPLE_PROJECT);
		// builder.or(DataBaseQueryOpertion.NOT_IN, User.ID, userIds);
		//

		if (EweblibUtil.isValid(vo.getUserName())) {

			builder.and(DataBaseQueryOpertion.LIKE, User.USER_NAME, vo.getUserName());
		}

		Set<String> ids = new HashSet<String>();

		if (EweblibUtil.isValid(vo.getTeamId())) {

			DataBaseQueryBuilder tquery = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
			tquery.and(EmployeeTeam.TEAM_ID, vo.getTeamId());

			List<EmployeeTeam> etList = this.dao.listByQuery(tquery, EmployeeTeam.class);
			for (EmployeeTeam et : etList) {
				ids.add(et.getUserId());
			}

			builder.and(DataBaseQueryOpertion.IN, User.ID, ids);

		}

		builder.limitColumns(new User().getColumnList());
		List<User> ulist = this.dao.listByQuery(builder, User.class);

		List<User> finalList = new ArrayList<User>();

		List<User> excludeList = new ArrayList<User>();

		Set<String> userIds = new HashSet<String>();

		DataBaseQueryBuilder epquery = new DataBaseQueryBuilder(EmployeeProject.TABLE_NAME);

		if (vo.getId() != null) {
			epquery.and(EmployeeProject.PROJECT_ID, vo.getId());

			List<EmployeeProject> epList = this.dao.listByQuery(epquery, EmployeeProject.class);
			for (EmployeeProject ep : epList) {

				userIds.add(ep.getUserId());
			}
		}

		DataBaseQueryBuilder pquery = new DataBaseQueryBuilder(EmployeeProject.TABLE_NAME);
		pquery.distinct(EmployeeProject.USER_ID);
		List<EmployeeProject> epList = this.dao.distinctQuery(pquery, EmployeeProject.class);

		for (User user : ulist) {
			boolean find = false;
			for (EmployeeProject ep : epList) {
				if (ep.getUserId().equalsIgnoreCase(user.getId()) && !userIds.contains(ep.getUserId())) {
					user.setProjectId("xxxxxxxxxx");

					if (!user.getIsMultipleProject()) {
						find = true;
					}

					break;
				}
			}

			if (find) {
				excludeList.add(user);
			} else {
				finalList.add(user);
			}
		}
		finalList.addAll(excludeList);
		return finalList;

	}

	public List<User> selectAllUsersForTeam(UserSearchVo vo) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.leftJoin(User.TABLE_NAME, UserType.TABLE_NAME, User.USER_TYPE_ID, UserType.ID);
		builder.joinColumns(UserType.TABLE_NAME, new String[] { UserType.TYPE_NAME });

		builder.leftJoin(User.TABLE_NAME, UserLevel.TABLE_NAME, User.USER_LEVEL_ID, UserLevel.ID);
		builder.joinColumns(UserLevel.TABLE_NAME, new String[] { UserLevel.LEVEL_NAME });

		// DataBaseQueryBuilder tquery = new
		// DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		// if (vo.getId() != null) {
		// tquery.and(DataBaseQueryOpertion.NOT_EQUALS, EmployeeTeam.TEAM_ID,
		// vo.getId());
		// }
		//
		// List<EmployeeTeam> epList = this.dao.listByQuery(tquery,
		// EmployeeTeam.class);
		// Set<String> userIds = new HashSet<String>();
		// for (EmployeeTeam ep : epList) {
		//
		// userIds.add(ep.getUserId());
		// }
		//
		//
		// tquery = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		// if (vo.getId() != null) {
		// tquery.and(EmployeeTeam.TEAM_ID, vo.getId());
		// this.dao.listByQuery(tquery, EmployeeTeam.class);
		// userIds = new HashSet<String>();
		// for (EmployeeTeam ep : epList) {
		//
		// userIds.add(ep.getUserId());
		// }
		// builder.or(DataBaseQueryOpertion.IN, User.ID, userIds);
		// }
		//
		//
		//
		// builder.or(DataBaseQueryOpertion.IS_TRUE, User.IS_MULTIPLE_TEAM);
		// builder.or(DataBaseQueryOpertion.NOT_IN, User.ID, userIds);

		if (EweblibUtil.isValid(vo.getUserName())) {

			builder.and(DataBaseQueryOpertion.LIKE, User.USER_NAME, vo.getUserName());
		}

		Set<String> ids = new HashSet<String>();

		if (EweblibUtil.isValid(vo.getProjectId())) {

			DataBaseQueryBuilder tquery = new DataBaseQueryBuilder(EmployeeProject.TABLE_NAME);
			tquery.and(EmployeeProject.PROJECT_ID, vo.getProjectId());

			List<EmployeeProject> etList = this.dao.listByQuery(tquery, EmployeeProject.class);
			for (EmployeeProject et : etList) {
				ids.add(et.getUserId());
			}

			builder.and(DataBaseQueryOpertion.IN, User.ID, ids);

		}

		builder.limitColumns(new User().getColumnList());
		List<User> ulist = this.dao.listByQuery(builder, User.class);

		Set<String> userIds = new HashSet<String>();

		DataBaseQueryBuilder tquery = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		if (vo.getId() != null) {
			tquery.and(EmployeeTeam.TEAM_ID, vo.getId());
			List<EmployeeTeam> epList = this.dao.listByQuery(tquery, EmployeeTeam.class);
			for (EmployeeTeam ep : epList) {

				userIds.add(ep.getUserId());
			}
		}

		List<User> finalList = new ArrayList<User>();

		List<User> excludeList = new ArrayList<User>();

		DataBaseQueryBuilder pquery = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		pquery.distinct(EmployeeTeam.USER_ID);
		List<EmployeeTeam> epList = this.dao.distinctQuery(pquery, EmployeeTeam.class);
		for (User user : ulist) {
			boolean find = false;
			for (EmployeeTeam ep : epList) {
				if (ep.getUserId().equalsIgnoreCase(user.getId()) && !userIds.contains(ep.getUserId())) {
					user.setTeamId("xxxxxxxxxx");

					if (!user.getIsMultipleTeam()) {
						find = true;
					}

					break;
				}
			}

			if (find) {
				excludeList.add(user);
			} else {
				finalList.add(user);
			}
		}
		finalList.addAll(excludeList);

		return finalList;

	}

	public boolean isAdmin(String userId) {

		if (EweblibUtil.isEmpty(userId)) {
			return false;
		}

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(User.TABLE_NAME);
		query.and(User.ID, userId);
		query.and(DataBaseQueryOpertion.IS_TRUE, User.IS_ADMIN);

		if (this.dao.exists(query)) {
			return true;
		}
		return false;
	}

	@Transactional
	public void deleteSalary(IDS ids) {

		for (String id : ids.getIds()) {

			Salary old = (Salary) this.dao.findById(id, Salary.TABLE_NAME, Salary.class);

			Salary salary = new Salary();
			salary.setId(id);
			this.dao.deleteById(salary);
			DataBaseQueryBuilder query = new DataBaseQueryBuilder(SalaryItem.TABLE_NAME);
			query.and(SalaryItem.SALARY_ID, salary.getId());
			this.dao.deleteByQuery(query);

			query = new DataBaseQueryBuilder(DeductedSalaryItem.TABLE_NAME);
			query.and(DeductedSalaryItem.SALARY_ID, salary.getId());
			this.dao.deleteByQuery(query);

		}
	}

	public List<Menu> getMenuList() {
		DataBaseQueryBuilder menuQuery = new DataBaseQueryBuilder(Menu.TABLE_NAME);

		if (!isAdmin(EWeblibThreadLocal.getCurrentUserId())) {

			DataBaseQueryBuilder query = new DataBaseQueryBuilder(User.TABLE_NAME);
			query.and(User.ID, EWeblibThreadLocal.getCurrentUserId());
			query.limitColumns(new String[] { User.GROUP_ID });
			User user = (User) dao.findOneByQuery(query, User.class);

			if (user != null) {
				String groupId = user.getGroupId();

				DataBaseQueryBuilder groupQuery = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
				groupQuery.and(RoleGroup.ID, groupId);
				RoleGroup rg = (RoleGroup) dao.findOneByQuery(groupQuery, RoleGroup.class);

				if (rg != null) {
					menuQuery.and(DataBaseQueryOpertion.IN, Menu.MENU_GROUP_ID, rg.getPermissions().split(","));

				} else {
					menuQuery.and(DataBaseQueryOpertion.IN, Menu.MENU_GROUP_ID, new String[] {});
				}
			} else {
				menuQuery.and(DataBaseQueryOpertion.IN, Menu.MENU_GROUP_ID, new String[] {});
			}
		}

		menuQuery.orderBy(Menu.DISPLAY_ORDER, true);

		List<Menu> menulist = dao.listByQuery(menuQuery, Menu.class);

		for (Menu menu : menulist) {

			DataBaseQueryBuilder itemQuery = new DataBaseQueryBuilder(MenuItem.TABLE_NAME);
			itemQuery.and(MenuItem.MENU_ID, menu.getId());
			itemQuery.orderBy(MenuItem.DISPLAY_ORDER, true);

			List<MenuItem> itemList = dao.listByQuery(itemQuery, MenuItem.class);

			menu.setList(itemList);
		}

		return menulist;
	}

	public void logout() {

		sys.createMsgLog(EWeblibThreadLocal.getCurrentUserId(), "登出后台系统");
	}

	public Set<String> getOwnedUserIdsByReportManager(String userId) {
		Set<String> ids = new HashSet<String>();
		ids.add(userId);

		Set<String> finalIds = new HashSet<String>();
		getIdByReportManager(ids, finalIds);
		finalIds.add(userId);

		return finalIds;

	}
	
	
	public Set<String> getUserReportManagerIds(String userId){
		Set<String> ids = new HashSet<String>();
		ids.add(userId);

		Set<String> finalIds = new HashSet<String>();
		getReportManagerIds(ids, finalIds);

		return finalIds;
		
	}
	

	public void getReportManagerIds(Set<String> ids, Set<String> finalIds) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(User.TABLE_NAME);
		query.and(DataBaseQueryOpertion.IN, User.ID, ids);
		query.limitColumns(new String[] { User.REPORT_MANAGER_ID });


		List<User> userList = this.dao.listByQuery(query, User.class);

		Set<String> userIds = new HashSet<String>();

		for (User user : userList) {

			if (!finalIds.contains(user.getReportManagerId())) {
				userIds.add(user.getReportManagerId());
				finalIds.add(user.getReportManagerId());
			}
		}

		if (!userIds.isEmpty()) {

			getReportManagerIds(userIds, finalIds);
		}

	}

	public void getIdByReportManager(Set<String> ids, Set<String> finalIds) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(User.TABLE_NAME);
		query.limitColumns(new String[] { User.ID });
		query.and(DataBaseQueryOpertion.IN, User.REPORT_MANAGER_ID, ids);

		List<User> userList = this.dao.listByQuery(query, User.class);

		Set<String> userIds = new HashSet<String>();

		for (User user : userList) {

			if (!finalIds.contains(user.getId())) {
				userIds.add(user.getId());
				finalIds.add(user.getId());
			}
		}

		if (!userIds.isEmpty()) {

			getIdByReportManager(userIds, finalIds);
		}

	}

	public String getUserNameById(String id) {

		if (id == null) {
			id = EWeblibThreadLocal.getCurrentUserId();
		}

		User user = (User) this.dao.findById(id, User.TABLE_NAME, User.class);

		if (user != null) {
			return user.getUserName();
		}

		return "";
	}

}
