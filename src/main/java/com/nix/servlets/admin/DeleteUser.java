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

import com.nix.jdbcdaotask.JdbcUserDao;
import com.nix.jdbcdaotask.User;
import com.nix.utils.UserListBuilder;

@WebServlet("/deleteuser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	JdbcUserDao userDao = new JdbcUserDao();
	private UserListBuilder userListBuilder = new UserListBuilder();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = userDao.findById(Long.parseLong(request.getParameter("id")));

		userDao.remove(user);

		List<User> userList = userListBuilder.buildUserWithoutPassList();
		request.setAttribute("userList", userList);

		response.sendRedirect("homepageadmin");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		doPost(req, resp);
	}

}
