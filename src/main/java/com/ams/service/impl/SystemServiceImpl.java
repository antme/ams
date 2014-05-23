package com.ams.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ams.bean.DeductedSalaryItem;
import com.ams.bean.Project;
import com.ams.bean.Salary;
import com.ams.bean.SalaryItem;
import com.ams.bean.Task;
import com.ams.bean.Team;
import com.ams.bean.User;
import com.ams.bean.UserLevel;
import com.ams.bean.UserType;
import com.ams.bean.vo.SalaryMonth;
import com.ams.service.ISystemService;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.exception.ResponseException;
import com.eweblib.service.AbstractService;
import com.eweblib.util.DateUtil;
import com.eweblib.util.EweblibUtil;
import com.eweblib.util.ExcleUtil;

@Service(value = "sys")
public class SystemServiceImpl extends AbstractService implements ISystemService {

	@Transactional
	public void importSalary(InputStream inputStream) {
		ExcleUtil excleUtil = new ExcleUtil(inputStream);
		List<String[]> list = excleUtil.getAllData(0);

		if (!list.isEmpty()) {
			SalaryMonth month = getMonthAndSalaryPerDay(list);

			if (month != null && month.getUserName() != null) {
				List<SalaryItem> items = getPaySalary(list);
				List<DeductedSalaryItem> ditems = getDeductedSalary(list);

				DataBaseQueryBuilder query = new DataBaseQueryBuilder(User.TABLE_NAME);
				query.and(User.USER_NAME, month.getUserName());

				User user = (User) this.dao.findOneByQuery(query, User.class);
				if (user == null) {
					throw new ResponseException("无此用户，请先创建此用户");
				}

				double totalSalary = 0;
				for (SalaryItem item : items) {

					if (item.getTotolSalary() != null) {
						totalSalary += item.getTotolSalary();
					}
				}

				double deductedSalary = 0;
				for (DeductedSalaryItem item : ditems) {

					if (item.getTotolSalary() != null) {
						deductedSalary += item.getTotolSalary();
					}
				}

				Salary salary = new Salary();
				salary.setYear(month.getYear());
				salary.setMonth(month.getMonth());
				salary.setTotalSalary(totalSalary);
				salary.setDeductedSalary(deductedSalary);
				salary.setUserId(user.getId());
				salary.setRemainingSalaray(totalSalary - deductedSalary);
				salary.setSalaryPerDay((double) month.getSalaryPerDay());

				this.dao.insert(salary);
				for (SalaryItem item : items) {
					item.setSalaryId(salary.getId());
					this.dao.insert(item);
				}

				for (DeductedSalaryItem item : ditems) {
					item.setSalaryId(salary.getId());
					this.dao.insert(item);
				}

			} else {
				throw new ResponseException("请检查模板");
			}

		}

	}

