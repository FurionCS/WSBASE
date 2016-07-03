package com.wssettle.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.wssettle.pojo.Admin;
import com.wssettle.service.AdminService;
import com.wssettle.util.Encryption;

@Controller
@Scope("prototype")
public class AdminAction extends ActionSupport implements SessionAware,ServletResponseAware,ServletRequestAware{

	@Autowired
	private AdminService adminService;
	private Admin admin;
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public Admin getAdmin() {
		return admin;
	}
	private String newpassword;
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public String checkAdmin() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		//加密
		admin.setAdpassword(Encryption.encodeByBase64(admin.getAdpassword()));
		List<Admin> la=adminService.checkLogin(admin);
		if(la!=null&&la.size()>0){
			session.put("admin", la.get(0));
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
	public String LoginOut(){
		session.clear();
		return "input";
	}
	/**
	 * 管理员修改密码
	 * @return
	 * @throws IOException
	 */
	public String updatePassword() throws IOException{
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		Admin oldadmin=(Admin) session.get("admin");
		//解密
		if(Encryption.decodeBase64(oldadmin.getAdpassword()).equals(admin.getAdpassword())){
			//加密
		 oldadmin.setAdpassword(Encryption.encodeByBase64(newpassword));
			try{
				adminService.updatePassword(oldadmin);
				jb.put("code", 1);
			}catch(Exception e){
				 jb.put("code", 0);
			}
		}else{
			jb.put("code", -1);
		}
		
		PrintWriter out=response.getWriter();
		out.print(jb);
		out.flush();
		out.close();
		 return null;
	}
	private Map<String,Object> session;
	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		session=arg0;
	}
	private ServletResponse response;
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		response=arg0;
	}
	HttpServletRequest request;
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		request=arg0;
	}

}
