package tmall.servlet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.util.HtmlUtils;

import tmall.bean.Category;
import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.bean.PropertyValue;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.comparator.ProductAllComparator;
import tmall.comparator.ProductDateComparator;
import tmall.comparator.ProductPriceComparator;
import tmall.comparator.ProductReviewComparator;
import tmall.comparator.ProductSaleCountComparator;
import tmall.dao.CategoryDAO;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
import tmall.util.Page;

public class ForeServlet extends BaseForeServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//首页
	public String home(HttpServletRequest request, HttpServletResponse response, Page page) {
		//疑惑, 为什么不用 继承的categoryDAO
		List<Category> cs = new CategoryDAO().list();
		//为每个Category对象，设置products属性
		new ProductDAO().fill(cs);
		//为每个Category对象，设置productsByRow属性
		new ProductDAO().fillByRow(cs);
		request.setAttribute("cs", cs);
		
		return "home.jsp";
	}
	//registerPage.jsp注册页面
	public String register(HttpServletRequest request, HttpServletResponse response, Page page) {
		//获取账号密码
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		//把账号里的特殊符号进行转义
		name = HtmlUtils.htmlEscape(name);
		//注册名是否已存在
		boolean exist = userDAO.isExist(name);
		
		if(exist) {
			request.setAttribute("msg", "会员名已被注册,不能使用该名称");
			return "register.jsp";
		}
		
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		userDAO.add(user);
				
		return "@registerSuccess.jsp";
	}
	//登录
	public String login(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		name = HtmlUtils.htmlEscape(name);
		String password = request.getParameter("password");
		
		User user = userDAO.get(name, password);
		if(null == user) {
			request.setAttribute("msg", "你输入的密码和账户名不匹配，是否忘记密码或忘记会员名");
			return "login.jsp";
		}
		request.getSession().setAttribute("user", user);
		
		return "@forehome";
	}
	//退出
	public String logout(HttpServletRequest request, HttpServletResponse response, Page page) {
		request.getSession().removeAttribute("user");
		return "@forehome";
	}
	//产品详情页面
	public String product(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		//获取这个产品对应的单个图片集合
		List<ProductImage> productSingleImages = productImageDAO.list(p, ProductImageDAO.type_single);
		//获取这个产品对应的详情图片集合
		List<ProductImage> productDetailImages = productImageDAO.list(p, ProductImageDAO.type_detail);
		p.setProductSingleImages(productSingleImages);
		p.setProductDetailImages(productDetailImages);
		//获取产品的所有属性值
		List<PropertyValue> pvs = propertyValueDAO.list(p.getId());
		//获取产品对应的所有的评价
		List<Review> reviews = reviewDAO.list(p.getId());
		
		//设置产品的销量和评价数量
		productDAO.setSaleAndReviewNumber(p);
		
		request.setAttribute("reviews", reviews);
		request.setAttribute("p", p);
		request.setAttribute("pvs", pvs);
		
		return "product.jsp";
	}
	//当前是否登录状态
	public String checkLogin(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		if(null!=user)
			return "%success";
		return "%fail";
	}
	
	public String loginAjax(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		name = HtmlUtils.htmlEscape(name);
		String password = request.getParameter("password");
		
		User user = userDAO.get(name, password);
		if(null == user) {
			return "%fail";
		}
		request.getSession().setAttribute("user", user);
		return "%success";
	}
	//产品分类页面
	public String category(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		
		Category c = new CategoryDAO().get(cid);
		productDAO.fill(c);
		productDAO.setSaleAndReviewNumber(c.getProducts());
		
		String sort = request.getParameter("sort");
		if(null!=sort) {
			switch(sort) {
			case "review":
				Collections.sort(c.getProducts(), new ProductReviewComparator());
				break;
			case "date":
				Collections.sort(c.getProducts(), new ProductDateComparator());
				break;
			case "saleCount":
				Collections.sort(c.getProducts(), new ProductSaleCountComparator());
				break;
			case "price":
				Collections.sort(c.getProducts(), new ProductPriceComparator());
				break;
			case "all":
				Collections.sort(c.getProducts(), new ProductAllComparator());
				break;
			}
		}
		
		request.setAttribute("c", c);
		
		return "category.jsp";
	}
	//搜索
	public String search(HttpServletRequest request, HttpServletResponse response, Page page) {
		String keyword = request.getParameter("keyword");
		List<Product> ps = productDAO.search(keyword, 0, 20);
		productDAO.setSaleAndReviewNumber(ps);
		request.setAttribute("ps", ps);
		
		return "searchResult.jsp";
	}
	
	public String buyone(HttpServletRequest request, HttpServletResponse resposne, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		int num = Integer.parseInt(request.getParameter("num"));
		
		Product p = productDAO.get(pid);
		int oiid = 0;
		
		User user =  (User) request.getSession().getAttribute("user");
		boolean found = false;
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		for (OrderItem oi : ois) {
			if(oi.getProduct().getId() == p.getId()) {
				oi.setNumber(oi.getNumber() + num);
				orderItemDAO.update(oi);
				found = true;
				oiid = oi.getId();
				break;
			}
		}
		if(!found) {
			OrderItem oi = new OrderItem();
			oi.setUser(user);
			oi.setNumber(num);
			oi.setProduct(p);
			orderItemDAO.add(oi);
			oiid = oi.getId();
		}
		
		return "@forebuy?oiid=" + oiid;
	}
	//结算页面
	public String buy(HttpServletRequest request, HttpServletResponse response, Page page) {
		String[] oiids = request.getParameterValues("oiid");
		List<OrderItem> ois = new ArrayList<>();
		//订单总价格
		float total = 0;
		
		for (String strid : oiids) {
			int oiid = Integer.parseInt(strid);
			OrderItem oi = orderItemDAO.get(oiid);
			total += oi.getProduct().getPromotePrice() * oi.getNumber();
			ois.add(oi);
		}
		
		request.getSession().setAttribute("ois", ois);
		request.setAttribute("total", total);
		
		return "buy.jsp";
	}
	//加入购物车
	public String addCart(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		int num = Integer.parseInt(request.getParameter("num"));
		
		User user = (User) request.getSession().getAttribute("user");
		boolean found = false;
		
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		//如果该用户的购物车存在此产品
		for (OrderItem oi : ois) {
			if(oi.getProduct().getId()==p.getId()) {
				oi.setNumber(oi.getNumber() + num);
				orderItemDAO.update(oi);
				found = true;
				break;
			}
		}
		//如果该用户的购物车没有此产品,就新增一个OrderItem
		if(!found) {
			OrderItem oi = new OrderItem();
			oi.setUser(user);
			oi.setNumber(num);
			oi.setProduct(p);
			orderItemDAO.add(oi);
		}
		
		return "%success";
	}
	//查看购物车
	public String cart(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		request.setAttribute("ois", ois);
		
		return "cart.jsp";
	}
	
	//购物车调整某产品数量
	public String changeOrderItem(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		if(null==user)
			return "%fail";
		
		int pid = Integer.parseInt(request.getParameter("pid"));
		int number = Integer.parseInt(request.getParameter("number"));
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		for (OrderItem oi : ois) {
			if(oi.getProduct().getId()==pid){
				oi.setNumber(number);
				orderItemDAO.update(oi);
				break;
			}
		}
		
		return "%success";
	}
	//购物车删除订单项
	public String deleteOrderItem(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		if(null==user)
			return "%fail";
		int oiid = Integer.parseInt(request.getParameter("oiid"));
		orderItemDAO.delete(oiid);
		return "%success";
	}
	//创建订单
	public String createOrder(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		
		List<OrderItem> ois = (List<OrderItem>) request.getSession().getAttribute("ois");
		if(ois.isEmpty())
			return "@login.jsp";
		//获取地址，邮编，收货人，用户留言等信息
		String address = request.getParameter("address");
		String post = request.getParameter("post");
		String receiver = request.getParameter("receiver");
		String mobile = request.getParameter("mobile");
		String userMessage = request.getParameter("userMessage");
		
		Order order = new Order();
		//根据当前时间加上一个4位随机数生成订单号
		String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
		
		order.setOrderCode(orderCode);
		order.setAddress(address);
		order.setPost(post);
		order.setReceiver(receiver);
		order.setMobile(mobile);
		order.setUserMessage(userMessage);
		order.setCreateDate(new Date());
		order.setUser(user);
		order.setStatus(orderDAO.waitPay);
		
		orderDAO.add(order);
		float total = 0;
		for (OrderItem oi : ois) {
			oi.setOrder(order);
			orderItemDAO.update(oi);
			total += oi.getProduct().getPromotePrice() * oi.getNumber();
		}
		
		return "@forealipay?oid=" + order.getId() + "&total=" + total;
	}
	//确认支付
	public String alipay(HttpServletRequest request, HttpServletResponse response, Page page) {
		return "alipay.jsp";
	}
	//付款成功
	public String payed(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order order = orderDAO.get(oid);
		order.setStatus(orderDAO.waitDelivery);
		order.setPayDate(new Date());
		orderDAO.update(order);
		request.setAttribute("o", order);
		return "payed.jsp";
	}
	//我的订单
	public String bought(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		//查询user所有的状态不是"delete" 的订单集合os
		List<Order> os = orderDAO.list(user.getId(),orderDAO.delete);
		
		orderItemDAO.fill(os);
		request.setAttribute("os", os);
		return "bought.jsp";
	}
	
	//删除订单
	public String deleteOrder(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		o.setStatus(orderDAO.delete);
		orderDAO.update(o);
		return "%success";
	}
	//确认收货
	public String confirmPay(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		orderItemDAO.fill(o);
		request.setAttribute("o", o);
		return "confirmPay.jsp";
	}
	//确认收货成功
	public String orderConfirmed(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		o.setStatus(orderDAO.waitReview);
		o.setConfirmDate(new Date());
		orderDAO.update(o);
		
		return "orderConfirmed.jsp";
	}
	//评价
	public String review(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		// 为订单对象填充订单项
		orderItemDAO.fill(o);
		Product p = o.getOrderItems().get(0).getProduct();
		List<Review> reviews = reviewDAO.list(p.getId());
		//为此产品设置评价数量和销售量
		productDAO.setSaleAndReviewNumber(p);
		request.setAttribute("p", p);
		request.setAttribute("o", o);
		request.setAttribute("reviews", reviews);
		
		return "review.jsp";
	}
	public String doreview(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		//修改订单对象状态
		o.setStatus(orderDAO.finish);
		//更新订单对象到数据库
		orderDAO.update(o);
		
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		String content = request.getParameter("content");
		content = HtmlUtils.htmlEscape(content);
		
		User user = (User) request.getSession().getAttribute("user");
		Review review = new Review();
		
		review.setContent(content);
		review.setProduct(p);
		review.setCreateDate(new Date());
		review.setUser(user);
		//将新添加的评价增加到数据库
		reviewDAO.add(review);
		
		
		return "@forereview?oid=" + oid + "&showonly=true";
	}
	
	
	
	
	
	
	
	
	
	
	
}
