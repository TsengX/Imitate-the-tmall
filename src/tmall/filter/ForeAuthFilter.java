package tmall.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import tmall.bean.User;

/**
 * 前台登录状态管理
 * @author Tseng
 *
 */
public class ForeAuthFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String contextPath = request.getServletContext().getContextPath();
		
		//存放不需要登录也能访问的路径
		String[] noNeedAuthPage = new String[]{
			"home",
			"checkLogin",
			"register",
            "loginAjax",
            "login",
            "product",
            "category",
            "search"
		};
		
		String uri = request.getRequestURI();
		uri = StringUtils.remove(uri, contextPath);
		// 如果访问的地址是/fore开头，又不是/foreServlet
		if(uri.startsWith("/fore") && !uri.startsWith("/foreServlet")) {
			String method = StringUtils.substringAfterLast(uri, "/fore");
			//判断cart是否是在noNeedAuthPage 
			if(!Arrays.asList(noNeedAuthPage).contains(method)) {
				User user = (User) request.getSession().getAttribute("user");
				if(null==user) {
					response.sendRedirect("login.jsp");
					return;
				}
			}
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
