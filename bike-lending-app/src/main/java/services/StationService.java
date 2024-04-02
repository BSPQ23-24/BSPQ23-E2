package services;

import domain.Bicycle;
import domain.Station;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;

public class StationService {

    // Devuleve todas las estaciones con caada una def las bicis que tenga
    public List<Station> getAllAvailableStationsWithBikes() {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        List<Station> stations = null;
        try {
            Query query = pm.newQuery(Station.class);
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
        PersistenceManager pm = PMF.get().getPersistenceManager();
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
}