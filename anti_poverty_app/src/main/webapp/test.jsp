<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript" src="${ctxPlugins}/jquery/jquery-1.9.1.min.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    
    <title>提交页面</title>
    
	

  </head>
  
  <body>
  <script type="text/javascript">
   var params="{\"userId\":\"445302001206\",\"os\":\"1\",\"versionCode\":1,\"timeStamp\":\"20161123101931\",\"appId\":\"04eea3c72b634b2bad7b65ba968e9832\",\"auth\":\"1acc840868af3a1df88a488b849588ad\",\"format\":\"1\",\"requestAction\":\"2\"}";
   var data = eval('(' + params+ ')');
   //var array=JSON.stringify(d);
   console.info(data);
  	$(function(){
  		$.ajax({
  		type:"post",
  		url:"http://localhost:8080/anti_poverty_app/app/version/versionUpdate",
  		data:{"params":params},
  		dataType:"json",
  		success:function(msg){
  		alert( "Data Saved: " + msg );
  		 console.info(msg);
  		}
  		});
  	
  	});
  
  
  
  </script>
   
  </body>
</html>
