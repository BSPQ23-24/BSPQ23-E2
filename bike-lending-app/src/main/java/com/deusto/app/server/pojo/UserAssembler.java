package com.deusto.app.server.pojo;

import java.util.ArrayList;
import java.util.List;


import com.deusto.app.server.data.domain.User;

public class UserAssembler {
	private static UserAssembler instance;

	private UserAssembler() { }
	
	public static UserAssembler getInstance() {
		if (instance == null) {
			instance = new UserAssembler();
		}
		
		return instance;
	}

	public UserData userToPOJO(User user) {
		UserData userP = new UserData();		
		
		userP.setDni(user.getDni());
		userP.setDateOfBirth(user.getDateOfBirth());
		userP.setMail(user.getMail());
		userP.setName(user.getName());
		userP.setPassword(user.getPassword());
		userP.setSurname(user.getSurname());
		userP.setPhone(user.getPhone());
		
		
		
		
		return userP;
	}

	public List<UserData> usersToPOJO(List<User> users) {		
		List<UserData> userPs = new ArrayList<>();
		
		for (User user : users) {
			userPs.add(this.userToPOJO(user));
		}
		
		return userPs;
	}
}
