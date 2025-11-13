package bankingapplication.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bankingapplication.dto.UserDetails;
import bankingapplication.util.DatabaseUtil;

public class UserDetailsDao {
	public boolean setUserDetails(UserDetails userdetails) {
		String query = "insert into userDetails(firstName, lastName, contactNumber, permanentAddress, state, district, city, pincode, dateOfBirth) values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		Connection conn = DatabaseUtil.getConnection();
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, userdetails.getFirstName());
			pstmt.setString(2, userdetails.getLastName());
			pstmt.setString(3, userdetails.getContactNumber());
			pstmt.setString(4, userdetails.getPermanentAddress());
			pstmt.setString(5, userdetails.getState());
			pstmt.setString(6, userdetails.getDistrict());
			pstmt.setString(7, userdetails.getCity());
			pstmt.setString(8, userdetails.getPincode());
			pstmt.setDate(9, java.sql.Date.valueOf(userdetails.getDateOfBirth()));
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public UserDetails getUserDetailsById(int userId) {
		UserDetails userDetails = null;
		String query = "select * from userDetails where userId = ?;";
		Connection conn = DatabaseUtil.getConnection();
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				userDetails = new UserDetails();
				userDetails.setUserId(rs.getInt(1));
				userDetails.setFirstName(rs.getString(2));
				userDetails.setLastName(rs.getString(3));
				userDetails.setContactNumber(rs.getString(4));
				userDetails.setPermanentAddress(rs.getString(5));
				userDetails.setPincode(rs.getString(6));
				userDetails.setState(rs.getString(7));
				userDetails.setDistrict(rs.getString(8));
				userDetails.setCity(rs.getString(9));
				userDetails.setDateOfBirth(rs.getDate(10).toLocalDate());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userDetails;
	}
}
