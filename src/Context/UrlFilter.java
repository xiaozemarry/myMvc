package Context;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.io.LineReader;

/**
 * 指定的url不做过滤(通过配置文件设置)
 *
 */
public class UrlFilter {
	private List<String> urlList;
	private String loginPage;
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
        final String defaultPagePath = "/login/blueBack/page/index.jsp";
        InputStream pageInput = this.getClass().getResourceAsStream("/Config/loginPage.page");
        if(pageInput!=null)
        {
        	try 
        	{
				this.loginPage = StringUtils.trimToNull(IOUtils.toString(pageInput));
				if(this.loginPage==null)
				{
					System.out.println("在本程序中找到配置的登陆页文件,但是文件内容为空,将使用默认的路径:"+defaultPagePath);
					this.loginPage = defaultPagePath;
				}
				System.out.println("成功配置程序中的登陆页面,但并未检查路径的有效性,该路径:"+this.loginPage);
			} catch (IOException e) 
			{
				e.printStackTrace();
			}	
        }else System.out.println("在本程序中并未找到配置的登陆页文件,将使用默认的路径:"+defaultPagePath);
	}
	
	public String getLoginPage() {
		return loginPage;
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
