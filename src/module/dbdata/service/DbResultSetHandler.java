package module.dbdata.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.log4j.Logger;

import base.DruidDBConnection;
import utils.DBUtils;

public class DbResultSetHandler<V> implements Callable<V> {
	private static final Logger logger = Logger.getLogger(DbResultSetHandler.class);
	private String table;
	private String searchSql;
	private List<String> cc;
	private String insertSql;
	private DataSource dsTo;
	private DataSource dsFrom;
	private CountDownLatch latch;
	private DruidDBConnection db;

	@Override
	public V call() {
		try {
			DbResultSetHandler<V>.ResultHandle innerHandle = new DbResultSetHandler.ResultHandle(cc);
			ResultSet rs = DBUtils.query(dsFrom.getConnection(), searchSql, 5000);
			Object[][] dbParams = (Object[][]) MethodUtils.invokeMethod(innerHandle, "handle", rs);
			db.batch(dsTo, insertSql, dbParams);
			this.getLatch().countDown();
			return null;
		} catch (Exception e) {
			logger.error("{}", e);
			return null;
		}
	}

	public class ResultHandle implements ResultSetHandler {
		private List<String> cc;

		public ResultHandle(List<String> cc) {
			this.cc = cc;
		}

		@Override
		public Object handle(ResultSet rs) throws SQLException {
			List params = new ArrayList(10000);
			final int size = cc.size();
			while (rs.next()) {
				Object[] itemParams = new Object[size];
				for (int j = 0; j <size; j++) {
					Object value = rs.getObject(cc.get(j).toLowerCase());
					itemParams[j] = value;
				}
				params.add(itemParams);
			}

			Object[][] dbParams = new Object[params.size()][];
			for (int i = 0; i < params.size(); i++) {
				Object[] item = (Object[])params.get(i);
				dbParams[i] = item;
			}
			DBUtils.closeResultSet(rs);
			return dbParams;
		}
	}

	public String getSearchSql() {
		return searchSql;
	}

	public void setSearchSql(String searchSql) {
		this.searchSql = searchSql;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public DataSource getDsTo() {
		return dsTo;
	}

	public void setDsTo(DataSource dsTo) {
		this.dsTo = dsTo;
	}

	public DruidDBConnection getDb() {
		return db;
	}

	public void setDb(DruidDBConnection db) {
		this.db = db;
	}

	public DataSource getDsFrom() {
		return dsFrom;
	}

	public void setDsFrom(DataSource dsFrom) {
		this.dsFrom = dsFrom;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	public List<String> getCc() {
		return cc;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public String getInsertSql() {
		return insertSql;
	}

	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}

	public static void main(String[] args) {
		List<Object[]> list = new ArrayList<Object[]>();
		list.add(new Object[] { "中文", "english" });
		list.add(new Object[] { "中文", "english" });
		Object[][] array = new Object[][] { list.toArray() };
		System.out.println(array);
	}
}
