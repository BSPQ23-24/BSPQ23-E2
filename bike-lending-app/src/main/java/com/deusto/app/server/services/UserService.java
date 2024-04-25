package com.deusto.app.server.services;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import org.apache.logging.log4j.LogManager;
import com.deusto.app.server.data.domain.User;
import com.deusto.app.server.pojo.UserData;
import jakarta.ws.rs.core.Response;

public class UserService {

	private static UserService instance;

	private PersistenceManagerFactory pmf;
	private PersistenceManager pm;
	private Transaction tx;

	private UserService() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		pm = pmf.getPersistenceManager();
		tx = pm.currentTransaction();
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

	public User loginUser(String dni, String password) {
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
		return user;
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
	        LogManager.getLogger(UserService.class).info("Password changed successfully for user: '{}'", dni);

	        tx.commit();
	        return true;
	    } catch (Exception e) {
	        if (tx.isActive()) {
	            tx.rollback();
	        }
	        LogManager.getLogger(UserService.class).error("Error changing password for user: '{}'", dni, e);
	        return false;
	    } finally {
	        if (pm != null && !pm.isClosed()) {
	            pm.close();
	        }
	    }
	}

}
