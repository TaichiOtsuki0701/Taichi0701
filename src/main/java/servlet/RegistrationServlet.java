package servlet;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.CategoryDAO;
import model.dao.ProductDAO;
import model.entity.ProductBean;

public class RegistrationServlet extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException ,IOException{
		String name = request.getParameter("name");
		int price=Integer.parseInt(request.getParameter("price"));
		int stock = Integer.parseInt(request.getParameter("stock"));
        int categoryId = Integer.parseInt(request.getParameter("categoryid"));
        ProductBean product = new ProductBean();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategoryId(categoryId);

        ProductDAO productDAO = new ProductDAO();
        try {
            // エラーハンドリング
            if (price < 0 || stock < 0) {
                request.setAttribute("errorMessage", "価格と在庫数は正の値でなければなりません。");
                CategoryDAO categoryDAO = new CategoryDAO();
                request.setAttribute("categories", categoryDAO.getAllCategories());
                RequestDispatcher dispatcher = request.getRequestDispatcher("product_registration.jsp");
                dispatcher.forward(request, response);
                return;
            }
            productDAO.registerProduct(product);
            response.sendRedirect("success.jsp"); // 成功ページにリダイレクト
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

