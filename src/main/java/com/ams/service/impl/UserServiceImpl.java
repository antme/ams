package com.ams.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ams.bean.AmsUser;
import com.ams.bean.Attendance;
import com.ams.bean.Customer;
import com.ams.bean.Department;
import com.ams.bean.EmployeeTeam;
import com.ams.bean.Pic;
import com.ams.bean.Salary;
import com.ams.bean.Team;
import com.ams.bean.vo.SearchVo;
import com.ams.service.IUserService;
import com.ams.util.Role;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;
import com.eweblib.bean.RoleGroup;
import com.eweblib.bean.User;
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
		// // 手机号码作为默认登录名字
		// if (EweblibUtil.isEmpty(user.getUserName())) {
		// user.setUserName(user.getMobileNumber());
		// }
		//
		// if (EweblibUtil.isEmpty(user.getPassword())) {
		// user.setPassword(DataEncrypt.generatePassword(user.getMobileNumber()));
		// } else {
		// user.setPassword(DataEncrypt.generatePassword(user.getPassword()));
		// }
		// ValidatorUtil.validate(user, "user", "userReg",
		// PermissionConstants.validateFiles);
		// DataBaseQueryBuilder builder = new
		// DataBaseQueryBuilder(User.TABLE_NAME);
		// builder.and(User.USER_NAME, user.getUserName());
		// if (dao.exists(builder)) {
		// throw new ResponseException("此用户名已经被注册");
		// }
		//
		// builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		// builder.and(User.MOBILE_NUMBER, user.getMobileNumber());
		// if (dao.exists(builder)) {
		// throw new ResponseException("此手机号码已经被注册");
		// }
		//
		// if (user.getRoleName() == null) {
		// user.setRoleName(Role.USER.toString());
		// }
		//
		// if (EweblibUtil.isEmpty(user.getStatus())) {
		// user.setStatus(UserStatus.NORMAL.toString());
		// }
		//
		//
		// user = (User) dao.insert(user);
		//
		// // checkUserMobile(user.getMobileNumber());
		//
		// // query field return to client
		// DataBaseQueryBuilder userQuery = new
		// DataBaseQueryBuilder(User.TABLE_NAME);
		// userQuery.and(User.ID, user.getId());
		//
		// userQuery.limitColumns(new String[] { User.DEFAULT_ADDRESS,
		// User.EMAIL, User.MOBILE_NUMBER, User.USER_NAME, User.NAME, User.SEX,
		// User.AGE, User.ID, User.STATUS });
		// return (User) dao.findOneByQuery(userQuery, User.class);

		return null;
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
			builder.and(User.STATUS, userStatus);
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

	public EntityResults<AmsUser> listUserForApp(SearchVo vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(AmsUser.TABLE_NAME);

		builder.limitColumns(new String[] { User.USER_NAME, AmsUser.USER_CODE, AmsUser.MOBILE_NUMBER, AmsUser.ID });
		EntityResults<AmsUser> userList = this.dao.listByQueryWithPagnation(builder, AmsUser.class);

		for (AmsUser user : userList.getEntityList()) {
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

	public EntityResults<Department> listDepartments(SearchVo vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Department.TABLE_NAME);

		return this.dao.listByQueryWithPagnation(builder, Department.class);
	}

	public void addTeam(Team dep) {
		if (EweblibUtil.isValid(dep.getId())) {
			this.dao.updateById(dep);
		} else {
			this.dao.insert(dep);
		}

		
		//删除原来的队员
		String[] members = dep.getTeamMemberIds();

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(EmployeeTeam.TABLE_NAME);
		query.and(EmployeeTeam.TEAM_ID, dep.getId());
		this.dao.deleteByQuery(query);

		if (members != null) {
			for (String id : members) {
				EmployeeTeam et = new EmployeeTeam();
				et.setUserId(id);
				et.setTeamId(dep.getId());
				this.dao.insert(et);
			}
		}
	}

	public EntityResults<Team> listTeams(SearchVo vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Team.TABLE_NAME);

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
		builder.join(Salary.TABLE_NAME, AmsUser.TABLE_NAME, Salary.USER_ID, AmsUser.ID);
		builder.joinColumns(AmsUser.TABLE_NAME, new String[] { AmsUser.USER_NAME });

		builder.limitColumns(new String[] { Salary.ID, Salary.DEDUCTED_SALARY, Salary.REMAINING_SALARAY, Salary.TOTAL_SALARY, Salary.MONTH, Salary.YEAR });

		return this.dao.listByQueryWithPagnation(builder, Salary.class);
	}

	public void addSalart(Salary salary) {
		salary.setUserId(EWeblibThreadLocal.getCurrentUserId());
		this.dao.insert(salary);
	}
	
	public Salary getSalaryDetail(Salary salary){
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Salary.TABLE_NAME);
		builder.limitColumns(new String[] { Salary.ID, Salary.DEDUCTED_SALARY, Salary.REMAINING_SALARAY, Salary.TOTAL_SALARY, Salary.MONTH, Salary.YEAR });
		builder.and(Salary.ID, salary.getId());
		
		salary = (Salary) this.dao.findOneByQuery(builder, Salary.class);
		
		
		
		return null;
	}
	
	
	public EntityResults<Department> listDepartmentsForApp(SearchVo vo){
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Department.TABLE_NAME);
		builder.limitColumns(new String[]{Department.ID, Department.DEPARTMENT_NAME});

		return this.dao.listByQueryWithPagnation(builder, Department.class);
	}
	
	
	public List<Team> listTeamsForApp(Team team){
		
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Team.TABLE_NAME);
		builder.join(Team.TABLE_NAME, Department.TABLE_NAME, Team.DEPARTMENT_ID, Department.ID);
		builder.joinColumns(Department.TABLE_NAME, new String[]{Department.DEPARTMENT_NAME});
		
		builder.limitColumns(new String[]{Team.ID, Team.TEAM_DESCRIPTION, Team.TEAM_NAME});
		
		builder.and(Team.DEPARTMENT_ID, team.getDepartmentId());
		
		List<Team> teams =  this.dao.listByQuery(builder, Team.class);
		
		for(Team t: teams){
			t.setMembersNumber((int)(Math.random() * 100));
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

		DataBaseQueryBuilder atquery = new DataBaseQueryBuilder(AmsUser.TABLE_NAME);
		atquery.join(AmsUser.TABLE_NAME, Attendance.TABLE_NAME, AmsUser.ID, Attendance.USER_ID);
		atquery.joinColumns(Attendance.TABLE_NAME, new String[] { Attendance.ID, Attendance.ATTENDANCE_DATE, Attendance.ATTENDANCE_DAY_TYPE,
		        Attendance.ATTENDANCE_TIME_SELECT_TYPE, Attendance.ATTENDANCE_TYPE, Attendance.TIME });

		if (team.getAttendanceDate() != null) {
			atquery.and(Attendance.TABLE_NAME + "." + Attendance.ATTENDANCE_DATE, team.getAttendanceDate());
		}
		
		atquery.and(Attendance.TABLE_NAME + "." + Attendance.TEAM_ID, team.getTeamId());

		atquery.limitColumns(new String[] { AmsUser.USER_NAME,  AmsUser.ID + "," + Attendance.USER_ID });
		atquery.and(DataBaseQueryOpertion.IN, AmsUser.ID, userIds);
		
		

		List<Attendance> result = this.dao.listByQuery(atquery, Attendance.class);
		
		if(result.isEmpty()){
			DataBaseQueryBuilder userquery = new DataBaseQueryBuilder(AmsUser.TABLE_NAME);
			userquery.limitColumns(new String[] { AmsUser.USER_NAME,  AmsUser.ID + "," + Attendance.USER_ID });
			userquery.and(DataBaseQueryOpertion.IN, AmsUser.ID, userIds);
			
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
