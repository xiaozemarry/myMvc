package cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

public class SimleCache {
	public static void main(String[] args) {
      Jedis jedis = new Jedis("192.168.10.253",6379);
     // jedis.auth("admin");
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
