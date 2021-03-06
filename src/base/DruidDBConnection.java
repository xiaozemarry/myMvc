/*
 * Copyright (C) The Home Software Co,.Ltd. All rights reserved.
 *
 * DBConn.java, Created on 2005-5-4
 *
 */
package base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.alibaba.druid.pool.DruidDataSource;

import base.bean.BaseDruidConnConfig;
import utils.DBUtils;


public class DruidDBConnection{
	public DruidDBConnection() {
	}
	private static final Map<String,DataSource> myDataSource = new HashMap<String, DataSource>();
	private static final Logger logger = Logger.getLogger(DruidDBConnection.class);
	private static DruidDataSource  druidDataSource;
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>(){
		protected Connection initialValue(){
			final String threadName = Thread.currentThread().getName();
			logger.info(threadName+"-->get connection from threadlocal...");
			if(druidDataSource!=null) {
				/**
				 * There is one important difference: dataSource.getConnection()
				 * always returns a new connection obtained from the dataSource
				 * or connection pool. DataSourceUtils.getConnection() checks if
				 * there is an active transaction for the current thread. If
				 * there is one, it will return connection with this
				 * transaction. If there is none it will behave exactly the same
				 * way as dataSource.getConnection().
				 * 
				 * You need to be careful when using
				 * DataSourceUtils.getConnection(). If it returns connection for
				 * the active transaction it means someone else will be closing
				 * it since it's the responsibility of whoever opened the
				 * transaction. On the other hand, if it returns a brand new
				 * connection from the dataSource it's you who should
				 * commit/rollback/close it.
				 */
				 Connection con = DataSourceUtils.getConnection(druidDataSource);
				 logger.info(threadName+"-->get connection from threadlocal-->result:"+con);
				 return con;
			}
			logger.error(threadName+"-->plan to get connection from threadlocal,but datasouce is null");
			return null;
		};
	};
	
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public  DruidDataSource getDruidDataSource() {
		return druidDataSource;
	}
	
	@Resource(name="dataSource")
	public  void setDruidDataSource(DruidDataSource druidDataSource) {
	    DruidDBConnection.druidDataSource = druidDataSource;
	}
    
	public void closeConnection(Connection connection) {
		DataSourceUtils.releaseConnection(connection,druidDataSource);
	    threadLocal.remove();//必须remove
	}

	public Connection getDefaultConnection(){
		Connection conn = threadLocal.get();
		Validate.notNull(conn);
		return conn;
	}
	
	/**
	 * 根据名字获取连接
	 * @param name
	 * @return if(name==null)return 默认的连接</br>if(name!=null)返回对应的连接,maybe null;
	 */
	public Connection getConnectionByBean(HttpServletRequest request,String beanName){
		//
		return null;
	}
	
	public DataSource getDataSourceFromBean(BaseDruidConnConfig config) throws NullPointerException{
		Validate.notNull(config,"参数实例config不能为空");
		if(!config.useful()){
			logger.error("非法对象试图获取数据源:"+config);
			throw new NullPointerException("非法对象试图获取数据源");
		}
		DataSource cache = myDataSource.get(config.toString());
		if(cache==null){
			DruidDataSource clone = this.getDruidDataSource().cloneDruidDataSource();
			clone.setUrl(config.getUrl());
			clone.setUsername(config.getUserName());
			clone.setPassword(config.getPassWord());
			myDataSource.put(config.toString(),clone);
			logger.info("cache datasource:"+config);
			return clone;
		}else{
			logger.info("using cache datasource");
		}
		logger.info("对象试图获取数据源,并且找到该数据源,配置信息:"+config);
		return cache;
	}
	
	public Connection getConnectionFromBean(BaseDruidConnConfig config) throws SQLException{
		DataSource ds = this.getDataSourceFromBean(config);
		return ds.getConnection();
	}
	
	public Connection getConnection(){
		return getDefaultConnection();
	}
	
