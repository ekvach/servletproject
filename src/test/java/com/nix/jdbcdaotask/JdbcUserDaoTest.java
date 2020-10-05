package com.nix.jdbcdaotask;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.GregorianCalendar;
import java.util.List;

public class JdbcUserDaoTest {

	private static IDatabaseTester tester = null;
	private static final Logger logger = LoggerFactory.getLogger(JdbcUserDaoTest.class);
	private static TestDatabaseCreator creator;

	@BeforeClass
	public static void setUpClass() throws Exception {
		creator = new TestDatabaseCreator();
		tester = new JdbcDatabaseTester(creator.DB_DRIVER, creator.DB_URI);
		tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		tester.setTearDownOperation(DatabaseOperation.NONE);
	}

	@Before
	public void setUр() throws Exception {

		IDataSet dataSet = new FlatXmlDataSetBuilder()
				.build(JdbcUserDaoTest.class.getClassLoader().getResource("testDbData.xml"));
		tester.setDataSet(dataSet);
		tester.onSetup();
	}

	@After
	public void tearDown() throws Exception {
		tester.onTearDown();
	}

	@Test
	public void testCreateRequiredFields() throws Exception {
		JdbcUserDao userDao = new JdbcUserDao(creator.DB_DRIVER, creator.DB_URI);

		userDao.create(new User(10L, "admin", "passwordA", "emailAdmin@email.com", 1L));

		IDataSet actualDataSet = tester.getConnection().createDataSet();
		ITable actualTable = actualDataSet.getTable("APP_USERS");

		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
				.build(JdbcUserDaoTest.class.getClassLoader().getResource("testCreateUserExpected.xml"));
		ITable expectedTable = expectedDataSet.getTable("APP_USERS");

		Assertion.assertEquals(expectedTable, actualTable);
	}

	@Test
	public void testCreateAllFields() throws Exception {
		JdbcUserDao userDao = new JdbcUserDao(creator.DB_DRIVER, creator.DB_URI);

		userDao.create(new User(11L, "admin", "passwordA", "emailAdmin@email.com", "Jack", "Jonson",
				new Date(new GregorianCalendar(2000, 8, 05).getTimeInMillis()), 1L));

		IDataSet actualDataSet = tester.getConnection().createDataSet();
		ITable actualTable = actualDataSet.getTable("APP_USERS");

		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
				.build(JdbcUserDaoTest.class.getClassLoader().getResource("testCreateUserAllFieldsExpected.xml"));
		ITable expectedTable = expectedDataSet.getTable("APP_USERS");

		Assertion.assertEquals(expectedTable, actualTable);
	}

	@Test
	public void testUpdate() throws Exception {
		JdbcUserDao userDao = new JdbcUserDao(creator.DB_DRIVER, creator.DB_URI);

		userDao.update(new User(1L, "Clerk", "newPass", "newemail@mail.com", "Rob", "Bobbins",
				new Date(new GregorianCalendar(2002, 8, 10).getTimeInMillis()), 3L));

		IDataSet actualDataSet = tester.getConnection().createDataSet();
		ITable actualTable = actualDataSet.getTable("APP_USERS");

		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
				.build(JdbcUserDaoTest.class.getClassLoader().getResource("testUpdateUsersExpected.xml"));
		ITable expectedTable = expectedDataSet.getTable("APP_USERS");

		Assertion.assertEquals(expectedTable, actualTable);
	}

	@Test
	public void testRemove() throws Exception {
		JdbcUserDao userDao = new JdbcUserDao(creator.DB_DRIVER, creator.DB_URI);

		userDao.remove(new User(1L, "Clerk", "newPass", "newemail@mail.com", "Rob", "Bobbins",
				new Date(new GregorianCalendar(2002, 10, 10).getTimeInMillis()), 3L));

		IDataSet actualDataSet = tester.getConnection().createDataSet();
		ITable actualTable = actualDataSet.getTable("APP_USERS");

		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
				.build(JdbcUserDaoTest.class.getClassLoader().getResource("testRemoveUserExpected.xml"));
		ITable expectedTable = expectedDataSet.getTable("APP_USERS");

		Assertion.assertEquals(expectedTable, actualTable);
	}

	@Test
	public void testFindAll() throws Exception {
		JdbcUserDao userDao = new JdbcUserDao(creator.DB_DRIVER, creator.DB_URI);

		List<User> list = userDao.findAll();

		ResultSet rs = tester.getConnection().getConnection().createStatement().executeQuery("SELECT * FROM APP_USERS");

		int i = 0;
		while (rs.next()) {
			Assert.assertEquals(list.get(i).getEmail(), rs.getString("EMAIL"));
			i++;
		}

		rs.close();
	}

	@Test
	public void testFindByLogin() {
		JdbcUserDao userDao = new JdbcUserDao(creator.DB_DRIVER, creator.DB_URI);

		User user = userDao.findByLogin("ekvach");

		Assert.assertEquals("No records found by Login", new Long(1), user.getId());
		Assert.assertEquals("No records found by Login", "testemail1@test.com", user.getEmail());
	}

	@Test
	public void testFindByEmail() {
		JdbcUserDao userDao = new JdbcUserDao(creator.DB_DRIVER, creator.DB_URI);

		User user = userDao.findByEmail("testemail1@test.com");

		Assert.assertEquals("No records found by Email", new Long(1), user.getId());
		Assert.assertEquals("No records found by Email", "ekvach", user.getLogin());
	}

	@Test(expected = NullPointerException.class)
	public void testCreateNull() {
		JdbcUserDao userDao = new JdbcUserDao(creator.DB_DRIVER, creator.DB_URI);

		userDao.create(null);
	}

	@Test(expected = NullPointerException.class)
	public void testUpdateNull() {
		JdbcUserDao userDao = new JdbcUserDao(creator.DB_DRIVER, creator.DB_URI);

		userDao.update(null);
	}

	@Test(expected = NullPointerException.class)
	public void testRemoveNull() {
		JdbcUserDao userDao = new JdbcUserDao(creator.DB_DRIVER, creator.DB_URI);

		userDao.remove(null);
	}

	@Test(expected = NullPointerException.class)
	public void testFindByLoginNull() {
		JdbcUserDao userDao = new JdbcUserDao(creator.DB_DRIVER, creator.DB_URI);

		userDao.findByLogin(null);
	}

	@Test(expected = NullPointerException.class)
	public void testFindByEmailNull() throws Exception {
		JdbcUserDao userDao = new JdbcUserDao(creator.DB_DRIVER, creator.DB_URI);

		userDao.findByEmail(null);
	}
}
