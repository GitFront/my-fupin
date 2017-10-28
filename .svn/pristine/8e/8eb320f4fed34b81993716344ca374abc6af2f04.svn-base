(function (win, Vue) {
//公用方法
	var
		doc = document,
		elemBody = doc.getElementsByTagName('body')[0],

		Helpers = {},

		hasOwn = Object.prototype.hasOwnProperty,
		toStr = Object.prototype.toString,

		Promise = Vue.Promise,

		templatesLoaded = {};

	function isArray(arr) {
		if (typeof Array.isArray === 'function') {
			return Array.isArray(arr);
		}

		return toStr.call(arr) === '[object Array]';
	}

	function isPlainObject(obj) {
		if (!obj || toStr.call(obj) !== '[object Object]') {
			return false;
		}

		var hasOwnConstructor = hasOwn.call(obj, 'constructor');
		var hasIsPrototypeOf = obj.constructor && obj.constructor.prototype && hasOwn.call(obj.constructor.prototype, 'isPrototypeOf');
		// Not own constructor property must be Object
		if (obj.constructor && !hasOwnConstructor && !hasIsPrototypeOf) {
			return false;
		}

		// Own properties are enumerated firstly, so to speed up,
		// if last one is own, then all properties are own.
		var key;
		for (key in obj) { /**/
		}

		return typeof key === 'undefined' || hasOwn.call(obj, key);
	}

	function extend() {
		var options, name, src, copy, copyIsArray, clone;
		var target = arguments[0];
		var i = 1;
		var length = arguments.length;
		var deep = false;

		// Handle a deep copy situation
		if (typeof target === 'boolean') {
			deep = target;
			target = arguments[1] || {};
			// skip the boolean and the target
			i = 2;
		} else if ((typeof target !== 'object' && typeof target !== 'function') || target == null) {
			target = {};
		}

		for (; i < length; ++i) {
			options = arguments[i];
			// Only deal with non-null/undefined values
			if (options != null) {
				// Extend the base object
				for (name in options) {
					src = target[name];
					copy = options[name];

					// Prevent never-ending loop
					if (target !== copy) {
						// Recurse if we're merging plain objects or arrays
						if (deep && copy && (isPlainObject(copy) || (copyIsArray = isArray(copy)))) {
							if (copyIsArray) {
								copyIsArray = false;
								clone = src && isArray(src) ? src : [];
							} else {
								clone = src && isPlainObject(src) ? src : {};
							}

							// Never move original objects, clone them
							target[name] = extend(deep, clone, copy);

							// Don't bring in undefined values
						} else if (typeof copy !== 'undefined') {
							target[name] = copy;
						}
					}
				}
			}
		}

		// Return the modified object
		return target;
	}

	//封装成类似jquery的ajax请求
	function ajax(opts) {
		var
			GET = 'GET',
			JSONP = 'jsonp',
			JSON = 'json',
			TEXT = 'text',

			http = Vue.http;

		opts = extend({
			type: GET,
			cache: true
		}, opts);

		var
			options = {
				//not use application/json
				emulateJSON: true
			},

			url = opts.url,
			data = opts.data,
			timeout = opts.timeout,
			dataType = opts.dataType,
			cache = opts.cache,
			type = opts.type.toUpperCase(),
			method = type.toLocaleLowerCase(),
			completeCB = opts.complete,
			successCB = opts.success,
			errorCB = opts.error,

			args,
		//prevent multiple fire
			errorCBFired = false,
			completeCBFired = false;

		if (!cache) {
			options.params = extend(options.params, {
				_t: Date.now()
			});
		}

		if (type == GET || dataType == JSONP) {
			options.params = extend(options.params, data);
			args = [url, options];
		}
		else {
			args = [url, data, options];
		}

		if (dataType == JSONP) {
			method = JSONP;
		}

		//vue-resource有timeout的设定但后面什么callback都没了，要自己实现
		if (timeout) {
			var timeoutTimer;
			options.before = function (request) {
				timeoutTimer = setTimeout(function () {
					request.abort();

					if (errorCB && !errorCBFired) {
						errorCB();
						errorCBFired = true;
					}

					if (completeCB && !completeCBFired) {
						completeCB();
						completeCBFired = true;
					}
				}, timeout);
			};
		}

		function convertBlobToText(blob) {
			return new Promise(function (resolve, reject) {
				var reader = new FileReader();

				reader.onload = function () {
					resolve(reader.result);
				};

				reader.onerror = function () {
					reject(reader.result);
				};

				reader.readAsBinaryString(blob);
			});
		}

		function extractResult(response) {
			var
				body = response.body,
				result = response.text();

			//防止服务器设置不对，尝试转换内容为文本
			if (dataType == TEXT && typeof Blob != 'undefined' && typeof FileReader != 'undefined') {
				if (body instanceof Blob) {
					result = convertBlobToText(body);
				}
			}

			switch (dataType) {
				case JSON:
				case JSONP:
					result = response.json()
						.catch(function (e) {
							console.log(e);
						});
					break;
			}

			return result;
		}

		function clearTimeoutTimer() {
			if (timeoutTimer) {
				clearTimeout(timeoutTimer);
				timeoutTimer = null;
			}
		}

		http[method].apply(http, args)
			.then(function (response) {
				clearTimeoutTimer();

				var result = extractResult(response);

				if (successCB) {
					result.then(function (data) {
						successCB(data);
					});
				}
			}, function (response) {
				clearTimeoutTimer();

				var result = extractResult(response);

				if (errorCB && !errorCBFired) {
					errorCBFired = true;

					result.then(function (data) {
						errorCB(data);
					});
				}
			})
			.then(function () {
				if (completeCB && !completeCBFired) {
					completeCB();
					completeCBFired = true;
				}
			});
	}

	//封装统一错误处理的ajax
	function ajaxCommon(opts) {
		var JSON = 'json',
			JSONP = 'jsonp';

		opts = extend({
			dataType: JSON,
			timeout: 60 * 1000
		}, opts);

		var
			dataType = opts.dataType,
			successCB = opts.success,
			errorCB = opts.error;

		opts.success = function (data) {
			switch (dataType) {
				case JSON:
				case JSONP:
					var code = data.code,
						msg = data.msg;

					if (code == 0) {
						if (successCB) {
							successCB(data);
						}
					}
					else {
						//TODO 要统一处理错误处理再加
					}
					break;
				default:
					if (successCB) {
						successCB(data);
					}
					break;
			}
		};
		opts.error = function () {
			if (errorCB) {
				errorCB.apply(this, arguments);
			}
		};

		ajax(opts);
	}

	function getSearchObj() {
		return deparam(location.search.substring(1));
	}

	function deparam(str) {
		var obj = str.split("&").reduce(function (prev, curr, i, arr) {
			if (curr !== '') {
				var p = curr.split("=");
				prev[decodeURIComponent(p[0])] = decodeURIComponent(p[1]);
			}
			return prev;
		}, {});
		return obj;
	}

	/**
	 * Load templates into body
	 * @param keys: Required. Array or string. File names without suffix.
	 * @param successCB: optional
	 * @param errorCB: optional
	 * @returns {*} Promise
	 */
	function loadTemplates(keys, successCB, errorCB) {
		var URL_TPL_BASE = '../../tpl/',
			TPL_SUFFIX = '.tpl',

			STATE_LOADING = 1,
			STATE_LOADED = 2;

		if (!isArray(keys)) {
			keys = [keys];
		}

		var promises = [];

		keys.forEach(function (key) {
			var v = templatesLoaded[key];
			if (!v) {
				v = templatesLoaded[key] = {
					state: STATE_LOADING,
					successCbs: [],
					errorCBs: [],
					promise: new Promise(function (resolve, reject) {
						ajax({
							url: URL_TPL_BASE + key + TPL_SUFFIX,
							dataType: 'text',
							success: function (data) {
								var frag = doc.createDocumentFragment(),
									t = doc.createElement('div'),
									c, i = 0;

								t.innerHTML = data;
								for (; c = t.childNodes[i]; i++) {
									//不能创建frag再加进去，否则出错
									elemBody.appendChild(c);
								}

								t.innerHTML = '';

								var successCBs = templatesLoaded[key].successCbs;

								templatesLoaded[key] = {
									state: STATE_LOADED,
									promise: Promise.resolve()
								};

								successCBs.forEach(function (cb) {
									cb();
								});

								resolve();
							},
							error: function (data) {
								var errorCBs = templatesLoaded[key].errorCBs;

								templatesLoaded[key] = null;

								errorCBs.forEach(function (cb) {
									cb(data);
								});

								reject(data);
							}
						})
					})
				};
			}

			if (v.state == STATE_LOADING) {
				if (successCB) {
					v.successCbs.push(successCB);
				}

				if (errorCB) {
					v.errorCBs.push(errorCB);
				}
			}
			else if (v.state == STATE_LOADED) {
				if (successCB) {
					successCB();
				}
			}

			promises.push(v.promise);
		});

		return Promise.all(promises);
	}

	var Components = function () {
		var comps = {};

		/**
		 * 注册组件并加载模板，必须先在这里注册再使用组件
		 * @param opts
		 * tagName：String, Required, 组件注册的key，组件标签名
		 * factory：Function, Required, 组件加载模板后的注册操作写在这里
		 * tplName：String, Optional, 默认使用与tagName同样的模板文件，同loadTemplates加载逻辑
		 */
		function register(opts) {
			var tagName = opts.tagName,
				factory = opts.factory,
				tplName = opts.tplName;

			if (!tplName) {
				tplName = tagName;
			}

			if (!comps[tagName]) {
				comps[tagName] = {
					promise: loadTemplates(tplName)
						.then(factory)
				};
			}
			else {
				throw new Error('component name duplicated');
			}
		}

		/**
		 * 为保证组件加载后再做其他操作，调用此方法等待组件加载完成后再操作
		 * @param componentNames, String or Array, 需要等待的组件名，之前注册的组件key
		 * @param successCB
		 * @param errorCB
		 * @returns {*} Promise
		 */
		function wait(componentNames, successCB, errorCB) {
			if (!isArray(componentNames)) {
				componentNames = [componentNames];
			}

			var promises = [];

			componentNames.forEach(function (key) {
				var comp = comps[key];
				if (!comp) {
					throw new Error('component not registered');
				}
				else {
					var promise = comp.promise;
					promises.push(promise);
				}
			});

			var finalPromise = Promise.all(promises);
			finalPromise
				.then(function () {
					if (successCB) {
						successCB();
					}
				}, function (data) {
					if (errorCB) {
						errorCB(data);
					}
				});

			return finalPromise;
		}

		return {
			register: register,
			wait: wait
		}
	}();

	win.Helpers = extend(Helpers, {
		isArray: isArray,
		isPlainObject: isPlainObject,
		extend: extend,
		ajax: ajax,
		ajaxCommon: ajaxCommon,
		getSearchObj: getSearchObj,
		deparam: deparam,
		loadTemplates: loadTemplates,
		/**
		 * 组件注册与加载工具，方法见里面声明
		 */
		Components: Components
	});


//公用组件
	//header template loaded
	Components.register({
		tagName: 'app-header',
		tplName: 'common',
		factory: function () {
			Vue.component('app-header', {
				template: '#tplAppHeader',
				props: {
					title: {
						type: String
					},
					showBack: {
						type: Boolean,
						default: false
					},
					backByClose: {
						type: Boolean,
						default: false
					}
				},
				methods: {
					goBack: function () {
						var me = this;
						if (me.backByClose) {
							if (/android|Android/i.test(navigator.userAgent)) {
								window.ncp.closeCurrentPage();
							}else if (/ipad|iphone|mac|iOS/i.test(navigator.userAgent)) {
								var iosFrame = document.getElementById("iosFrame");
								if(typeof iosFrame == "undefined" || iosFrame == null){
									iosFrame = document.createElement('iframe');
									iosFrame.style.display = 'none';
									iosFrame.id = "iosFrame";
									if(document.body){
										document.body.appendChild(iosFrame);
									}else{
										document.documentElement.appendChild(iosFrame);
									}
								}

								iosFrame.src='objc://closeCurrentPage';
							}
						}
						else {
							history.back();
						}
					}
				}
			});
		}
	});
	//dialog template loaded
	Components.register({
		tagName: 'app-dialog',
		tplName: 'common',
		factory: function () {
			Vue.component('app-dialog', {
				template: '#tplAppDialog',
				props: {
					dialogContent: {
						type: String
					},
					isShowDialog: {
						type: Boolean,
						default: false
					}
				},
				methods: {
					openDialog: function () {
						this.$set(this, 'isShowDialog', true);
					},
					closeDialog: function () {
						this.$set(this, 'isShowDialog', false);
					}
				}
			});
		}
	});

	//公用filter
	Vue.filter('money', function(v){
		return (+v).toFixed(2);
	});
})(window, Vue);

