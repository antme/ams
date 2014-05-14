package com.ams.service;

import java.util.List;

import com.ams.bean.Project;
import com.eweblib.bean.EntityResults;

public interface IProjectService {

	void addProject(Project project);

	EntityResults<Project> listProjects();

	List<Project> listProjectsForAppDailyReport();

}
