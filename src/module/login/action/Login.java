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
		 try {
			System.out.println(db.searchToMapList("SELECT * FROM pipeinfo_s WHERE EVENTID = 'c2f2de9c-6c43-4322-b33b-f34b6d15c485'"));;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 String userName = request.getParameter("userName");
		 String passWord = request.getParameter("passWord");
		 
		 logger.info(String.format("logininfo--->userName:%s;passWord:%s", new Object[]{userName,passWord}));
		 
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
