$.extend($.fn.validatebox.defaults.rules,{
	/*username : {// 验证用户名
		validator : function(value) {
			return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
		},
		message : "用户名不合法（字母开头，6-16长度，允许字母数字下划线）"
	},*/
	test:{
		validator : function(value) {
		},
		message : ''
	},
	realname : {// 验证姓名，可以是中文或英文
		validator : function(value) {
			return /^[u0391-uFFE5]+$/i.test(value)
					| /^w+[ws]+w+$/i.test(value);
		},
		message : "请输入姓名"
	},
    equalTo: { //必须和某个字段相等,常用于两次密码相等验证
    	validator: function (value, param) { 
    		return $(param[0]).val() == value; 
    	}, 
    	message: '两次密码输入不匹配' 
    },
    loginName: {//验证用户名是否重复
    	validator: function (value, param) {// param 参数集合
    		if (!/^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value)) {
    			$.fn.validatebox.defaults.rules.loginName.message = '用户名不合法（字母开头，6-16长度，允许字母数字下划线）';
    				return false;
    		} else {
				var result = $.ajax({
					url: 'user_isExist.action?loginName='+value,
					type: 'post',
					async: false,
					cache: false
				}).responseText;
				if (result == 'true') {
					$.fn.validatebox.defaults.rules.loginName.message = '用户名已存在！';
					return false;
				} else {
					return true;
				}
    		}
    	},
    	message: ''
    },
    phone : {// 验证电话号码
		validator : function(value) {
			return /^(((d{2,3}))|(d{3}-))?((0d{2,3})|0d{2,3}-)?[1-9]d{6,7}(-d{1,4})?$/i
					.test(value);
		},
		message : "格式不正确,请使用下面格式:023-88888888"
	},
	mobile : {// 验证手机号码
		validator : function(value) {
			return /^(13|15|18)[0-9]{9}$/i.test(value);
			
		},
		message : "手机号码格式不正确"
	},
	qq : {// 验证QQ,从10000开始
		validator : function(value) {
			return /^[1-9]*[1-9][0-9]*$/i.test(value);
		},
		message : "QQ号码格式不正确"
	},
	integer : {// 验证整数
		validator : function(value) {
			return /^[+]?[1-9]+d*$/i.test(value);
		},
		message : "请输入整数"
	},
	zip : {// 验证邮政编码
		validator : function(value) {
			return /^[1-9]d{5}$/i.test(value);
		},
		message : "邮政编码格式不正确"
	},
	ip : {// 验证IP地址
		validator : function(value) {
			return /d+.d+.d+.d+/i.test(value);
		},
		message : "IP地址格式不正确"
	},
	carNo : {
		validator : function(value) {
			return /^[u4E00-u9FA5][da-zA-Z]{6}$/.test(value);
		},
		message : "车牌号码无效（例：粤J12350）"
	},
	carenergin : {
		validator : function(value) {
			return /^[a-zA-Z0-9]{16}$/.test(value);
		},
		message : "发动机型号无效(例：FG6H012345654584)"
	},
	msn : {
		validator : function(value) {
			return /^w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*$/
					.test(value);
		},
		message : "请输入有效的msn账号(例：abc@hotnail(msn/live).com)"
	}
	
});
