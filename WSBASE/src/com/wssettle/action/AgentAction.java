package com.wssettle.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.wssettle.pojo.Agent;
import com.wssettle.pojo.Page;
import com.wssettle.service.AchievementService;
import com.wssettle.service.AgentService;
import com.wssettle.util.JsonUtil;

@Controller
@Scope("prototype")
public class AgentAction extends ActionSupport implements SessionAware,ServletResponseAware,Preparable{
	@Autowired
	private AgentService agentService;
	@Autowired
	private AchievementService achievementService;
	private Agent agent;

	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	private Page page;
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	private String newpassword;
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getNewpassword() {
		return newpassword;
	}
	
	/**
	 * 通过代理名字获取代理id
	 * @param agname
	 * @return
	 * @throws IOException 
	 */
	public String selectAgentid() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		
		int pagid = agentService.selectAgentid(agent.getAgwxnum());
		if(pagid!=-1){
			 jb.put("pid", pagid);
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
	 * 添加代理
	 * 如果有上家，更新上家代理的数量
	 * @return
	 * @throws IOException
	 */
	public String addAgent() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		agent.setAgcreateday(new Date());
		agent.setAgstatus(1);
		agent.setAgcount(0);
		if(agentService.addAgent(agent)){
			if(agentService.updateAgentCount(agent.getAgpid(), true)){
			jb.put("code", 1);
			}else{
				jb.put("code", 0);
			}
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
	 * 得到代理列表通过分页
	 * @return
	 * @throws IOException
	 */
	public String getAgentAllByPage() throws IOException{
		List<Agent> lg=agentService.getAgentByPage(page);
		int count=agentService.getAgentCount(page);
		
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		
		if(lg!=null&&lg.size()>0){
			jb.put("code", 1);
			jb.put("lg", JsonUtil.listToJson(lg));
			jb.put("count", count);
		}else{
			jb.put("code", 0);
		}
		PrintWriter out=response.getWriter();
		out.print(jb);
		out.flush();out.close();
		return null;
	}
	/**
	 * 得到代理通过id
	 * @return
	 */
	public String getAgentByID(){
		Agent ag = agentService.getAgentByID(agent.getAgid());
		Agent pag = agentService.getAgentByID(ag.getAgpid());
		session.put("agent", ag);
		session.put("pagent", pag);
		return "editagent";
	}
	/**
	 * 
	 * @return 根据代理id得到总代理的信息
	 * @throws IOException 
	 */
	public String getGeneralAgentByID() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		
		Agent ag = agentService.getAgentByID(agent.getAgid());
		if(ag!=null){
			jb.put("agent",ag);
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
	 * 修改代理
	 * @return
	 * @throws IOException 
	 */
	public String updateAgent() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		//当代理上家改变时应该修改对应上家的团队人数，如果上家为总代时，应该修改上家的奖金
		if(agent.getAgpid()==id){
		if(agentService.updateAgent(agent)){
			jb.put("code", 1);
		}
		else{
			jb.put("code", 0);
		}}
		else{
			try{
				agentService.updateAgentAndAch(agent, id);
				jb.put("code", 1);
			}catch(Exception e){
				jb.put("code", 0);
			}
		}
		PrintWriter out = response.getWriter();
		out.print(jb);
		out.flush();
		out.close();
		return null;
	}
	public void prepareUpdateAgent(){
		agent=(Agent)session.get("agent");
	}
	/**
	 * 如果该代理存在下属就不能删除，如果该代理还有当月业绩就不能删除
	 * 删除后应该更新其上家的下属数量
	 * @return
	 * @throws IOException
	 */
	public String deleteAgent() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		int count=agentService.getAgentByID(agent.getAgid()).getAgcount();
		if(count>0||achievementService.getAchievementByAgent(agent.getAgid()).size()>0){
			jb.put("code", 0);
		}else{
			agentService.deleteAgent(agent);
			agentService.updateAgentCount(agent.getAgpid(), false);
			jb.put("code", 1);
		}
		PrintWriter out=response.getWriter();
		out.print(jb);
		out.flush();
		out.close();
		return null;
	}
	
	/**
	 * 得到代理树图数据
	 */
	public String getAgentTreeData() throws IOException{
		JSONObject jb =new JSONObject();
		response.setCharacterEncoding("utf-8");
		
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		List<Agent> lg=agentService.getAgentByagpid(id);
		if(lg!=null){
		for(int i = 0;i < lg.size(); i ++){
			HashMap<String,Object> hm = new HashMap<String,Object>();   //最外层，父节点           
			//当前节点的id
			hm.put("id", lg.get(i).getAgid());
			//展示的名字和后缀
			if(lg.get(i).getAglevel()==0){
			hm.put("name", lg.get(i).getAgname()+"(总代)");
			}else{
				hm.put("name", lg.get(i).getAgname()+"("+lg.get(i).getAglevel()+"级代理)");
			}
			
			hm.put("pId",id);
			
			if(lg.get(i).getAgcount()>0){
				hm.put("isParent", true);
				hm.put("icon", "Css/img/diy/10.png");
			}else{
				hm.put("isParent", false);
				hm.put("icon", "Css/img/diy/11.png");
			}
			list.add(hm);
		}	
		}
		JSONArray arr = new JSONArray().fromObject(list);
		try {
			jb.put("success", true);
			jb.put("arr", arr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		response.getWriter().write(arr.toString());
		return null;
	}
	
	public String getAgentByAgwxnum() throws IOException{
		List<Agent> lg=agentService.getAgentByAgwxnum(agent.getAgwxnum());
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		if(lg.size()>0){
			jb.put("code", 1);
		//	session.put("pagent", lg.get(0));
			jb.put("agent", lg.get(0));
		}else{
			jb.put("code", 0);
		}
		PrintWriter out=response.getWriter();
		out.print(jb);
		out.flush();  
	    out.close(); 
		return null;
		
	}

	/**
	 * 得到代理性别比例数量
	 * @return
	 * @throws IOException 
	 */
	public String getAgentDate() throws IOException{
		JSONObject jb =new JSONObject();
		response.setCharacterEncoding("utf-8");
		
		List<Object> losex=agentService.getAgentSex();
		jb.put("losex", losex);
		List<Agent> loaddress=agentService.getAgentAddress();
		jb.put("loaddress", loaddress);
		Map<String,Integer> agerange=new HashMap<String, Integer>();
		agerange.put("0-20", agentService.getCountAgeRange(0, 20));
		for(int i=20;i<=50;i+=5){
			int tonum=i+5;
			agerange.put(i+"-"+tonum, agentService.getCountAgeRange(i, 5));
		}
		agerange.put("55以上", agentService.getCountAgeRange(55, 100));
		jb.put("agerange", agerange);
		PrintWriter out=response.getWriter();
		out.print(jb);
		out.flush();
		out.close();
		return null;
		
	}

	/**
	 * 代理登录
	 * @throws IOException 
	 */
	public String login() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb = new JSONObject();
		
		List<Agent> la = agentService.checkLogin(agent);
		if(la != null && la.size() > 0){
			session.put("agent", la.get(0));
			jb.put("agent", la.get(0));
			jb.put("code", 1);
		}else{
			jb.put("code", 0);
		}
		
		PrintWriter out=response.getWriter();
		out.print(jb);
		out.flush();
		out.close();
		return null;
		
	}
	/**
	 * 修改密码
	 * @return
	 * @throws IOException
	 */
	public String updatePassword() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		
		//能用微信名密码登录则证明是真的代理，能修改密码
		List<Agent> la = agentService.checkLogin(agent); //测试登录
		if(la != null && la.size() > 0){
			la.get(0).setAgpassword(newpassword); //更新登录时返回的用户的信息而不是页面上传过来agent的信息，因为页面上的没有id等其他信息
			try{
				agentService.updatePassword(la.get(0));
				jb.put("code", 1);//修改成功
			}catch(Exception e)
			{
				jb.put("code", 0);//修改成功
			}
		}else{
			jb.put("code", -1);//用户不存在
		}
		
		PrintWriter out=response.getWriter();
		out.print(jb);
		out.flush();
		out.close();
		 return null;
	}

	/**
	 * 分页
	 * @return
	 * @throws IOException 
	 */
	public String getAgentByPage() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		
		 try{
			 List<Agent> galist = agentService.getGeneralAgentByPage(page); //分页获取当前页面数据
			 if(galist.size()>0){
				 jb.put("code", 1);
				 jb.put("galist",galist); //把当前页面数据返回jsp
				// System.out.print("test:"+jb.get("ulist"));
				 jb.put("count", agentService.getGeneralAgentNumber(page)); //获取符合查询条件的数据总数，并把该值返回jsp页面
				 //System.out.println(session.get("count"));
			 }else{
				 jb.put("code", 0);
				 jb.put("count", 0);
			 }
			
		 }catch(Exception e){
			 jb.put("code", -1);
		 }

		PrintWriter out = response.getWriter();
		out.print(jb);
		out.flush();
		out.close();
		return null;		
	}

	/**
	 * 得到代理人数，今天代理新增人数，代理最高个人业绩
	 * @return
	 * @throws IOException
	 */
	public String getBaseDate() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		int agentcount =agentService.getAgentCount(new Page());
		List list=agentService.getAgentCountToday();
		double personMoney=achievementService.getTopPersonMoney();
		double teamMoney=achievementService.getTopTeamMoney();
		if(list!=null&&list.size()>0){
			jb.put("newagentcount", list.get(0));
		}else{
			jb.put("newagentcount", 0);
		}
		jb.put("agentcount", agentcount);
		jb.put("personMoney", personMoney);
		jb.put("teamMoney", teamMoney);
		
		PrintWriter out = response.getWriter();
		out.print(jb);
		out.flush();
		out.close();
		return null;
	}
	private ServletResponse response;
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		response=arg0;
	}
	private Map session;
	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		session=arg0;
	}
	@Override
	public void prepare() throws Exception {}

}
