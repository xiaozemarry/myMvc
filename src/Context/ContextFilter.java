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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import Tokens.TokenManager;
import Tools.HttpServletRequestTool;

public class ContextFilter extends HandlerInterceptorAdapter implements Filter{
	private static final Logger logger = Logger.getLogger(ContextFilter.class);
	/**
	 * Spring的过滤器
	 */
    @Override
    public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
    	this.commonFilter(request, response, handler);
    }
	@Override
	public void destroy() {
		
	}
	/**
	 * servlet的过滤器
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse rep,FilterChain chain) throws IOException, ServletException {
		this.commonFilter(req, rep, chain);
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	public void commonFilter(ServletRequest req, ServletResponse rep,Object chain){
		 HttpServletRequest request = (HttpServletRequest) req;
		 HttpServletResponse response=(HttpServletResponse)rep;
		
  	     final String basePath = new HttpServletRequestTool(request).getBasePath();
		 final String currentFullPath = request.getRequestURL().toString();
		 final String currentReqeustPath = currentFullPath.substring(basePath.length()-1,currentFullPath.length());
		 
		 TokenManager<?> token = TokenManager.instance();
		 String hasCookie = new HttpServletRequestTool((HttpServletRequest)req).hasLoginCookie();
		 boolean hasUser = token.hasUser(hasCookie);
		 boolean userDead = !token.userDead(hasCookie);
		 boolean result = (hasCookie!=null && hasUser && userDead) || (UrlFilter.instance().containsUrl(currentReqeustPath));
		 if(result)//存在cookie并且合法,继续操作
		 {
			if(chain instanceof FilterChain)
			{
				try 
				{
					((FilterChain)chain).doFilter(req, rep);
					return;
				} catch (IOException e) 
				{
				   logger.error("IOException",e);
				} catch (ServletException e) 
				{
				   logger.error("ServletException",e);
				}
			}
			else 
			{
				return;
			}
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
        try 
        {
			request.getRequestDispatcher("/"+UrlFilter.instance().getPageRel().getString("loginPage")).forward(request,response);
		} catch (ServletException e) 
        {
			logger.error("forward-ServletException",e);
		} catch (IOException e) 
        {
			logger.error("forward-IOException",e);
		}
	}
}
