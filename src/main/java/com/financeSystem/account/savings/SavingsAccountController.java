package com.financeSystem.account.savings;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class SavingsAccountController
 */
@WebServlet("/SavingsAccount")
public class SavingsAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SavingsAccountDao savingsAccountDao;
	

    public void init() {
    	savingsAccountDao = new SavingsAccountDao();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SavingsAccountController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		int user_id = (int) session.getAttribute("user_id");
		try {
			List<SavingsAccount> accountList=savingsAccountDao.getAccountsByUserId(user_id);
			session.setAttribute("accountList",accountList);
			response.sendRedirect("savings.jsp");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
