package com.wssettle.daoimp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wssettle.dao.BaseDao;
import com.wssettle.dao.TrendDateDao;

@Repository
public class TrendDateDaoImp extends BaseDao implements TrendDateDao{
	
	@Override
	public int GetRecordCountByUnit(String table, String dateStr, Date date,
			String unit) {
		if (table.isEmpty()||table.length()<1) return 0;
        if (dateStr.isEmpty()||dateStr.length()<1) return 0;
        if (unit.isEmpty()||unit.length()<1) return 0;
        if (date == null) date = new Date();
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
            str1 = "SELECT  cnt FROM (SELECT   COUNT(*)  cnt, substr(to_char(" + dateStr + ", 'yyyy-MM'), 1, 7)  day";
            str2 = "GROUP BY substr(to_char(" + dateStr + ", 'yyyy-MM'), 1, 7))  b";
            DateFormat format=new SimpleDateFormat("yyyy-MM");
            str3 = format.format(date);
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
            str1 = "SELECT  cnt FROM (SELECT   COUNT(*)  cnt, substr(to_char(" + dateStr + ",'yyyy-MM-dd HH24'), 1, 13)  day";
            str2 = "GROUP BY substr(to_char(" + dateStr + ",'yyyy-MM-dd HH24'), 1, 13))  b";
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH");
            str3 = format.format(date);
        }
		String sqlstr=str1 + " FROM " + table + " " + str2 + " where day = '" + str3 + "'";
		List<Object> lo=getSession().createSQLQuery(sqlstr).list();
        int count=0;
        if(lo.size()>0){
        	count=  Integer.parseInt(lo.get(0).toString());
        }
		return count;
	}

}
