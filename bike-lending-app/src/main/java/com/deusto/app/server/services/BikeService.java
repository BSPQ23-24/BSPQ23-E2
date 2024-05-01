
package com.deusto.app.server.services;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Station;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationData;

public class BikeService {
	private static BikeService instance;
	private PersistenceManager pm = null;
	private Transaction tx = null;

	public BikeService() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
	}

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

	public List<StationData> displayStations() {

		LogManager.getLogger(BikeService.class).info("Display Stations Start");

		try {
			tx.begin();

			Query<Station> stationQuery = pm.newQuery(Station.class);
			List<Station> stations = (List<Station>) stationQuery.execute();

			List<StationData> stationInfos = new ArrayList<>();
			for (Station station : stations) {
				StationData stationInfo = new StationData();
				stationInfo.setId(station.getId());
				stationInfo.setLocation(station.getLocation());
				stationInfos.add(stationInfo);
			}

			tx.commit();

			LogManager.getLogger(BikeService.class).info("Display Stations Success");

			return stationInfos;
		} catch (Exception e) {
			LogManager.getLogger(BikeService.class).error("Display Stations Failed | '{}'", e);
			if (tx.isActive()) {
				tx.rollback();
			}

			return null;
		}
	}

	public BicycleData selectBike(int stationId) {

		LogManager.getLogger(BikeService.class).info("Select Bike Start | StationID : '{}'",
				stationId);

		try {
			tx.begin();

			Station station = pm.getObjectById(Station.class, stationId);
			Bicycle selectedBike = null;

			for (Bicycle bike : station.getBikes()) {
				if (bike.isAvailable()) {
					selectedBike = bike;
					break;
				}
			}

			if (selectedBike != null) {
				selectedBike.setAvailable(false);

				BicycleData selectedBikeInfo = new BicycleData();
				selectedBikeInfo.setId(selectedBike.getId());
				selectedBikeInfo.setAcquisitionDate(selectedBike.getAcquisitionDate());
				selectedBikeInfo.setType(selectedBike.getType());
				selectedBikeInfo.setAvailable(selectedBike.isAvailable());
				selectedBikeInfo.setStationId(stationId);

				tx.commit();

				LogManager.getLogger(BikeService.class).info("Select Bike Success | BikeID : '{}'", selectedBikeInfo.getId());

				return selectedBikeInfo;
			} else {
				LogManager.getLogger(BikeService.class).error("Select Bike Failed | No available bikes in station | StationID: '{}'", stationId);
				tx.rollback();
				return null;
			}
		} catch (Exception e) {
			LogManager.getLogger(BikeService.class).error("Select Bike Failed | '{}' | StationID: '{}'", e, stationId);
			if (tx.isActive()) {
				tx.rollback();
			}
			return null;
		}
	}

	public List<BicycleData> getAvailableBikesInStation(int stationId) {

		LogManager.getLogger(BikeService.class).info("Get Available Bikes Start | StationID : '{}'", stationId);

		try {
			tx.begin();

			Station station = pm.getObjectById(Station.class, stationId);
			List<Bicycle> availableBikes = station.getBikes();

			List<BicycleData> bicycleInfos = new ArrayList<>();
			for (Bicycle bike : availableBikes) {
				BicycleData bicycleInfo = new BicycleData();
				bicycleInfo.setId(bike.getId());
				bicycleInfo.setAcquisitionDate(bike.getAcquisitionDate());
				bicycleInfo.setType(bike.getType());
				bicycleInfo.setAvailable(bike.isAvailable());
				bicycleInfo.setStationId(stationId);
				bicycleInfos.add(bicycleInfo);
			}

			tx.commit();

			LogManager.getLogger(BikeService.class).info("Get Available Bikes Success | StationID : '{}'", stationId);

			return bicycleInfos;
		} catch (Exception e) {
			LogManager.getLogger(BikeService.class).error("Get Available Bikes Failed | '{}' | StationID: '{}'", e, stationId);
			if (tx.isActive()) {
				tx.rollback();
			}
			return null;
		}
	}

	public BicycleData getBikeById(int bikeId) {

		LogManager.getLogger(BikeService.class).info("Get Bike By ID Start | BikeID : '{}'", bikeId);

		try {
			tx.begin();

			Bicycle bike = pm.getObjectById(Bicycle.class, bikeId);
			BicycleData bikeInfo = new BicycleData();
			bikeInfo.setId(bike.getId());
			bikeInfo.setAcquisitionDate(bike.getAcquisitionDate());
			bikeInfo.setType(bike.getType());
			bikeInfo.setAvailable(bike.isAvailable());
			bikeInfo.setStationId(bike.getStation().getId());

			tx.commit();

			LogManager.getLogger(BikeService.class).info("Get Bike By ID Success | BikeID : '{}'", bikeId);

			return bikeInfo;
		} catch (Exception e) {
			LogManager.getLogger(BikeService.class).error("Get Bike By ID Failed | '{}' | BikeID: '{}'", e, bikeId);
			if (tx.isActive()) {
				tx.rollback();
			}
			return null;
		}
	}
}
