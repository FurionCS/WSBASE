package com.wssettle.service;

import java.util.List;

import com.wssettle.pojo.Ranges;

public interface RangesService {
	public Boolean addRanges(Ranges ranges);
	public List<Ranges> getAllRanges(Ranges ranges);
	public Boolean deleteRanges(Ranges ranges);
	public boolean updateRanges(Ranges ranges);
	public List<Ranges> getRangesByMoney(double money);
}
