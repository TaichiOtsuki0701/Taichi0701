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
		int price = 0;
		int stock = 0;
		int categoryId = 0;

		ProductBean currentProduct = new ProductBean();

		try {
			price = Integer.parseInt(request.getParameter("price"));
			stock = Integer.parseInt(request.getParameter("stock"));
			categoryId = Integer.parseInt(request.getParameter("categoryId"));

			currentProduct.setName(name);
			currentProduct.setPrice(price);
			currentProduct.setStock(stock);
			currentProduct.setCategoryId(categoryId);

			if (price < 1 || stock < 0) {
				String errorMessage = "";
				if (price < 1) {
					errorMessage += "価格は1以上の値を入力してください。";
				}
				if (stock < 0) {
					if (!errorMessage.isEmpty()) {
						errorMessage += " ";
					}
					errorMessage += "在庫数は0以上の値を入力してください。";
				}

				request.setAttribute("errorMessage", errorMessage);

				CategoryDAO categoryDAO = new CategoryDAO();
				request.setAttribute("categories", categoryDAO.getAllCategories());
				request.setAttribute("product", currentProduct);

				RequestDispatcher dispatcher = request.getRequestDispatcher("product_registration.jsp");
				dispatcher.forward(request, response);
				return;
			}
			ProductDAO productDAO = new ProductDAO();
			boolean registered = productDAO.registerProduct(currentProduct);

			if (registered) {
				CategoryDAO categoryDAO = new CategoryDAO();
				CategoryBean category = categoryDAO.getCategoryById(categoryId);
				currentProduct.setCategory(category);

				request.setAttribute("product", currentProduct);
				RequestDispatcher dispatcher = request.getRequestDispatcher("registration_success.jsp");
				dispatcher.forward(request, response);
			} else {

				request.setAttribute("errorMessage", "商品の登録に失敗しました。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
				dispatcher.forward(request, response);
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "入力値が不正です。数値項目には数値を入力してください。");

			currentProduct.setName(name);
			try {
				categoryId = Integer.parseInt(request.getParameter("categoryId"));
				currentProduct.setCategoryId(categoryId);
			} catch (NumberFormatException nfe) {
			}

			CategoryDAO categoryDAO = new CategoryDAO();
			try {
				request.setAttribute("categories", categoryDAO.getAllCategories());
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			request.setAttribute("product", currentProduct);

			RequestDispatcher dispatcher = request.getRequestDispatcher("product_registration.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "商品の登録に失敗しました。データベースエラー: " + e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
			dispatcher.forward(request, response);
		}
	}
}
