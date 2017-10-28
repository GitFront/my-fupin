package com.aspire.birp.modules.dataMapping.mapping.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DMTableMapping entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DM_TABLE_MAPPING")
public class DmTableMapping implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7455847133892644068L;
	// Fields

	private String id;
	private String tableName;
	private String mappingName;
	private String tableType;
	private String status;
	private String isPerimssion;
	private String cycType;
	private String storageType;
	private String creator;
	private Timestamp createTime;
	private Long sort;
	

	/** default constructor */
	public DmTableMapping() {
	}

	/** minimal constructor */
	public DmTableMapping(String id) {
		this.id = id;
	}
	
	public DmTableMapping(String id, String tableName, String mappingName,
			String tableType, String status,String isPerimssion, String creator,
			Timestamp createTime, String cycType, String storageType) {
		this.id = id;
		this.tableName = tableName;
		this.mappingName = mappingName;
		this.tableType = tableType;
		this.status = status;
		this.isPerimssion = isPerimssion;
		this.creator = creator;
		this.createTime = createTime;
		this.cycType=cycType;
		this.storageType=storageType;
	}
	
	
	/** full constructor 
	 * @param storageType 
	 * @param cycType */
	public DmTableMapping(String id, String tableName, String mappingName,
			String tableType, String status, String catalogId,
			String isPerimssion, String creator,
			Timestamp createTime, Long sort, String storageType, String cycType) {
		super();
		this.id = id;
		this.tableName = tableName;
		this.mappingName = mappingName;
		this.tableType = tableType;
		this.status = status;
		this.isPerimssion = isPerimssion;
		this.creator = creator;
		this.createTime = createTime;
		this.sort = sort;
		this.cycType=cycType;
		this.storageType=storageType;
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

	@Column(name = "TABLE_NAME", length = 100,updatable=false)
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "MAPPING_NAME", length = 200)
	public String getMappingName() {
		return this.mappingName;
	}

	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}

	@Column(name = "TABLE_TYPE", length = 20)
	public String getTableType() {
		return this.tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	@Column(name = "STATUS", length = 10,updatable=false)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "IS_PERIMSSION",nullable = false, length = 1)
	public String getIsPerimssion() {
		return isPerimssion;
	}

	public void setIsPerimssion(String isPerimssion) {
		this.isPerimssion = isPerimssion;
	}
	


	@Column(name = "CREATOR", length = 100,updatable=false)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CREATE_TIME", length = 7,updatable=false)
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


	@Column(name = "CYC_TYPE", length = 2)
	public String getCycType() {
		return cycType;
	}

	public void setCycType(String cycType) {
		this.cycType = cycType;
	}


	@Column(name = "STORAGE_TYPE", length = 2)
	public String getStorageType() {
		return storageType;
	}

	
	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
	

}