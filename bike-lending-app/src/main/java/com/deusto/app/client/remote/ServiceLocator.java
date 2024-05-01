package com.deusto.app.client.remote;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

public class ServiceLocator {
	private static ServiceLocator instance;
	private Client client;
	private WebTarget webTarget;

	private ServiceLocator() {

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

	public void setService(String hostname, String port) {
		client = ClientBuilder.newClient();
		webTarget = client.target(String.format("http://%s:%s/rest", hostname, port));
	}
}
