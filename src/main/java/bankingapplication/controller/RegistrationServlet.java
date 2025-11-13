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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Users user = new Users();
		UserDetails userDetails = new UserDetails();
		
		//set initial user details
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));
		
		//set complete user details 
		userDetails.setFirstName(request.getParameter("firstName"));
		userDetails.setLastName(request.getParameter("lastName"));
		userDetails.setContactNumber(request.getParameter("contactNumber"));
		userDetails.setPermanentAddress(request.getParameter("permanentAddress"));
		userDetails.setPincode(request.getParameter("pincode"));
		userDetails.setState(request.getParameter("state"));
		userDetails.setDistrict(request.getParameter("district"));
		userDetails.setCity(request.getParameter("city"));
		userDetails.setDateOfBirth(LocalDate.parse(request.getParameter("dateOfBirth")));
		
		UserService uService = new UserServiceImpl();
		if(uService.registerUser(user, userDetails)) {
			request.getRequestDispatcher("dashboard.jsp").forward(request, response);
		} else {
			request.setAttribute("status", "failed");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
	}

}
