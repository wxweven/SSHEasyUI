package com.wxweven.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.wxweven.base.BaseAction;
import com.wxweven.domain.User;
import com.wxweven.utils.JsonLibDateProcessor;
import com.wxweven.utils.JsonLibUserProcessor;

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

	private String returnMesg = null;
	private String usercaptcha;// 验证码参数
	private String newPass;// 用户需要更改的新密码

	/** 与 User 关联的 Department 以及 UserGroup，只需要持有 id 属性 */
	private String departmentId;
	private String userGroupId;

	private Integer[] deletIds;// 要被删除的记录的id
	private Integer[] initPasswdIds;// 要被初始化密码的记录的id

	/** 分页 排序参数 */
	private String page;// 当前页
	private String rows;// 每页记录数
	private String sort;// 排序的字段
	private String order;// ASC 或者 desc

//============用户登录相关============
	/** 登录页面 */
	public String loginUI() throws Exception {
		return "loginUI";
	}

	/** 登录验证 */
	public String login() throws Exception {
		// 1. 检查验证码是否正确
		if (!checkCaptcha()) {
			// addFieldError("login", "验证码不正确！");
			returnMesg = "验证码不正确！";
			out.print(returnMesg);
			out.flush();
			out.close();
			return null;
		}

		// 2. 检查用户名和密码是否正确
		User user = userService.findByLoginNameAndPassword(model.getLoginName(), model.getPassword());
		logger.debug("user：" + user);
		if (user == null) {
			returnMesg = "用户名或密码不正确！";
			out.print(returnMesg);
			out.flush();
			out.close();
			return null;
		} else {
			// 1. 将登录用户存入 session
			ActionContext.getContext().getSession().put("user", user);
//			ActionContext.getContext().getApplication().put("user", );
			
			// 2. 更改用户的登录时间为当前时间
			user.setLastLoginTime(new Date());
			userService.update(user);// 将更改同步到数据库

			// 3. 返回数据给前台
			out.print("success");
			out.flush();
			out.close();
			return null;
		}
	}

	/**
	 * 检查session 中的验证码和 表单中请求的验证码是否一致
	 * 
	 * @return 一致返回 true,否则返回false
	 */
	public boolean checkCaptcha() {
		// 1. 获取context里面的验证码是否启用配置参数
		String isCaptchaEnable = ServletActionContext.getServletContext().getInitParameter("isCaptchaEnable");
		if (isCaptchaEnable.equals("false")) {
			return true;// 如果配置不启用验证码，则始终返回true
		}

		// 2. 如果启用了验证码，则坚称session中的验证码是否与请求参数中的验证码一致
		String sessionCaptcha = request.getSession().getAttribute("randomStr").toString();// 获取session中保存的验证码
		String paramCaptcha = this.getUsercaptcha();// 获取 param 参数中的验证码

		logger.debug("session中的验证码" + sessionCaptcha);
		logger.debug("param中的验证码" + paramCaptcha);

		return sessionCaptcha.equals(paramCaptcha);
		// String captcha =
		// ActionContext.getContext().getSession().get("randomStr").toString();
	}

	/** 注销 */
	public String logout() throws Exception {
		// 注销：从 session 里面删除 user
		ActionContext.getContext().getSession().remove("user");
		return "loginUI";// 注销后，回到登录界面
	}

	/** 修改用户密码 */
	public String changePWD() throws Exception {
		// 1. 从session中取出原对象
		User user = (User) request.getSession().getAttribute("user");
		// 2. 设置要修改的密码, 新密码值由post请求传递（要使用MD5摘要）
		String md5Digest = DigestUtils.md5Hex(getNewPass());
		user.setPassword(md5Digest);
		logger.debug("new password:" + getNewPass());

		// 3. 更新到数据库
		userService.update(user);

		// 4. 返回提示信息给前台
		out.print(getNewPass());
		out.flush();
		out.close();

		return null;
	}
	
	/** 初始化密码为123456 */
	public String initPasswd() throws Exception {

		// 循环操作对应id的记录
		for (int i = 0; i < initPasswdIds.length; i++) {

			// 1，从数据库中取出原对象
			User user = userService.getById(initPasswdIds[i]);

			// 2，设置要修改的属性（要使用MD5摘要）
			String md5Digest = DigestUtils.md5Hex("123456");
			user.setPassword(md5Digest);

			// 3，更新到数据库
			userService.update(user);
		}

		out.print("初始化密码成功！");
		out.flush();
		out.close();
		return null;
	}

