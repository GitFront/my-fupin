<!-- 村、户档案 begin -->
<div id="popupCountryFile" class="mask-popup-poor-file country-file">
	<div class="popup-poor-file">
		<div class="btn-close"></div>
		<div class="popup-content"></div>
	</div>
</div>
<div id="popupFamilyFile" class="mask-popup-poor-file family-file">
	<div class="popup-poor-file">
		<div class="btn-close"></div>
		<div class="popup-content"></div>
	</div>
</div>

<!-- 村户档案共用 -->
<!-- 年tab -->
<script id="tplSubTogglesYear" type="text/html">
	<div class="sub-toggles sub-toggles-year">
		<ul>
			<#for(var i = 0, len = available_years.length; i < len; i++) { #>
			<li class="<#=available_years[i].value == default_time.year ? 'active' : ''#>" data-value="<#=available_years[i].value#>"><#=available_years[i].name#></li>
			<# } #>
		</ul>
	</div>
	<div class="sub-toggles-content">
	</div>
</script>
<script id="tplTabContentSubTogglesYearWrap" type="text/html">
	<div class="page page-income">
		<div class="toggles-content-wrapper">
		</div>
	</div>
</script>

<!--村档案-->
<script id="tplCountryFileBasic" type="text/html">
	<div class="btn-close"></div>
	<div class="file-left">
		<div class="file-title"><#=country_name#>档案</div>
		<div class="responsible">
			<div class="avatar">
				<img src="<#=summary.responsible_avatar#>">
			</div>
			<div class="desc">
				<div class="ico-phone tipso" data-title="<#=summary.responsible_phone#>"></div>
				<p>村委负责人：<#=summary.responsible_name#></p>
				<p>职务：<#=summary.responsible_duty#></p>
			</div>
		</div>
		<div class="country-leader">
			<div class="avatar">
				<img src="<#=summary.country_leader_avatar#>">
			</div>
			<div class="desc">
				<div class="ico-phone tipso" data-title="<#=summary.country_leader_phone#>"></div>
				<p>驻村队长：<#=summary.country_leader_name#></p>
				<p>单位：<#=summary.country_leader_org#></p>
				<p>驻村时间：<#=summary.country_leader_start_time#></p>
			</div>
		</div>
	</div>
	<div class="file-right">
		<div class="basic-stat">
			<div class="data data1">
				<div class="values">
					<div class="value">
						<span class="num"><#=stat.amount_total_family#></span><span>户</span>
					</div>
					<div class="value">
						<span class="num"><#=stat.amount_total_people#></span><span>人</span>
					</div>
				</div>
				<div class="label">全村人口</div>
			</div>
			<div class="data data2">
				<div class="values">
					<div class="value">
						<span class="num"><#=stat.amount_poor_family#></span><span>户</span>
					</div>
					<div class="value">
						<span class="num"><#=stat.amount_poor_people#></span><span>人</span>
					</div>
				</div>
				<div class="label">贫困人口</div>
			</div>
			<div class="data data3">
				<div class="values">
					<div class="value"><span class="num"><#=stat.ratio_poor#></span><span>%</span></div>
				</div>
				<div class="label">贫困发生率</div>
			</div>
			<div class="data data4">
				<div class="values">
					<div class="value">
						<span class="num"><#=stat.amount_success_family#></span><span>户</span>
					</div>
					<div class="value">
						<span class="num"><#=stat.amount_success_people#></span><span>人</span>
					</div>
				</div>
				<div class="label">脱贫人口</div>
			</div>
			<div class="data data5">
				<div class="values">
					<div class="value"><span class="num"><#=stat.score#></span></div>
				</div>
				<div class="label">综合得分</div>
			</div>
			<div class="stamp <#=stat.has_achieved ? 'success' : ''#>"><#=stat.has_achieved ? '达标' : '未达标'#></div>
		</div>
		<div class="page-tabs">
			<ul>
				<li data-target="status">基本情况</li>
				<li data-target="development_status">发展现状</li>
				<li data-target="plan">帮扶计划</li>
				<li data-target="implement">帮扶施策</li>
				<li data-target="news">驻村动态</li>
				<li data-target="meeting_news">扶贫会议</li>
				<li data-target="invested">资金投入</li>
				<li data-target="eliminate_path">脱贫轨迹</li>
				<li data-target="data_path">数据轨迹</li>
				<li data-target="business">业务办理</li>
			</ul>
		</div>
		<div class="page-tab-content"></div>
	</div>
</script>
<script id="tplCountryFileStatus" type="text/html">
<div class="page page-status">
	<div class="main-title"><#=summary.country_name#></div>
	<ul class="attributes">
		<li class="pair"><span class="label">村属性：</span><span class="value"><#=summary.country_attribute#></span></li>
		<li class="pair"><span class="label">地形地貌：</span><span class="value"><#=summary.topography#></span></li>
		<li class="pair"><span class="label">发展方向：</span><span class="value"><#=summary.dev_target#></span></li>
		<li class="pair"><span class="label">自然村（村民小组）：</span><span class="value"><#=summary.groups#></span></li>
	</ul>
	<section class="population-section">
		<div class="sub-title">人口结构</div>
		<ul class="blocks-wrap">
			<li class="block family">
				<#var bars = population.family.bars, total = population.family.total;#>
				<div class="header"><span class="label">总户数</span><span class="value"><#=total#></span></div>
				<ul class="bars">
					<#for(var i = 0, len = bars.length; i < len; i++){#>
					<#var item = bars[i];#>
					<li>
						<div class="label"><#=item.name#></div>
						<div class="value"><#=item.value#></div>
						<div class="bar-wrap">
							<div class="bar" style="width: <#=item.value / total * 100#>%"></div>
						</div>
					</li>
					<#}#>
				</ul>
			</li>
			<li class="block">
				<#var bars = population.people.bars, total = population.people.total;#>
				<div class="header"><span class="label">总人口数</span><span class="value"><#=total#></span></div>
				<ul class="bars">
					<#for(var i = 0, len = bars.length; i < len; i++){#>
					<#var item = bars[i];#>
						<li>
							<div class="label"><#=item.name#></div>
							<div class="value"><#=item.value#></div>
							<div class="bar-wrap">
								<div class="bar" style="width: <#=item.value / total * 100#>%"></div>
							</div>
						</li>
					<#}#>
				</ul>
			</li>
			<li class="block">
				<#var bars = population.labor.bars, total = population.labor.total;#>
				<div class="header"><span class="label">劳动力人数</span><span class="value"><#=total#></span></div>
				<ul class="bars">
					<#for(var i = 0, len = bars.length; i < len; i++){#>
					<#var item = bars[i];#>
						<li>
							<div class="label"><#=item.name#></div>
							<div class="value"><#=item.value#></div>
							<div class="bar-wrap">
								<div class="bar" style="width: <#=item.value / total * 100#>%"></div>
							</div>
						</li>
					<#}#>
				</ul>
			</li>
		</ul>
	</section>
	<section class="production-section">
		<div class="sub-title">生产条件</div>
		<ul>
			<li class="block cultivated-land">
				<table class="card-table">
					<tr>
						<th rowspan="2">耕地面积</th>
						<td class="label">总数</td>
						<td class="value"><span class="num"><#=production.cultivated_land.total#></span><span>亩</span></td>
					</tr>
					<tr>
						<td class="label">有效</td>
						<td class="value"><span class="num"><#=production.cultivated_land.available#></span><span>亩</span></td>
					</tr>
				</table>
			</li>
			<li class="block forest-land">
				<table class="card-table">
					<tr>
						<th rowspan="3">林地面积</th>
						<td class="label">总数</td>
						<td class="value"><span class="num"><#=production.forest_land.total#></span><span>亩</span></td>
					</tr>
					<tr>
						<td class="label">有效</td>
						<td class="value"><span class="num"><#=production.forest_land.available#></span><span>亩</span></td>
					</tr>
				</table>
			</li>
			<li class="block grass-water">
				<table class="card-table">
					<tr>
						<th>牧草地面积</th>
						<td class="label">总数</td>
						<td class="value"><span class="num"><#=production.grass_total#></span><span>亩</span></td>
					</tr>
					<tr>
						<th>水面面积</th>
						<td class="label">总数</td>
						<td class="value"><span class="num"><#=production.water_total#></span><span>亩</span></td>
					</tr>
				</table>
			</li>
		</ul>
	</section>
