package com.nix.servlets.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.nix.jdbcdaotask.JdbcRoleDao;
import com.nix.jdbcdaotask.JdbcUserDao;
import com.nix.jdbcdaotask.User;
import com.nix.utils.UserFormValidationHelper;

@WebServlet("/edituser")
public class EditUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	int validationErrorCounter;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		validationErrorCounter = 0;

		response.setContentType("text/html");
		RequestDispatcher rd;
		HttpSession session = request.getSession(false);
		JdbcRoleDao roleDao = new JdbcRoleDao();
		JdbcUserDao userDao = new JdbcUserDao();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date userBirthday = null;

		try {
			userBirthday = dateFormat.parse(request.getParameter("birthday"));
		} catch (ParseException e) {
			throw new IllegalArgumentException("Date format does not correspond to \"yyyy-mm-dd\"");
		}

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		boolean isViolationOccured = false;

		User user = null;
		if (request.getParameter("pass").isEmpty() && request.getParameter("confpass").isEmpty()) {
			user = userDao.findByLogin(request.getParameter("uname"));
			user.setId(Long.parseLong(request.getParameter("id")));
			user.setEmail(request.getParameter("email"));
			user.setFirstName(request.getParameter("fname"));
			user.setLastName(request.getParameter("lname"));
			user.setBirthday(new Date(userBirthday.getTime()));
			user.setRoleId(roleDao.findByName(request.getParameter("role")).getId());
		} else {
			user = userDao.findByLogin(request.getParameter("uname"));
			user.setId(Long.parseLong(request.getParameter("id")));
			user.setPassword(request.getParameter("pass"));
			user.setEmail(request.getParameter("email"));
			user.setFirstName(request.getParameter("fname"));
			user.setLastName(request.getParameter("lname"));
			user.setBirthday(new Date(userBirthday.getTime()));
			user.setRoleId(roleDao.findByName(request.getParameter("role")).getId());

			String[] passError = UserFormValidationHelper
					.getErrorMessagesAsStringArray(validator.validateProperty(user, "password"));
			request.setAttribute("passError", passError);
			if (passError != null) {
				for (String string : passError) {
					System.out.println(string);
				}
				isViolationOccured = true;
			}
		}

		String[] idError = UserFormValidationHelper
				.getErrorMessagesAsStringArray(validator.validateProperty(user, "id"));
		request.setAttribute("idError", idError);
		if (idError != null) {
			for (String string : idError) {
				System.out.println(string);
			}
			isViolationOccured = true;
		}

		String[] loginError = UserFormValidationHelper
				.getErrorMessagesAsStringArray(validator.validateProperty(user, "login"));
		request.setAttribute("loginError", loginError);
		if (loginError != null) {
			for (String string : loginError) {
				System.out.println(string);
			}
			isViolationOccured = true;
		}

		String[] emailError = UserFormValidationHelper
				.getErrorMessagesAsStringArray(validator.validateProperty(user, "email"));
		request.setAttribute("emailError", emailError);
		if (emailError != null) {
			for (String string : emailError) {
				System.out.println(string);
			}
			isViolationOccured = true;
		}

		String[] firstNameError = UserFormValidationHelper
				.getErrorMessagesAsStringArray(validator.validateProperty(user, "firstName"));
		request.setAttribute("firstNameError", firstNameError);
		if (firstNameError != null) {
			for (String string : firstNameError) {
				System.out.println(string);
			}
			isViolationOccured = true;
		}

		String[] lastNameError = UserFormValidationHelper
				.getErrorMessagesAsStringArray(validator.validateProperty(user, "lastName"));
		request.setAttribute("lastNameError", lastNameError);
		if (lastNameError != null) {
			for (String string : lastNameError) {
				System.out.println(string);
			}
			isViolationOccured = true;
		}

		String[] birthdayError = UserFormValidationHelper
				.getErrorMessagesAsStringArray(validator.validateProperty(user, "birthday"));
		request.setAttribute("birthdayError", birthdayError);
		if (birthdayError != null) {
			for (String string : birthdayError) {
				System.out.println(string);
			}
			isViolationOccured = true;
		}

		String[] RoleIdError = UserFormValidationHelper
				.getErrorMessagesAsStringArray(validator.validateProperty(user, "roleId"));
		request.setAttribute("RoleIdError", RoleIdError);
		if (RoleIdError != null) {
			for (String string : RoleIdError) {
				System.out.println(string);
			}
			isViolationOccured = true;
		}

		boolean isPasswordTheSame = UserFormValidationHelper.isPassTheSameValidation(request.getParameter("pass"),
				request.getParameter("confpass"));
		request.setAttribute("isPasswordTheSame", isPasswordTheSame);

		if (isViolationOccured || !isPasswordTheSame) {
			rd = session.getServletContext().getRequestDispatcher("/edituserform");
			rd.forward(request, response);
		} else {
			userDao.update(user);
			rd = session.getServletContext().getRequestDispatcher("/homepageadmin");
			rd.forward(request, response);
		}

	}

}