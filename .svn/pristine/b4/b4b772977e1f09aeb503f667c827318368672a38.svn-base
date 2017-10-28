(function (Helpers, Vue) {
	var
		RESIZE_EV = 'resize',

		doc = document,
		win = window,

		Components = Helpers.Components;

	Components.register({
		tagName: 'score-chart',
		tplName: 'components',
		factory: function () {
			Vue.component('score-chart', {
				template: '#tplScoreChart',
				props: {
					score: {
						type: Number
					}
				},
				mounted: function () {
					var render = this.__render;
					render();
					win.addEventListener(RESIZE_EV, render);
				},
				updated: function () {
					this.__render();
				},
				beforeDestroyed: function () {
					win.removeEventListener(RESIZE_EV, this.__render);
				},
				methods: {
					__render: function () {
						var me = this,
							el = me.$el,
							score = me.score;

						el.innerHTML = '';

						if (typeof score != 'undefined') {
							drawDonutChart(el, score);
						}

						function drawDonutChart(parentEl, percent, width, height, text_y) {
							width = typeof width !== 'undefined' ? width : parentEl.clientWidth;
							height = typeof height !== 'undefined' ? height : parentEl.clientHeight;

							var dataset = {
									lower: calcPercent(0),
									upper: calcPercent(percent)
								},
								radius = Math.min(width, height) / 2,
								borderWidth = 4 / 248 * width,
								pie = d3.layout
									.pie()
									.sort(null)
									.startAngle(Math.PI * 2)
									.endAngle(0),
								format = d3.format(".0%");

							var arc = d3.svg.arc()
								.innerRadius(radius - borderWidth)
								.outerRadius(radius)
								.cornerRadius(borderWidth / 2);

							var svg = d3.select(parentEl).append("svg")
								.attr("width", width)
								.attr("height", height)
								.append("g")
								.attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

							var path = svg.selectAll("path")
								.data(pie(dataset.lower))
								.enter().append("path")
								.attr("class", function (d, i) {
									return "color" + i
								})
								.attr("d", arc)
								.each(function (d) {
									this._current = d;
								}); // store the initial values

							var text = svg.append("text")
								.attr("text-anchor", "middle");

							if (typeof(percent) === "string") {
								text.text(percent);
							}
							else {
								var progress = 0;
								var timeout = setTimeout(function () {
									clearTimeout(timeout);
									path = path.data(pie(dataset.upper)); // update the data
									path.transition().duration(500).attrTween("d", function (a) {
										// Store the displayed angles in _current.
										// Then, interpolate from _current to the new angles.
										// During the transition, _current is updated in-place by d3.interpolate.
										var i = d3.interpolate(this._current, a);
										var i2 = d3.interpolate(progress, percent);
										this._current = i(0);
										return function (t) {
											text.text(format(i2(t) / 100).replace('%', '分'));
											return arc(i(t));
										};
									}); // redraw the arcs
								}, 200);
							}
						}

						function calcPercent(percent) {
							return [percent, 100 - percent];
						}
					}
				}
			});
		}
	});
})(Helpers, Vue);