package com.deusto.app.client.controller;

import com.deusto.app.client.remote.ServiceLocator;
import com.deusto.app.server.pojo.UserData;
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
		LogManager.getLogger(UserController.class).info("Register Start");
		WebTarget registerUserWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/user/register");
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			LogManager.getLogger(UserController.class).info("Register Success");
			return true;

		} else {
			LogManager.getLogger(UserController.class).error("Register Failed | Code: {} | Reason: {}",
					response.getStatus(), response.readEntity(String.class));
			return false;
		}
	}

	public boolean loginUser(UserData userData) {
		LogManager.getLogger(UserController.class).info("Login Start");
		WebTarget loginUserWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/user/login");
		Invocation.Builder invocationBuilder = loginUserWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			UserController.token = response.readEntity(Long.class);
			LogManager.getLogger(UserController.class).info("Login Success | Token: {}", token);
			return true;
		} else {
			LogManager.getLogger(UserController.class).error("Login Failed | Code: {} | Reason: {}", response.getStatus(),
					response.readEntity(String.class));
			return false;
		}
	}

	public boolean changePassword(String dni, String oldPassword, String newPassword) {
		LogManager.getLogger(UserController.class).info("Change Password Start");
		WebTarget changePasswordWebTarget = ServiceLocator.getInstance().getWebTarget()
				.path("bikeapp/user/changePassword").queryParam("dni", dni).queryParam("oldPassword", oldPassword)
				  .queryParam("newPassword", newPassword);
		Invocation.Builder invocationBuilder = changePasswordWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.post(Entity.json(""));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			LogManager.getLogger(UserController.class).info("Change Password Success");
			return true;
		} else {
			LogManager.getLogger(UserController.class).error(
					"Change Password Failed | Code: {} | Reason: {}", response.getStatus(),
					response.readEntity(String.class));
			return false;
		}
	}

	public boolean logoutUser(long token) {
		LogManager.getLogger(UserController.class).info("Change Password Start");
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

}
