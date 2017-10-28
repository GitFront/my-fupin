package com.aspire.birp.modules.smartQuery.query.vo;



/**
 * SqListFilterInfo entity. 
 * @author MyEclipse Persistence Tools
 */
public class SqListFilterInfoVO {

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
	private String createTime;
	private Long sort;

	// Constructors

	/** default constructor */
	public SqListFilterInfoVO() {
	}

	/** full constructor */
	public SqListFilterInfoVO(String id, String listName, String listSeparator,
			Long listNum, String fileName, String filePath, String creatorId,
			String creator, String creatorAcc, String createTime, Long sort) {
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
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getListName() {
		return this.listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getListSeparator() {
		return this.listSeparator;
	}

	public void setListSeparator(String listSeparator) {
		this.listSeparator = listSeparator;
	}

	public Long getListNum() {
		return this.listNum;
	}

	public void setListNum(Long listNum) {
		this.listNum = listNum;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorAcc() {
		return this.creatorAcc;
	}

	public void setCreatorAcc(String creatorAcc) {
		this.creatorAcc = creatorAcc;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

}