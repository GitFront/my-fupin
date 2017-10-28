package com.aspire.birp.modules.smartQuery.query.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aspire.auth.modules.sys.entity.User;
import com.aspire.birp.common.excel.CSVUtils;
import com.aspire.birp.modules.smartQuery.base.constant.FileProperties;
import com.aspire.birp.modules.smartQuery.base.service.SqBaseService;
import com.aspire.birp.modules.smartQuery.query.entity.SqListFilterDesc;
import com.aspire.birp.modules.smartQuery.query.entity.SqListFilterInfo;
import com.aspire.birp.modules.smartQuery.query.service.SqListFilterService;
import com.aspire.birp.modules.smartQuery.query.vo.SqListFilterInfoVO;
import com.aspire.birp.modules.sys.utils.UserUtils;

/**
 * @Title: 列表过滤器功能服务实现类
 * @Description: 用于实现列表过滤器功能服务方法
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年3月23日 14:30:56
 * @version: V1.0
 */
@Service
public class SqListFilterServiceImpl extends SqBaseService implements SqListFilterService {

	private static Logger log = LoggerFactory
			.getLogger(SqListFilterServiceImpl.class);

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqListFilterService#queryOwnList()
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqListFilterInfoVO> queryOwnList() {
		List<SqListFilterInfoVO> vos = new ArrayList<SqListFilterInfoVO>();
		/*获取当前用户的账号信息*/
		String loginName = UserUtils.getUser().getLoginName();
		List<SqListFilterInfo> entityList =  this.getSqListFilterInfoDAO().findByCreatorAcc(loginName);
		if(entityList != null && !entityList.isEmpty()){
			for (SqListFilterInfo entity : entityList) {
				SqListFilterInfoVO vo = copyBeanPropertiesListFilter(entity);
				vos.add(vo);
			}
		}
		log.info("列表过滤器的列表信息加载完成！");
		return vos;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqListFilterService#queryListFilter(java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public SqListFilterInfoVO queryListFilter(String id) {
		if (StringUtils.isBlank(id))
			return null;
		SqListFilterInfo info = getSqListFilterInfoDAO().findById(id);
		SqListFilterInfoVO vo = copyBeanPropertiesListFilter(info);
		return vo;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqListFilterService#queryOwnList(java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqListFilterInfoVO> queryOwnList(String listName) {
		List<SqListFilterInfoVO> vos = new ArrayList<SqListFilterInfoVO>();
		/*获取当前用户的账号信息*/
		String loginName = UserUtils.getUser().getLoginName();
		Object[] value ;
		String hql = "FROM SqListFilterInfo f WHERE f.creatorAcc=? ";
		if(StringUtils.isNotBlank(listName)){
			hql += " AND f.listName LIKE ?";
			value = new Object[]{loginName,"%"+listName+"%"};
		}else{
			value = new Object[]{loginName};
		}
			
		List<?> list = this.getHibernateTemplate().find(hql,value);
		for (Object obj : list) {
			SqListFilterInfoVO vo = copyBeanPropertiesListFilter((SqListFilterInfo)obj);
			vos.add(vo);
		}

		log.info("列表过滤器的列表信息加载完成！");
		return vos;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqListFilterService#deleteListFilter(java.lang.String[])
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteListFilter(List<String> ids) {
		if(ids == null || ids.isEmpty())
			return false;
		/*判断ID集合是否存在于现有查询信息中，如果存在则不能批量删除*/
		String idList = com.aspire.bi.common.util.StringUtils.splitStringForList(ids);
		String hql = "FROM SqFilterInfo f WHERE f.value IN ("+idList+")";
		List<?> filters = getHibernateTemplate().find(hql);
		if(filters != null && !filters.isEmpty())
			return false;
		
		for (int i = 0; i < ids.size(); i++) {
			SqListFilterInfo info = getSqListFilterInfoDAO().findById(ids.get(i));
			String filepath = info.getFilePath();
			String id = info.getId();
			this.getHibernateTemplate().delete(info);
			/*删除对应的明细数据*/
			hql = "DELETE FROM SqListFilterDesc c WHERE c.listId=?";
			this.getHibernateTemplate().bulkUpdate(hql, id);
			/*删除上传原始文件*/
			String path = filepath.substring(0, filepath.lastIndexOf(File.separator));
			String fileName = filepath.substring(filepath.lastIndexOf(File.separator)+1);
			CSVUtils.deleteFile(path, fileName);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqListFilterService#uploadList(java.lang.String, java.lang.String, java.lang.String, org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean uploadList(String id,String listName, String listSeparator,
			MultipartFile listFile) throws IOException {
		if(StringUtils.isBlank(listName))
			return false;
		/*判断过滤器数据的保存方式*/
		boolean isUpdate = StringUtils.isNotBlank(id);
		if(!isUpdate) id = UUID.randomUUID().toString();
		boolean hasFile = !listFile.isEmpty();
		String targetName = "";
		String targetPath = "";
		List<SqListFilterDesc> descList = new ArrayList<SqListFilterDesc>();
		if(hasFile){
			/*获取文件名称*/
			targetName = listFile.getOriginalFilename();
			/*保存文件的目标目录*/
			targetPath = FileProperties.getFilePath() + System.getProperty("file.separator") + "ListFilter" 
								+ System.getProperty("file.separator");
			File targetFile = new File(targetPath, targetName);
			FileUtils.copyInputStreamToFile(listFile.getInputStream(), targetFile);
			
			targetPath = targetFile.getPath();
			
			/*读取列表过滤器的详细信息*/
			InputStreamReader read = new InputStreamReader(listFile.getInputStream());		
			BufferedReader br=new BufferedReader(read);
			
			/*是否通过换行进行条目分隔*/
			boolean isLine = StringUtils.isBlank(listSeparator);
			String lineTxt = null;
			if(isLine){
				while((lineTxt = br.readLine()) != null){
					SqListFilterDesc desc = new SqListFilterDesc();
					String descId = UUID.randomUUID().toString();
					desc.setId(descId);
					desc.setInfo(lineTxt);
					desc.setListId(id);
					descList.add(desc);
		        }
	        }else{
	        	while((lineTxt = br.readLine()) != null){
	        		String[] descs = StringUtils.split(lineTxt,listSeparator);
	        		for (int i = 0; i < descs.length; i++) {
	        			SqListFilterDesc desc = new SqListFilterDesc();
	    				String descId = UUID.randomUUID().toString();
	    				desc.setId(descId);
	    				desc.setInfo(descs[i]);
	    				desc.setListId(id);
	    				descList.add(desc);
					}
		        }
	        }
	        read.close();
		}
		
        Long listNum = (long) descList.size();
        
        if(isUpdate){
        	SqListFilterInfo info = getSqListFilterInfoDAO().findById(id);
        	info.setListName(listName);
        	info.setListSeparator(listSeparator);
        	if(hasFile){
        		info.setListNum(listNum);
        		info.setFileName(targetName);
            	info.setFilePath(targetPath);
        	}        	
        	this.getHibernateTemplate().merge(info);
        }else{
        	/*获取创建用户信息*/
        	User user = UserUtils.getUser();
        	String creatorId = user.getId();
        	String creator = user.getName();
        	String creatorAcc = user.getLoginName();
        	Timestamp createTime = new Timestamp(new Date().getTime());
        	Long sort = getSqListFilterInfoDAO().getMaxSort("SqListFilterInfo") + 1;
        	
        	SqListFilterInfo info = new SqListFilterInfo();
        	info.setId(id);
        	info.setListName(listName);
        	info.setListSeparator(listSeparator);
        	info.setListNum(listNum);
        	info.setFileName(targetName);
        	info.setFilePath(targetPath);
        	info.setCreatorId(creatorId);
        	info.setCreator(creator);
        	info.setCreatorAcc(creatorAcc);
        	info.setCreateTime(createTime);
        	info.setSort(sort);
        	this.getHibernateTemplate().save(info);
        }
        /*保存列表过滤器的详细信息*/
        if(hasFile){
        	/*删除对应的明细数据*/
			String hql = "DELETE FROM SqListFilterDesc c WHERE c.listId=?";
			this.getHibernateTemplate().bulkUpdate(hql, id);
			for (SqListFilterDesc desc : descList) {
				this.getHibernateTemplate().save(desc);
			}
        }
         
		return true;
	}

	

}
