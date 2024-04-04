package com.deusto.app.server.services;

import javax.jdo.PersistenceManager;
import com.deusto.app.server.data.domain.*;

public class UserService {

    public void registerUser(String DNI, String name, String surname, Date dateOfBirth, String phone, String mail) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
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