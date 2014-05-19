package com.ams.service;

import java.util.List;

import com.ams.bean.Customer;
import com.ams.bean.DailyReport;
import com.ams.bean.DailyReportComment;
import com.ams.bean.Project;
import com.ams.bean.Task;
import com.ams.bean.vo.DailyReportVo;
import com.ams.bean.vo.SearchVo;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;

public interface IProjectService {

	void addProject(Project project);

	EntityResults<Project> listProjects();

	EntityResults<Task> listProjectTasks();

	public DailyReport addDailyReport(DailyReportVo report, List<String> pics);

	public EntityResults<DailyReportVo> listDailyReport(DailyReportVo report);

	public void addDailyReportComment(DailyReportComment comment);

	List<Task> listProjectTasksForAppDailyReport();

	EntityResults<Customer> listCustomers(SearchVo vo);

	void viewDailyReport(DailyReportVo report);

	int countDailyReport(DailyReportVo report);

}
