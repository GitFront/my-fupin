package com.aspire.birp.modules.antiPoverty.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MaperJson {
	// 静态内部类, 只有当有引用时, 该类才会被装载  
		private static class Mjson {  
		public static ObjectMapper mapper = new ObjectMapper();;  
		}  
		  
		public static ObjectMapper getInstance() {  
		return Mjson.mapper;  
		}  
}

