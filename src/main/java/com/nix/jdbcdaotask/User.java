package com.nix.jdbcdaotask;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class User {

	@Positive
	private Long id;
	@NotNull
	@Size(min = 3)
	@NotBlank
	private String login;
	@NotNull
	@NotBlank
	@Size(min = 4)
	private String password;
	@Email
	@NotBlank
	private String email;
	private String firstName;
	private String lastName;
	@Past
	private Date birthday;
	@Positive
	private Long roleId;
	private String roleName;
	private int age;

	public User() {
	}

	// Constructor with required fields
	public User(Long id, String login, String password, String email, Long roleId) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.email = email;
		this.roleId = roleId;
	}

	// Constructor with all the fields
	public User(Long id, String login, String password, String email, String firstName, String lastName, Date birthday,
			Long roleId) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.roleId = roleId;
	}

	// Constructor with fields from the site user creation form
	public User(String login, String password, String email, String firstName, String lastName, Date birthday,
			Long roleId) {
		this.login = login;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.roleId = roleId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", login='" + login + '\'' + ", password='" + password + '\'' + ", email='"
				+ email + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", birthday="
				+ birthday + ", roleId=" + roleId + '}';
	}
}
