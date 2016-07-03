package com.wssettle.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class ExcelJxl {
	public static String JXLWrite(String[] title,String[][] body){
		Date date=new Date();
		String Filename="业绩表"+date.getTime()+".xls";
		File file = new File("C:/Users/Administrator/Desktop/"+Filename);
		try {
			file.createNewFile();
			//创建工作簿
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			//创建sheet
			WritableSheet sheet = workbook.createSheet("sheet1", 0);
			Label label = null;
			//第一行设置列名
			for (int i = 0; i < title.length; i++) {
				label = new Label(i,0,title[i]);
				sheet.addCell(label);	
			}
			//追加数据
			for (int i = 1; i <=body.length; i++) {
				label = new Label(0,i,body[i-1][0]);
				sheet.addCell(label);
				label = new Label(1,i,body[i-1][1]);
				sheet.addCell(label);
				label = new Label(2,i,body[i-1][2]);
				sheet.addCell(label);
				label = new Label(3,i,body[i-1][3]);
				sheet.addCell(label);
				label = new Label(4,i,body[i-1][4]);
				sheet.addCell(label);
			}
			//写入数据
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "C:/Users/Administrator/Desktop/"+Filename;
	}
}
