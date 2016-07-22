package utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.Validate;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

public class ActionUtils {
	/**
	 * 获取bean
	 * @param request 当前请求
	 * @param beanName 声明的bean的名称
	 * @return
	 */
	public static Object getBean(HttpServletRequest request,String beanName){
		//WebApplicationContextUtils.getWebApplicationContext(ServletContext sc)  
		ApplicationContext appContext = RequestContextUtils.getWebApplicationContext(request);//可能返回null
		Validate.notNull(appContext,"无法通过当前上下文获取ApplicationContext");
		return appContext.getBean(beanName);
	}
	
	
	
}

