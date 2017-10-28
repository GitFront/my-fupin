package com.aspire.birp.modules.dataLabel.config.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DmLabelTreeDef entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DM_LABEL_TREE_DEF")
public class DmLabelTreeDef implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1272391577621635069L;
	// Fields

		private String id;
		private String name;
		private String pid;
		private String fullPath;
		private String type;
		private String assoTableId;
		private String assoTable;
		private String assoFieldId;
		private String assoField;
		private String assoDimId;
		private String assoDim;
		private String assoRule;
		private Integer topNum;
		private String isTemp;
		private String active;
		private String creator;
		private Timestamp createTime;
		private Long sort;

		// Constructors

		/** default constructor */
		public DmLabelTreeDef() {
		}

		/** minimal constructor */
		public DmLabelTreeDef(String id, String name, String pid, String fullPath,
				String type, String isTemp,String active,Integer topNum, String creator, Timestamp createTime,
				Long sort) {
			this.id = id;
			this.name = name;
			this.pid = pid;
			this.fullPath = fullPath;
			this.type = type;
			this.isTemp = isTemp;
			this.active = active;
			this.topNum = topNum;
			this.creator = creator;
			this.createTime = createTime;
			this.sort = sort;
		}

		/** full constructor */
		public DmLabelTreeDef(String id, String name, String pid, String fullPath,
				String type, String assoTableId, String assoTable,
				String assoFieldId, String assoField, String assoDimId,
				String assoDim, String assoRule, Integer topNum, String isTemp,String active,
				String creator, Timestamp createTime, Long sort) {
			this.id = id;
			this.name = name;
			this.pid = pid;
			this.fullPath = fullPath;
			this.type = type;
			this.assoTableId = assoTableId;
			this.assoTable = assoTable;
			this.assoFieldId = assoFieldId;
			this.assoField = assoField;
			this.assoDimId = assoDimId;
			this.assoDim = assoDim;
			this.assoRule = assoRule;
			this.topNum = topNum;
			this.active = active;
			this.isTemp = isTemp;
			this.creator = creator;
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

		@Column(name = "NAME", nullable = false, length = 200)
		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Column(name = "PID", nullable = false, length = 50,updatable = false)
		public String getPid() {
			return this.pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		@Column(name = "FULL_PATH", nullable = false, length = 2000)
		public String getFullPath() {
			return this.fullPath;
		}

		public void setFullPath(String fullPath) {
			this.fullPath = fullPath;
		}

		@Column(name = "TYPE", nullable = false, length = 10)
		public String getType() {
			return this.type;
		}

		public void setType(String type) {
			this.type = type;
		}

		@Column(name = "ASSO_TABLE_ID", length = 50)
		public String getAssoTableId() {
			return this.assoTableId;
		}

		public void setAssoTableId(String assoTableId) {
			this.assoTableId = assoTableId;
		}

		@Column(name = "ASSO_TABLE", length = 50)
		public String getAssoTable() {
			return this.assoTable;
		}

		public void setAssoTable(String assoTable) {
			this.assoTable = assoTable;
		}

		@Column(name = "ASSO_FIELD_ID", length = 50)
		public String getAssoFieldId() {
			return this.assoFieldId;
		}

		public void setAssoFieldId(String assoFieldId) {
			this.assoFieldId = assoFieldId;
		}

		@Column(name = "ASSO_FIELD", length = 50)
		public String getAssoField() {
			return this.assoField;
		}

		public void setAssoField(String assoField) {
			this.assoField = assoField;
		}

		@Column(name = "ASSO_DIM_ID", length = 50)
		public String getAssoDimId() {
			return this.assoDimId;
		}

		public void setAssoDimId(String assoDimId) {
			this.assoDimId = assoDimId;
		}

		@Column(name = "ASSO_DIM", length = 50)
		public String getAssoDim() {
			return this.assoDim;
		}

		public void setAssoDim(String assoDim) {
			this.assoDim = assoDim;
		}

		@Column(name = "ASSO_RULE", length = 200)
		public String getAssoRule() {
			return this.assoRule;
		}

		public void setAssoRule(String assoRule) {
			this.assoRule = assoRule;
		}

		@Column(name = "TOP_NUM", precision = 0)
		public Integer getTopNum() {
			return this.topNum;
		}

		public void setTopNum(Integer topNum) {
			this.topNum = topNum;
		}

		@Column(name = "IS_TEMP", nullable = false, length = 1)
		public String getIsTemp() {
			return this.isTemp;
		}

		public void setIsTemp(String isTemp) {
			this.isTemp = isTemp;
		}

		@Column(name = "ACTIVE", nullable = false, length = 1)
		public String getActive() {
			return active;
		}

		public void setActive(String active) {
			this.active = active;
		}

		@Column(name = "CREATOR", nullable = false, length = 50,updatable = false)
		public String getCreator() {
			return this.creator;
		}

		public void setCreator(String creator) {
			this.creator = creator;
		}

		@Column(name = "CREATE_TIME", nullable = false, length = 7,updatable = false)
		public Timestamp getCreateTime() {
			return this.createTime;
		}

		public void setCreateTime(Timestamp createTime) {
			this.createTime = createTime;
		}

		@Column(name = "SORT", nullable = false, precision = 0)
		public Long getSort() {
			return this.sort;
		}

		public void setSort(Long sort) {
			this.sort = sort;
		}

}