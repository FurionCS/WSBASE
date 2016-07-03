package com.wssettle.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.wssettle.pojo.Achievement;
import com.wssettle.pojo.Agent;
import com.wssettle.pojo.Page;
import com.wssettle.pojo.Performance;
import com.wssettle.service.AchievementService;
import com.wssettle.util.ExportUtils;
import com.wssettle.util.JsonUtil;

@Controller
@Scope("prototype")
public class AchievementAction  extends ActionSupport implements SessionAware,ServletResponseAware{
	@Autowired
	private AchievementService achievementService;
	private Achievement achievement;
	private Agent agent;
	
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public Achievement getAchievement() {
		return achievement;
	}
	public void setAchievement(Achievement achievement) {
		this.achievement = achievement;
	}
	
	public double addMoney;
	public double getAddMoney() {
		return addMoney;
	}
	public void setAddMoney(double addMoney) {
		this.addMoney = addMoney;
	}
	private Page page;
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	/**
	 * 根据id查找他的业绩
	 * @return 
	 * @throws IOException
	 */
	public String getAchievementByID() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		
		List<Achievement> ac = achievementService.getAchievementByAgent(achievement.getAgent().getAgid());
		if(ac!=null && ac.size()>0){
			jb.put("achievement",ac.get(0));
			jb.put("code", 1);
		}
		else{
			jb.put("code", 0);
		}
		
		PrintWriter out = response.getWriter();
		out.print(jb);
		out.flush();
		out.close();
		return null;
		
	}
	
	/**
	 * 更新业绩
	 * @return
	 * @throws IOException 
	 */
	public String updateAchievementMoney() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		if(achievement.getAid()!=0){
			//获取当前agid,把新的业绩加上去
			achievement.setMoney(achievement.getMoney()+addMoney);
			achievement.setPersonmoney(addMoney);
			try{
				//调用update方法更新数据库信息
				achievementService.updateAchievementMoney(achievement);
				achievementService.updateAchievementPagent(achievement.getAgent().getAgid(), addMoney);
				jb.put("code", 1);
			}catch(Exception e){
				jb.put("code", 0);
			}
		}else {
				achievement.setLasttime(new Date());
				achievement.setMoney(addMoney);
				achievement.setPersonmoney(addMoney);
				if(achievementService.addAchievement(achievement)&&achievementService.updateAchievementPagent(achievement.getAgent().getAgid(), addMoney)){
					jb.put("code", 1);
				}else{
					jb.put("code", 0);
				}
		}
		PrintWriter out = response.getWriter();
		out.print(jb);
		out.flush();
		out.close();
		return null;
	}
	/**
	 * 得到业绩列表
	 * @return
	 * @throws IOException
	 */
	public String getPerformanceByPage() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		List<Performance> lp=achievementService.getPerformanceByPage(page);
		if(lp!=null && lp.size()>0){
			jb.put("code", 1);
			jb.put("lp",JsonUtil.listToJson(lp));
			jb.put("count", achievementService.getPerformanceCount(page));
		}else{
			jb.put("code", 0);
		}
		PrintWriter out = response.getWriter();
		out.print(jb);
		out.flush();
		out.close();
		return null;
	}
	/**
	 * 删除所有
	 * @return
	 * @throws IOException
	 */
	public String deleteAllAchievement() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		if(achievementService.deleteAllAchievement()){
			jb.put("code", 1);
		}else{
			jb.put("code", 0);
		}
		PrintWriter out = response.getWriter();
		out.print(jb);
		out.flush();
		out.close();
		return null;
	}
	/**
	 * @throws IOException 
	 * 根据agid获得业绩，用于前台展示
	 */
	public String getPerformanceByAgid() throws IOException{
			response.setCharacterEncoding("utf-8");
			JSONObject jb =new JSONObject();
			
			List<Performance> lp = achievementService.getPerformanceByAgid(agent);
			List<Performance> lp2 = achievementService.getPerformanceByAgpid(agent);
			//System.out.println("~"+);
			
			if(lp!=null && lp.size()>0){
				jb.put("code", 1);
				jb.put("lp",lp.get(0));
				jb.put("lp2",JsonUtil.listToJson(lp2));
			}else{
				jb.put("code", 0);
			}
			
			PrintWriter out = response.getWriter();
			out.print(jb);
			out.flush();
			out.close();
			return null;
	}
	/**
	 * 使用的包有：
	 * poi-3.2-FINAL-20081019.jar
	 *poi-contrib-3.2-FINAL-20081019.jar
	 *poi-scratchpad-3.2-FINAL-20081019.jar
	 * @throws IOException
	 */
	public void downexcel() throws IOException{
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=Achievement.xls");
		HSSFWorkbook  wb=new HSSFWorkbook();
		HSSFSheet sheet=wb.createSheet("sheet0");
		String[] title = {"姓名","微信号","团队业绩","团队奖金","个人业绩","个人奖金"};
		List<Performance> lp=achievementService.getPerformanceAll();
		ExportUtils.outputHeaders(title, sheet);
		ExportUtils.outputColumns(title, lp, sheet, 1);
		ServletOutputStream out=response.getOutputStream();
		wb.write(out);
		out.flush();
		out.close();
	}

	 /*	public String getPerformanceByAgpid() throws IOException{
			response.setCharacterEncoding("utf-8");
			JSONObject jb =new JSONObject();
			
			List<Performance> lp2 = achievementService.getPerformanceByAgpid(agent);
			//System.out.println("~"+);
			
			if(lp2!=null && lp2.size()>0){
				jb.put("code", 1);
				jb.put("lp",JsonUtil.listToJson(lp2));
			}else{
				jb.put("code", 0);
			}
			
			PrintWriter out = response.getWriter();
			out.print(jb);
			out.flush();
			out.close();
			return null;
	}*/
	
	private Map<String,Object> session;
	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		session=arg0;
	}
	private HttpServletResponse response;
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		response=arg0;
	}
}
