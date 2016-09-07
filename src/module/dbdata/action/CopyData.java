package module.dbdata.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import base.HttpBase;
import base.bean.BaseDruidConnConfig;
import module.dbdata.service.DbReader;
import utils.ActionUtils;
import utils.DBUtils;
import utils.DateUtils;
import utils.TemplateUtils;

@Controller
public class CopyData extends HttpBase {
	private static final Logger logger = Logger.getLogger(CopyData.class);
	private String tables;

	@ResponseBody
	@RequestMapping(value = "/testDb", method = { RequestMethod.POST, RequestMethod.GET })
	public void testDb() {
		try {
			ResultSet rs = DBUtils.getOnlyForTableColumnsByConn(db.getConnection(),"T_TAILOR_FORM");
			Map<String,Class<?>> map = DBUtils.getColumnMapping(rs);
			System.out.println(JSONObject.toJSONString(map));
			System.out.println(TemplateUtils.autoProduceJavaFile("reflect.bean", "T_TAILOR_FORM", map));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping(value = "/copydata", method = { RequestMethod.POST, RequestMethod.GET })
	public void copytData() {
		long start = System.currentTimeMillis();
		 logger.info(tables);
		 String sql = "SELECT COUNT(*) FROM USER_OBJECTS WHERE UPPER(OBJECT_NAME) = UPPER(?)";
		 StringTokenizer  st = new StringTokenizer(this.tables, ",");
		 BaseDruidConnConfig config191 = (BaseDruidConnConfig) ActionUtils.getBean(request, "测试库");
		 BaseDruidConnConfig config205 = (BaseDruidConnConfig) ActionUtils.getBean(request, "茂名");
		 logger.info("source fond it:"+config191);
		 logger.info("target fond it:"+config205);
		 DataSource ds191  = db.getDataSourceFromBean(config191);
		 DataSource ds205  = db.getDataSourceFromBean(config205);
		 int size =  st.countTokens();
		 List<String> listTables = new ArrayList<String>(size);
		 String token = null;
		 boolean tableNotExits = false;
		 for (int i = 0; i < size; i++) {
			token =  st.nextToken();
			int count1 = db.queryToCount(ds191,sql,token.toUpperCase());
			int count2 = db.queryToCount(ds205,sql,token.toUpperCase());
			if(count1<=0 || count2<=0){
				tableNotExits = true;
				break;
			}
			listTables.add(token);
		 }
		 
		 if(tableNotExits || token==null){
			 logger.error("配置错误,表不存在:"+token);
			 return ;
		 }
		 
		 logger.info("start to op db");
		 CountDownLatch latch = new CountDownLatch(listTables.size());
		 ExecutorService es = Executors.newCachedThreadPool();
		 for(String item:listTables){
			 DbReader task = new DbReader(item,ds205,ds191,db,latch,es);
			 es.submit(task);
		 }
		 try {
			latch.await();
		} catch (InterruptedException e) {
			logger.error("latch wait ex:Interrupted by other Thread",e);
		}
		 long end = System.currentTimeMillis();
		 try {
			String mark = DateUtils.getMarks((int)(end-start));
			logger.info("同步数据成功,耗时:"+mark);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 es.shutdown();
	}

	public String getTables() {
		return tables;
	}

	public void setTables(String tables) {
		this.tables = tables;
	}

}
