package com.deusto.app.bike.controller;

import org.apache.logging.log4j.LogManager;

import com.deusto.app.client.controller.UserController;
import com.deusto.app.client.remote.ServiceLocator;
import com.deusto.app.server.pojo.UserData;

import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class BikeController {
	private static BikeController instance;

    private BikeController() {}

    public static synchronized BikeController getInstance() {
        if (instance == null) {
            instance = new BikeController();
        }
        return instance;
    }
    
    //Display Stations & Bikes
    public Response displayStationsAndBikes(@QueryParam("token") long token) {
    	WebTarget registerUserWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/user/register");
        Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
        	LogManager.getLogger(UserController.class).info("User correctly registered");
            return true;
            
        } else {
        	LogManager.getLogger(UserController.class).error("Registration failed. Code: {}, Reason: {}", response.getStatus(), response.readEntity(String.class));
            return false;
        }
    }
    
    
}
