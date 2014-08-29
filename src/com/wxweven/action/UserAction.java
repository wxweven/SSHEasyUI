package com.wxweven.action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.wxweven.base.BaseAction;
import com.wxweven.domain.User;

/**
 * 
 * @author wxweven
 * @date 2014年8月24日
 * @version 1.0
 * @email wxweven@163.com
 * @blog http://wxweven.blog.163.com/
 * @Copyright: Copyright (c) wxweven 2014
 * @Description:用户相关的Action，包括用户登录、注销，修改用户信息等
 */
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	private static final long serialVersionUID = -3945297891744479559L;
	
//	private transient User user;//当前登录的用户
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	private String returnMesg = null;
	private String usercaptcha;//验证码参数
	private String newPass;//用户需要更改的新密码
	
	private Long departmentId;
	private Long[] roleIds;
	
	/** 分页 排序参数 */
	private String page;//当前页
	private String rows;//每页记录数
	private String sort;//排序的字段
	private String order;//ASC 或者 desc
	
//	/** 删除 */
//	public String delete() throws Exception {
//		userService.delete(model.getId());
//		return "toList";
//	}
//
//	/** 添加页面 */
//	public String addUI() throws Exception {
//		// 准备数据, departmentList
//		List<Department> topList = departmentService.findTopList();
//		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
//		ActionContext.getContext().put("departmentList", departmentList);
//
//		// 准备数据, roleList
//		List<Role> roleList = roleService.findAll();
//		ActionContext.getContext().put("roleList", roleList);
//
//		return "saveUI";
//	}
//
//	/** 添加 */
//	public String add() throws Exception {
//		// 封装到对象中（当model是实体类型时，也可以使用model，但要设置未封装的属性）
//		// >> 设置所属部门
//		model.setDepartment(departmentService.getById(departmentId));
//		// >> 设置关联的岗位
//		List<Role> roleList = roleService.getByIds(roleIds);
//		model.setRoles(new HashSet<Role>(roleList));
//		// >> 设置默认密码为1234（要使用MD5摘要）
//		String md5Digest = DigestUtils.md5Hex("1234");
//		model.setPassword(md5Digest);
//
//		// 保存到数据库
//		userService.save(model);
//
//		return "toList";
//	}
//	/** 修改页面 */
//	public String editUI() throws Exception {
//		// 准备数据, departmentList
//		List<Department> topList = departmentService.findTopList();
//		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
//		ActionContext.getContext().put("departmentList", departmentList);
//
//		// 准备数据, roleList
//		List<Role> roleList = roleService.findAll();
//		ActionContext.getContext().put("roleList", roleList);
//
//		// 准备回显的数据
//		User user = userService.getById(model.getId());
//		ActionContext.getContext().getValueStack().push(user);
//		if (user.getDepartment() != null) {
//			departmentId = user.getDepartment().getId();
//		}
//		if (user.getRoles() != null) {
//			roleIds = new Long[user.getRoles().size()];
//			int index = 0;
//			for (Role role : user.getRoles()) {
//				roleIds[index++] = role.getId();
//			}
//		}
//
//		return "saveUI";
//	}
//
//	/** 修改 */
//	public String edit() throws Exception {
//		// 1，从数据库中取出原对象
//		User user = userService.getById(model.getId());
//
//		// 2，设置要修改的属性
//		user.setLoginName(model.getLoginName());
//		user.setName(model.getName());
//		user.setGender(model.getGender());
//		user.setPhoneNumber(model.getPhoneNumber());
//		user.setEmail(model.getEmail());
//		user.setDescription(model.getDescription());
//		// >> 设置所属部门
//		user.setDepartment(departmentService.getById(departmentId));
//		// >> 设置关联的岗位
//		List<Role> roleList = roleService.getByIds(roleIds);
//		user.setRoles(new HashSet<Role>(roleList));
//
//		// 3，更新到数据库
//		userService.update(user);
//
//		return "toList";
//	}
//
//	/** 初始化密码为1234 */
//	public String initPassword() throws Exception {
//		// 1，从数据库中取出原对象
//		User user = userService.getById(model.getId());
//
//		// 2，设置要修改的属性（要使用MD5摘要）
//		String md5Digest = DigestUtils.md5Hex("1234");
//		user.setPassword(md5Digest);
//
//		// 3，更新到数据库
//		userService.update(user);
//
//		return "toList";
//	}

	/** 登录页面 */
	public String loginUI() throws Exception {
		return "loginUI";
	}

	/** 登录验证 */
	public String login() throws Exception {
		//1. 检查验证码是否正确
		if(!checkCaptcha()){
//			addFieldError("login", "验证码不正确！");
			returnMesg = "验证码不正确！";
			out.print(returnMesg);
			out.flush();
			out.close();
			return null;
		}
		
			
		//2. 检查用户名和密码是否正确
		User user = userService.findByLoginNameAndPassword(model.getLoginName(), model.getPassword());
		logger.debug("user："+user);
		if (user == null) {
			returnMesg = "用户名或密码不正确！";
			out.print(returnMesg);
			out.flush();
			out.close();
			return null;
		} else {
			// 将登录用户存入 session
			ActionContext.getContext().getSession().put("user", user);
			out.print("success");
			out.flush();
			out.close();
			return null;
		}
	}
	
	/**
	 * 检查session 中的验证码和 表单中请求的验证码是否一致
	 * @return 一致返回 true,否则返回false
	 */
	public boolean checkCaptcha() {
		// 1. 获取context里面的验证码是否启用配置参数
		String isCaptchaEnable = ServletActionContext.getServletContext().getInitParameter("isCaptchaEnable");
		if (isCaptchaEnable.equals("false")) {
			return true;//如果配置不启用验证码，则始终返回true
		}

		//2. 如果启用了验证码，则坚称session中的验证码是否与请求参数中的验证码一致
		String sessionCaptcha = request.getSession().getAttribute("randomStr").toString();// 获取session中保存的验证码
		String paramCaptcha = this.getUsercaptcha();// 获取 param 参数中的验证码

		logger.debug("session中的验证码" + sessionCaptcha);
		logger.debug("param中的验证码" + paramCaptcha);

		return sessionCaptcha.equals(paramCaptcha);
		// String captcha = ActionContext.getContext().getSession().get("randomStr").toString();
	}

	/** 注销 */
	public String logout() throws Exception { 
		//注销：从 session 里面删除 user
		ActionContext.getContext().getSession().remove("user");
		return "loginUI";//注销后，回到登录界面
	}
	
	/** 修改用户密码 */
	public String changePWD() throws Exception {
		// 1. 从session中取出原对象
		User user = (User) request.getSession().getAttribute("user");
		// 2. 设置要修改的密码, 新密码值由post请求传递（要使用MD5摘要）
		String md5Digest = DigestUtils.md5Hex(getNewPass());
		user.setPassword(md5Digest);
		logger.debug("new password:"+getNewPass());
		
		// 3. 更新到数据库
		userService.update(user);
		
		// 4. 返回提示信息给前台
		out.print(getNewPass());
		out.flush();
		out.close();

		return null;
	}
	
	/** 
	 * 获取登录用户的菜单列表，返回 json
	 */
	public String menu() throws Exception { 
		String menus = "";
		//1. 获取已登录的session中的user
		User user = (User) request.getSession().getAttribute("user");
		if(user != null) {
		//2. 根据user 来获取菜单
			menus = userService.getUserMenu(user);
		}
		
		//3. 返回用户菜单
		out.print(menus);
		out.flush();
		out.close();

		return null;
	}

	/** 默认显示list.jsp页面 */
	public String toList() throws Exception {
		return "list";
	}
	
	/** 获取用户列表数据 */
	public String list() throws Exception {
		// 1. 根据分页，排序等条件来获取用户列表
		List<User> userList = userService.findAll(getPage(), getRows(), getSort(), getOrder());

		// 2. 获得用户的总数
		Integer totalCount = userService.totalCount();

		// 3. 过滤掉无关的关联属性"department", "userGroup"，否则容易引起死循环异常
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "department", "userGroup" });

		// 4. 根据list得到json字符串
		String resultStr = JSONArray.fromObject(userList, config).toString();

		// 5. 包装json字符串，符合easyui要求
		resultStr = wrapReturnJsonStr(resultStr, totalCount);

		logger.debug("userList--->" + resultStr);

		// 6. 返回数据给前台
		out.print(resultStr);
		out.flush();
		out.close();

		// 7. 由于直接返回数据给前台，而不需要跳转页面，这里直接返回null
		return null;// 返回 null 表示不用跳转页面
	}
	
	// ---getters and setters

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public String getUsercaptcha() {
		return usercaptcha;
	}

	public void setUsercaptcha(String usercaptcha) {
		this.usercaptcha = usercaptcha;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
