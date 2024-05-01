package com.deusto.app.client.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;

import com.deusto.app.client.remote.ServiceLocator;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationData;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class BikeController {
	private static BikeController instance;

	private BikeController() {
	}

	public static synchronized BikeController getInstance() {
		if (instance == null) {
			instance = new BikeController();
		}
		return instance;
	}

	// Display Bike Info
	public BicycleData getBikeDetails(@PathParam("bikeId") int bikeId, @QueryParam("token") long token) {
		LogManager.getLogger(BikeController.class).info("Get Bike Details Start | BikeID: '{}'", bikeId);

		WebTarget bikeDetailsWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/bike/{bikeId}")
				.resolveTemplate("bikeId", bikeId).queryParam("token", token);
		Invocation.Builder invocationBuilder = bikeDetailsWebTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			BicycleData bikeSelected = response.readEntity(BicycleData.class);
			LogManager.getLogger(BikeController.class).info("Get Bike Details Success | BikeID: '{}'", bikeId);
			return bikeSelected;
		} else {
			LogManager.getLogger(BikeController.class).error("Get Bike Details Failed | Code: {} | Reason: {}",
					response.getStatus(), response.readEntity(String.class));
			return null;
		}
	}

	// Display Stations
	public List<StationData> displayStations(@QueryParam("token") long token) {
		LogManager.getLogger(BikeController.class).info("Display Stations Start");
		WebTarget displaySBWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/bike/stations")
				.queryParam("token", token);
		Invocation.Builder invocationBuilder = displaySBWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			List<StationData> listStations = response.readEntity(new GenericType<List<StationData>>() {
			});
			LogManager.getLogger(BikeController.class).info("Display Stations Success");
			return listStations;

		} else {
			LogManager.getLogger(BikeController.class).error("Display Stations Failed | Code: {} | Reason: {}",
					response.getStatus(), response.readEntity(String.class));
			return null;
		}

	}

	// Get all available bikes In Station
	public List<BicycleData> getAvailableBikesInStation(@QueryParam("stationId") int stationId,
			@QueryParam("token") long token) {
		LogManager.getLogger(BikeController.class).info("Get Available Bikes Start | StationID: '{}'", stationId);
		WebTarget getBikesWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/bike/available")
				.queryParam("stationId", stationId).queryParam("token", token);
		Invocation.Builder invocationBuilder = getBikesWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			List<BicycleData> listBikes = response.readEntity(new GenericType<List<BicycleData>>() {
			});
			LogManager.getLogger(BikeController.class).info("Get Available Bikes Success | StationID: '{}'", stationId);
			return listBikes;

		} else {
			LogManager.getLogger(BikeController.class).error("Get Available Bikes Failed | Code: {} | Reason: {}",
					response.getStatus(), response.readEntity(String.class));
			return null;
		}
	}

	// Bike Selection
	public BicycleData selectBike(@QueryParam("stationId") int stationId, @QueryParam("token") long token) {
		LogManager.getLogger(BikeController.class).info("Select Bike Start | StationID: '{}'", stationId);
		WebTarget selectBikeWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/bike/select")
				.queryParam("stationId", stationId).queryParam("token", token);
		Invocation.Builder invocationBuilder = selectBikeWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			BicycleData bikeSelected = response.readEntity(BicycleData.class);
			LogManager.getLogger(BikeController.class).info("Select Bike Success | StationID: '{}'", stationId);
			return bikeSelected;

		} else {
			LogManager.getLogger(BikeController.class).error("Select Bike Failed | Code: {} | Reason: {}",
					response.getStatus(), response.readEntity(String.class));
			return null;
		}

	}

	// Bike Creation

	public boolean createBike(@QueryParam("stationId") int stationId, BicycleData bikeData,
			@QueryParam("token") long token) {
		LogManager.getLogger(BikeController.class).info("Create Bike Start | StationID: '{}'", stationId);
		WebTarget createBikeWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/bike/create")
				.queryParam("stationId", stationId).queryParam("token", token);

		Invocation.Builder invocationBuilder = createBikeWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.post(Entity.entity(bikeData, MediaType.APPLICATION_JSON));

		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			LogManager.getLogger(BikeController.class).info("Create Bike Success | StationID: '{}'", stationId);
			return true;

		} else {
			LogManager.getLogger(BikeController.class).error("Create Bike Failed | Code: {} | Reason: {}",
					response.getStatus(), response.readEntity(String.class));
			return false;
		}

	}

}
