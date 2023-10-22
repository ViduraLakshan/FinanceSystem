package com.financeSystem.account.fixed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.financeSystem.account.Account;


public class FixedAccountDao {
	private final float interestRate=8.5f;
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	
	public FixedAccountDao(Connection con) {
		this.con = con;
	}
	public int createFixedDepositAccount(Account fixedDepositAccount){
        String INSERT_ACCOUNT_SQL = "INSERT INTO fixed_accounts" +
            "  (account_no, created_date, maturity_date, fixed_balance, interestRate, interest_earned, user_id, account_type) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?, ?);";

        int result = 0;

        //Class.forName("com.mysql.jdbc.Driver");

        try {
            // Step 2:Create a statement using connection object
            pst = con.prepareStatement(INSERT_ACCOUNT_SQL);
            pst.setString(1, fixedDepositAccount.getAccount_no());
            pst.setString(2, java.time.LocalDate.now().toString());
            pst.setString(3, java.time.LocalDate.now().plusYears(2).toString());
            pst.setFloat(4, fixedDepositAccount.getBalance());
            pst.setFloat(5, interestRate);
            pst.setFloat(6, 0.0f);
            pst.setInt(7, fixedDepositAccount.getUser_id());
            pst.setString(8, fixedDepositAccount.getAccount_type());
            //User userFromDb= new User();
            
          

            System.out.println(pst);
            // Step 3: Execute the query or update query
            result = pst.executeUpdate();
        } catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
        
        return result;
    }
	public List<FixedDepositAccount> getAccountByUserId(int user_id) {
		List<FixedDepositAccount> accountList= new ArrayList<FixedDepositAccount>();

		try {
			pst = con.prepareStatement("select * from fixed_accounts where user_id = ?");
			pst.setInt(1, user_id);

			System.out.println(pst);
			rs = pst.executeQuery();
			 while (rs.next()) {
				 FixedDepositAccount savingsAccount=new FixedDepositAccount();
				 savingsAccount.setAccount_no(rs.getString("account_no"));
				 savingsAccount.setBalance(rs.getFloat("fixed_balance"));
				 savingsAccount.setId(rs.getInt("id"));
				 savingsAccount.setInterestRate(rs.getFloat("interestRate"));
				 savingsAccount.setInterestEarned(rs.getFloat("interest_earned"));
				 savingsAccount.setCreated_date(rs.getString("created_date"));
				 savingsAccount.setMaturity_date(rs.getString("maturity_date"));
				 savingsAccount.setAccount_type(rs.getString("account_type"));
				 accountList.add(savingsAccount);
			 }

		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return accountList;
	}
	
	public FixedDepositAccount getAccountByAccountId(int account_id) {
		FixedDepositAccount accountFromDb= new FixedDepositAccount();

		try {
			pst = con.prepareStatement("select * from fixed_accounts where id = ?"); 
			pst.setInt(1, account_id);

			System.out.println(pst);
			rs = pst.executeQuery();
			 while (rs.next()) {
				 accountFromDb.setAccount_no(rs.getString("account_no"));
				 accountFromDb.setBalance(rs.getFloat("fixed_balance"));
				 accountFromDb.setId(rs.getInt("id"));
				 accountFromDb.setInterestRate(rs.getFloat("interestRate"));
				 accountFromDb.setInterestEarned(rs.getFloat("interest_earned"));
				 accountFromDb.setCreated_date(rs.getString("created_date"));
				 accountFromDb.setMaturity_date(rs.getString("maturity_date"));
				 accountFromDb.setAccount_type(rs.getString("account_type"));
			 }

		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return accountFromDb;
	}
	
	public String transactionUpdateDepositAccount(int account_id, float amount, String transaction_type){
		FixedDepositAccount accountFromDb= getAccountByAccountId(account_id);
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
			String UPDATE_ACCOUNT_SQL = "UPDATE fixed_accounts SET fixed_balance = ? WHERE id = ?;";

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
	
	public void fixedInterestCalculate() {
    	
		String getDateNow=java.time.LocalDate.now().toString();
		String getDate=getDateNow.substring(8);
		
		try {

				// Step 2:Create a statement using connection object
			pst = con.prepareStatement("select * from fixed_accounts where created_date like?");
			pst.setString(1, "%"+getDate);
			
			System.out.println(pst);
			rs = pst.executeQuery();
				
			 while (rs.next()) {
				 int id=rs.getInt("id");
				 float balance=rs.getFloat("fixed_balance");
				 float interestRate=rs.getFloat("interestRate");
				 float interest_earned=rs.getFloat("interest_earned");
				 float interest_earned_new=interest_earned+((balance*interestRate)/100);
				 float newBalance=balance+(balance*interestRate)/100;
				
				 
				 String UPDATE_ACCOUNT_SQL = "UPDATE fixed_accounts SET fixed_balance = ?, interest_earned = ? WHERE id = ?;";
				 			           
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
