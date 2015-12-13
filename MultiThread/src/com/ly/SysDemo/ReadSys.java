package com.ly.SysDemo;

public class ReadSys {
	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		
		new Thread(new BuyRunnable(ticket),"B").start();
		new Thread(new AddRunnable(ticket),"A").start();
		
	}
}
class Ticket{
	private int ticket =50;
	
	public void buyTicket(){
		synchronized(this){
			while(ticket<=0){
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ticket--;
			System.out.println(Thread.currentThread().getName()+" buy ticket,tikcet has "+this.ticket);
		}
	}
	
	public void addTicket(){
		synchronized(this){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ticket++;
			System.out.println(Thread.currentThread().getName()+" buy ticket ,tikcet has "+this.ticket);
			notifyAll();
		}
	}
	
	public String getTicket() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Thread.currentThread().getName()+" has "+ticket;
	}
}

class BuyRunnable implements Runnable{
	
	private Ticket  ticket ;
	
	public BuyRunnable(Ticket ticket){
		this.ticket = ticket;
	}
	
	@Override
	public void run() {
		while(true){
			ticket.buyTicket();
			System.out.println(ticket.getTicket());
		}
	}
}
class AddRunnable implements Runnable{
	
	private Ticket  ticket ;
	
	public AddRunnable(Ticket ticket){
		this.ticket = ticket;
	}
	
	@Override
	public void run() {
		while(true){
			ticket.addTicket();
			System.out.println(ticket.getTicket());
		}
	}
}
