package com.wxweven.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.wxweven.base.BaseServiceImpl;
import com.wxweven.domain.Department;
import com.wxweven.domain.SysMenu;
import com.wxweven.domain.User;
import com.wxweven.service.SysMenuService;

/**
 * 
 * @author wxweven
 * @date 2014年9月15日
 * @version 1.0
 * @email wxweven@163.com
 * @blog http://wxweven.blog.163.com/
 * @Copyright: Copyright (c) wxweven 2014
 * @Description: 菜单Service实现类
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu> implements SysMenuService {

	@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
	@Override
	public String getSysMenuTree(User user) {
		logger.debug("登录用户是："+user);
		String hql = "from SysMenu where parentId is null";// 获取顶级菜单
		Query query = getSession().createQuery(hql);
		List resultList = query.list();

		StringBuilder treeJsonStr = new StringBuilder();// 定义最终返回的 json 格式的字符串
		treeJsonStr.append("{\"menus\":[");

		// 利用递归构建部门树的json字符串
		for (int i = 0; i < resultList.size(); i++) {
			SysMenu sysMenu = (SysMenu) resultList.get(i);
			treeJsonStr.append(getMenuTree(sysMenu, user));
		}

		treeJsonStr.append("]}");
		String menuTree = treeJsonStr.toString().replaceAll("\\[,", "\\[");

		logger.debug("菜单树---->" + menuTree);

		return menuTree;
	}

	@Override
	public String getMenuTree(SysMenu sysMenu, User user) {

		StringBuilder resultStr = new StringBuilder();
		String menuName = sysMenu.getName();//菜单名
		if(user.hasSysMenuByName(menuName)) {
			resultStr.append(",{\"id\":").append("\"").append(sysMenu.getId()).append("\"").append(",");
			resultStr.append("\"icon\":").append("\"").append(sysMenu.getIcon()).append("\"").append(",");
			resultStr.append("\"name\":").append("\"").append(sysMenu.getName()).append("\"").append(",");
			resultStr.append("\"url\":").append("\"").append(sysMenu.getUrl()).append("\"");
	
			if (sysMenu.getChildren() != null && !sysMenu.getChildren().isEmpty()) {
				resultStr.append(",\"menus\":[");
				// 循环构建子菜单 json
				for (SysMenu children : sysMenu.getChildren()) {
					resultStr.append(getMenuTree(children, user));
				}
	
				resultStr.append("]");
			}
			resultStr.append("}");
		}

		return resultStr.toString();
	}

	@Override
	public String getSysMenuPrivTree() {
//		//如果当前用户不是管理员，则没有设置权限的权利
//		if(!user.isAdmin()){
//			return null;
//		}
//		
		String hql = "from SysMenu where parentId is null";// 获取顶级菜单
		Query query = getSession().createQuery(hql);
		List resultList = query.list();

		StringBuilder treeJsonStr = new StringBuilder();// 定义最终返回的 json 格式的字符串
		treeJsonStr.append("[");

		// 利用递归构建部门树的json字符串
		for (int i = 0; i < resultList.size(); i++) {
			SysMenu sysMenu = (SysMenu) resultList.get(i);
			treeJsonStr.append(getMenuPrivTree(sysMenu));
		}

		treeJsonStr.append("]");
		String menuPrivTree = treeJsonStr.toString().replaceAll("\\[,", "\\[");

		logger.debug("菜单权限树---->" + menuPrivTree);

		return menuPrivTree;
	}

	@Override
	public String getMenuPrivTree(SysMenu sysMenu) {
		StringBuilder resultStr = new StringBuilder();
		//因为easyui前台，只认 "id":"101" 这种双引号的格式，而不认 'id':'101'格式，所以，这里要用双引号。。。。
		resultStr.append(",{\"id\":").append("\"").append(sysMenu.getId()).append("\"").append(",");
		resultStr.append("\"text\":").append("\"").append(sysMenu.getName()).append("\"");

		if (sysMenu.getChildren() != null && !sysMenu.getChildren().isEmpty()) {
			resultStr.append(",\"children\":[");
			// 循环构建子部门 json
			for (SysMenu children : sysMenu.getChildren()) {
				resultStr.append(getMenuPrivTree(children));
			}

			resultStr.append("]");
		}
		resultStr.append("}");
		
		return resultStr.toString();
	}

}
