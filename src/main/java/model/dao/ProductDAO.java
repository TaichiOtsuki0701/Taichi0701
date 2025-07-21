package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.CategoryBean;
import model.entity.ProductBean;

public class ProductDAO {
	public boolean registerProduct(ProductBean product) throws SQLException {
		String sql = "INSERT INTO products(name, price, stock, category_id) VALUES(?,?,?,?)";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, product.getName());
			pstmt.setInt(2, product.getPrice());
			pstmt.setInt(3, product.getStock());
			pstmt.setInt(4, product.getCategoryId());

			int count = pstmt.executeUpdate();
			return count > 0;
		}
	}

	public List<ProductBean> getAllProducts() throws SQLException {
		List<ProductBean> products = new ArrayList<>();
		String sql = "SELECT products.id, products.name, products.price, products.stock, "
				+ "categories.id AS categoryId, categories.category_name AS categoryName "
				+ "FROM products JOIN categories ON products.category_id = categories.id";
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				ProductBean product = new ProductBean();
				product.setId(resultSet.getInt("id"));
				product.setName(resultSet.getString("name"));
				product.setPrice(resultSet.getInt("price"));
				product.setStock(resultSet.getInt("stock"));
				product.setCategoryId(resultSet.getInt("categoryId"));

				CategoryBean category = new CategoryBean();
				category.setCategoryId(resultSet.getInt("categoryId"));
				category.setCategoryName(resultSet.getString("categoryName"));
				product.setCategory(category);

				products.add(product);
			}
		}
		return products;
	}
}
