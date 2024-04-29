package com.deusto.app.server.services;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import org.apache.logging.log4j.LogManager;

import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Loan;
import com.deusto.app.server.data.domain.Station;
import com.deusto.app.server.data.domain.User;
import com.deusto.app.server.pojo.UserData;

public class UserService {

	private static UserService instance;

	private PersistenceManagerFactory pmf;
	private PersistenceManager pm;
	private Transaction tx;
	private Map<Long, User> serverState;

	private UserService() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		pm = pmf.getPersistenceManager();
		tx = pm.currentTransaction();
		serverState = new HashMap<>();
		initializeData();
	}

	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}

	public boolean registerUser(UserData userData) {

		LogManager.getLogger(UserService.class).info("Register User: '{}'", userData.getDni());

		try {
			tx.begin();

			User user = null;
			try {
				user = pm.getObjectById(User.class, userData.getDni());
				// If the user is found, return an unauthorized response
				if (user != null) {
					LogManager.getLogger(UserService.class).info("User is already registered: '{}'", userData.getDni());
					return false;
				}
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				// User not found, proceed to registration
				user = new User(userData.getDni(), userData.getPassword(), userData.getName(), userData.getSurname(),
						userData.getDateOfBirth(), userData.getPhone(), userData.getMail());

				pm.makePersistent(user);
				LogManager.getLogger(UserService.class).info("User registered succesfully: '{}'", userData.getDni());
			}
			tx.commit();
			return true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}

	public long loginUser(String dni, String password) {
		LogManager.getLogger(UserService.class).info("Login User: '{}' | Password: '{}'", dni, password);

		User user = null;
		try {
			tx.begin();
			try {
				user = pm.getObjectById(User.class, dni);
				if (!user.getPassword().equals(password)) {
					user = null;
					LogManager.getLogger(UserService.class).info("Login User: '{}' | Password Mismatch", dni);
				}
			} catch (javax.jdo.JDOObjectNotFoundException e) {
				user = null;
				LogManager.getLogger(UserService.class).info("Login User: '{}' | User not found", dni);
			}
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}

		if (user != null) {
			// If user is not logged in
			if (!this.serverState.values().contains(user)) {
				long token = Calendar.getInstance().getTimeInMillis();
				this.serverState.put(token, user);
				LogManager.getLogger(UserService.class).info("Login Succesful | User: '{}'", dni);
				return token;
			} else {
				return -1;
			}

		}
		return -1;
	}

	public boolean isLoggedIn(long token) {
		if (serverState.containsKey(token)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean logoutUser(long token) {
		LogManager.getLogger(UserService.class).info("Logout User: Token '{}'", token);
		if (UserService.getInstance().isLoggedIn(token)) {
			serverState.remove(token); // Remove the user from the server state
			LogManager.getLogger(UserService.class).info("Logout Succesful | Token '{}'", token);
			return true;
		} else {
			LogManager.getLogger(UserService.class).info("Logout | Token '{}' | User isn't logged in", token);
			return false;
		}
	}

	public boolean changePassword(String dni, String oldPassword, String newPassword) {
		LogManager.getLogger(UserService.class).info("Changing password for user: '{}'", dni);

		try {
			tx.begin();

			User user = pm.getObjectById(User.class, dni);

			if (!user.getPassword().equals(oldPassword)) {
				LogManager.getLogger(UserService.class).info("Old password does not match for user: '{}'", dni);
				return false;
			}

			// Update the password with the new one
			user.setPassword(newPassword);
			pm.makePersistent(user);
			LogManager.getLogger(UserService.class).info("Password changed successfully for user: '{}'", dni);

			tx.commit();
			return true;
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			LogManager.getLogger(UserService.class).error("Error changing password for user: '{}'", dni, e);
			return false;
		}
	}

	private void initializeData() {
		LogManager.getLogger(UserService.class).info("Initializing database with test data.");

		try {
			tx.begin();

			// Check if there are any existing data entries
			if (pm.getExtent(User.class).iterator().hasNext()) {
				LogManager.getLogger(UserService.class).info("Test data already present.");
			} else {
				// Create and persist example Users
				User user1 = new User("12345678A", "password123", "John", "Doe", "01-01-1980", "555123456",
						"john@example.com");
				User user2 = new User("87654321B", "password456", "Jane", "Smith", "02-02-1990", "555654321",
						"jane@example.com");
				pm.makePersistent(user1);
				pm.makePersistent(user2);

				// Create and persist example Bicycles
				Bicycle bike1 = new Bicycle();
				bike1.setType("Mountain");
				bike1.setAcquisitionDate("2023-01-01"); // Use consistent format if required
				bike1.setAvailable(true);

				Bicycle bike2 = new Bicycle();
				bike2.setType("Road");
				bike2.setAcquisitionDate("2023-02-01"); // Use consistent format if required
				bike2.setAvailable(true);

				Bicycle bike3 = new Bicycle();
				bike3.setType("Hybrid");
				bike3.setAcquisitionDate("2023-03-01");
				bike3.setAvailable(true);

				Bicycle bike4 = new Bicycle();
				bike4.setType("Electric");
				bike4.setAcquisitionDate("2023-03-15");
				bike4.setAvailable(true);

				pm.makePersistent(bike1);
				pm.makePersistent(bike2);
				pm.makePersistent(bike3);
				pm.makePersistent(bike4);

				// Create and persist example Station
				Station station1 = new Station();
				station1.setLocation("Central Park");
				Station station2 = new Station();
				station2.setLocation("Riverside Park");

				// Assign bicycles to station
				station1.setBikes(java.util.Arrays.asList(bike1, bike2));
				station2.setBikes(java.util.Arrays.asList(bike3, bike4));

				pm.makePersistent(station1);
				pm.makePersistent(station2);

				// Create and persist example Loans
				Loan loan1 = new Loan();
				loan1.setLoanDate("15-04-2023");
				loan1.setStartHour("10:00");
				loan1.setEndHour("12:00");
				loan1.setUser(user1);
				loan1.setBicycle(bike1);
				pm.makePersistent(loan1);

				Loan loan2 = new Loan();
				loan2.setLoanDate("16-04-2023");
				loan2.setStartHour("13:00");
				loan2.setEndHour("15:00");
				loan2.setUser(user2);
				loan2.setBicycle(bike3);
				pm.makePersistent(loan2);

				LogManager.getLogger(UserService.class).info("Test data created successfully.");
			}

			tx.commit();
		} catch (Exception e) {
			LogManager.getLogger(UserService.class).error("Error initializing test data.", e);
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}

}
