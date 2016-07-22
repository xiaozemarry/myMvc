package utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import exception.IllegaParams;
import tools.Sequence;

/**
 * 文件操作的基本公用方法(该类包括了java.io.FileFilter的基本用法,详见内部类SimpleFileFilter)
 * @author mojf
 *
 */
public class FileUtils {
	
	public static JSONObject getJSONObject(JSONObject base,File file) throws IllegaParams{
		String fileName = file.getName();
		base.put("id",Sequence.nextId());
		base.put("text",fileName);
		if(file.isFile()){
			return base;
		}else if(file.isDirectory()){
			getJSONArray(base,new JSONArray(), file,null);
		}
		return base;
	}
	
	public static JSONArray getJSONArray(JSONObject base,JSONArray array,File file,FileFilter fFilter) throws IllegaParams{
		if(file.isFile()){
			throw new IllegaParams("参数:file不能为文件类型,而应该是文件夹");
		}
		if(base==null)base = new JSONObject();
		if(array==null)array = new JSONArray();
		File[] folder = file.listFiles(fFilter);//new MyFileFilter("管道储运有限公司")
		if(folder==null)return array;
		for(File item:folder){
			JSONObject each = getJSONObject(new JSONObject(), item);
			array.add(each);
			base.put("children",array);
			if(array.size()==0){
				base.put("size",array.size());
			}
		}
		return array;
	}
	
	public static void main(String[] args) throws IllegaParams {
		File file = new File("E:\\R_Attachment\\竣工期资料d");
		System.out.println(FileUtils.getJSONArray(new JSONObject(),new JSONArray(), file, null));
	}
	
	public class SimpleFileFilter implements FileFilter{
		private String enterName;
		private List<String> strFileter;

		public SimpleFileFilter(String enterName) {
			this.enterName = enterName;
			strFileter = new ArrayList<String>(4);
			strFileter.add("施工期资料" + File.separator + enterName);
			strFileter.add("竣工期资料" + File.separator + enterName);
			strFileter.add("设计期资料" + File.separator + enterName);
			strFileter.add("运营期资料" + File.separator + enterName);
		}
		@Override
		public boolean accept(File pathname) {
			final String path = pathname.getAbsolutePath();
			if(path.endsWith(File.separator+"施工期资料") 
			   ||path.endsWith(File.separator+"竣工期资料")
			   ||path.endsWith(File.separator+"设计期资料")
			   ||path.endsWith(File.separator+"运营期资料")
			   ||path.endsWith(File.separator+"运营期资料")
			   ||path.endsWith(File.separator+"R_Attachment") 
			  ){
				return true;
			}
			for(String item:strFileter){
				if(path.indexOf(item)>0)return true;
			}
			return false;
		}
	}
}
