package com.ly.chapter1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CharsetDemo {
	
	private String file = "d:/test.txt";
	private String charset = "UTF-8";
	
	public static void main(String[] args) throws Exception {
		
		CharsetDemo demo = new CharsetDemo();
	//	demo.write();
		demo.read();
	}
	
	
	public void write() throws Exception{
		FileOutputStream outputStream = new FileOutputStream(file);
		OutputStreamWriter writer = new OutputStreamWriter(outputStream,charset);
		writer.write("这是要保存的中文字符");
		writer.close();
	}
	
	public void read() throws Exception{
		FileInputStream inputStream = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(inputStream,charset);
		StringBuilder sb = new StringBuilder();
		int count = 0;
		char[] buf = new char[5];
		
		while((count=reader.read(buf))!=-1){
			sb.append(new String(buf,0,count));
		}
		
		System.out.println(new String(sb));
		
	}
}




















