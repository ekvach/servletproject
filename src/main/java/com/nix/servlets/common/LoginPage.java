package com.nix.servlets.common;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nix.jdbcdaotask.JdbcUserDao;
import com.nix.jdbcdaotask.User;
import com.nix.utils.UserRoleChecker;

@WebServlet("/loginpage")
public class LoginPage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		RequestDispatcher rd;

		String login = request.getParameter("uname");
		String pass = request.getParameter("pass");

		JdbcUserDao userDao = new JdbcUserDao();
		User user = userDao.findUserByLoginAndPass(login, pass);

		Long roleId = UserRoleChecker.getUserRoleId(login, pass);

		session.setAttribute("roleId", roleId);
		session.setAttribute("user", user);

		switch (String.valueOf(roleId)) {
		case "1":
			response.sendRedirect("homepageadmin");
			break;
		case "2":
		case "3":
			rd = session.getServletContext().getRequestDispatcher("/user/homepageuser.jsp");
			rd.forward(request, response);
			break;
		default:
			throw new IllegalArgumentException("Invalid User Role");
		}
	}
}
