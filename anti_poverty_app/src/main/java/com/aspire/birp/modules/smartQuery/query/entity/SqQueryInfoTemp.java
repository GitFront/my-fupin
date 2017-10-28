package com.aspire.birp.modules.smartQuery.query.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SqQueryInfoTemp entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SQ_QUERY_INFO_TEMP")
public class SqQueryInfoTemp implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -700701276857088008L;
	private String id;
	private String name;
	private String configType;
	private String mpTableId;
	private String sqTable;
	private String fileName;
	private String personalCatalogId;
	private Integer dataStoreDate;
	private String cycType;
	private Integer cycLen;
	private Integer cycInfo;
	private Timestamp validDate;
	private Timestamp invalidDate;
	private String creatorId;
	private String creator;
	private Timestamp createTime;
	private String sqSelectSql;
	private String sqFromSql;
	private String sqFilterSql;
	private String sqSortSql;
	private String sqColumns;
	private Long sort;

	// Constructors

	/** default constructor */
	public SqQueryInfoTemp() {
		super();
	}

	/** minimal constructor */
	public SqQueryInfoTemp(String id, String name, String configType,
			String sqTable,String creatorId , String creator, Timestamp createTime) {
		this.id = id;
		this.name = name;
		this.configType = configType;
		this.sqTable = sqTable;
		this.creatorId = creatorId;
		this.creator = creator;
		this.createTime = createTime;
	}
	

	/** full constructor */
	public SqQueryInfoTemp(String id, String name, String configType,
			String mpTableId, String sqTable, String fileName,
			String personalCatalogId, Integer dataStoreDate, String cycType,
			Integer cycLen, Integer cycInfo, Timestamp validDate,
			Timestamp invalidDate, String creatorId, String creator,
			Timestamp createTime, String sqSelectSql, String sqFromSql,
			String sqFilterSql, String sqSortSql, String sqColumns, Long sort) {
		super();
		this.id = id;
		this.name = name;
		this.configType = configType;
		this.mpTableId = mpTableId;
		this.sqTable = sqTable;
		this.fileName = fileName;
		this.personalCatalogId = personalCatalogId;
		this.dataStoreDate = dataStoreDate;
		this.cycType = cycType;
		this.cycLen = cycLen;
		this.cycInfo = cycInfo;
		this.validDate = validDate;
		this.invalidDate = invalidDate;
		this.creatorId = creatorId;
		this.creator = creator;
		this.createTime = createTime;
		this.sqSelectSql = sqSelectSql;
		this.sqFromSql = sqFromSql;
		this.sqFilterSql = sqFilterSql;
		this.sqSortSql = sqSortSql;
		this.sqColumns = sqColumns;
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

	@Column(name = "NAME", nullable = false, length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CONFIG_TYPE", nullable = false, length = 1)
	public String getConfigType() {
		return this.configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	@Column(name = "MP_TABLE_ID", length = 2000,updatable=false)
	public String getMpTableId() {
		return this.mpTableId;
	}

	public void setMpTableId(String mpTableId) {
		this.mpTableId = mpTableId;
	}

	@Column(name = "SQ_TABLE", nullable = false, length = 2000,updatable=false)
	public String getSqTable() {
		return this.sqTable;
	}

	public void setSqTable(String sqTable) {
		this.sqTable = sqTable;
	}

	@Column(name = "FILE_NAME", length = 200)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "PERSONAL_CATALOG_ID", length = 50)
	public String getPersonalCatalogId() {
		return this.personalCatalogId;
	}

	public void setPersonalCatalogId(String personalCatalogId) {
		this.personalCatalogId = personalCatalogId;
	}

	@Column(name = "DATA_STORE_DATE", precision = 10, scale = 0)
	public Integer getDataStoreDate() {
		return this.dataStoreDate;
	}

	public void setDataStoreDate(Integer dataStoreDate) {
		this.dataStoreDate = dataStoreDate;
	}

	@Column(name = "CYC_TYPE", length = 1)
	public String getCycType() {
		return this.cycType;
	}

	public void setCycType(String cycType) {
		this.cycType = cycType;
	}

	@Column(name = "CYC_LEN", precision = 16, scale = 0)
	public Integer getCycLen() {
		return this.cycLen;
	}

	public void setCycLen(Integer cycLen) {
		this.cycLen = cycLen;
	}

	@Column(name = "CYC_INFO", precision = 16, scale = 0)
	public Integer getCycInfo() {
		return this.cycInfo;
	}

	public void setCycInfo(Integer cycInfo) {
		this.cycInfo = cycInfo;
	}

	@Column(name = "VALID_DATE", length = 7)
	public Timestamp getValidDate() {
		return this.validDate;
	}

	public void setValidDate(Timestamp validDate) {
		this.validDate = validDate;
	}

	@Column(name = "INVALID_DATE", length = 7)
	public Timestamp getInvalidDate() {
		return this.invalidDate;
	}

	public void setInvalidDate(Timestamp invalidDate) {
		this.invalidDate = invalidDate;
	}
	
	@Column(name = "CREATOR_ID", nullable = false, length = 50,updatable=false)
	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
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
	

	@Column(name = "SQ_COLUMNS", length = 4000)
	public String getSqColumns() {
		return sqColumns;
	}

	public void setSqColumns(String sqColumns) {
		this.sqColumns = sqColumns;
	}

	@Column(name = "SQ_SELECT_SQL", length = 4000)
	public String getSqSelectSql() {
		return sqSelectSql;
	}

	public void setSqSelectSql(String sqSelectSql) {
		this.sqSelectSql = sqSelectSql;
	}

	@Column(name = "SQ_FROM_SQL", length = 4000)
	public String getSqFromSql() {
		return sqFromSql;
	}

	public void setSqFromSql(String sqFromSql) {
		this.sqFromSql = sqFromSql;
	}

	@Column(name = "SQ_FILTER_SQL", length = 4000)
	public String getSqFilterSql() {
		return sqFilterSql;
	}

	public void setSqFilterSql(String sqFilterSql) {
		this.sqFilterSql = sqFilterSql;
	}

	@Column(name = "SQ_SORT_SQL", length = 4000)
	public String getSqSortSql() {
		return sqSortSql;
	}

	public void setSqSortSql(String sqSortSql) {
		this.sqSortSql = sqSortSql;
	}
	

}