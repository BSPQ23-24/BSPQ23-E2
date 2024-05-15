package com.deusto.app.client.remote;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

/**
 * ServiceLocator class is responsible for configuring and providing access to the
 * remote web service. It follows the singleton pattern to ensure a single instance
 * of the service locator throughout the application.
 */
public class ServiceLocator {
	private static ServiceLocator instance;
	private Client client;
	private WebTarget webTarget;

	/**
     * Private constructor to prevent instantiation.
     */
	private ServiceLocator() {

	}

	/**
     * Returns the singleton instance of ServiceLocator.
     *
     * @return ServiceLocator instance
     */
	public static ServiceLocator getInstance() {
		if (instance == null) {
			instance = new ServiceLocator();
		}
		return instance;
	}

	/**
     * Returns the WebTarget for the configured web service.
     *
     * @return WebTarget configured with the service URL
     */
	public WebTarget getWebTarget() {
		return webTarget;
	}

	/**
     * Configures the service with the specified hostname and port.
     *
     * @param hostname the hostname of the service
     * @param port     the port number of the service
     */
	public void setService(String hostname, String port) {
		client = ClientBuilder.newClient();
		webTarget = client.target(String.format("http://%s:%s/rest", hostname, port));
	}
}
