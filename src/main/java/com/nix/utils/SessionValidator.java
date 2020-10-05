package com.nix.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

public class SessionValidator {

	public static final void validateAdminSession(HttpSession session) throws ServletException, IOException {

		Long adminId = 1L;
		Object o = session.getAttribute("roleId");
		if (session == null || o == null || !(o.equals(adminId))) {
			throw new IllegalArgumentException("OOps. You have no access to this page");
		}

	}
}