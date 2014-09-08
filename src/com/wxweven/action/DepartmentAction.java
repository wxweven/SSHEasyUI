package com.wxweven.action;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wxweven.base.BaseAction;
import com.wxweven.domain.Department;

@Controller
// 声明为Spring管理的Controller，默认的名字是类名的第一个字母小写
// @Controller 与 @Controller("departmentAction") 等价
@Scope("prototype")
// 声明作用域为 prototype，所有的action 作用域都是 prototype
public class DepartmentAction extends BaseAction<Department> {

	private static final long serialVersionUID = 2894678137860541838L;

	public String getDepartmentTree() throws Exception {
		String deptTree = departmentService.getDepartmentMTree();

		out.print(deptTree);
		out.flush();
		out.close();

		return null;
	}

	/** 添加页面 ,方法名以 UI 结尾*/
	public String addUI() throws Exception {
		return "addUI"; // 需要跳转到其他页面，就会返回struts action result中对应的 name 值
	}

	/** 添加 */
	public String add() throws Exception {
		// >> 保存到数据库
		departmentService.save(model); // 基本的CRUD操作，交给service完成

		// >> 返回数据给前台
		out.print("添加部门成功！");
		out.flush();
		out.close();

		return null; // 不需要跳转到其他页面，直接返回null
	}
}