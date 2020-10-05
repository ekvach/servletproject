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

import com.nix.jdbcdaotask.JdbcRoleDao;
import com.nix.jdbcdaotask.JdbcUserDao;
import com.nix.jdbcdaotask.Role;
import com.nix.jdbcdaotask.User;
import com.nix.utils.UserListBuilder;

@WebServlet("/edituserform")
public class EditUserForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	JdbcUserDao userDao = new JdbcUserDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Role> roleList = new JdbcRoleDao().findAll();
		request.setAttribute("roleList", roleList);
		HttpSession session = request.getSession(false);

		User user = null;
		if (request.getParameter("id") != null) {
			user = userDao.findById(Long.parseLong(request.getParameter("id")));
		} else {
			user = userDao.findByLogin(request.getParameter("uname"));
		}
		user = new UserListBuilder().fillRoleNameAndAge(user);
		request.setAttribute("user", user);

		RequestDispatcher rd = session.getServletContext().getRequestDispatcher("/admin/edituserform.jsp");

		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Role> roleList = new JdbcRoleDao().findAll();
		request.setAttribute("roleList", roleList);

		doGet(request, response);

	}

}
