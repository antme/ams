package com.ams.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ams.bean.Attendance;
import com.ams.bean.Department;
import com.ams.bean.EmployeeTeam;
import com.ams.bean.Pic;
import com.ams.bean.Project;
import com.ams.bean.Team;
import com.ams.bean.User;
import com.ams.service.IAttendanceService;
import com.ams.service.IProjectService;
import com.ams.service.IUserService;
import com.eweblib.bean.EntityResults;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.service.AbstractService;
import com.eweblib.util.DateUtil;
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

			if (EweblibUtil.isEmpty(a.getDepartmentName())) {
				for (Team team : teams) {
					if (a.getTeamId().equalsIgnoreCase(team.getId())) {

						a.setDepartmentName(team.getDepartmentName());
						// a.setProjectName(team.getProjectName());
						break;
					}
				}
			}
			a.setOperator(userNameMap.get(a.getOperatorId()));
		}

		return list;
	}

	public DataBaseQueryBuilder getAttendanceQuery(Attendance attendance) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Attendance.TABLE_NAME);
		builder.leftJoin(Attendance.TABLE_NAME, User.TABLE_NAME, Attendance.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });
	
		builder.leftJoin(Attendance.TABLE_NAME, Department.TABLE_NAME, Attendance.DEPARTMENT_ID, Department.ID);
		builder.joinColumns(Department.TABLE_NAME, new String[] { Department.DEPARTMENT_NAME });

		builder.leftJoin(Attendance.TABLE_NAME, Team.TABLE_NAME, Attendance.TEAM_ID, Team.ID);
		builder.joinColumns(Team.TABLE_NAME, new String[] { Team.TEAM_NAME });

		builder.leftJoin(Attendance.TABLE_NAME, Project.TABLE_NAME, Attendance.PROJECT_ID, Project.ID);
		builder.joinColumns(Project.TABLE_NAME, new String[] { Project.PROJECT_NAME });

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

			if (EweblibUtil.isValid(attendance.getUserId())) {
				builder.and(Attendance.USER_ID, attendance.getUserId());
			}
			if (EweblibUtil.isValid(attendance.getOperatorId())) {
				builder.and(Attendance.OPERATOR_ID, attendance.getOperatorId());
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

			if (EweblibUtil.isValid(attendance.getYear()) && attendance.getYear() > 0) {
				builder.and(Attendance.YEAR, attendance.getYear());
			}

			if (EweblibUtil.isValid(attendance.getMonth()) && attendance.getMonth() > 0) {
				builder.and(Attendance.MONTH, attendance.getMonth() - 1);
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

		DataBaseQueryBuilder pquery = new DataBaseQueryBuilder(Project.TABLE_NAME);
		pquery.leftJoin(Project.TABLE_NAME, Department.TABLE_NAME, Project.DEPARTMENT_ID, Department.ID);
		pquery.joinColumns(Department.TABLE_NAME, new String[] { Department.DEPARTMENT_NAME });

		pquery.and(Project.ID, attendance.getProjectId());
		pquery.limitColumns(new String[] { Project.PROJECT_NAME });
		Project project = (Project) this.dao.findOneByQuery(pquery, Project.class);

		if (new File(desXlsPath).exists()) {
			new File(desXlsPath).delete();
		}
		new File(desXlsPath).getParentFile().mkdirs();
		etu.setDesPath(desXlsPath);
		etu.setSheetName("考勤表");

		etu.getSheet();
		etu.setCellDoubleValue(0, 2, attendance.getYear());
		etu.setCellDoubleValue(0, 6, attendance.getMonth());
		Map<String, List<Attendance>> mapList = new HashMap<String, List<Attendance>>();

		for (Attendance at : list) {

			if (mapList.get(at.getUserName()) == null) {
				List<Attendance> atList = new ArrayList<Attendance>();
				atList.add(at);

				mapList.put(at.getUserName(), atList);
			} else {
				List<Attendance> atList = mapList.get(at.getUserName());
				atList.add(at);

				mapList.put(at.getUserName(), atList);
			}
		}

		int start = 6;
		int totalWork = 0;
		int totalRest = 0;

		Set<String> operatoids = new HashSet<String>();

		for (String userName : mapList.keySet()) {
			etu.setCellStrValue(start, 0, userName);

			Map<String, Integer> timeMap = new HashMap<String, Integer>();
			timeMap.put("√", 0);
			timeMap.put("●", 0);
			timeMap.put("○", 0);
			timeMap.put("×", 0);
			timeMap.put("△", 0);
			timeMap.put("◇", 0);
			timeMap.put("□", 0);
			timeMap.put("◆", 0);
			timeMap.put("▼", 0);
			timeMap.put("▲", 0);
			timeMap.put("■", 0);

			for (Attendance at : mapList.get(userName)) {

				operatoids.add(at.getOperatorId());

				String type = "";

				int hours = 4;
				if (at.getHours() != null && at.getHours() > 0) {
					hours = at.getHours();
				}

				if (at.getMinutes() > 30) {
					hours = hours + 1;
				}

				if (at.getAttendanceType() == 0) {
					type = "√";
					totalWork = totalWork + hours;
				} else if (at.getAttendanceType() == 1) {
					type = "●";
					totalRest = totalRest + hours;

				} else if (at.getAttendanceType() == 2) {
					type = "○";
				} else if (at.getAttendanceType() == 3) {
					type = "×";
				} else if (at.getAttendanceType() == 4) {
					type = "△";
				} else if (at.getAttendanceType() == 5) {
					type = "□";
				} else if (at.getAttendanceType() == 6) {
					type = "◇";
				} else if (at.getAttendanceType() == 7) {
					type = "◆";
				} else if (at.getAttendanceType() == 8) {
					type = "▼";
				} else if (at.getAttendanceType() == 9) {
					type = "▲";
				} else if (at.getAttendanceType() == 10) {
					type = "■";
				}

				Calendar c = Calendar.getInstance();
				c.setTime(at.getAttendanceDate());
				int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

				if (at.getAttendanceDayType() == 0) {
					etu.setCellStrValue(start, dayOfMonth + 1, type);
				} else {
					etu.setCellStrValue(start + 1, dayOfMonth + 1, type);

				}

				timeMap.put(type, timeMap.get(type) + hours);

			}
			int column = 32;

			etu.setCellStrValue(start, column + 1, getTime(timeMap.get("√")));
			etu.setCellStrValue(start, column + 2, getTime(timeMap.get("●")));
			etu.setCellStrValue(start, column + 3, getTime(timeMap.get("○")));
			etu.setCellStrValue(start, column + 4, getTime(timeMap.get("×")));
			etu.setCellStrValue(start, column + 5, getTime(timeMap.get("△")));
			etu.setCellStrValue(start, column + 6, getTime(timeMap.get("□")));
			etu.setCellStrValue(start, column + 7, getTime(timeMap.get("◇")));
			etu.setCellStrValue(start, column + 8, getTime(timeMap.get("◆")));
			etu.setCellStrValue(start, column + 9, getTime(timeMap.get("▼")));
			etu.setCellStrValue(start, column + 10, getTime(timeMap.get("▲")));
			etu.setCellStrValue(start, column + 11, getTime(timeMap.get("■")));

			start = start + 2;

		}

		DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
		userQuery.and(DataBaseQueryOpertion.IN, User.ID, operatoids);
		userQuery.limitColumns(new String[] { User.USER_NAME });
		List<User> users = this.dao.listByQuery(userQuery, User.class);
		String userName = "";

		for (User user : users) {

			userName = userName + user.getUserName() + " ";
		}

		String projectInfo = "部门：" + project.getDepartmentName() + "                  项目名称：" + project.getProjectName() + "                          记录员：" + userName + "                应出勤天数："
		        + getTime(totalWork) + "天" + "           应休天数：  " + getTime(totalRest) + "天";
		etu.setCellStrValue(2, 0, projectInfo);

		etu.deletRow(start, 100);
		etu.exportToNewFile();

		return desXlsPath;
	}

	private String getTime(int hours) {
		DecimalFormat df2 = new DecimalFormat("####0.0");

		return df2.format((double) hours / 8);
	}

	public String exportPicToExcle(Pic p, HttpServletRequest request) {

		FileOutputStream fileOut = null;

		BufferedImage bufferImg = null;
		String desXlsPath = null;
		String[] columnHeaders = new String[] { "上传者", "项目", "描述", "上传时间", "图片" };

		try {

			// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray

			// 创建一个工作薄

			HSSFWorkbook wb = new HSSFWorkbook();

			HSSFSheet sheet1 = wb.createSheet("图片");

			sheet1.setColumnWidth(1, 4000);
			sheet1.setColumnWidth(2, 10000);
			sheet1.setColumnWidth(3, 10000);
			sheet1.setColumnWidth(4, 10000);

			HSSFRow row = sheet1.createRow(0);
			int index = 0;
			for (String header : columnHeaders) {
				HSSFCell cell = row.createCell(index);
				cell.setCellValue(header);
				index++;
			}

			DataBaseQueryBuilder query = userService.getPicQuery(p);
			List<Pic> pics = this.dao.listByQuery(query, Pic.class);

			int rowIndex = 1;
			String webPath = request.getSession().getServletContext().getRealPath("/");

			for (Pic pic : pics) {
				File picFile = new File(webPath + pic.getPicUrl());

				row = sheet1.createRow(rowIndex);

				row.createCell(0).setCellValue(pic.getUserName());
				row.createCell(1).setCellValue(pic.getProjectName());
				row.createCell(2).setCellValue(pic.getDescription());
				row.createCell(3).setCellValue(DateUtil.getDateStringTime(pic.getCreatedOn()));
				if (picFile.exists()) {
					HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
					HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 120, (short) 4, rowIndex, (short) 5, (rowIndex + 1));
					// anchor1.setAnchorType(2);

					// 插入图片

					ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
					bufferImg = ImageIO.read(picFile);

					ImageIO.write(bufferImg, "png", byteArrayOut);

					patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
				}
				rowIndex++;

			}

			String filePath = genDownloadRandomRelativePath(EWeblibThreadLocal.getCurrentUserId()) + new Date().getTime() + ".xls";
			desXlsPath = webPath + filePath;

			new File(desXlsPath).getParentFile().mkdirs();

			fileOut = new FileOutputStream(desXlsPath);

			// 写入excel文件

			wb.write(fileOut);

			fileOut.close();

		} catch (IOException io) {

			io.printStackTrace();

			System.out.println("io erorr : " + io.getMessage());

		} finally

		{

			if (fileOut != null)

			{

				try {

					fileOut.close();

				}

				catch (IOException e)

				{

					e.printStackTrace();

				}

			}

		}
		return desXlsPath;

	}

	public List<Attendance> checkAttendance(Attendance attendance) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Project.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.LIKE, Project.PROJECT_ATTENDANCE_MANAGER_ID, attendance.getUserId());
		builder.limitColumns(new String[] { Project.PROJECT_NAME, Project.ID });
		List<Project> list = this.dao.listByQuery(builder, Project.class);

		Set<String> projectIds = new HashSet<String>();

		List<Attendance> checkList = new ArrayList<Attendance>();

		if (list != null) {

			for (Project project : list) {
				projectIds.add(project.getId());
				Attendance att = new Attendance();

				att.setUserId(attendance.getUserId());
				att.setProjectId(project.getId());

				List<Team> teamList = pservice.listTeamsForAppAttendance(att);

				for (Team team : teamList) {
					boolean find = false;
					att.setTeamId(team.getId());
					att.setAttendanceDayType(attendance.getAttendanceDayType());
					att.setAttendanceDate(DateUtil.getDate(DateUtil.getDateString(new Date()), DateUtil.formatSimple));

					List<Attendance> attendanceList = pservice.listTeamMemebersForAppAttendance(att);

					for (Attendance temp : attendanceList) {

						if (temp.getAttendanceType() != null) {
							find = false;

							break;
						}

						find = true;
					}

					if (find) {
						Attendance a = new Attendance();
						a.setProjectName(project.getProjectName());
						a.setTeamName(team.getTeamName());
						checkList.add(a);
					}
				}

			}
		}

		return checkList;

	}

}
