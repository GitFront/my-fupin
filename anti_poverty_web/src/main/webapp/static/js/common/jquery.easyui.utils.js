var Page = $.extend({}, Page);
Page.utils = {
	/**
	 * 页面自适应通用方法（只适用于改变window大小对页面布局的自适应）
	 * 自适应用到的自定义属性autoResize、resizeHeight、resizeWidth，需设置componentType指定组件类型
	 * 		支持的自适应easyui组件：datagrid、treegrid、tabs、accordion，这些组件需要自适应时，需要设置id、class(html方式初始化);
	 * 		支持的自适应ztree组件：ztree，这些组件需要自适应时，需要设置id、class(html方式初始化).
	 * 兼容性：IE7/8/9（注意jquery版本2.0+不支持IE7/8）、火狐
	 * 自定义属性说明：
	 * 1、autoResize		值为true这说明该元素需要自适应，否则不需要自适应
	 * 2、componentType 第三方组件类型：datagrid、treegrid、tabs、accordion、ztree，如果非第三方组件，则可以不设置该属性或设置该属性值为null
	 * 3、resizeHeight	值为false或没有该属性说明该元素高度不需要自适应，使用原高度即可；值为数字，表示该元素的下边框距离window底边框的偏移量；
	 * 		值为百分比，表示该元素在可自适应的区域内所占高度的比例（如“false”,"20","50%"）
	 * 4、resizeWidth	值为false或没有该属性说明该元素宽度不需要自适应，使用原宽度即可；值为数字，表示该元素的右边框距离window右边框的偏移量；
	 * 		值为百分比，表示该元素在可自适应的区域内所占宽度的比例（如“false”,"20","50%"）
	 * @Param minHeight 最小高度
	 * @Param minWidth 最小宽度（如果css全局样式设定了浏览器最小宽度，那这个参数就不是很必要了）
	 * @problem 存在问题：当页面设定最小宽度时，最小宽度高度计算有问题。目前解决方法是在代码里面定死浏览器最小高度minClientWidth变量的值来辅助计算。
	 */
	autoResize: function(minHeight,minWidth){
		//扫描是否有需要自适应的元素或组件
		var objs = $("[autoResize=true]");
		if(objs.length<=0) return;
		
		//浏览器最小宽度
		var minClientWidth = 1024;
		
		var Sys = {};
        var ua = navigator.userAgent.toLowerCase();
        var s;
        (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
        (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
        (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
        (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
        (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
        
        /**
         * 获取组件的offset
         * @param component
         * @return offset
         */
        function getOffest(jq,componentType){
        	//表格
			if(componentType.indexOf("datagrid")>-1)
				return jq.datagrid("getPanel").parent().offset();
			//树表格
			if(componentType.indexOf("treegrid")>-1)
				return jq.treegrid("getPanel").parent().offset();
			return jq.offset();
        }
        
        /**
         * 组件resize
         * @param componentType 组件类型
         * @param offset
         */
        function resize(jq,componentType,offset){
        	//tabs面板
			if(componentType.indexOf("tabs")>-1){
				if(offset.height==undefined)
					offset.height = jq.tabs("options").height;
				if(offset.width==undefined)
					offset.width = jq.tabs("options").width;
				jq.tabs("options").width=offset.width;
				jq.tabs("options").height=offset.height;
				jq.tabs("resize");
			}
			//accordion手风琴
			else if(componentType.indexOf("accordion")>-1){
				if(offset.height==undefined)
					offset.height = jq.accordion("options").height;
				if(offset.width==undefined)
					offset.width = jq.accordion("options").width;
				jq.accordion("options").width=offset.width;
				jq.accordion("options").height=offset.height;
				jq.accordion("resize");
			}
			//表格
			else if(componentType.indexOf("datagrid")>-1){
				if(offset.height==undefined)
					offset.height = jq.datagrid("options").height;
				if(offset.width==undefined)
					offset.width = jq.datagrid("options").width;
				jq.datagrid("resize",{width:offset.width,height:offset.height});
			}
			//树表格
			else if(componentType.indexOf("treegrid")>-1){
				if(offset.height==undefined)
					offset.height = jq.datagrid("options").height;
				if(offset.width==undefined)
					offset.width = jq.datagrid("options").width;
				jq.treegrid("resize",{width:offset.width,height:offset.height});
			}
			//树图
			else if(componentType.indexOf("ztree")>-1){
			}
        }

		for(var i=0;i<2;i++){
			setTimeout(function(){
				//火狐用document，IE用window
				var width = $(window).width();
				var height = $(window).height();
				//火狐浏览器窗口可视高度包括左右滚动条，故出现左右滚动条时需要减去滚动条高度
				if(!Sys.ie){
					height = width<$(document).width()?window.innerHeight-15:window.innerHeight;
				}
				width = width<minClientWidth?minClientWidth:width;
				
				objs.each(function(index,obj){
					var componentType = $(this).attr("componentType");
					var isComponent = true;

					//非第三方组件
					if(componentType==undefined || !componentType
							|| componentType==null || componentType=="null" || componentType=="")
						isComponent = false;
					
					var offset  = isComponent?getOffest($(this),componentType):$(this).offset();
					//计算自适应高宽
					var resizeHeight = $(this).attr("resizeHeight");
					if(resizeHeight!=undefined && resizeHeight!="false"){
						if(resizeHeight.indexOf("%")>-1){
							offset.height = (height-offset.top)*resizeHeight.substring(0, resizeHeight.length-1)/100;
						}else 
							offset.height = height-offset.top-resizeHeight;
						//最小高度还要除去调整高度
						if(minHeight){
							minHeight = minHeight-parseInt(resizeHeight);
						}
					}
					var resizeWidth = $(this).attr("resizeWidth");
					if(resizeWidth!=undefined && resizeWidth!="false"){
						if(resizeWidth.indexOf("%")>-1){
							offset.width = (width-offset.left)*resizeWidth.substring(0, resizeWidth.length-1)/100;
						}else 
							offset.width = width-offset.left-resizeWidth;
						//最小宽度还要除去调整宽度
						if(minWidth){
							minWidth = minWidth-parseInt(resizeWidth);
						}
					}

					//最小高度
					if(minHeight && offset.height!=undefined && offset.height<minHeight){
						offset.height = minHeight;
					}
					//最小宽度
					if(minWidth && offset.width!=undefined && offset.width<minWidth){
						offset.width = minWidth;
					}
					
					//第三方组件自适应（根据具体的第三方UI修改）
					if(isComponent)
						resize($(this),componentType,offset);
					//其他普通元素
					else{
						if(offset.height==undefined)
							offset.height = $(this).height();
						if(offset.width==undefined)
							offset.width = $(this).width();
						$(this).height(offset.height);
						$(this).width(offset.width);
					}
				});
			}, 50);
		}
	}
};

/************************************************
 * easyUI扩展
 * 		datagrid支持显示外键关联属性；可编辑表格支持my97
 * @autor jiangxd
 ***********************************************/
(function($) {
	/**
	 * 在可编辑datagrid中，使用my97日期控件
	 * 用例(设置日期格式)：
	 * {
	 * 		field : 'time',
	 * 		title : '时间',
	 * 		width : 130,
	 * 		editor : { type : 'my97',options:{dateFmt:'yyyy-MM-dd HH:00'} }
	 * }
	 */
	$.extend($.fn.datagrid.defaults.editors, {
		my97 : {
			init : function(container, options) {
				var dateFmt = "yyyy-MM-dd HH:mm:ss";
				var maxDate = "%y-%M-%d";
				if(options){
					if(options.dateFmt) dateFmt = options.dateFmt;//如options里定义了dateFmt，则按定义的格式
					if(options.maxDate) maxDate = options.maxDate;//如options里定义了maxDate，则按定义的格式
				}
				var input = $('<input class="Wdate" onclick="WdatePicker({dateFmt:\''+dateFmt+'\',maxDate:\''+maxDate+'\',readOnly:true});"/>').appendTo(container);
				return input;
			},
			getValue : function(target) {
				return $(target).val();
			},
			setValue : function(target, value) {
				$(target).val(value);
			},
			resize : function(target, width) {
				var input = $(target);
				if ($.boxModel == true) {
					input.width(width - (input.outerWidth() - input.width()));
				} else {
					input.width(width);
				}
			}
		}
	});
});
/**
 * 树表格方法扩展：判断结点行是否被勾选
 * @param id 树结点ID
 * @return boolean
 */
$.fn.treegrid.methods = $.extend({}, $.fn.treegrid.methods, {
	isChecked : function(jq, id){
		var panel = $(jq).datagrid("getPanel");
		var ck = panel.find("table.datagrid-btable tr[node-id="+id+"] input[name=ck]");
		if(ck && ck.attr("checked"))
			return true;
		return false;
	}
});
/**
 * datagrid扩展
 * 		重写view的renderRow方法，当datagrid返回的Json带外键时，列的field可以直接获取到外键关联对象的属性值
 * 		如后台返回List<User>,User外键关联到Mail对象（即User.mail），前台呈现Mail的ip属性时，列可如下定义：
 * 			<th data-options="field:'mail.ip',width:100">邮件地址</th>
 */
$.fn.datagrid.defaults.view = $.extend({}, $.fn.datagrid.defaults.view, {
	renderRow : function(target, fields, frozen, rowIndex, rowData) {
		var opts = $.data(target, "datagrid").options;
		var cc = [];
		if (frozen && opts.rownumbers) {
			var rownumber = rowIndex + 1;
			if (opts.pagination) {
				rownumber += (opts.pageNumber - 1) * opts.pageSize;
			}
			cc.push("<td class=\"datagrid-td-rownumber\"><div class=\"datagrid-cell-rownumber\">" + rownumber + "</div></td>");
		}
		for ( var i = 0; i < fields.length; i++) {
			var field = fields[i];
			var col = $(target).datagrid("getColumnOption", field);
			if (col) {
				//var fieldValue = rowData[field];
				/**fieldValue值不再直接获取，而是根据外键关联方式获取**/
				var fieldValue = jQuery.proxy(function(){try{return eval('this.'+field);}catch(e){return "";}},rowData)();
				/******************************************/
				var styleValue = col.styler ? (col.styler(fieldValue, rowData, rowIndex) || "") : "";
				var style = col.hidden ? "style=\"display:none;" + styleValue + "\"" : (styleValue ? "style=\"" + styleValue + "\"" : "");
				cc.push("<td field=\"" + field + "\" " + style + ">");
				var style = "";
				if (!col.checkbox) {
					if (col.align) {
						style += "text-align:" + col.align + ";";
					}
					if (!opts.nowrap) {
						style += "white-space:normal;height:auto;";
					} else {
						if (opts.autoRowHeight) {
							style += "height:auto;";
						}
					}
				}
				cc.push("<div style=\"" + style + "\" ");
				if (col.checkbox) {
					cc.push("class=\"datagrid-cell-check ");
				} else {
					cc.push("class=\"datagrid-cell " + col.cellClass);
				}
				cc.push("\">");
				if (col.checkbox) {
					cc.push("<input type=\"checkbox\" name=\"" + field + "\" value=\"" + (fieldValue != undefined ? fieldValue : "") + "\"/>");
				} else {
					if (col.formatter) {
						cc.push(col.formatter(fieldValue, rowData, rowIndex));
					} else {
						cc.push(fieldValue);
					}
				}
				cc.push("</div>");
				cc.push("</td>");
			}
		}
		return cc.join("");
	}
});
var EasyuiTools = {
	/**
	 * 添加tab方法
	 * 		注意：tab统一使用iframe。先看本页有没有tabs，如果没有则看父页面有没有，如果都没有就window.open()
	 * @param id tab面板id
	 * @param tabId 子tab id
	 * @param title 子tab标题
	 * @param url 子tab内容指向地址
	 * @param refresh 是否刷新tab
	 */
	addTab : function (id, tabId, title, url, refresh){  
	    var tabObj = $("#"+id);  
	    if(tabObj.length<=0)
	    	tabObj = parent.$("#"+id);
	    if(tabObj.length<=0)
	    	window.open(url,title);
	    var content = "未实现";
        if (url)
            content = "<iframe src='"+url+"' frameborder='0' style='width: 100%; height: 100%;'></iframe>";  
        
	    if (tabObj.tabs("exists", title)){//如果tab已经存在,则选中并刷新该tab          
	    	tabObj.tabs("select", title);  
	    	if(refresh)
	    		tabObj.tabs('getSelected').panel('options').content=content;
		    	tabObj.tabs('getSelected').panel('refresh');
	    } else {  
	    	tabObj.tabs("add",{
	        	id: tabId,
	            title: title,
	            fit: true,
	            closable: true,
	            content: content
	        });  
	    }  
	},
	/**
	 * 弹出窗口
	 * @param id 窗口ID
	 * @param title 窗口标题
	 * @param width 窗口宽度
	 * @param url 窗口href地址
	 */
	openWindow : function (id, title, width, url){
		$('<div id="'+id+'"/>').window({
			title: title,
			width: width,
			minimizable: false,
			maximizable: false,
			collapsible: false,
			modal: true,
			href: url,
			onClose:function(){
				$(this).window('destroy');
			},  
            onLoad: function(){  
            	$(this).window("center");
            }
		});
	},
	/**
	 * 错误信息框
	 * @param responseText 异常信息
	 * @param width 宽度，无则默认800
	 * @param height 高度，无则默认500
	 */
	alertError: function(responseText,width,height){
		$('<div/>').dialog({
			title:'异常',
			width: width?width:800,
			height: height?height:200,
			closed:false,
			cache:false,
			modal:true,
			onLoad:function(){
				$(this).dialog('center');
			}
		}).html(responseText);
	}
};
/**
 * 表格数据pageSize
 */
$.extend($.fn.datagrid.defaults, {
	pageList : [ 20, 30, 40, 50 ],
	striped : true
});