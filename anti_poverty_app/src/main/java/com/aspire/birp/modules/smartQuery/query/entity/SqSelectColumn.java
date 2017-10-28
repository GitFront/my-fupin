package com.aspire.birp.modules.smartQuery.query.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SqSelectColumn entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SQ_SELECT_COLUMN")
public class SqSelectColumn implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -268302027808653490L;
	private String id;
	private String sqTable;
	private String sqColumn;
	private String sqColumnAlias;
	private String sqColumnName;
	private String sqId;
	private Long sort;

	// Constructors

	/** default constructor */
	public SqSelectColumn() {
	}

	/** full constructor */
	public SqSelectColumn(String id, String sqTable, String sqColumn,
			String sqColumnAlias, String sqColumnName, String sqId, Long sort) {
		this.id = id;
		this.sqTable = sqTable;
		this.sqColumn = sqColumn;
		this.sqColumnAlias = sqColumnAlias;
		this.sqColumnName = sqColumnName;
		this.sqId = sqId;
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

	@Column(name = "SQ_TABLE", nullable = false, length = 50)
	public String getSqTable() {
		return this.sqTable;
	}

	public void setSqTable(String sqTable) {
		this.sqTable = sqTable;
	}

	@Column(name = "SQ_COLUMN", nullable = false, length = 100)
	public String getSqColumn() {
		return this.sqColumn;
	}

	public void setSqColumn(String sqColumn) {
		this.sqColumn = sqColumn;
	}

	@Column(name = "SQ_COLUMN_ALIAS", nullable = false, length = 200)
	public String getSqColumnAlias() {
		return this.sqColumnAlias;
	}

	public void setSqColumnAlias(String sqColumnAlias) {
		this.sqColumnAlias = sqColumnAlias;
	}

	@Column(name = "SQ_COLUMN_NAME", nullable = false, length = 500)
	public String getSqColumnName() {
		return this.sqColumnName;
	}

	public void setSqColumnName(String sqColumnName) {
		this.sqColumnName = sqColumnName;
	}

	@Column(name = "SQ_ID", nullable = false, length = 50)
	public String getSqId() {
		return this.sqId;
	}

	public void setSqId(String sqId) {
		this.sqId = sqId;
	}

	@Column(name = "SORT", nullable = false, precision = 10, scale = 0)
	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

}