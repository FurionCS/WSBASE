package com.wssettle.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



import java.util.Date;

import com.wssettle.pojo.Agent;

/**
 * 趋势图
 * @author cs
 *
 */
public class TrendData {
 	Connection con=null;//第一步，初始化对象
 	PreparedStatement sql;	
 	Statement stmt;
	ResultSet rs;//结果集
	ArrayList<Agent> agList;	//短消息列表
	private int count=0; //记录数
	/**
	 * 通过unit获得记录数
	 * @param table  表名
	 * @param dateStr  日期列名
	 * @param date     当前时间
	 * @param unit     Year:Day:hour:month
	 * @return count
	 * 
	 */
	public int GetRecordCountByUnit(String table, String dateStr, Date date, String unit){
		
		if (table.isEmpty()||table.length()<1) return 0;
        if (dateStr.isEmpty()||dateStr.length()<1) return 0;
        if (unit.isEmpty()||unit.length()<1) return 0;
        if (date == null) date = new Date();
        count=0;
        String str1 = "", str2 = "", str3 = "";
        if (unit.equals("Year") || unit.equals("YEAR") || unit.equals("year"))
        {																		
            str1 = "SELECT  cnt FROM (SELECT   COUNT(*)  cnt, substr(to_char(" + dateStr + ", 'yyyy'), 1, 4)  day";
            str2 = "GROUP BY substr(to_char(" + dateStr + ", 'yyyy'), 1, 4))  b";
            DateFormat format=new SimpleDateFormat("yyyy");
            str3 = format.format(date);
        }
        else if (unit.equals("Month") || unit.equals("MONTH") ||unit.equals("month"))
        {																			
            str1 = "SELECTrmat(date)";
        }
        else if (unit.equals("Day") || unit.equals("DAY") || unit.equals("day"))
        {                                                         
            str1 = "SELECT  cnt FROM (SELECT   COUNT(*)  cnt, substr(to_char(" + dateStr + ", 'yyyy-MM-dd'), 1, 10)  day";
            str2 = "GROUP BY substr(to_char(" + dateStr + ", 'yyyy-MM-dd'), 1, 10))  b";
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            str3 = format.format(date);
        }
        else if (unit.equals("Hour") || unit.equals("hour") || unit.equals("HOUR"))
        {                                                   
            str1 = "SELECT  cnt FROM (SELECT   COUNT(*)  cnt, substr(to_char(" + dateStr + ",'yyyy-MM-dd HH'), 1, 13)  day";
            str2 = "GROUP BY substr(to_char(" + dateStr + ",'yyyy-MM-dd HH'), 1, 13))  b";
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH");
            str3 = format.format(date);
        }
       
        //连接数据库 
	//	con=UtilJDBC.getConnection();
		
		String sqlstr=str1 + " FROM " + table + " " + str2 + " where day = '" + str3 + "'";
		try {
		///	System.out.println("我已经进入底层:"+sqlstr);
			sql=con.prepareStatement(sqlstr);
			
			rs = sql.executeQuery();
			
			while(rs.next()){
				count++;
			}      
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	//	System.out.println("我获得趋势图记录数："+count);
		return count;
		
	}
}
