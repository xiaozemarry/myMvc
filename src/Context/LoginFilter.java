package Context;

import java.io.IOException;
import java.util.Date;
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

import Base.Constant;
import Tokens.TokenManager;
import Tools.HttpServletRequestTool;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException, ServletException {
		
		 TokenManager token = TokenManager.instance();
		 String hasCookie = new HttpServletRequestTool((HttpServletRequest)req).hasLoginCookie();
		 boolean hasUser = token.hasUser(hasCookie);
		 boolean userDead = !token.userDead(hasCookie);
		 if(hasCookie!=null && hasUser && userDead)//存在cookie并且合法,继续操作
		 {
			chain.doFilter(req, rep);
			return;
		 }
		 
		//用户已经失效,需重新登录
		 HttpServletRequest request = (HttpServletRequest) req;
		 HttpServletResponse response = (HttpServletResponse) rep;
		
		 String referer =  request.getHeader("referer");
         if(referer!=null)//记录当前连接信息,用户跳转到登录页面,如果登录成功,直接跳到当前请求的页面
         {
        	 String qs = request.getQueryString();
        	 request.setAttribute("prenLink",referer+"?"+qs);
         }
         //地址栏不会改变
         request.getRequestDispatcher("/login/blueBack/page/index.jsp").forward(request, response);
        //response.sendError(SC_UNAUTHORIZED);401
		//chain.doFilter(request, response);
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
