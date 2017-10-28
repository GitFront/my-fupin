
package com.aspire.birp.modules.smartQuery.market.service.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aspire.auth.modules.sys.entity.Office;
import com.aspire.auth.modules.sys.entity.User;
import com.aspire.bi.common.constant.DateConstant;
import com.aspire.bi.common.util.DateUtils;
import com.aspire.birp.common.excel.CSVUtils;
import com.aspire.birp.modules.smartQuery.base.constant.OptionType;
import com.aspire.birp.modules.smartQuery.base.constant.SmartQueryConstant;
import com.aspire.birp.modules.smartQuery.base.service.SqBaseService;
import com.aspire.birp.modules.smartQuery.base.vo.CombotreeObject;
import com.aspire.birp.modules.smartQuery.base.vo.SqUserInfoVO;
import com.aspire.birp.modules.smartQuery.market.entity.SqFileDataInfo;
import com.aspire.birp.modules.smartQuery.market.entity.SqFileShare;
import com.aspire.birp.modules.smartQuery.market.entity.SqPersonalCatalog;
import com.aspire.birp.modules.smartQuery.market.service.SqDataMarketService;
import com.aspire.birp.modules.smartQuery.market.vo.SqFileCatalogTree;
import com.aspire.birp.modules.smartQuery.market.vo.SqFileDataInfoVO;
import com.aspire.birp.modules.smartQuery.market.vo.SqFileTimeTree;
import com.aspire.birp.modules.smartQuery.market.vo.SqPersonalCatalogVO;
import com.aspire.birp.modules.smartQuery.query.entity.SqQueryInfo;
import com.aspire.birp.modules.smartQuery.query.service.SqQueryService;
import com.aspire.birp.modules.smartQuery.query.vo.SqQueryInfoVO;
import com.aspire.birp.modules.sys.utils.UserUtils;

/**  
 * @Title: 数据超市管理实现类 
 * @Description: 用于定义数据文件浏览及权限方法实现
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年10月27日 上午11:05:58
 * @version: V1.0
 */
