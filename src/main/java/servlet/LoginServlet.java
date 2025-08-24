package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.LoginDAO;
import model.entity.LoginBean;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String userName = request.getParameter("userName");
	    String password = request.getParameter("password");

	    LoginDAO dao = new LoginDAO();
	    LoginBean user = dao.login(userName, password);

	    if (user != null) {
	        HttpSession session = request.getSession();
	        session.setAttribute("user", user);
	        response.sendRedirect("login_home.jsp");
	    } else {
	        request.setAttribute("errorMessage", "ユーザー名またはパスワードが無効です。");
	        request.getRequestDispatcher("Login.jsp").forward(request, response);
	    }
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    request.getRequestDispatcher("Login.jsp").forward(request, response);
	}
}