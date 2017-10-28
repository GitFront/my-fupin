
package com.aspire.birp.modules.base.thread;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.ibatis.session.RowBounds;

import com.aspire.birp.modules.base.service.BaseService;
import com.aspire.birp.modules.base.vo.ProgressObject;

/**  
 * @Title: 通过Ibatis多线程查询数据信息
 * @Description: 主要用于大数据量多线程查询数据集合
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年5月20日 下午2:46:56
 * @version: V1.0
 */
public class IbatisReadThread implements Runnable {

	/*基础方法服务类*/
	private BaseService baseService;
	/*数据查询结果*/
	private List<Map<String, Object>> resultList;
	/*数据查询标识*/
	private String queryName;
	/*数据查询参数*/
	private Map<String,Object> parameters;
	/*分页查询的页数*/
	private Integer page;
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
	@Override
	public void run() {
		/*每个线程分页查询，最终将结果放入公共的resultList*/
		List<Map<String, Object>> rows = baseService.getSqlSessionTemplate().selectList(queryName, parameters,new RowBounds((page-1)*pageSize, pageSize));
		resultList.addAll(rows);
		loadPool.get(searchKey).setFinished(loadPool.get(searchKey).getFinished() + pageSize);
		Double progress = com.aspire.bi.common.util.Utils.divide(Double.valueOf(loadPool.get(searchKey).getFinished()), Double.valueOf(totalRecords), 2);
		int p = (int)(progress*100);
		/*子线程不能直接完成进度*/
		if( p == 100){
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
	
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}

	public void setPage(Integer page) {
		this.page = page;
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

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	
	

}


