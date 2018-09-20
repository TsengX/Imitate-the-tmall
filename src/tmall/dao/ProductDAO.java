package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class ProductDAO {
	//获取总数
	public int getTotal(int cid) {
		int total = 0;
		try(Connection conn = DBUtil.getConnection();Statement stmt = conn.createStatement();) {
			
			String sql = "SELECT COUNT(*) FROM product WHERE cid =" + cid;
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//增加
	public void add(Product bean) {
		String sql = "INSERT INTO product VALUES(null,?,?,?,?,?,?,?)";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getSubTitle());
			ps.setFloat(3, bean.getOriginalPrice());
			ps.setFloat(4, bean.getPromotePrice());
			ps.setInt(5, bean.getStock());
			ps.setInt(6, bean.getCategory().getId());
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				int id = rs.getInt(1);
				bean.setId(id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	//修改
	public void update(Product bean) {
		String sql = "UPDATE product SET name = ?, subTitle = ?, originalPrice = ?,"
				+ " promotePrice = ?, stock = ?, cid = ?, createDate = ?"
				+ " WHERE id = ?";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getSubTitle());
			ps.setFloat(3, bean.getOriginalPrice());
			ps.setFloat(4, bean.getPromotePrice());
			ps.setInt(5, bean.getStock());
			ps.setInt(6, bean.getCategory().getId());
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
			ps.setInt(8, bean.getId());
			
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//删除
	public void delete(int id) {
		String sql = "DELETE FROM product WHERE id = ?";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, id);
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//根据id获取一条记录
	public Product get(int id) {
		Product bean = null;
		String sql = "SELECT * FROM product WHERE id = ?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				bean = new Product();
				int cid = rs.getInt("cid");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
				
				bean.setName(rs.getString("name"));
				bean.setSubTitle(rs.getString("subTitle"));
				bean.setOriginalPrice(rs.getFloat("originalPrice"));
				bean.setPromotePrice(rs.getFloat("promotePrice"));
				bean.setStock(rs.getInt("stock"));
				bean.setCategory(new CategoryDAO().get(cid));
				bean.setCreateDate(createDate);
				bean.setId(id);
				setFirstProductImage(bean);	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	//分页查询
	public List<Product> list(int start, int count) {
		List<Product> beans = new ArrayList<Product>();
		String sql = "SELECT * FROM product LIMIT ?,?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, start);
			ps.setInt(2, count);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Product bean = new Product();
				
				int cid = rs.getInt("cid");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
				
				bean.setName(rs.getString("name"));
				bean.setSubTitle(rs.getString("subTitle"));
				bean.setOriginalPrice(rs.getFloat("originalPrice"));
				bean.setPromotePrice(rs.getFloat("promotePrice"));
				bean.setStock(rs.getInt("stock"));
				bean.setCategory(new CategoryDAO().get(cid));
				bean.setCreateDate(createDate);
				bean.setId(rs.getInt("id"));
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return beans;
	}
	//查询所有
	public List<Product> list() {
		return list(0, Short.MAX_VALUE);
	}
	//根据分类(cid)查询所有
	public List<Product> list(int cid) {
		return list(cid, 0, Short.MAX_VALUE);
	}
	
	//根据分类(cid)查询
	public List<Product> list(int cid, int start, int count) {
		List<Product> beans = new ArrayList<Product>();
		String sql = "SELECT * FROM product WHERE cid = ? ORDER BY id DESC LIMIT ?,?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Product bean = new Product();
				
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
				
				bean.setName(rs.getString("name"));
				bean.setSubTitle(rs.getString("subTitle"));
				bean.setOriginalPrice(rs.getFloat("originalPrice"));
				bean.setPromotePrice(rs.getFloat("promotePrice"));
				bean.setStock(rs.getInt("stock"));
				bean.setCategory(new CategoryDAO().get(cid));
				bean.setCreateDate(createDate);
				bean.setId(rs.getInt("id"));
				
				setFirstProductImage(bean);	
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return beans;
	}
	
	//为分类填充产品集合
	public void fill(List<Category> cs) {
		for (Category c : cs) {
			fill(c);
		}
	}
	public void fill(Category c) {
		List<Product> ps = this.list(c.getId());
		c.setProducts(ps);
	}
	
	//每productNumberEachRow 个产品为一个List
	public void fillByRow(List<Category> cs) {
		int productNumberEachRow = 8;
		for (Category c : cs) {
			List<Product> products = c.getProducts();
			List<List<Product>> productsByRow = new ArrayList<>();
			
			for(int i = 0;i < products.size();i += productNumberEachRow) {
				int size = i + productNumberEachRow;
				size = size > products.size() ? products.size() : size;
				
				List<Product> productsOfEachRow = products.subList(i, size);
				productsByRow.add(productsOfEachRow);
			}
			c.setProductsByRow(productsByRow);
		}
	}
	//一个产品有多个图片，但是只有一个主图片，把第一个单个图片设置为主图片
	public void setFirstProductImage(Product p) {
		List<ProductImage> pis = new ProductImageDAO().list(p, ProductImageDAO.type_single);
		if(!pis.isEmpty()) {
			p.setFirstProductImage(pis.get(0));
		}
	}
	//为产品设置销售和评价数量
	public void setSaleAndReviewNumber(Product p) {
		int saleCount = new OrderItemDAO().getSaleCount(p.getId());
		p.setSaleCount(saleCount);
		
		int reviewCount = new ReviewDAO().getCount(p.getId());
		p.setReviewCount(reviewCount);
	}
	
	public void setSaleAndReviewNumber(List<Product> products) {
		for (Product p : products) {
			setSaleAndReviewNumber(p);
		}
	}
	//根据关键字查询产品
	public List<Product> search(String keyword, int start, int count) {
		List<Product> beans = new ArrayList<Product>();
		
		if(null == keyword || 0 == keyword.trim().length())
			return beans;
		
		String sql = "SELECT * FROM product WHERE name like ? limit ?,?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setString(1, "%" + keyword.trim() + "%");
			ps.setInt(2, start);
			ps.setInt(3, count);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Product bean = new Product();
				
				int cid = rs.getInt("cid");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
				
				bean.setName(rs.getString("name"));
				bean.setSubTitle(rs.getString("subTitle"));
				bean.setOriginalPrice(rs.getFloat("originalPrice"));
				bean.setPromotePrice(rs.getFloat("promotePrice"));
				bean.setStock(rs.getInt("stock"));
				bean.setCategory(new CategoryDAO().get(cid));
				bean.setCreateDate(createDate);
				bean.setId(rs.getInt("id"));
				
				setFirstProductImage(bean);	
				
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return beans;
	}
	
	

	
}
