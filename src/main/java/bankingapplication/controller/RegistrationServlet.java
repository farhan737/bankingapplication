package bankingapplication.controller;

import java.io.IOException;
import java.time.LocalDate;

import bankingapplication.dto.UserDetails;
import bankingapplication.dto.Users;
import bankingapplication.service.UserService;
import bankingapplication.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Users user = new Users();
		UserDetails userDetails = new UserDetails();

		// set initial user details
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));

		// set complete user details
		userDetails.setFirstName(request.getParameter("firstName"));
		userDetails.setLastName(request.getParameter("lastName"));
		userDetails.setContactNumber(request.getParameter("contactNumber"));
		userDetails.setPermanentAddress(request.getParameter("permanentAddress"));
		userDetails.setPincode(request.getParameter("pincode"));
		userDetails.setState(request.getParameter("state"));
		userDetails.setDistrict(request.getParameter("district"));
		userDetails.setCity(request.getParameter("city"));
		String dobParam = request.getParameter("dateOfBirth");
		if (dobParam != null && !dobParam.isEmpty()) {
			userDetails.setDateOfBirth(LocalDate.parse(dobParam));
		} else {
			userDetails.setDateOfBirth(null);
		}

		UserService uService = new UserServiceImpl();

		int status = uService.validateUserInfo(user, userDetails);
		String confirmPassword = request.getParameter("confirmPassword");
		if (!user.getPassword().equals(confirmPassword)) {
			status = -16;
		}
		if (status < 0) {
			String message = ((UserServiceImpl) uService).getStatusmessage().get(status);
			request.setAttribute("status", message);
			request.getRequestDispatcher("register.jsp").forward(request, response);
		} else {
			if (uService.registerUser(user, userDetails)) {
				response.sendRedirect("index.jsp");
			} else {
				request.setAttribute("exists", true);
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}
		}
	}
}
