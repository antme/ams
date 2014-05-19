import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ams.bean.DeductedSalaryItem;
import com.ams.bean.SalaryItem;
import com.ams.bean.vo.SalaryMonth;
import com.ams.service.INoticeService;
import com.ams.service.impl.NoticeServiceImpl;
import com.eweblib.dao.IQueryDao;
import com.eweblib.dao.QueryDaoImpl;
import com.eweblib.util.EweblibUtil;
import com.eweblib.util.ExcleUtil;

public class BaseTestCase extends TestCase {
	private static Logger logger = LogManager.getLogger(BaseTestCase.class);

	protected static ApplicationContext ac;

	public IQueryDao dao;

	public INoticeService noticeService;

	public IQueryDao getDao() {
		return dao;
	}

	public void setDao(IQueryDao dao) {
		this.dao = dao;
	}

	public BaseTestCase() {

		if (ac == null) {
			ac = new FileSystemXmlApplicationContext("/src/main/WEB-INF/application.xml");
		}
		dao = ac.getBean(QueryDaoImpl.class);
		noticeService = ac.getBean(NoticeServiceImpl.class);
	}

	public void testEmpty() throws IOException, InterruptedException {

		InputStream inputStream = new FileInputStream(new File("/Users/ymzhou/Documents/salary.xls"));

		ExcleUtil excleUtil = new ExcleUtil(inputStream);
		List<String[]> list = excleUtil.getAllData(0);

		if (!list.isEmpty()) {
			// getMonthAndSalaryPerDay(list);

			// getPaySalary(list);

			getDeductedSalary(list);

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
		for (int i = 0; i < list.size(); i++) {

			String[] rows = list.get(i);

			int monthSearch = 0;
			int month = 0;
			int salaryPerDay = 0;

			for (String row : rows) {

				if (EweblibUtil.isValid(row)) {
					if (row.contains("结算单")) {
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
				SalaryMonth sm = new SalaryMonth();
				sm.setMonth(month);
				sm.setSalaryPerDay(salaryPerDay);
				sm.setRowNumber(i);
				return sm;
			}

		}
		return null;

	}

}
