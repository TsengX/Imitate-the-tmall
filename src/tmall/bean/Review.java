package tmall.bean;

import java.util.Date;

/*
 * 1. �������Ե�getter��setter
 * 2. ��User�Ķ��һ��ϵ ,getter,setter
 * 3. ��Product �Ķ��һ��ϵ, getter, setter
 */

public class Review {
	private int id;
	private String content;
	private Date createDate;
	private User user;
	private Product product;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
