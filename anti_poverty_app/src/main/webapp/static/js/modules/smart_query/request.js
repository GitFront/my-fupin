var Request = function(url,data) {
	this.url = url;
	this.data = data;

	this.RefreshRequest = function(callbacks) {
		$.ajax({
			type:'post',
			dataType:'json',
			data :this.data,
			url: ctx + this.url,
			success: function (data) {
				callbacks && $.isFunction(callbacks) && callbacks(data);
			}
		});
	}
}