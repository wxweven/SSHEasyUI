package com.wxweven.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wxweven.service.SysMenuService;

/**
 * @author wxweven
 * @date 2014年9月20日
 * @version 1.0
 * @email wxweven@163.com
 * @blog http://wxweven.blog.163.com/
 * @Copyright: Copyright (c) wxweven 2014
 * @Description:应用启动时，做的一些初始化工作
 */
public class InitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 获取容器与相关的Service对象
//		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
//		SysMenuService sysMenuService = (SysMenuService) ac.getBean("sysMenuService");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

}
