<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/jsp/public/commons.jspf"%>
    <meta charset="UTF-8">
    <title>User List</title>
    <style type="text/css">
    	.f1{
            width:200px;
        }
        
	table.view {border:1px solid #A8CFEB;border-collapse: collapse;margin-bottom:5px;}
	.view th{ padding: 5px; height:23px; width: 150px; border: 1px solid silver; background-color:#F1F6FF; 
		font-weight: normal; text-align: right;}
	.view td{ padding:5px;height:23px;width: 150px;border: 1px solid silver;background-color:#FAFCFF; }
    </style>
    
</head>
<body>
<!-- ========查询条件========== -->
	<div class="easyui-panel" title="查询条件" style="width:900px;padding:5px;">
	    <table id="searchTable">
	        <tr>
	            <td>用户id:</td>
	            <td><input name="id" id="userid" class="f1 easyui-textbox" /></td>
	            <td>用户名loginName:</td>
	            <td><input name="loginName" id="loginName" class="f1 easyui-textbox" /></td>
	            <td>邮箱Dmail:</td>
	            <td><input name="email" id="email" class="f1 easyui-textbox" /></td>
	        </tr>
	        <tr>
	            <td>真实姓名:</td>
	            <td><input name="realName" id="realName" class="f1 easyui-filebox"></input></td>
	            <td>其他条件:</td>
	            <td><input name="file" class="f1 easyui-filebox"></input></td>
	        </tr>
	        <tr>
	            <td></td><!-- 空填充，保持格式对齐，不要删除 -->
	            <td>
	            	 <a href="javascript:void(0)" onclick="doSearch()" class="easyui-linkbutton" 
	            	 	data-options="iconCls:'icon-search'" style="width:80px">查询</a>
	            	 <a href="javascript:void(0)" onclick="doReset()" class="easyui-linkbutton" 
	            	 	data-options="iconCls:'icon-remove'" style="width:80px">重置</a>
	            </td>
	        </tr>
	    </table>
    </div>
    <div style="height: 6px;bbackground-color:#BBD2E9;"></div>
	<div class="easyui-panel" title="${jspGridTitle}" style="width:900px;padding:0px;"></div>
	
<!-- =========显示数据表格======== -->
	<table id="dategrid"></table>
	
<!-- =========显示弹窗======== -->
	<div id="dialog"></div>

<script type="text/javascript">
	$(function() {
		//定义表格上的工具条
		var toolbar = [ 
			{text : 'Add', iconCls : 'icon-add', handler : addUser}//添加用户
		];

		//加载datagrid数据表格
		$('#dategrid').datagrid({
			url : 'user_list.action',
			columns : [ [ 
				{field : 'ck', checkbox: true},//定义多选框 checkbox
				{field : 'id', title : '用户id', width : 80, sortable : true},//sortable: true,允许点击表头排序
				{field : 'loginName', title : '用户名', width : 80, sortable : true}, 
				{field : 'email', title : '邮箱', width : 80, sortable : true},
				{field : 'realName', title : '真实姓名', width : 80, sortable : true}
			] ],
			toolbar : toolbar,
			resizeable : true,//宽度可调整
			fitColumns : true,//自适应表格宽度
			pageSize : 5,//默认选择的分页是每页5行数据  
			pageList : [ 5, 10, 15, 20 ],//可以选择的分页集合
			striped : true,//设置为true将交替显示行背景
			collapsible : true,//显示可折叠按钮  
			sortable : true,//可以对列排序
			sortName : 'id',//当数据表格初始化时以哪一列来排序  
			sortOrder : 'asc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。  
			pagination : true,//分页  
			//singleSelect: true,//只能选中单行
			showFooter: true,
			rownumbers : true//显示行号
		//行数 

		});
	});

	//表格的查询
	function doSearch() {
		//根据查询条件，重新加载表格
		$('#dategrid').datagrid('load', {
			id : $('#userid').val(),
			loginName : $('#loginName').val(),
			email: $('#email').val(),
			realName: $('#realName').val()
		});
	}
	
	//重置查询条件
	function doReset() {
		//获取到查询条件下的所有input框,并设置值为空
		$('#searchTable').find("input").val("");
		
	}
	
	function addUser() {
		$('#dialog').dialog({
		    title: '添加用户',
		    width: 700,
		    height: 350,
		    closed: false,
		    cache: false,
		    resizable: true,
		    content: '表单',
		    //href: "${pageContext.request.contextPath}/saveUI.jsp",
		    href: 'user_addUI.action',
		    modal: true
		});
	}
</script>

</body>
</html>