
$(function(){
	
	changeCode();//页面加载时，刷新验证码
	
	//禁用鼠标右键
	$(document).bind("contextmenu",function(e){   
        return false;   
	});
	
	/*$("#userId").focus(function(){
		$("#boxId").css("background","#F2FAFC");
	}).blur(function(){
		$("#boxId").css("background","#FFF");
	});
	$("#userpsw").focus(function(){
		$("#boxpsw").css("background","#F2FAFC");
	}).blur(function(){
		$("#boxpsw").css("background","#FFF");
	});
	$("#userverg").focus(function(){
		$("#boxverg").css("background","#F2FAFC");
	}).blur(function(){
		$("#boxverg").css("background","#FFF");
	});*/
	
});


//验证登录函数
function checkLogin(){
	//获取用户填入的基本信息 
	var loginName = $("#loginAction_loginName").val();
	var password = $("#loginAction_password").val();
	var usercaptcha = $("#loginAction_usercaptcha").val();

	$.blockUI({
		message : "正在登录，请稍候……"
	});
	
	//发送 ajax 的 post 请求给服务器，验证登录信息
	var loginCheckUrl = "user_login" + actionExtension;
	var postData = {'loginName': loginName, 'password': password, 'usercaptcha': usercaptcha};
	
	$.ajax({
		url: loginCheckUrl,
		type: 'POST',
		data: postData,
		async: false,//默认为true，代表异步请求；如果为false，代表同步请求
//		dataType: 'json',//预期服务器返回的数据类型为 json
		success: callback
		
	});
	
//	$.get(loginCheckUrl, null, callback);//简洁版的 get 请求
	
	setTimeout(function() {
		$.unblockUI();
		alert('系统异常，登陆超时，请稍后再试');
	}, 600000);
}

//验证登录的回调函数
function callback(data){
	$(document).ready(function(){
		if(data ==('success'))
		{
			window.location.href="home_frame" + actionExtension;
		} else {
			$.unblockUI();
			changeCode();
			$("#loginAction_usercaptcha").val("");//清空验证码 input 框
			$("#errorInfo").html("<span>"+data+"</span>");
		}

	});
}

//点击更换验证码图片
function changeCode(){
	$('#identifycode').attr('src','identify_Code.jsp?'+Math.random());
}
