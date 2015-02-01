/**
 * @date 2014/08/25
 * @author wxweven
 */
var $_menus = null;// 全局变量，用于获取菜单

$(function() {
	getMenu();
//	console.log(actionExtension+"-----");//测试js全局变量
});

// 通过 Ajax请求，获取菜单json
function getMenu() {
	tabClose();
	tabCloseEven();

	$.ajax({
		url : 'user_menu.action',
		type : 'POST',
		async : false,// 默认为true，代表异步请求；如果为false，代表同步请求
		// dataType: 'json',//预期服务器返回的数据类型为 json
		success : function(data) {
			// console.log(data);
			// $_menus = data;

			try{
				$_menus = $.parseJSON(data);
				InitLeftMenu();// 左边的用户菜单
				InitRightMenu();// 右边的欢迎页面
				$("#pageloading").hide();
			}catch(e){
				console.log(e.name + ": " + e.message);
			}

		}
	});

}

// 初始化左侧 菜单显示
function InitLeftMenu() {
	// $("#nav").accordion({animate:true});//定义当延伸或者折叠面板时是否显示动画效果。 默认为true
	var array=[];
	$.each($_menus.menus, function(key, val) {
		// menulist：内部的菜单列表
		var menulist = '';
		var min_menulist = '';
		menulist += '<ul>';

		var listNum='';
		if (val.hasOwnProperty("menus")) {//说明还有子菜单
			

			$.each(val.menus, function(key2, val2) {
				if(val2.menus){
					var thirdNum=key2;
				}
				/*代表含有三级菜单*/
				if(key2==thirdNum){
					var thirdMenu=val2.menus;
					var thirdMenuLength=thirdMenu.length;
					var thirdStr="<ul>";
					for(var z=0;z<thirdMenuLength;z++){
						/*代表有四级菜单*/
						if(thirdMenu[z].menus){
							var fourNum=z;
							var fourMenu=thirdMenu[z].menus;
							var fourLength=fourMenu.length;
							var fourStr="<ul>";
							for(var j=0;j<fourLength;j++){
								fourStr+="<li style='padding:4px 0px;'>"+'<span class="icon '
								+ val2.icon + '" >&nbsp;</span>' + '<span class="nav">'
								+fourMenu[j].name+ '</span>'+"</li>"
							}
							fourStr+="</ul>";
							//console.log(fourStr);
							thirdStr+="<li style='padding:4px 0px;'>"+'<span class="icon '
							+ val2.icon + '" >&nbsp;</span>' + '<span class="nav">'
							+thirdMenu[z].name+ '</span>'+fourStr+"</li>";
						}else{
							thirdStr+="<li style='padding:4px 0px;'>"+'<span class="icon '
							+ val2.icon + '" >&nbsp;</span>' + '<span class="nav">'
							+thirdMenu[z].name+ '</span>'+"</li>";	
						}					
						
					}
					thirdStr+="</ul>";
					menulist += '<li><div>' + '<a ref="' + val2.id
						+ '" href="#" rel="' + val2.url + actionExtension
						+ '" class="menuToFrame" >' + '<span class="icon '
						+ val2.icon + '" >&nbsp;</span>' + '<span class="nav">'
						+ val2.name + '</span>' + '</a>'+thirdStr+'</div></li> '
				}else{
					menulist += '<li><div>' + '<a ref="' + val2.id
					+ '" href="#" rel="' + val2.url + actionExtension
					+ '" class="menuToFrame" >' + '<span class="icon '
					+ val2.icon + '" >&nbsp;</span>' + '<span class="nav">'
					+ val2.name + '</span>' + '</a>'+'</div></li> ';
				}
				
				
					
				
			});
		}
		
		

		menulist += '</ul>';

		// 添加最外层的菜单列表
		$('#nav').accordion('add', {
			title : val.name,
			content : menulist,
			iconCls : 'icon ' + val.icon
		});

	});

	/**
	 * easyui 的bug，这里还要加一个空的accordion， 不然最后一个accordion里面的菜单，点击后没有反应。。。
	 * 尼玛，调试了一下午，给他跪了。。。
	 */
	$('#nav').accordion('add', {});

	// 点击左侧的菜单，在右边显示页面
	$('.easyui-accordion li a').click(function() {
		var tabTitle = $(this).children('.nav').text();

		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		var icon = getIcon(menuid);// 获取左侧导航的图标

		addTab(tabTitle, url, icon);
		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
	}).hover(function() {
		$(this).parent().addClass("hover");
	}, function() {
		$(this).parent().removeClass("hover");
	});

	// 选中第一个
	var panels = $('#nav').accordion('panels');
	var t = panels[0].panel('options').title;
	$('#nav').accordion('select', t);
}

