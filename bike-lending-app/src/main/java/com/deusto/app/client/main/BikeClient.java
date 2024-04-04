package com.deusto.app.client.main;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;

import jakarta.ws.rs.client.ClientBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BikeClient {

	protected static final Logger logger = LogManager.getLogger();
	
	private Client client;
	private WebTarget webTarget;
	
	public BikeClient(String hostname, String port) {
		// TODO Auto-generated constructor stub
		client = ClientBuilder.newClient();
		webTarget = client.target(String.format("http://%s:%s/rest/user", hostname, port)); // Pongo user por poner algo, habría que cambiarlo
	}

	public static void main(String[] args) {
		String hostname = args[0];
		String port = args[1];

		new BikeClient(hostname, port);
		BikeClient exampleClient = new BikeClient(hostname, port);
//		 exampleClient.registerUser("test", "test");
	}
}