package com.deusto.app.server.services;

import java.util.Date;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Station;

@Path("/bike")
@Produces(MediaType.APPLICATION_JSON)
public class BikeResource {
    
    private PersistenceManager pm = null;
    private Transaction tx = null;

    public BikeResource() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        this.pm = pmf.getPersistenceManager();
        this.tx = pm.currentTransaction();
    }

    @POST
    @Path("/create")
    public Response createBike(int stationId, Bicycle bikeData) {
        try {
            tx.begin();

         // creation of new station
            Station station = new Station();
            station.setLocation("Central Park");

            pm.makePersistent(station);

            // creation of new bicycle
            Bicycle bike = new Bicycle();
            bike.setAcquisitionDate(new Date());
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
    @Path("/select")
    public Response selectBike(int stationId) {
        try {
            tx.begin();

            Station station = pm.getObjectById(Station.class, stationId);

         // Get the first bike available at the station
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
}
