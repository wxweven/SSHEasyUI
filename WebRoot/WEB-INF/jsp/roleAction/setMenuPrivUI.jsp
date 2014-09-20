<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
</head>
<body>
<div style="padding:10px;">
	
	<!-- <div style="margin:20px 0;">
		<a href="#" class="easyui-linkbutton" onclick="getChecked()">GetChecked</a> 
	</div> -->
	<div style="margin:5px;">
		<a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" 
                		onclick="javascript:submitMenuPriv()">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" id="btnCancel" iconcls="icon-cancel" 
                 		onclick="javascript:closeDialog()">取消</a>
	</div>
	<div class="easyui-panel" style="padding:5px">
		<input type="checkbox" id="cbSelectAll" >全选 
		<ul id="tt"></ul><!-- 加载菜单权限树 -->
	</div>
</div>

<script type="text/javascript">
$(function(){
	var menuPrivTree = ${menuPrivTree};//从栈顶拿到菜单权限树
	
	var privIds = ${privIds};
	
	//回显角色所拥有的菜单权限
	$.each(menuPrivTree, function(key,val){
		
		if (val.hasOwnProperty("children")) {
			//有子元素，而且满足回显条件，就让子元素的 checked 为 true
			$.each(val.children, function(key2, val2) {
				if($.inArray(val2.id, privIds) != -1){
					menuPrivTree[key].children[key2].checked = true;
				}
			});
		} else{
			//如果没有子元素，而且满足回显条件，就让自己的 checked 为 true
			if($.inArray(val.id, privIds) != -1){
				menuPrivTree[key].checked = true;
			}
		}
	});
	
	//console.dir(menuPrivTree);
	
	$('#tt').tree({
	    data: menuPrivTree,
	    animate:true,
	    checkbox:true,
	    lines:true
	});
	
	//全选与全不选
	$('#cbSelectAll').click(function(){
		
		isChecked = $(this).is(':checked');//用于判断 "全选"按钮是否选中
		var treeNode = $(".tree-checkbox");//获得所有的tree节点 
		
		//"全选"按钮选中
		if(isChecked) {
			//选中所有的树节点
			treeNode.removeClass("tree-checkbox0");
			treeNode.addClass("tree-checkbox1");
		} else {
			//全不选所有的树节点
			treeNode.removeClass("tree-checkbox1");
			treeNode.addClass("tree-checkbox0");
		}
	});
});

//获得选中的树节点
function getChecked(){
	var sysMenuIds = [];
	//获得选中的节点
	//var nodes = $('#tt').tree('getChecked');
	var nodes = $('#tt').tree('getChecked', ['checked','indeterminate']);
	for(var i=0; i<nodes.length; i++){
		sysMenuIds.push(nodes[i].id);
	}
	console.log(sysMenuIds);
}

//提交设置的权限
function submitMenuPriv() {
	var id = ${id};//从当前值栈中拿Role 对应的 id
	var sysMenuIds = [];
	//获得选中的节点
	//var nodes = $('#tt').tree('getChecked');
	var nodes = $('#tt').tree('getChecked', ['checked','indeterminate']);
	for(var i=0; i<nodes.length; i++){
		sysMenuIds.push(nodes[i].id);
	}
	
	//console.log(sysMenuIds);
	
	var postData = {'sysMenuIds': sysMenuIds};
	
	//ajax 请求设置对应的权限
	$.ajax({
		url: 'role_setMenuPriv'+actionExtension+'?id='+id,
		type: 'POST',
		data: postData,//删除的id
		traditional: true,//传递数组是，一定要设置 traditional:true，
						  //防止jquery深度序列化参数对象,即将数组参数转化为传统格式：deletIds=1&deletIds=2
		async: true,//默认为true，代表异步请求；如果为false，代表同步请求
		success: function(data){
			$.messager.alert("设置权限",data,'info');
			$('#dialog').dialog('close');
		}
	});//end of $.ajax
}

function closeDialog() {
	$('#dialog').dialog('close');
}

</script>

</body>
</html>
