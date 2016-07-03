package com.wssettle.daoimp;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.wssettle.dao.AdminDao;
import com.wssettle.dao.BaseDao;
import com.wssettle.pojo.Admin;

@Repository
public class AdminDaoImp extends BaseDao implements AdminDao{

	/**
	 * 管理员登录
	 */
	@Override
	public List<Admin> select(Admin admin) {
		try{
		Query query=getSession().createQuery("From Admin where adname=:name and adpassword=:password");
		query.setString("name", admin.getAdname());
		query.setString("password", admin.getAdpassword());
		List<Admin> la=query.list();
		return la;
		}catch(Exception e){
			System.out.print(e.getMessage());
		return null;
		}
	}
	/**
	 * 管理员修改密码
	 */
	@Override
	public boolean updatePassword(Admin admin) {
		try{
			Admin adm=(Admin) getSession().get(Admin.class, admin.getAdid());
			adm.setAdpassword(admin.getAdpassword());
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
