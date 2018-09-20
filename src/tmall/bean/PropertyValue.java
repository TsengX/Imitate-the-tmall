package tmall.bean;
/*
 * 1. 基本属性的getter、setter
 * 2. 与Product的多对一关系,getter、setter
 * 3. 与Propety的多对一关系,getter、setter
 */

public class PropertyValue {
	private int id;
	private String value;
	private Property property;	//与Property属性的多对一关系
	private Product product;	//与Product产品的多对一关系
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
