/*
 * Copyright (C) The Home Software Co,.Ltd. All rights reserved.
 *
 * DBConn.java, Created on 2005-5-4
 *
 */
package Base;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import Tools.Sequence;


@SuppressWarnings(value = { "unused","rawtypes","unchecked" })
public class DBConn {
	private String DaseBaseType;
	private DBCustorm custorm;
	
	private static DBConn instance = null;
    static
    {
    	init();
    }
    private static void init(){
    	String showNames = (String)EnvironmentConfig.getInstance().getPropertieFile().get("ShowNames");
	    instance=new DBConn(showNames);	
    }
	private DBConn() {}
	
	public static DBConn instance(){
		return instance;
	}
	
	public DBConn(String configName) {
		setConfigDataSource(configName);
	}

	/**
	 * 使用自定义连接
	 */
	public DBConn(DBCustorm custorm) {
		this.custorm = custorm;
	}

	/**
	 * 获取链接类型
	 * 
	 * @return
	 */
	public String getDaseBaseType() {
		return DaseBaseType;
	}

	public DBConn(HttpServletRequest request) {
		custorm =UserInfoPool.getInstance().getCurrentUser(request);
		if (custorm != null) {

			DaseBaseType = custorm.getDBType();
		}
	}

	/**
	 * 设置配置文件中指定的数据库链接，调试时候使用
	 * 
	 * 
	 * @throws UnknownHostException
	 */
	public void setConfigDataSource(String ConfigName) {
		custorm = new DBCustorm();
		String DBType = (String) EnvironmentConfig.getInstance()
				.getPropertieFile().get(ConfigName+".DBType");
		String DBPort = (String) EnvironmentConfig.getInstance()
				.getPropertieFile().get(ConfigName+".DBPort");
		String DBName = (String) EnvironmentConfig.getInstance()
				.getPropertieFile().get(ConfigName+".DBName");
		String DBUrl = (String) EnvironmentConfig.getInstance()
				.getPropertieFile().get(ConfigName+".DBUrl");
		String DBUser = (String) EnvironmentConfig.getInstance()
				.getPropertieFile().get(ConfigName+".DBUser");
		String DBPwd = (String) EnvironmentConfig.getInstance()
				.getPropertieFile().get(ConfigName+".DBPwd");
		custorm.setDBType(DBType);
		custorm.setDBPort(DBPort);
		custorm.setDBName(DBName);
		custorm.setDBUrl(DBUrl);
		custorm.setDbuser(DBUser);
		custorm.setDbpwd(DBPwd);
		DaseBaseType = custorm.getDBType();

	}