</div>
</script>
<script id="tplCountryFileDevelopmentStatus" type="text/html">
<div class="page page-development-status">
	<div class="content-wrapper">
		<table class="blocks-table">
			<tr>
				<td class="cell-block">
					<div class="header">收入状况</div>
					<table class="content">
						<tr>
							<td class="label">农民人均可支配收入</td>
							<td class="value"><#=income.average#></td>
						</tr>
						<tr>
							<td class="label">村级集体经济收入</td>
							<td class="value"><#=income.collective#></td>
						</tr>
					</table>
				</td>
				<td class="cell-block">
					<div class="header">社会保障</div>
					<table class="content">
						<tr>
							<td class="label">参加新型农村合作医疗</td>
							<td class="value"><#=social_security.rural_medical#></td>
						</tr>
						<tr>
							<td class="label">参加城乡居民基本养老保险</td>
							<td class="value"><#=social_security.pension_insurance#></td>
						</tr>
						<tr>
							<td class="label">获得医疗救助人次</td>
							<td class="value"><#=social_security.medical_help#></td>
						</tr>
					</table>
				</td>
				<td class="cell-block">
					<div class="header">危房改造</div>
					<table class="content">
						<tr>
							<td class="label">危房户数</td>
							<td class="value"><#=danger_house.amount#></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="cell-block">
					<div class="header">村级道路硬底化</div>
					<table class="content">
						<tr>
							<td class="label">行政村到乡镇未通里程</td>
							<td class="value"><#=country_road.distance_admin_to_town#></td>
						</tr>
						<tr>
							<td class="label">行政村是否通客运班车</td>
							<td class="value"><#=country_road.bus_available#></td>
						</tr>
						<tr>
							<td class="label">自然村到行政村未通村数</td>
							<td class="value"><#=country_road.amount_natural_to_admin#></td>
						</tr>
						<tr>
							<td class="label">自然村到行政村未通里程数</td>
							<td class="value"><#=country_road.distance_natural#></td>
						</tr>
					</table>
				</td>
				<td class="cell-block">
					<div class="header">农村电力保障</div>
					<table class="content">
						<tr>
							<td class="label">未通生活用电的自然村数</td>
							<td class="value"><#=electricity.not_enabled_living_country#></td>
						</tr>
						<tr>
							<td class="label">未通生产用电的自然村数</td>
							<td class="value"><#=electricity.not_enabled_production_country#></td>
						</tr>
						<tr>
							<td class="label">未通生活用电户数</td>
							<td class="value"><#=electricity.not_enabled_living_family#></td>
						</tr>
						<tr>
							<td class="label">已通电自然村数</td>
							<td class="value"><#=electricity.enabled_country#></td>
						</tr>
					</table>
				</td>
				<td class="cell-block">
					<div class="header">饮水安全</div>
					<table class="content">
						<tr>
							<td class="label">未实现饮水安全户数</td>
							<td class="value"><#=water_safety.unsafe#></td>
						</tr>
						<tr>
							<td class="label">饮水困难户数</td>
							<td class="value"><#=water_safety.difficult#></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="cell-block">
					<div class="header">特色产业增收</div>
					<table class="content">
						<tr>
							<td class="label">特色产业农民专业合作社</td>
							<td class="value"><#=industry.amount_fsc#></td>
						</tr>
						<tr>
							<td class="label">特色产业农民专业合作社贫困户数</td>
							<td class="value"><#=industry.amount_family_fsc#></td>
						</tr>
					</table>
				</td>
				<td class="cell-block">
					<div class="header">乡村旅游</div>
					<table class="content">
						<tr>
							<td class="label">开展乡村旅游的户数</td>
							<td class="value"><#=travel.travel_family#></td>
						</tr>
						<tr>
							<td class="label">乡村旅游从业人员数</td>
							<td class="value"><#=travel.travel_people#></td>
						</tr>
						<tr>
							<td class="label">经营农家乐的户数</td>
							<td class="value"><#=travel.rural_inn_family#></td>
						</tr>
						<tr>
							<td class="label">经营农家乐户年均收入</td>
							<td class="value"><#=travel.rural_inn_average#></td>
						</tr>
					</table>
				</td>
				<td class="cell-block">
					<div class="header">文化建设</div>
					<table class="content">
						<tr>
							<td class="label">行政村文化(图书)室个数</td>
							<td class="value"><#=culture.library#></td>
						</tr>
						<tr>
							<td class="label">通广播电视户数</td>
							<td class="value"><#=culture.tv#></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="cell-block">
					<div class="header">卫生和计划生育</div>
					<table class="content">
						<tr>
							<td class="label">卫生室个数</td>
							<td class="value"><#=health.office#></td>
						</tr>
						<tr>
							<td class="label">执业(助理)医师人数</td>
							<td class="value"><#=health.doctor#></td>
						</tr>
						<tr>
							<td class="label">公共卫生厕所个数</td>
							<td class="value"><#=health.toilet#></td>
						</tr>
						<tr>
							<td class="label">生产生活垃圾集中堆放点</td>
							<td class="value"><#=health.garbage#></td>
						</tr>
					</table>
				</td>
				<td class="cell-block">
					<div class="header">贫困村信息化</div>
					<table class="content">
						<tr>
							<td class="label">通宽带户数</td>
							<td class="value"><#=information.broadband_family#></td>
						</tr>
						<tr>
							<td class="label">能手机上网的户数</td>
							<td class="value"><#=information.mobile_family#></td>
						</tr>
						<tr>
							<td class="label">通宽带的自然村数</td>
							<td class="value"><#=information.broadband_natural#></td>
						</tr>
						<tr>
							<td class="label">通宽带的村小学个数</td>
							<td class="value"><#=information.broadband_country#></td>
						</tr>
						<tr>
							<td class="label">行政村信息员</td>
							<td class="value"><#=information.admin_staff#></td>
						</tr>
					</table>
				</td>
				<td class="cell-block">
					<div class="header">易地扶贫搬迁</div>
					<table class="content">
						<tr>
							<td class="label">符合两不具备搬迁扶持条件，有搬迁意愿（2016-2018年）</td>
							<td class="value"><#=move.amount#></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="cell-block">
					<div class="header">雨露计划</div>
					<table class="content">
						<tr>
							<td class="label">初中毕业未升入高中的新劳动力</td>
							<td class="value"><#=rain.middle_school#></td>
						</tr>
						<tr>
							<td class="label">已参加雨露计划人数</td>
							<td class="value"><#=rain.joined#></td>
						</tr>
						<tr>
							<td class="label">高中毕业未升大学等的新劳动力</td>
							<td class="value"><#=rain.high_school#></td>
						</tr>
					</table>
				</td>
				<td class="cell-block">
					<div class="header">扶贫小额信贷</div>
					<table class="content">
						<tr>
							<td class="label">批复的扶贫小额信贷户数</td>
							<td class="value"><#=loan.allow#></td>
						</tr>
						<tr>
							<td class="label">当年贷款户数</td>
							<td class="value"><#=loan.cur_year#></td>
						</tr>
						<tr>
							<td class="label">逾期还款户数</td>
							<td class="value"><#=loan.due#></td>
						</tr>
					</table>
				</td>
				<td class="cell-block">
					<div class="header">党员情况</div>
					<table class="content">
						<tr>
							<td class="label">中共党员数</td>
							<td class="value"><#=party.amount#></td>
						</tr>
						<tr>
							<td class="label">党员占全村人口比例</td>
							<td class="value"><#=party.ratio#></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>
