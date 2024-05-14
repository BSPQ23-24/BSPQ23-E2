package com.deusto.app.unitary.domain;

import org.junit.jupiter.api.Test;

import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Station;

import static org.junit.jupiter.api.Assertions.*;

class BicycleTest {

    @Test
    void testGetAndSetId() {
        Bicycle bicycle = new Bicycle();
        bicycle.setId(1);
        assertEquals(1, bicycle.getId());
    }

    @Test
    void testGetAndSetAcquisitionDate() {
        Bicycle bicycle = new Bicycle();
        bicycle.setAcquisitionDate("2021-05-14");
        assertEquals("2021-05-14", bicycle.getAcquisitionDate());
    }

    @Test
    void testGetAndSetType() {
        Bicycle bicycle = new Bicycle();
        bicycle.setType("Mountain");
        assertEquals("Mountain", bicycle.getType());
    }

    @Test
    void testGetAndSetAvailable() {
        Bicycle bicycle = new Bicycle();
        bicycle.setAvailable(true);
        assertTrue(bicycle.isAvailable());
        bicycle.setAvailable(false);
        assertFalse(bicycle.isAvailable());
    }

    @Test
    void testGetAndSetStation() {
        Bicycle bicycle = new Bicycle();
        Station station = new Station();
        bicycle.setStation(station);
        assertEquals(station, bicycle.getStation());
    }

    @Test
    void testToString() {
        Bicycle bicycle = new Bicycle();
        bicycle.setId(1);
        bicycle.setAcquisitionDate("2021-05-14");
        bicycle.setType("Mountain");
        bicycle.setAvailable(true);
        String expected = "ID: 1 / Type: Mountain / Acquisition Date: 2021-05-14 / Available: Yes";
        assertEquals(expected, bicycle.toString());
    }

    @Test
    void testEquals() {
        Bicycle bicycle1 = new Bicycle();
        Bicycle bicycle2 = new Bicycle();
        bicycle1.setId(1);
        bicycle2.setId(1);
        assertTrue(bicycle1.equals(bicycle2));

        bicycle2.setId(2);
        assertFalse(bicycle1.equals(bicycle2));

        assertFalse(bicycle1.equals(new Object()));
    }
}