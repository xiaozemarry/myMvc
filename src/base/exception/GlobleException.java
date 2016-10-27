package base.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

public class GlobleException implements HandlerExceptionResolver {
	
	private final static Logger logger = Logger.getLogger(GlobleException.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj,
			Exception ex) {
		logger.error("catch globle exception", ex);
		return null;
	}
	
	public static void main(String[] args) {
		print();
	}
	
	public static void print(){
		try {
			log(GlobleException.class);
			
			String a = null;
		} catch (Exception e) {
			//printStackTrace(e);
		}
	}
	
	private static void printStackTrace(Throwable e){
		StackTraceElement[] stacks = e.getStackTrace();
		for (int i = 0; i < stacks.length; i++) {
			StackTraceElement item = stacks[i];
			System.out.println(JSONObject.toJSONString(item));
		}
	}
	
	private static void printStackTrace(StackTraceElement[] stacks){
		for (int i = 0; i < stacks.length; i++) {
			StackTraceElement item = stacks[i];
			System.out.println(JSONObject.toJSONString(item));
		}
	}
	
	private static void log(Class<?> clazz){
		printStackTrace(Thread.currentThread().getStackTrace());
	}
}
