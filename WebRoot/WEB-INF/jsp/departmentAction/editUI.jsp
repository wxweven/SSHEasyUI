<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
</head>
<body>
<div style="padding:10px;">
	<form id="ffAdd" method="post" data-options="novalidate:true">
		<table id="tblAdd" class="view">
			<tr>	
				<th><label for="departmentId">所属部门：</label></th>
				<td><input class="easyui-validatebox" type="text" id="departmentId" name="departmentId" value="${departmentId }"/></td>
				
				<!-- <th><label for="userGroupId">所属用户组：</label></th>
				<td><input class="easyui-validatebox" type="text" id="userGroupId" name="userGroupId"
					 required="true" /></td> -->
			</tr>
			
			<tr>
				<th><label for="userState">用户状态：</label></th>
				<td>
					<input class="easyui-validatebox" id="userState" type="radio" name="userState" checked="checked" 
						data-options="required:true" value="可用">可用
					<input class="easyui-validatebox" type="radio" name="userState"
						data-options="required:true" value="不可用">可用
				</td>

				<th><label for="gender">性别：</label></th>
				<td>
					<input class="easyui-validatebox" id="gender" type="radio" name="gender" checked="checked" 
						data-options="required:true" value="男">男
					<input class="easyui-validatebox" type="radio" name="gender"
						data-options="required:true" value="女">女
				</td>

			</tr>
			
			<tr>
				<th><label for="phoneNumber">电话号码：</label></th>
				<td><input class="easyui-validatebox" type="text" id="phoneNumber" name="phoneNumber" 
					data-options="required:true, validType:'mobile'" value="${phoneNumber }" /></td>

				<th><label for="email">邮箱：</label></th>
				<td><input class="easyui-validatebox" type="text" id="email" name="email" 
					data-options="required:true, validType:'email'" value="${email }"/></td>

			</tr>
			
			<tr>
				<th><label for="description">备注：</label></th>
				<td colspan="3">
					<textarea style="height:60px;width:500px" id="description" name="description">${description }
				</textarea></td>
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
	
});

function submitForm() {
	var id = ${id};//从当前值栈中拿User 对应的 id
	
	$('#ffAdd').form('submit', {
		url: 'user_edit.action?id='+id,//指定修改用户的url,一定要传入id
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
	    success:function(data){
	    	$.messager.alert("修改信息",data,'info',function(){
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
