package com.deusto.app.client.test;

import com.deusto.app.client.controller.UserController;
import com.deusto.app.client.remote.ServiceLocator;
import com.deusto.app.server.pojo.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class UserFunctionalityTest {

	private static UserController userController;

	@BeforeAll
	public static void setUp() {
		ServiceLocator.getInstance().setService("127.0.0.1", "8080");
		userController = UserController.getInstance();
	}

	@Test
	public void testUserRegistration() {
		// Prepare user data
		UserData userData = new UserData();
		userData.setDni("11111111A");
		userData.setPassword("root");
		userData.setName("UsuarioTest");
		userData.setSurname("ApellidoTest");
		userData.setDateOfBirth("01-01-2000");
		userData.setPhone("123456789");
		userData.setMail("test@mail.es");

		// Attempt to register the user
		boolean registrationResult = userController.registerUser(userData);
		Assertions.assertTrue(registrationResult, "User registration should return true on success.");
	}

	@Test
	public void testUserLoginLogout() {
		// Prepare user data
		UserData userData = new UserData();
		userData.setDni("12345678A");
		userData.setPassword("password123");

		// Attempt to log in the newly registered user
		boolean loginSuccess = userController.loginUser(userData);
		Assertions.assertTrue(loginSuccess, "Login should return true on success.");
		Assertions.assertNotNull(userController.getToken(), "Login should set a non-null token.");

		// Attempt to log out
		boolean logoutSuccess = userController.logoutUser(userController.getToken());
		// When running the test the controller sends the actual real token, but server
		// receives a 0
		Assertions.assertTrue(logoutSuccess, "Logout should return true on success.");
		Assertions.assertEquals(userController.getToken(), -1, "Logout should set the token to -1.");
	}

	@Test
	public void testChangePassword() {
		UserData userData = new UserData();
		userData.setDni("87654321B");

		// Attempt to change password
		String oldPassword = "password456";
		String newPassword = "newPassword456";
		boolean changePasswordResult = userController.changePassword(userData.getDni(), oldPassword, newPassword);
		Assertions.assertTrue(changePasswordResult, "Changing password should return true on success.");
	}

}
