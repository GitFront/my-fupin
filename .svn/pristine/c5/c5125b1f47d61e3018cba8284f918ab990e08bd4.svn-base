$(function () {
	var
		CLICK_EV = 'click',

		CLASS_SHOW = 'show',
		CLASS_ACTIVE = 'active',


		STRATEGY_SCOPE = {
			QUANSHENG: 'quansheng',
			XIANGDUIPINKUNCUN: 'xiangduipinkuncun',
			FUNSANCUN: 'funsancun',
			GEMINGLAOQU: 'geminglaoqu',
			ZHONGYANGSUQU: 'zhongyangsuqu'
		},

		extend = $.extend,
		ajax = function (opts) {
			$.ajax(extend({
				dataType: 'json'
			}, opts));
		},

		$body = $('body'),
		$btnScope = $('#btnScope'),
		$scopeList = $('#scopeList'),
	//default scope
		currentScope = STRATEGY_SCOPE.QUANSHENG,

		dynamicTimer = null;

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

	function renderByScope(scope) {
		if (scope) {
			currentScope = scope;
		}
		renderStaticData();

		if(dynamicTimer){
			clearInterval(dynamicTimer);
			dynamicTimer = null;
		}
		dynamicTimer = setInterval(function(){
			renderDynamicIndex();
		}, 10 * 1000);
		renderDynamicIndex();
	}

	//TODO 这段代码为了只DEMO求速度用了随机数模拟，读接口的场合不适用
	function renderDynamicIndex() {
		ajax({
			url: ctx+'/data/strategy/dynamic_index.json',
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
		});

		function renderTopIndex(topIndexes) {
			$('#blockTop').html(template('tplTopIndex', topIndexes));
			//TODO
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

			function saveLastStat(){
				renderBottomIndex.lastStat = stat;

				//TODO debug 模拟增加
				var o = {};
				$.map(stat, function(v, k){
					o[k] = v - 100;
				});
				renderBottomIndex.lastStat = o;
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
			url: ctx+'/data/strategy/static_data.json',
			data: {
				scope: currentScope
			},
			success: function (data) {
				var code = data.code,
					d = data.data;
				if (code == 0 && d) {
					renderCountDown(d.time_end);
					renderAverage(d.average);
					renderGuarantee(d.chart_config_guarantee);
					renderBars(d.chart_config_bars);
					renderAlarm(d.alarm);
					renderCapital(d.capital)
				}
			}
		});


		function renderAverage(average) {
			$('#blockAverage')
				.html(template('tplAverage', average));
			//TODO
		}

		function renderGuarantee(config) {
			var $chart = createChartElem();
			$('#blockGuarantee')
				.html($chart);
			//TODO
		}

		function renderBars(config) {
			var $chart = createChartElem();
			$('#blockBars')
				.html($chart);
			//TODO
		}

		function renderAlarm(alarm) {
			$('#blockAlarm').html(template('tplAlarm', alarm));
		}

		function renderCapital(capital) {
			$('#blockCapital').html(template('tplCapital', capital));
			//TODO
		}

		function renderCountDown(timestamp) {
			var intDiff = timestamp - Date.now();//毫秒1000*60*60*24*2

			countdown(intDiff);

			function countdown(intDiff) {
				intDiff = parseInt(intDiff / 1000);
				window.setInterval(function () {
					var day = 0,
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
					intDiff--;
				}, 1000);
			}
		}

	}


});
