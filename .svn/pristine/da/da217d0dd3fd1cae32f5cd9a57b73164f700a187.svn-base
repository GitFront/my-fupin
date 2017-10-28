
package com.aspire.birp.modules.dataLabel.config.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aspire.birp.modules.dataLabel.base.constant.DataLabelConstant;
import com.aspire.birp.modules.dataLabel.base.constant.LabelNodeType;
import com.aspire.birp.modules.dataLabel.base.service.DlBaseService;
import com.aspire.birp.modules.dataLabel.config.entity.DmLabelTreeDef;
import com.aspire.birp.modules.dataLabel.config.service.DlConfigService;
import com.aspire.birp.modules.dataLabel.config.vo.DmLabelTreeDefVO;
import com.aspire.birp.modules.dataMapping.mapping.service.DmMappingService;
import com.aspire.birp.modules.sys.utils.UserUtils;

/**  
 * @Title: 数据标签配置功能服务实现类
 * @Description: 用于实现数据标签配置功能服务方法
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年4月26日 下午2:58:35
 * @version: V1.0
 */
@Service
public class DlConfigServiceImpl extends DlBaseService implements
		DlConfigService {

	@Autowired
	private DmMappingService sqMappingService;
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.dataLabel.config.service.DlConfigService#queryLabelTree()
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<DmLabelTreeDefVO> queryLabelEditTree() {
		List<DmLabelTreeDefVO> vos = new ArrayList<DmLabelTreeDefVO>();
		String queryString = "from DmLabelTreeDef order by sort";
		List<?> rs = getHibernateTemplate().find(queryString);
		if(rs != null && !rs.isEmpty()){
			for (Object obj : rs) {
				DmLabelTreeDef def = (DmLabelTreeDef) obj;
				DmLabelTreeDefVO vo = this.copyBeanProperties(def);
				if(vo.getPid().equals(DataLabelConstant.EDIT_LABEL_TREE_ROOT_NODE_ID)){
					vo.setParent(true);
					vo.setOpen(true);
				}
				vos.add(vo);
			}
		}
		return vos;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.dataLabel.config.service.DlConfigService#queryLabelDef(java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public DmLabelTreeDefVO queryLabelDef(String id) {
		DmLabelTreeDefVO vo = new DmLabelTreeDefVO();
		if(StringUtils.isBlank(id))
			return vo;
		String queryString = "from DmLabelTreeDef where id=?";
		List<?> rs = getHibernateTemplate().find(queryString,id);
		if(rs != null && !rs.isEmpty()){
			DmLabelTreeDef label = (DmLabelTreeDef) rs.get(0);
			vo = copyBeanProperties(label);
			String type = label.getType();
			if(LabelNodeType.label.toString().equals(type) && StringUtils.isNotBlank(vo.getAssoField())){
				vo.setFieldName(sqMappingService.queryColumnMappingName(vo.getAssoFieldId()));
				vo.setDimName(sqMappingService.queryTableMappingName(vo.getAssoDimId()));
			}
			
		}
		
		return vo;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.dataLabel.config.service.DlConfigService#saveTreeNode(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean saveTreeNode(String id, String name, String pid,
			String fullPath) {
		/*节点创建信息*/
		String creator = UserUtils.getUser().getName();
		Timestamp createTime = new Timestamp(new Date().getTime());
		Long sort = this.getDmLabelTreeDefDAO().getMaxSort("DmLabelTreeDef") + 10;
		/*默认节点类型为目录类型*/
		this.getHibernateTemplate().save(
				new DmLabelTreeDef(id, name, pid, fullPath, LabelNodeType.catalog.toString(), 
						DataLabelConstant.COMMON_FLAG_TRUE,DataLabelConstant.COMMON_FLAG_TRUE,0, creator, createTime, sort));
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.dataLabel.config.service.DlConfigService#deleteLabel(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean deleteLabelNode(String id) {
		String hql = "DELETE FROM DmLabelTreeDef t WHERE t.id=?";
		/*删除标签节点对象*/
		this.getHibernateTemplate().bulkUpdate(hql,id);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.dataLabel.config.service.DlConfigService#updateTreeNode(com.aspire.birp.modules.dataLabel.config.vo.DmLabelTreeDefVO)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean updateTreeNode(DmLabelTreeDefVO vo) {
		String id = vo.getId();
		String queryString = "from DmLabelTreeDef where id=?";
		List<?> rs = getHibernateTemplate().find(queryString,id);
		if(rs != null && !rs.isEmpty()){
			DmLabelTreeDef label = (DmLabelTreeDef) rs.get(0);
			label.setType(vo.getType());
			label.setSort(vo.getSort());
			label.setAssoTableId(vo.getAssoTableId());
			label.setAssoTable(vo.getAssoTable());
			label.setAssoFieldId(vo.getAssoFieldId());
			label.setAssoField(vo.getAssoField());
			label.setAssoDimId(vo.getAssoDimId());
			label.setAssoDim(vo.getAssoDim());
			label.setAssoRule(vo.getAssoRule());
			label.setTopNum(vo.getTopNum());
			label.setActive(vo.getActive());
			label.setIsTemp(DataLabelConstant.COMMON_FLAG_FALSE);
			this.getHibernateTemplate().merge(label);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.dataLabel.config.service.DlConfigService#renameNode(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean renameNode(String id, String name, String fullPath) {
		String queryString = "from DmLabelTreeDef where id=?";
		List<?> rs = getHibernateTemplate().find(queryString,id);
		if(rs != null && !rs.isEmpty()){
			DmLabelTreeDef label = (DmLabelTreeDef) rs.get(0);
			label.setName(name);
			label.setFullPath(fullPath.substring(fullPath.indexOf("/")+1));
			label.setIsTemp(DataLabelConstant.COMMON_FLAG_FALSE);
			this.getHibernateTemplate().merge(label);
		}
		return true;
	}

	

}


