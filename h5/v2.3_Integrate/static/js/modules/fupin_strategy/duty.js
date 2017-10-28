$(function () {
	var CLICK_EV = 'click',

		extend = $.extend,

		monitorHelper = $.monitorHelper,

		createDataMonitor = monitorHelper.createDataMonitor,

		ROW_KEY = $.components.ROW_KEY,
		CONST_RADIO_VALUES = $.components.CONST_RADIO_VALUES,

		//责任监控
		DATA_MONITOR_TARGET_DUTY = {
			TITLE: '责任监控',
			TYPE: 'duty',
			TAB: [
				{
					NAME: '责任管理',
					VALUE: 'duty_management'
				},
				{
					NAME: '责任分析',
					VALUE: 'duty_analysis'
				}
			]
		},

		//责任监控
		monitorDuty = createDataMonitor({
			mix: {
				//渲染-责任监控-责任管理
				renderDutyDutyManagement: function (opts) {
					var self = this;

					opts = extend({}, opts);

					opts.url = URLS.URL_DATA_MONITOR_DUTY_MANAGEMENT;

					self.renderPageContent();

					self.renderChartTable(opts);
				},
				//渲染-责任监控-责任分析
				renderDutyDutyAnalysis: function (opts) {
					var self = this;

					opts = extend({}, opts);

					opts.url = URLS.URL_DATA_MONITOR_DUTY_ANALYSIS;
					opts.tableDetail = true;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE
						]
					});
					self.renderChartTable(opts);
				}
			}
		});

	monitorDuty.showDataMonitor({
		target: DATA_MONITOR_TARGET_DUTY,
		hasBack: false
	});
});