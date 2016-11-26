package module.database.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import base.HttpBase;
import module.database.service.DataBaseService;
@Controller
public class DataBaseTable extends HttpBase{
	private DataBaseService<Object> dataBaseService;
	@RequestMapping(value = "/getTableInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void getAllTablesInfo(){
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Map<String,Object> map = new HashMap<String, Object>(2);
		//map.put("rows",dataBaseService.getTableColumnsInfoByTableName("PIPEINFO"));
		map.put("rows",dataBaseService.getTableColumnsInfoByTableName("marker"));
		//map.put("rows", 0);
		//map.put("total",7);//多少页
		//map.put("page",1);//当前页
		//map.put("records",66);//共多少条记录
		String newData = JSONObject.toJSONString(map,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
		this.printJson(newData, true);
	}
	public DataBaseService<Object> getDataBaseService() {
		return dataBaseService;
	}
	public void setDataBaseService(DataBaseService<Object> dataBaseService) {
		this.dataBaseService = dataBaseService;
	}
}
