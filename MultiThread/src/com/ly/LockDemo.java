package com.ly;

import java.util.concurrent.TimeUnit;


public class LockDemo {
	public static void main(String[] args) {
		final MyLock lock  = new MyLock();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i =0;i<4;i++){
					try {
						lock.readLock();
						TimeUnit.SECONDS.sleep(1);
						System.out.println("write do writing");
						lock.readUnlock();
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		},"ReadThread").start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i =0;i<4;i++){
					try{
						lock.writeLock();
						TimeUnit.SECONDS.sleep(2);
						System.out.println("reader to reading");
						lock.writeUnlock();
						TimeUnit.SECONDS.sleep(2);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		},"WriteThread").start();;
		
	}
}

class MyLock {
	private int readingReaders = 0;
	private boolean isWriting = false;
	
	public synchronized void readLock() throws InterruptedException{
		while(isWriting){
			System.out.println(Thread.currentThread().getName()+" do waiting");
			this.wait();
		}
		readingReaders++;
	}
	
	public synchronized void readUnlock(){
		readingReaders--;
		notifyAll();
		System.out.println(Thread.currentThread().getName()+" notifyAll");
	}
	
	public synchronized void writeLock() throws InterruptedException {
		while(readingReaders>0||isWriting){
			System.out.println(Thread.currentThread().getName()+" do waiting");
			this.wait();
		}
		isWriting = true;
	}
	
	public synchronized void writeUnlock(){
		isWriting = false;
		notifyAll();
		System.out.println(Thread.currentThread().getName()+" notifyAll");
	}
}
