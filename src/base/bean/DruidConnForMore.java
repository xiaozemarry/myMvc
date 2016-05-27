package base.bean;

import java.util.Map;

/**
 * 多数据源配置
 *
 */
public class DruidConnForMore {
	//key表示默认的数据源,value表示对应的数据源的beanid
    private Map<String,String> more;

	public Map<String, String> getMore() {
		return more;
	}

	public void setMore(Map<String, String> more) {
		this.more = more;
	}
    
}
