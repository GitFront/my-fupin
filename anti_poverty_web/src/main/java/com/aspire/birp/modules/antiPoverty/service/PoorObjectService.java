package com.aspire.birp.modules.antiPoverty.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.bi.common.config.Global;
import com.aspire.birp.common.excel.CSVUtils;
import com.aspire.birp.common.utils.DownLoadFileUtils;
import com.aspire.birp.modules.antiPoverty.dao.PoorChangeDao;
import com.aspire.birp.modules.antiPoverty.utils.Constants;
import com.aspire.birp.modules.base.vo.ProgressObject;

import net.sf.json.JSONObject;

@Service
public class PoorObjectService extends DataMonitorService{
	
	private static Logger log = LoggerFactory.getLogger(PoorObjectService.class);
	
	@Autowired
	private PoorChangeDao pcDao;

	public String getDataMonitorHeadDownloadTableList(HttpServletRequest request, String id, String level, String year,
			String scope) {
		log.info("下载列表===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			getParamsPool().put("queryPopType", params);
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			JSONObject data = new JSONObject();
			JSONObject download_list = new JSONObject();
			download_list.put("title", "统计表");
			List<JSONObject> urlList = new ArrayList<JSONObject>();
			JSONObject download = new JSONObject();
			download.put("name", "表1：贫困人口类型表");
			download.put("export_url", request.getContextPath()+"/antiPoverty/poorObject/doExport.do?export_task_id=queryPopType");
			urlList.add(download);
			JSONObject download1 = new JSONObject();
			download1.put("name", "表2：贫困人口明细表");
			getParamsPool().put("queryPopDetail", params);
			download1.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=queryPopDetail");
			urlList.add(download1);
			JSONObject download2 = new JSONObject();
			download2.put("name", "表3：贫困户数据汇总表");
			getParamsPool().put("queryPopDataCount", params);
			download2.put("export_url", request.getContextPath()+"/antiPoverty/poorObject/doExport.do?export_task_id=queryPopDataCount");
			urlList.add(download2);
			download_list.put("list", urlList);
			data.put("download_list", download_list);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}

	public String doExport(String export_task_id) {
		JSONObject json = new JSONObject();
		try{
			ProgressObject p = new ProgressObject(1, 0, 0,false);
			if(getLoadPool().containsKey(export_task_id))
				getLoadPool().remove(export_task_id);
			getLoadPool().put(export_task_id, p);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			data.put("export_task_id", export_task_id);
			json.put("data", data);
			getTaskExecutor().execute(new Thread() {  
	            public void run() { 
	            	exportExcelData(export_task_id);
	            }

	        });  
			 
		}catch(Exception e){
			e.printStackTrace();
			log.error("文件操作异常信息："+e.getMessage());
			json.put("code", 0);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	private void exportExcelData(String export_task_id) {
		try{
			Map<String, Object> params =(Map<String, Object>) getParamsPool().get(export_task_id);
			Map<String, Object> context = new HashMap<String, Object>();
		    String title = "";
		    String templatePath = "";
			List<Map<String,Object>>  tyList = null;
			Map<String, Object> tyListCount = null;
			Long romNum = System.currentTimeMillis();
			String path =Global.getConfig("temp.filePath");
			if("queryPopType".equals(export_task_id)){
				title = "贫困人口类型表-总计";
				templatePath = Global.getConfig("template.filePath")+"poorType.xls";
				params.put("title", "贫困人口类型表"+romNum);
				tyList = pcDao.queryPopType(params);
				tyListCount = pcDao.queryPopTypeCount(params);
				if(null != tyListCount)
					context.putAll(tyListCount);
				context.put("poorType", tyList);
				//System.out.println("==============poorType");
				params.put("title1", path+title+romNum+".xls");
				DownLoadFileUtils.createExcel(templatePath, context, path+title+romNum+".xls");
				
				params.put("poverty", 1);
				title = "贫困人口类型表-贫困村";
				tyList = pcDao.queryPopType(params);
				tyListCount = pcDao.queryPopTypeCount(params);
				if(null != tyListCount)
					context.putAll(tyListCount);
				context.put("poorType", tyList);
				//System.out.println("==============poorType");
				params.put("title2", path+title+romNum+".xls");
				DownLoadFileUtils.createExcel(templatePath, context, path+title+romNum+".xls");
				
				params.put("poverty", 0);
				title = "贫困人口类型表-分散村";
				tyList = pcDao.queryPopType(params);
				tyListCount = pcDao.queryPopTypeCount(params);
				if(null != tyListCount)
					context.putAll(tyListCount);
				context.put("poorType", tyList);
				//System.out.println("==============poorType");
				params.put("title3", path+title+romNum+".xls");
				DownLoadFileUtils.createExcel(templatePath, context, path+title+romNum+".xls");
			}else if("queryPopDataCount".equals(export_task_id)){
				title = "贫困人口汇总表-总计";
				templatePath = Global.getConfig("template.filePath")+"queryPopDataCount.xls";
				params.put("title", "贫困人口汇总表"+romNum);
				params.put("title1", title);
				tyList = pcDao.queryPopDataCount(params);
				context.put("popDataCount", tyList);
				//System.out.println("==============queryPopDataCount");
				params.put("title1", path+title+romNum+".xls");
				DownLoadFileUtils.createExcel(templatePath, context, path+title+romNum+".xls");
				
				title = "贫困人口汇总表-贫困村";
				params.put("poverty", 1);
				params.put("title2", title);
				tyList = pcDao.queryPopDataCount(params);
				context.put("popDataCount", tyList);
				//System.out.println("==============queryPopDataCount");
				params.put("title2", path+title+romNum+".xls");
				DownLoadFileUtils.createExcel(templatePath, context, path+title+romNum+".xls");
				
				title = "贫困人口汇总表-分散村";
				params.put("poverty", 0);
				params.put("title3", title);
				tyList = pcDao.queryPopDataCount(params);
				context.put("popDataCount", tyList);
				//System.out.println("==============queryPopDataCount");
				params.put("title3", path+title+romNum+".xls");
				DownLoadFileUtils.createExcel(templatePath, context, path+title+romNum+".xls");
			}
			
			ProgressObject p = new ProgressObject(1, 0, 0,true);
			if(getLoadPool().containsKey(export_task_id))
				getLoadPool().remove(export_task_id);
			getLoadPool().put(export_task_id, p);

		}catch(Exception e){
			e.printStackTrace();
			log.error("文件操作异常信息："+e.getMessage());			
		}

		
	}

	public void downloadFile(HttpServletResponse response, String filePath, String fileName, String export_task_id) {
		log.info("导出到文件后下载=====");
		try {
			Map<String, Object> params =(Map<String, Object>) getParamsPool().get(export_task_id);
			List<File> fl = new ArrayList<File>();
			String name = "";
			for(int i=0; i<3; i++){
				name = (String) params.get("title"+(i+1));
				fl.add(new File(name));
			}
			String zipName = (String)params.get("title");
			File zipFile = new File(Global.getConfig("temp.filePath")+zipName+".zip");
			DownLoadFileUtils.zipFiles(fl, zipFile);
			DownLoadFileUtils.downFile(response, Global.getConfig("temp.filePath"), zipName+".zip");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导出失败异常信息："+e.getMessage());
		}
		
	}

}
