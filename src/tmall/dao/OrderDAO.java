package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tmall.bean.Order;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class OrderDAO {
	//订单类型
	public static final String waitPay = "waitPay";
	public static final String waitDelivery = "waitDelivery";
	public static final String waitConfirm = "waitConfirm";
	public static final String waitReview = "waitReview";
	public static final String finish = "finish";
	public static final String delete = "delete";
	
	
	//获取记录总数
	public int getTotal() {
		int total = 0;
		try(Connection conn = DBUtil.getConnection();Statement stmt = conn.createStatement();) {
			
			String sql = "SELECT COUNT(*) FROM order_";
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
	public void add(Order bean) {
		
		String sql = "INSERT INTO order_ VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?)";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setString(1, bean.getOrderCode());
			ps.setString(2, bean.getAddress());
			ps.setString(3, bean.getPost());
			ps.setString(4, bean.getReceiver());
			ps.setString(5, bean.getMobile());
			ps.setString(6, bean.getUserMessage());
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
			ps.setTimestamp(8, DateUtil.d2t(bean.getPayDate()));
			ps.setTimestamp(9, DateUtil.d2t(bean.getDeliveryDate()));
			ps.setTimestamp(10, DateUtil.d2t(bean.getConfirmDate()));
			ps.setInt(11, bean.getUser().getId());
			ps.setString(12, bean.getStatus());
			
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
	public void update(Order bean) {
		String sql = "UPDATE order_ SET orderCode = ?, address = ?, post = ?, receiver = ?,"
				+ "mobile = ?, userMessage = ?, createDate = ?, payDate = ?, deliveryDate = ?,"
				+ " confirmDate = ?, uid = ?, status = ? WHERE id = ?";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setString(1, bean.getOrderCode());
			ps.setString(2, bean.getAddress());
			ps.setString(3, bean.getPost());
			ps.setString(4, bean.getReceiver());
			ps.setString(5, bean.getMobile());
			ps.setString(6, bean.getUserMessage());
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
			ps.setTimestamp(8, DateUtil.d2t(bean.getPayDate()));
			ps.setTimestamp(9, DateUtil.d2t(bean.getDeliveryDate()));
			ps.setTimestamp(10, DateUtil.d2t(bean.getConfirmDate()));
			ps.setInt(11, bean.getUser().getId());
			ps.setString(12, bean.getStatus());
			ps.setInt(13, bean.getId());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//删除
	public void delete(int id) {
		String sql = "DELETE FROM order_ WHERE id = ?";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, id);
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Order get(int id) {
		Order bean = null;
		String sql = "SELECT * FROM order_ WHERE id = ?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				bean = new Order();
				
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
				Date payDate = DateUtil.t2d(rs.getTimestamp("payDate"));
				Date deliveryDate = DateUtil.t2d(rs.getTimestamp("deliveryDate"));
				Date confirmDate = DateUtil.t2d(rs.getTimestamp("confirmDate"));
				
				bean.setOrderCode(rs.getString("orderCode"));
				bean.setAddress(rs.getString("address"));
				bean.setPost(rs.getString("post"));
				bean.setReceiver(rs.getString("receiver"));
				bean.setMobile(rs.getString("mobile"));
				bean.setUserMessage(rs.getString("userMessage"));
				bean.setCreateDate(createDate);
				bean.setPayDate(payDate);
				bean.setDeliveryDate(deliveryDate);
				bean.setConfirmDate(confirmDate);
				bean.setUser(new UserDAO().get(rs.getInt("uid")));
				bean.setStatus(rs.getString("status"));
				
				bean.setId(id);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public List<Order> list(int start, int count) {
		List<Order> beans = new ArrayList<Order>();
		
		String sql = "SELECT * FROM order_ ORDER BY id DESC LIMIT ?,? ";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, start);
			ps.setInt(2, count);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Order bean = new Order();
				
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
				Date payDate = DateUtil.t2d(rs.getTimestamp("payDate"));
				Date deliveryDate = DateUtil.t2d(rs.getTimestamp("deliveryDate"));
				Date confirmDate = DateUtil.t2d(rs.getTimestamp("confirmDate"));
				
				bean.setOrderCode(rs.getString("orderCode"));
				bean.setAddress(rs.getString("address"));
				bean.setPost(rs.getString("post"));
				bean.setReceiver(rs.getString("receiver"));
				bean.setMobile(rs.getString("mobile"));
				bean.setUserMessage(rs.getString("userMessage"));
				bean.setCreateDate(createDate);
				bean.setPayDate(payDate);
				bean.setDeliveryDate(deliveryDate);
				bean.setConfirmDate(confirmDate);
				bean.setUser(new UserDAO().get(rs.getInt("uid")));
				bean.setStatus(rs.getString("status"));
				bean.setId(rs.getInt("id"));
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	public List<Order> list() {
        return list(0, Short.MAX_VALUE);
    }
	//查询指定用户的订单
	public List<Order> list(int uid, String excludedStatus, int start, int count) {
		List<Order> beans = new ArrayList<Order>();
		
		String sql = "SELECT * FROM order_ WHERE uid = ? AND status != ? ORDER BY id DESC LIMIT ?,? ";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, uid);
			ps.setString(2, excludedStatus);
			ps.setInt(3, start);
			ps.setInt(4, count);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Order bean = new Order();
				
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
				Date payDate = DateUtil.t2d(rs.getTimestamp("payDate"));
				Date deliveryDate = DateUtil.t2d(rs.getTimestamp("deliveryDate"));
				Date confirmDate = DateUtil.t2d(rs.getTimestamp("confirmDate"));
				
				bean.setOrderCode(rs.getString("orderCode"));
				bean.setAddress(rs.getString("address"));
				bean.setPost(rs.getString("post"));
				bean.setReceiver(rs.getString("receiver"));
				bean.setMobile(rs.getString("mobile"));
				bean.setUserMessage(rs.getString("userMessage"));
				bean.setCreateDate(createDate);
				bean.setPayDate(payDate);
				bean.setDeliveryDate(deliveryDate);
				bean.setConfirmDate(confirmDate);
				bean.setUser(new UserDAO().get(uid));
				bean.setStatus(rs.getString("status"));
				bean.setId(rs.getInt("id"));
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	public List<Order> list(int uid,String excludedStatus) {
		return list(uid, excludedStatus, 0, Short.MAX_VALUE);
	}
	
	
}
