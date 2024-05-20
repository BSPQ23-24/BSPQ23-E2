package com.deusto.app.unitary.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Station;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationData;
import com.deusto.app.server.services.BikeService;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BikeServiceTest {

    @InjectMocks
    private BikeService bikeService;

    @Mock
    private PersistenceManagerFactory pmf;

    @Mock
    private PersistenceManager pm;

    @Mock
    private Transaction tx;

    @BeforeEach
    public void setUp() {
        when(pmf.getPersistenceManager()).thenReturn(pm);
        when(pm.currentTransaction()).thenReturn(tx);
    }

    @Test
    public void testGetAvailableBikesInStation() {
        // Mocking data
        Station station = new Station();
        station.setId(1);
        station.setLocation("Location");

        // Create bicycles and associate them with the station
        Bicycle bicycle1 = new Bicycle();
        bicycle1.setId(1);
        bicycle1.setAcquisitionDate("2024-05-17");
        bicycle1.setType("Type 1");
        bicycle1.setAvailable(true);
        bicycle1.setStation(station);

        Bicycle bicycle2 = new Bicycle();
        bicycle2.setId(2);
        bicycle2.setAcquisitionDate("2024-05-18");
        bicycle2.setType("Type 2");
        bicycle2.setAvailable(false);
        bicycle2.setStation(station);

        List<Bicycle> bikes = new ArrayList<>();
        bikes.add(bicycle1);
        bikes.add(bicycle2);
        station.setBikes(bikes);

        when(pm.getObjectById(Station.class, 1)).thenReturn(station);

        // Testing
        List<BicycleData> bicycleDataList = bikeService.getAvailableBikesInStation(1);
        assertEquals(2, bicycleDataList.size());
        assertEquals("Type 1", bicycleDataList.get(0).getType());
        assertEquals("Type 2", bicycleDataList.get(1).getType());
    }

    @Test
    public void testDisplayStations() {
        // Mocking data
        Station station1 = new Station();
        station1.setId(1);
        station1.setLocation("Location 1");

        // Create bicycles and associate them with the station
        Bicycle bicycle1Station1 = new Bicycle();
        bicycle1Station1.setId(1);
        bicycle1Station1.setAcquisitionDate("2024-05-17");
        bicycle1Station1.setType("Type 1");
        bicycle1Station1.setAvailable(true);
        bicycle1Station1.setStation(station1);

        List<Bicycle> bikesStation1 = new ArrayList<>();
        bikesStation1.add(bicycle1Station1);
        station1.setBikes(bikesStation1);

        List<Station> stations = Arrays.asList(station1);
        Query<Station> stationQuery = mock(Query.class);
        when(pm.newQuery(Station.class)).thenReturn(stationQuery);
        when(stationQuery.execute()).thenReturn(stations);

        // Testing
        List<StationData> stationDataList = bikeService.displayStations();
        assertEquals(1, stationDataList.size());
        assertEquals("Location 1", stationDataList.get(0).getLocation());
        assertNotNull(stationDataList.get(0).getBikeIds()); // Ensure bikeIds list is not null
        assertEquals(1, stationDataList.get(0).getBikeIds().size()); // Ensure bikeIds list contains one element
        assertEquals(Integer.valueOf(1), stationDataList.get(0).getBikeIds().get(0)); // Ensure the correct bike ID is present
    }

    @Test
    public void testGetBikeById() {
        // Mocking data
        Bicycle bicycle = new Bicycle();
        bicycle.setId(1);
        bicycle.setType("Type");
        bicycle.setStation(new Station()); // Set a dummy station

        when(pm.getObjectById(Bicycle.class, 1)).thenReturn(bicycle);

        // Testing
        BicycleData bicycleData = bikeService.getBikeById(1);
        assertEquals(1, bicycleData.getId());
        assertEquals("Type", bicycleData.getType());
    }
}
