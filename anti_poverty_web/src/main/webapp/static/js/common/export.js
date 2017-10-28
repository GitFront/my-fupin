function exportExcel(id,title,url){
		url=url==undefined ?'/a/common/export/exportGridData':url;
		var url=ctx+url;
		var chartid = common.report.page.reportmap[id];
		var obj = common.report.highcharts.object.get(chartid);
		var params=obj.parameters.param;
		params.queryName=obj.parameters.queryName;
		var keys='';
		var headers='';
		var columns=$("#"+id).datagrid('options').columns[0];
		$.each(columns,function(i,column){
			keys+=column.field +','
			headers+=column.title +','
		})
		params.keys=keys;
		params.headers=headers;
		params.title=title;
		
		sysAjaxDownload(url, params, 'post');

}
// Ajax 文件下载
// 用jquery的方式组织一个字符串，模拟提交一个form请求。动态渲染表单，提交表单后再删除。
function sysAjaxDownload(url, data, method) {
	// 获取url和data
	if (url && data) {
		// data 是 string 或者 array/object
		data = typeof data == 'string' ? data : jQuery.param(data);
		// 把参数组装成 form的 input
		var inputs = '';
		jQuery.each(data.split('&'), function() {
			var pair = this.split('=');
			inputs += '<input type="hidden" name="' + pair[0] + '" value="'
					+ decodeURIComponent(pair[1]) + '" />';
		});
		// request发送请求
		jQuery(
				'<form action="' + url + '" method="' + (method || 'post')
						+ '">' + inputs + '</form>').appendTo('body').submit()
					.remove();
		}
};