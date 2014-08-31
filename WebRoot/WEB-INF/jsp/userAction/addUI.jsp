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
					<input class="easyui-validatebox" type="text" id="loginName" name="loginName" 
						data-options="required:true,validType:'loginName'"/>
<!-- 					<input class="easyui-validatebox" type="text" id="loginName" name="loginName" 
						data-options="required:true,validType:['username','unique[\'#loginName\',\'user_isExist.action\']']"/> -->
				</td>
				
				<th><label for="realName">真实姓名：</label></th>
				<td>
					<input class="easyui-validatebox" type="text" id="realName" name="realName"
						data-options="required:true,validType:'length[1,20]'" />
				</td>
			</tr>
			
			<tr>	
				<th><label for="password">密码：</label></th>
				<td><input class="easyui-validatebox" type="password" id="password" name="password"
					data-options="required:true,validType:'length[6,20]'" /></td>
				
				<th><label for="repassword">确认密码：</label></th>
				<td><input class="easyui-validatebox" type="password" id="repassword" name="repassword"
					 required="true" missingMessage="请再次填写新密码" validType="equalTo['#password']" /></td>
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
					data-options="required:true, validType:'mobile'"/></td>

				<th><label for="email">邮箱：</label></th>
				<td><input class="easyui-validatebox" type="text" id="email" name="email" 
					data-options="required:true, validType:'email'"/></td>

			</tr>
			
			<tr>
				<th><label for="description">备注：</label></th>
				<td colspan="3"><textarea style="height:60px;width:500px" id="description" name="description"></textarea></td>
			</tr>
			
			<tr>
            	<td colspan="4" style="text-align:right; padding-top:10px">
                	<a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" 
                		iconcls="icon-ok" onclick="submitForm()">确定</a>
                 	<a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" 
                 		onclick="javascript:closeDialog()">取消</a>
              	</td>
          	</tr>
		</table>
	</form>
	
</div>

<script>
	
	/* $(function(){
		//表单验证，利用formValidator-4.0.1.min.js
		//表单验证的配置
		$.formValidator.initConfig({
			validatorgroup:"1",
			formid:"ffAdd",
			onerror:function(msg){
				alert(msg);
			}
		}); 
		
		//验证用户名
		$("#loginName").formValidator({
			 onshow:"请输入用户名",
			 onfocus:"用户名至少6个字符,最多10个字符"
		}).inputValidator({
			min:6,
			max:10,
			onerror:"你输入的用户名非法,请确认"
		});
	}); */
	/* $(function(){
		$('#loginName').blur(function(){
			var postData = {'loginName': $(this).val()};
			console.log(postData);
			$.ajax({
				url: 'user_isExist.action',
				type: 'POST',
				data: postData,
				async: false,//默认为true，代表异步请求；如果为false，代表同步请求
//				dataType: 'json',//预期服务器返回的数据类型为 json
				success: callback
				
			});
			
		});
	}); */
	
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
