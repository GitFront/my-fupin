
package com.aspire.birp.modules.base.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.aspire.birp.common.excel.CSVUtils;
import com.aspire.birp.common.excel.FileProperties;
import com.aspire.birp.modules.sys.service.LogService;


/**  
 * @Title: 功能模块的通用功能服务实现类
 * @Description: 用于实现功能模块的通用功能服务方法
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年4月26日 下午2:30:56
 * @version: V1.0
 */
@Service
public class BaseService {

	/**
	 * 统计SQL语句
	 */
	protected final static String countSQL = "SELECT COUNT(1) ";	

	/**
	 * 分页SQL处理
	 */
	protected final static String paginationDual = "/*+ first_rows */";
	
	
	@Autowired
    private LogService logService;
	
	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	/*Mybatis操作接口*/
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	/*Hibernate操作接口*/
	@Autowired
	private HibernateTemplate hibernateTemplate;
	 
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {  
         this.hibernateTemplate = hibernateTemplate; 
    }
    
    /**
     * 获取Spring的Hibernate操作对象
     * @return
     * @author 张健雄
     */
    public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
    
    /**
	 * 通用数据列表的查询
	 * @param queryName mybatis的通用查询
	 * @param params 查询参数信息
	 * @return 返回数据列表
	 */
    protected List<Map<String, Object>> queryList(String queryName,
			Map<String, Object> params) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		/*参数格式化*/
		if(StringUtils.isBlank(queryName)) return list;
		
		if(params == null) params = new HashMap<String, Object>();
		/*数据查询*/
		list = sqlSessionTemplate.selectList(queryName, params);
		
