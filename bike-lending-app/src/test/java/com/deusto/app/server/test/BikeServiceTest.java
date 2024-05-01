package com.deusto.app.server.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.client.remote.ServiceLocator;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationData;
import com.deusto.app.server.pojo.UserData;

public class BikeServiceTest {

	@BeforeAll
	public static void setUp() {
		ServiceLocator.getInstance().setService("127.0.0.1", "8080");

		UserData userTestData = new UserData();
		userTestData.setDni("12345678A");
		userTestData.setPassword("password123");

		UserController.getInstance().loginUser(userTestData);
	}

	@Test
	public void testDisplayStations() {
		List<StationData> response = BikeController.getInstance()
				.displayStations(UserController.getInstance().getToken());

		assertNotNull(response, "Response should not be null");
		assertFalse(response.isEmpty(), "List of stations should not be empty");
		assertEquals(2, response.size(), "Number of stations should match");

	}

	/*
	 * @Test public void testCreateBike() { // Arrange BicycleData bikeData = new
	 * BicycleData(); bikeData.setType("Mountain Bike");
	 * bikeData.setAcquisitionDate("2024-04-26");
	 * 
	 * 
	 * // Act boolean result = BikeController.getInstance().createBike(stationId,
	 * bikeData, userController.getToken());
	 * 
	 * // Assert assertTrue(result, "Bike creation should return true on success");
	 * }
	 */

	/*
	 * @Test public void testSelectBike() { // Arrange int stationId = 1;
	 * BicycleData expectedBike = new BicycleData(); expectedBike.setId(1);
	 * expectedBike.setType("Mountain Bike"); String expectedResponse =
	 * "{\"id\":1,\"type\":\"Mountain Bike\"}"; Response mockResponse =
	 * Response.ok(expectedResponse).build();
	 * 
	 * // Mocking the behavior of Invocation.Builder to return a mocked response
	 * when(invocationBuilder.get()).thenReturn(mockResponse);
	 * 
	 * // Act BicycleData response = bikeController.selectBike(stationId,
	 * userController.getToken());
	 * 
	 * // Assert assertNotNull(response, "Response should not be null");
	 * assertEquals(expectedBike.getId(), response.getId(), "Bike ID should match");
	 * assertEquals(expectedBike.getType(), response.getType(),
	 * "Bike type should match"); }
	 */

	@Test
	public void testGetAvailableBikesInStation() {

		int stationId = 1;

		List<BicycleData> response = BikeController.getInstance().getAvailableBikesInStation(stationId,
				UserController.getInstance().getToken());

		assertNotNull(response, "Response should not be null");
		assertFalse(response.isEmpty(), "List of bikes should not be empty");
		assertEquals(response.size(), 2, "Number of bikes should match");
		assertTrue(response.getFirst().isAvailable(), "The bikes in the list should be available");
	}

	@Test
	public void testGetBikeDetails() {

		int bikeId = 1;

		BicycleData expectedBikeData = new BicycleData();
		expectedBikeData.setId(bikeId);
		expectedBikeData.setType("Mountain");
		expectedBikeData.setAcquisitionDate("2024-04-26");

		BicycleData response = BikeController.getInstance().getBikeDetails(bikeId,
				UserController.getInstance().getToken());

		assertNotNull(response, "Response should not be null");
		assertEquals(response.getId(), expectedBikeData.getId(), "The bike's ID should be the same");
		assertEquals(response.getType(), expectedBikeData.getType(), "The bike's details should be the same");
	}

	@AfterAll
	public static void tearDown() {
		UserController.getInstance().logoutUser(UserController.getInstance().getToken());
	}
}
