package com.deusto.app.server.data.domain;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

/**
 * User class represents a user in the bike rental system.
 * It includes details such as DNI, name, password, surname, date of birth,
 * phone number, and email address.
 */
@PersistenceCapable
public class User {

    @PrimaryKey
    private String dni;
    private String name;
    private String password;
    private String surname;
    private String dateOfBirth;
    private String phone;
    private String mail;
    private boolean admin; //true admin, false client

    /**
     * Constructs a new User with the specified details.
     *
     * @param dni         the user's DNI
     * @param password    the user's password
     * @param name        the user's name
     * @param surname     the user's surname
     * @param dateOfBirth the user's date of birth
     * @param phone       the user's phone number
     * @param mail        the user's email address
     * @param admin       if the user is client or admin
     */
    public User(String dni, String password, String name, String surname, String dateOfBirth, String phone, String mail, boolean admin) {
        this.dni = dni;
        this.name = name;
        this.password = password;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.mail = mail;
        this.admin= admin;  //true admin, false client
    }

    /**
     * Default constructor for User.
     */
    public User() {
    }

    /**
     * Returns the user's DNI.
     *
     * @return the DNI
     */
    public String getDni() {
        return dni;
    }

    /**
     * Sets the user's DNI.
     *
     * @param dni the DNI to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Returns the user's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the user's surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the user's surname.
     *
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns the user's date of birth.
     *
     * @return the date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the user's date of birth.
     *
     * @param dateOfBirth the date of birth to set
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns the user's phone number.
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the user's phone number.
     *
     * @param phone the phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email address
     */
    public String getMail() {
        return mail;
    }

    /**
     * Sets the user's email address.
     *
     * @param mail the email address to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Returns the user's password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
     * Returns a string representation of the user.
     *
     * @return a string containing the user details
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("DNI: ").append(this.dni);
        result.append(" / Password: ").append(this.password);
        result.append(" / Name: ").append(this.name);
        result.append(" / Surname: ").append(this.surname);
        result.append(" / Date of Birth: ").append(this.dateOfBirth);
        result.append(" / Phone: ").append(this.phone);
        result.append(" / Email: ").append(this.mail);
        return result.toString();
    }

    /**
     * Checks if the given object is equal to this user.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return dni.equals(user.dni);
    }
}
