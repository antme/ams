package com.ams.service;

import java.util.List;

import com.ams.bean.AmsUser;
import com.ams.bean.Customer;
import com.ams.bean.Department;
import com.ams.bean.Pic;
import com.ams.bean.Team;
import com.ams.bean.vo.SearchVo;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;
import com.eweblib.bean.User;

public interface IUserService {

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

	public EntityResults<AmsUser> listUserForApp(SearchVo vo);

	public void addDepartment(Department dep);

	public EntityResults<Department> listDepartments(SearchVo vo);

	public void addTeam(Team dep);

	public EntityResults<Team> listTeams(SearchVo vo);

	public void addCustomer(Customer customer);

	public EntityResults<Customer> listCustomersForApp(SearchVo vo);

	public void addPic(Pic pic);

	public EntityResults<Pic> listPics(SearchVo vo);

}
