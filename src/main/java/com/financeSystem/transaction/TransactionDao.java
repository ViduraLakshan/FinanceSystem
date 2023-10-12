package com.financeSystem.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.financeSystem.account.fixed.FixedDepositAccount;
import com.financeSystem.account.loan.LoanAccount;
import com.financeSystem.account.savings.SavingsAccount;

public class TransactionDao {
	
    
	public int createTransactio(SavingsAccount savingsAccount, float amount, String transaction_type,String account_type) throws ClassNotFoundException {
		
		
		String INSERT_ACCOUNT_SQL = "INSERT INTO transaction_details" +
            "  (account_no , transaction_date , balance , detail , account_id, amount, account_type) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?);";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT_SQL)) {
            preparedStatement.setString(1, savingsAccount.getAccount_no());
            preparedStatement.setString(2, java.time.LocalDate.now().toString());
            preparedStatement.setFloat(3, savingsAccount.getBalance());
            preparedStatement.setString(4, transaction_type);
            preparedStatement.setInt(5, savingsAccount.getId());
            preparedStatement.setFloat(6, amount);
            preparedStatement.setString(7, account_type);
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
	public int createTransactio(FixedDepositAccount fixedDepositAccount, float amount, String transaction_type,String account_type) throws ClassNotFoundException {
		
		
		String INSERT_ACCOUNT_SQL = "INSERT INTO transaction_details" +
            "  (account_no , transaction_date , balance , detail , account_id, amount, account_type) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?);";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT_SQL)) {
            preparedStatement.setString(1, fixedDepositAccount.getAccount_no());
            preparedStatement.setString(2, java.time.LocalDate.now().toString());
            preparedStatement.setFloat(3, fixedDepositAccount.getBalance());
            preparedStatement.setString(4, transaction_type);
            preparedStatement.setInt(5, fixedDepositAccount.getId());
            preparedStatement.setFloat(6, amount);
            preparedStatement.setString(7, account_type);
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
	
public int createTransactio(LoanAccount loanAccountAccount, float amount, String transaction_type,String account_type) throws ClassNotFoundException {
		
		
		String INSERT_ACCOUNT_SQL = "INSERT INTO transaction_details" +
            "  (account_no , transaction_date , balance , detail , account_id, amount, account_type) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?);";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT_SQL)) {
            preparedStatement.setString(1, loanAccountAccount.getAccount_no());
            preparedStatement.setString(2, java.time.LocalDate.now().toString());
            preparedStatement.setFloat(3, loanAccountAccount.getBalance());
            preparedStatement.setString(4, transaction_type);
            preparedStatement.setInt(5, loanAccountAccount.getId());
            preparedStatement.setFloat(6, amount);
            preparedStatement.setString(7, account_type);
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
