package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class ProductReviewComparator implements Comparator<Product> {
	/*
	 * 人气比较器,把 评价数量多的放前面
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Product p1, Product p2) {
		return p2.getReviewCount() - p1.getReviewCount();
	}
	
}
