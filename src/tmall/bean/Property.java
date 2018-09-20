package tmall.bean;

/*
 * 1. 基本属性的getter、setter
 * 2. 与Category的多对一关系
 */
public class Property {
	private int id;
	private String name;
	private Category category;	//与Category的多对一关系
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
}
