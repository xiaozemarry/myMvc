package module.login.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import base.Constant;
import base.HttpBase;
import context.UrlFilter;
import tokens.TokenManager;
import tools.HttpServletRequestTool;
import tools.StringTools;
@Controller
public class Login extends HttpBase{
	private static final Logger logger = Logger.getLogger(Login.class);
	 @RequestMapping(value="/loginUser",method=RequestMethod.POST)
	 public void loginIn(){
		 System.out.println(2212112);
		 System.out.println(this.druidDBConn);
//	        logger.error("Did it again!");   //error级别的信息，参数就是你输出的信息
//	        logger.info("我是info信息");    //info级别的信息
//	        logger.debug("我是debug信息");
//	        logger.warn("我是warn信息");
//	        logger.fatal("我是fatal信息");
//	        logger.log(Level.DEBUG, "我是debug信息");   //这个就是制定Level类型的调用：谁闲着没事调用这个，也不一定哦！
//		 final String sqla = "SELECT * FROM (SELECT ROWNUM rn,EVENTID,PICTUREID FROM MARKER_S )a WHERE 1=1 AND PICTUREID IS NOT NULL AND  a.RN<10000 ORDER BY PICTUREID";
//		 List list = null;
//		try {
//			list = db.searchToMapList(sqla);
//			System.out.println(db.getCustorm().toString());
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		 System.out.println(list.size());
//		 if(1==1)return;
//		 long beingTime = System.currentTimeMillis();
//		 int index = 0;
//		 for (int i = 0; i < 3; i++) 
//		 {
//			 //final int size = 10000;
//			 final int size = 10;
//			 Object[][] params = new Object[size][0];
//			 for(int k=0;k<size;k++)
//			 {
//				 params[k] = new Object[]{index,"f_ip"+index,"F_PROJECT"+index,k,k,k};
//				 index++;
//			 }
//			 final String sql = "insert into CAMERA1(f_id,F_IP,F_PROJECT,F_UOID,F_ENTERID,F_CARMERID) values (?,?,?,?,?,?)";	
//			 try {
//				db.batch(sql, params);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		 }
//		 System.out.println(db.getCustorm().toString());
//		 long beingEnd = System.currentTimeMillis();
//		 System.out.println("耗时(ms):"+(beingEnd-beingTime));
		//这里模拟数据库操作
		 String userName = request.getParameter("userName");
		 System.out.println("userName:"+userName);
		 String passWord = request.getParameter("passWord");
		 System.out.println("passWord:"+passWord);
		 boolean condition = userName!=null && passWord!=null && "admin".equals(userName) && "admin".equals(passWord);
		 if(!condition)
		 {			
			 try 
			 {
				 request.setAttribute("notice","非法用户!");
			 	 request.getRequestDispatcher("/login/blueBack/page/index.jsp").forward(request, response);
			 } catch (ServletException e) 
			 {
				e.printStackTrace();
			 } catch (IOException e) 
			 {
				e.printStackTrace();
			 }
			 return;
		 }
		 
		//登陆成功,设置token
		 String hasCookie = new HttpServletRequestTool(request).hasLoginCookie();
		 if(hasCookie!=null)
		 {
			 logger.info(Constant.SDFYMDHMS.format(new Date())+":移除登录用户池中的信息--->"+hasCookie);
			 TokenManager.instance().remove(hasCookie);
		 }
		 //多次登录,需要删除之前的
		 Map<String,String> userMap = new HashMap<String,String>(2);
		 userMap.put("userName","admin");
		 userMap.put("passWord","admin");
		 String id = TokenManager.instance().add(userMap);
		 Cookie cookie = new Cookie(Constant.COOKIENAME,id);
		 cookie.setMaxAge(Constant.MAXCOOKIEAGE);
		 //cookie.setHttpOnly(true);//只有在网络的情况下可以读取,js无法读取
		 
		 logger.info(Constant.SDFYMDHMS.format(new Date())+":设置登录用户池中的信息--->"+id);//+"\n"
		 response.addCookie(cookie);
		 try 
		 {
			 String referer = request.getParameter("referer");
			 boolean isIlle = StringTools.instance().isIllegalStr(referer);
			//response.setContentType("text/html");
			 if(isIlle)//response.getWriter().print("登录成功!<a href='template/index.html'>点我</a>");
			 {
				 final String homePage = UrlFilter.instance().getPageRel().getString("homePage");
				 response.sendRedirect(homePage);
			 }
			 else
				 response.sendRedirect(referer);//之前的连接页面
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	 }
	 
	 /**
	  * 注销登录
	  */
	 @RequestMapping("/loginOut")
	 public void loginOut(){
		 String hasCookie = new HttpServletRequestTool(request).hasLoginCookie();
		 if(hasCookie!=null)
		 {
			 logger.info(Constant.SDFYMDHMS.format(new Date())+":移除登录用户池中的信息--->"+hasCookie);
			 TokenManager.instance().remove(hasCookie);
		 }
		 
		 try 
		 {
			response.sendRedirect(UrlFilter.instance().getPageRel().getString("loginPage"));
		 } catch (IOException e) 
		 {
			e.printStackTrace();
		 }
	 }
}
