package com.aspire.birp.modules.smartQuery.query.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SqTableInfo entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SQ_TABLE_INFO")
public class SqTableInfo implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -7742950261210825596L;
	private String id;
	private String mpTableId;
	private String sqTable;
	private String sqId;
	private String creator;
	private Timestamp createTime;
	private Long sort;

	// Constructors

	/** default constructor */
	public SqTableInfo() {
	}

	/** minimal constructor */
	public SqTableInfo(String id, String sqId, String creator,
			Timestamp createTime) {
		this.id = id;
		this.sqId = sqId;
		this.creator = creator;
		this.createTime = createTime;
	}

	/** full constructor */
	public SqTableInfo(String id, String mpTableId, String sqTable,
			String sqId, String creator, Timestamp createTime, Long sort) {
		this.id = id;
		this.mpTableId = mpTableId;
		this.sqTable = sqTable;
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

	@Column(name = "MP_TABLE_ID", nullable = false, length = 50)
	public String getMpTableId() {
		return this.mpTableId;
	}

	public void setMpTableId(String mpTableId) {
		this.mpTableId = mpTableId;
	}

	@Column(name = "SQ_TABLE", nullable = false, length = 50)
	public String getSqTable() {
		return this.sqTable;
	}

	public void setSqTable(String sqTable) {
		this.sqTable = sqTable;
	}

	@Column(name = "SQ_ID", nullable = false, length = 50)
	public String getSqId() {
		return this.sqId;
	}

	public void setSqId(String sqId) {
		this.sqId = sqId;
	}

	@Column(name = "CREATOR", nullable = false, length = 20)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CREATE_TIME", nullable = false, length = 7)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "SORT", precision = 10, scale = 0)
	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

}