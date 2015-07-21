package com.ly.test;

import java.util.concurrent.TimeUnit;

public class VolatileDemo {
	public static void main(String[] args) {
		MyThread thread = new MyThread();
		
		new Thread(thread,"theadA").start();
		new Thread(thread,"threadB").start();
	}
}

class ShareObj{
	
	private   volatile  int counter = 0;
	
	public void add(){
		counter++;
	}

	@Override
	public String toString() {
		return "ShareObj [counter=" + counter + "]";
	}
}

class MyThread  implements Runnable{
	
	private ShareObj obj = new ShareObj();
	
	public MyThread(){
	}
	
	@Override
	public void run() {
		while(true){
			obj.add();
			System.out.println(Thread.currentThread().getName()+"-->" +obj);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}