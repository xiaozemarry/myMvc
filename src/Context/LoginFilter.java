package Context;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		//System.out.println(request.getRequestURI());
		//System.out.println(request.getRequestURL());
		//boolean isLogin = LoginUserInfoPool.instance().isLoginIn(request);
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	public static void main(String[] args) {
		// aa ab ac ad
		// ba bb bc bd
		// ca cb cc cd
		// da dc db da
		// aaa aab aac aad x4
		// aaa aaab aaac aaad x4
		System.out.println(Math.round(11.5));
		System.out.println(Math.round(-11.5));

		final String str[] = new String[] { "a", "b", "c", "d" };

	}

	public static Set<String> org(String str[], Set<String> set, int index) {
		if (index > str.length - 1)
			return set;
		for (int i = 0; i < str.length; i++) {

		}
		return set;
	}

	public class a extends Thread implements Runnable {
		public void run() {

		}
	}

}
