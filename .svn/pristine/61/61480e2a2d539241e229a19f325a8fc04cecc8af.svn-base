package com.aspire.birp.modules.smartQuery.market.vo;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * SqFileDataInfo entity. 
 * @author MyEclipse Persistence Tools
 */
public class SqFileDataInfoVO {
	// Fields

	private String id;
	private String fileName;
	private String createType;
	private String fileType;
	private Double fileSize;
	/*文件大小转化说明*/
	private String fileSizeCov;
	private String filePath;
	private String catalogId;
	/*存储目录全路径*/
	private String catalogPath;
	private String sqId;
	private String dataCyc;
	private String execTime;
	private String dataStoreDate;
	private String status;
	private String creatorId;
	private String creator;
	private String creatorAcc;
	private String createTime;
	private String timeTree;
	private Long sort;

	// Constructors

	/** default constructor */
	public SqFileDataInfoVO() {
	}
	
	public SqFileDataInfoVO(String id, String fileName, String createType,
			String fileType, Double fileSize, String fileSizeCov,
			String filePath, String catalogId, String catalogPath, String sqId,
			String dataCyc, String execTime, String dataStoreDate,
			String status, String creatorId, String creator,
			String creatorAcc, String createTime, Long sort) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.createType = createType;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.fileSizeCov = fileSizeCov;
		this.filePath = filePath;
		this.catalogId = catalogId;
		this.catalogPath = catalogPath;
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
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCreateType() {
		return this.createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}
	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Double getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public String getSqId() {
		return this.sqId;
	}

	public void setSqId(String sqId) {
		this.sqId = sqId;
	}
	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getFileSizeCov() {
		return fileSizeCov;
	}

	public void setFileSizeCov(Double fileSize) {
		String size = "";
		NumberFormat nf = new DecimalFormat("0.##");
		//判断文件大小来决定文件所取单位
		if(fileSize < 1024){
			size = fileSize + " B";
		}else if(1024 <= fileSize && fileSize < (1024*1024)){
			size = nf.format((fileSize/1024)) + " KB";			
		}else if((1024*1024) <= fileSize && fileSize < (1024*1024*1024)){
			size = nf.format(fileSize/(1024*1024)) + " MB";
		}else{
			size = nf.format(fileSize/(1024*1024*1024)) + " GB";
		}		
		this.fileSizeCov = size;
	}

	public String getDataCyc() {
		return dataCyc;
	}

	public void setDataCyc(String dataCyc) {
		this.dataCyc = dataCyc;
	}

	public String getExecTime() {
		return execTime;
	}

	public void setExecTime(String execTime) {
		this.execTime = execTime;
	}

	public String getDataStoreDate() {
		return dataStoreDate;
	}

	public void setDataStoreDate(String dataStoreDate) {
		this.dataStoreDate = dataStoreDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCatalogPath() {
		return catalogPath;
	}

	public void setCatalogPath(String catalogPath) {
		this.catalogPath = catalogPath;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorAcc() {
		return creatorAcc;
	}

	public void setCreatorAcc(String creatorAcc) {
		this.creatorAcc = creatorAcc;
	}

	public void setFileSizeCov(String fileSizeCov) {
		this.fileSizeCov = fileSizeCov;
	}

	public String getTimeTree() {
		return timeTree;
	}

	public void setTimeTree(String timeTree) {
		this.timeTree = timeTree;
	}
	

}