$(function () {
	var CLICK_EV = 'click',

		extend = $.extend,

		monitorHelper = $.monitorHelper,

		createDataMonitor = monitorHelper.createDataMonitor,

		ROW_KEY = $.components.ROW_KEY,
		CONST_RADIO_VALUES = $.components.CONST_RADIO_VALUES,

		PROJECT_ATTRIBUTE_VALUES = CONST_RADIO_VALUES.PROJECT_ATTRIBUTE,

		//项目监控
		DATA_MONITOR_TARGET_PROJECT = {
			TITLE: '扶贫项目管理',
			TYPE: 'project',
			TAB: [
				{
					NAME: '项目管理',
					VALUE: 'project_management'
				},
				{
					NAME: '项目分析',
					VALUE: 'project_analysis'
				},
				{
					NAME: '项目排序',
					VALUE: 'project_order',
					CONFIG_ORDER: 'true'
				}
			]
		},
		//项目监控户扶贫项目分析
		DATA_MONITOR_TARGET_PROJECT_FAMILY = {
			TITLE: '户扶贫项目分析',
			TYPE: 'project_family',
			TAB: [
				{
					NAME: '产业扶贫',
					VALUE: 'industry'
				},
				{
					NAME: '金融扶贫',
					VALUE: 'finance'
				},
				{
					NAME: '住房改造',
					VALUE: 'house'
				},
				{
					NAME: '资产扶贫',
					VALUE: 'property'
				},
				{
					NAME: '慰问扶贫',
					VALUE: 'visit'
				},
				{
					NAME: '就业扶贫',
					VALUE: 'employment'
				},
				{
					NAME: '技能培训',
					VALUE: 'skill'
				},
				{
					NAME: '教育扶贫',
					VALUE: 'edu'
				}
			]
		},
		//项目监控村扶贫项目分析
		DATA_MONITOR_TARGET_PROJECT_COUNTRY = {
			TITLE: '村扶贫项目分析',
			TYPE: 'project_country',
			TAB: [
				{
					NAME: '村道硬底化',
					VALUE: 'road'
				},
				{
					NAME: '饮水工程',
					VALUE: 'water'
				},
				{
					NAME: '文体设施',
					VALUE: 'recreation_sport'
				},
				{
					NAME: '卫生设施',
					VALUE: 'hygiene'
				},
				{
					NAME: '路灯安装',
					VALUE: 'lamp'
				},
				{
					NAME: '农田水利',
					VALUE: 'farm'
				},
				{
					NAME: '公共设施',
					VALUE: 'public_facility'
				},
				{
					NAME: '集体经济',
					VALUE: 'collective_economy'
				},
				{
					NAME: '教育教学',
					VALUE: 'edu'
				}
			]
		},

		//项目监控
		monitorProject = createDataMonitor({
			mix: {
				//渲染-扶贫项目管理-项目管理
				renderProjectProjectManagement: function (opts) {
					var self = this;

					opts = extend({}, opts);

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_PROJECT_MANAGEMENT;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.PROJECT_ATTRIBUTE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-扶贫项目管理-项目分析
				renderProjectProjectAnalysis: function (opts) {
					var self = this,

						$monitorContent = self.$popupContent;

					opts = extend({}, opts);
					opts.tableDetail = true;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE,
							{
								rowKey: ROW_KEY.PROJECT_ATTRIBUTE,
								handler: function () {
									var $elem = $(this),
										value = $elem.attr('data-value');

									changeContent(value);
								}
							}
						]
					});

					changeContent(PROJECT_ATTRIBUTE_VALUES.ALL.value);

					function changeContent(value) {
						var url,
							tplID,
							bindFunc;

						var year = self.getCurYear(),
							area_level = self.treeCurLevel,
							area_id = self.treeCurAreaID,

							targetOpts = {
								year: year,
								area_level: area_level,
								area_id: area_id
							};

						switch (value) {
							case PROJECT_ATTRIBUTE_VALUES.ALL.value:
								url = URLS.URL_DATA_MONITOR_PROJECT_PROJECT_ANALYSIS_ALL;
								tplID = 'tplProjectProjectAnalysisAllChartTable';
								break;
							case PROJECT_ATTRIBUTE_VALUES.COUNTRY.value:
								url = URLS.URL_DATA_MONITOR_PROJECT_PROJECT_ANALYSIS_COUNTRY;
								tplID = 'tplProjectProjectAnalysisCountryChartTable';
								bindFunc = function () {
									$monitorContent.find('.link-more')
										.on(CLICK_EV, function () {
											monitorProjectCountry.showDataMonitor(extend(targetOpts, {
												target: DATA_MONITOR_TARGET_PROJECT_COUNTRY
											}));
										});
								};
								break;
							case PROJECT_ATTRIBUTE_VALUES.FAMILY.value:
								url = URLS.URL_DATA_MONITOR_PROJECT_PROJECT_ANALYSIS_FAMILY;
								tplID = 'tplProjectProjectAnalysisCountryChartTable';
								bindFunc = function () {
									$monitorContent.find('.link-more')
										.on(CLICK_EV, function () {
											monitorProjectFamily.showDataMonitor(extend(targetOpts ,{
												target: DATA_MONITOR_TARGET_PROJECT_FAMILY
											}));
										});
								};
								break;
						}

						opts.url = url;
						opts.tplID = tplID;
						self.renderChartTable(opts, bindFunc);
					}
				}
			}
		}),
		//项目监控户扶贫项目分析
		monitorProjectFamily = createDataMonitor({
			mix: {
				//渲染-项目监控户扶贫项目分析-产业扶贫
				renderProjectFamilyIndustry: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_FAMILY_INDUSTRY;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE,
							ROW_KEY.POOR_ATTRIBUTE,
							ROW_KEY.LABOR_ATTRIBUTE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-项目监控户扶贫项目分析-金融扶贫
				renderProjectFamilyFinance: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_FAMILY_FINANCE;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE,
							ROW_KEY.POOR_ATTRIBUTE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-项目监控户扶贫项目分析-住房改造
				renderProjectFamilyHouse: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_FAMILY_HOUSE;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE,
							ROW_KEY.POOR_ATTRIBUTE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-项目监控户扶贫项目分析-资产扶贫
				renderProjectFamilyProperty: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_FAMILY_PROPERTY;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE,
							ROW_KEY.POOR_ATTRIBUTE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-项目监控户扶贫项目分析-慰问扶贫
				renderProjectFamilyVisit: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_FAMILY_VISIT;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE,
							ROW_KEY.POOR_ATTRIBUTE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-项目监控户扶贫项目分析-就业扶贫
				renderProjectFamilyEmployment: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_FAMILY_EMPLOYMENT;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE,
							ROW_KEY.POOR_ATTRIBUTE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-项目监控户扶贫项目分析-技能培训
				renderProjectFamilySkill: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_FAMILY_SKILL;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE,
							ROW_KEY.POOR_ATTRIBUTE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-项目监控户扶贫项目分析-教育扶贫
				renderProjectFamilyEdu: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_FAMILY_EDU;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE,
							ROW_KEY.POOR_ATTRIBUTE
						]
					});
					self.renderChartTable(opts);
				}
			}
		}),
		//项目监控村扶贫项目分析
		monitorProjectCountry = createDataMonitor({
			mix: {
				//渲染-村扶贫项目分析-村道硬底化
				renderProjectCountryRoad: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_COUNTRY_ROAD;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-村扶贫项目分析-饮水工程
				renderProjectCountryWater: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_COUNTRY_WATER;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-村扶贫项目分析-文体设施
				renderProjectCountryRecreationSport: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_COUNTRY_RECREATION_SPORT;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-村扶贫项目分析-卫生设施
				renderProjectCountryHygiene: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_COUNTRY_HYGIENE;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-村扶贫项目分析-路灯安装
				renderProjectCountryLamp: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_COUNTRY_LAMP;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-村扶贫项目分析-农田水利
				renderProjectCountryFarm: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_COUNTRY_FARM;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-村扶贫项目分析-公共设施
				renderProjectCountryPublicFacility: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_COUNTRY_PUBLIC_FACILITY;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-村扶贫项目分析-集体经济
				renderProjectCountryCollectiveEconomy: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_COUNTRY_COLLECTIVE_ECONOMY;

					self.renderPageContent();

					self.buildCheckArea(opts, {
						rowKeyArr: [
							ROW_KEY.SCOPE
						]
					});
					self.renderChartTable(opts);
				},
				//渲染-村扶贫项目分析-教育教学
				renderProjectCountryEdu: function (opts) {
					var self = this;

					opts = extend({}, opts);
					opts.tableDetail = true;

					opts.url = URLS.URL_DATA_MONITOR_PROJECT_COUNTRY_EDU;

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

	monitorProject.showDataMonitor({
		target: DATA_MONITOR_TARGET_PROJECT,
		hasBack: false
	});
});
