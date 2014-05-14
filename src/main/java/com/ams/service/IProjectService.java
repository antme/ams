package com.ams.service;

import com.ams.bean.Project;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;

public interface IProjectService {

	void addProject(Project project);

	EntityResults<Project> listProjects();

	EntityResults<Project> listProjectsForApp();

}
