<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
</head>
<body>
<div style="padding:10px;">
	<form id="ffAdd" method="post" data-options="novalidate:true">
		<table id="tblAdd" class="view">
			<tr>
				<th><label for="loginName">用户名：</label></th>
				<td>
				<!-- 	<input class="easyui-validatebox" type="text" id="loginName" name="loginName"
						data-options="required:true, validType:'loginName', delay:800"/> -->
					<input class="easyui-validatebox" type="text" id="loginName" name="loginName"
						data-options="required:true"/>
				</td>
				
			</tr>
			
			
			<tr>	
				<th><label for="departmentId">所属部门：</label></th>
				<td><input class="easyui-validatebox" type="text" id="departmentId" name="departmentId" /></td>
				
				<th><label for="roleIds">角色：</label></th>
				<td><input  type="text" id="roleIds" name="roleIds" /></td>
			</tr>
			
			<tr>
            	<td colspan="4" style="text-align:right; padding-top:10px">
                	<a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" 
                		onclick="javascript:submitForm()">确定</a>
                 	<a href="javascript:void(0)" class="easyui-linkbutton" id="btnCancel" iconcls="icon-cancel" 
                 		onclick="javascript:closeDialog()">取消</a>
              	</td>
          	</tr>
		</table>
	</form>
	
</div>

<script type="text/javascript">
$(function(){
	//用户所属部门下拉框：获取所有的部门
	$('#departmentId').combotree({
	    url:'department_getDepartmentTree.action',
	    valueField: 'id',
        textField: 'text',
	    required: true
	});
	
	//用户角色下拉框：获取所有的角色
	
	$('#roleIds').combobox({
	    url:'role_getRoleList.action',
	    method: 'get',
	    valueField:'id',
	    textField:'text',
	    multiple:true
	});
});

function submitForm() {
	$('#ffAdd').form('submit', {
		url: 'user_add.action',//指定提交的url
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
	    success:function(data){
	    	$.messager.alert("添加信息",data,'info',function(){
				location.reload(true);//显示信息后的回调函数：重新加载原页面
			});
	    	$('#dialog').dialog('close');
	    }
	});
}

function closeDialog() {
	$('#dialog').dialog('close');
}

</script>

</body>
</html>
