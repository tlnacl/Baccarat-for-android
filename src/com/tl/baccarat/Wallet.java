/**
 * 
 */
package com.tl.baccarat;

import java.math.BigDecimal;

/**
 * @author Tom.Tang
 *
 */
public class Wallet {
	private int balance;
	
	public Wallet(){
		balance = 0;
	}
	
	public Wallet(int initValue){
		balance = initValue * 100;
	}
	
	public void deposit(int amount){
		balance = balance + amount *100;
	}
	
	public void depositCents(int amount){
		balance = balance + amount;
	}
	
	public void withdraw(int amount){
		balance = balance - amount * 100;
	}
	
	public String getBalance(){
		return new BigDecimal(balance).divide(new BigDecimal(100)).setScale(2).toString();
	}
}
