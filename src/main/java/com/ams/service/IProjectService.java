package com.ams.service;

import java.util.List;

import com.ams.bean.Attendance;
import com.ams.bean.Customer;
import com.ams.bean.DailyReport;
import com.ams.bean.DailyReportComment;
import com.ams.bean.EmployeeTeam;
import com.ams.bean.Project;
import com.ams.bean.Task;
import com.ams.bean.Team;
import com.ams.bean.vo.DailyReportVo;
import com.ams.bean.vo.SearchVo;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;
import com.eweblib.dbhelper.DataBaseQueryBuilder;

public interface IProjectService {

	void addProject(Project project);
	
	public Project getProjectInfo(Project project);

	EntityResults<Project> listProjects(Project project);

	EntityResults<Task> listProjectTasks(Task task);

	public DailyReport addDailyReport(DailyReportVo report, List<String> pics);

	public EntityResults<DailyReportVo> listDailyReport(DailyReportVo report);

	public void addDailyReportComment(DailyReportComment comment);

	List<Task> listProjectTasksForAppDailyReport();

	EntityResults<Customer> listCustomers(SearchVo vo);

	void viewDailyReport(DailyReportVo report);

	int countDailyReport(DailyReportVo report);
	

	public void addTeam(Team team);
	
	public Team getTeam(Team team);
	
	public EntityResults<Team> listTeams(Team team);
	
	public DataBaseQueryBuilder getTeamQuery(Team team);

	public List<Team> listTeamsForApp(Team team);
	
	public List<Attendance> listTeamMemebersForApp(EmployeeTeam team);
	

	public void addCustomer(Customer customer);

	public EntityResults<Customer> listCustomersForApp(SearchVo vo);

	BaseEntity getCustomerInfo(Customer customer);

	EntityResults<Task> listAllTasksFor(Task task);

	List<DailyReport> listDailyReportPlan(DailyReportVo report);


}
