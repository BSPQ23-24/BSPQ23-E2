package com.deusto.app.server.remote;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.User;
import com.deusto.app.server.pojo.UserData;
import com.deusto.app.server.services.BikeService;
import com.deusto.app.server.services.UserService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/bikeapp")
@Produces(MediaType.APPLICATION_JSON)
public class ResourceFacade {

	// Data structure for manage Server State
	public Map<Long, User> serverState = new HashMap<>();

	@POST
	@Path("/user/register")
	public Response registerUser(UserData userData) {
		boolean register_success = UserService.getInstance().registerUser(userData);

		if (register_success) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is already registered").build();
		}

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
	
	@POST
	@Path("/bike/create")
	public Response createBike(int stationId, Bicycle bikeData, long token) {
	    if (!serverState.containsKey(token)) {
	        return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
	    }
	    return BikeService.getInstance().createBike(stationId, bikeData);
	}

	@GET
	@Path("/bike/stations")
	public Response displayStationsAndBikes(long token) {
	    if (!serverState.containsKey(token)) {
	        return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
	    }
	    return BikeService.getInstance().displayStationsAndBikes();
	}

	@GET
	@Path("/bike/select")
	public Response selectBike(int stationId, long token) {
	    if (!serverState.containsKey(token)) {
	        return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
	    }
	    return BikeService.getInstance().selectBike(stationId);
	}

	@GET
	@Path("/bike/available")
	public Response getAvailableBikesInStation(int stationId, long token) {
	    if (!serverState.containsKey(token)) {
	        return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
	    }
	    return BikeService.getInstance().getAvailableBikesInStation(stationId);
	}
}
