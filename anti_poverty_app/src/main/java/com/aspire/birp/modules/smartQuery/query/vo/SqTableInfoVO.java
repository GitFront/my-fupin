package com.aspire.birp.modules.smartQuery.query.vo;


/**
 * SqTableInfo entity. 
 * @author MyEclipse Persistence Tools
 */
public class SqTableInfoVO {

	// Fields
	private String id;
	private String mpTableId;
	private String sqTable;
	private String tableMappingName;
	private String sqId;
	private String creator;
	private String createTime;
	private Long sort;

	// Constructors

	/** default constructor */
	public SqTableInfoVO() {
	}
	
	public SqTableInfoVO(String id, String mpTableId, String sqTable,
			String tableMappingName, String sqId) {
		super();
		this.id = id;
		this.mpTableId = mpTableId;
		this.sqTable = sqTable;
		this.tableMappingName = tableMappingName;
		this.sqId = sqId;
	}

	public SqTableInfoVO(String id, String mpTableId, String sqTable,
			String tableMappingName, String sqId, String creator,
			String createTime, Long sort) {
		super();
		this.id = id;
		this.mpTableId = mpTableId;
		this.sqTable = sqTable;
		this.tableMappingName = tableMappingName;
		this.sqId = sqId;
		this.creator = creator;
		this.createTime = createTime;
		this.sort = sort;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	public String getTableMappingName() {
		return tableMappingName;
	}
	
	public void setTableMappingName(String tableMappingName) {
		this.tableMappingName = tableMappingName;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

}