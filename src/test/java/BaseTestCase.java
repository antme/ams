
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ams.bean.User;
import com.ams.service.INoticeService;
import com.ams.service.impl.NoticeServiceImpl;
import com.eweblib.dao.IQueryDao;
import com.eweblib.dao.QueryDaoImpl;

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
		
//		Notice notice = new Notice();
//		notice.setTitle("test");
////		this.dao.insert(notice);
//		
//		
//		System.out.println(noticeService.listNoticesForApp().getEntityList());;
		
		try {
	        System.out.println(User.class.getField("userName").getType().getName());
        } catch (NoSuchFieldException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (SecurityException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }

//		Field[] fields = User.class.getFields();
//		for(Field field: fields){
//			System.out.println(field.getType().getName());
//			System.out.println(field.getGenericType());
//		}
//		
		
		
	}

}
