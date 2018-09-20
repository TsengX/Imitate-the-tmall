package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class ProductSaleCountComparator implements Comparator<Product> {
	/*
	 * 销量比较器,把 销量高的放前面
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Product p1, Product p2) {
		return p2.getSaleCount() - p1.getSaleCount();
	}

}
