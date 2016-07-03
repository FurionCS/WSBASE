package com.wssettle.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

public class testaction extends ActionSupport implements ServletResponseAware{
	private File upload;
	private String uploadContentType;
	private String uploadFileName;
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	private String result;
	
	public String upload2() throws Exception {
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		String path = ServletActionContext.getServletContext().getRealPath("/images");
		File file = new File(path);
		if(!file.exists()){
			file.mkdir();
		}
		FileUtils.copyFile(upload, new File(file,uploadFileName));
		
		
		jb.put("code", 1);
		jb.put("path", path);
		PrintWriter out = response.getWriter();
		out.print(jb);
		out.flush();
		out.close();
		return null;
	}
	private File[] file;              //文件  
    private String[] fileFileName;    //文件名   
    private String[] filePath;        //文件路径
	 /**
     * 文件上传
     * @return
	 * @throws IOException 
     */
	public String fileUpload() throws IOException {
		response.setCharacterEncoding("utf-8");
		JSONObject jb =new JSONObject();
		String path = ServletActionContext.getServletContext().getRealPath("/upload");
		File file = new File(path); // 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()) {
			file.mkdir();
		}
		try {
			if (this.file != null) {
				File f[] = this.getFile();
				filePath = new String[f.length];
				for (int i = 0; i < f.length; i++) {
					String fileName = java.util.UUID.randomUUID().toString(); // 采用时间+UUID的方式随即命名
					String name = fileName + fileFileName[i].substring(fileFileName[i].lastIndexOf(".")); // 保存在硬盘中的文件名

					FileInputStream inputStream = new FileInputStream(f[i]);
					FileOutputStream outputStream = new FileOutputStream(path+ "\\" + name);
					byte[] buf = new byte[1024];
					int length = 0;
					while ((length = inputStream.read(buf)) != -1) {
						outputStream.write(buf, 0, length);
					}
					inputStream.close();
					outputStream.flush();
					//文件保存的完整路径
					// 如：D:\tomcat6\webapps\struts_ajaxfileupload\\upload\a0be14a1-f99e-4239-b54c-b37c3083134a.png
					filePath[i] = path + "\\" + name;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		jb.put("code", 1);
		jb.put("filePath", filePath);
		PrintWriter out = response.getWriter();
		out.print(jb);
		out.flush();
		out.close();
		return null;
	}
	
	public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}

	public String[] getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String[] getFilePath() {
		return filePath;
	}

	public void setFilePath(String[] filePath) {
		this.filePath = filePath;
	}

	private ServletResponse response;
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		response=arg0;
	}
}
