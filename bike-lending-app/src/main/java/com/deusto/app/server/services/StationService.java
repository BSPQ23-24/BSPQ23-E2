package com.deusto.app.server.services;

import com.deusto.app.server.data.domain.*;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import java.util.List;

public class StationService {

    // Devuleve todas las estaciones con caada una def las bicis que tenga
    public List<Station> getAllAvailableStationsWithBikes() {
    	PersistenceManagerFactory pm = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        List<Station> stations = null;
        try {
            Query query = ((PersistenceManager) pm).newQuery(Station.class);
            query.setFilter("isAvailable == true");
            stations = (List<Station>) query.execute();
            for (Station station : stations) {
                station.setBikes(getAvailableBikesForStation(station.getID()));
            }
        } finally {
            pm.close();
        }
        return stations;
    }

    // Devuelve las bicis de cada estacion
    private List<Bicycle> getAvailableBikesForStation(int stationId) {
    	PersistenceManagerFactory pm = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        List<Bicycle> bikes = null;
        try {
            Query query = pm.newQuery(Bicycle.class);
            query.setFilter("stationID == " + stationId + " && isAvailable == true");
            bikes = (List<Bicycle>) query.execute();
        } finally {
            pm.close();
        }
        return bikes;
    }
    
    
 // Devuelve todas las bicis de la estacion especifica que quieras
    public List<Bicycle> getAvailableBikesAtStation(int stationId) {
    	PersistenceManagerFactory pm = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        List<Bicycle> availableBikes = null;
        try {
            Query query = pm.newQuery(Bicycle.class);
            query.setFilter("stationID == " + stationId + " && isAvailable == true");
            availableBikes = (List<Bicycle>) query.execute();
        } finally {
            pm.close();
        }
        return availableBikes;
    }

    //Para seleccionar una bici especifica de las disponibles en una estacion
    public Bicycle selectBike(int stationId, int bikeId) {
    	PersistenceManagerFactory pm = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        Bicycle selectedBike = null;
        try {
            Query query = pm.newQuery(Bicycle.class);
            query.setFilter("stationID == " + stationId + " && ID == " + bikeId + " && isAvailable == true");
            List<Bicycle> bikes = (List<Bicycle>) query.execute();
            if (!bikes.isEmpty()) {
                selectedBike = bikes.get(0);
                selectedBike.setAvailable(false);
                pm.makePersistent(selectedBike);
            }
        } finally {
            pm.close();
        }
        return selectedBike;
    }
}