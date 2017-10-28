package com.aspire.birp.modules.smartQuery.task.web;

import com.aspire.auth.modules.sys.constants.LogConstant;
import com.aspire.auth.modules.sys.entity.Log;
import com.aspire.bi.common.web.bean.easyui.DataGrid;
import com.aspire.birp.common.mapper.JsonMapper;
import com.aspire.birp.modules.smartQuery.query.entity.SqQueryInfo;
import com.aspire.birp.modules.smartQuery.query.vo.SqQueryInfoVO;
import com.aspire.birp.modules.smartQuery.task.service.SqTaskManageService;
import com.aspire.birp.modules.sys.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询任务管理
 * Title: hydc_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 *
 * @author <a href="mailto:suweicheng@aspirecn.com">苏伟城</a>
 * @version 1.0
 * @e-mail: suweicheng@aspirecn.com
 * @creatdate 2015/10/25 13:45
 */
@Controller
@RequestMapping(value = "${adminPath}/smart-query/task/")
public class SqTaskManageController {

    private static Logger log = LoggerFactory.getLogger(SqTaskManageController.class);

    private static final String BASE_PATH = "/modules/smart_query/task/";

    private static final String FUNCTION = "自动任务";

    @Autowired
    private SqTaskManageService sqTaskManageService;

    @Autowired
    private LogService logService;

    @RequestMapping("taskpage.do")
    public String page() {
        return BASE_PATH + "taskPage";
    }
    
    @RequestMapping("tasklist.htm")
    public String tasklisthtml() {
    	 return BASE_PATH + "tasklist";
    }

    @RequestMapping("tasklist.do")
    @ResponseBody
    public String tasklist(Integer page,Integer rows,String user,String type,String name) {
        Map<String,Object> result = sqTaskManageService.queryTaskList(page,rows,user,type,name);
        return objectToJSON(result);
    }

    /**
     * 启动或者暂停任务
     * @param taskId
     * @return
     */
    @RequestMapping(value = "startOrStopTask.do", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String,Object> startOrStopTask(@RequestParam("index") String taskId) {
        Map<String,Object> result = new HashMap<String, Object>(2);
        try{
            String log = sqTaskManageService.startOrStopTask(taskId);
            result.put("success", true);
            if(log.contains("启动")) {
                logService.saveLog(LogConstant.TYPE_OPERATION,FUNCTION,LogConstant.OPER_START,log);
            }else{
                logService.saveLog(LogConstant.TYPE_OPERATION,FUNCTION,LogConstant.OPER_STOP,log);
            }
        }catch (Exception e) {
            log.error("启动/暂停任务失败", e);
            result.put("success", false);
            result.put("info", "操作失败");
        }
        return result;
    }

    @RequestMapping(value = "deleteTask.do", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String,Object> deleteTask(@RequestParam("index") String taskId) {
        Map<String,Object> result = new HashMap<String, Object>(2);
        try{
            String log = sqTaskManageService.deleteTask(taskId);
            result.put("success", true);
            logService.saveLog(LogConstant.TYPE_OPERATION,FUNCTION,LogConstant.OPER_DELETE,log);
        }catch (Exception e) {
            log.error("删除任务失败", e);
            result.put("success", false);
            result.put("info", "删除失败");
        }
        return result;
    }

    /**
     * 页面创建人查询列表
     * @return
     */
    @RequestMapping("queryCreator.do")
    @ResponseBody
    public String queryCreator() {
    	List<Map<String, Object>> userList = sqTaskManageService.queryCreators();
        return objectToJSON(userList);
    }

    /**
     * 将对象转成json数据
     * @param object
     * @return
     */
    private String objectToJSON(Object object) {
        return JsonMapper.getInstance().toJson(object);
    }

    /**
     * 查询任务异常日志
     * @param taskId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("queryTaskException.do")
    @ResponseBody
    public String queryTaskException(@RequestParam("index") String taskId,int page, int rows) {
        DataGrid dataGrid = sqTaskManageService.queryTaskExceptions(taskId,page,rows);
        Map<String,Object> result = new HashMap<String,Object>(2);
        result.put("total", dataGrid.getTotal());
        result.put("rows", dataGrid.getRows());
        return objectToJSON(result);
    }

    /**
     * 查询任务详细信息
     * @param id
     * @return
     */
    @RequestMapping("queryInfo.do")
    @ResponseBody
    public Map<String,Object> queryInfo(@RequestParam("index") String id) {
        return sqTaskManageService.queryInfoById(id);
    }

    @RequestMapping("updateSqQueryInfo.do")
    @ResponseBody
    public String updateSqQueryInfo(SqQueryInfoVO sqQueryInfoVO) {
        Map<String,Object> result = new HashMap<String, Object>(2);
        boolean flag = false;
        try {
            String log = sqTaskManageService.updateSqQueryInfo(sqQueryInfoVO);
            flag = true;
            logService.saveLog(LogConstant.TYPE_OPERATION,FUNCTION,LogConstant.OPER_MODIFY,log);
        } catch (Exception e) {
           log.error("更新任务失败",e);
        }
        result.put("flag",flag);
        return objectToJSON(result);
    }
}
