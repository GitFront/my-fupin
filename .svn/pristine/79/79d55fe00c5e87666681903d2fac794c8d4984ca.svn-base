package com.aspire.birp.modules.smartQuery.market.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SqFileDataInfo entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SQ_FILE_DATA_INFO")
public class SqFileDataInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1154651940466268304L;
	// Fields

	private String id;
	private String fileName;
	private String createType;
	private String fileType;
	private Double fileSize;
	private String filePath;
	private String catalogId;
	private String sqId;
	private Timestamp dataCyc;
	private Timestamp execTime;
	private Timestamp dataStoreDate;
	private String status;
	private String creatorId;
	private String creator;
	private String creatorAcc;
	private Timestamp createTime;
	private Long sort;

	// Constructors

	/** default constructor */
	public SqFileDataInfo() {
	}

	/** minimal constructor */
	public SqFileDataInfo(String id, String fileName, String catalogId,
			String sqId, Timestamp dataCyc, Timestamp execTime,
			Timestamp dataStoreDate, String status, String creatorId,
			String creator,String creatorAcc,Timestamp createTime) {
		this.id = id;
		this.fileName = fileName;
		this.catalogId = catalogId;
		this.sqId = sqId;
		this.dataCyc = dataCyc;
		this.execTime = execTime;
		this.dataStoreDate = dataStoreDate;
		this.status = status;
		this.creatorId = creatorId;
		this.creator = creator;
		this.creatorAcc = creatorAcc;
		this.createTime = createTime;
	}

	/** full constructor */
	public SqFileDataInfo(String id, String fileName, String createType,
			String fileType, Double fileSize, String filePath,
			String catalogId, String sqId, Timestamp dataCyc,
			Timestamp execTime, Timestamp dataStoreDate, String status,
			String creatorId, String creator, String creatorAcc,
			Timestamp createTime, Long sort) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.createType = createType;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.filePath = filePath;
		this.catalogId = catalogId;
		this.sqId = sqId;
		this.dataCyc = dataCyc;
		this.execTime = execTime;
		this.dataStoreDate = dataStoreDate;
		this.status = status;
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

	@Column(name = "FILE_NAME", nullable = false, length = 200)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "CREATE_TYPE", nullable = false, length = 1)
	public String getCreateType() {
		return this.createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}

	@Column(name = "FILE_TYPE", nullable = false, length = 10)
	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "FILE_SIZE", precision = 0)
	public Double getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name = "FILE_PATH", nullable = false, length = 2000)
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "CATALOG_ID", nullable = false, length = 50)
	public String getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	@Column(name = "SQ_ID", nullable = true, length = 50)
	public String getSqId() {
		return this.sqId;
	}

	public void setSqId(String sqId) {
		this.sqId = sqId;
	}

	@Column(name = "DATA_CYC", nullable = true, length = 7)
	public Timestamp getDataCyc() {
		return this.dataCyc;
	}

	public void setDataCyc(Timestamp dataCyc) {
		this.dataCyc = dataCyc;
	}

	@Column(name = "EXEC_TIME", nullable = true, length = 7)
	public Timestamp getExecTime() {
		return this.execTime;
	}

	public void setExecTime(Timestamp execTime) {
		this.execTime = execTime;
	}

	@Column(name = "DATA_STORE_DATE", nullable = true, length = 7)
	public Timestamp getDataStoreDate() {
		return this.dataStoreDate;
	}

	public void setDataStoreDate(Timestamp dataStoreDate) {
		this.dataStoreDate = dataStoreDate;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CREATOR", nullable = false, length = 200)
	public String getCreator() {
		return this.creator;
	}

	

	public void setCreator(String creator) {
		this.creator = creator;
	}	
	
	@Column(name = "CREATOR_ID", nullable = false, length = 50)
	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Column(name = "CREATOR_ACC", nullable = false, length = 200)
	public String getCreatorAcc() {
		return creatorAcc;
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