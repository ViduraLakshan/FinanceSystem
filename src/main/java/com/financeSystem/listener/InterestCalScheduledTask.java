package com.financeSystem.listener;


import com.financeSystem.account.fixed.FixedAccountDao;
import com.financeSystem.account.loan.LoanAccountDao;
import com.financeSystem.account.savings.SavingsAccountDao;
import com.financeSystem.connection.DbConnection;

public class InterestCalScheduledTask implements Runnable {
	
	
    @Override
    public void run() {
    	
    	try {
    	SavingsAccountDao savingsAccountDao=new SavingsAccountDao(DbConnection.getConnection());
    	LoanAccountDao loanAccountDao=new LoanAccountDao(DbConnection.getConnection());
    	FixedAccountDao fixedAccountDao=new FixedAccountDao(DbConnection.getConnection());
    	
    	savingsAccountDao.savingsInterestCalculate();
    	loanAccountDao.loanInterestCalculate();
    	
    	fixedAccountDao.fixedInterestCalculate();
    	}catch(Exception e) {
			e.printStackTrace();
		}
    }
   
}