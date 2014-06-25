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

import com.ams.bean.Salary;
import com.ams.bean.vo.SearchVo;
import com.ams.service.INoticeService;
import com.ams.service.ISystemService;
import com.ams.service.IUserService;
import com.ams.service.impl.NoticeServiceImpl;
import com.ams.service.impl.SystemServiceImpl;
import com.ams.service.impl.UserServiceImpl;
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

	
	public IUserService us;

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
		us = ac.getBean(UserServiceImpl.class);
	}

	public void testEmpty() throws IOException, InterruptedException {

		SearchVo vo = new SearchVo();
		vo.setUserId("44166a1a-54cc-4ce3-98e2-0d86bc9c5dcf");
		System.out.println(us.listUserForApp(vo).getEntityList());

	}
	
	
}
