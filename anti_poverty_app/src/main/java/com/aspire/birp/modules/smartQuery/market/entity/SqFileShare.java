package com.aspire.birp.modules.smartQuery.market.entity;

import java.sql.Timestamp;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * SqFileShare entity.
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SQ_FILE_SHARE")
public class SqFileShare implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -1393560813687501842L;
	private SqFileShareId id;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public SqFileShare() {
	}

	/** minimal constructor */
	public SqFileShare(SqFileShareId id) {
		this.id = id;
	}
	
	public SqFileShare(String userId) {
		this.id = new SqFileShareId(userId);	
	}
	
	public SqFileShare(String fileId,String userId, Timestamp createTime) {
		this.id = new SqFileShareId(fileId,userId);
		this.createTime = createTime;		
	}

	/** full constructor */
	public SqFileShare(SqFileShareId id, Timestamp createTime) {
		this.id = id;
		this.createTime = createTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "fileId", column = @Column(name = "FILE_ID", nullable = false, length = 50)),
			@AttributeOverride(name = "userId", column = @Column(name = "USER_ID", nullable = false, length = 50)) })
	public SqFileShareId getId() {
		return this.id;
	}

	public void setId(SqFileShareId id) {
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