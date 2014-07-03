import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ams.bean.AmsBaseEntity;
import com.ams.bean.Project;
import com.ams.service.INoticeService;
import com.ams.service.IProjectService;
import com.ams.service.ISystemService;
import com.ams.service.IUserService;
import com.ams.service.impl.NoticeServiceImpl;
import com.ams.service.impl.ProjectServiceImpl;
import com.ams.service.impl.SystemServiceImpl;
import com.ams.service.impl.UserServiceImpl;
import com.eweblib.dao.IQueryDao;
import com.eweblib.dao.QueryDaoImpl;
import com.eweblib.util.EweblibUtil;

public class BaseTestCase extends TestCase {
	private static Logger logger = LogManager.getLogger(BaseTestCase.class);

	protected static ApplicationContext ac;

	public IQueryDao dao;

	public INoticeService noticeService;
	
	
	public ISystemService sys;

	
	public IUserService us;
	
	
	public IProjectService pservice;

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
		pservice = ac.getBean(ProjectServiceImpl.class);
		
	}

	public void testEmpty() throws IOException, InterruptedException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(AmsBaseEntity.DISPLAY_FOR_APP, 1);
		Project p = (Project) EweblibUtil.toEntity(map, Project.class);
		
		
		
		System.out.println(p);

	}
	
	
}
