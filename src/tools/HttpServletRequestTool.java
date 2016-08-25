package tools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import base.Constant;

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
	 * 
	 * @return
	 */
	public static String hasLoginCookie(HttpServletRequest request) {
		Cookie cookies[] = request.getCookies();
		if (cookies == null)
			return null;
		for (int i = 0; i < cookies.length; i++) {
			Cookie eachCookie = cookies[i];
			String name = eachCookie.getName();
			if (!Constant.COOKIENAME.equals(name))
				continue;
			return eachCookie.getValue();
		}
		return null;
	}

	/**
	 * 获取base路径 http://xxx.xxx.xx.xx:x/projectName/
	 * 
	 * @return
	 */
	public String getBasePath() {
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
	}

	/**
	 * 获取base路径 http://xxx.xxx.xx.xx:x/projectName/
	 * 
	 * @return
	 */
	public static String getBasePath(HttpServletRequest request) {
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
	}

	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getRequestURIPath(HttpServletRequest request) {
		final String basePath = HttpServletRequestTool.getBasePath(request);
		final String currentFullPath = request.getRequestURL().toString();
		final String currentReqeustPath = currentFullPath.substring(basePath.length() - 1, currentFullPath.length());
		return currentReqeustPath;
	}

}
