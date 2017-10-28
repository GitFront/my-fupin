package com.aspire.birp.modules.dataMapping.mapping.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * DmTableMappingAssociationId entity. 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class DmTableMappingAssociationId implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 4285784926421222831L;
	private String mpTableId1;
	private String mpTableId2;

	// Constructors

	/** default constructor */
	public DmTableMappingAssociationId() {
	}

	/** full constructor */
	public DmTableMappingAssociationId(String mpTableId1, String mpTableId2) {
		this.mpTableId1 = mpTableId1;
		this.mpTableId2 = mpTableId2;
	}

	// Property accessors

	@Column(name = "MP_TABLE_ID1", nullable = false, length = 50)
	public String getMpTableId1() {
		return this.mpTableId1;
	}

	public void setMpTableId1(String mpTableId1) {
		this.mpTableId1 = mpTableId1;
	}

	@Column(name = "MP_TABLE_ID2", nullable = false, length = 50)
	public String getMpTableId2() {
		return this.mpTableId2;
	}

	public void setMpTableId2(String mpTableId2) {
		this.mpTableId2 = mpTableId2;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DmTableMappingAssociationId))
			return false;
		DmTableMappingAssociationId castOther = (DmTableMappingAssociationId) other;

		return ((this.getMpTableId1() == castOther.getMpTableId1()) || (this
				.getMpTableId1() != null && castOther.getMpTableId1() != null && this
				.getMpTableId1().equals(castOther.getMpTableId1())))
				&& ((this.getMpTableId2() == castOther.getMpTableId2()) || (this
						.getMpTableId2() != null
						&& castOther.getMpTableId2() != null && this
						.getMpTableId2().equals(castOther.getMpTableId2())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getMpTableId1() == null ? 0 : this.getMpTableId1()
						.hashCode());
		result = 37
				* result
				+ (getMpTableId2() == null ? 0 : this.getMpTableId2()
						.hashCode());
		return result;
	}

}