package com.wssettle.pojo;

import java.io.Serializable;


public class Performance implements Serializable{
	private int agid;           //代理id
	private String agname;      //代理名字
	private String agwxnum;     //代理微信号
	private double money;       //代理团队业绩
	private double teambonus;        //代理团队奖金
	private double personmoney; //个人业绩
	private double personbonus; //个人业绩
	
	public Performance() {
		super();
	}

	public int getAgid() {
		return agid;
	}

	public void setAgid(int agid) {
		this.agid = agid;
	}

	public String getAgname() {
		return agname;
	}

	public void setAgname(String agname) {
		this.agname = agname;
	}

	public String getAgwxnum() {
		return agwxnum;
	}

	public void setAgwxnum(String agwxnum) {
		this.agwxnum = agwxnum;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getTeambonus() {
		return teambonus;
	}

	public void setTeambonus(double teambonus) {
		this.teambonus = teambonus;
	}

	public double getPersonmoney() {
		return personmoney;
	}

	public void setPersonmoney(double personmoney) {
		this.personmoney = personmoney;
	}

	public double getPersonbonus() {
		return personbonus;
	}

	public void setPersonbonus(double personbonus) {
		this.personbonus = personbonus;
	}

	public Performance(int agid, String agname, String agwxnum, double money,
			double teambonus, double personmoney, double personbonus) {
		super();
		this.agid = agid;
		this.agname = agname;
		this.agwxnum = agwxnum;
		this.money = money;
		this.teambonus = teambonus;
		this.personmoney = personmoney;
		this.personbonus = personbonus;
	}



}
