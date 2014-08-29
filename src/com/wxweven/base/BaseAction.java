package com.wxweven.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.wxweven.service.UserService;

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	private static final long serialVersionUID = 937971571496347323L;
	
	//获取原生的 HttpServletRequest 和 HttpServletResponse
	protected HttpServletRequest request = null; 
	protected HttpServletResponse response = null;
	protected PrintWriter out = null;
	
	// =============== ModelDriven的支持 ==================
	protected T model;

	public BaseAction() {
		//子类 Action 会调用BaseAction 的构造方法，来完成一些初始化工作
		
		//1. 为请求和响应的 request 和 response, out对象赋值
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//2. 通过反射获取model的真实类型
		try {
			// 通过反射获取model的真实类型
			ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
			Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
			// 通过反射创建model的实例
			model = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public T getModel() {
		return model;
	}

	//将原始的list加上total，和 rows 属性
	protected String wrapReturnJsonStr(String listStr, int size) {
		return "{\"total\":" + size + ", \"rows\":" + listStr + "}";
	}
	
	
	// =============== Service实例的声明 ==================
//	@Resource
//	protected RoleService roleService;
//	@Resource
//	protected DepartmentService departmentService;
	@Resource
	protected UserService userService;
//	@Resource
//	protected PrivilegeService privilegeService;

}
