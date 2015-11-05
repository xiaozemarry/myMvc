package Context;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.LineReader;

/**
 * 指定的url不做过滤(通过配置文件设置)
 *
 */
public class UrlFilter {
	private List<String> urlList;
	private UrlFilter() {
		urlList = new ArrayList<String>();
        URL url = this.getClass().getResource("/Config/filterUrl.fileter");
        try 
        {
            File file = new File(url.toURI());
            FileReader fileReader = new FileReader(file);
            LineReader lr = new LineReader(fileReader);	
            String next = lr.readLine();
            while(next!=null)
            {
            	urlList.add(next);
            	next = lr.readLine();
            }
            fileReader.close();
		} catch (Exception e) 
        {
		 	e.printStackTrace();
		}
	}
	
	
	public List<String> getUrlList() {
		return urlList;
	}


	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}



	public void start(){
		System.out.println("正在读取在自定义过滤器中直接略过的路径...........共有"+this.urlList.size()+"条记录");
	}

	private static UrlFilter instance = new UrlFilter();

	public static UrlFilter instance() {
		return instance;
	}
	
	/**
	 * 当前路径是否被过滤
	 * @param url
	 * @return
	 */
	
	public boolean containsUrl(final String url){
		if(url==null)return false;
		if(this.urlList!=null)return this.urlList.contains(url);
		return false;
	}
	public static void main(String[] args) {
		UrlFilter.instance();
	}
}
