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
	
	public boolean deposit(Accounts account, Transactions transaction);
	
	public boolean widthdraw(Accounts account, Transactions transaction);
	
	public boolean transfer(Accounts fromAccount, Accounts toAccount, Transactions transaction);
}
