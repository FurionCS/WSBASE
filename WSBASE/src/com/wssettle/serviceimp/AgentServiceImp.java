package com.wssettle.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wssettle.dao.AchievementDao;
import com.wssettle.dao.AgentDao;
import com.wssettle.pojo.Achievement;
import com.wssettle.pojo.Agent;
import com.wssettle.pojo.Page;
import com.wssettle.service.AgentService;

@Service
public class AgentServiceImp implements AgentService{

	@Autowired
	private AgentDao agentDao;
	
	@Autowired
	private AchievementDao achievementDao;
	@Override
	public Boolean addAgent(Agent agent) {
		return agentDao.save(agent);
	}

	@Override
	public int selectAgentid(String agname) {
		return agentDao.select(agname);
	}

	@Override
	public List<Agent> getAgentByPage(Page page) {
		return agentDao.getAgentByPage(page);
	}

	@Override
	public int getAgentCount(Page page) {
		return agentDao.getAgentCount(page);
	}

	@Override
	public Agent getAgentByID(int id) {
		return agentDao.getAgentByID(id);
	}

	@Override
	public boolean updateAgent(Agent agent) {
		return agentDao.updateAgent(agent);
	}

	@Override
	public boolean deleteAgent(Agent agent) {
		return agentDao.deleteAgent(agent);
	}

	@Override
	public boolean updateAgentCount(int agent, boolean isAdd) {
		return agentDao.updateAgentCount(agent, isAdd);
	}

	@Override
	public List<Agent> getAgentByagpid(int id) {
		return agentDao.getAgentByagpid(id);
	}


	@Override
	public List<Object> getAgentSex() {
		return agentDao.getAgentSex();
	}

	@Override
	public List<Agent> getAgentAddress() {
		return agentDao.getAgentAddress();
	}


	@Override
	public List<Agent> checkLogin(Agent agent) {
		// TODO Auto-generated method stub
		return agentDao.selectForLogin(agent);
	}

	@Override
	public boolean updatePassword(Agent agent) {
		// TODO Auto-generated method stub
		return agentDao.updatePassword(agent);
	}

	@Override
	public int getGeneralAgentNumber(Page page) {
		// TODO Auto-generated method stub
		return agentDao.getGeneralAgentNumber(page);
	}

	@Override
	public List<Agent> getGeneralAgentByPage(Page page) {
		return agentDao.getGeneralAgentByPage(page);
	}

	@Override
	public boolean updateAgentAndAch(Agent agent, int agpid) {
		agentDao.updateAgent(agent);
		agentDao.updateAgentCount(agpid, false);
		agentDao.updateAgentCount(agent.getAgpid(), true);
		List<Achievement> la=achievementDao.getAchievementByAgent(agent.getAgid());
		if(la!=null && la.size()>0){
			achievementDao.removeupdateAchievement(agpid, -la.get(0).getMoney());
			achievementDao.removeupdateAchievement(agent.getAgpid(), la.get(0).getMoney());
		}
		return true;
	}

	@Override
	public List getAgentCountToday() {
		return agentDao.getAgentCountToday();
	}

	@Override
	public List<Agent> getAgentByAgwxnum(String wxnum) {
		return agentDao.getAgentByAgwxnum(wxnum);
	}

	@Override
	public int getCountAgeRange(int baseNum, int addNum) {
		// TODO Auto-generated method stub
		return agentDao.getCountAgeRange(baseNum, addNum);
	}

}
