package bankingapplication.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bankingapplication.dto.Accounts;
import bankingapplication.util.DatabaseUtil;

public class AccountsDao {
	public boolean setAccount(Accounts account) {
		boolean status = false;
		String query = "insert into accounts(userId, accountType, accountPin) values(?, ?, ?);";
		Connection conn = DatabaseUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, account.getUserId());
			pstmt.setString(2, account.getAccountType());
			pstmt.setString(3, account.getAccountPin());
			status = pstmt.executeUpdate() > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	public Accounts getAccountByAccountId(int accountId) {
		Accounts account = null;
		String query = "select * from accounts where accountId = ?;";
		Connection conn = DatabaseUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, accountId);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			account = new Accounts();
			account.setUserId(rs.getInt("userId"));
			account.setAccountId(rs.getInt("accountId"));
			account.setBalance(rs.getDouble("balance"));
			account.setAccountPin(rs.getString("accountPin"));
			account.setAccountType(rs.getString("accountType"));
			account.setCreatedAt(rs.getTimestamp("createdAt"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	public List<Accounts> getAccountsByUserId(int userId) {
		List<Accounts> accounts = new ArrayList<>();
		String query = "select * from accounts where userId = ?;";
		Connection conn = DatabaseUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Accounts account = new Accounts();
				account.setUserId(rs.getInt("userId"));
				account.setAccountId(rs.getInt("accountId"));
				account.setBalance(rs.getDouble("balance"));
				account.setAccountPin(rs.getString("accountPin"));
				account.setAccountType(rs.getString("accountType"));
				account.setCreatedAt(rs.getTimestamp("createdAt"));
				accounts.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}

	public List<Accounts> getAllAccounts() {
		List<Accounts> accounts = new ArrayList<>();
		String query = "select * from accounts;";
		Connection conn = DatabaseUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Accounts account = new Accounts();
				account.setUserId(rs.getInt("userId"));
				account.setAccountId(rs.getInt("accountId"));
				account.setBalance(rs.getDouble("balance"));
				account.setAccountPin(rs.getString("accountPin"));
				account.setAccountType(rs.getString("accountType"));
				account.setCreatedAt(rs.getTimestamp("createdAt"));
				accounts.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}
}
