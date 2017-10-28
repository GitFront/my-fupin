package com.aspire.birp.modules.dataMapping.mapping.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * DmColumnMapping entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DM_CATALOG_BINDING")
public class DmCatalogBinding implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -4309508400032085416L;
	
	//private Integer id;
	private String catalogId;
	private String tableId;
	private String creator;
	private Timestamp createTime;
 
	
	

/*
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return id;
	}

	

	public void setId(Integer id) {
		this.id = id;
	}*/
	
	
	
	@Id
	@Column(name = "CATALOG_ID", unique = true,nullable = false, length = 50,updatable=false)
	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
   
	@Id
	@Column(name = "TABLE_ID",unique = true, nullable = false, length = 50,updatable=false)
	public String getTableId() {
		return this.tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	@Column(name = "CREATOR", nullable = false, length = 50,updatable=false)
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

	

}