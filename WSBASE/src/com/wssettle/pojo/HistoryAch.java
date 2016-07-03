package com.wssettle.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 历史业绩表
 * @author cs
 *
 */
@Entity
@Table(name="T_WSSETTLE_HISTORYACH")
public class HistoryAch {
	private int haid;       //业绩id
	private int agentid;  //总代ID
	private double hmoney;  //团队业绩
	private	double hpersonmoney; //个人业绩
	private double hproportion;  //比例
	private Date hlasttime;      //最新更新时间
	
	@Id
	@GeneratedValue
	public int getHaid() {
		return haid;
	}
	public void setHaid(int haid) {
		this.haid = haid;
	}
	
	public int getAgentid() {
		return agentid;
	}
	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}
	public double getHmoney() {
		return hmoney;
	}
	public void setHmoney(double hmoney) {
		this.hmoney = hmoney;
	}
	public double getHpersonmoney() {
		return hpersonmoney;
	}
	public void setHpersonmoney(double hpersonmoney) {
		this.hpersonmoney = hpersonmoney;
	}
	public double getHproportion() {
		return hproportion;
	}
	public void setHproportion(double hproportion) {
		this.hproportion = hproportion;
	}
	public Date getHlasttime() {
		return hlasttime;
	}
	public void setHlasttime(Date hlasttime) {
		this.hlasttime = hlasttime;
	}
	
	
	
	

}
