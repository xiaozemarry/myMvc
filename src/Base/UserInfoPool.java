package Base;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 对象池 将一些对象保存为全局变量
 * 
 * 
 * @author masl
 * 
 */
public class UserInfoPool implements Serializable {


	private static final long serialVersionUID = 1L;
	static private UserInfoPool instance; // 唯一实例的引用
	private Hashtable pools = new Hashtable(); // 创建一个Hashtable对象
	
	private static Map onlineUser = new HashMap(); // 实时监测在线用户（周期为：10秒）
	
	private static Log log = null;
	public static synchronized UserInfoPool getInstance() {
		if (instance == null) {
			instance = new UserInfoPool();
		}
		log = LogFactory.getLog(UserInfoPool.class);
		return instance;
	}

	/**
	 * 增加一个对象到池中
	 * @param key
	 * @param value
	 */

	private void add(String key, Object value) {
		pools.put(key, value);
	}
	private Object get(String key) {
		return pools.get(key);
	}

	private void Remove(String key) {
		pools.remove(key);
	}
	/**
	 * 添加在线用户
	 */
	synchronized public void addOnlineUser(String userid){
		onlineUser.put( userid,new Date().getTime());
	}
	/**
	 * 移除在线用户
	 */
	synchronized public void removeOnlineUser(String userid){
		
		onlineUser.remove(userid);
	
	}
	/**
	 * 更新
	 */
	public void updataOnlineUserTime(String userid){
		
		this.addOnlineUser(userid);
		
		Iterator rs =onlineUser.entrySet().iterator();
		long currentTime=new Date().getTime();
		while (rs.hasNext()){
			
			Map.Entry ent= (Map.Entry) rs.next();
			Long temptime= Long.parseLong(String.valueOf(ent.getValue()));
			String k= String.valueOf(ent.getKey());
			if(currentTime-temptime>65000){
				removeOnlineUser(k);
			}
		}
		
	}

	/**
	 * 获得在线用户
	 */
	public  Map  GetOnlineUser(){
		return this.onlineUser;
	}
	// 打印内存注册信息
	public void toView() {
      
	}
	
	 /**
     * 设置用户身份信息
     * @param request
     * @return 2010-6-3
     */
	public void RemoveCurrentUser(HttpServletRequest request)
	{
		
		
		UserInfoPool.getInstance().Remove(request.getRemoteHost());
		/**
		 * 应急流程监听用户下线
		 */
		DBCustorm dbc=getCurrentUser(request);
		if (dbc!=null&&dbc.getUserid()!=null) {
			UserInfoPool.getInstance().Remove(dbc.getUserid());
			log.info(new Date()+" 用户:"+dbc.getUserid()+" IP:"+dbc.getUserIp()+" 离开系统了。");
			System.out.println("移除池中用户信息：但实际并未移除");
			
			request.getSession().invalidate();
		}
	}
	
	 /**
     * 设置用户身份信息
     * @param request
     * @return 2010-6-3
     */
	public void setCurrentUser(DBCustorm CustormInfo,HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		session.setAttribute("CustormInfo",CustormInfo);
		System.out.println("将用户信息存入Sessions KeyNmae：CustormInfo IP:"+CustormInfo.getUserIp()+" "+(new Date()));
		
		//UserInfoPool.getInstance().add(CustormInfo.getUserid(), CustormInfo);
		String terminal_id=CustormInfo.getTerminalID();
		if(terminal_id==null){
			terminal_id=CustormInfo.getUserIp();
			CustormInfo.setTerminalID(terminal_id);
		}
		else if(!terminal_id.equals(CustormInfo.getUserIp())){
			UserInfoPool.getInstance().add(CustormInfo.getUserIp(), CustormInfo);
		}
		
		UserInfoPool.getInstance().add(terminal_id, CustormInfo);
		
		//新增在线用户
		UserInfoPool.getInstance().addOnlineUser(CustormInfo.getUserid());
		/**
		 * 应急流程监听用户上线
		 */
		log.info(new Date()+" 用户:"+CustormInfo.getUserid()+" IP:"+CustormInfo.getUserIp()+" 进入系统了。");
	}
	
	
	/**
	 * 获取当前用户身份信息
	 * @param request
	 * @return 2010-6-3
	 */
	public DBCustorm getCurrentUser(HttpServletRequest request)
	{
		Object obj=null;
		String terminal_id=request.getParameter("terminal_id");
		if(terminal_id!=null && !terminal_id.equals("null") && !terminal_id.equals("undefined")){
			obj=UserInfoPool.getInstance().get(terminal_id);
			if(obj!=null && obj instanceof DBCustorm){
				request.getSession().setAttribute("CustormInfo", obj);
				return (DBCustorm)obj;
			}
		}
		
		HttpSession session=request.getSession();
		obj=session.getAttribute("CustormInfo");
		DBCustorm dd = new DBCustorm();
		if(obj!=null){
			try {
				dd = (DBCustorm)obj;
			} catch (Exception e) {
			}
			return dd;
		}
		
		if(terminal_id==null || terminal_id.equals("null") || terminal_id.equals("undefined")){
			obj=UserInfoPool.getInstance().get(request.getRemoteHost());
			if(obj!=null && obj instanceof DBCustorm){
				return (DBCustorm)obj;
			}
		}
		return null; 
	}
	
	public void setCurrentUser(DBCustorm CustormInfo){
		String terminal_id=CustormInfo.getTerminalID();
		String user_ip=CustormInfo.getUserIp();
		UserInfoPool.getInstance().add(terminal_id, CustormInfo);
		if(!terminal_id.equals(user_ip)){
			UserInfoPool.getInstance().add(user_ip, CustormInfo);
		}
	}
	
	public DBCustorm getCurrentUser(String terminal_id){
		Object obj=UserInfoPool.getInstance().get(terminal_id);
		if(obj!=null && obj instanceof DBCustorm){
			return (DBCustorm)obj;
		}
		return null;
	}
	
	
	/**
	 * 得到所有用户信息
	 * @return
	 */
	public Hashtable getPools() {
		return pools;
	}

	

}