	/**
	 * Get a connection from pool
	 * 
	 * @return a Connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DBConnectionManager.getInstance().getConnection(custorm);
		return conn;
	}
	/**
	 * 通过传入的custom对象构建一个connecion
	 * @param custom
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection(DBCustorm custom) throws SQLException {
		Connection conn = null;
		conn = DBConnectionManager.getInstance().getConnection(custom);
		return conn;
	}
	
	/**
	 * 返回一个新实例化的dbconn对象,通常用于项目中需要用不同的连接数据库对象
	 * @param custom
	 * @return
	 */
	public DBConn getNewInstanceDBConnByDBCustorm(DBCustorm _custom){
		DBConn _new = new DBConn(_custom);
		return _new;
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

		Connection userConn = getConnection();
		Object[] result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new ArrayHandler();

			result = (Object[]) run.query(userConn, sql, h);

		} catch (Exception _e) {
			throw _e;

		} finally {
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
		}
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
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
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
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
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
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
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
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
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
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
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
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
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
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
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
	public ArrayList searchToMapList(String sql, Object... params)
			throws Exception {

		Connection userConn = getConnection();
		ArrayList result = null;
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new MapListHandler();

			result = (ArrayList) run.query(userConn, sql, params, h);
		} catch (Exception _e) {
			throw _e;

		} finally {
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
		}
		return result;
	}
	

	/**
	 * 执行SELECT查询，并把返回结果的第一行的第一列的值转化成Object
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
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
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
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
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
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
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
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
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
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
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
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
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
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
		}
		return rows;
	}
	
	public Map searchToMapValThanOne(String sql,String keyTemp) throws Exception{
		Connection userConn = getConnection();
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new DBConn.myResultSetHandler(keyTemp);
			return (Map)run.query(userConn, sql, h);
		} catch (Exception _e) {
			throw _e;

		} finally {
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
		}
	}
	public Map searchToMapValThanOne(String sql,Object params,String keyTemp) throws Exception{
		Connection userConn = getConnection();
		try {
			QueryRunner run = new QueryRunner();
			ResultSetHandler h = new DBConn.myResultSetHandler(keyTemp);
			return (Map)run.query(userConn, sql, params, h);
		} catch (Exception _e) {
			throw _e;

		} finally {
			DBConnectionManager.getInstance().freeConnection(custorm, userConn);
		}
	}
    
	/**
	 * 根据参数自动插入到数据库(主键使用默认的生产策略)
	 * @param request 请求对象
	 * @param primaryKeyName 主键名称
	 * @param _db 数据库对象
	 * @param exceptArr 参数中某些值并不是都能和数据库字段匹配
	 * @return
	 * @throws Exception 
	 */
	public int autoInsertByHttpParams(HttpServletRequest request,DBConn _db,String tableName,String primaryKeyName,String...exceptArr) throws Exception{
		if(tableName==null||primaryKeyName==null)throw new Exception("Agreements [conditionName,tableName] Can Not Be NULL!Thank You!!!");
		if(exceptArr==null)exceptArr = new String[0]; 
		List except = Arrays.asList(exceptArr);
		Map pMap = request.getParameterMap();
		List params = new ArrayList(8);
		StringBuilder insert = new StringBuilder("insert into "+tableName);
		insert.append("(");
		StringBuilder parameters = new StringBuilder("(");
		Set<String> keySets = pMap.keySet();
		Iterator<String> it = keySets.iterator();
		while(it.hasNext())
		{
			String key = StringUtils.trim(it.next());
			if(except.contains(key))continue;
			String[] val = (String[])pMap.get(key);
		    String indexVal = val[0];
		    String value = StringUtils.trimToNull(indexVal);
		    if(value==null)continue;
		    Object paramsVal = value;
		    boolean isNum = NumberUtils.isNumber(value);
		    if(isNum)
		    {
		    	if(value.indexOf(".")!=-1)paramsVal = NumberUtils.toDouble(value);
		    	else if(NumberUtils.toLong(value)>Integer.MAX_VALUE)paramsVal = NumberUtils.toLong(value);
		    	else paramsVal = NumberUtils.toInt(value);
		    }
		    insert.append(key+",");
		    parameters.append("?,");
		    params.add(paramsVal);
		}
		insert.append(primaryKeyName+")");
		parameters.append("?)");
		insert.append(" values "+parameters);
		params.add(Sequence.nextId());
		
		Connection conn =_db.getConnection();
		return _db.update(insert.toString(),params.toArray());
	}
	
	/**
	 * 简单的自动更新数据库操作
	 * @param request 请求对象
	 * @param _db 数据库对象
	 * @param conditionName 条件 where f_id = ?
	 * @param exceptArr 参数中某些值并不是都能和数据库字段匹配(换句话说:就是这些参数是不用插入数据库)
	 * @param tableName 更新的表名
	 * @return
	 * @throws Exception 如果conditionName为null,或则更新失败,或则条件值为null
	 */
	public int autoUpdateByHttpParams(HttpServletRequest request,DBConn _db,String tableName,String conditionName,String...exceptArr) throws Exception{
		if(conditionName==null || tableName==null)throw new Exception("Agreements [conditionName,tableName] Can Not Be NULL!Thank You!!!");
		if(exceptArr==null)exceptArr = new String[0]; 
		List except = Arrays.asList(exceptArr);
		
		Object conditionVal = null;
		Map pMap = request.getParameterMap();
		List params = new ArrayList(8);
		
		StringBuilder update = new StringBuilder("update ");
		update.append(tableName+" set ");
		Set<String> keySets = pMap.keySet();
		if(!keySets.contains(conditionName))if(conditionVal==null)throw new Exception("conditionVal Can Not Be NULL,But Given Key Is NULL!!!---->conditionName="+conditionName);
		Iterator<String> it = keySets.iterator();
		while(it.hasNext())
		{
			String key = StringUtils.trim(it.next());
			if(except.contains(key))continue;
			String[] val = (String[])pMap.get(key);
		    String indexVal = val[0];
		    String value = StringUtils.trimToNull(indexVal);
		    if(key.equals(conditionName))
		    {
		    	conditionVal = value;
		    	if(conditionVal==null)throw new Exception("conditionVal Can Not Be NULL,But Given Value Is NULL!!!conditionName="+conditionName);
		    	continue;
		    }
		    if(value==null)continue;
		    Object paramsVal = value;
		    boolean isNum = NumberUtils.isNumber(value);
		    if(isNum)
		    {
		    	if(value.indexOf(".")!=-1)paramsVal = NumberUtils.toDouble(value);
		    	else if(NumberUtils.toLong(value)>Integer.MAX_VALUE)paramsVal = NumberUtils.toLong(value);
		    	else paramsVal = NumberUtils.toInt(value);
		    }
		    update.append(key+"=?,");
		    params.add(paramsVal);
		}
		if(params.size()==0)throw new Exception("No Fields Can Be Updated!!!");
//		update.append(tableName+"."+conditionName);
//		update.append("=");
//		update.append(tableName+"."+conditionName);
		String subStr = update.substring(0,update.length()-1);
		subStr+=(" where "+conditionName+"=?");
		params.add(conditionVal);
		
		Connection conn =_db.getConnection();
		return _db.update(subStr,params.toArray());
	}
	public DBCustorm getCustorm() {
		return custorm;
	}
	
	public DBCustorm getCus() {
		return custorm;
	}
	
    private class myResultSetHandler implements ResultSetHandler{
    	private String keyTemp;
    	/**
    	 * 每个row默认的key的名称
    	 * @param keyTemp
    	 */
    	public myResultSetHandler(String keyTemp) {
    		   this.keyTemp = keyTemp;
		}
    	/**
    	 * 如果keyTemp==null,默认使用第一列作为Map的key
    	 */
    	public myResultSetHandler(){}
		@Override
		public Object handle(ResultSet rs) throws SQLException {
			Map map =  new HashMap();
			ResultSetMetaData rsm = rs.getMetaData();
			int allCloumn = rsm.getColumnCount();
			while(rs.next())
			{
				 Object key = null;
				 if(keyTemp==null) key = rs.getObject(1);
				 else key = rs.getObject(keyTemp);
				 List val = new ArrayList(allCloumn);
				 for (int i =1; i <= allCloumn; i++)
				 val.add(rs.getObject(i));
			     map.put(key,val);
			}
			 return map;
		}
    }
}
