$(document).ready(function() {
	$("#configTable").datagrid({
		url : ctx + "/a/smart-query/task/tasklist.do",
		rownumbers : true,
		striped : true,
		loadMsg : '数据加载中...',
		pagination : true,
		pageSize : '15',
		pageList : [ 10, 15, 20, 30, 40 ],
		border : true,
		idField : 'id',
		onLoadSuccess : loadCss
	});

	$("#close_exception_win").on("click",function(){
		$("#his_exception_Win").window('close');
	});

	$("#save_config_btn").on("click",function(){
		common.utils.showLoading();
		/* 获取表单判断数据 */
		var id = $("#id").val();
		var name = $("#task_name").val();
		var isOpen = $("#isOpen").linkbutton('options').selected;

		/* 验证查询配置名称是否唯一 */
		var url = ctx + "/a/smart-query/query/smartquery/valid/name";

		var validName = Boolean($.ajax({ url: url,data: {id:id,name:name}, type: "POST", async: false }).responseText);
		if(!validName) {
			common.utils.closeLoading();
			common.utils.showErrorMsg("配置名称已存在，请使用其它名称对此查询配置进行标识！");
			return;
		}

		$("#saveConfig").form('submit', {
			url:ctx + "/a/smart-query/task/updateSqQueryInfo.do",
			onSubmit: function(params){
				var isValid = $(this).form('validate');
				if (isOpen && !isValid){
					common.utils.closeLoading();//如果表单是无效的则隐藏进度条
					common.utils.showErrorMsg("请将查询配置信息的必填项补充完整！");
					return isValid
				}
				params.configType = isOpen?"1":"0";
				return true;
			},
			success: function(rs){
				common.utils.closeLoading();
				var data = $.parseJSON(rs);
				if(data.flag){
					$('#saveConfigWin').dialog('close');
					common.utils.showSucMsg("查询配置保存成功！");
					$("#configTable").datagrid("reload");
				}else{
					common.utils.showErrorMsg("查询配置保存失败，请稍候再试！");
				}
			}
		});
	});

	$("#personal_catalog_btn").on("click",function(){
		$('#editPathWin').dialog('open');
	});

	$("#choose_personal_catalog_btn").on("click",function(){
		var treeObj = $.fn.zTree.getZTreeObj("pathTree");
		var select = treeObj.getSelectedNodes();

		if(!select || select.length === 0) {
			common.utils.showErrorMsg("必须先选择一个文件保存的目录！");
			return;
		}
		/* 获取选中树节点 */
		var selected = select[0].id;
		/* 更新下拉选择树 */
		$('#personalCatalogId').combotree('reload');
		$('#personalCatalogId').combotree('setValue',selected);
		$('#editPathWin').dialog('close');
	});
	initPersonalCatalog();
});

function showException(name,btn_id) {
	$("#his_exception_Win").window('center');
	$("#his_exception_Win").window('open');
	var param = btn_id.substr(9);
	$("#exception_logs_table").datagrid({
		url :ctx + "/a/smart-query/task/queryTaskException.do",
		queryParams:{index:param},
		rownumbers : true,
		fitColumns : true,
		striped : true,
		loadMsg : '数据加载中...',
		pagination : true,
		pageSize : '15',
		pageList : [ 10, 15, 20, 30, 40 ],
		border : true
	});
}

function deleteInfo(name,btn_id) {
	layer.confirm('是否需要删除“' + name + '”这个配置文件？', {
		icon : 3,
		btn : [ '删 除', '取 消' ],
		shade : 0.2
	}, function(index) {
		var param = btn_id.substr(9);
		$.ajax({
			type:'post',
			dataType:'json',
			data :{index:param},
			url: ctx + "/a/smart-query/task/deleteTask.do",
			success: function (data) {
				layer.close(index);
				if(data.success) {
					$("#configTable").datagrid("reload");
				}else {
					layer.msg("删除“" + name + "”任务失败", {
						shade : 0.2
					});
				}
			}
		});
	});
}

