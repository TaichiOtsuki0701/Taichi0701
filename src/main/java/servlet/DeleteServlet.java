package servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher; // インポート漏れがないか確認
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
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		int id = 0;
		String productName = "不明";

		try {

			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "削除する商品のIDが不正です。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
			dispatcher.forward(request, response);
			return;
		}

		ProductDAO productDAO = new ProductDAO();
		ProductBean productToDelete = null;

		try {
			productToDelete = productDAO.getProductById(id);

			if (productToDelete != null) {
				productName = productToDelete.getName();

				boolean deleted = productDAO.deleteProduct(productToDelete);

				if (deleted) {
					String encodedProductName = URLEncoder.encode(productName, "UTF-8");
					response.sendRedirect("delete_success.jsp?name=" + encodedProductName);
				} else {

					request.setAttribute("errorMessage", "指定された商品を削除できませんでした。既に削除されているか、存在しません。");
					RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
					dispatcher.forward(request, response);
				}
			} else {
				request.setAttribute("errorMessage", "指定された商品が見つかりませんでした。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
				dispatcher.forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "商品の削除中にデータベースエラーが発生しました: " + e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}