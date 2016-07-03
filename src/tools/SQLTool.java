package tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

public class SQLTool {
	private SQLTool(){}
	private static final Logger logger = Logger.getLogger(SQLTool.class);
	
    public static ThisBean getNativeInsertEntityByMap(Map<String,Object> map,String tableName) throws Exception{
    	if(map==null || tableName==null){
    		logger.info("plan to convert paramser of map,but the given is null");
    		throw new NullPointerException("paramser of map can not be null");
    	}
    	String c = "class";
    	String table = "table";
    	boolean has = map.containsKey(c);
    	if(has){
    		logger.info("has key named [class],remove it now");
    		map.remove(c);
    	}
    	boolean has1 = map.containsKey(table);
    	if(has1){
    		logger.info("has key named [table],remove it now");
    		map.remove(table);
    	}
    	
		StringBuilder sb = new StringBuilder("INSERT INTO ");
		sb.append(tableName);
		sb.append(" (");
		
		StringBuilder vals = new StringBuilder();
		
    	int size = map.size();
    	if(size==0)return null;
    	List<Object> params = new ArrayList<Object>(size);
    	Set<Map.Entry<String,Object>> set = map.entrySet();
    	for(Map.Entry<String,Object> each:set){
    		Object value = each.getValue();
    		if(value==null)continue;
    		String key = each.getKey().toUpperCase();
    		sb.append(key).append(",");
    		vals.append("?,");
    		params.add(value);
    	}
    	CollectionUtils.filter(params,new Predicate() {
			@Override
			public boolean evaluate(Object val) {
				if(val==null)return false;
				return true;
			}
		});
    	Object[] realParams = params.toArray();
		String sb1 = sb.substring(0, sb.length() - 1) + ")";
		String vals1 = vals.substring(0, vals.length() - 1);
	    StringBuilder sql = new StringBuilder(sb1);
		sql.append(" values (");
		sql.append(vals1);
		sql.append(")");
		
		String arrayParams = Arrays.toString(realParams);
		String sbTostring = sql.toString();
		styleLog(arrayParams,sbTostring);
		return new ThisBean(sbTostring, realParams);
    }
    /**
     * update table set c1 =,c2=,c3=, where 1=1 and f1=,
     * @param updateMap
     * @param conditionMap
     * @param tableName
     * @return
     */
    public static ThisBean getNativeUpdateEntityByMap(Map<String,Object> updateMap,Map<String,Object> conditionMap,String tableName){
    	String c = "class";
    	boolean has = updateMap.containsKey(c);
    	if(has){
    		logger.info("has key named [class],remove it now");
    		updateMap.remove(c);
    	}
    	
    	boolean has1 = conditionMap.containsKey(c);
    	if(has1){
    		logger.info("has key named [class],remove it now");
    		conditionMap.remove(c);
    	}
    	ThisBean tb = null;
    	UpdateBean up1 = getUpdateBean(updateMap,true);
    	if(up1==null)return tb;
    	UpdateBean up2 = getUpdateBean(conditionMap,false);
    	if(up2==null)return tb;
    	List<Object> allParams = up1.getParams();
    	allParams.addAll(up2.getParams());
    	StringBuilder sb = new StringBuilder("UPDATE ");
    	sb.append(tableName).append(" SET ");
    	sb.append(up1.getKeyParams());
    	sb.append(" WHERE 1=1 and ");
    	sb.append(up2.getKeyParams());
    	
    	Object params[] = allParams.toArray();
    	styleLog(Arrays.toString(allParams.toArray(params)), sb.toString());
    	tb = new ThisBean(sb.toString(), params);
    	return tb;
    }
    
    private static UpdateBean getUpdateBean(Map<String,Object> updateMap,boolean col){
    	if(updateMap==null)return null;
    	int size = updateMap.size();
    	if(size==0)return null;
    	StringBuilder sb = new StringBuilder();
    	List<Object> params = new ArrayList<Object>(size);
    	Set<Map.Entry<String,Object>> set = updateMap.entrySet();
    	for(Map.Entry<String,Object> each:set){
    		Object value = each.getValue();
    		if(value==null)continue;
    		String key = each.getKey().toUpperCase();
    		if(col){
    			sb.append(key).append("=?,");
    		}else{
    			sb.append(key).append("=? and ");
    		}
    		params.add(value);
    	}
    	String kp = null;
		if(col){
			kp = sb.substring(0,sb.length()-1);
		}else{
			kp = sb.substring(0,sb.length()-4);
		}
     	//styleLog(Arrays.toString(params.toArray()), kp);
     	return new SQLTool.UpdateBean(kp, params);
    }
    /**
     * 有格式的日志
     * @param arrayParams
     * @param sql
     */
    private static void styleLog(String arrayParams,String sql){
		int biger = NumberUtils.max(new int[]{arrayParams.length(),sql.length()})+5;
		StringBuilder a = new StringBuilder();
		for (int i = 0; i < biger; i++) {
			a.append("*");
		}
		StringBuilder logerInfo = new StringBuilder(a);
		logerInfo.append("\n").append(sql);
		logerInfo.append("\n").append(arrayParams);
		logerInfo.append("\n").append(a).append("#");
		logger.info(logerInfo);
    }
    
    public static class UpdateBean{
    	private String keyParams;
    	private List<Object> params;
    	public UpdateBean(String keyParams, List<Object> params) {
			super();
			this.keyParams = keyParams;
			this.params = params;
		}
		public String getKeyParams() {
			return keyParams;
		}
		public void setKeyParams(String keyParams) {
			this.keyParams = keyParams;
		}
		public List<Object> getParams() {
			return params;
		}
		public void setParams(List<Object> params) {
			this.params = params;
		}
    }
    
    public static class ThisBean{
    	private String strSql;
    	private Object[] params;
		public ThisBean(String strSql, Object[] params) {
			super();
			this.strSql = strSql;
			this.params = params;
		}
		public String getStrSql() {
			return strSql;
		}
		public void setStrSql(String strSql) {
			this.strSql = strSql;
		}
		public Object[] getParams() {
			return params;
		}
		public void setParams(Object[] params) {
			this.params = params;
		}
    }
}
