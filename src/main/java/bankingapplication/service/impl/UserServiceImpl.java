package bankingapplication.service.impl;

import bankingapplication.dao.UserDao;
import bankingapplication.dao.UserDetailsDao;
import bankingapplication.dto.UserDetails;
import bankingapplication.dto.Users;
import bankingapplication.service.UserService;

public class UserServiceImpl implements UserService {
	@Override
	public boolean userExists(Users user) {
		UserDao uDao = new UserDao();
		Users checkUser = uDao.getUserByEmail(user.getEmail());
		return checkUser != null;
	}

	@Override
	public boolean registerUser(Users user, UserDetails userDetails) {
		if (userExists(user)) {
			return false; // user already exists
		}

		UserDao uDao = new UserDao();
		UserDetailsDao uDDao = new UserDetailsDao();

		// Step 1: Insert into users table
		boolean userInserted = uDao.setUser(user);

		if (userInserted) {
			// Step 2: Retrieve the new user's ID by email
			Users createdUser = uDao.getUserByEmail(user.getEmail());
			if (createdUser != null) {
				// Step 3: Set correct userId for details
				userDetails.setUserId(createdUser.getUserId());

				// Step 4: Insert user details
				boolean detailsInserted = uDDao.setUserDetails(userDetails);
				return detailsInserted;
			}
		}

		return false;
	}

	@Override
	public boolean loginUser(Users user) {
		if (userExists(user)) {
			UserDao uDao = new UserDao();
			Users checkUser = uDao.getUserByEmail(user.getEmail());
			if (checkUser == null)
				return false;
			return checkUser.getPassword().equals(user.getPassword());
		}
		return false;
	}

	@Override
	public int validateUserInfo(Users user) {
	    if (user == null)
	        return -1;

	    String email = user.getEmail();
	    if (email == null || email.trim().isEmpty())
	        return -2;
	    if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))
	        return -3;

	    String password = user.getPassword();
	    if (password == null || password.trim().isEmpty())
	        return -4;
	    if (password.length() < 6)
	        return -5;

	    return 1;
	}


	@Override
	public boolean validateUserInfo(Users user, UserDetails userDetails) {
		// TODO Auto-generated method stub
		return false;
	}

}
