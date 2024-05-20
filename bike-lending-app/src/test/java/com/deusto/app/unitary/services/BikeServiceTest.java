package com.deusto.app.unitary.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Station;
import com.deusto.app.server.pojo.BicycleAssembler;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationAssembler;
import com.deusto.app.server.pojo.StationData;
import com.deusto.app.server.services.BikeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BikeServiceTest {


    @InjectMocks
    private BikeService bikeService;


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

        // Mocking behavior
        BikeService mockedBikeService = mock(BikeService.class);
        when(mockedBikeService.getAvailableBikesInStation(1))
                .thenReturn(BicycleAssembler.getInstance().bikesToPOJO(bikes));

        // Testing
        List<BicycleData> bicycleDataList = mockedBikeService.getAvailableBikesInStation(1);
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

        // Mocking behavior
        BikeService mockedBikeService = mock(BikeService.class);
        List<Station> stations = Arrays.asList(station1);
        when(mockedBikeService.displayStations()).thenReturn(StationAssembler.getInstance().stationsToPOJO(stations));

        // Testing
        List<StationData> stationDataList = mockedBikeService.displayStations();
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

        // Mocking behavior
        BikeService mockedBikeService = mock(BikeService.class);
        when(mockedBikeService.getBikeById(1))
                .thenReturn(BicycleAssembler.getInstance().bikeToPOJO(bicycle));

        // Testing
        BicycleData bicycleData = mockedBikeService.getBikeById(1);
        assertEquals(1, bicycleData.getId());
        assertEquals("Type", bicycleData.getType());
    }
}
