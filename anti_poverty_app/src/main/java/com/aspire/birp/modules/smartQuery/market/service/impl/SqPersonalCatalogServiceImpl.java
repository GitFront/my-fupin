
package com.aspire.birp.modules.smartQuery.market.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aspire.auth.modules.sys.entity.User;
import com.aspire.birp.common.utils.StringUtils;
import com.aspire.birp.modules.smartQuery.base.constant.OptionType;
import com.aspire.birp.modules.smartQuery.base.constant.SmartQueryConstant;
import com.aspire.birp.modules.smartQuery.base.service.SqBaseService;
import com.aspire.birp.modules.smartQuery.market.entity.SqPersonalCatalog;
import com.aspire.birp.modules.smartQuery.market.entity.SqPersonalCatalogShare;
import com.aspire.birp.modules.smartQuery.market.entity.SqPersonalCatalogShareId;
import com.aspire.birp.modules.smartQuery.market.service.SqPersonalCatalogService;
import com.aspire.birp.modules.sys.utils.UserUtils;
/**
 * 
 * Title:目录分享实现类 <br>
 * Description: <br>
 * Copyright: aspire Copyright (C) 2009<br>
 * @author <a href="mailto:zr.wuganqin@aspirecn.com">吴淦钦</a><br>
 * @version 1.0<br>
 * @e-mail: zr.wuganqin@aspirecn.com<br>
 * @creatdate 2015年11月17日 下午2:18:14<br>
 */
@Service
public class SqPersonalCatalogServiceImpl extends SqBaseService implements SqPersonalCatalogService {
	
	@Override
	public Map<String, Object> querySqPersonalCatalogList(Integer page,
			Integer rows,String creator, String name) {
		 	Map<String,Object> result = new HashMap<String, Object>(2);
		 	DetachedCriteria dc = DetachedCriteria.forClass(SqPersonalCatalog.class);
		 	 User currentUser = UserUtils.getUser();
		    if(!currentUser.isAdmin()) {
		        	dc.add(Restrictions.eq("creatorId",currentUser.getId()));
		    }
		 	if(StringUtils.isNotBlank(name)){
		 		dc.add(Restrictions.like("name",name,MatchMode.ANYWHERE));
		 	}
		 	if(StringUtils.isNotBlank(creator)){
		 		dc.add(Restrictions.like("creator",creator,MatchMode.ANYWHERE));
		 	}
		 	dc.addOrder(Order.asc("createTime"));
		 	Integer start=(page-1)*rows;
		 	List<SqPersonalCatalog> list = (List<SqPersonalCatalog>)getHibernateTemplate().findByCriteria(dc,start,rows);
		 	dc.setProjection(Projections.rowCount());
		 	int count = ((Long) getHibernateTemplate().findByCriteria(dc).get(0)).intValue(); 
		 	
	        result.put("total",count);
	       result.put("rows",list);
		return result;
	}

	@Override
	public List<Map<String, Object>> queryUserTree() {
		String userId = UserUtils.getUser().getId();
        StringBuffer sb = new StringBuffer();
        List<Map<String, Object>> userList = new LinkedList<Map<String, Object>>();
        sb.append("SELECT  DISTINCT COMPANY as id,COMPANY as name,COMPANY  as pid, 'true' as isParent ,'false' as checked   FROM SYS_USER  where (OFFICE is null) and (COMPANY is not null) and ID !=:userId");
        sb.append(" UNION ");
        sb.append("SELECT  DISTINCT OFFICE as id,OFFICE as name,COMPANY  as pid ,'true' as isParent ,'false' as checked  FROM SYS_USER where (OFFICE is not null)  and ID !=:userId");
        sb.append(" UNION ");
        sb.append("SELECT   ID as id,Name as name,OFFICE  as pid ,'false' as isParent ,'false' as checked  FROM SYS_USER  where (OFFICE is not null)  and ID !=:userId");
       // List<Object[]> rowsTemp = this.excuteSQLQuery(sb.toString());
        Map<String,Object>parameters=new HashMap<String, Object>();
        parameters.put("userId", userId);
        List<Object[]> rowsTemp = this.excuteSQLQuery(sb.toString(), parameters);
       String[] columnArr = {"id","name","pId","isParent","checked"};
       List<String> columns = Arrays.asList(columnArr);
       userList = sqlResultTransfer(rowsTemp,columns);
		return userList;
	}
	

