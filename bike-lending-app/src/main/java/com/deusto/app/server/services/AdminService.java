package com.deusto.app.server.services;

import javax.annotation.Nonnull;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import javax.jdo.annotations.Persistent;

import org.apache.logging.log4j.LogManager;

import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Station;
import com.deusto.app.server.data.domain.User;
import com.deusto.app.server.pojo.BicycleData;

public class AdminService {
	private static AdminService instance;
	private PersistenceManager pm = null;
	private Transaction tx = null;

	/**
     * Private constructor to initialize PersistenceManager and Transaction.
     */
	private AdminService() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
	}

	/**
     * Returns the singleton instance of BikeService.
     *
     * @return BikeService instance
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
     * @return Add a bicycle 
     */
	
	 public boolean addBike(BicycleData bikeData) { 
		 LogManager.getLogger(AdminService.class).info("Add | Bike: '{}'", bikeData.getId());

			try {
				tx.begin();

				Bicycle bike = null;
				try {
					bike = pm.getObjectById(Bicycle.class, bikeData.getId());
					// If the user is found, return an unauthorized response
					if (bike != null) {
						LogManager.getLogger(AdminService.class)
								.info("Addition Failed | The bike is already added | Bike: '{}'", bikeData.getId());
						return false;
					}
				} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
					// User not found, proceed to registration
					bike=new Bicycle();
					bike.setId(bikeData.getId());
					bike.setAcquisitionDate(null);
					bike.setType(bikeData.getType());
					bike.setAvailable(true);
					Station station=null;
					try {
						station=pm.getObjectById(Station.class,bikeData.getStationId());
						if (station == null) {
							LogManager.getLogger(AdminService.class)
							.info("This station do not exist | Station: '{}'", bikeData.getStationId());
							return false;
						}
					}catch (javax.jdo.JDOObjectNotFoundException jonfe2) {
						bike.setStation(station);
						
						pm.makePersistent(bike);
						LogManager.getLogger(AdminService.class).info("Addition Success | Bike: '{}'", bikeData.getId());
					}
					
				}
				tx.commit();
				return true;
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
			}
	 }
	 
	 public boolean deleteBike(int bikeId) {
		 LogManager.getLogger(AdminService.class).info("Delete | Bike: '{}'", bikeId);
		 
		 try {
			 tx.begin();
			 Bicycle bike=null;
			 try {
				 bike=pm.getObjectById(Bicycle.class,bikeId);
				 if (bike==null) {
					 LogManager.getLogger(AdminService.class)
						.info("This bike do not exist | Bike: '{}'", bikeId);
					 return false;
				 }
			 } catch (javax.jdo.JDOObjectNotFoundException jonfe) {
			 
			 pm.removeUserObject(bikeId);
			 
			 LogManager.getLogger(AdminService.class).info("Deletion Success | Bike: '{}'", bikeId);
			 
			 }
			 
			 tx.commit();
			 return true;
		 
		 } finally {
			 if(tx.isActive()) {
				 tx.rollback();
			 }
		 }
	 }
	 
	 public boolean disableBike(int bikeId) {
		 LogManager.getLogger(AdminService.class).info("Disable | Bike: '{}'", bikeId);
		 try {
			 tx.begin();
			 Bicycle bike=null;
			 try {
				 bike=pm.getObjectById(Bicycle.class,bikeId);
				 if (bike==null) {
					 LogManager.getLogger(AdminService.class)
						.info("This bike do not exist | Bike: '{}'", bikeId);
					 return false;
				 }
			  } catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				 if(deleteBike(bikeId)) {
					 bike.setAvailable(false);
					 pm.makePersistent(bike);
				 }
			 }
			 
			 tx.commit();
			 return true;
		 
		 } finally {
			 if(tx.isActive()) {
				 tx.rollback();
			 }
		 }
	 }
	 
	 public boolean ableBike(int bikeId) {
		 LogManager.getLogger(AdminService.class).info("Able | Bike: '{}'", bikeId);
		 try {
			 tx.begin();
			 Bicycle bike=null;
			 try {
				 bike=pm.getObjectById(Bicycle.class,bikeId);
				 if (bike==null) {
					 LogManager.getLogger(AdminService.class)
						.info("This bike do not exist | Bike: '{}'", bikeId);
					 return false;
				 }
			  } catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				 if(deleteBike(bikeId)) {
					 bike.setAvailable(true);
					 pm.makePersistent(bike);
				 }
			 }
			 
			 tx.commit();
			 return true;
		 
		 } finally {
			 if(tx.isActive()) {
				 tx.rollback();
			 }
		 }
	 }
	 
	 
}
