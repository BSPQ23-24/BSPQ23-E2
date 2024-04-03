package com.deusto.app.server.data.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class User {
    @PrimaryKey
    private String DNI;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String phone;
    private String mail;


    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public String toString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        StringBuilder result = new StringBuilder();

        result.append("DNI: ");
        result.append(this.DNI);
        result.append(" / Name: ");
        result.append(this.name);
        result.append(" / Surname: ");
        result.append(this.surname);
        result.append(" / Date of Birth: ");
        result.append(dateFormatter.format(this.dateOfBirth));
        result.append(" / Phone: ");
        result.append(this.phone);
        result.append(" / Email: ");
        result.append(this.mail);

        return result.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (this.getClass().getName().equals(obj.getClass().getName())) {
            return this.DNI.equals(((User)obj).DNI);
        }

        return false;
    }
}