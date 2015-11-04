package Beans.Login;

import java.io.Serializable;

/**
 * 登录用户信息
 */
public class UserInfo implements Serializable{
	private String userId;
	private String userName;
	private String passWord;
    public UserInfo() {
    }
    
	public UserInfo(String userId, String userName, String passWord) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.passWord = passWord;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

}
