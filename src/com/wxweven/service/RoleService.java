package com.wxweven.service;

import com.wxweven.base.BaseService;
import com.wxweven.domain.Role;

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
public interface RoleService extends BaseService<Role> {

	/**
	 * 得到所有角色的json格式
	 * @return
	 */
	String getRoleListJson();
}
