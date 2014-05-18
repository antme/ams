package com.ams.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ams.bean.Attendance;
import com.ams.bean.Customer;
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
import com.ams.bean.vo.SearchVo;
import com.ams.service.IUserService;
import com.ams.util.Role;
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
		if (dao.exists(builder)) {
			throw new ResponseException("此用户名已经被注册");
		}

		// if (EweblibUtil.isEmpty(user.getStatus())) {
		// user.setStatus(UserStatus.NORMAL.toString());
		// }

		dao.insert(user);

		return user;
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

	@Override
	public EntityResults<User> listForAdmin(SearchVo vo) {
		String keyword = vo.getKeyword();
		String userStatus = vo.getUserStatus();
		String roleName = vo.getRoleName();

		if (EweblibUtil.isEmpty(roleName)) {
			roleName = Role.USER.toString();
		}

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		// builder.and(User.ROLE_NAME, roleName.toUpperCase());

		if (!EweblibUtil.isEmpty(userStatus)) {
//			builder.and(User.STATUS, userStatus);
		}

		if (!EweblibUtil.isEmpty(keyword)) {
			DataBaseQueryBuilder builder2 = new DataBaseQueryBuilder(User.TABLE_NAME);
			builder2.or(DataBaseQueryOpertion.LIKE, User.USER_NAME, keyword);
			builder2.or(DataBaseQueryOpertion.LIKE, "name", keyword);
			// builder2.or(DataBaseQueryOpertion.LIKE, User.MOBILE_NUMBER,
			// keyword);
			// if (Role.CUSTOMER_SERVICE.toString().equalsIgnoreCase(roleName))
			// {
			// builder2.or(DataBaseQueryOpertion.LIKE, User.USER_EXT_PHONE,
			// keyword);
			// builder2.or(DataBaseQueryOpertion.LIKE, User.USER_CODE, keyword);
			// }
			builder.and(builder2);
		}

		builder.orderBy(User.CREATED_ON, false);

		return dao.listByQueryWithPagnation(builder, User.class);
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

	public User loadUserInfo(User user) {

		return (User) this.dao.findById(user.getId(), User.TABLE_NAME, User.class);
	}

	public User loadUserInfoForApp(User user) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		// builder.and(User.ID, user.getUserId());
		// builder.limitColumns(new String[]{User.USER_NAME, User.MOBILE_NUMBER,
		// User.NAME, User.DEFAULT_ADDRESS, User.AGE, User.SEX, User.EMAIL,
		// User.USER_CITY_NAME, User.ID});

		User u = (User) this.dao.findOneByQuery(builder, User.class);
		// u.setUserId(u.getId());

		return u;

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

		builder.limitColumns(new String[] { User.USER_NAME, User.USER_CODE, User.MOBILE_NUMBER, User.ID });
		EntityResults<User> userList = this.dao.listByQueryWithPagnation(builder, User.class);

		for (User user : userList.getEntityList()) {
			user.setUserType("油漆工");
			user.setUserLevel("油漆工一级");
			user.setTeams("施工一对,  施工三对");
		}

		return userList;

	}

	public void addDepartment(Department dep) {

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
	
	public Team getTeam(Team team){
		
		return (Team) this.dao.findById(team.getId(), Team.TABLE_NAME, Team.class);
	}

	public EntityResults<Team> listTeams(SearchVo vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Team.TABLE_NAME);
		builder.join(Team.TABLE_NAME, Department.TABLE_NAME, Team.DEPARTMENT_ID, Department.ID);
		builder.joinColumns(Department.TABLE_NAME, new String[]{Department.DEPARTMENT_NAME});
		
		builder.join(Team.TABLE_NAME, User.TABLE_NAME, Team.TEAM_LEADER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[]{User.USER_NAME});
		
		builder.join(Team.TABLE_NAME, Project.TABLE_NAME, Team.PROJECT_ID, Project.ID);
		builder.joinColumns(Project.TABLE_NAME, new String[]{Project.PROJECT_NAME});
		
		builder.limitColumns(new Team().getColumnList());

		return this.dao.listByQueryWithPagnation(builder, Team.class);
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

		builder.limitColumns(new String[] { Customer.ID, Customer.NAME, Customer.CONTACT_MOBILE_NUMBER, Customer.CONTACT_PERSON, Customer.ADDRESS, Customer.REMARK, Customer.POSITION });

		EntityResults<Customer> customerList = this.dao.listByQueryWithPagnation(builder, Customer.class);

		for (Customer customer : customerList.getEntityList()) {

			customer.setProjects("项目1， 项目2");
		}

		return customerList;

	}

	public void addPic(Pic pic) {

		this.dao.insert(pic);
	}

	public EntityResults<Pic> listPics(SearchVo vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Pic.TABLE_NAME);

		return this.dao.listByQueryWithPagnation(builder, Pic.class);
	}

	public EntityResults<Salary> listUserSalaries(SearchVo vo) {

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

		List<SalaryItem> items = new ArrayList<SalaryItem>();
		for (int i = 1; i < 8; i++) {

			SalaryItem item = new SalaryItem();
			item.setAttendanceDays((double) i);
			item.setProjectName("project" + i);
			item.setComment("备注" + i);
			item.setTotolSalary((double) i * 200);
			item.setPerformanceSalary((double) i * 100);
			items.add(item);

		}

		double salaryTotal = 0;

		for (SalaryItem item : items) {
			salaryTotal += item.getTotolSalary();
		}
		salary.setTotalSalary(salaryTotal);

		salary.setSalaryItems(items);

		List<DeductedSalaryItem> ditems = new ArrayList<DeductedSalaryItem>();

		for (int i = 1; i < 3; i++) {

			DeductedSalaryItem item = new DeductedSalaryItem();
			item.setComment("扣款备注" + i);
			item.setName("扣款项" + i);
			item.setTotolSalary((double) i * 100);
			ditems.add(item);
		}
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

	public void addAttendance(List<Attendance> attendanceList) {

		for (Attendance attendance : attendanceList) {
			this.dao.insert(attendance);
		}
	}

}
