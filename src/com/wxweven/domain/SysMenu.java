package com.wxweven.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author wxweven
 * @date 2014年8月25日
 * @version 1.0
 * @email wxweven@163.com
 * @blog http://wxweven.blog.163.com/
 * @Copyright: Copyright (c) wxweven 2014
 * @Description:菜单实体
 */
public class SysMenu implements java.io.Serializable {

	private static final long serialVersionUID = 1414367200246483282L;

	// 基本属性
	private String id;// 菜单的id
	private String name;// 菜单的名字
	private String icon;// 菜单的icon
	private String url;// 菜单的url
	private String level;// 菜单的级别
	private String description;// 菜单的描述
	private String orderNum;// 菜单的顺序

	// 关联关系
	private SysMenu parent;// 父菜单, 多对一
	private Set<SysMenu> children = new HashSet<SysMenu>();// 子菜单，一对多

	private Set<Role> roles = new HashSet<Role>(); // 菜单关联的角色，多对多

	
	// ========getters and setters
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public SysMenu getParent() {
		return parent;
	}

	public void setParent(SysMenu parent) {
		this.parent = parent;
	}

	public Set<SysMenu> getChildren() {
		return children;
	}

	public void setChildren(Set<SysMenu> children) {
		this.children = children;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", name=" + name + ", url=" + url + "]";
	}

	
}
