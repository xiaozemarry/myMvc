package goodCode;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang.StringUtils;



public class 递归文件夹 {
	/**
	 * 递归所有的文件夹,返回文件夹Map
	 * @param path 根目录
	 * @param container Map容器
	 * @return
	 */
	public  Map<String,Map<String,Long>> traverseFileFolders(File base,Map<String,Map<String,Long>> container){
		if(base!=null)return traverseFileFolders(base.getAbsolutePath(),container);
		return null;
	}
    /*
     * 递归所有的文件夹,返回文件夹Map
     */
	public  Map<String,Map<String,Long>> traverseFileFolders(String path,Map<String,Map<String,Long>> container){
		path = StringUtils.trim(path);
		if(path==null)return null;
		File file = new File(path);
		if(!file.exists())return container;
		Long cy = System.currentTimeMillis();
		synchronized (cy) 
		{
			try 
			 {
			    Thread.sleep(1);
			 } catch (InterruptedException e) {}
		   cy = System.currentTimeMillis()+1;
		}
		if(file.isFile())
		{
			//System.out.print(file.getName()+":");
			//System.out.println(file.getAbsolutePath());
			container.get("files").put(file.getAbsolutePath(),cy);
			return container;
		}
		else if(file.isDirectory())
		container.get("directory").put(file.getAbsolutePath(),cy);
		File[] files = file.listFiles();
		for(File each:files)
		{
			String eachPath = each.getAbsolutePath();
			//System.out.println(eachPath);
			traverseFileFolders(eachPath,container);
		}
		return container;
	}
	
	/**
	 * 集合变成map
	 * @param list
	 * @return
	 */
	public Map<Object,Map<String,String>> mapListToMap(List<Map> list,Object key){
		Map<Object,Map<String,String>> map = new HashMap<Object, Map<String,String>>();
		if(list==null)return map;
		try 
		{
			for (Map<String,String>  each : list) 
			{
				String value = each.get(key);
				map.put(value,each);
			}
		} catch (Exception e) 
		{
		  System.out.println("转换为Map异常!!!");
		}
		return map;
	}
}
