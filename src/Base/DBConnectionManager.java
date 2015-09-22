package Base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;

public class DBConnectionManager {

	static private DBConnectionManager instance; // 唯一实例的引用

	/*
	 * 得数数据库连接
	 */
	public synchronized Connection getConnection(DBCustorm custorm) {
		return getConnectionFromProxool(custorm);
	}

	//移除给定
	public void  removeConnectionPool(DBCustorm custorm) {

		String pooname=custorm.getPoolalias().toLowerCase();
		try 
		{
			ProxoolFacade.removeConnectionPool(pooname);
		} catch (ProxoolException e) 
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * 获得连接
	 * 
	 */
	private Connection getConnectionFromProxool(DBCustorm custorm){

		Connection conn = null;
		String JDBC_URL = "";
		String JDBC_DRIVER = "";
		//连接池
		String alias =custorm.getPoolalias().toLowerCase();
		//获取注册过的所有连接池
		String aliasAll[]=ProxoolFacade.getAliases();
		
		//是否注册果
		boolean Isregister=false;
		for(int i=0;i<aliasAll.length;i++)
		{
           if(aliasAll[i].equalsIgnoreCase(alias)) 
	    	   {
		    	   Isregister=true;
		    	   break;
	    	   }	  
		}
		
		if(!Isregister)
		{
			//如果没有注册则注册
			Properties info = new Properties();
			
			info.setProperty("user", custorm.getDbuser());
			info.setProperty("password", custorm.getDbpwd());
			//启动后申请连接数
			info.setProperty("proxool.minimum-connection-count", "1");
			//追加申请数量
			info.setProperty("proxool.prototype-count", "5");
			//最大链接数量
			info.setProperty("proxool.maximum-connection-count", "50");
			//调试SQL运行时间
			info.setProperty("proxool.trace", "true");
		
			info.setProperty("proxool.test-before-use", "true");
			info.setProperty("proxool.test-after-use", "true");
			//info.setProperty("proxool.maximum-active-time", "4000000");
		
			if ("MSSQL".equals(custorm.getDBType())) 
			{
				JDBC_URL = "jdbc:sqlserver://" + custorm.getDBUrl() + ":"+ custorm.getDBPort() + ";databaseName="+ custorm.getDBName();
				JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				info.setProperty("proxool.house-keeping-test-sql", "SELECT  CURRENT_USER");
			}
			else if ("ORA".equals(custorm.getDBType())) 
			{
				//JDBC_URL = "jdbc:oracle:thin:@" + custorm.getDBUrl() + ":"+ custorm.getDBPort() + ":" + custorm.getDBName();//低版本的oracle链接时需要使用冒号
				JDBC_URL = "jdbc:oracle:thin:@" + custorm.getDBUrl() + ":"+ custorm.getDBPort() + "/" + custorm.getDBName();//高版本的需要斜杠
				JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
				info.setProperty("proxool.house-keeping-test-sql", "SELECT SYSDATE FROM DUAL");
			}
			else if ("MYSQL".equals(custorm.getDBType())) 
			{
				JDBC_URL = "jdbc:mysql://" + custorm.getDBUrl() + ":"+ custorm.getDBPort() + "/"+ custorm.getDBName();
				JDBC_DRIVER = "com.mysql.jdbc.Driver";
				info.setProperty("proxool.house-keeping-test-sql", "SELECT  CURRENT_USER");
			}
			else 
			{
				
			}
			try {
				Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
			} catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}

			String url = "proxool." + alias + ":" + JDBC_DRIVER + ":" + JDBC_URL;
			try 
			{
				ProxoolFacade.registerConnectionPool(url, info);
			} catch (ProxoolException e) 
			{
				e.printStackTrace();
			}
			
		}
		//如果已注册则直接从池中获取链接
		 try
		 {
			conn = DriverManager.getConnection("proxool." + alias );
		 } catch (SQLException e)
		 {
			e.printStackTrace();
		 }
		 return conn;
	}

	/*
	 * 释放使用完的连接
	 */
	public void freeConnection(DBCustorm custorm, Connection conn) {
		  
		try 
		{
			if(conn!=null)conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static synchronized DBConnectionManager getInstance() {

		if (instance == null) 
			instance = new DBConnectionManager();
		return instance;
	}

	public void release() {
		  instance = null;
	}

}
