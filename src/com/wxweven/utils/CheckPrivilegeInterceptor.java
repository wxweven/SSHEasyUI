package com.wxweven.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.wxweven.domain.User;

/**
 * 
 * @author wxweven
 * @date 2014年9月1日
 * @version 1.0
 * @email wxweven@163.com
 * @blog http://wxweven.blog.163.com/
 * @Copyright: Copyright (c) wxweven 2014
 * @Description: 权限验证拦截器：所有的Action都需要进行权限验证
 */
public class CheckPrivilegeInterceptor extends AbstractInterceptor {

	public String intercept(ActionInvocation invocation) throws Exception {
		Log logger = LogFactory.getLog(this.getClass());

		// 获取信息
		User user = (User) ActionContext.getContext().getSession().get("user"); // 当前登录用户
		String namespace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String privUrl = namespace + actionName; // 对应的权限URL
//		String privUrl = actionName; // 对应的权限URL

//		logger.debug("user:"+user);
//		logger.debug("privUrl:"+privUrl);
		
		// 如果未登录
		if (user == null) {
			if (privUrl.startsWith("/user_login")) { // "/user_loginUI", "/user_login"
														
				// 如果是去登录，就放行
				return invocation.invoke();
			} else {
				// 如果不是去登录，就转到登录页面
				return "loginUI";
			}
		}
		// 如果已登录，就判断菜单权限
		else {
			if (user.hasSysMenuByUrl(privUrl,namespace)) {
				// 如果有权限，就放行
				return invocation.invoke();
			} else {
				// 如果没有权限:
				// 1. 如果是请求的url是 home_frame,从session中删除user
				if (privUrl.equals("/home_frame")) {
					ActionContext.getContext().getSession().remove("user");
				}
				// 2.就转到提示页面
				return "noPrivilegeError";
			}
		}
	}

}
