
package com.aspire.birp.modules.dataLabel.label.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.common.excel.CSVUtils;
import com.aspire.birp.modules.base.vo.ProgressObject;
import com.aspire.birp.modules.dataLabel.base.constant.DataLabelConstant;
import com.aspire.birp.modules.dataLabel.base.web.DlBaseController;
import com.aspire.birp.modules.dataLabel.label.vo.LabelTreeObject;

/**  
 * Title: 数据标签用户分析功能服务控制类 <br>
 * Description: 主要用于处理数据标签的用户分析功能 <br>
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年5月5日 下午2:40:41
 * @version: V1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/data-label/user-analysis")
public class DlUserAnalysisController extends DlBaseController {

	private static Logger log = LoggerFactory.getLogger(DlUserAnalysisController.class);
	
	/**
	 * 临时文件信息储存信息
	 */
	private static Map<String,String> fileMap;
	
	
	
	public DlUserAnalysisController() {
		super();
		if(fileMap == null){
			fileMap = new HashMap<String, String>();
		}
	}

	public static void clearFileMap() {
		log.info("清空临时文件储存信息");
		DlUserAnalysisController.fileMap.clear();
	}
	
	/**
	 * 数据标签树信息读取
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tree", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<LabelTreeObject> loadLabelTree(){	

		List<LabelTreeObject> list = new ArrayList<LabelTreeObject>();
		try {
   			list = getDlUserAnalysisService().queryLabelTree();
  		} catch (Exception e) {
			log.error("数据标签树信息读取操作失败",e);
 		}		
		return list;
	}
	
	/**
	 * 数据标签树的标签分层属性
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tree/layer", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<LabelTreeObject> loadLabelTreeForLayer(@RequestParam(defaultValue="")String id){	

		List<LabelTreeObject> list = new ArrayList<LabelTreeObject>();
		try {
   			list = getDlUserAnalysisService().queryDimLabel(id);
  		} catch (Exception e) {
			log.error("数据标签树的标签分层属性读取操作失败",e);
 		}		
		return list;
	}
	
	/**
	 * 查询数据表的可用历史周期
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/period/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<Map<String,String>> loadPeriodList(@RequestParam(value="tableName") String tableName){	

		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
   			list = getDlUserAnalysisService().queryPeriodList(tableName);
  		} catch (Exception e) {
			log.error("查询数据表的可用历史周期操作失败",e);
 		}		
		return list;
	}
	
	/**
	 * 查询标签树数据表的默认历史周期
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/period/default", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<Map<String,String>> loadDefaultPeriod(){	

		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
   			list = getDlUserAnalysisService().queryLastPeriod();
  		} catch (Exception e) {
			log.error("查询标签树数据表的默认历史周期操作失败",e);
 		}		
		return list;
	}
	
	/**
	 * 对输出结果的列属性进行语义转换
	 * @param value 维表值 
	 * @param label 标签属性ID
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/column/formatter", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String formatterColumn(@RequestParam(value="value") String value,@RequestParam(value="label") String label){	
		String dimValue = "";
		
		try {
			dimValue = getDlUserAnalysisService().queryDimValue(value, label);
  		} catch (Exception e) {
			log.error("对输出结果的列属性进行语义转换操作失败");
 		}
		return dimValue;
	}
	
	
	private List<String> labelIds;
	private List<String> labelTimes;
	private List<String[]> labelValues;
	
	/**
	 * 通过查询标签信息查询数据列表
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/datalist", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Map<String, Object> loadDataList(@RequestParam Map<String, Object> params){	
		
		Map<String, Object> rs = new HashMap<String, Object>();
		
		Object labels = params.get("labels");
		if(labels != null){
			packageInLabel(params);
		}else{
			labelIds = null;
			labelTimes = null;
			labelValues = null;
		}
		
		String searchKey = params.get("searchKey").toString().trim();
		String searchNum = params.get("userNum").toString().trim();
		Integer page =  Integer.parseInt(params.get("page").toString().trim());
		Integer rows = Integer.parseInt(params.get("rows").toString().trim());
		try {
			rs = getDlUserAnalysisService().queryLabelData(searchKey,labelIds, labelValues, labelTimes,searchNum, page, rows);
  		} catch (Exception e) {
			log.error("通过查询标签信息查询数据列表操作失败！",e);
 		}		
		return rs;
	}
	
	
	/**
	 * 封装传入的查询标签对象信息属性
	 * @param params
	 * @return
	 * @author 张健雄
	 */
	private void packageInLabel(Map<String, Object> params){
		
		
		String labels = params.get("labels").toString().trim();
		String times = params.get("times").toString().trim();
		String values = params.get("values").toString().trim();
		
		labelIds = new ArrayList<String>();
		String[] labelArr = com.aspire.bi.common.util.StringUtils.split(labels, ",");
		for (int i = 0; i < labelArr.length; i++) {
			labelIds.add(labelArr[i]);
		}
		
		labelTimes = new ArrayList<String>();
		String[] timeArr = com.aspire.bi.common.util.StringUtils.split(times, ",");
		for (int i = 0; i < timeArr.length; i++) {
			labelTimes.add(timeArr[i]);
		}
		
		labelValues = new ArrayList<String[]>();
		String[] valuesArr = com.aspire.bi.common.util.StringUtils.split(values, ",");
		for (int i = 0; i < valuesArr.length; i++) {
			String[] vs = com.aspire.bi.common.util.StringUtils.split(valuesArr[i],"|");
			String allID = DataLabelConstant.LABEL_DIM_ALL_PREFIX + labelArr[i];
			List<String> vl = new ArrayList<String>();
			for (int j = 0; j < vs.length; j++) {
				if(allID.equals(vs[j]))
					continue;
				vl.add(vs[j]);
			}
			if(vl.isEmpty()){
				labelValues.add(new String[0]);
			}else{
				labelValues.add(vl.toArray(new String[1]));
			}				
		}
	}
	
	/**
	 * 文件导出前，通过查询库标识码，生成临时文件
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/export/temp", produces = "application/json;charset=UTF-8")
	public @ResponseBody
		Map<String, Object>  createTempData(@RequestParam String searchKey,@RequestParam int total){	

		boolean flag = true;
		
		try {
			/*生成临时数据*/
			Map<String,String> info = getDlUserAnalysisService().saveTempForThread(searchKey,total);
			/*Map<String,String> info = getDlUserAnalysisService().saveTempData(searchKey,total);*/
			String filePath = info.get("path");
			String fileName = info.get("name");
			if(StringUtils.isBlank(filePath) || StringUtils.isBlank(fileName)){
				flag = false;
			}else{
				fileMap.put(searchKey, fileName + "|" + filePath);
				flag = true;
			}
  		} catch (Exception e) {
			log.error("生成临时csv文件失败",e);
			flag = false;
 		}
		
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("flag", flag);
		
		return rs;
	}
	
	/**
	 * 通过查询配置信息，导出数据列表
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/export", produces = "application/json;charset=UTF-8")
	public void dataExport(@RequestParam String fileId,HttpServletResponse response){

		try {
			/*获取文件路径*/
			String info = fileMap.get(fileId);
			if(StringUtils.isBlank(info)) 
				throw new RuntimeException("找不到临时文件生成路径，数据导出前请先调用/export/temp生成临时文件！");
			String[] infos = StringUtils.split(info, "|");
			String fileName = infos[0];
			String filePath = infos[1];
			
			CSVUtils.exportFile(response, filePath, fileName);
			
  		} catch (Exception e) {
			log.error("cvs数据导出操作失败",e);
 		}		
	}
	
	/**
	 * 通过查询KEY定位文件导出的进度信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/export/progress", produces = "application/json;charset=UTF-8")
	public @ResponseBody 
		ProgressObject loadProgress(@RequestParam String searchKey){
		
		ProgressObject p = null;		
		try {
			p = this.getDlUserAnalysisService().queryLoadStatus(searchKey);	
  		} catch (Exception e) {
			log.error("读取进度信息操作失败",e);
 		}	

		return p;
	}
	
}


