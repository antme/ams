import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.ams.bean.Pic;
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

		FileOutputStream fileOut = null;

		BufferedImage bufferImg = null;

		String[] columnHeaders = new String[] { "NAME", "FILE" };

		try {

			// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray

			// 创建一个工作薄

			HSSFWorkbook wb = new HSSFWorkbook();

			HSSFSheet sheet1 = wb.createSheet("poi picT");

			HSSFRow row = sheet1.createRow(0);
			int index = 0;
			for (String header : columnHeaders) {
				HSSFCell cell = row.createCell(index);
				cell.setCellValue(header);
				index++;
			}

			List<Pic> pics = userService.listPics(null).getEntityList();

			int rowIndex = 1;
			for (Pic pic : pics) {
				row = sheet1.createRow(rowIndex);

				row.createCell(0).setCellValue(pic.getId());
				;

				HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();

				HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 450, 122, (short) rowIndex, 1, (short) (rowIndex + 1), 2);

				// anchor1.setAnchorType(2);

				// 插入图片

				ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();

				bufferImg = ImageIO.read(new File("/Users/ymzhou/Documents/workspace/ams/src/main/webapp/" + pic.getPicUrl()));

				ImageIO.write(bufferImg, "png", byteArrayOut);

				patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
				rowIndex++;

			}

			fileOut = new FileOutputStream("/Users/ymzhou/Documents/workbook.xls");

			// 写入excel文件

			wb.write(fileOut);

			fileOut.close();

		} catch (IOException io) {

			io.printStackTrace();

			System.out.println("io erorr : " + io.getMessage());

		} finally

		{

			if (fileOut != null)

			{

				try {

					fileOut.close();

				}

				catch (IOException e)

				{

					// TODO Auto-generated catch block

					e.printStackTrace();

				}

			}

		}

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
