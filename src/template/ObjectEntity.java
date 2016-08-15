package template;

import java.util.List;
import java.util.Map;

public class ObjectEntity {
	private String packageName;
	private List<String> importName;
	private String className;
	private Map<String, Class<?>> fields;

	public ObjectEntity(String packageName, List<String> importName, String className, Map<String, Class<?>> fields) {
		super();
		this.packageName = packageName;
		this.importName = importName;
		this.className = className;
		this.fields = fields;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Map<String, Class<?>> getFields() {
		return fields;
	}

	public void setFields(Map<String, Class<?>> fields) {
		this.fields = fields;
	}

	public List<String> getImportName() {
		return importName;
	}

	public void setImportName(List<String> importName) {
		this.importName = importName;
	}

}
