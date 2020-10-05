package com.nix;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) {

//        String baseInitQuery = new TextFromResourceReader().readTextFromResource("dbForDockerInit.sql");
//		UserDao userDao = new JdbcUserDao();
//        try (Connection con = ((AbstractJdbcDao) userDao).createConnection()) {
//            con.createStatement().execute(baseInitQuery);
//        } catch (Exception e) {
//            throw new IllegalArgumentException(e);
//        }
//
//        Role roleAdmin = new Role(1L, "Admin");
//        Role roleCleaner = new Role(2L, "Cleaner");
//        Role roleDirector = new Role(3L, "Director");
//
//        RoleDao roleDao = new JdbcRoleDao();
//        roleDao.create(roleAdmin);
//        roleDao.create(roleCleaner);
//        roleDao.create(roleDirector);
//
//        User userAdmin = new User(1L, "admin", "passwordA", "emailAdmin", 1L);
//        User userCleaner = new User(2L, "cleaner", "passwordC", "emailCleaner", 2L);
//        User userDirector = new User(3L, "ceo", "passwordCeo", "emailCeo", 3L);
//        User userWithAllFields = new User(11L, "FullUser", "passwordA",
//                "emailAdmin@email.com", "Jack", "Jonson",
//                new Date(new GregorianCalendar(2000, Calendar.SEPTEMBER, 5).getTimeInMillis()), 2L);

//        System.out.println("users created");

//        userDao.create(userAdmin);
//        userDao.create(userCleaner);
//        userDao.create(userDirector);
//        userDao.create(userWithAllFields);

//        System.out.println("users added");
//		Date currentDate = new Date();
//		Date userBirthday = new Date();
//		List<User> userList = userDao.findAll();
//		for (User u : userList) {
//			userBirthday.setTime(u.getBirthday().getTime());
//			int age = currentDate.getYear() - userBirthday.getYear();
//			System.out.println(u.toString());
//		}

//        System.out.println("users printed");
//        userDao.remove(userAdmin);
//        userDao.remove(userCleaner);
//        userDao.remove(userDirector);
//        userDao.remove(userWithAllFields);

		String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

		// adding emails to an array list
		List<String> emails = new ArrayList();
		// valid email addresses
		emails.add("alice@example.com");
		emails.add("alice.bob@example.com");
		emails.add("alice@example.me.org");
		// invalid email addresses
		emails.add("alice.example.com");
		emails.add("alice..bob@example.com");
		emails.add("alice@.example.me.org");

		// initialize the Pattern object
		Pattern pattern = Pattern.compile(regex);

		// searching for occurrences of regex
		for (String value : emails) {
			Matcher matcher = pattern.matcher(value);

			System.out.println("Email " + value + " is " + (matcher.matches() ? "valid" : "invalid"));
		}
	}

}
