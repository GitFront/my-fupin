package com.aspire.birp.modules.smartQuery.query.vo;

import java.util.List;


public class SqQueryData {
	private String searchKey;
	private List<SqSelecterVO> selecters;
	private List<SqFilterVO> filters;
	private int page;
	private int rows;
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public List<SqSelecterVO> getSelecters() {
		return selecters;
	}
	public void setSelecters(List<SqSelecterVO> selecters) {
		this.selecters = selecters;
	}
	public List<SqFilterVO> getFilters() {
		return filters;
	}
	public void setFilters(List<SqFilterVO> filters) {
		this.filters = filters;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
	
	
}
