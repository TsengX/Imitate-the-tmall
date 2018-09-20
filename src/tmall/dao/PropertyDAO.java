package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Property;
import tmall.util.DBUtil;

public class PropertyDAO {
	//获取某种分类下的属性总数
	public int getTotal(int cid) {
		int total = 0;
		try(Connection conn = DBUtil.getConnection(); Statement s = conn.createStatement();) {
			
			String sql = "SELECT COUNT(*) FROM property where cid =" + cid;
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(Property bean) {
		String sql = "INSERT INTO property VALUES(NULL,?,?)";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
		
			ps.setInt(1, bean.getCategory().getId());	//cid
			ps.setString(2, bean.getName());
			
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
	
	public void update(Property bean) {
		String sql = "UPDATE property SET cid = ?, name = ? WHERE id = ?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
			ps.setInt(3, bean.getId());
			
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "DELETE FROM property WHERE id = ?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setInt(1, id);
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Property get(int id) {
		Property bean = null;
		String sql = "SELECT * FROM property WHERE id = ?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setInt(1, id);
			
			ps.execute();
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				bean = new Property();
				bean.setName(rs.getString("name"));
				int cid = rs.getInt("cid");
				bean.setCategory(new CategoryDAO().get(cid));
				bean.setId(id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	//查询某个分类下的的属性对象
	public List<Property> list(int cid) {
        return list(cid, 0, Short.MAX_VALUE);
    }
	//分页查询某个分类下的的属性对象
	public List<Property> list(int cid, int start, int count) {
		List<Property> beans = new ArrayList<Property>();
		String sql = "SELECT * FROM property WHERE cid = ? ORDER BY id DESC LIMIT ?,? ";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Property bean = new Property();
				
				bean.setName(rs.getString("name"));
				bean.setCategory(new CategoryDAO().get(cid));
				bean.setId(rs.getInt("id"));
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
}
