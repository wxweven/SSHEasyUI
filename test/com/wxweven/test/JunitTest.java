package com.wxweven.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wxweven.domain.Department;
import com.wxweven.domain.SysMenu;
import com.wxweven.domain.User;
import com.wxweven.domain.UserGroup;

public class JunitTest {
	/**
	 * 这种方式获得的日志对象，是log4j专门的
	 * 扩展性不好，应该用后面一种“面向接口编程”的方式来获得日志对象
	 */
//	private static Logger logger = Logger.getLogger(JunitTest.class);
	
	/**
	 * 用日志工厂来产生日志记录对象
	 * 这样面向接口编程，而不是绑定在实现上
	 */
	private Log logger = LogFactory.getLog(this.getClass());
	private ApplicationContext ac = null;
	SessionFactory sessionFactory = null;
	Session session = null;
	
	{
		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
		session = sessionFactory.openSession();
		
	}
	
	public Session getSession(){
		return sessionFactory.openSession();
	}
	
	/** 测试sessionFactory，生成数据库表 */
	@Test
	public void testSessionFactory(){
		logger.debug(sessionFactory);
	}
	
	
	@Test
	public void testfff(){
		String str = "[,{111111";
		logger.debug("str------>"+str.replaceAll("\\[,", "\\["));

	}
	
	private String printNode(Department dept, int level) {
		
		StringBuilder resultStr = new StringBuilder();
		resultStr.append("{'id':").append("'").append(dept.getId()).append("'").append(",");
		resultStr.append("'text':").append("'").append(dept.getName()).append("'");
		
		if(dept.getChildren() != null && !dept.getChildren().isEmpty()){
			resultStr.append(",'children':[");
			//循环构建子部门 json
			for (Department children : dept.getChildren()) {
				resultStr.append(printNode(children, level + 1));
			}
			
			resultStr.append("],");
		}
		resultStr.append("},");
		
		
		return resultStr.toString();
	}

	
	@Test
	public void getDepartmentMTree() {
		session.beginTransaction();
		// --------------------------------------------
		
		StringBuilder resultStr = new StringBuilder();// 定义最终返回的 json 格式的字符串
		
		String hql = "from Department where parentId is null";//获取顶级部门
		Query query = getSession().createQuery(hql);
		List resultList = query.list();
		Department topDept = (Department) resultList.get(0);
		
		resultStr.append("[");
		
		resultStr.append(printNode(topDept,1));
		
		resultStr.append("]");
		
		logger.error("部门列表-->"+resultStr.toString());
		
		
		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}
	
	
	@Test
	public void getMenusByDept(){
		session.beginTransaction();
		// --------------------------------------------
	
//		String hql = "select new list(u.loginName, u.userState) from User u where u.id=:id ";//返回list
//		String hql = "select new map(u.loginName as loginName, u.userState as user_state) from User u where u.id=:id ";
//		String hql = "select t.userName,t.age from TestDomain t where id=1";
//		Query query = session.createQuery(hql)//
//				.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
//		List result = query.list();
		StringBuilder resultStr = new StringBuilder();// 定义最终返回的 json 格式的字符串
		
		// 根据登录的用户名loginName，获取该用户对应的顶级菜单
		String topMenuSql = "select a.id,a.name,a.icon,a.url from wxw_sys_menu a where a.id in "//
				+ " (select md.menuId from wxw_menu_dept md where md.deptId in "//
				+ " (select u.departmentId from wxw_user u where loginName=:loginName))"//
				+ " order by orderNum asc";
		logger.debug("topMenuSql---->"+topMenuSql);

		Query sqlQuery = getSession().createSQLQuery(topMenuSql)//
				.setParameter("loginName", "admin")//
				.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);

		List resultList = sqlQuery.list();

