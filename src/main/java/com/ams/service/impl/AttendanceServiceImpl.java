package com.ams.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ams.bean.Attendance;
import com.ams.bean.Team;
import com.ams.bean.User;
import com.ams.service.IAttendanceService;
import com.ams.service.IProjectService;
import com.ams.service.IUserService;
import com.eweblib.bean.EntityResults;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.service.AbstractService;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;
import com.eweblib.util.ExcelTemplateUtil;

@Service(value = "attendanceService")
public class AttendanceServiceImpl extends AbstractService implements IAttendanceService {

	@Autowired
	private IProjectService pservice;

	@Autowired
	private IUserService userService;

	public EntityResults<Attendance> listAttendances(Attendance attendance) {

		DataBaseQueryBuilder builder = getAttendanceQuery(attendance);

		DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
		userQuery.limitColumns(new String[] { User.ID, User.USER_NAME });
		List<User> users = this.dao.listByQuery(userQuery, User.class);
		Map<String, String> userNameMap = new HashMap<String, String>();
		for (User user : users) {
			userNameMap.put(user.getId(), user.getUserName());
		}

		DataBaseQueryBuilder teamQuery = pservice.getTeamQuery(null);
		List<Team> teams = this.dao.listByQuery(teamQuery, Team.class);

		EntityResults<Attendance> list = this.dao.listByQueryWithPagnation(builder, Attendance.class);

		for (Attendance a : list.getEntityList()) {

			for (Team team : teams) {
				if (a.getTeamId().equalsIgnoreCase(team.getId())) {
					a.setDepartmentName(team.getDepartmentName());
					a.setProjectName(team.getProjectName());
					break;
				}
			}
			a.setOperator(userNameMap.get(a.getOperatorId()));
		}

		return list;
	}

	public DataBaseQueryBuilder getAttendanceQuery(Attendance attendance) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Attendance.TABLE_NAME);
		builder.join(Attendance.TABLE_NAME, User.TABLE_NAME, Attendance.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		builder.join(Attendance.TABLE_NAME, Team.TABLE_NAME, Attendance.TEAM_ID, Team.ID);
		builder.joinColumns(Team.TABLE_NAME, new String[] { Team.TEAM_NAME });

		if (attendance != null) {
			String userName = attendance.getUserName();
			if (EweblibUtil.isValid(userName)) {

				List<String> userIds = userService.getUserIds(userName);

				builder.and(DataBaseQueryOpertion.IN, Attendance.USER_ID, userIds);

			}

			userName = attendance.getOperator();
			if (EweblibUtil.isValid(userName)) {

				List<String> userIds = userService.getUserIds(userName);

				builder.and(DataBaseQueryOpertion.IN, Attendance.OPERATOR_ID, userIds);

			}

			if (EweblibUtil.isValid(attendance.getTeamId())) {
				builder.and(Attendance.TEAM_ID, attendance.getTeamId());
			}

			if (EweblibUtil.isValid(attendance.getDepartmentId())) {
				builder.and(Attendance.DEPARTMENT_ID, attendance.getDepartmentId());
			}

			if (EweblibUtil.isValid(attendance.getProjectId())) {
				builder.and(Attendance.PROJECT_ID, attendance.getProjectId());
			}
		}

		builder.limitColumns(new Attendance().getColumnList());
		return builder;
	}

	public String exportAttendanceToExcle(Attendance attendance, HttpServletRequest request) {
		DataBaseQueryBuilder builder = getAttendanceQuery(attendance);
		List<Attendance> list = this.dao.listByQuery(builder, Attendance.class);
		String webPath = request.getSession().getServletContext().getRealPath("/");

		ExcelTemplateUtil etu = new ExcelTemplateUtil();
		etu.setSrcPath(webPath + File.separator + "template" + File.separator + "attendance.xls");

		String filePath = genDownloadRandomRelativePath(EWeblibThreadLocal.getCurrentUserId()) + "考勤表" + attendance.getYear() + "-" + attendance.getMonth() + ".xls";
		String desXlsPath = webPath + filePath;

		if (new File(desXlsPath).exists()) {
			new File(desXlsPath).delete();
		}
		new File(desXlsPath).getParentFile().mkdirs();
		etu.setDesPath(desXlsPath);
		etu.setSheetName("考勤表");

		etu.getSheet();
		etu.setCellDoubleValue(0, 2, attendance.getYear());
		etu.setCellDoubleValue(0, 6, attendance.getMonth());

		int start = 6;
		for (Attendance at : list) {

			if (at.getAttendanceDayType() == 0) {
				etu.setCellStrValue(start, 0, at.getUserName());
				etu.setCellStrValue(start, 2, "●");
				etu.setCellStrValue(start, 3, "●");
				etu.setCellStrValue(start, 4, "●");
				etu.setCellStrValue(start, 5, "●");
				etu.setCellStrValue(start, 6, "△");
			} else {
				etu.setCellStrValue(start + 1, 0, at.getUserName());
				etu.setCellStrValue(start + 1, 2, "●");
				etu.setCellStrValue(start + 1, 3, "●");
				etu.setCellStrValue(start + 1, 4, "●");
				etu.setCellStrValue(start + 1, 5, "●");
				etu.setCellStrValue(start + 1, 6, "△");
			}

			start = start + 2;

		}
		etu.exportToNewFile();

		return desXlsPath;
	}

}
