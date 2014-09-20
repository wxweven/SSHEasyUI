package com.wxweven.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.wxweven.base.BaseServiceImpl;
import com.wxweven.domain.Role;
import com.wxweven.service.RoleService;

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
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

	@Override
	public String getRoleListJson() {
		List<Role> roleList = findAll();
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		for(int i =0; i<roleList.size(); i++) {
			Role role = roleList.get(i);
			if(i != 0){
				stringBuilder.append(",");
			}
			stringBuilder.append("{\"id\":");
			stringBuilder.append(role.getId()).append(",");
			stringBuilder.append("\"text\":");
			stringBuilder.append("\"").append(role.getName()).append("\"");
			stringBuilder.append("}");
		}
		
		stringBuilder.append("]");
		
		return stringBuilder.toString();
	}

}
