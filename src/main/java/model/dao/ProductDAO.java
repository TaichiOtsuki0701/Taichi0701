package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.ProductBean;

public class ProductDAO {
    private Connection connection;
	public boolean registerProduct(ProductBean product) throws SQLException {
        String sql = "INSERT INTO products(name, price, stock, categoryId) VALUES(?,?,?,?)";

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
	    String sql = "SELECT * FROM products";
	    
	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement statement = conn.prepareStatement(sql);
	         ResultSet resultSet = statement.executeQuery()) {
	        while (resultSet.next()) {
	            ProductBean product = new ProductBean();
	            product.setName(resultSet.getString("name"));
	            product.setPrice(resultSet.getInt("price"));
	            product.setStock(resultSet.getInt("stock"));
	            product.setCategoryId(resultSet.getInt("categoryId")); 
	            products.add(product);
	        }
	    }
	    return products;
	}
}
