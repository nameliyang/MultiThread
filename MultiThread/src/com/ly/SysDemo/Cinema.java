package com.ly.SysDemo;

public class Cinema {
	
	private long vecanciesCinama1;
	
	private long vacanciesCinema2;
	
	private final Object controlCinema1 ,controlCinema2;
	
	public Cinema(){
		controlCinema1 = new Object();
		controlCinema2 = new Object();
		vecanciesCinama1 = 20;
		vacanciesCinema2 = 20;
	}
	
	public synchronized boolean sellTickets1(int number){
		synchronized (controlCinema1) {
			if(number<vecanciesCinama1){
				vecanciesCinama1 -= number;
				return true;
			}else{
				return false;
			}
		}
	}
	
	public boolean sellTickets2(int number){
		synchronized (controlCinema2) {
			if(number<vacanciesCinema2){
				vacanciesCinema2 -= number;
				return true;
			}else{
				return false;
			}
		}
	}
	public boolean returnTickets1(int number){
		synchronized(controlCinema1){
			vecanciesCinama1+=number;
			return true;
		}
	}
	
	public boolean returnTickets2(int number){
		synchronized(controlCinema2){
			vacanciesCinema2 +=number;
			return true;
		}
	}

	public long getVacanciesCinema1(){
		return vacanciesCinema2;
	}
	
	public long getVacanciesCinema2(){
		return vacanciesCinema2;
	}
}
