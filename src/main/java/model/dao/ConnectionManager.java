package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	//DB接続用定数
	static final String DATABASE_NAME = "product_management";
	static final String URL = "jdbc:mysql://localhost/" + DATABASE_NAME;
	static final String USER = "root";
	static final String PASSWORD = "Taichi1775";

	//データベースに接続
	public static Connection getConnection() throws SQLException {
		try {
			// ドライバのロード
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			System.err.println("MySQLドライバのロードに失敗しました: " + e.getMessage());
			throw new SQLException(e);
		} catch (SQLException e) {
			System.err.println("JDBC接続エラー: " + e.getMessage());
			throw e;
		}
	}

}
