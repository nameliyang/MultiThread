package com.ly.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueDemo {
	
	public static void main(String[] args) {
		ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
		queue.add("l");
		queue.add("b");
		queue.add("c");
		System.out.println(queue.poll());
		System.out.println(queue);
	
	}
}
