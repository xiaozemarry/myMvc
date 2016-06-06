package testtransaction.userinfo;

import java.util.Map;

import base.DruidDBConnection;

public class UserInfo{
	public boolean saveUser() {
		boolean re = false;
		
		return re;
	}
	
	public boolean updateUserSalary(DruidDBConnection db) throws Exception{
		boolean re = false;
		String sql1 = "update user set salary = salary+10 where id = 1";
		String sql2 = "update user set salary = salary-10 where id = 2";
		db.update(sql1);
		db.update(sql2);
		db.update(sql1);
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
