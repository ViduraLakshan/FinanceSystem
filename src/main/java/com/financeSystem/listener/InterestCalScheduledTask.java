package com.financeSystem.listener;


import com.financeSystem.account.fixed.FixedAccountDao;
import com.financeSystem.account.loan.LoanAccountDao;
import com.financeSystem.account.savings.SavingsAccountDao;

public class InterestCalScheduledTask implements Runnable {
	
	
    @Override
    public void run() {
    	SavingsAccountDao savingsAccountDao=new SavingsAccountDao();
    	LoanAccountDao loanAccountDao=new LoanAccountDao();
    	FixedAccountDao fixedAccountDao=new FixedAccountDao();
    	savingsAccountDao.savingsInterestCalculate();
    	loanAccountDao.loanInterestCalculate();
    	
    	fixedAccountDao.fixedInterestCalculate();
    }
   
}