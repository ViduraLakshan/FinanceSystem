package com.financeSystem.account.loan;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.financeSystem.connection.DbConnection;


/**
 * Servlet implementation class LoanAccountController
 */
@WebServlet("/LoanAccount")
public class LoanAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoanAccountDao loanAccountDao;

    public void init() {
    	try {
    	loanAccountDao=new LoanAccountDao(DbConnection.getConnection());
    	}catch(Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoanAccountController() {
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
		List<LoanAccount> accountList=loanAccountDao.getAccountByUserId(user_id);
		session.setAttribute("accountList",accountList);
		response.sendRedirect("loan.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
