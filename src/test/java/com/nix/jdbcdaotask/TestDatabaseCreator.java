package com.nix.jdbcdaotask;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDatabaseCreator {

	private static final Logger logger = LoggerFactory.getLogger(TestDatabaseCreator.class);

	public String DB_DRIVER = "";
	public String DB_URI = "";

	private static Properties properties = loadProperties();

	public TestDatabaseCreator() {
		DB_DRIVER = properties.getProperty("db.driver");
		DB_URI = properties.getProperty("db.uri");
	}

	private static Properties loadProperties() {
		try (InputStream in = AbstractJdbcDao.class.getResourceAsStream("/.h2.testserver.properties")) {
			Properties properties = new Properties();
			properties.load(in);
			return properties;
		} catch (Exception e) {
			logger.error("properties loading failure", e);
			throw new IllegalArgumentException(e);
		}
	}
}