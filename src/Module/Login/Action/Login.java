package Module.Login.Action;

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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import Base.Constant;
import Base.HttpBase;
import Context.UrlFilter;
import Tokens.TokenManager;
import Tools.HttpServletRequestTool;
import Tools.StringTools;
@Controller
public class Login extends HttpBase{
	
	//@RequestParam String userName,@RequestParam String passWord
	 @RequestMapping(value="login",method={RequestMethod.POST,RequestMethod.GET})
	 public void login(){
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
		 String passWord = request.getParameter("passWord");
		 if(userName!=null && passWord!=null && "admin".equals(userName) && "admin".equals(passWord))
		 {
			 final String homePage = UrlFilter.instance().getPageRel().getString("homePage");
			 try {
				response.sendRedirect(homePage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		 else
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
			 System.out.println(Constant.SDFYMDHMS.format(new Date())+":移除登录用户池中的信息--->"+hasCookie);
			 TokenManager.instance().remove(hasCookie);
		 }
		 //多次登录,需要删除之前的
		 Map<String,String> userMap = new HashMap<String,String>(2);
		 userMap.put("userName","admin");
		 userMap.put("passWord","admin");
		 String id = TokenManager.instance().add(userMap);
		 Cookie cookie = new Cookie(Constant.COOKIENAME,id);
		 cookie.setMaxAge(Constant.MAXCOOKIEAGE);
		 cookie.setHttpOnly(true);//只有在网络的情况下可以读取,js无法读取
		 
		 System.out.println(Constant.SDFYMDHMS.format(new Date())+":设置登录用户池中的信息--->"+id+"\n");
		 response.addCookie(cookie);
		 try 
		 {
			 String referer = request.getParameter("referer");
			 boolean isIlle = StringTools.instance().isIllegalStr(referer);
			 response.setContentType("text/html");
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
	 @RequestMapping("/loginOut.do")
	 public void loginOut(){
		 String hasCookie = new HttpServletRequestTool(request).hasLoginCookie();
		 if(hasCookie!=null)
		 {
			 System.out.println(Constant.SDFYMDHMS.format(new Date())+":移除登录用户池中的信息--->"+hasCookie);
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