</script>
<script id="tplTabContentCountryPlan" type="text/html">
	<div class="page page-plan">
		<div class="plan-wrapper">
			<ul class="sub-tabs">
				<#for(var i = 0, len = plans.length; i < len; i++) {#>
				<li class="<#=i == 0 ? 'active' : ''#>" data-index="<#=i#>">
					<div class="tab"><#=#plans[i].tab_title#></div>
				</li>
				<#}#>
			</ul>
			<ul class="sub-tab-content">
				<#for(var i = 0, len = plans.length; i < len; i++) {#>
				<#var item = plans[i];#>
				<li class="<#=i == 0 ? 'active' : ''#>" data-index="<#=i#>">
					<div class="content-wrapper">
						<div class="header">
							<span>驻村干部签名：<#=item.summary.country_leader_sign#></span>
							<span>村委负责人签名：<#=item.summary.responsible_sign#></span>
							<span>制定时间：<#=item.summary.time#></span>
						</div>
						<div class="content">
							<#if(item.title){#>
								<h3 class="title"><#=item.title#></h3>
							<#}#>
							<div class="text">
								<#=#item.content#>
							</div>
						</div>
					</div>
				</li>
				<#}#>
			</ul>
		</div>
	</div>
</script>
<script id="tplTabContentCountryImplement" type="text/html">
	<div class="page page-implement">
		<div class="content-wrapper">
			<div class="stat">
				<div class="block projects">
					<div class="data">
						<p class="value"><span class="num"><#=summary.projects.total#></span><span>个</span></p>
						<p class="label">项目数</p>
					</div>
					<div class="data">
						<p class="value"><span class="num"><#=summary.projects.running#></span><span>个</span></p>
						<p class="label">进行中</p>
					</div>
					<div class="data">
						<p class="value"><span class="num"><#=summary.projects.completed#></span><span>个</span></p>
						<p class="label">已完成</p>
					</div>
				</div>
				<div class="block">
					<div class="data">
						<p class="value"><span class="num"><#=summary.invested#></span><span>万元</span></p>
						<p class="label">已投入资金</p>
					</div>
				</div>
				<div class="block">
					<div class="data">
						<p class="value"><span class="num"><#=summary.profit#></span><span>万元</span></p>
						<p class="label">项目收益</p>
					</div>
				</div>
			</div>
			<div class="table-wrapper">
				<#var tableList = table.list, tableTotal = table.total;#>
				<table class="table">
					<thead>
					<tr>
						<th>项目类别</th>
						<th>项目名称</th>
						<th>规模</th>
						<th>预计投入资金(元)</th>
						<th>实际投入资金(元)</th>
						<th>项目收益(元)</th>
						<th>开始时间</th>
						<th>完成时间</th>
						<th>项目照片</th>
					</tr>
					</thead>
					<tbody>
					<#if(tableList && tableList.length > 0){#>
						<#for(var i = 0, len = tableList.length; i < len; i++){#>
							<#var item = tableList[i];#>
							<tr>
								<td><#=item.type#></td>
								<td><#=item.name#></td>
								<td><#=item.size#></td>
								<td><#=item.invest_expected#></td>
								<td><#=item.invest_actual#></td>
								<td><#=item.profit#></td>
								<td><#=item.time_start#></td>
								<td><#=item.time_completed#></td>
								<td class="album">
									<#if(item.album){#>
										<div class="ico-pic" data-index="<#=i#>"></div>
									<#}#>
								</td>
							</tr>
						<#}#>
					<#}#>
					</tbody>
				</table>
				<!--
				<table class="table-empty">
					<tr>
						<td>目前没有已完成的项目</td>
					</tr>
				</table>
				--!>
			</div>
		</div>
	</div>
	<div class="page page-implement-album">
	</div>
