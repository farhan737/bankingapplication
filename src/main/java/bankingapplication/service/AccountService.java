package bankingapplication.service;

import bankingapplication.dto.Accounts;
import bankingapplication.dto.Users;

public interface AccountService {
	
	/**
	 * for validating account field if invalid data
	 * @param account
	 * @return returns a status code which corresponds to a particular error
	 */
	public int validateAccount(Accounts account);
	
	/**
	 * for checking if an account exists for the userId
	 * @param user
	 * @return return false if an account is not already associated with a userId
	 */
	public boolean accountExistsByUserId(int userId);
	
	
	/**
	 * for checking if an account exists for the accountId
	 * @param user
	 * @return return false if an account is not already associated with a accountId
	 */
	public boolean accountExistsByAccountId(int accountId);
	
	public boolean createAccount(Accounts account);
	
	/**
	 * for checking id there is enough balance for making a payment for a transaction
	 * @param account
	 * @param neededBalance
	 * @return	will return false if the balance is not enough for the transaction
	 */
	public boolean hasEnoughBalance(Accounts account, double neededBalance);
}
