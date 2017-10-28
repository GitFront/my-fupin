package com.aspire.birp.modules.smartQuery.query.vo;

import java.sql.Date;

/**
 * SqSortInfo entity. 
 * @author MyEclipse Persistence Tools
 */
public class SqSortInfoVO {

	// Fields

	private String id;
	private String mpTableId;
	private String sqTable;	
	private String mpColumnId;
	private String sqColumn;
	private String sortTypeId;
	private String sortType;
	private String sqId;
	private String creator;
	private Date createTime;
	private Long sort;

	// Constructors

	/** default constructor */
	public SqSortInfoVO() {
	}

	/** minimal constructor */
	public SqSortInfoVO(String id, String mpColumnId, String sqColumn,
			String sortTypeId, String sortType, String sqId, String creator,
			Date createTime) {
		this.id = id;
		this.mpColumnId = mpColumnId;
		this.sqColumn = sqColumn;
		this.sortTypeId = sortTypeId;
		this.sortType = sortType;
		this.sqId = sqId;
		this.creator = creator;
		this.createTime = createTime;
	}

	/** full constructor */
	public SqSortInfoVO(String id, String mpTableId, String sqTable,
			String mpColumnId, String sqColumn, String sortTypeId,
			String sortType, String sqId, String creator, Date createTime,
			Long sort) {
		super();
		this.id = id;
		this.mpTableId = mpTableId;
		this.sqTable = sqTable;
		this.mpColumnId = mpColumnId;
		this.sqColumn = sqColumn;
		this.sortTypeId = sortTypeId;
		this.sortType = sortType;
		this.sqId = sqId;
		this.creator = creator;
		this.createTime = createTime;
		this.sort = sort;
	}
	
	public SqSortInfoVO(String id, String mpColumnId, String sqColumn,
			String sortTypeId, String sortType, String sqId) {
		this.id = id;
		this.mpColumnId = mpColumnId;
		this.sqColumn = sqColumn;
		this.sortTypeId = sortTypeId;
		this.sortType = sortType;
		this.sqId = sqId;
	}
	
	
	
	public SqSortInfoVO(String id, String mpTableId, String sqTable,
			String mpColumnId, String sqColumn, String sortTypeId,
			String sortType, String sqId) {
		super();
		this.id = id;
		this.mpTableId = mpTableId;
		this.sqTable = sqTable;
		this.mpColumnId = mpColumnId;
		this.sqColumn = sqColumn;
		this.sortTypeId = sortTypeId;
		this.sortType = sortType;
		this.sqId = sqId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMpColumnId() {
		return mpColumnId;
	}

	public void setMpColumnId(String mpColumnId) {
		this.mpColumnId = mpColumnId;
	}

	public String getSqColumn() {
		return sqColumn;
	}

	public void setSqColumn(String sqColumn) {
		this.sqColumn = sqColumn;
	}

	public String getSortTypeId() {
		return sortTypeId;
	}

	public void setSortTypeId(String sortTypeId) {
		this.sortTypeId = sortTypeId;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getSqId() {
		return sqId;
	}

	public void setSqId(String sqId) {
		this.sqId = sqId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getMpTableId() {
		return mpTableId;
	}

	public void setMpTableId(String mpTableId) {
		this.mpTableId = mpTableId;
	}

	public String getSqTable() {
		return sqTable;
	}

	public void setSqTable(String sqTable) {
		this.sqTable = sqTable;
	}


}