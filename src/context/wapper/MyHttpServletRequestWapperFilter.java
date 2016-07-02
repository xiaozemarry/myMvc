package context.wapper;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class MyHttpServletRequestWapperFilter implements Filter {
	private static final Logger logger = Logger.getLogger(MyHttpServletRequestWapperFilter.class);
	private boolean isWapper = false;
	@Override
	public void destroy() {
		logger.info("destroy class of MyHttpServletRequestWapperFilter");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,FilterChain chain) throws IOException, ServletException {
		if(isWapper){
			final MyHttpServletRequestWapper myHttpServletRequestWapper = new MyHttpServletRequestWapper((HttpServletRequest) arg0);
			chain.doFilter(myHttpServletRequestWapper,arg1);
		}else{
			chain.doFilter(arg0, arg1);
		}
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("init class of MyHttpServletRequestWapperFilter");
		String wapper = filterConfig.getInitParameter("wapper");
		if(wapper!=null){
			wapper = StringUtils.trimToNull(wapper);
			if(wapper==null)return;
			Boolean re  = BooleanUtils.toBooleanObject(wapper);
			if(re==null)return;
			isWapper = re;
		}
		logger.info(String.format("init wappering HttpServletRequest value is [%s]",isWapper));
	}
}
