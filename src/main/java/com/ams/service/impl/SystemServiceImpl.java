package com.ams.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ams.bean.DeductedSalaryItem;
import com.ams.bean.Department;
import com.ams.bean.Menu;
import com.ams.bean.Project;
import com.ams.bean.ProjectTask;
import com.ams.bean.RoleGroup;
import com.ams.bean.Salary;
import com.ams.bean.SalaryItem;
import com.ams.bean.Task;
import com.ams.bean.Team;
import com.ams.bean.User;
import com.ams.bean.UserLevel;
import com.ams.bean.UserType;
import com.ams.bean.vo.SalaryMonth;
import com.ams.bean.vo.SearchVo;
import com.ams.service.IProjectService;
import com.ams.service.ISystemService;
import com.ams.service.IUserService;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;
import com.eweblib.bean.IDS;
import com.eweblib.bean.Log;
import com.eweblib.bean.LogItem;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.exception.ResponseException;
import com.eweblib.service.AbstractService;
import com.eweblib.util.DateUtil;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;
import com.eweblib.util.ExcelUtil;

@Service(value = "sys")
public class SystemServiceImpl extends AbstractAmsService implements ISystemService {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IProjectService projectService;
	

	@Transactional
	public void importSalary(InputStream inputStream, Salary temp) {
		ExcelUtil excleUtil = new ExcelUtil(inputStream);
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

				DataBaseQueryBuilder exquery = new DataBaseQueryBuilder(Salary.TABLE_NAME);
				exquery.and(Salary.USER_ID, user.getId());
				exquery.and(Salary.YEAR, month.getYear());
				exquery.and(Salary.MONTH, month.getMonth());
				Salary old = (Salary) this.dao.findOneByQuery(exquery, Salary.class);

				if (temp.getOverrideexists() == null) {
					if (old != null) {
						throw new ResponseException("此员工的工资已经存在,不能重复导入,如果导入,请先删除后再导入或者勾选覆盖选项");
					}

				} else {

					if (old != null) {
						IDS ids = new IDS();
						List<String> l = new ArrayList<String>();
						l.add(old.getId());
						ids.setIds(l);
						userService.deleteSalary(ids);
					}

				}

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

	@Transactional
	public void importTask(InputStream inputStream, Task temp) {
		ExcelUtil excleUtil = new ExcelUtil(inputStream);
		List<String[]> list = excleUtil.getAllData(0);

		int index = 0;
		String teamName = "";
		String teamLeaderName = "";
		String teamLeaderContactPhone = "";
		String projectName = "";
		String projectStartDate = "";
		String projectEndDate = "";
		String projectPeriod = "";
		String taskDescrpition = "";

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
							} else {
								throw new ResponseException("请检查模板");
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
						task.setDescription(taskDescrpition);
						task.setPrice(EweblibUtil.getDouble(rows[5], 0d));
						task.setAmountDescription(EweblibUtil.getDouble(rows[3], 0d) + rows[2]);
						task.setPriceDescription(EweblibUtil.getDouble(rows[5], 0d) + "元");
						task.setRemark(rows[6]);
						taskList.add(task);
					}

				} else if (row.startsWith("施工细节描述")) {
					taskDescrpition = row.split(getKey(row, "施工细节描述"))[1].trim();

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
		Team team = (Team) this.dao.findOneByQuery(teamQuery, Team.class);

		if (team == null) {
			throw new ResponseException("施工队不存在，请先创建施工队");
		}

		DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
		userQuery.and(User.USER_NAME, teamLeaderName);
		User user = (User) this.dao.findOneByQuery(userQuery, User.class);

		if (user == null) {
			throw new ResponseException("用户不存在，请先创建用户");
		}

		for (Task task : taskList) {
			task.setTeamId(team.getId());
			task.setProjectId(project.getId());
			task.setProjectName(projectName);
			task.setProjectStartDate(DateUtil.getDate(projectStartDate, "YYYY年MM月DD日"));
			task.setProjectEndDate(DateUtil.getDate(projectEndDate, "YYYY年MM月DD日"));
			task.setTeamName(teamName);
			task.setDescription(taskDescrpition);
			task.setTaskContactPhone(teamLeaderContactPhone);
			task.setTaskPeriod(projectPeriod);
			task.setUserId(user.getId());
		}

		ProjectTask pt = null;
		for (Task task : taskList) {
			pt = (ProjectTask) EweblibUtil.toEntity(task.toString(), ProjectTask.class);
			break;
		}

		DataBaseQueryBuilder ptquery = new DataBaseQueryBuilder(ProjectTask.TABLE_NAME);
		ptquery.and(ProjectTask.USER_ID, user.getId());
		ptquery.and(ProjectTask.TEAM_ID, team.getId());
		ptquery.and(ProjectTask.PROJECT_ID, project.getId());
		ptquery.and(ProjectTask.PROJECT_START_DATE, pt.getProjectStartDate());
		ptquery.and(ProjectTask.PROJECT_END_DATE, pt.getProjectEndDate());
		ptquery.and(DataBaseQueryOpertion.IS_FALSE, ProjectTask.IS_DELETED);

		ProjectTask prot = (ProjectTask) this.dao.findOneByQuery(ptquery, ProjectTask.class);
		if (prot != null) {

			if (temp.getOverrideexists() == null) {
				throw new ResponseException("此任务已经存在，不能导入, 你可以删除任务后导入或者勾选覆盖现有数据再导入");
			} else {
		
				IDS ids = new IDS();
				List<String> idlist = new ArrayList<String>();
				idlist.add(prot.getId());
				ids.setIds(idlist);
				projectService.deleteProjectTasks(ids);
				

			}
		}

		this.dao.insert(pt);

		for (Task task : taskList) {
			task.setProjectTaskId(pt.getId());
			this.dao.insert(task);
		}
	}

