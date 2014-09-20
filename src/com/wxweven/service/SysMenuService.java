package com.wxweven.service;

import com.wxweven.base.BaseService;
import com.wxweven.domain.SysMenu;
import com.wxweven.domain.User;

/**
 * 
 * @author wxweven
 * @date 2014年9月15日
 * @version 1.0
 * @email wxweven@163.com
 * @blog http://wxweven.blog.163.com/
 * @Copyright: Copyright (c) wxweven 2014
 * @Description:
 */
public interface SysMenuService extends BaseService<SysMenu> {
	/**
	 * 得到用户的菜单树
	 * 
	 * @param user
	 *            指定的用户
	 * @return
	 */
	String getSysMenuTree(User user);

	/**
	 * 得到指定SysMenu的树状结构
	 * 
	 * @param sysMenu
	 * @return
	 */
	String getMenuTree(SysMenu sysMenu, User user);

	/**
	 * 得到用户的菜单权限树，默认只有管理员才能给其他用户分配权限
	 * 
	 * @return
	 */
	String getSysMenuPrivTree();
	
	/**
	 * 得到指定SysMenu的树状结构
	 * 
	 * @param sysMenu
	 * @return
	 */
	String getMenuPrivTree(SysMenu sysMenu);
	
	
}
