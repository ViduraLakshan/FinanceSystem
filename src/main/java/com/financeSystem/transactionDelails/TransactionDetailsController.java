package com.financeSystem.transactionDelails;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.financeSystem.transaction.Transaction;

/**
 * Servlet implementation class TransactionDetailsController
 */
@WebServlet("/TransactionDetails")
public class TransactionDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private TransactionDetailsDao transactionDetailsDao;
    public void init() {
    	transactionDetailsDao=new TransactionDetailsDao();
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionDetailsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		int account_id = Integer.parseInt(request.getParameter("id"));
		String account_type = request.getParameter("account_type");
		
		
		try {
			List<Transaction> transactionList=transactionDetailsDao.gettransactionDetailsByAccountId(account_id,account_type);
			session.setAttribute("transactionList",transactionList);
			response.sendRedirect("transactionDetails.jsp");
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
