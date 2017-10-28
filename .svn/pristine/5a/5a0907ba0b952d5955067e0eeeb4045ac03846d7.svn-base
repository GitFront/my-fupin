
package com.aspire.birp.modules.antiPoverty.utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.aspire.birp.modules.antiPoverty.service.DataMonitorService;
import com.aspire.birp.modules.base.vo.ProgressObject;

/**  
 * @Title: 多线程查询数据信息
 * @Description: 主要用于大数据量多线程查询数据集合
 */
public class ReadThread implements Runnable {
	/*数据查询DAO类*/
	private DataMonitorService service;
	/*数据字段个数*/
	private int contentSize;
	/*数据序号*/
	private int index;
	/*数据分页码数*/
	private int page;
	/*数据查询结果*/
	private List<Map<String, Object>> resultList;
	/*数据查询参数*/
	private Map<String,Object> parameters;
	/*分页查询的页总条数*/
	private Integer pageSize;
	/*数据查询中唯一标识*/
	private String searchKey;
	/*导出文件进度缓存库*/
	private Map<String,ProgressObject> loadPool;
	/*数据记录总条数*/
    private Integer totalRecords;
    /*多线程辅助类*/
    private CyclicBarrier cyclicBarrier;
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void run() {
		/*每个线程分页查询，最终将结果放入公共的resultList*/
		List rows = service.getRows(parameters, searchKey, contentSize, index, totalRecords, page);
		resultList.addAll(rows);
		loadPool.get(searchKey).setFinished(loadPool.get(searchKey).getFinished() + pageSize);
		Double progress = com.aspire.bi.common.util.Utils.divide(Double.valueOf(loadPool.get(searchKey).getFinished()), Double.valueOf(totalRecords), 2);
		int p = (int)(progress*100);
		/*子线程不能直接完成进度*/
		if( p > 100 || p == 100){
			loadPool.get(searchKey).setProgress(99);
		}else{
			loadPool.get(searchKey).setProgress(p);
		}			
        try {
        	/*子线程执行结束,等待其他未执行完的线程，如果所有线程都执行结束即执行同步线程*/
        	System.out.println("******子线程执行结束,等待其他未执行完的线程******");
			cyclicBarrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public void setLoadPool(Map<String, ProgressObject> loadPool) {
		this.loadPool = loadPool;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
		this.cyclicBarrier = cyclicBarrier;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public void setService(DataMonitorService service) {
		this.service = service;
	}

	public void setContentSize(int contentSize) {
		this.contentSize = contentSize;
	}

	public void setPage(int page) {
		this.page = page;
	}
}


