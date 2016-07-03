package com.wssettle.dao;

import java.util.List;

import com.wssettle.pojo.Admin;

public interface AdminDao {
	public List<Admin> select(Admin admin);
	public boolean updatePassword(Admin admin);
}
