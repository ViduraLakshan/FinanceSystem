package com.financeSystem.account.savings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.financeSystem.account.Account;

public class SavingsAccountDao {
	private final float interestRate=8.5f;
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	
	
	public SavingsAccountDao(Connection con) {
		this.con = con;
	}

	public int createSavingAccount(Account savingsaccount) {
        String INSERT_ACCOUNT_SQL = "INSERT INTO savings_accounts" +
            "  (account_no, account_type, balance , user_id, interestRate, interest_earned, created_date) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?);";

        int result = 0;

        try {
            pst = con.prepareStatement(INSERT_ACCOUNT_SQL); 
            pst.setString(1, savingsaccount.getAccount_no());
            pst.setString(2, savingsaccount.getAccount_type());
            pst.setFloat(3, savingsaccount.getBalance());
            pst.setInt(4, savingsaccount.getUser_id());
            pst.setFloat(5, interestRate);
            pst.setFloat(6, 0.0f);
            pst.setString(7, java.time.LocalDate.now().toString());


            System.out.println(pst);
            result = pst.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
    }
	
	public List<SavingsAccount> getAccountsByUserId(int user_id) {
		List<SavingsAccount> accountList= new ArrayList<SavingsAccount>();

		try {
			pst = con.prepareStatement("select * from savings_accounts where user_id = ?");
			pst.setInt(1, user_id);

			System.out.println(pst);
			rs = pst.executeQuery();
			 while (rs.next()) {
				 SavingsAccount accountFromDb= new SavingsAccount();
				 	accountFromDb.setId(rs.getInt("id"));
				 	accountFromDb.setAccount_no(rs.getString("account_no"));
					accountFromDb.setBalance(rs.getFloat("balance"));
					accountFromDb.setAccount_type(rs.getString("account_type"));
					accountFromDb.setUser_id(rs.getInt("id"));
					accountFromDb.setInterestRate(rs.getFloat("interestRate"));
					accountFromDb.setInterestEarned(rs.getFloat("interest_earned"));
					accountList.add(accountFromDb);
			 }

		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return accountList;
	}
	
	public SavingsAccount getAccountByAccountId(int account_id) {
		SavingsAccount accountFromDb= new SavingsAccount();

		try {
			pst = con.prepareStatement("select * from savings_accounts where id = ?");
			pst.setInt(1, account_id);

			System.out.println(pst);
			rs = pst.executeQuery();
			 while (rs.next()) {
				 	accountFromDb.setId(rs.getInt("id"));
				 	accountFromDb.setAccount_no(rs.getString("account_no"));
					accountFromDb.setBalance(rs.getFloat("balance"));
					accountFromDb.setAccount_type(rs.getString("account_type"));
					accountFromDb.setUser_id(rs.getInt("id"));
					accountFromDb.setInterestRate(rs.getFloat("interestRate"));
					accountFromDb.setInterestEarned(rs.getFloat("interest_earned"));
			 }

		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return accountFromDb;
	}
	
	public String transactionUpdateSavingAccount(int account_id, float amount, String transaction_type) {
		SavingsAccount accountFromDb= getAccountByAccountId(account_id);
		String withdrawValid="no";
		float balance=0.0f;
		String error="";
		if(transaction_type.equals("withdraw"))
		{
			withdrawValid= accountFromDb.iswithdrawValid(amount);
			if(withdrawValid.equals("success")) {
				balance=accountFromDb.dowithdraw(amount);
			}else {
				error=withdrawValid;
			}
			
		}else if(transaction_type.equals("deposit")) {
			balance=accountFromDb.doDeposit(amount);
		}
		
		if(balance!=0.0f) {
			String UPDATE_ACCOUNT_SQL = "UPDATE savings_accounts SET balance = ? WHERE id = ?;";

		        try {

		            pst = con.prepareStatement(UPDATE_ACCOUNT_SQL);
		            pst.setFloat(1, balance);
		            pst.setInt(2, account_id);
		            
		            System.out.println(pst);
		            pst.executeUpdate();
		            error="success";
		        } catch (SQLException e) {
		            // process sql exception
		            printSQLException(e);
		        }
		}
		
        return error;
    }
	
	public void savingsInterestCalculate() {
    
		String getDateNow=java.time.LocalDate.now().toString();
		String getDate=getDateNow.substring(8);
		
		try {
			pst = con.prepareStatement("select * from savings_accounts where created_date like?");
			pst.setString(1, "%"+getDate);
			
			System.out.println(pst);
			rs = pst.executeQuery();

			 while (rs.next()) {
				 int id=rs.getInt("id");
				 float balance=rs.getFloat("balance");
				 float interestRate=rs.getFloat("interestRate");
				 float interest_earned=rs.getFloat("interest_earned");
				 float interest_earned_new=interest_earned+((balance*interestRate)/100);
				 float newBalance=balance+(balance*interestRate)/100;
				 
				 
				 String UPDATE_ACCOUNT_SQL = "UPDATE savings_accounts SET balance = ?, interest_earned = ? WHERE id = ?;";
				 

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
