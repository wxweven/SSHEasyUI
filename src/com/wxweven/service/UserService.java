package com.wxweven.service;

import com.wxweven.base.BaseService;
import com.wxweven.domain.User;

public interface UserService extends BaseService<User> {

	/**
	 * 根据登录名与密码查询用户
	 * 
	 * @param loginName
	 * @param password
	 *            明文密码
	 * @return
	 */
	User findByLoginNameAndPassword(String loginName, String password);

	/**
	 * 根据登录的用户 user，来获得该 user 的菜单
	 * 
	 * @param user
	 * @return
	 */
	String getUserMenu(User user);
}
