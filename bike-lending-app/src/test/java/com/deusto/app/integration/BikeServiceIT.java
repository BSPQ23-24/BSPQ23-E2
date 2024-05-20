package com.deusto.app.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.client.remote.ServiceLocator;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationData;
import com.deusto.app.server.pojo.UserData;

@Tag("IntegrationTest")
public class BikeServiceIT {

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

	@Test
	public void testGetAvailableBikesInStation() {

		int stationId = 1;

		List<BicycleData> response = BikeController.getInstance().getAvailableBikesInStation(stationId,
				UserController.getInstance().getToken());

		assertNotNull(response, "Response should not be null");
		assertFalse(response.isEmpty(), "List of bikes should not be empty");
		assertEquals(response.size(), 2, "Number of bikes should match");
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
