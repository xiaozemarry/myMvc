package context;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class MyHttpSession implements HttpSessionListener{
	/**
	 * 统计在线人数
	 */
	public static int sessionCount;
	private static final Logger logger = Logger.getLogger(MyHttpSession.class);
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		sessionCount+=1;
		HttpSession session = event.getSession();
		session.getAttribute("");
		logger.info("new session created:"+JSONObject.toJSONString(event.getSession()));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		sessionCount-=1;
		logger.info("session destroyed:"+JSONObject.toJSONString(event.getSession()));
	}

}
