package com.financeSystem.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.financeSystem.account.fixed.FixedDepositAccount;
import com.financeSystem.account.loan.LoanAccount;
import com.financeSystem.account.savings.SavingsAccount;

public class TransactionDao {
	private final float interestRate=8.5f;
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	
	public TransactionDao(Connection con) {
		this.con = con;
	}
	public int createTransactio(SavingsAccount savingsAccount, float amount, String transaction_type,String account_type) {
		
		
		String INSERT_ACCOUNT_SQL = "INSERT INTO transaction_details" +
            "  (account_no , transaction_date , balance , detail , account_id, amount, account_type) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?);";

        int result = 0;

        try {

            pst = con.prepareStatement(INSERT_ACCOUNT_SQL);
            pst.setString(1, savingsAccount.getAccount_no());
            pst.setString(2, java.time.LocalDate.now().toString());
            pst.setFloat(3, savingsAccount.getBalance());
            pst.setString(4, transaction_type);
            pst.setInt(5, savingsAccount.getId());
            pst.setFloat(6, amount);
            pst.setString(7, account_type);

            System.out.println(pst);
            result = pst.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
    }
	public int createTransactio(FixedDepositAccount fixedDepositAccount, float amount, String transaction_type,String account_type) {

		String INSERT_ACCOUNT_SQL = "INSERT INTO transaction_details" +
            "  (account_no , transaction_date , balance , detail , account_id, amount, account_type) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?);";

        int result = 0;

        try {

            pst = con.prepareStatement(INSERT_ACCOUNT_SQL);
            pst.setString(1, fixedDepositAccount.getAccount_no());
            pst.setString(2, java.time.LocalDate.now().toString());
            pst.setFloat(3, fixedDepositAccount.getBalance());
            pst.setString(4, transaction_type);
            pst.setInt(5, fixedDepositAccount.getId());
            pst.setFloat(6, amount);
            pst.setString(7, account_type);

            System.out.println(pst);
            // Step 3: Execute the query or update query
            result = pst.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
    }
	
public int createTransactio(LoanAccount loanAccountAccount, float amount, String transaction_type,String account_type) {
		
		
		String INSERT_ACCOUNT_SQL = "INSERT INTO transaction_details" +
            "  (account_no , transaction_date , balance , detail , account_id, amount, account_type) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?);";

        int result = 0;

        try {

            pst = con.prepareStatement(INSERT_ACCOUNT_SQL);
            pst.setString(1, loanAccountAccount.getAccount_no());
            pst.setString(2, java.time.LocalDate.now().toString());
            pst.setFloat(3, loanAccountAccount.getBalance());
            pst.setString(4, transaction_type);
            pst.setInt(5, loanAccountAccount.getId());
            pst.setFloat(6, amount);
            pst.setString(7, account_type);

            System.out.println(pst);
            // Step 3: Execute the query or update query
            result = pst.executeUpdate();

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
