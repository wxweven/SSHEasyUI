package com.wxweven.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author wxweven
 * @date 2014年8月23日
 * @version 1.0
 * @email wxweven@163.com
 * @blog http://wxweven.blog.163.com/
 * @Copyright: Copyright (c) wxweven 2014
 * @Description: 用户实体
 */
public class User implements java.io.Serializable {
	private static final long serialVersionUID = 9012904753863651113L;

	// 用户的基本信息
	private Integer id;// 用户id，用Integer类，而不要用 int类型
	
	private String loginName; // 登录名
	private String password; // 密码
	
	private String userState; // 用户状态是否可用
	private Date lastLoginTime;// 用户最后登录的时间
	private String realName; // 真实姓名
	private String gender; // 性别
	private String phoneNumber; // 电话号码
	private String email; // 电子邮件
	private String description; // 说明

	// 用户的关联信息
	private Department department;// 用户所属的部门，多对一关系（多个 User 属于一个 Department ）
	private UserGroup userGroup;// 用户所属的用户组，多对一关系（多个 User 属于一个 UserGroup ）
	// private Set<Department> roles = new HashSet<Department>();//用户的角色，一对多关系
	// （一个 User 有多个角色 Department）

	/**
	 * 判断本用户是否有指定名称的权限
	 * 
	 * @param name
	 * @return
	 */
	// public boolean hasPrivilegeByName(String name) {
	// // 超级管理有所有的权限
	// if (isAdmin()) {
	// return true;
	// }
	//
	// // 普通用户要判断是否含有这个权限
	// for (Department role : roles) {
	// for (Privilege priv : role.getPrivileges()) {
	// if (priv.getName().equals(name)) {
	// return true;
	// }
	// }
	// }
	// return false;
	// }

	/**
	 * 判断本用户是否有指定URL的权限
	 * 
	 * @param privUrl
	 * @return 占位符，默认这里所有权限判断都返回true
	 */
	public boolean hasPrivilegeByUrl(String privUrl) {
		 // 超级管理有所有的权限
		 if (isAdmin()) {
			return true;
		 }
		 // TODO 要对权限做判断
		 return true;
	}
	
	// public boolean hasPrivilegeByUrl(String privUrl) {
	// // 超级管理有所有的权限
	// if (isAdmin()) {
	// return true;
	// }
	//
	// // >> 去掉后面的参数
	// int pos = privUrl.indexOf("?");
	// if (pos > -1) {
	// privUrl = privUrl.substring(0, pos);
	// }
	// // >> 去掉UI后缀
	// if (privUrl.endsWith("UI")) {
	// privUrl = privUrl.substring(0, privUrl.length() - 2);
	// }
	//
	// // 如果本URL不需要控制，则登录用户就可以使用
	// Collection<String> allPrivilegeUrls = (Collection<String>)
	// ActionContext.getContext().getApplication().get("allPrivilegeUrls");
	// if (!allPrivilegeUrls.contains(privUrl)) {
	// return true;
	// } else {
	// // 普通用户要判断是否含有这个权限
	// for (Department role : roles) {
	// for (Privilege priv : role.getPrivileges()) {
	// if (privUrl.equals(priv.getUrl())) {
	// return true;
	// }
	// }
	// }
	// return false;
	// }
	// }

	/**
	 * 判断本用户是否是超级管理员
	 * 
	 * @return
	 */
	public boolean isAdmin() {
		return "admin".equals(loginName);
	}

	//=====getters and setters
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	//========
	@Override
	public String toString() {
		return "User [id=" + id + ", loginName=" + loginName + ", password=" + password + ", userState=" + userState
				+ ", lastLoginTime=" + lastLoginTime + ", realName=" + realName + ", gender=" + gender
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + ", description=" + description + "]";
	}

}
