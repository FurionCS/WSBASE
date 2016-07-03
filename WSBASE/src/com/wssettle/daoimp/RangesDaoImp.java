package com.wssettle.daoimp;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.wssettle.dao.BaseDao;
import com.wssettle.dao.RangesDao;
import com.wssettle.pojo.Admin;
import com.wssettle.pojo.Ranges;

@Repository
public class RangesDaoImp extends BaseDao implements RangesDao{
	/**
	 * 保存范围
	 */
	@Override
	public Boolean save(Ranges ranges) {
		// TODO Auto-generated method stub
		try{
			getSession().save(ranges);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * 查找范围
	 */
	@Override
	public List<Ranges> select(Ranges ranges) {
		// TODO Auto-generated method stub
		try{
			Query query = getSession().createQuery("From Ranges where 1=1 order by minnum");
			List<Ranges> ranges1 = query.list();
			return ranges1;
			}catch(Exception e){
				System.out.print(e.getMessage());
			return null;
			}
	}
	/**
	 * 删除一条记录
	 */
	@Override
	public Boolean delete(Ranges deleteranges) {
		// TODO Auto-generated method stub
		try{
			Ranges ranges = (Ranges) getSession().load(Ranges.class, deleteranges.getRid());
			getSession().delete(ranges);
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}
	
	/**
	 * 更新记录
	 */
	@Override
	public boolean update(Ranges upfateRanges) {
		// TODO Auto-generated method stub
		try{
			Ranges adm = (Ranges) getSession().get(Ranges.class, upfateRanges.getRid());
			adm.setMinnum(upfateRanges.getMinnum());
			adm.setMaxnum(upfateRanges.getMaxnum());
			adm.setProportion(upfateRanges.getProportion());
			return true;
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * 得到范围通过业绩
	 */
	@Override
	public List<Ranges> getRangesByMoney(double money) {
		try{
		Query query=getSession().createQuery("from Ranges where minnum<=:minmoney and maxnum>:maxmoney");
		List<Ranges> lr=query.setDouble("minmoney", money).setDouble("maxmoney", money).list();
			return lr;
		}catch(Exception e){
			return null;
		}
	}

}
