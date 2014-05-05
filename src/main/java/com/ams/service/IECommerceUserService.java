package com.ams.service;

import java.util.List;
import java.util.Map;

import com.ams.bean.vo.SearchVo;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;
import com.eweblib.bean.User;

public interface IECommerceUserService {

	public void updateUser(User user);

	public User regUser(User user);

	public User login(User user, boolean fromApp);

	public String getRoleByUserId(String id);

	public String getRoleNameByUserId(String id);

	public EntityResults<User> listForAdmin(SearchVo vo);




	public void resetPwd(User user);

	public User loadUserInfo(User user);


	public void lockUserById(BaseEntity be);

	public void unlockUserById(BaseEntity be);


	public List<String> listUserAccessMenuIds();




	public void checkUserName(String userName);


	public boolean inRole(String groupIds, String roleId);

}
