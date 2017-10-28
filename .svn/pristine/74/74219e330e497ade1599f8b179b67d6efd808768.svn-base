<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/fupin-taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>广东省新时期脱贫攻坚信息平台</title>
	<link rel="stylesheet" href="${ctxStatic}/themes/css/fupin/entrance.css">
</head>

<body>
<div class="main-bg">
	<div class="main-wrap">
		<div class="main-title"></div>
		<div class="btns-row clearfix">
			<div class="btns-wrap clearfix">
				<a class="btn-entrance" href="http://202.104.32.197:83/s/login/" target="_blank">建档立卡信息平台</a>
				<a class="btn-entrance" href="http://210.76.68.130" target="_blank">广东扶贫云</a>
				<a class="btn-entrance" href="http://www.gdfp.gov.cn/" target="_blank">广东扶贫信息网</a>
			</div>
		</div>
		<div class="sections clearfix">
			<section>
				<div class="section-title">广东省情</div>
				<div class="section-content">
					<p>广东省地处中国大陆最南部。东邻福建，北接江西、湖南，西连广西，南临南海。全省陆地面积为17.98万平方公里，约占全国陆地面积的1.87%。2016年末常住人口10999万人。全年实现地区生产总值79512.05亿元，同比增长7.5%。人均GDP达到72787元，全年粮食产量1360万吨，增长0.2%；经济作物播种面积和产量呈逐年上升态势，其中瓜类播种面积增长4.8%，中草药播种面积增长11.1%。水果产量平稳提升，增长3.9%。渔业经济稳定发展，水产品总量增长2.8%，其中水产品养殖比重达81.1%，同比提高0.6个百分点。全年农村常住居民人均可支配收入14512.2元，比上年增长8.6%；扣除价格因素，实际增长6.5%。农村居民消费支出中教育文化娱乐服务所占比重为8.5%。农村居民现住房建筑面积人均43.92平方米。农村最高20%收入组人均可支配收入30204元，最低20%收入组人均可支配收入5453元。</p>
					<p>在新时期精准扶贫精准脱贫开展中，全省各地、各部门动员了32.67万名干部进村入户，按照发布群众公开信、整村摸查、农户申请、群众评议、公示、镇县审核批准等程序，创新“四看、五优先、六进、七不进”、“四看、十不宜、五把关”、“十问精准识贫”、“看、访、听22333工作法”等方式方法，对相对贫困人口进行精准识别，截至2016年底，扶贫信息系统已录入相对贫困人口66.4万户、173.1万人，基本完成了相对贫困村、相对贫困人口的精准识别工作。在精准识别的基础上，全省各地整合梳理贫困人口基本信息，完成了分布在97个县市区（含90个建制县区、7个非建制区）、1112个乡镇（街道等）、16483条村的相对贫困人口建档立卡工作，基本做到“户有卡、村有册、镇有簿、市县有数据库、省有数据平台”。按照省公安厅农村户籍人口计算，全省的贫困发生率为4.55%（按照省统计局农村户籍人口统计口径计算，全省贫困发生率为4.34%）。相对贫困人口4万人以上（含4万人）的县（市、区）有10个，相对贫困人口5000人以上的乡镇有30个。全省相对贫困户主要致贫原因前5位分别是因病（36.2%）、缺劳力（23.3%）、因残（19.9%）、因学（4.9%）、缺资金（4.5%），全省有88.8％贫困人口因上述五大因素造成贫困，其它因素（因灾、因婚、缺土地、缺水、缺技术、交通不便等）占11.2％。</p>
				</div>
			</section>
			<section class="map">
				<div class="section-title">贫困分布</div>
				<div class="section-content">
					<div id="map_index" ></div>
					<div id="chart" class="chart"></div>
				</div>
			</section>
		</div>
		<div class="copyright-wrap">
			<div class="copyright">版权所有：广东省扶贫开发办公室&nbsp;&nbsp;&nbsp;&nbsp;技术支持：中国移动通信集团广东有限公司</div>
		</div>
		<div class="btns-download clearfix">
			<div class="btn-download android-app">
				<div class="ico-download-wrap">
					<div class="hint">
						<div class="qrcode" id="qrAndroidApp" data-src="http://210.76.68.6:8090/gdhelp/download/apk.action?id=1"></div>
						<p class="hint-name">下载扶贫云安卓客户端</p>
					</div>
					<i class="ico-download android-app"></i>
				</div>
			</div>
			<div class="btn-download wechat">
				<div class="ico-download-wrap">
					<div class="hint">
						<div class="qrcode"><img src="http://open.weixin.qq.com/qr/code/?username=gdsfpb"></div>
						<p class="hint-name">关注“广东省扶贫办”</p>
					</div>
					<i class="ico-download wechat"></i><span class="ico-label">二维码入口</span>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/fupin-taglib-body.jsp"%>
<script src="${ctxStatic}/js/modules/fupin/libs/qrcode.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin_strategy/entrance.min.js"></script>
<!-- map -->
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highcharts-4.1.5/highcharts.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highcharts-4.1.5/highcharts-more.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highcharts-4.1.5/modules/exporting.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highcharts-4.1.5/highcharts-zh_CN.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highcharts-4.1.5/modules/solid-gauge.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highmaps-1.1.7/modules/map.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highmaps-1.1.7/modules/data.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highmaps-1.1.7/modules/drilldown.min.js"></script>
 <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highmaps-1.1.7/themes/dark-unica.min.js"></script>

  <script type="text/javascript" src="${ctxStatic}/js/modules/script/common/common.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/common/layer/layer.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/common/common-utils.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/common/common-map.min.js"></script>

  <script type="text/javascript" src="${ctxStatic}/js/modules/script/charts/common-highcharts.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/charts/common-highmaps.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/charts/common-report-page.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/charts/Highcharts.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/charts/common-highcharts-map.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/maps/cn-guangdong.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/index_map_index.min.js"></script>
</body>
</html>
