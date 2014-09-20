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
 * @Description: 部门实体
 */
public class Department implements java.io.Serializable {
	private static final long serialVersionUID = -5718345247198080315L;

	// 部门基本信息
	private String id;// 部门id
	private String name;// 部门名字
	private String address;// 部门地址
	private String phoneNumber;// 部门电话
	private String email;// 部门邮箱
	private String deptState;// 部门是否可用的状态
	private String description;//部门描述

	// 部门的关联信息
	private Set<User> users = new HashSet<User>();// 部门关联的用户 User，一对多关系
	private Department parent;// 部门的父部门Department，多对一关系
	private Set<Department> children = new HashSet<Department>();// 部门的子部门Department，一对多关系
	
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDeptState() {
		return deptState;
	}

	public void setDeptState(String deptState) {
		this.deptState = deptState;
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

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public Set<Department> getChildren() {
		return children;
	}

	public void setChildren(Set<Department> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}

	

}
