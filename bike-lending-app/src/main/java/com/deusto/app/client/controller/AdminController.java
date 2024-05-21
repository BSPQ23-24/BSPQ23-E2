package com.deusto.app.client.controller;

import org.apache.logging.log4j.LogManager;

import com.deusto.app.client.remote.ServiceLocator;
import com.deusto.app.server.pojo.BicycleData;


import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class AdminController {
	private static AdminController instance;

	/**
     * Private constructor to prevent instantiation.
     */
	private AdminController() {
	}

	/**
     * Returns the singleton instance of AdminController.
     *
     * @return AdminController instance
     */
	public static synchronized AdminController getInstance() {
		if (instance == null) {
			instance = new AdminController();
		}
		return instance;
	}
	
	/**
	 * Adds a new bike to the system.
	 *
	 * @param bikeData the bike data to be added
	 * @param token    the user's authentication token
	 * @return true if the bike addition is successful, false otherwise
	 */

	public boolean addBike(BicycleData bikeData, @QueryParam("token") long token) {
		
		LogManager.getLogger(AdminController.class).info("Register Start");
		WebTarget registerUserWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/admin/add")
				                                                                     .queryParam("token", token);
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.post(Entity.entity(bikeData, MediaType.APPLICATION_JSON));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			LogManager.getLogger(AdminController.class).info("Addition Success");
			return true;

		} else {
			LogManager.getLogger(AdminController.class).error("Addition Failed | Code: {} | Reason: {}",
					response.getStatus(), response.readEntity(String.class));
			return false;
		}
	}
	
	/**
	 * Disables a bike.
	 *
	 * @param bikeId the ID of the bike to be disabled
	 * @param token  the user's authentication token
	 * @return true if the bike is successfully disabled, false otherwise
	 */
	public boolean disableBike(@QueryParam("bikeId") int bikeId, @QueryParam("token") long token) {
			
			LogManager.getLogger(AdminController.class).info("Register Start");
			WebTarget registerUserWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/admin/disable")
																						 .queryParam("bikeId", bikeId)
					                                                                     .queryParam("token", token);
			Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);
	
			Response response = invocationBuilder.post(Entity.json(""));
			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				LogManager.getLogger(AdminController.class).info("Disabled!");
				return true;
	
			} else {
				LogManager.getLogger(AdminController.class).error("Disable Failed | Code: {} | Reason: {}",
						response.getStatus(), response.readEntity(String.class));
				return false;
			}
	}
	
	/**
	 * Re-enables a previously disabled bike.
	 *
	 * @param bikeId the ID of the bike to be re-enabled
	 * @param token  the user's authentication token
	 * @return true if the bike is successfully re-enabled, false otherwise
	 */
	public boolean ableBike(@QueryParam("bikeId") int bikeId, @QueryParam("token") long token) {
		
		LogManager.getLogger(AdminController.class).info("Register Start");
		WebTarget registerUserWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/admin/able")
																					 .queryParam("bikeId", bikeId)
				                                                                     .queryParam("token", token);
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.post(Entity.json(""));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			LogManager.getLogger(AdminController.class).info("It is available again!");
			return true;

		} else {
			LogManager.getLogger(AdminController.class).error("Able Failed | Code: {} | Reason: {}",
					response.getStatus(), response.readEntity(String.class));
			return false;
		}
	}
	
	/**
	 * Checks if the login is from admin or client.
	 *
	 * @param token the user's authentication token
	 * @return Response containing true or false
	 */
	public boolean isAdmin(@QueryParam("token") long token) {
		LogManager.getLogger(AdminController.class).info("Are you admin? | Token: ", token);
		WebTarget isAdminWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/admin/isAdmin")
																				.queryParam("token", token);
		Invocation.Builder invocationBuilder = isAdminWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			boolean bikeSelected = response.readEntity(Boolean.class);
			if(bikeSelected) {
				LogManager.getLogger(AdminController.class).info("You are an admin");
			} else {
				LogManager.getLogger(AdminController.class).info("You are a client");
			}
			return bikeSelected;

		} else {
			LogManager.getLogger(AdminController.class).error("Check Who Are You Failed | Code: {} | Reason: {}",
					response.getStatus(), response.readEntity(String.class));
			return (Boolean) null;
		}

	}
}
