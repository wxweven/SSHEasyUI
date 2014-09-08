<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/jsp/public/commons.jspf"%>
    <meta charset="UTF-8">
    <title>${jspGridTitle}</title>

	<script type="text/javascript">
	   $(function(){
		   $("#searchTable tr td:even").addClass("alignright");//让 偶数 的 td 向右靠齐
	   });
	</script>    
</head>
<body>
<!-- ========查询条件========== -->
	<div class="easyui-panel" title="查询条件" style="width:900px;padding:5px;">
	    <table id="searchTable">
	        <tr>
	            <td>用户id:</td>
	            <td><input name="id" id="userid" class="searchinput easyui-textbox" /></td>
	            <td>用户名loginName:</td>
	            <td ><input name="loginName" id="loginName" class="searchinput easyui-textbox" /></td>
	            <td >邮箱Dmail:</td>
	            <td><input name="email" id="email" class="searchinput easyui-textbox" /></td>
	        </tr>
	        <tr>
	            <td>真实姓名:</td>
	            <td><input name="realName" id="realName" class="searchinput easyui-filebox"></input></td>
	            <td>其他条件:</td>
	            <td> <input name="file" class="searchinput easyui-filebox"></input></td>
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
	
<!-- =========显示数据表格列表======== -->
	<table id="dategrid"></table>
	
<!-- =========显示弹窗======== -->
	<div id="dialog"></div>

