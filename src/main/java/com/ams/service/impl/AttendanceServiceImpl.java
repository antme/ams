package com.ams.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.eweblib.util.EweblibUtil;

@Service(value = "attendanceService")
public class AttendanceServiceImpl extends AbstractService implements IAttendanceService {

	@Autowired
	private IProjectService pservice;
	
	@Autowired
	private IUserService userService;
	
	
	public EntityResults<Attendance> listAttendances(Attendance attendance) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Attendance.TABLE_NAME);
		builder.join(Attendance.TABLE_NAME, User.TABLE_NAME, Attendance.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		builder.join(Attendance.TABLE_NAME, Team.TABLE_NAME, Attendance.TEAM_ID, Team.ID);
		builder.joinColumns(Team.TABLE_NAME, new String[] { Team.TEAM_NAME });

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

		builder.limitColumns(new Attendance().getColumnList());

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


}
