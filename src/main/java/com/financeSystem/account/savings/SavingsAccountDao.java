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
	
	public int createSavingAccount(Account savingsaccount) throws ClassNotFoundException {
        String INSERT_ACCOUNT_SQL = "INSERT INTO savings_accounts" +
            "  (account_no, account_type, balance , user_id, interestRate, interest_earned, created_date) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?);";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT_SQL)) {
            preparedStatement.setString(1, savingsaccount.getAccount_no());
            preparedStatement.setString(2, savingsaccount.getAccount_type());
            preparedStatement.setFloat(3, savingsaccount.getBalance());
            preparedStatement.setInt(4, savingsaccount.getUser_id());
            preparedStatement.setFloat(5, interestRate);
            preparedStatement.setFloat(6, 0.0f);
            preparedStatement.setString(7, java.time.LocalDate.now().toString());
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
	
	public List<SavingsAccount> getAccountsByUserId(int user_id) throws ClassNotFoundException {
		List<SavingsAccount> accountList= new ArrayList<SavingsAccount>();
		Class.forName("com.mysql.jdbc.Driver");

		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from savings_accounts where user_id = ?")) {
			preparedStatement.setInt(1, user_id);

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
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
	
	public SavingsAccount getAccountByAccountId(int account_id) throws ClassNotFoundException {
		SavingsAccount accountFromDb= new SavingsAccount();
		Class.forName("com.mysql.jdbc.Driver");

		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from savings_accounts where id = ?")) {
			preparedStatement.setInt(1, account_id);

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
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
	
	public String transactionUpdateSavingAccount(int account_id, float amount, String transaction_type) throws ClassNotFoundException {
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