</script>
<script id="tplTabContentCountryInvested" type="text/html">
	<div class="page page-invested">
		<div class="summary">
			<div class="block">
				<div class="data">
					<p class="value"><span class="num"><#=summary.total#></span><span>万元</span></p>
					<p class="label">累计到村资金</p>
				</div>
			</div>
			<div class="block">
				<div class="data">
					<p class="value"><span class="num"><#=summary.cur_year#></span><span>万元</span></p>
					<p class="label">当年到村资金</p>
				</div>
			</div>
		</div>
		<div class="table-section">
			<table class="table fixed">
				<thead>
				<tr>
					<th colspan="2">资金来源（单位：元）</th>
				</tr>
				<tr>
					<th colspan="2">单位自筹资金</th>
				</tr>
				<tr>
					<th rowspan="3">帮扶市资金</th>
					<th>地级市财政</th>
				</tr>
				<tr>
					<th>区级和镇级财政</th>
				</tr>
				<tr>
					<th>社会资金</th>
				</tr>
				<tr>
					<th rowspan="5">中央和省资金</th>
					<th>中央财政</th>
				</tr>
				<tr>
					<th>省级财政</th>
				</tr>
				<tr>
					<th>中央行业</th>
				</tr>
				<tr>
					<th>省级行业</th>
				</tr>
				<tr>
					<th>社会资金</th>
				</tr>
				<tr>
					<th rowspan="5">被帮扶市资金</th>
					<th>地级市财政</th>
				</tr>
				<tr>
					<th>县级和镇级财政</th>
				</tr>
				<tr>
					<th>地级市行业</th>
				</tr>
				<tr>
					<th>县级和镇级行业</th>
				</tr>
				<tr>
					<th>社会资金</th>
				</tr>
				<tr>
					<th colspan="2">小计</th>
				</tr>
				</thead>
			</table>
			<div class="table-float-wrapper">
				<div class="table-float">
					<table class="table float">
						<thead>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<th><#=table[i].title#></th>
							<#}#>
						</tr>
						</thead>
						<tbody>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].organization#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helper_city.city_finance#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helper_city.district_town_finance#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helper_city.social_money#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].central_province.central_finance#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].central_province.province_finance#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].central_province.central_industry#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].central_province.province_industry#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].central_province.social_money#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helped_city.city_finance#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helped_city.county_town_finance#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helped_city.city_industry#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helped_city.county_town_industry#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helped_city.social_money#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].summary#></td>
							<#}#>
						</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</script>

<!--户档案-->
<script id="tplFamilyFileBasic" type="text/html">
	<div class="btn-close"></div>
	<div class="file-left">
		<div class="file-title"><#=summary.house_holder_name#>户档案</div>
		<div class="house-holder">
			<div class="avatar">
				<img src="<#=summary.house_holder_avatar#>">
			</div>
			<div class="desc">
				<div class="ico-phone tipso" data-title="<#=summary.house_holder_phone#>"></div>
				<p>户主姓名：<#=summary.house_holder_name#></p>
				<p>贫困户属性：<#=summary.poor_attribute#></p>
				<p>致贫原因：<#=summary.poor_reason#></p>
				<p>期初年人均收入：<#=summary.start_income#></p>
			</div>
		</div>
		<div class="country-leader">
			<div class="avatar">
				<img src="<#=summary.country_leader_avatar#>">
			</div>
			<div class="desc">
				<div class="ico-phone tipso" data-title="<#=summary.country_leader_phone#>"></div>
				<p>帮扶责任人：<#=summary.country_leader_name#></p>
				<p>帮扶单位：<#=summary.country_leader_org#></p>
				<p>开始帮扶时间：<#=summary.country_leader_start_time#></p>
			</div>
		</div>
		<#if (status && status.code) { #>
			<div class="stamp <#=status.code == FAMILY_STAT.SUCCESS ? 'success' : 'error'; #>">
				<p class="stamp-date"><#=status.date#></p>
				<p class="stamp-name"><#=status.name#></p>
			</div>
		<# } #>
	</div>
	<div class="file-right">
		<div class="basic-stat">
			<div class="data data1">
				<div class="values">
					<div class="value"><span class="num"><#=stat.income#></span><span>元</span></div>
				</div>
				<div class="label">人均可支配收入</div>
			</div>
			<div class="data data2">
				<div class="values">
					<div class="value"><span class="num"><#=stat.edu_achieved#></span><span>人</span></div>
					<div class="value"><span class="num"><#=stat.edu_total#></span><span>人</span></div>
				</div>
				<div class="labels">
					<div class="label">实际</div>
					<div class="label">应享受义务教育</div>
				</div>
			</div>
			<div class="data data3">
				<div class="values">
					<div class="value"><span class="num"><#=stat.medical_security_achieved#></span><span>人</span></div>
					<div class="value"><span class="num"><#=stat.medical_security_total#></span><span>人</span></div>
				</div>
				<div class="labels">
					<div class="label">实际</div>
					<div class="label">应享受医疗保障</div>
				</div>
			</div>
			<div class="data data4">
				<div class="values">
					<#if(stat.is_danger_house){#>
					<div class="value">
						<span>危房</span><span class="num"><#=stat.house_danger_level#></span><span>级</span>
						<span><#=stat.has_house_become_safe?'已改造':'未完成改造'#></span>
					</div>
					<#}else{#>
					<div class="value">
					  <span>住房有保障</span>
					  </div>
					<#}#>
				</div>
				<div class="label">住房状况</div>
			</div>
			<div class="data data5">
				<div class="values">
					<div class="value"><span class="num"><#=stat.index#></span></div>
				</div>
				<div class="label">综合得分</div>
			</div>
			<div class="stamp <#=stat.has_achieved ? 'success' : ''#>"><#=stat.has_achieved ? '达标' : '未达标'#></div>
		</div>
		<div class="page-tabs">
			<ul>
				<li data-target="members">家庭成员</li>
				<li data-target="condition">生产生活条件</li>
				<li data-target="plan">帮扶计划</li>
				<li data-target="implement">帮扶施策</li>
				<li data-target="news">帮扶动态</li>
				<li data-target="invested">资金投入</li>
				<li data-target="income">可支配收入</li>
				<li data-target="eliminate_path">脱贫轨迹</li>
				<li data-target="data_path">数据轨迹</li>
				<li data-target="business">业务办理</li>
			</ul>
		</div>
		<div class="page-tab-content"></div>
	</div>
