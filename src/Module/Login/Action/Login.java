package Module.Login.Action;

import java.io.IOException;

import javax.servlet.http.Cookie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import bean.User;

import Tokens.TokenManager;

import Base.Base;
import Base.Constant;
@Controller
public class Login extends Base{
	
	 @RequestMapping("/login.do")
	 public void login(){
		 System.out.println("login");
		 //登陆成功,设置token
		 User user = new User();
		 user.setName("张三");
		 user.setAge(50);
		 
		 //多次登录,需要删除之前的
		 
		 String id = TokenManager.instance().add(user);
		 Cookie cookie = new Cookie("userinfo",id);
		 cookie.setMaxAge(Constant.MAXCOOKIEAGE);
		 cookie.setHttpOnly(true);//只有在网络的情况下可以读取,js无法读取
		 
		 response.addCookie(cookie);
		 try {
			response.getWriter().print("aaaaaa");
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
