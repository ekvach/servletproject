package com.nix.jdbcdaotask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao extends AbstractJdbcDao implements UserDao {

	private static final Logger logger = LoggerFactory.getLogger(JdbcUserDao.class);

	public JdbcUserDao() {
	}

	public JdbcUserDao(String DB_DRIVER, String DB_URI) {
		super(DB_DRIVER, DB_URI);
	}

	@Override
	public void create(User user) {
		if (user == null) {
			logger.error("cannot create a record from null reference",
					new NullPointerException("cannot create a record from null reference"));
			throw new NullPointerException("cannot create a record from null reference");
		}

		String SQL = "INSERT INTO APP_USERS VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = createConnection()) {
			connection.setAutoCommit(false);

			long newUserId;
			if (user.getId() == null) {
				newUserId = 1;
				ResultSet rs = connection.createStatement().executeQuery("select max(id) from app_users");
				if (rs.next()) {
					newUserId = rs.getLong("MAX(ID)") + 1;
				}
			} else {
				newUserId = user.getId();
			}
			java.sql.Date userBirthday = null;
			if (user.getBirthday() != null) {
				userBirthday = new java.sql.Date(user.getBirthday().getTime());
			}
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
				preparedStatement.setLong(1, newUserId);
				preparedStatement.setString(2, user.getLogin());
				preparedStatement.setString(3, user.getPassword());
				preparedStatement.setString(4, user.getEmail());
				preparedStatement.setString(5, user.getFirstName());
				preparedStatement.setString(6, user.getLastName());
				preparedStatement.setDate(7, userBirthday);
				preparedStatement.setLong(8, user.getRoleId());
				preparedStatement.execute();

				connection.commit();
			} catch (Exception e) {
				logger.error("something went wrong with creating", e);
				if (connection != null) {
					connection.rollback();
				}
				throw e;
			}

		} catch (Exception throwables) {
			logger.error("something went wrong with creating", throwables);
			throw new IllegalArgumentException("something went wrong upon creating", throwables);
		}
	}

	@Override
	public void update(User user) {
		if (user == null) {
			logger.error("cannot create a record from null reference",
					new NullPointerException("cannot create a record from null reference"));
			throw new NullPointerException("cannot create a record from null reference");
		}
		String SQL = "UPDATE APP_USERS SET " + "LOGIN = ?, " + "PASSWORD = ?, " + "EMAIL = ?, " + "FIRSTNAME = ?, "
				+ "LASTNAME = ?, " + "BIRTHDAY  = ?, " + "USER_ROLEID  = ? " + " WHERE ID = ?";

		try (Connection connection = createConnection()) {
			connection.setAutoCommit(false);
			java.sql.Date userBirthday = null;
			if (user.getBirthday() != null) {
				userBirthday = new java.sql.Date(user.getBirthday().getTime());
			}
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

				preparedStatement.setString(1, user.getLogin());
				preparedStatement.setString(2, user.getPassword());
				preparedStatement.setString(3, user.getEmail());
				preparedStatement.setString(4, user.getFirstName());
				preparedStatement.setString(5, user.getLastName());
				preparedStatement.setDate(6, userBirthday);
				preparedStatement.setLong(7, user.getRoleId());
				preparedStatement.setLong(8, user.getId());
				preparedStatement.execute();

				connection.commit();
			} catch (Exception e) {
				logger.error("something went wrong with updating", e);
				if (connection != null) {
					connection.rollback();
				}
				throw e;
			}
		} catch (Exception throwables) {
			logger.error("something went wrong with updating", throwables);
			throw new IllegalArgumentException("something went wrong upon updating", throwables);
		}
	}

	@Override
	public void remove(User user) {
		if (user == null) {
			logger.error("cannot refer to null", new NullPointerException("cannot refer to null"));
			throw new NullPointerException("cannot refer to null");
		}
		String SQL = "DELETE FROM APP_USERS WHERE ID = ?";

		try (Connection connection = createConnection()) {
			connection.setAutoCommit(false);

			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

				preparedStatement.setLong(1, user.getId());
				preparedStatement.execute();

				connection.commit();
			} catch (Exception e) {
				logger.error("something went wrong with removing", e);
				if (connection != null) {
					connection.rollback();
				}
				throw e;
			}
		} catch (Exception throwables) {
			logger.error("something went wrong with removing", throwables);
			throw new IllegalArgumentException("something went wrong upon removing", throwables);
		}
	}

	@Override
	public List<User> findAll() {

		List<User> userList = new ArrayList<>();

		String SQL = "SELECT * FROM APP_USERS";

		try (Connection connection = createConnection()) {

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL);

			while (resultSet.next()) {
				userList.add(new User(resultSet.getLong("ID"), resultSet.getString("LOGIN"),
						resultSet.getString("PASSWORD"), resultSet.getString("EMAIL"), resultSet.getString("FIRSTNAME"),
						resultSet.getString("LASTNAME"), resultSet.getDate("BIRTHDAY"),
						resultSet.getLong("USER_ROLEID")));
			}

			connection.commit();

		} catch (Exception throwables) {
			logger.error("something went wrong with global search", throwables);
			throw new IllegalArgumentException("something went wrong with global search", throwables);
		}
		return userList;
	}

	@Override
	public User findByLogin(String login) {
		if (login == null) {
			logger.error("cannot parse null login", new NullPointerException("cannot parse null login"));
			throw new NullPointerException("cannot parse null login");
		}
		User user = null;
		String SQL = "SELECT * FROM APP_USERS WHERE LOGIN = ?";

		try (Connection connection = createConnection()) {

			PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setString(1, login);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = new User(resultSet.getLong("ID"), resultSet.getString("LOGIN"), resultSet.getString("PASSWORD"),
						resultSet.getString("EMAIL"), resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"),
						resultSet.getDate("BIRTHDAY"), resultSet.getLong("USER_ROLEID"));
			}

		} catch (Exception throwables) {
			logger.error("something went wrong with search by login", throwables);
			throw new IllegalArgumentException("something went wrong with search by login", throwables);
		}
		return user;
	}

	@Override
	public User findByEmail(String email) {
		if (email == null) {
			logger.error("cannot parse null email", new NullPointerException("cannot parse null email"));
			throw new NullPointerException("cannot parse null email");
		}
		User user = null;
		String SQL = "SELECT * FROM APP_USERS WHERE EMAIL = ?";

		try (Connection connection = createConnection()) {

			PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setString(1, email);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = new User(resultSet.getLong("ID"), resultSet.getString("LOGIN"), resultSet.getString("PASSWORD"),
						resultSet.getString("EMAIL"), resultSet.getString("FIRSTNAME"), resultSet.getString("lastname"),
						resultSet.getDate("BIRTHDAY"), resultSet.getLong("USER_ROLEID"));
			}

		} catch (Exception throwables) {
			logger.error("something went wrong with search by email", throwables);
			throw new IllegalArgumentException("something went wrong with search by email", throwables);
		}
		return user;
	}

	public User findUserByLoginAndPass(String login, String pass) {
		if (login == null || pass == null) {
			throw new IllegalArgumentException("name or pass cannot be null");
		}
		User user = null;

		try (Connection con = createConnection()) {

			PreparedStatement preparedStatement = con
					.prepareStatement("select * from APP_USERS where login=? and password=?");
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, pass);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = new User(resultSet.getLong("ID"), resultSet.getString("LOGIN"), resultSet.getString("PASSWORD"),
						resultSet.getString("EMAIL"), resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"),
						resultSet.getDate("BIRTHDAY"), resultSet.getLong("USER_ROLEID"));
			}

		} catch (Exception throwables) {
			logger.error("something went wrong with search by login", throwables);
			throw new IllegalArgumentException("something went wrong with search by login", throwables);
		}
		return user;
	}

	public User findById(Long id) {
		if (id == null || id <= 0) {
			logger.error("Id should be a positive number",
					new IllegalArgumentException("Id should be a positive number"));
			throw new IllegalArgumentException("Id should be a positive number");
		}
		User user = null;
		String SQL = "SELECT * FROM APP_USERS WHERE ID = ?";

		try (Connection connection = createConnection()) {

			PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setString(1, String.valueOf(id));

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = new User(resultSet.getLong("ID"), resultSet.getString("LOGIN"), resultSet.getString("PASSWORD"),
						resultSet.getString("EMAIL"), resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"),
						resultSet.getDate("BIRTHDAY"), resultSet.getLong("USER_ROLEID"));
			}

		} catch (Exception throwables) {
			logger.error("something went wrong with search by id", throwables);
			throw new IllegalArgumentException("something went wrong with search by id", throwables);
		}
		return user;
	}
}
