package com.wxweven.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionContext;
import com.wxweven.base.BaseAction;
import com.wxweven.domain.Role;
import com.wxweven.domain.SysMenu;
import com.wxweven.utils.CommonUtils;

/**
 * @author wxweven
 * @date 2014年9月15日
 * @version 1.0
 * @email wxweven@163.com
 * @blog http://wxweven.blog.163.com/
 * @Copyright: Copyright (c) wxweven 2014
 * @Description:角色Role相关的Action
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

	private static final long serialVersionUID = 7643166662265803263L;

	private String[] sysMenuIds;// 菜单对应的id

	private Integer[] deletIds;// 要被删除的记录的id

	/** 分页 排序参数 */
	private String page;// 当前页
	private String rows;// 每页记录数
	private String sort;// 排序的字段
	private String order;// ASC 或者 desc

	// =================角色管理================
	/** 默认显示list.jsp页面 */
	public String toList() throws Exception {
		ActionContext.getContext().put("jspGridTitle", "角色列表");
		return "list";
	}

	/** 获取列表数据 */
	public String list() throws Exception {
		// 1. 最终的结果集jsonMap
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		// 2. 根据分页，排序，查询条件等来获取角色列表
		List<Role> roleList = roleService.findAll(page, rows, sort, order, getModelConditions());

		// 3. 获得符合条件的用户的总数(不带分页)
		int totalCount = roleService.totalCount();

		// 4. 去掉关联的users, sysMenus
		for (Role role : roleList) {
			role.setUsers(null);
			role.setSysMenus(null);
		}

		// 5. 构建 jsonMap
		jsonMap.put("rows", roleList);
		jsonMap.put("total", totalCount);
		
		/**
		 * 6.将jsonMap序列化成JSONString，利用阿里巴巴的fastjson，吊的一笔
		 * SerializerFeature.DisableCircularReferenceDetect: 禁止循环引用检测
		 */
		String jsonString = JSON.toJSONString(jsonMap, SerializerFeature.DisableCircularReferenceDetect);
		logger.debug("---->jsonString:" + jsonString);

		// 7. 返回数据给前台
		out.print(jsonString);
		out.flush();
		out.close();

		// 7. 由于直接返回数据给前台，而不需要跳转页面，这里直接返回null
		return null;// 返回 null 表示不用跳转页面
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		return "addUI";
	}

	/** 添加 */
	public String add() throws Exception {
		// // 封装到对象中
		// Role role = new Role();
		// role.setName(model.getName());
		// role.setDescription(model.getDescription());
		//
		// // 保存到数据库
		// roleService.save(role);
		// >> 保存到数据库
		logger.debug("role add model---->" + model);
		roleService.save(model);

		out.print("添加角色成功！");
		out.flush();
		out.close();

		return null;
	}

	/** 删除 */
	public String delete() throws Exception {
		// logger.debug("删除的id是----》"+Arrays.asList(getDeletIds()));
		// 循环删除对应id的记录
		for (int i = 0; i < deletIds.length; i++) {
			roleService.delete(deletIds[i]);
		}

		out.print("删除记录成功！");
		out.flush();
		out.close();

		return null;
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		// 准备回显的数据
		Role role = roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role);

		return "editUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		// 1. 从数据库中取出原对象
		Role role = roleService.getById(model.getId());

		// 2. 设置要修改的属性
		role.setName(model.getName());
		role.setDescription(model.getDescription());

		// 3. 更新到数据库
		roleService.update(role);

		out.print("修改信息成功！");
		out.flush();
		out.close();

		return null;
	}

	/** 设置菜单权限页面 */
	public String setMenuPrivUI() throws Exception {
		// 准备回显的数据
		Role role = roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role);

		if (role.getSysMenus() != null) {
			sysMenuIds = new String[role.getSysMenus().size()];
			int index = 0;
			for (SysMenu priv : role.getSysMenus()) {
				sysMenuIds[index++] = priv.getId();
			}
		}

		// 准备菜单权限树
		String menuPrivTree = sysMenuService.getSysMenuPrivTree();
		logger.debug("菜单权限树：" + menuPrivTree);
		ActionContext.getContext().put("menuPrivTree", menuPrivTree);

		// 将 sysMenuIds 转化为字符串，放到栈顶
		String privIds = CommonUtils.arrayToListWithQuote(sysMenuIds, "\"", ",");
		logger.debug("菜单权限树ids：" + privIds);
		ActionContext.getContext().put("privIds", privIds);
		// ActionContext.getContext().put("privIds", "['02','0201']");
		return "setMenuPrivUI";
	}

	/** 设置权限 */
	public String setMenuPriv() throws Exception {
		// 1，从数据库中获取原对象
		Role role = roleService.getById(model.getId());
		logger.debug("设置的url对应的id："+Arrays.asList(sysMenuIds));
		
		// 2，设置要修改的属性
		List<SysMenu> SysMenuList = sysMenuService.getByIds(sysMenuIds);
		logger.debug("设置的菜单是："+SysMenuList);
		
		
		role.setSysMenus(new HashSet<SysMenu>(SysMenuList));

		// 3，更新到数据库
		roleService.update(role);

		out.print("设置权限成功！");
		out.flush();
		out.close();

		return null;
	}

	/**
	 * 得到所有的role，json形式
	 * 
	 * @return
	 */
	public String getRoleList() {
		String roleListJson = roleService.getRoleListJson();

		out.print(roleListJson);
		out.flush();
		out.close();

		return null;
	}

	// =======setters and getters
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String[] getSysMenuIds() {
		return sysMenuIds;
	}

	public void setSysMenuIds(String[] sysMenuIds) {
		this.sysMenuIds = sysMenuIds;
	}

	public Integer[] getDeletIds() {
		return deletIds;
	}

	public void setDeletIds(Integer[] deletIds) {
		this.deletIds = deletIds;
	}
}
