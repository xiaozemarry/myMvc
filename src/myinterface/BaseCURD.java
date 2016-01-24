package myinterface;
import java.util.List;
import java.util.Map;


/**
 * 增对某种数据的操作,比如数据,缓存,NOSQL....</br>
 * 这里的方法并不是所有的都实现,根据自己的需要实现不同的方法。比如你的某个类是用来做这个操纵的,但是只需要查询出List<Map>,那么只需重载selectToMapList即可
 *
 * @param <T> 实体
 */
public abstract class BaseCURD<T> {
	
 private BaseCURD(){}
	
 public  List<Map> selectToMapList(){return null;};
 
 public  List<T> selectToBeanList(){return null;};
 
 public  T selectToBean(){return null;};
 
 public  Map selectToMap(){return null;};
}

