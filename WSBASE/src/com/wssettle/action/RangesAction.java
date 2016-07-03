package com.wssettle.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.wssettle.pojo.Ranges;
import com.wssettle.service.RangesService;

@Controller
@Scope("prototype")
public class RangesAction extends ActionSupport implements SessionAware,ServletResponseAware{
	@Autowired
	private RangesService rangesService;
	private Ranges ranges;
	
	public Ranges getRanges() {
		return ranges;
	}
	public void setRanges(Ranges ranges) {
		this.ranges = ranges;
	}
	
	/**
	 * 添加范围
	 * @return
	 * @throws IOException
	 */
	public String addRanges() throws IOException{
		
		if(ranges.getMaxnum()==0||ranges.getMaxnum()<ranges.getMinnum()){return "addRangesFail";}
		if(ranges.getRid()>0){
			if(rangesService.updateRanges(ranges)){
				return "updateRangesSuc";
			}else{
				return "updateRangesSuc";
			}
		}else{
			if(rangesService.addRanges(ranges)){
				return "addRangesSuc";
			 }else{
				return "addRangesFail";
			 }	
		}
		
	}
	
	/**
	 * 查找范围
	 */
	public String getAllRanges() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		
		 try{
			 List<Ranges> rlist = rangesService.getAllRanges(ranges); 
			 if(rlist.size()>0){
				 jb.put("code", 1);
				 jb.put("rlist",rlist); //把当前页面数据返回jsp
				// System.out.print("test:"+jb.get("ulist"));
			 }else{
				 jb.put("code", 0);
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
	 * 删除范围
	 * @return
	 * @throws IOException
	 */
	public String deleteRanges() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		
		if(rangesService.deleteRanges(ranges)){
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
	 * 得到范围根据业绩
	 * @return
	 * @throws IOException 
	 */
	public String getRangesByMoney() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		//这里使用minnum接受money
		List<Ranges> lr=rangesService.getRangesByMoney(ranges.getMinnum());
		if(lr!=null&&lr.size()==1){
			jb.put("code", 1);
			jb.put("proportion", lr.get(0).getProportion());
		}else if(lr!=null&&lr.size()>1){
			jb.put("code", 2);
		}else{
			jb.put("code", 0);
		}
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
}
