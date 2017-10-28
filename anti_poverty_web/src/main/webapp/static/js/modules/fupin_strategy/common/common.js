/*jshint browser:true */
/* global define, module */
(function (root, factory) {
	if (typeof define === 'function' && define.amd) {
		define([], factory);
	} else if (typeof module === 'object') {
		module.exports = factory();
	} else {
		root.format = factory();
	}
}(this, function () {

	return function (mask, value) {
		'use strict';
		if (!mask || isNaN(+value)) {
			return value; // return as it is.
		}

		var isNegative, result, decimal, group, posLeadZero, posTrailZero, posSeparator,
			part, szSep, integer,

		// find prefix/suffix
			len = mask.length,
			start = mask.search(/[0-9\-\+#]/),
			prefix = start > 0 ? mask.substring(0, start) : '',
		// reverse string: not an ideal method if there are surrogate pairs
			str = mask.split('').reverse().join(''),
			end = str.search(/[0-9\-\+#]/),
			offset = len - end,
			substr = mask.substring(offset, offset + 1),
			indx = offset + ( ( substr === '.' || ( substr === ',' )) ? 1 : 0 ),
			suffix = end > 0 ? mask.substring(indx, len) : '';

		// mask with prefix & suffix removed
		mask = mask.substring(start, indx);

		// convert any string to number according to formation sign.
		value = mask.charAt(0) === '-' ? -value : +value;
		isNegative = value < 0 ? value = -value : 0; // process only abs(), and turn on flag.

		// search for separator for grp & decimal, anything not digit, not +/- sign, not #.
		result = mask.match(/[^\d\-\+#]/g);
		decimal = ( result && result[result.length - 1] ) || '.'; // treat the right most symbol as decimal
		group = ( result && result[1] && result[0] ) || ',';  // treat the left most symbol as group separator

		// split the decimal for the format string if any.
		mask = mask.split(decimal);
		// Fix the decimal first, toFixed will auto fill trailing zero.
		value = value.toFixed(mask[1] && mask[1].length);
		value = +( value ) + ''; // convert number to string to trim off *all* trailing decimal zero(es)

		// fill back any trailing zero according to format
		posTrailZero = mask[1] && mask[1].lastIndexOf('0'); // look for last zero in format
		part = value.split('.');
		// integer will get !part[1]
		if (!part[1] || ( part[1] && part[1].length <= posTrailZero )) {
			value = ( +value ).toFixed(posTrailZero + 1);
		}
		szSep = mask[0].split(group); // look for separator
		mask[0] = szSep.join(''); // join back without separator for counting the pos of any leading 0.

		posLeadZero = mask[0] && mask[0].indexOf('0');
		if (posLeadZero > -1) {
			while (part[0].length < ( mask[0].length - posLeadZero )) {
				part[0] = '0' + part[0];
			}
		} else if (+part[0] === 0) {
			part[0] = '';
		}

		value = value.split('.');
		value[0] = part[0];

		// process the first group separator from decimal (.) only, the rest ignore.
		// get the length of the last slice of split result.
		posSeparator = ( szSep[1] && szSep[szSep.length - 1].length );
		if (posSeparator) {
			integer = value[0];
			str = '';
			offset = integer.length % posSeparator;
			len = integer.length;
			for (indx = 0; indx < len; indx++) {
				str += integer.charAt(indx); // ie6 only support charAt for sz.
				// -posSeparator so that won't trail separator on full length
				/*jshint -W018 */
				if (!( ( indx - offset + 1 ) % posSeparator ) && indx < len - posSeparator) {
					str += group;
				}
			}
			value[0] = str;
		}
		value[1] = ( mask[1] && value[1] ) ? decimal + value[1] : '';

		// remove negative sign if result is zero
		result = value.join('');
		if (result === '0' || result === '') {
			// remove negative sign if result is zero
			isNegative = false;
		}

		// put back any negation, combine integer and fraction, and add back prefix & suffix
		return prefix + ( ( isNegative ? '-' : '' ) + result ) + suffix;
	};

}));

(function ($) {
	var

		CLICK_EV = 'click',


		ANIMATION_TYPE_FADE = 'fade',
		ANIMATION_TYPE_NONE = 'none',

		extend = $.extend,
		trim = $.trim,

		modalMap = {};

	/*
	 note:
	 fix ie8 below click on filter not work bug
	 background: url(#)\9;
	 filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#4D000000, endColorstr=#4D000000)\9;

	 modal-bg should be display:none
	 modal itself had better to be display:none too
	 */
	function modal(opts) {
		opts = extend({
			id: 'modal',
			modalAnim: ANIMATION_TYPE_NONE,
			modalAnimSpeed: 300,
			//html or dom
			//tmpl: '',
			//Close on background clicked
			//closeOnBg: true,
			//modal close callback
			//onModalClose:function(){},
			classContent: 'modal-content',
			classModalBg: 'modal-bg',
			classClose: 'modal-close',
		}, opts);

		var id = opts.id,
			modal = modalMap[id];

		//all modals are new
		if (modal) {
			destroyModal();
		}

		var lock = false,
			$modalBackground,
			$content,
			animationType = opts.modalAnim,
			animationSpeed = opts.modalAnimSpeed,
			onModalClose = opts.onModalClose;

		modal = {
			show: showModal,
			hide: hideModal,
			destroy: destroyModal
		};
		init();
		modalMap[id] = modal;

		function init() {
			var closeOnBg = opts.closeOnBg,
				classModalBg = opts.classModalBg,
				classClose = opts.classClose,
				classContent = opts.classContent,
				tmpl = opts.tmpl,

				$body = $('body');


			modal.$modal = $modalBackground = $('<div id="' + id + '" class="' + classModalBg + '"></div>');
			$content = $('<div class="' + classContent + '"></div>');

			if (tmpl) {
				$content.append(tmpl);
			}

			$body.append($modalBackground.append($content));

			$content.on(CLICK_EV, '.' + trim(classClose).split(' ').join('.'), function () {
				hideModal();
			});

			showModal();

			if (closeOnBg) {
				$modalBackground.on(CLICK_EV, function (e) {
					if (e.target == e.currentTarget) {
						hideModal();
					}
				});
			}
		}

		//TODO show的api设计有点问题，被destroy的show无法处理
		function showModal(tmpl, onShow) {
			if (!lock) {
				lockModal();

				if (tmpl) {
					$content.html('')
						.append(tmpl);
				}

				$modalBackground.css({
					opacity: 0
				}).show();

				onShow && onShow();

				//modal will be destroyed when it's hidden
				switch (animationType) {
					case ANIMATION_TYPE_NONE:
						$modalBackground.css({
							opacity: 1
						});
						unlockModal();
						break;
					case ANIMATION_TYPE_FADE:
					default :
						$modalBackground.data('animate-speed', animationSpeed);
						if ($modalBackground.animate) {
							$modalBackground.animate({
								opacity: 1
							}, animationSpeed, unlockModal);
						}
						else {
							unlockModal();
						}
						break;
				}
			}

			return modal;
		}

		function hideModal() {
			onModalClose && onModalClose();

			if (!lock) {
				lockModal();

				//There may be no animate module. If not, only write data attribute and unlock immediately.
				switch (animationType) {
					case ANIMATION_TYPE_NONE:
						$modalBackground.hide();
						unlockModal();
						destroyModal();
						break;
					case ANIMATION_TYPE_FADE:
					default :
						$modalBackground.animate({
							opacity: 0
						}, animationSpeed, function () {
							$modalBackground.hide();
							unlockModal();
							destroyModal();
						});
						break;
				}
			}

			return modal;
		}

		function lockModal() {
			lock = true;
		}

		function unlockModal() {
			lock = false;
		}

		function destroyModal() {
			modal.$modal.remove();
			modalMap[id] = modal = null;
		}

		return modal;
	}

	$.modal = modal;

})($);
//require jquery.modal.js
(function ($) {
	var CLICK_EV = 'click',

		MODE_CENTER = 'center',

		extend = $.extend,
		isArray = $.isArray;

	function dialog(opts) {
		opts = extend({
			id: 'modal-dialog',
			//margin top and left 50% compensate
			//dialogMode: MODE_CENTER,
			classClose: 'dialog-close',
			classDialog: 'dialog',
			classDialogTitle: 'dialog-title',
			classDialogContent: 'dialog-content',
			classDialogBtns: 'dialog-btns',
			//null for no title
			title: '提示',
			//dialog content, html or dom
			//content:'',
			//btns: [
			//  {
			//    text: '确定',
			//    clazz: 'btn-norm',
			//    //clazzTxt: 'txt-norm',
			//    /**
			//     * on click
			//     * @param modal
			//     * @param $dialog
			//     */
			//    click: function (modal) {
			//      modal.hide();
			//    }
			//  }
			//]
		}, opts);

		var modal,
			$dialog = generateTemplate(),
			dialogMode = opts.dialogMode;

		function generateTemplate() {
			var
				classDialog = opts.classDialog,
				classDialogClose = opts.classClose,
				classDialogTitle = opts.classDialogTitle,
				classDialogContent = opts.classDialogContent,
				classDialogBtns = opts.classDialogBtns,
				title = opts.title,


				content = opts.content,
				btns = opts.btns,

				$dialogClose = $('<div class="' + classDialogClose + '"></div>'),
				$dialogContent = $('<div class="' + classDialogContent + '"></div>');

			$dialog = $('<div class="' + classDialog + '"></div>');

			if (title !== null) {
				var $dialogTitle = $('<div class="' + classDialogTitle + '">' + title + '</div>');
				$dialog.append($dialogTitle.append($dialogClose));
			}
			else {
				$dialog.append($dialogClose);
			}

			$dialog.append($dialogContent.append(content));

			if (isArray(btns)) {
				var i, btn,
					btnClick,
					$dialogBtns = $('<div data-count="' + btns.length + '" class="' + classDialogBtns + '"></div>'),
					$btn;
				for (i = 0; btn = btns[i]; i++) {
					$btn = $('<div class="' + btn.clazz + '"><span class="' + (btn.clazzTxt ? btn.clazzTxt : '') + '">' + btn.text + '</span></div>');
					$dialogBtns.append($btn);

					btnClick = btn.click;
					(function (btnClick) {
						$btn.on(CLICK_EV, function () {
							if (btnClick) {
								btnClick(modal, $dialog);
							}
							else {
								modal.hide();
							}
						});
					})(btnClick);
				}

				$dialog.append($dialogBtns);
			}

			return $dialog;
		}

		modal = $.modal(opts).show($dialog, function () {
			if (dialogMode == MODE_CENTER) {
				var dialogHeight = fullHeight($dialog),
					dialogWidth = fullWidth($dialog);
				$dialog.css({
					marginTop: -dialogHeight / 2 + 'px',
					marginLeft: -dialogWidth / 2 + 'px'
				});
			}
		});

		modal.$dialog = $dialog;

		return modal;
	}

	//patch for zepto
	function fullHeight($elem) {
		var height;
		if ($elem.outerHeight) {
			height = $elem.outerHeight();
		}
		else {
			height = $elem.height();
		}
		return height;
	}

	function fullWidth($elem) {
		var width;
		if ($elem.outerWidth) {
			width = $elem.outerWidth();
		}
		else {
			width = $elem.width();
		}
		return width;
	}

	$.dialog = dialog;
})($);

var
	LEVELS = {
		PROVINCE: 'province',
		CITY: 'city',
		COUNTY: 'county',
		TOWN: 'town',
		COUNTRY: "country"
	},
//区域范围
	AREA_SCOPE = {
		//全省/全部
		QUANBU: '1',
		//相对贫困村
		XIANGDUIPINKUNCUN: '2',
		//分散村
		FUNSANCUN: '3',
		//革命老区
		GEMINGLAOQU: '4',
		//中央苏区
		ZHONGYANGSUQU: '5'
	},

	DEFAULT_LEVEL = LEVELS.PROVINCE,
	DEFAULT_AREA_ID = 440000000000,
	DEFAULT_AREA_NAME = '广东省',
	DEFAULT_AREA_SCOPE = AREA_SCOPE.QUANBU,

	commonJSPConfig = {
		hideFocus: true
	},

	commonTipsoConfig = {
		speed: 200,
		width: 130,
		delay: 100,
		color: '#34344f',
		background: 'rgba(255, 255, 255, .7)'
	};

if (template) {
	template.config('openTag', '<#');
	template.config('closeTag', '#>');
}

$(function () {
	var
		MSG_NET_ERR = '数据出错，可能发生了错误或者数据未完成录入，请稍后再试',

		MODE_CENTER = 'center',

		win = window,
		extend = $.extend,
		$body = $('body'),

		commonDialog,
		$loadingDialog,

		Helpers = {};

	function showLoadingMask() {
		if (!$loadingDialog) {
			$loadingDialog = $([
				'<div class="loading-mask">',
				'<div class="spinner">',
				'<div class="spinner-container container1">',
				'<div class="circle1"></div>',
				'<div class="circle2"></div>',
				'<div class="circle3"></div>',
				'<div class="circle4"></div>',
				'</div>',
				'<div class="spinner-container container2">',
				'<div class="circle1"></div>',
				'<div class="circle2"></div>',
				'<div class="circle3"></div>',
				'<div class="circle4"></div>',
				'</div>',
				'<div class="spinner-container container3">',
				'<div class="circle1"></div>',
				'<div class="circle2"></div>',
				'<div class="circle3"></div>',
				'<div class="circle4"></div>',
				'</div>',
				'</div>',
				'</div>'
			].join(''));
			$body.append($loadingDialog);
		}

		$loadingDialog.show();
	}

	function hideLoadingMask() {
		if ($loadingDialog) {
			$loadingDialog.hide();
		}

	
	}

	function ajax(opts) {
		opts = extend({
			dataType: 'json',
			timeout: 60 * 1000
		}, opts);

		var
			successCB = opts.success,
			errorCB = opts.error,
			suppressNetErr = opts.suppressNetErr,
			dataType = opts.dataType;

		opts.success = function (data) {
			if (dataType == 'json') {
				var code = data.code,
					msg = data.msg;

				if (code != 0) {
					if (msg) {
						showAlertDialog({
							content: msg
						});
					}
				}

				if (successCB) {
					successCB(data);
				}
			}
			else {
				if (successCB) {
					successCB(data);
				}
			}
		};

		opts.error = function () {
			var args = arguments;
			if (!suppressNetErr) {
				showCommonErrorDialog(errorCB, args);
			}
			else {
				if (errorCB) {
					errorCB.apply(this, args);
				}
			}

		};

		$.ajax(opts);
	}

	function showCommonErrorDialog(errorCB, args) {
		showAlertDialog({
			content: MSG_NET_ERR,
			btnPosCB: function (modal) {
				if (errorCB) {
					errorCB.apply(this, args);
				}
				modal.hide();
			}
		});
	}

	function showAlertDialog(opts) {
		var btnPosCB = opts.btnPosCB,
			btnPosText = opts.btnPosText;
		showCommonDialog(extend({
			btns: [
				{
					text: btnPosText || '我知道了',
					clazz: 'btn',
					click: function (modal) {
						if (btnPosCB) {
							btnPosCB(modal);
						}
						else {
							modal.hide();
						}
					}
				}
			]
		}, opts));
	}

	function hideCommonDialog() {
		commonDialog && commonDialog.hide();
		commonDialog = null;
	}

	function showCommonDialog(opts) {
		commonDialog = $.dialog(extend({
			id: 'dialogCommon',
			classDialog: 'common-dialog',
			classModalBg: 'common-dialog-mask',
			dialogMode: MODE_CENTER,
			title: '提示',
			btns: null
		}, opts)).show();
	}

	function getSearchObject() {
		var search = location.search.substring(1);
		return search ? JSON.parse('{"' + search.replace(/&/g, '","').replace(/=/g, '":"') + '"}',
			function (key, value) {
				return key === "" ? value : decodeURIComponent(value)
			}) : {};
	}

	extend(Helpers, {
		ajax: ajax,
		showCommonDialog: showCommonDialog,
		hideCommonDialog: hideCommonDialog,
		showAlertDialog: showAlertDialog,
		showCommonErrorDialog: showCommonErrorDialog,
		getSearchObject: getSearchObject
	});

	extend(win, {
		showLoadingMask: showLoadingMask,
		hideLoadingMask: hideLoadingMask,
		Helpers: Helpers
	});
});
