package com.financeSystem.account.loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.financeSystem.account.Account;

public class LoanAccountDao {
	private final float interestRate=8.5f;
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	
	public LoanAccountDao(Connection con) {
		this.con = con;
	}
	
	public int createLoanAccount(Account loanAccount) {
        String INSERT_ACCOUNT_SQL = "INSERT INTO loan_accounts" +
            "  (account_no, created_date, loan_balance, interestRate, interest_balance, user_id, account_type) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?);";

        int result = 0;

        try {

            PreparedStatement pst = con.prepareStatement(INSERT_ACCOUNT_SQL);
            pst.setString(1, loanAccount.getAccount_no());
            pst.setString(2, java.time.LocalDate.now().toString());
            pst.setFloat(3, loanAccount.getBalance());
            pst.setFloat(4, interestRate);
            pst.setFloat(5, 0.0f);
            pst.setInt(6, loanAccount.getUser_id());
            pst.setString(7, loanAccount.getAccount_type());
            
            System.out.println(pst);
            result = pst.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
    }
	public List<LoanAccount> getAccountByUserId(int user_id) {
		List<LoanAccount> accountList= new ArrayList<LoanAccount>();

		try {

			pst = con.prepareStatement("select * from loan_accounts where user_id = ?"); 
			pst.setInt(1, user_id);

			System.out.println(pst);
			rs = pst.executeQuery();
			 while (rs.next()) {
				 LoanAccount savingsAccount=new LoanAccount();
				 savingsAccount.setAccount_no(rs.getString("account_no"));
				 savingsAccount.setBalance(rs.getFloat("loan_balance"));
				 savingsAccount.setId(rs.getInt("id"));
				 savingsAccount.setInterestRate(rs.getFloat("interestRate"));
				 savingsAccount.setInterestEarned(rs.getFloat("interest_balance"));
				 savingsAccount.setCreated_date(rs.getString("created_date"));
				 savingsAccount.setAccount_type(rs.getString("account_type"));
				 
				 accountList.add(savingsAccount);
			 }

		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return accountList;
	}
	public LoanAccount getAccountByAccountId(int account_id) {
		LoanAccount accountFromDb= new LoanAccount();

		try {

			pst = con.prepareStatement("select * from loan_accounts where id = ?");
			pst.setInt(1, account_id);

			System.out.println(pst);
			rs = pst.executeQuery();
			 while (rs.next()) {
				 accountFromDb.setAccount_no(rs.getString("account_no"));
				 accountFromDb.setBalance(rs.getFloat("loan_balance"));
				 accountFromDb.setId(rs.getInt("id"));
				 accountFromDb.setInterestRate(rs.getFloat("interestRate"));
				 accountFromDb.setInterestEarned(rs.getFloat("interest_balance"));
				 accountFromDb.setCreated_date(rs.getString("created_date"));
				 accountFromDb.setAccount_type(rs.getString("account_type"));
			 }

		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return accountFromDb;
	}
	
	public String transactionUpdateLoanAccount(int account_id, float amount, String transaction_type) {
		LoanAccount accountFromDb= getAccountByAccountId(account_id);
		String withdrawValid="";
		float balance=0.0f;
		String error="";
		if(transaction_type.equals("repayment"))
		{
			withdrawValid= accountFromDb.isRePaymentValid(amount);
			if(withdrawValid.equals("success")) {
				balance=accountFromDb.doRePayment(amount);
			}else {
				error=withdrawValid;
			}
		}
		
		if(balance!=0.0f) {
			String UPDATE_ACCOUNT_SQL = "UPDATE loan_accounts SET loan_balance = ? WHERE id = ?;";

		        try {
		            pst = con.prepareStatement(UPDATE_ACCOUNT_SQL); 
		            pst.setFloat(1, balance);
		            pst.setInt(2, account_id);
		            

		            System.out.println(pst);
		            // Step 3: Execute the query or update query
		            pst.executeUpdate();
		            error="success";
		        } catch (SQLException e) {
		            // process sql exception
		            printSQLException(e);
		        }
		}
		
        return error;
    }
	
	public void loanInterestCalculate() {
    	
		String getDateNow=java.time.LocalDate.now().toString();
		String getDate=getDateNow.substring(8);
		
		try {

			pst = con.prepareStatement("select * from loan_accounts where created_date like?");
			pst.setString(1, "%"+getDate);

			System.out.println(pst);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int id=rs.getInt("id");
				float balance=rs.getFloat("loan_balance");
				float interestRate=rs.getFloat("interestRate");
				float interest_earned=rs.getFloat("interest_balance");
				float interest_earned_new=interest_earned+((balance*interestRate)/100);
				float newBalance=balance+(balance*interestRate)/100;
				 
				 
				String UPDATE_ACCOUNT_SQL = "UPDATE loan_accounts SET loan_balance = ?, interest_balance = ? WHERE id = ?;";
				pst = con.prepareStatement(UPDATE_ACCOUNT_SQL); 
				pst.setFloat(1, newBalance);
				pst.setFloat(2, interest_earned_new);
				pst.setInt(3, id);
				            
				System.out.println(pst);
				            
				pst.executeUpdate();
				 
			 }

		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
    }
	
	private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
