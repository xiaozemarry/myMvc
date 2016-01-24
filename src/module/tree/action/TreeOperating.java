package module.tree.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import base.HttpBase;

@Controller
public class TreeOperating extends HttpBase {

	@RequestMapping("/treeInsert.do")
	public void insert() {
		try 
		{  
			//int result =   db.autoInsertByHttpParams(request, db, "tree", "id","a");
			//db.autoUpdateByHttpParams(request, db, "tree","id");
			//System.out.println(result);
			this.printStr("success");
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
