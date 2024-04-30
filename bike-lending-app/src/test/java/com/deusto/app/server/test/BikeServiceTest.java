package com.deusto.app.server.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationData;

import com.deusto.app.server.services.UserService;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.Response;

public class BikeServiceTest {
	
	@Mock
	private BikeController bikeController;
	
	@Mock
	private UserController userController;
    
    @Mock
    private Invocation.Builder invocationBuilder;


    @BeforeEach
    public void setUp() {
        // Set the properties as needed for the test environment
        System.setProperty("bikeapp.hostname", "localhost");
        System.setProperty("bikeapp.port", "8080");
        MockitoAnnotations.openMocks(BikeServiceTest.class);
       
        // To ensure that the user service is initialized before executing any test.
        UserService.getInstance();
        bikeController = BikeController.getInstance();
        invocationBuilder = mock(Invocation.Builder.class);
        userController = UserController.getInstance();
    }
    
    @Test
    public void testDisplayStations_Success() {
        // Arrange
        List<StationData> expectedStations = new ArrayList<>();
        StationData station1 = new StationData();
        station1.setId(1);
        station1.setLocation("Central Park");
        expectedStations.add(station1);
        String expectedResponse = "[{\"id\":1,\"location\":\"Central Park\"}]";
        Response mockResponse = Response.ok(expectedResponse).build();

        // Mocking the behavior of Invocation.Builder to return a mocked response
        when(invocationBuilder.get()).thenReturn(mockResponse);

        // Act
        List<StationData> response = bikeController.displayStations(userController.getToken());

        // Assert
        assertNotNull(response, "Response should not be null");
        assertFalse(response.isEmpty(), "List of stations should not be empty");
        assertEquals(expectedStations.size(), response.size(), "Number of stations should match");
        assertTrue(response.contains(station1), "List of stations should contain station1");
    }

    
    @Test
    public void testCreateBike_Success() {
        // Arrange
        int stationId = 1;
        long token = 12345L;
        BicycleData bikeData = new BicycleData();
        bikeData.setType("Mountain Bike");
        bikeData.setAcquisitionDate("2024-04-26");
        String expectedResponse = "Bike Created!";
        Response mockResponse = Response.ok(expectedResponse).build();

        // Mocking the behavior of Invocation.Builder to return a mocked response
        when(invocationBuilder.post(any(Entity.class))).thenReturn(mockResponse);

        // Act
        boolean result = bikeController.createBike(stationId, bikeData, userController.getToken());

        // Assert
        assertTrue(result, "Bike creation should return true on success");
    }

    @Test
    public void testCreateBike_Failure() {
        // Arrange
        int stationId = 1;
        long token = 12345L;
        BicycleData bikeData = new BicycleData();
        bikeData.setType("Mountain Bike");
        bikeData.setAcquisitionDate("2024-04-26");
        String expectedErrorMessage = "Failed to create bike";
        Response mockResponse = Response.serverError().entity(expectedErrorMessage).build();

        // Mocking the behavior of Invocation.Builder to return a mocked response
        when(invocationBuilder.post(any(Entity.class))).thenReturn(mockResponse);

        // Act
        boolean result = bikeController.createBike(stationId, bikeData, userController.getToken());

        // Assert
        assertFalse(result, "Bike creation should return false on failure");
    }

    
    @Test
    public void testSelectBike_Success() {
        // Arrange
        int stationId = 1;
        BicycleData expectedBike = new BicycleData();
        expectedBike.setId(1);
        expectedBike.setType("Mountain Bike");
        String expectedResponse = "{\"id\":1,\"type\":\"Mountain Bike\"}";
        Response mockResponse = Response.ok(expectedResponse).build();

        // Mocking the behavior of Invocation.Builder to return a mocked response
        when(invocationBuilder.get()).thenReturn(mockResponse);

        // Act
        BicycleData response = bikeController.selectBike(stationId, userController.getToken());

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(expectedBike.getId(), response.getId(), "Bike ID should match");
        assertEquals(expectedBike.getType(), response.getType(), "Bike type should match");
    }

    
    @Test
    public void testGetAvailableBikesInStation_Success() {
        // Arrange
        int stationId = 1;
        List<BicycleData> expectedBikes = new ArrayList<>();
        BicycleData bike1 = new BicycleData();
        bike1.setId(1);
        bike1.setType("Mountain Bike");
        expectedBikes.add(bike1);
        String expectedResponse = "[{\"id\":1,\"type\":\"Mountain Bike\"}]";
        Response mockResponse = Response.ok(expectedResponse).build();

        // Mocking the behavior of Invocation.Builder to return a mocked response
        when(invocationBuilder.get()).thenReturn(mockResponse);

        // Act
        List<BicycleData> response = bikeController.getAvailableBikesInStation(stationId, userController.getToken());

        // Assert
        assertNotNull(response, "Response should not be null");
        assertFalse(response.isEmpty(), "List of bikes should not be empty");
        assertEquals(expectedBikes.size(), response.size(), "Number of bikes should match");
        assertTrue(response.contains(bike1), "List of bikes should contain bike1");
    }

    
    @Test
    public void testGetBikeDetails_Success() {
        // Arrange
        int bikeId = 1;
        BicycleData expectedBikeData = new BicycleData();
        expectedBikeData.setId(bikeId);
        expectedBikeData.setType("Mountain Bike");
        expectedBikeData.setAcquisitionDate("2024-04-26");
        String expectedResponse = "{\"bikeId\":1,\"type\":\"Mountain Bike\",\"acquisitionDate\":\"2024-04-26\"}";
        Response mockResponse = Response.ok(expectedResponse).build();

        // Mocking the behavior of Invocation.Builder to return a mocked response
        when(invocationBuilder.get()).thenReturn(mockResponse);

        // Act
        BikeController bikeController = BikeController.getInstance();
        BicycleData response = bikeController.getBikeDetails(bikeId, userController.getToken());

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(expectedBikeData.getId(), response.getId(), "BikeId should match");
        assertEquals(expectedBikeData.getType(), response.getType(), "Type should match");
        assertEquals(expectedBikeData.getAcquisitionDate(), response.getAcquisitionDate(), "Acquisition date should match");
        assertTrue(response.getId() > 0, "BikeId should be greater than 0");
        assertTrue(response.getType().equals("Mountain Bike"), "Type should be 'Mountain Bike'");
    }
    
    @Test
    public void testGetBikeDetails_Failure() {
        // Arrange
        int bikeId = 1;
        Response mockResponse = Response.status(Response.Status.NOT_FOUND).build();

        // Mocking the behavior of Invocation.Builder to return a mocked response
        when(invocationBuilder.get()).thenReturn(mockResponse);

        // Act
        BicycleData response = bikeController.getBikeDetails(bikeId, userController.getToken());

        // Assert
        assertNull(response, "Response should be null when status is not OK");
    }
    
    
}
