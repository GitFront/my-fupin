package com.aspire.birp.modules.smartQuery.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DimFilterFormula entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DIM_FILTER_FORMULA")
public class DimFilterFormula implements java.io.Serializable {

	private static final long serialVersionUID = 5186267256994064804L;
	// Fields

	private String id;
	private String code;
	private String transcode;
	private String name;
	private String space;
	private Long sort;

	// Constructors

	/** default constructor */
	public DimFilterFormula() {
	}

	/** minimal constructor */
	public DimFilterFormula(String id, String code) {
		this.id = id;
		this.code = code;
	}

	/** full constructor */
	public DimFilterFormula(String id, String code, String transcode,
			String name, String space, Long sort) {
		super();
		this.id = id;
		this.code = code;
		this.transcode = transcode;
		this.name = name;
		this.space = space;
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

	@Column(name = "TRANSCODE", length = 20)
	public String getTranscode() {
		return this.transcode;
	}

	public void setTranscode(String transcode) {
		this.transcode = transcode;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "SPACE", length = 10)
	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	@Column(name = "SORT", precision = 10, scale = 0)
	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

}