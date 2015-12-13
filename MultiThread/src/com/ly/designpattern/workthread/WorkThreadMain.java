package com.ly.designpattern.workthread;

import java.util.Random;

public class WorkThreadMain {
	public static void main(String[] args) {
		
	}
}


class WorkThread extends Thread{
	
	@Override
	public void run() {
		super.run();
	}
}

class Request{
	private final  String name;
	private final int number;
	private static final Random random = new Random();
	
	
	public Request(String name,int number){
		this.name = name;
		this.number  = number;
	}
	
	public void execute(){
		System.out.println(Thread.currentThread().getName()+""
				+ "executes "+this);
		try{
			Thread.sleep(random.nextInt(1000));
		}catch(InterruptedException e){
			
		}
	}
	
	public String toString(){
		return "[ Request from "+name+" No."+ number+" ]";
	}
}














