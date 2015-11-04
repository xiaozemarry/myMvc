package Context;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Tokens.TokenManager;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse rep = (HttpServletResponse) response;
        Cookie cookies[] =  request.getCookies();
        String referer =  request.getHeader("referer");
        if(referer==null)
        {
        	//chain.doFilter(request, response);
        	//return;
        }
        System.out.println(referer);
        boolean hasUserInfo = false;
        for (int i = 0; i < cookies.length; i++) 
        {
			 Cookie eachCookie = cookies[i];
			 String name = eachCookie.getName();
			 if(!"userinfo".equals(name))continue;//判断是否登录
			 hasUserInfo = true;
			 String value = eachCookie.getValue();
			 if(!TokenManager.instance().getTokenMap().containsKey(value))//未登录,直接蹦到登录页面
			 {
				request = this.chainToLogin(request,response);
			 }
			 else
			 {
				
			 }
		}
        if(!hasUserInfo)request = this.chainToLogin(request,response);
        //rep.sendRedirect("/login/blueBack/page/index.html");
		chain.doFilter(request, response);
	}
	
	private HttpServletRequest chainToLogin(HttpServletRequest request, ServletResponse response){
		//获取当前的路径,登录成功之后直接跳到该路径
		String referer =  request.getHeader("referer");
		request.setAttribute("loginReferer",referer);
		//以后根据不同的公司跳转到不同的登录页面,打算基于配置做!目前写死
			//request.getRequestDispatcher("/login/blueBack/page/index.html").forward(request, response);
		//System.out.println(referer);
		return request;
	}
	

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	public static void main(String[] args) {
		// aa ab ac ad
		// ba bb bc bd
		// ca cb cc cd
		// da dc db da
		// aaa aab aac aad x4
		// aaa aaab aaac aaad x4
		System.out.println(Math.round(11.5));
		System.out.println(Math.round(-11.5));

		final String str[] = new String[] { "a", "b", "c", "d" };

	}

	public static Set<String> org(String str[], Set<String> set, int index) {
		if (index > str.length - 1)
			return set;
		for (int i = 0; i < str.length; i++) {

		}
		return set;
	}

	public class a extends Thread implements Runnable {
		public void run() {

		}
	}

}
