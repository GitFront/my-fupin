package com.aspire.birp.modules.dataMapping.mapping.vo;

import java.sql.Timestamp;

import com.aspire.bi.common.constant.DateConstant;
import com.aspire.bi.common.util.DateUtils;


/**
 * DmColumnMapping entity. @author MyEclipse Persistence Tools
 */
public class DmColumnMappingVO {

	// Fields
	private String id;
	private String columnName;
	private String mappingName;
	private String columnType;
	private String columnTypeName;
	private String dataType;
	private Long dataLength;
	private Long dataPrecision;
	private String nullable;
	private String columnId;
	private String mpTableId;
	private String mpTable;
	private String tableMappingName;
	private String creator;
	private String createTime;
	private Long sort;
	private String isKeys;

	// Constructors

	/** default constructor */
	public DmColumnMappingVO() {
	}

	/** minimal constructor */
	public DmColumnMappingVO(String id, String columnName, String mappingName,
			String columnType, String dataType, String mpTableId,
			String mpTable, String tableMappingName, Long sort) {
		super();
		this.id = id;
		this.columnName = columnName;
		this.mappingName = mappingName;
		this.columnType = columnType;
		this.dataType = dataType;
		this.mpTableId = mpTableId;
		this.mpTable = mpTable;
		this.tableMappingName = tableMappingName;
		this.sort = sort;
	}
	
	public DmColumnMappingVO(String id, String columnName, String mappingName,
			String columnType, String dataType, Long dataLength,
			Long dataPrecision, String nullable, String columnId,
			String mpTableId, String mpTable, String creator,
			String createTime) {
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
	public DmColumnMappingVO(String id, String columnName, String mappingName,
			String columnType, String columnTypeName, String dataType,
			Long dataLength, Long dataPrecision, String nullable,
			String columnId, String mpTableId, String mpTable,
			String tableMappingName, String creator, Timestamp createTime, Long sort) {
		super();
		this.id = id;
		this.columnName = columnName;
		this.mappingName = mappingName;
		this.columnType = columnType;
		this.columnTypeName = columnTypeName;
		this.dataType = dataType;
		this.dataLength = dataLength;
		this.dataPrecision = dataPrecision;
		this.nullable = nullable;
		this.columnId = columnId;
		this.mpTableId = mpTableId;
		this.mpTable = mpTable;
		this.tableMappingName = tableMappingName;
		this.creator = creator;
		this.createTime = DateUtils.dateTimeToString(createTime, DateConstant.YEAR_MOUTH_DAY);
		this.sort = sort;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getMappingName() {
		return mappingName;
	}

	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Long getDataLength() {
		return dataLength;
	}

	public void setDataLength(Long dataLength) {
		this.dataLength = dataLength;
	}

	public Long getDataPrecision() {
		return dataPrecision;
	}

	public void setDataPrecision(Long dataPrecision) {
		this.dataPrecision = dataPrecision;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getMpTableId() {
		return mpTableId;
	}

	public void setMpTableId(String mpTableId) {
		this.mpTableId = mpTableId;
	}

	public String getMpTable() {
		return mpTable;
	}

	public void setMpTable(String mpTable) {
		this.mpTable = mpTable;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getTableMappingName() {
		return tableMappingName;
	}

	public void setTableMappingName(String tableMappingName) {
		this.tableMappingName = tableMappingName;
	}

	public String getIsKeys() {
		return isKeys;
	}

	public void setIsKeys(String isKeys) {
		this.isKeys = isKeys;
	}

	

}