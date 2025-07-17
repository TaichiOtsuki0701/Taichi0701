package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.CategoryBean;

public class CategoryDAO {
	public List<CategoryBean> getAllCategories() throws SQLException {
		List<CategoryBean> categories = new ArrayList<>();
		String sql = "SELECT * FROM categories";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				CategoryBean category = new CategoryBean();
				category.setCategoryId(rs.getInt("id"));
				category.setCategoryName(rs.getString("category_name"));
				categories.add(category);
			}
		}
		return categories;
	}
}
