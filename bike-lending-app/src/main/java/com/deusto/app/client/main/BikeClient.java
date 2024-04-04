package com.deusto.app.client.main;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;

import jakarta.ws.rs.client.ClientBuilder;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.deusto.app.server.pojo.UserData;

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
		
		exampleClient.registerUser("12345678A", "root", "UsuarioTest", "ApellidoTest", "01-01-2000", "123456789", "test@mail.es");
	}
	
	public void registerUser(String dni, String password, String name, String surname, String dateOfBirth, String phone, String mail) {
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
		
		WebTarget registerUserWebTarget = webTarget.path("register");
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);
		
		UserData userData = new UserData();
		userData.setDni(dni);
		userData.setPassword(password);
		userData.setName(name);
		userData.setSurname(surname);
		try {
			userData.setDateOfBirth(dateFormatter.parse(dateOfBirth));
		} catch (ParseException e) {
			logger.info("Exception launched: {}", e.getMessage());
		}
		userData.setPhone(phone);
		userData.setMail(mail);
		
		Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("User correctly registered");
		}
	}
}