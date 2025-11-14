package bankingapplication.service;

import bankingapplication.dto.Accounts;
import bankingapplication.dto.Transactions;

public interface TransactionService {
	
	/**
	 * for validating a Transaction object for a transaction 
	 * @param transaction
	 * @return will return 1 if the Transaction object is valid or <0 status for an invalid object
	 */
	public int validateTransaction(Transactions transaction);
	
	/**
	 * has its own validation process which ignores the 0.0 balance for an account as its being created and this make 'initial deposit' transaction
	 * @param account
	 * @param transaction
	 * @return 
	 */
	public boolean initialDeposit(Accounts account, Transactions transaction);
	
	public boolean deposit(Accounts account, Transactions transaction);
	
	public boolean widthdraw(Accounts account, Transactions transaction);
	
	public boolean transfer(Accounts fromAccount, Accounts toAccount, Transactions transaction);
}
