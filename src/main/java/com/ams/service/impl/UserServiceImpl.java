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
import com.eweblib.bean.BaseEntity;
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

	@Override
	public void updateUser(User user) {
		if (EweblibUtil.isEmpty(user.getId())) {
			user.setId(EWeblibThreadLocal.getCurrentUserId());
		}
		this.dao.updateById(user);

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

		builder.limitColumns(new String[] { User.ID, User.USER_NAME });
		User u = (User) dao.findOneByQuery(builder, User.class);

		return u;
	}

	@Override
	public String getRoleByUserId(String id) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.ID, id);

		User user = (User) dao.findOneByQuery(builder, User.class);

		return user.getId();
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
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.ID, user.getId());
		builder.and(User.PASSWORD, DataEncrypt.generatePassword(user.getPassword()));

		if (this.dao.exists(builder)) {
			// user.setPassword(DataEncrypt.generatePassword(user.getNewPwd()));
			this.dao.updateById(user);
		} else {
			throw new ResponseException("原始密码错误");
		}

	}

	@Override
	public void lockUserById(BaseEntity be) {
		User user = (User) dao.findById(be.getId(), User.TABLE_NAME, User.class);
		// user.setStatus(UserStatus.LOCKED.toString());

		dao.updateById(user);
	}

	@Override
	public void unlockUserById(BaseEntity be) {
		User user = (User) dao.findById(be.getId(), User.TABLE_NAME, User.class);
		// user.setStatus(UserStatus.NORMAL.toString());
		dao.updateById(user);

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

	public void checkUserName(String userName) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.USER_NAME, userName);
		if (dao.exists(builder)) {
			throw new ResponseException("此用户已经注册");
		}
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

	public EntityResults<Pic> listPics(SearchVo vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Pic.TABLE_NAME);

		builder.join(Pic.TABLE_NAME, User.TABLE_NAME, Pic.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });
		builder.limitColumns(new Pic().getColumnList());

		builder.and(DataBaseQueryOpertion.NULL, Pic.DAILY_REPORT_ID);
		return this.dao.listByQueryWithPagnation(builder, Pic.class);
	}

	public EntityResults<Salary> listUserSalaries(SearchVo vo) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Salary.TABLE_NAME);
		builder.join(Salary.TABLE_NAME, User.TABLE_NAME, Salary.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

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
		detailQuery.limitColumns(new String[] { SalaryItem.ATTENDANCE_DAYS, SalaryItem.PROJECT_ID, SalaryItem.TOTOL_SALARY, SalaryItem.PERFORMANCE_SALARY, SalaryItem.COMMENT });

		List<SalaryItem> items = this.dao.listByQuery(detailQuery, SalaryItem.class);
		double salaryTotal = 0;

		for (SalaryItem item : items) {
			item.setProjectName(pMap.get(item.getProjectId()));
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
			this.dao.insert(attendance);
		}
	}
	
	
	public EntityResults<User> listAllUsers(SearchVo vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.join(User.TABLE_NAME, UserType.TABLE_NAME, User.USER_TYPE_ID, UserType.ID);
		builder.joinColumns(UserType.TABLE_NAME, new String[]{UserType.TYPE_NAME});
		
		builder.join(User.TABLE_NAME, UserLevel.TABLE_NAME, User.USER_LEVEL_ID, UserLevel.ID);
		builder.joinColumns(UserLevel.TABLE_NAME, new String[]{UserLevel.LEVEL_NAME});
		
		builder.limitColumns(new User().getColumnList());

		return this.dao.listByQueryWithPagnation(builder, User.class);
	}

}
