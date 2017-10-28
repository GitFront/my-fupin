var DATA_MAP_DATA_TYPE = {
		//贫困户数
		AMOUNT_POOR_FAMILY: 1,
		//贫困人口数
		AMOUNT_POOR_PEOPLE: 2,
		//脱贫户数
		AMOUNT_SUCCESS_FAMILY: 3,
		//脱贫人口数
		AMOUNT_SUCCESS_PEOPLE: 4,
		//脱贫户比例
		RATE_PEOPLE: 5,
		//脱贫率
		RATE_SUCCESS: 6
	},
	DEFAULT_DATA_MAP_DATA_TYPE = DATA_MAP_DATA_TYPE.AMOUNT_SUCCESS_FAMILY;

$(function() {
	var
	//======数据分析监控窗口======
		monitorHelper = $.monitorHelper,
		CONST_RADIO_VALUES = $.components.CONST_RADIO_VALUES,
		CONST_CHECK_VALUES = $.components.CONST_CHECK_VALUES,
		//选择行key
		ROW_KEY = $.components.ROW_KEY,

		POOR_ATTRIBUTE_VALUES = CONST_RADIO_VALUES.POOR_ATTRIBUTE,
		EDU_LEVELS_VALUES = CONST_RADIO_VALUES.EDU_LEVELS,
		LABOR_ATTRIBUTE_VALUES = CONST_RADIO_VALUES.LABOR_ATTRIBUTE,
		EXCEPTION_TYPE_VALUES = CONST_CHECK_VALUES.EXCEPTION_TYPE,
		//二级弹窗
		DATA_MONITOR_SECOND_POPUP = monitorHelper.DATA_MONITOR_SECOND_POPUP,
		//示范村建设
		DATA_MONITOR_EXAMPLE_COUNTRY_BUILDING = {
			TITLE: "示范村建设",
			TYPE: "example_country_building",
			TAB: [{
				NAME: "整治达标",
				VALUE: "renovate_standard"
			}
			// , {
			// 	NAME: "提升创建",
			// 	VALUE: "promote_creation"
			// }
			]
		},

		//======数据分析监控窗口======

		CLASS_LEVEL_COUNTRY = 'level-country',
		CLASS_SHOW = 'show',
		CLASS_ACTIVE = 'active',
		CLASS_HIDDEN = 'hidden',

		CLICK_EV = 'click',
		CHANGE_EV = 'change',

		win = window,

		extend = $.extend,
		param = $.param,
		ajax = Helpers.ajax,

		renderDataMonitorTree = monitorHelper.renderDataMonitorTree,

		$body = $('body'),
		$btnArea = $('#btnArea'),
		$btnMapIndex = $('#btnMapIndex'),
		$scopeList = $('#scopeList'),
		$btnAreaDesc = $('#btnAreaDesc'),
		$btnPoorFamilyList = $('#btnPoorFamilyList'),
		$poorFamilyListWrap = $('#poorFamilyListWrap'),

		//隐藏按钮
		//	$btnMapBack = $('#btnMapBack'),
		$scopeAreaName = $('#scopeAreaName'),
		$scopeList = $('#scopeList'),
		$btnCountryFile = $('#btnCountryFile'),
		$blockMapDataTypes = $('#blockMapDataTypes'),
		$blockBottom = $('#blockBottom'),
		$blockBars = $('#blockBars'),
		$blockAlarm = $('#blockAlarm'),
		$blockPoorAnalysisWrap = $('#blockPoorAnalysisWrap'),

		poorFile = $.poorFile,

		dynamicTimer = null,
		countDownTimer = null,
		curLevel = DEFAULT_LEVEL,
		curAreaID = DEFAULT_AREA_ID,
		currentScope = DEFAULT_AREA_SCOPE,
		currentAreaDesc = null;

	init();
	win.changeDataDistrict = changeDataArea;
	// changeDataArea();
	//页面初始加载信息由地图接管并显示loading，地图加载后再调用切换区域以加载统计界面并隐藏loading
	function init() {
		// renderPage();

		$btnCountryFile.on(CLICK_EV, function() {
			poorFile.showCountryFile(curAreaID);
		});

		//地图底部指标
		$scopeList.on('click', 'li', function(event) {
			var _index=$(this).attr("data-index");
			var _title=$(this).text();

			$btnMapIndex.find("strong").text(_title);
			$btnMapIndex.find(".icon").removeClass('on');

			loadMap(parseInt(_index));
		});


		$btnMapIndex
			.on(CLICK_EV, function(e) {
				e.stopPropagation();
				$(this).find(".icon").toggleClass('on');
				toggleScopeList();
			});
		//$btnMapBack
		//	.on(CLICK_EV, function () {
		//		if (typeof dataMapBack != 'undefined') {
		//			dataMapBack();
		//		}
		//	});
		$scopeList
			.on(CLICK_EV, function(e) {
				e.stopPropagation();
			})
			.on(CLICK_EV, 'li', function() {
				var $li = $(this),
					scope = $li.attr('data-scope');

				if (!$li.hasClass(CLASS_ACTIVE)) {
					$li.addClass(CLASS_ACTIVE)
						.siblings()
						.removeClass(CLASS_ACTIVE);

					currentScope = scope;

					changeMapDataScope(currentScope);

					// renderPage();

					hideScopeList();
				}
			});
		$body
			.on(CLICK_EV, hideScopeList);

		//贫困分析
		$blockPoorAnalysisWrap.on(CLICK_EV, '.link-more', function() {
			$.dataMonitor.showDataMonitor({
				target: DATA_MONITOR_TARGET_POOR_ANALYSIS
			});
		});

		//点击“一相当各个子项数据”跳转“一相当分析>六个指标”对应的页面
		$blockBars.on(CLICK_EV, '.vl-item', function() {
			var $elem = $(this),
				name = $elem.attr('data-name'),
				targetObj;

			switch (name) {
				case '道路硬化':
					targetObj = DATA_MONITOR_TARGET_ROAD;
					break;
				case '安全饮水':
					targetObj = DATA_MONITOR_TARGET_WATER;
					break;
				case '生活用电':
					targetObj = DATA_MONITOR_TARGET_ELECTRICITY;
					break;
				case '医疗设施':
					targetObj = DATA_MONITOR_TARGET_MEDICAL_FACILITY;
					break;
				case '广播电视':
					targetObj = DATA_MONITOR_TARGET_BROADCAST;
					break;
				case '网络覆盖':
					targetObj = DATA_MONITOR_TARGET_NET;
					break;
			}

			if (targetObj) {
				$.dataMonitor.showDataMonitor({
					target: targetObj
				});
			}
		});
		//点击“贫困识别存在xx异常的数字”跳转“贫困识别监控”页面
		//点击“建档立卡数据的数字”跳转“建档立卡异常记录监控”页面
		//点击“帮扶资金<30万的数字”跳转“帮扶资金监控”页面
		//点击“一年内未被走访的数字”跳转“贫困户走访监控”页面
		$blockAlarm.on(CLICK_EV, 'td b', function() {
			var $elem = $(this),
				target = $elem.attr('data-target'),
				targetObj;

			switch (target) {
				case 'alarmed_family':
					targetObj = DATA_MONITOR_TARGET_ALARMED_POOR;
					break;
				case 'alarmed_records':
					targetObj = DATA_MONITOR_TARGET_ALARMED_RECORDS;
					break;
				case 'alarmed_country':
					targetObj = DATA_MONITOR_TARGET_ALARMED_MONEY;
					break;
				case 'alarmed_family_not_visited':
					targetObj = DATA_MONITOR_TARGET_ALARMED_VISIT;
					break;
			}

			if (targetObj) {
				$.dataMonitor.showDataMonitor({
					target: targetObj
				});
			}
		});

		$blockMapDataTypes.on(CHANGE_EV, '[name="map_data_type"]', function() {
			var $elem = $(this);
			changeMapDataType($elem.val());
		});




		// $.poorFile.showCountryPhoto(5,"pop")

	}


	function toggleScopeList() {
		if ($scopeList.hasClass(CLASS_SHOW)) {
			hideScopeList();
		} else {
			showScopeList();
		}
	}

	function showScopeList() {
		$scopeList.addClass(CLASS_SHOW);
	}

	function hideScopeList() {
		$scopeList.removeClass(CLASS_SHOW);
	}

	function createChartElem() {
		return $('<div class="chart"></div>')
	}

	function renderPage() {
		var i = 1,
			mapLoaded = false;

		function onSuccess() {
			i--;
			_onSuccess();
		}

		function _onSuccess() {
			if (i == 0 && mapLoaded) {
				hideLoadingMask();
			}
		}

		function _onError() {
			hideLoadingMask();
		}

		renderStatic(onSuccess, _onError);

		//if (dynamicTimer) {
		//	clearInterval(dynamicTimer);
		//	dynamicTimer = null;
		//}
		//dynamicTimer = setInterval(function () {
		//	renderDynamic();
		//}, 10 * 1000);
		return {
			onSuccess: function() {
				mapLoaded = true;
				_onSuccess();
			},
			onError: _onError
		};
	}

	function onRenderPageErr() {
		renderPage();
	}

	function changeMapDataType(type) {
		if (typeof changeDataMapDataType != 'undefined') {
			changeDataMapDataType(type);
		}
	}

	function changeMapDataScope(scope) {
		if (typeof changeDataMapDataScope != 'undefined') {
			changeDataMapDataScope(scope);
		}
	}

	function changeDataArea(level, id, opts) {
		if (level) {
			curLevel = level;
			$.dataMonitor.treeCurLevel = curLevel;
		}
		if (id) {
			curAreaID = id;
			$.dataMonitor.treeCurAreaID = curAreaID;
		}
		opts = extend({
			hasCountryFile: false
		}, opts);

		var hasCountryFile = opts.hasCountryFile;

		//reset 切换区域后不用恢复到“全部”
		//		currentScope = DEFAULT_AREA_SCOPE;
		//		$scopeList.find('li')
		//			.removeClass(CLASS_ACTIVE)
		//			.eq(0).addClass(CLASS_ACTIVE);

		if (curLevel == LEVELS.COUNTRY) {

			$.poorFile.showCountryPhoto(id)
			
			$blockBottom.hide();
			
		} else {
			if($("#exampleCountryPhoto").length>0){
			$("#exampleCountryPhoto").hide();
			}
			$blockBottom.show();

		}

		//if (curLevel == LEVELS.PROVINCE) {
		//	$btnMapBack.hide();
		//}
		//else {
		//	$btnMapBack.show();
		//}

		return renderPage();
	}

	function updateAreaUI(d) {
		var areaName = d.area_name;
		currentAreaDesc = d.area_desc;

		$scopeAreaName.text(areaName);
		if (currentScope == DEFAULT_AREA_SCOPE) {
			$btnArea.text(areaName);
		}

		if (currentAreaDesc) {
			$btnAreaDesc
				.show();
			$poorFamilyListWrap.removeClass("down");

		} else {
			$btnAreaDesc.hide();
			$poorFamilyListWrap.addClass("down");
		}
	}

	function renderStatic(successCB, errorCB) {
		ajax({
			url: URLS.URL_DATA_EXAMPLE_COUNTRY_INDEX_STATIC_DATA,
			data: {
				level: curLevel,
				id: curAreaID,
				scope: currentScope
			},
			success: function(data) {
				var code = data.code,
					d = data.data;
				if (code == 0 && d) {
					updateAreaUI(d);
					/*渲染map*/
					renderTopIndex(extend({}, {
						reach_standard_country:d.reach_standard_country,
						finish_promote_country:d.finish_promote_country

					}, {
						area_level: d.area_level,
						LEVELS: LEVELS
					}));

					renderAverage(d.more20_reach_standard);
					renderGuarantee(extend({}, {
						compile_renovate_plan:d.compile_renovate_plan,
						renovate_three:d.renovate_three
					}));
					renderBars(d.base_facility_short);

					renderPoorAnalysis(d.more20_promote_creation);
					renderCapital(extend({}, {
						compile_promote_plan:d.compile_promote_plan,
						green_beautiful:d.green_beautiful
					}));


					bindBarHover();

					if (successCB) {
						successCB();
					}
				} else {
					if (errorCB) {
						errorCB();
					}
				}
			},
			error: function() {
				if (errorCB) {
					errorCB();
				}
				// onRenderPageErr();
			}
		});


		//渲染首页图表
		function renderIndexChart(d,chartId) {

			var jsonData = d;

			if(d.chart_type=="highchart"){
				var container=document.getElementById(chartId);
				var func=d.convert_method;

				eval(func+"(container,jsonData.data,jsonData.config)")
			}else if(d.chart_type=="D3"){
				var func=d.convert_method;
				eval(func+"(chartId,jsonData.data,jsonData.config)")
			}
			else{
				echarts.init(document.getElementById(chartId)).setOption(commonEchartConfig[jsonData.convert_method](jsonData.config));
			}

		}

		/*条形图*/
		function bindBarHover(){
			var $percentBar=$(".percent-bar");

			$percentBar.find("p").hover(function() {
				$(this).find(".tips").show()
			}, function() {
				$(this).parents(".percent-bar").find(".tips").hide()
			});
		}



		/*左边*/
		function renderAverage(average) {
			$('#blockAverage')
				.html(template('tplAverage', average));

		}

		function renderGuarantee(config) {
			var $blockGuarantee = $('#blockGuarantee');

			$blockGuarantee.html(template('tplGuarantee',config));
			renderIndexChart(config.compile_renovate_plan.chart_config_renovate_plan,"compileRenovatePlanChart")
			renderIndexChart(config.renovate_three.chart_config_renovate_three,"renovateThreeChart")
		}

		function renderBars(bars) {
			if (bars) {
				$blockBars
					.html(template('tplBars', bars));

				renderIndexChart(bars.chart_config_road,"facilityShortChart1")
				renderIndexChart(bars.chart_config_rubbish,"facilityShortChart2")
				renderIndexChart(bars.chart_config_sewage,"facilityShortChart3")
				renderIndexChart(bars.chart_config_water_supply,"facilityShortChart4")

			}
		}
		/*右边*/
		function renderPoorAnalysis(average) {
			$('#blockPoorAnalysis')
				.html(template('tplPoorAnalysis', average));
		}

		function renderCapital(capital) {
			var $blockCapital = $('#blockCapital');

			$blockCapital.html(template('tplCapital',capital));
			renderIndexChart(capital.compile_promote_plan.chart_config_compile_promote_plan,"compilePromotePlanChart")
			renderIndexChart(capital.green_beautiful.chart_config_green_beautiful,"greenBeautifulChart")
		}

		function renderGauge(id, oConfig, convertFunc) {
			var opts = convertFunc(oConfig),
				data = opts.data,
				config = opts.config;

			loadLiquidFillGauge(id, data, config);
		}

		function renderTopIndex(topIndexes) {
			var $blockTop = $('#blockTop');
			$blockTop.html(template('tplTopIndex', topIndexes));

			renderIndexChart(topIndexes.reach_standard_country.chart_config_reach_standard,"topChartLeft");
			renderIndexChart(topIndexes.finish_promote_country.chart_config_reach_standard,"topChartRight");


			//点击“贫困户”、“贫困人口”跳转“示范村建设”页面
			$blockTop
				.children('ul')
				.on(CLICK_EV, ".data1 strong", function() {
					$.dataMonitor.showDataMonitor({
						target: DATA_MONITOR_EXAMPLE_COUNTRY_BUILDING
					});
				})
				//点击“脱贫户”、“脱贫人口”跳转“示范村建设”页面
				.on(CLICK_EV, ".data2 strong", function() {
					console.log("提升创建");
					// $.dataMonitor.showDataMonitor({
					// 	target:DATA_MONITOR_TARGET_SUCCESS
					// });
				});

		}

		function renderBottomIndex(stat) {

			if (!renderBottomIndex.lastStat) {
				saveLastStat();
			}

			render(stat);

			function render(stat) {
				$('#blockBottom')
					.html(template('tplBottomIndex', {
						lastStat: renderBottomIndex.lastStat,
						stat: stat
					}));
				$('#blockBottom').find('.num.dynamic').each(count);

				saveLastStat();
			}

			function saveLastStat() {
				renderBottomIndex.lastStat = stat;

				//TODO debug 模拟增加
				//var o = {};
				//$.map(stat, function(v, k){
				//	o[k] = v - 100;
				//});
				//renderBottomIndex.lastStat = o;
			}

		}

		function count(a) {
			var $elem = $(this);
			a = $.extend({},
				a || {},
				$elem.data("countToOptions") || {});
			$elem.countTo(a)
		}
	}

	//图表封装函数
	function d3OptsAverage1(dataConfig) {
		var opts = {},
			percent = dataConfig.percent;

		var config = liquidFillGaugeDefaultSettings();
		config.circleThickness = 0.1;
		config.circleFillGap = 0.1;
		if (percent == "100") {
			//100%比例的配置
			config.circleColor = "#f3e824";
			config.textColor = "#f3e824";
			config.waveTextColor = "#f3e824";
			config.waveColor = "#322e41";
		} else {
			config.circleColor = "#6cc3ff";
			config.textColor = "#fff";
			config.waveTextColor = "#fff";
			config.waveColor = "#6cc3ff";
		}
		config.textVertPosition = 0.5;
		config.waveAnimateTime = 1000;
		config.waveHeight = 0.05;
		config.waveAnimate = true;
		config.waveRise = false;
		config.waveHeightScaling = false;
		config.waveOffset = 0.25;
		config.textSize = 0.75;
		config.waveCount = 3;
		config.displayPercent = false;
		config.maxValue = 7365;

		opts.config = config;
		opts.data = percent;

		return opts;
	}

	function d3OptsAverage2(dataConfig) {
		var opts = {},
			percent = dataConfig.percent;

		var config = liquidFillGaugeDefaultSettings();
		config.circleThickness = 0.1;
		config.circleFillGap = 0.1;
		if (percent == "100") {
			//100%比例的配置
			config.circleColor = "#f3e824";
			config.textColor = "#f3e824";
			config.waveTextColor = "#f3e824";
			config.waveColor = "#322e41";
		} else {
			config.circleColor = "#6cc3ff";
			config.textColor = "#fff";
			config.waveTextColor = "#fff";
			config.waveColor = "#6cc3ff";
		}
		config.textVertPosition = 0.5;
		config.waveAnimateTime = 1000;
		config.waveHeight = 0.05;
		config.waveAnimate = true;
		config.waveRise = false;
		config.waveHeightScaling = false;
		config.waveOffset = 0.25;
		config.textSize = 0.75;
		config.waveCount = 3;

		opts.config = config;
		opts.data = percent;

		return opts;
	}

	//=============数据分析中的监控弹窗================
	//TODO 既然被写成了二级打补丁只能将打补丁的做法做下去，待进一步重构
	$.dataMonitor = {
		currentTarget: null,
		$analyseContent: null,
		curAreaName: '', //当前区域名称
		treeCurLevel: curLevel, //左侧树选中的-区域级别
		treeCurAreaID: curAreaID, //左侧树选中的-区域ID
		firstTabState: null, //第一级状态，用于第二进去后返回

		init: function() {
			var self = this;
			self.loadMonitorTpl();
			$body.on(CLICK_EV, hideScopeList);

			function hideScopeList() {
				var $selectBox = $(".select-box");
				$selectBox.removeClass('selected').find('.select-list').hide();
				$selectBox.find('.arrow').removeClass('arrow-on');
			}
		},
		loadMonitorTpl: function() {
			var self = this;
			$.ajax({
				url: URLS.URL_TPL_DATA_MONITOR + '?_=' + new Date().getTime(),
				async: false,
				success: function(tpl) {
					$('body').append(tpl);
					self.$monitorContent = $('#monitorContent');


					// self.secondPopup.openDetail()
				}
			});
		},

		//绑定顶部事件：选择年份、导航、返回
		bindHeader: function() {
			var self = this;
			$('#popupPoorMonitor').off(CLICK_EV).on(CLICK_EV, '.js-monitor-close', self.popup.close);
			//头部标签页导航切换
			var $headerNav = $('#monitorHeader .monitor-nav');
			$headerNav.on('click', 'li', function(event) {
				var $this = $(this),
					opts = {};

				opts.configOrder = $this.data("config-order");

				$this.siblings().removeClass('active');
				$this.addClass('active');

				self.renderTree(opts);
			});
			//头部的年份切换
			var $headerYearTab = $('#monitorHeader .tab-year');
			$headerYearTab.on('click', 'i', function(event) {
				var $this = $(this),
					opts = {};

				if ($this.hasClass('js-forbid')) {
					return 0;
				} else {
					opts.configOrder = $(".monitor-nav li.active").data("config-order");
					self.renderTree(opts);
				}
			});

			//绑定下拉事件
			var $monitorHeader = $('#monitorHeader');

			$monitorHeader.find(".js-select-box").on('click', toggleSelect)

			function toggleSelect() {
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

				//下载
				$monitorHeader.find('.download-select li').on('click', function() {
					var exportUrl = $(this).attr("data-export-url");
					$.components.tableEvent.exportTable(exportUrl);

				})

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
			}
		},
		//改变二级标题，绑定二级返回操作
		setSecondHeader: function(opts, title) {
			var $secondTabTitle = $("#monitorHeader #secondTabTitle"),
				$secondTabClose = $("#monitorHeader .js-monitor-close");

			$secondTabTitle.text(title).removeClass('hide');
			$secondTabTitle.attr('data-tabtype', opts.tabtype);
			$secondTabClose.addClass('second-tab-close');
		},
		onSecondTab: function() {
			var $secondTabTitle = $("#monitorHeader #secondTabTitle");
			return !$secondTabTitle.hasClass('hide');
		},
		pushFirstTabState: function(opts) {
			var self = this;

			self.firstTabState = extend({
				area_level: self.treeCurLevel,
				area_id: self.treeCurAreaID
			}, opts);
		},
		popFirstTabState: function() {
			var self = this,
				state = self.firstTabState;

			self.firstTabState = null;

			return state;
		},
		renderSecondTabContent: function(opts) {

		},
		//弹窗操作
		popup: {
			open: function() {
				$('#popupPoorMonitor').show();
			},
			close: function() {
				var $this = $(this),
					self = $.dataMonitor;
				var $secondTabTitle = $("#monitorHeader #secondTabTitle"),
					$secondTabClose = $("#monitorHeader .js-monitor-close");

				if ($this.hasClass('second-tab-close')) {
					$secondTabTitle.addClass('hide');
					$this.removeClass('second-tab-close');

					var savedState = self.popFirstTabState();

					if (savedState) {
						self.renderTree(extend(savedState, {
							year: self.getCurYear()
						}));
					}
				} else {
					self.treeCurLevel = curLevel;
					self.treeCurAreaID = curAreaID;
					$('#popupPoorMonitor').hide();
				}
			}
		},
		secondPopup: {
			openDetail: function(opts) {
				var dataMonitor = $.dataMonitor,
					me = this,
					$popupPoorMonitorSecondWrap = $('#popupPoorMonitorSecondWrap');

				$popupPoorMonitorSecondWrap
					.html(template('tplSecondPopup'));

				me.areaSelect.render(opts, function() {
					$popupPoorMonitorSecondWrap.show();
					me.bindSelectArea();

					dataMonitor.renderContent(extend({}, opts, {
						target: DATA_MONITOR_SECOND_POPUP.DETAIL_LIST
					}));
					me.bindClosePopup();
				});

			},
			openAreaData: function(opts) {
				var dataMonitor = $.dataMonitor,
					$popupPoorMonitorSecondWrap = $('#popupPoorMonitorSecondWrap');

				$popupPoorMonitorSecondWrap
					.html(template('tplSecondPopup'));

				$popupPoorMonitorSecondWrap.show();

				dataMonitor.renderContent(extend({}, opts, {
					target: DATA_MONITOR_SECOND_POPUP.AREA_DATA
				}));
				this.bindClosePopup();
			},
			close: function() {
				$('#popupPoorMonitorSecondWrap').hide();
			},
			bindClosePopup: function() {
				var $secondPopup = $('#popupPoorMonitorSecond');

				$secondPopup
					.on(CLICK_EV, '.js-second-popup-close', this.close)
			},
			bindSelectArea: function() {
				var $secondPopup = $('#popupPoorMonitorSecond');
				$secondPopup
					.off('click', '.js-select-box', this.areaSelect.toggle)
					.off('click', '.js-select-list li', this.areaSelect.choose);

				$secondPopup
				//toggle select
					.on('click', '.js-select-box', this.areaSelect.toggle)
					//选中
					.on('click', '.js-select-list li', this.areaSelect.choose)
			},
			//select box效果
			areaSelect: {
				toggle: function() {
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
							$selectList.jScrollPane(commonJSPConfig)
						}
					}
				},
				/**
				 * 选中某个区域
				 */
				choose: function() {
					var $this = $(this),
						self = $.dataMonitor,
						$selectArea = $("#popupPoorMonitorSecond #basicInfo .select-area");

					//选择区域
					if ($this.parents("#areaBarrio").length > 0) {
						var curAreaID = $this.attr('data-id');
						var curLevel = $this.closest('.js-select-box').attr('level');
						var year = $selectArea.find("#selectYear .select-box>span").attr("data-value");

						if($selectArea.find("#selectMonth").length>0){
							var month = $selectArea.find("#selectMonth .select-box>span").attr("data-value");
						}
						renderParamsTable();
					}
					//选择年份
					if ($this.parents("#selectYear").length > 0) {
						var year = $this.attr('data-year');
						if($selectArea.find("#selectMonth").length>0){
							var month = $selectArea.find("#selectMonth .select-box>span").attr("data-value");
						}

						var curLevel = $selectArea.find("#areaBarrio .js-select-box").last().attr("level");
						var curAreaID = $selectArea.find("#areaBarrio .js-select-box").last().attr("selected_id");
						if (curAreaID == "") {
							curAreaID = $selectArea.find("#areaBarrio .js-select-box").eq(-2).attr("selected_id");
							curLevel = $selectArea.find("#areaBarrio .js-select-box").eq(-2).attr("level");
						}

						renderParamsTable();
					}

					//选择月份
					if ($this.parents("#selectMonth").length > 0) {
						var month = $this.attr('data-month');
						
						var year = $selectArea.find("#selectYear .select-box>span").attr("data-value");

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
								month: month||'',
								wintype: self.getWinType(),
								tabtype: self.getCurTabType()
							},
							success: function(data) {
								var code = data.code,
									d = data.data;
								if (code == 0 && d) {
									d.hasSelectArea = true;

									$('#popupPoorMonitorSecond')
										.find('.basic-info-wrap')
										.html(template('tplSecondPopupBasicInfo', d));
								} else {
									hideLoadingMask();
								}
							},
							error: function() {
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
							month: month||'',
							wintype: self.getWinType(),
							tabtype: self.getCurTabType()
						};
						self.buildConfigCheckArea(opts);
						// self.renderSecondPopupTable(opts);
					}
				},
				/**
				 * 初始化select
				 */
				render: function(opts, callback) {
					var self = $.dataMonitor,
						checkArgObj = {}, //当前选择的参数
						defaultArgObj = { //默认的参数
							level: opts.area_level,
							id: opts.area_id,
							year: opts.year,
							month: opts.month,
							wintype: self.getWinType(),
							tabtype: self.getCurTabType(),
							page: opts.table_page || 1
						};

					function load() {
						showLoadingMask();

						ajax({
							url: URLS.URL_DATA_MONITOR_DETAIL_BASIC_INFO,
							data: extend(defaultArgObj, checkArgObj),
							success: function(data) {
								var code = data.code,
									d = data.data;
								d.AREA_SCOPE = AREA_SCOPE;
								if (code == 0 && d) {
									d.hasSelectArea = true;

									$('#secondPopupTitle').text(d.win_title);

									$('#popupPoorMonitorSecond')
										.find('.basic-info-wrap')
										.html(template('tplSecondPopupBasicInfo', d));

									$.isFunction(callback) && callback();
								} else {
									hideLoadingMask();
								}
							},
							error: function() {
								hideLoadingMask();
								// load();
							}
						});
					}

					load();
				}
			}
		},
		//绑定跳转二级tab
		bindSecondTab: function(oOpts, target) {
			var
				self = this,
				wintype = self.getWinType(),

				$thisCheckArea = $("#" + oOpts.tabName + "CheckArea"),

				THIS_SECONDTAB = target,
				tabtype = THIS_SECONDTAB.VALUE;

			oOpts = extend({}, oOpts, {
				checkActiveObj: self.getCheckAreaActiveObj($thisCheckArea)
			});

			var opts = extend({}, oOpts, {
				target: wintype + '_' + tabtype,
				tabtype: tabtype
			});

			self.pushFirstTabState(oOpts);

			self.setSecondHeader(opts, THIS_SECONDTAB.TITLE);

			self.renderTree(opts);
		},
		//显示导出进度
		exportWrap: {
			open: function() {
				$('#exportWrap').show();
			},
			close: function() {
				$('#exportWrap').hide();
			}
		},
		//获取窗口类型
		getWinType: function() {
			return $('#monitorHeader .header-title').data("win-type");
		},
		//获取header的年份
		getCurYear: function() {
			return $('#monitorHeader .tab-year .active').data("value");
		},
		//获取header标签页类型
		getCurTabType: function() {
			var self = this,
				tabType;
			if (!self.onSecondTab()) {
				tabType = $('#monitorHeader .monitor-nav .active').data("tab-type");
			} else {
				tabType = $("#monitorHeader #secondTabTitle").attr('data-tabtype');
			}
			return tabType;
		},
		//获取当前选择区域选中参数
		getCheckAreaActiveObj: function($thisCheckArea) {
			var checkArgObj = {};

			$thisCheckArea.find(".radio-row").each(function(index, el) {
				var $this = $(this),
					_key = $this.data("key"),
					_value = $this.find(".radio.active").data("value");

				checkArgObj[_key] = _value;
			});

			$thisCheckArea.find(".check-row").each(function(index, el) {
				var $this = $(this),
					valueArr = [],
					_key = $this.data("key");

				$this.find(".check.active").each(function(index, el) {
					var _value = $(this).data("value");

					if (_value) {
						valueArr.push(_value);
					}
				});
				checkArgObj[_key] = valueArr.join(",");
			});

			return checkArgObj;
		},
		/**
		 * [生成选择区域]
		 * @Author   luoJiangFeng
		 * @DateTime 2017-03-16
		 * @param    {[数组]}     rowKeyArr      [单行的key数组，key取值见api说明或components.js]
		 * @param    {[对象]}     activePositionObj [设置对应“行”的默认选中位置,多选时传数组(不传时全部默认为首位),{行key:value}]
		 *
		 * @return   {[字符串]}                     [html代码]
		 * @example  buildCheckRadioArea(["scopeType","poorAttr","laborAttr","statusAttr"],{laborAttr:value,statusAttr:value})
		 */
		buildCheckAreaHtml: function(rowKeyArr, activePositionObj) {
			var buildCheckRow = $.components.buildCheckRow;
			var _html = '<table class="check-area-table">';
			// var activePositionObj=activePositionObj||{default:0};

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
				} else {
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
		bindCheckArea: function(opts, callback) {
			var self = this,
				$thisCheckArea;

			opts = extend({}, opts);

			if (opts.secondPopup) {
				$thisCheckArea = $("#" + opts.paramsTplId);
			} else {
				$thisCheckArea = $("#" + opts.tabName + "CheckArea");
			}
			//单选
			$thisCheckArea.off("click").on('click', ".radio-row .radio", function(event) {
				var $this = $(this);

				opts = extend({}, opts);

				if ($this.hasClass('disabled')) {
					return 0;
				} else {
					$this.siblings().removeClass('active');
					$this.addClass('active');

					//带此属性的控件由绑到此元素的handler接管
					if (!$this.data('has-own-handler')) {
						if ($this[0].hasAttribute("data-url")) {
							opts.url = URLS[$this.attr("data-url")];
							opts.tplID = 'tpl' + opts.TabName + 'ChartTable';
							opts.secondPopup ? self.renderSecondPopupTable(opts, callback) : self.renderChartTable(opts, callback);
						} else {
							var changeUrl,
								changeTplID;

							$thisCheckArea.find(".radio-row .radio.active").each(function(index, el) {
								if ($(this)[0].hasAttribute("data-url")) {
									changeUrl = $(this).attr("data-url");
									changeTplID = $(this).attr("data-tpl-id");
								}
							});

							opts.url = URLS[changeUrl] || opts.url;
							opts.tplID = changeTplID || opts.tplID;
							opts.secondPopup ? self.renderSecondPopupTable(opts, callback) : self.renderChartTable(opts, callback);
						}
					}
				}
			});

			// TODO 没处理全选
			//多选
			$thisCheckArea.on('click', ".check-row .check", function(event) {
				var $this = $(this);

				opts = extend({}, opts);

				if ($this.hasClass('disabled')) {
					return 0;
				} else {
					$this.toggleClass('active');

					opts.secondPopup ? self.renderSecondPopupTable(opts, callback) : self.renderChartTable(opts, callback);
				}
			});
		},

		/**
		 * [生成选择区域]
		 * @Author   luoJiangFeng
		 * @DateTime 2017-03-17
		 * @param    {[type]}     opts              [description]
		 * @param    {[数组]}     rowKeyArr     		[单行的key数组，key取值见api说明或components.js]
		 * @param    {Object}     activePositionObj [设置对应“行”的默认选中位置,多选时传数组(不传时全部默认为首位),{行key:value}]
		 * @param    {[函数]}     callback          [重新渲染chartable时的回调函数]
		 * @return   {[type]}                       [description]
		 */
		buildCheckArea: function(opts, rowKeyArr, activePositionObj, callback) {
			var self = this;
			var $thisCheckArea = $("#" + opts.tabName + "CheckArea");
			//opts自带checkActiveObj的话则以opts的优先
			var checkAreaHtml = self.buildCheckAreaHtml(rowKeyArr, opts.checkActiveObj || activePositionObj);

			$thisCheckArea.html(checkAreaHtml);
			self.renderContentBasicInfo(opts);
			self.bindCheckArea(opts, callback);
		},
		//后端配置的选择区域
		buildConfigCheckArea: function(opts) {
			var self = this;
			if (opts.secondPopup) {
				opts.paramsTplId = "paramsCheckArea";
			}
			self.renderConfigCheckArea(opts);

			if (!opts.secondPopup) {
				self.renderContentBasicInfo(opts);
			}
			self.bindCheckArea(opts);
		},
		/**
		 * 渲染窗口外壳及调用渲染方法
		 * 调用顺序：
		 * renderWin -> renderTree -> renderContent
		 * @param opts
		 */
		renderWin: function(opts) {
			var self = this;

			showLoadingMask();
			ajax({
				url: URLS.URL_DATA_MONITOR_BASIC_INFO,
				data: {
					level: curLevel,
					id: curAreaID
				},
				success: function(data) {
					var code = data.code,
						d = data.data;
					d = extend({}, d, opts.target);
					if (code == 0 && d) {
						$('#monitorHeader').html(template('tplMonitorHeader', d));
						var $tabSwitch = $("#monitorHeader .tab-switch");
						$.components.tabSwitch($tabSwitch, d.years.available_years, d.years.selected_value);
						self.bindHeader();
						self.renderTree();
					} else {
						hideLoadingMask();
					}
				},
				error: function() {
					hideLoadingMask();
					// self.renderWin( opts);
				}
			});

		},
		/**
		 * 渲染窗口左边树    树的插件 zTree 
		 * @param opts
		 */
		renderTree: function(opts) {
			var self = this;

			opts = extend({
				year: self.getCurYear(),
				area_level: self.treeCurLevel,
				area_id: self.treeCurAreaID,
				wintype: self.getWinType(),
				tabtype: self.getCurTabType()
			}, opts);

			renderDataMonitorTree($('#monitorTree'), extend({
				instance: self
			}, opts));
		},
		//初始渲染数据监控弹窗
		showDataMonitor: function(opts) {
			var self = this;

			self.renderWin(opts);
			self.popup.open();
		},
		/**
		 * 渲染内容统一方法，再由它分配调用具体的渲染内容方法
		 * @param opts
		 */
		renderContent: function(opts) {
			var self = $.dataMonitor;
			var $secondTabTitle = $("#monitorHeader #secondTabTitle");

			var arr = opts.target.split('_');
			var arr2 = [];
			var fnName = '';
			for (var i = 0, len = arr.length; i < len; i++) {
				arr2.push(arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1));
			}

			opts.TabName = arr2.join('');
			opts.tabName = arr[0] + arr2.join('').substring(arr[0].length);
			fnName = 'render' + arr2.join('');

			$.dataMonitor[fnName](opts);
		},
		renderContentBasicInfo: function(opts) {
			var self = this,
				thisFunc = 'render' + opts.TabName,

				tabType = self.getCurTabType();

			var $thisCheckArea = $("#" + opts.tabName + "CheckArea");

			showLoadingMask();

			ajax({
				url: URLS.URL_DATA_MONITOR_CONTENT_BASIC_INFO,
				data: {
					level: opts.area_level,
					id: opts.area_id,
					year: opts.year
				},
				success: function(data) {
					if (tabType != self.getCurTabType()) {
						return;
					}

					var code = data.code,
						d = data.data;

					if (code == 0 && d) {
						$thisCheckArea.find("[data-key='scope'] .radio").each(function(index, el) {
							var $this = $(this);
							var key = index + 1;

							if (!d.scope_available[key]) {
								$this.addClass('disabled');
							}
						});
					} else {
						hideLoadingMask();
					}
				},
				error: function() {
					hideLoadingMask();
					// self[thisFunc]();
				}
			});
		},
		//后端配置-选择区域
		renderConfigCheckArea: function(opts) {
			var self = this,
				thisFunc = 'render' + opts.TabName,
				$thisCheckArea,

				winType = self.getWinType(),
				tabType = self.getCurTabType();

			if (opts.paramsTplId) {
				$thisCheckArea = $("#" + opts.paramsTplId);
			} else {
				$thisCheckArea = $("#" + opts.tabName + "CheckArea");
			}

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
				success: function(data) {
					if (winType != self.getWinType() || tabType != self.getCurTabType()) {
						return;
					}

					var code = data.code,
						d = data.data;

					if (code == 0 && d) {
						$thisCheckArea.html(template("tplConfigCheckArea", d));
						if (!opts.secondPopup) {
							self.renderChartTable(opts);
						} else {
							self.renderSecondPopupTable(opts);
						}
					} else {
						hideLoadingMask();
					}
				},
				error: function() {
					hideLoadingMask();
					// self[thisFunc]();
				}
			});

		},
		//渲染图+表
		renderChartTable: function(opts, callback) {
			var self = this,
				$thisCheckArea,
				$thisChartTable,
				thisFunc = 'render' + opts.TabName,
				tplThisChartTable = opts.tplID || 'tpl' + opts.TabName + 'ChartTable',

				winType = getWinType(),
				tabType = getCurTabType();

			opts = extend({
				renderTable: true,
				renderChart: true
			}, opts);

			function getWinType() {
				return opts.wintype || self.getWinType()
			}

			function getCurTabType() {
				return opts.tabtype || self.getCurTabType()
			}

			$thisCheckArea = $("#" + opts.tabName + "CheckArea");
			$thisChartTable = $("#" + opts.tabName + "ChartTable");

			var checkArgObj = {}, //当前选择的参数
				defaultArgObj = { //默认的参数
					level: opts.area_level,
					id: opts.area_id,
					year: opts.year,
					wintype: winType,
					tabtype: tabType,
					page: opts.table_page || 1
				};

			checkArgObj = self.getCheckAreaActiveObj($thisCheckArea);

			showLoadingMask();

			ajax({
				url: opts.url,
				data: extend(defaultArgObj, checkArgObj),
				success: function(data) {
					if (winType != getWinType() || tabType != getCurTabType()) {
						return;
					}

					var code = data.code,
						d = data.data;

					if (code == 0 && d) {
						//渲染统计图+表
						$thisChartTable.html(template(tplThisChartTable, d));

						if (opts.renderTable) {
							self.renderConfigTable(extend({}, opts, {
								tableData: d.table,
								tableDetail: opts.tableDetail,
								tableTplId: opts.tableTplId,
								secondPopup: false
							}));
						}
						if (opts.renderChart) {
							renderChart(d);
						}

						// TODO 再重构
						// bind chart tab
						var CLASS_ACTIVE = 'active';
						var $chartTabs = $thisChartTable.find(".chart-tab");
						if ($chartTabs.length > 0) {
							$chartTabs.each(function() {
								var $chartTab = $(this),
									$chartItem = $chartTab.closest('.chart-item'),
									$tabChartsWrap = $chartItem.find('.js-tab-charts-wrap');

								console.log(opts.chartTab || $chartTab.attr('data-active'))
									// default tab
								activateTab(opts.chartTab || $chartTab.attr('data-active'));

								$chartTab
									.on(CLICK_EV, 'li', function() {
										var $this = $(this);
										var targetChart = $this.data("target");

										activateTab(targetChart);
									});

								function activateTab(target) {
									$chartTab.attr("data-active", target)
										.find("li[data-target=" + target + "]").addClass(CLASS_ACTIVE)
										.siblings().removeClass(CLASS_ACTIVE);

									$tabChartsWrap.find(".chart").addClass('hide');
									$tabChartsWrap.find("#" + target).removeClass('hide');

								}
							});
						}

						var $pageContentWrap = self.$monitorContent.find('.page-content-wrap'),
							pageContentWrapJSP = $pageContentWrap.data('jsp');

						if (!pageContentWrapJSP) {
							pageContentWrapJSP = $pageContentWrap.jScrollPane(commonJSPConfig)
								.data('jsp');
						} else {
							pageContentWrapJSP.reinitialise();
						}

						$.isFunction(callback) && callback();

					}
					hideLoadingMask();
				},
				error: function() {
					hideLoadingMask();
					// self[thisFunc]();
				}
			});
			//渲染图
			function renderChart(d) {
				var chartArr = []

				for (var key in d) {
					if (key.substring(0, 12) == "chart_config") {
						chartArr.push({
							name: key,
							data: d[key]
						})
					}
				}
				for (var i = 0; i < chartArr.length; i++) {
					var jsonData = d[chartArr[i].name];
					var chartName = chartArr[i].name.substring(13)
					if (opts.chartHeaderId) {
						var tplChartId = opts.chartHeaderId + $.components.underlineToCamel(chartName);
					} else {
						var tplChartId = opts.tabName + 'Chart' + $.components.underlineToCamel(chartName);
					}

					// $thisChartTable.find('.js-build-chart-id').eq(i).attr("id",tplChartId)
					echarts.init(document.getElementById(tplChartId)).setOption(commonEchartConfig[jsonData.convert_method](jsonData.config));
				}
			}
		},
		/**
		 * [渲染后台配置表模板]
		 * @Author   luoJiangFeng
		 * @DateTime 2017-03-20
		 * @param    {[type]}     tableData   [表的数据]
		 * @param    {[boolean]}  tableDetail [表头是否有明细按钮]
		 */
		renderConfigTable: function(opts) {
			opts = extend({}, opts);

			opts.tableDetail = opts.tableDetail || false;
			var self = this,
				$tplTable,
				$thisChartTable;

			if (opts.secondPopup) {
				$tplTable = $("#" + opts.tableTplId);
				$thisChartTable = $("#secondPopupTable");
			} else {
				$tplTable = self.$monitorContent.find('.config-table');
				$thisChartTable = $("#" + opts.tabName + "ChartTable");
			}

			if (opts.tableDetail) {
				opts.tableData.hasDetailBtn = true;
				$tplTable.html(template("tplConfigTable", opts.tableData));
				bindPopupEvent();
				if (opts.tableData.pagination) {
					bindPagination();
				}
			} else {
				opts.tableData.hasDetailBtn = false;
				$tplTable.html(template("tplConfigTable", opts.tableData));
				bindPopupEvent();
				if (opts.tableData.pagination) {
					bindPagination();
				}
			}

			var $popupWin = null;
			if (opts.secondPopup) {
				$popupWin = $("#popupPoorMonitorSecond");
			} else {
				$popupWin = self.$monitorContent;
			}

			var $pageContentWrap = $popupWin.find('.page-content-wrap'),
				$monitorTbWrap = $popupWin.find('.monitor-tb-wrap'),
				pageContentJSP = $pageContentWrap.data('jsp'),
				monitorTbWrapJSP = $monitorTbWrap.data('jsp');

			if (!monitorTbWrapJSP) {
				monitorTbWrapJSP = $monitorTbWrap.jScrollPane(commonJSPConfig)
					.data('jsp');
			} else {
				monitorTbWrapJSP.reinitialise();
			}

			if (!pageContentJSP) {
				pageContentJSP = $pageContentWrap.jScrollPane(commonJSPConfig)
					.data('jsp');
			} else {
				pageContentJSP.reinitialise();
			}

			//Fix table size
			monitorTbWrapJSP.reinitialise();

			//bind 弹窗事件
			function bindPopupEvent() {
				if (!$thisChartTable.data('action-bound')) {
					$thisChartTable
						.on('click', 'td[data-action-type=area_data]', function(event) {
							var $elem = $(this),
								areaLevel = $elem.data('action-area-level'),
								areaID = $elem.data('action-area-id');

							self.secondPopup.openAreaData(extend({}, opts, {
								area_level: areaLevel,
								area_id: areaID
							}));
						})
						.on('click', 'td[data-action-type=country_file]', function(event) {
							var _id = $(this).data("action-area-id");
							$.poorFile.showCountryFile(_id);
						})
						.on('click', 'td[data-action-type=family_file]', function(event) {
							var _id = $(this).data("action-family-id");
							$.poorFile.showFamilyFile(_id);
						})
						.on('click', '.detail-btn', function(event) {
							self.secondPopup.openDetail(opts);
						})
						.on('click', '.export-btn', function(event) {
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
					success: function(data) {
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
												if (stat == 1) {
													renderExport(d);

													timer = setTimeout(load, INTERVAL);
												} else {
													//进度100%
													if (stat == 2) {
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
													else {
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

			function bindPagination() {
				var $tablePagination = $thisChartTable.find(".table-pagination");
				var pageTotal = opts.tableData.pagination.page_total,
					pageCur = opts.tableData.pagination.page_cur,
					pageNext = opts.tableData.pagination.page_cur + 1,
					pagePre = opts.tableData.pagination.page_cur - 1;

				$tablePagination
					.on('click', '.arrow-first', function(event) {
						if (pageCur == 1) {
							return 0;
						}
						opts.table_page = 1;
						opts.secondPopup ? self.renderSecondPopupTable(opts) : self.renderChartTable(opts);
					})
					.on('click', '.arrow-end', function(event) {
						console.log(pageCur+ "+"+ pageTotal);
						if (pageCur == pageTotal) {
							return 0;
						}

						opts.table_page = pageTotal;
						opts.secondPopup ? self.renderSecondPopupTable(opts) : self.renderChartTable(opts);
					})
					.on('click', '.arrow-left', function(event) {
						if (pagePre < 1) {
							return 0;
						} else {
							opts.table_page = pagePre;
						}
						opts.secondPopup ? self.renderSecondPopupTable(opts) : self.renderChartTable(opts);
					})
					.on('click', '.arrow-right', function(event) {
						if (pageNext > pageTotal) {
							return 0;
						} else {
							opts.table_page = pageNext;
						}
						opts.secondPopup ? self.renderSecondPopupTable(opts) : self.renderChartTable(opts);
					})
			}

		},
		configTableFun: {
			//bind页脚事件

		},
		renderSecondPopupTable: function(opts, callback) {
			var self = this,
				$thisCheckArea = $("#popupPoorMonitorSecond #paramsCheckArea"),
				checkArgObj = {}, //当前选择的参数

				winType = self.getWinType(),
				tabType = self.getCurTabType(),

				defaultArgObj = { //默认的参数
					level: opts.area_level,
					id: opts.area_id,
					year: opts.year,
					month: opts.month,
					wintype: winType,
					tabtype: tabType,
					page: opts.table_page || 1
				};

			opts.tableDetail = false;

			checkArgObj = self.getCheckAreaActiveObj($thisCheckArea);

			showLoadingMask();

			ajax({
				url: opts.url,
				data: extend(defaultArgObj, checkArgObj),
				success: function(data) {
					if (winType != self.getWinType() || tabType != self.getCurTabType()) {
						return;
					}

					var code = data.code,
						d = data.data;

					if (code == 0 && d) {
						var $popupPoorMonitorSecond = $("#popupPoorMonitorSecond");

						if (opts.secondPopupType == "area_data") {
							d.hasSelectArea = false;

							$popupPoorMonitorSecond.find(".basic-info-wrap")
								.html(template("tplSecondPopupBasicInfo", d));

							$('#secondPopupTitle').text(d.win_title);
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
				error: function() {
					hideLoadingMask();
					// self.renderSecondPopupTable(opts,callback);
				}
			});
		},
		//渲染-后台配置排序
		renderConfigOrder: function(opts) {
			var self = this,
				tplID = 'tplConfigOrder';

			opts.url = URLS.URL_DATA_MONITOR_ORDER_TABLE;
			opts.params_url = URLS.URL_DATA_MONITOR_ORDER_BASIC_INFO;

			if (!$("#headDownloadTable").hasClass('hide')) {
				self.renderHeadDownloadList(opts);
			}

			self.$monitorContent.html(template(tplID));
			self.buildConfigCheckArea(opts);
			// self.renderChartTable(opts);
		},
		//渲染二级弹窗-详细清单
		renderSecondPopupDetailList: function(opts) {
			var self = this;

			opts = extend({}, opts, {
				url: URLS.URL_DATA_MONITOR_DETAIL_TABLE,
				params_url: URLS.URL_DATA_MONITOR_DETAIL_PARAMS_INFO,
				secondPopup: true,
				secondPopupType: "detail_list"
			});
			self.buildConfigCheckArea(opts);
		},
		//渲染二级弹窗-区域数据
		renderSecondPopupAreaData: function(opts) {
			var self = this;

			opts = extend({}, opts, {
				url: URLS.URL_DATA_MONITOR_AREA_DATA_TABLE,
				secondPopup: true,
				secondPopupType: "area_data"
			});
			self.renderSecondPopupTable(opts);
		},
		//渲染头部下载表格列表
		renderHeadDownloadList: function(opts) {
			var self = this;
			showLoadingMask();
			ajax({
				url: URLS.URL_DATA_MONITOR_HEAD_DOWNLOAD_TABLE_LIST,
				data: {
					wintype: opts.wintype,
					id: opts.area_id,
					level: opts.area_level,
					year: opts.year
				},
				success: function(data) {

					var code = data.code,
						d = data.data;

					if (code == 0 && d) {
						var $popupPoorMonitorHeader = $("#monitorHeader");
						var $headDownloadTable = $popupPoorMonitorHeader.find("#headDownloadTable");

						$headDownloadTable
							.html(template("tplHeadDownloadTable", d));

						$headDownloadTable.removeClass('hide');
					}

				},
				error: function() {
					hideLoadingMask();
				}
			});

		},

		/*=====================渲染具体页面====================*/
		/*渲染-示范村建设*/
		//渲染-示范村建设-整治达标
		renderExampleCountryBuildingRenovateStandard: function(opts) {
			var self = this,
				tplID = 'tpl' + opts.TabName,
				$thisTableSelect,
				$thisTable;
			opts.chart_url = URLS.URL_DATA_EXAMPLE_COUNTRY_COUNTRY_RENOVATE_STANDARD_CHART;
			opts.table_url = URLS.URL_DATA_EXAMPLE_COUNTRY_COUNTRY_RENOVATE_STANDARD_TABLE;
			opts.tableDetail = true;
			opts.chartTab = 'exampleCountryBuildingRenovateStandardChartRenovateStandardRate';

			console.log(tplID);
			self.$monitorContent.html(template(tplID));

			renderChartOnly(opts);
			
			//表格头部的select下拉
			function bindTableSelect() {

				var date=new Date;

				var curMonth=date.getMonth()+1;
				
				var $selectArea=$("#exampleCountryBuildingRenovateStandardTableSelect .select-area");
				var $selectMonthTitle=$selectArea.find("#selectMonth>span")
				var $selectMonthList=$selectArea.find("#selectMonth .js-select-list")

				$selectMonthTitle.attr("data-month",curMonth.toString()).text(curMonth+"月")
				
				$selectMonthList.find("li").each(function(index, el) {
					$(this).removeClass('active');
					if($(this).attr("data-month")==curMonth){
						$(this).addClass('active');
					}
				});

			$thisTableSelect.find(".js-select-box").on('click', toggleSelect);
			$thisTableSelect.find(".js-select-list li").on('click', chooseSelect);

			function toggleSelect() {
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

					w = w > 80 ? 80 : w;
					if ($selectList.find('.jspPane').length < 1) {
						$selectList.width(w);
					}

					if (h > 150) {
						$selectList.jScrollPane(commonJSPConfig);
					}
				}
			}

			function chooseSelect() {
					var $this = $(this),
						self  = $.dataMonitor,
						$selectArea=$("#exampleCountryBuildingRenovateStandardTableSelect .select-area"),
						$selectMonthTitle=$selectArea.find("#selectMonth>span"),
						$selectStateTitle=$selectArea.find("#selectState>span");

					//选择状态
					if($this.parents("#selectState").length>0){
						var state = $this.attr('data-state');
						var month = $selectArea.find("#selectMonth>span").attr("data-month");

						$(this).siblings().removeClass('active')
						$(this).addClass('active')
						$selectStateTitle.attr("data-state",$(this).attr("data-state")).text($(this).text())


						opts=extend(opts,{
							month:month,
							state:state
						})

						console.log(opts);
						renderTableOnly(opts);
					}
					//选择月份
					if ($this.parents("#selectMonth").length > 0) {
						var month = $this.attr('data-month');
						var state = $selectArea.find("#selectState>span").attr("data-state");

						$(this).siblings().removeClass('active')
						$(this).addClass('active')
						$selectMonthTitle.attr("data-month",month.toString()).text(month+"月")

						opts=extend(opts,{
							month:month,
							state:state
						})
						renderTableOnly(opts);
					}

				}
		}

			function renderChartOnly(opts, callback) {
				var self = this,
					$thisChartTable = $("#" + opts.tabName + "ChartTable"),
					thisFunc = 'render' + opts.TabName,
					tplThisChartTable = opts.tplID || 'tpl' + opts.TabName + 'ChartTable',

					winType = getWinType(),
					tabType = getCurTabType();

				opts = extend({
					renderTable: true,
					renderChart: true
				}, opts);

				function getWinType() {
					return opts.wintype || self.getWinType()
				}

				function getCurTabType() {
					return opts.tabtype || self.getCurTabType()
				}

				

				var checkArgObj = {}, //当前选择的参数
					defaultArgObj = { //默认的参数
						level: opts.area_level,
						id: opts.area_id,
						year: opts.year,
						wintype: winType,
						tabtype: tabType,
					};

				showLoadingMask();

				ajax({
					url: opts.chart_url,
					data: extend(defaultArgObj, checkArgObj),
					success: function(data) {
						if (winType != getWinType() || tabType != getCurTabType()) {
							return;
						}

						var code = data.code,
							d = data.data;

						if (code == 0 && d) {
							//渲染统计图+表
							$thisChartTable.html(template(tplThisChartTable, d));
							$thisTable=$("#exampleCountryBuildingRenovateStandardTable");
							$thisTableSelect=$("#exampleCountryBuildingRenovateStandardTableSelect");

							bindTableSelect();


							if (opts.renderChart) {
								renderChart(d);
							}

							renderTableOnly(opts);

							// TODO 再重构
							// bind chart tab
							var CLASS_ACTIVE = 'active';
							var $chartTabs = $thisChartTable.find(".chart-tab");
							if ($chartTabs.length > 0) {
								$chartTabs.each(function() {
									var $chartTab = $(this),
										$chartItem = $chartTab.closest('.chart-item'),
										$tabChartsWrap = $chartItem.find('.js-tab-charts-wrap');

									console.log(opts.chartTab || $chartTab.attr('data-active'))
										// default tab
									activateTab(opts.chartTab || $chartTab.attr('data-active'));

									$chartTab
										.on(CLICK_EV, 'li', function() {
											var $this = $(this);
											var targetChart = $this.data("target");

											activateTab(targetChart);
										});

									function activateTab(target) {
										$chartTab.attr("data-active", target)
											.find("li[data-target=" + target + "]").addClass(CLASS_ACTIVE)
											.siblings().removeClass(CLASS_ACTIVE);

										$tabChartsWrap.find(".chart").addClass('hide');
										$tabChartsWrap.find("#" + target).removeClass('hide');

									}
								});
							}

							$.isFunction(callback) && callback();

						}
						hideLoadingMask();
					},
					error: function() {
						hideLoadingMask();
						// self[thisFunc]();
					}
				});
				//渲染图
				function renderChart(d) {
					var chartArr = []

					for (var key in d) {
						if (key.substring(0, 12) == "chart_config") {
							chartArr.push({
								name: key,
								data: d[key]
							})
						}
					}
					for (var i = 0; i < chartArr.length; i++) {
						var jsonData = d[chartArr[i].name];
						var chartName = chartArr[i].name.substring(13)
						if (opts.chartHeaderId) {
							var tplChartId = opts.chartHeaderId + $.components.underlineToCamel(chartName);
						} else {
							var tplChartId = opts.tabName + 'Chart' + $.components.underlineToCamel(chartName);
						}
						// $thisChartTable.find('.js-build-chart-id').eq(i).attr("id",tplChartId)
						echarts.init(document.getElementById(tplChartId)).setOption(commonEchartConfig[jsonData.convert_method](jsonData.config));
					}
				}
			}


			//渲染表
		function renderTableOnly(opts,callback){
			var self=$.dataMonitor,
				$thisChartTable,
				thisFunc='render'+opts.TabName,
				tplThisChartTable=opts.tplID||'tpl'+opts.TabName+'ChartTable',

				winType = getWinType(),
				tabType = getCurTabType();

			var $thisChartTable = $("#" + opts.tabName + "ChartTable");

			var date=new Date;
			var curMonth=date.getMonth()+1;

			opts = extend({
				renderTable:true,
				renderChart:true,
				month:opts.month||curMonth,
				state:opts.state||"doing"
			}, opts);

			function getWinType() {
				return opts.wintype || self.getWinType()
			}

			function getCurTabType() {
				return opts.tabtype || self.getCurTabType()
			}
			
			var 
				ajaxData={//默认的参数
					level: opts.area_level,
					id: opts.area_id,
					year:opts.year,
					wintype: winType,
					tabtype: tabType,
					page:opts.table_page||1,
					table_month:opts.month,
					table_state:opts.state
				};

			showLoadingMask();

			var tableUrl=opts.table_url;

			ajax({
				url: tableUrl,
				data: ajaxData,
				success: function (data) {
					if(winType != getWinType() || tabType != getCurTabType()) {
						return;
					}

					var code = data.code,
							d = data.data;

					if (code == 0 && d) {
						d.table.hasDetailBtn=true;
						$thisTable.html(template("tplConfigTable", d.table));
						bindScroll();
						var _opts = {
							tableData: d.table,
							table_page: 1,
							table_url:tableUrl
						}

						bindPopupEvent(opts);
						if (_opts.tableData.pagination) {
							$.components.tableEvent.bindPagination($thisTable, _opts, ajaxTable);
						}


					}
					hideLoadingMask();
				},
				error:function(){
					hideLoadingMask();
					// self[thisFunc]();
				}
			});

			function bindPopupEvent(){
				if(!$thisChartTable.data('action-bound')) {
					$thisChartTable
						.on('click', 'td[data-action-type=area_data]', function(event) {
							var $elem = $(this),
								areaLevel = $elem.data('action-area-level'),
								areaID = $elem.data('action-area-id');

							self.secondPopup.openAreaData(extend({}, opts, {
								area_level: areaLevel,
								area_id: areaID
							}));
						})
						.on('click', 'td[data-action-type=country_file]', function(event) {
							var _id=$(this).data("action-area-id");
							$.poorFile.showCountryFile(_id);
						})
						.on('click', 'td[data-action-type=family_file]', function(event) {
							var _id=$(this).data("action-family-id");
							$.poorFile.showFamilyFile(_id);
						})
						.on('click', '.detail-btn', function(event) {
							var $selectArea=$("#exampleCountryBuildingRenovateStandardTableSelect .select-area")
							var month = $selectArea.find("#selectMonth>span").attr("data-month");

							opts.month=parseInt(month)
							self.secondPopup.openDetail(opts);
						})
						.on('click', '.export-btn', function(event) {
							var exportUrl=$(this).data("export-url");

							exportTable(exportUrl);
						});

					$thisChartTable.data('action-bound', true);
				}
			}

			//导出表格
		function exportTable(exportUrl) {
			var win = window,
				Helpers = win.Helpers,
				ajax = Helpers.ajax,
				$exportWrap,
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
				}


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

			//滚动条
			function bindScroll(){
				var $popupFileDetailWrap=$("#popupPoorMonitor"),
				$pageContentWrap = $popupFileDetailWrap.find('.page-content-wrap'),
				$monitorTbWrap = $popupFileDetailWrap.find('.monitor-tb-wrap'),
				pageContentJSP = $pageContentWrap.data('jsp'),
				monitorTbWrapJSP = $monitorTbWrap.data('jsp');
				if(!monitorTbWrapJSP) {
					monitorTbWrapJSP = $monitorTbWrap.jScrollPane(commonJSPConfig)
					.data('jsp');
				}
				else {
					monitorTbWrapJSP.reinitialise();
				}

				if(!pageContentJSP) {
					pageContentJSP = $pageContentWrap.jScrollPane(commonJSPConfig)
					.data('jsp');
				}
				else {
					pageContentJSP.reinitialise();
				}
						//Fix table size
						monitorTbWrapJSP.reinitialise();
			}


			function ajaxTable(opts) {
				showLoadingMask();
				console.log(opts.table_url);
				ajax({
					url: opts.table_url,
					data: extend(ajaxData, {
						page: opts.table_page
					}),
					success: function(data) {
						var code = data.code,
							d = data.data;

						if (code == 0 && d) {
							var _opts = {
								tableData: d.table,
								table_page: opts.table_page,
								table_url:opts.table_url
							}
							d.table.hasDetailBtn=true;
							$thisTable.html(template("tplConfigTable", d.table));
							bindScroll()
							bindPopupEvent();
							if (opts.tableData.pagination) {
								$.components.tableEvent.bindPagination($thisTable, _opts, ajaxTable);
							}
						}
						hideLoadingMask();
					},
					error: function() {
						hideLoadingMask();
					}
				});
			}
			}
		},
		//渲染-示范村建设-提升创建
		renderExampleCountryBuildingPromoteCreation: function(opts) {
			hideLoadingMask();
			return 0;
		}


	};


	//初始化
	$.dataMonitor.init()

});