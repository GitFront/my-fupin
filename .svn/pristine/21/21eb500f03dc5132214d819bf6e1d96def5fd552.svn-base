package com.aspire.birp.modules.smartQuery.task.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SqTaskInfo entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SQ_TASK_INFO")
public class SqTaskInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1323303903007097617L;
	private String taskId;
	private String rqSourceTb;
	private String cycTyp;
	private Integer cycLen;
	private Timestamp validTime;
	private Timestamp invalidTime;
	private String taskAutherId;
	private String taskAutherAcc;
	private String taskAuther;
	private Timestamp taskCommitTime;
	private String taskStatus;
	private Integer dataStoreDt;
	private String fileName;
	private String sqColumns;

	private String sqSelectSql;
	private String sqFromSql;
	private String sqFilterSql;
	private String sqSortSql;

	private Timestamp dataCyc;

	private String catalogId;

	// Constructors

	/** default constructor */
	public SqTaskInfo() {
	}

	/** minimal constructor */
	public SqTaskInfo(String taskId,  String rqSourceTb,
			String cycTyp, Timestamp validTime, Timestamp invalidTime) {
		this.taskId = taskId;
		this.rqSourceTb = rqSourceTb;
		this.cycTyp = cycTyp;
		this.validTime = validTime;
		this.invalidTime = invalidTime;
	}

	public SqTaskInfo(String taskId, String rqSourceTb, String cycTyp, Integer cycLen, Timestamp validTime, Timestamp invalidTime, String taskAutherId, String taskAutherAcc, String taskAuther, Timestamp taskCommitTime, String taskStatus, Integer dataStoreDt, String fileName, String sqColumns, String sqSelectSql, String sqFromSql, String sqFilterSql, String sqSortSql, Timestamp dateCyc, String catalogId) {
		this.taskId = taskId;
		this.rqSourceTb = rqSourceTb;
		this.cycTyp = cycTyp;
		this.cycLen = cycLen;
		this.validTime = validTime;
		this.invalidTime = invalidTime;
		this.taskAutherId = taskAutherId;
		this.taskAutherAcc = taskAutherAcc;
		this.taskAuther = taskAuther;
		this.taskCommitTime = taskCommitTime;
		this.taskStatus = taskStatus;
		this.dataStoreDt = dataStoreDt;
		this.fileName = fileName;
		this.sqColumns = sqColumns;
		this.sqSelectSql = sqSelectSql;
		this.sqFromSql = sqFromSql;
		this.sqFilterSql = sqFilterSql;
		this.sqSortSql = sqSortSql;
		this.dataCyc = dateCyc;
		this.catalogId = catalogId;
	}

	// Property accessors
	@Id
	@Column(name = "TASK_ID", unique = true, nullable = false, length = 50)
	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Column(name = "RQ_SOURCE_TB", nullable = false, length = 4000)
	public String getRqSourceTb() {
		return this.rqSourceTb;
	}

	public void setRqSourceTb(String rqSourceTb) {
		this.rqSourceTb = rqSourceTb;
	}

	@Column(name = "CYC_TYP", nullable = false, length = 1)
	public String getCycTyp() {
		return this.cycTyp;
	}

	public void setCycTyp(String cycTyp) {
		this.cycTyp = cycTyp;
	}

	@Column(name = "CYC_LEN", precision = 0)
	public Integer getCycLen() {
		return this.cycLen;
	}

	public void setCycLen(Integer cycLen) {
		this.cycLen = cycLen;
	}

	@Column(name = "VALID_TIME", nullable = false, length = 7)
	public Timestamp getValidTime() {
		return this.validTime;
	}

	public void setValidTime(Timestamp validTime) {
		this.validTime = validTime;
	}

	@Column(name = "INVALID_TIME", nullable = false, length = 7)
	public Timestamp getInvalidTime() {
		return this.invalidTime;
	}

	public void setInvalidTime(Timestamp invalidTime) {
		this.invalidTime = invalidTime;
	}

	@Column(name = "TASK_AUTHER", length = 32)
	public String getTaskAuther() {
		return this.taskAuther;
	}

	public void setTaskAuther(String taskAuther) {
		this.taskAuther = taskAuther;
	}

	@Column(name = "TASK_COMMIT_TIME", length = 7)
	public Timestamp getTaskCommitTime() {
		return this.taskCommitTime;
	}

	public void setTaskCommitTime(Timestamp taskCommitTime) {
		this.taskCommitTime = taskCommitTime;
	}

	@Column(name = "TASK_STATUS", length = 1)
	public String getTaskStatus() {
		return this.taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	@Column(name = "DATA_STORE_DT", precision = 0)
	public Integer getDataStoreDt() {
		return this.dataStoreDt;
	}

	public void setDataStoreDt(Integer dataStoreDt) {
		this.dataStoreDt = dataStoreDt;
	}

	@Column(name = "FILE_NAME", length = 200)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "SQ_COLUMNS", length = 4000)
	public String getSqColumns() {
		return this.sqColumns;
	}

	public void setSqColumns(String sqColumns) {
		this.sqColumns = sqColumns;
	}

	@Column(name = "DATA_CYC", nullable = false, length = 7)
	public Timestamp getDataCyc() {
		return dataCyc;
	}

	public void setDataCyc(Timestamp dataCyc) {
		this.dataCyc = dataCyc;
	}

	@Column(name = "SQ_SELECT_SQL", nullable = false, length = 4000)
	public String getSqSelectSql() {
		return sqSelectSql;
	}

	public void setSqSelectSql(String sqSelectSql) {
		this.sqSelectSql = sqSelectSql;
	}

	@Column(name = "SQ_FROM_SQL", nullable = false, length = 4000)
	public String getSqFromSql() {
		return sqFromSql;
	}

	public void setSqFromSql(String sqFromSql) {
		this.sqFromSql = sqFromSql;
	}

	@Column(name = "SQ_FILTER_SQL", nullable = false, length = 4000)
	public String getSqFilterSql() {
		return sqFilterSql;
	}

	public void setSqFilterSql(String sqFilterSql) {
		this.sqFilterSql = sqFilterSql;
	}

	@Column(name = "SQ_SORT_SQL", nullable = false, length = 4000)
	public String getSqSortSql() {
		return sqSortSql;
	}

	public void setSqSortSql(String sqSortSql) {
		this.sqSortSql = sqSortSql;
	}

	@Column(name = "CATALOG_ID", nullable = false, length = 50)
	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	@Column(name = "TASK_AUTHER_ID",nullable = false,  length = 50)
	public String getTaskAutherId() {
		return taskAutherId;
	}

	public void setTaskAutherId(String taskAutherId) {
		this.taskAutherId = taskAutherId;
	}

	@Column(name = "TASK_AUTHER_ACC",nullable = false,  length = 200)
	public String getTaskAutherAcc() {
		return taskAutherAcc;
	}

	public void setTaskAutherAcc(String taskAutherAcc) {
		this.taskAutherAcc = taskAutherAcc;
	}
}