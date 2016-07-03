package com.wssettle.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;








import com.opensymphony.xwork2.ActionSupport;
import com.wssettle.service.TrendDateService;

@Controller
@Scope("prototype")
public class TrendDateAction extends ActionSupport implements ServletResponseAware{
	@Autowired
	private TrendDateService trendDateService;
	private int num;
	private String unit;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String GetTrendData() throws IOException{
		ArrayList<long[]> ALSM=new ArrayList<long[]>();
		//ArrayList<long[]> ALSM2=new ArrayList<long[]>();
        JSONObject jb =new JSONObject();	
        try
        {
            for (int i = (num - 1); i > -1; i--)
            {
                Date time = new Date();
              // System.out.println("我获得当前时间："+time);
                int n = 0 - i;
                if (unit.equals("day")) { 
                	Calendar cal = Calendar.getInstance();  
                	cal.setTime(time);  
                	cal.add(Calendar.DATE, n);  
                	time=cal.getTime();  
             //   	 System.out.println("我获得当前时间day："+time);
                }
                else if (unit.equals("month")) { 
                	Calendar cal = Calendar.getInstance();  
                    cal.setTime(time);  
                    cal.add(Calendar.MONTH, n);  
                    time=cal.getTime();  
                //  System.out.println("我获得当前时间month："+time);
                  }
                else if (unit.equals("hour")) { 
                	Calendar cal = Calendar.getInstance();  
                    cal.setTime(time);  
                    cal.add(Calendar.HOUR, n);  
                    time=cal.getTime();  
                    //System.out.println("我获得当前时间hour："+time);
                   // System.out.println("我获得当前时间秒："+DateToInt(time));
                    
                    }
                else if (unit.equals("year")) { 
                	Calendar cal = Calendar.getInstance();  
                    cal.setTime(time);  
                    cal.add(Calendar.YEAR, n);  
                    time=cal.getTime();  
              //      System.out.println("我获得当前时间year："+time);
                }
                ALSM.add(new long[] { DateToInt(time) + (8 * 60 * 60 * 1000), trendDateService.GetRecordCountByUnit("T_WSSETTLE_AGENT", "agcreateday", time, unit) });
             //   ALSM2.add(new long[] { DateToInt(time) + (8 * 60 * 60 * 1000), trendDateService.GetRecordCountByUnit("T_WSSETTLE_AGENT", "agcreateday", time, unit) });
            }
            jb.put("code",1);
            jb.put("ALSM", ALSM);
           // jb.put("ALSM2", ALSM2);
         //   System.out.println("TSETE:"+jb.get("ALSM"));
        }
        catch (Exception err){
            jb.put("code", "0");
        }
        PrintWriter out=response.getWriter();
		out.print(jb);
		return null;
		
	}
	/**
	 * 时间转化,求得和计算机元年的时间差
	 * @param time
	 * @return long
	 * 
	 */
	public long DateToInt(Date time)
    {
		Calendar start=Calendar.getInstance();
		start.set(1970, 0, 1,8,0,0);//8是东八区
		Calendar endTime=Calendar.getInstance();
		endTime.setTime(time);
        return (long)(endTime.getTimeInMillis()-start.getTimeInMillis());
    }
	private ServletResponse response;
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		response=arg0;
	}

}
