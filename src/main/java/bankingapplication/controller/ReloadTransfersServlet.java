package bankingapplication.controller;

import java.io.IOException;
import java.util.List;

import bankingapplication.dao.TransactionsDao;
import bankingapplication.dto.Accounts;
import bankingapplication.dto.Transactions;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ReloadTransfersServlet
 */
@WebServlet("/reloadtransactions")
public class ReloadTransfersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Accounts currentAccount = (Accounts) session.getAttribute("account");
		TransactionsDao tDao = new TransactionsDao();
		List<Transactions> transactions = tDao.getTransactionByAccountId(currentAccount.getAccountId());
		session.setAttribute("transactions", transactions);
		response.sendRedirect("transaction-history.jsp");
	}
}
