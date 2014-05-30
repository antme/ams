import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ams.bean.Attendance;
import com.ams.bean.Department;
import com.ams.bean.Pic;
import com.ams.bean.User;
import com.ams.service.IAttendanceService;
import com.ams.service.INoticeService;
import com.ams.service.IUserService;
import com.ams.service.impl.AttendanceServiceImpl;
import com.ams.service.impl.NoticeServiceImpl;
import com.ams.service.impl.UserServiceImpl;
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

	public void testExcel() {
	}

	public void testEmpty() throws IOException, InterruptedException {
		List<Department> list = new ArrayList<Department>();

		Department dep = new Department();
		dep.setDepartmentName("dylan1");
		list.add(dep);

		dep = new Department();
		dep.setDepartmentName("dylan2");

		list.add(dep);

		dep = new Department();
		dep.setDepartmentName("dylan3");

		list.add(dep);
		dao.batchInsert(list);
	}
}
