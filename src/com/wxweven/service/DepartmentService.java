package com.wxweven.service;

import com.wxweven.base.BaseService;
import com.wxweven.domain.Department;

public interface DepartmentService extends BaseService<Department> {

	/**
	 * 得到整个Department的树状结构
	 * @return
	 */
	String getDepartmentTree();
	
	/**
	 * 得到指定部门的部门树 
	 * @param dept 指定的部门
	 * @return
	 */
	String getDepartmentTree(Department dept);
	
	
}
