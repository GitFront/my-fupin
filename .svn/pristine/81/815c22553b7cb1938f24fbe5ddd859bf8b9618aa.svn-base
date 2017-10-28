$(function() {
	var CLICK_EV = 'click',

		extend = $.extend,
		win = window,
		Helpers = win.Helpers,
		ajax = Helpers.ajax,

		monitorHelper = $.monitorHelper,

		createDataMonitor = monitorHelper.createDataMonitor,

		ROW_KEY = $.components.ROW_KEY,
		CONST_RADIO_VALUES = $.components.CONST_RADIO_VALUES,

		$searchFileNum = $(".search-file-num"),
		$monitorNav = $(".monitor-nav"),

		//资金监控
		DATA_MONITOR_TARGET_CAPITAL = {
			TITLE: '资金监控',
			TYPE: 'capital',
			TAB: [{
				NAME: '资金管理',
				VALUE: 'capital_management'
			}, {
				NAME: '资金分析',
				VALUE: 'capital_analysis'
			}, {
				NAME: '资金排序',
				VALUE: 'capital_order',
				CONFIG_ORDER: "true"
			}
			// , {
			// 	NAME: '查文件号',
			// 	VALUE: 'capital_file',
			// 	CUSTOM: true
			// }
			]
		},

		//资金监控
		monitorCapital = createDataMonitor({
			mix: {
				//渲染-资金监控-资金管理
				renderCapitalCapitalManagement: function(opts) {
					var self = this;

					opts = extend({}, opts);

					opts.url = URLS.URL_DATA_MONITOR_CAPITAL_MANAGEMENT;

					self.renderPageContent();

					self.renderChartTable(opts);
				},
				//渲染-资金监控-资金分析
				renderCapitalCapitalAnalysis: function(opts) {
					var self = this;

					opts = extend({}, opts);

					opts.url = URLS.URL_DATA_MONITOR_CAPITAL_ANALYSIS;
					opts.tableDetail = true;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE,
							ROW_KEY.PROJECT_TYPE_COUNTRY,
							ROW_KEY.PROJECT_TYPE_FAMILY
						]
					});

					self.renderChartTable(opts);
				},
				//渲染-资金监控-查文件号
				renderCapitalCapitalFile: function(opts) {
					hideLoadingMask();
				}
			}
		});

	//nav切换事件
	$("body").on('click', '.monitor-nav li', function(event) {
		var _type = $(this).attr("data-tab-type")

		if (_type === "capital_file") {
			$searchFileNum.removeClass('hide')
		} else {
			$searchFileNum.addClass('hide')
		}
	});
	//资金来源-radio
	$("body").on('click', '#capitalSource .radio', function(event) {
		$(this).siblings().removeClass('active');
		$(this).addClass('active');
	});

	//查文件号-搜索
	$("body").on('click', '#popupSearchBtn', function(event) {
		var $searchResultTable=$("#searchResultTable");
		var _year=monitorCapital.getCurYear(),
			_level=$("#capitalSource .radio.active").attr("data-value")
			_keyword=$("#keywordInput").val()

		var ajaxData={
			year:_year,
			level:_level,
			keyword:_keyword,
			page:1
		}

		showLoadingMask();
		ajax({
			url: URLS.URL_DATA_MONITOR_CAPITAL_FILE,
			data:ajaxData,
			success: function(data) {
				var code = data.code,
					d = data.data;

				if (code == 0 && d) {
					var opts={
						tableData:d.table,
						table_page:1
					}
					$searchResultTable.html(template("tplConfigTable", opts.tableData));
					$searchResultTable.find(".export-btn").addClass('hide');
					$.components.tableEvent.bindPopupEvent($searchResultTable);
						if(opts.tableData.pagination){
							$.components.tableEvent.bindPagination($searchResultTable,opts,ajaxTable);
						}
				}
				hideLoadingMask();
			},
			error: function() {
				hideLoadingMask();
			}
		});

		function ajaxTable(opts){
			showLoadingMask();
				ajax({
					url: URLS.URL_DATA_MONITOR_CAPITAL_FILE,
					data: extend(ajaxData,{page:opts.table_page}),
					success: function(data) {
						var code = data.code,
						d = data.data;
						if (code == 0 && d) {
							var _opts={
								tableData:d.table,
								table_page:opts.table_page
							}
							$searchResultTable.html(template("tplConfigTable",d.table));
							$searchResultTable.find(".export-btn").addClass('hide');
							$.components.tableEvent.bindPopupEvent($searchResultTable);
							if(opts.tableData.pagination){
								$.components.tableEvent.bindPagination($searchResultTable,_opts,ajaxTable);
							}
						} 
						hideLoadingMask();
					},
					error: function() {
						hideLoadingMask();
					}
				});
			}

	});

	monitorCapital.showDataMonitor({
		target: DATA_MONITOR_TARGET_CAPITAL,
		hasBack: false
	});
});