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
	public int validateUserInfo(Users user, UserDetails userDetails) {

		if (user == null || userDetails == null)
			return -1; // One or both objects missing

		String email = user.getEmail();
		if (email == null || email.trim().isEmpty())
			return -2; // Missing email
		if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))
			return -3; // Invalid email format

		String password = user.getPassword();
		if (password == null || password.trim().isEmpty())
			return -4; // Missing password
		if (password.length() < 6)
			return -5; // Password too short

		String firstName = userDetails.getFirstName();
		if (firstName == null || firstName.trim().isEmpty())
			return -6; // Missing first name

		String lastName = userDetails.getLastName();
		if (lastName == null || lastName.trim().isEmpty())
			return -7; // Missing last name

		String contactNumber = userDetails.getContactNumber();
		if (contactNumber == null || !contactNumber.matches("\\d{10}"))
			return -8; // Invalid contact number (must be 10 digits)

		String address = userDetails.getPermanentAddress();
		if (address == null || address.trim().isEmpty())
			return -9; // Missing address

		String state = userDetails.getState();
		if (state == null || state.trim().isEmpty())
			return -10; // Missing state

		String district = userDetails.getDistrict();
		if (district == null || district.trim().isEmpty())
			return -11; // Missing district

		String city = userDetails.getCity();
		if (city == null || city.trim().isEmpty())
			return -12; // Missing city

		String pincode = userDetails.getPincode();
		if (pincode == null || !pincode.matches("\\d{6}"))
			return -13; // Invalid pincode (must be 6 digits)

		if (userDetails.getDateOfBirth() == null)
			return -14; // Missing DOB
		if (userDetails.getDateOfBirth().isAfter(java.time.LocalDate.now()))
			return -15; // DOB cannot be in the future

		return 1;
	}

}
