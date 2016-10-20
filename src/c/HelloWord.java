package c;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import tools.LoadSqlConfig;
import base.DBConn;
import base.DBCustorm;
import base.HttpBase;

/**
 *
 * SHOW VARIABLES LIKE 'character_set_%';
 * @author jacky-chueng
 *
 */
@Controller
public class HelloWord extends HttpBase{
	public static  List listData = null;
	public static Map<String,Integer> sMap=new HashMap<String,Integer>();
	public static Map<String,Integer> eMap=new HashMap<String,Integer>();
	public HelloWord(){
		 System.out.println("走一次实例化一下哦!");
	}
		@RequestMapping("/helloWorld/a")
	    public String helloWorld(Model model) throws Exception {
			String myCacheSql= LoadSqlConfig.instance().getSql("ora", "queryPipe");
			if(listData==null || 1==2)
			{
				if(listData==null)listData = db.searchToMapList(myCacheSql);
				int index = 0;
				for(Map map:(List<Map>)listData)
				{
					index++;
					Blob blob = (Blob)map.get("f_points");
					byte[] pointVal = IOUtils.toByteArray(blob.getBinaryStream());
					if(pointVal.length>64)continue;
					byte[] s = Arrays.copyOfRange(pointVal,0,31);
					byte[] e = Arrays.copyOfRange(pointVal,32,63);
					sMap.put(Arrays.toString(s),index);
					eMap.put(Arrays.toString(e),index);
				}
				System.out.println("end:"+index);
			}
			System.out.println("listData:"+listData.size());

		    Set<String> sSet = sMap.keySet();
		    Set<String> eSet = eMap.keySet();

		    for(String str:sSet)
		    {
		    	//if(!eSet.contains(str))System.out.println(sMap.get(str));
		    }
		    List<String> subtractList = (List<String>) CollectionUtils.subtract(sSet, eSet);
		    System.out.println(subtractList.size());

			if(1==1)return null;
		   //System.out.println(myUser.getAge());
		   //System.out.println(myUser.getName());
		   String cacheSql= LoadSqlConfig.instance().getSql("mysql", "queryJUserInfo");
		   System.out.println(cacheSql);
		  // List list = db.searchToMapList(cacheSql);
		   String sql ="insert into j_user values (2,'王小利',21)";
	      // System.out.println(db.update(sql));
		  // System.out.println(list);
		   DBCustorm custorm = new DBCustorm();
		   custorm.setDBName("tsk");
		   custorm.setDBPort("1040");
		   custorm.setDbpwd("cnooc2008");
		   custorm.setDbuser("cnooc");
		   custorm.setDBUrl("192.168.100.66");
		   custorm.setDBType("MSSQL");
		  // DBConn newConn = db.getNewInstanceDBConnByDBCustorm(custorm);
		   try {
			   //List alist = newConn.searchToMapList("select * from T_BASEPROCESSSEG where f_id='10000000004401'");
			   //System.out.println(alist);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	       return "/index.jsp";
	    }
	   @RequestMapping("/second")
	    public String second(Model model) {
	        model.addAttribute("message", "Hello World!");
	        System.out.println(123);
	        return "index.jsp";
	    }
	   @RequestMapping("/third")
	    public String third(Model model) {
		   System.out.println(this.request.getParameter("a"));;
	        model.addAttribute("message", "Hello World!");
	        System.out.println(123);
	        return "redirect:/login/a/index.html";
	    }
	   @RequestMapping("/four")
	    public void four(Model model) throws IOException {
		   File file = new File("C:\\ali213\\up.exe");
		   InputStream isRef =  new FileInputStream(file);
		   OutputStream osRef = response.getOutputStream();
		   byte[] b = new byte[1024];
		   int len = 0;
		   while((len = isRef.read(b))!=-1)osRef.write(b, 0, len);
	    }

	   @RequestMapping(value="/resources/userInfo/{id}/{name}",method=RequestMethod.GET)
	   @ResponseBody
	   public String restFull(@PathVariable String id,@PathVariable String name){
		   System.out.println("id:"+id);
		   System.out.println("name:"+name);
		   JSONObject obj = new JSONObject();
		   obj.put("id", id);
		   obj.put("name", name);
		   return obj.toJSONString();
	   }
	   @RequestMapping(value="/resources/userInfo/{id}/{name}/getInfo/{age}",method=RequestMethod.GET)
	   public void restFullA(@PathVariable String id,@PathVariable String name,@PathVariable String age){
		   System.out.println("id:"+id);
		   System.out.println("name:"+name);
		   System.out.println("age:"+age);
		   JSONObject object = new JSONObject();
		   object.put("id",id);
		   object.put("name", name);
		   object.put("age", age);
		   object.put("a3", "aaaa");
		   object.put("a", "aaaa");
		   this.printStr(object.toString());
	   }
//	   @RequestMapping(value="/login",method=RequestMethod.POST)
//	   public void login(@PathVariable String id,@PathVariable String name,@PathVariable String age){
//		   System.out.println("id:"+id);
//		   System.out.println("name:"+name);
//		   System.out.println("age:"+age);
//		   JSONObject object = new JSONObject();
//		   object.put("a", "aaaa");
//		   object.put("a1", "aaaa");
//		   object.put("a2", "aaaa");
//		   object.put("a3", "aaaa");
//		   object.put("a", "aaaa");
//		   this.printStr(object.toString());
//	   }
}
