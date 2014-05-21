import java.io.IOException;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ams.service.INoticeService;
import com.ams.service.impl.NoticeServiceImpl;
import com.eweblib.dao.IQueryDao;
import com.eweblib.dao.QueryDaoImpl;
import com.eweblib.util.DateUtil;

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

		System.out.println(DateUtil.getDate("2014年5月10日", "YYYY年MM月DD日"));
		// InputStream inputStream = new FileInputStream(new
		// File("/Users/ymzhou/Documents/task.xls"));

	}
}
