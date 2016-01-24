package base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;


public class EnvironmentConfig {
	static EnvironmentConfig ec;
	private static Hashtable register = new Hashtable();
	private static Properties p = null;
	private String defaultProject;
	private String defaultLoginFile;
	private String defaultIndexFile;
	

	/**
	 * 实例化时候读取默认配置文件
	 */
	public EnvironmentConfig() {
		InputStream is = null;
		String fileName  = "/config/dbConfig.properties";// 默认文件存放路径

		try {
			p = (Properties) register.get(fileName);
			if (p == null) {

				String catalina_base = System.getProperty("catalina.base");
				java.io.File f = new java.io.File(catalina_base + "/classes" + fileName);
				if (false && f.exists()) {
					try {
						is = new FileInputStream(catalina_base + "/classes" + fileName);
					}
					catch (Exception e) {
						System.out.println("数据库配置文件加载失败！[" + catalina_base + "/classes" + fileName + "]");
					}
				}
				else {
					try {
						is = new FileInputStream(fileName);
					}
					catch (Exception e) {
						if (fileName.startsWith("/"))
							is = EnvironmentConfig.class.getResourceAsStream(fileName);
						else
							is = EnvironmentConfig.class.getResourceAsStream("/" + fileName);
					}
				}

				p = new Properties();
				p.load(is);
				register.put(fileName, p);
				is.close();
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return ec
	 */
	public static EnvironmentConfig getInstance() {
		if (ec == null) ec = new EnvironmentConfig();
		return ec;
	}

	/**
	 * 获取配置文件
	 */

	public Properties getPropertieFile() {
		return p;
	}

	/**
	 * 存储
	 * 
	 * @param out
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static boolean Sava(FileOutputStream out, String str) throws IOException {
		p.save(out, str);
		return false;
	}
	
	
	
	public String getDefaultIndexFile() {
		return defaultIndexFile;
	}
	
	public String getDefaultLoginFile() {
		return defaultLoginFile;
	}
	
	public String getDefaultProject() {
		return defaultProject;
	}

}
