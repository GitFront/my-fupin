
package com.aspire.birp.modules.base.thread;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.aspire.birp.common.excel.CSVUtils;
import com.aspire.birp.modules.base.vo.ProgressObject;

/**  
 * @Title: 多线程文件写入服务类
 * @Description: java多线程数据文件读写操作
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年5月20日 下午3:29:16
 * @version: V1.0
 */
public class FileWriteTaskThread implements Runnable {

	/*数据查询结果*/
	private List<Map<String, Object>> resultList;
	/*数据映射关系集合*/
	private LinkedHashMap<String, String> mapper;
	/*已创建的csv文件*/
	private File csv;
	/*数据查询中唯一标识*/
	private String searchKey;
	/*导出文件进度缓存库*/
	private Map<String,ProgressObject> loadPool;
	
	
	
	 public FileWriteTaskThread(List<Map<String, Object>> resultList,LinkedHashMap<String, String> mapper,
			 File csv,String searchKey,Map<String,ProgressObject> loadPool) {
		 this.resultList = resultList;
		 this.mapper = mapper;
		 this.csv = csv;
		 this.searchKey = searchKey;
		 this.loadPool = loadPool;
	 }
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("****************将所有子线程统计的最终的结果保存到文件中 FILE *******************");
		
		/*写入数据文件*/
		CSVUtils.appendCSV(resultList, mapper, csv);
		
		/*设置完成进度*/
		loadPool.get(searchKey).setFinish(true);
		loadPool.get(searchKey).setProgress(100);
		
		/*taskExecutor.shutdown();*/

	}

}


