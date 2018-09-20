package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class ProductPriceComparator implements Comparator<Product> {
	/*
	 * 价格比较器,把 价格低的放前面
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Product p1, Product p2) {
		return (int)(p1.getPromotePrice() - p2.getPromotePrice());
	}

}
