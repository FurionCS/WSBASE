package com.wssettle.daoimp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.wssettle.dao.AchievementDao;
import com.wssettle.dao.BaseDao;
import com.wssettle.pojo.Achievement;
import com.wssettle.pojo.Admin;
import com.wssettle.pojo.Agent;
import com.wssettle.pojo.Page;
import com.wssettle.pojo.Performance;

@Repository
public class AchievementDaoImp extends BaseDao implements AchievementDao {

	@Override
	public List<Achievement> getAchievementByAgent(int agent) {
		try{
			Query query=getSession().createQuery("From Achievement where agent=?");
			query.setInteger(0, agent);
			return query.list();
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 更新业绩
	 */
	@Override
	public boolean updateAchievementMoney(Achievement upachievement) {
		try{
			Achievement ach = (Achievement) getSession().get(Achievement.class, upachievement.getAid());
			ach.setMoney(upachievement.getMoney());
			ach.setProportion(upachievement.getProportion());
			ach.setLasttime(new Date());
			ach.setPersonmoney(ach.getPersonmoney()+upachievement.getPersonmoney());
			return true;
		}catch(Exception e){
			return false;
		}
	}
/**
 * 新增业绩
 */
@Override
public Boolean save(Achievement achievement) {
	try{
		getSession().save(achievement);
		return true;
	}catch(Exception e){
		return false;
	}
}
@Override
public Boolean updateAchievementPagent(int agid, double addmoney) {
	try{Query query=getSession().createSQLQuery("{call updateAchievementPAgent(?,?)}");
	query.setInteger(0, agid).setDouble(1, addmoney);
	query.executeUpdate();
	return true;
	}catch(Exception e){
		return false;
	}
}
/**
 * 得到业绩列表
 */
@Override
public List<Performance> getPerformanceByPage(Page page) {
	try{
		List<Performance> lp=new ArrayList<Performance>();
		
		Query query=getSession().createSQLQuery("select * from V_WSSETTLE_PERFORMANCE where aglevel=0 and agstatus=1 and (agwxnum like ? or agname like ?)");
		query.setString(0, "%"+page.getStrWhere()+"%").setString(1, "%"+page.getStrWhere()+"%");
		List list= query.setFirstResult((page.getPageIndex() - 1) * page.getPageSize()).setMaxResults(page.getPageSize()).list();
	
		Iterator it=list.iterator();
		while(it.hasNext()){
			Object[] obj=(Object[]) it.next();
			Performance p=new Performance(Integer.parseInt(String.valueOf(obj[0])),(String)obj[1],(String)obj[3],Double.parseDouble(String.valueOf(obj[5])),Double.parseDouble(String.valueOf(obj[8])),Double.parseDouble(String.valueOf(obj[7])),Double.parseDouble(String.valueOf(obj[9])));
			lp.add(p);
		}
		 return lp;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
}
/**
 * 得到分页业绩条数
 */
@Override
public int getPerformanceCount(Page page) {
	try{Query query=getSession().createSQLQuery("select * from V_WSSETTLE_PERFORMANCE where aglevel=0 and agstatus=1 and (agwxnum like ? or agname like ?)");
	query.setString(0, "%"+page.getStrWhere()+"%").setString(1, "%"+page.getStrWhere()+"%");
	return query.list().size();
	}catch(Exception e){
		return 0;
	}
}
@Override
public boolean deleteAllAchievement() {
	try{
		getSession().createSQLQuery("delete from T_WSSETTLE_ACHIEVENTMENT where 1=1").executeUpdate();
		return true;
	}catch(Exception e){
		return false;
	}
}
/**
 * 根据代理id，得到业绩列表，用与前台展示
 */
@Override
public List<Performance> getPerformanceByAgid(Agent agent) {
	try{
		List<Performance> lp = new ArrayList<Performance>();
		
		Query query = getSession().createSQLQuery("select * from V_WSSETTLE_PERFORMANCE where agid=?");
		List list=query.setInteger(0, agent.getAgid()).list();
	
		Iterator it = list.iterator();
		while(it.hasNext()){
			Object[] obj=(Object[]) it.next();
			Performance p=new Performance(Integer.parseInt(String.valueOf(obj[0])),(String)obj[1],(String)obj[3],Double.parseDouble(String.valueOf(obj[5])),Double.parseDouble(String.valueOf(obj[8])),Double.parseDouble(String.valueOf(obj[7])),Double.parseDouble(String.valueOf(obj[9])));
			lp.add(p);
		}
		 return lp;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
}

/**
 * 根据agpid=传过来的agid来获取当前代理下属的团队业绩
 */
@Override
public List<Performance> getPerformanceByAgpid(Agent agent) {
	try{
		List<Performance> lp = new ArrayList<Performance>();
		
		Query query = getSession().createSQLQuery("select * from V_WSSETTLE_PERFORMANCE where agpid=?");
		List list = query.setInteger(0, agent.getAgid()).list();
	
		Iterator it = list.iterator();
		while(it.hasNext()){
			Object[] obj=(Object[]) it.next();
			Performance p=new Performance(Integer.parseInt(String.valueOf(obj[0])),(String)obj[1],(String)obj[3],Double.parseDouble(String.valueOf(obj[5])),Double.parseDouble(String.valueOf(obj[8])),Double.parseDouble(String.valueOf(obj[7])),Double.parseDouble(String.valueOf(obj[9])));
			lp.add(p);
		}
		 return lp;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
}

@Override
public boolean removeupdateAchievement(int id, double addmoney) {
	try{Query query=getSession().createSQLQuery("{call RemoveupdateAchievement(?,?)}");
	query.setInteger(0, id).setDouble(1, addmoney);
	query.executeUpdate();
	return true;
	}catch(Exception e){
		return false;
	}
}
/**
 * 得到所有业绩为了下载
 */
@Override
public List<Performance> getPerformanceAll() {
	List<Performance> lp=new ArrayList<Performance>();
	List list=getSession().createSQLQuery("select * from V_WSSETTLE_PERFORMANCE where aglevel=0 and agstatus=1").list();
	Iterator it=list.iterator();
	while(it.hasNext()){
		Object[] obj=(Object[]) it.next();
		Performance p=new Performance(Integer.parseInt(String.valueOf(obj[0])),(String)obj[1],(String)obj[3],Double.parseDouble(String.valueOf(obj[5])),Double.parseDouble(String.valueOf(obj[8])),Double.parseDouble(String.valueOf(obj[7])),Double.parseDouble(String.valueOf(obj[9])));
		lp.add(p);
	}
	return lp;
}
/**
 * 得到最高的个人业绩
 */
@Override
public double getTopPersonMoney() {
	List<Achievement> la= getSession().createQuery("from Achievement  order by personmoney desc").list();
	if(la.size()>0){
		return la.get(0).getPersonmoney();
	}
	else return 0;
}
/**
 * 得到最高的团队业绩
 */
@Override
public double getTopTeamMoney() {
	List<Achievement> la= getSession().createQuery("from Achievement  order by money desc").list();
	if(la.size()>0){
		return la.get(0).getMoney();
	}
	else return 0;
}


}
