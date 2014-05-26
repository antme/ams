import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ams.bean.Attendance;
import com.ams.service.IAttendanceService;
import com.ams.service.INoticeService;
import com.ams.service.impl.AttendanceServiceImpl;
import com.ams.service.impl.NoticeServiceImpl;
import com.eweblib.dao.IQueryDao;
import com.eweblib.dao.QueryDaoImpl;
import com.eweblib.util.DateUtil;
import com.eweblib.util.ExcelTemplateUtil;

public class BaseTestCase extends TestCase {
	private static Logger logger = LogManager.getLogger(BaseTestCase.class);

	protected static ApplicationContext ac;

	public IQueryDao dao;

	public INoticeService noticeService;

	private IAttendanceService ats;

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
	}

	public void testEmpty() throws IOException, InterruptedException {

		System.out.println(DateUtil.getDate("2014年5月10日", "YYYY年MM月DD日"));
		// InputStream inputStream = new FileInputStream(new
		// File("/Users/ymzhou/Documents/task.xls"));

		ExcelTemplateUtil etu = new ExcelTemplateUtil();
		etu.setSrcPath("/Users/ymzhou/Documents/a.xls");
		etu.setDesPath("/Users/ymzhou/Documents/a1.xls");
		etu.setSheetName("考勤表");

		etu.getSheet();
		etu.setCellStrValue(0, 2, "2014");
		etu.setCellStrValue(0, 6, "7");
		List<Attendance> atss = ats.listAttendances(null).getEntityList();
		int start = 6;
		for (Attendance at : atss) {

			if (at.getAttendanceDayType() == 0) {
				etu.setCellStrValue(start, 0, at.getUserName());
				etu.setCellStrValue(start, 2, "●");
				etu.setCellStrValue(start, 3, "●");
				etu.setCellStrValue(start, 4, "●");
				etu.setCellStrValue(start, 5, "●");
				etu.setCellStrValue(start, 6, "△");
			} else {
				etu.setCellStrValue(start + 1, 0, at.getUserName());
				etu.setCellStrValue(start + 1, 2, "●");
				etu.setCellStrValue(start + 1, 3, "●");
				etu.setCellStrValue(start + 1, 4, "●");
				etu.setCellStrValue(start + 1, 5, "●");
				etu.setCellStrValue(start + 1, 6, "△");
			}

			start = start + 2;

		}
		etu.exportToNewFile();

	}
}