		return list;
	}
	
	/**
	 * 通用数据列表的查询
	 * @param queryName mybatis的通用查询
	 * @param params 查询参数信息
	 * @param page 页数
	 * @param pageSize 每页条数
	 * @return 返回数据列表
	 */
    protected List<Map<String, Object>> queryList(String queryName,
				Map<String, Object> params,Integer page,Integer pageSize) {
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			/*参数格式化*/
			if(StringUtils.isBlank(queryName)) return list;
			
			if(params == null) params = new HashMap<String, Object>();
			/*数据查询*/
			list = sqlSessionTemplate.selectList(queryName, params,new RowBounds((page-1)*pageSize, pageSize));
			
			return list;
		}
    
    /**
     * 通过HibernateTemplate来执行DQL查询sql语句
     * @param sql 需要查询的sql语句
     * @param obj 查询参数
     * @param entityType 返回对象类型
     * @return 返回对象列表信息
     * @author 张健雄
     */
    @SuppressWarnings({ "unchecked" })
    protected<T> List<T> excuteSQLQuery(final String sql, final Object[] obj, final Class<T> entityType){
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            public List<T> doInHibernate(Session session) throws HibernateException {
                SQLQuery q = session.createSQLQuery(sql);
                if(obj != null && obj.length > 0){
                    for(int i = 0;i < obj.length; i++){
                        q.setParameter(i, obj[i]);
                    }
                }
                return q.addEntity(entityType).list();
            }
        });
    }
    
    /**
     * 通过HibernateTemplate来执行DQL查询sql语句
     * @param sql 需要查询的sql语句
     * @param obj 查询参数
     * @return 返回对象列表信息
     * @author 张健雄
     */
    protected<T> List<T> excuteSQLQuery(final String sql,final Map<String,Object> parameters){
        return this.excuteSQLQuery(sql, parameters, 0, 0);
    }
    
    /**
     * 通过HibernateTemplate来执行DQL查询sql语句
     * @param sql 需要查询的sql语句
     * @param obj 查询参数
     * @return 返回对象列表信息
     * @author 张健雄
     */
    @SuppressWarnings({ "unchecked"})
    protected<T> List<T> excuteSQLQuery(final String sql, final Object[] obj){
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            public List<T> doInHibernate(Session session) throws HibernateException {
                SQLQuery q = session.createSQLQuery(sql);
                if (obj != null && obj.length > 0) {
                    for (int i = 0; i < obj.length; i++) {
                        q.setParameter(i, obj[i]);
                    }
                }
                return q.list();
            }
        });
    }
    
    /**
     * 通过HibernateTemplate来执行DQL分页查询sql语句
     * @param sql 需要查询的sql语句
     * @param page 查询页数
     * @param pageSize 查询每页条数
     * @return 返回对象列表信息
     * @author 张健雄
     */
    protected<T> List<T> excuteSQLQuery(final String sql,final int page,final int pageSize){
        return this.excuteSQLQuery(sql, new HashMap<String, Object>(), page, pageSize);
    }
    
    /**
     * 通过HibernateTemplate来执行DQL分页查询sql语句
     * @param sql 需要查询的sql语句
     * @param parameters 参数列表
     * @param page 查询页数
     * @param pageSize 查询每页条数
     * @return 返回对象列表信息
     * @author 张健雄
     */
    @SuppressWarnings({ "unchecked"})
    protected<T> List<T> excuteSQLQuery(final String sql,final Map<String,Object> parameters,final int page,final int pageSize){
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            public List<T> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                if (parameters != null && !parameters.isEmpty()) {
                    for (String key : parameters.keySet()) {
                        query.setParameter(key, parameters.get(key));
                    }
                }
                if (!(page == 0 && pageSize == 0)) {
                    query.setFirstResult((page - 1) * pageSize);
                    query.setMaxResults(pageSize);
                }
                return query.list();
            }
        });
    }
    
    
    /**
     * 通过HibernateTemplate来执行DQL分页查询sql语句
     * @param sql 需要查询的sql语句
     * @param page 查询页数
     * @param pageSize 查询每页条数
     * @return 返回对象列表信息
     * @author 苏伟城
     */
    @SuppressWarnings({ "unchecked"})
    protected<T> List<T> excuteSQLQuery(final String sql,final Object[] obj,final int page,final int pageSize){
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            public List<T> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                if(obj != null && obj.length > 0){  
                    for(int i = 0;i < obj.length; i++){
                    	query.setParameter(i, obj[i]);  
                    }  
                }  
                if(!(page == 0 && pageSize == 0)){
                	query.setFirstResult((page - 1) * pageSize);
                    query.setMaxResults(pageSize);
                }    
                return query.list();
            }
        });
    }
    
    /**
     * 通过HibernateTemplate来执行DQL查询sql语句
     * @param sql 需要查询的sql语句
     * @param obj 查询参数
     * @return 返回对象列表信息
     * @author 张健雄
     */
    protected<T> List<T> excuteSQLQuery(final String sql){
        return this.excuteSQLQuery(sql, new Object[]{});
    }
    
    /**
     * 通过HibernateTemplate来执行DML操作的sql语句
     * @param sql
     * @param obj
     * @return
     * @author 张健雄
     */
    protected<T> int excuteSQLUpdate(final String sql,final Map<String,Object> parameters){  
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                SQLQuery q = session.createSQLQuery(sql);
                if (parameters != null && !parameters.isEmpty()) {
                    for (String key : parameters.keySet()) {
                        q.setParameter(key, parameters.get(key));
                    }
                }
                return q.executeUpdate();
            }
        });  
    } 
    
    /**
     * 通过HibernateTemplate来执行DML操作的sql语句
     * @param sql
     * @param obj
     * @return
     * @author 张健雄
     */
    protected<T> int excuteSQLUpdate(final String sql, final Object[] obj){  
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                SQLQuery q = session.createSQLQuery(sql);
                if (obj != null && obj.length > 0) {
                    for (int i = 0; i < obj.length; i++) {
                        q.setParameter(i, obj[i]);
                    }
                }
                return q.executeUpdate();
            }
        });  
    } 
	
	/**
     * 通过HibernateTemplate来执行DQL分页查询hql语句
     * @param hql 需要查询的hql语句
     * @param page 查询页数
     * @param pageSize 查询每页条数
     * @return 返回对象列表信息
     * @author 张健雄
     */
    protected<T> List<T> excuteQuery(final String hql,final int page,final int pageSize){
        return this.excuteQuery(hql, null, page, pageSize);
    }
    
    /**
     * 通过HibernateTemplate来执行DQL分页查询hql语句
     * @param hql 需要查询的hql语句
     * @param page 查询页数
     * @param pageSize 查询每页条数
     * @return 返回对象列表信息
     * @author 张健雄
     */
    @SuppressWarnings({ "unchecked"})
    protected<T> List<T> excuteQuery(final String hql, final Object[] obj,final int page,final int pageSize){
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            public List<T> doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                if (obj != null && obj.length > 0) {
                    for (int i = 0; i < obj.length; i++) {
                        query.setParameter(i, obj[i]);
                    }
                }
                if (!(page == 0 && pageSize == 0)) {
                    query.setFirstResult((page - 1) * pageSize);
                    query.setMaxResults(pageSize);
                }
                return query.list();
            }
        });
    }
    
   
    
    /**
	 * 执行动态生成的SQL语句
	 * @param sql 动态SQL语句
	 * @param parameters sql的匹配参数
	 * @param columns 列映射名列表
	 * @return
	 * @author 张健雄
	 */
    protected List<Map<String,Object>> runSQL(String sql,Map<String,Object> parameters,List<String> columns){
		List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();
		if(StringUtils.isBlank(sql)) return data;
		/*数据列表*/
		if(columns.size() == 1) {
			List<Object> rowsTemp = this.excuteSQLQuery(sql,parameters);
			for (int i = 0; i < rowsTemp.size(); i++) {
				Map<String, Object> row = new HashMap<String, Object>();
				row.put(columns.get(0), rowsTemp.get(i));
				data.add(row);
			}
		}else {
			List<Object[]> rowsTemp = this.excuteSQLQuery(sql,parameters);
			for (int i = 0; i < rowsTemp.size(); i++) {
				Map<String, Object> row = new HashMap<String, Object>();
				for (int j = 0; j < columns.size(); j++) {
					Object value = rowsTemp.get(i)[j];
					row.put(columns.get(j), value);
				}
				data.add(row);
			}
		}
		return data;
	}
    
    /**
	 * 执行动态生成的SQL语句（分页查询）
	 * 
	 * @param sql 执行SQL语句
	 * @param parameters 对应的参数信息
	 * @param columns 显示列信息
	 * @param page 总页数
	 * @param pageSize 每页行数
	 * @return 返回分页数据列表
	 * @author 张健雄
	 */
    public List<Map<String, Object>> runSQL(String sql,Map<String, Object> parameters,
			List<String> columns, int page, int pageSize) {
		/* 数据列表 */
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<Object[]> rowsTemp = this.excuteSQLQuery(sql, parameters, page,
				pageSize);
		if (columns != null && columns.size() == 1) {
			for (int i = 0; i < rowsTemp.size(); i++) {
				Map<String, Object> row = new HashMap<String, Object>();
				Object value = rowsTemp.get(i);
				row.put(columns.get(0), value);
				rows.add(row);
			}
		} else {
			for (int i = 0; i < rowsTemp.size(); i++) {
				Map<String, Object> row = new HashMap<String, Object>();
				for (int j = 0; j < columns.size(); j++) {
					Object value = rowsTemp.get(i)[j];
					row.put(columns.get(j), value);
				}
				rows.add(row);
			}
		}
		return rows;
	}   
	
	/**
	 * 执行动态生成的SQL语句（分页查询）
	 * 
	 * @param sql 执行SQL语句
	 * @param parameters 对应的参数信息
	 * @param columns 显示列信息
	 * @param page 总页数
	 * @param pageSize 每页行数
	 * @return 返回行总数及分页数据列表
	 * @author 张健雄
	 */
	protected Map<String, Object> runSQLForGrid(String sql,Map<String, Object> parameters,
			List<String> columns, int page, int pageSize) {
		Map<String, Object> data = new HashMap<String, Object>();
		/* 数据列表 */
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<Object[]> rowsTemp = this.excuteSQLQuery(sql, parameters, page,
				pageSize);
		if (columns != null && columns.size() == 1) {
			for (int i = 0; i < rowsTemp.size(); i++) {
				Map<String, Object> row = new HashMap<String, Object>();
				Object value = rowsTemp.get(i);
				row.put(columns.get(0), value);
				rows.add(row);
			}
		} else {
			for (int i = 0; i < rowsTemp.size(); i++) {
				Map<String, Object> row = new HashMap<String, Object>();
				for (int j = 0; j < columns.size(); j++) {
					Object value = rowsTemp.get(i)[j];
					row.put(columns.get(j), value);
				}
				rows.add(row);
			}
		}
		String countSql = countSQL + sql.substring(sql.indexOf("FROM"));
		int size = 0;
		List<Object> rowCount = this.excuteSQLQuery(countSql, parameters);
		if (rowCount != null && rowCount.size() > 0 && rowCount.get(0) != null) {
			size = Integer.parseInt(String.valueOf(rowCount.get(0)));
		}
		data.put("rows", rows);
		data.put("total", size);
		return data;
	}
	
	/**
	 * 通过SQL语句生成CSV文件，并放置在系统个人文件夹或临时个人文件中
	 * 
	 * @param sql sql语句
	 * @param parameters sql的匹配参数
	 * @param mapper 字段与名称的对应关系
	 * @param fileName 文件名
	 * @param creator 归属人账号
	 * @param isTemp 是否为临时数据
	 * @return 返回excel文件信息（name/path/size/type）
	 * @throws Exception
	 * @author 张健雄
	 */
	@SuppressWarnings("rawtypes")
	protected Map<String, String> createFileBySql(String sql,Map<String, Object> parameters,
			LinkedHashMap<String, String> mapper, String fileName,String creator, boolean isTemp) {
		Map<String, String> fileinfo = new HashMap<String, String>();

		/* 用于保存SQL列读取信息 */
		List<String> columnReader = new ArrayList<String>();
		for (Iterator propertyIterator = mapper.entrySet().iterator(); propertyIterator.hasNext();) {
			java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
			columnReader.add(propertyEntry.getKey().toString().trim());
		}

		/* 生成导出数据信息 */
		/*List<Map<String, Object>> rows = this.runSQL(sql, parameters,
				columnReader);*/

		/* 获取文件在服务器上的存储路径 */
		/*
		 * WebApplicationContext webApplicationContext =
		 * ContextLoader.getCurrentWebApplicationContext(); ServletContext
		 * servletContext = webApplicationContext.getServletContext(); String
		 * storePath = servletContext.getRealPath("/USER_FILES");
		 */
		String filePath = "";
		if (!isTemp) {
			/* 创建用户个人文件夹 */
			creator = StringUtils.isBlank(creator) ? "others" : creator;
			String storePath = FileProperties.getFilePath();
			filePath = storePath + System.getProperty("file.separator")
					+ creator + System.getProperty("file.separator");
		} else {
			/* 创建临时文件夹 */
			filePath = FileProperties.getTempFilePath()
					+ System.getProperty("file.separator");
		}

		File path = new File(filePath);

		if (!path.isDirectory())
			path.mkdirs();

		/*生成带标题信息的csv文件*/
		File csv = CSVUtils.createEmptyCSV(mapper, filePath, fileName);		
		/* 获取统计导出总数SQL */
		String count = countSQL + sql.substring(sql.indexOf("FROM"));
		/* 查询导出总条数 */
		List<Object> countRs = this.excuteSQLQuery(count, parameters);
		int countRow = Integer.parseInt(String.valueOf(countRs.get(0)));
		/*计算分页查询页数与分页导出数据并写入文件*/
		final int x = 10000;
		for (int i = 0; i < (countRow/x)+1; i++) {
			int page = i+1;
			List<Map<String, Object>> rows = this.runSQL(sql,parameters,columnReader,page, x);
			csv = CSVUtils.appendCSV(rows, mapper, csv);
		}

		String name = csv.getName();

		long size = csv.length();

		fileinfo.put("name", name);
		fileinfo.put("path", filePath + System.getProperty("file.separator")
				+ name);
		fileinfo.put("size", String.valueOf(size));
		fileinfo.put("type", "csv");

		return fileinfo;
	}
    
}