	public void addUserType(UserType type) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(UserType.TABLE_NAME);
		builder.and(UserType.TYPE_NAME, type.getTypeName());
		if (type.getId() != null) {
			builder.and(DataBaseQueryOpertion.NOT_EQUALS, UserType.ID, type.getId());
		}

		if (dao.exists(builder)) {
			throw new ResponseException("此类型已经存在");
		}

		if (EweblibUtil.isValid(type.getId())) {

			UserType old = (UserType) this.dao.findById(type.getId(), UserType.TABLE_NAME, UserType.class);
			this.dao.updateById(type);

		} else {

			this.dao.insert(type);
		}
	}

	public BaseEntity getUserType(UserType type) {
		return this.dao.findById(type.getId(), UserType.TABLE_NAME, UserType.class);
	}

	public void addUserLevel(UserLevel level) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(UserLevel.TABLE_NAME);
		builder.and(UserLevel.LEVEL_NAME, level.getLevelName());
		if (level.getId() != null) {
			builder.and(DataBaseQueryOpertion.NOT_EQUALS, UserLevel.ID, level.getId());
			builder.and(UserLevel.USER_TYPE_ID, level.getUserTypeId());
		}

		if (dao.exists(builder)) {
			throw new ResponseException("此级别已经存在");
		}

		if (EweblibUtil.isValid(level.getId())) {

			UserLevel old = (UserLevel) this.dao.findById(level.getId(), UserLevel.TABLE_NAME, UserLevel.class);

			this.dao.updateById(level);

		} else {

			this.dao.insert(level);

		}
	}

	public EntityResults<UserType> listUserTypes(UserType type) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(UserType.TABLE_NAME);
		query.join(UserType.TABLE_NAME, User.TABLE_NAME, UserType.CREATOR_ID, User.ID);
		query.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		query.limitColumns(new UserType().getColumnList());
		
		mergeCommonQuery(query);

		return this.dao.listByQueryWithPagnation(query, UserType.class);

	}

	public EntityResults<UserLevel> listUserLevels(UserLevel level) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(UserLevel.TABLE_NAME);
		query.join(UserLevel.TABLE_NAME, User.TABLE_NAME, UserLevel.CREATOR_ID, User.ID);
		query.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		query.join(UserLevel.TABLE_NAME, UserType.TABLE_NAME, UserLevel.USER_TYPE_ID, UserType.ID);
		query.joinColumns(UserType.TABLE_NAME, new String[] { UserType.TYPE_NAME });

		query.limitColumns(new UserLevel().getColumnList());

		if (level.getUserTypeId() != null) {
			query.and(UserLevel.USER_TYPE_ID, level.getUserTypeId());
		}
		
		mergeCommonQuery(query);

		return this.dao.listByQueryWithPagnation(query, UserLevel.class);
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

	public EntityResults<RoleGroup> listUserGroups(RoleGroup group) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);

		
		mergeCommonQuery(query);
		return this.dao.listByQueryWithPagnation(query, RoleGroup.class);

	}

	public void addUserGroup(RoleGroup group) {

		if (group.getPermissionitems() != null) {

			String p = "";

			for (String per : group.getPermissionitems()) {
				p = p + per + ",";
			}

			p = p + "]]";
			p = p.replace(",]]", "");

			group.setPermissions(p);
		}

		if (EweblibUtil.isValid(group.getId())) {

			this.dao.updateById(group);
		} else {
			this.dao.insert(group);
		}

	}

	public BaseEntity getUserGroup(RoleGroup group) {

		return this.dao.findById(group.getId(), RoleGroup.TABLE_NAME, RoleGroup.class);
	}

	public void deleteUserGroup(RoleGroup group) {

		DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
		userQuery.and(User.GROUP_ID, group.getId());

		if (this.dao.exists(userQuery)) {
			throw new ResponseException("请确保此角色下的用户角色已经更新");
		} else {

			RoleGroup rg = (RoleGroup) this.dao.findById(group.getId(), RoleGroup.TABLE_NAME, RoleGroup.class);
			this.dao.deleteById(group);
			createMsgLog(null, "删除角色【" + rg.getGroupName() + "】");
		}
	}

	public void deleteDepartment(Department dep) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Project.TABLE_NAME);
		query.and(Project.DEPARTMENT_ID, dep.getId());

		if (this.dao.exists(query)) {
			throw new ResponseException("请先确保项目下的部门信息已经修改再删除");
		} else {
			this.dao.deleteById(dep);
		}
	}

	public void deleteUserType(UserType type) {

		DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
		userQuery.and(User.USER_TYPE_ID, type.getId());

		if (this.dao.exists(userQuery)) {
			throw new ResponseException("请确保此工种下的用户已经更新成新的工种");
		} else {
			DataBaseQueryBuilder levelQuery = new DataBaseQueryBuilder(UserLevel.TABLE_NAME);
			levelQuery.and(UserLevel.USER_TYPE_ID, type.getId());
			if (this.dao.exists(levelQuery)) {
				throw new ResponseException("请确保此工种下的工种级别全部先删除");
			} else {

				type = (UserType) this.dao.findById(type.getId(), UserType.TABLE_NAME, UserType.class);
				createMsgLog(null, String.format("删除工种类型【" + type.getTypeName() + "】"));
				this.dao.deleteById(type);
			}
		}

	}

	public void deleteUserLevel(UserLevel level) {

		DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
		userQuery.and(User.USER_LEVEL_ID, level.getId());

		if (this.dao.exists(userQuery)) {
			throw new ResponseException("请确保此级别下的用户已经更新成新的级别");
		} else {

			level = (UserLevel) this.dao.findById(level.getId(), UserLevel.TABLE_NAME, UserLevel.class);
			createMsgLog(null, String.format("删除工种级别【" + level.getLevelName() + " 】"));
			this.dao.deleteById(level);
		}

	}

	public void createMenu(List<String> items) {

		int i = 0;
		for (String item : items) {
			DataBaseQueryBuilder query = new DataBaseQueryBuilder(Menu.TABLE_NAME);
			query.and(Menu.TITLE, item.trim());
			Menu menu = (Menu) this.dao.findOneByQuery(query, Menu.class);
			menu.setDisplayOrder(i);

			i++;
			this.dao.updateById(menu);
		}

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

	public void createMsgLog(String userId, String message) {

		try {
			if (EweblibUtil.isEmpty(userId)) {
				userId = EWeblibThreadLocal.getCurrentUserId();
			}

			Log log = new Log();
			log.setUserId(userId);
			log.setMessage(message);
			log.setLogType("msg");
			System.out.println(log.toString());
			this.dao.insert(log);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EntityResults<Log> listLogs(SearchVo vo) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Log.TABLE_NAME);
		query.join(Log.TABLE_NAME, User.TABLE_NAME, Log.USER_ID, User.ID);
		query.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });

		query.limitColumns(new Log().getColumnList());
		return this.dao.listByQueryWithPagnation(query, Log.class);
	}

	public List<LogItem> listLogItemss(Log log) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(LogItem.TABLE_NAME);
		query.and(LogItem.LOG_ID, log.getId());

		return this.dao.listByQuery(query, LogItem.class);

	}

}