// 初始化右侧欢迎页面
function InitRightMenu() {
	var url = "";
	$.each($_menus.menus, function(key, val) {
		var menulist = '';

		/**
		 * 尼玛，这里也有对内层数据的遍历，内层数据也可能没有 menus属性， 所以这里也要判断val是否有menus属性
		 * 你大爷的，调试了几个小时，最后是这里出错，狂徒一吨血。。。。
		 */
		if (val.hasOwnProperty("menus")) {
			$.each(val.menus, function(key1, val2) {
				if (key == 0 && key1 == 0)
					url = val2.url;
			});
		}
	});
	$("#content_frame").attr('src', 'home_welcome.action');
}

// 获取左侧导航的图标
function getIcon(menuid) {
	var icon = 'icon ';
	$.each($_menus.menus, function(key, val) {
		if (val.hasOwnProperty("menus")) {
			$.each(val.menus, function(key1, val2) {
				if (val2.id == menuid) {
					icon += val2.icon;
				}
			});
		}
	});

	return icon;
}

function addTab(subtitle, url, icon) {
	if (!$('#tabs').tabs('exists', subtitle)) {
		$('#tabs').tabs('add', {
			title : subtitle,
			content : createFrame(url),
			closable : true,
			icon : icon
		});
	} else {
		$('#tabs').tabs('select', subtitle);
		$('#mm-tabupdate').click();
	}
	tabClose();
}

function createFrame(url) {
	var s = '<iframe scrolling="auto" frameborder="0"  src="' + url
			+ '" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose() {
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close', subtitle);
	});
	/* 为选项卡绑定右键 */
	$(".tabs-inner").bind('contextmenu', function(e) {
		$('#mm').menu('show', {
			left : e.pageX,
			top : e.pageY
		});

		var subtitle = $(this).children(".tabs-closable").text();

		$('#mm').data("currtab", subtitle);
		$('#tabs').tabs('select', subtitle);
		return false;
	});
}
// 绑定右键菜单事件
function tabCloseEven() {
	// 刷新
	$('#mm-tabupdate').click(function() {
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		$('#tabs').tabs('update', {
			tab : currTab,
			options : {
				content : createFrame(url)
			}
		});
	});
	// 关闭当前
	$('#mm-tabclose').click(function() {
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close', currtab_title);
	});
	// 全部关闭
	$('#mm-tabcloseall').click(function() {
		$('.tabs-inner span').each(function(key, val) {
			var t = $(val).text();
			$('#tabs').tabs('close', t);
		});
	});
	// 关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function() {
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	// 关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function() {
		var nextall = $('.tabs-selected').nextAll();
		if (nextall.length == 0) {
			msgShow('系统提示', '后边没有啦~~', 'warning');
			// alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(key, val) {
			var t = $('a:eq(0) span', $(val)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});
	// 关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function() {
		var prevall = $('.tabs-selected').prevAll();
		if (prevall.length == 0) {
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(key, val) {
			var t = $('a:eq(0) span', $(val)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});

	// 退出
	$("#mm-exit").click(function() {
		$('#mm').menu('hide');
	});
}

// 弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}

/*
 * //最原始的 获得 $_menu 的请求 function getMenu() {
 * 
 * tabClose(); tabCloseEven(); var xhr = getXMLHttpRequest(); var
 * responseContext="";
 * 
 * 
 * xhr.open("POST", "userMenuAction", true);
 * 
 * xhr.send();
 * 
 * xhr.onreadystatechange = function() { //回调函数
 * 
 * if (xhr.readyState == 4 && xhr.status == 200) {
 * 
 * responseContext = xhr.responseText; _menus =
 * eval("("+responseContext+")");//responseContext; InitLeftMenu();//左边的用户菜单
 * InitRightMenu();//右边的欢迎页面 $("#pageloading").hide();
 *  }
 *  } return responseContext; }
 */