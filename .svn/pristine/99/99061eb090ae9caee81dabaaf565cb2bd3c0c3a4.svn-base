$(function () {
	var
		CLICK_EV = 'click',

		CLASS_SHOW = 'show',
		CLASS_ACTIVE = 'active',


		extend = $.extend,
		ajax = Helpers.ajax,

		$body = $('body'),
		$btnScope = $('#btnScope'),
		$scopeList = $('#scopeList'),
	//default scope
		currentScope = DEFAULT_AREA_SCOPE,

		dynamicTimer = null,
		countDownTimer = null;

	init();

	function createChartElem() {
		return $('<div class="chart"></div>')
	}

	function init() {
		$btnScope
			.on(CLICK_EV, function (e) {
				e.stopPropagation();
				toggleScopeList();
			});
		$scopeList
			.on(CLICK_EV, function (e) {
				e.stopPropagation();
			})
			.on(CLICK_EV, 'li', function () {
				var $li = $(this),
					scope = $li.attr('data-scope');

				if (!$li.hasClass(CLASS_ACTIVE)) {
					$li.addClass(CLASS_ACTIVE)
						.siblings()
						.removeClass(CLASS_ACTIVE);
					renderByScope(scope);
					$btnScope.text($li.text());
					hideScopeList();
				}
			});
		$body
			.on(CLICK_EV, hideScopeList);

		function toggleScopeList() {
			if ($scopeList.hasClass(CLASS_SHOW)) {
				hideScopeList();
			}
			else {
				showScopeList();
			}
		}

		function showScopeList() {
			$scopeList.addClass(CLASS_SHOW);
		}

		function hideScopeList() {
			$scopeList.removeClass(CLASS_SHOW);
		}

		renderByScope();
	}

	function onRenderPageErr() {
		renderByScope();
	}

	function renderByScope(scope) {
		if (currentScope != scope) {
			changeMapDataScope(scope);
		}

		if (scope) {
			currentScope = scope;
		}
		renderStaticData();

		//if (dynamicTimer) {
		//	clearInterval(dynamicTimer);
		//	dynamicTimer = null;
		//}
		//dynamicTimer = setInterval(function () {
		//	renderDynamicIndex();
		//}, 10 * 1000);
		renderDynamicIndex();
	}

	function changeMapDataScope(scope) {
		if (typeof changeStrategyMapDataScope != 'undefined') {
			changeStrategyMapDataScope(scope);
		}
	}

	function renderDynamicIndex() {
		ajax({
			url: URLS.URL_STRATEGY_DYNAMIC_INDEX,
			data: {
				scope: currentScope
			},
			success: function (data) {
				var code = data.code,
					d = data.data;
				if (code == 0 && d) {
					renderBottomIndex(d.stat);
					renderTopIndex(d.top_indexes);
				}
			}
			// error: onRenderPageErr
		});

		function renderTopIndex(topIndexes) {
			$('#blockTop').html(template('tplTopIndex', topIndexes));
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


	function renderStaticData() {
		ajax({
			url: URLS.URL_STRATEGY_STATIC_DATA,
			data: {
				scope: currentScope
			},
			success: function (data) {
				var code = data.code,
					d = data.data;
				if (code == 0 && d) {
					renderCountDown(d.time_end);
					renderAverage(d.average);
					renderGuarantee(d.guarantee);
					renderBars(d.bars);
					renderAlarm(d.alarm);
					renderCapital(d.capital)
				}
			}
			// error: onRenderPageErr
		});


		function renderAverage(average) {
			$('#blockAverage')
				.html(template('tplAverage', average));

			renderGauge('chartAverageLabor', average.chart_config_labor, d3OptsAverage1);
			renderGauge('chartAverageSociety', average.chart_config_society, d3OptsAverage2);
		}

		function renderGauge(id, oConfig, convertFunc) {
			var opts = convertFunc(oConfig),
				data = opts.data,
				config = opts.config;

			loadLiquidFillGauge(id, data, config);
		}

		function renderGuarantee(config) {
			var $blockGuarantee = $('#blockGuarantee');

			$blockGuarantee.html(template('tplGuarantee'));

			var $chartEdu = $blockGuarantee.find('.chart-edu'),
				$chartMedical = $blockGuarantee.find('.chart-medical'),
				$chartHouse = $blockGuarantee.find('.chart-house'),

				chartEdu = echarts.init($chartEdu[0]),
				chartMedical = echarts.init($chartMedical[0]),
				chartHouse = echarts.init($chartHouse[0]);

			chartEdu.setOption(commonEchartConfig.genGuaranteeConfig(config.chart_config_edu));
			chartMedical.setOption(commonEchartConfig.genGuaranteeConfig(config.chart_config_medical));
			chartHouse.setOption(commonEchartConfig.genGuaranteeConfig(config.chart_config_house));
		}

		function renderBars(bars) {
			var $blockBars = $('#blockBars')
				.html(template('tplBars', {
					list: bars
				}));
			Vote.ListShow.init({
				id: 'chartBars',
				percent: bars.map(function (item) {
					return item.percent / 100;
				}),
				width: $blockBars.find('.litem:first').width()
			});
			Vote.ListShow.go();
		}

		function renderAlarm(alarm) {
			$('#blockAlarm').html(template('tplAlarm', alarm));
		}

		function renderCapital(capital) {
			$('#blockCapital').html(template('tplCapital', capital));
			echarts.init(document.getElementById('chartPie')).setOption(commonEchartConfig.genChartPieConfig(capital.chart_config_pie));
			echarts.init(document.getElementById('chartBubble')).setOption(commonEchartConfig.genChartBubbleConfig(capital.chart_config_bubble));
		}

		function renderCountDown(timestamp) {
			//毫秒1000*60*60*24*2
			countdown();

			function countdown() {
				if (countDownTimer) {
					clearInterval(countDownTimer);
				}

				countDownTimer = setInterval(count, 1000);

				function count() {
					var intDiff = parseInt((timestamp - Date.now()) / 1000),
						day = 0,
						hour = 0,
						minute = 0,
						second = 0;//时间默认值
					if (intDiff > 0) {
						day = Math.floor(intDiff / (60 * 60 * 24));
						hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
						minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
						second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
					}
					if (day <= 9) {
						day = '0' + day;
					}
					if (hour <= 9) {
						hour = '0' + hour;
					}
					if (minute <= 9) {
						minute = '0' + minute;
					}
					if (second <= 9) {
						second = '0' + second;
					}
					$('#dayshow').html(day);
					$('#hourshow').html(hour);
					$('#minuteshow').html(minute);
					$('#secondshow').html(second);
				}
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
			config.maxValue=7365;

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
	}
});
