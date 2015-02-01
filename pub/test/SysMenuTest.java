package com.wxweven.test;

import java.util.List;

import org.hibernate.Query;
import org.junit.Test;

import com.wxweven.domain.SysMenu;

public class SysMenuTest extends SessionTest {
	
	@Test
	public void getSysMenuTreeTree() {
		String hql = "from SysMenu where parentId is null";//获取顶级菜单
		Query query = getSession().createQuery(hql);
		List resultList = query.list();
		
		StringBuilder treeJsonStr = new StringBuilder();// 定义最终返回的 json 格式的字符串
		treeJsonStr.append("{\"menus\":[");
		
		//利用递归构建部门树的json字符串
		for(int i=0; i<resultList.size();i++){
			SysMenu sysMenu = (SysMenu) resultList.get(i);
			treeJsonStr.append(getMenu(sysMenu));
		}
		
		
		treeJsonStr.append("]}");
		String returnStr = treeJsonStr.toString().replaceAll("\\[,", "\\[");
		
		logger.debug("顶级菜单----》"+returnStr);
	}

	/**
	 * 根据传入的 部门 sysMenu，递归构建菜单树 json 并返回
	 * @param sysMenu
	 * @return
	 */
	public String getMenu(SysMenu sysMenu) {

		StringBuilder resultStr = new StringBuilder();
		resultStr.append(",{\"id\":").append("\"").append(sysMenu.getId()).append("\"").append(",");
		resultStr.append("\"icon\":").append("\"").append(sysMenu.getIcon()).append("\"").append(",");
		resultStr.append("\"name\":").append("\"").append(sysMenu.getName()).append("\"");
		
		if (sysMenu.getChildren() != null && !sysMenu.getChildren().isEmpty()) {
			resultStr.append(",\"menus\":[");
			// 循环构建子部门 json
			for (SysMenu children : sysMenu.getChildren()) {
				resultStr.append(getMenu(children));
			}

			resultStr.append("]");
		}
		resultStr.append("}");
		
		return resultStr.toString();
	}
}
