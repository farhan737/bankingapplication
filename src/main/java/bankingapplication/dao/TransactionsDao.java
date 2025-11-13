package bankingapplication.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bankingapplication.dto.Transactions;
import bankingapplication.util.DatabaseUtil;

public class TransactionsDao {
	public Transactions getTransactionByTransactionId(int transactionId) {
		Transactions transaction = null;
		String query = "select * from transactions where transactionId = ?;";
		Connection conn = DatabaseUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, transactionId);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			transaction = new Transactions();
			transaction.setTransactionId(rs.getInt("transactionId"));
			transaction.setUserId(rs.getInt("userId"));
			transaction.setAccountId(rs.getInt("accountId"));
			transaction.setAmount(rs.getDouble("amount"));
			transaction.setToAccountId(rs.getInt("toAccountId"));
			transaction.setTransactionType(rs.getString("transactionType"));
			transaction.setTransactionTime(rs.getTimestamp("transactionTime"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transaction;
	}

	public List<Transactions> getTransactionByUserId(int userId) {
		List<Transactions> transactions = new ArrayList<>();
		String query = "select * from transactions where userId = ?";
		Connection conn = DatabaseUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Transactions transaction = new Transactions();
				transaction.setTransactionId(rs.getInt("transactionId"));
				transaction.setUserId(rs.getInt("userId"));
				transaction.setAccountId(rs.getInt("accountId"));
				transaction.setAmount(rs.getDouble("amount"));
				transaction.setToAccountId(rs.getInt("toAccountId"));
				transaction.setTransactionType(rs.getString("transactionType"));
				transaction.setTransactionTime(rs.getTimestamp("transactionTime"));
				transactions.add(transaction);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}

	public List<Transactions> getTransactionByAccountId(int accountId) {
		List<Transactions> transactions = new ArrayList<>();
		String query = "select * from transactions where accountId = ? or toAccountId = ?;";
		Connection conn = DatabaseUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, accountId);
			pstmt.setInt(2, accountId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Transactions transaction = new Transactions();
				transaction.setTransactionId(rs.getInt("transactionId"));
				transaction.setUserId(rs.getInt("userId"));
				transaction.setAccountId(rs.getInt("accountId"));
				transaction.setAmount(rs.getDouble("amount"));
				transaction.setToAccountId(rs.getInt("toAccountId"));
				transaction.setTransactionType(rs.getString("transactionType"));
				transaction.setTransactionTime(rs.getTimestamp("transactionTime"));
				transactions.add(transaction);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}

	public boolean setTransaction(Transactions transaction) {
		boolean status = false;
		String query = "insert into transactions(userId, accountId, amount, toAccountId, transactionType) values(?, ?, ?, ?, ?);";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, transaction.getUserId());
			pstmt.setInt(2, transaction.getAccountId());
			pstmt.setDouble(3, transaction.getAmount());
			if (transaction.getToAccountId() > 0) {
				pstmt.setInt(4, transaction.getToAccountId());
			} else {
				pstmt.setNull(4, java.sql.Types.INTEGER);
			}
			pstmt.setString(5, transaction.getTransactionType());
			status = pstmt.executeUpdate() > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
}
