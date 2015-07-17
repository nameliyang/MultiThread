package com.ly.activeobj;


public class Main {
	public static void main(String[] args) {
		
	}
}

class MakerClientThread extends Thread{
	private final ActiveObject activeObject;
	private final char fillchar;
	
	public MakerClientThread(String name,ActiveObject activeObject){
		super(name);
		this.activeObject = activeObject;
		this.fillchar = name.charAt(0);
	}
	
	@Override
	public void run() {
		try{
			for(int i =0;true;i++){
				Result result = activeObject.makeString(i, fillchar);
				Thread.sleep(10);
				String value = (String)result.getResultValue();
				System.out.println(Thread.currentThread().getName()+":value="+value);
			}
		}catch(InterruptedException e){
			
		}
	}
}

class DisplayClientThread extends Thread{
	private final ActiveObject activeObject;
	
	public DisplayClientThread(String name,ActiveObject activeObject){
		super(name);
		this.activeObject = activeObject;
	}
	
	@Override
	public void run() {
		try{
			for(int i=0;true;i++){
				String string = Thread.currentThread().getName()+" "+i;
				activeObject.displayString(string);
				Thread.sleep(200);
			}
		}catch(InterruptedException e){
			
		}
	}
}

interface ActiveObject {
	Result makeString(int count,char fillchar);
	void displayString(String string);
}

class ActiveObjectFactory{
	public static ActiveObject createActiveObject(){
		Servant servant = new Servant();
		ActivationQueue queue = new ActivationQueue();
		SchedulerThread scheduler = new SchedulerThread();
		Proxy proxy = new Proxy(scheduler,servant);
		scheduler.start();
		return proxy;
	}
}

class Proxy implements ActiveObject{
	
	private final SchedulerThread scheduler;
	private final Servant servant;

	public Proxy(SchedulerThread scheduler,Servant servant){
		this.scheduler = scheduler;
		this.servant = servant;
	}
	
	@Override
	public Result makeString(int count, char fillchar) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void displayString(String string) {
	}
}

class SchedulerThread extends Thread{
}

class ActivationQueue{
	
}

abstract class MethodRequest{
	protected final Servant servant;
	protected final FutureResult future;
	
	protected MethodRequest(Servant servant,FutureResult future){
		this.servant = servant;
		this.future = future;
	}
	
	public abstract void execute();
}

class MakeStringRequest extends MethodRequest{
	private final int count;
	private final char fillchar;
	
	public MakeStringRequest(Servant servant,FutureResult future,int count,char fillchar){
		super(servant,future);
		this.count = count;
		this.fillchar = fillchar;
	}
	public void execute(){
		Result result = servant.makeString(count, fillchar);
		future.setResult(result);
	}
}

class DisplayStringRequest extends MethodRequest{
	private final String string;
	
	public DisplayStringRequest(Servant servant,String string){
		super(servant,null);
		this.string = string;
	}
	
	public void execute(){
		
	}
}

abstract  class Result{
	
	public abstract Object getResultValue();
}

class FutureResult extends Result{
	
	private Result result;
	private boolean ready = false;
	
	public synchronized void setResult(Result result){
		this.result = result;
		this.ready = true;
		notifyAll();
	}
	
	@Override
	public synchronized Object getResultValue(){
		while(!ready){
			try{
				wait();
			}catch(InterruptedException e){
				
			}
		}
		return result.getResultValue();
	}
	
}

class RealResult extends Result{
	
	private final Object resultValue;
	
	public RealResult(Object resultValue) {
		this.resultValue = resultValue;
	}
	
	@Override
	public Object getResultValue() {
		return resultValue;
	}
	
}

class Servant implements ActiveObject{

	public Result makeString(int count, char fillchar) {
		char[] buffer = new char[count];
		for(int i =0;i<count;i++){
			buffer[i] = fillchar;
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){
				
			}
		}
		return new RealResult(new String(buffer));
	}

	@Override
	public void displayString(String string) {
		
		try{
			System.out.println("displayString: "+string);
			Thread.sleep(10);
		}catch(InterruptedException e){
			
		}
	}
}
