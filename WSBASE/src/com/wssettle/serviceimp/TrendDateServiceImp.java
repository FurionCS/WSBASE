package com.wssettle.serviceimp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wssettle.dao.TrendDateDao;
import com.wssettle.service.TrendDateService;

@Service
public class TrendDateServiceImp implements TrendDateService {
	
	@Autowired
	private TrendDateDao trendDateDao;
	@Override
	public int GetRecordCountByUnit(String table, String dateStr, Date date,
			String unit) {
		return trendDateDao.GetRecordCountByUnit(table, dateStr, date, unit);
	}

}
