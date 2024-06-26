
package com.deusto.app.server.services;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Station;
import com.deusto.app.server.pojo.BicycleAssembler;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationAssembler;
import com.deusto.app.server.pojo.StationData;

/**
 * BikeService class provides services related to bike operations such as
 * displaying stations, getting available bikes, and retrieving bike details. It
 * uses JDO for persistence.
 */
public class BikeService {
	private static BikeService instance;
	private PersistenceManagerFactory pmf;

	/**
	 * Private constructor to initialize PersistenceManagerFactory.
	 */
	private BikeService() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}
	
	// Constructor for testing purposes
	public BikeService(PersistenceManagerFactory pmf) {
	    this.pmf = pmf;
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

	/**
	 * Retrieves and displays all bike stations with their respective bike IDs.
	 *
	 * @return List of StationData containing the station details and bike IDs
	 */
	public List<StationData> displayStations() {

		LogManager.getLogger(BikeService.class).info("Display Stations Start");

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();

			Query<Station> stationQuery = pm.newQuery(Station.class);
			List<Station> stations = (List<Station>) stationQuery.execute();

			tx.commit();

			LogManager.getLogger(BikeService.class).info("Display Stations Success");
			

			return StationAssembler.getInstance().stationsToPOJO(stations);

		} catch (Exception e) {
			LogManager.getLogger(BikeService.class).error("Display Stations Failed | '{}'", e);
			if (tx.isActive()) {
				tx.rollback();
			}

			return null;
		} finally {
			pm.close();
		}
	}


	public List<BicycleData> displayNoAvailableBikes(){
		LogManager.getLogger(BikeService.class).info("Display No Available Bikes");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();

			Query<Bicycle> bicycleQuery = pm.newQuery(Bicycle.class);
			List<Bicycle> bikes = (List<Bicycle>) bicycleQuery.execute();

			tx.commit();
			
			List<Bicycle> bikesNA=new ArrayList<Bicycle>();
			for (Bicycle bike : bikes) {
				if(!bike.isAvailable()) {
					bikesNA.add(bike);
				}
			}

			LogManager.getLogger(BikeService.class).info("Display No Available Bikes Success");
			
			
			

			return BicycleAssembler.getInstance().bikesToPOJO(bikesNA);
			
		} catch (Exception e) {
			LogManager.getLogger(BikeService.class).error("Display Stations Failed | '{}'", e);
			if (tx.isActive()) {
				tx.rollback();
			}

			return null;
		}
	}
	

	/**
	 * Retrieves available bikes in a specific station.
	 *
	 * @param stationId the ID of the station
	 * @return List of BicycleData containing the available bike details
	 */
	public List<BicycleData> getAvailableBikesInStation(int stationId) {

		LogManager.getLogger(BikeService.class).info("Get Available Bikes Start | StationID : '{}'", stationId);

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();

			Station station = pm.getObjectById(Station.class, stationId);
			List<Bicycle> bikes = station.getBikes();
			List<Bicycle> availableBikes=new ArrayList<Bicycle>();
			for (Bicycle bike : bikes) {
				if(bike.isAvailable()) availableBikes.add(bike);
			}

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
		} finally {
			pm.close();
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

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

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
		} finally {
			pm.close();
		}
	}
}