package com.wxweven.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wxweven.base.BaseAction;
import com.wxweven.domain.Department;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department> {

	private static final long serialVersionUID = 2894678137860541838L;
	
	public String getDepartmentTree() throws Exception {
		String deptTree = departmentService.getDepartmentMTree();
	
		out.print(deptTree);
		out.flush();
		out.close();
		
		return null;
		
	}

}