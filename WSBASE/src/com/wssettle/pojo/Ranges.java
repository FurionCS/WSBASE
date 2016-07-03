package com.wssettle.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 范围表
 * @author cs
 *
 */
@Entity
@Table(name="T_WSSETTLE_RANGES")
public class Ranges {
	private int rid;            //范围id
	private double minnum;      //范围最小值   包括
	private double maxnum;      //范围最大值   不包括
	private double proportion;  //范围比例

	@Id
	@GeneratedValue
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public double getMinnum() {
		return minnum;
	}
	public void setMinnum(double minnum) {
		this.minnum = minnum;
	}
	public double getMaxnum() {
		return maxnum;
	}
	public void setMaxnum(double maxnum) {
		this.maxnum = maxnum;
	}
	public double getProportion() {
		return proportion;
	}
	public void setProportion(double proportion) {
		this.proportion = proportion;
	}
	
	public Ranges() {
		super();
	}
	public Ranges(int rid, double minnum, double maxnum, double proportion) {
		super();
		this.rid = rid;
		this.minnum = minnum;
		this.maxnum = maxnum;
		this.proportion = proportion;
	}
	
	

}
