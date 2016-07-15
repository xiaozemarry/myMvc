package module.login.dao;

import java.util.List;

import abstractentity.AbsEntity;
import module.login.bean.User;

public class UserDao extends AbsEntity<User>{

	@Override
	public List<User> findAllEntity() {
		return null;
	}

	public User test(){
		System.out.println(db.getConnection());
		System.out.println(db.getConnection());
		System.out.println(db.getConnection());
		System.out.println(db.getConnection());
		System.out.println(db.getConnection());
		System.out.println(db.getConnection());
		System.out.println(db.getConnection());
		return new User();
	}
	
	

}
