package com.aspire.birp.modules.dataMapping.mapping.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DmColumnMapping entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DM_COLUMN_MAPPING")
public class DmColumnMapping implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -4309508400032085416L;
	private String id;
	private String columnName;
	private String mappingName;
	private String columnType;
	private String dataType;
	private Long dataLength;
	private Long dataPrecision;
	private String nullable;
	private String columnId;
	private String mpTableId;
	private String mpTable;
	private String creator;
	private Timestamp createTime;
	private Long sort;
	private String isKeys;

	// Constructors

	/** default constructor */
	public DmColumnMapping() {
	}

	/** minimal constructor */
	public DmColumnMapping(String id, String columnName, String mappingName,
			String columnType, String columnId, String mpTableId,
			String mpTable, String creator, Timestamp createTime) {
		this.id = id;
		this.columnName = columnName;
		this.mappingName = mappingName;
		this.columnType = columnType;
		this.columnId = columnId;
		this.mpTableId = mpTableId;
		this.mpTable = mpTable;
		this.creator = creator;
		this.createTime = createTime;
	}
	
	public DmColumnMapping(String id, String columnName, String mappingName,
			String columnType, String dataType, Long dataLength,
			Long dataPrecision, String nullable, String columnId,
			String mpTableId, String mpTable, String creator,
			Timestamp createTime) {
		this.id = id;
		this.columnName = columnName;
		this.mappingName = mappingName;
		this.columnType = columnType;
		this.dataType = dataType;
		this.dataLength = dataLength;
		this.dataPrecision = dataPrecision;
		this.nullable = nullable;
		this.columnId = columnId;
		this.mpTableId = mpTableId;
		this.mpTable = mpTable;
		this.creator = creator;
		this.createTime = createTime;
	}

	/** full constructor */
	public DmColumnMapping(String id, String columnName, String mappingName,
			String columnType, String dataType, Long dataLength,
			Long dataPrecision, String nullable, String columnId,
			String mpTableId, String mpTable, String creator,
			Timestamp createTime, Long sort,String isKeys) {
		this.id = id;
		this.columnName = columnName;
		this.mappingName = mappingName;
		this.columnType = columnType;
		this.dataType = dataType;
		this.dataLength = dataLength;
		this.dataPrecision = dataPrecision;
		this.nullable = nullable;
		this.columnId = columnId;
		this.mpTableId = mpTableId;
		this.mpTable = mpTable;
		this.creator = creator;
		this.createTime = createTime;
		this.sort = sort;
		this.isKeys=isKeys;
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

	@Column(name = "COLUMN_NAME", nullable = false, length = 100)
	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Column(name = "MAPPING_NAME", nullable = true, length = 200)
	public String getMappingName() {
		return this.mappingName;
	}

	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}

	@Column(name = "COLUMN_TYPE", nullable = false, length = 1)
	public String getColumnType() {
		return this.columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	@Column(name = "DATA_TYPE", length = 20,updatable=false)
	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name = "DATA_LENGTH", precision = 10, scale = 0,updatable=false)
	public Long getDataLength() {
		return this.dataLength;
	}

	public void setDataLength(Long dataLength) {
		this.dataLength = dataLength;
	}

	@Column(name = "DATA_PRECISION", precision = 10, scale = 0,updatable=false)
	public Long getDataPrecision() {
		return this.dataPrecision;
	}

	public void setDataPrecision(Long dataPrecision) {
		this.dataPrecision = dataPrecision;
	}

	@Column(name = "NULLABLE", length = 1,updatable=false)
	public String getNullable() {
		return this.nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	@Column(name = "COLUMN_ID", nullable = false, length = 20,updatable=false)
	public String getColumnId() {
		return this.columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	@Column(name = "MP_TABLE_ID", nullable = false, length = 50,updatable=false)
	public String getMpTableId() {
		return this.mpTableId;
	}

	public void setMpTableId(String mpTableId) {
		this.mpTableId = mpTableId;
	}

	@Column(name = "MP_TABLE", nullable = false, length = 200,updatable=false)
	public String getMpTable() {
		return this.mpTable;
	}

	public void setMpTable(String mpTable) {
		this.mpTable = mpTable;
	}

	@Column(name = "CREATOR", nullable = false, length = 100,updatable=false)
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

	@Column(name = "SORT", precision = 10, scale = 0)
	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	@Column(name = "IS_KEYS",length = 1)
	public String getIsKeys() {
		return isKeys;
	}

	public void setIsKeys(String isKeys) {
		this.isKeys = isKeys;
	}
	
	
	

}