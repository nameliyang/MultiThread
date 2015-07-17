package com.ly.test;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayDemo {
	public static void main(String[] args) throws InterruptedException {
		
		DelayObj obj1 = new DelayObj(1L);
		DelayObj obj2 = new DelayObj(2L);
		DelayObj obj3 = new DelayObj(4L);
		DelayObj obj4 = new DelayObj(3L);
		DelayQueue<DelayObj> queue = new DelayQueue<DelayObj>();
		
		queue.put(obj1);
		queue.put(obj2);
		queue.put(obj3);
		queue.put(obj4);
		
		System.out.println(queue.take());
		
	}
}
class DelayObj implements Delayed{
	private long time = 0;
	
	public DelayObj(long time){
		this.time = time;
	}
	
	@Override
	public int compareTo(Delayed o) {
		DelayObj obj = (DelayObj) o;
		
		if(obj.getTime()>this.time){
			return 1;
		}else if(obj.getTime()<this.time){
			return -1;
		}
		return 0;
	}
	
	@Override
	public long getDelay(TimeUnit unit) {
		
		return time;
	}
	
	public long getTime(){
		return this.time;
	}
	
	@Override
	public String toString() {
		return "DelayObj [time=" + time + "]";
	}
	
}

