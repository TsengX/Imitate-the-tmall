package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class ProductAllComparator implements Comparator<Product> {
	/*
	 * 综合比较器,把 销量*评价 高的放前面
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Product p1, Product p2) {
		return p2.getReviewCount() * p2.getSaleCount() - p1.getReviewCount() * p1.getSaleCount();
	}
	
}
