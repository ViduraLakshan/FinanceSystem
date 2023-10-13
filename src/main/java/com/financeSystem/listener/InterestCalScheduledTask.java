package com.financeSystem.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.financeSystem.account.fixed.FixedAccountDao;
import com.financeSystem.account.loan.LoanAccountDao;
import com.financeSystem.account.savings.SavingsAccountDao;

public class InterestCalScheduledTask implements Runnable {
	
	
    @Override
    public void run() {
    	savingsInterestCalculate();
    	loanInterestCalculate();
    	fixedInterestCalculate();
    }
    
    public void savingsInterestCalculate() {
    	
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
						.prepareStatement("select * from savings_accounts where created_date like?")) {
			preparedStatement.setString(1, "%"+getDate);
			
			
			
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			
			
			
			 while (rs.next()) {
				 int id=rs.getInt("id");
				 float balance=rs.getFloat("balance");
				 float interestRate=rs.getFloat("interestRate");
				 float interest_earned=rs.getFloat("interest_earned");
				 float interest_earned_new=interest_earned+((balance*interestRate)/100);
				 float newBalance=balance+(balance*interestRate)/100;
				 
				 
				 String UPDATE_ACCOUNT_SQL = "UPDATE savings_accounts SET balance = ?, interest_earned = ? WHERE id = ?;";
				 

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
    public void fixedInterestCalculate() {
    	
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
						.prepareStatement("select * from fixed_accounts where created_date like?")) {
			preparedStatement.setString(1, "%"+getDate);
			
			
			
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			
			
			
			 while (rs.next()) {
				 int id=rs.getInt("id");
				 float balance=rs.getFloat("fixed_balance");
				 float interestRate=rs.getFloat("interestRate");
				 float interest_earned=rs.getFloat("interest_earned");
				 float interest_earned_new=interest_earned+((balance*interestRate)/100);
				 float newBalance=balance+(balance*interestRate)/100;
				
				 
				 String UPDATE_ACCOUNT_SQL = "UPDATE fixed_accounts SET fixed_balance = ?, interest_earned = ? WHERE id = ?;";
				 

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