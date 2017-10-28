package com.aspire.birp.modules.common.web.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aspire.bi.common.util.StringUtils;
import com.aspire.bi.common.web.controller.BaseController;
import com.aspire.birp.common.utils.ExportExcelUtils;
import com.aspire.birp.modules.base.constant.BaseConstant;
import com.aspire.birp.modules.common.service.ExportService;

@Controller
@RequestMapping(value = "${adminPath}/common/export")
public class ExportController extends BaseController{

	
	private static Logger log = Logger.getLogger(ExportController.class);
	
	@Autowired
	private ExportService exportService;
	
	@RequestMapping("/exportGridData")
	public void exportGridData(@RequestParam Map<String, Object> params,HttpServletRequest request,HttpServletResponse response){
		try {
			if(params != null){
				/*获取字段对应列表与查询参数*/
				String[] keyList = params.get("keys")==null?null:params.get("keys").toString().split(",");
				String[] headerList = params.get("headers")==null?null:params.get("headers").toString().split(",");
	   			String queryName = params.get("queryName").toString();
	   			params.remove("keyList");
	   			params.remove("headerList");
	   			params.remove("queryName");
				/*处理数据组类型参数*/
				Map<String, Object> parameters = new HashMap<String, Object>();
				Set<String> keys = params.keySet();
				for (String key : keys) {
					if(key.endsWith(BaseConstant.RP_PARAMETER_ARRAY_SUFFIX)){
						String[] array = StringUtils.split(params.get(key).toString(),"|");
						if(array.length == 0) array = null;
						parameters.put(key, array);						
					}else{
						parameters.put(key, params.get(key));
					}
				}		
				
 				List<Map<String, Object>> dataList = exportService.queryList(queryName, parameters);
 				
 				//outputstream def
             	OutputStream os = response.getOutputStream();// 取得输出流   
              	try {
             		String title = params.get("title").toString();
                     String fileName = title+".xlsx";
                    response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
                    response.setHeader("Connection", "close");
    	 			response.setHeader("Content-Type", "application/vnd.ms-excel");
                 	//excel constuct
                	ExportExcelUtils eeu = new ExportExcelUtils();
        			Workbook book = new SXSSFWorkbook(128);
        			eeu.exportExcel(book, 0, title, headerList, dataList, keyList);
         			book.write(os);
				} catch (Exception e) {
					log.error("导出excel失败", e);
				}finally{
					os.flush();
	    			os.close();		
				}
  			}
		} catch (Exception e) {
			log.error("导出excel,查询数据失败", e);
 		}
	}
}
