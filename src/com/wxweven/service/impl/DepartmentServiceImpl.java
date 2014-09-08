package com.wxweven.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.wxweven.base.BaseServiceImpl;
import com.wxweven.domain.Department;
import com.wxweven.service.DepartmentService;

/**
 * 
 * @author wxweven
 * @date 2014年9月2日
 * @version 1.0
 * @email wxweven@163.com
 * @blog http://wxweven.blog.163.com/
 * @Copyright: Copyright (c) wxweven 2014
 * @Description: DepartmentService 的实现类
 */
@Service("departmentService")
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartmentService {

	@Transactional(readOnly=true,
			isolation=Isolation.READ_COMMITTED)
	@Override
	public String getDepartmentMTree() {
		
		String hql = "from Department where parentId is null";//获取顶级部门
		Query query = getSession().createQuery(hql);
		List resultList = query.list();
		Department topDept = (Department) resultList.get(0);
		
		return getDepartmentMTree(topDept);
	}
	
	@Override
	public String getDepartmentMTree(Department dept) {
		StringBuilder treeJsonStr = new StringBuilder();// 定义最终返回的 json 格式的字符串
		treeJsonStr.append("[");

		//利用递归构建部门树的json字符串
		treeJsonStr.append(getDept(dept));

		treeJsonStr.append("]");
		
		String returnStr = treeJsonStr.toString().replaceAll("\\[,", "\\[");
		logger.error("部门列表-->" + returnStr);
		
		return returnStr;
	}

	/**
	 * 根据传入的 部门 dept，递归构建部门树 json 并返回
	 * @param dept
	 * @return
	 */
	public String getDept(Department dept) {

		StringBuilder resultStr = new StringBuilder();
		//因为easyui前台，只认 "id":"101" 这种双引号的格式，而不认 'id':'101'格式，所以，这里要用双引号。。。。
		resultStr.append(",{\"id\":").append("\"").append(dept.getId()).append("\"").append(",");
		resultStr.append("\"text\":").append("\"").append(dept.getName()).append("\"");

		if (dept.getChildren() != null && !dept.getChildren().isEmpty()) {
			resultStr.append(",\"children\":[");
			// 循环构建子部门 json
			for (Department children : dept.getChildren()) {
				resultStr.append(getDept(children));
			}

			resultStr.append("]");
		}
		resultStr.append("}");
		
		return resultStr.toString();
	}

}
