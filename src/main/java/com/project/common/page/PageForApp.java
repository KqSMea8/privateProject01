package com.project.common.page;

import java.io.Serializable;
import java.util.List;

public class PageForApp extends Page implements Serializable {
	private static final long serialVersionUID = 7052093828764538735L;

	private Integer page = 1; // 当前第几页
	private Object rows = 10; // 每页数据条数

	public PageForApp() {
		super();
	}

	public PageForApp(Integer page, Integer rows) {
		super();
		if (page != null && page > 0)
			this.page = page;
		if (rows != null && rows > 0)
			setRows(rows);
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	@Override
	public int getPageIndex() {
		return this.getPage();
	}

	@Override
	public int getPageSize() {
		Object obj = this.getRows();
		if (obj instanceof Integer)
			return (Integer) obj;
		return 10;
	}

	@Override
	public void setPageContent(List<?> pageContent) {
		super.setPageContent(pageContent);
		this.rows = getPageContent();
	}

	public void setTotalRows(Integer total) {
		super.setTotal(total);
	}

	@Override
	public void setTotal(int total) {
		setTotalRows(total);
	}

	public int getTotalRows() {
		return super.getTotal();
	}

}
