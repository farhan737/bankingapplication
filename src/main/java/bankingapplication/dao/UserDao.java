package bankingapplication.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bankingapplication.dto.Users;
import bankingapplication.util.DatabaseUtil;

public class UserDao {
	public boolean setUser(Users user) {
		Connection conn = DatabaseUtil.getConnection();
		boolean status = false;
		String query = "insert into users(email, password) values(?, ?);";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getPassword());
			status = (0 < pstmt.executeUpdate()) ? true : false;
			if (status)
				System.out.printf("successfully inserted/n	email: %s/n	password: %s", user.getEmail(),
						user.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	public Users getUserById(int userId) {
		Users user = null;
		String query = "select * from users where userId = ?;";
		Connection conn = DatabaseUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			user = new Users();
			if (rs.next()) {
				user.setUserId(rs.getInt("userId"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public Users getUserByEmail(String email) {
		Users user = null;
		String query = "select * from users where email = ?;";
		Connection conn = DatabaseUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new Users();
				user.setUserId(rs.getInt("userId"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public List<Users> getAllUsers() {
		List<Users> users = new ArrayList<>();
		String query = "select * from users";
		Connection conn = DatabaseUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Users user = new Users();
				user.setUserId(rs.getInt("userId"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
}
