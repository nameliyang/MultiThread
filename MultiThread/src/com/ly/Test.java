package com.ly;

import java.util.concurrent.TimeUnit;

public class Test {
	
	private static boolean ready;
	
	private static int number;
	
	
	public static void main(String[] args) {
		new ReaderThread().start();
		number =42;
		ready = true;
	}
	
	private static class ReaderThread extends Thread{
		
		@Override
		public void run() {
			while(!ready){
				Thread.yield();
			}
			System.out.println(number);
		}
	}
}

class Data{
	
	private char[] buffer = new char[10];
	
	private final  ReadWriteLock lock = new ReadWriteLock();
	
	
	public Data(){
		for(int i =0;i<buffer.length;i++){
			buffer[i] = '*';
		}
	}
	
	
	public char[] doRead() throws InterruptedException{
		lock.readLock();
		try{
			char[] temp = new char[buffer.length];
			for(int i=0;i<buffer.length;i++){
				temp[i] = buffer[i];
			}
			System.out.println(Thread.currentThread().getName()+" read ---->"+new String(temp));
			TimeUnit.SECONDS.sleep(1);
			return temp;
		}finally{
			lock.readUnlock();
		}
	}
	
	public void doWrite(char c) throws InterruptedException{
		lock.writeLock();
		try{
			for(int i=0;i<buffer.length;i++){
				buffer[i] = c;
			}
			System.out.println(Thread.currentThread().getName()+" "+new String(buffer));
			TimeUnit.SECONDS.sleep(1);
		}finally{
			lock.writeUnlock();
		}
	}
	
}

class ReadThread extends Thread{
	private Data data ;
	
	public ReadThread(Data data,String threadname){
		super(threadname);
		this.data = data;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				data.doRead();
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class WriteThread extends Thread{
	private Data data ;
	private String msg = "hello";
	private int index = 0;
	
	public WriteThread(Data data,String threadname){
		super(threadname);
		this.data = data;
	}
	
	@Override
	public void run() {
		while(true){
			index = (msg.length() ==index)?0:index;
			try {
				data.doWrite(msg.charAt(index));
				index ++;
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class ReadWriteLock{
	
	private int readingReaders = 0;
	
	private boolean isWriting = false;
	
	
	public synchronized void readLock() throws InterruptedException{
		while(isWriting){
			System.out.println(Thread.currentThread().getName()+" do wait");
			this.wait();
		}
		readingReaders++;
	}
	
	public synchronized void readUnlock(){
		readingReaders--;
		notifyAll();
		System.out.println(Thread.currentThread().getName()+" do notifyAll");
	}
	
	public synchronized void writeLock() throws InterruptedException {
		while(readingReaders>0||isWriting){
			System.out.println(Thread.currentThread().getName()+" do wait");
			this.wait();
		}
		isWriting = true;
	}
	
	public synchronized void writeUnlock(){
		isWriting = false;
		notifyAll();
		System.out.println(Thread.currentThread().getName()+" do notifyAll");
	}
}
