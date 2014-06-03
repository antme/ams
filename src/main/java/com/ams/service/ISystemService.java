package com.ams.service;

import java.io.InputStream;
import java.util.List;

import com.ams.bean.Department;
import com.ams.bean.RoleGroup;
import com.ams.bean.Salary;
import com.ams.bean.Task;
import com.ams.bean.UserLevel;
import com.ams.bean.UserType;
import com.ams.bean.vo.SearchVo;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;
import com.eweblib.bean.Log;
import com.eweblib.bean.LogItem;


public interface ISystemService {

	void importSalary(InputStream inputStream, Salary temp);

	void importTask(InputStream inputStream, Task temp);

	void addUserType(UserType type);
	
	BaseEntity getUserType(UserType type);


	void addUserLevel(UserLevel level);

	EntityResults<UserType> listUserTypes(UserType type);

	EntityResults<UserLevel> listUserLevels(UserLevel level);

	BaseEntity getUserLevel(UserLevel level);

	EntityResults<RoleGroup> listUserGroups(RoleGroup group);

	void addUserGroup(RoleGroup group);

	BaseEntity getUserGroup(RoleGroup group);

	void deleteUserGroup(RoleGroup group);

	void deleteDepartment(Department dep);

	void deleteUserType(UserType type);

	void deleteUserLevel(UserLevel level);

	void createMenu(List<String> items);

	
	void createMsgLog(String userId, String message);

	EntityResults<Log> listLogs(SearchVo vo);
	
	List<LogItem> listLogItemss(Log log);

}
