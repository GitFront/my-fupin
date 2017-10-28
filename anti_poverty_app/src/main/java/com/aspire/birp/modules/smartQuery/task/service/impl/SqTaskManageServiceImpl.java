package com.aspire.birp.modules.smartQuery.task.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.aspire.bi.common.util.DateUtils;
import com.aspire.bi.common.web.bean.easyui.DataGrid;
import com.aspire.birp.modules.smartQuery.exception.entity.ModuleType;
import com.aspire.birp.modules.smartQuery.query.vo.SqQueryInfoVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aspire.auth.modules.sys.entity.User;
import com.aspire.birp.common.utils.StringUtils;
import com.aspire.birp.modules.smartQuery.base.constant.SmartQueryConstant;
import com.aspire.birp.modules.smartQuery.base.service.SqBaseService;
import com.aspire.birp.modules.smartQuery.query.entity.SqQueryInfo;
import com.aspire.birp.modules.smartQuery.task.entity.SqTaskInfo;
import com.aspire.birp.modules.smartQuery.task.service.SqTaskManageService;
import com.aspire.birp.modules.sys.utils.UserUtils;

/**
 * Title: hydc_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 *
 * @author <a href="mailto:suweicheng@aspirecn.com">苏伟城</a>
 * @version 1.0
 * @e-mail: suweicheng@aspirecn.com
 * @creatdate 2015/10/25 15:21
 */
@Service
public class SqTaskManageServiceImpl extends SqBaseService implements SqTaskManageService {

    /**
     * 页面列表查询
     * @param page
     * @param pageSize
     * @return
     */
    public Map<String,Object> queryTaskList(int page,int pageSize,String user,String type,String name) {
        Map<String,Object> result = new HashMap<String, Object>(2);
        List<Object> params = new LinkedList<Object>();
        String condition = getCondition(params,user,type,name);
        Object[] obj = params.toArray();
        int count = countTasks(condition, obj);
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>(0);
        if(count > 0 ) {
            rows = queryTasks(condition,obj,page,pageSize);
            rows = getExceptionInfo(rows);
        }
        result.put("total",count);
        result.put("rows",rows);
        return result;
    }

    /**
     * 统计任务数量
     * @param condition
     * @param obj
     * @return
     */
    private int countTasks(String condition,Object[] obj) {
        StringBuffer sb = new StringBuffer();
        sb.append("select count(1) as sum ");
        sb.append("from SQ_QUERY_INFO ti ");
        if(StringUtils.isNotBlank(condition)) {
            sb.append("where 1=1 ");
            sb.append(condition);
        }
        List<BigDecimal> result = excuteSQLQuery(sb.toString(), obj);
        if(result.size() != 0 ) {
            return result.get(0).intValue();
        }else {
            return 0;
        }
    }

