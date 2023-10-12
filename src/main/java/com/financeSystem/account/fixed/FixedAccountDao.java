package com.financeSystem.account.fixed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.financeSystem.account.Account;


public class FixedAccountDao {
	private final float interestRate=8.5f;
	
	public int createFixedDepositAccount(Account fixedDepositAccount) throws ClassNotFoundException {
        String INSERT_ACCOUNT_SQL = "INSERT INTO fixed_accounts" +
            "  (account_no, created_date, maturity_date, fixed_balance, interestRate, interest_earned, user_id, account_type) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?, ?);";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT_SQL)) {
            preparedStatement.setString(1, fixedDepositAccount.getAccount_no());
            preparedStatement.setString(2, java.time.LocalDate.now().toString());
            preparedStatement.setString(3, java.time.LocalDate.now().plusYears(2).toString());
            preparedStatement.setFloat(4, fixedDepositAccount.getBalance());
            preparedStatement.setFloat(5, interestRate);
            preparedStatement.setFloat(6, 0.0f);
            preparedStatement.setInt(7, fixedDepositAccount.getUser_id());
            preparedStatement.setString(8, fixedDepositAccount.getAccount_type());
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
	public List<FixedDepositAccount> getAccountByUserId(int user_id) throws ClassNotFoundException {
		List<FixedDepositAccount> accountList= new ArrayList<FixedDepositAccount>();
		Class.forName("com.mysql.jdbc.Driver");

		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from fixed_accounts where user_id = ?")) {
			preparedStatement.setInt(1, user_id);

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
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
	
	public FixedDepositAccount getAccountByAccountId(int account_id) throws ClassNotFoundException {
		FixedDepositAccount accountFromDb= new FixedDepositAccount();
		Class.forName("com.mysql.jdbc.Driver");

		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from fixed_accounts where id = ?")) {
			preparedStatement.setInt(1, account_id);

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
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
	
	public String transactionUpdateDepositAccount(int account_id, float amount, String transaction_type) throws ClassNotFoundException {
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
