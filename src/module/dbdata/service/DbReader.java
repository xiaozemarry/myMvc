package module.dbdata.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import base.Constant;
import base.DruidDBConnection;
import utils.DBUtils;

public class DbReader implements Callable<List<Future>> {
	private static final Logger logger = Logger.getLogger(DbResultSetHandler.class);
	private String table;
	private DataSource dsTo;//数据要到哪里去
	private DataSource dsFrom;//数据从哪里来
	private DruidDBConnection db;
	private CountDownLatch latch;
	private ExecutorService eService;
	
	public DbReader(String table, DataSource dsTo, DataSource dsFrom, DruidDBConnection db, CountDownLatch latch,
			ExecutorService eService) {
		super();
		this.table = table;
		this.dsTo = dsTo;
		this.dsFrom = dsFrom;
		this.db = db;
		this.latch = latch;
		this.eService = eService;
	}

	@Override
	public List<Future> call() throws Exception {
		return reader();
	}
    
	public List<Future> reader() throws SQLException, InterruptedException{
		List<String> cc = DBUtils.getTableColumnsByConn(dsTo.getConnection(), this.table);
		logger.info("fetch column:"+cc);
		String insertSql = this.getSql(cc);
		logger.info("insert sql:"+insertSql);
		String sqls[]  = DBUtils.splitSqlForPage(dsFrom.getConnection(),table,10000);
		final int size = sqls.length;
		logger.info("split size:"+size);
		List<Future> list = new ArrayList<Future>(size);
		final CountDownLatch myLatch = new CountDownLatch(size);
		this.createTableBak();
		for (int i = 0; i < size; i++) {
			DbResultSetHandler<Object> dbResultSetHandler = new DbResultSetHandler<Object>();
			dbResultSetHandler.setCc(cc);
			dbResultSetHandler.setDb(db);
			dbResultSetHandler.setDsTo(dsTo);
			dbResultSetHandler.setDsFrom(dsFrom);
			dbResultSetHandler.setTable(table);
			dbResultSetHandler.setLatch(myLatch);
			dbResultSetHandler.setSearchSql(sqls[i]);
			dbResultSetHandler.setInsertSql(insertSql);
			list.add(eService.submit(dbResultSetHandler));
		}
		myLatch.await();
		this.getLatch().countDown();
		return list;
	}
	
	public String getSql(List<String> cc) throws SQLException {
		StringBuilder sql = new StringBuilder("INSERT INTO ");
		sql.append(this.table).append("(");
		StringBuilder params = new StringBuilder();
		for (String item : cc) {
			sql.append(item).append(",");
			params.append("?").append(",");
		}
		StringBuilder sql1 = new StringBuilder(sql.subSequence(0, sql.length() - 1));
		sql1.append(") values (");
		sql1.append(params.substring(0, params.length() - 1));
		sql1.append(")");
		return sql1.toString();
	}

	
	/**
	 * 备份原有的数据库
	 */
	public void createTableBak(){
	 	//String date = Constant.SDFYMDHMSA.format(new Date());
		final String tableBakName = "MYTABLEBAK_"+this.getTable();
		String sql = "create table "+tableBakName+" as select * from "+this.getTable();
		try {
			String my = "SELECT COUNT(*) FROM USER_OBJECTS WHERE UPPER(OBJECT_NAME) = UPPER(?)";
			int count = db.queryToCount(dsTo,my,tableBakName);
			if(count>0){
				logger.info("要复制的表在当前数据库中存在:");
			}else{
				boolean result = DBUtils.execute(dsTo.getConnection(), sql);
				logger.info(String.format("创建备份表:%s,创建结果:%s",new Object[]{tableBakName,result}));
			}
			//删除表
			String drop = "drop table "+this.getTable();
			boolean result1 = DBUtils.execute(dsTo.getConnection(), drop);
			logger.info(String.format("删除对象表:%s,删除结果:%s",new Object[]{this.getTable(),result1}));
			//复制表结构
			String copy ="create table "+this.getTable()+" as select * from "+tableBakName +" where 1=2";
			boolean result2 = DBUtils.execute(dsTo.getConnection(), copy);
			logger.info(String.format("复制对象表:%s,复制结果:%s",new Object[]{tableBakName,result2}));
		} catch (SQLException e) {
			logger.error("导入之前的操作失败",e);
		}
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

	public DataSource getDsFrom() {
		return dsFrom;
	}

	public void setDsFrom(DataSource dsFrom) {
		this.dsFrom = dsFrom;
	}

	public DruidDBConnection getDb() {
		return db;
	}

	public void setDb(DruidDBConnection db) {
		this.db = db;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	public ExecutorService geteService() {
		return eService;
	}

	public void seteService(ExecutorService eService) {
		this.eService = eService;
	}
	
}
