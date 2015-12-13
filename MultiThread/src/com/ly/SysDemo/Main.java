package com.ly.SysDemo;

public class Main {
	public static void main(String[] args) {
		Account account = new Account();
		account.setBalance(1000);
		
		Company company = new Company(account);
		Thread companyThread = new Thread(company);
		Bank bank = new Bank(account);
		Thread bankThread = new Thread(bank);
		
		System.out.println("Account :initilal Balance  "+account.getBalance());
		companyThread.start();
		bankThread.start();
		
		
		try {
			companyThread.join();
			bankThread.join();
			System.out.println("Account :Final Balance :"+account.getBalance());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Account{
	private double balance;
	
	public double getBalance(){
		return balance;
	}
	
	public void setBalance(double balance){
		this.balance = balance;
	}
	
	public synchronized void addAmount(double amount){
		double tmp = balance;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tmp += amount;
		balance = tmp;
	}
	
	public synchronized void substractAmount(double amount){
		double tmp = balance;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tmp-= amount;
		balance = tmp;
	}
}

class Bank implements Runnable{
	
	private Account account;
	
	public Bank(Account account){
		this.account = account;
	}
	
	@Override
	public void run() {
		for(int i =0;i<100;i++){
			account.substractAmount(1000);
		}
	}
}

class Company implements Runnable{
	private Account account;
	
	public Company(Account account){
		this.account = account;
	}
	
	@Override
	public void run() {
		for(int i = 0;i<100;i++){
			account.addAmount(1000);
		}
	}
}














