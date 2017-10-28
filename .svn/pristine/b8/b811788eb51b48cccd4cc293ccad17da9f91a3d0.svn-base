package com.aspire.birp.modules.smartQuery.exception.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SqExceptionInfo entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SQ_EXCEPTION_INFO")
public class SqExceptionInfo implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -7647025591259565978L;
	private String id;
	private Timestamp createTime;
	private String log;
	private Integer type;
	private String description;
	private Integer moduleId;
	/**
	 * 当moduId为自动任务时，extraPropOne表示自动任务的taskId
	 */
	private String extraPropOne;

	/**
	 * 当moduId为自动任务时，extraPropTwo表示自动任务的当期周期
	 */
	private String extraPropTwo;

	// Constructors

	/** default constructor */
	public SqExceptionInfo() {
	}

	/** minimal constructor */
	public SqExceptionInfo(String id, Timestamp createTime, String log,
						   Integer type, Integer moduleId) {
		this.id = id;
		this.createTime = createTime;
		this.log = log;
		this.type = type;
		this.moduleId = moduleId;
	}

	/** full constructor */
	public SqExceptionInfo(String id, Timestamp createTime, String log,
						   Integer type, String description, Integer moduleId,
			String extraPropOne, String extraPropTwo) {
		this.id = id;
		this.createTime = createTime;
		this.log = log;
		this.type = type;
		this.description = description;
		this.moduleId = moduleId;
		this.extraPropOne = extraPropOne;
		this.extraPropTwo = extraPropTwo;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "CREATE_TIME", nullable = false, length = 7)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "LOG", nullable = false, length = 0)
	public String getLog() {
		return this.log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	@Column(name = "TYPE", nullable = false, precision = 0)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "DESCRIPTION", length = 1024)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "MODULE_ID", nullable = false, precision = 0)
	public Integer getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name = "EXTRA_PROP_ONE", length = 512)
	public String getExtraPropOne() {
		return this.extraPropOne;
	}

	public void setExtraPropOne(String extraPropOne) {
		this.extraPropOne = extraPropOne;
	}

	@Column(name = "EXTRA_PROP_TWO", length = 512)
	public String getExtraPropTwo() {
		return this.extraPropTwo;
	}

	public void setExtraPropTwo(String extraPropTwo) {
		this.extraPropTwo = extraPropTwo;
	}

}