
package com.aspire.birp.modules.dataLabel.base.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.auth.modules.sys.constants.LogConstant;
import com.aspire.auth.modules.sys.entity.User;
import com.aspire.birp.modules.base.service.BaseService;
import com.aspire.birp.modules.dataLabel.config.dao.DmLabelTreeDefDAO;
import com.aspire.birp.modules.dataLabel.config.entity.DmLabelTreeDef;
import com.aspire.birp.modules.dataLabel.config.vo.DmLabelTreeDefVO;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableMapping;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmTableMappingVO;
import com.aspire.birp.modules.smartQuery.base.constant.OptionType;
import com.aspire.birp.modules.sys.utils.UserUtils;


/**  
 * @Title: 数据标签通用功能服务实现类
 * @Description: 用于实现数据标签的通用功能服务方法
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年4月26日 下午2:30:56
 * @version: V1.0
 */
@Service
public class DlBaseService extends BaseService {

	private static Logger log = LoggerFactory.getLogger(DlBaseService.class);

    
    /*获取DAO服务类*/
    @Autowired
    private DmLabelTreeDefDAO dmLabelTreeDefDAO;
    
    /*entity与vo属性转换方法*/
    protected DmTableMappingVO copyBeanProperties(DmTableMapping orig){
    	DmTableMappingVO vo = new DmTableMappingVO();
		try {
			BeanUtils.copyProperties(orig, vo);
		} catch (Exception e) {
			log.error(DmTableMapping.class+"对象属性转换失败",e);
		} 
		return vo;
    }
    
    protected DmLabelTreeDefVO copyBeanProperties(DmLabelTreeDef orig){
    	DmLabelTreeDefVO vo = new DmLabelTreeDefVO();
		try {
			BeanUtils.copyProperties(orig, vo);
		} catch (Exception e) {
			log.error(DmLabelTreeDef.class+"对象属性转换失败",e);
		} 
		return vo;
    }
    
    /**
     * 通过table列表获取映射表信息
     * @param table 单表或多表模式
     * @return
     * @author 张健雄
     */
    protected List<DmTableMappingVO> queryMappingbytables(String... table) {
    	List<DmTableMappingVO> tables = new ArrayList<DmTableMappingVO>();
    	if(table.length == 0 )
    		return tables;
    	String ids = com.aspire.bi.common.util.StringUtils.splitStringForArray(table, ",");
    	String hql = "FROM DmTableMapping t WHERE t.tableName IN ("+ids+")";
    	List<?> tms = getHibernateTemplate().find(hql);
    	for (Object obj : tms) {
    		DmTableMapping tm = (DmTableMapping) obj;
    		DmTableMappingVO vo = copyBeanProperties(tm);
    		tables.add(vo);
		}    	
		return tables;
	}
    
    /**
     * 数据操作日志记录接口
     * @param function 功能名称
     * @param type 操作类型
     * @param lp 日志描述参数
     * @param operationType 操作类别
     * @author 张健雄
     */
    protected void markOptionLogger(final String function,OptionType type,Map<String,String> lp,String operationType) {
		User user = UserUtils.getUser();
		String desc = getLogDescForDataoption(lp, type);
		this.getLogService().saveLog(LogConstant.TYPE_OPERATION, function, operationType, desc,user);
	}
    
    /**
     * 通过功能模块的标识符及必要的参数信息生成数据操作的日志描述
     * @param parameters 日志参数
     * @param type 功能模块的标识符
     * @return
     * @author 张健雄
     */
    protected String getLogDescForDataoption(Map<String,String> parameters,OptionType type ) {
    	StringBuffer desc = new StringBuffer();
    	if(type == null)
    		return desc.toString();
    	switch (type) {
		case label_search:
			String lss = parameters.get("labels");
			desc.append("对 "+lss+"等标签进行数据查询操作");
			break;
		case label_export:
			String lse = parameters.get("labels");
			String totalCount = parameters.get("totalCount");
			desc.append("对 "+lse+"等标签进行数据导出操作，总记录条数为"+totalCount);
			break;
		
		default:
			break;
		}
    	return desc.toString();
	}

	public DmLabelTreeDefDAO getDmLabelTreeDefDAO() {
		return dmLabelTreeDefDAO;
	}

	public void setDmLabelTreeDefDAO(DmLabelTreeDefDAO dmLabelTreeDefDAO) {
		this.dmLabelTreeDefDAO = dmLabelTreeDefDAO;
	}

	
    
    
}


