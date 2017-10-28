package com.aspire.birp.modules.smartQuery.market.vo;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * SqPersonalCatalog entity. 
 * @author MyEclipse Persistence Tools
 */
public class SqPersonalCatalogVO {

	// Fields

	private String id;
	private String parentId;
	private String name;
	private String fullPath;
	private String creatorId;
	private String creator;
	private String createTime;
	private String shareStatus;
	private boolean isRead = false;
	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	private Long sort;

	// Constructors

	/** default constructor */
	public SqPersonalCatalogVO() {
	}

	/** minimal constructor */
	public SqPersonalCatalogVO(String id, String parentId, String name,
			String fullPath, String creatorId, String creator,
			String createTime, String shareStatus) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.fullPath = fullPath;
		this.creatorId = creatorId;
		this.creator = creator;
		this.createTime = createTime;
		this.shareStatus = shareStatus;
	}
	
	public SqPersonalCatalogVO(String id, String parentId, String name,
			String fullPath, String creatorId, String creator,
			String createTime, Long sort) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.fullPath = fullPath;
		this.creatorId = creatorId;
		this.creator = creator;
		this.createTime = createTime;
		this.sort = sort;
	}

	/** full constructor */
	public SqPersonalCatalogVO(String id, String parentId, String name,
			String fullPath, String creatorId, String creator,
			String createTime, String shareStatus, Long sort) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.fullPath = fullPath;
		this.creatorId = creatorId;
		this.creator = creator;
		this.createTime = createTime;
		this.shareStatus = shareStatus;
		this.sort = sort;
	}
	
	// Property accessors
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
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

	public String getShareStatus() {
		return shareStatus;
	}

	public void setShareStatus(String shareStatus) {
		this.shareStatus = shareStatus;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	
	

}