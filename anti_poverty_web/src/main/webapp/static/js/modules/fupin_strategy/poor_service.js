$(function() {
	var CLICK_EV = 'click',
		ajax = Helpers.ajax;

	$.poorService = {
		$poorServiceWrap: $("#poorServiceWrap"), //最外层
		$poorServiceIndexWrap: $("#poorServiceIndexWrap"), //首页
		$popupPoorServiceDetail: $("#popupPoorServiceDetail"), //详情弹窗

		init: function() {
			var self = this;
			var opts = {};
			self.renderPoorServiceIndex(opts);

			$('body').on('click', hideScopeList);

			function hideScopeList() {
				var $selectBox = $(".select-box");
				$selectBox.removeClass('selected').find('.select-list').hide();
				$selectBox.find('.arrow').removeClass('arrow-on');
			}
		},
		//渲染首页数据
		renderPoorServiceIndex: function(opts) {
			var self = this;
			var curLevel = opts.curLevel || DEFAULT_LEVEL;
			var curAreaID = opts.curAreaID || DEFAULT_AREA_ID;

			ajax({
				url: URLS.URL_DATA_POOR_SERVICE_INDEX,
				data: {
					level: curLevel,
					id: curAreaID
				},
				success: function(data) {
					var code = data.code,
						d = data.data;
					if (code == 0 && d) {
						console.log(d);
						self.$poorServiceIndexWrap.html(template('tplPoorServiceIndex', d));
						//图形
						var personDoneChartJson = d.personal.chart_config_done;
						var personDoingChartJson = d.personal.chart_config_doing;
						var legalPersonDoingChartJson = d.legal_person.chart_config_doing;
						var legalPersonDoneChartJson = d.legal_person.chart_config_done;
						echarts.init(document.getElementById('personalDoneChart')).setOption(commonEchartConfig[personDoneChartJson.convert_method](personDoneChartJson.config));
						echarts.init(document.getElementById('personalDoingChart')).setOption(commonEchartConfig[personDoingChartJson.convert_method](personDoingChartJson.config));
						echarts.init(document.getElementById('legalPersonDoingChart')).setOption(commonEchartConfig[legalPersonDoingChartJson.convert_method](legalPersonDoingChartJson.config));
						echarts.init(document.getElementById('legalPersonDoneChart')).setOption(commonEchartConfig[legalPersonDoneChartJson.convert_method](legalPersonDoneChartJson.config));

						self.bindIndexChartTab();
						self.bindSelectArea();
						self.bindIndexDetail();
						self.renderPoorServiceLingnan(opts);
					}
				},
				error: function() {
					// self.renderPoorEastWestIndex();
				}
			})
		},
		//渲染电商扶贫-岭南优品
		renderPoorServiceLingnan: function(opts) {
			var self = this,
				curLevel = opts.curLevel || DEFAULT_LEVEL,
				curAreaID = opts.curAreaID || DEFAULT_AREA_ID,
				defaultTime = $("#lingnanSelect").attr("selected_value"),
				lingnanTime = opts.time || defaultTime;

			ajax({
				url: URLS.URL_DATA_POOR_SERVICE_INDEX_DATA_LINGNAN,
				data: {
					level: curLevel,
					id: curAreaID,
					time: lingnanTime
				},
				success: function(data) {
					var code = data.code,
						d = data.data;
					if (code == 0 && d) {
						$('#indexLingnan').html(template('tplIndexLingnan', d));
						//图形
						echarts.init(document.getElementById('indexLingnanChart')).setOption(commonEchartConfig[d.chart_config.convert_method](d.chart_config.config));

						self.bindSelectArea();
						//处理“电商扶贫”select的数据
						// var selectData=d
					}
				},
				error: function() {
					// self.renderPoorEastWestIndex();
				}
			})
		},
		bindIndexChartTab: function() {

			var $chartTab = $('.chart-wrap-50p .blue-tab li');

			$chartTab.on('click', function(event) {
				var $this = $(this),
					targetChart = $this.attr("data-target"),
					$targetChart = $this.parents('.chart-wrap-50p').find('#' + targetChart);

				$this.siblings().removeClass('active');
				$this.addClass('active');

				$targetChart.siblings().addClass('hide');
				$targetChart.removeClass('hide')
			});
		},
		bindSelectArea: function() {
			var self = this;

			self.$poorServiceWrap
				.off('click', '.js-select-box', self.areaSelect.toggle)
				.off('click', '.js-select-list li', self.areaSelect.choose);

			self.$poorServiceWrap
				//toggle select
				.on('click', '.js-select-box', self.areaSelect.toggle)
				//选中
				.on('click', '.js-select-list li', self.areaSelect.choose)
		},
		//select box效果
		areaSelect: {
			toggle: function() {
				var $this = $(this);
				var $selectList = $this.find('.select-list');
				var $otherSelectBox = $(".select-box").not(this);

				$this.toggleClass('selected');
				$selectList.toggle();
				$this.find('.arrow').toggleClass('arrow-on');
				$otherSelectBox.removeClass('selected').find('.select-list').hide();
				$otherSelectBox.find('.arrow').removeClass('arrow-on');
				bindJsp();
				return false;

				function bindJsp() {
					var w = $selectList.innerWidth();
					var h = $selectList.innerHeight();

					w = w > 150 ? 150 : w;
					if ($selectList.find('.jspPane').length < 1) {
						$selectList.width(w);
					}

					if (h > 320) {
						$selectList.jScrollPane(commonJSPConfig)
					}
				}
			},
			/**
			 * 选中某个区域
			 */
			choose: function() {
				var $this = $(this),
					self = $.poorService;

				//首页-选择区域
				if ($this.parents("#areaBarrio").length > 0) {
					var opts = {
						curAreaID: $this.attr('data-id'),
						curLevel: $this.closest('.js-select-box').attr('level')
					};

					self.renderPoorServiceIndex(opts);
				}
				//首页-电商扶贫-选择年份
				if ($this.parents("#lingnanSelect").length > 0) {
					var opts = {};
					var $thisSelectBox=self.$poorServiceIndexWrap.find("#areaBarrio .js-select-box");
					var selectLevelId=self.getSelectLevelId($thisSelectBox);

					opts.time = $this.attr('data-value');
					opts=$.extend(opts,selectLevelId);

					self.renderPoorServiceLingnan(opts);
				}

				//弹窗-选择区域
				if ($this.parents("#areaBarrioPopup").length > 0) {
					var $popupTitle=self.$popupPoorServiceDetail.find("#popupTitle");
						
					var opts = {
						curAreaID: $this.attr('data-id'),
						curLevel: $this.closest('.js-select-box').attr('level'),
						subject:$popupTitle.attr('data-subject'),
						status:$popupTitle.attr('data-status')
					};

					self.renderPopupDetail(opts);
				}

				//弹窗-分类导航
				if ($this.parents("#typeSelect").length > 0) {
					var $popupTitle=self.$popupPoorServiceDetail.find("#popupTitle");
					var $thisSelectBox=self.$popupPoorServiceDetail.find("#areaBarrioPopup .js-select-box");
					var selectLevelId=self.getSelectLevelId($thisSelectBox);
					var opts = {
						type: $this.attr('data-value'),
						subject:$popupTitle.attr('data-subject'),
						status:$popupTitle.attr('data-status')
					};

					opts=$.extend(opts,selectLevelId);

					self.renderPopupSearchTable(opts);
				}
			}
		},
		//获取行政区域Level,Id
		getSelectLevelId:function($selectBox){
			var returnObj={};
			returnObj.curLevel = $selectBox.last().attr("level");
			returnObj.curAreaID = $selectBox.last().attr("selected_id");
			if (returnObj.curAreaID == "") {
				returnObj.curAreaID = $selectBox.eq(-2).attr("selected_id");
				returnObj.curLevel = $selectBox.eq(-2).attr("level");
			}
			return 	returnObj;		
		},
		//首页-详情按钮
		bindIndexDetail: function() {
			return 0;
			var self = this;

			self.$poorServiceIndexWrap.find('.js-detail-btn').on('click', function(event) {
				var $this=$(this),
					$thisSelectBox=self.$poorServiceIndexWrap.find("#areaBarrio .js-select-box");
				var	selectLevelId=self.getSelectLevelId($thisSelectBox),
					opts={
						subject:$this.parents('.chart-wrap-50p').attr('data-matter'),
						status:$this.parents('.chart-wrap-50p').find('.blue-tab li.active').attr('data-status')
					};
				    
				opts=$.extend(opts,selectLevelId);

				self.renderPopupDetail(opts);
				self.$popupPoorServiceDetail.show();
			});
		},
		/*详情弹窗-返回*/
		bindPopupClose: function() {
			var self = this;

			self.$popupPoorServiceDetail.find('.js-popup-close').on('click', function(event) {
				self.$popupPoorServiceDetail.hide();
			});
		},
		/*详情弹窗-搜索按钮*/
		bindSearchBtn: function() {
			var self = this;

			self.$popupPoorServiceDetail.find('#popupSearchBtn').on('click', function(event) {
				var $popupTitle=self.$popupPoorServiceDetail.find("#popupTitle"),
					$typeSelect=self.$popupPoorServiceDetail.find("#typeSelect"),
					$thisSelectBox=self.$popupPoorServiceDetail.find("#areaBarrioPopup .js-select-box");
				var selectLevelId=self.getSelectLevelId($thisSelectBox);
				var opts = {
					type: $typeSelect.attr("selected_value"),
					subject:$popupTitle.attr('data-subject'),
					status:$popupTitle.attr('data-status')
				};

				opts=$.extend(opts,selectLevelId);
				self.renderPopupSearchTable(opts);
			});
		},
		//显示导出进度
		exportWrap:{
			open:function(){
				$('#exportWrap').show();
			},
			close:function(){
				$('#exportWrap').hide();
			}
		},
		//渲染详细弹窗基本信息
		renderPopupDetail: function(opts) {
			var self=this,
				chSubject,
				chStatus;

			ajax({
				url: URLS.URL_DATA_POOR_SERVICE_ITEM_LIST_BASIC_INFO,
				data:{
					level:opts.curLevel,
					id:opts.curAreaID,
					subject:opts.subject,
					status:opts.status
				},
				success: function(data) {
					var code = data.code,
						d = data.data;
					if (code == 0 && d) {
						switch(opts.subject){
							case 'personal':chSubject='个人';
								break;
							case 'legal_person':chSubject='法人';
								break;
						}
						switch(opts.status){
							case 'doing':chStatus='在办';
								break;
							case 'done':chStatus='已办';
								break;
						}

						d.popupTitle=chSubject+'事项'+chStatus+'列表';
						self.$popupPoorServiceDetail.html(template('tplPopupPoorServiceDetail',d));
						self.bindPopupClose();
						self.bindSelectArea();
						self.bindSearchBtn();

						var $popupTitle=self.$popupPoorServiceDetail.find("#popupTitle");
						
						$popupTitle.attr('data-subject',opts.subject);
						$popupTitle.attr('data-status',opts.status);

						//渲染搜索结果
						var $typeSelect=self.$popupPoorServiceDetail.find("#typeSelect");
						opts.type=$typeSelect.attr("selected_value");
						self.renderPopupSearchTable(opts);
					}
				},
				error: function() {
					// self.renderPoorEastWestIndex();
				}
			})
		},
		//渲染详细弹窗-搜索表
		renderPopupSearchTable: function(opts) {
			var self=this;

			ajax({
				url: URLS.URL_DATA_POOR_SERVICE_ITEM_LIST_SEARCH,
				data:{
					level:opts.curLevel,
					id:opts.curAreaID,
					subject:opts.subject,
					status:opts.status,
					type:opts.type || 1,
					keyword:$("#keywordInput").val() || '',
					page:opts.table_page || 1
				},
				success: function(data) {
					var code = data.code,
						d = data.data;
					if (code == 0 && d) {
						var $popupSearchTable=self.$popupPoorServiceDetail.find("#popupSearchTable");
						var $searchResultCount=self.$popupPoorServiceDetail.find("#searchResultCount");
						var $noneSearch=self.$popupPoorServiceDetail.find('.none-search');

						$searchResultCount.text(d.amount_total);
						if (d.amount_total < 1) {
							$noneSearch.show();
							$popupSearchTable.hide();
						} else {
							$popupSearchTable.html(template('tplPopupSearchTable', d));

							//绑定页脚事件
							if (d.table.pagination) {
								bindPagination(d.table, opts);
							}

							if (!$popupSearchTable.data('action-bound')) {
								$popupSearchTable
									.on(CLICK_EV, 'td[data-action-type=country_file]', function (event) {
										var _id = $(this).data("action-area-id");
										$.poorFile.showCountryFile(_id);
									})
									.on(CLICK_EV, 'td[data-action-type=family_file]', function (event) {
										var _id = $(this).data("action-family-id");
										$.poorFile.showFamilyFile(_id);
									})
									//绑定导出事件
									.on(CLICK_EV, '.export-btn', function(event) {
										var exportUrl = $(this).data("export-url");

										exportTable(exportUrl);
									});

								$popupSearchTable.data('action-bound', true);
							}
						}
					}
				},
				error: function() {
					// self.renderPoorEastWestIndex();
				}
			})

			//导出表格
			function exportTable(exportUrl) {
				var INTERVAL=1000,
					$exportContent = $("#exportContent");

				ajax({
					url: exportUrl,
					success: function(data) {
						var code = data.code,
							d = data.data;

						if (code == 0 && d) {
							var taskId=d.export_task_id,
								timer = null;

							checkProcess(taskId);

							function renderExport(d) {
								$exportContent.html(template("tplExportContent", d));
							}

							//轮询导出状态
							function checkProcess(taskId){
								self.exportWrap.open();
								renderExport({
									progress: 0
								});

								load();

								function load() {
									ajax({
										url: URLS.URL_DATA_CHECK_EXPORT_RESULT,
										data: {
											export_task_id: taskId
										},
										success: function(data) {
											var code = data.code,
												d = data.data,
												msg = data.msg,
												stat = d.stat;

											if (code == 0 && d) {
												//导出中
												if(stat==1){
													renderExport(d);

													timer = setTimeout(load, INTERVAL);
												}
												else {
													//进度100%
													if (stat==2){
														Helpers.showAlertDialog({
															content: '生成完成',
															btnPosText: '下载',
															btnPosCB: function(modal) {
																window.open(d.url);
																modal.hide();
															}
														});
													}
													//导出错误
													else{
														Helpers.showAlertDialog({
															title: '导出错误',
															content: msg
														});
													}

													self.exportWrap.close();
												}
											}
										},
										error: function() {
											// load();
										}
									})
								}
							}
						}
					},
					error: function() {
						// exportTable(exportUrl);
					}
				})
			}

			//bind页脚事件
			function bindPagination(tableData,opts){
				var $tablePagination=self.$popupPoorServiceDetail.find(".table-pagination");
				var pageTotal=tableData.pagination.page_total,
					pageCur=tableData.pagination.page_cur,
					pageNext=tableData.pagination.page_cur + 1,
					pagePre=tableData.pagination.page_cur - 1;

				$tablePagination
					.on('click', '.arrow-first', function(event) {
						if(pageCur==1){
							return 0;
						}
						opts.table_page=1;
						self.renderPopupSearchTable(opts);
					})
					.on('click', '.arrow-end', function(event) {
						if(pageCur==pageTotal){
							return 0;
						}
						opts.table_page=pageTotal;
						self.renderPopupSearchTable(opts);
					})
					.on('click', '.arrow-left', function(event) {
						if(pagePre<1){
							return 0;
						}
						else{
							opts.table_page=pagePre;
						}
						self.renderPopupSearchTable(opts);
					})
					.on('click', '.arrow-right', function(event) {
						if(pageNext>pageTotal){
							return 0;
						}
						else{
							opts.table_page=pageNext;
						}
						self.renderPopupSearchTable(opts);
					})
			}
		}
	};

	$.poorService.init();
})