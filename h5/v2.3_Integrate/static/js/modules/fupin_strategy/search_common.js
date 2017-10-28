function goSearchResult(id, type) {
	location.href = 'search_result.html?id=' + id + '&type=' + type;
}

var SEARCH_TYPES = {
	FAMILY: 'family',
	COUNTRY: 'country',
	HELPER: 'helper',
	PROJECT: 'project'
};

$(function () {
	var
		CLICK_EV = 'click',
		INPUT_EV = 'input',
		CHANGE_EV = 'change',
		KEYUP_EV = 'keyup',

		CLASS_OPEN = 'open',
		CLASS_HOVER = 'hover',

		trim = $.trim,
		extend = $.extend,
		ajax = Helpers.ajax,

		lastKeyword,
		searchID,
		denouncedSendPresearch = denounce(sendPresearch, 300),

		$body = $('body'),
		$navSearch = $('#navSearch'),
		$selectPresearchTypes = $('#selectPresearchTypes'),
		$inputPresearchField = $('#inputPresearchField'),
		$btnClearPresearch = $('#btnClearPresearch'),
		$presearchWrapper = $('#presearchWrapper'),
		$searchsSuggest = $('#searchsSuggest');


	init();

	$body.append([
		'<script id="tplPreSearch" type="text/html">',
		'<ul>',
		'<#for(var i = 0, len = result.length; i < len; i++){#>',
		'<#var item = result[i];#>',
		'<li data-id="<#=item.id#>"><#=item.name#><span> - <#=item.place#></span></li>',
		'<#}#>',
		'</ul>',
		'</script>'
	].join(''));

	function init() {
		$navSearch.on(CLICK_EV, function (e) {
			e.stopPropagation();
			$navSearch.toggleClass(CLASS_OPEN);
		});

		$btnClearPresearch.on(CLICK_EV, clearPreseachField);

		$selectPresearchTypes
			.on(CHANGE_EV, function () {
				clearPreseachField();
			})
			.selectbox();

		$inputPresearchField
			.on(INPUT_EV, function () {
				var v = $inputPresearchField.val();

				if (v !== '') {
					$btnClearPresearch.show();
				}
				else {
					$btnClearPresearch.hide();
				}
			})
			.on(KEYUP_EV, sendKeyWord);

		$presearchWrapper.on(CLICK_EV, function (e) {
			e.stopPropagation();
		});
		$body.on(CLICK_EV, function () {
			$navSearch.removeClass(CLASS_OPEN);
			hideSuggest();
		});


		$searchsSuggest.on(CLICK_EV, 'li', function () {
			var $li = $(this);
			selectItem($li);
		});
	}

	function sendKeyWord(event) {
		var current = $searchsSuggest.find('li.hover');

		switch (event.keyCode) {
			case 38: {
				if (current.length > 0) {
					var $prevLi = current
						.removeClass(CLASS_HOVER)
						.prev();
					if ($prevLi.length > 0) {
						$prevLi.addClass(CLASS_HOVER);
					}
				} else {
					var $lastLi = $searchsSuggest.find('li:last');
					$lastLi.addClass(CLASS_HOVER);
				}
				break;
			}
			case 40: {
				if (current.length > 0) {
					var $nextLi = current.removeClass('hover').next();
					if ($nextLi.length > 0) {
						$nextLi.addClass(CLASS_HOVER);
					}
				} else {
					var $firstLi = $searchsSuggest.find('li:first');
					$firstLi.addClass(CLASS_HOVER);
				}
				break;
			}
			case 13: {
				selectItem(current);
				break;
			}
			default: {
				var v = $inputPresearchField.val();
				if (v != lastKeyword) {
					denouncedSendPresearch(trim(v));
				}
				break;
			}
		}

		lastKeyword = $inputPresearchField.val();
	}

	function sendPresearch(keyword) {
		ajax({
			url: URLS.URL_SEARCH_PRE_SEARCH,
			data: {
				key: keyword,
				type: getSelectedSearchType()
			},
			suppressNetErr: true,
			success: function (data) {
				var code = data.code,
					d = data.data;
				if (code == 0 && d) {
					showSuggest(d);
				}
			}
		});
	}

	function selectItem($li) {
		if ($li && $li.length > 0) {
			searchID = $li.attr('data-id');
			goSearchResult(searchID, getSelectedSearchType());
		}
	}

	function clearPreseachField() {
		$inputPresearchField
			.val('')
			[0].focus();
		hideSuggest();
	}

	function getSelectedSearchType() {
		return $selectPresearchTypes.val();
	}

	function showSuggest(data) {
		var resultList = data.result,
			keyword = data.keyword;
		if (resultList.length <= 0) {
			hideSuggest();
			return;
		}
		else if (!$inputPresearchField.val()) {
			hideSuggest();
			return;
		}

		$searchsSuggest
			.html(template('tplPreSearch', {
				keyword: keyword,
				result: resultList
			}))
			.show();

		var $lis = $searchsSuggest
			.find('li');

		$lis
			.hover(function () {
				$lis.removeClass(CLASS_HOVER);
				$(this).addClass(CLASS_HOVER);
			}, function () {
				$(this).removeClass(CLASS_HOVER);
			});
	}

	function hideSuggest() {
		$searchsSuggest
			.html('')
			.hide();
	}

	function denounce(fn, time) {
		var timer;

		return function () {
			var args = Array.prototype.slice.apply(arguments);
			if (timer) {
				clearTimeout(timer);
				timer = null;
			}

			timer = setTimeout(function () {
				fn.apply(this, args);

				timer = null;
			}, time);
		};
	}
});