package context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import base.Path;
import tokens.TokenManager;
import tools.LoadSqlConfig;

public class ContextStart implements ServletContextListener{
	
	private final static Logger logger = Logger.getLogger(ContextStart.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("shut down!!!!!!");	
		logger.info("关闭时刷新缓存文件............");
		TokenManager.instance().refresh();
        TokenManager.instance().writeingFile();
	}
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("CATALINE_BASE:"+Path.CATALINE_BASE);
		LoadSqlConfig.init(null);
		LoadSqlConfig.instance().getAllNodes();
		
		UrlFilter.instance().start();
		
		logger.info("开始装载用户登录信息的缓存文件......");
	    TokenManager token = TokenManager.instance();
	    token.loadingFile();
	    logger.info("开始刷新缓存文件(即刷新无效的登陆信息)....");
		token.refresh();
	}
}