package base;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 控制器继承的基础类
 *
 */
public class HttpBase{
	  private static final Logger logger = Logger.getLogger(HttpBase.class);
	  protected HttpServletRequest request;
	  protected HttpServletResponse response;
	  protected DBConn db = DBConn.instance();
	  @ModelAttribute
	  public void set(HttpServletRequest request,HttpServletResponse response){
		  this.request = request;
		  this.response = response;
	  }
	  
	  /**
	   * mongodb所需要的格式
	   * @param request
	   * @return
	   */
	 public Document reqeustParameterToDocument(HttpServletRequest request){
		 Map<String, Object> map = this.reqeustParameterToMap(request);
		 if(map==null)return null;
		 return new Document(map);
	 }
	  
	 public Document reqeustParameterToDocument(HttpServletRequest request,List<String>  continueKey){
		 Map<String,Object> map = this.reqeustParameterToMap(request,continueKey);
		 if(map==null)return null;
		 return new Document(map);
	 }
	/**
	 * 把参数转换为Map格式
	 * @param request
	 * @return
	 */
	public Map<String,Object> reqeustParameterToMap(HttpServletRequest request){
	     if(request==null)return null;
	     Map<String,String[]> map =  request.getParameterMap();
	     Set<String> set = map.keySet();
	     Map<String,Object> rMap  = new HashMap<String,Object>(set.size());
	     Iterator<String> it = set.iterator();
	     while(it.hasNext())
	     {
	    	 String next = it.next();
	    	 String rVal = null;
	    	 String[] val = map.get(next);
	    	 if(val!=null && val.length>0)rVal = StringUtils.trimToNull(val[0]);
	    	 if(rVal==null)continue;
	    	 rMap.put(next,rVal);
	     }
	     return rMap;
	 }
	/**
	 * 把参数转换为Map格式,但是某些参数不是我们需要的
	 * @param request HeetServletRequest
	 * @param continueKey 直接略过的(比如参数中a这个参数,假如咋们不需要用a,所以这个集合中就包括a)
	 * @return
	 */
	 public Map<String,Object> reqeustParameterToMap(HttpServletRequest request,List<String>  continueKey){
	     if(request==null)return null;
	     Map<String,String[]> map =  request.getParameterMap();
	     Set<String> set = map.keySet();
	     Map<String,Object> rMap  = new HashMap<String,Object>(set.size());
	     Iterator<String> it = set.iterator();
	     while(it.hasNext())
	     {
	    	 String next = it.next();
	    	 if(continueKey!=null && continueKey.contains(next))continue;
	    	 String rVal = null;
	    	 String[] val = map.get(next);
	    	 if(val!=null && val.length>0)rVal = StringUtils.trimToNull(val[0]);
	    	 if(rVal==null)continue;
	    	 rMap.put(next,rVal);
	     }
	     return rMap;
	  }
	  
	  public void printStream(final File file){
		  
	  }
	  
	  public void printStream(final InputStream input){
		  
	  }  
	  
	  public void printStream(final byte bytes[]){
		  
	  }
	  public void printJson(final String source,final boolean isIE6){
		  try 
		  {
			if(isIE6)
			    this.response.setHeader("Content-type","text/json;charset=UTF-8");
			else
				this.response.setHeader("Content-type","application/json;charset=UTF-8");
			
			this.response.setCharacterEncoding("UTF-8");
			PrintWriter writer = this.response.getWriter();
			writer.print(source);
			writer.close();
		  } catch (IOException e) 
		  {
			  logger.error("{Attention--Should Not Like This}", e);
		  }
	  }
	  /**
	   * 先前台输出内容
	   * @param source 需要输出的内容
	   * @param strings 需要设置的头部信息(可不填)
	   */
	  public void printStr(String source,String...strings){
		  try 
		  {
			PrintWriter writer = this.response.getWriter();
			writer.print(source);
			writer.close();
		  } catch (IOException e) 
		  {
			  logger.error("{Attention--Should Not Like This}", e);
		  }
	   }
}