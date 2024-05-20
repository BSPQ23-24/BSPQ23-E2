package com.deusto.app.server.remote;

import java.util.List;
import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.User;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationData;
import com.deusto.app.server.pojo.UserData;
import com.deusto.app.server.services.AdminService;
import com.deusto.app.server.services.BikeService;
import com.deusto.app.server.services.UserService;
import com.deusto.app.server.pojo.LoanData;
import com.deusto.app.server.services.LoanService;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


/**
 * ResourceFacade class provides RESTful APIs for bike rental application.
 * It includes user management (registration, login, logout, password change) 
 * and bike management (display stations, available bikes, bike details).
 * 
 * All endpoints produce responses in JSON format.
 */
@Path("/bikeapp")
@Produces(MediaType.APPLICATION_JSON)
public class ResourceFacade {

	/**
     * Changes the password of a user.
     *
     * @param dni the user's DNI
     * @param oldPassword the user's current password
     * @param newPassword the new password to set
     * @return Response indicating success or failure of the password change
     */
	@POST
	@Path("/user/changePassword")
	public Response changePassword(@QueryParam("dni") String dni, @QueryParam("oldPassword") String oldPassword,
			@QueryParam("newPassword") String newPassword) {

		boolean change_success = UserService.getInstance().changePassword(dni, oldPassword, newPassword);

		if (change_success) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Failed to change password").build();
		}
	}

	/**
     * Registers a new user.
     *
     * @param userData the user's data
     * @return Response indicating success or failure of the registration
     */
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

	/**
     * Logs in a user.
     *
     * @param userData the user's data containing DNI and password
     * @return Response containing the user's token if login is successful
     */
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

	/**
     * Logs out a user.
     *
     * @param token the user's authentication token
     * @return Response indicating success or failure of the logout
     */
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

	/**
     * A simple endpoint to test connectivity.
     *
     * @return A plain text "Hello world!" response
     */
	@GET
	@Path("/user/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHello() {
		return Response.ok("Hello world!").build();
	}

	/*
	 * @POST
	 * 
	 * @Path("/bike/create") public Response createBike(@QueryParam("stationId") int
	 * stationId, BicycleData bikeData,
	 * 
	 * @QueryParam("token") long token) { if
	 * (UserService.getInstance().isLoggedIn(token)) { return
	 * Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in")
	 * .build(); }
	 * 
	 * int newBikeId = BikeService.getInstance().createBike(stationId, bikeData); if
	 * (newBikeId != -1) { return Response.ok("Bike created with ID: " +
	 * newBikeId).build(); } else { return
	 * Response.serverError().entity("Error creating bike").build(); } }
	 */

	/**
     * Displays all bike stations.
     *
     * @param token the user's authentication token
     * @return Response containing a list of all bike stations
     */
	@GET
	@Path("/bike/stations")
	public Response displayStations(@QueryParam("token") long token) {
		if (UserService.getInstance().isLoggedIn(token)) {
			List<StationData> stations = BikeService.getInstance().displayStations();
			if (stations != null) {
				return Response.ok(stations).build();
			} else {
				return Response.serverError().entity("Error displaying stations").build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
		}
	}

	/*
	 * @GET
	 * 
	 * @Path("/bike/select") public Response selectBike(@QueryParam("stationId") int
	 * stationId, @QueryParam("token") long token) { if
	 * (UserService.getInstance().isLoggedIn(token)) { return
	 * Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in")
	 * .build(); }
	 * 
	 * BicycleData selectedBike = BikeService.getInstance().selectBike(stationId);
	 * if (selectedBike != null) { return Response.ok(selectedBike).build(); } else
	 * { return Response.status(Response.Status.NOT_FOUND).
	 * entity("No available bikes at this station").build(); } }
	 */
	
	/**
     * Retrieves available bikes at a specific station.
     *
     * @param stationId the ID of the bike station
     * @param token the user's authentication token
     * @return Response containing a list of available bikes
     */
	@GET
	@Path("/bike/available")
	public Response getAvailableBikesInStation(@QueryParam("stationId") int stationId,
			@QueryParam("token") long token) {
		if (UserService.getInstance().isLoggedIn(token)) {
			List<BicycleData> availableBikes = BikeService.getInstance().getAvailableBikesInStation(stationId);
			if (availableBikes != null) {
				return Response.ok(availableBikes).build();
			} else {
				return Response.serverError().entity("Error getting available bikes").build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
		}
	}
	
	/**
     * Retrieves No Available bikes.
     *
     * @param token the user's authentication token
     * @return Response containing a list of no available bikes
     */
	
	@GET
	@Path("/bike/na")
	public Response displayNoAvailableBikes(@QueryParam("token") long token) {
		if (UserService.getInstance().isLoggedIn(token)) {
			List<BicycleData> noAvailableBikes = BikeService.getInstance().displayNoAvailableBikes();
			if (noAvailableBikes != null) {
				return Response.ok(noAvailableBikes).build();
			} else {
				return Response.serverError().entity("Error getting no available bikes").build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
		}
	}

	/**
     * Retrieves the details of a specific bike.
     *
     * @param bikeId the ID of the bike
     * @param token the user's authentication token
     * @return Response containing the bike details
     */
	@GET
	@Path("/bike/{bikeId}")
	public Response getBikeDetails(@PathParam("bikeId") int bikeId, @QueryParam("token") long token) {
		if (UserService.getInstance().isLoggedIn(token)) {
			BicycleData bike = BikeService.getInstance().getBikeById(bikeId);
			if (bike != null) {
				return Response.ok(bike).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Bike not found").build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
		}
	}
	
	/**
     * Adds a new bike.
     *
     * @param BicycleData the ID of the bike
     * @param token the user's authentication token
     * @return Response containing the bike details
     */
	
	@POST
	@Path("/admin/add")
	public Response addBike(BicycleData bikeData, @QueryParam("token") long token) {
		if(UserService.getInstance().isLoggedIn(token)) {
			if(UserService.getInstance().isAdmin(token)) {
				boolean addition_success= AdminService.getInstance().addBike(bikeData);
				
				if(addition_success) {
					return Response.ok().build();
				} else {
					return Response.status(Response.Status.UNAUTHORIZED).entity("Bike is already added").build();
				}
			} else {
				return Response.status(Response.Status.UNAUTHORIZED).entity("You are not authorized!").build();
			}
			
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
		}

	}
	
	@POST
	@Path("/admin/disable")
	public Response disableBike(@QueryParam("bikeId") int bikeId, @QueryParam("token") long token) {
		
		if(UserService.getInstance().isLoggedIn(token)) {
			boolean disable_success= AdminService.getInstance().disableBike(bikeId);
			if(disable_success) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.UNAUTHORIZED).entity("Bike could not be disabled").build();
			}
			
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
		}
	}
	
	@POST
	@Path("/admin/able")
	public Response ableBike(@QueryParam("bikeId") int bikeId, @QueryParam("token") long token) {
		
		if(UserService.getInstance().isLoggedIn(token)) {
			boolean able_success= AdminService.getInstance().ableBike(bikeId);
			if(able_success) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.UNAUTHORIZED).entity("Bike could not be able").build();
			}
			
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
		}
	}
	
	/**
	 * Checks if the login is from admin or client.
	 *
	 * @param token the user's authentication token
	 * @return Response containing true or false
	 */
	
	@GET
	@Path("/admin/isAdmin")
	public Response isAdmin(@QueryParam("token") long token) {
		if(UserService.getInstance().isLoggedIn(token)) {
			boolean admin= UserService.getInstance().isAdmin(token);
				return Response.ok(admin).build();
			
			
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
		}
	}
	
	
	/**
	 * Retrieves all loans.
	 *
	 * @param token the user's authentication token
	 * @return Response containing a list of all loans
	 */
	@GET
	@Path("/loan/all")
	public Response getAllLoans(@QueryParam("token") long token) {
	    if (UserService.getInstance().isLoggedIn(token)) {
	        List<LoanData> loans = LoanService.getInstance().getAllLoans();
	        return Response.ok(loans).build();
	    } else {
	        return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
	    }
	}
	
	/**
	 * Check if the user has an active loan.
	 *
	 * @param token the user's authentication token
	 * @return Response containing a boolean if the user has an active loan
	 */
	@GET
	@Path("/loan/active")
	public Response isLoanActive(@QueryParam("token") long token) {
	    if (UserService.getInstance().isLoggedIn(token)) {
	        LoanData loan = LoanService.getInstance().isLoanActive(token);
	        return Response.ok(loan).build();
	    } else {
	        return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
	    }
	}
	
	/**
	 * Creates a new loan.
	 *
	 * @param loanData the loan data to create
	 * @param token the user's authentication token
	 * @return Response indicating success or failure of loan creation
	 */
	@POST
	@Path("/loan/create")
	public Response createLoan(LoanData loanData, @QueryParam("token") long token) {
	    if (UserService.getInstance().isLoggedIn(token)) {
	        boolean create_success = LoanService.getInstance().createLoan(loanData);
	        if (create_success) {
	            return Response.ok().build();
	        } else {
	            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create loan").build();
	        }
	    } else {
	        return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
	    }
	}
	
	/**
	 * Deletes a loan by ID.
	 *
	 * @param loanId the ID of the loan to delete
	 * @param token the user's authentication token
	 * @return Response indicating success or failure of loan deletion
	 */
	@DELETE
	@Path("/loan/delete/{loanId}")
	public Response deleteLoan(@PathParam("loanId") int loanId, @QueryParam("token") long token) {
	    if (UserService.getInstance().isLoggedIn(token)) {
	        boolean delete_success = LoanService.getInstance().deleteLoan(loanId);
	        if (delete_success) {
	            return Response.ok().build();
	        } else {
	            return Response.status(Response.Status.NOT_FOUND).entity("Loan not found").build();
	        }
	    } else {
	        return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
	    }
	}

}
