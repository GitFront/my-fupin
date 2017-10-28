
package com.aspire.birp.modules.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Title: 智能查询基础 DAO <br>
 * Description: 主要用于智能查询的底层接口开发 <br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2015年9月15日 下午10:45:03 <br>
 *
 */ 
@Repository("baseDAO")
public class BaseDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory
			.getLogger(BaseDAO.class);
	
	/** 
     * 加入会话读取线程   
     */
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	
	@Resource(name = "hibernateTemplate")
	public void setSuperHibernateTemplate(HibernateTemplate hibernateTemplate) {
		super.setHibernateTemplate(hibernateTemplate);
	}

	/**
	 * 获取会话session对象
	 * @return
	 * @throws HibernateException
	 * @author 张健雄
	 */
	public Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        
		if (session == null || !session.isOpen()) {
			SessionFactory sessionFactory = this.getSessionFactory();
			session = (sessionFactory != null) ? sessionFactory.openSession() : null;
			threadLocal.set(session);
		}
        return session;
    }
	
	/**
	 * 关闭session会话对象
	 * @throws HibernateException
	 * @author 张健雄
	 */
    public void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);

        if (session != null) {
            session.close();
        }
    }
    
    /**
     * 获取当前对象在数据库中的最大排序号
     * @param pojo 实体名称
     * @return
     * @author 张健雄
     */
    public long getMaxSort(String pojo){
    	String HQL = "SELECT MAX(sort) FROM " + pojo;
    	List<?> list =  this.getHibernateTemplate().find(HQL);
    	if(list != null && list.size()>0 && list.get(0) != null){
    		log.debug("select the max sort in "+pojo);
    		return (Long) list.get(0);
    	}else{
    		return 0L;
    	}
    	
    }
    
    
    
	public List<?> findByCriteria(DetachedCriteria dc) {
		log.debug("findByCriteria instances");
		try {
			getSession().clear();
			return  this.getHibernateTemplate().findByCriteria(dc);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
}


