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
 * Servlet implementation class TransferServlet
 */
@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {
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
		Accounts fromAccount = (Accounts) session.getAttribute("account");

		int status = 1;

		// ----------- AMOUNT VALIDATION -----------
		double amount = 0;
		String amountStr = request.getParameter("amount");
		if (amountStr == null || amountStr.isEmpty()) {
		    status = -4;
		} else {
		    try {
		        amount = Double.parseDouble(amountStr);
		        if (amount <= 0) status = -4;
		    } catch (NumberFormatException e) {
		        status = -4;
		    }
		}

		// ----------- PIN VALIDATION -----------
		String pinStr = request.getParameter("accountPin");
		if (pinStr == null || pinStr.isEmpty()) {
			status = -2; // PIN missing
		} else if (!pinStr.equals(fromAccount.getAccountPin())) {
			status = -12; // wrong PIN
		}

		// Stop early if any validation failed
		if (status < 0) {
			request.setAttribute("status", status);
			request.getRequestDispatcher("transfer.jsp").forward(request, response);
			return;
		}

		// ----------- CREATE TRANSACTION OBJECT -----------
		Transactions transaction = new Transactions();
		transaction.setUserId(user.getUserId());
		transaction.setAccountId(fromAccount.getAccountId());
		int toAccountId = 0;
		try {
		    toAccountId = Integer.parseInt(request.getParameter("toAccountId"));
		} catch (NumberFormatException e) {
		    status = -7;
		}
		transaction.setToAccountId(toAccountId);
		transaction.setTransactionType("transfer_out");
		transaction.setAmount(amount);

		TransactionService tService = new TransactionServiceImpl();

		// validate business rules
		status = tService.validateTransaction(transaction);
		if (status < 0) {
			request.setAttribute("status", status);
			request.getRequestDispatcher("transfer.jsp").forward(request, response);
			return;
		}

		// ----------- EXECUTE TRANSFER -----------
		Accounts toAccount = new AccountsDao().getAccountByAccountId(transaction.getToAccountId());

		if (toAccount == null) {
			status = -7; // "destination account missing"
			request.setAttribute("status", status);
			request.getRequestDispatcher("transfer.jsp").forward(request, response);
			return;
		}
		
		if (toAccount.getAccountId() == fromAccount.getAccountId()) {
		    status = -8;
		    request.setAttribute("status", status);
		    request.getRequestDispatcher("transfer.jsp").forward(request, response);
		    return;
		}


		boolean success = tService.transfer(fromAccount, toAccount, transaction);

		if (!success) {
			status = -11; // probably insufficient balance
			request.setAttribute("status", status);
			request.getRequestDispatcher("transfer.jsp").forward(request, response);
			return;
		}

		// ----------- UPDATE SESSION -----------
		System.out.println(status + "from transfer");
		Accounts updated = new AccountsDao().getAccountByAccountId(fromAccount.getAccountId());
		session.setAttribute("account", updated);
		response.sendRedirect("dashboard.jsp");
	}

}
