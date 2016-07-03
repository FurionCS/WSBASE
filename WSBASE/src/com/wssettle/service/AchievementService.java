package com.wssettle.service;

import java.util.List;

import com.wssettle.pojo.Achievement;
import com.wssettle.pojo.Agent;
import com.wssettle.pojo.Page;
import com.wssettle.pojo.Performance;

public interface AchievementService {
	public List<Achievement> getAchievementByAgent(int agent);
	public boolean updateAchievementMoney(Achievement achievement);
	public Boolean addAchievement(Achievement achievement);
	public Boolean updateAchievementPagent(int agid,double addmoney);
	public List<Performance> getPerformanceByPage(Page page);
	public int getPerformanceCount(Page page);
	public boolean deleteAllAchievement();
	public List<Performance> getPerformanceByAgid(Agent agent);
	public List<Performance> getPerformanceByAgpid(Agent agent);
	public List<Performance> getPerformanceAll();
	public double getTopPersonMoney();
	public double getTopTeamMoney();
}
