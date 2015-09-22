package Context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import Base.Path;
import Tools.LoadSqlConfig;

public class ContextStart implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
      System.out.println("shut down!!!!!!");		
	}
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("CATALINE_BASE:"+Path.CATALINE_BASE);
		LoadSqlConfig.init(null);
		LoadSqlConfig.instance().getAllNodes();
	}
}