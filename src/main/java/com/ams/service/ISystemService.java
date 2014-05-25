package com.ams.service;

import java.io.InputStream;

import com.ams.bean.RoleGroup;
import com.ams.bean.UserLevel;
import com.ams.bean.UserType;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;


public interface ISystemService {

	void importSalary(InputStream inputStream);

	void importTask(InputStream inputStream);

	void addUserType(UserType type);
	
	BaseEntity getUserType(UserType type);


	void addUserLevel(UserLevel level);

	EntityResults<UserType> listUserTypes(UserType type);

	EntityResults<UserLevel> listUserLevels(UserLevel level);

	BaseEntity getUserLevel(UserLevel level);

	EntityResults<RoleGroup> listUserGroups(RoleGroup group);

	void addUserGroup(RoleGroup group);

	BaseEntity getUserGroup(RoleGroup group);


	
}
