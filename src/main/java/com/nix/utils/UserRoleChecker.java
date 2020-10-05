package com.nix.utils;

import com.nix.jdbcdaotask.JdbcRoleDao;
import com.nix.jdbcdaotask.JdbcUserDao;
import com.nix.jdbcdaotask.User;

public class UserRoleChecker {

	public static Long getUserRoleId(String login, String pass) {
		if (login == null || pass == null) {
			throw new IllegalArgumentException("name or pass cannot be null");
		}
		Long userRoleId = new Long(0);
		JdbcUserDao userDao = new JdbcUserDao();
		User user = null;
		user = userDao.findUserByLoginAndPass(login, pass);
		if (user != null) {
			userRoleId = user.getRoleId();
		}
		return userRoleId;
	}

	public static String getUserRoleName(String login, String pass) {
		if (login == null || pass == null) {
			throw new IllegalArgumentException("name or pass cannot be null");
		}
		String userRoleName = null;
		Long userRoleId = getUserRoleId(login, pass);

		JdbcRoleDao roleDao = new JdbcRoleDao();
		userRoleName = roleDao.findById(userRoleId).getName();
		if (userRoleName == null) {
			throw new IllegalArgumentException("User has invalid role");
		}
		return userRoleName;
	}
}
