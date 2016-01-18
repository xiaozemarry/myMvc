package Tools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import Base.Constant;

public class HttpServletRequestTool {
	private HttpServletRequest request;
	
	public HttpServletRequestTool(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	/**
	 * 是否存在登录的cookie
	 * @return
	 */
	public String hasLoginCookie(){
		Cookie cookies[] =  request.getCookies();
		if(cookies==null)return null;
        for (int i = 0; i < cookies.length; i++) 
        {
			 Cookie eachCookie = cookies[i];
			 String name = eachCookie.getName();
			 if(!Constant.COOKIENAME.equals(name))continue;
			 return eachCookie.getValue();
        }
		return null;
	}
	
	/**
	 * 获取base路径 http://xxx.xxx.xx.xx:x/projectName/
	 * @return
	 */
	public String getBasePath(){
		String path = request.getContextPath();
		StringBuilder sb = new StringBuilder();
		sb.append(request.getScheme());
		sb.append("://");
		sb.append(request.getServerName());
		sb.append(":");
		sb.append(request.getServerPort());
		sb.append(path);
		sb.append("/");
		return sb.toString();
		//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		//return basePath;
	}

}
