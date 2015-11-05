package c;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import Tools.LoadSqlConfig;
import Base.DBConn;
import Base.DBCustorm;
import Base.HttpBase;

/**
 * 
 * SHOW VARIABLES LIKE 'character_set_%';
 * @author jacky-chueng
 *
 */
@Controller
public class HelloWord extends HttpBase{
	public HelloWord(){
		//System.out.println("走一次实例化一下哦!");
	}
		@RequestMapping("/helloWorld/a.do")
	    public String helloWorld(Model model) throws Exception {
		   //System.out.println(myUser.getAge());
		   //System.out.println(myUser.getName());
		   String cacheSql= LoadSqlConfig.instance().getSql("mysql", "queryJUserInfo");
		   System.out.println(cacheSql);
		   List list = db.searchToMapList(cacheSql);
		   String sql ="insert into j_user values (2,'王小利',21)";
	      // System.out.println(db.update(sql));
		   System.out.println(list);
		   DBCustorm custorm = new DBCustorm();
		   custorm.setDBName("tsk");
		   custorm.setDBPort("1040");
		   custorm.setDbpwd("cnooc2008");
		   custorm.setDbuser("cnooc");
		   custorm.setDBUrl("192.168.100.66");
		   custorm.setDBType("MSSQL");
		   DBConn newConn = db.getNewInstanceDBConnByDBCustorm(custorm);
		   try {
			   //List alist = newConn.searchToMapList("select * from T_BASEPROCESSSEG where f_id='10000000004401'");
			   //System.out.println(alist);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	       return "/index.jsp";
	    }
	   @RequestMapping("/second.do")
	    public String second(Model model) {
	        model.addAttribute("message", "Hello World!");
	        System.out.println(123);
	        return "index.jsp";
	    }
	   @RequestMapping("/third.do")
	    public String third(Model model) {
		   System.out.println(this.request.getParameter("a"));;
	        model.addAttribute("message", "Hello World!");
	        System.out.println(123);
	        return "redirect:/login/a/index.html";
	    }
	   @RequestMapping("/four.do")
	    public void four(Model model) throws IOException {
		   File file = new File("C:\\ali213\\up.exe");
		   InputStream isRef =  new FileInputStream(file);
		   OutputStream osRef = response.getOutputStream();
		   byte[] b = new byte[1024];
		   int len = 0;
		   while((len = isRef.read(b))!=-1)osRef.write(b, 0, len);
	    }
}