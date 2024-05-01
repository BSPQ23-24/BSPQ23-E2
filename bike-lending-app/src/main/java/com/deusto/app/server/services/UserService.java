package com.deusto.app.server.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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

		LogManager.getLogger(UserService.class).info("Register Start | User: '{}'", userData.getDni());

		try {
			tx.begin();

			User user = null;
			try {
				user = pm.getObjectById(User.class, userData.getDni());
				// If the user is found, return an unauthorized response
				if (user != null) {
					LogManager.getLogger(UserService.class).info("Registration Failed | User is already registered | User: '{}'", userData.getDni());
					return false;
				}
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				// User not found, proceed to registration
				user = new User(userData.getDni(), userData.getPassword(), userData.getName(), userData.getSurname(),
						userData.getDateOfBirth(), userData.getPhone(), userData.getMail());

				pm.makePersistent(user);
				LogManager.getLogger(UserService.class).info("Registration Success | User: '{}'", userData.getDni());
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
		LogManager.getLogger(UserService.class).info("Login Start | User: '{}'", dni);

		User user = null;
		try {
			tx.begin();
			try {
				user = pm.getObjectById(User.class, dni);
				if (!user.getPassword().equals(password)) {
					user = null;
					LogManager.getLogger(UserService.class).error("Login Failed | Password missmatch | User: '{}'", dni);
				}
			} catch (javax.jdo.JDOObjectNotFoundException e) {
				user = null;
				LogManager.getLogger(UserService.class).error("Login Failed | User not found | User: '{}'", dni);
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
				LogManager.getLogger(UserService.class).info("Login Success | User: '{}'", dni);
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
		LogManager.getLogger(UserService.class).info("Logout Start | Token: '{}'", token);
		if (UserService.getInstance().isLoggedIn(token)) {
			serverState.remove(token); // Remove the user from the server state
			LogManager.getLogger(UserService.class).info("Logout Success | Token '{}'", token);
			return true;
		} else {
			LogManager.getLogger(UserService.class).error("Logout Failed | User isn't logged in | Token '{}' ", token);
			return false;
		}
	}

	public boolean changePassword(String dni, String oldPassword, String newPassword) {
		LogManager.getLogger(UserService.class).info("Change Password Start | User: '{}'", dni);

		try {
			tx.begin();

			User user = pm.getObjectById(User.class, dni);

			if (!user.getPassword().equals(oldPassword)) {
				LogManager.getLogger(UserService.class).error("Change Password Failed | Old password missmatch | User: '{}'", dni);
				return false;
			}

			// Update the password with the new one
			user.setPassword(newPassword);
			LogManager.getLogger(UserService.class).info("Change Password Success | User: '{}'", dni);

			tx.commit();
			return true;
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			LogManager.getLogger(UserService.class).error("Change Password Failed | '{}' | User: '{}'", e, dni);
			return false;
		}
	}

	private void initializeData() {
		LogManager.getLogger(UserService.class).info("Initialize Data");

		try {
			tx.begin();

			// Check if there are any existing data entries
			if (pm.getExtent(User.class).iterator().hasNext()) {
				LogManager.getLogger(UserService.class).error("Initialize Data Failed | Data already present");
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
				
				bike1.setStation(station1);
				bike2.setStation(station1);
				bike3.setStation(station2);
				bike4.setStation(station2);
				
				List<Bicycle> station1array = new ArrayList<>();
				station1array.add(bike1);
				station1array.add(bike2);

				
				List<Bicycle> station2array = new ArrayList<>();
				station2array.add(bike3);
				station2array.add(bike4);


				
				station1.setBikes(station1array);
				station2.setBikes(station2array);

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

				LogManager.getLogger(UserService.class).info("Initialize Data Success");
			}

			tx.commit();
		} catch (Exception e) {
			LogManager.getLogger(UserService.class).error("Initialize Data Failed | Reason: '{}'", e);
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}

}