function editInfo(name,btn_id,type) {
	var index = btn_id.substr(9);
	$("#task_name").textbox('setValue',name);
	$("#id").val(index);
	if('0' == type) {
		resetConfigWin();
		$("#isOpen").linkbutton('unselect');
		$("#timer").hide();
		editConfig();
	}else {
		$("#isOpen").linkbutton('select');
		$("#timer").show();
		var url = "/a/smart-query/task/queryInfo.do";
		var index = btn_id.substr(9);
		var param = {index : index};
		var modify_request = new Request(url,param);
		modify_request.RefreshRequest(function(data) {
			$("#fileName").textbox('setValue',data.fileName);
			$("#dataStoreDate").numberspinner('setValue',data.dataStoreDate);
			$("#personalCatalogId").combotree('setValue',data.personalCatalogId);
			$("#validDate").datebox('setValue',data.validDate);
			$("#invalidDate").datebox('setValue',data.invalidDate);
			$("#cycType").combobox('setValue',data.cycType);
			$("#cycLen").numberspinner('setValue',data.cycLen);
			editConfig();
		});
	}
}

function timerSwitch(){
	var isOpen = !$("#isOpen").linkbutton('options').selected;
	if(isOpen){
		$("#timer").show();
		$('#saveConfigWin').dialog('resize');
	}else{
		$("#timer").hide();
		$('#saveConfigWin').dialog('resize');
	}
}

function editConfig(){
	$('#saveConfigWin').dialog('open');
	$("#saveConfigWin").panel("move",{top:$(document).scrollTop()});
	$('#saveConfigWin').dialog('resize');
}

function doTask(btn_id, name) {
	layer.msg("正在更新“" + name + "”任务状态....", {
		shade : 0.2
	});
	var index = btn_id.substr(9);
	
 	$.ajax({
		type:'post',
		dataType:'json',
		data :{index:index},
		url: ctx + "/a/smart-query/task/startOrStopTask.do",
		success: function (data) {
			if(data.success) {
				$("#configTable").datagrid("reload");
			}else {
				layer.msg("更新“" + name + "”任务状态失败", {
					shade : 0.2
				});
			}
		}
	});
}

function formatOper(val, row, index) {
	var name = row["name"];
	var type = row["type"];
	var id = row["id"];
	var status = row["status"];
	var btn_id = "do_button" + id;
	var btns = "";
	if (type === '1') {
		if (status === '1') {
			btns = "<a id='" + btn_id
					+ "' href=\"javascript:void(0);\" onclick=\"doTask('"
					+ btn_id + "','" + name + "')\" class=\"do_stop\">暂 停</a>"
		} else if (status === '0') {
			btns = "<a id='" + btn_id
					+ "' href=\"javascript:void(0);\" onclick=\"doTask('"
					+ btn_id + "','" + name + "')\" class=\"do_start\">启 动</a>"
		}
	}
	btns += "<a href=\"javascript:void(0);\" onclick=\"editInfo('" + name
			+ "','"+ btn_id + "','" + type + "')\" class=\"edit_button\">修 改</a>"
	btns += "<a href=\"javascript:void(0);\" onclick=\"deleteInfo('" + name
			+ "','" + btn_id + "')\" class=\"delete_button\">删 除</a>";
	btns += "<a href=\"javascript:void(0);\" onclick=\"showException('" + name
		+ "','" + btn_id + "')\" class=\"view_exception_button\">查看历史异常</a>";
	return btns;

}

function loadCss(data) {
	$(".do_stop").linkbutton({
		text : '暂停',
		iconCls : 'icon-btn-pause',
		plain : true
	});
	$(".do_start").linkbutton({
		text : '启动',
		iconCls : 'icon-btn-play',
		plain : true
	});
	$(".edit_button").linkbutton({
		text : '修改',
		iconCls : 'icon-btn-wrench',
		plain : true
	});
	$(".delete_button").linkbutton({
		text : '删除',
		iconCls : 'icon-btn-trash',
		plain : true
	});
	$(".view_exception_button").linkbutton({
		text : '查看历史异常',
		iconCls : 'icon-btn-refresh',
		plain : true
	});
	$(".easyui-tooltip").each(function() {
		$(this).tooltip();
	});

	$("#configTable").datagrid('fixRowHeight');

}

