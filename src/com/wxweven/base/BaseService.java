package com.wxweven.base;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
/**
 * 
 * @author wxweven
 * @File: BaseDao.java
 * @Description: 最基础的DAO接口，定义增删改查接口
 * @date 2014年7月8日
 * @version 1.0
 * @email wxweven@163.com
 * @blog http://wxweven.blog.163.com/
 * <p>Copyright: Copyright (c) wxweven 2014</p>
 */
public interface BaseService<T> {

	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	void save(T entity);

	/**
	 * 删除实体
	 * 
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	void update(T entity);

	/**
	 * 查询实体
	 * 
	 * @param id Integer类型
	 * @return
	 */
	T getById(Integer id);
	
	/**
	 * 查询实体
	 * 
	 * @param id String 类型
	 * @return
	 */
	T getById(String id);

	/**
	 * 查询实体
	 * 
	 * @param ids
	 * @return
	 */
	List<T> getByIds(Integer[] ids);

	/**
	 * 查询所有,可以分页，排序，where条件
	 * @param page
	 * @param rows
	 * @param orderColumn
	 * @param order
	 * @return
	 */
	List<T> findAll(String page, String rows, String orderColumn, String order, Map<String, Object> conditions);
	
	/**
	 * 查询所有记录的条数，没有分页限制，有where条件
	 * @return
	 */
	int totalCount();
	
	/**
	 * 根据SQL来执行Query，并返回Hibernate执行后的Query
	 * @param sql
	 * @return
	 */
	Query executeSQLQuery(String sql);
}
