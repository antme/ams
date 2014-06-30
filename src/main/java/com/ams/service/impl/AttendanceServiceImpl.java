package com.ams.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ams.bean.Pic;
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

			if (EweblibUtil.isValid(attendance.getYear())) {
				builder.and(Attendance.YEAR, attendance.getYear());
			}

			if (EweblibUtil.isValid(attendance.getMonth())) {
				builder.and(Attendance.MONTH, attendance.getMonth()-1);
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
		for (String userName : mapList.keySet()) {
			etu.setCellStrValue(start, 0, userName);

			for (Attendance at : mapList.get(userName)) {

				String type = "";

				if (at.getAttendanceType() == 0) {
					type = "√";
				} else if (at.getAttendanceType() == 1) {
					type = "●";
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

			}
			start = start + 2;

		}
		etu.exportToNewFile();

		return desXlsPath;
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
				row.createCell(3).setCellValue(pic.getCreatedOn());
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

}
