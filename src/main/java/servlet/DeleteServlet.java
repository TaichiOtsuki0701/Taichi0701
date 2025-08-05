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
// CategoryBean は DeleteServlet では直接使われないが、もし必要ならインポートしておく
// import model.entity.CategoryBean;

public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8"); // レスポンスタイプの設定
		request.setCharacterEncoding("UTF-8");

		int id = 0;
		// 削除する商品の名前を保持する変数。初期値はエラー時のための「不明」。
		String productName = "不明"; 

		try {
		
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "削除する商品のIDが不正です。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
			dispatcher.forward(request, response);
			return; // エラー発生時はここで処理を終了
		}

		ProductDAO productDAO = new ProductDAO();
		ProductBean productToDelete = null;

		try {
			// 2. まず、削除する商品の情報をDBから取得する（商品名のため）
			productToDelete = productDAO.getProductById(id);

			if (productToDelete != null) {
				// 商品が見つかった場合、その商品名を取得
				productName = productToDelete.getName();
				
				// 3. 取得したProductBeanオブジェクトを使って削除処理を実行
				boolean deleted = productDAO.deleteProduct(productToDelete);

				if (deleted) {
					// 削除成功の場合、商品名をエンコードして成功JSPへリダイレクト
					String encodedProductName = URLEncoder.encode(productName, "UTF-8");
					response.sendRedirect("delete_success.jsp?name=" + encodedProductName);
				} else {
					// データベース操作は成功したが、対象レコードが削除されなかった場合
					// (例: 削除処理の直前に他のユーザーによってすでに削除されていた)
					request.setAttribute("errorMessage", "指定された商品を削除できませんでした。既に削除されているか、存在しません。");
					RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
					dispatcher.forward(request, response);
				}
			} else {
				// 指定されたIDの商品がデータベースに見つからなかった場合
				request.setAttribute("errorMessage", "指定された商品が見つかりませんでした。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
				dispatcher.forward(request, response);
			}
		} catch (SQLException e) {
			// データベースアクセス中にエラーが発生した場合
			e.printStackTrace();
			request.setAttribute("errorMessage", "商品の削除中にデータベースエラーが発生しました: " + e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// POSTリクエストが来た場合もGETと同じ処理を行う
		doGet(request, response);
	}
}