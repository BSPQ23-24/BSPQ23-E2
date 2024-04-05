package com.deusto.app.server.services;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import jakarta.ws.rs.GET;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.deusto.app.server.data.domain.*;

import java.util.List;

@Path("/bike")
@Produces(MediaType.APPLICATION_JSON)
public class BikeResource {

    protected static final Logger logger = LogManager.getLogger();

    private PersistenceManager pm = null;
    private Transaction tx = null;

    public BikeResource() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        this.pm = pmf.getPersistenceManager();
        this.tx = pm.currentTransaction();
    }

    @GET
    @Path("/select")
    public Response selectBike() {
        try {
            tx.begin();

            Query query = pm.newQuery(Bicycle.class);
            query.setFilter("isAvailable == true");
            List<Bicycle> availableBikes = (List<Bicycle>) query.execute();

            if (!availableBikes.isEmpty()) {
                Bicycle selectedBike = availableBikes.get(0);
                selectedBike.setAvailable(false); 
                pm.makePersistent(selectedBike); 
                tx.commit();
                return Response.ok("Bike with ID: " + selectedBike.getID() + " selected!").build();
            } else {
                tx.rollback();
                return Response.status(Response.Status.NOT_FOUND).entity("No available bikes found").build();
            }
        } catch (Exception e) {
            logger.error("Error selecting bike: {}", e.getMessage());
            if (tx.isActive()) {
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error selecting bike").build();
        } finally {
            pm.close();
        }
    }

    @GET
    @Path("/stations")
    public Response displayStationsAndBikes() {
        try {
            tx.begin();

            Query stationQuery = pm.newQuery(Station.class);
            List<Station> stations = (List<Station>) stationQuery.execute();

            StringBuilder result = new StringBuilder();
            for (Station station : stations) {
                result.append("Station ID: ").append(station.getID()).append(" / Location: ").append(station.getLocation()).append("\n");

                Query bikeQuery = pm.newQuery(Bicycle.class);
                bikeQuery.setFilter("station == stationParam");
                bikeQuery.declareParameters("com.deusto.app.server.data.domain.Station stationParam");
                List<Bicycle> bikesAtStation = (List<Bicycle>) bikeQuery.execute(station);

                if (!bikesAtStation.isEmpty()) {
                    result.append("Bikes at this station: ");
                    for (Bicycle bike : bikesAtStation) {
                        result.append(bike.getID()).append(", ");
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
}
