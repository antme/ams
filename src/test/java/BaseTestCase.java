import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ams.bean.Department;
import com.ams.service.IAttendanceService;
import com.ams.service.INoticeService;
import com.ams.service.IUserService;
import com.ams.service.impl.AttendanceServiceImpl;
import com.ams.service.impl.NoticeServiceImpl;
import com.ams.service.impl.UserServiceImpl;
import com.ams.util.InitUtil;
import com.eweblib.dao.IQueryDao;
import com.eweblib.dao.QueryDaoImpl;

public class BaseTestCase extends TestCase {
	private static Logger logger = LogManager.getLogger(BaseTestCase.class);

	protected static ApplicationContext ac;

	public IQueryDao dao;

	public INoticeService noticeService;

	private IAttendanceService ats;

	private IUserService userService;

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
		ats = ac.getBean(AttendanceServiceImpl.class);
		userService = ac.getBean(UserServiceImpl.class);
	}

	public void testEmpty() throws IOException, InterruptedException {

		InitUtil.initSystem(this.dao);

	}
}
