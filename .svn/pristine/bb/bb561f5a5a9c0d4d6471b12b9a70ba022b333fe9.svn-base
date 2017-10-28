$(function() {
	
	
	$('#theme').combobox({
		onLoadSuccess:function (none){
			$('#theme').combobox("select",none[0].type);
		},
		onSelect:function(record){
			if(record){
				initCategoryTree(record.type);
			}
		}
	}); 
	
	
    $("#associationTable").datagrid({
        url : ctx + "/a/dataMapping/tableMappingAssociation/queryAssociations.do",
        rownumbers : true,
        fitColumns : true,
        striped : true,
        loadMsg : '数据加载中...',
        pagination : true,
        pageSize : '15',
        pageList : [ 10, 15, 20, 30, 40 ],
        border : true,
        onLoadSuccess:loadCss
    });
    
    function loadCss(data){
    	$(".delete_button").linkbutton({text:'删除',iconCls:'icon-btn-trash',plain:true});
    	$("#associationTable").datagrid('fixRowHeight');
    }
    
    var table_request = new Request("/a/dataMapping/tableMappingAssociation/queryTables.do");
    table_request.RefreshRequest(function(data) {
        $("#table1").combobox("loadData",data);
        $("#table2").combobox("loadData",data);
        $("#table1").combobox("select",'-999');
        $("#table2").combobox("select",'-999');
    });
});

function initCategoryTree(type){
	var params={};
	params.type=type;
	$.ajax({
        type: "POST",
        url: ctx + "/a/dataMapping/tableMappingAssociation/queryTableMappingTree.do",
        data: params,
        cache:true,
        dataType: "json",
        success: function(treeData){
        	  $.fn.zTree.init($("#sourceTableTree"), setting, treeData);
        	  $.fn.zTree.init($("#mapingTableTree"), setting, treeData);
        }
    });
	
	
	
	
	
}





var setting = {
    edit: {
        enable: true,
        showRemoveBtn: false,
        showRenameBtn: false
    },
    async: {
        enable: true,
        url:ctx + "/a/dataMapping/tableMappingAssociation/queryTableColums.do",
        autoParam:["id"],
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeDrag: beforeDrag,
        beforeDrop: beforeDrop,
        beforeAsync: beforeAsync
    }
};

function beforeDrag(treeId, treeNodes) {
    if('mapingTableTree' == treeId) {
        return false;
    }
    for (var i=0,l=treeNodes.length; i<l; i++) {
        if (treeNodes[i].drag === false) {
            return false;
        }
    }
    return true;
}
function beforeDrop(treeId, treeNodes, targetNode, moveType) {
    if(null != targetNode && 'mapingTableTree' == treeId) {
        if(!targetNode.isParent) {
            var str = treeNodes[0].id + "\t" + targetNode.id;
            var param = {};
            param.sourceId = treeNodes[0].id;
            param.targetId = targetNode.id;
            common.utils.showLoading();
            var addAssociation = new Request("/a/dataMapping/tableMappingAssociation/addAssociation.do",param);
            addAssociation.RefreshRequest(function(data){
                common.utils.closeLoading();
                if(data.success) {
                    common.utils.showSucMsg(data.msg);
                    $("#associationTable").datagrid("reload");
                    //window.scrollTo(0, document.documentElement.scrollHeight-document.documentElement.clientHeight);
                }else {
                    common.utils.showErrorMsg(data.msg);
                }
            });

        }

    }
    return false;
    //return targetNode ? targetNode.drop !== false : true;
}

function beforeAsync(treeId, treeNode) {
    if(undefined == treeNode.mpTableName) {
        return false;
    }
    return true;
}

function search() {
    var param = {};
    param.table1 = $('#table1').combogrid('getValue');
    param.table2 = $("#table2").combobox('getValue');
    $("#associationTable").datagrid("reload",param);
}

function formatOper(val, row, index) {
    var mt1 = row["mt1"];
    var mt2 = row["mt2"];
    var tn1 = row["tn1"];
    var tn2 = row["tn2"];
    var btns = "<a href=\"javascript:deleteAssociation('"+ mt1 + "','"+ mt2 + "','"+ tn1
            +"','" + tn2 + "');\" class=\"delete_button\">删除</a>";
    return btns;
}

function deleteAssociation(mt1,mt2,tn1,tn2) {
    layer.confirm('是否需要删除“' + tn1 + '”和“' + tn2 + '”的关联关系？', {
        icon : 3,
        btn : [ '删 除', '取 消' ],
        shade : 0.2
    }, function(index) {
        common.utils.showLoading();
        var params = {};
        params.source = mt1;
        params.target = mt2;
        var delete_request = new Request("/a/dataMapping/tableMappingAssociation/deleteAssociation.do",params);
        delete_request.RefreshRequest(function(data) {
            common.utils.closeLoading();
            if(data.success) {
                common.utils.showSucMsg(data.msg);
                $("#associationTable").datagrid("reload");
            }else {
                common.utils.showErrorMsg(data.msg);
            }
        });
    });
}
