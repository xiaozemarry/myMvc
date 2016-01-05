package bean;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * 测试1
 * 测试家里提交的新代码
 * 测试家里提交的新代码1
 * 测试家里提交的新代码2
 * 测试家里提交的新代码2(项目remoteMvc1)  dasda  df
 * @author jacky-chueng
 *
 */
public class User implements Serializable{
	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
   public static void main(String[] args) throws MalformedURLException, IOException {
	 System.out.println(IOUtils.toString(new URL("http://10.246.146.31/IPMSAPI/PDS/UAS/GetEnterpriseOrgByUserId?userid=ASM_00014e76cf11cd2a").openStream()));
}
}