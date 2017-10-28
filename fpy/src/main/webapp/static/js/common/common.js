String.prototype.trim = function() {
	//var reExtraSpace = /^\s*(.*?)\s+$/;
	//return this.replace(reExtraSpace, "$1");
	var reExtraSpace = /(^\s*)|(\s*$)/g;
	return this.replace(reExtraSpace, "");
};
/**
 * 检查数组中是否还有指定目标对象
 * @param {Object} o 目标对象
 * @return {Boolean}
 */
Array.prototype.contains = function(o) {
	return this.indexOf(o) != -1;
};

/**
 * 根据下标删除元素
 */ 
Array.prototype.remove = function(index) { 
    if(isNaN(index)||index>this.length){return false;} 
    this.splice(index,1); 
}

/**
 * 根据元素删除元素
 */
Array.prototype.removeByVal=function(val) { 
	if(!val){return false;}
 	for(var i = 0 ; i < this.length ; i++){
		var value = this[i];
		if(val == value){
			this.remove(i); 
			i--;
 		}
	}
}
 

/**
 * 检查字符串中是否含有目标字符串
 * @param {String} substr 目标字符串
 * @return {Boolean}
 */
String.prototype.contains = function(substr) {
	return this.indexOf(substr) != -1;
};
/**
 * 替换所有参数
 * @param s1
 * @param s2
 * @returns
 */
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
};

/**
 * 检查字符串是否以指定目标字符串开始
 * @param {String} str 目标字符串
 * @return {Boolean}
 */
String.prototype.startsWith = function(str) {
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substr(0,str.length)==str)
	  return true;
	else
	  return false;
	return true;     
};

/**
 * 检查字符串是否以指定目标字符串结尾
 * @param {String} str 目标字符串
 * @return {Boolean} 是否以指定字符串结尾
 */
String.prototype.endsWith = function(str) {
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substring(this.length-str.length)==str)
	  return true;
	else
	  return false;
	return true;
};

/**
 * 日期属性操作
 * @param {} fmt
 * @return {}
 */
Date.prototype.format = function(fmt) 
{
    var o =
    { 
        "M+" : this.getMonth() + 1, 
        "d+" : this.getDate(), 
        "h+" : this.getHours(), 
        "m+" : this.getMinutes(), 
        "s+" : this.getSeconds(), 
        "q+" : Math.floor((this.getMonth() + 3) / 3), 
        "S" : this.getMilliseconds() 
    }; 
    if (/(y+)/.test(fmt)) 
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length)); 
    for (var k in o) 
        if (new RegExp("(" + k + ")").test(fmt)) 
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length))); 
    return fmt; 
}
Date.prototype.addDays = function(d)
{
    this.setDate(this.getDate() + d);
};
Date.prototype.addWeeks = function(w)
{
    this.addDays(w * 7);
};
Date.prototype.addMonths= function(m)
{
    var d = this.getDate();
    this.setMonth(this.getMonth() + m);
    if (this.getDate() < d)
        this.setDate(0);
};
Date.prototype.addYears = function(y)
{
    var m = this.getMonth();
    this.setFullYear(this.getFullYear() + y);
    if (m < this.getMonth()) 
    {
        this.setDate(0);
    }
};


Common = {
		init : function(){

		},		
		resetWindow : function(div){
			$("#" + div).find(":input").val("");
			$("#" + div).find(":input")
					.not(":button, :submit, :reset, :hidden, :radio")
					.removeAttr("checked").removeAttr("selected");
			$("#" + div).find(".easyui-combobox").combobox("clear");
			$("#" + div).find(".easyui-combotree").combotree("clear");
			$("#" + div).find(".easyui-datetimebox").combobox("clear");
			$("input[type='checkbox']").attr("checked", false);
			$("#bank_box_saveOrUpdate").unbind('click');
			$("#device_ping_ok").unbind('click');
			$("#device_trace_ok").unbind('click');
			$("#device_snmp_start").unbind('click');
			
			// 清空dataGrid中的数据
			try{
				$("#device_snmp_table").datagrid('loadData',{total:0,rows:[]});
			}catch(e){};
		},
		
		resetForm : function(){
			$(":input").not(":button, :submit, :reset, :radio").val("")
			.removeAttr("checked").removeAttr("selected");
			$(".easyui-combobox").combobox("clear");
			$(".easyui-combotree").combotree("clear");
			$(".easyui-datetimebox").combobox("clear");
		},
		
		getFormValues : function(div) {
			var s = {};
			var temp = $("#" + div).find(":input");
			temp.each(function() {
				var temp = $(this).attr("name");
				var s_ = $(this).val();
				if(s_!=null)
				s_ = Common.delHtml(s_);
				if (temp != null && undefined != temp) {
					s[temp] = s_;
				}
			});
			return s;
		},
		
		setFormValues :function(div, data) {
			for (var t in data) {
				var temp = $("#" + div).find("input[name='" + t + "']");
				if (temp != null && temp != undefined) {
					temp.val(data[t]);
					try {
						$('#' + t + '_box').combobox('setValue', data[t]);
						$('#' + t + '_treebox').combotree('setValue', data[t]);
					} catch (e) {
					}
				}
			}
		},
		
		
		createComboBox : function (inputId,width){
				$("#"+inputId).combobox({
				data : data,
				width :width,
				editable: false,
				valueField: 'value',
				textField: 'text'
			});
		},
		
		delHtml:function (Word) {
			a = Word.indexOf("<");
			b = Word.indexOf(">");
			len = Word.length;
			c = Word.substring(0, a);
			if(b == -1)
			b = a;
			d = Word.substring((b + 1), len);
			Word = c + d;
			tagCheck = Word.indexOf("<");
			if(tagCheck != -1)
				Word = Common.delHtml(Word);
			return Word;
			}

};

