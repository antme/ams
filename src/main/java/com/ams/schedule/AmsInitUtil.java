package com.ams.schedule;

import com.ams.bean.Menu;
import com.ams.bean.MenuItem;
import com.eweblib.dao.IQueryDao;
import com.eweblib.dbhelper.DataBaseQueryBuilder;

public class AmsInitUtil {

	public static void initMenu(IQueryDao dao) {
		createNoticeMenu(dao);
		createAttendanceMenu(dao);
		createDailyReportMenu(dao);
		createProjectMenu(dao);
		createCustomerMenu(dao);
		createReminderMenu(dao);
		createTaskMenu(dao);
		createUserMenu(dao);
		createSalaryMenu(dao);
		createPicMenu(dao);
		createSysMenu(dao);

	}

	public static void createNoticeMenu(IQueryDao dao) {
		Menu menu = new Menu();
		menu.setTitle("公告管理");
		menu.setStyle("height:150px;");
		menu.setMenuGroupId("adm_notice_management");
		menu.setDataOptions("iconCls:'icon-notice'");
		createOrUpdateMenu(menu, dao);

		MenuItem item = new MenuItem();
		item.setHref("?p=notice/add");
		item.setMenuId(menu.getId());
		item.setTitle("新增");
		item.setDisplayOrder(0);

		createOrUpdateMenuItem(item, dao);

		item = new MenuItem();
		item.setHref("?p=notice/list");
		item.setMenuId(menu.getId());
		item.setTitle("公告管理");
		item.setDisplayOrder(1);

		createOrUpdateMenuItem(item, dao);

	}

	public static void createAttendanceMenu(IQueryDao dao) {
		Menu menu = new Menu();
		menu.setTitle("考勤管理");
		menu.setStyle("height:150px;");
		menu.setMenuGroupId("adm_attendance_manage");
		menu.setDataOptions("iconCls:'icon-attendance'");
		createOrUpdateMenu(menu, dao);

		MenuItem item = new MenuItem();
		item.setHref("?p=attendance/list&a=1");
		item.setMenuId(menu.getId());
		item.setTitle("考勤管理");
		item.setDisplayOrder(0);

		createOrUpdateMenuItem(item, dao);

	}

	public static void createPicMenu(IQueryDao dao) {
		Menu menu = new Menu();
		menu.setTitle("图片管理");
		menu.setMenuGroupId("adm_pic_manage");
		menu.setStyle("height:150px;");
		menu.setDataOptions("iconCls:'icon-img'");
		createOrUpdateMenu(menu, dao);

		MenuItem item = new MenuItem();
		item.setHref("?p=pic/list&a=2");
		item.setMenuId(menu.getId());
		item.setTitle("图片管理");
		item.setDisplayOrder(0);

		createOrUpdateMenuItem(item, dao);

	}

	public static void createTaskMenu(IQueryDao dao) {
		Menu menu = new Menu();
		menu.setTitle("任务管理");
		menu.setMenuGroupId("adm_task_manage");
		menu.setStyle("height:150px;");
		menu.setDataOptions("iconCls:'icon-task'");
		createOrUpdateMenu(menu, dao);

		MenuItem item = new MenuItem();
		item.setHref("?p=task/import&a=3");
		item.setMenuId(menu.getId());
		item.setTitle("任务导入");
		item.setDisplayOrder(0);

		createOrUpdateMenuItem(item, dao);

		item = new MenuItem();
		item.setHref("?p=task/list&a=3");
		item.setMenuId(menu.getId());
		item.setTitle("任务管理");
		item.setDisplayOrder(1);

		createOrUpdateMenuItem(item, dao);

	}

	public static void createDailyReportMenu(IQueryDao dao) {
		Menu menu = new Menu();
		menu.setTitle("日报管理");
		menu.setMenuGroupId("adm_dailyreport_manage");
		menu.setStyle("height:150px;");
		menu.setDataOptions("iconCls:'icon-dailyreport'");
		createOrUpdateMenu(menu, dao);

		MenuItem item = new MenuItem();
		item.setHref("?p=report/list&a=4");
		item.setMenuId(menu.getId());
		item.setTitle("日报管理");
		item.setDisplayOrder(0);

		createOrUpdateMenuItem(item, dao);

	}

	public static void createReminderMenu(IQueryDao dao) {
		Menu menu = new Menu();
		menu.setTitle("备忘录管理");
		menu.setMenuGroupId("adm_reminder_management");
		menu.setStyle("height:150px;");
		menu.setDataOptions("iconCls:'icon-reminder'");
		createOrUpdateMenu(menu, dao);

		MenuItem item = new MenuItem();
		item.setHref("?p=reminder/list&a=6");
		item.setMenuId(menu.getId());
		item.setTitle("备忘录查询");
		item.setDisplayOrder(0);

		createOrUpdateMenuItem(item, dao);

	}

	public static void createProjectMenu(IQueryDao dao) {
		Menu menu = new Menu();
		menu.setTitle("项目管理");
		menu.setMenuGroupId("adm_project_manage");
		menu.setStyle("height:200px;");
		menu.setDataOptions("iconCls:'icon-project'");
		createOrUpdateMenu(menu, dao);

		MenuItem item = new MenuItem();
		item.setHref("?p=project/add&a=6");
		item.setMenuId(menu.getId());
		item.setTitle("新增项目");
		item.setDisplayOrder(0);

		createOrUpdateMenuItem(item, dao);

		item = new MenuItem();
		item.setHref("?p=project/list&a=6");
		item.setMenuId(menu.getId());
		item.setTitle("项目管理");
		item.setDisplayOrder(1);

		createOrUpdateMenuItem(item, dao);

		item = new MenuItem();
		item.setHref("?p=team/list&a=6");
		item.setMenuId(menu.getId());
		item.setTitle("施工队管理");
		item.setDisplayOrder(2);

		createOrUpdateMenuItem(item, dao);

	}

