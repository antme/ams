package com.ams.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ams.bean.AmsUser;
import com.ams.bean.DailyReport;
import com.ams.bean.DailyReportComment;
import com.ams.bean.Pic;
import com.ams.bean.Project;
import com.ams.bean.vo.DailyReportVo;
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

		builder.limitColumns(new String[] { Project.PROJECT_NAME, Project.ID, Project.PROJECT_START_DATE, Project.PROJECT_END_DATE });
		List<Project> projects = this.dao.listByQuery(builder, Project.class);

		for (Project project : projects) {

			Calendar c = Calendar.getInstance();
			c.setTime(project.getProjectStartDate());
			int startDay = c.get(Calendar.DAY_OF_YEAR);

			c.setTime(project.getProjectEndDate());
			int endDay = c.get(Calendar.DAY_OF_YEAR);

			c.setTime(new Date());
			int currentDay = c.get(Calendar.DAY_OF_YEAR);

			project.setProjectTotalDays((endDay - startDay));

			project.setProjectRemainingDays(endDay - currentDay);
			project.setProjectUsedDays(currentDay - startDay);

			project.setUserWorkedDays(2d);

		}

		return projects;

	}

	public DailyReport addDailyReport(DailyReportVo vo, List<String> pics) {

		if (vo.getRemovedPics() != null) {
			for (String pic : vo.getRemovedPics()) {
				DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Pic.TABLE_NAME);
				builder.and(Pic.PIC_URL, pic);
				this.dao.deleteByQuery(builder);
			}
		}
		DailyReport report = (DailyReport) EweblibUtil.toEntity(vo.toString(), DailyReport.class);
		if (EweblibUtil.isValid(report.getId())) {

			this.dao.updateById(report);
		} else {
			this.dao.insert(report);
		}

		if (pics != null) {
			for (String pic : pics) {
				Pic picture = new Pic();
				picture.setPicUrl(pic);
				picture.setDailyReportId(report.getId());
				picture.setUserId(report.getUserId());
				this.dao.insert(picture);
			}
		}

		return report;

	}

	public EntityResults<DailyReportVo> listDailyReport() {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(DailyReport.TABLE_NAME);
		builder.join(DailyReport.TABLE_NAME, AmsUser.TABLE_NAME, DailyReport.USER_ID, AmsUser.ID);
		builder.joinColumns(AmsUser.TABLE_NAME, new String[] { AmsUser.USER_NAME });

		builder.limitColumns(new String[] { DailyReport.ID, DailyReport.MATERIAL_RECORD, DailyReport.WORKING_RECORD, DailyReport.PLAN, DailyReport.SUMMARY, DailyReport.REPORT_DAY,
		        DailyReport.CREATED_ON });

		EntityResults<DailyReportVo> reports = this.dao.listByQueryWithPagnation(builder, DailyReportVo.class);

		for (DailyReportVo vo : reports.getEntityList()) {

			List<String> picUrls = new ArrayList<String>();

			DataBaseQueryBuilder picQuery = new DataBaseQueryBuilder(Pic.TABLE_NAME);
			picQuery.and(Pic.DAILY_REPORT_ID, vo.getId());
			List<Pic> pics = this.dao.listByQuery(picQuery, Pic.class);
			for (Pic p : pics) {
				picUrls.add(p.getPicUrl());
			}
			vo.setPics(picUrls);
			
			
			DataBaseQueryBuilder commentQuery = new DataBaseQueryBuilder(DailyReportComment.TABLE_NAME);
			commentQuery.join(DailyReportComment.TABLE_NAME, AmsUser.TABLE_NAME, DailyReportComment.USER_ID, AmsUser.ID);
			commentQuery.joinColumns(AmsUser.TABLE_NAME, new String[]{AmsUser.USER_NAME});

			commentQuery.and(DailyReportComment.DAILY_REPORT_ID, vo.getId());
			
			commentQuery.limitColumns(new String[]{DailyReportComment.COMMENT, DailyReportComment.COMMENT_DATE, DailyReportComment.ID});
			
			List<DailyReportComment> comments = this.dao.listByQuery(commentQuery, DailyReportComment.class);
			
			vo.setComments(comments);
		}

		return reports;

	}

	public void addDailyReportComment(DailyReportComment comment) {
		comment.setCommentDate(new Date());
		this.dao.insert(comment);

	}

}
