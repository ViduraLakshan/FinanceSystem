package com.financeSystem.transaction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.financeSystem.account.fixed.FixedAccountDao;
import com.financeSystem.account.fixed.FixedDepositAccount;
import com.financeSystem.account.loan.LoanAccount;
import com.financeSystem.account.loan.LoanAccountDao;
import com.financeSystem.account.savings.SavingsAccount;
import com.financeSystem.account.savings.SavingsAccountDao;
import com.financeSystem.connection.DbConnection;

/**
 * Servlet implementation class TransactionController
 */
@WebServlet("/transaction")
public class TransactionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SavingsAccountDao savingsAccountDao;
	private TransactionDao transactionDao;
	private FixedAccountDao fixedDepositDao;
	private LoanAccountDao loanAccountDao;
	
    public void init() {
    	try {
    	savingsAccountDao = new SavingsAccountDao(DbConnection.getConnection());
    	transactionDao=new TransactionDao(DbConnection.getConnection());
    	fixedDepositDao =new FixedAccountDao(DbConnection.getConnection());
    	loanAccountDao=new LoanAccountDao(DbConnection.getConnection());
    	}catch(Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionController() {
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
		
		if(account_type.equals("savingsaccounts")) {
			SavingsAccount savingsAccount=savingsAccountDao.getAccountByAccountId(account_id);
			session.setAttribute("account",savingsAccount);
			response.sendRedirect("transaction.jsp");
		}else if(account_type.equals("fixeddeposits")) {
			FixedDepositAccount fixedDepositAccount=fixedDepositDao.getAccountByAccountId(account_id);
			session.setAttribute("account",fixedDepositAccount);
			response.sendRedirect("transaction.jsp");
		}
		else if(account_type.equals("loans")) {
			LoanAccount loanAccount=loanAccountDao.getAccountByAccountId(account_id);
			session.setAttribute("account",loanAccount);
			response.sendRedirect("loanRePayment.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		int account_id = Integer.parseInt(request.getParameter("id"));
		String account_type = request.getParameter("account_type");
		String transactionType = request.getParameter("transactiontype");
		String amountString = request.getParameter("amount");
		
		
		float amount=0.0f;
		//amount
		if (amountString != null && !amountString.isEmpty()) {
            try {
            	amount = Integer.parseInt(amountString);
               
                response.getWriter().println("Number from request: " + amount);
            } catch (NumberFormatException e) {    
                response.getWriter().println("Invalid number format");
            }
        } else {
            response.getWriter().println("Number parameter not found in the request");
        }
		
		System.out.println(account_id+" "+amount+" "+transactionType);
		
		
		if(account_type.equals("savingsaccounts")) {
			String savingsAccountUpdate=savingsAccountDao.transactionUpdateSavingAccount(account_id, amount, transactionType);
			if(savingsAccountUpdate.equals("success")) {
				SavingsAccount savingsAccount=savingsAccountDao.getAccountByAccountId(account_id);
				transactionDao.createTransactio(savingsAccount, amount, transactionType, account_type);
				session.setAttribute("account",savingsAccount);
				response.sendRedirect("transaction.jsp");
			}
			//session.setAttribute("account",savingsAccount);
			//response.sendRedirect("transaction.jsp");
		}else if(account_type.equals("fixeddeposits")) {
			String fixedDepositAccountUpdate=fixedDepositDao.transactionUpdateDepositAccount(account_id, amount, transactionType);
			if(fixedDepositAccountUpdate.equals("success")) {
				FixedDepositAccount fixedDepositAccount=fixedDepositDao.getAccountByAccountId(account_id);
				transactionDao.createTransactio(fixedDepositAccount, amount, transactionType, account_type);
				session.setAttribute("account",fixedDepositAccount);
				response.sendRedirect("transaction.jsp");
			}
			//session.setAttribute("account",savingsAccount);
			//response.sendRedirect("transaction.jsp");
		}
		else if(account_type.equals("loans")) {
			String loanAccountUpdate=loanAccountDao.transactionUpdateLoanAccount(account_id, amount, transactionType);
			if(loanAccountUpdate.equals("success")) {
				LoanAccount loanAccount=loanAccountDao.getAccountByAccountId(account_id);
				transactionDao.createTransactio(loanAccount, amount, transactionType, account_type);
				session.setAttribute("account",loanAccount);
				response.sendRedirect("transaction.jsp");
			}
			//session.setAttribute("account",savingsAccount);
			//response.sendRedirect("transaction.jsp");
		}
	}

}
