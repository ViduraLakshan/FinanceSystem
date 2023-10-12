package com.financeSystem.account.fixed;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class FixedDepositAccountController
 */
@WebServlet("/FixedDeposit")
public class FixedDepositAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private FixedAccountDao fixedDepositDao;

    public void init() {
    	fixedDepositDao=new FixedAccountDao();
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FixedDepositAccountController() {
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
			List<FixedDepositAccount> accountList=fixedDepositDao.getAccountByUserId(user_id);
			session.setAttribute("accountList",accountList);
			response.sendRedirect("fixed.jsp");
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
