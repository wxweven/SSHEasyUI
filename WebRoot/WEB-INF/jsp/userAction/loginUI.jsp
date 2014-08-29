<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>WXWEVEN SSHEasyUI</title>
<!-- 包含公共的js,css等资源文件 -->
<%@ include file="/WEB-INF/jsp/public/commons.jspf"%>

<link href="${pageContext.request.contextPath}/style/css/login.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/script/loginjs.js" type="text/javascript"></script>

<script type="text/javascript">

	$(function(){
		document.forms[0].loginName.focus();
	});
	
	// 在被嵌套时就刷新上级窗口
	if(window.parent != window){
		window.parent.location.reload(true);
	}
	
	function key_down() {
		if (event.keyCode == 13) {
			checkLogin();
		}
	}
</script>

</head>

<body>
<div id="container">
	<div id="header">
	</div>
	<div id="content">
		<div class="left_main">
			<ul class="news" style="color:#FFFFFF;">
				<li><font size="4"><%=application.getInitParameter("webAppName") %></font></li>
			</ul>
		</div>
		<form id="form1" method="post" action = "user_login">
			<fieldset class="right_main">
				<dl class="setting">
					<dt>
						<label>用户名</label>
					</dt>
					<dd>
						<span class="text_input">
							<input type="text" name="loginName" id="loginAction_loginName"/>
						</span>
						<div id="usernameTip" style="width:200px;"></div>
					</dd>
					<dt>
						<label>密　码</label>
					</dt>
					<dd>
						<span class="text_input">
							<input type="password" name="password" id="loginAction_password" />
						</span>
						<div id="userpassTip" style="width:200px;"></div>
					</dd>
					<dt>
						<label>验证码</label>
					</dt>
					<dd>
						<span class="short_input">
							<input id="loginAction_usercaptcha" type="text" style="text-transform: uppercase;" name="usercaptcha" onkeydown="key_down()"/>
						</span>
						<span class="yzm">
							<img src="identify_Code.jsp" id="identifycode" onclick="changeCode()" />
							<a href="javascript:changeCode()">换一张</a>
						</span>
						<div id="vdcodeTip" style="width:280px;"></div>
					</dd>
					<dd>
						<s:fielderror></s:fielderror>
					</dd>
					<dd>
						<input type="button" id="loginBtn" value="登录" name="sm1"  class="login_btn" onClick="checkLogin()" />
						
						<div id="loginbottom" class="login">
							<div id="errorInfo" style="color:red; font-size:15px"></div>
						</div>
					</dd>
				</dl>
				
			</fieldset>
		</form>
	</div>	
	
	<div id="footer">Copyright &copy; @author wxweven @email wxweven@163.com 2014 重庆邮电大学. All rights reserved.</div>
</div>
</body>
</html>