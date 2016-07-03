package com.wssettle.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wssettle.dao.AdminDao;
import com.wssettle.pojo.Admin;
import com.wssettle.service.AdminService;

@Service
public class AdminServiceImp implements AdminService{

	@Autowired
	private AdminDao adminDao;
	@Override
	public List<Admin> checkLogin(Admin admin) {
		return adminDao.select(admin);
	}
	@Override
	public boolean updatePassword(Admin admin) {
		return adminDao.updatePassword(admin);
	}

}
