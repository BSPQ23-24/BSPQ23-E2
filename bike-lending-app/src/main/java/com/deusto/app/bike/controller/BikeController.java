package com.deusto.app.bike.controller;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;


import com.deusto.app.client.remote.ServiceLocator;

import jakarta.ws.rs.PathParam;
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
    
    
    //Display Bike Info
    public Response getBikeDetails(@PathParam("bikeId") int bikeId, @QueryParam("token") long token) {
    	WebTarget displaySBWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/bike/{bikeId}");
        Invocation.Builder invocationBuilder = displaySBWebTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity( MediaType.APPLICATION_JSON));
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
        	Response answer  = response.readEntity(Response.class);
        	LogManager.getLogger(BikeController.class).info("Bike number {} displayed", bikeId);
            return answer;
            
        } else {
        	LogManager.getLogger(BikeController.class).error("Display failed. Code: {}, Reason: {}", response.getStatus(), response.readEntity(String.class));
            return null;
        }
        
    }
    //Display Stations & Bikes
    public Response displayStationsAndBikes(@QueryParam("token") long token) {
    	WebTarget displaySBWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/bike/stations");
        Invocation.Builder invocationBuilder = displaySBWebTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(token, MediaType.APPLICATION_JSON));
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
        	Response answer  = response.readEntity(Response.class);
        	LogManager.getLogger(BikeController.class).info("All the Stations and Bikes are displayed!");
            return answer;
            
        } else {
        	LogManager.getLogger(BikeController.class).error("Display failed. Code: {}, Reason: {}", response.getStatus(), response.readEntity(String.class));
            return null;
        }
        
    }
    

    
    
}
