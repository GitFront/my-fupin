
package com.aspire.birp.modules.dataLabel.config.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.dataLabel.base.constant.DataLabelConstant;
import com.aspire.birp.modules.dataLabel.base.constant.LabelNodeType;
import com.aspire.birp.modules.dataLabel.base.web.DlBaseController;
import com.aspire.birp.modules.dataLabel.config.vo.DmLabelTreeDefVO;

/**  
 * Title: 数据标签配置功能服务控制类 <br>
 * Description: 主要用于处理数据标签的配置功能 <br>
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年4月26日 下午2:40:41
 * @version: V1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/data-label/config")
public class DlConfigController extends DlBaseController {

	private static Logger log = LoggerFactory.getLogger(DlConfigController.class);
	
	/**
	 * 数据标签属性配置功能的跳转
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/label-config.htm")
	public String forwardBindingConfig(@RequestParam(value="id") String id,@RequestParam(value="isParent") String isParent,Model model){	
		DmLabelTreeDefVO vo = getDlConfigService().queryLabelDef(id);
		/*定义参数返回值*/	
		model.addAttribute("treeNode", vo.getId());	
		model.addAttribute("nodeName", vo.getName());
		model.addAttribute("isParent",isParent);
		
		log.info("数据标签属性配置功能页面跳转");
		return "/modules/data_label/label-config";
	}
	
	/**
	 * 数据标签目录的列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tree", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DmLabelTreeDefVO> loadLabelTree(){	

		List<DmLabelTreeDefVO> list = new ArrayList<DmLabelTreeDefVO>();
		try {
   			list = getDlConfigService().queryLabelEditTree();
  		} catch (Exception e) {
			log.error("数据标签目录列表读取操作失败");
 		}		
		return list;
	}
	
	/**
	 * 通过ID查询数据标签信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/query/byid", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	DmLabelTreeDefVO loadLabelDef(@RequestParam String id){	

		DmLabelTreeDefVO vo = new DmLabelTreeDefVO();
		try {
			vo = getDlConfigService().queryLabelDef(id);
  		} catch (Exception e) {
			log.error("通过ID查询数据标签信息操作失败");
 		}		
		return vo;
	}
	
	/**
	 * 重命名保存数据标签信息
	 * @return 是否重命名成功
	 * @author 张健雄
	 */
	@RequestMapping(value = "/rename", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean renameTreeNode(HttpServletRequest request){
		boolean flag = true;
		/*接收的目录保存信息*/
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String fullPath = request.getParameter("fullPath");
		try {
			flag = getDlConfigService().renameNode(id, name, fullPath);
  		} catch (Exception e) {
			log.error("重命名数据标签信息操作失败");
			flag = false;
 		}		
		return flag;
	}
	
	/**
	 * 新增保存节点的简要信息
	 * @return 是否保存成功
	 * @author 张健雄
	 */
	@RequestMapping(value = "/save/node", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean addTreeNode(HttpServletRequest request){
		boolean flag = true;
		/*接收的目录保存信息*/
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String pid = request.getParameter("pid");
		String fullPath = new String(request.getParameter("fullPath"));
		try {
			flag = getDlConfigService().saveTreeNode(id, name, pid, fullPath);
  		} catch (Exception e) {
			log.error("新增节点简要信息的保存操作失败");
			flag = false;
 		}
		return flag;
	}
	
	/**
	 * 删除标签节点信息
	 * @return 是否删除成功
	 * @author 张健雄
	 */
	@RequestMapping(value = "/remove", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean removeLabel(@RequestParam String id){
		boolean flag = true;
		try {
			flag = getDlConfigService().deleteLabelNode(id);
  		} catch (Exception e) {
			log.error("标签节点删除操作失败");
			flag = false;
 		}		
		return flag;
	}
	
	/**
	 * 保存或更新数据标签信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/update", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void updateLabelConfig(@RequestParam Map<String, Object> params,Model model,HttpServletResponse response){
		boolean flag = true;
		DmLabelTreeDefVO label = this.packageLabelInfo(params);
		try {
			flag = getDlConfigService().updateTreeNode(label);
			
			Map<String,Object> retMap = new HashMap<String,Object>();
	        retMap.put("flag", flag);
	        PrintWriter out = response.getWriter();
	        out.write(net.sf.json.JSONObject.fromObject(retMap).toString());
	        //此处如果返回一个List，需要用net.sf.json.JSONArray.fromObject().toString()
	        out.flush();
	        out.close();
			
  		} catch (Exception e) {
			log.error("数据标签信息的保存或更新操作失败",e);
 		}
	}
	
	/**
	 * 封装传入的查询对象信息属性
	 * @param params
	 * @return
	 * @author 张健雄
	 */
	private DmLabelTreeDefVO packageLabelInfo(Map<String, Object> params){
		DmLabelTreeDefVO vo = new DmLabelTreeDefVO();
		String id = params.get("id").toString().trim();
		String name = params.get("name").toString().trim();
		String pid = params.get("pid").toString().trim();		
		String fullPath = params.get("fullPath").toString().trim();
		String type = params.get("type").toString().trim();
		String assoTableId = params.get("assoTableId").toString().trim();
		String assoFieldId = params.get("assoFieldId").toString().trim();	
		String assoDimId = params.get("assoDimId").toString().trim();
		String assoTable = params.get("assoTable").toString().trim();
		String assoField = params.get("assoField").toString().trim();	
		String assoDim = params.get("assoDim").toString().trim();
		String assoRule = params.get("assoRule").toString().trim();
		String topStr = params.get("topNum").toString().trim();
		String sortStr = params.get("sort").toString().trim();
		String active = params.get("active").toString().trim();
		Integer topNum = 0;
		Long sort = 0L;
		if(StringUtils.isNotBlank(topStr))
			topNum = Integer.valueOf(topStr);	
		if(StringUtils.isNotBlank(sortStr))
			sort = Long.valueOf(params.get("sort").toString());

		vo.setId(id);
		vo.setName(name);
		vo.setPid(pid);
		vo.setFullPath(fullPath.substring(0, fullPath.lastIndexOf("/")+1)+name);
		vo.setType(type);
		vo.setSort(sort);
		
		if(LabelNodeType.label.toString().equals(type)){
			vo.setAssoTableId(assoTableId);
			vo.setAssoTable(assoTable);
			vo.setAssoFieldId(assoFieldId);
			vo.setAssoField(assoField);
			vo.setAssoDimId(assoDimId);
			vo.setAssoDim(assoDim);
			vo.setAssoRule(assoRule);
			vo.setTopNum(topNum);
			vo.setActive(active);
		}else{
			vo.setAssoTableId("");
			vo.setAssoTable("");
			vo.setAssoFieldId("");
			vo.setAssoField("");
			vo.setAssoDimId("");
			vo.setAssoDim("");
			vo.setAssoRule("");
			vo.setTopNum(0);
			vo.setActive(DataLabelConstant.COMMON_FLAG_TRUE);
		}
		
		return vo;
	}
	
}


