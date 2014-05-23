package com.ams.service;

import java.io.InputStream;

import com.ams.bean.UserLevel;
import com.ams.bean.UserType;
import com.eweblib.bean.EntityResults;


public interface ISystemService {

	void importSalary(InputStream inputStream);

	void importTask(InputStream inputStream);

	void addUserType(UserType type);

	void addUserLevel(UserLevel level);

	EntityResults<UserType> listUserTypes(UserType type);

	EntityResults<UserLevel> listUserLevels(UserLevel level);

	
}