    /**
     * 查询任务
     * @param condition
     * @param obj
     * @return
     */
    public List<Map<String, Object>> queryTasks(String condition,Object[] obj,int page,int pageSize) {
        //执行的sql语句
        StringBuffer sb = new StringBuffer();
        sb.append("select a.file_name,a.cyc_type,a.cyc_len,a.auther,to_char(a.commit_time, 'yyyy-MM-dd') as commit_time,");
        sb.append("a.last_data_cyc,a.last_dur,to_char(nvl2(b.task_status,b.DATA_CYC_NEXT,a.cyc_next), 'yyyy-MM-dd') AS cyc_next,");
        sb.append("NVL ( b.task_status, A .current_status ) AS current_status,");
        sb.append("a.id,a.name,a.type,a.status from(");
        sb.append("select TI.FILE_NAME as file_name,TI.CYC_TYPE as cyc_type,TI.CYC_LEN as cyc_len, ");
        sb.append(" TI.CREATOR as auther,TI.CREATE_TIME as commit_time, ");
        sb.append(" to_char(TSI.TASK_BEGIN_TIME, 'yyyy-MM-dd HH24:mi:ss ') as last_data_cyc,TSI.TASK_DUR as last_dur,TSI.DATA_CYC_NEXT as cyc_next, ");
        sb.append(" TSI.TASK_STATUS as current_status,ti.id as id,ti.name as name,ti.CONFIG_TYPE as type, ");
        sb.append("sti.TASK_STATUS as status, sti.data_cyc ");
        sb.append("from SQ_QUERY_INFO ti left join SQ_TASK_INFO sti ");
        sb.append(" on(ti.id = sti.task_id) ");
        sb.append(" left join SQ_TASK_STATUS_INFO tsi on( ");
        sb.append("sti.TASK_ID = TSI.TASK_ID ");
        sb.append("and sti.DATA_CYC = TSI.DATA_CYC_NEXT) ");
        if(StringUtils.isNotBlank(condition)) {
            sb.append("where 1=1 ");
            sb.append(condition);
        }
        sb.append(") a left join SQ_TASK_STATUS_INFO b on(a .id = b.TASK_ID");
        sb.append(" and a.data_cyc = b.DATA_CYC) ");
        sb.append("order by a.commit_time ");

        List<Object[]> rowsTemp = this.excuteSQLQuery(sb.toString(),obj,page,pageSize);

        //查询的列
        String[] columnArr = {"file_name","cyc_type","cyc_len","auther","commit_time","last_data_cyc",
                                "last_dur","cyc_next","current_status","id","name","type","status"};
        List<String> columns = Arrays.asList(columnArr);
        return sqlResultTransfer(rowsTemp,columns);
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

    /**
     *  读取异常任务的当前异常信息
     * @param rows
     * @return
     */
    private List<Map<String,Object>> getExceptionInfo(List<Map<String,Object>> rows) {
        String sql = "select DESCRIPTION from SQ_EXCEPTION_INFO ei where EXTRA_PROP_ONE = ? ";
        for(Map<String,Object> map : rows) {
            if(map.get("type").toString().equals(SmartQueryConstant.QUERY_TASK_TYPE_AUTO) &&
                    map.get("status").toString().equals(SmartQueryConstant.COMMON_FLAG_TRUE)
                    && null != map.get("current_status")          //修改任务的时候会涉及到运行状态的变更
                    && map.get("current_status").toString().equals("4")){
                Object[] obj = new Object[1];
                obj[0] = map.get("id");
                List<Object> rowsTemp = this.excuteSQLQuery(sql,obj,1,1);
                if(rowsTemp.size() != 0) {
                    map.put("description",rowsTemp.get(0));
                }
            }
        }
        return rows;
    }

    /**
     * 获取查询条件和查询参数
     * @param params
     * @return
     */
    private String getCondition(List<Object> params,String user,String type,String name) {
        StringBuffer sb = new StringBuffer();
        User currentUser = UserUtils.getUser();
        if(!currentUser.isAdmin()) {
            user = currentUser.getId();
        }
        if(StringUtils.isNotBlank(user)) {
            sb.append(" and ti.CREATOR_ID = ? ");
            params.add(user);
        }
        if(StringUtils.isNotBlank(type) && !type.equals("all")) {
            sb.append(" and ti.CONFIG_TYPE = ? ");
            params.add(type);
        }
        if(StringUtils.isNotBlank(name)) {
            sb.append(" and ti.NAME like '%' || ? || '%' ");
            params.add(name);
        }
         return sb.toString();
    }

    /**
     * 任务启动/暂停
     * @param taskId
     * @return 任务状态变更的日志
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public String startOrStopTask(String taskId) {
        StringBuilder log = new StringBuilder("对");
        SqTaskInfo sqTaskInfo = getSqTaskInfoDAO().findById(taskId);
        SqQueryInfo sqQueryInfo = getSqQueryInfoDAO().findById(taskId);
        log.append(sqQueryInfo.getName());
        log.append("自动任务进行");
        if(sqTaskInfo.getTaskStatus().equals(SmartQueryConstant.COMMON_FLAG_TRUE)) {
            sqTaskInfo.setTaskStatus(SmartQueryConstant.COMMON_FLAG_FALSE);
            log.append("暂停");
        }else {
            sqTaskInfo.setTaskStatus(SmartQueryConstant.COMMON_FLAG_TRUE);
            log.append("启动");
        }
        log.append("操作");
        getHibernateTemplate().merge(sqTaskInfo);
        return log.toString();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public String deleteTask(String taskId) {
        StringBuilder log = new StringBuilder("对");
        SqQueryInfo sqQueryInfo = getSqQueryInfoDAO().findById(taskId);
        String sqId = sqQueryInfo.getId();
        SqTaskInfo sqTaskInfo = getSqTaskInfoDAO().findById(taskId);

        log.append(sqQueryInfo.getName());
        log.append("自动任务进行删除操作");
        if(null != sqTaskInfo) {  //需要删除自动任务表里面的数据,有可能自动转手动
        	getHibernateTemplate().delete(sqTaskInfo);
        }        
        //删除任务表数据
        this.getHibernateTemplate().delete(sqQueryInfo);
		String tableHql = "DELETE FROM SqTableInfo WHERE sqId=?";
		String selectHql = "DELETE FROM SqSelectInfo WHERE sqId=?";
		String filterHql = "DELETE FROM SqFilterInfo WHERE sqId=?";
		String sortHql = "DELETE FROM SqSortInfo WHERE sqId=?";
		String parameterHql = "DELETE FROM SqFilterParameter t WHERE EXISTS (SELECT id FROM SqQueryInfoTemp WHERE t.sqId=?)";

		/* 批量删除查询对象 */
		this.getHibernateTemplate().bulkUpdate(tableHql,sqId);
		this.getHibernateTemplate().bulkUpdate(selectHql,sqId);
		this.getHibernateTemplate().bulkUpdate(filterHql,sqId);
		this.getHibernateTemplate().bulkUpdate(sortHql,sqId);
		this.getHibernateTemplate().bulkUpdate(parameterHql,sqId);
        return log.toString();
    }

