<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/jsp/public/commons.jspf"%>
    <meta charset="UTF-8">
    <title>DataGrid with Toolbar - jQuery EasyUI Demo</title>
</head>
<body>
    <h2>DataGrid with Toolbar</h2>
    <p>Put buttons on top toolbar of DataGrid.</p>
    <div style="margin:20px 0;"></div>
    
    <!-- 显示查询条件 -->
    <div id="tb" style="padding:3px">
		<span>ID:</span>
		<input id="userid" style="line-height:26px;border:1px solid #ccc">
		<span>loginName:</span>
		<input id="loginName" style="line-height:26px;border:1px solid #ccc">
		<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">查询</a>
	</div>
    
    <!-- 显示数据表格 -->
    <table id="dategrid"></table>
    
<script type="text/javascript">

$(function() {
	//定义表格上的工具条
	var toolbar = [ {
		text : 'Add',
		iconCls : 'icon-add',
		handler : function() {
			alert('add');
		}
	}, {
		text : 'Cut',
		iconCls : 'icon-cut',
		handler : function() {
			alert('cut');
		}
	}, '-', {
		text : 'Save',
		iconCls : 'icon-save',
		handler : function() {
			alert('save');
		}
	} ];

	//加载datagrid数据表格
	$('#dategrid').datagrid({
		url : 'user_list.action',
		columns:[[
			{field:'id',title:'id',width:80, sortable: true},//sortable: true,允许点击表头排序
			{field:'loginName',title:'loginName',width:80, sortable: true},
			{field:'email',title:'email',width:80, sortable: true},
		]],
		toolbar : toolbar,
		resizeable: true,//宽度可调整
		fitColumns: true,//自适应表格宽度
		pageSize : 5,//默认选择的分页是每页5行数据  
        pageList : [ 5, 10, 15, 20 ],//可以选择的分页集合
        striped : true,//设置为true将交替显示行背景
        collapsible : true,//显示可折叠按钮  
        sortable: true,//可以对列排序
      	sortName : 'id',//当数据表格初始化时以哪一列来排序  
        sortOrder : 'asc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。  
        pagination : true,//分页  
        rownumbers : true//行数 

	});
});

//表格的查询
function doSearch(){
	//根据查询条件，重新加载表格
	$('#dategrid').datagrid('load',{
		id: $('#userid').val(),
		loginName: $('#loginName').val()
	});
}
</script>
</body>
</html>