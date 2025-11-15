package bankingapplication.service.impl;

import java.util.HashMap;
import java.util.Map;

import bankingapplication.dao.AccountsDao;
import bankingapplication.dao.TransactionsDao;
import bankingapplication.dto.Accounts;
import bankingapplication.dto.Transactions;
import bankingapplication.service.TransactionService;

public class TransactionServiceImpl implements TransactionService {

	final private Map<Integer, String> STATUSMESSAGE = new HashMap<>();

	public Map<Integer, String> getSTATUSMESSAGE() {
		return STATUSMESSAGE;
	}

	public TransactionServiceImpl() {
		super();
		STATUSMESSAGE.put(-1, "transaction object missing");
		STATUSMESSAGE.put(-2, "invalid user id");
		STATUSMESSAGE.put(-3, "invalid account id");
		STATUSMESSAGE.put(-4, "amount must be greater than zero");
		STATUSMESSAGE.put(-5, "transaction type is missing");
		STATUSMESSAGE.put(-6, "unknown transaction type");
		STATUSMESSAGE.put(-7, "destination account is missing");
		STATUSMESSAGE.put(-8, "cannot transfer to the same account");
		STATUSMESSAGE.put(-9, "withdraw amount should be positive");
		STATUSMESSAGE.put(-10, "initial deposit cannot be zero or negative");
	}

	@Override
	public int validateTransaction(Transactions transaction) {

		if (transaction == null)
			return -1; // transaction object missing

		// Validate userId
		if (transaction.getUserId() <= 0)
			return -2; // invalid user id

		// Validate accountId
		if (transaction.getAccountId() <= 0)
			return -3; // invalid account id

		// Validate amount
		if (transaction.getAmount() <= 0)
			return -4; // amount must be greater than zero

		// Validate transaction type
		String type = transaction.getTransactionType();
		if (type == null || type.trim().isEmpty())
			return -5; // transaction type missing

		// Normalize type
		type = type.toLowerCase();

		// Check valid types
		if (!type.equals("deposit") && !type.equals("withdraw") && !type.equals("transfer_out")
				&& !type.equals("initial_deposit"))
			return -6; // unknown transaction type

		// Transfer-specific validation
		if (type.equals("transfer")) {
			if (transaction.getToAccountId() <= 0)
				return -7; // destination account missing
			if (transaction.getToAccountId() == transaction.getAccountId())
				return -8; // cannot transfer to the same account
		}

		// Withdraw-specific validation
		if (type.equals("withdraw")) {
			if (transaction.getAmount() <= 0)
				return -9; // withdraw amount must be positive
		}

		// Initial deposit validation (only ignore account balance rule)
		if (type.equals("initial_deposit")) {
			if (transaction.getAmount() <= 0)
				return -10; // initial deposit cannot be zero or negative
		}

		return 1; // valid
	}

	@Override
	public boolean deposit(Accounts account, Transactions transaction) {
		TransactionsDao tDao = new TransactionsDao();
		AccountsDao aDao = new AccountsDao();

		double newBalance = account.getBalance() + transaction.getAmount();

		boolean transactionSaved = tDao.setTransaction(transaction);
		boolean balanceUpdated = aDao.updateBalance(account.getAccountId(), newBalance);

		return transactionSaved && balanceUpdated;
	}

	@Override
	public boolean widthdraw(Accounts account, Transactions transaction) {
		if (account.getBalance() < transaction.getAmount()) {
			return false;
		}
		TransactionsDao tDao = new TransactionsDao();
		AccountsDao aDao = new AccountsDao();
		double newBalance = account.getBalance() - transaction.getAmount();
		boolean transactionSaved = tDao.setTransaction(transaction);
		boolean balanceUpdated = aDao.updateBalance(account.getAccountId(), newBalance);
		return transactionSaved && balanceUpdated;
	}

	@Override
	public boolean transfer(Accounts fromAccount, Accounts toAccount, Transactions transaction) {
		if (fromAccount.getBalance() < transaction.getAmount()) {
			return false;
		}
		TransactionsDao tDao = new TransactionsDao();
		AccountsDao aDao = new AccountsDao();
		double newFromAccountBalance = fromAccount.getBalance() - transaction.getAmount();
		double newToAccountBalance = toAccount.getBalance() + transaction.getAmount();
		boolean transactionSaved = tDao.setTransaction(transaction);
		boolean fromAccountBalanceUpdated = aDao.updateBalance(fromAccount.getAccountId(), newFromAccountBalance);
		boolean toAccountBalanceUpdated = aDao.updateBalance(toAccount.getAccountId(),newToAccountBalance);
		return transactionSaved && fromAccountBalanceUpdated && toAccountBalanceUpdated;
	}

}
