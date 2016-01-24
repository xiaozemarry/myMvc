package module.userinfo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import tools.LoadSqlConfig;
import tools.Sequence;
import base.HttpBase;

@Controller
public class UserInfo extends HttpBase{
	private static final Logger logger = Logger.getLogger(UserInfo.class);
	private static final LoadSqlConfig sqlConfig = LoadSqlConfig.instance();
	
	@RequestMapping(value="/getUserInfo",method={RequestMethod.POST,RequestMethod.GET})
    public void getUserInfos(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id",Sequence.nextId());
		map.put("email", "test");
		map.put("normalpass", "123456");
		map.put("base64pass", "123456");
		try 
		{
			//db.autoInsertDBByMap(map, db, "user", false);
		} catch (Exception e1) 
		{
		}
		final String sql = sqlConfig.getSql(db,"queryUserInfo");
		System.out.println(sql);
		try 
		{
			List lm = db.searchToMapList(sql);
			JSONArray array = new JSONArray(lm);
			JSONObject object = new JSONObject();
			object.put("page",1);
			object.put("total",5);
			object.put("records",1);
			object.put("rows",array);
			this.printJson(object.toString(),false);
		} catch (Exception e) 
		{
			logger.error("{ 查询用户信息出错  }", e);
		}
    }
}
