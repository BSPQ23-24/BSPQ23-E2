package com.deusto.app.unitary.pojo;

import org.junit.jupiter.api.Test;

import com.deusto.app.server.pojo.BicycleData;

import static org.junit.jupiter.api.Assertions.*;

class BicycleDataTest {

    @Test
    void testGetAndSetId() {
        BicycleData bicycleData = new BicycleData();
        bicycleData.setId(1);
        assertEquals(1, bicycleData.getId());
    }

    @Test
    void testGetAndSetAcquisitionDate() {
        BicycleData bicycleData = new BicycleData();
        bicycleData.setAcquisitionDate("2021-05-14");
        assertEquals("2021-05-14", bicycleData.getAcquisitionDate());
    }

    @Test
    void testGetAndSetType() {
        BicycleData bicycleData = new BicycleData();
        bicycleData.setType("Mountain");
        assertEquals("Mountain", bicycleData.getType());
    }

    @Test
    void testGetAndSetAvailable() {
        BicycleData bicycleData = new BicycleData();
        bicycleData.setAvailable(true);
        assertTrue(bicycleData.isAvailable());
        bicycleData.setAvailable(false);
        assertFalse(bicycleData.isAvailable());
    }

    @Test
    void testGetAndSetStationId() {
        BicycleData bicycleData = new BicycleData();
        bicycleData.setStationId(1);
        assertEquals(1, bicycleData.getStationId());
    }

    @Test
    void testToString() {
        BicycleData bicycleData = new BicycleData();
        bicycleData.setId(1);
        bicycleData.setAcquisitionDate("2021-05-14");
        bicycleData.setType("Mountain");
        bicycleData.setAvailable(true);
        bicycleData.setStationId(1);
        String expected = "BicycleData{id=1, acquisitionDate='2021-05-14', type='Mountain', isAvailable=true, stationId=1}";
        assertEquals(expected, bicycleData.toString());
    }
}
