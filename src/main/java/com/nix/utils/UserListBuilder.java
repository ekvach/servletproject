package com.nix.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nix.jdbcdaotask.JdbcRoleDao;
import com.nix.jdbcdaotask.JdbcUserDao;
import com.nix.jdbcdaotask.Role;
import com.nix.jdbcdaotask.User;

public class UserListBuilder {

	@SuppressWarnings("deprecation")
	public List<User> buildFullUserList() {

		java.util.Date currentDate = new java.util.Date();
		java.util.Date userBirthday = new java.util.Date();
		List<User> userList = new JdbcUserDao().findAll();
		Map<Long, String> roleMap = new JdbcRoleDao().getAllRolesAsMap();
		for (User u : userList) {
			if (u.getBirthday() != null) {
				userBirthday.setTime(u.getBirthday().getTime());
				u.setAge(currentDate.getYear() - userBirthday.getYear());
			}
			u.setRoleName(roleMap.get(u.getRoleId()));
		}

		return userList;
	}

	@SuppressWarnings("deprecation")
	public User fillRoleNameAndAge(User u) {

		if (u.getBirthday() != null) {
			Date currentDate = new java.util.Date();
			Date userBirthday = new java.util.Date();
			userBirthday.setTime(u.getBirthday().getTime());
			u.setAge(currentDate.getYear() - userBirthday.getYear());
		}
		Role role = new JdbcRoleDao().findById(u.getRoleId());
		u.setRoleName(role.getName());

		return u;
	}

	@SuppressWarnings("deprecation")
	public List<User> buildUserWithoutPassList() {

		java.util.Date currentDate = new java.util.Date();
		java.util.Date userBirthday = new java.util.Date();
		List<User> userList = new JdbcUserDao().findAll();
		Map<Long, String> roleMap = new JdbcRoleDao().getAllRolesAsMap();
		for (User u : userList) {
			if (u.getBirthday() != null) {
				userBirthday.setTime(u.getBirthday().getTime());
				u.setAge(currentDate.getYear() - userBirthday.getYear());
			}
			u.setPassword(null);

			u.setRoleName(roleMap.get(u.getRoleId()));
		}

		return userList;
	}

}
