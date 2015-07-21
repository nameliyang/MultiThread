package com.ly.threadpool;

public class ThreadPoolTest {
	public static void main(String[] args) {
		int numTasks = 4;
		int poolSize = 3;
		
		ThreadPool threadPool = new ThreadPool(poolSize);
		for(int i=0;i<numTasks;i++){
			threadPool.execute(createTask(i));
		}
		threadPool.join();
		
	}
	
	private static Runnable createTask(final int taskId){
		return new Runnable() {
			@Override
			public void run() {
				System.out.println("Task "+taskId+":start");
				try{
					Thread.sleep(500);
				}catch(InterruptedException e){
				}
				System.out.println("Task "+taskId+":end");
				
			}
		};
	}
}
