package com.wssettle.service;

import java.util.Date;

public interface TrendDateService {
	public int GetRecordCountByUnit(String table, String dateStr, Date date, String unit);
}
