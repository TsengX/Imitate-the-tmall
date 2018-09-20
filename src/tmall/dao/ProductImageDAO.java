package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;

public class ProductImageDAO {
	//两种静态属性分别表示图片类型为 单个图片和详情图片
	public static final String type_single = "type_single";
	public static final String type_detail = "type_detail";
	
	public int getTotal() {
		int total = 0;
		try(Connection conn = DBUtil.getConnection(); Statement s = conn.createStatement();) {
			
			String sql = "SELECT COUNT(*) FROM productimage";
			
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(ProductImage bean) {
		
		String sql = "INSERT INTO productimage VALUES(NULL,?,?)";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, bean.getProduct().getId());
			ps.setString(2, bean.getType());
			ps.execute();
			
			ResultSet rs = ps. getGeneratedKeys();
			if(rs.next()) {
				int id = rs.getInt(1);
				bean.setId(id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void update(ProductImage bean) {
		
	}
	
	public void delete(int id) {
		try(Connection conn = DBUtil.getConnection(); Statement s = conn.createStatement();) {
			
			String sql = "DELETE FROM productimage WHERE id = " + id;
			
			s.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ProductImage get(int id) {
		ProductImage bean = null;
		String sql = "SELECT * FROM productimage WHERE id = ?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				bean = new ProductImage();
				int pid = rs.getInt("pid");
				
				bean.setProduct(new ProductDAO().get(pid));
				bean.setType(rs.getString("type"));
				bean.setId(id);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	//某种类型的ProductImage
	public List<ProductImage> list(Product p, String type, int start, int count) {
		
		List<ProductImage> beans = new ArrayList<ProductImage>();
		String sql = "SELECT * FROM productImage WHERE pid = ? and type = ? ORDER BY id DESC LIMIT ?,?";
		
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, p.getId());
			ps.setString(2, type);
			ps.setInt(3, start);
			ps.setInt(4, count);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ProductImage bean = new ProductImage();
				
				bean.setProduct(p);
				bean.setType(type);
				bean.setId(rs.getInt(1));
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	public List<ProductImage> list(Product p, String type) {
		return list(p, type, 0, Short.MAX_VALUE);
	}
	
}
