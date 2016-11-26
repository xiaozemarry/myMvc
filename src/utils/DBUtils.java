package utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.log4j.Logger;

public class DBUtils {
	private static final Logger logger = Logger.getLogger(DBUtils.class);
	/**
	 * 查询count(*)sql模板
	 */
	public static final String COUNT_SQL_TEMPLATE = "SELECT COUNT(*) AS COUNT FROM (%s)";
	/**
	 * 查询记录集
	 */
	public static final String SLICE_SQL_TEMPLATE = "SELECT * FROM (SELECT T.*, ROWNUM AS RN FROM (%s) T WHERE ROWNUM <= %d) WHERE RN > %d";
	/**
	 * 获取每次result的大小
	 */
	public static final int RESULTSET_FETCH_SIZE  = 5000;
	/**
	 * 分页的时候每次查询的条数(尽量不要太小)
	 */
	public static final int TABLE_PAGE_SIZE = 10*1000;
	
	/**
	 * 创建表,复制表,...(create,alter table....)
	 * @param conn
	 * @param sql
	 * @return
	 */
	public static boolean execute(Connection conn,String sql){
		try {
			final Statement stmt = executeStatement(conn);
			boolean rs = stmt.execute(sql);
			closeDBResources(stmt, conn);
			return rs;
		} catch (Exception e) {
			logger.error("{create failed}",e);
			return false;
		}
	}
	
	public static Statement executeStatement(Connection conn) throws SQLException{
		return conn.createStatement();
	}
	
	public static Statement createStatement(Connection conn) throws SQLException{
		return conn.createStatement();
	}
	/**
	 * 查询ResultSet
	 * @param conn 数据库连接
	 * @param sql SQL语句
	 * @return java.Sql.ResultSet
	 * @throws SQLException
	 */
	public static ResultSet query(Connection conn, String sql)
			throws SQLException {
		logger.info("sql:"+sql);
		conn.setAutoCommit(false);
		Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		return stmt.executeQuery(sql);
	}
	/**
	 * 查询ResultSet
	 * @param conn 数据库连接
	 * @param sql SQL语句
	 * @param fetchSize 获取的大小
	 * @return java.Sql.ResultSet
	 * @throws SQLException
	 */
	public static ResultSet query(Connection conn, String sql, int fetchSize)
			throws SQLException {
		logger.info("sql:"+sql);
		conn.setAutoCommit(false);
		Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		stmt.setFetchSize(fetchSize);
		return stmt.executeQuery(sql);
	}
	/**
	 * 查询ResultSet
	 * @param conn 数据库连接
	 * @param sql SQL语句
	 * @param fetchSize 获取的大小
	 * @param queryTimeout 查询时间
	 * @return  java.Sql.ResultSet
	 * @throws SQLException
	 */
	public static ResultSet query(Connection conn, String sql, int fetchSize, int queryTimeout)
	throws SQLException{
		conn.setAutoCommit(false);
        Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
        stmt.setFetchSize(fetchSize);
        stmt.setQueryTimeout(queryTimeout);
        return stmt.executeQuery(sql);
	}
	
	public static ResultSet getOnlyForTableColumnsByConn(Connection conn,String tableName){
		if(tableName.indexOf("from")<0){
			tableName = "select * from "+tableName;
		}
		Statement statement = null;
		ResultSet rs = null;
		String queryColumnSql = null;
		try {
			statement = conn.createStatement();
			queryColumnSql = String.format("select * from (%s) where 1=2",tableName);
			rs = statement.executeQuery(queryColumnSql);
		} catch (SQLException e) {
			logger.error("{}",e);
		}
		return rs;
	}
	
