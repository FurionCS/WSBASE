package com.wssettle.dao;

import java.util.List;

import com.wssettle.pojo.Admin;
import com.wssettle.pojo.Ranges;

public interface RangesDao {
	public Boolean save(Ranges ranges);
	public List<Ranges> select(Ranges ranges);
	public Boolean delete(Ranges ranges);
	public boolean update(Ranges ranges);
	public List<Ranges> getRangesByMoney(double money);
}