</script>
<script id="tplTabContentFamilyMembers" type="text/html">
	<div class="page page-members">
		<section class="member-section">
			<#var memberTable = member.table;#>
			<div class="section-title"><span>家庭成员 | <#=member.count#>人</span></div>
			<div class="member-table">
				<table class="table">
					<thead>
					<tr>
						<th>序号</th>
						<th>A1姓名</th>
						<th>A2性别</th>
						<th>证件类型</th>
						<th>年龄</th>
						<th>A4与户主关系</th>
						<th>A5民族</th>
						<th>A6政治面貌</th>
						<th>A7文化程度</th>
						<th>A8在校生情况</th>
						<th>A9健康状况</th>
						<th>A10劳动能力</th>
						<th>A11务工情况</th>
						<th>A12务工时间</th>
						<th>A13是否现役军人</th>
						<th>A14是否参加大病医疗保险</th>
						<th>入读技工院校意愿</th>
						<th>培训需求</th>
						<th>技能状况</th>
						<th>就业意向</th>
						<th>就业预期</th>
						<th>是否参加城镇职工基本养老保险</th>
						<th>是否参加城乡居民基本医疗保险</th>
						<th>是否参加城乡居民基本养老保险</th>
						<th>城乡居民基本养老保险个人缴费档次或目前领取养老金水平</th>
						<th>成员变更备注</th>
					</tr>
					</thead>
					<tbody>
						<#for(var i = 0, len = memberTable.length; i < len; i++) {#>
						<#var item = memberTable[i];#>
						<tr>
							<td><#=item.order#></td>
							<td><#=item.name#></td>
							<td><#=item.sex#></td>
							<td><#=item.credential_type#></td>
							<td><#=item.age#></td>
							<td><#=item.relationship#></td>
							<td><#=item.nationality#></td>
							<td><#=item.political_affiliation#></td>
							<td><#=item.education#></td>
							<td><#=item.at_school#></td>
							<td><#=item.health#></td>
							<td><#=item.labor_capacity#></td>
							<td><#=item.work_status#></td>
							<td><#=item.work_time#></td>
							<td><#=item.active_service#></td>
							<td><#=item.serious_illness_insurance#></td>
							<td><#=item.technical_school_willing#></td>
							<td><#=item.train_need#></td>
							<td><#=item.skill_status#></td>
							<td><#=item.employment_willing#></td>
							<td><#=item.employment_expectation#></td>
							<td><#=item.staff_pension_insurance#></td>
							<td><#=item.resident_medical_insurance#></td>
							<td><#=item.resident_pension_insurance#></td>
							<td><#=item.pension_level#></td>
							<td><#=item.comment#></td>
						</tr>
						<#}#>
					</tbody>
				</table>
			</div>
		</section>
		<section class="album-section">
			<#var albumList = album.list;#>
			<div class="section-title"><span>家庭相册 | <#=albumList.length#>张</span></div>
			<div class="member-album">
				<ul>
					<#for(var i = 0, len = albumList.length; i < len; i++) {#>
						<li><img data-index="<#=i#>" src="<#=albumList[i].src#>"></li>
					<#}#>
				</ul>
			</div>
		</section>
	</div>
	<div class="page page-members-album">
		<section class="album-section">
			<div class="section-title"><span><#=album.house_holder_name#>家庭相册</div>
			<div class="detail-album">
				<div class="ul owl-carousel owl-hidden">
					<#for(var i = 0, len = albumList.length; i < len; i++) {#>
					<div class="li item"><img src="<#=albumList[i].src#>"></div>
					<#}#>
				</div>
				<div class="pages"><span class="page-cur"></span>/<span class="page-total"><#=albumList.length#></span></div>
				<div class="btn-nav btn-prev"></div>
				<div class="btn-nav btn-next"></div>
			</div>
			<div class="btn-back">返回</div>
		</section>
	</div>
</script>
<script id="tplTabContentFamilyCondition" type="text/html">
	<div class="page page-condition">
		<section class="production-section">
			<div class="section-title"><span>生产条件</span></div>
			<ul>
				<li class="cultivated-land">
					<table class="card-table">
						<tr>
							<th rowspan="2">耕地面积</th>
							<td class="label">总数</td>
							<td class="value"><span class="num"><#=production.cultivated_land.total#></span><span>亩</span></td>
						</tr>
						<tr>
							<td class="label">有效</td>
							<td class="value"><span class="num"><#=production.cultivated_land.available#></span><span>亩</span></td>
						</tr>
					</table>
				</li>
				<li class="forest-land">
					<table class="card-table">
						<tr>
							<th rowspan="3">林地面积</th>
							<td class="label">总数</td>
							<td class="value"><span class="num"><#=production.forest_land.total#></span><span>亩</span></td>
						</tr>
						<tr>
							<td class="label">退耕还林</td>
							<td class="value"><span class="num"><#=production.forest_land.return#></span><span>亩</span></td>
						</tr>
						<tr>
							<td class="label">林果面积</td>
							<td class="value"><span class="num"><#=production.forest_land.fruit#></span><span>亩</span></td>
						</tr>
					</table>
				</li>
				<li class="grass-water">
					<table class="card-table">
						<tr>
							<th>牧草面积</th>
							<td class="label">总数</td>
							<td class="value"><span class="num"><#=production.grass_total#></span><span>亩</span></td>
						</tr>
						<tr>
							<th>水面面积</th>
							<td class="label">总数</td>
							<td class="value"><span class="num"><#=production.water_total#></span><span>亩</span></td>
						</tr>
					</table>
				</li>
			</ul>
		</section>
		<section class="living-section">
			<div class="section-title"><span>生活条件</span></div>
			<ul>
				<li class="living1">
					<table class="card-table no-label">
						<tr>
							<th>是否通生产用电</th>
							<td class="value"><span><#=living.production_electricity#></span></td>
						</tr>
						<tr>
							<th>是否通生活用电</th>
							<td class="value"><span><#=living.living_electricity#></span></td>
						</tr>
						<tr>
							<th>是否通饮水安全</th>
							<td class="value"><span><#=living.water_safety#></span></td>
						</tr>
					</table>
				</li>
				<li class="living2">
					<table class="card-table no-label">
						<tr>
							<th>住房面积平方米数</th>
							<td class="value"><span class="num"><#=living.house_area#></span><span>m</span><sup>2</sup></td>
						</tr>
						<tr>
							<th>与村主干路距离公里数</th>
							<td class="value"><span class="num"><#=living.distance_main_road#></span><span>公里</span></td>
						</tr>
						<tr>
							<th>入户路类型</th>
							<td class="value"><span><#=living.road_type#></span></td>
						</tr>
					</table>
				</li>
				<li class="living3">
					<table class="card-table no-label">
						<tr>
							<th>主要燃料类型</th>
							<td class="value"><span><#=living.fuel_type#></span></td>
						</tr>
						<tr>
							<th>有无卫生厕所</th>
							<td class="value"><span><#=living.toilet#></span></td>
						</tr>
						<tr>
							<th>是否加入农民专业合作社</th>
							<td class="value"><span><#=living.joined_fsc#></span></td>
						</tr>
					</table>
				</li>
			</ul>
		</section>
	</div>
