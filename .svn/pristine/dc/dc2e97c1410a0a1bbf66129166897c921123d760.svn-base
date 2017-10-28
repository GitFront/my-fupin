package com.aspire.birp.modules.smartQuery.query.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SqSelectInfoTemp entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SQ_SELECT_INFO_TEMP")
public class SqSelectInfoTemp implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2183147230156828840L;
	private String id;
	private String mpTableId;
	private String sqTable;	
	private String mpColumnId;
	private String sqColumn;
	private String sqId;
	private String creator;
	private Timestamp createTime;
	private Long sort;

	// Constructors

	/** default constructor */
	public SqSelectInfoTemp() {
	}

	/** minimal constructor */
	public SqSelectInfoTemp(String id, String mpTableId,String sqTable,String mpColumnId, String sqColumn,
			String sqId, String creator, Timestamp createTime) {
		this.id = id;
		this.mpTableId=mpTableId;
		this.sqTable = sqTable;
		this.mpColumnId = mpColumnId;
		this.sqColumn = sqColumn;
		this.sqId = sqId;
		this.creator = creator;
		this.createTime = createTime;
	}

	/** full constructor */
	public SqSelectInfoTemp(String id, String mpTableId, String sqTable,
			String mpColumnId, String sqColumn, String sqId, String creator,
			Timestamp createTime, Long sort) {
		super();
		this.id = id;
		this.mpTableId = mpTableId;
		this.sqTable = sqTable;
		this.mpColumnId = mpColumnId;
		this.sqColumn = sqColumn;
		this.sqId = sqId;
		this.creator = creator;
		this.createTime = createTime;
		this.sort = sort;
	}


	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}	

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "MP_TABLE_ID", nullable=false, length=50,updatable=false)
	public String getMpTableId() {
		return mpTableId;
	}

	public void setMpTableId(String mpTableId) {
		this.mpTableId = mpTableId;
	}

	@Column(name = "SQ_TABLE", nullable=false, length=50,updatable=false)
	public String getSqTable() {
		return sqTable;
	}

	public void setSqTable(String sqTable) {
		this.sqTable = sqTable;
	}

	@Column(name = "MP_COLUMN_ID", nullable = false, length = 50,updatable=false)
	public String getMpColumnId() {
		return this.mpColumnId;
	}

	public void setMpColumnId(String mpColumnId) {
		this.mpColumnId = mpColumnId;
	}

	@Column(name = "SQ_COLUMN", nullable = false, length = 100,updatable=false)
	public String getSqColumn() {
		return this.sqColumn;
	}

	public void setSqColumn(String sqColumn) {
		this.sqColumn = sqColumn;
	}

	@Column(name = "SQ_ID", nullable = false, length = 50,updatable=false)
	public String getSqId() {
		return this.sqId;
	}

	public void setSqId(String sqId) {
		this.sqId = sqId;
	}

	@Column(name = "CREATOR", nullable = false, length = 20,updatable=false)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CREATE_TIME", nullable = false, length = 7,updatable=false)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "SORT", precision = 10, scale = 0,updatable=false)
	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

}