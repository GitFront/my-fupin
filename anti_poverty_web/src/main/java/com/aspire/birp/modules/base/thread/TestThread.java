
package com.aspire.birp.modules.base.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.aspire.birp.modules.base.service.BaseService;
import com.aspire.birp.modules.base.vo.ProgressObject;

/**  
 * @Title: TestThread.java 
 * @Description: TODO(用一句话描述该文件做什么)
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2017年4月15日 下午9:29:10
 * @version: V1.0
 */
@Service
public class TestThread extends BaseService {
	/*线程池管理*/
	@Autowired
    private ThreadPoolTaskExecutor taskExecutor;
	
	/*分页导出单线程最大查询数*/
	private static final int MAX_PERCENT_DOWNLOAD_NUM = 150000;
	
	/**
	 * 导出文件进度缓存库，主要用于导出数据时使用
	 * key:searchKey标识单次导出
	 * value:本次导出的进度数据
	 */
	private static Map<String,ProgressObject> loadPool;
	/**
	 * 
	 */
	public TestThread() {
		// TODO Auto-generated constructor stub
	}

	public void insertData(){
		for (int i = 0; i < 5000000; i++) {
			Map<String,String> parameter = new HashMap<String,String>();
			parameter.put("id", UUID.randomUUID().toString());
			parameter.put("name", "TEST"+i);
			
			getSqlSessionTemplate().insert("aspire.bi.common.testThread.add", parameter);
		}
	}
	
	/**
	 * @param args
	 * @author 张健雄
	 */
	public static void main(String[] args) {
		TestThread t = new TestThread();
		t.insertData();

	}

}