$(function() {
	$('#user').combogrid({
		panelWidth : 400,
		idField : 'id',
		textField : 'name',
		url : ctx + "/a/smart-query/task/queryCreator.do",
		columns : [ [ {
			field : 'id',
			title : '编号',
			width : 80
		}, {
			field : 'name',
			title : '名称',
			width : 80
		}, {
			field : 'dept',
			title : '所属部门',
			width : 220
		} ] ]
	});
});

function search() {
	layer.msg("查询数据...", {
		shade : 0.2
	});
	var param = {};
	param.user = $('#user').combogrid('getValue');
    param.type = $("#type").combobox('getValue');
	param.name = $("#name").textbox('getValue');
	$("#configTable").datagrid("reload",param);
}

function transformTreeData(nodes) {
	var ct_nodes = new Array();

	if (!nodes || nodes.length === 0)
		return ct_nodes;

	function forChildren(node) {
		var childnodes = new Array();
		var child = node.children;
		if (child && child.length > 0) {
			for (var int = 0; int < child.length; int++) {
				var ct_child = {
					id : child[int].id,
					text : child[int].name,
					children : forChildren(child[int])
				};
				childnodes.push(ct_child);
			}
		}
		return childnodes;
	}

	for (var i = 0; i < nodes.length; i++) {
		var ct_node = {
			id : nodes[i].id,
			text : nodes[i].name,
			children : forChildren(nodes[i])
		};
		ct_nodes.push(ct_node);
	}

	return ct_nodes;
}

$(function() {
	$("#gap").combobox({
		onSelect : function(record) {
			switch (record.value) {
			case "day":
				$("#num").numberspinner({
					min : 1,
					max : 366
				});
				$("#gapinfo").slider({
					disabled : true
				});
				break;
			case "week":
				$("#num").numberspinner({
					min : 1,
					max : 48
				});
				$("#gapinfo").slider({
					disabled : false,
					rule : [ '一', '二', '三', '四', '五', '六', '日' ],
					min : 1,
					max : 7,
					tipFormatter : function(value) {
						var value_ = value;
						switch (value) {
						case 1:
							value_ = "星期一";
							break;
						case 2:
							value_ = "星期二";
							break;
						case 3:
							value_ = "星期三";
							break;
						case 4:
							value_ = "星期四";
							break;
						case 5:
							value_ = "星期五";
							break;
						case 6:
							value_ = "星期六";
							break;
						case 7:
							value_ = "星期日";
							break;

						default:
							break;
						}
						return value_;
					}

				});
				break;
			case "month":
				$("#num").numberspinner({
					min : 1,
					max : 12
				});
				$("#gapinfo").slider({
					disabled : false,
					min : 1,
					max : 31,
					rule : [ 1, '|', 10, '|', 20, '|', 31 ],
					tipFormatter : function(value) {
						return value + "号";
					}
				});
				break;
			default:
				break;
			}
		}
	});
});

function resetConfigWin() {
	$("#fileName").textbox('setValue','');
	$("#dataStoreDate").numberspinner('setValue','');
	$("#personalCatalogId").combotree('setValue','');
	$("#validDate").datebox('setValue','');
	$("#invalidDate").datebox('setValue','');
	$("#cycType").combobox('setValue','');
	$("#cycLen").numberspinner('setValue','');
}

function initPersonalCatalog(){
	/* 加载目录数据 */
	var opt = {
		async:true,/* 是否异步获取节点数据*/
		url:ctx + "/a/smart-query/query/personalcatalog/list/ztree", /*服务器访问地址*/
		mapper:{id:'id',name:'name',pId:'parentId',nfullPath:"fullPath"}, /*字段对应关系*/
		newRootName:"个人主目录",
		newEditName:"新文件夹", /* 新增节点默认名称*/
		isExpand:true, /*是否默认展开全部树节点*/
		showFolder:true
	};
	var callback = {
		noNewRoot:"addDefaultPath",
		onNew:"addChildPath",
		onRename:"renamePath",
		onRemove:"removePath",
		beforeRemove:"beforeRemovePath"
	};
	personalTree = new zEditTree("pathTree",[],{},callback,opt);
}