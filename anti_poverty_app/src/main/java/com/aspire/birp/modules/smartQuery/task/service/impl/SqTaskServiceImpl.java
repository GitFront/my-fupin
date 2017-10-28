package com.aspire.birp.modules.smartQuery.task.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aspire.auth.modules.sys.constants.LogConstant;
import com.aspire.auth.modules.sys.entity.User;
import com.aspire.bi.common.util.DateUtils;
import com.aspire.birp.modules.smartQuery.base.constant.SmartQueryConstant;
import com.aspire.birp.modules.smartQuery.base.constant.TackCycType;
import com.aspire.birp.modules.smartQuery.base.service.SqBaseService;
import com.aspire.birp.modules.smartQuery.exception.entity.ModuleType;
import com.aspire.birp.modules.smartQuery.exception.service.SqExceptionService;
import com.aspire.birp.modules.smartQuery.market.entity.SqFileDataInfo;
import com.aspire.birp.modules.smartQuery.query.entity.SqQueryInfo;
import com.aspire.birp.modules.smartQuery.query.service.SqQueryService;
import com.aspire.birp.modules.smartQuery.query.vo.SqQueryInfoVO;
import com.aspire.birp.modules.smartQuery.task.entity.SqTaskInfo;
import com.aspire.birp.modules.smartQuery.task.entity.SqTaskStatusInfo;
import com.aspire.birp.modules.smartQuery.task.entity.TaskException;
import com.aspire.birp.modules.smartQuery.task.service.SqTaskCommonService;
import com.aspire.birp.modules.smartQuery.task.service.SqTaskService;
import com.aspire.birp.modules.sys.service.LogService;

/**
 * SqTaskInfo定时任务执行
 * Title: hydc_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 *
 * @author <a href="mailto:suweicheng@aspirecn.com">苏伟城</a>
 * @version 1.0
 * @e-mail: suweicheng@aspirecn.com
 * @creatdate 2015/10/20 16:18
 */
@Service
public class SqTaskServiceImpl extends SqBaseService implements SqTaskService {

    private static Logger log = LoggerFactory.getLogger(SqTaskServiceImpl.class);

    private static final String FUNCTION = "自动任务";

    @Autowired
    private SqQueryService sqQueryService;

    @Autowired
    private SqTaskCommonService sqTaskCommonService;

    @Autowired
    private SqExceptionService sqExceptionService;

    private static final String DATE_CYC_FORMAT = "yyyyMMdd";

    @Autowired
    private LogService logService;

