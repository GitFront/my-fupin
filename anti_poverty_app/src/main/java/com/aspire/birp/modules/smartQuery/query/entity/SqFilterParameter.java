package com.aspire.birp.modules.smartQuery.query.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SqFilterParameter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SQ_FILTER_PARAMETER")
public class SqFilterParameter implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 5681993483038618194L;
	
	private String id;
	private String sqTable;
	private String sqColumn;
	private String parameterType;
	private String parameterKey;
	private String valueType;
	private String value;
	private String monthCycType;
	private Integer monthCycValue;
	private String dateCycType;
	private Integer dateCycValue;
	private String sqId;

	// Constructors

	/** default constructor */
	public SqFilterParameter() {
	}

	/** minimal constructor */
	public SqFilterParameter(String id, String sqTable, String sqColumn, String parameterType,
			String parameterKey, String valueType, String sqId) {
		this.id = id;
		this.sqTable = sqTable;
		this.sqColumn = sqColumn;
		this.parameterType = parameterType;
		this.parameterKey = parameterKey;
		this.valueType = valueType;
		this.sqId = sqId;
	}

	/** full constructor */
	public SqFilterParameter(String id, String sqTable, String sqColumn,
			String parameterType, String parameterKey, String valueType,
			String value, String monthCycType, Integer monthCycValue,
			String dateCycType, Integer dateCycValue, String sqId) {
		super();
		this.id = id;
		this.sqTable = sqTable;
		this.sqColumn = sqColumn;
		this.parameterType = parameterType;
		this.parameterKey = parameterKey;
		this.valueType = valueType;
		this.value = value;
		this.monthCycType = monthCycType;
		this.monthCycValue = monthCycValue;
		this.dateCycType = dateCycType;
		this.dateCycValue = dateCycValue;
		this.sqId = sqId;
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

	@Column(name = "PARAMETER_TYPE", nullable = false, length = 10)
	public String getParameterType() {
		return this.parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	@Column(name = "PARAMETER_KEY", nullable = false, length = 200)
	public String getParameterKey() {
		return this.parameterKey;
	}

	public void setParameterKey(String parameterKey) {
		this.parameterKey = parameterKey;
	}

	@Column(name = "VALUE_TYPE", nullable = false, length = 1)
	public String getValueType() {
		return this.valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	@Column(name = "VALUE", length = 1000)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "MONTH_CYC_TYPE", length = 20)
	public String getMonthCycType() {
		return this.monthCycType;
	}

	public void setMonthCycType(String monthCycType) {
		this.monthCycType = monthCycType;
	}

	@Column(name = "MONTH_CYC_VALUE", precision = 10, scale = 0)
	public Integer getMonthCycValue() {
		return this.monthCycValue;
	}

	public void setMonthCycValue(Integer monthCycValue) {
		this.monthCycValue = monthCycValue;
	}

	@Column(name = "DATE_CYC_TYPE", length = 20)
	public String getDateCycType() {
		return this.dateCycType;
	}

	public void setDateCycType(String dateCycType) {
		this.dateCycType = dateCycType;
	}

	@Column(name = "DATE_CYC_VALUE", precision = 10, scale = 0)
	public Integer getDateCycValue() {
		return this.dateCycValue;
	}

	public void setDateCycValue(Integer dateCycValue) {
		this.dateCycValue = dateCycValue;
	}

	@Column(name = "SQ_ID", nullable = false, length = 50)
	public String getSqId() {
		return this.sqId;
	}

	public void setSqId(String sqId) {
		this.sqId = sqId;
	}

}