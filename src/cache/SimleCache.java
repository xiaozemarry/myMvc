package cache;

import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.time.DateTime;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.SortingParams;

public class SimleCache {
	public static void main(String[] args) {
		DateTime dt = new DateTime();
		//dt.plusWeeks(arg0)
		 if(1==1)return;
      Jedis jedis = new Jedis("192.168.31.205",6379);
     // jedis.auth("admin");
//      User user = new User();
//      user.setAge(20);
//      user.setName("张三");
      //JedisPool jedisPool =  new  JedisPool(null);
     
      
      Map<String, String> map = new LinkedHashMap<String, String>();
      map.put("aa1", "aa");
      map.put("aa2", "aa");
      map.put("aa3", "aa");
      map.put("aa4", "aa");
      jedis.hmset("aaaa",map);
      
      System.out.println(jedis.set("name1","张三"));
      System.out.println(jedis.set("name2","张三"));
      System.out.println(jedis.set("name3","张三1"));
      System.out.println(jedis.get("name"));
      
      /*jedis.lpush("mylist", "1");  
      jedis.lpush("mylist", "4");  
      jedis.lpush("mylist", "6");  
      jedis.lpush("mylist", "3");  
      jedis.lpush("mylist", "0"); */
      
      System.out.println(jedis.sort("mylist"));
      SortingParams sortingParams = new SortingParams();
      //sortingParams.
	}
}
