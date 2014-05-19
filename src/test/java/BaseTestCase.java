
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

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
			getMonthAndSalaryPerDay(list);
		}
		
		

	}

	public SalaryMonth getMonthAndSalaryPerDay(List<String[]> list) {
		for (int i = 0; i < list.size(); i++) {// 从第5行开始读数据

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
