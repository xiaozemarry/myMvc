package context.wapper;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class MyHttpServletResponseWapper extends HttpServletResponseWrapper{
	private CharArrayWriter caw;//用于保存原始的输出内容
	public MyHttpServletResponseWapper(HttpServletResponse response) {
		super(response);
		caw = new CharArrayWriter();
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	/**
	 * 重写getWriter方法,当其他地方会调用该方法时,直接把内容追加到本类中定义的caw变量中
	 */
	@Override
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter(caw);
		//return super.getWriter();
	}
	
	/**
	 * 获取标准的输出内容
	 * @return 原本输出到页面的内容
	 */
	public String getResponseResult(){
		return caw.toString();
	}
	
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return super.getOutputStream();
	}
}
