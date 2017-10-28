
package com.aspire.birp.modules.antiPoverty.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.sql.CLOB;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**  
 * @Title: OracleClobTypeHandler.java 
 * @Description: TODO(用一句话描述该文件做什么)
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2017年4月27日 下午5:21:23
 * @version: V1.0
 */
public class OracleClobTypeHandler implements TypeHandler<Object> {

	public Object valueOf(String param) {
		return null;
	}

	@Override
	public Object getResult(ResultSet arg0, String arg1) throws SQLException {
		CLOB clob = (CLOB) arg0.getClob(arg1);
		return (clob == null || clob.length() == 0) ? null : clob.getSubString((long) 1, (int) clob.length());
	}

	@Override
	public Object getResult(ResultSet arg0, int arg1) throws SQLException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.CallableStatement, int)
	 */
	@Override
	public Object getResult(java.sql.CallableStatement arg0, int arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.TypeHandler#setParameter(java.sql.PreparedStatement, int, java.lang.Object, org.apache.ibatis.type.JdbcType)
	 */
	@Override
	public void setParameter(java.sql.PreparedStatement arg0, int arg1,
			Object arg2, JdbcType arg3) throws SQLException {
		CLOB clob = CLOB.getEmptyCLOB();
		clob.setString(1, (String) arg2);
		arg0.setClob(arg1, clob);
		
	}

}


