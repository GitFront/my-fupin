$.namespace("common");

/**
 * 用于定义常用的工具类方法
 * @type 
 */
common.utils = {
	/**
	 * 生成图表对象的唯一ID及管理ID
	 * @return {}
	 */
	guid:function(){
		return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
	        return v.toString(16);
	    });
	},
	/**
	 * 将字符串转换成日期类型
	 * @param {} strDate 日期字符 支持格式(yyyy-MM-dd hh:mm:ss or yyyy-MM-dd or yyyyMMdd)
	 * @return {}
	 */
	getDate:function(strDate) {
		var str_temp = strDate;
		if(strDate.length === 8)
		   str_temp = strDate.substring(0,4) + "-" + strDate.substring(4,6) + "-" + strDate.substring(6,8);
    	var date = eval('new Date(' + str_temp.replace(/\d+(?=-[^-]+$)/,
    	function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
    	return date;
    },
	/**
	 * 日期类型的字符串转换
	 * @param {} date 日期对象
	 * @param {} format 格式化方式 （yyyy-MM-dd hh:mm:ss）
	 * @return {} 返回字符串格式的日期
	 */
	dateFormat:function(date,format){
		var o = {  
	        "M+": date.getMonth() + 1, //month  
	        "d+": date.getDate(), //day  
	        "h+": date.getHours(), //hour  
	        "m+": date.getMinutes(), //minute  
	        "s+": date.getSeconds(), //second  
	        "q+": Math.floor((date.getMonth() + 3) / 3), //quarter  
	        "S": date.getMilliseconds() //millisecond  
	    }  
	    if (/(y+)/.test(format))  
	        format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));  
	    for (var k in o)  
	        if (new RegExp("(" + k + ")").test(format))  
	            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
	    return format;  
	},
	/**
	 * 获得时间差, 小时:分钟:秒 或者 年/月/日 小时：分钟：秒
	 * @param {} startTime 开始时间（年月日为全格式，例如 ：2010-10-12 01:00:00 or 2010-10-12）
	 * @param {} endTime   结束时间（年月日为全格式，例如 ：2010-10-12 01:00:00 or 2010-10-12）
	 * @param {} diffType  返回类型second/minute/hour/day,默认为毫秒
	 * @return {} 返回精度为：秒，分，小时，天
	 */
	getDateDiff:function(startTime, endTime, diffType) { 
		 /*将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式*/ 
		 if(startTime.length === 8){
		 	startTime = startTime.substring(0,4) + "/" + startTime.substring(4,6) + "/" + startTime.substring(6,8);
		 }else{
		 	startTime = startTime.replace(/-/g, "/"); 
		 }
		 if(endTime.length === 8){
		 	endTime = endTime.substring(0,4) + "-" + endTime.substring(4,6) + "-" + endTime.substring(6,8);
		 }else{
		 	endTime = endTime.replace(/-/g, "/");
		 }
		 /*将计算间隔类性字符转换为小写*/ 
		 diffType = diffType.toLowerCase(); 
		 var sTime = new Date(startTime);
		 var eTime = new Date(endTime);
		 /*作为除数的数字*/ 
		 var divNum = 1; 
		 switch (diffType) { 
			 case "second": 
			 divNum = 1000; 
			 break; 
			 case "minute": 
			 divNum = 1000 * 60; 
			 break; 
			 case "hour": 
			 divNum = 1000 * 3600; 
			 break; 
			 case "day": 
			 divNum = 1000 * 3600 * 24; 
			 break; 
			 default: 
			 break; 
		 	} 
		 return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum)); 
	 },	
	/**
	 * 通过日期来获取东8区的UTC值
	 * @param {} date 需要转换的日期类型
	 * @return {} 返回UTC值
	 */
	getUTC:function(date){
		/*东8区偏移量*/
		var offset = 8;
		if(date == null || typeof(date) == 'undefined' || typeof(date) != 'object') {
			 date = new Date();		 
		}
		var dateStr = common.utils.dateFormat(date,'yyyyMMddhhmmss');
		var year = parseInt(dateStr.substr(0,4));
		var month = parseInt(dateStr.substr(4,2))-1;	
		var day = parseInt(dateStr.substr(6,2));
		var hour = hour = parseInt(dateStr.substr(8,2)) + offset;
		return Date.UTC(year,month,day,hour);		
	},
	/**
	 * 判断一个变量是否被定义
	 * @param {} obj 
	 * @return {Boolean}
	 */
	isDefined:function(obj){
		if(typeof(obj) == "undefined"){
			return false;		
		}else {
			return true;
		}
	},
	/**
	 * 判断字符串是否为空
	 * @param {} str
	 * @return {Boolean}
	 */
	isEmpty:function(str){
		if(typeof(str) == "undefined" || str === null || str === 'null' || str === ''){
			return true;		
		}else {
			return false;
		}
	},
	/**
	 * 判断是否数组类型
	 * @param {} a
	 * @return {}
	 */
	isArray : function(a){
       return (Object.prototype.toString.call(a) == '[object Array]');
    },
    /**
	 * 判断是否字符类型
	 * @param {} str
	 * @return {}
	 */
    isString:function(str){ 
		return (typeof str==='string')&&str.constructor===String; 
	},
	/**
	 * 判断是否数字类型
	 * @param {} num
	 * @return {}
	 */
	isNumber:function(num){ 
		return (typeof num==='number')&&num.constructor===Number; 
	},
	/**
	 * 判断是否日期类型
	 * @param {} obj
	 * @return {}
	 */
	isDate:function(obj){ 
		return (typeof obj=='object')&&obj.constructor==Date; 
	},
    /**
	 * 判断是否为空数组
	 * @param {} a
	 * @return {}
	 */
	isArrayEmpty : function(a){
       return (Object.prototype.toString.call(a) == '[object Array]') && (a.length === 0);
    },
    /**
     * 判断变量的function是否存在
     * @param {} funName
     * @return {Boolean}
     */
	isFunction : function(funcName){
		var isFunction =false;
		try{
			/*定义时会抛出异常*/
			isFunction = typeof(eval(funcName))=="function";
		}catch(e){}
		return isFunction;
	},
	
	/**
	 * 判断一个变量是否为json对象
	 * @param {} obj
	 * @return {}
	 */
	isJson:function(obj){
	    var isjson = (typeof(obj) === "object" && ( Object.prototype.toString.call(obj).toLowerCase() === "[object object]" )&& !obj.length);
	    return isjson;
	},
	/**
	 * 将字符串格式的对象转化为json对象
	 * @param {} data
	 * @return {}
	 */
	strToJson:function(data){
		/*处理空值*/
		if (this.isEmpty(data)) return eval({});
	    if (!this.isJson(data)) data = eval('('+data+')');
	    return data;
	},
	/**
	 * 显示错误信息提示框(layer)
	 * @param {} msg
	 * @param {} option 接收配置参数
	 * @param {} fun 回调函数
	 */
	showErrorMsg:function(msg,fun,option){
		if(!this.isDefined(option)) option = {shift: 6};
		if(this.isFunction(fun)){
			layer.msg(msg,option,fun);
		}else{
			layer.msg(msg,option);
		}
		
	},
	/**
	 * 显示成功信息提示框(layer)
	 * @param {} msg
	 * @param {} option 接收配置参数
	 * @param {} fun 回调函数
	 */
	showSucMsg:function(msg,fun,option){
		if(!this.isDefined(option)) option = {icon: 1,shade:0.2};
		if(this.isFunction(fun)){
			layer.msg(msg,option,fun);
		}else{
			layer.msg(msg,option);
		}
	},
	/**
	 * 显示加载图标
	 */
	showLoading:function(){
		layer.load(0, {shade: [0.4,'#fff']});
	},
	/**
	 * 关闭加载图标
	 */
	closeLoading:function(){
		layer.closeAll('loading');
	},
	/**
	 * 设置默认JSON参数
	 * @param {} option 自定义参数属性
	 * @param {} defaultOption 默认参数属性
	 * @param {} pri 属性优先级（默认为false，则以默认属性列表为准，为true时，则以自定义参数属性列表为准）
	 * @return {} option_已匹配的默认参数属性
	 */
	setDefaultOption : function(option, defaultOption,pri) {
		if(!this.isDefined(pri)) pri = false;
		/*创建格式化对象*/
		var option_ = {};
		/*如果无定义设置参数*/
		if (!this.isDefined(option)) {
			option_ = defaultOption;
		} else {
			option = this.strToJson(option);
			if (this.isJson(option)) {
				if(pri){
					/*判断对应的自定义参数是否存在*/
					for (var item in option) {
						if (defaultOption.hasOwnProperty(item) && !option.hasOwnProperty(item)) {
							option_[item] = defaultOption[item];
						} else {
							option_[item] = eval('option.' + item);
						}
					}
				}else{
					/*判断对应的默认参数是否存在*/
					for (var item in defaultOption) {
						if (!option.hasOwnProperty(item)) {
							option_[item] = defaultOption[item];
						} else {
							option_[item] = eval('option.' + item);
						}
					}
				}				
			} else {
				option_ = defaultOption;
			}
		}	
		return option_;
	},
	/**
	 * 通过显示高度获取每页显示条数
	 * @param {} h
	 */
	getGridPage:function(h){
			var x = 120;
			var y = 496;
			var pageSize = '10';
			if(h >= y && h < y+x){
				pageSize = '15';
			}else if(h >= y+x && h < y+2*x){
				pageSize = '20';
			}else if(h >= y+2*x && h < y+3*x){
				pageSize = '25';
			}else if(h >= y+3*x && h < y+4*x){
				pageSize = '30';
			}else if(h >= y+4*x && h < y+5*x){
				pageSize = '35';
			}else if(h >= y+5*x && h < y+6*x){
				pageSize = '40';
			}else if(h >= y+6*x && h < y+7*x){
				pageSize = '45';
			}else if(h >= y+7*x){
				pageSize = '50';
			}
			return pageSize;
		}
	
}