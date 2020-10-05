package com.nix.servlets.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nix.jdbcdaotask.User;
import com.nix.utils.UserListBuilder;

@WebServlet("/homepageadmin")
public class HomePageAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserListBuilder userListBuilder = new UserListBuilder();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		List<User> userList = userListBuilder.buildUserWithoutPassList();

		session.setAttribute("userList", userList);
		RequestDispatcher rd = session.getServletContext().getRequestDispatcher("/admin/homepageadmin.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		List<User> userList = userListBuilder.buildUserWithoutPassList();

		session.setAttribute("userList", userList);
		RequestDispatcher rd = session.getServletContext().getRequestDispatcher("/admin/homepageadmin.jsp");

		rd.forward(request, response);
	}

}
