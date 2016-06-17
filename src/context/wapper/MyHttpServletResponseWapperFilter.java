package context.wapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import base.Constant;

public class MyHttpServletResponseWapperFilter implements Filter{
	private final static Logger logger = Logger.getLogger(MyHttpServletResponseWapperFilter.class);
	@Override
	public void destroy() {
		logger.info("MyHttpServletResponseWapperFilter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		 MyHttpServletResponseWapper my = new MyHttpServletResponseWapper((HttpServletResponse) response);
		 chain.doFilter(request,my);//filter others
		 String html = my.getResponseResult();
		 long start = System.currentTimeMillis();
		 logger.info("start to parse html");
		 int errorFlag = 0;//超过一定的数量就不找了,避免死循环和浪费资源
		 boolean foundHeadTag = false;
 		 Document elements = Jsoup.parse(html);
 		 int size = elements.childNodeSize();
 		 Element next = elements;
 		 Element headElement = null;
 		 do {
 			 size = elements.childNodeSize();
 			 if(size>0){
 				 next  = next.child(0);
 	 			 String nodeName = next.nodeName();
 	 			 if("head".equals(nodeName)){
 	 				foundHeadTag = true;
 	 				headElement = next;
 	 				break;
 	 			 }
 			 }
 			 if(errorFlag++>100)break;
		 } while (size>0);
 		 
 		 long spend = System.currentTimeMillis()-start;
 		 if(foundHeadTag){//找到head标签
 			logger.info(String.format("parse html and find <HEAD> tag success,spend time:%sms,now time:%s",new Object[]{spend,Constant.SDFYMDHMS.format(new Date())}));
 			headElement.append("<!--自动添加的文件开始-->");
 	 		headElement.append("<meta name=\"desaaaaaaaaaaaription1\" content=\"404 Error Page\">");
 	 		headElement.append("<meta name=\"desaaaaaaaaaaaription2\" content=\"404 Error Page\">");
 	 		headElement.append("<meta name=\"desaaaaaaaaaaariptionddd3\" content=\"404 Error Page\">");
 	 		headElement.append("<!--自动添加的文件结束-->");
 			PrintWriter pw = response.getWriter();
 			pw.print(elements);
 			pw.flush();
 			pw.close();
 		 }else{
 			logger.info("can not found <HEAD> tag,use default,spend time:"+spend);
 			PrintWriter pw = response.getWriter();
 			pw.print(html);
 			pw.flush();
 			pw.close();
 		 }
			elements = null;
			html = null;
 		 //Element childs = elements.child(0).child(0);//找到head节点
 		 //Collections.reverse(childs.childNodes());
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.info("MyHttpServletResponseWapperFilter init...");
	}

}
