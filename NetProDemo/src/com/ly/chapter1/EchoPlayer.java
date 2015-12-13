package com.ly.chapter1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class EchoPlayer {
	public String echo(String msg){
		return "echo:"+msg;
	}
	
	public void talk() throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String msg = null;
		while((msg=br.readLine())!=null){
			System.out.println(echo(msg));
			if(msg.equals("bye")){
				break;
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File("D:/test.txt")),"utf-8");
		Reader reader = inputStreamReader;
		char[] buffer = new char[6];
		int length = 0;
		StringBuffer sb = new StringBuffer();
		while((length=inputStreamReader.read(buffer))!=-1){
			sb.append(new String(buffer,0,length));
		}
		System.out.println(new String(sb));
	}
	
}
