<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
</head>
<body>
<div style="padding:10px;">
	<form id="ffAdd" method="post" data-options="novalidate:true">
		<table id="tblAdd" class="view">
			<tr>
				<th><label for="name">角色名：</label></th>
				<td>
					<input class="easyui-validatebox" type="text" id="name" name="name"
						data-options="required:true"/>
				</td>
			</tr>
			
			<tr>
				<th><label for="description">备注：</label></th>
				<td colspan="3"><textarea style="height:60px;width:500px" id="description" name="description"></textarea></td>
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
function submitForm() {
	$('#ffAdd').form('submit', {
		url: 'role_add' + actionExtension,//指定提交的url
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
