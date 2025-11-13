package bankingapplication.controller;

import java.io.IOException;

import bankingapplication.dao.UserDao;
import bankingapplication.dao.UserDetailsDao;
import bankingapplication.dto.Users;
import bankingapplication.service.UserService;
import bankingapplication.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class LogniServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getSession().invalidate(); // clear stale login session
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Users user = new Users();
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));

		UserService userService = new UserServiceImpl();

		int status = userService.validateUserInfo(user);

		if (status < 0) {
			String message = ((UserServiceImpl) userService).getStatusmessage().get(status);
			request.setAttribute("status", message);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {
			if (userService.loginUser(user)) {

				UserDao uDao = new UserDao();
				Users loggedInUser = uDao.getUserByEmail(user.getEmail()); // full user object

				HttpSession session = request.getSession();
				session.setAttribute("user", loggedInUser);

				UserDetailsDao uDDao = new UserDetailsDao();
				session.setAttribute("userDetails", uDDao.getUserDetailsById(loggedInUser.getUserId()));

				response.sendRedirect("dashboard.jsp");
			} else {
				status = -17;
				String message = ((UserServiceImpl) userService).getStatusmessage().get(status);
				request.setAttribute("status", message);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}

		}
	}
}
