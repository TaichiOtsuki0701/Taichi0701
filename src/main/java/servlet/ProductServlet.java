package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ProductDAO;
import model.entity.ProductBean;

public class ProductServlet {
	public class ProductListServlet extends HttpServlet {
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        ProductDAO productDAO = new ProductDAO();
	        try {
	            List<ProductBean> products = productDAO.getAllProducts();
	            request.setAttribute("products", products);
	            request.getRequestDispatcher("product_home.jsp").forward(request, response);
	        } catch (Exception e) {
	            e.printStackTrace();
	            request.setAttribute("errorMessage", "商品一覧の取得に失敗しました。");
	            request.getRequestDispatcher("error.jsp").forward(request, response);
	        }
	    }

	}}
