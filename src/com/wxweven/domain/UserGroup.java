package com.wxweven.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author wxweven
 * @date 2014年8月23日
 * @version 1.0
 * @email wxweven@163.com
 * @blog http://wxweven.blog.163.com/
 * @Copyright: Copyright (c) wxweven 2014
 * @Description:用户主实体
 */
public class UserGroup implements java.io.Serializable {

	private static final long serialVersionUID = 4911087762533167397L;

	// 用户组基本信息
	private String id;// 用户组id
	private String name;// 用户组名字
	private String description;// 用户组描述

	// 用户组的关联信息
	private Set<User> users = new HashSet<User>();// 用户组关联的用户 User，一对多关系
	private UserGroup parent;// 用户组的父用户组Department，多对一关系
	private Set<UserGroup> children = new HashSet<UserGroup>();// 用户组的子用户组Department，一对多关系

	
	
	/**
	 * getters and setters
	 */
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public UserGroup getParent() {
		return parent;
	}

	public void setParent(UserGroup parent) {
		this.parent = parent;
	}

	public Set<UserGroup> getChildren() {
		return children;
	}

	public void setChildren(Set<UserGroup> children) {
		this.children = children;
	}

}
