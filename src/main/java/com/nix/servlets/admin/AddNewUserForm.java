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
import com.nix.jdbcdaotask.Role;

@WebServlet("/addnewuserform")
public class AddNewUserForm extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		List<Role> roleList = new JdbcRoleDao().findAll();

		request.setAttribute("roleList", roleList);

		RequestDispatcher rd = session.getServletContext().getRequestDispatcher("/admin/addnewuserform.jsp");

		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Role> roleList = new JdbcRoleDao().findAll();
		request.setAttribute("roleList", roleList);

		doGet(request, response);
	}

}