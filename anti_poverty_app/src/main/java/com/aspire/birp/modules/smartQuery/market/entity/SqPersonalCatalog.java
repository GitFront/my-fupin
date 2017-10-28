package com.aspire.birp.modules.smartQuery.market.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * SqPersonalCatalog entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SQ_PERSONAL_CATALOG")
public class SqPersonalCatalog implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1468781874285329228L;
	// Fields

	private String id;
	private String parentId;
	private String name;
	private String fullPathId;
	private String fullPath;
	private String creatorId;
	private String creator;
	private String creatorAcc;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Timestamp createTime;
	private String shareStatus;
	private Long sort;
	
	
	// Constructors
	/** default constructor */
	public SqPersonalCatalog() {
	}

	/** minimal constructor */
	public SqPersonalCatalog(String id, String parentId, String name,
			String fullPathId,String fullPath, String creatorId,
			String creator,String creatorAcc,Timestamp createTime, String shareStatus) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.fullPathId = fullPathId;
		this.fullPath = fullPath;
		this.creatorId = creatorId;
		this.creator = creator;
		this.creatorAcc = creatorAcc;
		this.createTime = createTime;
		this.shareStatus = shareStatus;
	}
	
	public SqPersonalCatalog(String id, String parentId, String name,
			String fullPathId,String fullPath, String creatorId, String creator,
			Timestamp createTime, Long sort) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.fullPathId = fullPathId;
		this.fullPath = fullPath;
		this.creatorId = creatorId;
		this.creator = creator;
		this.createTime = createTime;
		this.sort = sort;
	}

	/** full constructor */
	public SqPersonalCatalog(String id, String parentId, String name,
			String fullPathId,String fullPath, String creatorId, String creator,String creatorAcc,
			Timestamp createTime, String shareStatus, Long sort) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.fullPathId = fullPathId;
		this.fullPath = fullPath;
		this.creatorId = creatorId;
		this.creator = creator;
		this.creatorAcc = creatorAcc;
		this.createTime = createTime;
		this.shareStatus = shareStatus;
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

	@Column(name = "PARENT_ID", nullable = false, length = 50,updatable=false)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "FULL_PATH_ID", nullable = false, length = 4000,updatable=false)
	public String getFullPathId() {
		return fullPathId;
	}

	public void setFullPathId(String fullPathId) {
		this.fullPathId = fullPathId;
	}

	@Column(name = "FULL_PATH", nullable = false, length = 500)
	public String getFullPath() {
		return this.fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	@Column(name = "CREATOR_ID", nullable = false, length = 50,updatable=false)
	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Column(name = "CREATOR", nullable = false, length = 200,updatable=false)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CREATOR_ACC", nullable = false, length = 200,updatable=false)
	public String getCreatorAcc() {
		return creatorAcc;
	}

	public void setCreatorAcc(String creatorAcc) {
		this.creatorAcc = creatorAcc;
	}

	@Column(name = "CREATE_TIME", nullable = false, length = 7,updatable=false)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "SHARE_STATUS", nullable = false, length = 1)
	public String getShareStatus() {
		return this.shareStatus;
	}

	public void setShareStatus(String shareStatus) {
		this.shareStatus = shareStatus;
	}

	@Column(name = "SORT", precision = 10, scale = 0,updatable=false)
	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

}