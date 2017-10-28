package com.aspire.birp.modules.smartQuery.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DimMColumnType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DIM_M_COLUMN_TYPE")
public class DimMColumnType implements java.io.Serializable {

	private static final long serialVersionUID = -1605403118983510888L;
	// Fields

	private String id;
	private String code;
	private String name;
	private Long sort;

	// Constructors

	/** default constructor */
	public DimMColumnType() {
	}

	/** minimal constructor */
	public DimMColumnType(String id, String code) {
		this.id = id;
		this.code = code;
	}

	/** full constructor */
	public DimMColumnType(String id, String code, String name, Long sort) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.sort = sort;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 1)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "CODE", nullable = false, length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SORT", precision = 10, scale = 0)
	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

}