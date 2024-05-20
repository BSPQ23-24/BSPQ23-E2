
package com.deusto.app.server.services;

import java.util.ArrayList;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.logging.log4j.LogManager;
import java.util.List;
import java.util.stream.Collectors;

import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Station;
import com.deusto.app.server.pojo.BicycleAssembler;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationAssembler;
import com.deusto.app.server.pojo.StationData;

/**
 * BikeService class provides services related to bike operations such as displaying stations,
 * getting available bikes, and retrieving bike details. It uses JDO for persistence.
 */
public class BikeService {
	private static BikeService instance;
	private PersistenceManager pm = null;
	private Transaction tx = null;

	/**
     * Private constructor to initialize PersistenceManager and Transaction.
     */
	private BikeService() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
	}

	/**
     * Returns the singleton instance of BikeService.
     *
     * @return BikeService instance
     */
	public static BikeService getInstance() {
		if (instance == null) {
			instance = new BikeService();
		}
		return instance;
	}

	/*
	 * public int createBike(int stationId, BicycleData bikeData) { try {
	 * tx.begin();
	 * 
	 * //creation of new station Station station = new Station();
	 * station.setLocation("Central Park"); pm.makePersistent(station);
	 * 
	 * // creation of new bicycle Bicycle bike = new Bicycle();
	 * bike.setAcquisitionDate(new String()); bike.setType("Mountain Bike");
	 * 
	 * bike.setStation(station);
	 * 
	 * pm.makePersistent(bike);
	 * 
	 * tx.commit(); return bike.getId();
	 * 
	 * } catch (Exception e) { if (tx.isActive()) { tx.rollback(); }
	 * e.printStackTrace();
	 * 
	 * return -1; } }
	 */

	/**
     * Retrieves and displays all bike stations with their respective bike IDs.
     *
     * @return List of StationData containing the station details and bike IDs
     */
	public List<StationData> displayStations() {

		LogManager.getLogger(BikeService.class).info("Display Stations Start");

		try {
			tx.begin();

			Query<Station> stationQuery = pm.newQuery(Station.class);
			List<Station> stations = (List<Station>) stationQuery.execute();

			tx.commit();

			LogManager.getLogger(BikeService.class).info("Display Stations Success");
			System.out.println(StationAssembler.getInstance().stationsToPOJO(stations));

			return StationAssembler.getInstance().stationsToPOJO(stations);
			
		} catch (Exception e) {
			LogManager.getLogger(BikeService.class).error("Display Stations Failed | '{}'", e);
			if (tx.isActive()) {
				tx.rollback();
			}

			return null;
		}
	}

	/*
	 * public BicycleData selectBike(int stationId) {
	 * 
	 * LogManager.getLogger(BikeService.class).
	 * info("Select Bike Start | StationID : '{}'", stationId);
	 * 
	 * try { tx.begin();
	 * 
	 * Station station = pm.getObjectById(Station.class, stationId); Bicycle
	 * selectedBike = null;
	 * 
	 * for (Bicycle bike : station.getBikes()) { if (bike.isAvailable()) {
	 * selectedBike = bike; break; } }
	 * 
	 * if (selectedBike != null) { selectedBike.setAvailable(false);
	 * 
	 * BicycleData selectedBikeInfo = new BicycleData();
	 * selectedBikeInfo.setId(selectedBike.getId());
	 * selectedBikeInfo.setAcquisitionDate(selectedBike.getAcquisitionDate());
	 * selectedBikeInfo.setType(selectedBike.getType());
	 * selectedBikeInfo.setAvailable(selectedBike.isAvailable());
	 * selectedBikeInfo.setStationId(stationId);
	 * 
	 * tx.commit();
	 * 
	 * LogManager.getLogger(BikeService.class).
	 * info("Select Bike Success | BikeID : '{}'", selectedBikeInfo.getId());
	 * 
	 * return selectedBikeInfo; } else { LogManager.getLogger(BikeService.class)
	 * .error("Select Bike Failed | No available bikes in station | StationID: '{}'"
	 * , stationId); tx.rollback(); return null; } } catch (Exception e) {
	 * LogManager.getLogger(BikeService.class).
	 * error("Select Bike Failed | '{}' | StationID: '{}'", e, stationId); if
	 * (tx.isActive()) { tx.rollback(); } return null; } }
	 */
	
	/**
	 * Retrieves available bikes in a specific station.
	 *
	 * @param stationId the ID of the station
	 * @return List of BicycleData containing the available bike details
	 */
	public List<BicycleData> getAvailableBikesInStation(int stationId) {

		LogManager.getLogger(BikeService.class).info("Get Available Bikes Start | StationID : '{}'", stationId);

		try {
			tx.begin();

			Station station = pm.getObjectById(Station.class, stationId);
			List<Bicycle> availableBikes = station.getBikes();
			System.out.println(availableBikes);

			tx.commit();

			LogManager.getLogger(BikeService.class).info("Get Available Bikes Success | StationID : '{}'", stationId);

			return BicycleAssembler.getInstance().bikesToPOJO(availableBikes);
			
		} catch (Exception e) {
			LogManager.getLogger(BikeService.class).error("Get Available Bikes Failed | '{}' | StationID: '{}'", e,
					stationId);
			if (tx.isActive()) {
				tx.rollback();
			}
			return null;
		}
	}

	/**
	 * Retrieves the details of a bike by its ID.
	 *
	 * @param bikeId the ID of the bike
	 * @return BicycleData containing the bike details, or null if retrieval fails
	 */
	public BicycleData getBikeById(int bikeId) {

		LogManager.getLogger(BikeService.class).info("Get Bike By ID Start | BikeID : '{}'", bikeId);

		try {
			tx.begin();

			Bicycle bike = pm.getObjectById(Bicycle.class, bikeId);
			
			tx.commit();

			LogManager.getLogger(BikeService.class).info("Get Bike By ID Success | BikeID : '{}'", bikeId);

			return BicycleAssembler.getInstance().bikeToPOJO(bike);
		} catch (Exception e) {
			LogManager.getLogger(BikeService.class).error("Get Bike By ID Failed | '{}' | BikeID: '{}'", e, bikeId);
			if (tx.isActive()) {
				tx.rollback();
			}
			return null;
		}
	}
}