		if (!(resultList == null || resultList.size() == 0)) {
			for (int i = 0; i < resultList.size(); i++) {
				// 这里返回的 resultList 本身就类似于 Map，所以强转不会粗问题~~
				Map resultMap = (Map) resultList.get(i);
				// logger.debug("menu_icon:"+resultMap.get("menu_icon"));
				if (0 == i) {
					resultStr.append("{'menus':[");
				}
				resultStr.append("{'id':'" + resultMap.get("id") + "',");
				resultStr.append("'icon':'" + resultMap.get("icon") + "',");
				resultStr.append("'name':'" + resultMap.get("name") + "',");
				resultStr.append("'menus':");
				String subMenuSql = "select a.id,a.name,a.icon,a.url from wxw_sys_menu a "//
						+ " where a.level = :level and a.parentId = :pId";
				List resultSubMenuList = getSession().createSQLQuery(subMenuSql)//
						.setParameter("level", 2)//
						.setParameter("pId", resultMap.get("id"))//
						.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)//
						.list();
				resultStr.append(JSONArray.fromObject(resultSubMenuList));

				if (i != (resultList.size() - 1)) {
					resultStr.append("},");
				}

				if (i == (resultList.size() - 1)) {
					resultStr.append("}]}");
				}
			}
		}

		logger.debug("jsonMenus:" + resultStr);
		
		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}
	
	@Test
	public void testJSONArray(){
		List<String> list = new ArrayList<String>();
		list.add("111");
		list.add("2222");
		list.add("3333");
		list.add("4444");
		
		logger.debug(JSONArray.fromObject(list));
	}
	
	/**
	 * 添加用户
	 */
	@Test
	public void testAddUser(){
		session.beginTransaction();
		// --------------------------------------------
		Department dept = (Department) session.load(Department.class, "1");
		UserGroup userGroup = (UserGroup) session.load(UserGroup.class, "1");
		
		User user = new User();
		user.setDepartment(dept);
		user.setUserGroup(userGroup);
		user.setLoginName("admin");
		user.setPassword(DigestUtils.md5Hex("admin"));
		user.setUserState("可用");
		user.setGender("男");
		user.setLastLoginTime(new Date());
		user.setRealName("王贤稳");
		user.setPhoneNumber("18983677667");
		user.setEmail("wxweven@163.com");
		user.setDescription("系统超级用户");
		
		session.save(user);
		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}
	
	
	@Test
	/**
	 * 保存学院部门信息
	 */
	public void testAddDepartment(){
		session.beginTransaction();
		// --------------------------------------------

		// 添加部门数据
		Department dept = new Department();
		dept.setId("1");
		dept.setName("管理员");
		dept.setParent(null);//父部门为 null，表示自己是顶级部门
		dept.setAddress("重邮");
		dept.setEmail("wxweven@163.com");
		dept.setPhoneNumber("18983677667");
		dept.setDeptState("enable");
		dept.setDescription("超级部门");
		
		Department dept1 = new Department();
		dept1.setId("101");
		dept1.setName("计算机学院");
		dept1.setParent(dept);
		dept1.setAddress("二教");
		dept1.setEmail("jsj@cqupt.edu.cn");
		dept1.setPhoneNumber("02362460101");
		dept1.setDeptState("enable");
		dept1.setDescription("二级学院");
		
		Department dept2 = new Department();
		dept2.setId("102");
		dept2.setName("通信学院");
		dept2.setParent(dept);
		dept2.setAddress("逸夫楼");
		dept2.setEmail("tx@cqupt.edu.cn");
		dept2.setPhoneNumber("02362460102");
		dept2.setDeptState("enable");
		dept2.setDescription("二级学院");
		
		Department dept3 = new Department();
		dept3.setId("103");
		dept3.setName("自动化学院");
		dept3.setParent(dept);
		dept3.setAddress("行政楼");
		dept3.setEmail("zdh@cqupt.edu.cn");
		dept3.setPhoneNumber("02362460103");
		dept3.setDeptState("enable");
		dept3.setDescription("二级学院");
		
		Department dept4 = new Department();
		dept4.setId("104");
		dept4.setName("光电工程学院");
		dept4.setParent(dept);
		dept4.setAddress("一教");
		dept4.setEmail("gdgc@cqupt.edu.cn");
		dept4.setPhoneNumber("02362460104");
		dept4.setDeptState("enable");
		dept4.setDescription("二级学院");

		
		session.save(dept1); // 保存部门dept1
//		session.save(dept2); // 保存部门dept2
//		session.save(dept3); // 保存部门dept3
//		session.save(dept4); // 保存部门dept4
//		session.delete(dept);

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
		
	}
	
	
	/**
	 * 保存学院部门信息
	 */
	@Test
	public void testAddMajor(){
		session.beginTransaction();
		// --------------------------------------------

		// 获得对应的学院
		Department computerDept = (Department) session.load(Department.class, "104");
		
		Department computerMajor1 = new Department();
		computerMajor1.setId("10401");
		computerMajor1.setName("光电子");
		computerMajor1.setParent(computerDept);
		computerMajor1.setAddress("一教");
		computerMajor1.setEmail("gd_gdz@cqupt.edu.cn");
		computerMajor1.setPhoneNumber("02362460104");
		computerMajor1.setDeptState("enable");
		computerMajor1.setDescription("学院专业");
		
		Department computerMajor2 = new Department();
		computerMajor2.setId("10402");
		computerMajor2.setName("光纤科技");
		computerMajor2.setParent(computerDept);
		computerMajor2.setAddress("一教");
		computerMajor2.setEmail("gd_gxkj@cqupt.edu.cn");
		computerMajor2.setPhoneNumber("02362460104");
		computerMajor2.setDeptState("enable");
		computerMajor2.setDescription("学院专业");
		
		
		session.save(computerMajor1); // 保存部门dept1
		session.save(computerMajor2); // 保存部门dept2
//		session.save(computerMajor3); // 保存部门dept3
//		session.delete(dept);

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
		
	}
	
//	public void 

}
