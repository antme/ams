import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

;

public class TestPOI {

	public static void main(String[] args) {

		FileOutputStream fileOut = null;

		BufferedImage bufferImg = null;

		BufferedImage bufferImg1 = null;
		String[] columnHeaders = new String[]{"NAME", "FILE"};
		

		try {

			// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray

			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();

			ByteArrayOutputStream byteArrayOut1 = new ByteArrayOutputStream();

			bufferImg = ImageIO.read(new File("/Users/ymzhou/Documents/Add.png"));

			bufferImg1 = ImageIO.read(new File("/Users/ymzhou/Documents/Add.png"));

			ImageIO.write(bufferImg, "png", byteArrayOut);

			ImageIO.write(bufferImg1, "png", byteArrayOut1);

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

			HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();

			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 450, 122, (short) 1, 1, (short) 2, 2);

			HSSFClientAnchor anchor1 = new HSSFClientAnchor(0, 0, 450, 122, (short) 2, 30, (short) 3, 31);

			anchor1.setAnchorType(2);

			// 插入图片

			patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

			patriarch.createPicture(anchor1, wb.addPicture(byteArrayOut1.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

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


					e.printStackTrace();

				}

			}

		}

	}

}
