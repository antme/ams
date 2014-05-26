package com.ams.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ams.bean.Attendance;
import com.ams.bean.DeductedSalaryItem;
import com.ams.bean.Department;
import com.ams.bean.EmployeeTeam;
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
import com.ams.service.IUserService;
import com.eweblib.bean.EntityResults;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.exception.LoginException;
import com.eweblib.exception.ResponseException;
import com.eweblib.service.AbstractService;
import com.eweblib.util.DataEncrypt;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;

@Service(value = "userService")
public class UserServiceImpl extends AbstractService implements IUserService {
	public static final String ADM_ORDER_MANAGE = "adm_order_manage";

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
			throw new ResponseException("此用户名已经被注册");
		}

		if (user.getUserPassword() != null) {
			user.setPassword(DataEncrypt.generatePassword(user.getUserPassword()));
		}

		if (EweblibUtil.isValid(user.getId())) {
			this.dao.updateById(user);
		} else {
			dao.insert(user);

		}
		return user;
	}

	public User loadUser(User user) {

		return (User) this.dao.findById(user.getId(), User.TABLE_NAME, User.class);
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
		return u;
	}


	@Override
	public String getRoleNameByUserId(String id) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.ID, id);

		User user = (User) dao.findOneByQuery(builder, User.class);

		// return user.getRoleName();
		return null;
	}

	public void resetPwd(User user) {
		user.setPassword(DataEncrypt.generatePassword(user.getUserPassword()));
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

		builder.join(User.TABLE_NAME, UserType.TABLE_NAME, User.USER_TYPE_ID, UserType.ID);
		builder.joinColumns(UserType.TABLE_NAME, new String[] { UserType.TYPE_NAME });

		builder.join(User.TABLE_NAME, UserLevel.TABLE_NAME, User.USER_LEVEL_ID, UserLevel.ID);
		builder.joinColumns(UserLevel.TABLE_NAME, new String[] { UserLevel.LEVEL_NAME });

		builder.limitColumns(new String[] { User.USER_NAME, User.USER_CODE, User.MOBILE_NUMBER, User.ID });

		// FIXME:根据上下级关系查询数据
		mergeKeywordQuery(builder, vo.getKeyword(), User.TABLE_NAME, new String[] { User.USER_NAME, User.MOBILE_NUMBER });

		EntityResults<User> userList = this.dao.listByQueryWithPagnation(builder, User.class);

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		query.join(EmployeeTeam.TABLE_NAME, Team.TABLE_NAME, EmployeeTeam.TEAM_ID, Team.ID);
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
		builder.join(Department.TABLE_NAME, User.TABLE_NAME, Department.DEPARTMENT_MANAGER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });
		builder.limitColumns(new Department().getColumnList());
		return this.dao.listByQueryWithPagnation(builder, Department.class);
	}


	

	public void addPic(Pic pic) {

		this.dao.insert(pic);
	}

	public EntityResults<Pic> listPics(Pic pic) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Pic.TABLE_NAME);

		builder.join(Pic.TABLE_NAME, User.TABLE_NAME, Pic.USER_ID, User.ID);
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
		return this.dao.listByQueryWithPagnation(builder, Pic.class);
	}

	public EntityResults<Salary> listUserSalaries(Salary salary) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Salary.TABLE_NAME);
		builder.join(Salary.TABLE_NAME, User.TABLE_NAME, Salary.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });
		
		//FIXME: 获取下属的工资
		builder.and(Salary.USER_ID, salary.getUserId());

		if (EweblibUtil.isValid(salary.getUserName())) {
			List<String> userIds = this.getUserIds(salary.getUserName());
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

	public EntityResults<Salary> listAllUserSalaries(SearchVo vo) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Salary.TABLE_NAME);
		builder.join(Salary.TABLE_NAME, User.TABLE_NAME, Salary.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		builder.limitColumns(new String[] { Salary.USER_ID, Salary.ID, Salary.DEDUCTED_SALARY, Salary.REMAINING_SALARAY, Salary.TOTAL_SALARY, Salary.MONTH, Salary.YEAR });

		return this.dao.listByQueryWithPagnation(builder, Salary.class);

	}

	public void addSalart(Salary salary) {
		salary.setUserId(EWeblibThreadLocal.getCurrentUserId());
		this.dao.insert(salary);
	}

	public Salary getSalaryDetail(Salary salary) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Salary.TABLE_NAME);
		builder.limitColumns(new String[] { Salary.ID, Salary.MONTH, Salary.YEAR });
		builder.and(Salary.ID, salary.getId());

		salary = (Salary) this.dao.findOneByQuery(builder, Salary.class);

		salary.setSalaryPerDay(200d);

		Map<String, String> pMap = new HashMap<String, String>();
		List<Project> projects = this.dao.listByQuery(new DataBaseQueryBuilder(Project.TABLE_NAME), Project.class);
		for (Project p : projects) {
			pMap.put(p.getId(), p.getProjectName());
		}

		DataBaseQueryBuilder detailQuery = new DataBaseQueryBuilder(SalaryItem.TABLE_NAME);
		detailQuery.and(SalaryItem.SALARY_ID, salary.getId());
		detailQuery.limitColumns(new String[] {SalaryItem.PROJECT_NAME, SalaryItem.ATTENDANCE_DAYS, SalaryItem.PROJECT_ID, SalaryItem.TOTOL_SALARY, SalaryItem.PERFORMANCE_SALARY, SalaryItem.COMMENT });

		List<SalaryItem> items = this.dao.listByQuery(detailQuery, SalaryItem.class);
		double salaryTotal = 0;

		for (SalaryItem item : items) {
//			item.setProjectName(pMap.get(item.getProjectId()));
			salaryTotal += item.getTotolSalary();
		}

		salary.setTotalSalary(salaryTotal);
		salary.setSalaryItems(items);
		DataBaseQueryBuilder deductedQuery = new DataBaseQueryBuilder(DeductedSalaryItem.TABLE_NAME);
		deductedQuery.and(SalaryItem.SALARY_ID, salary.getId());
		deductedQuery.limitColumns(new String[] { DeductedSalaryItem.TOTOL_SALARY, DeductedSalaryItem.NAME, DeductedSalaryItem.COMMENT });
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
		builder.limitColumns(new String[] { Department.ID, Department.DEPARTMENT_NAME });

		return this.dao.listByQueryWithPagnation(builder, Department.class);
	}


	public void addAttendance(List<Attendance> attendanceList) {

		for (Attendance attendance : attendanceList) {

			Team team = (Team) this.dao.findById(attendance.getTeamId(), Attendance.TABLE_NAME, Attendance.class);
			attendance.setProjectId(team.getProjectId());
			attendance.setDepartmentId(team.getDepartmentId());

			this.dao.insert(attendance);
		}
	}
	
	
	public EntityResults<User> listAllUsers(User vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.join(User.TABLE_NAME, UserType.TABLE_NAME, User.USER_TYPE_ID, UserType.ID);
		builder.joinColumns(UserType.TABLE_NAME, new String[]{UserType.TYPE_NAME});
		
		builder.join(User.TABLE_NAME, UserLevel.TABLE_NAME, User.USER_LEVEL_ID, UserLevel.ID);
		builder.joinColumns(UserLevel.TABLE_NAME, new String[]{UserLevel.LEVEL_NAME});
		
		if(EweblibUtil.isValid(vo.getUserName())){
			builder.and(DataBaseQueryOpertion.LIKE, User.USER_NAME, vo.getUserName());
		}
		
		if(EweblibUtil.isValid(vo.getUserTypeId())){
			builder.and( User.USER_TYPE_ID, vo.getUserTypeId());
		}
		
		if(EweblibUtil.isValid(vo.getUserLevelId())){
			builder.and( User.USER_LEVEL_ID, vo.getUserLevelId());
		}
		
		builder.limitColumns(new User().getColumnList());

		return this.dao.listByQueryWithPagnation(builder, User.class);
	}

}
