package com.ams.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ams.bean.Attendance;
import com.ams.bean.Customer;
import com.ams.bean.CustomerContact;
import com.ams.bean.DailyReport;
import com.ams.bean.DailyReportComment;
import com.ams.bean.EmployeeTeam;
import com.ams.bean.Project;
import com.ams.bean.ProjectTask;
import com.ams.bean.Task;
import com.ams.bean.Team;
import com.ams.bean.vo.CustomerContactVo;
import com.ams.bean.vo.DailyReportVo;
import com.ams.bean.vo.SearchVo;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;
import com.eweblib.bean.IDS;
import com.eweblib.dbhelper.DataBaseQueryBuilder;

public interface IProjectService {

	void addProject(Project project);
	
	public Project getProjectInfo(Project project);

	EntityResults<Project> listProjects(Project project);

	EntityResults<Task> listProjectTasks(Task task);
	
	EntityResults<ProjectTask> listProjectTasksForApp(Task task);
	
	ProjectTask getProjectTaskDetails(ProjectTask task);
	

	public DailyReport addDailyReport(DailyReportVo report, List<String> pics);

	public EntityResults<DailyReportVo> listDailyReport(DailyReportVo report, boolean fromApp);

	public void addDailyReportComment(DailyReportComment comment);

	List<Project> listProjectsForAppDailyReportSelect(Task t);

	EntityResults<Customer> listCustomers(SearchVo vo);

	void viewDailyReport(DailyReportVo report);

	int countDailyReport(DailyReportVo report);
	

	public void addTeam(Team team);
	
	public Team getTeam(Team team);
	
	public EntityResults<Team> listTeams(Team team);
	
	public DataBaseQueryBuilder getTeamQuery(Team team);

	public List<Team> listTeamsForAppAttendance(Attendance att);
	
	public List<Attendance> listTeamMemebersForAppAttendance(Attendance atten);
	

	public void addCustomer(Customer customer);

	public EntityResults<Customer> listCustomersForApp(SearchVo vo);

	BaseEntity getCustomerInfo(Customer customer);

	EntityResults<Task> listAllTasksFor(Task task);

	List<DailyReportVo> listDailyReportPlan(DailyReportVo report);

	String exportDailyReportToExcle(DailyReportVo report, HttpServletRequest request);

	void addCustomerContact(CustomerContactVo vo, List<CustomerContact> concats);

	List<CustomerContact> listCustomerContact(CustomerContactVo vo);

	EntityResults<ProjectTask> listAllProjectTasks(ProjectTask task);

	List<Task> listAllTasksFromProjectTasks(Task task);

	void deleteProjectTasks(IDS ids);

	List<Project> listProjectsForAppAttendance(SearchVo vo);

	public void addAttendance(List<Attendance> attendanceList, Attendance att);



	


}
