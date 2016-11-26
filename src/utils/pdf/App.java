package utils.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;


public class App {
	public static void main(String[] args) {
		try {
			//copy one
			copyFileUsingFileChannels(new File("c:\\a\\银国达佛山1号_估值表_bak.pdf"),new File("c:\\a\\银国达佛山1号_估值表.pdf"));
			//real set
			LogoBean logoBean = new LogoBean("c:\\a\\aaa.png", "c:\\a\\外包服务章.png");
			addPdfMark("c:\\a\\银国达佛山1号_估值表.pdf", "c:\\a\\bbbbbb.pdf", "c:\\a\\外包服务章.png",logoBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addPdfMark(String InPdfFile, String outPdfFile, String markImagePath,LogoBean logoBean)
			throws Exception {
		PdfReader reader = new PdfReader(InPdfFile, "PDF".getBytes());
		final FileOutputStream out = new FileOutputStream(outPdfFile);
		PdfStamper stamp = new PdfStamper(reader, out);
		final int pageSize = reader.getNumberOfPages();
		
		for (int i = 1; i <= pageSize; i++) {
			Rectangle rectangle =  reader.getPageSize(i);
			PdfContentByte imgPos = stamp.getOverContent(i);
			
			setIcon(rectangle,imgPos,logoBean.getCompanyLogoPath(),true);
			setIcon(rectangle,imgPos,logoBean.getCompanyStampPath(),false);
		}
		stamp.close();// 关闭
		File tempfile = new File(InPdfFile);
		if (tempfile.exists()) {
			tempfile.delete();
		}
	}
	
	public static void setIcon(Rectangle rectangle,PdfContentByte imgPos,final String iconPath,boolean leftAbsPostion) throws MalformedURLException, IOException, DocumentException{
        final float width = rectangle.getWidth();
        final float height =rectangle.getHeight();
        final Image img = Image.getInstance(iconPath);// 插入水印
		img.scaleToFit(100, 100);//设置图片的宽高
		if(leftAbsPostion){
			img.setAbsolutePosition(0, height-50);//设置在左上角
		}else{
			img.setAbsolutePosition(width-110, height-100);//设置在右上角
		}
		
		imgPos.addImage(img);
	}  
	
	private static void copyFileUsingFileChannels(File source, File dest) throws IOException {    
        FileChannel inputChannel = null;    
        FileChannel outputChannel = null;    
    try {
        inputChannel = new FileInputStream(source).getChannel();
        outputChannel = new FileOutputStream(dest).getChannel();
        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
    } finally {
        inputChannel.close();
        outputChannel.close();
    }
}
}
