package com.financeSystem.registration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/register")
public class UserRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    public void init() {
    	userDao = new UserDao();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	HttpSession session = request.getSession();
        String username = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        

        User user = new User();
        user.setUserName(username);
        user.setemail(email);
        LoginData loginData= new LoginData();
        loginData.setUserName(username);
        loginData.setEmail(email);
        loginData.setPassword(password);
        
        try {
        	int user_id=userDao.checkUser(user);
        	System.out.println(user_id);
			if (user_id>0) {
				session.setAttribute("userError","User Name allrady axist");
			} else {
				session.setAttribute("userError","");
				userDao.registerUser(user);
				user_id=userDao.checkUser(user);
				loginData.setUser_id(user_id);
				userDao.addLoginData(loginData);
				
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



        response.sendRedirect("registration.jsp");
    }
}
