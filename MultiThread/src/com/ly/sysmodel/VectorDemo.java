package com.ly.sysmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class VectorDemo {
	public static void main(String[] args) {
		
	/*	VectorObj obj  = new VectorObj();
		new Thread(new MyRunnable1(obj),"remove").start();
		new Thread(new MyRunnable2(obj),"add").start();*/
		
		//List<String> v = new Vector<String>();
		
		List<String> v = new ArrayList<String>();
		
		v.add("1");
		v.add("b");
		v.add("b");
		v.add("b");
		v.add("b");
		
		Iterator<String> iterator = v.iterator();
		System.out.println(iterator.getClass().getName());
		
		while(iterator.hasNext()){
			
			Object obj = iterator.next();
			v.remove(0);
			System.out.println(obj);
			
		}
	}
	static class VectorObj{
		private Vector<String> vector =  new Vector<String>();

		public VectorObj(){
			for(int i =0;i<100;i++){
				vector.add("str"+i);
			}
		}
		
		public void remove(){
			System.out.println(Thread.currentThread().getName()+"-->"+vector);
			synchronized(vector){
				int length = vector.size();
				vector.remove(length-1);
			}
		}
		
		public String getLast(){
			System.out.println(Thread.currentThread().getName()+"-->"+vector);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			synchronized (vector) {
				int length = vector.size();
				return vector.get(length-1);
			}
			
		}
	}
	static class MyRunnable1 implements Runnable{
		private VectorObj vectorObj ;
		
		public MyRunnable1(VectorObj vectorObj) {
			this.vectorObj = vectorObj;
		}
		
		@Override
		public void run() {
		
			while(true){
				vectorObj.remove();
			}
		}
	}
	
	static class MyRunnable2 implements Runnable{
		private VectorObj vectorObj ;
		
		public MyRunnable2(VectorObj vectorObj) {
			this.vectorObj = vectorObj;
		}
		
		
		@Override
		public void run() {
			while(true){
				vectorObj.getLast();
			}
			
		}
	}
	
}