    @Transactional
    public void executeTask() {
        //查询任务
        StringBuffer sb = new StringBuffer();
        sb.append("from SqTaskInfo where ");
        sb.append(" dataCyc <= invalidTime ");
        sb.append(" and dataCyc <= ? ");
        sb.append(" and taskStatus=? ");
        String hql = sb.toString();
        Date currentDate = Calendar.getInstance().getTime();
        List<?> sqTaskInfoList = this.getHibernateTemplate().find(hql,currentDate, SmartQueryConstant.COMMON_FLAG_TRUE);
        log.debug("size " + sqTaskInfoList.size());

      /*  //查询各个任务对应的表中是否有相应时间的数据
        String countSql = "select count(1) as sum from ";
        String querySql = " where " + SmartQueryConstant.COLUMN_DATE_NAME + " = ?";
        List<String> columns = new ArrayList<String>(1);
        columns.add("sum");
        List<BigDecimal> result;
        Integer currentDateCyc;
        List<SqTaskInfo> executeTasks = new LinkedList<SqTaskInfo>();
        List<SqTaskStatusInfo> executeTasksStatus = new LinkedList<SqTaskStatusInfo>();
        for(SqTaskInfo sqTaskInfo : sqTaskInfoList) {
            List<SqTaskStatusInfo> taskStatusInfos = sqTaskCommonService.queryCurrentTaskStatusInfo(sqTaskInfo);
            if(taskStatusInfos.size() != 0) {  //当前任务正在执行中，此次调度不需要执行，另没有在查task的时候使用left join原因：TaskStatusinfo是TaskInfo的属性才行，即建立外键
                continue;
            }
            currentDateCyc = convertDateCycToInteger(sqTaskInfo.getDataCyc(),DATE_CYC_FORMAT);
            sb.delete(0, sb.length());
            sb.append(countSql);
            sb.append(sqTaskInfo.getRqSourceTb());
            sb.append(querySql);
            
            try {
				result = excuteSQLQuery(sb.toString(),
						new Object[] { currentDateCyc });
			} catch (Exception e) {
				log.error("统计sqTaskInfo Id为" + sqTaskInfo.getTaskId() + "当前周期数据失败",e);
				continue;
			}
			if(result.size() != 0 ) {
                if(result.get(0).intValue() > 0) {  //因为是单线程任务，所以先记录任务开始时间，然后再执行任务
                    executeTasksStatus.add(recordStartTaskInfo(sqTaskInfo));
                    executeTasks.add(sqTaskInfo);
                }
            }
        }*/
        SqTaskInfo sqTaskInfo;
        SqTaskStatusInfo sqTaskStatusInfo;
        List<SqTaskInfo> executeTasks = new LinkedList<SqTaskInfo>();
        List<SqTaskStatusInfo> executeTasksStatus = new LinkedList<SqTaskStatusInfo>();
        for(int i = 0;i < sqTaskInfoList.size();i++) { //表中有数据，执行前台提供的sql语句
            sqTaskInfo = (SqTaskInfo)sqTaskInfoList.get(i);
            List<SqTaskStatusInfo> taskStatusInfos = sqTaskCommonService.queryCurrentTaskStatusInfo(sqTaskInfo);
            if(taskStatusInfos.size() != 0) {  //当前任务正在执行中，此次调度不需要执行，另没有在查task的时候使用left join原因：TaskStatusinfo是TaskInfo的属性才行，即建立外键
                continue;
            }
            sqTaskStatusInfo = recordStartTaskInfo(sqTaskInfo);
            executeTasks.add(sqTaskInfo);
            executeTasksStatus.add(sqTaskStatusInfo);
        }

        for(int i = 0;i < executeTasks.size();i++) {
            sqTaskInfo = executeTasks.get(i);
            sqTaskStatusInfo = executeTasksStatus.get(i);
            try {
                executeSqTaskInfo(sqTaskInfo,sqTaskStatusInfo);
            } catch (Exception e) {
                log.error("执行sqTaskInfo Id为" + sqTaskInfo.getTaskId() + "失败",e);
                if(e instanceof TaskException) {
                	TaskException taskException = (TaskException) e;
                	 executeFail(sqTaskInfo,sqTaskStatusInfo,taskException.getOriginalException(),taskException.getMsg());
                }else {
                	 executeFail(sqTaskInfo,sqTaskStatusInfo,e,"");
                }
              
            }
        }

    }

