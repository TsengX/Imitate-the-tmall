package tmall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import tmall.bean.Admin;

public class BackServletFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/* 1. 在web.xml配置文件中，让所有的请求都会经过BackServletFilter
	 * 2. 假设访问的路径是: http://127.0.0.1:8080/tmall/admin_category_list
	 * 3. 在BackServletFilter 中通过request.getRequestURI()取出访问的uri: /tmall/admin_category_list
	 * 4. 然后截掉/tmall，得到路径/admin_category_list
	 * 5. 判断其是否以/admin开头
	 * 6. 如果是，那么就取出两个_之间的字符串，category，并且拼接成/categoryServlet，通过服务端跳转到/categoryServlet
	 * 7. 在跳转之前，还取出了list字符串，然后通过request.setAttribute的方式，借助服务端跳转，传递到categoryServlet里去
	 * 
	 */
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String contextPath = request.getServletContext().getContextPath();
		String uri = request.getRequestURI();
		uri = StringUtils.remove(uri, contextPath);
		if(uri.startsWith("/admin_")) {
			//登录验证
			Admin admin = (Admin) request.getSession().getAttribute("admin");
        	if(null == admin) {
        		response.sendRedirect("backLogin.jsp");
        		return;
        	}
        	
			String servletPath = StringUtils.substringBetween(uri, "_", "_") + "Servlet";
			String method = StringUtils.substringAfterLast(uri, "_");	//最后一个"_"字符后面的字符串
			request.setAttribute("method", method);
			req.getRequestDispatcher("/" + servletPath).forward(request, response);
			return;
			
			
		}
		//后台登录
		if(uri.startsWith("/back_")) {
			String method = StringUtils.substringAfterLast(uri, "_");
			request.setAttribute("method", method);
			req.getRequestDispatcher("/backServlet").forward(request, response);
			return;
		}
		
		chain.doFilter(request, response);
		
	}


	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}
