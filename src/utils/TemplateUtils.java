package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import template.ObjectEntity;

public class TemplateUtils {
	private static final Logger logger = Logger.getLogger(TemplateUtils.class);
	/**
	 * 自动生成java文件(生成的路径的根目录为src)
	 * @param packageName 如果为多包名的话,参数应该为package.package.package...否则package
	 * @param className 类名(多数情况下应该为数据库表的名称)
	 * @param fields(字段属性)
	 * @return 如果成功返回true
	 */
	public static boolean autoProduceJavaFile(final String packageName,final String className,Map<String,Class<?>> fields){
		boolean result = true;
		try {
			long start = System.currentTimeMillis();
			// 初始化参数
			Properties properties = new Properties();
			// 设置velocity资源加载方式为file
			properties.setProperty("resource.loader", "file");
			properties.setProperty("url.resource.loader.cache","true");
			// 设置velocity资源加载方式为file时的处理类
			properties.setProperty("file.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			VelocityEngine velocityEngine = new VelocityEngine(properties);
			VelocityContext context = new VelocityContext();
			List<String> importName = new ArrayList<String>();
			importName.add("com.alibaba.fastjson.JSONObject");
			ObjectEntity oe = new ObjectEntity(packageName,importName, className, fields);
			context.put("objectEntity", oe);
			String srcPath = FileUtils.getProjectSrcPath();
			StringBuilder sb = new StringBuilder();
			sb.append(srcPath).append(File.separator);
			sb.append(packageName.indexOf(".")>0?packageName.replace(".",File.separator):packageName).append(File.separator);
			File folder = new File(sb.toString());
			logger.info("plan to auto create folder at:"+folder.getPath());
			if(!folder.exists()){
				logger.warn("folder not exists,create it.");
				folder.mkdirs();
			}
			sb.append(className).append(".java");
			File file = new File(sb.toString());
			logger.info("plan to auto create file at:"+file.getPath());
			if(!file.exists()){
				logger.warn("file not exists,create it.");
				file.createNewFile();
			}
			Writer writer = new FileWriter(file);
			velocityEngine.mergeTemplate("/config/AutoJavaTemplate", "UTF-8", context, writer);
			writer.flush();
			writer.close();
			long end = System.currentTimeMillis();
			logger.info("load success spend times is:"+DateUtils.getMarks((int)(end-start)));
		} catch (Exception e) {
			logger.error("生成文件失败",e);
			result = false;
		}
		return result;
	}
}
