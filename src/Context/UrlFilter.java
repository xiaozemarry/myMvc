package Context;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.google.common.io.LineReader;

/**
 * 指定的url不做过滤(通过配置文件设置)
 *
 */
public class UrlFilter {
	private final static Logger logger = Logger.getLogger(UrlFilter.class);
	private List<String> urlList;
	private JSONObject pageRel;
	private UrlFilter() {
		urlList = new ArrayList<String>();
        URL url = this.getClass().getResource("/Config/filterUrl.fileter");
        FileReader fileReader  = null;
        try 
        {
            File file = new File(url.toURI());
            fileReader = new FileReader(file);
            LineReader lr = new LineReader(fileReader);	
            String next = lr.readLine();
            while(next!=null)
            {
            	urlList.add(next);
            	next = lr.readLine();
            }
		} catch (Exception e) 
        {
		 	e.printStackTrace();
		}finally
		{
			try{if(fileReader!=null)fileReader.close();} catch (Exception e2){e2.printStackTrace();}
		}
        
        //设置登录页
        final JSONObject jsonObject = new JSONObject(2);
        jsonObject.put("homePage","/module/menu/page/menu.jsp");//登陆成功后的主页
        jsonObject.put("loginPage","/login/blueBack/page/index.jsp");//登陆页
        InputStream pageInput = this.getClass().getResourceAsStream("/Config/pageRel.page");
        if(pageInput!=null)
        {
        	try 
        	{
				final String str  = StringUtils.trimToNull(IOUtils.toString(pageInput));
				if(str==null)
				{
					logger.info("在本程序中找到配置的文件,但是文件内容为空,将使用默认的路径:"+jsonObject.values().toString());
					this.pageRel = jsonObject;
				}
				try 
				{
					this.pageRel = JSONObject.parseObject(str);	
				} catch (Exception e) 
				{
					System.out.println("配置文件内容格式错误!使用默认配置!");
					this.pageRel = jsonObject;
				}
				logger.info("成功配置程序中面,但并未检查路径的有效性,该路径:"+this.pageRel.toString());
			} catch (IOException e) 
			{
				e.printStackTrace();
			}	
        }else logger.info("在本程序中并未找到配置的文件,将使用默认的路径:"+jsonObject.values().toString());
	}
	
    public JSONObject getPageRel() {
		return pageRel;
	}
	public List<String> getUrlList() {
		return urlList;
	}


	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}



	public void start(){
		final String info = "正在读取在自定义过滤器中直接略过的路径...........共有"+this.urlList.size()+"条记录"; 
		logger.info(info);
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
		if(this.urlList.contains("*"))return true;
		else if(this.urlList!=null)return this.urlList.contains(url);
		return false;	
	}
	/**
	 * 通过正则表达式判断当前路径是否被过滤
	 * @param url
	 * @return
	 */
	public boolean containsUrlByRegEx(final String url){
		if(url==null)return false;
		for(String urls:this.urlList)
		{
			Pattern pattern = Pattern.compile(urls);
			
		}
		return false;
	}
	public static void main(String[] args) {
		UrlFilter.instance();
	}
}
