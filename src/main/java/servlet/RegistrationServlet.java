package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.CategoryDAO;
import model.dao.ProductDAO;
import model.entity.CategoryBean; // CategoryBeanをインポート
import model.entity.ProductBean;

public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryDAO categoryDAO = new CategoryDAO();
		try {
			List<CategoryBean> categories = categoryDAO.getAllCategories();
			request.setAttribute("categories", categories);
			RequestDispatcher dispatcher = request.getRequestDispatcher("product_registration.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "カテゴリリストの取得に失敗しました。");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String name = request.getParameter("name");
		int price = Integer.parseInt(request.getParameter("price"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		ProductBean product = new ProductBean();
		product.setName(name);
		product.setPrice(price);
		product.setStock(stock);
		product.setCategoryId(categoryId);

		ProductDAO productDAO = new ProductDAO();
		try {

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
			request.setAttribute("errorMessage", "商品の登録に失敗しました。データベースエラー: " + e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp"); // error.jspに転送
			dispatcher.forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "価格または在庫数に無効な値が入力されました。");
			CategoryDAO categoryDAO = new CategoryDAO();
			try {
				request.setAttribute("categories", categoryDAO.getAllCategories());
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("product_registration.jsp");
			dispatcher.forward(request, response);
		}
	}
}