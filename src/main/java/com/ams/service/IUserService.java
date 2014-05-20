package com.ams.service;

import java.util.List;

import com.ams.bean.Attendance;
import com.ams.bean.Customer;
import com.ams.bean.Department;
import com.ams.bean.EmployeeTeam;
import com.ams.bean.Pic;
import com.ams.bean.Salary;
import com.ams.bean.Team;
import com.ams.bean.User;
import com.ams.bean.vo.SearchVo;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;

public interface IUserService {

	public void updateUser(User user);

	public User regUser(User user);
	
	public User loadUser(User user);

	public User login(User user, boolean fromApp);

	public String getRoleByUserId(String id);

	public String getRoleNameByUserId(String id);


	public void resetPwd(User user);


	public void lockUserById(BaseEntity be);

	public void unlockUserById(BaseEntity be);

	public List<String> listUserAccessMenuIds();

	public void checkUserName(String userName);

	public boolean inRole(String groupIds, String roleId);

	public EntityResults<User> listUserForApp(SearchVo vo);

	public void addDepartment(Department dep);
	
	public Department loadDepartment(Department dep);


	public EntityResults<Department> listDepartments(SearchVo vo);




	public void addPic(Pic pic);

	public EntityResults<Pic> listPics(SearchVo vo);

	public EntityResults<Salary> listUserSalaries(SearchVo vo);

	public void addSalart(Salary salary);

	public Salary getSalaryDetail(Salary salary);

	public EntityResults<Department> listDepartmentsForApp(SearchVo vo);



	public void addAttendance(List<Attendance> attendanceList);

	public EntityResults<Salary> listAllUserSalaries(SearchVo vo);




}