	/**
	 * 获取给定sql或则表的数据库列名
	 * @param conn 数据库连接
	 * @param tableName 表或则sql
	 * @return
	 */
	public static List<String> getTableColumnsByConn(Connection conn,
			String tableName) {
		if(tableName.indexOf("from")<0){
			tableName = "select * from "+tableName;
		}
		List<String> columns = new ArrayList<String>();
		ResultSet rs = null;
		Statement statement = null;
		String queryColumnSql = null;
		try {
			rs = DBUtils.getOnlyForTableColumnsByConn(conn, tableName);
			statement = conn.createStatement();
			queryColumnSql = String.format("select * from (%s) where 1=2",tableName);
			rs = statement.executeQuery(queryColumnSql);
			ResultSetMetaData rsMetaData = rs.getMetaData();
			for (int i = 0, len = rsMetaData.getColumnCount(); i < len; ++i) {
				columns.add(rsMetaData.getColumnName(i + 1));
			}

		} catch (SQLException e) {
			logger.error("{}",e);
		} finally {
			closeResultSet(rs);
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("{}",e);
				}
				conn = null;
			}
		}
		return columns;
	}
	
	/**
	 * 关闭结果集,同时关闭Statement
	 * @param rs
	 */
	public static void closeResultSet(ResultSet rs) {
		try {
			if (null != rs) {
				Statement stmt = rs.getStatement();
				if (null != stmt) {
					stmt.close();
					stmt = null;
				}
				rs.close();
			}
			rs = null;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * 按照给定的大小分割当前sql语句的条数
	 * @param conn 数据库连接
	 * @param tablename 查询的SQL语句或则表名
	 * @param page_size 每次查询的条数
	 * @return
	 */
	public static String[] splitSqlForPage(Connection conn,String tablename,int page_size){
		String[] list = null;
		if(tablename.indexOf("from")<0){
			tablename = "select * from "+tablename;
		}
		String countSql = String.format(DBUtils.COUNT_SQL_TEMPLATE,tablename);
		ResultSet rs = null;
		try {
			rs = DBUtils.query(conn,countSql,1);
			while(rs.next()){
				int count = rs.getInt(1);
				int nPage = (int) Math.ceil(count * 1.0 / page_size);
				list = new String[nPage];
				for (int j = 0; j < nPage; ++j) {
					Object params[] = new Object[]{tablename,(j + 1)* page_size,j * page_size};
					String sliceSql = String.format(DBUtils.SLICE_SQL_TEMPLATE,params);
					list[j]=sliceSql;
			    }
			}
		} catch (SQLException e) {
			//ignore it
			logger.error("{}",e);
		}finally{
			DBUtils.closeResultSet(rs);
			DBUtils.closeDBResources(null, conn);
		}
		return list;
	}
	
	public static Triple<List<String>, List<Integer>, List<String>> getColumnMetaData(
			Connection conn, String tableName,
			String column) throws SQLException {
		try {
			return getColumnMeataData(conn, tableName, column);
		} finally {
			closeDBResources(null, conn);
		}
	}

    /**
     * @return Left:ColumnName Middle:ColumnType Right:ColumnTypeName
     */
	
	public static Triple<List<String>, List<Integer>, List<String>> getColumnMeataData(
			Connection conn, String tableName, String column)
			throws SQLException {
		Statement statement = null;
		ResultSet rs = null;

		Triple<List<String>, List<Integer>, List<String>> columnMetaData = new ImmutableTriple<List<String>, List<Integer>, List<String>>(
				new ArrayList<String>(), new ArrayList<Integer>(),
				new ArrayList<String>());

		try {
			statement = conn.createStatement();
			String queryColumnSql = "select " + column + "from " + tableName
					+ " where 1= 2";
			rs = statement.executeQuery(queryColumnSql);

			ResultSetMetaData rsMetaData = rs.getMetaData();
			for (int i = 0, len = rsMetaData.getColumnCount(); i < len; ++i) {
				columnMetaData.getLeft().add(rsMetaData.getColumnName(i + 1));
				columnMetaData.getMiddle().add(rsMetaData.getColumnType(i + 1));
				columnMetaData.getRight().add(
						rsMetaData.getColumnTypeName(i + 1));
			}
			return columnMetaData;
		} finally {
			closeDBResources(rs, statement, null);
		}
	}
	
	public static void closeDBResources(ResultSet rs, Statement stmt,
			Connection conn) {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}

		if (null != stmt) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
		}

		if (null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	public static void closeDBResources(Statement stmt, Connection conn) {
		closeDBResources(null, stmt, conn);
	}
	
	public static Class<?> getSqlTypes(ResultSetMetaData metaData,int resultSetMetaDataColumnType) throws SQLException {
		switch (resultSetMetaDataColumnType) {
		case Types.CHAR:
		case Types.NCHAR:
		case Types.VARCHAR:
		case Types.LONGVARCHAR:
		case Types.NVARCHAR:
		case Types.LONGNVARCHAR:
			return String.class;
		case Types.CLOB:
		case Types.NCLOB:
			return String.class;
		case Types.SMALLINT:
		case Types.TINYINT:
		case Types.INTEGER:
		case Types.BIGINT:
			return Long.class;
		case Types.NUMERIC:
		case Types.DECIMAL:
			return Double.class;
		case Types.FLOAT:
		case Types.REAL:
		case Types.DOUBLE:
			return Double.class;
		case Types.TIME:
			return Date.class;
		// for mysql bug, see http://bugs.mysql.com/bug.php?id=35115
		case Types.DATE:
			if (metaData.getColumnTypeName(1).equalsIgnoreCase("year")) {
				return Long.class;
			} else {
				return Date.class;
			}
		case Types.TIMESTAMP:
			return Date.class;
		case Types.BINARY:
		case Types.VARBINARY:
		case Types.BLOB:
		case Types.LONGVARBINARY:
			return Byte.class;
		// warn: bit(1) -> Types.BIT
		// warn: bit(>1) -> Types.VARBINARY
		case Types.BOOLEAN:
		case Types.BIT:
			return Boolean.class;
		case Types.NULL:
			return String.class;
		// TODO 添加BASIC_MESSAGE
		default:
			return null;
		}
	}
    /**
     * 获取数据库字段的映射,自动关闭ResultSet
     * @param rs java.sql.ResultSet
     * @return Map<String, Class<?>>--->key:列名称,value:当前列对应的java类型
     * @throws SQLException 
     */
	public static Map<String, Class<?>> getColumnMapping(final ResultSet rs) throws SQLException {
		final ResultSetMetaData resultSetMetaData = rs.getMetaData();
		final int columnCount = resultSetMetaData.getColumnCount();
		Map<String, Class<?>> mapping = new HashMap<String, Class<?>>(columnCount);
		for (int i = 1; i < columnCount; i++) {
			String columnName = resultSetMetaData.getColumnName(i);
			int dataType = resultSetMetaData.getColumnType(i);
			Class<?> clazz = getSqlTypes(resultSetMetaData, dataType);
			mapping.put(columnName,clazz);
		}
		logger.info("loop finish,close [java.sql.ResultSet]");
		DBUtils.closeResultSet(rs);
		return mapping;
	}
	
	/**
	 * 执行存储过程
	 * @param conn
	 * @param proc
	 * @param value
	 * @throws SQLException
	 */
	public static void doProc(Connection conn, String proc, String value)throws SQLException{
		conn.setAutoCommit(false);
		CallableStatement statement = null;
		String callStr = getCallProcString(proc, value);
		statement = conn.prepareCall(callStr);
		
		if(value == null)
			statement.execute();
		else{
			statement.setInt(1, Integer.valueOf(value));
			statement.execute();
		}
	}
	
	private static String getCallProcString(String proc, String value){
		if(value == null){
			return new String("{call " +proc+"()}");
		}else{
			return new String("{call " +proc+"(?)}");
		}
	}
}
