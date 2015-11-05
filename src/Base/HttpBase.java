package Base;

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
}