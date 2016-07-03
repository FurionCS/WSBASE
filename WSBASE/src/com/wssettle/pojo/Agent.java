package com.wssettle.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 代理类
 * @author cs
 *
 */
@Entity
@Table(name="T_WSSETTLE_AGENT")
public class Agent {
	private int  agid;            //ID        *
	private String agwxnum;      //微信号
	private String agname;       //名字
	private String agtel;        //电话
	private String agidcard;     //身份证
	private int agpid;           //上级ID
	private int aglevel;          //等级  0为总代，1为一级代理。。。。     
	private String agauthorization;  //授权号
	private Date agcreateday;     //录入时间      *
	private String agpassword;    //代理密码
	private int agstatus;         //代理状态1为在职，0为不在        *
	private int agcount;         //下属代理数量        *
	private Date agdeadline;     	 //授权过期时间
	private int agtype;            //0为公司，1为个体户，2为个人
	private int aggender;          //性别 1：男0：女
	private String agaddress;     //地址
	private int agage;             //年龄
	
	@Id
	@GeneratedValue
	public int getAgid() {
		return agid;
	}
	public void setAgid(int agid) {
		this.agid = agid;
	}
	public String getAgwxnum() {
		return agwxnum;
	}
	public void setAgwxnum(String agwxnum) {
		this.agwxnum = agwxnum;
	}
	public String getAgname() {
		return agname;
	}
	public void setAgname(String agname) {
		this.agname = agname;
	}
	public String getAgtel() {
		return agtel;
	}
	public void setAgtel(String agtel) {
		this.agtel = agtel;
	}
	public String getAgidcard() {
		return agidcard;
	}
	public void setAgidcard(String agidcard) {
		this.agidcard = agidcard;
	}
	public int getAgpid() {
		return agpid;
	}
	public void setAgpid(int agpid) {
		this.agpid = agpid;
	}
	public int getAglevel() {
		return aglevel;
	}
	public void setAglevel(int aglevel) {
		this.aglevel = aglevel;
	}
	public String getAgauthorization() {
		return agauthorization;
	}
	public void setAgauthorization(String agauthorization) {
		this.agauthorization = agauthorization;
	}
	public Date getAgcreateday() {
		return agcreateday;
	}
	public void setAgcreateday(Date agcreateday) {
		this.agcreateday = agcreateday;
	}
	public String getAgpassword() {
		return agpassword;
	}
	public void setAgpassword(String agpassword) {
		this.agpassword = agpassword;
	}
	public void setAgstatus(int agstatus) {
		this.agstatus = agstatus;
	}
	public int getAgstatus() {
		return agstatus;
	}
	public void setAgcount(int agcount) {
		this.agcount = agcount;
	}
	public int getAgcount() {
		return agcount;
	}
	public Date getAgdeadline() {
		return agdeadline;
	}
	public void setAgdeadline(Date agdeadline) {
		this.agdeadline = agdeadline;
	}
	public int getAgtype() {
		return agtype;
	}
	public void setAgtype(int agtype) {
		this.agtype = agtype;
	}
	public int getAggender() {
		return aggender;
	}
	public void setAggender(int aggender) {
		this.aggender = aggender;
	}
	public String getAgaddress() {
		return agaddress;
	}
	public void setAgaddress(String agaddress) {
		this.agaddress = agaddress;
	}
	public int getAgage() {
		return agage;
	}
	public void setAgage(int agage) {
		this.agage = agage;
	}
	public Agent() {
		super();
	}
	public Agent(int agid, String agwxnum, String agname, String agtel,
			String agidcard, int agpid, int aglevel, String agauthorization,
			Date agcreateday, String agpassword, int agstatus, int agcount,
			Date agdeadline, int agtype, int aggender, String agaddress,
			int agage) {
		super();
		this.agid = agid;
		this.agwxnum = agwxnum;
		this.agname = agname;
		this.agtel = agtel;
		this.agidcard = agidcard;
		this.agpid = agpid;
		this.aglevel = aglevel;
		this.agauthorization = agauthorization;
		this.agcreateday = agcreateday;
		this.agpassword = agpassword;
		this.agstatus = agstatus;
		this.agcount = agcount;
		this.agdeadline = agdeadline;
		this.agtype = agtype;
		this.aggender = aggender;
		this.agaddress = agaddress;
		this.agage = agage;
	}
	public Agent(int agcount, String agaddress) {
		super();
		this.agcount = agcount;
		this.agaddress = agaddress;
	}
	
}
