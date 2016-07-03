package com.wssettle.dao;

import java.util.Date;

public interface TrendDateDao {
	public int GetRecordCountByUnit(String table, String dateStr, Date date, String unit);
}
