package com.wxweven.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SessionTest {
	Log logger = LogFactory.getLog(this.getClass());
	ApplicationContext ac = null;
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
		logger.debug("sessionFactory------->"+sessionFactory);
	}
}
