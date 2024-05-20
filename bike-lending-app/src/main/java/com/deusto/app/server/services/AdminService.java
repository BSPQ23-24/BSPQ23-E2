package com.deusto.app.server.services;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.logging.log4j.LogManager;

import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Station;
import com.deusto.app.server.pojo.BicycleData;

public class AdminService {
	private static AdminService instance;

	private PersistenceManagerFactory pmf;

	/**
	 * Private constructor to initialize PersistenceManagerFactory.
	 */
	private AdminService() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}

	/**
	 * Returns the singleton instance of AdminService.
	 *
	 * @return AdminService instance
	 */
	public static AdminService getInstance() {
		if (instance == null) {
			instance = new AdminService();
		}
		return instance;
	}

	/**
	 * Adds a bike to a station.
	 *
	 * @return true if the bike is added successfully, false otherwise
	 */
	public boolean addBike(BicycleData bikeData) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Bicycle bike = new Bicycle();
			LogManager.getLogger(AdminService.class).info("Add | Bike: '{}'", bike.getId());

			try {
				bike = pm.getObjectById(Bicycle.class, bike.getId());
				// If the bike is found, return a failure response
				if (bike != null) {
					LogManager.getLogger(AdminService.class)
							.info("Addition Failed | The bike is already added | Bike: '{}'", bike.getId());
					return false;
				}
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				// Bike not found, proceed to addition

				bike.setAcquisitionDate(bikeData.getAcquisitionDate());
				bike.setType(bikeData.getType());
				bike.setAvailable(true);

				// Modify station logic moved here
				int idStation = bikeData.getStationId();
				LogManager.getLogger(AdminService.class).info("Modify | Station: '{}'", idStation);
				Station station;
				try {
					station = pm.getObjectById(Station.class, idStation);
					// If the station is not found, return a failure response
					if (station == null) {
						LogManager.getLogger(AdminService.class)
								.info("Addition Failed | The station does not exist | Station: '{}'", idStation);
						return false;
					}
				} catch (javax.jdo.JDOObjectNotFoundException jonfe2) {
					LogManager.getLogger(AdminService.class)
							.info("Addition Failed | The station does not exist | Station: '{}'", idStation);
					return false;
				}

				List<Bicycle> bikes = new ArrayList<>(station.getBikes());
				bikes.add(bike);
				station.setBikes(bikes);
				bike.setStation(station);

				pm.makePersistent(bike);
				pm.makePersistent(station); // Persist the modified station
				System.out.println(bike.getStation().getBikes());
				LogManager.getLogger(AdminService.class).info("Addition Success | Bike: '{}'", bike.getId());
			}

			tx.commit();
			return true;
		} catch (Exception e) {
			LogManager.getLogger(AdminService.class).error("Addition Failed | Exception: {}", e.getMessage());
			return false;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public boolean deleteBike(int bikeId) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		LogManager.getLogger(AdminService.class).info("Delete | Bike: '{}'", bikeId);

		try {
			tx.begin();
			try {
				Bicycle bike = pm.getObjectById(Bicycle.class, bikeId);
				if (bike != null) {
					pm.deletePersistent(bike);
					LogManager.getLogger(AdminService.class).info("Deletion Success | Bike: '{}'", bikeId);
					tx.commit();
					return true;
				}
			} catch (JDOObjectNotFoundException jonfe) {
				LogManager.getLogger(AdminService.class).info("This bike does not exist | Bike: '{}'", bikeId);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw e;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return false;
	}

	public boolean disableBike(int bikeId) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		LogManager.getLogger(AdminService.class).info("Disable | Bike: '{}'", bikeId);
		try {
			tx.begin();
			Bicycle bike = null;
			try {
				bike = pm.getObjectById(Bicycle.class, bikeId);
				if (bike == null) {
					LogManager.getLogger(AdminService.class).info("This bike do not exist | Bike: '{}'", bikeId);
					return false;
				}
				bike.setAvailable(false);
				pm.makePersistent(bike);
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				LogManager.getLogger(AdminService.class).info("Bike not found, cannot disable | Bike: '{}'", bikeId);
				return false;
			}
			tx.commit();
			return true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public boolean ableBike(int bikeId) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		LogManager.getLogger(AdminService.class).info("Able | Bike: '{}'", bikeId);
		try {
			tx.begin();
			Bicycle bike = null;
			try {
				bike = pm.getObjectById(Bicycle.class, bikeId);
				if (bike == null) {
					LogManager.getLogger(AdminService.class).info("This bike do not exist | Bike: '{}'", bikeId);
					return false;
				}
				bike.setAvailable(true);
				pm.makePersistent(bike);
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				LogManager.getLogger(AdminService.class).info("Bike not found, cannot disable | Bike: '{}'", bikeId);
				return false;
			}
			tx.commit();
			return true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
}
