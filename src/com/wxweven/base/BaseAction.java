package com.wxweven.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.wxweven.service.DepartmentService;
import com.wxweven.service.RoleService;
import com.wxweven.service.SysMenuService;
import com.wxweven.service.UserService;

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	private static final long serialVersionUID = 937971571496347323L;

	protected Log logger = LogFactory.getLog(this.getClass()); // 日志记录

	// 获取原生的 HttpServletRequest 和 HttpServletResponse
	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;
	protected PrintWriter out = null;

	// =============== ModelDriven的支持 ==================
	protected T model;

	protected Map<String, String> conditions = new HashMap<String, String>();

	public BaseAction() {
		// 子类 Action 会调用BaseAction 的构造方法，来完成一些初始化工作

		// 1. 为请求和响应的 request 和 response, out对象赋值
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 2. 通过反射获取model的真实类型
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

	// 将原始的list加上total，和 rows 属性
	protected String wrapReturnJsonStr(String listStr, int size) {
		return "{\"total\":" + size + ", \"rows\":" + listStr + "}";
	}

	/**
	 * 通过反射拿到model属性和值 如果model 对应的属性 被设值了，就加入到conditions Map 中
	 * 
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> getModelConditions() throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();

		Method[] methods = model.getClass().getDeclaredMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			// 拿到所有的getter
			if (methodName.startsWith("get")) {
				String noGetName = methodName.substring(3);
				String noGetNameOne = noGetName.substring(0, 1).toLowerCase();
				String property = noGetNameOne + noGetName.substring(1);
				Object val = method.invoke(model);
//				logger.debug(property + "-->" + val);

				if (!isEmpty(val)) {
					// 如果val不为空，就设置到条件数组中
					conditions.put(property, val);
				}
			}
		}
		
//		logger.debug("conditions:"+conditions );
		return conditions;
	}

	/**
	 * 判断 val 对象是否为空：空对象，空字符串，空集合。。。。
	 * 
	 * @param val
	 * @return
	 */
	private boolean isEmpty(Object val) {
		// 空对象
		if (val == null)
			return true;
		// toString为空
		if (val.toString().equals("null"))
			return true;
		// 空字符串
		if (val.toString().equals(""))
			return true;
		// 空集合
		if (val.toString().equals("[]"))
			return true;

		return false;
	}

	// =============== Service实例的声明 ==================
	@Resource
	protected RoleService roleService; // 注入roleService

	@Resource
	protected DepartmentService departmentService; // 注入 departmentService

	@Resource
	protected UserService userService;

	@Resource
	protected SysMenuService sysMenuService;
	// @Resource
	// protected PrivilegeService privilegeService;
}
