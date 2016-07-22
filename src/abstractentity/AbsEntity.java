package abstractentity;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import base.Constant;
import base.DruidDBConnection;
import tools.SQLTool;
import tools.Sequence;

/**
 * 一般实体DAO类extends的class
 * @author 
 *public abstract class AbsEntity<T> extends DruidDBConnection{
 */
public abstract class AbsEntity<T>{
	private Logger logger = Logger.getLogger(AbsEntity.class);
    protected DruidDBConnection db;
    
    /**
     * 查询所有
     * @return
     */
    public List<T> findAllEntity(){return null;};
    
    /**
     * 查询单个
     * @return
     */
    //public T findSingleEntity(){return null;};
    
    public int updateOneById(T t,PKOfDBEntity pk){
    	int res = Constant.DBEFFECTROWNUMBER;
    	try {
			Map<String,Object> updateMap = BeanUtils.describe(t);
	    	Map<String,Object> conditionMap = new HashMap<String, Object>(1);
	    	conditionMap.put(pk.getPkName(),pk.getPkValue());
			this.updateByMap(updateMap, conditionMap);
		} catch (IllegalAccessException e) {
			logger.error("",e);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			logger.error("",e);
		}
    	return res;
    }
    
    /**
     * 根据对象更新值
     * @param updateEntity 需要更新的对象
     * @param conditionEntity 条件对象(where conditionEntity.字段=conditionEntity.字段的值)
     * @return 更新结果
     */
    public int updateByEntity(T updateEntity,T conditionEntity){
    	int res = Constant.DBEFFECTROWNUMBER;
    	try {
			Map<String,Object> updateMap = BeanUtils.describe(updateEntity);
	    	Map<String,Object> conditionMap = BeanUtils.describe(conditionEntity);
			this.updateByMap(updateMap, conditionMap);
		} catch (IllegalAccessException e) {
			logger.error("",e);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			logger.error("",e);
		}
    	return res;
    }
    
    /**
     * 根据对象更新值
     * @param updateEntity 需要更新的对象
     * @param conditionEntity 条件对象(where conditionEntity.字段=conditionEntity.字段的值)
     * @return 更新结果
     */
    public int updateByMap(Map<String,Object> updateMap,Map<String,Object> conditionMap){
    	int res = Constant.DBEFFECTROWNUMBER;
    	SQLTool.ThisBean tb;
		try {
			String clazz = (String)updateMap.get("class");
			String tablename = (String)updateMap.get("table");
			if(clazz!=null || tablename !=null){
			   if(clazz!=null){
					int index = clazz.lastIndexOf(".")+1;
					if(index!=-1){
					   clazz = clazz.substring(index,clazz.length());
					}else{
					   clazz = clazz.substring(clazz.indexOf("class"),clazz.length());
					}
			   }
			   tb = SQLTool.getNativeUpdateEntityByMap(updateMap, conditionMap, clazz==null?tablename:clazz);
			   String sql = tb.getStrSql();
			   Object param[] = tb.getParams();
			   return db.update(sql, param);
			}
		} catch (Exception e) {
			logger.error("update failed",e);
			return res;
		}
    	return res;
    }
    
