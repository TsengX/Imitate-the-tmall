package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.util.DBUtil;

public class OrderItemDAO {
	//获取记录总数
	public int getTotal() {
		int total = 0;
		try(Connection conn = DBUtil.getConnection();Statement stmt = conn.createStatement();) {
			
			String sql = "SELECT COUNT(*) FROM orderitem";
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
	public void add(OrderItem bean) {
		String sql = "INSERT INTO orderitem VALUES(null,?,?,?,?)";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, bean.getProduct().getId());
			//�������ڴ�����ʱ����û�еٶ�����Ϣ��
			if(null == bean.getOrder()) {
				ps.setInt(2, -1);
			} else {
				ps.setInt(2, bean.getOrder().getId());
			}
			
			ps.setInt(3, bean.getUser().getId());
			ps.setInt(4, bean.getNumber());
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
	public void update(OrderItem bean) {
		String sql = "UPDATE orderitem SET pid = ?, oid = ?, uid = ?, number = ? WHERE id = ?";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, bean.getProduct().getId());
			if(null == bean.getOrder()) {
				ps.setInt(2, -1);
			} else {
				ps.setInt(2, bean.getOrder().getId());
			}
			ps.setInt(3, bean.getUser().getId());
			ps.setInt(4, bean.getNumber());
			ps.setInt(5, bean.getId());
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//删除
	public void delete(int id) {
		String sql = "DELETE FROM orderitem WHERE id = ?";
		try(Connection conn = DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, id);
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public OrderItem get(int id) {
		OrderItem bean = null;
		String sql = "SELECT * FROM orderitem WHERE id = ?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				bean = new OrderItem();
				
				int pid = rs.getInt("pid");
				int oid = rs.getInt("oid");
				int uid = rs.getInt("uid");
				
				bean.setProduct(new ProductDAO().get(pid));
				bean.setUser(new UserDAO().get(uid));
				bean.setNumber(rs.getInt("number"));
				
				if(-1 != oid) {
					bean.setOrder(new OrderDAO().get(oid));
				}
				bean.setId(id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public List<OrderItem> listByUser(int uid, int start, int count) {
		List<OrderItem> beans = new ArrayList<OrderItem>();
		
		String sql = "SELECT * FROM orderitem WHERE uid = ? and oid = -1 ORDER BY id DESC LIMIT ?,? ";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			
			ps.setInt(1, uid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				OrderItem bean = new OrderItem();
				
				int oid = rs.getInt("oid");
				int pid = rs.getInt("pid");
				
				if(-1 != oid) {
					bean.setOrder(new OrderDAO().get(oid));
				}
				
				bean.setProduct(new ProductDAO().get(pid));
				bean.setUser(new UserDAO().get(uid));
				bean.setNumber(rs.getInt("number"));
				bean.setId(rs.getInt("id"));
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	//查询某个用户的未生成订单的订单项(既购物车中的订单项)
	public List<OrderItem> listByUser(int uid) {
		return listByUser(uid, 0, Short.MAX_VALUE);
	}
	//分页查询某种订单下的订单项
	public List<OrderItem> listByOrder(int oid, int start, int count) {
		List<OrderItem> beans = new ArrayList<OrderItem>();

		String sql = "SELECT * FROM orderitem WHERE oid = ? ORDER BY id DESC LIMIT ?,? ";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);) {

			ps.setInt(1, oid);
			ps.setInt(2, start);
			ps.setInt(3, count);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				OrderItem bean = new OrderItem();

				int uid = rs.getInt("uid");
				int pid = rs.getInt("pid");

				if (-1 != oid) {
					bean.setOrder(new OrderDAO().get(oid));
				}

				bean.setProduct(new ProductDAO().get(pid));
				bean.setUser(new UserDAO().get(uid));
				bean.setNumber(rs.getInt("number"));
				bean.setId(rs.getInt("id"));

				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	//查询某种订单下所有的订单项
	public List<OrderItem> listByOrder(int oid) {
		return listByOrder(oid, 0, Short.MAX_VALUE);
	}
	//Ϊ为订单设置订单项集合
	public void fill(List<Order> os) {
		for (Order o : os) {
			List<OrderItem> ois = listByOrder(o.getId());
			float total = 0;
			int totalNumber = 0;
			for (OrderItem oi : ois) {
				//用forEach计算订单的总金额和商品总数量
				total += oi.getNumber() * oi.getProduct().getPromotePrice();
				totalNumber += oi.getNumber();
			}
			o.setTotal(total);
			o.setOrderItems(ois);
			o.setTotalNumber(totalNumber);
		}
	}
	//为订单设置单个订单项
	public void fill(Order o) {
		List<OrderItem> ois = listByOrder(o.getId());
		float total = 0;
		for (OrderItem oi : ois) {
			total += oi.getNumber() * oi.getProduct().getPromotePrice();
		}
		o.setTotal(total);
		o.setOrderItems(ois);
	}
	//查询某种产品下的订单项
	public List<OrderItem> listByProduct(int pid, int start, int count) {
		List<OrderItem> beans = new ArrayList<OrderItem>();

		String sql = "SELECT * FROM orderitem WHERE and pid = ? ORDER BY id DESC LIMIT ?,? ";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);) {

			ps.setInt(1, pid);
			ps.setInt(2, start);
			ps.setInt(3, count);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				OrderItem bean = new OrderItem();

				int uid = rs.getInt("uid");
				int oid = rs.getInt("oid");

				if (-1 != oid) {
					bean.setOrder(new OrderDAO().get(oid));
				}

				bean.setProduct(new ProductDAO().get(pid));
				bean.setUser(new UserDAO().get(uid));
				bean.setNumber(rs.getInt("number"));
				bean.setId(rs.getInt("id"));

				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	//查询某种产品下所有的订单项
	public List<OrderItem> listByProduct(int pid) {
		return listByProduct(pid, 0, Short.MAX_VALUE);
	}
	//获取某一种产品的销量
	public int getSaleCount(int pid) {
		int total = 0;
		String sql = "SELECT SUM(number) FROM orderitem WHERE pid = ?";
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setInt(1, pid);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
}
