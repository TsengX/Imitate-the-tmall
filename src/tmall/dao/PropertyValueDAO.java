package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

public class PropertyValueDAO {
	
	public int getTotal() {
		int total = 0;
		try(Connection conn = DBUtil.getConnection();Statement stmt = conn.createStatement();) {
			
			String sql = "SELECT COUNT(*) FROM propertyvalue";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(PropertyValue bean) {
		String sql = "INSERT INTO propertyvalue VALUES(NULL,?,?,?)";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, bean.getProduct().getId());
			ps.setInt(2,bean.getProperty().getId());
			ps.setString(3,bean.getValue());
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
	
	public void update(PropertyValue bean) {
		String sql = "UPDATE propertyvalue SET pid = ?, ptid = ?, value = ? WHERE id = ?";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, bean.getProduct().getId());
			ps.setInt(2, bean.getProperty().getId());
			ps.setString(3, bean.getValue());
			ps.setInt(4, bean.getId());	
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "DELETE FROM propertyvalue WHERE id = ?";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, id);
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public PropertyValue get(int id) {
		PropertyValue bean = null;
		String sql = "SELECT * FROM propertyvalue WHERE id = ?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				bean = new PropertyValue();
				
				int pid = rs.getInt("pid");
				int ptid = rs.getInt("ptid");
				
				bean.setProduct(new ProductDAO().get(pid));
				bean.setProperty(new PropertyDAO().get(ptid));
				bean.setValue(rs.getString("value"));
				bean.setId(id);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	//根据属性id和产品id，获取一个属性值
	public PropertyValue get(int ptid, int pid) {
		PropertyValue bean = null;
		String sql = "SELECT * FROM propertyvalue WHERE ptid = ? and pid = ?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, ptid);
			ps.setInt(2, pid);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				bean = new PropertyValue();
				
				bean.setProduct(new ProductDAO().get(pid));
				bean.setProperty(new PropertyDAO().get(ptid));
				bean.setValue(rs.getString("value"));
				bean.setId(rs.getInt("id"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public List<PropertyValue> list(int start, int count) {
		List<PropertyValue> beans = new ArrayList<>();
		String sql = "SELECT * FROM propertyvalue ORDER BY id DESC LIMIT ?,?";
		
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, start);
			ps.setInt(2, count);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				PropertyValue bean = new PropertyValue();
				
				int pid = rs.getInt("pid");
				int ptid = rs.getInt("ptid");
				
				bean.setProduct(new ProductDAO().get(pid));
				bean.setProperty(new PropertyDAO().get(ptid));
				bean.setValue(rs.getString("value"));
				bean.setId(rs.getInt("id"));
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
		
	}
	
	public List<PropertyValue> list() {
		return list(0, Short.MAX_VALUE);
	}
	//初始化某个产品对应的属性值ֵ
	/**
	 * 初始化逻辑：
	 * 1. 根据分类获取所有的属性 
	 * 2. 遍历每一个属性
	 * 2.1 根据属性和产品，获取属性值 
	 * 2.2  如果属性值不存在，就创建一个属性值对象
	 * @param p
	 */
	public void init(Product p) {
		List<Property> pts = new PropertyDAO().list(p.getCategory().getId());
		
		for (Property pt : pts) {
			PropertyValue pv = get(pt.getId(), p.getId());
			if(null == pv) {
				pv = new PropertyValue();
				pv.setProduct(p);
				pv.setProperty(pt);
				
				this.add(pv);
			}
		}
	}
	//查询某个产品下所有的属性值ֵ
	public List<PropertyValue> list(int pid) {
		List<PropertyValue> beans = new ArrayList<PropertyValue>();
		String sql = "SELECT * FROM propertyvalue WHERE pid = ? ORDER BY ptid DESC";
		
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, pid);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				PropertyValue bean = new PropertyValue();
				
				int ptid = rs.getInt("ptid");
				
				bean.setProduct(new ProductDAO().get(pid));
				bean.setProperty(new PropertyDAO().get(ptid));
				bean.setValue(rs.getString("value"));
				bean.setId(rs.getInt("id"));
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
