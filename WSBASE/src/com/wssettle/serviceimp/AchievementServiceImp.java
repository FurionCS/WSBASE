package com.wssettle.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wssettle.dao.AchievementDao;
import com.wssettle.pojo.Achievement;
import com.wssettle.pojo.Agent;
import com.wssettle.pojo.Page;
import com.wssettle.pojo.Performance;
import com.wssettle.service.AchievementService;

@Service
public class AchievementServiceImp implements AchievementService{
	@Autowired
	private AchievementDao achievementDao;
	@Override
	public List<Achievement> getAchievementByAgent(int agent) {
		return achievementDao.getAchievementByAgent(agent);
	}
	@Override
	public boolean updateAchievementMoney(Achievement achievement) {
		// TODO Auto-generated method stub
		return achievementDao.updateAchievementMoney(achievement);
	}
	@Override
	public Boolean addAchievement(Achievement achievement) {
		// TODO Auto-generated method stub
		return achievementDao.save(achievement);
	}
	@Override
	public Boolean updateAchievementPagent(int agid, double addmoney) {
		return achievementDao.updateAchievementPagent(agid, addmoney);
	}
	@Override
	public List<Performance> getPerformanceByPage(Page page) {
		return achievementDao.getPerformanceByPage(page);
	}
	@Override
	public int getPerformanceCount(Page page) {
		return achievementDao.getPerformanceCount(page);
	}
	@Override
	public boolean deleteAllAchievement() {
		return achievementDao.deleteAllAchievement();
	}
	@Override
	public List<Performance> getPerformanceByAgid(Agent agent) {
		// TODO Auto-generated method stub
		return achievementDao.getPerformanceByAgid(agent);
	}
	@Override
	public List<Performance> getPerformanceByAgpid(Agent agent) {
		// TODO Auto-generated method stub
		return achievementDao.getPerformanceByAgpid(agent);
	}
	@Override
	public List<Performance> getPerformanceAll() {
		return achievementDao.getPerformanceAll();
	}
	@Override
	public double getTopPersonMoney() {
		return achievementDao.getTopPersonMoney();
	}
	@Override
	public double getTopTeamMoney() {
		return achievementDao.getTopTeamMoney();
	}

}
