package abstractentity;

/**
 * 数据库中的主键字段实体
 */
public class PKOfDBEntity {
	private String pkName;
	private String pkValue;
	
	public PKOfDBEntity(String pkName, String pkValue) {
		super();
		this.pkName = pkName;
		this.pkValue = pkValue;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getPkValue() {
		return pkValue;
	}

	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}

}
