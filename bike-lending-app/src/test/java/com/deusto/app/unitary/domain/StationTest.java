package com.deusto.app.unitary.domain;

import org.junit.jupiter.api.Test;

import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Station;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class StationTest {

    @Test
    void testGetAndSetId() {
        Station station = new Station();
        station.setId(1);
        assertEquals(1, station.getId());
    }

    @Test
    void testGetAndSetLocation() {
        Station station = new Station();
        station.setLocation("Main Street");
        assertEquals("Main Street", station.getLocation());
    }

    @Test
    void testGetAndSetBikes() {
        Station station = new Station();
        List<Bicycle> bikes = new ArrayList<>();
        Bicycle bike1 = new Bicycle();
        Bicycle bike2 = new Bicycle();
        bikes.add(bike1);
        bikes.add(bike2);
        station.setBikes(bikes);
        assertEquals(bikes, station.getBikes());
    }

    @Test
    void testToString() {
        Station station = new Station();
        station.setId(1);
        station.setLocation("Main Street");
        String expected = "Station ID: 1 / Location: Main Street";
        assertEquals(expected, station.toString());
    }

    @Test
    void testEquals() {
        Station station1 = new Station();
        Station station2 = new Station();
        station1.setId(1);
        station2.setId(1);
        assertTrue(station1.equals(station2));

        station2.setId(2);
        assertFalse(station1.equals(station2));

        assertFalse(station1.equals(new Object()));
    }
}
