package servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException; // Connection, PreparedStatement, ResultSet のインポートは不要になる
import java.util.List; // List は必要

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// model.dao.ConnectionManager のインポートは不要になる（DAOがラップするため）
import model.dao.CategoryDAO; // CategoryDAO は必要
import model.dao.ProductDAO;
import model.entity.CategoryBean;
import model.entity.ProductBean;

// @WebServletアノテーションでURLマッピング
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

        ProductDAO productDAO = new ProductDAO(); // DAOインスタンス化
        ProductBean product = null;
        List<CategoryBean> categories = null;

        try {
            // --- ここで DAO の getProductById を呼び出す ---
            product = productDAO.getProductById(id);
            // --- ここまで ---

            // カテゴリリストを取得 (これはそのまま)
            CategoryDAO categoryDAO = new CategoryDAO();
            categories = categoryDAO.getAllCategories();
            request.setAttribute("categories", categories);

            if (product != null) {
                request.setAttribute("product", product);
                RequestDispatcher dispatcher = request.getRequestDispatcher("product_update.jsp");
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

    // 商品情報の更新処理 (POSTリクエスト)
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
            
            // エラー時にカテゴリリストと既存商品情報（ユーザーが入力した値）をJSPに戻す
            // ProductBeanにはCategoryBeanもセットする必要がある（getCategoryNameのため）
            ProductBean currentProduct = new ProductBean();
            CategoryDAO categoryDAO = new CategoryDAO();
            try {
                List<CategoryBean> categories = categoryDAO.getAllCategories();
                // 選択されたカテゴリをProductBeanにセット
                for(CategoryBean cat : categories) {
                    if (cat.getCategoryId() == categoryId) {
                        currentProduct.setCategory(cat);
                        break;
                    }
                }
                request.setAttribute("categories", categories);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            request.setAttribute("product", currentProduct); // 再構築したproductをセット
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("product_update.jsp");
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
                String encodedProductName = URLEncoder.encode(name, "UTF-8");
                response.sendRedirect("update_success.jsp?name=" + encodedProductName);
            } else {
                request.setAttribute("errorMessage", "商品の更新に失敗しました。指定された商品が見つからない可能性があります。");
                
                // 失敗時もカテゴリリストと既存商品情報（ユーザーが入力した値）をJSPに戻す
                // ProductBeanにはCategoryBeanもセットする必要がある
                CategoryDAO categoryDAO = new CategoryDAO();
                try {
                    List<CategoryBean> categories = categoryDAO.getAllCategories();
                    // 選択されたカテゴリをProductBeanにセット
                    for(CategoryBean cat : categories) {
                        if (cat.getCategoryId() == categoryId) {
                            product.setCategory(cat); // 既存のproductオブジェクトにセット
                            break;
                        }
                    }
                    request.setAttribute("categories", categories);
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                request.setAttribute("product", product);

                RequestDispatcher dispatcher = request.getRequestDispatcher("product_update.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "商品の更新中にデータベースエラーが発生しました: " + e.getMessage());
            
            // DBエラー時もカテゴリリストと既存商品情報（ユーザーが入力した値）をJSPに戻す
            // ProductBeanにはCategoryBeanもセットする必要がある
            CategoryDAO categoryDAO = new CategoryDAO();
            try {
                List<CategoryBean> categories = categoryDAO.getAllCategories();
                // 選択されたカテゴリをProductBeanにセット
                for(CategoryBean cat : categories) {
                    if (cat.getCategoryId() == categoryId) {
                        product.setCategory(cat); // 既存のproductオブジェクトにセット
                        break;
                    }
                }
                request.setAttribute("categories", categories);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            request.setAttribute("product", product);

            RequestDispatcher dispatcher = request.getRequestDispatcher("product_update.jsp");
            dispatcher.forward(request, response);
        }
    }
}