    /**
     * 通过实体类插入一条记录,数据库默认的主键名称为id
     * @param t 实体类
     * @return
     */
    public int insertOne(T t){
    	int res = Constant.DBEFFECTROWNUMBER;
    	try {
			Map<String,Object> map = BeanUtils.describe(t);
			if(map.get("id")==null){
				map.put("id",Sequence.nextId());
			}
			return this.insertOne(map);
		} catch (IllegalAccessException e) {
			logger.error("",e);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			logger.error("",e);
		}
    	return res;
    }
    /**
     * 通过Map插入一条记录,数据库默认的主键名称为id
     * @param t 实体类
     * @return
     */
    public int insertOne(Map<String,Object> map){
		if(map.get("id")==null){
			map.put("id",Sequence.nextId());
		}
    	int res = -10;
    	SQLTool.ThisBean tb;
		try {
			String clazz = (String)map.get("class");
			String tablename = (String)map.get("table");
			if(clazz!=null || tablename !=null){
			   if(clazz!=null){
					int index = clazz.lastIndexOf(".")+1;
					if(index!=-1){
					   clazz = clazz.substring(index,clazz.length());
					}else{
					   clazz = clazz.substring(clazz.indexOf("class"),clazz.length());
					}
			   }
			   tb = SQLTool.getNativeInsertEntityByMap(map,clazz==null?tablename:clazz);
			   String sql = tb.getStrSql();
			   Object param[] = tb.getParams();
			   return db.update(sql, param);
			}
		} catch (Exception e) {
			logger.error("insert failed",e);
			return res;
		}
    	return res;
    }
    /**
     * 通过Map插入一条记录,数据库默认的主键名称为id
     * @param t 实体类
     * @return
     */
    private int insertOneWithPk(Map<String,Object> map){
    	int res = -10;
    	SQLTool.ThisBean tb;
		try {
			String clazz = (String)map.get("class");
			String tablename = (String)map.get("table");
			if(clazz!=null || tablename !=null){
			   if(clazz!=null){
					int index = clazz.lastIndexOf(".")+1;
					if(index!=-1){
					   clazz = clazz.substring(index,clazz.length());
					}else{
					   clazz = clazz.substring(clazz.indexOf("class"),clazz.length());
					}
			   }
			   tb = SQLTool.getNativeInsertEntityByMap(map,clazz==null?tablename:clazz);
			   String sql = tb.getStrSql();
			   Object param[] = tb.getParams();
			   return db.update(sql, param);
			}
		} catch (Exception e) {
			logger.error("insert failed",e);
			return res;
		}
    	return res;
    }
    /**
     * 向数据库插入一条记录
     * @param t 实体类
     * @param idNameOfDb 数据库主键列的名称
     * @return
     */
    public int insertOne(T t,String idNameOfDb){
    	int res = Constant.DBEFFECTROWNUMBER;
    	try {
			Map<String,Object> map = BeanUtils.describe(t);
			map.put(idNameOfDb,Sequence.nextId());
			return this.insertOneWithPk(map);
		} catch (IllegalAccessException e) {
			logger.error("",e);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			logger.error("",e);
		}
    	return res;
    }
    /**
     * 向数据库插入一条记录
     * @param map 参数
     * @param idNameOfDb 数据库主键列的名称
     * @return
     */
    public int insertOne(Map<String,Object> map,String idNameOfDb){
	   	map.put(idNameOfDb, Sequence.nextId());
    	return this.insertOneWithPk(map);
    }
    /**
     * 批量更新(该方法地层调用DruidDBConnection中的batch方法,只是sql语句通过参数t来动态生成 的)
     * @param t 当前对象
     * @param batchParams 参数
     * @return 每行受影响的数目
     */
    public int[] insertMany(T t,Object[][] batchParams){
    	int[] res = new int[]{};
    	try {
    		Map<String,Object> map = BeanUtils.describe(t);
        	SQLTool.ThisBean tb;
    			String clazz = (String)map.get("class");
    			if(clazz!=null){
    				int index = clazz.lastIndexOf(".")+1;
    				if(index!=-1){
    				   clazz = clazz.substring(index,clazz.length());
    				}else{
    				   clazz = clazz.substring(clazz.indexOf("class"),clazz.length());
    				}
    			   try {
					  tb = SQLTool.getNativeInsertEntityByMap(map,clazz);
					  String sql = tb.getStrSql();
					  return db.batch(sql, batchParams);
				   } catch (Exception e) {
					   logger.error("getNativeInsertEntityByMap",e);
				   }
    			}
		} catch (IllegalAccessException e) {
			logger.error("{}",e);
		} catch (InvocationTargetException e) {
			logger.error("{}",e);
		} catch (NoSuchMethodException e) {
			logger.error("{}",e);
		}
    	return res;
    }
    
	public DruidDBConnection getDb() {
		return db;
	}
	
	@Resource
	public void setDb(DruidDBConnection db) {
		this.db = db;
	}
    
}
