package com.deusto.app.client.controller;

import org.apache.logging.log4j.LogManager;


import com.deusto.app.client.remote.ServiceLocator;
import com.deusto.app.server.pojo.BicycleData;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class BikeController {
	private static BikeController instance;
	private static long token = -1;
	
    private BikeController() {}

    public static synchronized BikeController getInstance() {
        if (instance == null) {
            instance = new BikeController();
        }
        return instance;
    }
    
    
    //Display Bike Info
    public Response getBikeDetails(@PathParam("bikeId") int bikeId, @QueryParam("token") long token) {
        WebTarget bikeDetailsWebTarget = ServiceLocator.getInstance().getWebTarget()
                                                     .path("bikeapp/bike/{bikeId}")
                                                     .resolveTemplate("bikeId", bikeId)
                                                     .queryParam("token", token);
        Invocation.Builder invocationBuilder = bikeDetailsWebTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            LogManager.getLogger(BikeController.class).info("Bike number {} displayed", bikeId);
            return Response.ok(response.getEntity()).build();
        } else {
            LogManager.getLogger(BikeController.class).error("Display failed. Code: {}, Reason: {}", response.getStatus(), response.readEntity(String.class));
            return Response.ok(response.getEntity()).build();
        }
    }

    //Display Stations & Bikes
    public Response displayStationsAndBikes(@QueryParam("token") long token) {
    	WebTarget displaySBWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/bike/stations")
    																			  .queryParam("token", token);
        Invocation.Builder invocationBuilder = displaySBWebTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
        	LogManager.getLogger(BikeController.class).info("All the Stations and Bikes are displayed!");
        	return Response.ok(response.getEntity()).build();
            
        } else {
        	LogManager.getLogger(BikeController.class).error("Display failed. Code: {}, Reason: {}", response.getStatus(), response.readEntity(String.class));
        	return Response.ok(response.getEntity()).build();
        }
        
    }
    
    //Get all available bikes In Station
    public Response getAvailableBikesInStation( @QueryParam("stationId") int stationId,  @QueryParam("token") long token) {
    	WebTarget getBikesWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/bike/available")
    																			  .queryParam("stationId", stationId)
				  																  .queryParam("token", token);
		Invocation.Builder invocationBuilder = getBikesWebTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.get();
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
		LogManager.getLogger(BikeController.class).info("The bikes of the station {} are displayed!", stationId);
		return Response.ok(response.getEntity()).build();
		
		} else {
		LogManager.getLogger(BikeController.class).error("Display failed. Code: {}, Reason: {}", response.getStatus(), response.readEntity(String.class));
		return Response.ok(response.getEntity()).build();
		}
    }
    
	//Bike Selection
	public Response selectBike( @QueryParam("stationId") int stationId,  @QueryParam("token") long token) {
	    WebTarget selectBikeWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/bike/select")
	    																		  .queryParam("stationId", stationId)
					  															  .queryParam("token", token);
		Invocation.Builder invocationBuilder = selectBikeWebTarget.request(MediaType.APPLICATION_JSON);
			
		Response response = invocationBuilder.get();
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
		LogManager.getLogger(BikeController.class).info("Bike selected!");
		return Response.ok(response.getEntity()).build();
			
		} else {
		LogManager.getLogger(BikeController.class).error("Display failed. Code: {}, Reason: {}", response.getStatus(), response.readEntity(String.class));
		return Response.ok(response.getEntity()).build();
		}

	}
	
	//Bike Creation
	
	public Response createBike( @QueryParam("stationId") int stationId, BicycleData bikeData, @QueryParam("token") long token) {
	    WebTarget createBikeWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/bike/create")
	    																		  .queryParam("stationId", stationId)
					  															  .queryParam("token", token);
	    
		Invocation.Builder invocationBuilder = createBikeWebTarget.request(MediaType.APPLICATION_JSON);
			
		Response response = invocationBuilder.post(Entity.entity(bikeData, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
		LogManager.getLogger(BikeController.class).info("Bike Created!");
		return Response.ok(response.getEntity()).build();
			
		} else {
		LogManager.getLogger(BikeController.class).error("Display failed. Code: {}, Reason: {}", response.getStatus(), response.readEntity(String.class));
		return Response.ok(response.getEntity()).build();
		}

	}
	
	public long getToken() {

		return token;
	}
	
   
    

    
    
}
