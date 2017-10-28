package com.aspire.birp.modules.antiPoverty.timeTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aspire.bi.common.config.Global;
import com.aspire.birp.common.excel.CSVUtils;
import com.aspire.birp.modules.antiPoverty.service.DataMonitorService;
import com.aspire.birp.modules.antiPoverty.service.NewDateService;
import com.aspire.birp.modules.antiPoverty.utils.Constants;
import com.aspire.birp.modules.common.service.PovertyGeometryService;
/**
 * 定时任务 每天早上6点取一次最新统计日期
 * */
@Component
public class queryDateTask {
	private static Logger log = Logger.getLogger(queryDateTask.class);
	@Autowired
	private NewDateService newDateService;
	
	@Autowired
    private DataMonitorService dataMonitorService;
	
	@Scheduled(cron="0 */10 * * * ?")
	public void getdateContants(){
		newDateService.refreshDateContants();
		Constants.dateContants=NewDateService.getDateMapCostants();
		log.error("定时器启动成功：更新到最新统计时间,10分钟更新一次");
		System.out.println("定时器启动成功：更新到最新统计时间");
	}
	@Autowired
	private PovertyGeometryService  povertyGeometryService;
	@Scheduled(cron="0 0 6 * * ?")
	public void getMapdate(){
		povertyGeometryService.queryIndexMap();
		log.error("定时器启动成功：更新首页地图数据，每天6点更新一次");
		System.out.println("定时器启动成功：更新首页地图数据");
	}
	
	@SuppressWarnings("static-access")
	@Scheduled(cron="0 0 6 * * ?")
	public void cleanTempFiles(){
		CSVUtils.deleteFiles(Global.getConfig("temp.filePath"));
		log.error("定时器启动成功：导出的临时文件夹已经清空");
		System.out.println("定时器启动成功：导出的临时文件夹已经清空");
		dataMonitorService.clearLoadPool();
		dataMonitorService.clearParamsdPool();
		log.error("定时器启动成功：导出的参数缓存已经清空");
		System.out.println("定时器启动成功：导出的参数缓存已经清空");
	}
}