</script>
<script id="tplTabContentFamilyPlan" type="text/html">
	<div class="page page-plan">
		<div class="plan-wrapper">
			<ul class="sub-tabs">
				<#for(var i = 0, len = plans.length; i < len; i++) {#>
				<li class="<#=i == 0 ? 'active' : ''#>" data-index="<#=i#>">
					<div class="tab"><#=#plans[i].tab_title#></div>
				</li>
				<#}#>
			</ul>
			<ul class="sub-tab-content">
				<#for(var i = 0, len = plans.length; i < len; i++) {#>
				<#var item = plans[i];#>
				<li class="<#=i == 0 ? 'active' : ''#>" data-index="<#=i#>">
					<div class="content-wrapper">
						<div class="header">
							<span>驻村干部签名：<#=item.summary.country_leader_sign#></span>
							<span>贫困户签名：<#=item.summary.house_holder_sign#></span>
							<span>制定时间：<#=item.summary.time#></span>
						</div>
						<div class="content">
							<#if(item.title){#>
								<h3 class="title"><#=item.title#></h3>
							<#}#>
							<div class="text">
								<#=#item.content#>
							</div>
						</div>
					</div>
				</li>
				<#}#>
			</ul>
		</div>
	</div>
</script>
<script id="tplTabContentFamilyImplement" type="text/html">
<div class="page page-implement">
	<div class="stat">
		<div class="block projects">
			<div class="data">
				<p class="value"><span class="num"><#=summary.projects.total#></span><span>个</span></p>
				<p class="label">项目数</p>
			</div>
			<div class="data">
				<p class="value"><span class="num"><#=summary.projects.running#></span><span>个</span></p>
				<p class="label">进行中</p>
			</div>
			<div class="data">
				<p class="value"><span class="num"><#=summary.projects.completed#></span><span>个</span></p>
				<p class="label">已完成</p>
			</div>
		</div>
		<div class="block">
			<div class="data">
				<p class="value"><span class="num"><#=summary.invested#></span><span>万元</span></p>
				<p class="label">已投入资金</p>
			</div>
		</div>
		<div class="block">
			<div class="data">
				<p class="value"><span class="num"><#=summary.profit#></span><span>万元</span></p>
				<p class="label">项目收益</p>
			</div>
		</div>
	</div>
	<div class="table-wrapper">
		<#var tableList = table.list, tableTotal = table.total;#>
		<table class="table">
			<thead>
			<tr>
				<th>扶贫类别</th>
				<th>实施对象</th>
				<th>项目名称</th>
				<th>数量</th>
				<th>预计投入资金(元)</th>
				<th>实际投入资金(元)</th>
				<th>项目收益(元)</th>
				<th>项目状态</th>
				<th>开始时间</th>
				<th>完成时间</th>
				<th>项目照片</th>
			</tr>
			</thead>
			<tbody>
			<#for(var i = 0, len = tableList.length; i < len; i++){#>
			<#var item = tableList[i];#>
			<tr>
				<td><#=item.type#></td>
				<td><#=item.object#></td>
				<td><#=item.name#></td>
				<td><#=item.amount#></td>
				<td><#=item.invest_expected#></td>
				<td><#=item.invest_actual#></td>
				<td><#=item.profit#></td>
				<td><#=item.status#></td>
				<td><#=item.time_start#></td>
				<td><#=item.time_completed#></td>
				<td class="album">
					<#if(item.album){#>
						<div class="ico-pic" data-index="<#=i#>"></div>
					<#}#>
				</td>
			</tr>
			<#}#>
			<!--
			<#if(tableTotal){#>
			<tr>
				<td colspan="4" class="txt-center">合计</td>
				<td><#=tableTotal.invest_expected#></td>
				<td><#=tableTotal.invest_actual#></td>
				<td><#=tableTotal.profit#></td>
				<td colspan="4"></td>
			</tr>
			<#}#>
			-->
			</tbody>
		</table>
	</div>
</div>
<div class="page page-implement-album">
</div>
</script>
<script id="tplTabContentFamilyInvested" type="text/html">
	<div class="page page-invested">
		<div class="summary">
			<div class="block">
				<div class="data">
					<p class="value"><span class="num"><#=summary.total#></span><span>万元</span></p>
					<p class="label">累计到户资金</p>
				</div>
			</div>
			<div class="block">
				<div class="data">
					<p class="value"><span class="num"><#=summary.cur_year#></span><span>万元</span></p>
					<p class="label">当年到户资金</p>
				</div>
			</div>
		</div>
		<div class="table-section">
			<table class="table fixed">
				<thead>
				<tr>
					<th colspan="2">资金来源（单位：元）</th>
				</tr>
				<tr>
					<th colspan="2">单位自筹资金</th>
				</tr>
				<tr>
					<th rowspan="3">帮扶市资金</th>
					<th>地级市财政</th>
				</tr>
				<tr>
					<th>区级和镇级财政</th>
				</tr>
				<tr>
					<th>社会资金</th>
				</tr>
				<tr>
					<th rowspan="5">中央和省资金</th>
					<th>中央财政</th>
				</tr>
				<tr>
					<th>省级财政</th>
				</tr>
				<tr>
					<th>中央行业</th>
				</tr>
				<tr>
					<th>省级行业</th>
				</tr>
				<tr>
					<th>社会资金</th>
				</tr>
				<tr>
					<th rowspan="5">被帮扶市资金</th>
					<th>地级市财政</th>
				</tr>
				<tr>
					<th>县级和镇级财政</th>
				</tr>
				<tr>
					<th>地级市行业</th>
				</tr>
				<tr>
					<th>县级和镇级行业</th>
				</tr>
				<tr>
					<th>社会资金</th>
				</tr>
				<tr>
					<th colspan="2">小计</th>
				</tr>
				</thead>
			</table>
			<div class="table-float-wrapper">
				<div class="table-float">
					<table class="table float">
						<thead>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<th><#=table[i].title#></th>
							<#}#>
						</tr>
						</thead>
						<tbody>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].organization#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helper_city.city_finance#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helper_city.district_town_finance#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helper_city.social_money#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].central_province.central_finance#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].central_province.province_finance#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].central_province.central_industry#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].central_province.province_industry#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].central_province.social_money#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helped_city.city_finance#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helped_city.county_town_finance#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helped_city.city_industry#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helped_city.county_town_industry#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].helped_city.social_money#></td>
							<#}#>
						</tr>
						<tr>
							<#for(var i = 0, len = table.length; i < len; i++){#>
							<td><#=table[i].summary#></td>
							<#}#>
						</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</script>
