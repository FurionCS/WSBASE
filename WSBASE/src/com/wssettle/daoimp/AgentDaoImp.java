package com.wssettle.daoimp;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.wssettle.dao.AgentDao;
import com.wssettle.dao.BaseDao;
import com.wssettle.pojo.Agent;
import com.wssettle.pojo.Page;

@Repository
public class AgentDaoImp extends BaseDao implements AgentDao {

	/**
	 * 添加数据
	 * @author rjy
	 */
	@Override
	public Boolean save(Agent agent) {
		// TODO Auto-generated method stub
		try{
			getSession().save(agent);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * 通过代理名字获取代理id
	 * @author rjy
	 */
	@Override
	public int select(String pagwxnum) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery("From Agent where agwxnum=:pagwxnum");
		query.setString("pagwxnum",pagwxnum);
		List<Agent> lg=query.list();
		if(lg.size()>0){
			return lg.get(0).getAgid();
		}else{
			return -1;
		}
		
	}

	/**
	 * 得到代理列表分页
	 * 通过模糊查询微信号和姓名
	 * @author cs
	 * 
	 */
	@Override
	public List<Agent> getAgentByPage(Page page) {
		try{
		Query query=getSession().createQuery("From Agent where aglevel!=-1 and (agwxnum like ? or agname like ?)");
		query.setString(0, "%"+page.getStrWhere()+"%").setString(1, "%"+page.getStrWhere()+"%");
		List<Agent> la=query.setFirstResult((page.getPageIndex() - 1) * page.getPageSize()).setMaxResults(page.getPageSize()).list();
		return la;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * 得到分页代理总数量
	 * @author cs
	 */
	@Override
	public int getAgentCount(Page page) {
		try{
		Query query=getSession().createQuery("From Agent where aglevel!=-1 and(agwxnum like ? or agname like ?)");
		return query.setString(0, "%"+page.getStrWhere()+"%").setString(1, "%"+page.getStrWhere()+"%").list().size();
		}catch(Exception e){
			return 0;
		}
	}

	/**
	 * 得到代理通过id
	 * @author cs
	 */
	@Override
	public Agent getAgentByID(int id) {
		return (Agent)getSession().get(Agent.class, id);
	}

	/**
	 * 更新代理
	 * @author cs
	 */
	@Override
	public boolean updateAgent(Agent agent) {
		try{
			getSession().update(agent);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * 删除代理
	 */
	@Override
	public boolean deleteAgent(Agent agent) {
		try{
			getSession().delete(agent);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * 更新代理下属数量
	 * 如果isAdd是为真就增加，为假就减少
	 * 
	 */
	@Override
	public boolean updateAgentCount(int agent, boolean isAdd) {
		Agent ag=(Agent) getSession().load(Agent.class, agent);
		if(isAdd){
			ag.setAgcount(ag.getAgcount()+1);
		}else{
			if(ag.getAgcount()>0){
			ag.setAgcount(ag.getAgcount()-1);
			}
		}
		return true;
	}

	/**
	 * 得到直接下属代理
	 */
	@Override
	public List<Agent> getAgentByagpid(int id) {
		try{
			Query query=getSession().createQuery("from Agent where agpid=:pid");
			List<Agent> la=query.setInteger("pid", id).list();
			return la;
		}catch(Exception e){
			return null;
		}
	}


	/**
	 * 得到代理性别数量
	 */
	@Override
	public List<Object> getAgentSex() {
		try{
			List<Object> lo=getSession().createSQLQuery("select count(*) from t_wssettle_agent group by aggender order by aggender").list();
			return lo;
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * 得到代理地区分布图
	 */
	@Override
	public List<Agent> getAgentAddress() {
		
		List<Agent> la=new ArrayList<Agent>();
		List<Object> lo=getSession().createSQLQuery("select addr,count(*) addrnum from (select substr(agaddress,0,3) addr from t_wssettle_agent)  group by addr").list();
		
		Iterator it=lo.iterator();
		while(it.hasNext()){
			Object[] obj=(Object[]) it.next();
			Agent p=new Agent(Integer.parseInt(String.valueOf(obj[1])),String.valueOf(obj[0]));
			la.add(p);
		}
		return la;
	}
	


	/**
	 * 登录
	 */
	@Override
	public List<Agent> selectForLogin(Agent agent) {
		try{
		Query query = getSession().createQuery("from Agent where agwxnum =:logagwxnum and agpassword=:logagpassword");
		query.setString("logagwxnum", agent.getAgwxnum());
		query.setString("logagpassword", agent.getAgpassword());
		
		List<Agent> la = query.list();
		return la;
		}catch(Exception e){
			System.out.print(e.getMessage());
			return null;
		}
		
	}

	/**
	 * 修改自己的密码
	 */
	@Override
	public boolean updatePassword(Agent upagent) {
		// TODO Auto-generated method stub
		try{
			Agent agent = (Agent) getSession().get(Agent.class,upagent.getAgid());
			agent.setAgpassword(upagent.getAgpassword());
			return true;
		}catch(Exception e){
			return false;
		}
	}

	
	/** 
	 *获取记录总共条数 ,总代
	 * gagentNumber 数据库取出的数据总共有多少
	 * pageSize 一页显示的记录数
	 */
	@Override
	public int getGeneralAgentNumber(Page page) {
		Query query = getSession().createQuery("From Agent where (agname like ? or agwxnum like ?) and aglevel=0");
		int gagentNumber = query.setString(0, "%"+page.getStrWhere()+"%").setString(1,"%"+page.getStrWhere()+"%").list().size();
		return gagentNumber;
	}
/**
 * 分页查询，总代
 */
	@Override
	public List<Agent> getGeneralAgentByPage(Page page) {
		
		String hql = "From Agent where (agname like ? or agwxnum like ?) and aglevel=0";
		Query query = getSession().createQuery(hql).setString(0, "%"+page.getStrWhere()+"%").setString(1,"%"+page.getStrWhere()+"%");
		
		int pageSize = page.getPageSize();
		
		List<Agent> gagentlist = 
				query.setFirstResult((page.getPageIndex() - 1)*pageSize) //当前页的第一条数据的下标（0开始）
					 .setMaxResults(pageSize) ////当前页显示几条记录
					 .list();
		
		return gagentlist;
	}

	/**
	 * 得到代理当天新增人数
	 */
@Override
public List getAgentCountToday() {
	Date date=new Date();
	DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	String time=format.format(date);
	String sql=" SELECT cnt FROM (SELECT COUNT(*)  cnt,substr(to_char(agcreateday,'yyyy-MM-dd'),1,10)  day FROM T_WSSETTLE_AGENT  GROUP BY substr(to_char(agcreateday,'yyyy-MM-dd'),1,10))  b where day =:daytime";
	Query query=getSession().createSQLQuery(sql);
	List list=query.setString("daytime", time).list();
	
	 return list;
}

	@Override
	public List<Agent> getAgentByAgwxnum(String wxnum) {
		Query query=getSession().createQuery("From Agent where agwxnum=?");
		query.setString(0, wxnum);
		List<Agent> lg=query.list();
		return lg;
	}

	/**
	 * 得到年龄段范围数量
	 */
	@Override
	public int getCountAgeRange(int baseNum, int addNum) {
		Query query=getSession().createQuery("From Agent where agage>=:minage and agage<:maxage");
		List<Agent> la= query.setInteger("minage", baseNum).setInteger("maxage", baseNum+addNum).list();
		if(la!=null){
			return la.size();
		}else{
			return 0;
		}
	}

}
