
package com.aspire.birp.modules.base.constant;
/**  
 * @Title: 数据表存储类型 
 * @Description: 描述数据表存储类型信息
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年8月16日 下午10:32:25
 * @version: V1.0
 */
public enum StorageType {

	/*全量数据表*/
	F,
	/*增量数据表*/
	I;
	
	public static  StorageType getStorageType(String key){
		if("F".equals(key)){
			return StorageType.F;
		}else if("I".equals(key)){
			return StorageType.I;
		}else{
			return null;
		}
		
	}
}


