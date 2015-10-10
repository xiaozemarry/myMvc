package Context;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.User;

/**
 * 记录用户登录信息
 * @author jacky-chueng
 *
 */
public class LoginUserInfoPool {
   private static LoginUserInfoPool instance = new LoginUserInfoPool();
   private final  Map<String,User> LoginSessionMap  =  new LinkedHashMap<String, User>();

   public Map<String, User> getLoginSessionMap() {
	      return LoginSessionMap;
  }
	private LoginUserInfoPool(){}
	   public synchronized static LoginUserInfoPool instance(){
		   if(instance==null)instance = new LoginUserInfoPool();
		   return instance;
	   }
   /**
    * 是否已经登录进系统
    * @param request
    * @return
    */
   public boolean isLoginIn(HttpServletRequest request){
	   HttpSession session = request.getSession();
	   System.out.println(request.getRequestURI());
	   System.out.println(request.getRequestURI());
	   
	   System.out.println(session.getId());
	   return this.getLoginSessionMap().containsKey(session.getId());
   }
}
