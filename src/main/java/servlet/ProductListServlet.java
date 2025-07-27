package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ProductDAO;
import model.entity.ProductBean;

public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductDAO productDAO = new ProductDAO();
		try {
			List<ProductBean> products = productDAO.getAllProducts();

			System.out.println("--- ProductServlet: 商品リストのデバッグ ---");
			System.out.println("取得した商品数: " + products.size());
			if (products.isEmpty()) {
				System.out.println("商品リストは空です。");
			} else {
				for (ProductBean p : products) {
					String categoryName = (p.getCategory() != null) ? p.getCategory().getCategoryName() : "カテゴリなし";
					System.out.println("  商品ID: " + p.getId() + ", 名前: " + p.getName() + ", 価格: " + p.getPrice()
							+ ", 在庫: " + p.getStock() + ", カテゴリ名: " + categoryName);
				}
			}
			System.out.println("--- デバッグ終了 ---");

			request.setAttribute("products", products);
			request.getRequestDispatcher("product_home.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "商品一覧の取得に失敗しました。");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

}