package com.aspire.birp.modules.smartQuery.task.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SqTaskStatusInfo entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SQ_TASK_STATUS_INFO")
public class SqTaskStatusInfo implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 3452203889392011239L;
	private String id;
	private String taskId;
	private Timestamp dataCyc;
	private String taskStatus;
	private Timestamp taskBeginTime;
	private Timestamp taskEndTime;
	private BigDecimal taskDur;
	private Timestamp dataCycNext;

	// Constructors

	/** default constructor */
	public SqTaskStatusInfo() {
	}

	/** minimal constructor */
	public SqTaskStatusInfo(String id, String taskId, Timestamp dataCyc,
			String taskStatus) {
		this.id = id;
		this.taskId = taskId;
		this.dataCyc = dataCyc;
		this.taskStatus = taskStatus;
	}

	/** full constructor */
	public SqTaskStatusInfo(String id, String taskId, Timestamp dataCyc,
			String taskStatus, Timestamp taskBeginTime, Timestamp taskEndTime,
			BigDecimal taskDur, Timestamp dataCycNext) {
		this.id = id;
		this.taskId = taskId;
		this.dataCyc = dataCyc;
		this.taskStatus = taskStatus;
		this.taskBeginTime = taskBeginTime;
		this.taskEndTime = taskEndTime;
		this.taskDur = taskDur;
		this.dataCycNext = dataCycNext;
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

	@Column(name = "TASK_ID", nullable = false, length = 50)
	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Column(name = "DATA_CYC", nullable = false, length = 7)
	public Timestamp getDataCyc() {
		return this.dataCyc;
	}

	public void setDataCyc(Timestamp dataCyc) {
		this.dataCyc = dataCyc;
	}

	@Column(name = "TASK_STATUS", nullable = false, length = 1)
	public String getTaskStatus() {
		return this.taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	@Column(name = "TASK_BEGIN_TIME", length = 7)
	public Timestamp getTaskBeginTime() {
		return this.taskBeginTime;
	}

	public void setTaskBeginTime(Timestamp taskBeginTime) {
		this.taskBeginTime = taskBeginTime;
	}

	@Column(name = "TASK_END_TIME", length = 7)
	public Timestamp getTaskEndTime() {
		return this.taskEndTime;
	}

	public void setTaskEndTime(Timestamp taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	@Column(name = "TASK_DUR", precision = 20, scale = 0)
	public BigDecimal getTaskDur() {
		return this.taskDur;
	}

	public void setTaskDur(BigDecimal taskDur) {
		this.taskDur = taskDur;
	}

	@Column(name = "DATA_CYC_NEXT", length = 7)
	public Timestamp getDataCycNext() {
		return this.dataCycNext;
	}

	public void setDataCycNext(Timestamp dataCycNext) {
		this.dataCycNext = dataCycNext;
	}

}