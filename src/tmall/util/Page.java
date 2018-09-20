package tmall.util;

public class Page {
	int start;
	int count;
	int total;
	//用于让分页带上参数
	String param;
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	//含参构造器
	public Page(int start, int count) {
		super();
		this.start = start;
		this.count = count;
	}
	
	//获得一共又多少页
	public int getTotalPage() {
		int totalPage;
		if(0 == total % count)
			totalPage = total / count;
		else
			totalPage = total / count + 1;
		if(0 == total)
			totalPage = 1;
		return totalPage;
	}
	//获得最后一页的开始记录数ֵ
	public int getLast() {
		int last;
		if(0 == total % count)
			last = total - count;
		else
			last = total - total % count;
		last = last < 0 ? 0 : last;
		return last;
	}
	//是否又下一页
	public boolean isHasNext() {
		if(start == getLast())
			return false;
		else
			return true;
	}
	//是否又上一页
	public boolean isHasPrevious() {
		if(0 == start)
			return false;
		return true;
	}
	

	
	
}
