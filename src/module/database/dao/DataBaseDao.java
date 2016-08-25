package module.database.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.druid.sql.SQLUtils;

import abstractentity.AbsEntity;
import tools.LoadSqlConfig;

public class DataBaseDao<T> extends AbsEntity<T>{
	private static final Logger logger = Logger.getLogger(DataBaseDao.class);
	 /**
	  * 获取表列的相关信息
	  * @return
	  */
	 public List<Map<String,Object>> getTableColumnsInfoByTableName(String tableName){
		 try {
			 if(tableName==null)tableName = "DUAL";
			 tableName = tableName.toUpperCase();
			 String sql = LoadSqlConfig.instance().getSql(LoadSqlConfig.ORA, "queryTable");
			 return db.searchToMapList(sql,new Object[]{tableName,tableName});
		} catch (Exception e) {
			logger.error("{}",e);
			return null;
		}
	 }
	 
	 public boolean addCloumnByTableName(String tableName){
		 boolean r = false;
		 //ALTER TABLE mytable ADD myClouna VARCHAR(20) NOT NULL;
		 return r;
	 }
}