    /**
     * 执行sqTaskInfo内容，并导出到excel中和计算下一个周期
     * @param sqTaskInfo
     */
    @Transactional
    private void executeSqTaskInfo(SqTaskInfo sqTaskInfo,SqTaskStatusInfo sqTaskStatusInfo) throws Exception {
        long startTime = System.currentTimeMillis();
		/*生成运行的sql*/
       /* StringBuffer sb = new StringBuffer();
        sb.append(sqTaskInfo.getSqSelectSql());
        sb.append("  ");
        sb.append(sqTaskInfo.getSqFromSql());
        sb.append("  ");
        if(StringUtils.isBlank(sqTaskInfo.getSqFilterSql())) {  //没有过滤条件下需要添加where
            sb.append("where ");
        }else {
            sb.append(sqTaskInfo.getSqFilterSql());
            sb.append(" and ");
        }
        sb.append(SmartQueryConstant.COLUMN_DATE_NAME);
        sb.append(" = ?");
        sb.append("  ");
        if(!StringUtils.isBlank(sqTaskInfo.getSqSortSql())) {
        	sb.append(sqTaskInfo.getSqSortSql());
        }*/
    	/*String selectSql = sqTaskInfo.getSqSelectSql();
    	String fromSql = sqTaskInfo.getSqFromSql();
    	
    	StringBuffer sb = new StringBuffer();
    	if(StringUtils.isBlank(sqTaskInfo.getSqFilterSql())) {  //没有过滤条件下需要添加where
    		sb.append(" where ");
        }else {
            sb.append(sqTaskInfo.getSqFilterSql());
            sb.append(" and ");
        }
    	 sb.append(SmartQueryConstant.COLUMN_DATE_NAME);
         sb.append(" = ? ");
         String filterSql = sb.toString();
         String sortSql =  sqTaskInfo.getSqSortSql();
         String userAcc = sqTaskInfo.getTaskAutherAcc();
        String sql = sqQueryService.packageSQL(selectSql, fromSql, filterSql,sortSql,userAcc);
        String userAcc = sqTaskInfo.getTaskAutherAcc();
        Integer startDate = convertDateCycToInteger(sqTaskInfo.getDataCyc(), DATE_CYC_FORMAT);*/
        //生成excel
        /*查询对应的查询对象*/
        Map<String,String> excelResult = null;
        SqQueryInfoVO query;
		try {
			query = sqQueryService.querySqQueryInfoById(sqTaskInfo.getTaskId(), SmartQueryConstant.COMMON_FLAG_FALSE);
			excelResult = sqQueryService.createFileByTaskId(sqTaskInfo.getTaskId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			TaskException taskException = new TaskException(e,TaskException.CREATE_FILE_ERROR);
			throw taskException;
		}

        //log.debug("test excelResult",excelResult.toString());
        //保存任务
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        SqFileDataInfo sqFileDataInfo = new SqFileDataInfo();
        sqFileDataInfo.setId(UUID.randomUUID().toString());
        sqFileDataInfo.setFileName(query.getFileName());
        sqFileDataInfo.setCreateType(SmartQueryConstant.QUERY_TASK_TYPE_AUTO);
        sqFileDataInfo.setFileType(excelResult.get("type"));
        sqFileDataInfo.setFilePath(excelResult.get("path"));
        sqFileDataInfo.setCatalogId(sqTaskInfo.getCatalogId());
        sqFileDataInfo.setSqId(sqTaskInfo.getTaskId());
        sqFileDataInfo.setDataCyc(sqTaskInfo.getDataCyc());
        sqFileDataInfo.setExecTime(currentTimestamp);
        sqFileDataInfo.setFileSize(Double.parseDouble(excelResult.get("size")));
        sqFileDataInfo.setStatus(SmartQueryConstant.FILE_STATUS_NORMAL);
        sqFileDataInfo.setDataStoreDate(DateUtils.dateToTimestamp(DateUtils.dateAddDays(currentTimestamp,sqTaskInfo.getDataStoreDt())));
        sqFileDataInfo.setCreatorId(sqTaskInfo.getTaskAutherId());
        sqFileDataInfo.setCreator(sqTaskInfo.getTaskAuther());
        sqFileDataInfo.setCreatorAcc(sqTaskInfo.getTaskAutherAcc());
        sqFileDataInfo.setCreateTime(currentTimestamp);
        sqFileDataInfo.setSort(1l);
        getHibernateTemplate().save(sqFileDataInfo);

        //设置下个周期时间
        Timestamp nextDateCyc = calculateNextDateCyc(sqTaskInfo);
        sqTaskInfo.setDataCyc(nextDateCyc);
        if(!compareTimestamp(nextDateCyc,sqTaskInfo.getInvalidTime())) {
            sqTaskInfo.setTaskStatus(SmartQueryConstant.COMMON_FLAG_FALSE);
        }
        getHibernateTemplate().merge(sqTaskInfo);

        //修改运行状态数据
        recordEndTaskInfo(sqTaskInfo, sqTaskStatusInfo);

        SqQueryInfo sqQueryInfo = getSqQueryInfoDAO().findById(sqTaskInfo.getTaskId());
        long endTime = System.currentTimeMillis();
        StringBuilder content = new StringBuilder();
        content.append(sqQueryInfo.getName());
        content.append("自动任务已创建");
        content.append(sqFileDataInfo.getFileName());
        content.append("数据文件，耗时");
        content.append((endTime - startTime) / 1000);
        content.append("秒");

        User user = new User();
        user.setId(sqTaskInfo.getTaskAutherId());
        //记录运行日志
        logService.saveLog(LogConstant.TYPE_OPERATION, FUNCTION, LogConstant.OPER_CREATE_FILE, content.toString(),user);
    }

    /**
     * 任务开始时状态信息记录
     * @param sqTaskInfo
     */
    private SqTaskStatusInfo recordStartTaskInfo(SqTaskInfo sqTaskInfo) {
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        SqTaskStatusInfo sqTaskStatusInfo = new SqTaskStatusInfo();
        sqTaskStatusInfo.setId(UUID.randomUUID().toString());
        sqTaskStatusInfo.setTaskId(sqTaskInfo.getTaskId());
        sqTaskStatusInfo.setDataCyc(sqTaskInfo.getDataCyc());
        sqTaskStatusInfo.setTaskStatus(SmartQueryConstant.TASK_STATUS_RUNNING);
        sqTaskStatusInfo.setTaskBeginTime(startTime);
        getHibernateTemplate().save(sqTaskStatusInfo);
        return sqTaskStatusInfo;
    }

    /**
     * 任务结束后状态信息记录
     * @param sqTaskInfo
     */
    private void recordEndTaskInfo(SqTaskInfo sqTaskInfo,SqTaskStatusInfo sqTaskStatusInfo) {
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        sqTaskStatusInfo.setDataCycNext(sqTaskInfo.getDataCyc());
        sqTaskStatusInfo.setTaskEndTime(endTime);
        sqTaskStatusInfo.setTaskStatus(SmartQueryConstant.TASK_STATUS_FINDISH);
        BigDecimal taskDur = BigDecimal.valueOf(sqTaskStatusInfo.getTaskEndTime().getTime() - sqTaskStatusInfo.getTaskBeginTime().getTime());
        sqTaskStatusInfo.setTaskDur(taskDur);
        getHibernateTemplate().merge(sqTaskStatusInfo);
    }

    /**
     * 判断第一个时间是否小于或者等于第二个时间
     * @param t1
     * @param t2
     * @return
     */
    private boolean compareTimestamp(Timestamp t1,Timestamp t2) {
        return t1.getTime() <= t2.getTime();
    }

    /**
     * 计算下个周期的时间
     * @param sqTaskInfo
     * @return
     */
    private static Timestamp calculateNextDateCyc(SqTaskInfo sqTaskInfo) {
        Date nextDateCyc;
        Date dateCyc = sqTaskInfo.getDataCyc();
        int lec = sqTaskInfo.getCycLen();
        String cycTyp = sqTaskInfo.getCycTyp();
        if(cycTyp.equals(TackCycType.y.toString())) {
            nextDateCyc = DateUtils.dateAddYear(dateCyc, lec);
        }else if(cycTyp.equals(TackCycType.m.toString())) {
            nextDateCyc = DateUtils.dateAddMonth(dateCyc,lec);
        }else if(cycTyp.equals(TackCycType.w.toString())) {
            nextDateCyc = DateUtils.dateAddDays(dateCyc, 7 * lec);
        }else {
            nextDateCyc = DateUtils.dateAddDays(dateCyc, lec);
        }
       return DateUtils.dateToTimestamp(nextDateCyc);
    }

    /**
     * 将定时任务的当前周期转换成string，以便sql查询
     * @param timestamp
     * @param format
     * @return
     */
    private static Integer convertDateCycToInteger(Timestamp timestamp,String format) {
        String date = DateUtils.dateTimeToString(timestamp, format);
        return Integer.parseInt(date);
    }


    /**
     * 任务运行失败的处理
     * @param sqTaskInfo
     * @param sqTaskStatusInfo
     */
    private void executeFail(SqTaskInfo sqTaskInfo,SqTaskStatusInfo sqTaskStatusInfo,Exception e,String msg) {
        //删除status info信息
//        getSqTaskStatusInfoDAO().delete(sqTaskStatusInfo);
    	
    	//新实现：statusInfo里面的状态改成异常中
    	sqTaskStatusInfo.setTaskStatus(SmartQueryConstant.TASK_STATUS_EXCEPTION);
    	getHibernateTemplate().merge(sqTaskStatusInfo);

        //在异常表里面记录异常信息
        String date = DateUtils.dateTimeToString(sqTaskInfo.getDataCyc(), DATE_CYC_FORMAT);
        sqExceptionService.saveExceptionInfo(e.toString(),1,msg, ModuleType.SQ_TASK,sqTaskInfo.getTaskId(),date);
    }



    public static void main(String[] args) {
       SqTaskInfo sqTaskInfo = new SqTaskInfo();
        sqTaskInfo.setDataCyc(new Timestamp(System.currentTimeMillis()));
        sqTaskInfo.setCycTyp(TackCycType.m.toString());
        sqTaskInfo.setCycLen(1);
        Timestamp next = calculateNextDateCyc(sqTaskInfo);
        System.out.println(convertDateCycToInteger(next,"yyyyMMdd"));
    }
}
