<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
</head>
<body>
	<div style="padding:10px 60px 20px 60px">
		<form id="ff" class="easyui-form" method="post" data-options="novalidate:true">
			<table class="view">
				<tr>
					<th>
                    	<label for="PID">父菜单：</label>
                    </th>
					<td><input class="easyui-validatebox" type="text" name="loginName" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input class="easyui-validatebox" type="password" name="password" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td>确认密码:</td>
					<td><input class="easyui-validatebox" type="password" name="repassword" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td>用户状态</td>
					<td><input class="easyui-validatebox" type="radio" name="userState" checked="checked" 
						data-options="required:true" value="可用">男
						<input class="easyui-validatebox" type="radio" name="userState"
						data-options="required:true" value="不可用">可用
					</td>
				</tr>
				<tr>
					<td>性别</td>
					<td><input class="easyui-validatebox" type="radio" name="gender" checked="checked" 
						data-options="required:true" value="男">男
						<input class="easyui-validatebox" type="radio" name="gender" 
						data-options="required:true" value="女">女
					</td>
				</tr>
				
			</table>
		</form>
		
		
		
		
		<div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a> <a href="javascript:void(0)"
				class="easyui-linkbutton" onclick="clearForm()">Clear</a>
		</div>
	</div>
	
	
	<script>
		function submitForm() {
			$('#ff').form('submit', {
				onSubmit : function() {
					return $(this).form('enableValidation').form('validate');
				}
			});
		}
		function clearForm() {
			$('#ff').form('clear');
		}
	</script>

</body>
</html>
