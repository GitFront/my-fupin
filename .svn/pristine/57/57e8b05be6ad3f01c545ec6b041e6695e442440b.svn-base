package com.aspire.birp.modules.smartQuery.market.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SqPersonalCatalogShareId entity. 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class SqPersonalCatalogShareId implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 8947389825623753432L;
	private String personalCatalogId;
	private String userId;

	// Constructors

	/** default constructor */
	public SqPersonalCatalogShareId() {
	}
	
	public SqPersonalCatalogShareId(String userId) {
		this.userId = userId;
	}

	/** full constructor */
	public SqPersonalCatalogShareId(String personalCatalogId, String userId) {
		this.personalCatalogId = personalCatalogId;
		this.userId = userId;
	}

	// Property accessors

	@Column(name = "PERSONAL_CATALOG_ID", nullable = false, length = 50)
	public String getPersonalCatalogId() {
		return this.personalCatalogId;
	}

	public void setPersonalCatalogId(String personalCatalogId) {
		this.personalCatalogId = personalCatalogId;
	}

	@Column(name = "USER_ID", nullable = false, length = 50)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SqPersonalCatalogShareId))
			return false;
		SqPersonalCatalogShareId castOther = (SqPersonalCatalogShareId) other;

		return ((this.getPersonalCatalogId() == castOther
				.getPersonalCatalogId()) || (this.getPersonalCatalogId() != null
				&& castOther.getPersonalCatalogId() != null && this
				.getPersonalCatalogId()
				.equals(castOther.getPersonalCatalogId())))
				&& ((this.getUserId() == castOther.getUserId()) || (this
						.getUserId() != null && castOther.getUserId() != null && this
						.getUserId().equals(castOther.getUserId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getPersonalCatalogId() == null ? 0 : this
						.getPersonalCatalogId().hashCode());
		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		return result;
	}

}