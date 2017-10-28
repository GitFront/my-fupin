function getCheckedSearchType() {
	return $('.search-type:checked').val();
}

$(function () {
	var
		CLICK_EV = 'click',

		extend = $.extend,
		ajax = Helpers.ajax,
		searchID,

		$body = $('body'),
		$searchField = $('#searchField'),
		$btnSearch = $('#btnSearch');

	$btnSearch.on(CLICK_EV, function () {
		var searchType = getCheckedSearchType();
		if (!searchID) {
			alert('请先选择下拉结果再点击搜索按钮');
		}
		else {
			goSearchResult(searchID, searchType);
		}
	});


	//下拉框相关代码
	//实现搜索输入框的输入提示js类
	function oSearchSuggest(searchFuc) {
		var input = $searchField;
		var suggestWrap = $('#searchsSuggest');
		var key = "";
		var init = function () {
			input.bind('keyup', sendKeyWord);
			suggestWrap.on(CLICK_EV, function (e) {
				e.stopPropagation();
			});
			$body.on(CLICK_EV, function () {
				hideSuggest();
			});
		};
		var hideSuggest = function () {
			suggestWrap
				.html('')
				.hide();
		};

//发送请求，根据关键字到后台查询
		var sendKeyWord = function (event) {

//键盘选择下拉项
			if (suggestWrap.css('display') == 'block' && event.keyCode == 38 || event.keyCode == 40 || event.keyCode == 13) {
				var current = suggestWrap.find('li.hover');
				if (event.keyCode == 38) {
					if (current.length > 0) {
						var prevLi = current.removeClass('hover').prev();
						if (prevLi.length > 0) {
							prevLi.addClass('hover');
							var sp = prevLi.find('span').text();
							var vl = prevLi.text().replace(sp, '');
							input.val(vl);
						}
					} else {
						var last = suggestWrap.find('li:last');
						last.addClass('hover');
						var sp = last.find('span').text();
						var vl = last.text().replace(sp, '');
						input.val(vl);
					}

				} else if (event.keyCode == 40) {
					if (current.length > 0) {
						var nextLi = current.removeClass('hover').next();
						if (nextLi.length > 0) {
							nextLi.addClass('hover');
							var sp = nextLi.find('span').text();
							var vl = nextLi.text().replace(sp, '');
							input.val(vl);
						}
					} else {
						var first = suggestWrap.find('li:first');
						first.addClass('hover');
						var sp = first.find('span').text();
						var vl = first.text().replace(sp, '');
						input.val(vl);
					}
				}
				else if (event.keyCode == 13) {
					selectItem(current);
				}

//输入字符
			} else {
				var valText = $.trim(input.val());
				if (valText == '' || valText == key) {
					return;
				}
				searchFuc(valText);
				key = valText;
				searchID = null;
			}

		};
//请求返回后，执行数据展示
		this.dataDisplay = function (data) {
			var resultList = data.result;
			if (resultList.length <= 0) {
				hideSuggest();
				return;
			}

//往搜索框下拉建议显示栏中添加条目并显示
			suggestWrap.html(template('tplPreSearch', {
				//TODO 这里为了模拟数据不是用返回的数据
				keyword: key,
				result: resultList
			}));
			suggestWrap.show();

//为下拉选项绑定鼠标事件
			suggestWrap.find('li').hover(function () {
				suggestWrap.find('li').removeClass('hover');
				$(this).addClass('hover');

			}, function () {
				$(this).removeClass('hover');
			}).bind('click', function () {
				var $li = $(this);
				selectItem($li);
			});
		};

		function selectItem($li) {
			input.val($li.text());
			searchID = $li.attr('data-id');
			hideSuggest();
		}

		init();
	}

	//实例化输入提示的JS,参数为进行查询操作时要调用的函数名
	var searchSuggest = new oSearchSuggest(sendKeyWordToBack);

	//这是一个模似函数，实现向后台发送ajax查询请求，并返回一个查询结果数据，传递给前台的JS,再由前台JS来展示数据。本函数由程序员进行修改实现查询的请求
	//参数为一个字符串，是搜索输入框中当前的内容
	function sendKeyWordToBack(keyword) {
		/* var obj = {
		 "keyword" : keyword
		 };
		 $.ajax({
		 type: "POST",
		 url: "${ctx}/front/suqiu2/search/prompt-keyword.action",
		 async:false,
		 data: obj,
		 dataType: "json",
		 success: function(data){
		 //var json = eval("("+data+")");
		 var key=data.split(",");
		 var aData = [];
		 for(var i=0;i<key.length;i++){
		 //以下为根据输入返回搜索结果的模拟效果代码,实际数据由后台返回
		 if(key[i]!=""){
		 aData.push(key[i]);
		 }
		 }
		 //将返回的数据传递给实现搜索输入框的输入提示js类
		 searchSuggest.dataDisplay(aData);
		 }
		 }); */

//以下为根据输入返回搜索结果的模拟效果代码,实际数据由后台返回
		ajax({
			url: URLS.URL_SEARCH_PRE_SEARCH,
			data: {
				key: keyword,
				type: getCheckedSearchType()
			},
			suppressNetErr: true,
			success: function (data) {
				var code = data.code,
					d = data.data;
				if (code == 0 && d) {
					searchSuggest.dataDisplay(d);
				}
			}
		});
//将返回的数据传递给实现搜索输入框的输入提示js类
	}

//下拉框相关代码 end


});
$(function () {
	var
		CLICK_EV = 'click',

		ACTION_KEYS = {
			OVERVIEW_FAMILY: 'overview_family',
			HELPER: 'helper',
			FAMILY_IMPLEMENT: 'family_implement',
			COUNTRY_STATUS: 'country_status',
			COUNTRY_LEADER: 'country_leader',
			COUNTRY_IMPLEMENT: 'country_implement'
		},

		extend = $.extend,
		ajax = Helpers.ajax,
		getSearchObject = Helpers.getSearchObject,
		poorFile = $.poorFile,
		FAMILY_TARGETS = poorFile.FAMILY_TARGETS,
		COUNTRY_TARGETS = poorFile.COUNTRY_TARGETS,


		$searchResult = $('#searchResult');

	init();

	function init() {
		var params = getSearchObject(),
			id = params.id,
			type = params.type;

		$('.search-types').find('[value=' + type + ']').prop('checked', true);

		renderList(id, type);
		$searchResult
			.on(CLICK_EV, 'li', function () {
				var $elem = $(this),
					key = $elem.attr('data-key'),
					id = $elem.attr('data-id'),
					noLink = $elem.hasClass('no-link');

				switch (key) {
					case ACTION_KEYS.OVERVIEW_FAMILY:
						poorFile.showFamilyFile(id);
						break;
					case ACTION_KEYS.HELPER:
						poorFile.showFamilyFile(id, FAMILY_TARGETS.NEWS);
						break;
					case ACTION_KEYS.FAMILY_IMPLEMENT:
						poorFile.showFamilyFile(id, FAMILY_TARGETS.IMPLEMENT);
						break;
					case ACTION_KEYS.COUNTRY_STATUS:
						if (!noLink) {
							poorFile.showCountryFile(id);
						}
						break;
					case ACTION_KEYS.COUNTRY_LEADER:
						poorFile.showCountryFile(id, COUNTRY_TARGETS.NEWS);
						break;
					case ACTION_KEYS.COUNTRY_IMPLEMENT:
						poorFile.showCountryFile(id, COUNTRY_TARGETS.IMPLEMENT);
						break;
				}
			});

		function renderList(id, type) {
			showLoadingMask();
			ajax({
				url: URLS.URL_SEARCH_SEARCH,
				data: {
					id: id,
					type: type
				},
				success: function (data) {
					var code = data.code,
						d = data.data;
					if (code == 0 && d) {
						$('#searchField').val(d.search_word);
						$searchResult.html(template('tplSearchResult', extend(d, {
							SEARCH_TYPES: SEARCH_TYPES
						})));
					}
				},
				complete: function() {
					hideLoadingMask();
				},
				error: function () {
					// renderList(id, type);
				}
			});
		}
	}

});