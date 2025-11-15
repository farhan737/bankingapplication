package bankingapplication.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import bankingapplication.dao.AccountsDao;
import bankingapplication.dto.Accounts;
import bankingapplication.dto.Transactions;
import bankingapplication.dto.Users;
import bankingapplication.service.TransactionService;
import bankingapplication.service.impl.TransactionServiceImpl;

/**
 * Servlet implementation class DepositServlet
 */
@WebServlet("/deposit")
public class DepositServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null || session.getAttribute("account") == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		Users user = (Users) session.getAttribute("user");
		Accounts account = (Accounts) session.getAttribute("account");

		int status = 1;

		// ----------- AMOUNT VALIDATION -----------
		String amountStr = request.getParameter("amount");
		if (amountStr == null || amountStr.isEmpty()) {
			status = -4; // invalid amount
		}

		double amount = 0;
		if (status > 0) {
			amount = Double.parseDouble(amountStr);
		}

		// ----------- PIN VALIDATION -----------
		String pinStr = request.getParameter("accountPin");
		if (pinStr == null || pinStr.isEmpty()) {
			status = -2; // PIN missing
		} else if (!pinStr.equals(account.getAccountPin())) {
			status = -12; // wrong PIN
		}

		// Stop early if any validation failed
		if (status < 0) {
			request.setAttribute("status", status);
			request.getRequestDispatcher("withdraw.jsp").forward(request, response);
			return;
		}

		// ----------- CREATE TRANSACTION OBJECT -----------
		Transactions transaction = new Transactions();
		transaction.setUserId(user.getUserId());
		transaction.setAccountId(account.getAccountId());
		transaction.setTransactionType("deposit");
		transaction.setAmount(amount);

		TransactionService tService = new TransactionServiceImpl();

		// validate business rules
		status = tService.validateTransaction(transaction);
		if (status < 0) {
			request.setAttribute("status", status);
			request.getRequestDispatcher("deposit.jsp").forward(request, response);
			return;
		}

		// ----------- EXECUTE DEPOSIT -----------
		boolean success = tService.deposit(account, transaction);

		if (!success) {
			status = -11;
			request.setAttribute("status", status);
			request.getRequestDispatcher("deposit.jsp").forward(request, response);
			return;
		}

		// ----------- UPDATE SESSION -----------
		Accounts updated = new AccountsDao().getAccountByAccountId(account.getAccountId());
		session.setAttribute("account", updated);
		response.sendRedirect("dashboard.jsp");
	}
}
