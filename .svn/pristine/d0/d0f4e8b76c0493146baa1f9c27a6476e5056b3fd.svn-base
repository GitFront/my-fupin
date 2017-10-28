#广东扶贫云项目

##项目地址
### SVN（源码）
https://10.12.3.96:8443/svn/sjfw/trunk/广东省扶贫大数据平台/sourceCode
###测试环境
https://10.9.14.71:8443/cas/login?service=http://10.9.14.71:8080/anti_poverty_web/a/login
###正式环境
https://210.76.68.60:8443/cas/login?service=http://210.76.68.60:80/anti_poverty_web/a/login

##项目运作方式
1. 前端研发部（下简称前端）这边主要负责做界面，图表和地图由数据部门那边负责（下简称地图），分开代码编写
1. 开发时，前端仅需写好后本地架起服务器调用模拟数据即可运行，写完后运行gulp任务对代码进行压缩，后端会使用压缩版的js文件（带.min后缀）去套页面
1. 模拟数据和项目里面引用的路径可以的话请使用相对路径以便开发
1. 纯弹窗的页面靠跳转过去，目前仅在数据监控上面（非导航）点击才会带参数过去以显示正确的区域（area_id和area_level）
1. js引用的顺序和引用的库比较全的是`data.html`，请参考它
1. 纯弹窗的页面比较全的是`project.html`，请参考它
1. 前端侧的地图为地图侧给的特殊版本，仅能用于本地运行，请勿放到后端那边


##项目目录
```
# 目录尽量使用现有的fupin_strategy，这些才是目前版本前端主要用到的代码，部分一些公用的代码可能会直接改原来的fupin目录的代码。这是历史遗留的原因，如有兴趣请重构并修订此文档
# 插件的文档和项目地址直接在网上搜，都能搜到

anti_poverty_app/ # 后端项目路径，里面有个gulp目录，给后端编译用，基本与v2.3的一致
h5/	# 前端静态资源及源码
	fupin_app/	# 手机端部分内嵌页，基于Vue，但不需要编译，也不需要Vue文件，直接可加载执行，项目结构与PC版类似
		css/	# CSS文件
		data/	# 模拟数据
		doc/	# 文档与数据格式
		gulp/	# gulp任务
		html/	# HTML文件
		images/	# 图片文件
			fupin_strategy/	# 主要的图片目录
		js/		# JS文件
		scss/	# SCSS文件
		tpl/	# 组件等的模板文件
	v2.2/	# 这个项目原本是PC的旧版本，被当作文档用了
		data/	# 别的目录不用管，只有这个目录是有用的，用于存放文档和数据格式
			api说明.md	# 所有接口定义都在里面，看这个文件的方法是：
				# 上面是一些注意事项和常量定义，定义新接口和常量必须先在这文档里定义，然后与后端协商，然后自己模拟数据写
				# 像“/api/kpi”这样的地址是虚的（原本是用来给后端定路径的），只是用于标识什么接口，具体返回什么看返回里面的json文件名，在本目录下能找到对应的文件
				# 部分公共属性或结构会单独提取一个文件出来，在使用的时候会有引用该json的注明。当返回数据直接为该json格式时，需要完整包括msg, code, data的结构；当只是作为某接口里面的某属性时，仅需要其data属性里的值放到该属性中
				# 后续命名请继续使用上述规范
			全局回调函数.md	# 用于说明地图侧和前端侧声明的公共常量及方法，主要两个方法
				# “更新界面”指地图侧调用前端侧接口，由前端声明
				# “更新地图”指前端侧调用地图侧接口，由地图声明
	v2.3_Integrate/	# PC侧
		data/	# 模拟数据，如有添加，目录结构和文件命名参考现有的
		gulp/	# gulp任务
		scss/	# SCSS文件
			entrance.scss	# 系统入口页
			login.scss	# 登录页
			main.scss	# 旧版的，基本没用了
			main_strategy.scss	# 基本全部页面都是用这个生成的CSS文件，没拆
		static/	# 服务器给用户的静态资源文件（目录结构为后端要求）
			js/	# JS文件
				common/	# 地图侧用
				maps/	# 地图侧用
				modules/
					fupin/	# 主要是旧版的代码，有提到的才需要关注，其余可能是地图侧用到的或者是废弃的
						controllers/
							poor_country_detail_ctrl.js	# 村、户档案
						libs/	# 引用的库尽量放这边放，部分会与地图侧共用
							jquery.jscrollpane.js	# 滚动条插件
							jquery.mousewheel.js	# 滚动条插件依赖的库
							jquery.selectbox-0.2.js	# 下拉框插件
							jquery.ztree.js	# 树插件
							owl.carousel.js	# 轮播图插件
							qrcode.js	# 二维码插件
							template-native.js	# artTempalte插件（native语法版）
							tipso.js	# 带箭头的提示插件
					fupin_strategy/	# 全是新版的代码
						common/
							common.js	# 公用JS
							components.js	# 主要是带树弹窗里面单选、多选等的功能
							monitor.js	# 带树弹窗及其二级弹窗、导出等功能的公用代码
							urls_mock.js	# 这个v2.3与后端套完页面的JS是分开的，前者用的是本地模拟数据的路径，后者有的是后端真实的接口路径
							votelist.js	# 数据监控“一相当”图表
						tpl/	# 模版文件
							analyse_tpl.tpl	# 数据分析（旧需求，已废弃）
							data_monitor.tpl	# 数据监控等各种监控的一二级弹窗
							file_tpl.tpl	# 村户档案
						capital.js	# 资金监控
						countActive-int.js	# 数字跳动效果
						data.js	# 数据监控
						duty.js	# 责任监控
						entrance.js	# 系统入口
						login.js	# 系统登录
						poor_east_west.js	# 东西扶贫
						poor_service.js	# 扶贫服务
						project.js	# 项目监控
						render_strategy.js	# 战略地图
						search_common.js	# 导航搜索
						search_result.js	# 搜索结果
					script/	# 地图侧用代码
			themes/
				css/	# CSS文件
				font/	# 字体文件（请不要再允许UI加载中文字体，已去掉）
				images/	# 图片
				modules/
					fupin/	# 主要是旧版的代码
					fupin_strategy/	# 全是新版的代码
		webinf/	# （目录结构为后端要求）
			modules/
				fupin_h5/ # 静态页面
					capital.html	# 资金监控
					data.html	# 数据监控
					duty.html	# 责任监控
					entrance.html	# 项目入口页
					login.html	# 项目登录页
					poor_east_west.html	# 东西扶贫
					poor_service.html	# 扶贫服务
					poor_service_history.html	# 扶贫服务历史版本
					project.html	# 项目监控
					example_country.html	# 示范村
					search_result.html	# 搜索结果页，需要带参数过去（id：结果id；type：搜索类型）才能正确显示结果
					strategy.html	# 战略地图（大概已没用，与数据监控类型，但仅有一个不动的地图用于展示用）
					under_construction.html	# 正在建设中的页面模板，用于未开发但又需要占位的页面
```


##项目运行
###配置
1. 本地起一个服务器，设v2.3为根目录

###命令
gulp:
1. `gulp`：开发
1. `gulp min`：压缩js和css

