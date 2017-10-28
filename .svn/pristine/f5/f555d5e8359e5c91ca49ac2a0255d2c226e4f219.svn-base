package com.aspire.birp.modules.smartQuery.market.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.smartQuery.base.constant.OptionType;
import com.aspire.birp.modules.smartQuery.base.web.SqBaseController;
import com.aspire.birp.modules.smartQuery.market.vo.SqFileCatalogTree;
import com.aspire.birp.modules.smartQuery.market.vo.SqFileDataInfoVO;
import com.aspire.birp.modules.smartQuery.market.vo.SqFileTimeTree;

/**
 * Title: 数据超市功能服务控制类 <br>
 * Description: 主要用于处理数据超市功能的请求信息 <br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2015年10月27日 下午14:45:03 <br>
 * 
 */

@Controller
@RequestMapping(value = "${adminPath}/smart-query/market")
public class SqDataMarketController extends SqBaseController {

	private static Logger log = LoggerFactory
			.getLogger(SqDataMarketController.class);

	/**
	 * 查询文件目录树数据信息
	 * 
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/catalogtree/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqFileCatalogTree> loadFileCatalogTree() {

		List<SqFileCatalogTree> list = new ArrayList<SqFileCatalogTree>();
		try {
			list = getSqDataMarketService().queryFileCatalogTree();
		} catch (Exception e) {
			log.error("查询文件目录树数据信息失败", e);
		}
		return list;
	}

	/**
	 * 查询文件时间树数据信息
	 * 
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/timetree/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqFileTimeTree> loadFileTimeTree() {

		List<SqFileTimeTree> list = new ArrayList<SqFileTimeTree>();
		try {
			list = getSqDataMarketService().queryFileTimeTree();
		} catch (Exception e) {
			log.error("查询文件时间树数据信息失败", e);
		}
		return list;
	}

	/**
	 * 查询数据文件的列表信息
	 * 
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/file/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqFileDataInfoVO> loadFileList() {

		List<SqFileDataInfoVO> list = new ArrayList<SqFileDataInfoVO>();
		try {
			list = getSqDataMarketService().queryFileList();
		} catch (Exception e) {
			log.error("查询数据文件的列表信息失败", e);
		}
		return list;
	}

	/**
	 * 查询数据文件的列表信息(通过个人目录过滤)
	 * 
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/file/list/catalog", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqFileDataInfoVO> loadFileListForCatalog(
			@RequestParam(value = "catalogId") String catalogId) {

		List<SqFileDataInfoVO> list = new ArrayList<SqFileDataInfoVO>();
		try {
			list = getSqDataMarketService().queryFileListByCatalog(catalogId);
		} catch (Exception e) {
			log.error("通过个人目录查询数据文件的列表信息失败", e);
		}
		return list;
	}

	/**
	 * 查询数据文件的列表信息(通过创建时间)
	 * 
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/file/list/time", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqFileDataInfoVO> loadFileListForTime(
			@RequestParam(value = "time") String time,
			@RequestParam(value = "timeType") String timeType) {

		List<SqFileDataInfoVO> list = new ArrayList<SqFileDataInfoVO>();
		try {
			list = getSqDataMarketService().queryFileListByTime(time, timeType);
		} catch (Exception e) {
			log.error("通过创建时间查询数据文件的列表信息失败", e);
		}
		return list;
	}

	/**
	 * 查询数据文件的列表信息(通过自动任务)
	 * 
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/file/list/task", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqFileDataInfoVO> loadFileListForTask(
			@RequestParam(value = "taskId") String taskId) {

		List<SqFileDataInfoVO> list = new ArrayList<SqFileDataInfoVO>();
		try {
			list = getSqDataMarketService().queryFileListByTask(taskId);
		} catch (Exception e) {
			log.error("通过自动任务查询数据文件的列表信息失败", e);
		}
		return list;
	}

	/**
	 * 通过数据文件ID导出数据列表
	 * 
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/file/download", produces = "application/json;charset=UTF-8")
	public void dataExport(HttpServletRequest request,
			HttpServletResponse response) {

		/* 获取请求参数信息 */
		String fileId = request.getParameter("fileId");
		try {
			/* 获取文件信息 */
			SqFileDataInfoVO file = this.getSqDataMarketService()
					.queryFileInfoById(fileId);
			String filePath = file.getFilePath();
			/* 记录文件下载日志 */
			Map<String, String> lp = new HashMap<String, String>();
			lp.put("file", file.getFileName());
			getSqDataMarketService().marketLogger(
					OptionType.DataMarket_download, lp);
			com.aspire.birp.modules.base.utils.Utils.download(request,
					response, filePath);

		} catch (Exception e) {
			log.error("数据文件下载失败", e);
		}
	}

	/**
	 * 查询已分享的用户列表信息
	 * 
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/userSelect/list", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<String> loadUserSelectd(String fileId) {

		List<String> list = new ArrayList<String>();
		try {
			list = this.getSqDataMarketService().queryUserSelected(fileId);
		} catch (Exception e) {
			log.error("查询已分享的用户列表信息失败", e);
		}
		return list;
	}

	/**
	 * 通过数据文件ID校验数据文件是否有效
	 * 
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/file/check", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String dataCheck(@RequestParam(value = "fileId") String fileId) {
		boolean flag = true;
		try {
			/* 获取文件信息 */
			flag = this.getSqDataMarketService().checkFileInfoById(fileId);

		} catch (Exception e) {
			log.error("校验数据文件失败", e);
			flag = false;
		}
		if(!flag) return null;
		return "true";
	}
	
	/**
	 * 通过数据文件ID删除数据文件
	 * 
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/file/delete", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean dataDelete(@RequestParam(value = "fileId") String fileId) {
		boolean flag = true;
		try {
			/* 获取文件信息 */
			flag = this.getSqDataMarketService().deleteFileInfoById(fileId);

		} catch (Exception e) {
			log.error("数据文件删除失败", e);
			flag = false;
		}
		return flag;
	}

	/**
	 * 保存文件分享信息
	 * @param fileId
	 * @param userIds
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("fileshare/save")
	public @ResponseBody
	boolean saveFileShare(String fileId, String[] userIds) {
		boolean success = false;
		try {
			success = this.getSqDataMarketService().saveFileShare(fileId, userIds);
		} catch (Exception e) {
			log.error("保存文件分享信息失败", e);
		}
		return success;
	}

}
