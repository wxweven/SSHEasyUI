package com.wxweven.service.impl;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.wxweven.base.BaseServiceImpl;
import com.wxweven.domain.User;
import com.wxweven.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	Log logger = LogFactory.getLog(this.getClass());
	
	@Transactional(readOnly=true,
			isolation=Isolation.READ_COMMITTED)
	@Override
	public User findByLoginNameAndPassword(String loginName, String password) {
		// 使用密码的MD5摘要进行对比
		String md5Digest = DigestUtils.md5Hex(password);
		String hql = "FROM User u WHERE u.loginName=? AND u.password=? and u.userState=?";
		return (User) getSession().createQuery(hql)//
				.setParameter(0, loginName)//
				.setParameter(1, md5Digest)//
				.setParameter(2, "可用")//
				.uniqueResult();
	}

	@Transactional(readOnly=true,
			isolation=Isolation.READ_COMMITTED)
	@Override
	public String getUserMenu(User user) {
		String loginName = user.getLoginName();//获得登录用户的logiName
		StringBuilder resultStr = new StringBuilder();// 定义最终返回的 json 格式的字符串
		
		// 根据登录的用户名loginName，获取该用户对应的顶级菜单
		String topMenuSql = "select a.id,a.name,a.icon,a.url from wxw_sys_menu a where a.id in "//
				+ " (select md.menuId from wxw_menu_dept md where md.deptId in "//
				+ " (select u.departmentId from wxw_user u where loginName=:loginName))"//
				+ " order by orderNum asc";
		logger.debug("topMenuSql:"+topMenuSql);

		Query sqlQuery = getSession().createSQLQuery(topMenuSql)//
				.setParameter("loginName", loginName)//
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

		return resultStr.toString();
	}

	
	@Override
	public boolean isExist(String loginName) {
		String hql = "FROM User u WHERE u.loginName = ?";
		List list = getSession().createQuery(hql)//
				.setParameter(0, loginName)//
				.list();
		if(list == null || list.isEmpty()){//指定用户名的记录不存在
			return false;
		}
		
		return true;
	}
}
