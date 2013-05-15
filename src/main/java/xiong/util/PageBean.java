package xiong.util;

import java.util.ArrayList;
import java.util.List;

public class PageBean<M> {
	public static final int MAX_PAGESIZE = 40;
	public static final int MIN_PAGESIZE = 10;
	private int total;
	private List<M> data = new ArrayList<M>();
	private int start;
	private int pageSize;
	private boolean success;

	public PageBean(int total, int start, int pageSize) {
		this.total = total;
		this.start = start;
		this.pageSize = pageSize;
		this.success = true;
		if (start < 0 || total <= pageSize)
			start = 0;
		else if (start >= total - 1)
			start = total - pageSize;
		if (pageSize < 1)
			pageSize = MIN_PAGESIZE;
		else if (pageSize > MAX_PAGESIZE)
			pageSize = MAX_PAGESIZE;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<M> getData() {
		return data;
	}

	public void setData(List<M> data) {
		this.data = data;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
