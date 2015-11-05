package Tokens;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;


import Base.Constant;

import com.google.common.io.Files;

public class TokenManager<T> {
	     private Map<String,Map<Date,T>> tokenMap = new HashMap<String,Map<Date,T>>(500);
         private TokenManager(){
         }
         private static TokenManager<?> tm = new TokenManager();
         public static TokenManager instance(){
        	 return tm;
         }
         public Map<String,Map<Date,T>> getTokenMap() {
			return tokenMap;
		}
         public Character randomUppercaseChar(){
        	 final int min = 'A';//65
        	 final int max = 'Z';//90
             Random random = new Random();
             int s = random.nextInt(max)%(max-min+1) + min;
             char c = (char)s;
        	 return c;
         }
         
         public String createTokenByT(T t){
        	 String uuid = UUID.randomUUID().toString().replace('-',this.randomUppercaseChar());
        	 if(this.tokenMap.containsKey(uuid))uuid = createTokenByT(t);
        	 return uuid;
         }
         
         /**
          * 添加用户
          * @param t 用户对象,可以使任何对象,一般登录用户信息用map来表示,保存用户名和密码
          * @return 当前token
          */
         public String add(T t){
        	 String token = this.createTokenByT(t);
        	 Map<Date,T> map = new HashMap<Date,T>(1);//登录时间和登录对象
        	 map.put(new Date(), t);
        	 this.tokenMap.put(token, map);
        	 return token;
         }
         
         public Map<String,Map<Date,T>> readObject(){
        	 return null;
         }
         
         public URI getTargetUri(){
        	 URL url = this.getClass().getResource("File/tokens.bin");
        	 System.out.println(url);
        	 URI uri=null;
			 try 
			 {
				if(url!=null)uri = url.toURI();
			 } catch (URISyntaxException e) 
			 { 
			 }
        	 return uri;
         }
         
         /**
          * 写进去
          */
         public void writeingFile(){
			 URI uri = this.getTargetUri();
			 if(uri==null)return;
			 File file = new File(uri);
			 try 
			 {
				FileOutputStream out = new FileOutputStream(file);
				ObjectOutputStream outputStream = new ObjectOutputStream(out);
				outputStream.writeObject(this.tokenMap);
				outputStream.close();
				out.close();
			 } catch (FileNotFoundException e) 
			 {
				 e.printStackTrace();
			 } catch (IOException e) 
			 {
				e.printStackTrace();
			 }
			 
         }
         
         /**
          * 装载已经有过的缓存(请勿随意调用)
          */
         public void loadingFile(){
    	    try 
    	    {
				 URI uri = this.getTargetUri();
				 if(uri==null)return;
				 File file = new File(uri);
				 if(file.length()==0)return;//暂不判断内容有空格的情况
				 FileInputStream fileInputStream = new FileInputStream(file);
				 ObjectInputStream inputStream  = new ObjectInputStream(fileInputStream);
        		 this.tokenMap  = (Map<String,Map<Date,T>>)inputStream.readObject();
        		 fileInputStream.close();
        		 inputStream.close();
			} catch (IOException e) 
			{
				e.printStackTrace();
			} catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
            }
         }
         
         /**
          * 刷新所有的缓存,清除非法的缓存(所谓非法:即Cookie的时间已经超出)
          */
         public void refresh(){
        	 final Set<String> set = this.tokenMap.keySet();
        	 final List<String> keyList = new ArrayList<String>(4);
        	 for(String key:set)
        	 {
        		 Map<Date,?> next = this.tokenMap.get(key);
        		 Date now = new Date();
        		 DateTime dt = new DateTime(now.getTime());
        		 Date target = (Date)new ArrayList(next.keySet()).get(0);
        		 if(dt.plusMillis(Constant.MAXCOOKIEAGE).getMillis()>target.getTime())keyList.add(key);
        	 }
        	 for(String key:keyList)this.tokenMap.remove(key);
         }
         
         /**
          * 通过cookie刷新单独的一个用户状态
          * @return 是否刷新成功,即是否非法
          */
         public boolean refreshByCookie(){
			return false;
         }
         public static void main(String[] args) {
        	Map map = new HashMap();
        	map.put("1", "111");
        	map.put("2", "111");
        	map.put("3", "111");
        	map.put("4", "111");
        	Set<String> set = map.keySet();
        	for(String key :set)map.remove(key);
        	System.out.println(map.size());
		}
}