@Service
public class SqDataMarketServiceImpl extends SqBaseService implements
		SqDataMarketService {

	private static Logger log = LoggerFactory.getLogger(SqDataMarketServiceImpl.class);
	/**
	 * 个人目录转化列表
	 */
	protected Map<String,SqPersonalCatalog> catalogMap;	
	
	@Autowired
	private SqQueryService sqQueryService;
	
    private static final String FUNCTION = "数据超市";
	

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#queryPersonalCatalogList()
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqPersonalCatalogVO> queryPersonalCatalogList() {
		/*获取当前用户的信息*/
		String creator = UserUtils.getUser().getId();
		List<SqPersonalCatalogVO> vos = new ArrayList<SqPersonalCatalogVO>();
		List<SqPersonalCatalog> list = this.getSqPersonalCatalogDAO().findByCreatorId(creator);
		if(list == null || list.size() == 0) return new ArrayList<SqPersonalCatalogVO>();
		for (SqPersonalCatalog en : list) {
			vos.add(this.copyBeanProperties(en));
		}
		return vos;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#queryPersonalCatalogInfo(java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public SqPersonalCatalogVO queryPersonalCatalogInfo(String id) {
		SqPersonalCatalogVO vo = new SqPersonalCatalogVO();
		if(StringUtils.isBlank(id)) return vo;
		SqPersonalCatalog catalog = this.getSqPersonalCatalogDAO().findById(id);
		vo = this.copyBeanProperties(catalog);
		return vo;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#savePersonalCatalog(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean savePersonalCatalog(String id, String name, String pId,String fullPathId,
			String fullPath) {
		User user = UserUtils.getUser();
		String creator = user.getName();
		String creatorId = user.getId();
		String creatorAcc = user.getLoginName();
		Timestamp createTime = new Timestamp(new Date().getTime());
		Long sort = this.getSqPersonalCatalogDAO().getMaxSort("SqPersonalCatalog") + 1;
		this.getHibernateTemplate().save(new SqPersonalCatalog(id, pId, name,fullPathId, fullPath,creatorId, creator,creatorAcc, createTime,SmartQueryConstant.COMMON_FLAG_FALSE,sort));
		return true;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#updatePersonalCatalog(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean updatePersonalCatalog(String id, String name, String fullPath) {
		if(StringUtils.isBlank(id)) return false;
		SqPersonalCatalog renameInstance = this.getSqPersonalCatalogDAO().findById(id);
		renameInstance.setName(name);
		renameInstance.setFullPath(fullPath);
		this.getHibernateTemplate().merge(renameInstance);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#removePersonalCatalog(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean removePersonalCatalog(String id) {
		if(StringUtils.isBlank(id)) return false;

		SqPersonalCatalog persistentInstance = this.getSqPersonalCatalogDAO().findById(id);
		this.getHibernateTemplate().delete(persistentInstance);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#isremovePersonalCatalog(java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public boolean isremovePersonalCatalog(String id) {
		if(StringUtils.isBlank(id)) return false;
		
		/*判断该目录是否含有绑定的查询配置*/
		List<SqQueryInfo> config = this.getSqQueryInfoDAO().findByPersonalCatalogId(id);
		if(config != null && config.size()>0) return false;
		
		/*判断该目录是否含有绑定的文件数据*/
		List<SqFileDataInfo> files = this.getSqFileDataInfoDAO().findByCatalogId(id);
		if(files != null && files.size()>0) return false;	
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#queryPersonalCatalogListByCombotree()
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<CombotreeObject> queryPersonalCatalogListByCombotree() {
		List<CombotreeObject> tree = new ArrayList<CombotreeObject>();
		List<SqPersonalCatalogVO> rows = this.queryPersonalCatalogList();
		if(rows.size() > 0){
			String creatorId = UserUtils.getUser().getId();
			/*查询目录的叶子节点信息*/
			String hql = "SELECT t.id FROM SqPersonalCatalog t WHERE t.creatorId=? AND t.id not in (SELECT r.parentId FROM SqPersonalCatalog r WHERE r.creatorId=? )";
			List<?> leef = this.getHibernateTemplate().find(hql, new Object[]{creatorId,creatorId});
			CombotreeObject root = new CombotreeObject(rows.get(0).getId(),rows.get(0).getName());
			this.forChlidren(root, rows, leef);			
			tree.add(root);
		}		
		return tree;
	}
	
	/**
	 * 递归生成树形结构的数据
	 * @param parent
	 * @param rows
	 * @param leef
	 * @author 张健雄
	 */
	private void forChlidren(CombotreeObject parent,List<SqPersonalCatalogVO> rows,List<?> leef){
		String parentId = parent.getId();
		
		for (SqPersonalCatalogVO row : rows) {
			if(parentId.equals(row.getParentId()) && !row.isRead()){
				row.setRead(true);
				String chlidId = row.getId();
				String name = row.getName();
				CombotreeObject chlid = new CombotreeObject(chlidId,name);
				if(!leef.contains(chlidId)){
					this.forChlidren(chlid,rows,leef);
				}
				parent.addChlidren(chlid);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#queryShareUserList(java.util.List)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Map<String,Object> queryShareUserList(String userids,String filter,int page,int pageSize) {
		Map<String, Object> rs = new HashMap<String,Object>();
		List<SqUserInfoVO> users = new ArrayList<SqUserInfoVO>();

		String creatorId = UserUtils.getUser().getId();
		/*分享用户不包括当前用户*/
		userids = StringUtils.isBlank(userids)?creatorId:userids+","+creatorId;
		String idlist = com.aspire.bi.common.util.StringUtils.splitStringForIds(userids);
		/* 分享用户查询 */
		String sql = "from User t where t.id not in ("+idlist+") ";
		if(StringUtils.isNotBlank(filter))
			sql += " and (t.loginName like '%"+filter+"%' or t.name like '%"+filter+"%') ";
		sql += " order by t.name ";
		
		List<User> userList = this.excuteQuery(sql,page,pageSize);
		if(userList != null && userList.size() > 0){
			for (User user : userList) {
				SqUserInfoVO userinfo = new SqUserInfoVO();
				userinfo.setId(user.getId());
				userinfo.setUserAccount(user.getLoginName());
				userinfo.setName(user.getName());
				userinfo.setEmail(user.getEmail());
				userinfo.setCompany(user.getCompany());
				userinfo.setDepartmentName(user.getCompany());
				userinfo.setDepartmentPath(user.getCompany());
//				
//				userinfo.setCompany(user.getCompany().getName());
//				userinfo.setDepartmentName(user.getOffice().getName());
//				userinfo.setDepartmentPath(user.getOffice().getName());
				users.add(userinfo);
			}
		}
		String countSql = "select count(t.id) from User t where t.id not in ("+idlist+")";
		if(StringUtils.isNotBlank(filter))
			countSql += " and (t.loginName like '%"+filter+"%' or t.name like '%"+filter+"%') ";
		int size = 0;
		List<Object> rowCount = this.excuteQuery(countSql,0,0);
		if(rowCount != null && rowCount.size() > 0 && rowCount.get(0) != null){
			size = Integer.parseInt(String.valueOf(rowCount.get(0)));
		}
		rs.put("rows", users);
		rs.put("total", size);
		return rs;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.market.service.SqDataMarketService#saveDataFile(java.lang.String, java.lang.String[])
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public boolean saveFileShare(String fileId, String[] userids) {
		if(StringUtils.isBlank(fileId))
			return false;
		/*清空分享列表*/
		List<?> list = this.getHibernateTemplate().find("From SqFileShare where id.fileId=?", fileId);
		this.getHibernateTemplate().deleteAll(list);
		SqFileDataInfo file = this.getSqFileDataInfoDAO().findById(fileId);
		
		Map<String,String> lp = new HashMap<String, String>();
		lp.put("file", file.getFileName());
		if(userids == null || userids.length == 0){
			if(!SmartQueryConstant.FILE_STATUS_LOST.equals(file.getStatus())){
				file.setStatus(SmartQueryConstant.FILE_STATUS_NORMAL);
				this.getHibernateTemplate().merge(file);
			}
			/*保存文件清空分享日志*/
			markOptionLogger("数据分享", OptionType.ShareClean_file, lp);
			return true;
		}
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		StringBuffer shareUsers = new StringBuffer();
		String ids = com.aspire.bi.common.util.StringUtils.splitStringForArray(userids, ",");
		/* 分享用户查询 */
		String hql = "from User t where t.id in ("+ids+") ";
		List<?> users = getHibernateTemplate().find(hql);
		for (int i = 0; i < users.size(); i++) {
			User u = (User) users.get(i);
			this.getHibernateTemplate().save(new SqFileShare(fileId,u.getId(),createTime));
			if(i != 0)
				shareUsers.append("，");
			shareUsers.append("“"+u.getName()+"”");				
		}
		file.setStatus(SmartQueryConstant.FILE_STATUS_SHARE);
		this.getHibernateTemplate().merge(file);
		/*保存文件分享日志*/
		lp.put("shareUsers", shareUsers.toString());
		markOptionLogger("数据分享", OptionType.DataShare_file, lp);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#saveDataFile(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean saveDataFile(String sqId, String isTemp, String fileName,
			String personalCatalogId, List<String> userids) {
		
		/*查询当前用户的创建信息*/
		User user = UserUtils.getUser();
		String creatorId = user.getId();
		String creatorAcc = user.getLoginName();
		String creator = user.getName();
		Timestamp createTime = new Timestamp(new Date().getTime());
		
		SqQueryInfoVO query = sqQueryService.querySqQueryInfoById(sqId, isTemp);
		
		Map<String,String> fileinfo = null;
		
		/*通过sql参数生成excel文件*/
		try {
			fileinfo = sqQueryService.createFileBySqId(sqId, isTemp,fileName,creatorAcc);
		} catch (Exception e) {
			log.error("创建Excel文件失败！",e);
			return false;
		}
		
		/**记录操作日志信息-文件保存及分享操作*/
		Map<String,String> lp = new HashMap<String, String>();
		lp.put("tables", query.getSqTable());
		lp.put("file", fileName);
		
		String isShare = (userids != null && !userids.isEmpty())?SmartQueryConstant.FILE_STATUS_SHARE:SmartQueryConstant.FILE_STATUS_NORMAL;
		/*文件保存ID值*/
		String id = UUID.randomUUID().toString();
		
		SqFileDataInfo file = new SqFileDataInfo();
		file.setId(id);
		file.setFileName(fileName);
		file.setCreateType(SmartQueryConstant.QUERY_TASK_TYPE_MANUAL);
		file.setFileType(fileinfo.get("type"));
		file.setFileSize(fileinfo.get("size") == null ? 0D:Double.valueOf(fileinfo.get("size")));
		file.setFilePath(fileinfo.get("path"));
		file.setCatalogId(personalCatalogId);
		file.setSqId(sqId);
		file.setDataCyc(new Timestamp(new Date().getTime()));
		/*手动任务不设期限*/
		file.setExecTime(createTime);
		file.setDataStoreDate(new Timestamp(DateUtils.stringToDate("9999-12-31", DateConstant.YEAR_MOUTH_DAY).getTime()));
		file.setCreatorId(creatorId);
		file.setCreator(creator);
		file.setCreatorAcc(creatorAcc);
		file.setCreateTime(createTime);
		file.setStatus(isShare);
		this.getHibernateTemplate().save(file);
		
		/*保存文件日志信息*/
		marketLogger(OptionType.SmartQuery_filesave, lp);
		
		/*添加文件访问权限*/
		/*给其它用户赋权*/
		if(userids != null && !userids.isEmpty()){
			StringBuffer shareUsers = new StringBuffer();
			String ids = com.aspire.bi.common.util.StringUtils.splitStringForList(userids);
			/* 分享用户查询 */
			String hql = "from User t where t.id in ("+ids+") ";
			List<?> users = getHibernateTemplate().find(hql);
			for (int i = 0; i < users.size(); i++) {
				User u = (User) users.get(i);
				getHibernateTemplate().save(new SqFileShare(id,u.getId(),createTime));
				if(i != 0)
					shareUsers.append("，");
				shareUsers.append("“"+u.getName()+"”");
			}
			/*保存数据分享日志*/
			lp.put("shareUsers", shareUsers.toString());
			markOptionLogger("数据分享",OptionType.DataShare_file, lp);
		}
		
		
		
		return true;
	}
	
	

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.market.service.SqDataMarketService#queryFileCatalogTree()
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqFileCatalogTree> queryFileCatalogTree() {
		List<SqFileCatalogTree> tree = new ArrayList<SqFileCatalogTree>();
		/*获取当前用户信息*/
		String creator = UserUtils.getUser().getId();

		/*查询个人目录信息*/
		/*构建目录树的根结点*/
		SqFileCatalogTree allFiles = new SqFileCatalogTree(SmartQueryConstant.DATA_MARKET_ALL_FILE,"所有目录", "-1");
		tree.add(allFiles);
		SqFileCatalogTree root = new SqFileCatalogTree(SmartQueryConstant.DATA_MARKET_PERSONAL_CATALOG,"个人目录", SmartQueryConstant.DATA_MARKET_ALL_FILE);
		tree.add(root);
		List<SqPersonalCatalog> persons = this.getSqPersonalCatalogDAO().findByCreatorId(creator);
		if(persons != null && persons.size()>0){
			for (SqPersonalCatalog person : persons) {
				/*处理个人目录根节点连接*/
				String parentId = person.getParentId();
				if("-1".equals(person.getParentId()))
					parentId = SmartQueryConstant.DATA_MARKET_PERSONAL_CATALOG;
				tree.add(new SqFileCatalogTree(person.getId(),person.getName(), parentId,person.getFullPath()));
			}
		}		
		/*查询共享目录信息*/
		SqFileCatalogTree root2 = new SqFileCatalogTree(SmartQueryConstant.DATA_MARKET_SHARE_CATALOG,"共享目录", SmartQueryConstant.DATA_MARKET_ALL_FILE);
		tree.add(root2);
		tree.addAll(querySharedCatalogList(creator));
		
		return tree;
	}
	
	/**
	 * 通过用户ID查询该用户的共享目录
	 * @param userId
	 * @return
	 * @author 张健雄
	 */
	public List<SqFileCatalogTree> querySharedCatalogList(String userId){
		
		
		List<SqFileCatalogTree> sharedTree = new ArrayList<SqFileCatalogTree>();
		
		if(StringUtils.isBlank(userId)) return sharedTree;
		
		List<String> cidList = new ArrayList<String>();
		
		String hql = "FROM SqPersonalCatalog t "
				+ "WHERE t.id IN (SELECT f.catalogId FROM SqFileDataInfo f WHERE EXISTS (SELECT s.id.fileId FROM SqFileShare s WHERE f.id=s.id.fileId AND s.id.userId=?)) "
				+ "OR t.id IN (SELECT s.id.personalCatalogId FROM SqPersonalCatalogShare s WHERE s.id.userId=?)";
		
		List<?> sharedCatalogs = getHibernateTemplate().find(hql,new Object[]{userId,userId});
		
		/*寻找扩展的父节点目录*/
		if(sharedCatalogs != null && !sharedCatalogs.isEmpty()){
			for (int i = 0; i < sharedCatalogs.size(); i++) {
				SqPersonalCatalog catalog = (SqPersonalCatalog) sharedCatalogs.get(i);
				String fullPathId = catalog.getFullPathId();
				String[] ids = StringUtils.split(fullPathId,"/");
				for (int j = 0; j < ids.length; j++) {
					String id = ids[j];
					if(!cidList.contains(id))
						cidList.add(id);
				}			
			}
		}
		
		String cids = com.aspire.bi.common.util.StringUtils.splitStringForList(cidList);
		
		hql = "FROM SqPersonalCatalog t WHERE t.id IN ("+cids+") ORDER BY t.createTime";
		
		List<?> catalogList = getHibernateTemplate().find(hql);
		
		if(catalogList != null && !catalogList.isEmpty()){
			for (int i = 0; i < catalogList.size(); i++) {
				SqPersonalCatalog catalog = (SqPersonalCatalog) catalogList.get(i);
				
				/*判断个人目录顶级节点*/
				if("-1".equals(catalog.getParentId())){
					String sharedUser = getShareUser(catalog.getCreatorAcc(), catalog.getCreator());
					SqFileCatalogTree userNode = new SqFileCatalogTree(catalog.getCreatorId(),sharedUser, SmartQueryConstant.DATA_MARKET_SHARE_CATALOG);
					SqFileCatalogTree node = new SqFileCatalogTree(catalog.getId(),catalog.getName(), catalog.getCreatorId(),catalog.getFullPath());
					sharedTree.add(userNode);
					sharedTree.add(node);
				}else{
					sharedTree.add(new SqFileCatalogTree(catalog.getId(),catalog.getName(), catalog.getParentId(),catalog.getFullPath()));
				}				
			}
		}
		return sharedTree;
	}
	

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.market.service.SqDataMarketService#queryFileTimeTree()
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqFileTimeTree> queryFileTimeTree() {
		List<SqFileTimeTree> tree = new ArrayList<SqFileTimeTree>();
		SqFileTimeTree allFiles = new SqFileTimeTree(SmartQueryConstant.DATA_MARKET_ALL_FILE,"所有时间", "-1");
		tree.add(allFiles);
		/*获取当前用户信息*/
		String creator = UserUtils.getUser().getId();
		List<SqFileDataInfoVO> files = queryPermissionFileList(creator,false);
		if(files != null && files.size()>0){
			
			List<String> timeList = new ArrayList<String>();
			for (SqFileDataInfoVO file : files) {
				String createTime = file.getTimeTree();
				String year = createTime.substring(0,4);
				if(!timeList.contains(year)){
					tree.add(new SqFileTimeTree(createTime,SmartQueryConstant.DATA_MARKET_ALL_FILE));
					timeList.add(year);
				}
				if(!timeList.contains(createTime)){
					tree.add(new SqFileTimeTree(createTime));
					timeList.add(createTime);
				}				
			}
		}		
		return tree;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.market.service.SqDataMarketService#queryFileList()
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqFileDataInfoVO> queryFileList() {
		/*获取当前用户信息*/
		String creator = UserUtils.getUser().getId();
		List<SqFileDataInfoVO> vos = queryPermissionFileList(creator,true);
		return vos;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.market.service.SqDataMarketService#queryFileInfoById(java.lang.String)
	 */
	@Override
	public SqFileDataInfoVO queryFileInfoById(String fileId) {
		SqFileDataInfo file = this.getSqFileDataInfoDAO().findById(fileId);
		return copyBeanPropertiesFileinfo(file);
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.market.service.SqDataMarketService#deleteFileInfoById(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean deleteFileInfoById(String fileId) {
		if(StringUtils.isBlank(fileId)) return false;
		SqFileDataInfo file = this.getHibernateTemplate().get(SqFileDataInfo.class, fileId);
		Map<String,String> lp = new HashMap<String, String>();
		lp.put("file", file.getFileName());
		/*物理删除数据文件*/
		String filepath = file.getFilePath();
		if(StringUtils.isNotBlank(filepath) && filepath.indexOf(File.separator) != -1){
			String path = filepath.substring(0, filepath.lastIndexOf(File.separator));
			String fileName = filepath.substring(filepath.lastIndexOf(File.separator)+1);
			CSVUtils.deleteFile(path, fileName);
		}
		if(file != null)
			this.getHibernateTemplate().delete(file);
		/*记录文件删除日志*/
		marketLogger(OptionType.DataMarket_delete, lp);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.market.service.SqDataMarketService#deleteFileInfoById(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean checkFileInfoById(String fileId) {
		if(StringUtils.isBlank(fileId)) return false;
		SqFileDataInfo file = this.getSqFileDataInfoDAO().findById(fileId);
		if(file == null) return false;
		/*物理删除数据文件*/
		String filepath = file.getFilePath();
		File f = new File(filepath);
		
		if(f.exists())
			return true;
		/*更新数据文件当前丢失状态*/
		file.setStatus(SmartQueryConstant.FILE_STATUS_LOST);
		this.getHibernateTemplate().merge(file);
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.market.service.SqDataMarketService#queryFileListByCatalog(java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqFileDataInfoVO> queryFileListByCatalog(String catalogId) {
		/*获取当前用户信息*/
		String creator = UserUtils.getUser().getId();
		List<SqFileDataInfoVO> vos = queryPermissionFileListByCatalog(creator,catalogId,true);
		return vos;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.market.service.SqDataMarketService#queryFileListByTime(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqFileDataInfoVO> queryFileListByTime(String time,
			String timeType) {
		/*获取当前用户信息*/
		String creator = UserUtils.getUser().getId();
		
		List<SqFileDataInfoVO> files = queryPermissionFileListByTime(creator, time, timeType, true);
		
		return files;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.market.service.SqDataMarketService#queryFileListByConfig(java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqFileDataInfoVO> queryFileListByTask(String taskId) {
		/*获取当前用户信息*/
		String creator = UserUtils.getUser().getId();
		List<SqFileDataInfoVO> vos = queryPermissionFileListByTask(creator, taskId, true);
		return vos;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.market.service.SqDataMarketService#marketLogger(java.lang.String, com.aspire.birp.modules.smartQuery.base.constant.OptionType)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public void marketLogger(OptionType type,Map<String,String> lp) {
		markOptionLogger(FUNCTION, type, lp);
	}
	
	/**
	 * 数据文件列表属性复制
	 * @param orig
	 * @return
	 * @author 张健雄
	 */
	private List<SqFileDataInfoVO> copyArrayProperties(List<?> origList){
		List<SqFileDataInfoVO> vos = new ArrayList<SqFileDataInfoVO>();
		if(origList != null && !origList.isEmpty()){
			/*转换file属性对象*/
			for (int i = 0; i < origList.size(); i++) {
				SqFileDataInfo file = (SqFileDataInfo)origList.get(i);
				vos.add(copyBeanPropertiesFileinfo(file));
			}		
		}
		return vos;
    }
	
	/**
	 * 数据文件属性复制
	 * @param orig
	 * @param catalogMap
	 * @return
	 * @author 张健雄
	 */
	private SqFileDataInfoVO copyBeanPropertiesFileinfo(SqFileDataInfo orig){
    	SqFileDataInfoVO vo = new SqFileDataInfoVO();
		try {
			BeanUtils.copyProperties(orig, vo);
			vo.setTimeTree(DateUtils.dateTimeToString(orig.getCreateTime(), DateConstant.YEARMOUTH));
			vo.setCreateTime(DateUtils.dateTimeToString(orig.getCreateTime(), DateConstant.YYYY_MM_DD_HH_MM_SS));
			vo.setDataCyc(DateUtils.dateTimeToString(orig.getDataCyc(), DateConstant.YEAR_MOUTH_DAY));
			vo.setExecTime(DateUtils.dateTimeToString(orig.getExecTime(), DateConstant.YEAR_MOUTH_DAY));
			vo.setFileSizeCov(orig.getFileSize());
			if(catalogMap == null){
				catalogMap = new HashMap<String, SqPersonalCatalog>();
				List<SqPersonalCatalog> catalogs = this.getSqPersonalCatalogDAO().findAll();
				for (SqPersonalCatalog catalog : catalogs) {
					catalogMap.put(catalog.getId(), catalog);
				}
			}
			String fullPath = "";
			if(catalogMap.get(orig.getCatalogId()) != null)
					fullPath = catalogMap.get(orig.getCatalogId()).getFullPath();
			vo.setCatalogPath(fullPath);
		} catch (Exception e) {
			log.error(SqFileDataInfo.class+"对象属性转换失败",e);
		} 
		return vo;
    }

	/**
	 * 通过用户ID获取该用户所有授权数据文件
	 * @param uesrId
	 * @return 返回所有文件的ID值
	 * @author 张健雄
	 */
	private String queryUserFilePermission(String userId){
		/*查询该用户所拥有的共享文件ID集合SQL*/
		/*该用户所创建的数据文件*/
		String sql = "SELECT sdf.id FROM SqFileDataInfo sdf WHERE sdf.creatorId='"+userId+"' ";		
		/*该用户所被授权的数据文件*/
		sql += " OR EXISTS (SELECT sfs.id.fileId FROM SqFileShare sfs WHERE sdf.id=sfs.id.fileId AND sfs.id.userId='"+userId+"') ";
		/*该用户所被授权的目录内所有数据文件*/
		sql += " OR EXISTS (SELECT spcs.id.personalCatalogId FROM SqPersonalCatalogShare spcs WHERE sdf.catalogId=spcs.id.personalCatalogId AND spcs.id.userId='"+userId+"')";
		
		return sql;
	}
	
	/**
	 * 通过用户ID查询该用户的共享文件信息(可定义排序方式)
	 * @param uesrId
	 * @param isDesc
	 * @return
	 * @author 张健雄
	 */
	private List<SqFileDataInfoVO> queryPermissionFileList(String uesrId,boolean isDesc){
		/*查询该用户所拥有的共享文件列表*/
		String idSQL = queryUserFilePermission(uesrId);
		
		String hql = "FROM SqFileDataInfo f WHERE f.id IN ("+idSQL+") ORDER BY f.createTime ";
		if(isDesc) hql += "DESC";
		List<?> files =  this.getHibernateTemplate().find(hql);
		List<SqFileDataInfoVO> vos = copyArrayProperties(files);
		return vos;
	}
	
	/**
	 * 通过用户ID查询该用户的共享文件信息(可定义指定目录与排序方式)
	 * @param uesrId
	 * @param catalogId
	 * @param isDesc
	 * @return
	 * @author 张健雄
	 */
	private List<SqFileDataInfoVO> queryPermissionFileListByCatalog(String uesrId,String catalogId,boolean isDesc){
		/*查询该用户所拥有的共享文件列表*/
		String idSQL = queryUserFilePermission(uesrId);
		
		String hql = "FROM SqFileDataInfo f WHERE f.id IN ("+idSQL+") ";
		String orderSQL = isDesc?" ORDER BY f.createTime DESC ":" ORDER BY f.createTime ";
		List<?> files = null;
		if(StringUtils.isNotBlank(catalogId)){
			if(SmartQueryConstant.DATA_MARKET_PERSONAL_CATALOG.equals(catalogId)){
				/*所有个人目录处理*/
				hql += " AND EXISTS (SELECT id FROM SqPersonalCatalog WHERE id=f.catalogId AND creatorId = ? ) ";
				hql += orderSQL;
				files = this.getHibernateTemplate().find(hql,uesrId);
			}else if(SmartQueryConstant.DATA_MARKET_SHARE_CATALOG.equals(catalogId)){
				/*所有共享目录处理*/
				hql += " AND EXISTS (SELECT id FROM SqPersonalCatalog WHERE id=f.catalogId AND creatorId <> ? ) ";
				hql += orderSQL;
				files =  this.getHibernateTemplate().find(hql,uesrId);
			}else if(SmartQueryConstant.DATA_MARKET_ALL_FILE.equals(catalogId)){
				hql += orderSQL;
				files = this.getHibernateTemplate().find(hql);
			}else{
				hql += " AND EXISTS (SELECT id FROM SqPersonalCatalog WHERE id=f.catalogId AND fullPathId LIKE ? ) ";
				hql += orderSQL;
				files = this.getHibernateTemplate().find(hql,"%"+catalogId+"%");
			}
		}
		List<SqFileDataInfoVO> vos = copyArrayProperties(files);
		
		return vos;
	}
	
	/**
	 * 通过用户ID查询该用户的共享文件信息(可定义指定目录与排序方式)
	 * @param uesrId
	 * @param time 查询时间 YYYYMM
	 * @param timeType 时间类型
	 * @param isDesc
	 * @return
	 * @author 张健雄
	 */
	private List<SqFileDataInfoVO> queryPermissionFileListByTime(String uesrId,String time,String timeType,boolean isDesc){
		
		/*查询该用户所拥有的共享文件列表*/
		String idSQL = queryUserFilePermission(uesrId);
		
		String hql = "FROM SqFileDataInfo f WHERE f.id IN ("+idSQL+") ";
		String orderSQL = isDesc?" ORDER BY f.createTime DESC ":" ORDER BY f.createTime ";
		List<?> files = null;
		if(!SmartQueryConstant.DATA_MARKET_ALL_FILE.equals(time)){
			/*处理开始结束时间信息*/
			Timestamp first = null;
			Timestamp last = null;
			if(SmartQueryConstant.DATA_MARKET_TIME_TYPE_YRAR.equals(timeType)){
				/*年时间处理方式*/
				Date firstDay = com.aspire.birp.modules.base.utils.Utils.getFirstDayOfMonth(Integer.valueOf(time),1);
		        Date lastDay  = com.aspire.birp.modules.base.utils.Utils.getLastDayOfMonth(Integer.valueOf(time),12);
		        
		        first = new Timestamp(firstDay.getTime());
		        last = new Timestamp(lastDay.getTime());
			}else{
				/*解析年部分信息*/
				String year = time.substring(0,4);
				String month = time.substring(4);
				/*年时间处理方式*/
				Date firstDay = com.aspire.birp.modules.base.utils.Utils.getFirstDayOfMonth(Integer.valueOf(year),Integer.valueOf(month));
		        Date lastDay  = com.aspire.birp.modules.base.utils.Utils.getLastDayOfMonth(Integer.valueOf(year),Integer.valueOf(month));
		        
		        first = new Timestamp(firstDay.getTime());
		        last = new Timestamp(lastDay.getTime());
			}
			hql += " AND f.createTime BETWEEN ? AND ? ";
			hql += orderSQL;
			files = this.getHibernateTemplate().find(hql,new Object[]{first,last});
		}else {
			hql += orderSQL;
			files = this.getHibernateTemplate().find(hql);
		}
		
		List<SqFileDataInfoVO> vos = copyArrayProperties(files);		
		return vos;
	}
	
	/**
	 * 通过用户ID查询该用户的共享文件信息(可定义指定目录与排序方式)
	 * @param uesrId
	 * @param catalogId
	 * @param isDesc
	 * @return
	 * @author 张健雄
	 */
	private List<SqFileDataInfoVO> queryPermissionFileListByTask(String uesrId,String taskId,boolean isDesc){
		/*查询该用户所拥有的共享文件列表*/
		String idSQL = queryUserFilePermission(uesrId);
		
		String hql = "FROM SqFileDataInfo f WHERE f.id IN ("+idSQL+") ";
		if(StringUtils.isNotBlank(taskId))
			hql += " AND f.sqId=? ";
		hql += " ORDER BY f.createTime ";
		if(isDesc) hql += " DESC";
		List<?> files = this.getHibernateTemplate().find(hql,taskId);
		
		List<SqFileDataInfoVO> vos = copyArrayProperties(files);
		
		return vos;
	}

	/**
	 * 获取共享目录的唯一用户名
	 * @param loginName
	 * @param userName
	 * @return
	 * @author 张健雄
	 */
	private String getShareUser(String loginName,String userName){
		return userName + "("+loginName+")";
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.market.service.SqDataMarketService#queryUserSelected(java.lang.String)
	 */
	@Override
	public List<String> queryUserSelected(String fileId) {
		List<?> shareList = this.getHibernateTemplate().find("From SqFileShare where id.fileId=?", fileId);
		List<String> userids = new ArrayList<String>();
		for (Object object : shareList) {
			SqFileShare share = (SqFileShare) object;
			userids.add(share.getId().getUserId());
		}
		
		return userids;
	}

}


