package tmall.bean;
/*
 * 1. �������Ե�getter��setter
 * 2. ��Product�Ķ��һ��ϵ,getter��setter
 * 3. ��Propety�Ķ��һ��ϵ,getter��setter
 */

public class PropertyValue {
	private int id;
	private String value;
	private Property property;	//��Property���ԵĶ��һ��ϵ
	private Product product;	//��Product��Ʒ�Ķ��һ��ϵ
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
