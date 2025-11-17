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
		List<Transactions> list = new ArrayList<>();
		String q = "SELECT *, " + "CASE "
				+ "   WHEN accountId = ? AND transactionType = 'transfer_out' THEN 'transfer_out' "
				+ "   WHEN toAccountId = ? AND transactionType = 'transfer_out' THEN 'transfer_in' "
				+ "   ELSE transactionType " + "END AS direction " + "FROM transactions "
				+ "WHERE accountId = ? OR toAccountId = ? " + "ORDER BY transactionId DESC";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(q)) {

			ps.setInt(1, accountId);
			ps.setInt(2, accountId);
			ps.setInt(3, accountId);
			ps.setInt(4, accountId);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Transactions t = new Transactions();
				t.setTransactionId(rs.getInt("transactionId"));
				t.setUserId(rs.getInt("userId"));
				t.setAccountId(rs.getInt("accountId"));
				t.setToAccountId(rs.getInt("toAccountId"));
				t.setAmount(rs.getDouble("amount"));
				t.setTransactionTime(rs.getTimestamp("transactionTime"));

				// This is the important part:
				t.setTransactionType(rs.getString("direction"));

				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
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
