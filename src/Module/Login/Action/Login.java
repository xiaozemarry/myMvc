package Module.Login.Action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Base.Constant;
import Base.HttpBase;
import Tokens.TokenManager;
import Tools.HttpServletRequestTool;
import Tools.StringTools;
@Controller
public class Login extends HttpBase{
	
	 @RequestMapping("/login.do")
	 public void login(){
		//这里模拟数据库操作,且用户登录成功
		//登陆成功,设置token
		 String hasCookie = new HttpServletRequestTool(request).hasLoginCookie();
		 if(hasCookie!=null)
		 {
			 System.out.println(Constant.SDFYMDHMS.format(new Date())+":移除登录用户池中的信息--->"+hasCookie);
			 TokenManager.instance().remove(hasCookie);
		 }
		 else
		 {
			 
		 }
		 
		 if(1==1)//登录失败
		 {
			 
		 }
		 //多次登录,需要删除之前的
		 Map<String,String> userMap = new HashMap<String, String>(2);
		 userMap.put("userName","admin");
		 userMap.put("passWord","admin");
		 String id = TokenManager.instance().add(userMap);
		 Cookie cookie = new Cookie(Constant.COOKIENAME,id);
		 cookie.setMaxAge(Constant.MAXCOOKIEAGE);
		 cookie.setHttpOnly(true);//只有在网络的情况下可以读取,js无法读取
		 
		 System.out.println(Constant.SDFYMDHMS.format(new Date())+":设置登录用户池中的信息--->"+id+"\n");
		 response.addCookie(cookie);
		 try {
			 String referer = request.getParameter("referer");
			 boolean isIlle = StringTools.instance().isIllegalStr(referer);
			 response.setContentType("text/html");
			 if(isIlle)
				 response.getWriter().print("登录成功!<a href='template/index.html'>点我</a>");
			 else
				 response.sendRedirect(referer);//之前的连接页面
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }

	private Object instance() {
		// TODO Auto-generated method stub
		return null;
	}
    
/*	*//**
	 * 供系统启动的时候调用 默认加载 再访问的时候不用从新加载
	 *//*
	@RenderMapping(value="contextStart.do")
	public void ContextStartRequst(){
   		
	}*/
}
