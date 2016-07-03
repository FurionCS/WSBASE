package com.wssettle.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesUtil {

	public static String getDestPath(){
		String savePath = "";
		try {
			//加载目录配置文件
			Properties pro = new Properties();
			pro.load( new FileInputStream(new File("config/source.properties") ) );
			savePath = pro.getProperty("savePath");
		} catch (Exception e) {
			System.out.println("目标文件未找到!");
			e.printStackTrace();
		}
		
		return savePath;
	}
	
	public static void main(String[] args) {
		System.out.println(getDestPath());
	}
}
