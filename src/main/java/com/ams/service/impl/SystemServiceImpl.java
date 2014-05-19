package com.ams.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ams.bean.DeductedSalaryItem;
import com.ams.bean.Salary;
import com.ams.bean.SalaryItem;
import com.ams.bean.User;
import com.ams.bean.vo.SalaryMonth;
import com.ams.service.ISystemService;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.exception.ResponseException;
import com.eweblib.service.AbstractService;
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
			String userName;
			boolean findUserName = false;
			for (String row : rows) {

				if (row.contains("应付款")) {
					payStartIndex = i;
					findUserName = true;
				}
				if (EweblibUtil.isValid(row) && !findUserName) {
					userName = row.trim();
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
				if (EweblibUtil.isValid(rows[4])) {
					item.setAttendanceDays(Double.parseDouble(rows[4]));
				}

				if (EweblibUtil.isValid(rows[5])) {
					item.setTotolSalary(Double.parseDouble(rows[5]));
				}
				if (EweblibUtil.isValid(rows[6])) {
					item.setPerformanceSalary(Double.parseDouble(rows[6]));
				}
				if (EweblibUtil.isValid(rows[7])) {
					item.setPerformanceSalaryUnit(Double.parseDouble(rows[7]));
				}

				item.setComment(rows[8]);
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
