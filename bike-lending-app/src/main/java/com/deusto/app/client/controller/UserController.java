package com.deusto.app.client.controller;

import com.deusto.app.client.remote.ServiceLocator;
import com.deusto.app.server.pojo.UserData;

import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;

public class UserController {
	private static UserController instance;
	private static long token = -1;

	private UserController() {
	}

	public static synchronized UserController getInstance() {
		if (instance == null) {
			instance = new UserController();
		}
		return instance;
	}

	public boolean registerUser(UserData userData) {
		WebTarget registerUserWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/user/register");
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			LogManager.getLogger(UserController.class).info("User correctly registered");
			return true;

		} else {
			LogManager.getLogger(UserController.class).error("Registration failed. Code: {}, Reason: {}",
					response.getStatus(), response.readEntity(String.class));
			return false;
		}
	}

	public boolean loginUser(UserData userData) {
		WebTarget loginUserWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/user/login");
		Invocation.Builder invocationBuilder = loginUserWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			UserController.token = response.readEntity(Long.class);
			LogManager.getLogger(UserController.class).info("User login successful, token received: {}", token);
			return true;
		} else {
			LogManager.getLogger(UserController.class).error("Login failed. Code: {}, Reason: {}", response.getStatus(),
					response.readEntity(String.class));
			return false;
		}
	}

	public boolean changePassword(String dni, String oldPassword, String newPassword) {
		
		WebTarget changePasswordWebTarget = ServiceLocator.getInstance().getWebTarget()
				.path("bikeapp/user/changePassword").queryParam("dni", dni).queryParam("oldPassword", oldPassword)
				  .queryParam("newPassword", newPassword);
		Invocation.Builder invocationBuilder = changePasswordWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.post(Entity.json(""));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			LogManager.getLogger(UserController.class).info("Password changed successfully for user with DNI: {}", dni);
			return true;
		} else {
			LogManager.getLogger(UserController.class).error(
					"Password change failed for user with DNI: {}. Code: {}, Reason: {}", dni, response.getStatus(),
					response.readEntity(String.class));
			return false;
		}
	}

	public boolean logoutUser(long token) {
		WebTarget logoutUserWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/user/logout")
				.queryParam("token", token);
		Invocation.Builder invocationBuilder = logoutUserWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.post(Entity.json(""));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			UserController.token = -1; // Reset the token after successful logout
			LogManager.getLogger(UserController.class).info("Logged out successfully!");
			return true;
		} else {
			LogManager.getLogger(UserController.class).error("Logout failed. Code: {}, Reason: {}",
					response.getStatus(), response.readEntity(String.class));
			return false;
		}
	}

	public long getToken() {

		return token;
	}

	// Add more methods to handle other user actions like loginUser, sayHello, etc.
}