	public static void createCustomerMenu(IQueryDao dao) {
		Menu menu = new Menu();
		menu.setTitle("客户管理");
		menu.setMenuGroupId("adm_customer_manage");
		menu.setStyle("height:150px;");
		menu.setDataOptions("iconCls:'icon-customer'");
		createOrUpdateMenu(menu, dao);

		MenuItem item = new MenuItem();
		item.setHref("?p=customer/add&a=7");
		item.setMenuId(menu.getId());
		item.setTitle("新增客户");
		item.setDisplayOrder(0);

		item = new MenuItem();
		item.setHref("?p=customer/list&a=7");
		item.setMenuId(menu.getId());
		item.setTitle("客户管理");
		item.setDisplayOrder(1);

		createOrUpdateMenuItem(item, dao);

	}

	public static void createUserMenu(IQueryDao dao) {
		Menu menu = new Menu();
		menu.setTitle("用户管理");
		menu.setMenuGroupId("adm_user_manage");
		menu.setStyle("height:150px;");
		menu.setDataOptions("iconCls:'icon-user'");
		createOrUpdateMenu(menu, dao);

		MenuItem item = new MenuItem();
		item.setHref("?p=user/add&a=8");
		item.setMenuId(menu.getId());
		item.setTitle("新增用户");
		item.setDisplayOrder(0);

		createOrUpdateMenuItem(item, dao);

		item = new MenuItem();
		item.setHref("?p=user/list&a=8");
		item.setMenuId(menu.getId());
		item.setTitle("用户管理");
		item.setDisplayOrder(1);

		createOrUpdateMenuItem(item, dao);

	}

	public static void createSalaryMenu(IQueryDao dao) {
		Menu menu = new Menu();
		menu.setTitle("工资管理");
		menu.setMenuGroupId("adm_salary_manage");
		menu.setStyle("height:150px;");
		menu.setDataOptions("iconCls:'icon-salary'");
		createOrUpdateMenu(menu, dao);

		MenuItem item = new MenuItem();
		item.setHref("?p=salary/list&a=9");
		item.setMenuId(menu.getId());
		item.setTitle("工资管理");
		item.setDisplayOrder(0);

		createOrUpdateMenuItem(item, dao);

	}

	public static void createSysMenu(IQueryDao dao) {
		Menu menu = new Menu();
		menu.setTitle("系统设置");
		menu.setMenuGroupId("adm_sys_manage");
		menu.setStyle("height:300px;");
		menu.setDataOptions("iconCls:'icon-sys'");
		createOrUpdateMenu(menu, dao);

		MenuItem item = new MenuItem();
		item.setHref("?p=sys/usertypelist&a=10");
		item.setMenuId(menu.getId());
		item.setTitle("员工类型管理");
		item.setDisplayOrder(0);

		createOrUpdateMenuItem(item, dao);

		item = new MenuItem();
		item.setHref("?p=sys/userlevellist&a=10");
		item.setMenuId(menu.getId());
		item.setTitle("员工级别管理");
		item.setDisplayOrder(1);

		createOrUpdateMenuItem(item, dao);

		item = new MenuItem();
		item.setHref("?p=sys/grouplist&a=10");
		item.setMenuId(menu.getId());
		item.setTitle("角色管理");
		item.setDisplayOrder(2);

		createOrUpdateMenuItem(item, dao);

		item = new MenuItem();
		item.setHref("?p=department/list&a=10");
		item.setMenuId(menu.getId());
		item.setTitle("部门管理");
		item.setDisplayOrder(3);

		createOrUpdateMenuItem(item, dao);

		item = new MenuItem();
		item.setHref("?p=log/list&a=10");
		item.setMenuId(menu.getId());
		item.setTitle("日志查询");
		item.setDisplayOrder(4);

		createOrUpdateMenuItem(item, dao);

		item = new MenuItem();
		item.setHref("?p=sys/menu&a=10");
		item.setMenuId(menu.getId());
		item.setTitle("菜单顺序管理");
		item.setDisplayOrder(5);

		createOrUpdateMenuItem(item, dao);

	}

	public static void createOrUpdateMenuItem(MenuItem item, IQueryDao dao) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(MenuItem.TABLE_NAME);
		query.and(MenuItem.TITLE, item.getTitle());
		query.and(MenuItem.MENU_ID, item.getMenuId());

		MenuItem temp = (MenuItem) dao.findOneByQuery(query, MenuItem.class);

		if (temp != null) {

			item.setId(temp.getId());
			dao.updateById(item);
		} else {
			dao.insert(item);
		}

	}

	public static void createOrUpdateMenu(Menu menu, IQueryDao dao) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Menu.TABLE_NAME);
		query.and(Menu.MENU_GROUP_ID, menu.getMenuGroupId());

		Menu temp = (Menu) dao.findOneByQuery(query, Menu.class);

		if (temp != null) {

			menu.setId(temp.getId());
			dao.updateById(menu);
		} else {
			menu.setDisplayOrder(0);
			dao.insert(menu);
		}

	}

}
