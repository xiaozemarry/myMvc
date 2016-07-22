package module.login.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;

import testtransaction.userinfo.UserInfo;
import tokens.TokenManager;
import tools.HttpServletRequestTool;
import tools.StringTools;
import utils.ActionUtils;
import utils.DBUtils;
import base.Constant;
import base.HttpBase;
import base.bean.BaseDruidConnConfig;
import context.UrlFilter;
import module.login.dao.UserDao;

@Controller
public class Login extends HttpBase {
	private static final Logger logger = Logger.getLogger(Login.class);
	private UserInfo testTranscation;
    //private UserDao userDao;

	@RequestMapping(value = "/loginUser", method = {RequestMethod.POST,RequestMethod.GET})
	public void loginIn() {
		try {
			BaseDruidConnConfig config = (BaseDruidConnConfig) ActionUtils.getBean(request, "my205db");
			
			System.out.println(config);
			System.out.println(db.getConnectionFromBean(config));
//			BaseDruidConnConfig dd = new BaseDruidConnConfig("jdbc:oracle:thin:@10.112.6.205:1521/ipms","thd","thd");
//			Connection con = db.getConnectionFromBean(dd);
//			System.out.println(con);
			System.out.println(testTranscation.updateUserSalary());
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
		String hasCookie = new HttpServletRequestTool(request).hasLoginCookie();
		if (hasCookie != null) {
			logger.debug(Constant.SDFYMDHMS.format(new Date())+ ":移除登录用户池中的信息--->" + hasCookie);
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

		logger.debug(Constant.SDFYMDHMS.format(new Date()) + ":设置登录用户池中的信息--->"+ id);// +"\n"
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
	public void loginOut() {
		String hasCookie = new HttpServletRequestTool(request).hasLoginCookie();
		if (hasCookie != null) {
			logger.info(Constant.SDFYMDHMS.format(new Date())+ ":移除登录用户池中的信息--->" + hasCookie);
			TokenManager.instance().remove(hasCookie);
		}

		try {
			response.sendRedirect(UrlFilter.instance().getPageRel().getString("loginPage"));
		} catch (IOException e) {
			logger.error("case when response.sendRedirect",e);
		}
	}

	public UserInfo getTestTranscation() {
		return testTranscation;
	}

	public void setTestTranscation(UserInfo testTranscation) {
		this.testTranscation = testTranscation;
	}
}
