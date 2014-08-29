var isClickOk = true;// 判断的变量
$(function() {
	// datagrid设置参数
	$('#mydatagrid').datagrid({
		title : 'datagrid实例',
		iconCls : 'icon-ok',
		width : 600,
		pageSize : 5,// 默认选择的分页是每页5行数据
		pageList : [ 5, 10, 15, 20 ],// 可以选择的分页集合
		nowrap : true,// 设置为true，当数据长度超出列宽时将会自动截取
		striped : true,// 设置为true将交替显示行背景。
		collapsible : true,// 显示可折叠按钮
		toolbar : "#easyui_toolbar",// 在添加 增添、删除、修改操作的按钮要用到这个
		url : 'studentallInfo',// url调用Action方法
		loadMsg : '数据装载中......',
		singleSelect : true,// 为true时只能选择单行
		fitColumns : true,// 允许表格自动缩放，以适应父容器
		sortName : 'studentid',// 当数据表格初始化时以哪一列来排序
		sortOrder : 'asc',// 定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。
		remoteSort : true,
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		pagination : true,// 分页
		rownumbers : true
	// 行数
	});

	// 当点击添加学生信息的时候触发
	$("#easyui_add").click(function() {
		$("#xianshi1").empty();// 清除上次出现的图标1
		$("#xianshi2").empty();// 清除上次出现的图标2
		$('#addDlg').dialog('open').dialog('setTitle', '添加学生信息');// 打开对话框
		/*$('#addDlg').dialog({
		    title: 'My Dialog',
		    closed: false,
		    cache: false,
		    modal: true
		});*/
		$('#addForm').form('clear');
	});

	// 当光标移开焦点的时候进行重复验证
	$("#studentid")
			.blur(
					function() {
						jQuery
								.ajax({ // 使用Ajax异步验证主键是否重复
									type : "post",
									url : "studentverify?table=Student&field=studentid&parameter="
											+ $('#studentid').val(),
									dataType : 'json',
									success : function(s) {
										if ($('#studentid').val() == "") {// 当为主键为空的时候什么都不显示，因为Easyui的Validate里面已经自动方法限制

										} else if (s == "1")// 当返回值为1，表示在数据库中没有找到重复的主键
										{
											isClickOk = true;
											$("#xianshi1").empty();
											var txt1 = "<img src="
													+ "'imgs/agree_ok.gif'"
													+ "/>";// 引入打勾图标的路径
											$("#xianshi1").append(txt1);// 在id为xianshi1里面加载打勾图标
											$("#xianshi2").empty();
											$("#xianshi2").append("未被使用");// 在di为xianshi2中加载“未被使用”这四个字
										} else {
											$("#xianshi1").empty();
											isClickOk = false;
											var txt1 = "<img src="
													+ "'imgs/agree_no.gif'"
													+ "/>"; // 引入打叉图标的路径
											$("#xianshi1").append(txt1);// 在id为xianshi1里面加载打叉图标
											$("#xianshi2").empty();
											$("#xianshi2").append("已被使用");// 在id为xianshi2里面加载“已被使用”四个字
										}
									}
								});
					});

});

// 添加信息点击保存的时候触发此函数
function add_ok() {
	$.messager.defaults = {
		ok : "确定",
		cancel : "取消"
	};
	$.messager.confirm('Confirm', '您确定增加?', function(r) {// 使用确定，取消的选择框
		if (r) {
			$('#addForm').form('submit', {// 引入Easyui的Form
				url : "studentadd",// URL指向添加的Action
				onSubmit : function() {
					if (isClickOk == false) {// 当主键重复的时候先前就已经被设置为false，如果为false就不提交，显示提示框信息不能重复
						$.messager.alert('操作提示', '主键不能重复！', 'error');
						return false;
					} else if ($('#addForm').form('validate')) {// 判断Easyui的Validate如果都没错误就同意提交
						$.messager.alert('操作提示', '添加信息成功！', 'info');
						return true;
					} else {// 如果Easyui的Validate的验证有一个不完整就不提交
						$.messager.alert('操作提示', '信息填写不完整！', 'error');
						return false;
					}
				}
			});
			$('#mydatagrid').datagrid({
				url : 'studentallInfo'
			});// 实现Datagrid重新刷新效果
			$('#addDlg').dialog('close');// 关闭对话框
		}
	});
}