	/**
     * 将sql的结果转成List<Map>存储
     * @param rowsTemp
     * @param columns
     * @return
     */
    private List<Map<String, Object>> sqlResultTransfer(List<Object[]> rowsTemp,List<String> columns) {
        List<Map<String, Object>> rows = new ArrayList<Map<String,Object>>(rowsTemp.size());
        //将数据转成Map存储
        for (int i = 0; i < rowsTemp.size(); i++) {
            Map<String, Object> row = new HashMap<String, Object>();
            for (int j = 0; j < columns.size(); j++) {
                Object value = rowsTemp.get(i)[j];
                row.put(columns.get(j), value);
            }
            rows.add(row);
        }
        return rows;
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public void savePersonalCatalogShare(String catalogId, String[] userIds) {
		List<?> list = this.getHibernateTemplate().find("From SqPersonalCatalogShare where id.personalCatalogId=?", catalogId);
		this.getHibernateTemplate().deleteAll(list);
		SqPersonalCatalog pc = this.getSqPersonalCatalogDAO().findById(catalogId);
		Map<String,String> lp = new HashMap<String, String>();
		lp.put("user", pc.getCreator());
		lp.put("path", pc.getFullPath());	
		if(userIds!=null && userIds.length > 0){
			SqPersonalCatalogShare sqPersonalCatalogShare=null;
			SqPersonalCatalogShareId id= null;
			
			StringBuffer shareUsers = new StringBuffer();
			String ids = com.aspire.bi.common.util.StringUtils.splitStringForArray(userIds, ",");
			/* 分享用户查询 */
			String hql = "from User t where t.id in ("+ids+") ";
			List<?> users = getHibernateTemplate().find(hql);
			for (int i = 0; i < users.size(); i++) {
				User u = (User) users.get(i);
				sqPersonalCatalogShare=new SqPersonalCatalogShare();
				id=new SqPersonalCatalogShareId();
				id.setPersonalCatalogId(catalogId);
				id.setUserId(u.getId());
				sqPersonalCatalogShare.setId(id);
				sqPersonalCatalogShare.setCreateTime(new Timestamp(System.currentTimeMillis()));
				
				this.getHibernateTemplate().save(sqPersonalCatalogShare);
				if(i != 0)
					shareUsers.append("，");
				shareUsers.append("“"+u.getName()+"”");				
			}
			this.getHibernateTemplate().bulkUpdate("update SqPersonalCatalog  set shareStatus=? where id=?",SmartQueryConstant.COMMON_FLAG_TRUE, catalogId);
			/*保存目录分享日志*/
			lp.put("shareUsers", shareUsers.toString());
			markOptionLogger("数据分享", OptionType.DataShare_catalog, lp);
			
		}else{
			this.getHibernateTemplate().bulkUpdate("update SqPersonalCatalog  set shareStatus=? where id=?",SmartQueryConstant.COMMON_FLAG_FALSE, catalogId);
			/*保存目录清空分享日志*/
			markOptionLogger("数据分享", OptionType.ShareClean_catalog, lp);
		}
	}
	
	@Override
	public List<SqPersonalCatalogShare> queryUserSelectTree(String id) {
		List<SqPersonalCatalogShare> shareList = (List<SqPersonalCatalogShare>) this.getHibernateTemplate().find("From SqPersonalCatalogShare where id.personalCatalogId=?", id);
		return shareList;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.market.service.SqPersonalCatalogService#savePersonalCatalogByUsers()
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean savePersonalCatalogByUsers() {
		/*查询未创建个人目录的用户列表*/
		String hql = "FROM User t WHERE NOT EXISTS (select creatorAcc from SqPersonalCatalog where creatorAcc=t.loginName)";
		List<?> users = this.getHibernateTemplate().find(hql);
		/*获取初始化信息*/
		Timestamp createTime = new Timestamp(new Date().getTime());
		Long sort = this.getSqPersonalCatalogDAO().getMaxSort("SqPersonalCatalog") + 1;
		for (Object obj : users) {
			String id = UUID.randomUUID().toString();
			String nodeName = "个人主目录";
			User user = (User) obj;
			String creator = user.getName();
			String creatorId = user.getId();
			String creatorAcc = user.getLoginName();
			this.getHibernateTemplate().save(
					new SqPersonalCatalog(id, "-1", nodeName,id,nodeName,creatorId, creator,creatorAcc, createTime,SmartQueryConstant.COMMON_FLAG_FALSE,sort));
			sort++;			
		}
		return true;
	}
}


