package com.wssettle.service;

import java.util.List;

import com.wssettle.pojo.Admin;
import com.wssettle.pojo.Agent;
import com.wssettle.pojo.Page;

public interface AgentService {
	public Boolean addAgent(Agent agent);
	public int selectAgentid(String agname);
	public List<Agent> getAgentByPage(Page page);
	public int getAgentCount(Page page);
	public Agent getAgentByID(int id);
	public boolean updateAgent(Agent agent);
	public boolean deleteAgent(Agent agent);
	public boolean updateAgentCount(int agent,boolean isAdd);
	public List<Agent> getAgentByagpid(int id);
	public List<Object> getAgentSex();
	public List<Agent> getAgentAddress();
	public List<Agent> checkLogin(Agent agent);
	public boolean updatePassword(Agent agent);
	public int getGeneralAgentNumber(Page page);
	public List<Agent> getGeneralAgentByPage(Page page);
	public boolean updateAgentAndAch(Agent agent,int agpid);
	public List getAgentCountToday();
	public List<Agent> getAgentByAgwxnum(String wxnum);
	public int getCountAgeRange(int baseNum,int addNum);
}

