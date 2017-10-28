$.namespace("common.report");

/**
 * 报表界面通用查询工具类
 * @type 
 */

common.report.page  = {
	reportmap:{},
	seach_form_id:"",
	seachid:"head-search",
	gridId:"",
	expotTitle:"",
	
	/**
	 * 界面初始化方法AB
	 * @param {} seachFormId 查询控件formID
	 * @param {} gridId 列表控件ID
	 * @param {} expotTitle 默认导出标题
	 */
	init:function(seachFormId,gridId,expotTitle){
		this.seach_form_id=seachFormId;
		this.gridId=gridId;
		this.expotTitle=expotTitle;
		/*默认给查询按钮赋值*/
		if($('#hc-report-btn-search').linkbutton('options') && 
				$('#hc-report-btn-search').linkbutton('options').disabled){
			$('#hc-report-btn-search').linkbutton('enable');
			$('#hc-report-btn-search').bind('click', this.reload);
		}
		if($('#hc-report-btn-reset').length>0 && 
				$('#hc-report-btn-reset').linkbutton('options').disabled){
			$('#hc-report-btn-reset').linkbutton('enable');
			$('#hc-report-btn-reset').bind('click',this.reset);
		}
		
		if($('#hc-report-btn-export').length>0 && 
				$('#hc-report-btn-export').linkbutton('options').disabled){
			$('#hc-report-btn-export').linkbutton('enable');
			$('#hc-report-btn-export').bind('click',this.download);
		}				
	},
	load:{
		/**
		 * 饼状图的动态生成
		 * @param {} container(必须) 报表指向的divid
		 * @param {} queryName(必须) mybatis的对应查询名称
		 * @param {} parameters 自定义查询参数
		 * @param {} mapper 数据字段对应信息
		 * @param {} option 自定义配置项
		 * @param {} rowsValue 行转列配置，针对同一行数据不同字段进行分维度显示。（格式为field_name1:维度名称1;field_name2:维度名称2）
		 */
		Pie:function(container,queryName,parameters,mapper,option,rowsValue){
			/*获取页面及固定查询条件*/			
			var p =common.report.page.utils.getDefauleParams(common.report.page.seachid,parameters);
			/*调用Highchart封装工具类的生成方法*/
			var chartid = Highchart.mybatis.Pie(container,queryName,p,mapper,option,rowsValue);	
	  		/*创建页面指向与ID之间的关联*/
	        common.report.page.reportmap[container] = chartid;
		},
		/**
		 * 折线图的动态生成
		 * @param {} container(必须) 报表指向的divid
		 * @param {} queryName(必须) mybatis的对应查询名称
		 * @param {} parameters 自定义查询参数
		 * @param {} mapper 数据字段对应信息
		 * @param {} option 自定义配置项
		 * @param {} hideGroup 隐藏分组名。格式为'group1名称;group2名称;group3名称'
		 * @param {} rowsGroup 行转列配置，针对同一行数据不同字段进行分组显示。（格式为field_name1:分组名称1;field_name2:分组名称2）
		 */
		Line:function(container,queryName,parameters,mapper,option,hideGroup,rowsGroup){
			/*获取页面及固定查询条件*/			
			var p =common.report.page.utils.getDefauleParams(common.report.page.seachid,parameters);
			/*调用Highchart封装工具类的生成方法*/
			var chartid = Highchart.mybatis.Line(container,queryName,p,mapper,option,hideGroup,rowsGroup);	
	  		/*创建页面指向与ID之间的关联*/
	        common.report.page.reportmap[container] = chartid;
		},
		/**
		 * 折线图的动态生成
		 * @param {} container(必须) 报表指向的divid
		 * @param {} queryName(必须) mybatis的对应查询名称
		 * @param {} parameters 自定义查询参数
		 * @param {} mapper 数据字段对应信息
		 * @param {} option 自定义配置项
		 * @param {} hideGroup 隐藏分组名。格式为'group1名称;group2名称;group3名称'
		 */
		InvertedLine:function(container,queryName,parameters,mapper,option,hideGroup){
			/*获取页面及固定查询条件*/			
			var p =common.report.page.utils.getDefauleParams(common.report.page.seachid,parameters);
			/*调用Highchart封装工具类的生成方法*/
			var chartid = Highchart.mybatis.InvertedLine(container,queryName,p,mapper,option,hideGroup);	
	  		/*创建页面指向与ID之间的关联*/
	        common.report.page.reportmap[container] = chartid;
		},
		/**
		 * 柱状图的动态生成
		 * @param {} container 报表指向的divid
		 * @param {} queryName mybatis的对应查询名称
		 * @param {} parameters 自定义查询参数
		 * @param {} mapper 数据字段对应信息
		 * @param {} option 自定义配置项
		 * @param {} hideGroup 隐藏分组名。格式为'group1名称;group2名称;group3名称'
		 * @param {} rowsGroup 行转列配置，针对同一行数据不同字段进行分组显示。（格式为field_name1:分组名称1;field_name2:分组名称2）
		 */
		Bars:function(container,queryName,parameters,mapper,option,hideGroup,rowsGroup){
			/*获取页面及固定查询条件*/			
			var p =common.report.page.utils.getDefauleParams(common.report.page.seachid,parameters);
			/*调用Highchart封装工具类的生成方法*/
			var chartid = Highchart.mybatis.Bars(container,queryName,p,mapper,option,hideGroup,rowsGroup);	
	  		/*创建页面指向与ID之间的关联*/
	        common.report.page.reportmap[container] = chartid;			
		},
		/**
		 * 堆积柱状图的动态生成
		 * @param {} container 报表指向的divid
		 * @param {} queryName mybatis的对应查询名称
		 * @param {} parameters 自定义查询参数
		 * @param {} mapper 数据字段对应信息
		 * @param {} option 自定义配置项
		 * @param {} hideGroup 隐藏分组名。格式为'group1名称;group2名称;group3名称'
		 * @param {} rowsGroup 行转列配置，针对同一行数据不同字段进行分组显示。（格式为field_name1:分组名称1;field_name2:分组名称2）
		 */
		StackBars:function(container,queryName,parameters,mapper,option,hideGroup,rowsGroup){
			/*获取页面及固定查询条件*/			
			var p =common.report.page.utils.getDefauleParams(common.report.page.seachid,parameters);
			/*调用Highchart封装工具类的生成方法*/
			var chartid = Highchart.mybatis.StackBars(container,queryName,p,mapper,option,hideGroup,rowsGroup);	
	  		/*创建页面指向与ID之间的关联*/
	        common.report.page.reportmap[container] = chartid;			
		},
		/**
		 * 时序图的动态生成
		 * @param {} container 报表指向的divid
		 * @param {} queryName mybatis的对应查询名称
		 * @param {} parameters 自定义查询参数
		 * @param {} mapper 数据字段对应信息
		 * @param {} option 自定义配置项
		 * @param {} hideGroup 隐藏分组名。格式为'group1名称;group2名称;group3名称'
		 * @param {} rowsGroup 行转列配置，针对同一行数据不同字段进行分组显示。（格式为field_name1:分组名称1;field_name2:分组名称2）
		 */
		TimeSeriesLine:function(container,queryName,parameters,mapper,option,hideGroup,rowsGroup){
			/*获取页面及固定查询条件*/			
			var p =common.report.page.utils.getDefauleParams(common.report.page.seachid,parameters);
			/*调用Highchart封装工具类的生成方法*/
			var chartid = Highchart.mybatis.TimeSeriesLine(container,queryName,p,mapper,option,hideGroup,rowsGroup);	
	  		/*创建页面指向与ID之间的关联*/
	        common.report.page.reportmap[container] = chartid;				
		},
		/**
		 * 仪表盘的动态生成
		 * @param {} container 报表指向的divid
		 * @param {} queryName mybatis的对应查询名称
		 * @param {} parameters 自定义查询参数
		 * @param {} mapper 数据字段对应信息
		 * @param {} option 自定义配置项
		 */
		Gauge:function(container,queryName,parameters,mapper,option,decimal){
			/*获取页面及固定查询条件*/			
			var p =common.report.page.utils.getDefauleParams(common.report.page.seachid,parameters);
			/*调用Highchart封装工具类的生成方法*/
			var chartid = Highchart.mybatis.Gauge(container,queryName,p,mapper,option,decimal);	
	  		/*创建页面指向与ID之间的关联*/
	        common.report.page.reportmap[container] = chartid;				
		},
		
		/**
		 * 仪表盘的动态生成
		 * @param {} container 报表指向的divid
		 * @param {} queryName mybatis的对应查询名称
		 * @param {} parameters 自定义查询参数
		 * @param {} isPage 是否分页显示
		 * @param {} autoHeight 是否自适应高度（单表）
		 */
		Grid:function(container,queryName,parameters,isPage,autoHeight){
			/*获取页面及固定查询条件*/			
			var p =common.report.page.utils.getDefauleParams(common.report.page.seachid,parameters);
			
			/*进行参数封装*/
			var params = {};
			params.queryName = queryName;
			params.param = common.utils.strToJson(p);
			params.isPage = isPage;			
			
			/*加载url参数*/
			var url = ctx+"/a/report/grid/load";
			var grid = $('#'+container);
			/*设计页面高度*/			
			if(common.utils.isDefined(autoHeight) && autoHeight){
				var h = $(window).height()-55-93;
				var pageSize = common.utils.getGridPage(h);
				grid.datagrid({			
					height:h,
					pageSize:pageSize,
					pageList:[10,15,20,25,30,35,40,45,50],
					url:url,
					queryParams:params
				});
			}else{
				grid.datagrid({
					url:url,
					queryParams:params
				});
			}			
			 /*管理报表对象信息*/
	         var obj = {};
	         obj["chart"] = grid;
	         obj["chartType"] = 'Grid';
	         obj["url"] = url;
	         obj["parameters"] = params;
	         var id = common.report.highcharts.object.add(obj);
	  		/*创建页面指向与ID之间的关联*/
	        common.report.page.reportmap[container] = id;				
		},
		
		/**
		 * 加载info数据表信息
		 * @param {} container 报表指向的divid
		 * @param {} queryName mybatis的对应查询名称
		 * @param {} parameters 自定义查询参数
		 * @param {} mapper 数据字段对应信息
		 */
		InfoTable:function(container,queryName,parameters,mapper){
			/*获取页面及固定查询条件*/			
			var p =common.report.page.utils.getDefauleParams(common.report.page.seachid,parameters);
			/*进行参数封装*/
			var params = {};
			params.queryName = queryName;
			params.param = common.utils.strToJson(p);
			params.mapper = common.utils.strToJson(mapper);
			
			var url = ctx+"/a/report/infotable/load";
			$.ajax({
	             type: "POST",
	             url: url,
	             data: params,
	             cache:true,
	             dataType: "json",
	             success: function(data){
	             	common.report.page.utils.loadInfoData(container,data);
	                /*管理报表对象信息*/
	             	var obj = {};
	                obj["chart"] = container;
	                obj["chartType"] = 'InfoTable';
	                obj["data"] = data;
	                obj["url"] = url;
	                obj["parameters"] = params;
	                var id = common.report.highcharts.object.add(obj);
	                /*创建页面指向与ID之间的关联*/
	        		common.report.page.reportmap[container] = id;
	             }
	         });
		},
		
		/**
		 * 地图的动态生成
		 * @param {} container 报表指向的divid
		 * @param {} queryName mybatis的对应查询名称
		 * @param {} parameters 自定义查询参数
		 * @param {} mapper 数据字段对应信息
	  	 * @param {} mapData 地图数据信息
	  	 * @param {} joinBy 地图与数据对应字段
		 * @param {} option 自定义配置项
		 */
		Map:function(container,queryName,parameters,mapper,mapData,joinBy,option){
			/*获取页面及固定查询条件*/			
			var p =common.report.page.utils.getDefauleParams(common.report.page.seachid,parameters);
			/*调用Highchart封装工具类的生成方法*/
			var chartid = Highmap.mybatis.Map(container,queryName,p,mapper,mapData,joinBy,option);	
	  		/*创建页面指向与ID之间的关联*/
	        common.report.page.reportmap[container] = chartid;				
		}
		
	},
	/**
	 * 统一重新加载
	 * @param {} container 报表指向的divid
	 * @param {} queryName mybatis的对应查询名称
	 * @param {} parameters 自定义查询参数
	 * @param {} mapper 数据字段对应信息
	 * @param {} option 自定义配置项
	 */
	reload:function(container,queryName,parameters,mapper,option){
		/*找不到元素ID，页面元素统一更新*/
		if(!common.utils.isString(container) || common.utils.isEmpty(container)){
			common.report.page.utils.reloadAll();
		}else{
			if(!common.report.page.reportmap.hasOwnProperty(container)) return ;
			var chartid = common.report.page.reportmap[container];
			var obj = common.report.highcharts.object.get(chartid);
			var type = obj["chartType"];
			/*重新更新参数值*/
			var pobj = obj["parameters"];
			if(common.utils.isEmpty(queryName)) queryName = pobj.queryName;
			if(common.utils.isEmpty(parameters)) parameters = pobj.param;
			if(common.utils.isEmpty(mapper)) mapper = pobj.mapper;
			/*获取页面及固定查询条件*/			
			var pn =common.report.page.utils.getQueryParams(common.report.page.seachid,parameters);
			/*按类型分类更新*/
			switch(type){
				case 'InfoTable':
					common.report.page.utils.reloadInfoTable(container,queryName,pn,mapper);
					break;
				case 'Grid':
					common.report.page.utils.reloadGrid(container,queryName,pn);
					break;
				case 'Map':
					Highmap.mybatis.reload(chartid,queryName,pn,mapper,option);
					break;
				default:
					/*调用Highchart接口重载数据*/
					Highchart.mybatis.reload(chartid,queryName,pn,mapper,option);
					break;
			}			
		}	
	},
	/*重置功能*/
	reset:function(){
		$('#'+common.report.page.seach_form_id).form('clear');
	},
	/*数据下载功能*/
	download:function(){
		/*表单数据下载事件*/
		exportExcel(common.report.page.gridId,common.report.page.expotTitle);
	},
	utils:{
		/**
		 * 通过div标识获取参数信息
		 * @param {} div div的ID值
		 * @param {} 固定附加参数
		 * @return {}
		 */
		getQueryParams:function(div,append){
				var apd = common.utils.strToJson(append);
 				var param = {};
 				var p_ = {};
				var temp = $("#"+div).find(":input");
				temp.each(function() {
					var temp = $(this).attr("name");
 					var s_ = $(this).val();
					if(s_!=null)
					s_ = Common.delHtml(s_);
					if (temp != null && undefined != temp) {
						if(p_.hasOwnProperty(temp)){
							p_[temp] += "|" + s_;
						}else{
							p_[temp] = s_;
						}		 				
					}
 	 			});
 	 			/*添加固定参数项*/
 	 			var param= $.extend({}, append , p_);
				return param;
			},
			/**
			 * 通过默认参数加载查询信息(默认参数优先于查询条件)
			 * @param {} div div的ID值
			 * @param {} 固定附加参数
			 * @return {}
			 */
			getDefauleParams:function(div,append){
				var apd = common.utils.strToJson(append);
 				var param = {};
 				var p_ = {};
				var temp = $("#"+div).find(":input");
				temp.each(function() {
					var temp = $(this).attr("name");
 					var s_ = $(this).val();
					if(s_!=null)
					s_ = Common.delHtml(s_);
					if (temp != null && undefined != temp) {
						if(p_.hasOwnProperty(temp)){
							p_[temp] += "|" + s_;
						}else{
							p_[temp] = s_;
						}		 				
					}
 	 			});
 	 			/*添加固定参数项*/
 	 			var param= $.extend({}, p_,append);
				return param;
			},
			
			/**
			 * 重新加载info数据表格
			 * @param {} container
			 * @param {} queryName
			 * @param {} parameters
			 * @param {} mapper
			 */
			reloadInfoTable:function(container,queryName,parameters,mapper){
				if(!common.utils.isString(container) || common.utils.isEmpty(container)) return;
				/*获取已保存元素信息*/
				var chartid = common.report.page.reportmap[container];
				var obj = common.report.highcharts.object.get(chartid);
				var url = obj["url"];

				/*进行参数封装*/
				var params = {};
				params.queryName = queryName;
				params.param = common.utils.strToJson(parameters);
				params.mapper = common.utils.strToJson(mapper);			
				
				$.ajax({
		             type: "POST",
		             url: url,
		             data: params,
		             cache:true,
		             dataType: "json",
		             success: function(data){
		             	common.report.page.utils.loadInfoData(container,data);
		                /*管理报表对象信息*/
		                obj["data"] = data;
		                obj["queryName"] = queryName;
		                obj["parameters"] = params;
		                common.report.highcharts.object.reset(chartid,obj);
		             }
		         });				
			},
			/**
			 * 重新加载grid列表数据
			 * @param {} container
			 * @param {} queryName
			 * @param {} parameters
			 */
			reloadGrid:function(container,queryName,parameters){
				if(!common.utils.isString(container) || common.utils.isEmpty(container)) return;
				/*获取已保存元素信息*/
				var chartid = common.report.page.reportmap[container];
				var obj = common.report.highcharts.object.get(chartid);
				var grid = obj["chart"];
				var params = obj["parameters"];
		    	params["queryName"] = queryName;
		    	params["param"] = common.utils.strToJson(parameters);
				
				grid.datagrid('load',params);
				
				obj["parameters"] = params;
		        common.report.highcharts.object.reset(chartid,obj);			
			},
			/**
			 * 加载info数据表格数据
			 * @param {} container
			 * @param {} data
			 */
			loadInfoData:function(container,data){
				if(!common.utils.isEmpty(data)){
	             	/*动态加载数据*/
		             $("#"+container+" span").each(function(){
		             	var key = $(this).attr('id');
						if(data.hasOwnProperty(key)){
						   	$(this).text(data[key]);
						}else{
						    if(!common.utils.isEmpty(key)) {
						    	$(this).text('-');
						    }						    	
						}
					});
	             }	
			},
			/**
			 * 重新加载该页面注册的全部报表
			 */
			reloadAll:function(){
				/*历遍每一个初始化元素*/
				for(var container in common.report.page.reportmap){
					if(!common.utils.isEmpty(container)) 
						common.report.page.reload(container);
				}
			}
	}
		
};