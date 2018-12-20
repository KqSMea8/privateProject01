package com.project.common.page;

import java.util.List;

public abstract class Page {

	protected int total = 0; // 总数据条数
	protected List<?> pageContent;// 分页数据内容
	protected String sort; // 排序的字段
	protected String order; // 排序方式 desc asc

	public List<?> getPageContent() {
		return pageContent;
	}

	public void setPageContent(List<?> pageContent) {
		this.pageContent = pageContent;
	}

	/**
	 * 获取当前页index
	 * 
	 * @return
	 */
	public abstract int getPageIndex();

	/**
	 * 获取每页size
	 * 
	 * @time 2016-11-7 上午9:03:57
	 */
	public abstract int getPageSize();

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