//===============用户菜单=================
	/**
	 * 获取登录用户的菜单列表，返回 json
	 */
	public String menu() throws Exception {
		String menus = "";
		// 1. 获取已登录的session中的user
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			// 2. 根据user 来获取菜单
			menus = userService.getUserMenu(user);
		}

		// 3. 返回用户菜单
		out.print(menus);
		out.flush();
		out.close();

		return null;
	}

//=================用户管理================	
	/** 默认显示list.jsp页面 */
	public String toList() throws Exception {
		ActionContext.getContext().put("jspGridTitle", "用户列表");
		return "list";
	}
	
	/** 获取用户列表数据 */
	public String list() throws Exception {
		// 1. 根据分页，排序，查询条件等来获取用户列表
		List<User> userList = userService.findAll(getPage(), getRows(), getSort(), getOrder(), getModelConditions());

		// 2. 获得符合条件的用户的总数(不带分页)
		int totalCount = userService.totalCount();

		// 3. 过滤掉无关的关联属性"department", "userGroup"，否则容易引起死循环异常
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "department", "userGroup" });
		// 对从数据库中取回的字段过滤
		config.registerJsonValueProcessor(Date.class, JsonLibDateProcessor.instance);
		config.registerJsonValueProcessor(String.class, JsonLibUserProcessor.instance);

		// 4. 根据list得到json字符串
		// JSONArray jsonArray = JSONArray.fromObject(userList, config);
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

	/** 添加页面 */
	public String addUI() throws Exception {
		return "addUI";
	}

	/** 添加 */
	public String add() throws Exception {
		// 封装到对象中（当model是实体类型时，也可以使用model，但要设置未封装的属性）
		// >> 设置所属部门
		model.setDepartment(departmentService.getById(departmentId));
		// >> 设置关联的用户组

		// >> 密码要使用MD5摘要加密
		String md5Digest = DigestUtils.md5Hex(model.getPassword());
		model.setPassword(md5Digest);

		// >> 保存到数据库
		logger.debug("user add model---->" + model);
		userService.save(model);

		out.print("添加用户成功！");
		out.flush();
		out.close();

		return null;
	}

	/** 删除 */
	public String delete() throws Exception {
		// logger.debug("删除的id是----》"+Arrays.asList(getDeletIds()));
		// 循环删除对应id的记录
		for (int i = 0; i < deletIds.length; i++) {
			userService.delete(deletIds[i]);
		}

		out.print("删除记录成功！");
		out.flush();
		out.close();

		return null;
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		// 准备回显的数据
		User user = userService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(user);
		if (user.getDepartment() != null) {
			departmentId = user.getDepartment().getId();
			ActionContext.getContext().getValueStack().push(departmentId);
		}

		return "editUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		// 1. 从数据库中取出原对象
		User user = userService.getById(model.getId());

		// 2. 设置要修改的属性,但是有些属性，如loginName,realName，密码等不能直接修改
		user.setGender(model.getGender());
		user.setPhoneNumber(model.getPhoneNumber());
		user.setEmail(model.getEmail());
		user.setDescription(model.getDescription());
		// >> 设置所属部门
		user.setDepartment(departmentService.getById(departmentId));

		// 3. 更新到数据库
		userService.update(user);

		out.print("修改信息成功！");
		out.flush();
		out.close();

		return null;
	}

	/** 检查loginName是否存在 */
	public String isExist() throws Exception {
		String retMsg = "false";
		boolean exist = userService.isExist(model.getLoginName());
		if (exist) {
			retMsg = "true";
		}

		out.print(retMsg);
		out.flush();
		out.close();

		return null;
	}
	
	/** 导出Excel */
	public String export() throws Exception {
		logger.debug("111111111111");
		//1. 生成Excel
		userService.excelWriter();
		return null;
	}
	
	
//===============在线用户管理============
	/** 默认显示onlineList.jsp页面 */
	public String toOnlineList() throws Exception {
		ActionContext.getContext().put("jspGridTitle", "在线用户列表");
		return "onlineList";
	}
	
	public String onlineList() throws Exception {
		List<User> userList = new ArrayList<User>();
		logger.debug("---------3333-----------"+ActionContext.getContext().getApplication());
		
		return null;
	}
	

	// ---getters and setters

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

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	public Integer[] getDeletIds() {
		return deletIds;
	}

	public void setDeletIds(Integer[] deletIds) {
		this.deletIds = deletIds;
	}

	public Integer[] getInitPasswdIds() {
		return initPasswdIds;
	}

	public void setInitPasswdIds(Integer[] initPasswdIds) {
		this.initPasswdIds = initPasswdIds;
	}
}
