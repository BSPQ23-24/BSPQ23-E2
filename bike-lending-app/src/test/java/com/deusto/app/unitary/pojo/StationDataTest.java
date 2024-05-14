package com.deusto.app.unitary.pojo;

import org.junit.jupiter.api.Test;

import com.deusto.app.server.pojo.StationData;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class StationDataTest {

    @Test
    void testGetAndSetId() {
        StationData stationData = new StationData();
        stationData.setId(1);
        assertEquals(1, stationData.getId());
    }

    @Test
    void testGetAndSetLocation() {
        StationData stationData = new StationData();
        stationData.setLocation("Main Street");
        assertEquals("Main Street", stationData.getLocation());
    }

    @Test
    void testGetAndSetBikeIds() {
        StationData stationData = new StationData();
        List<Integer> bikeIds = Arrays.asList(1, 2, 3);
        stationData.setBikeIds(bikeIds);
        assertEquals(bikeIds, stationData.getBikeIds());
    }

    @Test
    void testToString() {
        StationData stationData = new StationData();
        stationData.setId(1);
        stationData.setLocation("Main Street");
        List<Integer> bikeIds = Arrays.asList(1, 2, 3);
        stationData.setBikeIds(bikeIds);
        String expected = "StationData{id=1, location='Main Street', bikeIds=" + bikeIds + "}";
        assertEquals(expected, stationData.toString());
    }
}
