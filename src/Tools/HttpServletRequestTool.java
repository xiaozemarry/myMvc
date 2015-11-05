package Tools;

import java.util.HashMap;
import java.util.Map;

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

}
