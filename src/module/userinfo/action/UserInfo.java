package module.userinfo.action;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import base.HttpBase;

public class UserInfo extends HttpBase{
	private static final Logger logger = Logger.getLogger(UserInfo.class);
	@RequestMapping(value="/getUserInfo",method=RequestMethod.POST)
    public void getUserInfo(){
    }
}
