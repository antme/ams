package com.ams.service;

import java.util.List;
import java.util.Set;

import com.ams.bean.Attendance;
import com.ams.bean.Department;
import com.ams.bean.EmployeeProject;
import com.ams.bean.EmployeeTeam;
import com.ams.bean.Menu;
import com.ams.bean.Pic;
import com.ams.bean.Salary;
import com.ams.bean.User;
import com.ams.bean.vo.SearchVo;
import com.ams.bean.vo.UserSearchVo;
import com.eweblib.bean.EntityResults;
import com.eweblib.bean.IDS;
import com.eweblib.dbhelper.DataBaseQueryBuilder;

public interface IUserService {

	public List<String> getUserIds(String userName);
	public User regUser(User user);
	
	public User loadUser(User user);

	public User login(User user, boolean fromApp);



	public void resetPwd(User user);

	public List<String> listUserAccessMenuIds();


	public boolean inRole(String groupIds, String roleId);

	public EntityResults<User> listUserForApp(SearchVo vo);

	public void addDepartment(Department dep);
	
	public Department loadDepartment(Department dep);


	public EntityResults<Department> listDepartments(SearchVo vo);


	public void addPic(Pic pic);

	public EntityResults<Pic> listPics(Pic vo);
	
	
	public DataBaseQueryBuilder getPicQuery(Pic pic);

	public EntityResults<Salary> listUserSalaries(Salary salary);

	public void addSalart(Salary salary);

	public Salary getSalaryDetail(Salary salary);

	public EntityResults<Department> listDepartmentsForApp(SearchVo vo);




	public EntityResults<Salary> listAllUserSalaries(Salary salary);

	public EntityResults<User> listAllUsers(User vo);
	
	public List<User> selectAllUsersForProject(UserSearchVo vo);
	
	public List<User> selectAllUsersForTeam(UserSearchVo vo);

    public boolean isAdmin(String userId);
    
    
    public Set<String> getOwnedDepartmentIds(Set<String> mockedUserIds);
    
    
    public Set<String> getOwnerdProjectIds(Set<String> mockedUserIds, Set<String> depIds);
    
    
    public Set<String> getOwnedTeamIds(Set<String> mockedUserIds, Set<String> projectIds);
	public Set<String> getOwnedUserIds(String currentUserId);
	
	public void deleteSalary(IDS ids);
	
	
	public List<Menu> getMenuList();
	public void logout();
	
	
	Set<String> getOwnedUserIdsByReportManager(String userId);
	

	
//	public String getUserNameById(String id);


}
