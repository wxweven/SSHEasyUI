package com.wxweven.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

//@Transactional注解可以被继承
//@Transactional注解对父类中声明的方法无效
@Transactional
@SuppressWarnings("unchecked")
public abstract class BaseServiceImpl<T> implements BaseService<T> {
	protected  Log logger = LogFactory.getLog(this.getClass());
	
	@Resource
	protected SessionFactory sessionFactory;

	protected Class<T> clazz; // 这是一个问题！

	public BaseServiceImpl() {
		// 通过反射得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];

		logger.debug("子类Dao:" + clazz.getName());
	}

	public void save(T entity) {
		getSession().save(entity);
	}

	public void update(T entity) {
		getSession().update(entity);
	}

	public void delete(Integer id) {
		Object obj = getSession().get(clazz, id);
		getSession().delete(obj);
	}

	public T getById(Integer id) {
		return (T) getSession().get(clazz, id);
	}

	public List<T> getByIds(Integer[] ids) {
		if (ids == null || ids.length == 0) {
			return Collections.EMPTY_LIST;
		}

		String hql = "FROM " + clazz.getSimpleName() + " WHERE id IN(:ids)";
		return getSession().createQuery(hql)//
				.setParameterList("ids", ids)//
				.list();
	}

	/**
	 * 查询所有 可以分页，可以排序
	 * @return
	 */
	public List<T> findAll(String pageStr, String rowsStr, String orderColumn, String order) {
		int rowsPerPage = Integer.parseInt(rowsStr);
		int start = (Integer.parseInt(pageStr) - 1) * rowsPerPage;
		
		String hql = "FROM " + clazz.getSimpleName() + " order by " + orderColumn + " " + order;
		logger.debug("limit:"+start+"--"+rowsPerPage);
		logger.debug("findAll hql:" + hql);
		return getSession().createQuery(hql)//
				.setFirstResult(start)// 指定起始记录
				.setMaxResults(rowsPerPage)// 指定每页显示的记录数
				.list();
	}
	
	/**
	 * 查询所有的记录条数
	 */
	@Override
	public int totalCount() {
		String hql = "from " + clazz.getSimpleName();

		return getSession().createQuery(hql)//
				.list().size();
	}

	/**
	 * 根据SQL来执行Query，并返回Hibernate执行后的Query
	 * @param sql
	 * @return
	 */
	public Query executeSQLQuery(String sql) {
		return getSession().createQuery(sql);
	}

	/**
	 * 获取当前可用的Session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
