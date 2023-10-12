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

    public int registerUser(User user) throws ClassNotFoundException {
        String INSERT_USERS_SQL = "INSERT INTO users" +
            "  (username, email) VALUES " +
            " (?, ?);";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getemail());
          
          

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            result = preparedStatement.executeUpdate();
            

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
    }
    
    public int checkUser(User user) throws ClassNotFoundException {
		

		Class.forName("com.mysql.jdbc.Driver");
		int id=0;
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from users where username = ?")) {
			preparedStatement.setString(1, user.getUserName());

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			id=rs.getInt("id");

		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return id;
	}
    
    public int validate(LoginData loginData) throws ClassNotFoundException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
    	String encKeyString = "1234567890123456";
		EncryptAndDeEncryptPassword encryptAndDeEncryptPassword=new EncryptAndDeEncryptPassword();
		String encryptpassword=encryptAndDeEncryptPassword.encryptMessage(loginData.getPassword().getBytes(), encKeyString.getBytes());
		boolean status = false;
		int id=0;
		Class.forName("com.mysql.jdbc.Driver");

		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from login_data where username = ? and password = ? ")) {
			preparedStatement.setString(1, loginData.getUserName());
			preparedStatement.setString(2, encryptpassword);

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
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

	public void addLoginData(LoginData loginData) throws ClassNotFoundException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
		String encKeyString = "1234567890123456";
		EncryptAndDeEncryptPassword encryptAndDeEncryptPassword=new EncryptAndDeEncryptPassword();
		String encryptpassword=encryptAndDeEncryptPassword.encryptMessage(loginData.getPassword().getBytes(), encKeyString.getBytes());
		
		String INSERT_LOGIN_SQL = "INSERT INTO login_data" +
	            "  (username, email, password, user_id) VALUES " +
	            " (?, ?, ?, ?);";


	        Class.forName("com.mysql.jdbc.Driver");

	        try (Connection connection = DriverManager
	            .getConnection("jdbc:mysql://localhost:3306/fsdb?useSSL=false", "root", "3636");

	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LOGIN_SQL)) {
	            preparedStatement.setString(1, loginData.getUserName());
	            preparedStatement.setString(2, loginData.getEmail());
	            preparedStatement.setString(3, encryptpassword);
	            preparedStatement.setInt(4, loginData.getUser_id());
	          

	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            preparedStatement.executeUpdate();

	        } catch (SQLException e) {
	            // process sql exception
	            printSQLException(e);
	        }
		
	}
}