	public void importTask(InputStream inputStream) {
		ExcleUtil excleUtil = new ExcleUtil(inputStream);
		List<String[]> list = excleUtil.getAllData(0);

		int index = 0;
		String teamName = "";
		String teamLeaderName = "";
		String teamLeaderContactPhone = "";
		String projectName = "";
		String projectStartDate = "";
		String projectEndDate = "";
		String projectPeriod = "";

		List<Task> taskList = new ArrayList<Task>();

		boolean taskStart = true;
		if (!list.isEmpty()) {

			for (String[] rows : list) {

				String row = "";
				for (int i = 0; i < rows.length; i++) {
					if (EweblibUtil.isValid(rows[i])) {
						row = row + rows[i];
					}
				}

				if (index == 1) {

					String[] teamInfo = row.split(getKey(row, "联系电话"));
					if (teamInfo.length > 1) {
						teamLeaderContactPhone = teamInfo[1].trim();
					}

					teamInfo = teamInfo[0].split(getKey(row, "班组名称"));
					for (String ti : teamInfo) {
						if (EweblibUtil.isValid(ti)) {
							teamInfo = ti.trim().split(" ");
							if (teamInfo.length > 1) {
								teamName = teamInfo[0].trim();
								teamLeaderName = teamInfo[1].trim();
							}
						}
					}

				} else if (index == 2) {
					projectPeriod = row.split(getKey(row, "工期"))[1];
					projectStartDate = row.split(getKey(row, "工期"))[0].split("开工日期")[1].replace(" ", "").trim();

				} else if (index == 3) {
					projectName = row.split(getKey(row, "项目名称"))[1].trim();
				} else if (!row.startsWith("施工细节") && !row.startsWith("序号") && taskStart) {

					if (EweblibUtil.isValid(rows[1])) {
						Task task = new Task();
						task.setAmount(EweblibUtil.getDouble(rows[3], 0d));
						task.setUnit(rows[2]);
						task.setTaskName(rows[1]);
						task.setDisplayOrder(EweblibUtil.getInteger(rows[0], 0));
						task.setDescription(rows[5]);
						task.setPrice(EweblibUtil.getDouble(rows[4], 0d));
						task.setAmountDescription(EweblibUtil.getDouble(rows[3], 0d) + rows[2]);
						task.setPriceDescription(EweblibUtil.getDouble(rows[4], 0d) + "元");
						taskList.add(task);
					}

				} else if (row.startsWith("施工细节")) {
					taskStart = false;
				} else if (row.startsWith("竣工日期")) {
					projectEndDate = row.split(getKey(row, "绩效单价"))[0].trim().split(getKey(row, "竣工日期"))[1].trim().replace(" ", "");
				}

				index++;
			}

		}
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Project.TABLE_NAME);
		query.and(Project.PROJECT_NAME, projectName);
		Project project = (Project) this.dao.findOneByQuery(query, Project.class);

		if (project == null) {
			throw new ResponseException("项目不存在，请先创建此项目");
		}

		DataBaseQueryBuilder teamQuery = new DataBaseQueryBuilder(Team.TABLE_NAME);
		teamQuery.and(Team.TEAM_NAME, teamName);
		Team team = (Team) this.dao.findOneByQuery(query, Team.class);

		if (team == null) {
			throw new ResponseException("施工队不存在，请先创建施工队");
		}

		DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
		userQuery.and(User.USER_NAME, teamLeaderName);
		User user = (User) this.dao.findOneByQuery(userQuery, User.class);

		if (user == null) {
			throw new ResponseException("用户不存在，请先创建施工队");
		}

