
package com.aspire.birp.modules.smartQuery.query.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aspire.birp.common.excel.CSVUtils;
import com.aspire.birp.modules.dataLabel.label.service.impl.DlUserAnalysisServiceImpl;
import com.aspire.birp.modules.dataLabel.label.web.DlUserAnalysisController;
import com.aspire.birp.modules.smartQuery.base.constant.FileProperties;
import com.aspire.birp.modules.smartQuery.query.service.SqQueryService;
import com.aspire.birp.modules.smartQuery.query.web.SqQueryController;

/**  
 * @Title: 智能查询定时器任务类
 * @Description: 主要用于定义定时器任务执行情况
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年10月14日 下午3:11:27
 * @version: V1.0
 */
@Component
public class SqTimerTaskImpl implements SqTimerTask {


	private static Logger log = LoggerFactory.getLogger(SqTimerTaskImpl.class);
	
	/*智能查询管理服务类*/
	@Autowired
	private SqQueryService sqQueryService;
	

	public SqQueryService getSqQueryService() {
		return sqQueryService;
	}

	public void setSqQueryService(SqQueryService sqQueryService) {
		this.sqQueryService = sqQueryService;
	}
	
	/*每天凌晨两点对智能查询的无效数据清理操作*/
	//@Scheduled(cron="0 0 2 * * ? ")
    public void noAvailableDataCleaner(){
         log.debug("执行智能查询临时数据的清除工作");
         this.sqQueryService.cleanTemporaryQuery();
         /*清空临时文件的缓存信息*/
         SqQueryController.clearFileMap();
         DlUserAnalysisController.clearFileMap();
         DlUserAnalysisServiceImpl.clearSqlPool();
         DlUserAnalysisServiceImpl.clearLoadPool();
        // OperationLogServiceImpl.clearLoadPool();
       //  OperationLogController.clearFileMap();
         
         /*清空服务器临时文件信息*/
         CSVUtils.deleteFiles(FileProperties.getTempFilePath());
         
         log.debug("扫描并标识已丢失或已过期的数据文件");
         this.sqQueryService.markLostfiles();
         
    }
}


