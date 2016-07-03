package com.wssettle.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/**
 * 
 * @业绩表
 * @author cs
 *
 */
@Entity
@Table(name="T_WSSETTLE_ACHIEVENTMENT")
public class Achievement {
	private int aid;       //业绩id
	private Agent agent;  //总代ID
	private double money;  //团队业绩
	private	double personmoney; //个人业绩
	private double proportion;  //比例
	private Date lasttime;      //最新更新时间
	@Id
	@GeneratedValue
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	@OneToOne
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getProportion() {
		return proportion;
	}
	public void setProportion(double proportion) {
		this.proportion = proportion;
	}
	public Date getLasttime() {
		return lasttime;
	}
	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}
	public double getPersonmoney() {
		return personmoney;
	}
	public void setPersonmoney(double personmoney) {
		this.personmoney = personmoney;
	}
	
	public Achievement() {
		super();
	}
	public Achievement(Agent agent, double money, double proportion) {
		super();
		this.agent = agent;
		this.money = money;
		this.proportion = proportion;
	}
	
	
}
