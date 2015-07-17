package com.ly.test;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DemoTest {
	public static void main(String[] args) {

	}
}
class DelayObject implements Delayed {
	
	private String data;
	private long startTime;

	public DelayObject(String data, long delay) {
		this.data = data;
		this.startTime = System.currentTimeMillis() + delay;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		long diff = startTime - System.currentTimeMillis();
		return unit.convert(diff, TimeUnit.MILLISECONDS);
	}

	@Override
	public int compareTo(Delayed o) {
		if (this.startTime < ((DelayObject) o).startTime) {
			return -1;
		}
		if (this.startTime > ((DelayObject) o).startTime) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "{" + "data='" + data + '\'' + ", startTime=" + startTime + '}';
	}
}

class DelayQueueProducer {

	private BlockingQueue queue;

	private final Random random = new Random();

	public DelayQueueProducer(BlockingQueue queue) {
		super();
		this.queue = queue;
	}

	private Thread producerThread = new Thread(new Runnable() {
		@Override
		public void run() {
			while (true) {
				try {
					// Put some Delayed object into the DelayQueue.
					int delay = random.nextInt(10000);
					DelayObject object = new DelayObject(UUID.randomUUID()
							.toString(), delay);
					System.out.printf("Put object = %s%n", object);
					queue.put(object);
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}, "Producer Thread");

	public void start() {
		this.producerThread.start();
	}
}
class DelayQueueConsumer {
	
	private String name;
	
    private BlockingQueue queue;

	public DelayQueueConsumer(String name, BlockingQueue queue) {
		super();
		this.name = name;
		this.queue = queue;
	}
	
	private Thread consumerThread =  new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    // Take elements out from the DelayQueue object.
                    DelayObject object = (DelayObject) queue.take();
                    System.out.printf("[%s] - Take object = %s%n",
                            Thread.currentThread().getName(), object);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
	
	public void start(){
		this.consumerThread.setName(name);
		this.consumerThread.start();
	}

}


