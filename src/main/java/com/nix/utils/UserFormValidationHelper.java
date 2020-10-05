package com.nix.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.nix.jdbcdaotask.User;

public class UserFormValidationHelper {

	public static String[] getErrorMessagesAsStringArray(Set<ConstraintViolation<User>> set) {
		String[] array = null;

		if (set.size() > 0) {
			array = new String[2];
			array[0] = set.iterator().next().getInvalidValue().toString();
			array[1] = set.iterator().next().getMessage();
		}
		return array;
	}

	public static Boolean isPassTheSameValidation(String pass, String confirmPass) {

		if (pass.equals(confirmPass)) {
			return true;
		} else {
			return false;
		}
	}

}
