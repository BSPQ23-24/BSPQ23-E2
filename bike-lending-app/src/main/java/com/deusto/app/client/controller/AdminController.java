package com.deusto.app.client.controller;

import org.apache.logging.log4j.LogManager;

import com.deusto.app.client.remote.ServiceLocator;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.UserData;

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
}
