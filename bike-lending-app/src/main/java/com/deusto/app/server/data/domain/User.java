package com.deusto.app.server.data.domain;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

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
	
	public User(String dni, String password, String name, String surname, String dateOfBirth, String phone, String mail) {
		super();
		this.dni = dni;
		this.name = name;
		this.password = password;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.phone = phone;
		this.mail = mail;
	}

	public User() {
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append("DNI: ");
		result.append(this.dni);
		result.append("Password: ");
		result.append(this.password);
		result.append(" / Name: ");
		result.append(this.name);
		result.append(" / Surname: ");
		result.append(this.surname);
		result.append(" / Date of Birth: ");
		result.append(this.dateOfBirth);
		result.append(" / Phone: ");
		result.append(this.phone);
		result.append(" / Email: ");
		result.append(this.mail);

		return result.toString();
	}

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