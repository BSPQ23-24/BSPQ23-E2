package com.deusto.app.server.remote;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.deusto.app.server.data.domain.User;
import com.deusto.app.server.pojo.UserData;
import com.deusto.app.server.services.UserService;

@Path("/bikeapp")
@Produces(MediaType.APPLICATION_JSON)
public class ResourceFacade {

	// Data structure for manage Server State
	public Map<Long, User> serverState = new HashMap<>();

	@POST
	@Path("/user/register")
	public Response registerUser(UserData userData) {
		return UserService.getInstance().registerUser(userData);
	}

	@POST
	@Path("/user/login")
	public Response loginUser(UserData userData) {
		User user = UserService.getInstance().loginUser(userData.getDni(), userData.getPassword());
		// If user exists
		if (user != null) {
			// If user is not logged in
			if (!this.serverState.values().contains(user)) {
				long token = Calendar.getInstance().getTimeInMillis();
				this.serverState.put(token, user);

				return Response.ok(token).build();
			} else {
				return Response.status(Response.Status.UNAUTHORIZED).entity("User is already logged in").build();
			}

		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
		}
	}

	@GET
	@Path("/user/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHello() {
		return Response.ok("Hello world!").build();
	}

	/*
	@POST
	@Path("/bike/create") public Response createBike(int stationId, Bicycle
	bikeData) { // Bike creation logic goes here... }

	@GET
	@Path("/bike/stations") public Response displayStationsAndBikes() { // Logic to display stations and bikes goes
																		// here... }

	@GET
	@Path("/bike/select") public Response selectBike(int stationId) { // Logic to select a bike goes here... }
	*/
}