	public Connection getConnection1() {
		try {
			return druidDataSource.getConnection();
		} catch (SQLException e) {
			logger.error("[ { when get connection } ]",e);
		}
		try {
			return druidDataSource.tryGetConnection();
		} catch (SQLException e) {
			logger.error("[ { when tryGet connection } ]",e);
		}
		return null;
	}
	/**
	 * Execute an SQL SELECT query without any replacement parameters and
	 * Convert the first row of the ResultSet into a bean with the Class given
	 * in the parameter.
	 * 
	 * Usage Demo:
	 * 
	 * <pre>
	 * String sql = &quot;SELECT * FROM test&quot;;
	 * Test test = ( Test ) searchToBean( Test.class, sql );
	 * if ( test != null ) {
	 * 
	 * }
	 * </pre>
	 * 
	 * @param type
	 *            The Class of beans.
	 * @param sql
	 *            The SQL to execute.
	 * @return An initialized JavaBean or null if there were no rows in the
	 *         ResultSet.
	 */
	public Object searchToBeanOfSingle(String sql,Class type ) throws Exception {
		Connection userConn = getConnection();
		Object result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new BeanHandler( type );
			result = run.query( userConn, sql, h );
		}
		catch ( Exception _e ) {
			throw _e;
		}
		finally {
			closeConnection(userConn);
		}
		return result;
	}

	/**
	 * Execute an SQL SELECT query without any replacement parameters and
	 * Convert the first row of the ResultSet into a bean with the Class given
	 * in the parameter.
	 * 
	 * Usage Demo:
	 * 
	 * <pre>
	 * String sql = &quot;SELECT * FROM test&quot;;
	 * Test test = ( Test ) searchToBean( Test.class, sql );
	 * if ( test != null ) {
	 * 
	 * }
	 * </pre>
	 * 
	 * @param type
	 *            The Class of beans.
	 * @param sql
	 *            The SQL to execute.
	 * @return An initialized JavaBean or null if there were no rows in the
	 *         ResultSet.
	 */
	public Object searchToBeanOfSingle( Class type, String sql ) throws Exception {
		Connection userConn = getConnection();
		Object result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new BeanHandler( type );
			result = run.query( userConn, sql, h );
		}
		catch ( Exception _e ) {
			throw _e;
		}
		finally {
			closeConnection(userConn);
		}
		return result;
	}


	/**
	 * Executes the given SELECT SQL with a single replacement parameter and
	 * Convert the first row of the ResultSet into a bean with the Class given
	 * in the parameter.
	 * 
	 * @param type
	 *            The Class of beans.
	 * @param sql
	 *            The SQL to execute.
	 * @param param
	 *            The replacement parameter.
	 * @return An initialized JavaBean or null if there were no rows in the
	 *         ResultSet.
	 */
	public Object searchToBeanOfSingle( Class type, String sql, Object param ) throws Exception {
		Connection userConn = getConnection();
		Object result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new BeanHandler( type );
			result = run.query( userConn, sql, param, h );
		}
		catch ( Exception _e ) {
			throw _e;
		}
		finally {
			closeConnection(userConn);
		}
		return result;
	}


	/**
	 * Executes the given SELECT SQL query and Convert the first row of the
	 * ResultSet into a bean with the Class given in the parameter.
	 * 
	 * @param type
	 *            The Class of beans.
	 * @param sql
	 *            The SQL to execute.
	 * @param params
	 *            Initialize the PreparedStatement's IN parameters with this
	 *            array.
	 * @return An initialized JavaBean or null if there were no rows in the
	 *         ResultSet.
	 */
	public Object searchToBeanOfSingle( Class type, String sql, Object[] params ) throws Exception {
		Connection userConn = getConnection();
		Object result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new BeanHandler( type );
			result = run.query( userConn, sql, params, h );
		}
		catch ( Exception _e ) {
			throw _e;
		}
		finally {
			closeConnection(userConn);
		}
		return result;
	}


	/**
	 * Execute an SQL SELECT query without any replacement parameters and
	 * Convert the ResultSet rows into a List of beans with the Class given in
	 * the parameter.
	 * 
	 * Usage Demo:
	 * 
	 * <pre>
	 * ArrayList result = searchToBeanList( Test.class, sql );
	 * Iterator iterator = result.iterator();
	 * while ( iterator.hasNext() ) {
	 * 	Test test = ( Test ) iterator.next();
	 * 
	 * }
	 * </pre>
	 * 
	 * @param type
	 *            The Class that objects returned from handle() are created
	 *            from.
	 * @param sql
	 *            The SQL to execute.
	 * @return A List of beans (one for each row), never null.
	 */
	public ArrayList searchToBeanList( Class type, String sql ) throws Exception {
		Connection userConn = getConnection();
		ArrayList result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new BeanListHandler( type );
			result = ( ArrayList ) run.query( userConn, sql, h );
		}
		catch ( Exception _e ) {
			throw _e;
		}
		finally {
			closeConnection(userConn);
		}
		return result;
	}


	/**
	 * Executes the given SELECT SQL with a single replacement parameter and
	 * Convert the ResultSet rows into a List of beans with the Class given in
	 * the parameter.
	 * 
	 * @param type
	 *            The Class that objects returned from handle() are created
	 *            from.
	 * @param sql
	 *            The SQL to execute.
	 * @param param
	 *            The replacement parameter.
	 * @return A List of beans (one for each row), never null.
	 */
	public ArrayList searchToBeanList( Class type, String sql, Object param ) throws Exception {
		Connection userConn = getConnection();
		ArrayList result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new BeanListHandler( type );
			result = ( ArrayList ) run.query( userConn, sql, param, h );
		}
		catch ( Exception _e ) {
			throw _e;
		}
		finally {
			closeConnection(userConn);
		}
		return result;
	}


	/**
	 * Executes the given SELECT SQL query and Convert the ResultSet rows into a
	 * List of beans with the Class given in the parameter.
	 * 
	 * @param type
	 *            The Class that objects returned from handle() are created
	 *            from.
	 * @param sql
	 *            The SQL to execute.
	 * @param params
	 *            Initialize the PreparedStatement's IN parameters with this
	 *            array.
	 * @return A List of beans (one for each row), never null.
	 */
	public ArrayList searchToBeanList( Class type, String sql, Object[] params ) throws Exception {
		Connection userConn = getConnection();
		ArrayList result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new BeanListHandler( type );
			result = ( ArrayList ) run.query( userConn, sql, params, h );
		}
		catch ( Exception _e ) {
			throw _e;
		}
		finally {
			closeConnection(userConn);
		}
		return result;
	}
	/**
	 * Execute an SQL SELECT query without any replacement parameters and place
	 * the column values from the first row in an Object[].
	 * 
	 * Usage Demo:
	 * 
	 * <pre>
	 * Object[] result = searchToArray(sql);
	 * if (result != null) {
	 * 	for (int i = 0; i &lt; result.length; i++) {
	 * 
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @param sql
	 *            The SQL to execute.
	 * @return An Object[] or null if there are no rows in the ResultSet.
	 */
	public Object[] searchToArray(String sql) throws Exception {

		Connection connection = getConnection();
		Object[] result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new ArrayHandler();

			result = (Object[]) run.query(connection, sql, h);

		} catch (Exception _e) {
			throw _e;

		} finally {}
			closeConnection(connection);
		
		return result;
	}

	/**
	 * Executes the given SELECT SQL with a single replacement parameter and
	 * place the column values from the first row in an Object[].
	 * 
	 * @param sql
	 *            The SQL statement to execute.
	 * @param param
	 *            The replacement parameter.
	 * @return An Object[] or null if there are no rows in the ResultSet.
	 */
	public Object[] searchToArray(String sql, Object param) throws Exception {

		Object[] result = null;
		Connection userConn = getConnection();
		try {

			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new ArrayHandler();

			result = (Object[]) run.query(userConn, sql, param, h);
		} catch (Exception _e) {
			throw _e;

		} finally {
			closeConnection(userConn);
		}
		return result;
	}

	/**
	 * Executes the given SELECT SQL query and place the column values from the
	 * first row in an Object[].
	 * 
	 * @param sql
	 *            The SQL statement to execute.
	 * @param params
	 *            Initialize the PreparedStatement's IN parameters with this
	 *            array.
	 * @return An Object[] or null if there are no rows in the ResultSet.
	 */
	public Object[] searchToArray(String sql, Object[] params) throws Exception {

		Object[] result = null;
		Connection userConn = getConnection();
		try {

			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new ArrayHandler();

			result = (Object[]) run.query(userConn, sql, params, h);
		} catch (Exception _e) {
			throw _e;

		} finally {
			closeConnection(userConn);
		}
		return result;
	}

	/**
	 * Execute an SQL SELECT query without any replacement parameters and
	 * converts the first ResultSet into a Map object.
	 * 
	 * Usage Demo:
	 * 
	 * <pre>
	 * Map result = searchToMap(sql);
	 * 
	 * </pre>
	 * 
	 * @param sql
	 *            The SQL to execute.
	 * @return A Map with the values from the first row or null if there are no
	 *         rows in the ResultSet.
	 */
	public Map searchToMap(String sql) throws Exception {
		Connection userConn = getConnection();
		Map result = new HashMap();
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new MapHandler();
			result = (Map) run.query(userConn, sql, h);
		} catch (Exception _e) {
			throw _e;
		} finally {
			closeConnection(userConn);
		}
		return result;
	}

	/**
	 * Executes the given SELECT SQL with a single replacement parameter and
	 * converts the first ResultSet into a Map object.
	 * 
	 * @param sql
	 *            The SQL to execute.
	 * @param param
	 *            The replacement parameter.
	 * @return A Map with the values from the first row or null if there are no
	 *         rows in the ResultSet.
	 */
	public Map searchToMap(String sql, Object param) throws Exception {
		Connection userConn = getConnection();
		Map result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new MapHandler();
			result = (Map) run.query(userConn, sql, param, h);
		} catch (Exception _e) {
			throw _e;

		} finally {
			closeConnection(userConn);
		}
		return result;
	}

	/**
	 * Executes the given SELECT SQL query and converts the first ResultSet into
	 * a Map object.
	 * 
	 * @param sql
	 *            The SQL to execute.
	 * @param params
	 *            Initialize the PreparedStatement's IN parameters with this
	 *            array.
	 * @return A Map with the values from the first row or null if there are no
	 *         rows in the ResultSet.
	 */
	public Map searchToMap(String sql, Object... params) throws Exception {
		Connection userConn = getConnection();
		Map result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new MapHandler();
			result = (Map) run.query(userConn, sql, params, h);
		} catch (Exception _e) {
			throw _e;
		} finally {
			closeConnection(userConn);
		}
		return result;
	}

	/**
	 * Execute an SQL SELECT query without any replacement parameters and
	 * converts the ResultSet into a List of Map objects.
	 * 
	 * Usage Demo:
	 * 
	 * <pre>
	 * ArrayList result = searchToMapList(sql);
	 * Iterator iterator = result.iterator();
	 * while (iterator.hasNext()) {
	 * 	Map map = (Map) iterator.next();
	 * 
	 * }
	 * </pre>
	 * 
	 * @param sql
	 *            The SQL to execute.
	 * @return A List of Maps, never null.
	 */
	public ArrayList searchToMapList(String sql) throws Exception {
		Connection userConn = getConnection();
		ArrayList result = new ArrayList();
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new MapListHandler();
			result = (ArrayList) run.query(userConn, sql, h);
		} catch (Exception _e) {
			throw _e;
		} finally {
			closeConnection(userConn);
		}
		return result;
	}

	/**
	 * Executes the given SELECT SQL with a single replacement parameter and
	 * converts the ResultSet into a List of Map objects.
	 * 
	 * @param sql
	 *            The SQL to execute.
	 * @param param
	 *            The replacement parameter.
	 * @return A List of Maps, never null.
	 */
	public ArrayList searchToMapList(String sql, Object param) throws Exception {
		Connection userConn = getConnection();
		ArrayList result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new MapListHandler();
			result = (ArrayList) run.query(userConn, sql, param, h);
		} catch (Exception _e) {
			throw _e;
		} finally {
			closeConnection(userConn);
		}
		return result;
	}

	/**
	 * Executes the given SELECT SQL query and converts the ResultSet into a
	 * List of Map objects.
	 * 
	 * @param sql
	 *            The SQL to execute.
	 * @param params
	 *            Initialize the PreparedStatement's IN parameters with this
	 *            array.
	 * @return A List of Maps, never null.
	 */
	public ArrayList searchToMapList(String sql, Object... params) throws Exception {
		Connection userConn = getConnection();
		ArrayList result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new MapListHandler();
			result = (ArrayList) run.query(userConn, sql, params, h);
		} catch (Exception _e) {
			throw _e;
		} finally {
			closeConnection(userConn);
		}
		return result;
	}

	/**
	 * 执行SELECT查询，并把返回结果的第一行的第一列的值转化成Object
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public Object searchToObject(String sql) throws Exception {
		Connection userConn = getConnection();
		Object result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new ScalarHandler(1);
			result = run.query(userConn, sql, h);
		} catch (Exception _e) {
			throw _e;
		} finally {
			closeConnection(userConn);
		}
		return result;
	}

	public Object searchToObject(String sql, Object params) throws Exception {
		Connection userConn = getConnection();
		Object result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new ScalarHandler(1);
			result = run.query(userConn, sql, params, h);
		} catch (Exception _e) {
			throw _e;
		} finally {
			closeConnection(userConn);
		}
		return result;
	}

	public Object searchToObject(String sql, Object... params) throws Exception {
		Connection userConn = getConnection();
		Object result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new ScalarHandler(1);
			result = run.query(userConn, sql, params, h);
		} catch (Exception _e) {
			throw _e;
		} finally {
			closeConnection(userConn);
		}
		return result;
	}

	/**
	 * Execute a batch of SQL INSERT, UPDATE, or DELETE queries.
	 * 
	 * @param sql
	 *            The SQL to execute.
	 * @param params
	 *            An array of query replacement parameters. Each row in this
	 *            array is one set of batch replacement values.
	 * @return The number of rows updated per statement.
	 */
	public int[] batch(String sql, Object[][] params) throws Exception {
		Connection userConn = getConnection();
		int[] rows = null;
		try {
			QueryRunner run = new QueryRunner();
			rows = run.batch(userConn, sql, params);
		} catch (Exception _e) {
			throw _e;
		} finally {
			closeConnection(userConn);
		}
		return rows;
	}

	/**
	 * Executes the given INSERT, UPDATE, or DELETE SQL statement without any
	 * replacement parameters.
	 * 
	 * @param sql
	 *            The SQL statement to execute.
	 * @return The number of rows updated.
	 */
	public int update(String sql) throws Exception {
		Connection userConn = getConnection();
		int rows = 0;
		try {
			QueryRunner run = new QueryRunner();
			rows = run.update(userConn, sql);
		} catch (Exception _e) {
			throw _e;
		} finally {
			closeConnection(userConn);
		}
		return rows;
	}

	/**
	 * Executes the given INSERT, UPDATE, or DELETE SQL statement with a single
	 * replacement parameter.
	 * 
	 * @param sql
	 *            The SQL statement to execute.
	 * @param param
	 *            The replacement parameter.
	 * @return The number of rows updated.
	 */
	public int update(String sql, Object param) throws Exception {
		Connection userConn = getConnection();
		int rows = 0;
		try {
			QueryRunner run = new QueryRunner();
			rows = run.update(userConn, sql, param);
		} catch (Exception _e) {
			throw _e;
		} finally {
			closeConnection(userConn);
		}
		return rows;
	}

	/**
	 * Executes the given INSERT, UPDATE, or DELETE SQL statement.
	 * 
	 * @param sql
	 *            The SQL statement to execute.
	 * @param params
	 *            Initializes the PreparedStatement's IN (i.e. '?') parameters.
	 * @return The number of rows updated.
	 */
	public int update(String sql, Object... params) throws Exception {
		Connection userConn = getConnection();
		int rows = 0;
		try {
			QueryRunner run = new QueryRunner();
			rows = run.update(userConn, sql, params);
		} catch (Exception _e) {
			throw _e;
		} finally {
			closeConnection(userConn);
		}
		return rows;
	}

	/**
	 * 通过Map的格式入库
	 * 
	 * @param map
	 * @param conn
	 * @param tableName
	 *            想要入库的表名
	 * @param allowMapValNULL
	 *            是否允许map的值为NULL的情况
	 * @return
	 * @throws Exception
	 *             如果参数为NULL,或则更新数据库时失败
	 */
	public int autoInsertDBByMap(Map map, DBConn conn, final String tableName, final boolean allowMapValNULL)
			throws Exception {
		if (map == null || conn == null || tableName == null || StringUtils.trimToNull(tableName) == null)
			throw new Exception("Agreements No One Can Be NULL!!!--->参数一个也不能少");
		try {
			StringBuilder sb = new StringBuilder("insert into ");
			sb.append(tableName);
			sb.append(" (");

			StringBuilder vals = new StringBuilder();
			Set keySet = map.keySet();
			final int size = keySet.size();
			if (size == 0)
				return -10;
			List params = new ArrayList(size);
			Iterator it = keySet.iterator();
			while (it.hasNext()) {
				Object next = it.next();
				Object value = map.get(next);
				if (allowMapValNULL && value == null)
					return -10;

				sb.append(next);
				sb.append(",");

				vals.append("?,");
				params.add(value);
			}

			String sb1 = sb.substring(0, sb.length() - 1) + ")";
			String vals1 = vals.substring(0, vals.length() - 1);
			final StringBuilder sql = new StringBuilder(sb1);
			sql.append(" values (");
			sql.append(vals1);
			sql.append(")");

			logger.info("sql:" + sql + Path.LS + "params:" + params);
			return conn.update(sql.toString(), params.toArray());
		} catch (Exception e) {
			logger.error("{ 问题比较严重  }", e);
		}
		return -1;
	}


	private class MyResultSetHandler implements ResultSetHandler {
		private String keyTemp;

		/**
		 * 每个row默认的key的名称
		 * 
		 * @param keyTemp
		 */
		public MyResultSetHandler(String keyTemp) {
			this.keyTemp = keyTemp;
		}

		/**
		 * 如果keyTemp==null,默认使用第一列作为Map的key
		 */
		public MyResultSetHandler() {
		}

		@Override
		public Object handle(ResultSet rs) throws SQLException {
			Map map = new HashMap();
			ResultSetMetaData rsm = rs.getMetaData();
			int allCloumn = rsm.getColumnCount();
			while (rs.next()) {
				Object key = null;
				if (keyTemp == null)
					key = rs.getObject(1);
				else
					key = rs.getObject(keyTemp);
				List val = new ArrayList(allCloumn);
				for (int i = 1; i <= allCloumn; i++)
					val.add(rs.getObject(i));
				map.put(key, val);
			}
			return map;
		}
	}
	
	public int[] batch(DataSource ds,String sql,Object[][] params) throws SQLException {
		Connection conn = ds.getConnection();
		int[] rows = null;
		try {
			QueryRunner run = new QueryRunner();
			rows = run.batch(conn, sql, params);
		} catch (SQLException _e) {
			logger.error("{}",_e);
			throw _e;
		} finally {
			DBUtils.closeDBResources(null, conn);
		}
		return rows;
	}
	
	//下面是spring的jdbc操作
	public int queryToCount(String sql){
		return this.jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	public int queryToCount(DataSource dataSource,String sql){
		JdbcTemplate jt = new JdbcTemplate(dataSource);
		return jt.queryForObject(sql,Integer.class);
	}
	
	public int queryToCount(String sql,Object...objects){
		return this.jdbcTemplate.queryForObject(sql,objects,Integer.class);
	}
	
	public int queryToCount(DataSource dataSource,String sql,Object...objects){
		JdbcTemplate jt = new JdbcTemplate(dataSource);
		int i = -1;
		i = jt.queryForObject(sql,objects,Integer.class);
		return i;
	}
	
	public Map<String,?> queryToMap(String sql){
		return this.getJdbcTemplate().queryForMap(sql);
	}
	
	public Map<String,?> queryToMap(DataSource dataSource,String sql){
		JdbcTemplate jt = new JdbcTemplate(dataSource);
		return jt.queryForMap(sql);
	}
	
	public Object queryToBeanOfSingle(String sql,Class<?> requiredType){
		return jdbcTemplate.queryForObject(sql, requiredType);
	}
	
	public Object queryToBeanOfSingle(DataSource dataSource,String sql,Class<?> requiredType){
		JdbcTemplate jt = new JdbcTemplate(dataSource);
		return jt.queryForObject(sql, requiredType);
	}
	
	public List<Object> queryToBeanOfList(String sql,Class<?> requiredType){
		return null;
	}

}
