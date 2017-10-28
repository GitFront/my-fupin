package com.aspire.birp.modules.dataMapping.mapping.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.aspire.auth.common.utils.StringUtils;
import com.aspire.auth.modules.sys.entity.Office;
import com.aspire.birp.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;

/**
 * DmTableCatalog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DM_TABLE_CATALOG")
public class DmTableCatalog implements java.io.Serializable {

	private static final long serialVersionUID = 5638848380208917351L;
	// Fields
	private String id;
	private String parentId;
	private String name;
	private String fullPath;
	private String creator;
	private String type;
	private Timestamp createTime;
	private Long sort;
	private List<DmTableCatalog> children = Lists.newArrayList();	//映射子节点
	private List<DmTableMapping> dmTableMappingList = Lists.newArrayList();	//映射 数据表映射
	
	
	
	// Constructors

	/** default constructor */
	public DmTableCatalog() {
		
	}

	/** minimal constructor */
	public DmTableCatalog(String id) {
		this.id = id;
	}
	
	public DmTableCatalog(String id,String name,String fullPath) {
		this.id = id;
		this.name = name;
		this.fullPath = fullPath;
	}

	/** full constructor */
	public DmTableCatalog(String id, String parentId, String name,
			String fullPath, String creator,
			Timestamp createTime, Long sort,String type) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.fullPath = fullPath;
		this.creator = creator;
		this.createTime = createTime;
		this.sort = sort;
		this.type = type;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 50,updatable=false)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "PARENT_ID", length = 50,updatable=false)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "FULL_PATH", length = 500)
	public String getFullPath() {
		return this.fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	@Column(name = "CREATOR", length = 50,updatable=false)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CREATE_TIME", length = 7,updatable=false)
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
	
	
	
	@OneToMany(mappedBy = "parentId", fetch=FetchType.LAZY ,cascade={CascadeType.ALL},orphanRemoval=true)
	@OrderBy(value="createTime") @Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	public List<DmTableCatalog> getChildren() {
		return children;
	}
	
	public void setChildren(List<DmTableCatalog> children) {
		this.children = children;
	}
	/*@OneToMany(mappedBy="dmTableCatalog",cascade=CascadeType.ALL)
	public List<DmCatalogBinding> getDmCatalogBindingList() {
		return dmCatalogBindingList;
	}

	public void setDmCatalogBindingList(List<DmCatalogBinding> dmCatalogBindingList) {
		this.dmCatalogBindingList = dmCatalogBindingList;
	}*/

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "DM_CATALOG_BINDING", joinColumns = { @JoinColumn(name = "CATALOG_ID") }, inverseJoinColumns = { @JoinColumn(name = "TABLE_ID") })
	@OrderBy("id") @Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	public List<DmTableMapping> getDmTableMappingList() {
		return dmTableMappingList;
	}
	@Column(name = "TYPE", length =100)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDmTableMappingList(List<DmTableMapping> dmTableMappingList) {
		this.dmTableMappingList = dmTableMappingList;
	}
	

/*	@Transient
	public String getTmids() {
		List<String> tmids = Lists.newArrayList();
		for (DmCatalogBinding dmCatalogBinding : dmCatalogBindingList) {
			tmids.add(dmCatalogBinding.get);
		}
		return StringUtils.join(tmids, ",");
	}*/
	

	/*public void setTmids(String tmids) {
		dmCatalogBindingList = Lists.newArrayList();
		String creator = UserUtils.getUser().getName();
		Timestamp createTime = new Timestamp(new Date().getTime());
		if (tmids != null){
			String[] ids = StringUtils.split(tmids, ",");
			for (String tableId : ids) {
				DmCatalogBinding dmCatalogBinding = new DmCatalogBinding();
				dmCatalogBinding.setCreator(creator);
				dmCatalogBinding.setCreateTime(createTime);
				dmCatalogBinding.setDmTableCatalog(new DmTableCatalog(this.id));
				dmCatalogBinding.setDmTableMapping(new DmTableMapping(tableId));
				
				dmCatalogBindingList.add(dmCatalogBinding);
			}
		}
	}*/
	
	
	
	
	
	
	
	
	
	
}