package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import tmall.bean.Review;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class ReviewDAO {
	//获取总记录数
	public int getTotal() {
		int total = 0;
		try(Connection conn = DBUtil.getConnection();Statement stmt = conn.createStatement();) {
			
			String sql = "SELECT COUNT(*) FROM review";
			
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	public int getTotal(int pid) {
		int total = 0;
		try(Connection conn = DBUtil.getConnection();Statement stmt = conn.createStatement();) {
			
			String sql = "SELECT COUNT(*) FROM review WHERE pid = " + pid;
			
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
	public void add(Review bean) {
		String sql = "INSERT INTO review VALUES(NULL,?,?,?,?)";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setString(1, bean.getContent());
			ps.setInt(2, bean.getUser().getId());
			ps.setInt(3, bean.getProduct().getId());
			ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
			
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
	//更新
	public void update(Review bean) {
		String sql = "UPDATE review SET content = ?, uid = ?, pid = ?, createDate = ? WHERE id = ?";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setString(1, bean.getContent());
			ps.setInt(2, bean.getUser().getId());
			ps.setInt(3, bean.getProduct().getId());
			ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
			ps.setInt(5, bean.getId());
			
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//删除
	public void delete(int id) {
		String sql = "DELETE FROM review WHERE id = ?";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, id);
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Review get(int id) {
		Review bean = null;
		String sql = "SELECT * FROM review WHERE id = ?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				bean = new Review();
				
				int uid = rs.getInt("uid");
				int pid = rs.getInt("pid");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
				
				bean.setContent(rs.getString("content"));
				bean.setUser(new UserDAO().get(uid));
				bean.setProduct(new ProductDAO().get(pid));
				bean.setCreateDate(createDate);
				bean.setId(id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	//获取一定数量的指定产品的评价
	public List<Review> list(int pid, int start, int count) {
		List<Review> beans = new ArrayList<Review>();
		
		String sql = "SELECT * FROM review WHERE pid = ? ORDER BY id DESC LIMIT ?,?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, pid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Review bean = new Review();
				
				int uid = rs.getInt("uid");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
				
				bean.setContent(rs.getString("content"));
				bean.setCreateDate(createDate);
				bean.setProduct(new ProductDAO().get(pid));
				bean.setUser(new UserDAO().get(uid));
				bean.setId(rs.getInt("id"));
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	//获取指定产品的所有评价
	public List<Review> list(int pid) {
		return list(pid, 0, Short.MAX_VALUE);
	}
	//获取指定产品一共有多少条评价
	public int getCount(int pid) {
		String sql = "SELECT COUNT(*) FROM review WHERE pid = ? ";
		
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, pid);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	//是否已存在评论, 防止网络卡的时候用户多次提交评论
	public boolean isExist(String content, int pid) {
		String sql = "SELECT * FROM review WHERE content = ? and pid = ? ";
		
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setString(1, content);
			ps.setInt(2, pid);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
		
		
		
		
		
		
		
		
		
		
}
