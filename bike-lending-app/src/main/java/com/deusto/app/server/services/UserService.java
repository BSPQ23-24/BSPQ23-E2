package com.deusto.app.server.services;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.deusto.app.server.data.domain.*;

public class UserService {

    public void registerUser(String DNI, String name, String surname, Date dateOfBirth, String phone, String mail) {
    	PersistenceManagerFactory pm = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        try {
            User user = new User();
            user.setDNI(DNI);
            user.setName(name);
            user.setSurname(surname);
            user.setDateOfBirth(dateOfBirth);
            user.setPhone(phone);
            user.setMail(mail);
            pm.makePersistent(user);
        } finally {
            pm.close();
        }
    }
}