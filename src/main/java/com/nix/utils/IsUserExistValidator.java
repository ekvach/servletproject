package com.nix.utils;

import com.nix.jdbcdaotask.JdbcUserDao;

public class IsUserExistValidator {

	public static boolean validate(String login, String pass) {
		if (login == null || pass == null) {
			throw new IllegalArgumentException("name or pass cannot be null");
		}
		JdbcUserDao userDao = new JdbcUserDao();

		return (userDao.findUserByLoginAndPass(login, pass) != null);
	}
}