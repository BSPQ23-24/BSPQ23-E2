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

/**
 * BikeController class handles client-side operations related to bicycles and stations.
 * It interacts with the remote services through RESTful APIs.
 */
public class BikeController {
	private static BikeController instance;

	/**
     * Private constructor to prevent instantiation.
     */
	private BikeController() {
	}

	/**
     * Returns the singleton instance of BikeController.
     *
     * @return BikeController instance
     */
	public static synchronized BikeController getInstance() {
		if (instance == null) {
			instance = new BikeController();
		}
		return instance;
	}

	/**
     * Retrieves the details of a specific bike.
     *
     * @param bikeId the ID of the bike
     * @param token  the user's authentication token
     * @return BicycleData containing the bike details, or null if retrieval fails
     */
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

	/**
     * Retrieves a list of all bike stations.
     *
     * @param token the user's authentication token
     * @return List of StationData containing the station details, or null if retrieval fails
     */
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

	/**
     * Retrieves a list of available bikes at a specific station.
     *
     * @param stationId the ID of the bike station
     * @param token     the user's authentication token
     * @return List of BicycleData containing the available bikes, or null if retrieval fails
     */
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
	
	/**
     * Retrieves No Available bikes.
     *
     * @param token the user's authentication token
     * @return Response containing a list of no available bikes
     */
	public List<BicycleData> displayNoAvailableBikes(@QueryParam("token") long token) {
		LogManager.getLogger(BikeController.class).info("Get No Available Bikes Start " );
		WebTarget getBikesWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/bike/na")
																				 .queryParam("token", token);
		Invocation.Builder invocationBuilder = getBikesWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			List<BicycleData> listBikes = response.readEntity(new GenericType<List<BicycleData>>() {
			});
			LogManager.getLogger(BikeController.class).info("Get No Available Bikes Success");
			return listBikes;

		} else {
			LogManager.getLogger(BikeController.class).error("Get No Available Bikes Failed | Code: {} | Reason: {}",
					response.getStatus(), response.readEntity(String.class));
			return null;
		}
	}

	/**
     * Selects a bike from a specific station.
     *
     * @param stationId the ID of the bike station
     * @param token     the user's authentication token
     * @return BicycleData containing the selected bike details, or null if selection fails
     */
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

	/**
     * Creates a new bike at a specific station.
     *
     * @param stationId the ID of the bike station
     * @param bikeData  the bike data to be created
     * @param token     the user's authentication token
     * @return true if the bike creation is successful, false otherwise
     */
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
