package Tokens;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class TokenManager<T> {
	     private Map<String,T> tokenMap = new HashMap<String,T>();
         private TokenManager(){}
         private static TokenManager<?> tm = new TokenManager();
         public static TokenManager instance(){
        	 return tm;
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
          * 从新
          * @param t
          * @return 当前token
          */
         public String add(T t){
        	 String k = this.createTokenByT(t);
        	 tokenMap.put(k, t);
        	 return k;
         }
         public static void main(String[] arg) {
        	 System.out.println(TokenManager.instance().createTokenByT(null));
        	 System.out.println(TokenManager.instance().createTokenByT(null));
        	 System.out.println(TokenManager.instance().createTokenByT(null));
		}
}
