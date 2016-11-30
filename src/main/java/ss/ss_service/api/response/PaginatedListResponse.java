package ss.ss_service.api.response;

import java.util.List;

public class PaginatedListResponse<T> {
	private List<T> data;
	private int total;
	private int page;
	private int pages;
	
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public void addData(T item) {
		this.data.add(item);
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public boolean hasNextPages() {
		return page < pages;
	}
}
