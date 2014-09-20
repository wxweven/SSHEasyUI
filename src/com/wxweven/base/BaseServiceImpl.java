package com.wxweven.base;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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
	
	protected String commonHql;//可重复利用的hql
	
	protected int totalCount;//总的记录数，不加 limit 子句的记录数

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
	
	public T getById(String id) {
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

	public List<T> getByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return Collections.EMPTY_LIST;
		}

		String hql = "FROM " + clazz.getSimpleName() + " WHERE id IN(:ids)";
		return getSession().createQuery(hql)//
				.setParameterList("ids", ids)//
				.list();
	}
	
	@Override
	public List<T> findAll() {
		return getSession().createQuery(//
				"FROM " + clazz.getSimpleName())//
				.list();
	}
	
	@Override
	public List<T> findAll(String page, String rows, String orderColumn, String order, Map<String, Object> conditions) {
		int currentPage = Integer.parseInt((page == null || page == "0") ? "1" : page);// 第几页
		int pageSize = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);// 每页多少行
		
		String hql = "FROM " + clazz.getSimpleName() + " t where 1=1 ";
		
		List<String> keys = new ArrayList<String>();
//		logger.debug("conditions--------:"+conditions);
		//如果查询条件 map 不为空
		if((conditions != null) && !conditions.isEmpty()) {
			for(String key : conditions.keySet()) {
				if(key != null){
					hql += " and t." + key +"=?";
					keys.add(key);//将查询条件的 key 加入 list
				}
			}
		}
		
		hql += " order by " + orderColumn + " " + order;//加上排序条件
		
//		logger.debug("findAll hql:" + hql);
		
		Query query = getSession().createQuery(hql);
		
		//循环设置值
		if(!keys.isEmpty()) {
			for(int i=0;i<keys.size();i++){
				query.setParameter(i, conditions.get(keys.get(i)));
			}
		}
		
		// 设置符合条件的不加limit 子句的总记录数
		this.setTotalCount(query.list().size());
		
		//分页设置
		query.setFirstResult((currentPage - 1) * pageSize)//指定起始记录数
			.setMaxResults(pageSize);//指定每页显示的记录数
		
		return query.list();
				
	}
	
	@Override
	public int totalCount() {
		return this.getTotalCount();
	}
//		String hql = "from " + clazz.getSimpleName()+ " t where 1=1 ";
//		
//		List<String> keys = new ArrayList<String>();
//		//如果查询条件 map 不为空
//		if(!conditions.isEmpty()) {
//			for(String key : conditions.keySet()) {
//				if(key != null){
//					hql += " and t." + key +"=?";
//					keys.add(key);//将查询条件的 key 加入 list
//				}
//			}
//		}
//
//		return getSession().createQuery(hql)//
//				.list().size();
//	}

	
	@Override
	public void excelWriter() {
		List users = findAll("1", "10", "id", "asc", null);
		
		try {
			// 打开文件
			WritableWorkbook book = Workbook.createWorkbook(new File("test.xls"));
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			WritableSheet sheet = book.createSheet("第一页 ", 0);
			// 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
			// 以及单元格内容为test
			Label label = new Label(0, 0, "test");

			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);

			/*
			 * 生成一个保存数字的单元格 必须使用Number的完整包路径，否则有语法歧义 单元格位置是第二列，第一行，值为789.123
			 */
			jxl.write.Number number = new jxl.write.Number(1, 0, 555.12541);
			sheet.addCell(number);

			// 写入数据并关闭文件
			book.write();
			book.close();

		} catch (Exception e) {
			System.out.println(e);
		}
				
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
	
	public String getCommonHql() {
		return commonHql;
	}

	public void setCommonHql(String commonHql) {
		this.commonHql = commonHql;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
