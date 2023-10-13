package com.financeSystem.account.savings;

import com.financeSystem.account.Account;

public class SavingsAccount extends Account{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String created_date;
	
	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}

	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub
		super.setId(id);
	}

	@Override
	public float getInterestRate() {
		// TODO Auto-generated method stub
		return super.getInterestRate();
	}

	@Override
	public void setInterestRate(float interestRate) {
		// TODO Auto-generated method stub
		super.setInterestRate(interestRate);
	}

	@Override
	public float getInterestEarned() {
		// TODO Auto-generated method stub
		return super.getInterestEarned();
	}

	@Override
	public void setInterestEarned(float interestEarned) {
		// TODO Auto-generated method stub
		super.setInterestEarned(interestEarned);
	}

	@Override
	public String getAccount_no() {
		// TODO Auto-generated method stub
		return super.getAccount_no();
	}

	@Override
	public void setAccount_no(String account_no) {
		// TODO Auto-generated method stub
		super.setAccount_no(account_no);
	}

	@Override
	public int getUser_id() {
		// TODO Auto-generated method stub
		return super.getUser_id();
	}

	@Override
	public void setUser_id(int user_id) {
		// TODO Auto-generated method stub
		super.setUser_id(user_id);
	}

	@Override
	public String getAccount_type() {
		// TODO Auto-generated method stub
		return super.getAccount_type();
	}

	@Override
	public void setAccount_type(String account_type) {
		// TODO Auto-generated method stub
		super.setAccount_type(account_type);
	}

	@Override
	public float getBalance() {
		// TODO Auto-generated method stub
		return super.getBalance();
	}

	@Override
	public void setBalance(float balance) {
		// TODO Auto-generated method stub
		super.setBalance(balance);
	}
	
	public float doDeposit(float amount){
		return getBalance()+amount;
	}
	public float dowithdraw(float amount){
		return getBalance()-amount;
	}
	public String iswithdrawValid(float amount){
		float stable_balance=400.f;
		
		if(getBalance()<amount) {
			return "";
		}else if(stable_balance>getBalance()-amount) {
			return "";
		}
		return "success";
	}
	

}
