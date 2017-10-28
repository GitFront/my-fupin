package com.aspire.birp.modules.dataMapping.mapping.entity;

import java.sql.Timestamp;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * DmTableMappingAssociation entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DM_TABLE_MAPPING_ASSOCIATION")
public class DmTableMappingAssociation implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -3017835518537770588L;
	private DmTableMappingAssociationId id;
	private String mpTableCode1;
	private String mpColumnId1;
	private String mpColumnCode1;
	private String mpTableCode2;
	private String mpColumnId2;
	private String mpColumnCode2;
	private String creator;
	private Timestamp createTime;
	private Long sort;

	// Constructors

	/** default constructor */
	public DmTableMappingAssociation() {
	}

	/** minimal constructor */
	public DmTableMappingAssociation(DmTableMappingAssociationId id,
			String mpTableCode1, String mpColumnId1, String mpColumnCode1,
			String mpTableCode2, String mpColumnId2, String mpColumnCode2,
			String creator, Timestamp createTime) {
		this.id = id;
		this.mpTableCode1 = mpTableCode1;
		this.mpColumnId1 = mpColumnId1;
		this.mpColumnCode1 = mpColumnCode1;
		this.mpTableCode2 = mpTableCode2;
		this.mpColumnId2 = mpColumnId2;
		this.mpColumnCode2 = mpColumnCode2;
		this.creator = creator;
		this.createTime = createTime;
	}

	/** full constructor */
	public DmTableMappingAssociation(DmTableMappingAssociationId id,
			String mpTableCode1, String mpColumnId1, String mpColumnCode1,
			String mpTableCode2, String mpColumnId2, String mpColumnCode2,
			String creator, Timestamp createTime, Long sort) {
		this.id = id;
		this.mpTableCode1 = mpTableCode1;
		this.mpColumnId1 = mpColumnId1;
		this.mpColumnCode1 = mpColumnCode1;
		this.mpTableCode2 = mpTableCode2;
		this.mpColumnId2 = mpColumnId2;
		this.mpColumnCode2 = mpColumnCode2;
		this.creator = creator;
		this.createTime = createTime;
		this.sort = sort;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "mpTableId1", column = @Column(name = "MP_TABLE_ID1", nullable = false, length = 50)),
			@AttributeOverride(name = "mpTableId2", column = @Column(name = "MP_TABLE_ID2", nullable = false, length = 50)) })
	public DmTableMappingAssociationId getId() {
		return this.id;
	}

	public void setId(DmTableMappingAssociationId id) {
		this.id = id;
	}

	@Column(name = "MP_TABLE_CODE1", nullable = false, length = 50)
	public String getMpTableCode1() {
		return this.mpTableCode1;
	}

	public void setMpTableCode1(String mpTableCode1) {
		this.mpTableCode1 = mpTableCode1;
	}

	@Column(name = "MP_COLUMN_ID1", nullable = false, length = 50)
	public String getMpColumnId1() {
		return this.mpColumnId1;
	}

	public void setMpColumnId1(String mpColumnId1) {
		this.mpColumnId1 = mpColumnId1;
	}

	@Column(name = "MP_COLUMN_CODE1", nullable = false, length = 50)
	public String getMpColumnCode1() {
		return this.mpColumnCode1;
	}

	public void setMpColumnCode1(String mpColumnCode1) {
		this.mpColumnCode1 = mpColumnCode1;
	}

	@Column(name = "MP_TABLE_CODE2", nullable = false, length = 50)
	public String getMpTableCode2() {
		return this.mpTableCode2;
	}

	public void setMpTableCode2(String mpTableCode2) {
		this.mpTableCode2 = mpTableCode2;
	}

	@Column(name = "MP_COLUMN_ID2", nullable = false, length = 50)
	public String getMpColumnId2() {
		return this.mpColumnId2;
	}

	public void setMpColumnId2(String mpColumnId2) {
		this.mpColumnId2 = mpColumnId2;
	}

	@Column(name = "MP_COLUMN_CODE2", nullable = false, length = 50)
	public String getMpColumnCode2() {
		return this.mpColumnCode2;
	}

	public void setMpColumnCode2(String mpColumnCode2) {
		this.mpColumnCode2 = mpColumnCode2;
	}

	@Column(name = "CREATOR", nullable = false, length = 200)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CREATE_TIME", nullable = false, length = 7)
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

}