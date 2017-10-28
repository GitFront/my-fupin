
package com.david.utils;
/**  
* @Title: Testjson.java 
* @Description: TODO(用一句话描述该文件做什么)
* @Copyright: aspire Copyright (C) 2009
* @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
* @Email: zhangjianxiong@aspirecn.com
* @date: 2017年9月19日 下午7:05:43
* @version: V1.0
*/
public class Testjson {

	/**
	 * 
	 */
	public Testjson() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @author 张健雄
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String test = "茂名市飞地";
		String test1  = "北环村与三丫村争议地";
		
		System.out.println(test.substring(test.indexOf("飞地"), test.length()));
		System.out.println(test1.substring(test1.indexOf("争议地"), test1.length()));
		
	}

}


