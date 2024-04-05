package com.deusto.app.server.pojo;

public class UserData {

	private String dni;
	private String password;
	private String name;
	private String surname;
	private String dateOfBirth;
	private String phone;
	private String mail;

	public UserData() {
		// required by serialization
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Override
	public String toString() {
		return "UserData [dni=" + dni + ", password=" + password + ", name=" + name + ", surname=" + surname
				+ ", dateOfBirth=" + dateOfBirth + ", phone=" + phone + ", mail=" + mail + "]";
	}

}
