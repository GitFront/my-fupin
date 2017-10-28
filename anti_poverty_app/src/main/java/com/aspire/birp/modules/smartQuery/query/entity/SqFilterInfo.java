package com.aspire.birp.modules.smartQuery.query.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SqFilterInfo entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SQ_FILTER_INFO")
public class SqFilterInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1024722550590071427L;
	private String id;
	private String filterType;
	private String relationId;
	private String relation;
	private String mpTableId;
	private String sqTable;	
	private String mpColumnId;
	private String sqColumn;
	private String sqColumnType;
	private String formulaId;
	private String formula;
	private String value;
	private String monthCycType;
	private Integer monthCycValue;
	private String dateCycType;
	private Integer dateCycValue;
	private String sqId;
	private String creator;
	private Timestamp createTime;
	private Long sort;

	// Constructors

	/** default constructor */
	public SqFilterInfo() {
	}

	/** minimal constructor */
	public SqFilterInfo(String id,String filterType, String relationId, String relation, String mpTableId,String sqTable,
			String mpColumnId, String sqColumn,String sqColumnType, String formulaId,
			String formula, String sqId, String creator, Timestamp createTime) {
		this.id = id;
		this.filterType = filterType;
		this.relationId = relationId;
		this.relation = relation;
		this.mpTableId=mpTableId;
		this.sqTable = sqTable;
		this.mpColumnId = mpColumnId;
		this.sqColumn = sqColumn;
		this.sqColumnType = sqColumnType;
		this.formulaId = formulaId;
		this.formula = formula;
		this.sqId = sqId;
		this.creator = creator;
		this.createTime = createTime;
	}

	/** full constructor */
	public SqFilterInfo(String id, String filterType, String relationId,
			String relation, String mpTableId, String sqTable,
			String mpColumnId, String sqColumn, String sqColumnType,
			String formulaId, String formula, String value,
			String monthCycType, Integer monthCycValue, String dateCycType,
			Integer dateCycValue, String sqId, String creator,
			Timestamp createTime, Long sort) {
		super();
		this.id = id;
		this.filterType = filterType;
		this.relationId = relationId;
		this.relation = relation;
		this.mpTableId = mpTableId;
		this.sqTable = sqTable;
		this.mpColumnId = mpColumnId;
		this.sqColumn = sqColumn;
		this.sqColumnType = sqColumnType;
		this.formulaId = formulaId;
		this.formula = formula;
		this.value = value;
		this.monthCycType = monthCycType;
		this.monthCycValue = monthCycValue;
		this.dateCycType = dateCycType;
		this.dateCycValue = dateCycValue;
		this.sqId = sqId;
		this.creator = creator;
		this.createTime = createTime;
		this.sort = sort;
	}

	public SqFilterInfo(String id, String relationId, String relation,
			String mpTableId, String sqTable,String mpColumnId, String sqColumn, String sqColumnType,
			String formulaId, String formula, String value, String sqId,
			String creator, Object createTime, Long sort) {
		super();
		this.id = id;
		this.relationId = relationId;
		this.relation = relation;
		this.mpTableId = mpTableId;
		this.sqTable = sqTable;
		this.mpColumnId = mpColumnId;
		this.sqColumn = sqColumn;
		this.sqColumnType = sqColumnType;
		this.formulaId = formulaId;
		this.formula = formula;
		this.value = value;
		this.sqId = sqId;
		this.creator = creator;
		this.createTime = Timestamp.valueOf(createTime.toString());
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
	
	@Column(name = "FILTER_TYPE", nullable = false, length = 10)
	public String getFilterType() {
		return this.filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	@Column(name = "RELATION_ID", nullable = false, length = 1)
	public String getRelationId() {
		return this.relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	@Column(name = "RELATION", nullable = false, length = 10)
	public String getRelation() {
		return this.relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	@Column(name = "MP_TABLE_ID", nullable=false, length=50,updatable=false)
	public String getMpTableId() {
		return mpTableId;
	}

	public void setMpTableId(String mpTableId) {
		this.mpTableId = mpTableId;
	}

	@Column(name = "SQ_TABLE", nullable=false, length=50,updatable=false)
	public String getSqTable() {
		return sqTable;
	}

	public void setSqTable(String sqTable) {
		this.sqTable = sqTable;
	}

	@Column(name = "MP_COLUMN_ID", nullable = false, length = 50)
	public String getMpColumnId() {
		return this.mpColumnId;
	}

	public void setMpColumnId(String mpColumnId) {
		this.mpColumnId = mpColumnId;
	}

	@Column(name = "SQ_COLUMN", nullable = false, length = 100)
	public String getSqColumn() {
		return this.sqColumn;
	}

	public void setSqColumn(String sqColumn) {
		this.sqColumn = sqColumn;
	}
	
	@Column(name = "SQ_COLUMN_TYPE", nullable = false, length = 1)
	public String getSqColumnType() {
		return this.sqColumnType;
	}

	public void setSqColumnType(String sqColumnType) {
		this.sqColumnType = sqColumnType;
	}

	@Column(name = "FORMULA_ID", nullable = false, length = 1)
	public String getFormulaId() {
		return this.formulaId;
	}

	public void setFormulaId(String formulaId) {
		this.formulaId = formulaId;
	}

	@Column(name = "FORMULA", nullable = false, length = 10)
	public String getFormula() {
		return this.formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
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

	@Column(name = "SQ_ID", nullable = false, length = 50,updatable=false)
	public String getSqId() {
		return this.sqId;
	}

	public void setSqId(String sqId) {
		this.sqId = sqId;
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

	
	

}