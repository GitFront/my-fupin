package com.aspire.birp.modules.smartQuery.market.entity;

import java.sql.Timestamp;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * SqPersonalCatalogShare entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SQ_PERSONAL_CATALOG_SHARE")
public class SqPersonalCatalogShare implements java.io.Serializable {

	
	// Fields
	private static final long serialVersionUID = -4131998624149103802L;
	private SqPersonalCatalogShareId id;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public SqPersonalCatalogShare() {
	}

	/** minimal constructor */
	public SqPersonalCatalogShare(SqPersonalCatalogShareId id) {
		this.id = id;
	}
	
	public SqPersonalCatalogShare(String userId) {
		this.id = new SqPersonalCatalogShareId(userId);
	}

	/** full constructor */
	public SqPersonalCatalogShare(SqPersonalCatalogShareId id,
			Timestamp createTime) {
		this.id = id;
		this.createTime = createTime;
	}
	
	public SqPersonalCatalogShare(String personalCatalogId,String userId,
			Timestamp createTime) {
		this.id = new SqPersonalCatalogShareId(personalCatalogId,userId);
		this.createTime = createTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "personalCatalogId", column = @Column(name = "PERSONAL_CATALOG_ID", nullable = false, length = 50)),
			@AttributeOverride(name = "userId", column = @Column(name = "USER_ID", nullable = false, length = 50)) })
	public SqPersonalCatalogShareId getId() {
		return this.id;
	}

	public void setId(SqPersonalCatalogShareId id) {
		this.id = id;
	}

	@Column(name = "CREATE_TIME", length = 7)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}