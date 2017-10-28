package com.aspire.birp.modules.antiPoverty.utils;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aspire.birp.modules.antiPoverty.dao.PoorAnalyseDao;
import com.aspire.birp.modules.antiPoverty.dao.PoorChangeDao;
import com.aspire.birp.modules.antiPoverty.service.DataMonitorService;

import net.sf.json.JSONObject;


public class AnalyseThread implements Runnable {  
	private static Logger log = LoggerFactory.getLogger(AnalyseThread.class);
	private WeakReference<DataMonitorService> weakReference;
	//private DataMonitorService service;
    private Map<String, Object> params;  
    private String flag=null;
    private CountDownLatch latch;  
    List<JSONObject> configList;
    List<Map<String,Object>> configList1;
    List<Map<String,Object>> configList2;
    Map<String, Object> tableListCol; 
    public AnalyseThread(Map<String, Object> params,String flag,List<JSONObject> configList,CountDownLatch latch,DataMonitorService service){  
        this.params=params;  
        this.flag = flag;
        this.latch=latch;
        this.configList = configList;
        this.weakReference = new WeakReference(service);
    }  
    
    public AnalyseThread(Map<String, Object> params,String flag,List<Map<String,Object>> configList,List<Map<String,Object>> configList1,CountDownLatch latch,DataMonitorService service){  
        this.params=params;  
        this.flag = flag;
        this.latch=latch;
        this.configList1 = configList;
        this.configList2 = configList1;
        this.weakReference = new WeakReference(service);
    } 
    
    public AnalyseThread(Map<String, Object> params,String flag,Map<String, Object> tableListCol,CountDownLatch latch,DataMonitorService service){  
        this.params=params;  
        this.flag = flag;
        this.latch=latch;
        this.tableListCol = tableListCol;
        this.weakReference = new WeakReference(service);
    } 
	@Override
	public void run() {
		try { 
			//List<JSONObject> configList = new ArrayList<JSONObject>();
			PoorAnalyseDao poorAnalyseDao = weakReference.get().getPoorAnalyseDao();
	    	List<Map<String,Object>> mapList = null;
	    	if("poorReason".equals(flag)){
	    		mapList = poorAnalyseDao.queryPoorReason(params);
				for(int i=0;i<mapList.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", mapList.get(i).get("S_NAME"));
					config.put("value", mapList.get(i).get("S_VALUE"));
					configList.add(config);
				}
	    	}else if("poorChange".equals(flag)){
	    		PoorChangeDao pcDao = weakReference.get().getPcDao();
	    		params.put("IF_YEAR_POOR", 1);
	    		configList1.addAll(pcDao.queryPoorChangeChart(params));
				params.put("IF_YEAR_POOR", 0);
				configList2.addAll(pcDao.queryPoorChangeChart(params));
	    	}else if("poorChangetal".equals(flag)){
	    		PoorChangeDao pcDao = weakReference.get().getPcDao();
	    		configList1.addAll(pcDao.queryThePoorTrendAllTable(params));
	    	}else if("poorChangecol".equals(flag)){
	    		PoorChangeDao pcDao = weakReference.get().getPcDao();
	    		String level = params.get("level") == null ? "" :  params.get("level").toString();
	    		if(!"country".equals(level)){
	    			tableListCol.putAll(pcDao.queryThePoorTrendAllTableC(params));//合计
				}
	    	}else{
	    		if("pab".equals(flag)){
	    			mapList = poorAnalyseDao.queryPoorAttribute(params);
	    		}else if("pic".equals(flag)){
	    			mapList = poorAnalyseDao.queryPoorIncome(params);
	    		}else if("pag".equals(flag)){
	    			mapList = poorAnalyseDao.queryPoorAge(params);
	    		}else if("psx".equals(flag)){
	    			mapList = poorAnalyseDao.queryPoorSex(params);
	    		}else if("pnt".equals(flag)){
	    			mapList = poorAnalyseDao.queryPoorNationality(params);
	    		}else if("ppc".equals(flag)){
	    			mapList = poorAnalyseDao.queryPoorProtical(params);
	    		}else if("pea".equals(flag)){
	    			mapList = poorAnalyseDao.queryPoorEducation(params);
	    		}else if("phs".equals(flag)){
	    			mapList = poorAnalyseDao.queryPoorHealthStatus(params);
	    		}else if("pls".equals(flag)){
	    			mapList = poorAnalyseDao.queryPoorLaborStatus(params);
	    		}else if("pws".equals(flag)){
	    			mapList = poorAnalyseDao.queryPoorWorkStatus(params);
	    		}
	    		for(int i=0;i<mapList.size();i++){
	    			JSONObject config = new JSONObject();
	    			config.put("name", mapList.get(i).get("S_NAME"));
	    			config.put("value", mapList.get(i).get("S_VALUE"));
	    			configList.add(config);
	    		}
	    		if(configList.size() < 1){
	    			JSONObject config = new JSONObject();
	    			config.put("name", "其他");
	    			config.put("value", "0");
	    			configList.add(config);
	    		}
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("AnalyseThread error: "+flag+" "+e);
			e.printStackTrace();
		}finally{
			latch.countDown();
		}
		// TODO Auto-generated method stub
		
	}  
	
	
}  
