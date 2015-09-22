package Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 记录用户登录信息
 * @author jacky-chueng
 *
 */
public class LoginUserInfoPool {
   private static LoginUserInfoPool instance = new LoginUserInfoPool();
   private LoginUserInfoPool(){}
   public synchronized static LoginUserInfoPool instance(){
	   if(instance==null)instance = new LoginUserInfoPool();
	   return instance;
   }
   /**
    * 是否登录进系统
    * @param request
    * @return
    */
   public boolean isLoginIn(HttpServletRequest request){
	   boolean r = false;
	  // String 
	   HttpSession session = request.getSession();
	   System.out.println(session.getId());
	   return r;
   }
}
