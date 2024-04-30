package com.deusto.app.server.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.remote.ServiceLocator;
import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Station;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.services.BikeService;
import com.deusto.app.server.services.UserService;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class BikeServiceTest {
	
	@Mock
	private static BikeController bikeController;
    
    @Mock
    private static Invocation.Builder invocationBuilder;


    @BeforeAll
    public void setUp() {
        // Set the properties as needed for the test environment
        System.setProperty("bikeapp.hostname", "localhost");
        System.setProperty("bikeapp.port", "8080");
        MockitoAnnotations.openMocks(BikeServiceTest.class);
       
        // To ensure that the user service is initialized before executing any test.
        UserService.getInstance();
        bikeController = BikeController.getInstance();
        invocationBuilder = mock(Invocation.Builder.class);
    }
    
    @Test
    public void testDisplayStationsAndBikes() {
        // Arrange
        long token = 12345L;
        String expectedResponse = "Station ID: 1 / Location: Central Park\nBikes at this station: 1, 2\n";
        Response mockResponse = Response.ok(expectedResponse).build();

        // Mocking the behavior of Invocation.Builder to return a mocked response
        when(invocationBuilder.get()).thenReturn(mockResponse);

        // Act
        Response response = bikeController.displayStationsAndBikes(token);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Response status should be OK");
        assertEquals(expectedResponse, response.getEntity(), "Response entity should match expected value");
    }
    
    @Test
    public void testCreateBike() {
        // Arrange
        int stationId = 1;
        long token = 12345L;
        BicycleData bikeData = new BicycleData();
        bikeData.setAcquisitionDate("2024-04-26");
        bikeData.setType("Mountain Bike");
        String expectedResponse = "Bike created with ID: 1";
        Response mockResponse = Response.ok(expectedResponse).build();

        // Mocking the behavior of Invocation.Builder to return a mocked response
        when(invocationBuilder.post(any(Entity.class))).thenReturn(mockResponse);

        // Act
        Response response = bikeController.createBike(stationId, bikeData, token);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Response status should be OK");
        assertEquals(expectedResponse, response.getEntity(), "Response entity should match expected value");
    }
    
    @Test
    public void testSelectBike() {
        // Arrange
        int stationId = 1;
        long token = 12345L;
        String expectedResponse = "Bike selected!";
        Response mockResponse = Response.ok(expectedResponse).build();

        // Mocking the behavior of Invocation.Builder to return a mocked response
        when(invocationBuilder.get()).thenReturn(mockResponse);

        // Act
        Response response = bikeController.selectBike(stationId, token);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Response status should be OK");
        assertEquals(expectedResponse, response.getEntity(), "Response entity should match expected value");
    }
    
    @Test
    public void testGetAvailableBikesInStation() {
        // Arrange
        int stationId = 1;
        long token = 12345L;
        String expectedResponse = "Bikes of the station " + stationId;
        Response mockResponse = Response.ok(expectedResponse).build();

        // Mocking the behavior of Invocation.Builder to return a mocked response
        when(invocationBuilder.get()).thenReturn(mockResponse);

        // Act
        Response response = bikeController.getAvailableBikesInStation(stationId, token);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Response status should be OK");
        assertEquals(expectedResponse, response.getEntity(), "Response entity should match expected value");
    }
    
    @Test
    public void testGetBikeDetails() {
        // Arrange
        int bikeId = 1;
        long token = 12345L;
        String expectedResponse = "Bike details for bike " + bikeId;
        Response mockResponse = Response.ok(expectedResponse).build();

        // Mocking the behavior of Invocation.Builder to return a mocked response
        when(invocationBuilder.get()).thenReturn(mockResponse);

        // Act
        Response response = bikeController.getBikeDetails(bikeId, token);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Response status should be OK");
        assertEquals(expectedResponse, response.getEntity(), "Response entity should match expected value");
    }
    
    
}
