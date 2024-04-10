package com.deusto.app.client.controller;

import com.deusto.app.client.remote.ServiceLocator;
import com.deusto.app.server.pojo.UserData;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;

public class UserController {
    private static UserController instance;

    private UserController() {}

    public static synchronized UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    public boolean registerUser(UserData userData) {
    	WebTarget registerUserWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/user/register");
        Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
        	LogManager.getLogger(UserController.class).info("User correctly registered");
            return true;
            
        } else {
        	LogManager.getLogger(UserController.class).error("Registration failed. Code: {}, Reason: {}", response.getStatus(), response.readEntity(String.class));
            return false;
        }
    }
    
    public Long loginUser(UserData userData) {
        WebTarget loginUserWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/user/login");
        Invocation.Builder invocationBuilder = loginUserWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            long token = response.readEntity(Long.class);
            LogManager.getLogger(UserController.class).info("User login successful, token received: {}", token);
            return token;
        } else {
            LogManager.getLogger(UserController.class).error("Login failed. Code: {}, Reason: {}", response.getStatus(), response.readEntity(String.class));
            return null;
        }
    }

    // Add more methods to handle other user actions like loginUser, sayHello, etc.
}
