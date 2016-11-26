package module.database.service;

import java.util.List;
import java.util.Map;

import module.database.dao.DataBaseDao;

public class DataBaseService<T> {
	private DataBaseDao<T> dataBaseDao;

	public List<Map<String, Object>> getTableColumnsInfoByTableName(String tableName) {
		return dataBaseDao.getTableColumnsInfoByTableName(tableName);
	}

	public DataBaseDao<T> getDataBaseDao() {
		return dataBaseDao;
	}

	public void setDataBaseDao(DataBaseDao<T> dataBaseDao) {
		this.dataBaseDao = dataBaseDao;
	}
}
