package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter({"/product_home.jsp", "/product_registration.jsp", "/product_update.jsp", "/delete_success.jsp", "/registration_success.jsp", "/update_success.jsp", "/ProductListServlet", "/RegistrationServlet", "/UpdateServlet", "/DeleteServlet", "/LogoutServlet"})
public class LoginFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		HttpSession session = httpRequest.getSession(false);
		boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

		if (isLoggedIn) {
			chain.doFilter(request, response); 
		} else {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/Login.jsp");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}
}
