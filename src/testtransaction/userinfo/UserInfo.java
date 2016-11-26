package testtransaction.userinfo;

import java.sql.Connection;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;

import abstractentity.AbsEntity;
import module.login.bean.User;

public class UserInfo extends AbsEntity<User>{
	public boolean saveUser() {
		boolean re = false;
		
		return re;
	}
	
	public boolean updateUserSalary() throws Exception{
		System.out.println(JSONArray.toJSON(db.searchToMap("select * from user")));
		if(true)return true;
		boolean re = false;
		String sql1 = "update userinfo set salary = salary+100 where id = 1";
		String sql2 = "update userinfo set salary = salary-100 where id = 2";
//		Connection connection =	db.getConnection();
//		connection.setAutoCommit(false);
//		connection.commit();
		db.update(sql1);
		db.update(sql1);
		db.update(sql1);
		db.update(sql1);
		db.update(sql1);
		db.update(sql1);
		db.update(sql1);
		db.update(sql1);
		db.update(sql2);
		//db.searchToMap("select * from userinfo");
//		String flag = null;
//		flag.equals("");
		return re;
	}
	
	public boolean deleteUserById(String id){
		boolean re = false;
		
		return re;
	}
	
	public boolean insertUserByMap(Map<String,Object> userMap){
		boolean re = false;
		
		return re;
	}
}
