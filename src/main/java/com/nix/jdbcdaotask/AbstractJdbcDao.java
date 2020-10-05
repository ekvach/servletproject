package com.nix.jdbcdaotask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractJdbcDao {

	private static final Logger logger = LoggerFactory.getLogger(AbstractJdbcDao.class);

	private String DB_DRIVER = null;
	private String DB_URI = null;

	public AbstractJdbcDao(String DB_DRIVER, String DB_URI) {
		this.DB_DRIVER = DB_DRIVER;
		this.DB_URI = DB_URI;
	}

	public AbstractJdbcDao() {
	}

	public Connection createConnection() {
		if (DB_DRIVER != null && DB_URI != null) {
			return getCustomConnection();
		} else {
			return ConnectionPool.getInstance().getConnection();
		}
	}

	private static class ConnectionPool {

		private ConnectionPool() {
			// private constructor
		}

		private static ConnectionPool instance = null;

		static ConnectionPool getInstance() {
			if (instance == null)
				instance = new ConnectionPool();
			return instance;
		}

		Connection getConnection() {
			Context ctx;
			Connection c = null;
			try {
				ctx = new InitialContext();
				DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ConPool");
				c = ds.getConnection();
			} catch (NamingException e) {
				throw new IllegalArgumentException(e);
			} catch (SQLException e) {
				throw new IllegalArgumentException(e);
			}
			return c;
		}

	}

	private Connection getCustomConnection() {
		try {
			Class.forName(DB_DRIVER);
			return DriverManager.getConnection(DB_URI);
		} catch (Exception e) {
			logger.error("createConnection method failure", e);
			throw new IllegalArgumentException("createConnection method failure", e);
		}
	}
}
