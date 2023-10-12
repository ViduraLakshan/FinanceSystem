package com.financeSystem.transactionDelails;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.financeSystem.transaction.Transaction;

public class TransactionDetailsDao {

	public List<Transaction> gettransactionDetailsByAccountId(int account_id, String account_type) throws ClassNotFoundException {
		List<Transaction> transactionList=new ArrayList<Transaction>();
		Class.forName("com.mysql.jdbc.Driver");

		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from transaction_details where account_id = ? and account_type = ?")) {
			preparedStatement.setInt(1, account_id);
			preparedStatement.setString(2, account_type);

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			 while (rs.next()) {
				 Transaction transaction=new Transaction();
				 transaction.setAccount_no(rs.getString("account_no"));
				 transaction.setBalance(rs.getFloat("balance"));
				 transaction.setId(rs.getInt("id"));
				 transaction.setAccount_id(rs.getInt("account_id"));
				 transaction.setAmount(rs.getFloat("amount"));
				 transaction.setDetail(rs.getString("detail"));
				 transaction.setAccount_type(rs.getString("account_type"));
				 transaction.setTransaction_date(rs.getString("transaction_date"));
				 
				 transactionList.add(transaction);
			 }

		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return transactionList;
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
