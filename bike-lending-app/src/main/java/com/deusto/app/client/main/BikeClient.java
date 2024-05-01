package com.deusto.app.client.main;

import com.deusto.app.client.remote.ServiceLocator;

public class BikeClient {

	public static void main(String[] args) {
		ServiceLocator.getInstance().setService(args[0], args[1]);

		// Example of user registration
		/*
		 * UserData userData = new UserData(); userData.setDni("12345678A");
		 * userData.setPassword("root"); userData.setName("UsuarioTest");
		 * userData.setSurname("ApellidoTest"); userData.setDateOfBirth("01-01-2000");
		 * userData.setPhone("123456789"); userData.setMail("test@mail.es");
		 * UserController.getInstance().registerUser(userData);
		 */
	}
}