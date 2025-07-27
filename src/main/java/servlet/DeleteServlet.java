package servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ProductDAO;
import model.entity.ProductBean;

public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		int id = Integer.parseInt(request.getParameter("id"));
		String productName = "不明"; 

		ProductDAO productDAO = new ProductDAO();

		try {
			ProductBean productToDelete = productDAO.getProductById(id);
			if (productToDelete != null) {
				productName = productToDelete.getName();
			}
			boolean deleted = productDAO.deleteProduct(productToDelete);

			if (deleted) {
				String encodedProductName = URLEncoder.encode(productName, "UTF-8");
				response.sendRedirect("delete_success.jsp?name=" + encodedProductName);
			} else {
				request.setAttribute("errorMessage", "指定された商品が見つからないか、削除できませんでした。");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "商品の削除に失敗しました。データベースエラー: " + e.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "削除する商品のIDが不正です。");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
