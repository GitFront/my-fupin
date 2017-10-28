$(function () {
	var

		MSG_NET_ERR = '网络连接出现问题，请重试',

		CLICK_EV = 'click',
		INPUT_EV = 'input',

		CLASS_DISABLED = 'disabled',
		CLASS_FOCUSED = 'focused',

		extend = $.extend,
		ajax = function (opts) {
			$.ajax(extend({
				dataType: 'json',
				timeout: 60 * 1000
			}, opts));
		},
		trim = $.trim,

		$formLogin = $('#formLogin'),
		$inputUsername = $('#inputUsername'),
		$inputPassword = $('#inputPassword'),
		$inputCode = $('#inputCode'),
		$imgCode = $('#imgCode'),
		$msgErr = $('#msgErr'),
		$checkboxRemember = $('#checkboxRemember'),
		$btnSubmit = $('#btnSubmit');

	init();

	function init() {
		[
			CLICK_EV,
			INPUT_EV
		].forEach(function (ev) {
			$formLogin
				.on(ev, function () {
					checkFocused();
				});
		});
		checkFocused();

		$imgCode
			.on(CLICK_EV, function () {
				var src = $imgCode.attr('data-src');
				$imgCode.attr('src', src + '?_=' + Date.now());
			});

		$btnSubmit
			.on(CLICK_EV, function () {
				if (!$btnSubmit.hasClass(CLASS_DISABLED)) {
					hideMsg();

					var msg = checkForm();

					if (msg) {
						showMsg(msg);
					}
					else {
						lockBtn();

						ajax({
							url: URLS.URL_LOGIN,
							type: 'POST',
							success: function (data) {
								var code = data.code,
									msg = data.msg;
								if (code == 0) {
									//TODO
									location.href = '';
								}
								else {
									showMsg(msg);
								}
							},
							error: function () {
								// showMsg(MSG_NET_ERR);
							},
							complete: function () {
								unlockBtn();
							}
						});
					}
				}

				function lockBtn() {
					$btnSubmit.addClass(CLASS_DISABLED);
				}

				function unlockBtn() {
					$btnSubmit.removeClass(CLASS_DISABLED);
				}
			});
	}

	function checkForm() {
		var msg,
			formData = genFormData(),
			username = formData.username,
			password = formData.password,
			code = formData.code;

		if (!username) {
			msg = '请输入用户名';
		}
		else if (!password) {
			msg = '请输入密码';
		}
		else if (!code) {
			msg = '请输入验证码';
		}

		return msg;
	}

	function checkFocused() {
		var formData = genFormData();
		if (formData.username || formData.password || formData.code) {
			$formLogin.addClass(CLASS_FOCUSED);
		}
		else {
			$formLogin.removeClass(CLASS_FOCUSED);
		}
	}

	function genFormData() {
		return {
			username: trim($inputUsername.val()),
			password: $inputPassword.val(),
			code: trim($inputCode.val()),
			remember_username: $checkboxRemember.prop('checked')
		};
	}

	function showMsg(msg) {
		$msgErr
			.text(msg)
			.show();
	}

	function hideMsg() {
		$msgErr.hide();
	}
});