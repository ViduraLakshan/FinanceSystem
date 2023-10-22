package com.financeSystem.registration;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.financeSystem.sequrity.EncryptAndDeEncryptPassword;


public class UserDao {
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	
	
    public UserDao(Connection con) {
		this.con = con;
	}

	public int registerUser(User user) {
        String INSERT_USERS_SQL = "INSERT INTO users" +
            "  (username, email) VALUES " +
            " (?, ?);";

        int result = 0;

        try {

            pst = con.prepareStatement(INSERT_USERS_SQL);
            pst.setString(1, user.getUserName());
            pst.setString(2, user.getemail());
        
            System.out.println(pst);
            // Step 3: Execute the query or update query
            result = pst.executeUpdate();
            

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
    }
    
    public int checkUser(User user) {
		
		int id=0;
		try {
			pst = con.prepareStatement("select * from users where username = ?");
			pst.setString(1, user.getUserName());

			System.out.println(pst);
			rs = pst.executeQuery();
			rs.next();
			id=rs.getInt("id");

		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return id;
	}
    
    public int validate(LoginData loginData) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
    	String encKeyString = "1234567890123456";
		EncryptAndDeEncryptPassword encryptAndDeEncryptPassword=new EncryptAndDeEncryptPassword();
		String encryptpassword=encryptAndDeEncryptPassword.encryptMessage(loginData.getPassword().getBytes(), encKeyString.getBytes());
		boolean status = false;
		int id=0;

		try {
			pst = con.prepareStatement("select * from login_data where username = ? and password = ? ");
			pst.setString(1, loginData.getUserName());
			pst.setString(2, encryptpassword);

			System.out.println(pst);
			rs = pst.executeQuery();
			status = rs.next();
			if(status) {
				id=rs.getInt("user_id");
			}

		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return id;
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

	public void addLoginData(LoginData loginData) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
		String encKeyString = "1234567890123456";
		EncryptAndDeEncryptPassword encryptAndDeEncryptPassword=new EncryptAndDeEncryptPassword();
		String encryptpassword=encryptAndDeEncryptPassword.encryptMessage(loginData.getPassword().getBytes(), encKeyString.getBytes());
		
		String INSERT_LOGIN_SQL = "INSERT INTO login_data" +
	            "  (username, email, password, user_id) VALUES " +
	            " (?, ?, ?, ?);";

	    try {

	         pst = con.prepareStatement(INSERT_LOGIN_SQL);
	         pst.setString(1, loginData.getUserName());
	         pst.setString(2, loginData.getEmail());
	         pst.setString(3, encryptpassword);
	         pst.setInt(4, loginData.getUser_id());

	         System.out.println(pst);
	         pst.executeUpdate();

	        } catch (SQLException e) {
	            // process sql exception
	            printSQLException(e);
	        }
		
	}
}