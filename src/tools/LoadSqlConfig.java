package tools;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.CDATA;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;

import base.DBConn;
import base.DBCustorm;

/**
 * 读取sqlconfig
 */
public class LoadSqlConfig{
	  private static final Logger logger = Logger.getLogger(LoadSqlConfig.class);
	  private static LoadSqlConfig instance = new LoadSqlConfig();
	  private  Map<String,Map<String,String>> allNodes;
	  private List<String> pathList;
	  private boolean debug = false;
	  
	  public  boolean isDebug() {
		return debug;
	}
	public  void setDebug(boolean debug) {
		this.debug = debug;
	}
	/**
	 * 外部调用处
	 * @return
	 */
	public Map<String, Map<String, String>> getAllNodes() {
		if(this.debug)//从新读取
		{
			instance = null;
			instance = instance();
			LoadSqlConfig.init(null);
		}
//		else//非调试模式下，所有System.out.println()的内容全部输出到文件
//		{
//			try 
//			{
//				String base = Path.CATALINE_BASE;
//				File file = new File(base+Path.FS+"console.txt");
//				if(!file.exists())file.createNewFile();
//				OutputStream out = new PrintStream(new FileOutputStream(file,true));
//				System.setOut((PrintStream) out);
//			} catch (Exception e) 
//			{
//				logger.warn("create console.txt filed!! useing the default console!!!");
//			}
//		 }
		return allNodes;
	}
	/**
	 * 获取数据库查询语句
	 * @param db 当前查询的数据库对象
	 * @param sqlId xml中配置的ID
	 * @return ID对应的sql语句,可能为null
	 */
	public String getSql(DBConn db,String sqlId){
		String str = null;
		if(db==null || this.getAllNodes()==null)return str;
		DBCustorm dbc = db.getCustorm();
		if(dbc==null)return str;
		String dbType = dbc.getDBType();
		if(dbType==null)return str;
		Map<String,String> map = instance().allNodes.get(dbType.toLowerCase());
		if(map==null)return str;
		return map.get(sqlId);
	}
	/**
	 * 获取数据库查询语句
	 * @param dbType 当前连接数据库类型
	 * @param sqlId xml中配置的ID
	 * @return ID对应的sql语句,可能为null 
	 */
	public String getSql(String dbType,String sqlId){
		String str = null;
		if(dbType==null)return str;
		this.getAllNodes();
		Map<String,String> map = instance().allNodes.get(dbType.toLowerCase());
		if(map==null)return str;
		return map.get(sqlId);
	}
	
	/**
	 * 外部请勿调用此方法!!!
	 * @return
	 */
	private Map<String, Map<String, String>> innerGetAllNodes() {
		return allNodes;
	}
	public void setAllNodes(Map<String, Map<String, String>> allNodes) {
		this.allNodes = allNodes;
	}
	private LoadSqlConfig(){
		 allNodes = new HashMap<String, Map<String,String>>(3);
		 pathList = new ArrayList<String>();
	  }
	 /**
	  * 初始化
	  * @param filePath
	  */
	  public static void init(URL filePath){
		    URL url = null;
		    if(filePath==null)url = LoadSqlConfig.class.getResource("../config/sqlConfig.xml");//默认的路径
		    else url = filePath;
		    if(url==null)
		    {
		    	logger.error("can not found the xml file named sqlConfig!!!path---->classes/config/sqlConfig.xml");
		    	return;
		    }
		  	SAXReader saxReader = new SAXReader();
		  	try 
		  	{
				Document document = saxReader.read(url);
				Element element = document.getRootElement();
				element.accept(instance.new DomVisitor());
			} catch (DocumentException e)
			{
				logger.error("read file failed",e);
			}
	  }
	  public static synchronized LoadSqlConfig instance(){
		  if(instance==null)instance = new LoadSqlConfig();
		  return instance;
	  }
	  private class DomVisitor extends VisitorSupport{
		  @Override
		public void visit(Document document1) {
			  logger.info("到目前还不可能走到这个方法哦");
		}
		  @Override
		public void visit(Attribute attribute) {
			  String attrName  = attribute.getName();
			  String attrVal = attribute.getValue();
			  if("path".equals(attrName))
			  {
				  if(pathList.contains(attrVal))
				  {
					  logger.error("Attention:the path of["+attrVal+"] has exists,we will ignore it!!!");
					  return;
				  }
				  URL url = this.getClass().getClassLoader().getResource(attrVal);
				  if(url==null)
				  {
					  logger.warn("Attention:the path of[classes/"+attrVal+"] does not exists!!!");
					  return;
				  }
				  else 
					  logger.warn("Loading:the path of[classes/"+attrVal+"] base is classes floder。");
				  pathList.add(attrVal);
				  LoadSqlConfig.init(url);//开始读取每一个指定位置的xml
			  }
			  else if("debugValue".equals(attrName) && "1".equals(attrVal))
			  {
				  LoadSqlConfig.instance.setDebug(true);
			  }
			super.visit(attribute);
		}
		  @Override
		public void visit(CDATA cdata) {
			String trimCdata = StringUtils.trimToNull(cdata.getText());
			String id = cdata.getParent().attributeValue("id");
		    String dbType = cdata.getParent().getParent().getParent().getName();//数据库类型节点
		    
		    Map<String,Map<String,String>> allNodes = LoadSqlConfig.instance.innerGetAllNodes();
		    Map<String,String> dbTypeVal = allNodes.get(dbType);
		    if(dbTypeVal==null)dbTypeVal = new HashMap<String, String>();
		    String hasId = dbTypeVal.get(id);
		    if(hasId==null)dbTypeVal.put(id,trimCdata);
		    else
		    {
		    	logger.info("id of ["+id+"] under the ["+dbType+"] exists,we will ignore it!!!");
		    	return;
		    }
	    	allNodes.put(dbType,dbTypeVal);
		}
	  }
	  public static void main(String[] args) {
		LoadSqlConfig.init(null);
		System.out.println(LoadSqlConfig.instance.getAllNodes());
		System.out.println(LoadSqlConfig.instance.getAllNodes());
		System.out.println(LoadSqlConfig.instance.getAllNodes());
		System.out.println(LoadSqlConfig.instance.getAllNodes());
		System.out.println(LoadSqlConfig.instance.getAllNodes());
	}
}