package Base;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 控制器继承的基础类
 *
 */
public class HttpBase{
	  protected HttpServletRequest request;
	  protected HttpServletResponse response;
	  protected DBConn db = DBConn.instance();
	  @ModelAttribute
	  public void set(HttpServletRequest request,HttpServletResponse response){
		  this.request = request;
		  this.response = response;
	  }
	  
	  public void printStream(final File file){
		  
	  }
	  
	  public void printStream(final InputStream input){
		  
	  }  
	  
	  public void printStream(final byte bytes[]){
		  
	  }
	  
	  /**
	   * 先前台输出内容
	   * @param source 需要输出的内容
	   * @param strings 需要设置的头部信息(可不填)
	   */
	  public void printStr(String source,String...strings){
		  try 
		  {
			Writer writer = this.response.getWriter();
		    writer.write(source);
		    writer.close();
		  } catch (IOException e) 
		  {
				System.out.println("********************************************Attention:"+Constant.SDFYMDHMS.format(new Date())+"********************************************");
				e.printStackTrace();
				System.out.println("********************************************Attention********************************************");
		 }
	  }
}