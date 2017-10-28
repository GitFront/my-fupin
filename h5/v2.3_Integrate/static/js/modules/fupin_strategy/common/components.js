$.components = {
	//选项取值常量
	CONST_RADIO_VALUES: {
		//贫困属性
		POOR_ATTRIBUTE: {
			//全部
			ALL: {
				name: '全部',
				value: 'all'
			},
			//低保户
			LOW: {
				name: '低保户',
				value: 'low'
			},
			//五保户
			FIVE: {
				name: '五保户',
				value: 'five'
			},
			//一般贫困的无劳动能力户
			NO_LABOR: {
				name: '无劳动能力的一般贫困户',
				value: 'no_labor'
			},
			//一般贫困户
			NORMAL: {
				name: '一般贫困户',
				value: 'normal'
			}
		},
		//教育阶段
		EDU_LEVELS: {
			//全部
			ALL: {
				name: '全部',
				value: 'all'
			},
			//义务教育
			COMPULSORY: {
				name: '义务教育',
				value: 'compulsory'
			},
			//高中（中职）教育
			HIGH_SCHOOL: {
				name: '高中（中职）教育',
				value: 'high_school'
			}
		},
		//劳力属性
		LABOR_ATTRIBUTE: {
			//全部
			ALL: {
				name: "全部",
				value: "all"
			},
			//有劳动能力
			HAVE_LABOR: {
				name: "有劳动能力",
				value: "have_labor"
			},
			//无劳动能力
			NO_LABOR: {
				name: "无劳动能力",
				value: "no_labor"
			}
		},
		//项目属性
		PROJECT_ATTRIBUTE: {
			//全部
			ALL: {
				name: '全部',
				value: 'all'
			},
			//到村项目
			COUNTRY: {
				name: '到村项目',
				value: 'country'
			},
			//到户项目
			FAMILY: {
				name: '到户项目',
				value: 'family'
			}
		}
	},
	CONST_CHECK_VALUES: {
		//致贫原因
		POOR_REASON: {
			//因病
			DISEASE: {
				name: '因病',
				value: 'disease'
			},
			//因残
			DISABLED: {
				name: '因残',
				value: 'disabled'
			},
			//因学
			EDU: {
				name: '因学',
				value: 'edu'
			},
			//因灾
			DISASTER: {
				name: '因灾',
				value: 'disaster'
			},
			//缺土地
			LAND: {
				name: '缺土地',
				value: 'land'
			},
			//缺水
			WATER: {
				name: '缺水',
				value: 'water'
			},
			//缺技术
			SKILL: {
				name: '缺技术',
				value: 'skill'
			},
			//缺劳力
			LABOR: {
				name: '缺劳力',
				value: 'labor'
			},
			//缺资金
			MONEY: {
				name: '缺资金',
				value: 'money'
			},
			//交通落后
			TRAFFIC: {
				name: '交通落后',
				value: 'traffic'
			},
			//自身发展
			SELF_DEV: {
				name: '自身发展',
				value: 'self_dev'
			},
			//其他
			OTHER: {
				name: '其他',
				value: 'other'
			}
		},
		//异常类型
		EXCEPTION_TYPE: {
			//身份证不匹配
			ID_CARD: {
				name: '身份证不匹配',
				value: 'id_card'
			},
			//低保五保不匹配
			LOW_FIVE: {
				name: '低保五保不匹配',
				value: 'low_five'
			},
			//残疾信息不匹配
			DISABLED_INFO: {
				name: '残疾信息不匹配',
				value: 'disabled_info'
			},
			//名下有房
			HOUSE: {
				name: '名下有房',
				value: 'house'
			},
			//名下有车
			CAR: {
				name: '名下有车',
				value: 'car'
			},
			//有工商注册信息
			I_N_C_INFO: {
				name: '有工商注册信息',
				value: 'i_n_c_info'
			},
			//享有财政供养
			FINANCE: {
				name: '享有财政供养',
				value: 'finance'
			},
			//户主身份信息错漏
			HOLDER_ID_MISS: {
				name: '户主身份信息错漏',
				value: 'holder_id_miss'
			},
			//成员身份信息错漏
			MEMBER_ID_MISS: {
				name: '成员身份信息错漏',
				value: 'member_id_miss'
			},
			//残疾信息错漏
			DISABLED_INFO_MISS: {
				name: '残疾信息错漏',
				value: 'disabled_info_miss'
			},
			//重复身份信息记录
			ID_REPEAT: {
				name: '重复身份信息记录',
				value: 'id_repeat'
			}
		},
		//项目类型-村
		PROJECT_TYPE_COUNTRY: {
			//村道硬底化
			COUNTRY_ROAD: {
				name: '村道硬底化',
				value: 'country_road'
			},
			//饮水工程
			COUNTRY_WATER: {
				name: '饮水工程',
				value: 'country_water'
			},
			//文体设施
			COUNTRY_RECREATION_SPORT: {
				name: '文体设施',
				value: 'country_recreation_sport'
			},
			//卫生设施
			COUNTRY_HYGIENE: {
				name: '卫生设施',
				value: 'country_hygiene'
			},
			//路灯安装
			COUNTRY_LAMP: {
				name: '路灯安装',
				value: 'country_lamp'
			},
			//农田水利
			COUNTRY_FARM: {
				name: '农田水利',
				value: 'country_farm'
			},
			//公共设施
			COUNTRY_PUBLIC_FACILITY: {
				name: '公共设施',
				value: 'country_public_facility'
			},
			//集体经济
			COUNTRY_COLLECTIVE_ECONOMY: {
				name: '集体经济',
				value: 'country_collective_economy'
			},
			//教育教学
			COUNTRY_EDU: {
				name: '教育教学',
				value: 'country_edu'
			},
			//其他
			COUNTRY_OTHER: {
				name: '其他',
				value: 'country_other'
			}
		},
		PROJECT_TYPE_FAMILY: {
			//产业扶贫
			FAMILY_INDUSTRY: {
				name: '产业扶贫',
				value: 'family_industry'
			},
			//金融扶贫
			FAMILY_FINANCE: {
				name: '金融扶贫',
				value: 'family_finance'
			},
			//住房改造
			FAMILY_HOUSE: {
				name: '住房改造',
				value: 'family_house'
			},
			//资产扶贫
			FAMILY_PROPERTY: {
				name: '资产扶贫',
				value: 'family_property'
			},
			//慰问扶贫
			FAMILY_VISIT: {
				name: '慰问扶贫',
				value: 'family_visit'
			},
			//就业扶贫
			FAMILY_EMPLOYMENT: {
				name: '就业扶贫',
				value: 'family_employment'
			},
			//技能培训
			FAMILY_SKILL: {
				name: '技能培训',
				value: 'family_skill'
			},
			//教育扶贫
			FAMILY_EDU: {
				name: '教育扶贫',
				value: 'family_edu'
			},
			//政策补贴和社会保障
			FAMILY_POLICY: {
				name: '政策补贴和社会保障',
				value: 'family_policy'
			}
		}
	},
	//选择行key
	ROW_KEY: {
		//区域类型
		SCOPE: "scope",
		//贫困属性
		POOR_ATTRIBUTE: "poor_attribute",
		//劳力属性
		LABOR_ATTRIBUTE: "labor_attribute",
		//状态属性
		STATUS_ATTRIBUTE: "status_attribute",
		//综合得分
		SCORE: "score",
		//两不愁
		INCOME: "income",
		//三保障
		GUARANTEE: "guarantee",
		//教育阶段
		EDU_LEVELS: 'edu_levels',
		//致贫原因
		POOR_REASON: "poor_reason",
		//异常类型
		EXCEPTION_TYPE: "exception_type",
		//项目属性
		PROJECT_ATTRIBUTE: 'project_attribute',
		//到村项目
		PROJECT_TYPE_COUNTRY: 'project_type_country',
		//到户项目
		PROJECT_TYPE_FAMILY: 'project_type_family'
	},
	/**
	 * [生成单选行]
	 * @Author   luoJiangFeng
	 * @DateTime 2017-03-15
	 * @param    {[type]}     row_key [选项行的key]
	 * @return   {[type]}     activePosition [默认选中位置,,多选时传数组，不传默认为1]
	 * 
	 * 行展示标题 与 row_key对应关系（同api说明）：
	 * 区域类型 — scope
	 * 贫困属性 — poor_attribute
	 * 劳力属性 — labor_attribute
	 * 状态属性 — status_attribute
	 * 综合得分 — score
	 * 两不愁	— income
	 * 三保障 	— guarantee
	 * 教育阶段 	— edu_levels
	 */
	buildCheckRow: function(row_key, activePosition) {
		var custom = false,

			ROW_KEY = $.components.ROW_KEY,
			CONST_RADIO_VALUES = $.components.CONST_RADIO_VALUES,
			CONST_CHECK_VALUES = $.components.CONST_CHECK_VALUES;

		if (typeof row_key == "object") {
			var customRowArr = row_key.checkArr;
			custom = true;
			row_key = row_key.rowKey;
		}

		switch (row_key) {
			case ROW_KEY.SCOPE:
				var checkObj = {
					title: "区域类型",
					rowType: "radio",
					rowKey: row_key,
					checkArr: custom && customRowArr ? customRowArr : [{
						name: "全部",
						value: "1"
					}, {
						name: "相对贫困村",
						value: "2"
					}, {
						name: "分散村",
						value: "3"
					}, {
						name: "革命老区",
						value: "4"
					}, {
						name: "中央苏区",
						value: "5"
					}]
				};
				return buildRadioHtml(checkObj, activePosition);
				break;

			case ROW_KEY.POOR_ATTRIBUTE:
				var checkObj = {
					title: "贫困属性",
					rowType: "radio",
					rowKey: row_key,
					checkArr: custom && customRowArr ? customRowArr : [{
						name: "全部",
						value: "all"
					}, {
						name: "一般贫困户",
						value: "normal"
					}, {
						name: "低保户",
						value: "low"
					}, {
						name: "五保户",
						value: "five"
					}]
				};
				return buildRadioHtml(checkObj, activePosition);
				break;

			case ROW_KEY.LABOR_ATTRIBUTE:
				var LABOR_ATTRIBUTE = CONST_RADIO_VALUES.LABOR_ATTRIBUTE;
				var checkObj = {
					title: "劳力属性",
					rowType: "radio",
					rowKey: row_key,
					checkArr: custom && customRowArr ? customRowArr : [
						LABOR_ATTRIBUTE.ALL,
						LABOR_ATTRIBUTE.HAVE_LABOR,
						LABOR_ATTRIBUTE.NO_LABOR
					]
				};
				return buildRadioHtml(checkObj, activePosition);
				break;

			case ROW_KEY.STATUS_ATTRIBUTE:
				var checkObj = {
					title: "状态属性",
					rowType: "radio",
					rowKey: row_key,
					checkArr: custom && customRowArr ? customRowArr : [{
						name: "累计贫困户",
						value: "all",
						url: "URL_DATA_MONITOR_POOR_CHANGE_ANALYSIS_ALL"
					}, {
						name: "当前贫困户",
						value: "poor_cur",
						url: "URL_DATA_MONITOR_POOR_CHANGE_ANALYSIS_CUR"
					}, {
						name: "新增贫困户",
						value: "poor_add",
						url: "URL_DATA_MONITOR_POOR_CHANGE_ANALYSIS_ADD"
					}, {
						name: "终止贫困户",
						value: "poor_end",
						url: "URL_DATA_MONITOR_POOR_CHANGE_ANALYSIS_END"
					}, {
						name: "自然增减户",
						value: "natural_change",
						url: "URL_DATA_MONITOR_POOR_CHANGE_ANALYSIS_NATURAL_CHANGE"
					}]
				};
				return buildRadioHtml(checkObj, activePosition);
				break;

			case ROW_KEY.SCORE:
				var checkObj = {
					title: "综合得分",
					rowType: "radio",
					rowKey: row_key,
					checkArr: custom && customRowArr ? customRowArr : [{
						name: "全部",
						value: "all"
					}, {
						name: "综合得分<60分",
						value: "domain_1"
					}, {
						name: "60分≤综合得分<100分",
						value: "domain_2"
					}, {
						name: "综合得分=100",
						value: "domain_3"
					}]
				};
				return buildRadioHtml(checkObj, activePosition);
				break;

			case ROW_KEY.INCOME:
				var checkObj = {
					title: "两不愁",
					rowType: "radio",
					rowKey: row_key,
					checkArr: custom && customRowArr ? customRowArr : [{
						name: "全部",
						value: "all"
					}, {
						name: "可支配收入<4000元",
						value: "domain_1"
					}, {
						name: "4000元≤可支配收入<7365元",
						value: "domain_2"
					}, {
						name: "可支配收入≥7365元",
						value: "domain_3"
					}, {
						name: "未落实低保/无保政策",
						value: "no_security"
					}]
				};
				return buildRadioHtml(checkObj, activePosition);
				break;

			case ROW_KEY.GUARANTEE:
				var checkObj = {
					title: "三保障",
					rowType: "radio",
					rowKey: row_key,
					checkArr: custom && customRowArr ? customRowArr : [{
						name: "全部",
						value: "all"
					}, {
						name: "未落实教育政策",
						value: "edu"
					}, {
						name: "未落实医保政策",
						value: "medical_security"
					}, {
						name: "住房未达标",
						value: "house"
					}]
				};
				return buildRadioHtml(checkObj, activePosition);
				break;

			case ROW_KEY.EDU_LEVELS:
				var EDU_LEVELS_VALUES = CONST_RADIO_VALUES.EDU_LEVELS;
				var checkObj = {
					title: "教育阶段",
					rowType: "radio",
					rowKey: row_key,
					checkArr: custom && customRowArr ? customRowArr : [
						EDU_LEVELS_VALUES.ALL,
						EDU_LEVELS_VALUES.COMPULSORY,
						EDU_LEVELS_VALUES.HIGH_SCHOOL
					]
				};
				return buildRadioHtml(checkObj, activePosition);
				break;

				/*多选*/
			case ROW_KEY.POOR_REASON:
				var POOR_REASON_VALUES = CONST_CHECK_VALUES.POOR_REASON;
				var checkObj = {
					title: "致贫原因",
					rowType: "check",
					rowKey: row_key,
					checkArr: custom && customRowArr ? customRowArr : [
						POOR_REASON_VALUES.DISEASE,
						POOR_REASON_VALUES.DISABLED,
						POOR_REASON_VALUES.EDU,
						POOR_REASON_VALUES.DISASTER,
						POOR_REASON_VALUES.LAND,
						POOR_REASON_VALUES.WATER,
						POOR_REASON_VALUES.SKILL,
						POOR_REASON_VALUES.LABOR,
						POOR_REASON_VALUES.MONEY,
						POOR_REASON_VALUES.TRAFFIC,
						POOR_REASON_VALUES.SELF_DEV,
						POOR_REASON_VALUES.OTHER
					]
				};
				return buildCheckHtml(checkObj, activePosition);
				break;

			case ROW_KEY.EXCEPTION_TYPE:
				var EXCEPTION_TYPE_VALUES = CONST_CHECK_VALUES.EXCEPTION_TYPE;
				var checkObj = {
					title: "异常类型",
					rowType: "check",
					rowKey: row_key,
					checkArr: custom && customRowArr ? customRowArr : [
						EXCEPTION_TYPE_VALUES.ID_CARD,
						EXCEPTION_TYPE_VALUES.LOW_FIVE,
						EXCEPTION_TYPE_VALUES.DISABLED_INFO,
						EXCEPTION_TYPE_VALUES.HOUSE,
						EXCEPTION_TYPE_VALUES.CAR,
						EXCEPTION_TYPE_VALUES.I_N_C_INFO,
						EXCEPTION_TYPE_VALUES.FINANCE
					]
				};
				return buildCheckHtml(checkObj, activePosition);
				break;

			case ROW_KEY.PROJECT_ATTRIBUTE:
				var PROJECT_ATTRIBUTE_VALUES = CONST_RADIO_VALUES.PROJECT_ATTRIBUTE;
				var checkObj = {
					title: "项目属性",
					rowType: "radio",
					rowKey: row_key,
					checkArr: custom && customRowArr ? customRowArr : [
						PROJECT_ATTRIBUTE_VALUES.ALL,
						PROJECT_ATTRIBUTE_VALUES.COUNTRY,
						PROJECT_ATTRIBUTE_VALUES.FAMILY
					]
				};
				return buildRadioHtml(checkObj, activePosition);
				break;

			case ROW_KEY.PROJECT_TYPE_FAMILY:
				var PROJECT_ATTRIBUTE_VALUES = CONST_CHECK_VALUES.PROJECT_TYPE_FAMILY;
				var checkObj = {
					title: '到户项目',
					rowType: 'check',
					rowKey: row_key,
					hasCheckAll: true,
					checkArr: custom && customRowArr ? customRowArr : [
						PROJECT_ATTRIBUTE_VALUES.FAMILY_INDUSTRY,
						PROJECT_ATTRIBUTE_VALUES.FAMILY_FINANCE,
						PROJECT_ATTRIBUTE_VALUES.FAMILY_HOUSE,
						PROJECT_ATTRIBUTE_VALUES.FAMILY_PROPERTY,
						PROJECT_ATTRIBUTE_VALUES.FAMILY_VISIT,
						PROJECT_ATTRIBUTE_VALUES.FAMILY_EMPLOYMENT,
						PROJECT_ATTRIBUTE_VALUES.FAMILY_SKILL,
						PROJECT_ATTRIBUTE_VALUES.FAMILY_EDU,
						PROJECT_ATTRIBUTE_VALUES.FAMILY_POLICY
					]
				};
				return buildCheckHtml(checkObj, activePosition);
				break;

			case ROW_KEY.PROJECT_TYPE_COUNTRY:
				var PROJECT_ATTRIBUTE_VALUES = CONST_CHECK_VALUES.PROJECT_TYPE_COUNTRY;
				var checkObj = {
					title: '到村项目',
					rowType: 'check',
					rowKey: row_key,
					hasCheckAll: true,
					checkArr: custom && customRowArr ? customRowArr : [
						PROJECT_ATTRIBUTE_VALUES.COUNTRY_ROAD,
						PROJECT_ATTRIBUTE_VALUES.COUNTRY_WATER,
						PROJECT_ATTRIBUTE_VALUES.COUNTRY_RECREATION_SPORT,
						PROJECT_ATTRIBUTE_VALUES.COUNTRY_HYGIENE,
						PROJECT_ATTRIBUTE_VALUES.COUNTRY_LAMP,
						PROJECT_ATTRIBUTE_VALUES.COUNTRY_FARM,
						PROJECT_ATTRIBUTE_VALUES.COUNTRY_PUBLIC_FACILITY,
						PROJECT_ATTRIBUTE_VALUES.COUNTRY_COLLECTIVE_ECONOMY,
						PROJECT_ATTRIBUTE_VALUES.COUNTRY_EDU,
						PROJECT_ATTRIBUTE_VALUES.COUNTRY_OTHER
					]
				};
				return buildCheckHtml(checkObj, activePosition);
				break;

		} //end switch

		/**
		 * [生成单选html代码]
		 * @Author   luoJiangFeng
		 * @DateTime 2017-03-15
		 * @param    {[type]}     checkArr [选项字符串数组]
		 * @param   {[type]}      activePosition [默认选中位置value,默认选中首个]
		 */
		// TODO 略晦涩，也跟模板割开了，待重构
		function buildRadioHtml(checkObj, activePosition) {
			var activePosition = activePosition || 1;
			var _html = "<tr class='radio-row clearfix' data-key=" + checkObj.rowKey + "><td class='check-area-label'><strong>" + checkObj.title + "：</strong></td><td><p>";

			//默认选中首个
			if (activePosition == 1) {
				if (typeof checkObj.checkArr[0].url != "undefined") {
					_html = _html + '<a class="radio active" data-url=' + checkObj.checkArr[0].url + ' data-value=' + checkObj.checkArr[0].value + '>' + checkObj.checkArr[0].name + '</a>';
				} else {
					_html = _html + '<a class="radio active" data-value=' + checkObj.checkArr[0].value + '>' + checkObj.checkArr[0].name + '</a>';
				}

				for (var i = 1; i < checkObj.checkArr.length; i++) {
					if (typeof checkObj.checkArr[i].url != "undefined") {
						_html = _html + '<a class="radio " data-url=' + checkObj.checkArr[i].url + ' data-value=' + checkObj.checkArr[i].value + '>' + checkObj.checkArr[i].name + '</a>';
					} else {
						_html = _html + '<a class="radio " data-value=' + checkObj.checkArr[i].value + '>' + checkObj.checkArr[i].name + '</a>';
					}
				}
			} else {
				for (var i = 0; i < checkObj.checkArr.length; i++) {
					if (checkObj.checkArr[i].value == activePosition) {
						if (typeof checkObj.checkArr[i].url != "undefined") {
							_html = _html + '<a class="radio active" data-url=' + checkObj.checkArr[i].url + ' data-value=' + checkObj.checkArr[i].value + '>' + checkObj.checkArr[i].name + '</a>';
						} else {
							_html = _html + '<a class="radio active" data-value=' + checkObj.checkArr[i].value + '>' + checkObj.checkArr[i].name + '</a>';
						}
					} else {
						if (typeof checkObj.checkArr[i].url != "undefined") {
							_html = _html + '<a class="radio " data-url=' + checkObj.checkArr[i].url + ' data-value=' + checkObj.checkArr[i].value + '>' + checkObj.checkArr[i].name + '</a>';
						} else {
							_html = _html + '<a class="radio " data-value=' + checkObj.checkArr[i].value + '>' + checkObj.checkArr[i].name + '</a>';
						}
					}
				}
			}

			return _html + "</td></p></tr>";
		}

		/**
		 * [生成多选html代码]
		 * @Author   luoJiangFeng
		 * @DateTime 2017-03-15
		 * @param    {[type]}     checkArr [选项字符串数组]
		 * @param    {[type]}     activePosition [默认选中位置value,多选时传数组，不传默认为全选]
		 */
		// TODO 略晦涩，也跟模板割开了，待重构
		function buildCheckHtml(checkObj, activePosition) {
			var activePosition = activePosition || "defaultCheck";
			var _html = "<tr class='check-row clearfix' data-key=" + checkObj.rowKey + "><td class='check-area-label'><strong>" + checkObj.title + "：</strong></td><td><p>";

			if (checkObj.hasCheckAll) {
				// 不要传错activePosition的值数，否则全选会不对
				_html = _html + '<a class="check ' + (activePosition == "defaultCheck" || Object.keys(activePosition).length === checkObj.checkArr.length ? 'active ' : ' ') + '" data-check-all>全选</a>';
			}

			//默认全选
			if (activePosition == "defaultCheck") {
				for (var i = 0; i < checkObj.checkArr.length; i++) {
					_html = _html + '<a class="check active" data-value=' + checkObj.checkArr[i].value + '>' + checkObj.checkArr[i].name + '</a>';
				}
			} else {
				for (var i = 0; i < checkObj.checkArr.length; i++) {
					var tag = 0;
					for (var n = 0; n < activePosition.length; n++) {
						if (activePosition[n] == checkObj.checkArr[i].value) {
							_html = _html + '<a class="check active" data-value=' + checkObj.checkArr[i].value + '>' + checkObj.checkArr[i].name + '</a>';
							tag = 1;
							break;
						}
					}
					if (tag == 0) {
						_html = _html + '<a class="check " data-value=' + checkObj.checkArr[i].value + '>' + checkObj.checkArr[i].name + '</a>';
					}
				}
			}

			return _html + "</td></p></tr>";
		}
	},
	/**
	 * [左右切换tab]
	 * @Author   luoJiangFeng
	 * @DateTime 2017-03-13
	 * @param    {[type]}     $ele       [要绑定tab的jquery对象]
	 * @param    {[type]}     tabArr     [tab对象数组：含value属性、name属性]
	 * @param    {[type]}     defaultTab [默认显示的tab(对应value值)]
	 * @return   {[type]}                [description]
	 */
	tabSwitch: function($ele, tabArr, defaultTab) {
		var PADDING = 14,
			tabLen = tabArr.length,
			tabLiHtml = "",
			maxLiW = 0,
			count = 0;
		var tabIsAnimate = false, //动画是否在运行中
			ANIMATESPEED = 150; //动画速度

		for (var i = 0; i < tabLen; i++) {
			tabLiHtml = tabLiHtml + '<li data-value=' + tabArr[i].value + '>' + tabArr[i].name + '</li>';
		}
		var _html = '<i class="icon icon-arrow-left"></i>\
		 <div class="overflow-wrap">\
         <ul class="tab-list">' + tabLiHtml + '</ul>\
         </div>\
         <i class="icon icon-arrow-right"></i>';
		$ele.html(_html)

		//统一宽度
		for (var i = 0; i < tabLen; i++) {
			_thisW = $($ele.find("li")[i]).width()
			if (_thisW > maxLiW) {
				maxLiW = _thisW;
			}
		}
		$ele.find("li").width(maxLiW);

		//
		var $tabList = $ele.find(".tab-list"),
			$arrowLeft = $ele.find(".icon-arrow-left"),
			$arrowRight = $ele.find(".icon-arrow-right"),
			eachW = maxLiW + PADDING * 2,
			listW = eachW * tabLen,
			maxRight = eachW - listW;

		$ele.css("width", eachW)
			//选中默认值
		for (var i = 0; i < tabLen; i++) {
			if (defaultTab == tabArr[i].value) {
				count = i;
			}
		}
		$tabList.find('li').eq(count).addClass('active')
		$tabList.css("width", listW).css("left", -eachW * count + "px");

		if ($tabList.position().left == 0) {
			$arrowLeft.addClass('forbid');
		}
		if ($tabList.position().left == maxRight) {
			$arrowRight.addClass('forbid');
		}

		$ele
			.on('click', '.icon-arrow-right', function(event) {
				var posNow = $tabList.position().left;

				arrowRemoveClass();
				if (posNow <= maxRight + eachW) {
					$arrowRight.addClass('forbid');
				}
				if (posNow == maxRight) {
					$arrowRight.addClass('js-forbid');
					return 0;
				} else {
					if (tabIsAnimate == false) {
						$tabList.stop().animate({
							"left": posNow - eachW + "px"
						}, ANIMATESPEED, function() {
							tabIsAnimate = false;
						});
						$tabList.find('li').removeClass('active');
						$tabList.find('li').eq(count + 1).addClass('active');
						count++;
						tabIsAnimate = true;
					} else {
						return 0;
					}
				}
			})
			.on('click', '.icon-arrow-left', function(event) {
				var posNow = $tabList.position().left;

				arrowRemoveClass();
				if (posNow >= -eachW) {
					$arrowLeft.addClass('forbid');
				}
				if (posNow == 0) {
					$arrowLeft.addClass('js-forbid');
					return 0;
				} else {
					if (tabIsAnimate == false) {
						$tabList.stop().animate({
							"left": posNow + eachW + "px"
						}, ANIMATESPEED, function() {
							tabIsAnimate = false;
						});
						$tabList.find('li').removeClass('active');
						$tabList.find('li').eq(count - 1).addClass('active');
						count--;
						tabIsAnimate = true;
					} else {
						return 0;
					}
				}
			});

		function arrowRemoveClass(argument) {
			$arrowRight.removeClass('forbid');
			$arrowRight.removeClass('js-forbid');
			$arrowLeft.removeClass('forbid');
			$arrowLeft.removeClass('js-forbid');
		}
	},
	/**
	 * [示范村相册的switch]
	 * @param    {[type]}     $ele       	  [要绑定tab的jquery对象]
	 * @param    {[num]}     eachPageImg      [每页显示的图片数量]
	 */
	photoSwitch: function($ele, eachPageImg) {
		var $tabList = $ele.find(".tab-list"),
			tabLen = $tabList.find("li").length,
			$arrowLeft = $ele.find(".icon-arrow-left"),
			$arrowRight = $ele.find(".icon-arrow-right");

		var PADDING = 18,
			imgW = 234,
			count = 0,
			eachW = imgW + PADDING,
			listW = eachW * tabLen-PADDING,
			maxRight = eachPageImg*eachW - listW;

		var tabIsAnimate = false, //动画是否在运行中
			ANIMATESPEED = 150; //动画速度


		//
		

		$tabList.css("width", listW)


		if ($tabList.position().left == 0) {
			$arrowLeft.addClass('forbid');
		}
		if ($tabList.position().left == maxRight) {
			$arrowRight.addClass('forbid');
		}

		if(tabLen<=eachPageImg){
			$arrowRight.addClass('forbid');
			$arrowRight.addClass('js-forbid');
			$arrowLeft.addClass('forbid');
			$arrowLeft.addClass('js-forbid');
		}

		$ele
			.on('click', '.icon-arrow-right', function(event) {
				var posNow = $tabList.position().left;
				if($(this).hasClass('forbid')){
					return 0;
				}

				arrowRemoveClass();
				if (posNow <= maxRight + eachPageImg*eachW) {
					$arrowRight.addClass('forbid');
				}
				if (posNow == maxRight) {
					$arrowRight.addClass('js-forbid');
					return 0;
				} else {
					if (tabIsAnimate == false) {
						$tabList.stop().animate({
							"left": posNow - eachPageImg*eachW + "px"
						}, ANIMATESPEED, function() {
							tabIsAnimate = false;
						});
						$tabList.find('li').removeClass('active');
						$tabList.find('li').eq(count + 1).addClass('active');
						count++;
						tabIsAnimate = true;
					} else {
						return 0;
					}
				}
			})
			.on('click', '.icon-arrow-left', function(event) {
				var posNow = $tabList.position().left;
				if($(this).hasClass('forbid')){
					return 0;
				}
				
				arrowRemoveClass();
				if (posNow >= -eachPageImg*eachW) {
					$arrowLeft.addClass('forbid');
				}
				if (posNow == 0) {
					$arrowLeft.addClass('js-forbid');
					return 0;
				} else {
					if (tabIsAnimate == false) {
						$tabList.stop().animate({
							"left": posNow + eachPageImg*eachW + "px"
						}, ANIMATESPEED, function() {
							tabIsAnimate = false;
						});
						$tabList.find('li').removeClass('active');
						$tabList.find('li').eq(count - 1).addClass('active');
						count--;
						tabIsAnimate = true;
					} else {
						return 0;
					}
				}
			});

		function arrowRemoveClass(argument) {
			$arrowRight.removeClass('forbid');
			$arrowRight.removeClass('js-forbid');
			$arrowLeft.removeClass('forbid');
			$arrowLeft.removeClass('js-forbid');
		}
	},
	/**
	 * [下划线命名 to 驼峰命名]
	 * @Author   luoJiangFeng
	 * @DateTime 2017-03-17
	 * @param    {[string]}     str [字符串]
	 * @param    {[boolean]}    firstUpper [首字母是否大写：默认首字母大写]
	 * @return   {[string]}     [description]
	 */
	underlineToCamel: function(str, firstUpper) {
		if (typeof firstUpper == "undefined") {
			var _firstUpper = true;
		} else if (firstUpper == false) {
			var _firstUpper = false;
		}

		var arr = str.split('_');
		var arr2 = [];
		for (var i = 0, len = arr.length; i < len; i++) {
			arr2.push(arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1));
		}

		if (_firstUpper == true) {
			return arr2.join('');
		} else {
			return arr[0] + arr2.join('').substring(arr[0].length);
		}
	},
	/**
	 * 绑定表格事件-页脚、弹窗、导出等
	 */
	tableEvent: {
		//页脚
		bindPagination: function($thisChartTable, opts, callback) {
			var self = $.dataMonitor;
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
					$.isFunction(callback) && callback(opts)
				})
				.on('click', '.arrow-end', function(event) {
					if (pageCur == pageTotal) {
						return 0;
					}
					opts.table_page = pageTotal;
					$.isFunction(callback) && callback(opts)
				})
				.on('click', '.arrow-left', function(event) {
					if (pagePre < 1) {
						return 0;
					} else {
						opts.table_page = pagePre;
					}
					$.isFunction(callback) && callback(opts)
				})
				.on('click', '.arrow-right', function(event) {
					if (pageNext > pageTotal) {
						return 0;
					} else {
						opts.table_page = pageNext;
					}
					$.isFunction(callback) && callback(opts)
				})
		},
		bindPopupEvent: function($thisChartTable) {
			var self = $.components.tableEvent;
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
					.on('click', 'td[data-action-type=file_detail]', function(event) {
						var _id = $(this).data("action-file-id");
						var _fileName = $(this).parents("tr").find("td").eq(0).text();
						console.log(_fileName);
						$.poorFile.showFileDetail(_id,_fileName);
					})
					.on('click', '.detail-btn', function(event) {
						self.secondPopup.openDetail(opts);
					})
					.on('click', '.export-btn', function(event) {
						var exportUrl = $(this).data("export-url");

						self.exportTable(exportUrl);
					});

				$thisChartTable.data('action-bound', true);
			}
		},
		//导出表格
		exportTable:function (exportUrl) {
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
	}
}