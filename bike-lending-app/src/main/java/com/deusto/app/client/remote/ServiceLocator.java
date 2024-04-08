package com.deusto.app.client.remote;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

public class ServiceLocator {
    private static ServiceLocator instance;
    private Client client;
    private WebTarget webTarget;

    private ServiceLocator() {
    	String hostname = System.getProperty("bikeapp.hostname");
        String port = System.getProperty("bikeapp.port");
        client = ClientBuilder.newClient();
        webTarget = client.target(String.format("http://%s:%s/rest", hostname, port));
    }

    public static ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }

    public WebTarget getWebTarget() {
        return webTarget;
    }

    // Ensure there's a way to handle the situation if hostname and port need to be reset or changed
}

