package bankingapplication.controller;

import java.io.IOException;

import bankingapplication.dao.AccountsDao;
import bankingapplication.dto.Accounts;
import bankingapplication.dto.Transactions;
import bankingapplication.dto.Users;
import bankingapplication.service.AccountService;
import bankingapplication.service.impl.AccountServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/create-account")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Users user = (Users) session.getAttribute("user");
		Accounts account = new Accounts();
		int status = 0;
		account.setUserId(user.getUserId());
		String amountStr = request.getParameter("amount");
		double initialDeposit = 0.0;
		if (amountStr == null || amountStr.isEmpty()) {
			status = -5; // amount can't be null or empty
		} else
			initialDeposit = Double.parseDouble(amountStr);
		account.setAccountType(request.getParameter("accountType"));
		account.setBalance(initialDeposit);
		account.setAccountPin(request.getParameter("accountPin"));
		AccountService accountService = new AccountServiceImpl();
		status = status < 0 ? status : accountService.validateAccount(account);
		if (status > 0) {
			accountService.createAccount(account);
			Accounts createdAccount = new AccountsDao().getAccountByUserId(account.getUserId());
			session.setAttribute("account", createdAccount);

			response.sendRedirect("accounts.jsp"); // <---- FIX
			return;
		} else {
			request.setAttribute("status", status);
			request.getRequestDispatcher("create-account.jsp").forward(request, response);
		}
	}

}
