package com.deusto.app.server.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.deusto.app.client.controller.BikeController;
import com.deusto.app.server.services.UserService;

public class BikeServiceTest {
	
    private static BikeController bikeController;

    @BeforeAll
    public static void setUp() {
        // Set the properties as needed for the test environment
        System.setProperty("bikeapp.hostname", "localhost");
        System.setProperty("bikeapp.port", "8080");
        // To ensure that the user service is initialized before executing any test.
        UserService.getInstance();
        bikeController = BikeController.getInstance();
    }
    
    @Test
    public void testCreateBike() {
    	
    }

}
