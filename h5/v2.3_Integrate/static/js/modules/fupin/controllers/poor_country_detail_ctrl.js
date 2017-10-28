$(function() {
	var
		extend = $.extend,
		win = window,
		Helpers = win.Helpers,
		ajax = Helpers.ajax,

		monitorHelper = $.monitorHelper,
		createDataMonitor = monitorHelper.createDataMonitor,

		CLICK_EV = 'click',
		LOAD_EV = 'load',

		CLASS_ACTIVE = 'active',
		CLASS_DISABLED = 'disabled',
		CLASS_OPENED = 'opened',
		CLASS_HIDE = 'hide',

		SRC_FAMILY = 'family',
		SRC_COUNTRY = 'country',

		TPL_STAT_NOT_LOAD = 0,
		TPL_STAT_LOADING = 1,
		TPL_STAT_LOADED = 2,

		FAMILY_TARGETS = {
			//贫困户档案家庭成员
			MEMBERS: 'members',
			//贫困户档案生产生活条件
			CONDITION: 'condition',
			//贫困户档案帮扶计划
			PLAN: 'plan',
			//贫困户档案帮扶施策
			IMPLEMENT: 'implement',
			//贫困户档案帮扶动态
			NEWS: 'news',
			//贫困户档案可支配收入
			INCOME: "income",
			//贫困户档案脱贫轨迹
			ELIMINATE_PATH: 'eliminate_path',
			//贫困户档案资金投入
			INVESTED: 'invested',
			//贫困户档案数据轨迹
			DATA_PATH: 'data_path',
			//贫困户档案业务办理
			BUSINESS: 'business'
		},
		COUNTRY_TARGETS = {
			//村档案基本情况
			STATUS: 'status',
			//村档案发展现状
			DEVELOPMENT_STATUS: 'development_status',
			//村档案帮扶计划
			PLAN: 'plan',
			//村档案帮扶施策
			IMPLEMENT: 'implement',
			//村档案驻村动态
			NEWS: 'news',
			//村档案会议动态
			MEETING_NEWS: 'meeting_news',
			//村档案脱贫轨迹
			ELIMINATE_PATH: 'eliminate_path',
			//村档案资金投入
			INVESTED: 'invested',
			//村档案数据轨迹
			DATA_PATH: 'data_path',
			//村档案业务办理
			BUSINESS: 'business'
		},

		FAMILY_STAT = {
			//已脱贫
			SUCCESS: 1
				//其他用户暂时全部是异常状态，先不作声明
		},

		extend = $.extend,
		ajax = function(opts) {
			var oCompleteCB = opts.complete,
				oSuccessCB = opts.success,
				oErrorCB = opts.error;

			function onSuccessCB() {
				hideLoadingMask();
				oSuccessCB && oSuccessCB.apply(this, arguments);
			}

			function onErrorCB() {
				hideLoadingMask();
				oErrorCB && oErrorCB.apply(this, arguments);
			}

			showLoadingMask();
			Helpers.ajax(extend({
				cache: false
			}, opts, {
				success: onSuccessCB,
				error: onErrorCB
			}));
		},

		tplLoadStat = TPL_STAT_NOT_LOAD,
		requestID = 0,

		$popupCountryFile,
		$popupFamilyFile,

		$popupCountryFileContent,
		$popupFamilyFileContent;

	loadTpl(null, true);

	function loadTpl(cb, isInit) {
		if (tplLoadStat === TPL_STAT_LOADED) {
			if (cb) {
				cb();
			}
		} else if (tplLoadStat === TPL_STAT_NOT_LOAD) {
			load();
		}

		function load() {
			//load template
			$.ajax({
				url: URLS.URL_TPL_POOR_FILE,
				dataType: 'text',
				success: function(tpl) {
					if (!tplLoadStat) {
						$('body').append(tpl);

						$popupCountryFile = $('#popupCountryFile');
						$popupFamilyFile = $('#popupFamilyFile');

						$popupCountryFileContent = $popupCountryFile.find('.popup-content');
						$popupFamilyFileContent = $popupFamilyFile.find('.popup-content');

						$popupCountryFile
							.on(CLICK_EV, '.btn-close', hideCountryFile);

						$popupFamilyFile
							.on(CLICK_EV, '.btn-close', hideFamilyFile);

						tplLoadStat = true;
					}

					tplLoadStat = TPL_STAT_LOADED;

					if (cb) {
						cb();
					}
				},
				error: function() {
					if (!isInit) {
						// load();
					}
				}
			});
		}
	}

	function setAvatarFallback($elem) {
		var SRC_DEFAULT_AVATAR = '../../../static/themes/images/fupin_strategy/default_avatar.jpg';

		$elem.find('.avatar img')
			.each(function() {
				var img = this,
					$img = $(img);
				img.onerror = function() {
					if (!$img.data('tried')) {
						img.src = SRC_DEFAULT_AVATAR;
						$img.data('tried', true);
					}
				};
			});
	}

	function showCountryFile(id, target) {
		loadTpl(function() {
			$popupCountryFile.show();

			$popupCountryFileContent.empty();

			if (!target) {
				target = COUNTRY_TARGETS.STATUS;
			}

			ajax({
				url: URLS.URL_COUNTRY_FILE_BASIC,
				data: {
					id: id
				},
				success: function(data) {
					var code = data.code,
						d = data.data;
					if (code == 0 && d) {
						$popupCountryFileContent
							.html(template('tplCountryFileBasic', d));

						$popupCountryFileContent.find('.tipso')
							.each(function() {
								var $elem = $(this);
								$elem.tipso(extend({}, commonTipsoConfig, {
									content: $elem.attr('data-title')
								}));
							});

						setAvatarFallback($popupCountryFile);

						initCountryFileSubContent(id, target);
					}
				},
				error: function() {
					hideCountryFile();
				}
			});
		});
	}

	function showFamilyFile(id, target) {
		loadTpl(function() {
			$popupFamilyFile.show();

			$popupFamilyFileContent.empty();

			if (!target) {
				target = FAMILY_TARGETS.MEMBERS;
			}

			ajax({
				url: URLS.URL_FAMILY_FILE_BASIC,
				data: {
					id: id
				},
				success: function(data) {
					var code = data.code,
						d = data.data;
					if (code == 0 && d) {
						$popupFamilyFileContent
							.html(template('tplFamilyFileBasic', extend(d, {
								FAMILY_STAT: FAMILY_STAT
							})));

						$popupFamilyFile.find('.tipso')
							.each(function() {
								var $elem = $(this);
								$elem.tipso(extend({}, commonTipsoConfig, {
									content: $elem.attr('data-title')
								}));
							});

						setAvatarFallback($popupFamilyFile);

						initFamilyFileSubContent(id, target);
					}
				},
				error: function() {
					hideFamilyFile();
				}
			});
		});
	}

	function switchFamilyFileSubContent(id, target) {
		var
			$pageTabs = $popupFamilyFileContent.find('.page-tabs'),
			$activeTab = $pageTabs.find('li.' + CLASS_ACTIVE),
			$pageTabContent = $popupFamilyFileContent.find('.page-tab-content'),

			activeTarget = $activeTab.attr('data-target'),

			url,
			extraData,
			successCB;

		if (activeTarget != target) {

			$pageTabs.find('li[data-target="' + target + '"]')
				.addClass(CLASS_ACTIVE)
				.siblings('li')
				.removeClass(CLASS_ACTIVE);

			$pageTabContent.empty();

			switch (target) {
				case FAMILY_TARGETS.MEMBERS:
					url = URLS.URL_FAMILY_FILE_MEMBERS;
					successCB = function(d) {
						$pageTabContent.html(template('tplTabContentFamilyMembers', d));

						var
							$pageMembers = $pageTabContent.find('.page-members'),
							$pageMembersAlbum = $pageTabContent.find('.page-members-album'),

							$memberTable = $pageMembers.find('.member-table'),
							$memberAlbum = $pageMembers.find('.member-album'),

							$memberDetailAlbum = $pageMembersAlbum.find('.detail-album'),

							$carousel,
							$pageCur;

						$memberTable
							.jScrollPane(commonJSPConfig);
						$memberAlbum
							.on(CLICK_EV, 'img', function() {
								var $elem = $(this),
									index = $elem.attr('data-index');

								goPageMembersAlbum(+index);
							})
							.jScrollPane(commonJSPConfig);

						$pageMembersAlbum
							.on(CLICK_EV, '.btn-prev', function(e) {
								$carousel.trigger('prev.owl.carousel');
								e.stopPropagation();
							})
							.on(CLICK_EV, '.btn-next', function() {
								$carousel.trigger('next.owl.carousel');
								e.stopPropagation();
							})
							.on(CLICK_EV, '.btn-back', function() {
								goPageMembers();
							});

						function goPageMembers() {
							$pageMembersAlbum.hide();
							$pageMembers.show();
						}

						function goPageMembersAlbum(index) {
							if (!$carousel) {
								$carousel = $memberDetailAlbum.find('.ul')
									.owlCarousel({
										items: 1,
										loop: true,
										mouseTouch: false
									})
									.on('changed.owl.carousel', function(e) {
										$pageCur.text(e.page.index + 1);
									});

								$pageCur = $memberDetailAlbum.find('.page-cur')
									.text(index + 1);
							}
							$carousel.trigger('to.owl.carousel', [index]);

							$pageMembers.hide();
							$pageMembersAlbum.show();
						}

					};
					break;
				case FAMILY_TARGETS.CONDITION:
					url = URLS.URL_FAMILY_FILE_CONDITION;
					successCB = function(d) {
						$pageTabContent.html(template('tplTabContentFamilyCondition', d));
					};
					break;
				case FAMILY_TARGETS.PLAN:
					url = URLS.URL_FAMILY_FILE_PLAN;
					successCB = function(d) {
						$pageTabContent.html(template('tplTabContentFamilyPlan', d));

						var
							$subTabs = $pageTabContent.find('.sub-tabs'),
							$subTabContent = $pageTabContent.find('.sub-tab-content'),
							$subTabContentLis = $subTabContent.find('li'),
							activeJSPs = {};

						//NOTE：同一个dom destroy过jsp就不能再次调用jsp，因此不destroy

						$subTabs.on(CLICK_EV, 'li', function() {
							var $li = $(this),
								index = $li.attr('data-index');
							if (!$li.hasClass(CLASS_ACTIVE)) {
								$li.addClass(CLASS_ACTIVE)
									.siblings('li')
									.removeClass(CLASS_ACTIVE);

								var $activeTabContent = $subTabContentLis.eq(index);

								$activeTabContent
									.addClass(CLASS_ACTIVE)
									.siblings('li')
									.removeClass(CLASS_ACTIVE);

								if (!activeJSPs[index]) {
									activeJSPs[index] = $activeTabContent
										.find('.content-wrapper')
										.jScrollPane(commonJSPConfig)
										.data('jsp');
								}
							}
						});

						if ($subTabContentLis.length > 0) {
							activeJSPs[0] = $subTabContentLis
								.filter('.' + CLASS_ACTIVE)
								.jScrollPane(commonJSPConfig)
								.data('jsp');
						}
					};
					break;
				case FAMILY_TARGETS.IMPLEMENT:
					(function() {
						loadSubTogglesYear({
							$pageTabContent: $pageTabContent,
							url: URLS.URL_FAMILY_FILE_IMPLEMENT,
							params: {
								id: id
							},
							success: function(d, ret) {
								var $subTogglesContent = ret.$subTogglesContent,
									refreshPageJSP = ret.refreshPageJSP;

								$subTogglesContent.html(template('tplTabContentFamilyImplement', d));

								var
									list = d.table.list,

									$pageImplement = $subTogglesContent.find('.page-implement'),
									$pageImplementAlbum = $subTogglesContent.find('.page-implement-album'),

									$tableWrapper = $pageImplement.find('.table-wrapper');

								$tableWrapper
									.on(CLICK_EV, '.ico-pic', function() {
										var $elem = $(this),
											index = $elem.attr('data-index');

										goPageImplementAlbum(list[index]);
									});

								refreshPageJSP();

								$pageImplementAlbum.on(CLICK_EV, '.btn-back', function() {
									goPageImplement();
								});

								function goPageImplement() {
									$pageImplementAlbum.hide();
									$pageImplement.show();
									refreshPageJSP();
								}

								function goPageImplementAlbum(item) {
									$pageImplement.hide();
									$pageImplementAlbum
										.html(template('tplTabContentImplementAlbum', item))
										.show();
									refreshPageJSP();
								}
							}
						});
					})();
					break;
				case FAMILY_TARGETS.NEWS:
					url = URLS.URL_FAMILY_FILE_NEWS;
					successCB = function(d) {
						renderNews(id, $pageTabContent, d, URLS.URL_FAMILY_FILE_NEWS_DETAIL, {
							src: SRC_FAMILY
						});
					};
					break;
				case FAMILY_TARGETS.INCOME:
					(function() {
						loadSubTogglesYear({
							$pageTabContent: $pageTabContent,
							url: URLS.URL_FAMILY_FILE_INCOME,
							params: {
								id: id
							},
							success: function(d, ret) {
								var defaultTime = d.default_time;

								var $subTogglesContent = ret.$subTogglesContent,
									refreshPageJSP = ret.refreshPageJSP;

								$subTogglesContent.html(template('tplTabContentFamilyIncome', d));

								var $chartSection = $subTogglesContent.find('.chart-section'),
									$tableSection = $subTogglesContent.find('.table-section'),
									$chart = $chartSection.find('.chart'),

									chart = echarts.init($chart[0]);

								chart.setOption(commonEchartConfig.genChartBarsConfig(d.chart_config_bar));

								chart.on(CLICK_EV, function(params) {
									var params_arr = params.name.split(".");
									var time = {};
									time.year = params_arr[0];
									time.month = params_arr[1];

									if (time) {
										renderTable(time);
									}
								});

								if (defaultTime) {
									renderTable(defaultTime);
								}
								refreshPageJSP();

								function renderTable(time) {
									var year = time.year,
										month = time.month;

									function loadTable() {
										ajax({
											url: URLS.URL_FAMILY_FILE_INCOME_TABLE,
											data: {
												id: id,
												year: year,
												month: month
											},
											success: function(data) {
												var code = data.code,
													d = data.data;

												if (code == 0 && d) {
													$tableSection.html(template('tplTabContentFamilyIncomeTable', d));

													var $tableFloat = $tableSection.find('.table-float-wrapper'),
														tableJSP = $tableFloat.data('jsp');

													if (!tableJSP) {
														tableJSP = $tableFloat.jScrollPane(commonJSPConfig)
															.data('jsp');
													}

													refreshPageJSP();
													tableJSP.reinitialise();
												}
											}
										});
									}

									loadTable();
								}
							}
						});
					})();
					break;
				case FAMILY_TARGETS.ELIMINATE_PATH:
					(function() {
						loadSubTogglesYear({
							$pageTabContent: $pageTabContent,
							url: URLS.URL_FAMILY_FILE_ELIMINATE_PATH,
							params: {
								id: id
							},
							success: function(d, ret) {
								renderEliminatePath(id, ret, d, URLS.URL_FAMILY_FILE_ELIMINATE_SCORES);
							}
						});
					})();
					break;
				case FAMILY_TARGETS.INVESTED:
					(function() {
						loadSubTogglesYear({
							$pageTabContent: $pageTabContent,
							url: URLS.URL_FAMILY_FILE_INVESTED,
							params: {
								id: id
							},
							success: function(d, ret) {
								renderInvested(ret, 'tplTabContentFamilyInvested', d);
							}
						});
					})();
					break;
				case FAMILY_TARGETS.DATA_PATH:
					url = URLS.URL_FAMILY_FILE_DATA_PATH;
					successCB = function(d) {
						renderDataPath($pageTabContent, d);
					};
					break;
				case FAMILY_TARGETS.BUSINESS:
					url = URLS.URL_FAMILY_FILE_BUSINESS;
					successCB = function(d) {
						renderBusiness($pageTabContent, d, URLS.URL_FAMILY_FILE_BUSINESS_REST, id);
					};
					break;
			}

			if (url) {
				load();
			}
		}

		function load() {
			ajax({
				url: url,
				data: {
					id: id
				},
				success: function(data) {
					var code = data.code,
						d = data.data;
					if (code == 0 && d) {
						successCB(d);
					}
				},
				error: function() {
					// load();
				}
			});
		}
	}

	function switchCountryFileSubContent(id, target) {
		var
			$pageTabs = $popupCountryFileContent.find('.page-tabs'),
			$activeTab = $pageTabs.find('li.' + CLASS_ACTIVE),
			$pageTabContent = $popupCountryFileContent.find('.page-tab-content'),

			activeTarget = $activeTab.attr('data-target'),

			url,
			successCB;

		if (activeTarget != target) {
			$pageTabs.find('li[data-target="' + target + '"]')
				.addClass(CLASS_ACTIVE)
				.siblings('li')
				.removeClass(CLASS_ACTIVE);

			$pageTabContent.empty();

			switch (target) {
				case COUNTRY_TARGETS.STATUS:
					url = URLS.URL_COUNTRY_FILE_STATUS;
					successCB = function(d) {
						$pageTabContent.html(template('tplCountryFileStatus', d));
					};
					break;
				case COUNTRY_TARGETS.DEVELOPMENT_STATUS:
					url = URLS.URL_COUNTRY_FILE_DEVELOPMENT_STATUS;
					successCB = function(d) {
						$pageTabContent.html(template('tplCountryFileDevelopmentStatus', d));

						var $contentWrapper = $pageTabContent.find('.content-wrapper');

						$contentWrapper
							.jScrollPane(commonJSPConfig);
					};
					break;
				case COUNTRY_TARGETS.PLAN:
					url = URLS.URL_COUNTRY_FILE_PLAN;
					successCB = function(d) {
						$pageTabContent.html(template('tplTabContentCountryPlan', d));

						var
							$subTabs = $pageTabContent.find('.sub-tabs'),
							$subTabContent = $pageTabContent.find('.sub-tab-content'),
							$subTabContentLis = $subTabContent.find('li'),
							activeJSPs = {};

						//NOTE：同一个dom destroy过jsp就不能再次调用jsp，因此不destroy

						$subTabs.on(CLICK_EV, 'li', function() {
							var $li = $(this),
								index = $li.attr('data-index');
							if (!$li.hasClass(CLASS_ACTIVE)) {
								$li.addClass(CLASS_ACTIVE)
									.siblings('li')
									.removeClass(CLASS_ACTIVE);

								var $activeTabContent = $subTabContentLis.eq(index);

								$activeTabContent
									.addClass(CLASS_ACTIVE)
									.siblings('li')
									.removeClass(CLASS_ACTIVE);

								if (!activeJSPs[index]) {
									activeJSPs[index] = $activeTabContent
										.find('.content-wrapper')
										.jScrollPane(commonJSPConfig)
										.data('jsp');
								}
							}
						});

						if ($subTabContentLis.length > 0) {
							activeJSPs[0] = $subTabContentLis
								.filter('.' + CLASS_ACTIVE)
								.jScrollPane(commonJSPConfig)
								.data('jsp');
						}
					};
					break;
				case COUNTRY_TARGETS.IMPLEMENT:
					(function() {
						loadSubTogglesYear({
							$pageTabContent: $pageTabContent,
							url: URLS.URL_COUNTRY_FILE_IMPLEMENT,
							params: {
								id: id
							},
							success: function(d, ret) {
								var $subTogglesContent = ret.$subTogglesContent,
									refreshPageJSP = ret.refreshPageJSP;

								$subTogglesContent.html(template('tplTabContentCountryImplement', d));

								var
									list = d.table.list,

									$pageImplement = $subTogglesContent.find('.page-implement'),
									$pageImplementAlbum = $subTogglesContent.find('.page-implement-album'),

									$tableWrapper = $pageImplement.find('.table-wrapper');

								$tableWrapper
									.on(CLICK_EV, '.ico-pic', function() {
										var $elem = $(this),
											index = $elem.attr('data-index');

										goPageImplementAlbum(list[index]);
									});

								refreshPageJSP();

								$pageImplementAlbum.on(CLICK_EV, '.btn-back', function() {
									goPageImplement();
								});

								function goPageImplement() {
									$pageImplementAlbum.hide();
									$pageImplement.show();
									refreshPageJSP();
								}

								function goPageImplementAlbum(item) {
									$pageImplement.hide();
									$pageImplementAlbum
										.html(template('tplTabContentImplementAlbum', item))
										.show();
									refreshPageJSP();
								}
							}
						});
					})();
					break;
				case COUNTRY_TARGETS.NEWS:
					url = URLS.URL_COUNTRY_FILE_NEWS;
					successCB = function(d) {
						renderNews(id, $pageTabContent, d, URLS.URL_COUNTRY_FILE_NEWS_DETAIL);
					};
					break;
				case COUNTRY_TARGETS.MEETING_NEWS:
					url = URLS.URL_COUNTRY_FILE_MEETING_NEWS;
					successCB = function(d) {
						renderNews(id, $pageTabContent, d, URLS.URL_COUNTRY_FILE_MEETING_NEWS_DETAIL);
					};
					break;
				case COUNTRY_TARGETS.ELIMINATE_PATH:
					(function() {
						loadSubTogglesYear({
							$pageTabContent: $pageTabContent,
							url: URLS.URL_COUNTRY_FILE_ELIMINATE_PATH,
							params: {
								id: id
							},
							success: function(d, ret) {
								renderEliminatePath(id, ret, d, URLS.URL_COUNTRY_FILE_ELIMINATE_SCORES);
							}
						});
					})();
					break;
				case COUNTRY_TARGETS.INVESTED:
					(function() {
						loadSubTogglesYear({
							$pageTabContent: $pageTabContent,
							url: URLS.URL_COUNTRY_FILE_INVESTED,
							params: {
								id: id
							},
							success: function(d, ret) {
								renderInvested(ret, 'tplTabContentCountryInvested', d);
							}
						});
					})();
					break;
				case COUNTRY_TARGETS.DATA_PATH:
					url = URLS.URL_COUNTRY_FILE_DATA_PATH;
					successCB = function(d) {
						renderDataPath($pageTabContent, d);
					};
					break;
				case COUNTRY_TARGETS.BUSINESS:
					url = URLS.URL_COUNTRY_FILE_BUSINESS;
					successCB = function(d) {
						renderBusiness($pageTabContent, d, URLS.URL_COUNTRY_FILE_BUSINESS_REST, id);
					};
					break;
			}

			if (url) {
				load();
			}
		}

		function load() {
			ajax({
				url: url,
				data: {
					id: id
				},
				success: function(data) {
					var code = data.code,
						d = data.data;
					if (code == 0 && d) {
						successCB(d);
					}
				},
				error: function() {
					// load();
				}
			});
		}
	}

	function renderEliminatePath(id, ret, d, urlScores) {
		var $subTogglesContent = ret.$subTogglesContent,
			refreshPageJSP = ret.refreshPageJSP;

		$subTogglesContent.html(template('tplTabContentEliminatePath', d));

		var
			defaultTime = d.default_time,
			config = d.chart_config,

			$chart = $subTogglesContent.find('.chart'),

			$scoresSection = $subTogglesContent.find('.scores-section');

		// $subTogglesContent.on(CLICK_EV, function () {
		// 	$pageWrapper.find('.ico-quest')
		// 		.removeClass(CLASS_ACTIVE);
		// });

		var chart = echarts.init($chart[0]);
		chart.setOption(commonEchartConfig.genChartBarsConfig(config));
		chart.on(CLICK_EV, function(params) {
			var params_arr = params.name.split(".");
			var time = {};
			time.year = params_arr[0];
			time.month = params_arr[1];
			if (time) {
				changeScores(time);
			}
		});

		function changeScores(time) {
			$scoresSection
				.empty();

			refreshPageJSP();

			load();

			function load() {
				ajax({
					url: urlScores,
					data: {
						id: id,
						year: time.year,
						month: time.month
					},
					success: function(data) {
						var code = data.code,
							d = data.data;

						if (code == 0 && d) {
							$scoresSection.html(template('tplTabContentFamilyEliminateScores', extend({
								time: time
							}, d)));

							// var $ul = $scoresSection.find('ul'),
							// 	$icoQuests = $ul.find('.ico-quest');

							// $ul
							// 	.on(CLICK_EV, '.ico-quest', function (e) {
							// 		var
							// 			thiz = this;
							//
							// 		$icoQuests.each(function () {
							// 			if (thiz != this) {
							// 				$(this).removeClass(CLASS_ACTIVE);
							// 			}
							// 		});
							//
							// 		$(thiz).toggleClass(CLASS_ACTIVE);
							//
							// 		e.stopPropagation();
							// 	});

							refreshPageJSP();
						}
					},
					error: function() {
						// load();
					}
				});
			}
		}

		changeScores(defaultTime);
	}

	function renderNews(id, $pageTabContent, d, urlDetail) {
		$pageTabContent.html(template('tplTabContentNews', d));

		var $pageNews = $pageTabContent.find('.page-news'),
			$pageNewsDetail = $pageTabContent.find('.page-news-detail'),
			$pageNewsDetailContent = $pageNewsDetail.find('.content-wrapper');

		$pageNews.find('.content-wrapper')
			.jScrollPane(commonJSPConfig);

		$pageNews.on(CLICK_EV, '.item', function() {
			var $elem = $(this),
				id = $elem.attr('data-id');
			goPageNewsDetail(id);
		});
		$pageNewsDetail.on(CLICK_EV, '.btn-back', function() {
			goPageNews();
		});

		function goPageNews() {
			$pageNews.show();
			$pageNewsDetail.hide();
		}

		function goPageNewsDetail(newsID) {
			$pageNewsDetailContent.empty();

			$pageNews.hide();
			$pageNewsDetail
				.show();

			load();

			function load() {
				ajax({
					url: urlDetail,
					data: {
						family_id: id,
						news_id: newsID
					},
					success: function(data) {
						var code = data.code,
							d = data.data;
						if (code == 0 && d) {
							$pageNewsDetailContent.html(template('tplTabContentNewsDetail', d));

							var jsp = $pageNewsDetailContent.find('.content')
								.jScrollPane(commonJSPConfig)
								.data('jsp');

							$pageNewsDetailContent.find('img')
								.on(LOAD_EV, function() {
									jsp.reinitialise();
								});
						}
					},
					error: function() {
						// load();
					}
				});
			}
		}

	}

	function renderDataPath($pageTabContent, d) {
		$pageTabContent.html(template('tplTabContentDataPath', d));

		var $pageDataPath = $pageTabContent.find('.page-data-path');

		$pageDataPath.find('.content-wrapper')
			.jScrollPane(commonJSPConfig);
	}

	function renderInvested(ret, tplName, d) {
		var $subTogglesContent = ret.$subTogglesContent,
			refreshPageJSP = ret.refreshPageJSP;

		$subTogglesContent.html(template(tplName, d));

		var $tableSection = $subTogglesContent.find('.table-float-wrapper'),
			// $tableFixed = $subTogglesContent.find('.table.fixed'),
			tableJSP = $tableSection
			.jScrollPane(commonJSPConfig)
			// .on('jsp-user-scroll-y', function (e, dest) {
			// 	$tableFixed.css('top', -dest + 'px');
			// });
			.data('jsp');

		refreshPageJSP();
		tableJSP.reinitialise();
	}

	function renderBusiness($pageTabContent, d, URL_REST, id) {
		$pageTabContent.html(template('tplTabContentBusiness', d));

		var $pageDataPath = $pageTabContent.find('.page-business'),
			$content = $pageDataPath.find('.content-wrapper'),
			jsp = $content.jScrollPane(commonJSPConfig).data('jsp');

		$content.on(CLICK_EV, '.link-more', function() {
			var $linkMore = $(this),
				type = $linkMore.attr('data-type'),
				$ulRest = $linkMore.parent().find('ul.rest');

			if (!$linkMore.hasClass(CLASS_DISABLED)) {
				if (!$linkMore.hasClass(CLASS_OPENED)) {
					lock();
					load();
				} else {
					$ulRest.addClass(CLASS_HIDE);
					$linkMore.removeClass(CLASS_OPENED);
					jsp.reinitialise();
				}
			}

			function load() {
				ajax({
					url: URL_REST,
					data: {
						type: type,
						id: id
					},
					success: function(data) {
						var code = data.code,
							d = data.data;
						if (code == 0) {
							$ulRest
								.html(template('tplTabContentBusinessRest', d))
								.removeClass(CLASS_HIDE);

							$linkMore.addClass(CLASS_OPENED);

							jsp.reinitialise();
						}
						unlock();
					},
					error: function() {
						// load();
					}
				});
			}

			function unlock() {
				$linkMore.removeClass(CLASS_DISABLED);
			}

			function lock() {
				$linkMore.addClass(CLASS_DISABLED);
			}
		});
	}

	function renderSubTogglesYear($contentWrapper, d, loadPage) {
		$contentWrapper.html(template('tplSubTogglesYear', d));

		var $subTogglesYear = $contentWrapper.find('.sub-toggles-year'),
			$subTogglesContent = $contentWrapper.find('.sub-toggles-content'),
			pageJSP = $contentWrapper.jScrollPane(commonJSPConfig)
			.data('jsp');

		$subTogglesYear.on(CLICK_EV, 'li', function() {
			var $elem = $(this);
			if (!$elem.hasClass(CLASS_ACTIVE)) {
				if (loadPage) {
					loadPage($elem.attr('data-value'));
					$elem.addClass(CLASS_ACTIVE)
						.siblings('li')
						.removeClass(CLASS_ACTIVE);
				}
			}
		});

		function refreshPageJSP() {
			pageJSP.reinitialise();
		}

		return {
			$subTogglesYear: $subTogglesYear,
			$subTogglesContent: $subTogglesContent,
			pageJSP: pageJSP,
			refreshPageJSP: refreshPageJSP
		};
	}

	/**
	 * 统一用于显示和加载年份切换功能的封装函数，在部分Tab页面用到切换年份加载的逻辑
	 * 初始化时不传年，服务器返回default_time为默认的年月
	 * 点击切换年份后，服务器返回default_time的年份为传过去的年份，月份为默认月份
	 * 使用方式参考现有调用此函数的地方
	 * @param opts
	 */
	function loadSubTogglesYear(opts) {
		opts = extend({}, opts);

		var params = opts.params || {},
			successCB = opts.success,
			errorCB = opts.error,
			$pageTabContent = opts.$pageTabContent,
			$contentWrapper,
			ret;

		$contentWrapper = $pageTabContent.html(template('tplTabContentSubTogglesYearWrap'))
			.find('.toggles-content-wrapper');

		function loadPage(year) {
			if (year) {
				params.year = year;
			}

			ajax({
				url: opts.url,
				data: params,
				success: function(data) {
					var code = data.code,
						d = data.data;
					if (code == 0 && d) {
						if (successCB) {
							if (!ret) {
								ret = renderSubTogglesYear($contentWrapper, d, loadPage);
							}

							successCB(d, extend({
								year: year
							}, ret));
						}
					}
				},
				error: function() {
					if (errorCB) {
						errorCB();
					}
				}
			});
		}

		loadPage();
	}

	function initFamilyFileSubContent(id, target) {
		var
			$pageTabs = $popupFamilyFileContent.find('.page-tabs');

		$pageTabs.on(CLICK_EV, 'li', function() {
			var
				$elem = $(this),
				target = $elem.attr('data-target');

			switchFamilyFileSubContent(id, target);
		});

		switchFamilyFileSubContent(id, target);
	}

	function initCountryFileSubContent(id, target) {
		var
			$pageTabs = $popupCountryFileContent.find('.page-tabs');

		$pageTabs.on(CLICK_EV, 'li', function() {
			var
				$elem = $(this),
				target = $elem.attr('data-target');

			switchCountryFileSubContent(id, target);
		});

		switchCountryFileSubContent(id, target);
	}

	function hideCountryFile() {
		$popupCountryFile.hide();
	}

	function hideFamilyFile() {
		$popupFamilyFile.hide();
	}

	function hideCountryPhoto() {
		$("#exampleCountryPhotoPop").hide();
	}
	//文件号详情
	function showFileDetail(id,fileName) {
		var $popupFileDetailWrap = $("#popupFileDetailWrap"),
			$popupFileDetailTitle = $popupFileDetailWrap.find("#popupFileDetailTitle"),
			$fileDetaiMonitorTree = $("#fileDetaiMonitorTree");

		var fileId = id;
		//文件号基础信息
		showLoadingMask();
		ajax({
			url: URLS.URL_DATA_MONITOR_CAPITAL_FILE_BASIC_INFO,
			data: {
				id: fileId
			},
			success: function(data) {
				var code = data.code,
					d = data.data;
					d.file_id = fileId;
					d.file_name = fileName;

				if (code == 0 && d) {
					$popupFileDetailTitle
						.html(template("tplPopupFileDetailTitle", d));

					$popupFileDetailWrap.show();
					bindEvents();

					var treeOpts = {
						year: "2017",
						area_level: d.area_level,
						area_id: d.area_id,
						wintype: "capital",
						tabtype: "capital_file_detail",
						file_id:fileId
					}
					renderFamilyFileTree($fileDetaiMonitorTree, treeOpts)
				}
				hideLoadingMask();
			},
			error: function() {
				hideLoadingMask();
			}
		});

		//绑定事件
		function bindEvents() {
			//关闭
			$popupFileDetailWrap.on('click', '.btn-close', function(event) {
				$popupFileDetailWrap.hide();
			});
			//下拉
			$("body").on('click', hideScopeList);
			function hideScopeList(){
				var $selectBox=$(".select-box");
				$selectBox.removeClass('selected').find('.select-list').hide();
				$selectBox.find('.arrow').removeClass('arrow-on');
			}
			
			$popupFileDetailTitle.find(".js-select-box").on('click', toggleSelect)

			function toggleSelect() {
				var $this = $(this);
				var $selectList = $this.find('.select-list');
				var $sibSelectBox = $this.parents(".select-area").find('.js-select-box').not(this);

				$this.toggleClass('selected');
				$selectList.toggle();
				$this.find('.arrow').toggleClass('arrow-on');
				$sibSelectBox.removeClass('selected').find('.select-list').hide();
				$sibSelectBox.find('.arrow').removeClass('arrow-on');
				bindJsp();

					//下载
					$popupFileDetailTitle.find('.download-select li').on('click' , function() {
						var dowloadUrl = $(this).attr("data-download-url");

						Helpers.showAlertDialog({
							content: '确认下载',
							btnPosText: '下载',
							btnPosCB: function(modal) {
								window.open(dowloadUrl);
								modal.hide();
							}
						});
					})

				return false;

				function bindJsp() {
					var w = $selectList.innerWidth();
					var h = $selectList.innerHeight();

					w = w > 120 ? 120 : w;
					if ($selectList.find('.jspPane').length < 1) {
						$selectList.width(w);
					}

					if (h > 320) {
						$selectList.jScrollPane(commonJSPConfig);
					}
				}
			}

		
		}

		//渲染详细表
		function renderFileDetailTable(opts) {
			var fileId = Number($("#fileDetailFileName").attr("data-file-id")),
				$fileDetailTable = $("#fileDetailTable"),
				ajaxData = extend({
					id: fileId,
					page: 1
				}, opts);


			showLoadingMask();
			ajax({
				url: URLS.URL_DATA_MONITOR_CAPITAL_FILE_DETAIL_TABLE,
				data: ajaxData,
				success: function(data) {
					var code = data.code,
						d = data.data;

					if (code == 0 && d) {
						$fileDetailTable.html(template("tplConfigTable", d.table));

						bindScroll();

						var opts = {
							tableData: d.table,
							table_page: 1
						}
						$.components.tableEvent.bindPopupEvent($fileDetailTable);
						if (opts.tableData.pagination) {
							$.components.tableEvent.bindPagination($fileDetailTable, opts, ajaxTable);
						}
					}
					hideLoadingMask();
				},
				error: function() {
					hideLoadingMask();
				}
			});

			function ajaxTable(opts) {
				showLoadingMask();
				ajax({
					url: URLS.URL_DATA_MONITOR_CAPITAL_FILE_DETAIL_TABLE,
					data: extend(ajaxData, {
						page: opts.table_page
					}),
					success: function(data) {
						var code = data.code,
							d = data.data;

						if (code == 0 && d) {
							var _opts = {
								tableData: d.table,
								table_page: opts.table_page
							}
							$fileDetailTable.html(template("tplConfigTable", d.table));
							bindScroll()
							$.components.tableEvent.bindPopupEvent($fileDetailTable);
							if (opts.tableData.pagination) {
								$.components.tableEvent.bindPagination($fileDetailTable, _opts, ajaxTable);
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

		//滚动条
		function bindScroll(){
			var $popupFileDetailWrap=$("#popupFileDetailWrap"),
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

		//渲染树
		function renderFamilyFileTree($container, opts) {
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
				instance = opts.instance,
				file_id=opts.file_id;

			if (!jsp) {
				jsp = $treeWrapper.jScrollPane(commonJSPConfig)
					.data('jsp');
			} else {
				jsp.reinitialise();
			}

			function load() {
				showLoadingMask();

				ajax({
					url: URLS.URL_DATA_MONITOR_FILE_DETAIL_TREE,
					data: {
						year: year,
						level: level,
						id: id,
						wintype: wintype,
						tabtype: tabtype,
						file_id:file_id
					},
					success: function(data) {

						var code = data.code,
							d = data.data;

						if (code == 0 && d) {
							var tip = d.tip,
								matchNodeID = d.match_node_id,
								tree = d.tree,

								treeApi = $.fn.zTree.init($treeUl, {
									async: {
										enable: true,
										url: URLS.URL_DATA_MONITOR_FILE_DETAIL_TREE_SUBTREE,
										dataType: 'json',
										type: 'get',
										autoParam: ['area_id=id', 'area_level=level'],
										otherParam: {
											year: year,
											wintype: wintype,
											tabtype: tabtype,
											file_id:file_id
										},
										dataFilter: function(treeId, parentNode, responseData) {
											var code = responseData.code,
												d = responseData.data;
											if (code == 0 && d) {
												return d;
											} else {
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
										onClick: function(event, treeId, treeNode, clickFlag) {
											changeContent(treeNode);
										},
										// TODO 设计稿可能有缺陷，如果按子节点是特殊一级来做的话，层级不定的同级子节点会变得很难看
										// onNodeCreated: function(event, treeId, treeNode) {
										// 	if(treeNode.pId && !treeNode.isParent) {
										// 		// TODO 很恶心的做法，但这个插件好像没什么别的方法可以设叶class
										// 		$('#' + treeNode.tId).addClass('leaf-node');
										// 	}
										// },
										onExpand: function() {
											jsp.reinitialise();
										},
										onCollapse: function() {
											jsp.reinitialise();
										},
										onAsyncError: function(event, treeId, treeNode) {
											Helpers.showCommonErrorDialog(function() {
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
						} else {
							hideLoadingMask();
						}

						function changeContent(node) {
							if (node) {
								var areaID = node.area_id,
									areaLevel = node.area_level;

								var _opts = {
									area_level: areaLevel,
									area_id: areaID
								}

								renderFileDetailTable(_opts);
							}
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

	} //end 文件号详情

	//示范村相册
	function showCountryPhoto(id,type) {
		var $exampleCountryPhotoPopWrap = $("#exampleCountryPhotoPopWrap")
		var $exampleCountryPhotoPop = $("#exampleCountryPhotoPop")

		var $exampleCountryPhoto = $("#exampleCountryPhoto")

		var photoData=[];
		showLoadingMask();

		ajax({
			url: URLS.URL_DATA_EXAMPLE_COUNTRY_COUNTRY_PHOTO,
			data: {
				id: id
			},
			success: function(data) {
				var code = data.code,
					d = data.data;
					photoData=d.photo_list;

				if (code == 0 && d) {
					if(type==="pop"){
					$exampleCountryPhotoPop
						.html(template("tplExampleCountryPhotoPop", d));

						$exampleCountryPhotoPop.find(".photo-switch")
						.html(template("tplPhotoSwitch", {photo_list:photoData}));

						$exampleCountryPhotoPopWrap.show();
						bindEvents($exampleCountryPhotoPopWrap);

						$.components.photoSwitch($exampleCountryPhotoPop.find(".photo-switch"),4);
					}
					else{
						$exampleCountryPhoto
						.html(template("tplExampleCountryPhoto", d));

						$exampleCountryPhoto.find(".photo-switch")
						.html(template("tplPhotoSwitch", {photo_list:photoData}));

						$exampleCountryPhoto.show();
						bindEvents($exampleCountryPhoto);

						$.components.photoSwitch($exampleCountryPhoto.find(".photo-switch"),2);

					}
				}
				hideLoadingMask();
			},
			error: function() {
				hideLoadingMask();
			}
		});

		//显示大图
		function showBigImg(_src,_tips){
			var $popBigImgWrap=$("#popBigImgWrap");
			var $bigImgWrap=$("#popBigImgWrap").find(".big-img-wrap");
			var $popBigImg=$("#popBigImgWrap").find(".big-img");

			function imgCenter(img,callback) {
				var imgtemp  = new Image();
				imgtemp.src = img.src;
				imgtemp.onload = function(){
					var imgW=$(img).width();
					$bigImgWrap.css("width",imgW);
				}
            }	

			$popBigImg.attr("src",_src)
            imgCenter($("#popBigImgWrap").find(".big-img")[0]);
            $bigImgWrap.find(".tips").text(_tips)

			$popBigImgWrap.show()

			$popBigImgWrap.find(".btn-close").on('click', function(event) {
				$popBigImgWrap.hide()
			});
		}


		//绑定事件
		function bindEvents($ele) {
			//关闭
			$ele.on('click', '.btn-close', function(event) {
				$ele.hide();
			});
			//显示大图
			$ele.on('click', '.tab-list li', function(event) {
				var _src=$(this).attr("data-big-src");
				var _tips=$(this).find(".tips").text();
				showBigImg(_src,_tips);
			});

			//下拉
			$("body").on('click', hideScopeList);
			function hideScopeList(){
				var $selectBox=$(".js-select-box");
				$selectBox.removeClass('selected').find('.select-list').hide();
				$selectBox.find('.arrow').removeClass('arrow-on');
			}
			$ele.find(".js-select-box").on('click', toggleSelect)
			$ele.find(".js-select-box li").on('click', chooseSelect)

			function toggleSelect() {
				var $this = $(this);
				var $selectList = $this.find('.select-list');

				$selectList.toggle();
				$this.find('.arrow').toggleClass('arrow-on');
				bindJsp();

				return false;

				function bindJsp() {
					var w = $selectList.innerWidth();
					var h = $selectList.innerHeight();

					// w = w > 120 ? 120 : w;
					// if ($selectList.find('.jspPane').length < 1) {
					// 	$selectList.width(w);
					// }

					if (h > 320) {
						console.log($selectList.length);
						$selectList.jScrollPane(commonJSPConfig);
					}
				}
			}

			function chooseSelect(){
				var $selectBoxTitle=$(this).parents(".js-select-box").find(">span");
				var _countryId=$(this).attr("data-id");
				var _str=$(this).text();

				$(this).siblings().removeClass('active');
				$(this).addClass('active');
				$selectBoxTitle.text(_str);

				var $exampleCountryPhoto = $(this).parents(".example-country-photo");
				var $photoSwitch=$exampleCountryPhoto.find(".photo-switch");
				var $photoSwitchList=$photoSwitch.find(".tab-list");
				var	_thisPhotoData=[]

				if(_countryId==""||_countryId==null){
					_thisPhotoData=photoData;
				}
				else{
					for(var i=0;i<photoData.length;i++){
						var item=photoData[i];
						if(item.belong_country_id==_countryId){
							_thisPhotoData.push(item)
						}
					}
				}


				$photoSwitch
						.html(template("tplPhotoSwitch", {photo_list:_thisPhotoData}));

				if(!$exampleCountryPhoto.hasClass('fixed')){
					$.components.photoSwitch($photoSwitch,4);
				}else{
					$.components.photoSwitch($photoSwitch,2);
				}
			}

		
		}
	}

	$.poorFile = {
		FAMILY_TARGETS: FAMILY_TARGETS,
		COUNTRY_TARGETS: COUNTRY_TARGETS,
		showCountryFile: showCountryFile,
		showFamilyFile: showFamilyFile,
		showFileDetail: showFileDetail,
		showCountryPhoto: showCountryPhoto,
		hideCountryPhoto: hideCountryPhoto,
		hideFamilyFile: hideFamilyFile
	};

	//TODO debug
	//showCountryFile(1, COUNTRY_TARGETS.DATA_PATH);
	//showFamilyFile(1, COUNTRY_TARGETS.IMPLEMENT);
});