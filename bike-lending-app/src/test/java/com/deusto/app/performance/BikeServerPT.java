package com.deusto.app.performance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.client.remote.ServiceLocator;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.UserData;
import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

@Tag("PerformanceTest")
public class BikeServerPT {

	@BeforeAll
	public static void setUp() {
		ServiceLocator.getInstance().setService("127.0.0.1", "8080");

		UserData userTestData = new UserData();
		userTestData.setDni("12345678A");
		userTestData.setPassword("password123");

		UserController.getInstance().loginUser(userTestData);
	}

	@Rule
	public JUnitPerfRule perfTestRule = new JUnitPerfRule(new HtmlReportGenerator("target/junitperf/report.html"));
	
	@Test
	@JUnitPerfTest(threads = 10, durationMs = 2000)
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

	@Test
	@JUnitPerfTest(threads = 10, durationMs = 2000)
	public void testRegisterUser() {
		// Prepare user data
		UserData userData = new UserData();
		userData.setDni(UUID.randomUUID().toString());
		userData.setPassword("performance");
		userData.setName("UsuarioPerformance");
		userData.setSurname("Surname");
		userData.setDateOfBirth("01-01-2000");
		userData.setPhone("123456789");
		userData.setMail("test@mail.es");

		// Attempt to register the user
		boolean registrationResult = UserController.getInstance().registerUser(userData);
		Assertions.assertTrue(registrationResult, "User registration should return true on success.");
	}
}