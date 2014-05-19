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

		InputStream inputStream = new FileInputStream(new File("/Users/ymzhou/Documents/task.xls"));

		ExcleUtil excleUtil = new ExcleUtil(inputStream);
		List<String[]> list = excleUtil.getAllData(0);

		if (!list.isEmpty()) {

			for (String[] rows : list) {

				String row = "";
				for (int i = 0; i < rows.length; i++) {
					row = row + rows[i];
				}
				System.out.println(row);
			}

		}

	}

}
