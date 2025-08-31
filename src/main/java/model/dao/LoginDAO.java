package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.entity.LoginBean;

public class LoginDAO {
	static final String DATABASE_NAME = "product_management";
	static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;
	static final String USER = "root";
	static final String PASSWORD = "Taichi1775";

	public LoginBean login(String userName, String password) {
		LoginBean user = null;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
				String sql = "SELECT * FROM users WHERE userName = ? AND password = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, userName);
				ps.setString(2, password);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					user = new LoginBean();
					user.setUserName(rs.getString("userName"));
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
}