    /**
     * 查询任务创建人，管理员可以看到全部，非管理员只能看到自己
     * @return
     */
    @Override
    public List<Map<String, Object>> queryCreators() {
        User user = UserUtils.getUser();
        List<Map<String, Object>> userList = new LinkedList<Map<String, Object>>();
        if(user.isAdmin()) {   //管理员显示任务里面的所有用户
            StringBuffer sb = new StringBuffer();
            sb.append("select u.id as id,u.name as name,");
            sb.append("u.company || '/' || u.office as dept from sys_user u");
            sb.append(",(select distinct CREATOR_ID from SQ_QUERY_INFO) qi ");
            sb.append("where u.id = qi.CREATOR_ID ");
            List<Object[]> rowsTemp = this.excuteSQLQuery(sb.toString());
            //查询的列
            String[] columnArr = {"id","name","dept"};
            List<String> columns = Arrays.asList(columnArr);
            userList = sqlResultTransfer(rowsTemp,columns);
        }else {
            Map<String,Object> map = new HashMap<String, Object>(4);
            map.put("id",user.getId());
            map.put("name",user.getName());
            map.put("dept", user.getCompany() + "/" + user.getOffice());
            userList.add(map);
        }
        return userList;
    }

    /**
     * 任务异常日志查询
     * @param taskId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public DataGrid queryTaskExceptions(String taskId, int page, int pageSize) {
        List<Object> params = new LinkedList<Object>();
        DataGrid dataGrid = new DataGrid();
        params.add(ModuleType.SQ_TASK.getId());
        params.add(taskId);
        long total = countTaskExceptions(params);
        dataGrid.setTotal(total);
        if(total > 0) {
            dataGrid.setRows(queryTaskExceptions(params, page, pageSize));
        }else {
            dataGrid.setRows(new ArrayList(0));
        }
        return dataGrid;
    }

    /**
     * 统计异常信息总数
     * @param params
     * @return
     */
    private long countTaskExceptions(List<Object> params) {
        StringBuilder hql = new StringBuilder();
        hql.append("select  count(*) as total from SqExceptionInfo where moduleId = ? and extraPropOne = ? ");
        List<Long> total = excuteQuery(hql.toString(),params.toArray(),1,10);
        return total.get(0);
    }

    /**
     * 分页查询异常信息
     * @param params
     * @param page
     * @param pageSize
     * @return
     */
    private List<Map> queryTaskExceptions(List<Object> params,int page,int pageSize) {
        StringBuilder hql = new StringBuilder();
        hql.append("select new Map(description as description,to_char(createTime,'yyyy-MM-dd HH:mm:ss') as createTime,extraPropTwo as data_cyc) ");
        hql.append("from SqExceptionInfo where moduleId = ? and extraPropOne = ? ");
        hql.append("order by createTime desc");
        List<Map> list = excuteQuery(hql.toString(),params.toArray(),page,pageSize);
        return list;
    }

