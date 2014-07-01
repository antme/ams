import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ams.bean.Project;
import com.ams.bean.vo.DailyReportVo;
import com.ams.service.INoticeService;
import com.ams.service.IProjectService;
import com.ams.service.ISystemService;
import com.ams.service.IUserService;
import com.ams.service.impl.NoticeServiceImpl;
import com.ams.service.impl.ProjectServiceImpl;
import com.ams.service.impl.SystemServiceImpl;
import com.ams.service.impl.UserServiceImpl;
import com.eweblib.bean.EntityResults;
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
		DailyReportVo report = new DailyReportVo();
		report.setUserId("05c07bcc-833e-4b22-a8be-3c3a63609ac8");
		
		
	
		EntityResults<DailyReportVo> results = pservice.listDailyReport(report,true);
		
		
		Map<String, Object> list = new HashMap<String, Object>();
		list.put("total", results.getPagnation().getTotal());

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		for (DailyReportVo dr : results.getEntityList()) {

			Map<String, Object> map = dr.toMap();

			Map<String, Object> projectMap = dr.getTaskInfo().toMap();

			if (projectMap.get(Project.PROJECT_START_DATE) == null) {
				projectMap.put(Project.PROJECT_START_DATE, "");
			}
			if (projectMap.get(Project.PROJECT_END_DATE) == null) {
				projectMap.put(Project.PROJECT_END_DATE, "");
			}

			map.put("taskInfo", projectMap);
			mapList.add(map);

		}
		list.put("rows", mapList);

		
		
		System.out.println(EweblibUtil.toJson(list));

	}
	
	
}