		for (Task task : taskList) {
			task.setTeamId(team.getId());
			task.setProjectId(project.getId());
			task.setProjectName(projectName);
			task.setProjectStartDate(DateUtil.getDate(projectStartDate, "YYYY年MM月DD日"));
			task.setProjectEndDate(DateUtil.getDate(projectEndDate, "YYYY年MM月DD日"));
			task.setTeamName(teamName);
			task.setTaskContactPhone(teamLeaderContactPhone);
			task.setTaskPeriod(projectPeriod);
			this.dao.insert(task);
		}
	}

	public void addUserType(UserType type) {

		if (EweblibUtil.isValid(type.getId())) {
			this.dao.updateById(type);
		} else {
			this.dao.insert(type);
		}
	}

	public BaseEntity getUserType(UserType type) {
		return this.dao.findById(type.getId(), UserType.TABLE_NAME, UserType.class);
	}

	public void addUserLevel(UserLevel level) {
		if (EweblibUtil.isValid(level.getId())) {
			this.dao.updateById(level);
		} else {
			this.dao.insert(level);
		}
	}

	public EntityResults<UserType> listUserTypes(UserType type) {

		return this.dao.listByQueryWithPagnation(new DataBaseQueryBuilder(UserType.TABLE_NAME), UserType.class);

	}

	public EntityResults<UserLevel> listUserLevels(UserLevel level) {
		return this.dao.listByQueryWithPagnation(new DataBaseQueryBuilder(UserLevel.TABLE_NAME), UserLevel.class);
	}

	public String getKey(String row, String splitKey) {
		if (row.contains(splitKey + "：")) {
			splitKey = splitKey + "：";
		} else if (row.contains(splitKey + ":")) {
			splitKey = splitKey + ":";
		} else if (row.contains(splitKey)) {
		}
		return splitKey;
	}

	public BaseEntity getUserLevel(UserLevel level) {
		return this.dao.findById(level.getId(), UserLevel.TABLE_NAME, UserLevel.class);

	}

	private List<DeductedSalaryItem> getDeductedSalary(List<String[]> list) {
		int payEndIndex = 0;
		for (int i = 0; i < list.size(); i++) {
			String[] rows = list.get(i);
			for (String row : rows) {

				if (row.contains("应扣款")) {
					payEndIndex = i;
				}
			}

			if (payEndIndex > 0) {
				break;
			}

		}

		List<DeductedSalaryItem> items = new ArrayList<DeductedSalaryItem>();
		for (int j = payEndIndex; j < list.size(); j++) {
			String[] rows = list.get(j);

			if (rows[3].contains("小计")) {
				break;
			}
			if (!rows[3].contains("小计")) {

				if (EweblibUtil.isValid(rows[3])) {
					DeductedSalaryItem item = new DeductedSalaryItem();

					item.setName(rows[3]);

					if (EweblibUtil.isValid(rows[6])) {
						item.setTotolSalary(Double.parseDouble(rows[6]));
					}

					item.setComment(rows[9]);
					items.add(item);
				}

			}

		}

		System.out.println(items);

		return items;

	}

	private List<SalaryItem> getPaySalary(List<String[]> list) {
		int payStartIndex = 0;
		int payEndIndex = 0;
		for (int i = 0; i < list.size(); i++) {
			String[] rows = list.get(i);
			for (String row : rows) {

				if (row.contains("应付款")) {
					payStartIndex = i;
				}

				if (row.contains("应扣款")) {
					payEndIndex = i;
				}
			}

			if (payStartIndex > 0 && payEndIndex > 0) {
				break;
			}

		}

		List<SalaryItem> items = new ArrayList<SalaryItem>();
		for (int j = payStartIndex; j < payEndIndex; j++) {
			String[] rows = list.get(j);

			if (!rows[3].contains("小计")) {
				SalaryItem item = new SalaryItem();
				item.setProjectName(rows[3]);
				if (EweblibUtil.isValid(rows[5])) {
					item.setAttendanceDays(Double.parseDouble(rows[5]));
				}

				if (EweblibUtil.isValid(rows[6])) {
					item.setTotolSalary(Double.parseDouble(rows[6]));
				}
				if (EweblibUtil.isValid(rows[7])) {
					item.setPerformanceSalary(Double.parseDouble(rows[7]));
				}
				if (EweblibUtil.isValid(rows[8])) {
					item.setPerformanceSalaryUnit(Double.parseDouble(rows[8]));
				}

				item.setComment(rows[9]);
				items.add(item);

			}

		}

		return items;

	}

	public SalaryMonth getMonthAndSalaryPerDay(List<String[]> list) {
		SalaryMonth sm = null;
		for (int i = 0; i < list.size(); i++) {

			String[] rows = list.get(i);

			int monthSearch = 0;
			int month = 0;
			int salaryPerDay = 0;

			for (String row : rows) {

				if (EweblibUtil.isValid(row)) {
					if (row.contains("结算单")) {
						sm = new SalaryMonth();
						String year = row.replace("结算单", "");
						sm.setYear(Integer.parseInt(year));
						monthSearch = 1;
						continue;
					}

					int number = 0;
					try {
						number = Integer.parseInt(row);
					} catch (Exception e) {
					}

					if (number > 0 && monthSearch == 1) {
						month = number;
						monthSearch = 2;
					} else if (number > 0 && monthSearch == 2) {
						salaryPerDay = number;
					}

				}
			}

			if (month > 0) {
				sm.setMonth(month);
				sm.setSalaryPerDay(salaryPerDay);
				sm.setRowNumber(i);
				break;
			}

		}

		for (int i = 0; i < list.size(); i++) {
			String[] rows = list.get(i);
			String userName = "";
			boolean findUserName = false;
			for (String row : rows) {

				if (row.contains("应付款")) {
					findUserName = true;
					if (sm != null) {
						sm.setUserName(userName);
					}
				}
				if (EweblibUtil.isValid(row) && !findUserName) {
					userName = row.trim();
				}

			}

		}
		return sm;

	}

}