<script type="text/javascript">
	$(function() {
		//定义表格上的工具条
		var toolbar = [ 
			{text : '添加', iconCls : 'icon-add', handler : addUser},//添加用户
			{text : '删除', iconCls : 'icon-cancel', handler : deleteUser},//删除用户，支持批量删除
			{text : '修改', iconCls : 'icon-edit', handler : editUser},//修改用户，只能单个修改
			{text : '初始化密码', iconCls : 'icon-remove', handler : initUserPasswd}//初始化用户密码，支持批量操作
		];

		//加载datagrid数据表格
		$('#dategrid').datagrid({
			url : 'user_list.action',
			columns : [ [ 
				{field : 'ck', checkbox: true},//定义多选框 checkbox
				//sortable: true,允许点击表头排序,hidden:true,隐藏id列
				{field : 'id', title : '用户id', width : 40, sortable : true},
				{field : 'loginName', title : '用户名', width : 80, sortable : true}, 
				{field : 'realName', title : '真实姓名', width : 80, sortable : true,
					formatter: function(value,row,index){
						
						return "<a href='"+value+"' tartget='_top'>"+value+"</a>";
						//return "<a href='http://www.baidu.com/s?wd="+value+"' tartget='_top'>"+value+"</a>";
						
					}
				},
				{field : 'userState', title : '用户状态', width : 80, sortable : true},
				{field : 'lastLoginTime', title : '最后登录时间', width : 100, sortable : true},
				{field : 'gender', title : '性别', width : 80, sortable : true},
				{field : 'phoneNumber', title : '电话号码', width : 80, sortable : true},
				{field : 'email', title : '邮箱', width : 80, sortable : true},
				{field : 'description', title : '说明', width : 80, sortable : true}
				
			] ],
			toolbar : toolbar,
			resizeable : true,//宽度可调整
			fitColumns : true,//自适应表格宽度
			pageSize : 10,//默认选择的分页是每页5行数据  
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
	
	//添加 和 修改 用户 共用的弹窗
	function addUser(action, id) {
		//默认是添加用户
		var title = "添加用户";
		var url = 'user_addUI.action';
		
		//如果传入的参数 action 是 edit, 则表示要修改用户
		if(action == 'edit'){
			title = "编辑用户";
			url = 'user_editUI.action?id='+id;
		}
		
		$('#dialog').dialog({
		    title: title,
		    width: 700,
		    height: 350,
		    closed: false,
		    cache: false,
		    resizable: true,
		    href: url,
		    modal: true
		});
	}
	
	//删除用户，支持批量删除
	function deleteUser(){
		var $checkedRows = $('#dategrid').datagrid('getChecked');
		
		//如果没有选中要删除的行，提示错误并返回
		if($checkedRows.length == 0){
			$.messager.alert('删除记录','请选择要删除的行!','error');//不要用alert,用$.messager.alert，error表示这是个错误提示
			return false;
		} else {
			$.messager.confirm('删除记录', '确定删除选中的行吗?', function(r){//确认框
	        	if (r){//如果确认删除，就获取到删除的行的id，并个发送删除请求
	        		var deleteIds = new Array();//要被删除的id数组
	    			
	        		//遍历将选中行的id加入deleteIds数组
	    			$.each($checkedRows, function(key, val) {
	    				deleteIds[key] = val.id;
	    			});
	    			console.log(deleteIds);
	        		var postData = {'deletIds': deleteIds};
	        		//ajax 请求删除选中的行
	    			$.ajax({
	    				url: 'user_delete.action',
	    				type: 'POST',
	    				data: postData,//删除的id
	    				traditional: true,//传递数组是，一定要设置 traditional:true，
	    								  //防止jquery深度序列化参数对象,即将数组参数转化为传统格式：deletIds=1&deletIds=2
	    				async: true,//默认为true，代表异步请求；如果为false，代表同步请求
	    				success: function(data){
	    					$.messager.alert("删除记录",data,'info',function(){
	    						location.reload(true);//显示信息后的回调函数：重新加载原页面
	    					});
	    				}
	    			});//end of $.ajax
	       		}// end of if
	   		});//end of confirm
		}//end of else
	}//end of deleteUser
	
	//修改用户，一次只能修改一个用户
	function editUser(){
		var $checkedRows = $('#dategrid').datagrid('getChecked');
		
		//如果没有选中要修改的行，提示错误并返回
		if($checkedRows.length == 0){
			$.messager.alert('删除记录','请选择要修改的行!','error');//不要用alert,用$.messager.alert，error表示这是个错误提示
			return false;
		} else if($checkedRows.length > 1) {
			$.messager.alert('修改记录','一次只能选中一行!','error');//不要用alert,用$.messager.alert，error表示这是个错误提示
			return false;
		} else {
			//console.log($checkedRows[0].id);
			
			/**
			 * 修改页面和添加页面是一样的， 只不过修改页面可以回显数据，
			 * 所以这里利用addUser()来显示修改页面
			 * 传递到后台时，准备回显数据
			 */
			addUser('edit',$checkedRows[0].id);
		}//end of if
	}//end of editUser
	
	
	//初始化用户密码，支持批量操作
	function initUserPasswd(){
		var $checkedRows = $('#dategrid').datagrid('getChecked');
		
		//如果没有选中的行，提示错误并返回
		if($checkedRows.length == 0){
			$.messager.alert('初始化密码','请选择要操作的行!','error');//不要用alert,用$.messager.alert，error表示这是个错误提示
			return false;
		} else {
			$.messager.confirm('初始化密码', '确定将选中用户的密码初始化为123456吗?', function(r){//确认框
	        	if (r){//如果确认操作，就获取到要操作的行的id，并个发送初始化密码请求
	        		var initPasswdIds = new Array();//要被删除的id数组
	    			
	        		//遍历将选中行的id加入deleteIds数组
	    			$.each($checkedRows, function(key, val) {
	    				initPasswdIds[key] = val.id;
	    			});
	        		var postData = {'initPasswdIds': initPasswdIds};
	        		//ajax 请求删除选中的行
	    			$.ajax({
	    				url: 'user_initPasswd.action',
	    				type: 'POST',
	    				data: postData,//初始化密码id
	    				traditional: true,//传递数组是，一定要设置 traditional:true，
	    								  //防止jquery深度序列化参数对象,即将数组参数转化为传统格式：deletIds=1&deletIds=2
	    				async: true,//默认为true，代表异步请求；如果为false，代表同步请求
	    				success: function(data){
	    					$.messager.alert("初始化密码",data,'info',function(){
	    						location.reload(true);//显示信息后的回调函数：重新加载原页面
	    					});
	    				}
	    			});//end of $.ajax
	       		}// end of if
	   		});//end of confirm
		}//end of else
	}//end of deleteUser
	
</script>

</body>
</html>