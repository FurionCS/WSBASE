package com.wssettle.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wssettle.dao.RangesDao;
import com.wssettle.pojo.Ranges;
import com.wssettle.service.RangesService;

@Service
public class RangesServiceImp implements RangesService {
	@Autowired
	private RangesDao rangesDao;

	@Override
	public Boolean addRanges(Ranges ranges) {
		// TODO Auto-generated method stub
		return rangesDao.save(ranges);
	}

	@Override
	public List<Ranges> getAllRanges(Ranges ranges) {
		// TODO Auto-generated method stub
		return rangesDao.select(ranges);
	}

	@Override
	public Boolean deleteRanges(Ranges ranges) {
		// TODO Auto-generated method stub
		return rangesDao.delete(ranges);
	}

	@Override
	public boolean updateRanges(Ranges ranges) {
		// TODO Auto-generated method stub
		return rangesDao.update(ranges);
	}

	@Override
	public List<Ranges> getRangesByMoney(double money) {
		return rangesDao.getRangesByMoney(money);
	}
}