<script id="tplTabContentFamilyIncome" type="text/html">
	<div class="stat">
		<table class="table-meta">
			<tr>
				<td>
					<span class="label">总收入:</span>
					<span class="value"><#=stat.total_income#></span>
				</td>
				<td>
					<span class="label">总支出:</span>
					<span class="value"><#=stat.total_expense#></span>
				</td>
				<td>
					<span class="label">可支配收入:</span>
					<span class="value"><#=stat.disposable_income#></span>
				</td>
				<td>
					<span class="label">年人均可支配收入:</span>
					<span class="value"><#=stat.year_average_disposable_income#></span>
				</td>
			</tr>
		</table>
	</div>
	<div class="chart-section">
		<div class="chart"></div>
	</div>
	<div class="table-section">
	</div>
</script>
<script id="tplTabContentFamilyIncomeTable" type="text/html">
	<table class="table fixed">
		<colgroup>
			<col width="60">
			<col>
		</colgroup>
		<thead>
		<tr>
			<th colspan="2">项目（单位：元）</th>
		</tr>
		<tr>
			<th colspan="2">家庭人均可支配收入</th>
		</tr>
		<tr>
			<th colspan="2">家庭可支配收入</th>
		</tr>
		<tr>
			<th rowspan="13">收入</th>
			<th>总收入</th>
		</tr>
		<tr>
			<th>工资性收入</th>
		</tr>
		<tr>
			<th>生产经营性收入</th>
		</tr>
		<tr>
			<th>财产性收入</th>
		</tr>
		<tr>
			<th>转移性收入</th>
		</tr>
		<tr>
			<th>计划生育金</th>
		</tr>
		<tr>
			<th>低保金</th>
		</tr>
		<tr>
			<th>五保金</th>
		</tr>
		<tr>
			<th>养老保险金</th>
		</tr>
		<tr>
			<th>生态补偿金</th>
		</tr>
		<tr>
			<th>基本医疗保险报销医疗费</th>
		</tr>
		<tr>
			<th>医疗求助金</th>
		</tr>
		<tr>
			<th>其他转移性收入</th>
		</tr>
		<tr>
			<th rowspan="8">支出</th>
			<th>总支出</th>
		</tr>
		<tr>
			<th>生产经营性支出</th>
		</tr>
		<tr>
			<th>转移性支出</th>
		</tr>
		<tr>
			<th>个人所得税</th>
		</tr>
		<tr>
			<th>社会保障支出</th>
		</tr>
		<tr>
			<th>赡养支出</th>
		</tr>
		<tr>
			<th>其他转移性支出</th>
		</tr>
		<tr>
			<th>未偿还借贷款</th>
		</tr>
		</thead>
	</table>
	<div class="table-float-wrapper">
		<div class="table-float">
			<table class="table float">
				<thead>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<th><#=table[i].title#></th>
					<#}#>
				</tr>
				</thead>
				<tbody>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].average_disposable_income#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].family_disposable_income#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].income.total_income#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].income.salary_income#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].income.production_income#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].income.property_income#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].income.transferred_income#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].income.birth_subsidy#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].income.low_subsidy#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].income.five_subsidy#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].income.pension#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].income.env_subsidy#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].income.medical_insurance#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].income.medical_subsidy#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].income.other_transferred_income#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].expense.total_payment#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].expense.production_payment#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].expense.transferred_income#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].expense.individual_income_tax#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].expense.social_security_payment#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].expense.alimony#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].expense.other_transferred_payment#></td>
					<#}#>
				</tr>
				<tr>
					<#for(var i = 0, len = table.length; i < len; i++){#>
					<td><#=table[i].expense.unpaid_loan#></td>
					<#}#>
				</tr>
				</tbody>
			</table>
		</div>
	</div>
</script>

<!--Common-->
<script id="tplTabContentImplementAlbum" type="text/html">
	<section class="album-section">
		<div class="section-title"><span><#=name#>项目照片</span></div>
		<div class="detail-album">
			<div class="before">
				<#if(album.before){#>
					<img src="<#=album.before#>">
					<#}#>
						<div class="label">实施前</div>
			</div>
			<div class="during">
				<#if(album.during){#>
					<img src="<#=album.during#>">
					<#}#>
						<div class="label">实施中</div>
			</div>
			<div class="after">
				<#if(album.after){#>
					<img src="<#=album.after#>">
					<#}#>
						<div class="label">完成后</div>
			</div>
		</div>
		<div class="btn-back">返回</div>
	</section>
</script>
<script id="tplTabContentNews" type="text/html">
	<div class="page page-news">
		<div class="content-wrapper">
			<div class="timeline-wrapper">
				<div class="timeline">
					<#for(var i = 0, len = list.length; i < len; i++) {#>
					<#var item = list[i], pics = item.pics;#>
						<div class="timeline-item">
							<div class="time"><#=item.time#></div>
							<div class="item <#=(pics && pics.length > 0) ? '' : 'desc-only' #>" data-id="<#=item.id#>">
								<div class="desc"><#=item.desc#></div>
								<#if(pics && pics.length > 0){#>
									<ul class="album">
										<#for(var j = 0, jLen = pics.length; j < jLen; j++){#>
										<li><img src="<#=pics[j]#>"></li>
										<#}#>
									</ul>
									<#}#>
										<div class="link-more">更多&gt;</div>
							</div>
						</div>
						<#}#>
				</div>
			</div>
		</div>
	</div>
	<div class="page page-news-detail">
		<div class="detail-news">
			<div class="btn-back">返回</div>
			<div class="content-wrapper">
			</div>
		</div>
	</div>
</script>
<script id="tplTabContentNewsDetail" type="text/html">
	<div class="content">
		<div class="desc"><#=desc#></div>
		<div class="pics">
			<#for(var i = 0, len = pics.length; i < len; i++){#>
			<img src="<#=pics[i]#>">
			<#}#>
		</div>
	</div>
</script>
<script id="tplTabContentEliminatePath" type="text/html">
	<div class="page page-eliminate-path">
		<div class="page-wrapper">
			<div class="chart-section">
				<div class="section-title"><span>综合得分月趋势</span></div>
				<div class="chart"></div>
			</div>
			<div class="scores-section"></div>
		</div>
	</div>
