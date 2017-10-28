package com.aspire.birp.modules.dataMapping.mapping.vo;

import java.sql.Date;
import java.util.List;

import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableCatalog;
import com.google.common.collect.Lists;


public class DmTableCatalogVO {

	// Fields
	private String id;
	private String parentId;
	private String name;
	private String fullPath;
	private String creator;
	private Date createTime;
	private Long sort;
	private String type;
	private List<DmTableCatalog> children = Lists.newArrayList();	
	// Constructors

	/** default constructor */
	public DmTableCatalogVO() {
	}

	/** minimal constructor */
	public DmTableCatalogVO(String id) {
		this.id = id;
	}
	
	public DmTableCatalogVO(String id,String name,String fullPath) {
		this.id = id;
		this.name = name;
		this.fullPath = fullPath;
	}

	/** full constructor */
	public DmTableCatalogVO(String id, String parentId, String name,
			String fullPath, String creator,
			Date createTime, Long sort) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.fullPath = fullPath;
		this.creator = creator;
		this.createTime = createTime;
		this.sort = sort;
	}

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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public List<DmTableCatalog> getChildren() {
		return children;
	}

	public void setChildren(List<DmTableCatalog> children) {
		this.children = children;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}