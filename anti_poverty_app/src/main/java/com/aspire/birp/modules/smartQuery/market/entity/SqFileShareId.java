package com.aspire.birp.modules.smartQuery.market.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SqFileShareId entity. 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class SqFileShareId implements java.io.Serializable {

	
	// Fields
	private static final long serialVersionUID = -6584374708843727449L;
	private String fileId;
	private String userId;

	// Constructors

	/** default constructor */
	public SqFileShareId() {
	}
	
	/** full constructor */
	public SqFileShareId(String userId) {
		this.userId = userId;
	}

	/** full constructor */
	public SqFileShareId(String fileId, String userId) {
		this.fileId = fileId;
		this.userId = userId;
	}

	// Property accessors

	@Column(name = "FILE_ID", nullable = false, length = 50)
	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
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
		if (!(other instanceof SqFileShareId))
			return false;
		SqFileShareId castOther = (SqFileShareId) other;

		return ((this.getFileId() == castOther.getFileId()) || (this
				.getFileId() != null && castOther.getFileId() != null && this
				.getFileId().equals(castOther.getFileId())))
				&& ((this.getUserId() == castOther.getUserId()) || (this
						.getUserId() != null && castOther.getUserId() != null && this
						.getUserId().equals(castOther.getUserId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getFileId() == null ? 0 : this.getFileId().hashCode());
		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		return result;
	}

}