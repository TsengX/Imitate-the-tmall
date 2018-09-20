package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;





import java.util.ArrayList;
import java.util.List;

import tmall.bean.User;
import tmall.util.DBUtil;

public class UserDAO {
	
	public int getTotal() {
		int total = 0;
		try(Connection conn = DBUtil.getConnection(); Statement s = conn.createStatement();) {
			
			String sql = "SELECT COUNT(*) FROM user";
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(User bean) {
		String sql = "INSERT INTO user VALUES(NULL,?,?)";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
		
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			
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
	
	public void update(User bean) {
		String sql = "UPDATE user SET name = ?, password = ? WHERE id = ?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.setInt(3, bean.getId());
			
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "DELETE FROM user WHERE id = ?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setInt(1, id);
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public User get(int id) {
		User bean = null;
		String sql = "SELECT * FROM user WHERE id = ?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setInt(1, id);
			
			ps.execute();
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				bean = new User();
				bean.setName(rs.getString("name"));
				bean.setPassword(rs.getString("password"));
				bean.setId(id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public List<User> list() {
        return list(0, Short.MAX_VALUE);
    }
	
	public List<User> list(int start, int count) {
		List<User> beans = new ArrayList<User>();
		String sql = "SELECT * FROM user ORDER BY id DESC LIMIT ?,? ";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				User bean = new User();
				
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setPassword(rs.getString("password"));
				beans.add(bean);
			}
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	//�����û�����ȡ����
	public User get(String name) {
		User bean = null;
		String sql = "SELECT * FROM user WHERE name = ?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				bean = new User();
				bean.setName(name);
				bean.setPassword(rs.getString("password"));
				bean.setId(rs.getInt("id"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	//�����˺ź������ȡ����
	public User get(String name, String password) {
		User bean = null;
		String sql = "SELECT * FROM user WHERE name = ? and password=?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, name);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				bean = new User();
				bean.setName(name);
				bean.setPassword(password);
				bean.setId(rs.getInt("id"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	//��boolean��ʽ����ĳ���û����Ƿ��Ѿ�����
	public boolean isExist(String name) {
		User user = get(name);
		return user != null;
	}
	
	
}
