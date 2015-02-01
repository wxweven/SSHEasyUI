package com.wxweven.test;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import com.wxweven.domain.User;
import com.wxweven.service.UserService;
import com.wxweven.service.impl.UserServiceImpl;


public class UserTest extends SessionTest {
	
	/**
	 * 添加用户
	 */
	@Test
	public void testAddUser(){
		session.beginTransaction();
		// --------------------------------------------
		
		User user = new User();
		user.setLoginName("even");
		user.setPassword(DigestUtils.md5Hex("admin"));
		user.setUserState("可用");
		user.setGender("男");
		user.setLastLoginTime(new Date());
		user.setRealName("wxweven");
		user.setPhoneNumber("18983677667");
		user.setEmail("wxweven@163.com");
		user.setDescription("测试用户");
		
		session.save(user);
		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}
}
