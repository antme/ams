package com.ams.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ams.bean.AmsUser;
import com.ams.bean.Department;
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
		// if (u.getStatus() != null &&
		// UserStatus.LOCKED.toString().equalsIgnoreCase(u.getStatus())) {
		// throw new ResponseException("账户已冻结，请联系管理员！");
		// }
		// if (!fromApp) {
		//
		// if (u.getGroupId() != null) {
		// DataBaseQueryBuilder groupQuery = new
		// DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
		// groupQuery.and(RoleGroup.ID, u.getGroupId());
		// groupQuery.limitColumns(new String[] { RoleGroup.INDEX_PAGE });
		// RoleGroup group = (RoleGroup) this.dao.findOneByQuery(groupQuery,
		// RoleGroup.class);
		// if (group != null && group.getIndexPage() != null) {
		// u.setIndexPage(group.getIndexPage());
		// }
		// }
		// }
		//
		// if (fromApp) {
		// // query field return to client
		// nameQuery.limitColumns(new String[] { User.DEFAULT_ADDRESS,
		// User.EMAIL, User.MOBILE_NUMBER, User.USER_NAME, User.NAME, User.SEX,
		// User.AGE, User.ID, User.USER_CITY_NAME, User.STATUS });
		//
		//
		// u.setUserId(u.getId());
		//
		// u = loadUserInfoForApp(u);
		// }
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

		builder.limitColumns(new String[]{User.USER_NAME, AmsUser.USER_CODE, AmsUser.MOBILE_NUMBER});
		EntityResults<AmsUser> userList = this.dao.listByQueryWithPagnation(builder, AmsUser.class);

		for (AmsUser user : userList.getEntityList()) {
			user.setUserType("油漆工");
			user.setUserLevel("油漆工一级");
			user.setTeams("施工一对,  施工三对");
		}

		return userList;

	}
	
	public void addDepartment(Department dep){
		
		if(EweblibUtil.isValid(dep.getId())){
			this.dao.updateById(dep);
		}else{
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
	}

	public EntityResults<Team> listTeams(SearchVo vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Team.TABLE_NAME);

		return this.dao.listByQueryWithPagnation(builder, Team.class);
	}

}
