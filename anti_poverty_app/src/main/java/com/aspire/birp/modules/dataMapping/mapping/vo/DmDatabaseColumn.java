package com.aspire.birp.modules.dataMapping.mapping.vo;


/**  
 * @Title: 数据库表的列信息VO对象 
 * @Description: 用于传递数据库表的列数据信息
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年9月16日 下午2:48:19
 * @version: V1.0
 */
public class DmDatabaseColumn {
	// Fields
	/*数据表列名*/
	private String column;
	/*数据表列注释*/
	private String comment;
	/*列类型*/
	private String dataType;
	/*列数据长度*/
	private Long dataLength;
	/*列数据精度*/
	private Long dataPrecision;
	/*列是否可为空*/
	private String nullable;
	/*列ID*/
	private String columnId;
	private String isKeys;

	// Constructors

	/** default constructor */
	public DmDatabaseColumn() {
	}

	/** full constructor */
	public DmDatabaseColumn(String column, String comment, String dataType,
			Long dataLength, Long dataPrecision, String nullable,
			String columnId) {
		super();
		this.column = column;
		this.comment = comment;
		this.dataType = dataType;
		this.dataLength = dataLength;
		this.dataPrecision = dataPrecision;
		this.nullable = nullable;
		this.columnId = columnId;
		
	}
	
	public DmDatabaseColumn(Object column, Object comment, Object dataType,
			Object dataLength, Object dataPrecision, Object nullable,
			Object columnId,String isKeys) {
		super();
		this.column = String.valueOf(column);
		this.comment = comment==null?"":String.valueOf(comment);
		this.dataType = String.valueOf(dataType);
		this.dataLength = dataLength==null?0L:Long.valueOf(String.valueOf(dataLength));
		this.dataPrecision = dataPrecision == null?0L:Long.valueOf(String.valueOf(dataPrecision));
		this.nullable = String.valueOf(nullable);
		this.columnId = String.valueOf(columnId);
		this.isKeys=isKeys;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Long getDataLength() {
		return dataLength;
	}

	public void setDataLength(Long dataLength) {
		this.dataLength = dataLength;
	}

	public Long getDataPrecision() {
		return dataPrecision;
	}

	public void setDataPrecision(Long dataPrecision) {
		this.dataPrecision = dataPrecision;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getIsKeys() {
		return isKeys;
	}

	public void setIsKeys(String isKeys) {
		this.isKeys = isKeys;
	}
	

}