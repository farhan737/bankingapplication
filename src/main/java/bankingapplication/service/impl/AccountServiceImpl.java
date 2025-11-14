package bankingapplication.service.impl;

import bankingapplication.dao.AccountsDao;
import bankingapplication.dto.Accounts;
import bankingapplication.dto.Transactions;
import bankingapplication.service.AccountService;
import bankingapplication.service.TransactionService;

public class AccountServiceImpl implements AccountService {

	@Override
	public int validateAccount(Accounts account) {

		if (account == null)
			return -1; // account object missing

		// PIN must exist and be 4 digits
		if (account.getAccountPin() == null || account.getAccountPin().length() != 4)
			return -2;

		// balance cannot be below zero
		if (account.getBalance() < 0)
			return -3;

		// account type must be "savings" or "current"
		String type = account.getAccountType();
		if (type == null || (!type.equals("savings") && !type.equals("current")))
			return -4;
		return 1;
	}

	@Override
	public boolean accountExistsByUserId(int userId) {
		return new AccountsDao().getAccountByUserId(userId) != null;
	}

	@Override
	public boolean accountExistsByAccountId(int accountId) {
		AccountsDao aDao = new AccountsDao();
		Accounts account = aDao.getAccountByAccountId(accountId);
		return account != null;
	}

	@Override
	public boolean hasEnoughBalance(Accounts account, double neededBalance) {
		return account != null && account.getBalance() >= neededBalance;
	}

	@Override
	public boolean createAccount(Accounts account) {
		if (account == null)
			return false;

		int status = validateAccount(account);
		if (status < 0)
			return false;

		if (accountExistsByUserId(account.getUserId()))
			return false;

		TransactionService tService = new TransactionServiceImpl();

		AccountsDao aDao = new AccountsDao();

		boolean accountCreated = aDao.setAccount(account);

		Accounts createdAccount = aDao.getAccountByUserId(account.getUserId());

		Transactions transaction = null;
		if (createdAccount != null) {
			transaction = new Transactions();
			transaction.setUserId(createdAccount.getUserId());
			transaction.setAccountId(createdAccount.getAccountId());
			transaction.setAmount(account.getBalance()); // initialDeposit
			transaction.setTransactionType("initial_deposit");
		} else {
			return false;
		}

		if (tService.validateTransaction(transaction) < 0)
			return false;

		boolean initialTransaction = false;
		if (accountCreated)
			initialTransaction = tService.deposit(createdAccount, transaction);

		return accountCreated && initialTransaction;
	}

}
