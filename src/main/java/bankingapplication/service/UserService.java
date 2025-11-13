package bankingapplication.service;

import bankingapplication.dto.UserDetails;
import bankingapplication.dto.Users;

public interface UserService {

	public int validateUserInfo(Users user);
	
	public int validateUserInfo(Users user, UserDetails userDetails);
	
	public boolean userExists(Users user);

	public boolean registerUser(Users user, UserDetails userDetails);
	
	public boolean loginUser(Users user);

}
