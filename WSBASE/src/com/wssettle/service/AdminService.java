package com.wssettle.service;

import java.util.List;

import com.wssettle.pojo.Admin;

public interface AdminService {
	public List<Admin> checkLogin(Admin admin);
	public boolean updatePassword(Admin admin);
}
