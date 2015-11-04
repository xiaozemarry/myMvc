package Tokens;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.IOUtils;

import com.google.common.io.Files;

public class TokenManager<T> {
	     private Map<String,Map<Date,T>> tokenMap = new HashMap<String,Map<Date,T>>();
         private TokenManager(){}
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
          * @param t
          * @return 当前token
          */
         public String add(T t){
        	 String token = this.createTokenByT(t);
        	 Map<Date,T> map = new HashMap<Date,T>(1);//登录时间和登录对象
        	 map.put(new Date(), t);
        	 tokenMap.put(token, map);
        	 return token;
         }
         
         public Map<String,Map<Date,T>> readObject(){
        	 return null;
         }
         
         /**
          * 装载已经有过的缓存(请勿随意调用)
          */
         public static void loadingFile(){
        	 URL url = TokenManager.class.getResource("file/tokens.bin");
        	 try 
        	 {
				 URI uri = url.toURI();
				 File file = new File(uri);
				 ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
				 HashMap aa = new HashMap();
				 aa.put("a","bb");
				 outputStream.writeObject(aa);
        		 ObjectInputStream inputStream  = new ObjectInputStream(new FileInputStream(file));
        		 Object obj = inputStream.readObject();
        		 System.out.println(obj);
        		 
			} catch (IOException e) 
			{
				e.printStackTrace();
			} catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
            } catch (URISyntaxException e1) 
    	    {
			    e1.printStackTrace();
		    }
         }
         
         /**
          * 刷新所有的缓存,清除非法的缓存(所谓非法:即Cookie的时间已经超出)
          */
         public void refresh(){
        	 
         }
         
         /**
          * 通过cookie刷新单独的一个用户状态
          * @return 是否刷新成功,即是否非法
          */
         public boolean refreshByCookie(){
			return false;
         }
         public static void main(String[] args) {
        	 TokenManager.loadingFile();
			File file = new File("c:\\a.txt");
			try {
				Files.write("".getBytes(), file);
				Files.touch(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
