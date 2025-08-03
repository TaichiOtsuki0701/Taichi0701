package servlet;

import java.io.IOException;
import java.net.URLEncoder; // URLEncoderをインポート
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ProductDAO;
import model.entity.ProductBean;
// CategoryDAO, CategoryBean のインポートは doGet でカテゴリリストを渡す場合のみ必要
// import model.dao.CategoryDAO;
// import model.entity.CategoryBean;


// @WebServletアノテーションでURLマッピング (web.xmlで設定する場合はコメントアウトのまま)
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 商品編集画面への遷移 (GETリクエスト)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "編集する商品のIDが不正です。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        ProductDAO productDAO = new ProductDAO();
        ProductBean product = null;

        try {
            product = productDAO.getProductById(id);

            if (product != null) {
                request.setAttribute("product", product);

                // もしカテゴリリストを動的にJSPで表示するなら、ここでCategoryDAOを使って取得
                // CategoryDAO categoryDAO = new CategoryDAO();
                // List<CategoryBean> categories = categoryDAO.getAllCategories();
                // request.setAttribute("categories", categories);

                // ここで JSP のパスを確認し、必要であれば変更
                RequestDispatcher dispatcher = request.getRequestDispatcher("product_update.jsp"); // または "product_update.jsp"
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "指定された商品が見つかりませんでした。");
                RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "商品の取得に失敗しました。データベースエラー: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        int id = 0;
        String name = request.getParameter("name");
        int price = 0;
        int stock = 0;
        int categoryId = 0;

        try {
            id = Integer.parseInt(request.getParameter("id"));
            price = Integer.parseInt(request.getParameter("price"));
            stock = Integer.parseInt(request.getParameter("stock"));
            categoryId = Integer.parseInt(request.getParameter("categoryId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "入力値が不正です。数値項目には数値を入力してください。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        ProductBean product = new ProductBean();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategoryId(categoryId);
     

        ProductDAO productDAO = new ProductDAO();

        try {
            boolean updated = productDAO.updateProduct(product);

            if (updated) {
                // 更新成功後、update_success.jsp にリダイレクトし、更新した商品名を渡す
                String encodedProductName = URLEncoder.encode(name, "UTF-8"); // 更新した商品名を使用
                response.sendRedirect("update_success.jsp?name=" + encodedProductName);
            } else {
                // 更新失敗（IDが見つからないなど）
                request.setAttribute("errorMessage", "商品の更新に失敗しました。指定された商品が見つからない可能性があります。");
                RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "商品の更新中にデータベースエラーが発生しました: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
