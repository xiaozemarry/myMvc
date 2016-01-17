package Context;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import Tokens.TokenManager;
import Tools.HttpServletRequestTool;

//extends HandlerInterceptorAdapter
public class LoginFilter implements Filter,HandlerInterceptor{
	
	//private static Logger logger =  Logger.getLogger(LoginFilter.class);

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response=(HttpServletResponse)rep;
		 
		 TokenManager<?> token = TokenManager.instance();
		 String hasCookie = new HttpServletRequestTool((HttpServletRequest)req).hasLoginCookie();
		 boolean hasUser = token.hasUser(hasCookie);
		 boolean userDead = !token.userDead(hasCookie);
		 if(hasCookie!=null && hasUser && userDead)//存在cookie并且合法,继续操作
		 {
			chain.doFilter(req, rep);
			return;
		 }
		 
		 //if(UrlFilter.instance().containsUrl(request.getServletPath()))//不需要判断cookie的请求路径
		 final String basePath = new HttpServletRequestTool(request).getBasePath();
		 final String currentFullPath = request.getRequestURL().toString();
		 final String currentReqeustPath = currentFullPath.substring(basePath.length()-1,currentFullPath.length());
		 if(UrlFilter.instance().containsUrl(currentReqeustPath))//不需要判断cookie的请求路径
		 {
				chain.doFilter(req, rep);	
				return;
		 }
			 
		//用户已经失效,需重新登录
		 
		 String referer =  request.getHeader("referer");
         if(referer!=null)//记录当前连接信息,用户跳转到登录页面,如果登录成功,直接跳到当前请求的页面
         {
        	 String qs = request.getQueryString();
        	 if(qs!=null)referer+="?"+qs;
        	 request.setAttribute("prevLink",referer);
         }
         //地址栏不会改变
        request.getRequestDispatcher("/"+UrlFilter.instance().getPageRel().getString("loginPage")).forward(request, response);
        //response.sendError(SC_UNAUTHORIZED);401
		//chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
        System.out.println("init!!!!!");
	}
    //最后执行
	@Override
	public void afterCompletion(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3)throws Exception {
		System.out.println("intercepter finish!!!");
	}
    //第二次执行
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,Object arg2, ModelAndView arg3) throws Exception {
		System.out.println("postHandle!!!");
	}

	//首先执行
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse rep,Object arg2) throws Exception {
		 HttpServletRequest request = (HttpServletRequest) req;
		 HttpServletResponse response=(HttpServletResponse)rep;
		 
		 TokenManager<?> token = TokenManager.instance();
		 String hasCookie = new HttpServletRequestTool((HttpServletRequest)req).hasLoginCookie();
		 boolean hasUser = token.hasUser(hasCookie);
		 boolean userDead = !token.userDead(hasCookie);
		//存在cookie并且合法,继续操作
		 if(hasCookie!=null && hasUser && userDead)return true;
		 
		 //if(UrlFilter.instance().containsUrl(request.getServletPath()))//不需要判断cookie的请求路径
		 final String basePath = new HttpServletRequestTool(request).getBasePath();
		 final String currentFullPath = request.getRequestURL().toString();
		 final String currentReqeustPath = currentFullPath.substring(basePath.length()-1,currentFullPath.length());
		//不需要判断cookie的请求路径
		 if(UrlFilter.instance().containsUrl(currentReqeustPath))return true;
			 
		//用户已经失效,需重新登录
		 
		 String referer =  request.getHeader("referer");
         if(referer!=null)//记录当前连接信息,用户跳转到登录页面,如果登录成功,直接跳到当前请求的页面
         {
	    	 String qs = request.getQueryString();
	    	 if(qs!=null)referer+="?"+qs;
	    	 request.setAttribute("prevLink",referer);
         }
         //地址栏不会改变
        request.getRequestDispatcher("/"+UrlFilter.instance().getPageRel().getString("loginPage")).forward(request, response);
        //response.sendError(SC_UNAUTHORIZED);401
		//chain.doFilter(request, response);
		return true;
	}
}
