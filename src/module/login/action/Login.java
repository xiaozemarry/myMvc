package module.login.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;

import testtransaction.userinfo.UserInfo;
import tokens.TokenManager;
import tools.HttpServletRequestTool;
import tools.StringTools;
import utils.ActionUtils;
import utils.DBUtils;
import utils.DateUtils;
import base.Constant;
import base.HttpBase;
import base.bean.BaseDruidConnConfig;
import context.UrlFilter;
import module.login.dao.UserDao;

@Controller
public class Login extends HttpBase {
	private static final Logger logger = Logger.getLogger(Login.class);
	private UserInfo testTranscation;

	@RequestMapping(value = "/loginUser", method = {RequestMethod.POST,RequestMethod.GET})
	public void loginIn() {
		try {
			String name = ManagementFactory.getRuntimeMXBean().getName();    
			logger.error("pid:"+name);
//			BaseDruidConnConfig config = (BaseDruidConnConfig) ActionUtils.getBean(request, "my205db");
//			BaseDruidConnConfig dd = new BaseDruidConnConfig("jdbc:oracle:thin:@10.112.6.205:1521/ipms","thd","thd");
//			Connection con = db.getConnectionFromBean(dd);
//			System.out.println(con);
//			System.out.println(testTranscation.updateUserSalary());
//			ApplicationContext appContext = RequestContextUtils.getWebApplicationContext(request);
//			DruidDataSource druidDataSource = (DruidDataSource)appContext.getBean("dataSource");
//			DruidDataSource clone = druidDataSource.cloneDruidDataSource();
//			clone.setUrl("jdbc:oracle:thin:@10.112.6.205:1521/ipms");
//			clone.setUsername("thd");
//			clone.setPassword("thd");
			//ResultSet resultSet = DBUtils.query(clone.getConnection(), "select * from ",50);
			//System.out.println(DBUtils.getTableColumnsByConn(clone.getConnection(), "select * from aaaaa"));;
//			System.out.println(clone.getConnection());
//			System.out.println(druidDataSource.getConnection());
			//db.getConnection()
			//return;
			//userDao.insertOne(user);
			//userDao.updateByEntity(user, user);
			// System.out.println(userDao.searchToMap("select * from T_TAILOR_FIELD"));;
			//userDao.updateOneById(user, new PKOfDBEntity("iddd","aaaaaaaa"));
			//testTranscation.updateUserSalary(db);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");

		logger.info(String.format("logininfo--->userName:%s;passWord:%s",new Object[] { userName, passWord }));

		boolean condition = userName != null && passWord != null && "admin".equals(userName) && "admin".equals(passWord);
		if (!condition) {
			try {
				request.setAttribute("notice", "非法用户!");
				request.getRequestDispatcher("/login/blueBack/page/index.jsp").forward(request, response);
			} catch (ServletException e) {
				logger.error("request-非法用户-ServletException",e);
			} catch (IOException e) {
				logger.error("request-非法用户-IOException",e);
			}
			return;
		}

		// 登陆成功,设置token
		String hasCookie = HttpServletRequestTool.hasLoginCookie(request);
		if (hasCookie != null) {
			logger.debug(DateUtils.formatNow()+ ":移除登录用户池中的信息--->" + hasCookie);
			TokenManager.instance().remove(hasCookie);
		}
		// 多次登录,需要删除之前的
		Map<String, String> userMap = new HashMap<String, String>(2);
		userMap.put("userName", "admin");
		userMap.put("passWord", "admin");
		String id = TokenManager.instance().add(userMap);
		Cookie cookie = new Cookie(Constant.COOKIENAME, id);
		cookie.setMaxAge(Constant.MAXCOOKIEAGE);
		// cookie.setHttpOnly(true);//只有在网络的情况下可以读取,js无法读取

		logger.debug(DateUtils.formatNow() + ":设置登录用户池中的信息--->"+ id);// +"\n"
		response.addCookie(cookie);
		
		try {
			String referer = request.getParameter("referer");
			boolean isIlle = StringTools.instance().isIllegalStr(referer);
			//response.setContentType("text/html");
			if (isIlle){
				final String homePage = UrlFilter.instance().getPageRel().getString("homePage");
				response.sendRedirect(homePage);
			} else{
				response.sendRedirect(referer);//之前的连接页面
			}
		} catch (IOException e) {
			logger.error("case IOException when response sendRedirect", e);
		}
	}

	/**
	 * 注销登录
	 */
	@RequestMapping("/loginOut")
	public String loginOut() {
		String hasCookie = HttpServletRequestTool.hasLoginCookie(request);
		if (hasCookie != null) {
			logger.info(DateUtils.formatNow()+ ":移除登录用户池中的信息--->" + hasCookie);
			TokenManager.instance().remove(hasCookie);
		}
		//return "forward:/index.do"
		//response.sendRedirect(UrlFilter.instance().getPageRel().getString("loginPage"));
		return "redirect:"+UrlFilter.instance().getPageRel().getString("loginPage");
		
	}
	
	@RequestMapping(value="/mv",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView get(){
		ModelAndView mv = new ModelAndView();
		//mv.setViewName("forward:index.jsp");
		//假如前缀加redirect:或则forward的话,使用自定义的controller,否则,默认使用返回值+.jsp
		Map<String, Object> map=null;
		try {
			map = db.searchToMap("SELECT f_points FROM  T_VO_PIPELOD WHERE f_id = 127834936");
			Blob points = (Blob) map.get("f_points");
			InputStream inputStream = points.getBinaryStream();
			int i = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((i = inputStream.read()) != -1) {
			    baos.write(i);
			}
			String content = baos.toString("utf-8");
			//String strPoints = IOUtils.toString(points.getBinaryStream(),"UTF-8");
			//System.out.println(strPoints);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("index");
		return mv;
	}
	public static void main(String[] args) {
		//byte[] b = new byte[]{41, -66, 12, -116, -35, -106, 91, 64, 126, -125, -35, 77, 67, 47, 53, 64, 0, 0, 0, -96, 119, -7, 68, 64, 0, 0, 0, 0, 0, 0, 0, 0};
		byte[] b = new byte[]{ -66};
		System.out.println(b.length);
		try {
			System.out.println(new String(b));
			System.out.println(IOUtils.toString(b));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public UserInfo getTestTranscation() {
		return testTranscation;
	}

	public void setTestTranscation(UserInfo testTranscation) {
		this.testTranscation = testTranscation;
	}
}
