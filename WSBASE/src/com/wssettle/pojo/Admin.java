package com.wssettle.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 管理员表
 * @author cs
 *
 */
@Entity
@Table(name="T_WSSETTLE_ADMIN")
public class Admin {
	private int adid;               //管理员id
	private String adname;          //管理员名字
	private String adpassword;      //管理员密码
	private String adtel;           //管理员电话
	private Date lasttime;          //管理员最后登入时间
	private int power;              //管理员权限等级
	private String pic;             //管理员头像url
	
	@Id
	@GeneratedValue
	public int getAdid() {
		return adid;
	}
	public void setAdid(int adid) {
		this.adid = adid;
	}
	public String getAdname() {
		return adname;
	}
	public void setAdname(String adname) {
		this.adname = adname;
	}
	public String getAdpassword() {
		return adpassword;
	}
	public void setAdpassword(String adpassword) {
		this.adpassword = adpassword;
	}
	public String getAdtel() {
		return adtel;
	}
	public void setAdtel(String adtel) {
		this.adtel = adtel;
	}
	public Date getLasttime() {
		return lasttime;
	}
	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Admin() {
		super();
	}
	public Admin(int adid, String adname, String adpassword, String adtel,
			Date lasttime, int power, String pic) {
		super();
		this.adid = adid;
		this.adname = adname;
		this.adpassword = adpassword;
		this.adtel = adtel;
		this.lasttime = lasttime;
		this.power = power;
		this.pic = pic;
	}
}
