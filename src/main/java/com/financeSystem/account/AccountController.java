package com.financeSystem.account;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.financeSystem.account.fixed.FixedAccountDao;
import com.financeSystem.account.loan.LoanAccountDao;
import com.financeSystem.account.savings.SavingsAccountDao;

/**
 * Servlet implementation class AccountController
 */
@WebServlet("/createaccount")
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private SavingsAccountDao savingsAccountDao;
	private FixedAccountDao fixedDepositDao;
	private LoanAccountDao loanAccountDao;

    public void init() {
    	savingsAccountDao = new SavingsAccountDao();
    	fixedDepositDao=new FixedAccountDao();
    	loanAccountDao=new LoanAccountDao();
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("accountCreation.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accountType = request.getParameter("accounttype");
		String depositString = request.getParameter("deposit");
		HttpSession session = request.getSession();
		int user_id = (int) session.getAttribute("user_id");
		System.out.println(user_id);
		float deposit=0.0f;
		if (depositString != null && !depositString.isEmpty()) {
            try {
                
            	deposit = Integer.parseInt(depositString);
               
                response.getWriter().println("Number from request: " + deposit);
            } catch (NumberFormatException e) {    
                response.getWriter().println("Invalid number format");
            }
        } else {
            response.getWriter().println("Number parameter not found in the request");
        }
		String account_num;
		account_num = (String) session.getAttribute("account_no");
		Account account=new Account();
		
		account.setAccount_type(accountType);
		account.setBalance(deposit);
		account.setUser_id(user_id);
		int min = 10000000;
        int max = 99999999;
        int randomNum = new Random().nextInt((max - min) + 1) + min;
        account_num= String.valueOf(randomNum);
        account.setAccount_no(account_num);
		
		
		try {
			
			if(accountType.equals("savingsaccounts")){
				savingsAccountDao.createSavingAccount(account);
			}else if(accountType.equals("fixeddeposits")) {
				fixedDepositDao.createFixedDepositAccount(account);
			}else if(accountType.equals("loans")) {
				loanAccountDao.createLoanAccount(account);
			}
			response.sendRedirect("accountCreation.jsp");
			
			
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
	}

}
