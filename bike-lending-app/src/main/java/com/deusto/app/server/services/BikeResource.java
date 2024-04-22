
package com.deusto.app.server.services;


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


public class BikeResource {
    private PersistenceManager pm = null;
    private Transaction tx = null;
    protected static final Logger logger = LogManager.getLogger();

    public BikeResource() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        this.pm = pmf.getPersistenceManager();
        this.tx = pm.currentTransaction();
    }
    
    
    public Response createBike(int stationId, Bicycle bikeData) {
        try {
            tx.begin();

            //creation of new station
            Station station = new Station();
            station.setLocation("Central Park");
            pm.makePersistent(station);

            // creation of new bicycle
            Bicycle bike = new Bicycle();
            bike.setAcquisitionDate(new String());
            bike.setType("Mountain Bike");

            bike.setStation(station);

            pm.makePersistent(bike);

            tx.commit();

            return Response.ok(bike).build();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            if (pm != null && !pm.isClosed()) {
                pm.close();
            }
        }
    }
    

    @GET
    @Path("/stations")
    public Response displayStationsAndBikes() {
        try {
            tx.begin();

            Query<Station> stationQuery = pm.newQuery(Station.class);
            List<Station> stations = (List<Station>) stationQuery.execute();

            StringBuilder result = new StringBuilder();
            for (Station station : stations) {
                result.append("Station ID: ").append(station.getId()).append(" / Location: ").append(station.getLocation()).append("\n");

                Query bikeQuery = pm.newQuery(Bicycle.class);
                bikeQuery.setFilter("station == stationParam");
                bikeQuery.declareParameters("com.deusto.app.server.data.domain.Station stationParam");
                List<Bicycle> bikesAtStation = (List<Bicycle>) bikeQuery.execute(station);

                if (!bikesAtStation.isEmpty()) {
                    result.append("Bikes at this station: ");
                    for (Bicycle bike : bikesAtStation) {
                        result.append(bike.getId()).append(", ");
                    }
                    result.setLength(result.length() - 2);
                    result.append("\n");
                } else {
                    result.append("No bikes at this station\n");
                }
            }

            tx.commit();
            return Response.ok(result.toString()).build();
        } catch (Exception e) {
            logger.error("Error displaying stations and bikes: {}", e.getMessage());
            if (tx.isActive()) {
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error displaying stations and bikes").build();
        } finally {
            pm.close();
        }
    }

    public Response selectBike(int stationId) {
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
                tx.commit();
                return Response.ok(selectedBike).build();
            } else {
                tx.rollback();
                return Response.status(Response.Status.NOT_FOUND).entity("No available bikes at this station").build();
            }
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            if (pm != null && !pm.isClosed()) {
                pm.close();
            }
        }
    }

    public Response getAvailableBikesInStation(int stationId) {
        try {
            tx.begin();

            Station station = pm.getObjectById(Station.class, stationId);
            List<Bicycle> availableBikes = station.getBikes();

            tx.commit();

            return Response.ok(availableBikes).build();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
            return Response.serverError().build();
        } finally {
            if (!pm.isClosed()) {
                pm.close();
            }
        }
    }
}

