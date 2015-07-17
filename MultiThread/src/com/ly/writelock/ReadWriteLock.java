package com.ly.writelock;

public class ReadWriteLock {
	public static void main(String[] args) {
		
	}
}
class ReaderThread extends Thread{
	
	
}


class ReadWriteLockDemo{
	private int waitingWriters = 0;
	private int readingReaders = 0;
	private boolean writePerfer = true;
	
	public synchronized void readLock() throws InterruptedException{
		while(waitingWriters>0||(readingReaders>0&&writePerfer)){
			this.wait();
		}
		readingReaders++;
	}
	
	public synchronized void readUnlock(){
		notifyAll();
		writePerfer = false;
	}

	public synchronized void writeLock() throws InterruptedException {
		waitingWriters++;
		try{
			while(readingReaders>0){
				this.wait();
			}
		}finally{
			waitingWriters--;
		}
		
	}
	
	public synchronized void writeUnlock(){
		writePerfer = true;
		notifyAll();
	}
	
}