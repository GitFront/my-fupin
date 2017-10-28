package com.aspire.birp.modules.smartQuery.query.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SqListFilterInfo entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SQ_LIST_FILTER_INFO")
public class SqListFilterInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3797321077188298055L;
	// Fields

	private String id;
	private String listName;
	private String listSeparator;
	private Long listNum;
	private String fileName;
	private String filePath;
	private String creatorId;
	private String creator;
	private String creatorAcc;
	private Timestamp createTime;
	private Long sort;

	// Constructors

	/** default constructor */
	public SqListFilterInfo() {
	}

	/** minimal constructor */
	public SqListFilterInfo(String id, String listName, String listSeparator,
			Long listNum, String fileName, String filePath, String creatorId,
			String creator, String creatorAcc, Timestamp createTime) {
		this.id = id;
		this.listName = listName;
		this.listSeparator = listSeparator;
		this.listNum = listNum;
		this.fileName = fileName;
		this.filePath = filePath;
		this.creatorId = creatorId;
		this.creator = creator;
		this.creatorAcc = creatorAcc;
		this.createTime = createTime;
	}

	/** full constructor */
	public SqListFilterInfo(String id, String listName, String listSeparator,
			Long listNum, String fileName, String filePath, String creatorId,
			String creator, String creatorAcc, Timestamp createTime, Long sort) {
		this.id = id;
		this.listName = listName;
		this.listSeparator = listSeparator;
		this.listNum = listNum;
		this.fileName = fileName;
		this.filePath = filePath;
		this.creatorId = creatorId;
		this.creator = creator;
		this.creatorAcc = creatorAcc;
		this.createTime = createTime;
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

	@Column(name = "LIST_NAME", nullable = false, length = 50)
	public String getListName() {
		return this.listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	@Column(name = "LIST_SEPARATOR", nullable = true, length = 1)
	public String getListSeparator() {
		return this.listSeparator;
	}

	public void setListSeparator(String listSeparator) {
		this.listSeparator = listSeparator;
	}

	@Column(name = "LIST_NUM", nullable = false, precision = 10, scale = 0)
	public Long getListNum() {
		return this.listNum;
	}

	public void setListNum(Long listNum) {
		this.listNum = listNum;
	}

	@Column(name = "FILE_NAME", nullable = false, length = 50)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "FILE_PATH", nullable = false, length = 50)
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "CREATOR_ID", nullable = false, length = 50)
	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Column(name = "CREATOR", nullable = false, length = 50)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CREATOR_ACC", nullable = false, length = 200)
	public String getCreatorAcc() {
		return this.creatorAcc;
	}

	public void setCreatorAcc(String creatorAcc) {
		this.creatorAcc = creatorAcc;
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