</script>
<script id="tplTabContentFamilyEliminateScores" type="text/html">
	<div class="section-title"><span><#=time.year#>年<#=time.month#>月综合得分表现</span></div>
	<ul>
		<#for(var i = 0, len = scores.length; i < len; i++){#>
		<#var item = scores[i];#>
			<li class="<#=i % 2 == 0 ? 'odd' : ''#>">
				<div class="meta">
					<span class="index-name"><#=i + 1#>. <#=item.index_name#></span>
					<span class="weight">权重：<#=item.weight#>/得分：</span>
					<span class="num"><#=item.score#></span>
				</div>
				<div class="values">
					<div class="value actual">
						<div class="num"><#=item.value_actual#></div>
						<div class="label"><#=item.value_name#></div>
					</div>
					<div class="value target">
						<div class="num"><#=item.value_target#></div>
						<div class="label">
							<span>考核目标值</span>
							<div class="ico-quest">
								<div class="tip">
									<table>
										<tr>
											<td>指标描述：</td>
											<td><#=item.tips.desc#></td>
										</tr>
										<tr>
											<td>数据来源：</td>
											<td><#=item.tips.source#></td>
										</tr>
										<tr>
											<td>计算方法：</td>
											<td><#=item.tips.calculation#></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="stamp <#=item.has_achieved ? 'success' : ''#>"><#=item.has_achieved ? '达标' : '未达标'#></div>
			</li>
			<#}#>
	</ul>
</script>
<script id="tplTabContentDataPath" type="text/html">
	<div class="page page-data-path">
		<div class="content-wrapper">
			<div class="timeline-wrapper">
				<div class="timeline">
					<#if(create){#>
						<div class="timeline-item">
							<div class="time">创建</div>
							<div class="item desc-only">
								<div class="desc">
									<span class="desc-date"><#=create.date#></span>
									<span class="desc-time"><#=create.time#></span>
									<span class="desc-org"><#=create.publisher_organization#></span>
									<span class="desc-name"><#=create.publisher_name#></span>
								</div>
							</div>
						</div>
						<#}#>
							<#if(update){#>
								<#for(var i = 0, len = update.length; i < len; i++) {#>
								<#var item = update[i], list = item.list;#>
									<div class="timeline-item">
										<div class="time"><#=i == 0 ? '变更' : ''#></div>
										<div class="item">
											<div class="desc">
												<span class="desc-date"><#=item.date#></span>
												<span class="desc-time"><#=item.time#></span>
												<span class="desc-org"><#=item.publisher_organization#></span>
												<span class="desc-name"><#=item.publisher_name#></span>
											</div>
											<table class="table">
												<thead>
												<tr>
													<th>变更内容</th>
													<th>原有值</th>
													<th>变更值</th>
												</tr>
												</thead>
												<tbody>
												<#for(var j = 0, jLen = list.length; j < jLen; j++){#>
												<#var jItem = list[j];#>
													<tr>
														<td><#=jItem.name#></td>
														<td><#=jItem.old#></td>
														<td><#=jItem.new#></td>
													</tr>
													<#}#>
												</tbody>
											</table>
										</div>
									</div>
									<#}#>
										<#}#>
				</div>
			</div>
		</div>
	</div>
</script>
<script id="tplTabContentBusiness" type="text/html">
	<div class="page page-business">
		<div class="content-wrapper">
			<section>
				<#var listDoing = list_doing.list;#>
					<div class="section-title"><span>在办项目 <#=list_doing.amount_total#></span></div>
					<ul>
						<#for(var i = 0, len = listDoing.length; i < len; i++) {#>
						<#var item = listDoing[i];#>
						<li>
						<a href="<#=item.link#>" target="_blank"><#=item.text#></a>
						</li>
						<#}#>
					</ul>
					<#if(list_doing.amount_total > listDoing.length){#>
						<ul class="rest hide">
						</ul>
						<div data-type="doing" class="link-more">更多</div>
					<#}#>
			</section>
			<section>
				<#var listDone = list_done.list;#>
					<div class="section-title"><span>已办项目 <#=list_done.amount_total#></span></div>
					<ul>
						<#for(var i = 0, len = listDone.length; i < len; i++) {#>
						<#var item = listDone[i];#>
							<li>
								<a href="<#=item.link#>" target="_blank"><#=item.text#></a>
							</li>
							<#}#>
					</ul>
					<#if(list_done.amount_total > listDone.length){#>
						<ul class="rest hide">
						</ul>
						<div data-type="done" class="link-more">更多</div>
					<#}#>
			</section>
			<#if(list_todo){#>
				<section>
					<#var listTodo = list_todo.list;#>
						<div class="section-title"><span>待办项目 <#=list_todo.amount_total#></span></div>
						<ul>
							<#for(var i = 0, len = listTodo.length; i < len; i++) {#>
							<#var item = listTodo[i];#>
							<li>
							<a href="<#=item.link#>" target="_blank"><#=item.text#></a>
							</li>
							<#}#>
						</ul>
					<#if(list_todo.amount_total > listTodo.length){#>
						<ul class="rest hide">
						</ul>
						<div data-type="todo" class="link-more">更多</div>
					<#}#>
				</section>
			<#}#>
		</div>
	</div>
</script>
<script id="tplTabContentBusinessRest" type="text/html">
	<#for(var i = 0, len = list.length; i < len; i++) {#>
	<#var item = list[i];#>
	<li>
	<a href="<#=item.link#>" target="_blank"><#=item.text#></a>
	</li>
	<#}#>
</script>
<!-- 村、户档案 end -->


<!-- 文件号详情 -->
<div id="popupFileDetailWrap" class="pop-poor-monitor-second-wrap  popup-file-detail-wrap">
	<div id="popupFileDetail" class="pop-poor-monitor-second">
        <div class="btn-close js-second-popup-close"></div>
        <h2 id="popupFileDetailTitle" class="second-popup-header second-popup-title" >
        </h2>
        <div class="page-content-wrap">
        	<section id="fileDetaiMonitorContent" class="monitor-content">
        		<div id="fileDetailTable" class="file-detail-table">
        		</div>
        	</section>
        	<section id="fileDetaiMonitorTree" class="monitor-tree popup-monitor-tree">
	
        	</section>
        </div>
    </div>
</div>

<script id="tplPopupFileDetailTitle" type="text/html">
	<strong id="fileDetailFileName" data-file-id=<#=file_id#>><#=file_name#></strong>
	<div  class="select-box js-select-box download-select">
		<i class="icon icon-down"></i>
		<strong >文件佐证</strong>
		<i class="arrow"></i>
		<div class="select-list js-select-list" style="display: none; width: 120px;">
			<div>
				<ul>
				 <#for(var i=0;i < download_list.length;i++){#>
					<li class="" data-download-url=<#=download_list[i].download_url#> >
						<#=download_list[i].title#>
					</li>
				 <#}#>
				</ul>
			</div>
		</div>
	</div>
</script>
<!-- 文件号详情 end-->





