package com.deusto.app.server.remote;

import java.util.List;
import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.UserData;
import com.deusto.app.server.services.BikeService;
import com.deusto.app.server.services.UserService;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/bikeapp")
@Produces(MediaType.APPLICATION_JSON)
public class ResourceFacade {

	@POST
	@Path("/user/changePassword")
	public Response changePassword(@FormParam("dni") String dni, @FormParam("oldPassword") String oldPassword,
			@FormParam("newPassword") String newPassword) {

		boolean change_success = UserService.getInstance().changePassword(dni, oldPassword, newPassword);

		if (change_success) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Failed to change password").build();
		}
	}

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
		long token = UserService.getInstance().loginUser(userData.getDni(), userData.getPassword());

		if (token != -1) {
			return Response.ok(token).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity("Invalid credentials or user is already logged in").build();
		}

	}

	@POST
	@Path("/user/logout")
	public Response logoutUser(@QueryParam("token") long token) {
		boolean logout_success = UserService.getInstance().logoutUser(token);
		if (logout_success) {
			return Response.ok().entity("User logged out successfully").build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token or user not logged in").build();
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
	public Response createBike(@QueryParam("stationId") int stationId, BicycleData bikeData,
			@QueryParam("token") long token) {
		if (UserService.getInstance().isLoggedIn(token)) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
		}

		int newBikeId = BikeService.getInstance().createBike(stationId, bikeData);
		if (newBikeId != -1) {
			return Response.ok("Bike created with ID: " + newBikeId).build();
		} else {
			return Response.serverError().entity("Error creating bike").build();
		}
	}

	@GET
	@Path("/bike/stations")
	public Response displayStationsAndBikes(@QueryParam("token") long token) {
		if (UserService.getInstance().isLoggedIn(token)) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
		}

		String stationsAndBikes = BikeService.getInstance().displayStationsAndBikes();
		return Response.ok(stationsAndBikes).build();
	}

	@GET
	@Path("/bike/select")
	public Response selectBike(@QueryParam("stationId") int stationId, @QueryParam("token") long token) {
		if (UserService.getInstance().isLoggedIn(token)) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
		}

		Bicycle selectedBike = BikeService.getInstance().selectBike(stationId);
		if (selectedBike != null) {
			return Response.ok(selectedBike).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("No available bikes at this station").build();
		}
	}

	@GET
	@Path("/bike/available")
	public Response getAvailableBikesInStation(@QueryParam("stationId") int stationId,
			@QueryParam("token") long token) {
		if (UserService.getInstance().isLoggedIn(token)) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
		}

		List<Bicycle> availableBikes = BikeService.getInstance().getAvailableBikesInStation(stationId);
		if (availableBikes != null) {
			return Response.ok(availableBikes).build();
		} else {
			return Response.serverError().entity("Error getting available bikes").build();
		}
	}

	@GET
	@Path("/bike/{bikeId}")
	public Response getBikeDetails(@PathParam("bikeId") int bikeId, @QueryParam("token") long token) {
		if (UserService.getInstance().isLoggedIn(token)) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
		}

		Bicycle bike = BikeService.getInstance().getBikeById(bikeId);
		if (bike != null) {
			return Response.ok(bike).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Bike not found").build();
		}
	}

}
