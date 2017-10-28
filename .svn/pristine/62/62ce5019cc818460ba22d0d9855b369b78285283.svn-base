package com.aspire.birp.modules.smartQuery.query.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SqListFilterDesc entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SQ_LIST_FILTER_DESC")
public class SqListFilterDesc implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 430694599155355758L;
	// Fields

	private String id;
	private String info;
	private String listId;

	// Constructors

	/** default constructor */
	public SqListFilterDesc() {
	}

	/** full constructor */
	public SqListFilterDesc(String id, String info, String listId) {
		this.id = id;
		this.info = info;
		this.listId = listId;
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

	@Column(name = "INFO", nullable = false, length = 50)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "LIST_ID", nullable = false, length = 50)
	public String getListId() {
		return this.listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

}