    /**
     * 通过id查找任务信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> queryInfoById(String id) {
        StringBuilder hql = new StringBuilder();
        hql.append("select new Map(id as id,name as name,configType as configType,fileName as fileName, ");
        hql.append("personalCatalogId as personalCatalogId,dataStoreDate as dataStoreDate,cycType as cycType, ");
        hql.append("cycLen as cycLen,to_char(validDate,'yyyy-MM-dd') as validDate, ");
        hql.append("to_char(invalidDate,'yyyy-MM-dd') as invalidDate) ");
        hql.append("from SqQueryInfo where id = ? ");
        List<String> params = new LinkedList<String>();
        params.add(id);
        List<Map<String,Object>> list = excuteQuery(hql.toString(), params.toArray(), 1, 10);
        if(list.size() != 0) {
            return list.get(0);
        }else {
            return null;
        }
    }

    /**
     * 更新任务信息
     * @param sqQueryInfoVo
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public String updateSqQueryInfo(SqQueryInfoVO sqQueryInfoVo) {
        StringBuilder log = new StringBuilder("对");
        //更新任务信息
        SqQueryInfo sqQueryInfo = getSqQueryInfoDAO().findById(sqQueryInfoVo.getId());
        sqQueryInfoVo.setMpTableId(sqQueryInfo.getMpTableId());
        sqQueryInfoVo.setSqTable(sqQueryInfo.getSqTable());
        sqQueryInfo.setId(sqQueryInfoVo.getId());
        this.updatePropertiesQuery(sqQueryInfoVo, sqQueryInfo);
        this.getHibernateTemplate().merge(sqQueryInfo);

        SqTaskInfo sqTaskInfo = getSqTaskInfoDAO().findById(sqQueryInfoVo.getId());
        if(sqQueryInfo.getConfigType().equalsIgnoreCase(SmartQueryConstant.QUERY_TASK_TYPE_AUTO)) {  //更新自动任务信息
            if(null == sqTaskInfo) {       //手动变自动，之前没有设置过自动任务
                sqTaskInfo = new SqTaskInfo();
                updatePropertiesTask(sqQueryInfo, sqTaskInfo);
                User user = UserUtils.getUser();
                sqTaskInfo.setTaskId(sqQueryInfo.getId());
                sqTaskInfo.setTaskAutherId(user.getId());
                sqTaskInfo.setTaskAutherAcc(user.getLoginName());
                sqTaskInfo.setTaskAuther(user.getName());
                sqTaskInfo.setTaskCommitTime(DateUtils.dateToTimestamp(new Date()));
                sqTaskInfo.setTaskStatus(SmartQueryConstant.COMMON_FLAG_TRUE);
                getHibernateTemplate().save(sqTaskInfo);
            }else{
                updatePropertiesTask(sqQueryInfo, sqTaskInfo);
                sqTaskInfo.setTaskStatus(SmartQueryConstant.COMMON_FLAG_TRUE);
                getHibernateTemplate().merge(sqTaskInfo);
            }
        }else { //不是自动任务
            if(null != sqTaskInfo) {  //以前是自动任务
                sqTaskInfo.setTaskStatus(SmartQueryConstant.COMMON_FLAG_FALSE); //变更自动任务状态
                getHibernateTemplate().merge(sqTaskInfo);
            }
        }

        log.append(sqQueryInfo.getName());
        log.append("自动任务进行修改操作");
        return log.toString();
    }

    /**
     * 通过查询对象信息更新任务列表数据
     * @param query
     * @param task
     * @author 张健雄
     */
    private void updatePropertiesTask(SqQueryInfo query,SqTaskInfo task){

        task.setRqSourceTb(query.getSqTable());
        task.setCatalogId(query.getPersonalCatalogId());
        task.setCycTyp(query.getCycType());
        task.setCycLen(query.getCycLen());
        task.setValidTime(query.getValidDate());
        task.setInvalidTime(query.getInvalidDate());

        task.setDataStoreDt(query.getDataStoreDate());
        task.setFileName(query.getFileName());
        task.setSqSelectSql(query.getSqSelectSql());
        task.setSqFilterSql(query.getSqFilterSql());
        task.setSqSortSql(query.getSqSortSql());
        task.setSqFromSql(query.getSqFromSql());
        task.setSqColumns(query.getSqColumns());
        task.setDataCyc(query.getValidDate());
    }
}
