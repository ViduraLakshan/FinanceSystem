package com.financeSystem.account.loan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.financeSystem.account.Account;

public class LoanAccountDao {
	private final float interestRate=8.5f;
	
	public int createLoanAccount(Account loanAccount) throws ClassNotFoundException {
        String INSERT_ACCOUNT_SQL = "INSERT INTO loan_accounts" +
            "  (account_no, created_date, loan_balance, interestRate, interest_balance, user_id, account_type) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?);";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT_SQL)) {
            preparedStatement.setString(1, loanAccount.getAccount_no());
            preparedStatement.setString(2, java.time.LocalDate.now().toString());
            preparedStatement.setFloat(3, loanAccount.getBalance());
            preparedStatement.setFloat(4, interestRate);
            preparedStatement.setFloat(5, 0.0f);
            preparedStatement.setInt(6, loanAccount.getUser_id());
            preparedStatement.setString(7, loanAccount.getAccount_type());
            //User userFromDb= new User();
            
          

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
    }
	public List<LoanAccount> getAccountByUserId(int user_id) throws ClassNotFoundException {
		List<LoanAccount> accountList= new ArrayList<LoanAccount>();
		Class.forName("com.mysql.jdbc.Driver");

		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from loan_accounts where user_id = ?")) {
			preparedStatement.setInt(1, user_id);

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
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
	public LoanAccount getAccountByAccountId(int account_id) throws ClassNotFoundException {
		LoanAccount accountFromDb= new LoanAccount();
		Class.forName("com.mysql.jdbc.Driver");

		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from loan_accounts where id = ?")) {
			preparedStatement.setInt(1, account_id);

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
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
	
	public String transactionUpdateLoanAccount(int account_id, float amount, String transaction_type) throws ClassNotFoundException {
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

		        Class.forName("com.mysql.jdbc.Driver");

		        try (Connection connection = DriverManager
		            .getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

		            // Step 2:Create a statement using connection object
		            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_SQL)) {
		            preparedStatement.setFloat(1, balance);
		            preparedStatement.setInt(2, account_id);
		            
		            //User userFromDb= new User();
		            
		          

		            System.out.println(preparedStatement);
		            // Step 3: Execute the query or update query
		            preparedStatement.executeUpdate();
		            error="success";
		        } catch (SQLException e) {
		            // process sql exception
		            printSQLException(e);
		        }
		}
		
        return error;
    }
	
	public void loanInterestCalculate() {
    	
    	try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String getDateNow=java.time.LocalDate.now().toString();
		String getDate=getDateNow.substring(8);
		
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from loan_accounts where created_date like?")) {
			preparedStatement.setString(1, "%"+getDate);
			
			
			
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			
			
			
			 while (rs.next()) {
				 int id=rs.getInt("id");
				 float balance=rs.getFloat("loan_balance");
				 float interestRate=rs.getFloat("interestRate");
				 float interest_earned=rs.getFloat("interest_balance");
				 float interest_earned_new=interest_earned+((balance*interestRate)/100);
				 float newBalance=balance+(balance*interestRate)/100;
				 
				 
				 String UPDATE_ACCOUNT_SQL = "UPDATE loan_accounts SET loan_balance = ?, interest_balance = ? WHERE id = ?;";
				 

				            // Step 2:Create a statement using connection object
				            PreparedStatement preparedStatement2 = connection.prepareStatement(UPDATE_ACCOUNT_SQL); 
				            preparedStatement2.setFloat(1, newBalance);
				            preparedStatement2.setFloat(2, interest_earned_new);
				            preparedStatement2.setInt(3, id);
				            
				            //User userFromDb= new User();
				            
				          

				            System.out.println(preparedStatement2);
				            // Step 3: Execute the query or update query
				            preparedStatement2.executeUpdate();
				 
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
