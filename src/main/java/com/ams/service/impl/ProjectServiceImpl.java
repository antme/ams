package com.ams.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ams.bean.Project;
import com.ams.service.IProjectService;
import com.eweblib.bean.EntityResults;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.service.AbstractService;
import com.eweblib.util.EweblibUtil;

@Service(value = "projectService")
public class ProjectServiceImpl extends AbstractService implements IProjectService {

	@Override
	public void addProject(Project project) {

		if (EweblibUtil.isValid(project.getId())) {

			this.dao.updateById(project);
		} else {
			this.dao.insert(project);
		}

	}

	@Override
	public EntityResults<Project> listProjects() {
		
		
		return this.dao.listByQueryWithPagnation(new DataBaseQueryBuilder(Project.TABLE_NAME), Project.class);
	}

	@Override
	public List<Project> listProjectsForAppDailyReport() {
		
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Project.TABLE_NAME);
		
		builder.limitColumns(new String[]{Project.PROJECT_NAME, Project.ID, Project.PROJECT_START_DATE, Project.PROJECT_END_DATE});
		List<Project> projects =  this.dao.listByQuery(builder, Project.class);
		
		for(Project project : projects){
			
			Calendar c = Calendar.getInstance();
			c.setTime(project.getProjectStartDate());
			int startDay = c.get(Calendar.DAY_OF_YEAR);
			
			c.setTime(project.getProjectEndDate());
			int endDay = c.get(Calendar.DAY_OF_YEAR);
			
			c.setTime(new Date());
			int currentDay = c.get(Calendar.DAY_OF_YEAR);
			
			project.setProjectTotalDays((endDay - startDay));
			
			project.setProjectRemainingDays(endDay-currentDay);
			project.setProjectUsedDays(currentDay - startDay);
			
			
			project.setUserWorkedDays(2d);
			
			
			
		}
		
		return projects;
		 
	}

}
