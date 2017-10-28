// TODO 未定型
// TODO Notice: 如需将此js的方法迁移到数据监控，务必注意打补丁可能带来的副作用
$(function () {
	var CLICK_EV = 'click',

		CLASS_ACTIVE = 'active',

		//二级弹窗
		DATA_MONITOR_SECOND_POPUP = {
			// 详细清单弹窗
			DETAIL_LIST: "second_popup_detail_list",
			// 区域数据弹窗
			AREA_DATA: "second_popup_area_data"
			// //详细清单弹窗基础信息
			// DETAIL_BASIC_INFO:"second_popup_detail_basic_info",
			// //详细清单弹窗参数信息
			// DETAIL_PARAMS_INFO:"second_popup_detail_params_info",
			// //详细清单弹窗表格
			// DETAIL_TABLE:"second_popup_detail_table",
			// //区域数据弹窗表格
			// AREA_DATA_TABLE:"second_popup_area_data_table"
		},

		extend = $.extend,

		$body = $('body'),
		$exportWrap,

		//显示导出进度
		instanceExportWrap = {
			open: function () {
				if (!$exportWrap) {
					$exportWrap = $('#exportWrap');
				}
				$exportWrap.show();
			},
			close: function () {
				$exportWrap.hide();
			}
		},
		win = window,
		Helpers = win.Helpers,
		ajax = Helpers.ajax,
		searchObject = Helpers.getSearchObject(),

		//基本参数
		areaLevel = searchObject.area_level,
		areaID = searchObject.area_id,

		STAT_MONITOR_TPL_NOT_LOAD = 0,
		STAT_MONITOR_TPL_LOADING = 1,
		STAT_MONITOR_TPL_LOADED = 2,
		monitorTplStat = STAT_MONITOR_TPL_NOT_LOAD;

	/**
	 * instance should have changeContent, getWinType, getCurTabType
	 */
	function renderDataMonitorTree($container, opts) {
		$container.html(template('tplDataMonitorTree'));

		var CLASS_ACTIVE = 'active',
			CLICK_EV = 'click',

			$treeWrapper = $container.find('.tree-wrapper'),
			$treeUl = $container.find('.tree-ul'),
			$icoQuest = $container.find('.ico-quest'),
			$tip = $container.find('.tip'),
			jsp = $container.data('jsp'),

			year = opts.year,
			level = opts.area_level,
			id = opts.area_id,
			wintype = opts.wintype,
			tabtype = opts.tabtype,
			instance = opts.instance;

		if (!jsp) {
			jsp = $treeWrapper.jScrollPane(commonJSPConfig)
				.data('jsp');
		}
		else {
			jsp.reinitialise();
		}

		function load() {
			showLoadingMask();

			ajax({
				url: URLS.URL_DATA_MONITOR_TREE,
				data: {
					year: year,
					level: level,
					id: id,
					wintype: wintype,
					tabtype: tabtype
				},
				success: function (data) {
					if (wintype != instance.getWinType() || tabtype != instance.getCurTabType()) {
						return;
					}

					var code = data.code,
						d = data.data;

					if (code == 0 && d) {
						var tip = d.tip,
							matchNodeID = d.match_node_id,
							tree = d.tree,

							treeApi = $.fn.zTree.init($treeUl, {
								async: {
									enable: true,
									url: URLS.URL_DATA_MONITOR_SUBTREE,
									dataType: 'json',
									type: 'get',
									autoParam: ['area_id=id', 'area_level=level'],
									otherParam: {
										year: year,
										wintype: wintype,
										tabtype: tabtype
									},
									dataFilter: function (treeId, parentNode, responseData) {
										var code = responseData.code,
											d = responseData.data;
										if (code == 0 && d) {
											return d;
										}
										else {
											// DO NOTHING
											return null;
										}
									}
								},
								view: {
									nameIsHTML: true,
									showTitle: false,
									showIcon: false
								},
								callback: {
									onClick: function (event, treeId, treeNode, clickFlag) {
										changeContent(treeNode);
									},
									// TODO 设计稿可能有缺陷，如果按子节点是特殊一级来做的话，层级不定的同级子节点会变得很难看
									// onNodeCreated: function(event, treeId, treeNode) {
									// 	if(treeNode.pId && !treeNode.isParent) {
									// 		// TODO 很恶心的做法，但这个插件好像没什么别的方法可以设叶class
									// 		$('#' + treeNode.tId).addClass('leaf-node');
									// 	}
									// },
									onExpand: function () {
										jsp.reinitialise();
									},
									onCollapse: function () {
										jsp.reinitialise();
									},
									onAsyncError: function (event, treeId, treeNode) {
										Helpers.showCommonErrorDialog(function () {
											// treeApi.reAsyncChildNodes(treeNode);
										});
									}
								}
							}, tree),
							matchedNode = treeApi.getNodeByParam('id', matchNodeID);
						// matchedParentNode = matchedNode && matchedNode.getParentNode();
						
						changeContent(matchedNode);

						// if(matchedParentNode) {
						// 	treeApi.expandNode(matchedParentNode, true, false, false, true);
						// }

						//打开指定节点的子节点列表，且选中该节点
						if (matchedNode) {
							treeApi.expandNode(matchedNode, true, false, false, true);
							treeApi.selectNode(matchedNode);
						}

						$tip.html(tip);

						// $container.on(CLICK_EV, function() {
						// 	$icoQuest.removeClass(CLASS_ACTIVE);
						// });
						//
						// $icoQuest.on(CLICK_EV, function(e) {
						// 	$icoQuest.toggleClass(CLASS_ACTIVE);
						//
						// 	e.stopPropagation();
						// })
						// 	.show();
						$icoQuest.show();

						jsp.reinitialise();
					}
					else {
						hideLoadingMask();
					}

					function changeContent(node) {
						if (node) {
							var areaID = node.area_id,
								areaLevel = node.area_level;

							instance.treeCurLevel = areaLevel;
							instance.treeCurAreaID = areaID;

							if (instance && instance.renderContent) {
								//TODO 不是好做法, too dirty
								instance.renderContent(extend({}, opts, {
									area_id: areaID,
									area_level: areaLevel,
									target: opts.configOrder ? "config_order" : wintype + "_" + tabtype
								}));
							}
						}
					}
				},
				error: function () {
					hideLoadingMask();

					// load();
				}
			});
		}

		load();
	}

	function loadMonitorTpl(successCB) {
		if (monitorTplStat == STAT_MONITOR_TPL_NOT_LOAD) {
			monitorTplStat = STAT_MONITOR_TPL_LOADING;
			load();
		}
		else {
			if (successCB) {
				successCB();
			}
		}

		function load() {
			$.ajax({
				url: URLS.URL_TPL_DATA_MONITOR,
				async: false,
				success: function (tpl) {
					if (monitorTplStat != STAT_MONITOR_TPL_LOADED) {
						$('body').append(tpl);

						monitorTplStat = STAT_MONITOR_TPL_LOADED;
					}

					if (successCB) {
						successCB();
					}
				},
				error: function () {
					if (monitorTplStat < STAT_MONITOR_TPL_LOADED) {
						monitorTplStat = STAT_MONITOR_TPL_NOT_LOAD;

						load();
					}
				}
			});
		}

	}

	function createDataMonitor(opts) {
		opts = extend({
			hasBack: true
		}, opts);

		var mixObj = opts.mix,
			mixSecondObj = opts.mixSecond;
		// id = opts.id,
		// ID_POPUP = 'popup' + id + 'Monitor';

		var instanceSecondPopup = extend({
				curWinType: null,
				curTabType: null,
				$popupWrap: null,
				$popupContent: null,
				$popupTitle: null,
				$popupTable: null,

				/**
				 * 容器初始化
				 * @param opts {Object}: wintype, tabtype
				 */
				init: function (opts) {
					var self = this,
						$secondPopupWrap = self.$popupWrap;

					self.curWinType = opts.wintype;
					self.curTabType = opts.tabtype;

					if (!$secondPopupWrap) {
						self.$popupWrap = $secondPopupWrap = $($('#tplMonitorSecondPopupWin').html());
						$body.append($secondPopupWrap);

						$secondPopupWrap
							.on(CLICK_EV, '.js-second-popup-close', function () {
								self.close();
							});
					}

					var $secondPopup = $(template('tplSecondPopup', {}));

					$secondPopupWrap
						.empty()
						.append($secondPopup);

					self.$popupContent = $secondPopup;
					self.$popupTitle = $secondPopup.find('.second-popup-title');
					self.$popupTable = $secondPopup.find('.second-popup-table');
				},
				clean: function () {
					var self = this,
						$secondPopupWrap = self.$popupWrap;

					$secondPopupWrap.remove();
					self.$popupWrap = null;
				},
				/**
				 * 打开详情窗
				 * @param opts {Object}: wintype, tabtype
				 */
				openDetail: function (opts) {
					var self = this;

					self.init(opts);
					var $secondPopupWrap = self.$popupWrap;

					self.areaSelect.render(opts, function () {
						$secondPopupWrap.show();
						self.bindSelectArea();

						self.renderContent(extend({}, opts, { target: DATA_MONITOR_SECOND_POPUP.DETAIL_LIST }));
					});

				},
				/**
				 * 打开区域数据窗
				 * @param opts {Object}: wintype, tabtype
				 */
				openAreaData: function (opts) {
					var self = this;

					self.init(opts);
					var $secondPopupWrap = self.$popupWrap;

					$secondPopupWrap.show();

					self.renderContent(extend({}, opts, { target: DATA_MONITOR_SECOND_POPUP.AREA_DATA }));
				},
				close: function () {
					var self = this;

					self.clean();
				},
				/**
				 * 渲染内容统一方法，再由它分配调用具体的渲染内容方法
				 * @param opts
				 */
				renderContent: function (opts) {
					var self = this;

					renderContent.call(self, opts);
				},
				bindSelectArea: function () {
					$body.on('click', hideScopeList);
					function hideScopeList(){
						var $selectBox=$(".select-box");
						$selectBox.removeClass('selected').find('.select-list').hide();
						$selectBox.find('.arrow').removeClass('arrow-on');
					}
					var self = this,
						$secondPopup = self.$popupContent;

					$secondPopup
						.off(CLICK_EV, '.js-select-box', this.areaSelect.toggle)
						.off(CLICK_EV, '.js-select-list li', this.areaSelect.choose);

					$secondPopup
					//toggle select
						.on(CLICK_EV, '.js-select-box', this.areaSelect.toggle)
						//选中
						.on(CLICK_EV, '.js-select-list li', this.areaSelect.choose);
				},
				renderChartTable: function (opts, callback) {
					var self = this,
						$secondPopup = instanceSecondPopup.$popupContent,
						$secondPopupTitle = instanceSecondPopup.$popupTitle,
						$thisCheckArea = $secondPopup.find('.check-area-wrap'),
						checkArgObj = {},//当前选择的参数

						winType = self.curWinType,
						tabType = self.curTabType,

						defaultArgObj = {//默认的参数
							level: opts.area_level,
							id: opts.area_id,
							year: opts.year,
							wintype: winType,
							tabtype: tabType,
							page: opts.table_page || 1
						};

					opts.tableDetail = false;

					checkArgObj = getCheckAreaActiveObj($thisCheckArea);

					showLoadingMask();

					ajax({
						url: opts.url,
						data: extend(defaultArgObj, checkArgObj),
						success: function (data) {
							var code = data.code,
								d = data.data;

							if (code == 0 && d) {
								if (opts.secondPopupType == "area_data") {
									d.hasSelectArea = false;

									$secondPopup.find(".basic-info-wrap")
										.html(template("tplSecondPopupBasicInfo", d));

									$secondPopupTitle.text(d.win_title);
								}

								self.renderConfigTable(extend({}, opts, {
									secondPopup: true,
									tableData: d.table,
									tableDetail: opts.tableDetail,
									tableTplId: "secondPopupTable"
								}));

								$.isFunction(callback) && callback();
							}
							hideLoadingMask();
						},
						error: function () {
							hideLoadingMask();
						}
					});
				},
				buildConfigCheckArea: function (opts) {
					var self = this;

					buildConfigCheckArea.call(self, opts);
				},
				/**
				 * [渲染后台配置表模板]
				 */
				renderConfigTable: function (opts) {
					var self = this;

					renderConfigTable.call(self, opts);
				},
				//后端配置-选择区域
				renderConfigCheckArea: function (opts, callback) {
					var self = this,

						winType = self.curWinType,
						tabType = self.curTabType;

					renderConfigCheckArea.call(self, opts, {
						wintype: winType,
						tabtype: tabType,
						callback: function () {
							if (callback) {
								callback();
							}
						}
					});
				},
				//渲染二级弹窗-详细清单
				renderSecondPopupDetailList: function (opts) {
					var self = this;

					opts = extend({}, opts, {
						url: URLS.URL_DATA_MONITOR_DETAIL_TABLE,
						params_url: URLS.URL_DATA_MONITOR_DETAIL_PARAMS_INFO,
						secondPopupType: "detail_list"
					});
					self.buildConfigCheckArea(opts);
				},
				//渲染二级弹窗-区域数据
				renderSecondPopupAreaData: function (opts) {
					var self = this;

					opts = extend({}, opts, {
						url: URLS.URL_DATA_MONITOR_AREA_DATA_TABLE,
						secondPopupType: "area_data"
					});
					self.renderChartTable(opts);
				},
				//绑定选择区域事件
				bindCheckArea: function (opts, rowKeyArr, callback) {
					var self = this;

					bindCheckArea.call(self, opts, rowKeyArr, callback);
				},
				//select box效果
				areaSelect: {
					toggle: function () {
						var $this = $(this);
						var $selectList = $this.find('.select-list');
						var $sibSelectBox = $this.parents(".select-area").find('.js-select-box').not(this);

						if($this.hasClass('forbid')){
							return 0;
						}

						$this.toggleClass('selected');
						$selectList.toggle();
						$this.find('.arrow').toggleClass('arrow-on');
						$sibSelectBox.removeClass('selected').find('.select-list').hide();
						$sibSelectBox.find('.arrow').removeClass('arrow-on');
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
								$selectList.jScrollPane(commonJSPConfig);
							}
						}
					},
					/**
					 * 选中某个区域
					 */
					choose: function () {
						var $this = $(this),
							$secondPopup = instanceSecondPopup.$popupContent,
							$selectArea = $secondPopup.find(".basic-info-wrap .select-area");

						//选择区域
						if ($this.parents("#areaBarrio").length > 0) {
							var curAreaID = $this.attr('data-id');
							var curLevel = $this.closest('.js-select-box').attr('level');
							var year = $selectArea.find("#selectYear .select-box>span").attr("data-value");

							renderParamsTable();
						}
						//选择年份
						if ($this.parents("#selectYear").length > 0) {
							var year = $this.attr('data-year');

							var curLevel = $selectArea.find("#areaBarrio .js-select-box").last().attr("level");
							var curAreaID = $selectArea.find("#areaBarrio .js-select-box").last().attr("selected_id");
							if (curAreaID == "") {
								curAreaID = $selectArea.find("#areaBarrio .js-select-box").eq(-2).attr("selected_id");
								curLevel = $selectArea.find("#areaBarrio .js-select-box").eq(-2).attr("level");
							}

							renderParamsTable();
						}

						function loadBasicInfo() {
							showLoadingMask();

							ajax({
								url: URLS.URL_DATA_MONITOR_DETAIL_BASIC_INFO,
								data: {
									level: curLevel,
									id: curAreaID,
									year: year,
									wintype: instanceMonitor.getWinType(),
									tabtype: instanceMonitor.getCurTabType()
								},
								success: function (data) {
									var code = data.code,
										d = data.data;
									if (code == 0 && d) {
										d.hasSelectArea = true;

										$secondPopup
											.find('.basic-info-wrap')
											.html(template('tplSecondPopupBasicInfo', d));
									}
									else {
										hideLoadingMask();
									}
								},
								error: function () {
									hideLoadingMask();
									// loadBasicInfo();
								}
							});
						}

						loadBasicInfo();

						function renderParamsTable() {

							var opts = {
								url: URLS.URL_DATA_MONITOR_DETAIL_TABLE,
								params_url: URLS.URL_DATA_MONITOR_DETAIL_PARAMS_INFO,
								secondPopup: true,
								secondPopupType: "detail_list",
								area_level: curLevel,
								area_id: curAreaID,
								year: year,
								wintype: instanceSecondPopup.curWinType,
								tabtype: instanceSecondPopup.curTabType
							};
							instanceSecondPopup.buildConfigCheckArea(opts);
						}
					},
					/**
					 * 初始化select
					 */
					render: function (opts, callback) {
						var $secondPopupTitle = instanceSecondPopup.$popupTitle,
							$secondPopup = instanceSecondPopup.$popupContent,

							checkArgObj = {}, //当前选择的参数
							defaultArgObj = { //默认的参数
								level: opts.area_level,
								id: opts.area_id,
								year: opts.year,
								wintype: instanceSecondPopup.curWinType,
								tabtype: instanceSecondPopup.curTabType,
								page: opts.table_page || 1
							};

						function load() {
							showLoadingMask();

							ajax({
								url: URLS.URL_DATA_MONITOR_DETAIL_BASIC_INFO,
								data: extend(defaultArgObj, checkArgObj),
								success: function (data) {
									var code = data.code,
										d = data.data;
									d.AREA_SCOPE = AREA_SCOPE;
									if (code == 0 && d) {
										d.hasSelectArea = true;

										$secondPopupTitle.text(d.win_title);

										$secondPopup
											.find('.basic-info-wrap')
											.html(template('tplSecondPopupBasicInfo', d));

										$.isFunction(callback) && callback();
									}
									else {
										hideLoadingMask();
									}
								},
								error: function () {
									hideLoadingMask();
									// load();
								}
							});
						}

						load();
					}
				}

			}, mixSecondObj),

			instanceMonitor = extend({
				$popupWrap: null,
				$popupHeader: null,
				$popupTree: null,
				$popupContent: null,
				curAreaName: '',//当前区域名称
				treeCurLevel: areaLevel || DEFAULT_LEVEL,//左侧树选中的-区域级别
				treeCurAreaID: areaID || DEFAULT_AREA_ID,//左侧树选中的-区域ID

				init: function (onLoad) {
					var self = this;

					loadMonitorTpl(function () {
						if (!self.$popupWrap) {
							var $popup = $($('#tplMonitorPopupWin').html());

							$body.append($popup);

							var $monitorHeader = $popup.find('.monitor-header'),
								$monitorTree = $popup.find('.monitor-tree'),
								$monitorContent = $popup.find('.monitor-content');

							self.$popupWrap = $popup;
							self.$popupHeader = $monitorHeader;
							self.$popupTree = $monitorTree;
							self.$popupContent = $monitorContent;
						}

						if (onLoad) {
							onLoad();
						}
					});
				},
				clean: function () {
					var self = this,
						$popup = self.$popupWrap;

					$popup.remove();
					self.$popupWrap = null;
				},
				//TODO
				//绑定顶部事件：选择年份、导航、返回
				bindHeader: function () {
					var self = this,
						$popup = self.$popupWrap,
						$monitorHeader = self.$popupHeader;

					$popup.off(CLICK_EV).on(CLICK_EV, '.js-monitor-close', self.popup.close);
					//头部标签页导航切换
					var $headerNav = $monitorHeader.find('.monitor-nav');
					$headerNav.on(CLICK_EV, 'li', function (event) {
						var $this = $(this),
							opts = {};

						opts.configOrder = $this.data("config-order");

						$this.siblings().removeClass('active');
						$this.addClass('active');


						self.renderTree(opts);
					});
					//头部的年份切换
					var $headerYearTab = $monitorHeader.find('.tab-year');
					$headerYearTab.on(CLICK_EV, 'i', function (event) {
						var $this = $(this),
							opts = {};

						if ($this.hasClass('js-forbid')) {
							return 0;
						}
						else {
							opts.configOrder = $headerNav.find("li.active").data("config-order");
							self.renderTree(opts);
						}
					});
				},
				//弹窗操作
				popup: {
					open: function () {
						var self = instanceMonitor,
							$popup = self.$popupWrap;

						$popup.show();
					},
					close: function () {
						var self = instanceMonitor;

						self.clean();
					}
				},
				secondPopup: instanceSecondPopup,
				//获取窗口类型
				getWinType: function () {
					var self = this,
						$monitorHeader = self.$popupHeader;

					return $monitorHeader.find('.header-title').data("win-type");
				},
				//获取header的年份
				getCurYear: function () {
					var self = this,
						$monitorHeader = self.$popupHeader;

					return $monitorHeader.find('.tab-year .active').data("value");
				},
				//获取header标签页类型
				getCurTabType: function () {
					var self = this,
						$monitorHeader = self.$popupHeader,
						tabType;

					tabType = $monitorHeader.find('.monitor-nav .active').data("tab-type");

					return tabType;
				},
				/**
				 * [生成选择区域]
				 * @Author   luoJiangFeng
				 * @DateTime 2017-03-16
				 * @param    {[数组]}     rowKeyArr      [单行的key数组，key取值见api说明或components.js]
				 * @param    {[对象]}     activePositionObj [设置对应“行”的默认选中位置,多选时传数组(不传时全部默认为首位),{行key:value}]
				 *
				 * @return   {[字符串]}                     [html代码]
				 * @example  buildCheckRadioArea(["scopeType","poorAttr","laborAttr","statusAttr"],{laborAttr:2,statusAttr:3})
				 */
				buildCheckAreaHtml: function (rowKeyArr, activePositionObj) {
					var buildCheckRow = $.components.buildCheckRow;
					var _html = '<table class="check-area-table">';
					activePositionObj = activePositionObj || { default: 0 };

					for (var i = 0; i < rowKeyArr.length; i++) {
						var tag = 0;
						if (typeof rowKeyArr[i] == "object") {

							for (var key in activePositionObj) {
								if (key == rowKeyArr[i].rowKey) {
									_html = _html + buildCheckRow(rowKeyArr[i], activePositionObj[key]);
									tag = 1;
									break;
								}
							}
						}
						else {
							for (var key in activePositionObj) {
								if (key == rowKeyArr[i]) {
									_html = _html + buildCheckRow(rowKeyArr[i], activePositionObj[key]);
									tag = 1;
									break;
								}
							}
						}
						if (tag == 0) {
							_html = _html + buildCheckRow(rowKeyArr[i]);
						}


					}
					return _html + '</table>';
				},

				//绑定选择区域事件
				bindCheckArea: function (opts, rowKeyArr, callback) {
					var self = this;

					bindCheckArea.call(self, opts, rowKeyArr, callback);
				},
				/**
				 * [生成选择区域]
				 * @Author   luoJiangFeng
				 * @DateTime 2017-03-17
				 * @param    {[type]}     opts              [description]
				 * @param    {[对象]}    checkAreaOpts              [只针对CheckArea的额外参数]
				 * @param    {[数组]}     rowKeyArr            [单行的key数组，key取值见api说明或components.js]
				 * @param    {[对象]}     activePositionObj [设置对应“行”的默认选中位置,多选时传数组(不传时全部默认为首位),{行key:value}]
				 * @param    {[函数]}     handler          [如有，则对应行会接管切换后的行为]
				 * @param    {[函数]}     callback          [重新渲染chartable时的回调函数]
				 * @return   {[type]}                       [description]
				 */
				buildCheckArea: function (opts, checkAreaOpts) {
					//TODO 由于这里的opts的部分参数（url和tplID）可能需要由外部更改，直接引用对象，待分离
					checkAreaOpts = extend({}, checkAreaOpts);

					var self = this,
						$monitorContent = self.$popupContent,
						rowKeyArr = checkAreaOpts.rowKeyArr,
						activePositionObj = checkAreaOpts.activePositionObj,
						callback = checkAreaOpts.callback,

						$thisCheckArea = $monitorContent.find('.check-area-wrap');

					//opts自带checkActiveObj的话则以opts的优先
					var checkAreaHtml = self.buildCheckAreaHtml(rowKeyArr, opts.checkActiveObj || activePositionObj);

					$thisCheckArea.html(checkAreaHtml);
					self.renderContentBasicInfo(opts);
					self.bindCheckArea(opts, rowKeyArr, callback);
				},
				//后端配置的选择区域
				buildConfigCheckArea: function (opts) {
					var self = this;

					buildConfigCheckArea.call(self, opts);

					self.renderContentBasicInfo(opts);
				},
				/**
				 * 渲染窗口外壳及调用渲染方法
				 * 调用顺序：
				 * renderWin -> renderTree -> renderContent
				 * @param opts
				 */
				renderWin: function (opts) {
					var self = this,
						$monitorHeader = self.$popupHeader;

					showLoadingMask();

					var params = {
						level: opts.area_level,
						id: opts.area_id
					};

					if (opts.year) {
						params.year = opts.year;
					}

					ajax({
						url: URLS.URL_DATA_MONITOR_BASIC_INFO,
						data: params,
						success: function (data) {
							var code = data.code,
								d = data.data;
							d = extend({}, d, opts.target, {
								hasBack: opts.hasBack
							});
							if (code == 0 && d) {
								$monitorHeader.html(template('tplMonitorHeader', d));
								var $tabSwitch = $monitorHeader.find(".tab-switch");
								$.components.tabSwitch($tabSwitch, d.years.available_years, d.years.selected_value);
								self.bindHeader();
								self.renderTree(opts);
							}
							else {
								hideLoadingMask();
							}
						},
						error: function () {
							hideLoadingMask();
							// self.renderWin( opts);
						}
					});

				},
				/**
				 * 渲染窗口左边树
				 * @param opts
				 */
				renderTree: function (opts) {
					var self = this,
						$monitorTree = self.$popupTree,

						opts = extend({
							year: self.getCurYear(),
							area_level: self.treeCurLevel,
							area_id: self.treeCurAreaID,
							wintype: self.getWinType(),
							tabtype: self.getCurTabType()
						}, opts);

					self.treeCurLevel = opts.area_level;
					self.treeCurAreaID = opts.area_id;

					renderDataMonitorTree($monitorTree, extend({
						instance: self
					}, opts));
				},
				//初始渲染数据监控弹窗
				showDataMonitor: function (opts) {
					var self = this;

					self.init(function () {
						self.renderWin(opts);
						self.popup.open();
					});
				},
				/**
				 * 渲染内容统一方法，再由它分配调用具体的渲染内容方法
				 * @param opts
				 */
				renderContent: function (opts) {
					var self = this;

					renderContent.call(self, opts);
				},
				//渲染monitor-content的基本布局
				renderPageContent: function (opts) {
					opts = extend({
						tplID: 'tplMonitorPageContentCommon'
					}, opts);

					var self = this,
						$monitorContent = self.$popupContent,
						tplID = opts.tplID;

					$monitorContent.html(template(tplID));
				},
				//TODO 调用的顺序有点问题，不严格要求先不管
				renderContentBasicInfo: function (opts) {
					var self = this,
						thisFunc = 'render' + opts.TabName,

						$monitorContent = self.$popupContent,
						tabType = self.getCurTabType();

					var $thisCheckArea = $monitorContent.find('.check-area-wrap');

					showLoadingMask();

					ajax({
						url: URLS.URL_DATA_MONITOR_CONTENT_BASIC_INFO,
						data: {
							level: opts.area_level,
							id: opts.area_id,
							year: opts.year
						},
						success: function (data) {
							if (tabType != self.getCurTabType()) {
								return;
							}

							var code = data.code,
								d = data.data;

							if (code == 0 && d) {
								$thisCheckArea.find("[data-key='scope'] .radio").each(function (index, el) {
									var $this = $(this);
									var key = index + 1;

									if (!d.scope_available[key]) {
										$this.addClass('disabled');
									}
								});
							}
							else {
								hideLoadingMask();
							}
						},
						error: function () {
							hideLoadingMask();
							// self[thisFunc]();
						}
					});
				},
				//后端配置-选择区域
				renderConfigCheckArea: function (opts, callback) {
					var self = this,

						winType = self.getWinType(),
						tabType = self.getCurTabType();

					renderConfigCheckArea.call(self, opts, {
						wintype: winType,
						tabtype: tabType,
						callback: function () {
							if (callback) {
								callback();
							}
						}
					});
				},
				//渲染图+表
				renderChartTable: function (opts, callback) {
					var self = this,

						$monitorContent = self.$popupContent,
						$thisCheckArea,
						$thisChartTable,
						thisFunc = 'render' + opts.TabName,
						tplThisChartTable = opts.tplID || 'tpl' + opts.TabName + 'ChartTable',

						winType = getWinType(),
						tabType = getCurTabType();

					opts = extend({}, opts);

					function getWinType() {
						return opts.wintype || self.getWinType();
					}

					function getCurTabType() {
						return opts.tabtype || self.getCurTabType();
					}

					$thisCheckArea = $monitorContent.find('.check-area-wrap');
					$thisChartTable = $monitorContent.find('.chart-table-wrap');

					var checkArgObj = {},//当前选择的参数
						defaultArgObj = {//默认的参数
							level: opts.area_level,
							id: opts.area_id,
							year: opts.year,
							wintype: winType,
							tabtype: tabType,
							page: opts.table_page || 1
						};

					checkArgObj = getCheckAreaActiveObj($thisCheckArea);

					showLoadingMask();

					ajax({
						url: opts.url,
						data: extend(defaultArgObj, checkArgObj),
						success: function (data) {
							if (winType != getWinType() || tabType != getCurTabType()) {
								return;
							}

							var code = data.code,
								d = data.data;

							if (code == 0 && d) {
								//渲染统计图+表
								$thisChartTable.html(template(tplThisChartTable, d));
								self.renderConfigTable(extend({}, opts, {
									tableData: d.table,
									tableDetail: opts.tableDetail,
									tableTplId: opts.tableTplId
								}));
								renderChart(d);

								var $pageContentWrap = self.$popupContent.find('.page-content-wrap'),
									pageContentWrapJSP = $pageContentWrap.data('jsp');

								if (!pageContentWrapJSP) {
									pageContentWrapJSP = $pageContentWrap.jScrollPane(commonJSPConfig)
										.data('jsp');
								}
								else {
									pageContentWrapJSP.reinitialise();
								}

								$.isFunction(callback) && callback();

							}
							hideLoadingMask();
						},
						error: function () {
							hideLoadingMask();
							// self[thisFunc]();
						}
					});
					//渲染图
					function renderChart(d) {
						var chartArr = [];

						for (var key in d) {
							if (key.substring(0, 12) == "chart_config") {
								chartArr.push({
									name: key,
									data: d[key]
								});
							}
						}
						for (var i = 0; i < chartArr.length; i++) {
							var jsonData = d[chartArr[i].name];
							var chartName = chartArr[i].name.substring(13);
							if (opts.chartHeaderId) {
								var tplChartId = opts.chartHeaderId + $.components.underlineToCamel(chartName);
							}
							else {
								var tplChartId = opts.tabName + 'Chart' + $.components.underlineToCamel(chartName);
							}

							var $chart = $('#' + tplChartId);

							echarts.init($chart[0]).setOption(commonEchartConfig[jsonData.convert_method](jsonData.config));

							(function ($chart, jsonData) {
								//有“展开”
								var configExpand = jsonData.expand_config;
								if (configExpand) {
									var CLASS_EXPANDED = 'chart-expanded';

									var $chartItem = $chart.closest('.chart-item'),
										$chartItemTitle = $chartItem.find('.chart-item-title'),
										$chartItemExtra = $chartItem.find('.chart-item-extra'),
										$chartRow = $chartItem.closest('.chart-row'),
										$chartExpandItem = $([
											'<div class="chart-item chart-whole chart-expand">',
											'<div class="chart-item-title">', $chartItemTitle.html(), '</div>',
											'<div class="chart-item-extra">',
											'<div class="btn-cyan btn-collapse">收回</div>',
											'</div>',
											'<div class="chart"></div>',
											'</div>'
										].join(''));

									if (!$chartItemExtra.length) {
										$chartItemExtra = $('<div class="chart-item-extra"></div>');
										$chartItem.append($chartItemExtra);
									}

									$chartItemExtra.prepend('<div class="btn-cyan btn-expand">展开</div>');
									$chartRow.append($chartExpandItem);

									var $chartExpand = $chartExpandItem.find('.chart');
									var chartInstance;

									$chartItem.find('.btn-expand').on(CLICK_EV, function () {
										$chartRow.addClass(CLASS_EXPANDED);
										$chartExpandItem.show();

										// display为none时没有宽高
										if (!chartInstance) {
											chartInstance = echarts.init($chartExpand[0]);
											chartInstance.setOption(commonEchartConfig[jsonData.expand_convert_method](configExpand));
										}
									});

									$chartExpandItem.find('.btn-collapse').on(CLICK_EV, function () {
										$chartRow.removeClass(CLASS_EXPANDED);
										$chartExpandItem.hide();
									});
								}
							})($chart, jsonData);
						}
					}
				},
				/**
				 * [渲染后台配置表模板]
				 */
				renderConfigTable: function (opts) {
					var self = this;

					renderConfigTable.call(self, opts);
				},
				//渲染-后台配置排序
				renderConfigOrder: function (opts) {
					var self = this,
						tplID = 'tplConfigOrder';

					opts.url = URLS.URL_DATA_MONITOR_ORDER_TABLE;
					opts.params_url = URLS.URL_DATA_MONITOR_ORDER_BASIC_INFO;

					self.$popupContent.html(template(tplID));
					self.buildConfigCheckArea(opts);
					// self.renderChartTable(opts);
				}
			}, mixObj);

		return instanceMonitor;
	}


	//后端配置的选择区域
	function buildConfigCheckArea(opts) {
		var self = this;

		self.renderConfigCheckArea(opts, function () {
			self.bindCheckArea(opts);
		});
	}

	/**
	 * 后端配置-选择区域
	 * @param opts
	 * @param args {Object}: wintype, tabtype
	 */
	function renderConfigCheckArea(opts, args) {
		args = args || {};

		var self = this,
			thisFunc = 'render' + opts.TabName,

			$popupContent = self.$popupContent,
			$thisCheckArea,

			winType = args.wintype,
			tabType = args.tabtype,

			callback = args.callback;

		$thisCheckArea = $popupContent.find('.check-area-wrap');

		showLoadingMask();

		ajax({
			url: opts.params_url,
			data: {
				level: opts.area_level,
				id: opts.area_id,
				year: opts.year,
				wintype: winType,
				tabtype: tabType
			},
			success: function (data) {
				var code = data.code,
					d = data.data;

				if (code == 0 && d) {
					$thisCheckArea.html(template("tplConfigCheckArea", d));
					self.renderChartTable(opts);

					if (callback) {
						callback();
					}
				}
				else {
					hideLoadingMask();
				}
			},
			error: function () {
				hideLoadingMask();
				// self[thisFunc]();
			}
		});

	}

	/**
	 * [渲染后台配置表模板]
	 * @Author   luoJiangFeng
	 * @DateTime 2017-03-20
	 * @param    {[type]}     tableData   [表的数据]
	 * @param    {[boolean]}  tableDetail [表头是否有明细按钮]
	 */
	function renderConfigTable(opts) {
		opts = extend({
			tableDetail: false
		}, opts);

		var self = this,
			$popupContent = self.$popupContent,
			$tplTable,
			$thisChartTable;

		$tplTable = $popupContent.find('.config-table');
		$thisChartTable = $popupContent.find('.chart-table-wrap');

		opts.tableData.hasDetailBtn = opts.tableDetail;
		$tplTable.html(template("tplConfigTable", opts.tableData));
		bindPopupEvent();
		if (opts.tableData.pagination) {
			bindPagination();
		}

		var $popupWin = $popupContent;

		var $pageContentWrap = $popupWin.find('.page-content-wrap'),
			$monitorTbWrap = $popupWin.find('.monitor-tb-wrap'),
			pageContentJSP = $pageContentWrap.data('jsp'),
			monitorTbWrapJSP = $monitorTbWrap.data('jsp');

		if (!monitorTbWrapJSP) {
			monitorTbWrapJSP = $monitorTbWrap.jScrollPane(commonJSPConfig)
				.data('jsp');
		}
		else {
			monitorTbWrapJSP.reinitialise();
		}

		if (!pageContentJSP) {
			pageContentJSP = $pageContentWrap.jScrollPane(commonJSPConfig)
				.data('jsp');
		}
		else {
			pageContentJSP.reinitialise();
		}

		//Fix table size
		monitorTbWrapJSP.reinitialise();

		//bind 弹窗事件
		function bindPopupEvent() {
			if (!$thisChartTable.data('action-bound')) {
				$thisChartTable
					.on(CLICK_EV, 'td[data-action-type=area_data]', function (event) {
						var $elem = $(this),
							areaLevel = $elem.data('action-area-level'),
							areaID = $elem.data('action-area-id');

						//暂时只有一级能打开二级
						if (self.secondPopup) {
							self.secondPopup.openAreaData(extend({}, opts, {
								area_level: areaLevel,
								area_id: areaID
							}));
						}
					})
					.on(CLICK_EV, 'td[data-action-type=country_file]', function (event) {
						var _id = $(this).data("action-area-id");
						$.poorFile.showCountryFile(_id);
					})
					.on(CLICK_EV, 'td[data-action-type=family_file]', function (event) {
						var _id = $(this).data("action-family-id");
						$.poorFile.showFamilyFile(_id);
					})
					.on(CLICK_EV, 'td[data-action-type=file_detail]', function(event) {
						var _id = $(this).data("action-file-id");
						$.poorFile.showFileDetail(_id);
					})
					.on(CLICK_EV, '.detail-btn', function (event) {
						//暂时只有一级能打开二级
						if (self.secondPopup) {
							self.secondPopup.openDetail(opts);
						}
					})
					.on(CLICK_EV, '.export-btn', function (event) {
						var exportUrl = $(this).data("export-url");

						exportTable(exportUrl);
					});

				$thisChartTable.data('action-bound', true);
			}
		}

		//导出表格
		function exportTable(exportUrl) {
			var INTERVAL = 1000,
				$exportContent = $("#exportContent");

			ajax({
				url: exportUrl,
				success: function (data) {
					var code = data.code,
						d = data.data;

					if (code == 0 && d) {
						var taskId = d.export_task_id,
							timer = null;

						checkProcess(taskId);

						function renderExport(d) {
							$exportContent.html(template("tplExportContent", d));
						}

						//轮询导出状态
						function checkProcess(taskId) {
							instanceExportWrap.open();
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
									success: function (data) {
										var code = data.code,
											d = data.data,
											msg = data.msg,
											stat = d.stat;

										if (code == 0 && d) {
											//导出中
											if (stat == 1) {
												renderExport(d);

												timer = setTimeout(load, INTERVAL);
											}
											else {
												//进度100%
												if (stat == 2) {
													Helpers.showAlertDialog({
														content: '生成完成',
														btnPosText: '下载',
														btnPosCB: function (modal) {
															window.open(d.url);
															modal.hide();
														}
													});
												}
												//导出错误
												else {
													Helpers.showAlertDialog({
														title: '导出错误',
														content: msg
													});
												}

												instanceExportWrap.close();
											}
										}
									},
									error: function () {
										// load();
									}
								});
							}
						}
					}
				},
				error: function () {
					// exportTable(exportUrl);
				}
			});
		}

		//bind页脚事件
		function bindPagination() {
			var $tablePagination = $thisChartTable.find(".table-pagination");
			var pageTotal = opts.tableData.pagination.page_total,
				pageCur = opts.tableData.pagination.page_cur,
				pageNext = opts.tableData.pagination.page_cur + 1,
				pagePre = opts.tableData.pagination.page_cur - 1;

			$tablePagination
				.on(CLICK_EV, '.arrow-first', function (event) {
					if (pageCur == 1) {
						return 0;
					}
					opts.table_page = 1;
					self.renderChartTable(opts);
				})
				.on(CLICK_EV, '.arrow-end', function (event) {
					if (pageCur == pageTotal) {
						return 0;
					}
					opts.table_page = pageTotal;
					self.renderChartTable(opts);
				})
				.on(CLICK_EV, '.arrow-left', function (event) {
					if (pagePre < 1) {
						return 0;
					}
					else {
						opts.table_page = pagePre;
					}
					self.renderChartTable(opts);
				})
				.on(CLICK_EV, '.arrow-right', function (event) {
					if (pageNext > pageTotal) {
						return 0;
					}
					else {
						opts.table_page = pageNext;
					}
					self.renderChartTable(opts);
				});
		}
	}

	//获取当前选择区域选中参数
	function getCheckAreaActiveObj($thisCheckArea) {
		var checkArgObj = {};

		$thisCheckArea.find(".radio-row").each(function (index, el) {
			var $this = $(this),
				_key = $this.data("key"),
				_value = $this.find(".radio.active").data("value");

			checkArgObj[_key] = _value;
		});

		$thisCheckArea.find(".check-row").each(function (index, el) {
			var $this = $(this),
				_key = $this.data("key");

			checkArgObj[_key] = getCheckRowCheckedValues($this).join(",");
		});

		return checkArgObj;
	}

	function getCheckRowCheckedValues($checkRow) {
		var valueArr = [];

		$checkRow.find(".check.active").each(function (index, el) {
			var _value = $(this).data("value");

			if (_value) {
				valueArr.push(_value);
			}
		});

		return valueArr;
	}

	//绑定选择区域事件
	function bindCheckArea(opts, rowKeyArr, callback) {
		var self = this,
			$thisCheckArea,
			//TODO 临时方案，可能会改
			rowHandlers = {};

		if (rowKeyArr) {
			for (var i = 0, len = rowKeyArr.length; i < len; i++) {
				var item = rowKeyArr[i];
				if (typeof item == 'object' && item.handler) {
					rowHandlers[item.rowKey] = item.handler;
				}
			}
		}

		var $popupContent = self.$popupContent;
		$thisCheckArea = $popupContent.find('.check-area-wrap');

		var EVENT_BOUND = 'event-bound';

		//单选
		$thisCheckArea.find('.radio-row')
			.each(function () {
				var $radioRow = $(this),
					rowKey = $radioRow.attr('data-key');

				if (!$radioRow.data(EVENT_BOUND)) {
					$radioRow
						.on(CLICK_EV, ".radio", function (event) {
							var me = this,
								$this = $(this);

							if ($this.hasClass('disabled')) {
								return 0;
							}
							else {
								$this.siblings().removeClass('active');
								$this.addClass('active');
								var handler = rowHandlers[rowKey];

								if (handler) {
									handler.apply(me, arguments);
								}
								else {
									self.renderChartTable(opts, callback);
								}
							}
						});

					$radioRow.data(EVENT_BOUND, true);
				}
			});

		//多选（多选先不处理handler的问题）
		$thisCheckArea.find('.check-row')
			.each(function () {
				var $checkRow = $(this);

				var $checkAll = $checkRow.find('a[data-check-all]'),
					hasCheckAll = $checkAll.length > 0,
					$values = $checkRow.find('a:not(.disabled)').not('[data-check-all]');

				if (!$checkRow.data(EVENT_BOUND)) {
					$checkRow
						.on(CLICK_EV, '.check', function (event) {
							var $this = $(this),
								isCheckAll = $this[0].hasAttribute('data-check-all');

							if (!isCheckAll) {
								if ($this.hasClass('disabled')) {
									return 0;
								}
								else {
									$this.toggleClass(CLASS_ACTIVE);

									if (hasCheckAll) {
										if (getCheckRowCheckedValues($checkRow).length === $values.length) {
											$checkAll.addClass(CLASS_ACTIVE);
										}
										else {
											$checkAll.removeClass(CLASS_ACTIVE);
										}
									}

									self.renderChartTable(opts);
								}
							}
							else {
								var isActive = $this.hasClass(CLASS_ACTIVE);

								if (isActive) {
									$this.removeClass(CLASS_ACTIVE);
									$values.removeClass(CLASS_ACTIVE);
								}
								else {
									$this.addClass(CLASS_ACTIVE);
									$values.addClass(CLASS_ACTIVE);
								}

								self.renderChartTable(opts);
							}
						});

					$checkRow.data(EVENT_BOUND, true);
				}
			});
	}

	function renderContent(opts) {
		var self = this;

		var arr = opts.target.split('_');
		var arr2 = [];
		var fnName = '';
		for (var i = 0, len = arr.length; i < len; i++) {
			arr2.push(arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1));
		}

		opts.TabName = arr2.join('');
		opts.tabName = arr[0] + arr2.join('').substring(arr[0].length);
		fnName = 'render' + arr2.join('');

		console.log(fnName);
		self[fnName](opts);
	}

	$.monitorHelper = {
		DATA_MONITOR_SECOND_POPUP: DATA_MONITOR_SECOND_POPUP,
		renderDataMonitorTree: renderDataMonitorTree,
		createDataMonitor: createDataMonitor
	};
});