package base.bean;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

/*
 * 最基本的连接属性,其他的属性从bean dataSourse中克隆
 */
public class BaseDruidConnConfig {
	private String url;
	private String userName;
	private String passWord;

	public BaseDruidConnConfig() {
		super();
	}

	public BaseDruidConnConfig(String url, String userName, String passWord) {
		super();
		this.url = url;
		this.userName = userName;
		this.passWord = passWord;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public boolean useful() {
		if (this.url == null || this.userName == null || this.passWord == null)
			return false;
		if (StringUtils.trimToNull(this.url) == null)
			return false;
		if (StringUtils.trimToNull(this.userName) == null)
			return false;
		if (StringUtils.trimToNull(this.passWord) == null)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
