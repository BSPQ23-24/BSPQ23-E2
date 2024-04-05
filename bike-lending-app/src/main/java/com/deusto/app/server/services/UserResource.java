package com.deusto.app.server.services;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import jakarta.ws.rs.GET;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.ws.rs.POST;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.deusto.app.server.data.domain.*;
import com.deusto.app.server.pojo.UserData;


@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	
	protected static final Logger logger = LogManager.getLogger();
	
	private PersistenceManager pm=null;
	private Transaction tx=null;
	
	public UserResource() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
	}

	@POST
	@Path("/register")
    public Response registerUser(UserData userData) {
        try
        {	
            tx.begin();
            logger.info("Checking whether the user already exits or not: '{}'", userData.getDni());
			User user = null;
			try {
				user = pm.getObjectById(User.class, userData.getDni());
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}
			logger.info("User: {}", user);
			if (user != null) {
				logger.info("Setting password user: {}", user);
				user.setPassword(userData.getPassword());
				logger.info("Password set user: {}", user);
			} else {
				logger.info("Creating user: {}", user);
				user = new User(userData.getDni(), userData.getName(), userData.getPassword(), userData.getSurname(), userData.getDateOfBirth(), userData.getPhone(), userData.getMail());
				pm.makePersistent(user);					 
				logger.info("User created: {}", user);
			}
			tx.commit();
			return Response.ok().build();
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
      
		}
    }
	
	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHello() {
		return Response.ok("Hello world!").build();
	}
}