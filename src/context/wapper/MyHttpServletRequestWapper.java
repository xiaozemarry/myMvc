package context.wapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class MyHttpServletRequestWapper extends HttpServletRequestWrapper{
    private static final Logger logger = Logger.getLogger(MyHttpServletRequestWapper.class);
	public MyHttpServletRequestWapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		logger.debug(String.format("try to resolve reqeust-parameter of [%s]", name));
		String realVal = super.getParameter(name);
		if(realVal==null){
			logger.debug(String.format("resolving reqeust-parameter of [%s],but value is null", name));
			return name;
		}
		String newVal  = StringUtils.trimToNull(realVal);
		logger.debug(String.format("resolving reqeust-parameter of [%s],source value is [%s],resolve value is [%s]", new Object[]{name,realVal,newVal}));
		return newVal;
	}
}
