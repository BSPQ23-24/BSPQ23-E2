package com.deusto.app.server.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import javax.jdo.annotations.Persistent;

import org.apache.logging.log4j.LogManager;

import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Station;
import com.deusto.app.server.data.domain.User;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationAssembler;
import com.deusto.app.server.pojo.StationData;

public class AdminService {
	private static AdminService instance;

	private PersistenceManagerFactory pmf;
	private PersistenceManager pm;
	private Transaction tx;

	/**
     * Private constructor to initialize PersistenceManager and Transaction.
     */
	private AdminService() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		pm = pmf.getPersistenceManager();
		tx = pm.currentTransaction();
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
			try {
				tx.begin();
				Bicycle bike = new Bicycle();
				LogManager.getLogger(AdminService.class).info("Add | Bike: '{}'", bike.getId());
				try {
					bike = pm.getObjectById(Bicycle.class, bike.getId());
					// If the user is found, return an unauthorized response
					if (bike != null) {
						LogManager.getLogger(AdminService.class)
								.info("Addition Failed | The bike is already added | Bike: '{}'", bike.getId());
						return false;
					}
				} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
					// User not found, proceed to registration
					
					
					bike.setAcquisitionDate(bikeData.getAcquisitionDate());
					bike.setType(bikeData.getType());
					bike.setAvailable(true);
					
					Station station= modifyStation(bikeData.getStationId(), bike);
					bike.setStation(station);
					pm.makePersistent(bike);
					System.out.println(bike.getStation().getBikes());
					LogManager.getLogger(AdminService.class).info("Addition Success | Bike: '{}'", bike.getId());
					
				}
				
				tx.commit();
				return true;
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
			}
	 }
	 
	 public Station modifyStation(int idStation, Bicycle bike) { 
		 	LogManager.getLogger(AdminService.class).info("Modify | Station: '{}'", idStation);
			try {
				Station station = null;
				
				try {
					station = pm.getObjectById(Station.class, idStation);
					// If the user is found, return an unauthorized response
					if (station == null) {
						LogManager.getLogger(AdminService.class)
								.info("Addition Failed | The station does not exist | Station: '{}'", idStation);
						return null;
					}
				} catch (javax.jdo.JDOObjectNotFoundException jonfe) {}
					// User not found, proceed to registration
					
					
					
					List<Bicycle> bikes=new ArrayList<Bicycle>();
					for (Bicycle b: station.getBikes()) {
						bikes.add(b);
					}
					bikes.add(bike);
					station.setBikes(bikes);
					
					
					
					return station;
			} finally {
				
			}
	 }
	 
	 public boolean deleteBike(int bikeId) {
		LogManager.getLogger(AdminService.class).info("Delete | Bike: '{}'", bikeId);
        Transaction tx = pm.currentTransaction();
        
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
        }
        return false;
	    
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
				 bike.setAvailable(true);
				 pm.makePersistent(bike);
			  } catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				  LogManager.getLogger(AdminService.class).info("Bike not found, cannot disable | Bike: '{}'", bikeId);
		          return false;
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
