import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ams.service.INoticeService;
import com.ams.service.ISystemService;
import com.ams.service.impl.NoticeServiceImpl;
import com.ams.service.impl.SystemServiceImpl;
import com.eweblib.dao.IQueryDao;
import com.eweblib.dao.QueryDaoImpl;
import com.eweblib.util.DateUtil;
import com.eweblib.util.EweblibUtil;

public class BaseTestCase extends TestCase {
	private static Logger logger = LogManager.getLogger(BaseTestCase.class);

	protected static ApplicationContext ac;

	public IQueryDao dao;

	public INoticeService noticeService;
	
	
	public ISystemService sys;


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
		sys = ac.getBean(SystemServiceImpl.class);
	}

	public void testEmpty() throws IOException, InterruptedException {

		
		InputStream is = new FileInputStream("/Users/ymzhou/Downloads/task.xls");
		
//		sys.importTask(is, new Task());
		
		System.out.println(getDate("2014年6月15日"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月DD日");
		
		System.out.println(sdf.format(new Date()));

	}
	
	
	private Date getDate(String date) {

		String year = date.substring(0, 4);
		String month = date.split("月")[0].split("年")[1];
		String day = date.split("月")[1].split("日")[0];

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, EweblibUtil.getInteger(year, 0));

		c.set(Calendar.MONTH, EweblibUtil.getInteger(month, 0) - 1);

		c.set(Calendar.DAY_OF_MONTH, EweblibUtil.getInteger(day, 0));

		return c.getTime();

	}
}
