package context;

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

import tokens.TokenManager;
import tools.HttpServletRequestTool;

public class ContextFilter extends HandlerInterceptorAdapter implements Filter{
	private static final Logger logger = Logger.getLogger(ContextFilter.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		final String currentReqeustPath = HttpServletRequestTool.getRequestURIPath(request);
		TokenManager<?> token = TokenManager.instance();
		String hasCookie = HttpServletRequestTool.hasLoginCookie(request);
		boolean hasUser = token.hasUser(hasCookie);
		boolean userDead = !token.userDead(hasCookie);
		boolean result = (hasCookie != null && hasUser && userDead) || (UrlFilter.instance().containsUrl(currentReqeustPath));
		if (result) {// 存在cookie并且合法,继续操作
			return true;
		}

		// 用户已经失效,需重新登录
		String referer = request.getHeader("referer");
		if (referer != null)// 记录当前连接信息,用户跳转到登录页面,如果登录成功,直接跳到当前请求的页面
		{
			String qs = request.getQueryString();
			if (qs != null){
				referer += "?" + qs;
			}
			request.setAttribute("prevLink", referer);
		}
		try {
			request.getRequestDispatcher("/" + UrlFilter.instance().getPageRel().getString("loginPage")).forward(request, response);
		} catch (ServletException e) {
			logger.error("forward-ServletException", e);
		} catch (IOException e) {
			logger.error("forward-IOException", e);
		}
		return false;
	}

	/**
	 * Spring的过滤器
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//do-nothing,just loging request for finding request path easier
		logger.info("request controller method:"+handler);
		//StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		//logger.info("at ("+ste.getFileName() + ":" + ste.getLineNumber()+")");
		super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(ex!=null){
			logger.error("process post ex",ex);
		}
		super.afterCompletion(request, response, handler, ex);
	}
	
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response= (HttpServletResponse) arg1;
		try {
			boolean result = this.preHandle(request, response, null);
			if(result)filterChain.doFilter(arg0, arg1);
		} catch (Exception e) {
			logger.error("use filter",e);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
 }
