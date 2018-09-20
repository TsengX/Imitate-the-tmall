package tmall.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;

import tmall.bean.Admin;
import tmall.util.Page;
/**
 * 实现后台的登录和退出
 * @author Tseng
 *
 */
public class BackServlet extends BaseBackServlet {
	//登录
	public String login(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		//name = HtmlUtils.htmlEscape(name);
		String password = request.getParameter("password");
		
		Admin admin = adminDAO.get(name, password);
		
		if(null == admin) {
			request.setAttribute("msg", "你输入的密码和账户名不匹配");
			return "backLogin.jsp";
		}
		
		request.getSession().setAttribute("admin", admin);
		
		return "@admin";
	}
	
	//退出
	public String logout(HttpServletRequest request, HttpServletResponse response, Page page) {
		request.getSession().removeAttribute("admin");
		return "backLogin.jsp";
	}

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
