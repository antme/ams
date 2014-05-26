package com.ams.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

			List<Pic> pics = userService.listPics(null).getEntityList();

			int rowIndex = 1;
			for (Pic pic : pics) {
				row = sheet1.createRow(rowIndex);

				row.createCell(0).setCellValue(pic.getUserName());
				row.createCell(1).setCellValue(pic.getProjectName());
				row.createCell(2).setCellValue(pic.getDescription());
				row.createCell(3).setCellValue(pic.getCreatedOn());

				HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();

				HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 450, 122, (short) rowIndex, 5, (short) (rowIndex + 1), 6);

				// anchor1.setAnchorType(2);

				// 插入图片

				ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();

				bufferImg = ImageIO.read(new File("/Users/ymzhou/Documents/workspace/ams/src/main/webapp/" + pic.getPicUrl()));

				ImageIO.write(bufferImg, "png", byteArrayOut);

				patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
				rowIndex++;

			}

			String webPath = request.getSession().getServletContext().getRealPath("/");

			String filePath = genDownloadRandomRelativePath(EWeblibThreadLocal.getCurrentUserId()) + "图片" + new Date().getTime() + ".xls";
			desXlsPath = webPath + filePath;

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

					// TODO Auto-generated catch block

					e.printStackTrace();

				}

			}

		}
		return desXlsPath;

	}

}
