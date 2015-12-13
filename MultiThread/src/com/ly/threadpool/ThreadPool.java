package com.ly.threadpool;

import java.util.LinkedList;
/**
 * 
 * @author liyang
 *
 */
public class ThreadPool extends ThreadGroup{
	
	//线程池是否关闭
	private boolean isClosed = false;
	
	//表示工作队列
	private LinkedList<Runnable> workQueue;
	
	//表示线程池ID
	private static int threadPoolID;
	
	//表示工作线程Id
	private int threadID;
	
	public ThreadPool(int poolSize) {
		super("ThreadPool-"+(threadPoolID++));
	//	setDaemon(true);
		workQueue = new LinkedList<Runnable>();
		
		for(int i =0;i<poolSize;i++){
			new WorkThread().start();
		}
	}
	
	/**
	 * 向工作队列中加入一个新任务，由工作线程去执行任务
	 * @param task
	 */
	public synchronized void execute(Runnable task){
		if(isClosed){
			throw new IllegalStateException();
		}
		
		if(task!=null){
			workQueue.add(task);
			notify();
		}
	}
	
	protected synchronized Runnable getTask() throws InterruptedException{
		while(workQueue.size()==0){
			if(isClosed) return null;
			wait();
		}
		return workQueue.removeFirst();
	}
	
	public synchronized void close(){
		if(!isClosed){
			isClosed = true;
			workQueue.clear();
			interrupt();
		}
	}
	
	public void join(){
		synchronized(this){
			isClosed = true;
			notifyAll();
		}
		
		Thread[] threads = new Thread[activeCount()];
		int count = enumerate(threads);
		for(int i = 0;i<count;i++){
			try{
				threads[i].join();
			}catch(InterruptedException ex){};
		}
		
	}
	
	private class WorkThread extends Thread{
		public WorkThread(){
			super(ThreadPool.this,"WorkThread-"+(threadID++));
		}
		
		@Override
		public void run() {
			while(!isInterrupted()){
				Runnable task = null;
				try{
					task = getTask();
				}catch(InterruptedException e){
				}
				if(task==null) return;
				try{
					task.run();
				}catch(Throwable t){
					
				}
			}
		}
	}
}
