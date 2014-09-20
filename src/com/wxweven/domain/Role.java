package com.wxweven.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wxweven
 * @date 2014年9月15日
 * @version 1.0
 * @email wxweven@163.com
 * @blog http://wxweven.blog.163.com/
 * @Copyright: Copyright (c) wxweven 2014
 * @Description:角色实体
 */
public class Role  implements java.io.Serializable{
	private static final long serialVersionUID = 3641990234085269683L;
	
	private Integer id;//角色id
	private String name;//角色名
	private String description;//角色备注
	
	private Set<User> users = new HashSet<User>();//角色所关联的用户
	private Set<SysMenu> sysMenus = new HashSet<SysMenu>();//角色所关联的菜单

	//===========getter and setter
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<SysMenu> getSysMenus() {
		return sysMenus;
	}

	public void setSysMenus(Set<SysMenu> sysMenus) {
		this.sysMenus = sysMenus;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", name=" + name + "]";
	}
	
	
}
